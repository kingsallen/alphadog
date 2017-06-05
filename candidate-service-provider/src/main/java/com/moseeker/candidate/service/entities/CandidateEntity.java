package com.moseeker.candidate.service.entities;

import com.moseeker.candidate.constant.RecomType;
import com.moseeker.candidate.service.Candidate;
import com.moseeker.candidate.service.checkout.ParamCheckTool;
import com.moseeker.candidate.service.dao.CandidateDBDao;
import com.moseeker.candidate.service.exception.CandidateCategory;
import com.moseeker.candidate.service.exception.CandidateExceptionFactory;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.biztools.RecruitmentScheduleEnum;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.Category;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.thrift.gen.candidate.struct.*;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.*;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateCompanyDO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidatePositionDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrPointsConfDO;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang.BooleanUtils;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 候选人实体，提供候选人相关业务
 * //todo 数据库访问操作，如果可以并行处理，则并行处理
 * Created by jack on 10/02/2017.
 */
@Component
@CounterIface
@Transactional
public class CandidateEntity implements Candidate {

    Logger logger = LoggerFactory.getLogger(CandidateEntity.class);

    ThreadPool tp = ThreadPool.Instance;

    @Autowired
    private CandidateDBDao candidateDBDao;

    /**
     * C端用户查看职位，判断是否生成候选人数据
     *
     * @param userID       用户编号
     * @param positionID   职位编号
     * @param shareChainID 是否来自员工转发
     */
    @Override
    @CounterIface
    public void glancePosition(int userID, int positionID, int shareChainID) {
        logger.info("CandidateEntity glancePosition userId:{}, positionID:{}, shareChainID:{}", userID, positionID, shareChainID);
        ValidateUtil vu = new ValidateUtil();
        vu.addRequiredValidate("微信编号", userID, null, null);
        vu.addRequiredValidate("职位编号", positionID, null, null);
        vu.addRequiredValidate("是否来自员工转发", shareChainID, null, null);
        vu.validate();
        if (vu.getVerified().get()) {
            try {
                //检查候选人数据是否存在
                Future userFuture = tp.startTast(() -> candidateDBDao.getUserByID(userID));
                Future positionFuture = tp.startTast(() -> candidateDBDao.getPositionByID(positionID));
                Future shareChainDOFuture = tp.startTast(() -> candidateDBDao.getCandidateShareChain(shareChainID));
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

<<<<<<< HEAD
                if (userUserDO != null && jobPositionDO != null) {
=======
                if(userUserDO != null && userUserDO.getId() > 0 && jobPositionDO != null && jobPositionDO.getId() > 0) {
>>>>>>> master

                    logger.info("CandidateEntity glancePosition userUserDO:{}, jobPositionDO:{}", userUserDO, jobPositionDO);
                    boolean fromEmployee = false;       //是否是员工转发
<<<<<<< HEAD
                    if (shareChainDO != null) {
                        UserEmployeeDO employeeDO = candidateDBDao.getEmployee(shareChainDO.getRootRecomUserId());
                        if (employeeDO != null) {
=======
                    if(shareChainDO != null) {
                        UserEmployeeDO employeeDO = CandidateDBDao.getEmployee(shareChainDO.getRootRecomUserId(),
                                jobPositionDO.getCompanyId());
                        logger.info("CandidateEntity glancePosition employeeDO:{}", employeeDO);
                        if(employeeDO != null && employeeDO.getId()> 0) {
>>>>>>> master
                            fromEmployee = true;
                            logger.info("CandidateEntity glancePosition 是员工转发");
                        }
                    }

                    //暂时不考虑HR的问题
<<<<<<< HEAD
                    List<CandidateRemarkDO> crs = candidateDBDao.getCandidateRemarks(userID, jobPositionDO.getCompanyId());
                    if (crs != null && crs.size() > 0) {
                        crs.forEach(candidateRemark -> candidateRemark.setStatus((byte) 1));
=======
                    List<CandidateRemarkDO> crs = CandidateDBDao.getCandidateRemarks(userID, jobPositionDO.getCompanyId());
                    if(crs != null && crs.size() > 0) {
                        crs.forEach(candidateRemark -> candidateRemark.setStatus((byte)1));
                        logger.info("CandidateEntity glancePosition crs crs");
>>>>>>> master
                    }
                    candidateDBDao.updateCandidateRemarks(crs);

                    String date = new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS");
<<<<<<< HEAD
                    Optional<CandidatePositionDO> cp = candidateDBDao.getCandidatePosition(positionID, userID);


                    if (cp.isPresent()) {
                        cp.get().setViewNumber(cp.get().getViewNumber() + 1);
                        cp.get().setSharedFromEmployee(fromEmployee ? (byte) 1 : 0);
                        candidateDBDao.updateCandidatePosition(cp.get());
                    } else {
                        Optional<CandidateCompanyDO> candidateCompanyDOOptional = candidateDBDao.getCandidateCompanyByUserIDCompanyID(userID, jobPositionDO.getCompanyId());
                        CandidateCompanyDO candidateCompanyDO = null;
                        if (!candidateCompanyDOOptional.isPresent()) {
                            candidateCompanyDO = candidateCompanyDOOptional.get();
=======
                    Optional<CandidatePositionDO> cp = CandidateDBDao.getCandidatePosition(positionID, userID);

                    if(cp.isPresent()) {
                        logger.info("CandidateEntity glancePosition campany_position is exist! cp:{}", cp.get());
                        cp.get().setViewNumber(cp.get().getViewNumber()+1);
                        cp.get().setSharedFromEmployee(fromEmployee?(byte)1:0);
                        CandidateDBDao.updateCandidatePosition(cp.get());
                    } else {
                        logger.info("CandidateEntity glancePosition campany_position not exist!");
                        Optional<CandidateCompanyDO> candidateCompanyDOOptional = CandidateDBDao.getCandidateCompanyByUserIDCompanyID(userID, jobPositionDO.getCompanyId());
                        CandidateCompanyDO candidateCompanyDO = null;
                        if(!candidateCompanyDOOptional.isPresent()) {
                            candidateCompanyDO = new CandidateCompanyDO();
>>>>>>> master
                            candidateCompanyDO.setCompanyId(jobPositionDO.getCompanyId());
                            candidateCompanyDO.setNickname(userUserDO.getNickname());
                            candidateCompanyDO.setHeadimgurl(userUserDO.getHeadimg());
                            candidateCompanyDO.setSysUserId(userID);
                            candidateCompanyDO.setMobile(String.valueOf(userUserDO.getMobile()));
                            candidateCompanyDO.setEmail(userUserDO.getEmail());
                            candidateCompanyDO.setUpdateTime(date);
                            candidateDBDao.saveCandidateCompany(candidateCompanyDO);
                        } else {
                            candidateCompanyDO = candidateCompanyDOOptional.get();
                        }
                        CandidatePositionDO candidatePositionDO = new CandidatePositionDO();
                        candidatePositionDO.setCandidateCompanyId(candidateCompanyDO.getId());
                        candidatePositionDO.setViewNumber(1);
                        candidatePositionDO.setUpdateTime(date);
                        candidatePositionDO.setSharedFromEmployee(fromEmployee ? (byte) 1 : 0);
                        candidatePositionDO.setPositionId(positionID);
                        candidatePositionDO.setUserId(userID);
                        candidateDBDao.saveCandidatePosition(candidatePositionDO);
                    }
                }
            } catch (TException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     *
     * @param user_id
     * @param position_id
     * @param is_interested
     * @return
     */
    @Override
    @CounterIface
    public Response changeInteresting(int user_id, int position_id, byte is_interested) {
        Response response = ResponseUtils.success("{}");
        Optional<CandidatePositionDO> position = candidateDBDao.getCandidatePosition(position_id, user_id);
        Boolean isInterested = BooleanUtils.toBooleanObject(is_interested);
        if (position.filter(f -> f.userId != 0 && BooleanUtils.toBooleanObject(f.isInterested) != isInterested).isPresent()) {
            try {
                candidateDBDao.updateCandidatePosition(position.map(m -> m.setIsInterested(is_interested)).map(m -> m.setUpdateTime(LocalDateTime.now().withNano(0).toString().replace('T', ' '))).get());
            } catch (TException e) {
                logger.error(e.getMessage(), e);
                response = ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
            }
        }
        return response;
    }

    /**
     * 查找职位转发浏览记录
     *
     * @param param 查询参数
     * @return 职位转发浏览记录
     */
    @Override
    @CounterIface
    public List<CandidateList> candidateList(CandidateListParam param) throws BIZException {
        logger.info("CandidateEntiry candidateList param:{}", param);

        List<CandidateList> result = new ArrayList<>();

        ValidateUtil vu = ParamCheckTool.checkCandidateList(param);
        String message = vu.validate();
        if (!StringUtils.isNullOrEmpty(message)) {
            throw CandidateExceptionFactory.buildCheckFailedException(message);
        }

        //是否开启挖掘被动求职者
        boolean passiveSeeker = candidateDBDao.isStartPassiveSeeker(param.getCompanyId());
        if (!passiveSeeker) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_NOT_START);
        }

        /** 查找职位转发浏览记录 */
        List<CandidateRecomRecordDO> candidateRecomRecordDOList =
                candidateDBDao.listCandidateRecomRecordDO(param.getPostUserId(), param.getClickTime(), param.getRecoms());

        if (candidateRecomRecordDOList != null && candidateRecomRecordDOList.size() > 0) {

            /** 并行查找职位信息，被动求职者信息，推荐人信息 */
            Future positionFuture = findPositionList(candidateRecomRecordDOList);
            Future presenteeFuture = findPresenteeList(candidateRecomRecordDOList);
            Future repostFuture = findRepostFuture(candidateRecomRecordDOList);
            Future candidatePositionFuture = findCandidatePositionFuture(candidateRecomRecordDOList);

            /** 封装职位标题，推荐人信息，被动求职者信息 */
            List<JobPositionDO> jobPositionDOList = null;
            try {
                jobPositionDOList = (List<JobPositionDO>) positionFuture.get();
<<<<<<< HEAD
                if (jobPositionDOList != null && jobPositionDOList.size() > 0) {
=======
                logger.info("CandidateEntity candidateList jobPositionDOList:{}",jobPositionDOList);
                if(jobPositionDOList != null && jobPositionDOList.size() > 0) {
>>>>>>> master

                    List<UserUserDO> presenteeUserList = convertFuture(presenteeFuture);    //候选人
                    List<UserUserDO> repostUserList = convertFuture(repostFuture);             //推荐者
                    List<CandidatePositionDO> candidatePositionDOList = convertFuture(candidatePositionFuture); //候选人查看职位记录

                    for (JobPositionDO positionDO : jobPositionDOList) {
                        CandidateList postionCandidate = addPositionCandidate(positionDO, candidateRecomRecordDOList,
                                presenteeUserList, repostUserList, candidatePositionDOList);
                        logger.info("CandidateEntity candidateList postionCandidate:{}",postionCandidate);
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
        logger.info("CandidateEntity candidateList result:{}",result);
        return result;
    }

    @Override
    @CounterIface
    public RecommendResult getRecommendations(int companyId, List<Integer> idList) throws BIZException {

        /** 参数校验 */
        ValidateUtil vu = ParamCheckTool.checkRecommends(companyId, idList);
        String message = vu.validate();
        if (!StringUtils.isNullOrEmpty(message)) {
            throw CandidateExceptionFactory.buildCheckFailedException(message);
        }

        /** 是否开启被动求职者 */
        boolean passiveSeeker = candidateDBDao.isStartPassiveSeeker(companyId);
        if (!passiveSeeker) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_NOT_START);
        }

        RecommendResult recommendResult = new RecommendResult();
        recommendResult.setRecomTotal(idList.size());
        recommendResult.setRecomIndex(0);
        recommendResult.setRecomIgnore(0);
        recommendResult.setNextOne(false);

        /** 查找职位转发浏览记录 */
<<<<<<< HEAD
        List<CandidateRecomRecordDO> candidateRecomRecordDOList = candidateDBDao.getCandidateRecomRecordDOByIdList(idList);
        if (candidateRecomRecordDOList != null && candidateRecomRecordDOList.size() > 0) {
=======
        List<CandidateRecomRecordDO> candidateRecomRecordDOList = CandidateDBDao.getCandidateRecomRecordDOByIdList(idList);
        if(candidateRecomRecordDOList != null && candidateRecomRecordDOList.size() > 0) {
            candidateRecomRecordDOList.forEach(candidateRecomRecordDO -> {
                candidateRecomRecordDO.setIsRecom((byte)RecomType.SELECTED.getValue());
            });
            CandidateDBDao.updateCandidateRecomRecords(candidateRecomRecordDOList);

>>>>>>> master
            CandidateRecomRecordDO candidateRecomRecordDO = candidateRecomRecordDOList.get(0);

            /** 查询职位信息和浏览者信息 */
            Future positionListFuture = findPositionFutureById(candidateRecomRecordDO.getPositionId());
            Future presenteeListFuture = findUserFutureById(candidateRecomRecordDO.getPresenteeUserId());

            //组装查询结果
            recommendResult = assembleResult(recommendResult, candidateRecomRecordDO, positionListFuture, presenteeListFuture);
            if (candidateRecomRecordDOList.size() > 1) {
                recommendResult.setNextOne(true);
            }
        }

        return recommendResult;
    }

    @Override
    @CounterIface
    public RecommendResult recommend(RecommmendParam param) throws BIZException {

        logger.info("CandidateEntiry recommend param:{}", param);
        /** 参数校验 */
        ValidateUtil vu = ParamCheckTool.checkRecommend(param);
        String message = vu.validate();
        if (!StringUtils.isNullOrEmpty(message)) {
            throw CandidateExceptionFactory.buildCheckFailedException(message);
        }

        /** 是否开启被动求职者 */
<<<<<<< HEAD
        boolean passiveSeeker = candidateDBDao.isStartPassiveSeeker(param.getCompanyId());
        if (!passiveSeeker) {
=======
        boolean passiveSeeker = CandidateDBDao.isStartPassiveSeeker(param.getCompanyId());
        if(!passiveSeeker) {
            logger.info("CandidateEntiry recommend 未开启挖掘被动求职者");
>>>>>>> master
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_NOT_START);
        }
        //修改参数长度
        refineParam(param);

<<<<<<< HEAD
        CandidateRecomRecordDO candidateRecomRecordDO = candidateDBDao.getCandidateRecomRecordDO(param.getId());
=======
        CandidateRecomRecordDO candidateRecomRecordDO = CandidateDBDao.getCandidateRecomRecordDO(param.getId());
        logger.info("CandidateEntiry recommend candidateRecomRecordDO:{}", candidateRecomRecordDO);
>>>>>>> master
        if (candidateRecomRecordDO == null) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_CANDIDATES_RECORD_NOT_EXIST);
        }

        if (candidateRecomRecordDO.getIsRecom() == 0) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_ALREADY_APPLIED_OR_RECOMMEND);
        }

        try {
            //添加推荐信息
            updateRecomRecord(candidateRecomRecordDO, param);
            //修改候选人推荐标记
            updateCandidateCompany(candidateRecomRecordDO);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
            throw CandidateExceptionFactory.buildException(Category.PROGRAM_POST_FAILED);
        }

        /** 添加员工积分 */
        if (candidateRecomRecordDO.getPostUserId() > 0) {
            updateEmployeePoint(candidateRecomRecordDO.getPostUserId(), param.getCompanyId(),
                    candidateRecomRecordDO.getAppId(), candidateRecomRecordDO.getPositionId());
        }

        return assembleRecommendResult(param.getId(), param.getPostUserId(), param.getClickTime(), param.getCompanyId());
    }

    /**
     * 查询职位转发浏览记录
     *
     * @param id         记录编号
     * @param postUserId 转发者编号
     * @return 查询结果
     * @throws BIZException 业务异常
     */
    @Override
    @CounterIface
    public RecomRecordResult getRecommendation(int id, int postUserId) throws BIZException {
        ValidateUtil vu = ParamCheckTool.checkGetRecommendation(id, postUserId);
        String message = vu.validate();
        if (!StringUtils.isNullOrEmpty(message)) {
            throw CandidateExceptionFactory.buildCheckFailedException(message);
        }
        CandidateRecomRecordDO candidateRecomRecordDO = candidateDBDao.getCandidateRecomRecordDO(id);
        if (candidateRecomRecordDO == null || candidateRecomRecordDO.getPostUserId() != postUserId) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_CANDIDATES_RECORD_NOT_EXIST);
        }

        Future positionFuture = findPositionFutureById(candidateRecomRecordDO.getPositionId());
        Future userFuture = findUserFutureById(candidateRecomRecordDO.getPresenteeUserId());
        RecomRecordResult recomRecordResult = assembleRecomRecordResult(candidateRecomRecordDO, positionFuture, userFuture);

        return recomRecordResult;
    }

    @Override
    @CounterIface
    public SortResult getRecommendatorySorting(int postUserId, int companyId) throws BIZException {
        logger.info("CandidateEntity getRecommendatorySorting postUserId:{}, companyId:{}", postUserId, companyId);
        /** 参数校验 */
        ValidateUtil vu = ParamCheckTool.checkRecommendatorySorting(postUserId, companyId);
        String message = vu.validate();
        if (!StringUtils.isNullOrEmpty(message)) {
            throw CandidateExceptionFactory.buildCheckFailedException(message);
        }
        /** 是否开启被动求职者 */
        boolean passiveSeeker = candidateDBDao.isStartPassiveSeeker(companyId);
        logger.info("CandidateEntity getRecommendatorySorting passiveSeeker:{}", passiveSeeker);
        if(!passiveSeeker) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_NOT_START);
        }

        /** 查找员工信息 */
        List<UserEmployeeDO> employeeDOList = candidateDBDao.listUserEmployee(companyId);
        logger.info("CandidateEntity getRecommendatorySorting employeeDOList:{}", employeeDOList);
        if(employeeDOList == null || employeeDOList.size() == 0) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_SORT_COLLEAGUE_NOT_EXIST);
        }
        List<Integer> employeeIdList = employeeDOList.stream().map(userEmployeeDO -> userEmployeeDO.getSysuserId()).collect(Collectors.toList());
        /** 查找排名 */
        List<CandidateRecomRecordSortingDO> sortingDOList = candidateDBDao.listSorting(employeeIdList);
        if (sortingDOList == null || sortingDOList.size() == 0) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_SORT_USER_NOT_EXIST);
        }
        logger.info("CandidateEntity getRecommendatorySorting sortingDOList:{}", sortingDOList);
        return assembleSortingResult(sortingDOList, postUserId);
    }

    @Override
    @CounterIface
    public RecommendResult ignore(int id, int companyId, int postUserId, String clickTime) throws BIZException {
        logger.info("CandidateEntity ignore id:{}, companyId:{}, postUserId:{}, clickTime:{}", id, companyId, postUserId, clickTime);
        /** 参数校验 */
        ValidateUtil vu = ParamCheckTool.checkignore(id, companyId, postUserId, clickTime);
        String message = vu.validate();
        if (!StringUtils.isNullOrEmpty(message)) {
            throw CandidateExceptionFactory.buildCheckFailedException(message);
        }

        /** 是否开启被动求职者 */
<<<<<<< HEAD
        boolean passiveSeeker = candidateDBDao.isStartPassiveSeeker(companyId);
        if (!passiveSeeker) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_NOT_START);
        }

        CandidateRecomRecordDO recomRecordDO = candidateDBDao.getCandidateRecomRecordDO(id);
        if (recomRecordDO == null) {
=======
        boolean passiveSeeker = CandidateDBDao.isStartPassiveSeeker(companyId);
        if(!passiveSeeker) {
            logger.info("CandidateEntiry ignore 未开启挖掘被动求职者");
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_NOT_START);
        }

        CandidateRecomRecordDO recomRecordDO = CandidateDBDao.getCandidateRecomRecordDO(id);
        logger.info("CandidateEntiry recommend recomRecordDO:{}", recomRecordDO);
        if(recomRecordDO == null) {
>>>>>>> master
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_CANDIDATES_RECORD_NOT_EXIST);
        }
        if (recomRecordDO.getIsRecom() == 0) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_ALREADY_APPLIED_OR_RECOMMEND);
        }
        recomRecordDO.setIsRecom((byte) RecomType.IGNORE.getValue());
        try {
            candidateDBDao.updateCandidateRecomRecord(recomRecordDO);
            
            List<CandidateRecomRecordDO> candidateRecomRecordList = candidateDBDao.listFiltredCandidateRecomRecord(recomRecordDO);
            if (candidateRecomRecordList != null && candidateRecomRecordList.size() > 0) {
            	candidateRecomRecordList.forEach(candidate -> {
            		candidate.setIsRecom((byte)RecomType.IGNORE.getValue());
            	});
                candidateDBDao.updateCandidateRecomRecords(candidateRecomRecordList);
            }
        } catch (TException e) {
            logger.error(e.getMessage(), e);
        }

        return assembleRecommendResult(id, postUserId, clickTime, companyId);
    }

    /**
     * 组装排序结果
     *
     * @param sortingDOList 推荐排序数组，按照推荐从多到少排序
     * @param postUserId    推荐人编号  @return 排序结果
     * @return 排序结果
     */
    private SortResult assembleSortingResult(List<CandidateRecomRecordSortingDO> sortingDOList, int postUserId) {
        SortResult sortResult = new SortResult();
        int rank = 0;
        for (CandidateRecomRecordSortingDO candidateRecomRecordSortingDO : sortingDOList) {
            rank++;
            if (candidateRecomRecordSortingDO.getPostUserId() == postUserId) {
                sortResult.setCount(candidateRecomRecordSortingDO.getCount());
                break;
            }
        }
        sortResult.setRank(rank);
        sortResult.setHongbao(Constant.BONUS*sortResult.getCount());
        logger.info("CandidateEntity getRecommendatorySorting assembleSortingResult:{}", sortResult);
        return sortResult;
    }

    /**
     * 组装推荐或者忽略的操作结果
     *
     * @param id         职位转发浏览记录编号
     * @param postUserId 转发者编号
     * @param clickTime  点击时间
     * @param companyId  公司编号
     * @return 操作结果
     */
    private RecommendResult assembleRecommendResult(int id, int postUserId, String clickTime, int companyId) {
        RecommendResult recommendResult = new RecommendResult();
        recommendResult.setId(id);

        List<Integer> exceptNotRecommend = new ArrayList<Integer>() {{
            add(0);
            add(2);
            add(3);
        }};

        List<Integer> recommended = new ArrayList<Integer>() {{
            add(0);
        }};

        List<Integer> ignore = new ArrayList<Integer>() {{
            add(2);
        }};

        List<Integer> selected = new ArrayList<Integer>() {{
            add(3);
        }};

        List<CandidateRecomRecordDO> candidateRecomRecordDOList =
                candidateDBDao.listCandidateRecomRecordDOExceptId(id, postUserId,
                        clickTime, selected);
        if (candidateRecomRecordDOList != null && candidateRecomRecordDOList.size() > 0) {
            CandidateRecomRecordDO candidateRecomRecordDO = candidateRecomRecordDOList.get(0);
            Future positionFuture = findPositionFutureById(candidateRecomRecordDO.getPositionId());
            Future userFuture = findUserFutureById(candidateRecomRecordDO.getPresenteeUserId());
            recommendResult.setPresenteeName(refineUserName(userFuture));
            recommendResult.setPositionName(refinePositionName(positionFuture));
        }



        try {
            int exceptNotRecommendedCount = candidateDBDao.countRecommendation(postUserId,
                    clickTime, exceptNotRecommend);
            recommendResult.setRecomTotal(exceptNotRecommendedCount);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
        }
        try {
            int recommendedCount = candidateDBDao.countRecommendation(postUserId, clickTime, recommended);
            recommendResult.setRecomIndex(recommendedCount);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
        }
        try {
            int ignoreCount = candidateDBDao.countRecommendation(postUserId, clickTime, ignore);
            recommendResult.setRecomIgnore(ignoreCount);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
        }
        if (candidateRecomRecordDOList.size() > 1) {
            recommendResult.setNextOne(true);
        } else {
            recommendResult.setNextOne(false);
        }
        logger.info("CandidateEntiry recommend recommendResult:{}", recommendResult);
        return recommendResult;
    }

    /**
     * 组装查询职位转发浏览记录的消息
     *
     * @param candidateRecomRecordDO 职位转发浏览记录
     * @param positionFuture         职位信息
     * @param userFuture             用户信息
     * @return 查询结果
     */
    private RecomRecordResult assembleRecomRecordResult(CandidateRecomRecordDO candidateRecomRecordDO,
                                                        Future positionFuture, Future userFuture) {
        RecomRecordResult recomRecordResult = new RecomRecordResult();
        recomRecordResult.setId(candidateRecomRecordDO.getId());
        recomRecordResult.setClickTime(candidateRecomRecordDO.getClickTime());
        recomRecordResult.setPresenteeName(refineUserName(userFuture));
        recomRecordResult.setTitle(refinePositionName(positionFuture));
        recomRecordResult.setRecom(candidateRecomRecordDO.getIsRecom());
        return recomRecordResult;
    }

    /**
     * 添加员工积分（完善被推荐人信息）
     *
     * @param employeeId 员工编号
     * @param compnayId  公司编号
     * @param appId      申请编号
     * @param positionId 职位编号
     * @return 员工积分
     */
    private int updateEmployeePoint(int employeeId, int compnayId, int appId, int positionId) {
        int point = 0;
        try {
            UserEmployeeDO employeeDO =
                    candidateDBDao.getEmployee(employeeId, compnayId);
            if (employeeDO != null) {
                HrPointsConfDO hrPointsConfDO = candidateDBDao.getHrPointConf(compnayId, RecruitmentScheduleEnum.IMPROVE_CANDIDATE);
                if (hrPointsConfDO != null && hrPointsConfDO.getReward() > 0) {
                    UserEmployeePointsRecordDO userEmployeePointsRecordDO = new UserEmployeePointsRecordDO();
                    userEmployeePointsRecordDO.setEmployee_id(employeeDO.getId());
                    userEmployeePointsRecordDO.setReason(hrPointsConfDO.getStatusName());
                    userEmployeePointsRecordDO.setAward((int) hrPointsConfDO.getReward());
                    userEmployeePointsRecordDO.setApplication_id(appId);
                    userEmployeePointsRecordDO.setPosition_id(positionId);
                    candidateDBDao.saveEmployeePointsRecord(userEmployeePointsRecordDO);

                    point = candidateDBDao.updateEmployeePoint(employeeDO.getId());
                }
            }
        } catch (TException e) {
            logger.error(e.getMessage(), e);
        }
        return point;
    }

    /**
     * 将候选人置为推荐状态
     */
    private void updateCandidateCompany(CandidateRecomRecordDO candidateRecomRecordDO) throws TException {
        //修改候选人账号
        Optional<CandidatePositionDO> candidatePositionDOOptional =
                candidateDBDao.getCandidatePosition(candidateRecomRecordDO.getPositionId(),
                        candidateRecomRecordDO.getPresenteeUserId());
        if (candidatePositionDOOptional.isPresent()) {
            CandidateCompanyDO candidateCompanyDO = new CandidateCompanyDO();
            candidateCompanyDO.setIsRecommend((byte) 1);
            candidateCompanyDO.setId(candidatePositionDOOptional.get().getCandidateCompanyId());
            candidateDBDao.updateCandidateCompany(candidateCompanyDO);
        }
    }

    /**
     * 添加推荐信息
     *
     * @param candidateRecomRecordDO 职位转发浏览记录
     * @param param                  推荐数据
     * @throws TException 业务异常
     */
    private void updateRecomRecord(CandidateRecomRecordDO candidateRecomRecordDO, RecommmendParam param) throws TException {
        DateTime now = new DateTime();
        String nowStr = now.toString("yyyy-MM-dd HH:mm:ss");
        candidateRecomRecordDO.setRecomReason(param.getRecomReason());
        candidateRecomRecordDO.setPosition(param.getPosition());
        candidateRecomRecordDO.setCompany(param.getCompany());
        candidateRecomRecordDO.setRealname(param.getRealName());
        candidateRecomRecordDO.setMobile(param.getMobile());
        candidateRecomRecordDO.setRecomTime(nowStr);
        candidateRecomRecordDO.setIsRecom((byte)0);
        candidateDBDao.updateCandidateRecomRecord(candidateRecomRecordDO);
        
        List<CandidateRecomRecordDO> candidateRecomRecordList = candidateDBDao.listFiltredCandidateRecomRecord(candidateRecomRecordDO);
        if (candidateRecomRecordList != null && candidateRecomRecordList.size() > 0) {
        	candidateRecomRecordList.forEach(candidate -> {
        		candidate.setRecomReason(param.getRecomReason());
        		candidate.setPosition(param.getPosition());
        		candidate.setCompany(param.getCompany());
        		candidate.setRealname(param.getRealName());
        		candidate.setMobile(param.getMobile());
        		candidate.setRecomTime(nowStr);
        		candidate.setIsRecom((byte)0);
        	});
            candidateDBDao.updateCandidateRecomRecords(candidateRecomRecordList);
        }
    }

    /**
     * 修改参数长度
     *
     * @param param 推荐接口参数
     */
    private void refineParam(RecommmendParam param) {
        if (param.getRealName().length() > 100) {
            param.setRealName(param.getRealName().substring(0, 100));
        }
        if (param.getCompany().length() > 200) {
            param.setCompany(param.getCompany().substring(0, 200));
        }
        if (param.getPosition().length() > 200) {
            param.setPosition(param.getPosition().substring(0, 200));
        }
        if (param.getRecomReason().length() > 500) {
            param.setRecomReason(param.getRecomReason().substring(0, 500));
        }
    }


    /**
     * 组装查询结果
     *
     * @param recommendResult        查询结果
     * @param candidateRecomRecordDO 职位转发浏览记录
     * @param positionListFuture     职位信息
     * @param presenteeListFuture    浏览者信息
     * @return 查询结果
     */
    private RecommendResult assembleResult(RecommendResult recommendResult, CandidateRecomRecordDO candidateRecomRecordDO,
                                           Future positionListFuture, Future presenteeListFuture) {
        recommendResult.setId(candidateRecomRecordDO.getId());
        String date = candidateRecomRecordDO.getClickTime();
        if (date != null && date.length() > 10) {
            date = date.trim().substring(0,10);
        }
        recommendResult.setClickTime(date);
        try {
            JobPositionDO positionDO = (JobPositionDO) positionListFuture.get();
            if (positionDO != null) {
                recommendResult.setPositionName(positionDO.getTitle());
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e);
        }
        recommendResult.setPositionName(refinePositionName(positionListFuture));
        recommendResult.setPresenteeName(refineUserName(presenteeListFuture));
        return recommendResult;
    }

    /**
     * 查找职位标题
     */
    private String refinePositionName(Future positionFuture) {
        String title = null;
        try {
            JobPositionDO positionDO = (JobPositionDO) positionFuture.get();
            if (positionDO != null) {
                title = positionDO.getTitle();
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e);
        }
        return title;
    }

    /**
     * 查找用户名称
     */
    private String refineUserName(Future userFuture) {
        String name = null;
        try {
            UserUserDO userUserDO = (UserUserDO) userFuture.get();
            if (userUserDO != null) {
                name = StringUtils.isNotNullOrEmpty(userUserDO.getName()) ? userUserDO.getName() : userUserDO.getNickname();
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e);
        }
        return name;
    }

    /**
     * 根据编号查找用户信息
     *
     * @param presenteeUserId 浏览者编号
     * @return 浏览者信息
     */
    private Future findUserFutureById(int presenteeUserId) {
        Future future = tp.startTast(() -> candidateDBDao.getUserByID(presenteeUserId));
        return future;
    }

    /**
     * 根据职位编号查找职位信息
     *
     * @param positionId 职位编号
     * @return 职位信息
     */
    private Future findPositionFutureById(int positionId) {
        Future future = tp.startTast(() -> {
            return candidateDBDao.getPositionByID(positionId);
        });
        return future;
    }

    private Future findCandidatePositionFuture(List<CandidateRecomRecordDO> candidateRecomRecordDOList) {
        Future candidatePositionFuture = tp.startTast(() -> {
            List<Map<Integer, Integer>> param = candidateRecomRecordDOList.stream()
                    .filter(candidateRecomRecordDO -> candidateRecomRecordDO.getPresenteeUserId() > 0
                            && candidateRecomRecordDO.getPositionId() > 0)
                    .flatMap(candidateRecomRecordDO -> {
                        Map<Integer, Integer> map = new HashMap<>();
                        map.put(candidateRecomRecordDO.getPositionId(), candidateRecomRecordDO.getPresenteeUserId());
                        return Stream.of(map);
                    })
                    .collect(Collectors.toList());

            return candidateDBDao.listCandidatePositionByUserIdPositionId(param);
        });
        return candidatePositionFuture;
    }

    /**
     * 查找转发者信息
     *
     * @param candidateRecomRecordDOList 职位转发浏览记录
     * @return 转发者信息
     */
    private Future findRepostFuture(List<CandidateRecomRecordDO> candidateRecomRecordDOList) {
        Future repostFuture = tp.startTast(() -> {
            List<Integer> repostIdList = candidateRecomRecordDOList.stream()
                    .map(candidateRecomRecordDO -> candidateRecomRecordDO.getRepostUserId())
                    .collect(Collectors.toList());
            return candidateDBDao.getUserByIDList(repostIdList);
        });
        return repostFuture;
    }

    /**
     * 查找职位浏览者信息
     *
     * @param candidateRecomRecordDOList 职位转发浏览记录
     * @return 浏览者信息
     */
    private Future findPresenteeList(List<CandidateRecomRecordDO> candidateRecomRecordDOList) {
        Future presenteeFuture = tp.startTast(() -> {
            List<Integer> presenteeIdList = candidateRecomRecordDOList.stream()
                    .map(candidateRecomRecordDO -> candidateRecomRecordDO.getPresenteeUserId())
                    .collect(Collectors.toList());
            return candidateDBDao.getUserByIDList(presenteeIdList);
        });
        return presenteeFuture;
    }

    /**
     * 查找职位
     *
     * @param candidateRecomRecordDOList 职位转发浏览记录
     * @return 职位信息
     */
    private Future findPositionList(List<CandidateRecomRecordDO> candidateRecomRecordDOList) {
        Future positionFuture = tp.startTast(() -> {
            List<Integer> positionIdList = candidateRecomRecordDOList.stream()
                    .map(candidateRecomRecordDO -> candidateRecomRecordDO.getPositionId())
                    .collect(Collectors.toList());
            return candidateDBDao.getPositionByIdList(positionIdList);
        });
        return positionFuture;
    }

    /**
     * 生成职位候选人信息
     *
     * @param positionDO                 职位信息
     * @param candidateRecomRecordDOList 职位转发浏览记录
     * @param presenteeUserList          被动求职者
     * @param repostUserList             推荐人信息
     * @return 职位候选人信息
     */
    private CandidateList addPositionCandidate(JobPositionDO positionDO,
                                               List<CandidateRecomRecordDO> candidateRecomRecordDOList,
                                               List<UserUserDO> presenteeUserList, List<UserUserDO> repostUserList,
                                               List<CandidatePositionDO> candidatePositionDOList) {
        CandidateList postionCandidate = new CandidateList();
        postionCandidate.setPositionId(positionDO.getId());
        postionCandidate.setPositionName(positionDO.getTitle());

        List<CandidateRecomRecordDO> candidateRecomRecordDOPositionList = candidateRecomRecordDOList
                .stream()
                .filter(candidateRecomRecordDO -> candidateRecomRecordDO.getPositionId() == positionDO.getId())
                .collect(Collectors.toList());

        List<com.moseeker.thrift.gen.candidate.struct.Candidate> candidates
                = addCandidate(candidateRecomRecordDOPositionList, presenteeUserList, repostUserList,
                candidatePositionDOList);

        postionCandidate.setCandidates(candidates);
        return postionCandidate;
    }

    /**
     * 生成职位下的候选人信息
     *
     * @param candidateRecomRecordDOPositionList 职位转发浏览记录
     * @param presenteeUserList                  被动求职者
     * @param repostUserList                     推荐人信息
     * @return 职位相关的被动求职者信息
     */
    private List<com.moseeker.thrift.gen.candidate.struct.Candidate> addCandidate(
            List<CandidateRecomRecordDO> candidateRecomRecordDOPositionList, List<UserUserDO> presenteeUserList,
            List<UserUserDO> repostUserList, List<CandidatePositionDO> candidatePositionDOList) {

        List<com.moseeker.thrift.gen.candidate.struct.Candidate> candidates = new ArrayList<>();

        for (CandidateRecomRecordDO candidateRecomRecordDO : candidateRecomRecordDOPositionList) {

            com.moseeker.thrift.gen.candidate.struct.Candidate candidate = new com.moseeker.thrift.gen.candidate.struct.Candidate();
            candidate.setIsRecom(candidateRecomRecordDO.getIsRecom());
            candidate.setId(candidateRecomRecordDO.getId());

            setPresentee(candidate, presenteeUserList, candidateRecomRecordDO);

            setRepost(candidate, repostUserList, candidateRecomRecordDO);

            setCandidatePosition(candidate, candidatePositionDOList, candidateRecomRecordDO);

            candidates.add(candidate);
        }
        return candidates;
    }

    /**
     * 设置浏览次数与是否感兴趣
     *
     * @param candidate               被动求职者和推荐者信息
     * @param candidatePositionDOList 候选人浏览职位数据
     * @param candidateRecomRecordDO  职位转发浏览记录
     */
    private void setCandidatePosition(com.moseeker.thrift.gen.candidate.struct.Candidate candidate,
                                      List<CandidatePositionDO> candidatePositionDOList,
                                      CandidateRecomRecordDO candidateRecomRecordDO) {
        if (candidatePositionDOList != null && candidatePositionDOList.size() > 0) {
            Optional<CandidatePositionDO> candidatePositionDOOptional = candidatePositionDOList.stream()
                    .filter(candidatePositionDO -> candidatePositionDO.getPositionId()
                            == candidateRecomRecordDO.getPositionId()
                            && candidatePositionDO.getUserId() == candidateRecomRecordDO.getPresenteeUserId())
                    .findAny();
            if (candidatePositionDOOptional.isPresent()) {
                candidate.setViewNumber(candidatePositionDOOptional.get().getViewNumber());
                candidate.setInsterested(BooleanUtils.toBooleanObject(candidatePositionDOOptional.get().getIsInterested()));
            }
        }
    }

    /**
     * 封装推荐人信息
     *
     * @param candidate              被动求职者和推荐者信息
     * @param repostUserList         推荐人列表
     * @param candidateRecomRecordDO 职位转发浏览记录
     */
    private void setRepost(com.moseeker.thrift.gen.candidate.struct.Candidate candidate,
                           List<UserUserDO> repostUserList, CandidateRecomRecordDO candidateRecomRecordDO) {
        if (repostUserList != null && repostUserList.size() > 0) {
            Optional<UserUserDO> userUserDOOptional = repostUserList.stream()
                    .filter(userUserDO -> userUserDO.getId() == candidateRecomRecordDO.getRepostUserId())
                    .findAny();
            if (userUserDOOptional.isPresent()) {
                candidate.setPresenteeFriendId(userUserDOOptional.get().getId());
                String name = StringUtils.isNotNullOrEmpty(userUserDOOptional.get().getName())
                        ? userUserDOOptional.get().getName() : userUserDOOptional.get().getNickname();
                candidate.setPresenteeFriendName(name);
            }
        }
    }

    /**
     * 封装求职者信息
     *
     * @param candidate              被动求职者和推荐者信息
     * @param presenteeUserList      被动求职者列表
     * @param candidateRecomRecordDO 职位转发浏览记录
     */
    private void setPresentee(com.moseeker.thrift.gen.candidate.struct.Candidate candidate,
                              List<UserUserDO> presenteeUserList, CandidateRecomRecordDO candidateRecomRecordDO) {
        if (presenteeUserList != null && presenteeUserList.size() > 0) {
            Optional<UserUserDO> userUserDOOptional = presenteeUserList
                    .stream()
                    .filter(presentee -> presentee.getId() == candidateRecomRecordDO.getPresenteeUserId()).findAny();
            if (userUserDOOptional.isPresent()) {
                candidate.setPresenteeUserId(userUserDOOptional.get().getId());
                String name = StringUtils.isNotNullOrEmpty(userUserDOOptional.get().getName())
                        ? userUserDOOptional.get().getName() : userUserDOOptional.get().getNickname();
                candidate.setPresenteeName(name);
                candidate.setPresenteeLogo(userUserDOOptional.get().getHeadimg());
            }
        }
    }

    private <T> T convertFuture(Future future) {
        try {
            return (T) future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
