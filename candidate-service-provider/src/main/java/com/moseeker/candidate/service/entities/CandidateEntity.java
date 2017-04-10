package com.moseeker.candidate.service.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.moseeker.candidate.service.checkout.ParamCheckTool;
import com.moseeker.candidate.service.exception.CandidateCategory;
import com.moseeker.candidate.service.exception.CandidateExceptionFactory;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.candidate.struct.CandidateList;
import com.moseeker.thrift.gen.candidate.struct.CandidateListParam;
import com.moseeker.thrift.gen.candidate.struct.RecommendResult;
import com.moseeker.thrift.gen.candidate.struct.RecommmendParam;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.*;
import org.apache.commons.lang.BooleanUtils;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.moseeker.candidate.service.Candidate;
import com.moseeker.candidate.service.dao.CandidateDBDao;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.thrift.gen.common.struct.Response;

/**
 * 候选人实体，提供候选人相关业务
 * //todo 数据库访问操作，如果可以并行处理，则并行处理
 * Created by jack on 10/02/2017.
 */
@Component
public class CandidateEntity implements Candidate {

    Logger logger = LoggerFactory.getLogger(CandidateEntity.class);

    ThreadPool tp = ThreadPool.Instance;

    /**
     * C端用户查看职位，判断是否生成候选人数据
     * @param userID 用户编号
     * @param positionID 职位编号
     * @param shareChainID 是否来自员工转发
     */
    @CounterIface
    @Override
    public void glancePosition(int userID, int positionID, int shareChainID) {
        ValidateUtil vu = new ValidateUtil();
        vu.addRequiredValidate("微信编号", userID, null, null);
        vu.addRequiredValidate("职位编号", positionID, null, null);
        vu.addRequiredValidate("是否来自员工转发", shareChainID, null, null);
        vu.validate();
        if(vu.getVerified().get()) {
            try {
                //检查候选人数据是否存在

                Future userFuture = tp.startTast(() -> CandidateDBDao.getUserByID(userID));
                Future positionFuture = tp.startTast(() -> CandidateDBDao.getPositionByID(positionID));
                Future shareChainDOFuture = tp.startTast(() -> CandidateDBDao.getCandidateShareChain(shareChainID));
                CandidateShareChainDO shareChainDO = null;
                UserUserDO userUserDO = null;
                JobPositionDO jobPositionDO = null;
                try {
                    shareChainDO = (CandidateShareChainDO) shareChainDOFuture.get();
                    userUserDO = (UserUserDO) userFuture.get();
                    jobPositionDO = (JobPositionDO) positionFuture.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage(), e);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage(), e);
                }

                if(userUserDO != null && jobPositionDO != null) {

                    boolean fromEmployee = false;       //是否是员工转发
                    if(shareChainDO != null) {
                        UserEmployeeDO employeeDO = CandidateDBDao.getEmployee(shareChainDO.getRootRecomUserId());
                        if(employeeDO != null) {
                            fromEmployee = true;
                        }
                    }

                    //暂时不考虑HR的问题
                    List<CandidateRemarkDO> crs = CandidateDBDao.getCandidateRemarks(userID, jobPositionDO.getCompanyId());
                    if(crs != null && crs.size() > 0) {
                        crs.forEach(candidateRemark -> candidateRemark.setStatus((byte)1));
                    }
                    CandidateDBDao.updateCandidateRemarks(crs);

                    String date = new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS");
                    Optional<CandidatePositionDO> cp = CandidateDBDao.getCandidatePosition(positionID, userID);



                    if(cp.isPresent()) {
                        cp.get().setViewNumber(cp.get().getViewNumber()+1);
                        cp.get().setSharedFromEmployee(fromEmployee?(byte)1:0);
                        CandidateDBDao.updateCandidatePosition(cp.get());
                    } else {
                        Optional<CandidateCompanyDO> candidateCompany = CandidateDBDao.getCandidateCompanyByUserIDCompanyID(userID, jobPositionDO.getCompanyId());
                        if(candidateCompany.isPresent()) {
                            candidateCompany.get().setCompanyId(jobPositionDO.getCompanyId());
                            candidateCompany.get().setNickname(userUserDO.getNickname());
                            candidateCompany.get().setHeadimgurl(userUserDO.getHeadimg());
                            candidateCompany.get().setSysUserId(userID);
                            candidateCompany.get().setMobile(String.valueOf(userUserDO.getMobile()));
                            candidateCompany.get().setEmail(userUserDO.getEmail());
                            candidateCompany.get().setUpdateTime(date);
                            CandidateDBDao.updateCandidateCompany(candidateCompany.get());
                        }
                        cp.get().setCandidateCompanyId(candidateCompany.get().getId());
                        cp.get().setViewNumber(1);
                        cp.get().setUpdateTime(date);
                        cp.get().setSharedFromEmployee(fromEmployee?(byte)1:0);
                        CandidateDBDao.saveCandidatePosition(cp.get());
                    }
                }
            } catch (TException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @CounterIface
	@Override
	public Response changeInteresting(int user_id, int position_id, byte is_interested) {
		Response response = ResponseUtils.success("{}");
		Optional<CandidatePositionDO> position = CandidateDBDao.getCandidatePosition(position_id, user_id);
		Boolean isInterested = BooleanUtils.toBooleanObject(is_interested);
		if (position.filter(f -> f.userId != 0 && f.isInterested != isInterested).isPresent()) {
			try {
				CandidateDBDao.updateCandidatePosition(position.map(m -> m.setIsInterested(isInterested)).map(m -> m.setUpdateTime(LocalDateTime.now().withNano(0).toString().replace('T', ' '))).get());
			} catch (TException e) {
				logger.error(e.getMessage(), e);
				response = ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
			}
		}
		return response;
	}

    /**
     * 查找职位转发浏览记录
     * @param param 查询参数
     * @return 职位转发浏览记录
     * @throws BIZException
     */
    @Override
    public List<CandidateList> candidateList(CandidateListParam param) throws BIZException {

        List<CandidateList> result = new ArrayList<>();

        ValidateUtil vu = ParamCheckTool.checkCandidateList(param);
        String message = vu.validate();
        if(!StringUtils.isNullOrEmpty(message)) {
            throw CandidateExceptionFactory.buildCheckFailedException(message);
        }

        //是否开启挖掘被动求职者
        boolean passiveSeeker = CandidateDBDao.isStartPassiveSeeker(param.getCompanyId());
        if(!passiveSeeker) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_NOT_START);
        }

        /** 查找职位转发浏览记录 */
        List<CandidateRecomRecordDO> candidateRecomRecordDOList =
                CandidateDBDao.getCandidateRecomRecordDO(param.getPostUserId(), param.getClickTime(), param.getRecoms());

        if(candidateRecomRecordDOList != null && candidateRecomRecordDOList.size() > 0) {

            /** 并行查找职位信息，被动求职者信息，推荐人信息 */
            Future positionFuture = findPositionList(candidateRecomRecordDOList);
            Future presenteeFuture = findPresenteeList(candidateRecomRecordDOList);
            Future repostFuture = findRepostFuture(candidateRecomRecordDOList);

            /** 封装职位标题，推荐人信息，被动求职者信息 */
            List<JobPositionDO> jobPositionDOList = null;
            try {
                jobPositionDOList = (List<JobPositionDO>) positionFuture.get();
                if(jobPositionDOList != null && jobPositionDOList.size() > 0) {

                    List<UserUserDO> presenteeUserList = convertPresenteeFuture(presenteeFuture);    //候选人
                    List<UserUserDO> repostUserList = convertRepostFuture(repostFuture);             //推荐者

                    for(JobPositionDO positionDO : jobPositionDOList) {
                        CandidateList postionCandidate = addPositionCandidate(positionDO, candidateRecomRecordDOList, presenteeUserList, repostUserList);
                        result.add(postionCandidate);
                    }
                } else {
                    throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_CANDIDATES_POSITION_NOT_EXIST);
                }
            } catch (InterruptedException | ExecutionException e) {
                logger.error(e.getMessage(), e);
                throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_CANDIDATES_POSITION_NOT_EXIST);
            }
        } else {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_CANDIDATES_RECORD_NOT_EXIST);
        }
        return result;
    }

    @Override
    public RecommendResult recommends(int companyId, List<Integer> idList) throws BIZException {

        /** 参数校验 */
        ValidateUtil vu = ParamCheckTool.checkRecommends(companyId, idList);
        String message = vu.validate();
        if(!StringUtils.isNullOrEmpty(message)) {
            throw CandidateExceptionFactory.buildCheckFailedException(message);
        }

        /** 是否开启被动求职者 */
        boolean passiveSeeker = CandidateDBDao.isStartPassiveSeeker(companyId);
        if(!passiveSeeker) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_NOT_START);
        }

        RecommendResult recommendResult = new RecommendResult();
        recommendResult.setRecomTotal(idList.size());
        recommendResult.setRecomIndex(0);
        recommendResult.setRecomIgnore(0);

        /** 查找职位转发浏览记录 */
        List<CandidateRecomRecordDO> candidateRecomRecordDOList = CandidateDBDao.getCandidateRecomRecordDOByIdList(idList);
        if(candidateRecomRecordDOList != null && candidateRecomRecordDOList.size() > 0) {
            CandidateRecomRecordDO candidateRecomRecordDO = candidateRecomRecordDOList.get(0);

            /** 查询职位信息和浏览者信息 */
            Future positionListFuture = findPositionFutureById(candidateRecomRecordDO.getPositionId());
            Future presenteeListFuture = findPresenteeFutureById(candidateRecomRecordDO.getPresenteeUserId());

            //组装查询结果
            recommendResult = assembleResult(recommendResult, candidateRecomRecordDO, positionListFuture, presenteeListFuture);
        }

        return recommendResult;
    }

    @Override
    public RecommendResult recommend(RecommmendParam param) throws BIZException {

        /** 参数校验 */
        ValidateUtil vu = ParamCheckTool.checkRecommend(param);


        return null;
    }


    /**
     * 组装查询结果
     * @param recommendResult 查询结果
     * @param candidateRecomRecordDO 职位转发浏览记录
     * @param positionListFuture 职位信息
     * @param presenteeListFuture 浏览者信息
     * @return 查询结果
     */
    private RecommendResult assembleResult(RecommendResult recommendResult, CandidateRecomRecordDO candidateRecomRecordDO,
                                Future positionListFuture, Future presenteeListFuture) {
        recommendResult.setId(candidateRecomRecordDO.getId());
        try {
            JobPositionDO positionDO = (JobPositionDO) positionListFuture.get();
            if(positionDO != null) {
                recommendResult.setPositionName(positionDO.getTitle());
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e);
        }
        try {
            UserUserDO userUserDO = (UserUserDO) presenteeListFuture.get();
            if(userUserDO != null) {
                String name = StringUtils.isNotNullOrEmpty(userUserDO.getName()) ? userUserDO.getName():userUserDO.getNickname();
                recommendResult.setPresenteeName(name);
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e);
        }
        return recommendResult;
    }

    /**
     * 根据编号查找用户信息
     * @param presenteeUserId 浏览者编号
     * @return 浏览者信息
     */
    private Future findPresenteeFutureById(int presenteeUserId) {
        Future future = tp.startTast(() -> CandidateDBDao.getUserByID(presenteeUserId));
        return future;
    }

    /**
     * 根据职位编号查找职位信息
     * @param positionId 职位编号
     * @return 职位信息
     */
    private Future findPositionFutureById(int positionId) {
        Future future = tp.startTast(() -> {
            return CandidateDBDao.getPositionByID(positionId);
        });
        return future;
    }


    /**
     * 查找转发者信息
     * @param candidateRecomRecordDOList 职位转发浏览记录
     * @return 转发者信息
     */
    private Future findRepostFuture(List<CandidateRecomRecordDO> candidateRecomRecordDOList) {
        Future repostFuture = tp.startTast(() -> {
            List<Integer> repostIdList = candidateRecomRecordDOList.stream()
                    .map(candidateRecomRecordDO -> candidateRecomRecordDO.getRepostUserId())
                    .collect(Collectors.toList());
            return CandidateDBDao.getUserByIDList(repostIdList);
        });
        return repostFuture;
    }

    /**
     * 查找职位浏览者信息
     * @param candidateRecomRecordDOList 职位转发浏览记录
     * @return 浏览者信息
     */
    private Future findPresenteeList(List<CandidateRecomRecordDO> candidateRecomRecordDOList) {
        Future presenteeFuture = tp.startTast(() -> {
            List<Integer> presenteeIdList = candidateRecomRecordDOList.stream()
                    .map(candidateRecomRecordDO -> candidateRecomRecordDO.getPresenteeUserId())
                    .collect(Collectors.toList());
            return CandidateDBDao.getUserByIDList(presenteeIdList);
        });
        return presenteeFuture;
    }

    /**
     * 查找职位
     * @param candidateRecomRecordDOList 职位转发浏览记录
     * @return 职位信息
     */
    private Future findPositionList(List<CandidateRecomRecordDO> candidateRecomRecordDOList) {
        Future positionFuture = tp.startTast(() -> {
            List<Integer> positionIdList = candidateRecomRecordDOList.stream()
                    .map(candidateRecomRecordDO -> candidateRecomRecordDO.getPositionId())
                    .collect(Collectors.toList());
            return CandidateDBDao.getPositionByIdList(positionIdList);
        });
        return positionFuture;
    }

    /**
     * 生成职位候选人信息
     * @param positionDO 职位信息
     * @param candidateRecomRecordDOList 职位转发浏览记录
     * @param presenteeUserList 被动求职者
     * @param repostUserList 推荐人信息
     * @return 职位候选人信息
     */
    private CandidateList addPositionCandidate(JobPositionDO positionDO,
                                               List<CandidateRecomRecordDO> candidateRecomRecordDOList,
                                               List<UserUserDO> presenteeUserList, List<UserUserDO> repostUserList) {
        CandidateList postionCandidate = new CandidateList();
        postionCandidate.setPositionId(positionDO.getId());
        postionCandidate.setPositionName(positionDO.getTitle());

        List<CandidateRecomRecordDO> candidateRecomRecordDOPositionList = candidateRecomRecordDOList
                .stream()
                .filter(candidateRecomRecordDO -> candidateRecomRecordDO.getPositionId() == positionDO.getId())
                .collect(Collectors.toList());

        List<com.moseeker.thrift.gen.candidate.struct.Candidate> candidates
                = addCandidate(candidateRecomRecordDOPositionList, presenteeUserList, repostUserList);

        postionCandidate.setCandidates(candidates);
        return postionCandidate;
    }

    /**
     * 生成职位下的候选人信息
     * @param candidateRecomRecordDOPositionList 职位转发浏览记录
     * @param presenteeUserList 被动求职者
     * @param repostUserList 推荐人信息
     * @return 职位相关的被动求职者信息
     */
    private List<com.moseeker.thrift.gen.candidate.struct.Candidate> addCandidate(List<CandidateRecomRecordDO> candidateRecomRecordDOPositionList, List<UserUserDO> presenteeUserList, List<UserUserDO> repostUserList) {

        List<com.moseeker.thrift.gen.candidate.struct.Candidate> candidates = new ArrayList<>();

        for (CandidateRecomRecordDO candidateRecomRecordDO : candidateRecomRecordDOPositionList) {

            com.moseeker.thrift.gen.candidate.struct.Candidate candidate = new com.moseeker.thrift.gen.candidate.struct.Candidate();
            candidate.setIsRecom(candidateRecomRecordDO.getIsRecom());

            setPresentee(candidate, presenteeUserList, candidateRecomRecordDO);

            setRepost(candidate, repostUserList, candidateRecomRecordDO);

            candidates.add(candidate);
        }
        return candidates;
    }

    /**
     * 封装推荐人信息
     * @param candidate 被动求职者和推荐者信息
     * @param repostUserList 推荐人列表
     * @param candidateRecomRecordDO 职位转发浏览记录
     */
    private void setRepost(com.moseeker.thrift.gen.candidate.struct.Candidate candidate,
                           List<UserUserDO> repostUserList, CandidateRecomRecordDO candidateRecomRecordDO) {
        if(repostUserList != null && repostUserList.size() > 0) {
            Optional<UserUserDO> userUserDOOptional = repostUserList.stream()
                    .filter(userUserDO -> userUserDO.getId() == candidateRecomRecordDO.getRepostUserId())
                    .findAny();
            if(userUserDOOptional.isPresent()) {
                candidate.setPresenteeFriendId(userUserDOOptional.get().getId());
                String name = StringUtils.isNotNullOrEmpty(userUserDOOptional.get().getName())
                        ? userUserDOOptional.get().getName():userUserDOOptional.get().getNickname();
                candidate.setPresenteeFriendName(name);
            }
        }
    }

    /**
     * 封装求职者信息
     * @param candidate 被动求职者和推荐者信息
     * @param presenteeUserList 被动求职者列表
     * @param candidateRecomRecordDO 职位转发浏览记录
     */
    private void setPresentee(com.moseeker.thrift.gen.candidate.struct.Candidate candidate,
                              List<UserUserDO> presenteeUserList, CandidateRecomRecordDO candidateRecomRecordDO) {
        if(presenteeUserList != null && presenteeUserList.size() > 0) {
            Optional<UserUserDO> userUserDOOptional = presenteeUserList
                    .stream()
                    .filter(presentee -> presentee.getId() == candidateRecomRecordDO.getPresenteeUserId()).findAny();
            if(userUserDOOptional.isPresent()) {
                candidate.setPresenteeUserId(userUserDOOptional.get().getId());
                String name = StringUtils.isNotNullOrEmpty(userUserDOOptional.get().getName())
                        ? userUserDOOptional.get().getName():userUserDOOptional.get().getNickname();
                candidate.setPresenteeName(name);
                candidate.setPresenteeLogo(userUserDOOptional.get().getHeadimg());
            }
        }
    }

    private List<UserUserDO> convertRepostFuture(Future repostFuture) {
        try {
            return  (List<UserUserDO>) repostFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private List<UserUserDO> convertPresenteeFuture(Future presenteeFuture) {
        try {
            return  (List<UserUserDO>) presenteeFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

}
