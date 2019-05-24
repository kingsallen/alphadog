package com.moseeker.candidate.service.entities;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.config.HRAccountType;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.profiledb.ProfileCompletenessDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.candidatedb.tables.*;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateCompanyRecord;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidatePositionRecord;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateRemarkRecord;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateShareChainRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrGroupCompanyRel;
import com.moseeker.baseorm.db.hrdb.tables.records.HrGroupCompanyRelRecord;
import com.moseeker.baseorm.db.jobdb.tables.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.candidate.constant.EmployeeType;
import com.moseeker.candidate.constant.RecomType;
import com.moseeker.candidate.service.Candidate;
import com.moseeker.candidate.service.checkout.ParamCheckTool;
import com.moseeker.candidate.service.dao.CandidateDBDao;
import com.moseeker.candidate.service.exception.CandidateCategory;
import com.moseeker.candidate.service.exception.CandidateException;
import com.moseeker.candidate.service.exception.CandidateExceptionFactory;
import com.moseeker.common.annotation.iface.CounterIface;

import static com.moseeker.common.biztools.RecruitmentScheduleEnum.IMPROVE_CANDIDATE;

import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.Category;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.entity.Constant.GenderType;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.thrift.gen.candidate.struct.*;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.CandidateRecomRecordSortingDO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.*;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.BooleanUtils;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.jooq.impl.DefaultDSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
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

    @Autowired
    private EmployeeEntity employeeEntity;

    @Autowired
    private JobPositionDao positionDao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileEntity profileEntity;

    @Autowired
    private ProfileCompletenessDao completenessDao;

    @Autowired
    private UserEmployeeDao employeeDao;

    @Autowired
    private UserHrAccountDao hrAccountDao;

    @Autowired
    private JobPositionCityDao positionCityDao;

    @Autowired
    private HrWxWechatDao wechatDao;

    @Autowired
    UserWxUserDao wxUserDao;

    @Autowired
    private AmqpTemplate amqpTemplate;

    private static final String REFINE_CANDIDATE_EXCHANGE = "refine_candidate_exchange";
    private static final String REFINE_CANDIDATE_ROUTING_KEY = "refine_candidate_exchange.redpacket";

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

                /* 1. 如果浏览者是员工，则不生成候选人数据;2. 以后需要加上没有把该公司下的所有职位都投递以便才算候选人；*/
                if (employeeEntity.isEmployee(userID, jobPositionDO.getCompanyId())) {
                    return;
                }

                if (userUserDO != null && userUserDO.getId() > 0 && jobPositionDO != null && jobPositionDO.getId() > 0) {
                    logger.info("CandidateEntity glancePosition userUserDO:{}, jobPositionDO:{}", userUserDO, jobPositionDO);
                    boolean fromEmployee = false;       //是否是员工转发
                    if (shareChainDO != null) {
                        if (employeeEntity.isEmployee(shareChainDO.getRootRecomUserId(), jobPositionDO.getCompanyId())) {
                            fromEmployee = true;
                            logger.info("CandidateEntity glancePosition 是员工转发");
                        }
                    }
                    //暂时不考虑HR的问题
                    List<CandidateRemarkDO> crs = candidateDBDao.getCandidateRemarks(userID, jobPositionDO.getCompanyId());
                    if (crs != null && crs.size() > 0) {
                        crs.forEach(candidateRemark -> candidateRemark.setStatus((byte) 1));
                        logger.info("CandidateEntity glancePosition crs crs");
                    }
                    candidateDBDao.updateCandidateRemarks(crs);
                    String date = new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS");
                    Optional<CandidatePositionDO> cp = candidateDBDao.getCandidatePosition(positionID, userID);

                    if (cp.isPresent()) {
                        cp.get().setViewNumber(cp.get().getViewNumber() + 1);
                        cp.get().setSharedFromEmployee(fromEmployee ? (byte) 1 : 0);
                        candidateDBDao.updateCandidatePosition(cp.get());
                    } else {
                        logger.info("CandidateEntity glancePosition campany_position not exist!");
                        Optional<CandidateCompanyDO> candidateCompanyDOOptional = candidateDBDao.getCandidateCompanyByUserIDCompanyID(userID, jobPositionDO.getCompanyId());
                        CandidateCompanyDO candidateCompanyDO = null;
                        if (!candidateCompanyDOOptional.isPresent()) {
                            candidateCompanyDO = new CandidateCompanyDO();
                            candidateCompanyDO.setCompanyId(jobPositionDO.getCompanyId());
                            candidateCompanyDO.setNickname(userUserDO.getNickname());
                            candidateCompanyDO.setHeadimgurl(userUserDO.getHeadimg());
                            candidateCompanyDO.setSysUserId(userID);
                            candidateCompanyDO.setMobile(String.valueOf(userUserDO.getMobile()));
                            candidateCompanyDO.setEmail(userUserDO.getEmail());
                            candidateCompanyDO.setUpdateTime(date);
                            candidateCompanyDO = candidateDBDao.saveCandidateCompany(candidateCompanyDO);
                            logger.info("CandidateEntity glancePosition save candidateCompanyDO:{}", candidateCompanyDO);
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
                        logger.info("CandidateEntity glancePosition candidatePositionDO:{}", candidatePositionDO);
                        candidateDBDao.saveCandidatePosition(candidatePositionDO);
                    }
                }
            } catch (TException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

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
    public List<CandidateList> candidateList(CandidateListParam param) throws CommonException {
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

        /** 查找公司下的职位 */
        List<Integer> companyIdList = employeeEntity.getCompanyIds(param.getCompanyId());
        List<Integer> positionIdList = positionDao.getPositionIds(companyIdList);
        if (positionIdList == null && positionIdList.size() == 0) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_CANDIDATES_POSITION_NOT_EXIST);
        }

        /** 查找职位转发浏览记录 */
//        List<CandidateRecomRecordDO> candidateRecomRecordDOList =
//                candidateDBDao.listCandidateRecomRecordDONew(param.getPostUserId(), param.getClickTime(),
//                        param.getRecoms(), positionIdList);

        List<CandidateRecomRecordDO> candidateRecomRecordDOList =
                candidateDBDao.listCandidateRecomRecordDO(param.getPostUserId(), param.getClickTime(),
                        param.getRecoms(), positionIdList);
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
                logger.info("CandidateEntity candidateList jobPositionDOList:{}", jobPositionDOList);
                if (jobPositionDOList != null && jobPositionDOList.size() > 0) {

                    List<UserUserDO> presenteeUserList = convertFuture(presenteeFuture);    //候选人
                    List<UserUserDO> repostUserList = convertFuture(repostFuture);             //推荐者
                    List<CandidatePositionDO> candidatePositionDOList = convertFuture(candidatePositionFuture); //候选人查看职位记录

                    for (JobPositionDO positionDO : jobPositionDOList) {
                        CandidateList postionCandidate = addPositionCandidate(positionDO, candidateRecomRecordDOList,
                                presenteeUserList, repostUserList, candidatePositionDOList);
                        logger.info("CandidateEntity candidateList postionCandidate:{}", postionCandidate);
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
        logger.info("CandidateEntity candidateList result:{}", result);
        return result;
    }

    @Override
    @CounterIface
    public RecommendResult getRecommendations(int companyId, List<Integer> idList) throws
            CommonException {

        /** 参数校验 */
        ValidateUtil vu = ParamCheckTool.checkRecommends(companyId, idList);
        String message = vu.validate();
        if (!StringUtils.isNullOrEmpty(message)) {
            throw CandidateExceptionFactory.buildCheckFailedException(message);
        }

        /** 是否开启被动求职者 */
        /*boolean passiveSeeker = candidateDBDao.isStartPassiveSeeker(companyId);
        if (!passiveSeeker) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_NOT_START);
        }*/

        RecommendResult recommendResult = new RecommendResult();
        recommendResult.setRecomTotal(idList.size());
        recommendResult.setRecomIndex(0);
        recommendResult.setRecomIgnore(0);
        recommendResult.setNextOne(false);

        /** 查找公司下的职位 */
        List<Integer> companyIdList = employeeEntity.getCompanyIds(companyId);
        List<Integer> positionIdList = positionDao.getPositionIds(companyIdList);
        if (positionIdList == null && positionIdList.size() == 0) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_CANDIDATES_POSITION_NOT_EXIST);
        }

        /** 查找职位转发浏览记录 */
        List<CandidateRecomRecordDO> candidateRecomRecordDOList = candidateDBDao.getCandidateRecomRecordDOByIdList(idList, positionIdList);
        if (candidateRecomRecordDOList != null && candidateRecomRecordDOList.size() > 0) {
            candidateRecomRecordDOList.forEach(candidateRecomRecordDO -> {
                candidateRecomRecordDO.setIsRecom((byte) RecomType.SELECTED.getValue());
            });
            candidateDBDao.updateCandidateRecomRecords(candidateRecomRecordDOList);
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
    public RecommendResult recommend(RecommmendParam param) throws CommonException {

        logger.info("CandidateEntiry recommend param:{}", param);
        /** 参数校验 */
        ValidateUtil vu = ParamCheckTool.checkRecommend(param);
        String message = vu.validate();
        if (!StringUtils.isNullOrEmpty(message)) {
            throw CandidateExceptionFactory.buildCheckFailedException(message);
        }

        GenderType genderType = GenderType.instanceFromValue(param.getGender());
        if (genderType == null) {
            genderType = GenderType.Secret;
        }

        /** 是否开启被动求职者 */
        /*boolean passiveSeeker = candidateDBDao.isStartPassiveSeeker(param.getCompanyId());
        if (!passiveSeeker) {
            logger.info("CandidateEntiry recommend 未开启挖掘被动求职者");
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_NOT_START);
        }*/

        //修改参数长度
        refineParam(param);
        CandidateRecomRecordDO candidateRecomRecordDO = candidateDBDao.getCandidateRecomRecordDO(param.getId(), param.getPostUserId());
        logger.info("CandidateEntiry recommend candidateRecomRecordDO:{}", candidateRecomRecordDO);
        if (candidateRecomRecordDO == null) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_CANDIDATES_RECORD_NOT_EXIST);
        }

        if (candidateRecomRecordDO.getIsRecom() == 0) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_ALREADY_APPLIED_OR_RECOMMEND);
        }

        try {
            //添加推荐信息
            updateRecomRecord(candidateRecomRecordDO, param, genderType);
            //修改候选人推荐标记
            updateCandidateCompany(candidateRecomRecordDO);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
            throw CandidateExceptionFactory.buildException(Category.PROGRAM_POST_FAILED);
        }

        /** 添加员工积分 */
        Query query = new Query.QueryBuilder().where("sysuser_id", candidateRecomRecordDO.getPostUserId())
                .and(new Condition("company_id", employeeEntity.getCompanyIds(param.getCompanyId()), ValueOp.IN))
                .and("disable", Constant.ENABLE_OLD).and("activation", EmployeeType.AUTH_SUCCESS.getValue()).buildQuery();
        UserEmployeeDO employeeDO = employeeDao.getData(query);
        if (candidateRecomRecordDO.getPostUserId() > 0) {
            try {
                if (employeeDO != null) {
                    employeeEntity.addReward(employeeDO.getId(), param.getCompanyId(), "", candidateRecomRecordDO.getAppId(), candidateRecomRecordDO.getPositionId(), IMPROVE_CANDIDATE.getId(), candidateRecomRecordDO.getPresenteeUserId());
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        if (employeeDO != null) {
            publishRecommendEvent(employeeDO.getId(), param.getPostUserId(),
                    candidateRecomRecordDO.getPresenteeUserId(), param.getCompanyId(),
                    candidateRecomRecordDO.getPositionId());
        }

        return assembleRecommendResult(param.getId(), param.getPostUserId(), param.getClickTime(), param.getCompanyId());
    }

    private void publishRecommendEvent(int employeeId, int userId, int candidateId, int companyId, int positionId) {
        JSONObject eventMessage = new JSONObject();
        eventMessage.put("name", "refine candidate");
        eventMessage.put("ID", UUID.randomUUID().toString());
        eventMessage.put("employee_id", employeeId);
        eventMessage.put("user_id", userId);
        eventMessage.put("candidate_id", candidateId);
        eventMessage.put("company_id", companyId);
        eventMessage.put("refine_time", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        eventMessage.put("position_id", positionId);
        logger.info("EmployeeEntity publishInitalScreenHbEvent param:{}, exchange:{}, routing:{}",
                eventMessage.toJSONString(), REFINE_CANDIDATE_EXCHANGE, REFINE_CANDIDATE_ROUTING_KEY);
        amqpTemplate.sendAndReceive(REFINE_CANDIDATE_EXCHANGE,
                REFINE_CANDIDATE_ROUTING_KEY, MessageBuilder.withBody(eventMessage.toJSONString().getBytes())
                        .build());
    }

    /**
     * 查询职位转发浏览记录
     *
     * @param id         记录编号
     * @param postUserId 转发者编号
     * @return 查询结果
     * @throws CommonException 业务异常
     */
    @Override
    @CounterIface
    public RecomRecordResult getRecommendation(int id, int postUserId) throws
            CommonException {
        ValidateUtil vu = ParamCheckTool.checkGetRecommendation(id, postUserId);
        String message = vu.validate();
        if (!StringUtils.isNullOrEmpty(message)) {
            throw CandidateExceptionFactory.buildCheckFailedException(message);
        }

        CandidateRecomRecordDO candidateRecomRecordDO = candidateDBDao.getCandidateRecomRecordDO(id, postUserId);
        if (candidateRecomRecordDO == null) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_CANDIDATES_RECORD_NOT_EXIST);
        }

        Future positionFuture = findPositionFutureById(candidateRecomRecordDO.getPositionId());
        Future userFuture = findUserFutureById(candidateRecomRecordDO.getPresenteeUserId());
        RecomRecordResult recomRecordResult = assembleRecomRecordResult(candidateRecomRecordDO, positionFuture, userFuture);

        return recomRecordResult;
    }

    /**
     * 查询员工在公司的推荐排名
     *
     * @param postUserId 转发者编号
     * @param companyId  公司编号
     */
    @Override
    @CounterIface
    public SortResult getRecommendatorySorting(int postUserId, int companyId) throws
            CommonException {
        logger.info("CandidateEntity getRecommendatorySorting postUserId:{}, companyId:{}", postUserId, companyId);
        /** 参数校验 */
        ValidateUtil vu = ParamCheckTool.checkRecommendatorySorting(postUserId, companyId);
        String message = vu.validate();
        if (!StringUtils.isNullOrEmpty(message)) {
            throw CandidateExceptionFactory.buildCheckFailedException(message);
        }
        /** 是否开启被动求职者 */
        /*boolean passiveSeeker = candidateDBDao.isStartPassiveSeeker(companyId);
        logger.info("CandidateEntity getRecommendatorySorting passiveSeeker:{}", passiveSeeker);
        if (!passiveSeeker) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_NOT_START);
        }*/

        /** 查找公司下的职位 */
        List<Integer> companyIdList = employeeEntity.getCompanyIds(companyId);
        List<Integer> positionIdList = positionDao.getPositionIds(companyIdList);
        if (positionIdList == null && positionIdList.size() == 0) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_CANDIDATES_POSITION_NOT_EXIST);
        }

        /** 查找员工信息 */
        List<UserEmployeeDO> employeeDOList = employeeEntity.getVerifiedUserEmployeeDOList(companyId);
        logger.info("CandidateEntity getRecommendatorySorting employeeDOList:{}", employeeDOList);
        if (employeeDOList == null || employeeDOList.size() == 0) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_SORT_COLLEAGUE_NOT_EXIST);
        }
        List<Integer> employeeIdList = employeeDOList.stream().map(userEmployeeDO -> userEmployeeDO.getSysuserId()).collect(Collectors.toList());

        /** 查找排名 */
        List<CandidateRecomRecordSortingDO> sortingDOList = candidateDBDao.listSorting(employeeIdList, positionIdList);
        if (sortingDOList == null || sortingDOList.size() == 0) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_SORT_USER_NOT_EXIST);
        }
        logger.info("CandidateEntity getRecommendatorySorting sortingDOList:{}", sortingDOList);
        return assembleSortingResult(sortingDOList, postUserId);
    }

    @Override
    @CounterIface
    public RecommendResult ignore(int id, int companyId, int postUserId, String
            clickTime) throws CommonException {
        logger.info("CandidateEntity ignore id:{}, companyId:{}, postUserId:{}, clickTime:{}", id, companyId, postUserId, clickTime);
        /** 参数校验 */
        ValidateUtil vu = ParamCheckTool.checkignore(id, companyId, postUserId, clickTime);
        String message = vu.validate();
        if (!StringUtils.isNullOrEmpty(message)) {
            throw CandidateExceptionFactory.buildCheckFailedException(message);
        }

        /** 是否开启被动求职者 */
        /*boolean passiveSeeker = candidateDBDao.isStartPassiveSeeker(companyId);
        if (!passiveSeeker) {
            logger.info("CandidateEntiry ignore 未开启挖掘被动求职者");
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_NOT_START);
        }*/

        CandidateRecomRecordDO recomRecordDO = candidateDBDao.getCandidateRecomRecordDO(id, postUserId);
        logger.info("CandidateEntiry recommend recomRecordDO:{}", recomRecordDO);
        if (recomRecordDO == null) {
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
                    candidate.setIsRecom((byte) RecomType.IGNORE.getValue());
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

    private SortResult assembleSortingResult
    (List<CandidateRecomRecordSortingDO> sortingDOList, int postUserId) {
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
        sortResult.setHongbao(Constant.BONUS * sortResult.getCount());
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
    private RecommendResult assembleRecommendResult(int id, int postUserId, String
            clickTime, int companyId) throws CommonException {
        RecommendResult recommendResult = new RecommendResult();

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

        /** 查找公司下的职位 */
        List<Integer> companyIdList = employeeEntity.getCompanyIds(companyId);
        List<Integer> positionIdList = positionDao.getPositionIds(companyIdList);
        if (positionIdList == null && positionIdList.size() == 0) {
            throw CandidateExceptionFactory.buildException(CandidateCategory.PASSIVE_SEEKER_CANDIDATES_POSITION_NOT_EXIST);
        }

        List<CandidateRecomRecordDO> candidateRecomRecordDOList =
                candidateDBDao.listCandidateRecomRecordDOExceptId(id, postUserId,
                        clickTime, selected, positionIdList);
        if (candidateRecomRecordDOList != null && candidateRecomRecordDOList.size() > 0) {
            CandidateRecomRecordDO candidateRecomRecordDO = candidateRecomRecordDOList.get(0);
            recommendResult.setId(candidateRecomRecordDO.getId());
            Future positionFuture = findPositionFutureById(candidateRecomRecordDO.getPositionId());
            Future userFuture = findUserFutureById(candidateRecomRecordDO.getPresenteeUserId());
            recommendResult.setPresenteeName(refineUserName(userFuture));
            recommendResult.setPositionName(refinePositionName(positionFuture));
            String date = candidateRecomRecordDO.getClickTime();
            if (date != null && date.length() > 10) {
                date = date.trim().substring(0, 10);
            }
            recommendResult.setClickTime(date);
        }


        try {
            int exceptNotRecommendedCount = candidateDBDao.countRecommendation(postUserId,
                    clickTime, exceptNotRecommend, positionIdList);
            recommendResult.setRecomTotal(exceptNotRecommendedCount);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
        }
        try {
            int recommendedCount = candidateDBDao.countRecommendation(postUserId, clickTime, recommended, positionIdList);
            recommendResult.setRecomIndex(recommendedCount);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
        }
        try {
            int ignoreCount = candidateDBDao.countRecommendation(postUserId, clickTime, ignore, positionIdList);
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
    private RecomRecordResult assembleRecomRecordResult(CandidateRecomRecordDO
                                                                candidateRecomRecordDO,
                                                        Future positionFuture, Future userFuture) {
        RecomRecordResult recomRecordResult = new RecomRecordResult();
        recomRecordResult.setId(candidateRecomRecordDO.getId());
        String date = candidateRecomRecordDO.getClickTime();
        if (date != null && date.length() > 10) {
            date = date.trim().substring(0, 10);
        }
        recomRecordResult.setClickTime(date);
        recomRecordResult.setPresenteeName(refineUserName(userFuture));
        recomRecordResult.setTitle(refinePositionName(positionFuture));
        recomRecordResult.setRecom((byte) candidateRecomRecordDO.getIsRecom());
        return recomRecordResult;
    }


    /**
     * 将候选人置为推荐状态
     */
    private void updateCandidateCompany(CandidateRecomRecordDO
                                                candidateRecomRecordDO) throws TException {
        //修改候选人账号
        Optional<CandidatePositionDO> candidatePositionDOOptional =
                candidateDBDao.getCandidatePosition(candidateRecomRecordDO.getPositionId(),
                        candidateRecomRecordDO.getPresenteeUserId());
        if (candidatePositionDOOptional.isPresent()) {
            DateTime now = new DateTime();
            String nowStr = now.toString("yyyy-MM-dd HH:mm:ss");

            CandidateCompanyDO candidateCompanyDO = new CandidateCompanyDO();
            candidateCompanyDO.setIsRecommend((byte) 1);
            candidateCompanyDO.setId(candidatePositionDOOptional.get().getCandidateCompanyId());
            candidateCompanyDO.setUpdateTime(nowStr);
            candidateDBDao.updateCandidateCompany(candidateCompanyDO);
        }
    }

    /**
     * 添加推荐信息
     *
     * @param candidateRecomRecordDO 职位转发浏览记录
     * @param param                  推荐数据
     * @param genderType
     * @throws TException 业务异常
     */
    private void updateRecomRecord(CandidateRecomRecordDO
                                           candidateRecomRecordDO, RecommmendParam param, GenderType genderType) throws TException {
        DateTime now = new DateTime();
        String nowStr = now.toString("yyyy-MM-dd HH:mm:ss");
        candidateRecomRecordDO.setRecomReason(param.getRecomReason());
        candidateRecomRecordDO.setPosition(param.getPosition());
        candidateRecomRecordDO.setCompany(param.getCompany());
        candidateRecomRecordDO.setRealname(param.getRealName());
        candidateRecomRecordDO.setMobile(param.getMobile());
        candidateRecomRecordDO.setRecomTime(nowStr);
        candidateRecomRecordDO.setIsRecom((byte) 0);
        candidateRecomRecordDO.setUpdateTime(nowStr);
        candidateRecomRecordDO.setGender((byte) genderType.getValue());
        candidateRecomRecordDO.setEmail(param.getEmail());
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
                candidate.setIsRecom((byte) 0);
                candidate.setUpdateTime(nowStr);
                candidate.setEmail(param.getEmail());
                candidate.setGender((byte) genderType.getValue());
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
    private RecommendResult assembleResult(RecommendResult
                                                   recommendResult, CandidateRecomRecordDO candidateRecomRecordDO,
                                           Future positionListFuture, Future presenteeListFuture) {
        recommendResult.setId(candidateRecomRecordDO.getId());
        String date = candidateRecomRecordDO.getClickTime();
        if (date != null && date.length() > 10) {
            date = date.trim().substring(0, 10);
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

    private Future findCandidatePositionFuture
            (List<CandidateRecomRecordDO> candidateRecomRecordDOList) {
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
    private Future findPresenteeList
    (List<CandidateRecomRecordDO> candidateRecomRecordDOList) {
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
    private List<com.moseeker.thrift.gen.candidate.struct.Candidate> addCandidate
    (List<CandidateRecomRecordDO> candidateRecomRecordDOPositionList, List<UserUserDO> presenteeUserList,
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
    private void setCandidatePosition
    (com.moseeker.thrift.gen.candidate.struct.Candidate candidate,
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
            } else {
                candidate.setViewNumber(0);
                candidate.setInsterested(false);
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
    private void setRepost(com.moseeker.thrift.gen.candidate.struct.Candidate
                                   candidate,
                           List<UserUserDO> repostUserList, CandidateRecomRecordDO
                                   candidateRecomRecordDO) {
        if (repostUserList != null && repostUserList.size() > 0) {
            Optional<UserUserDO> userUserDOOptional = repostUserList.stream()
                    .filter(userUserDO -> userUserDO.getId() == candidateRecomRecordDO.getRepostUserId())
                    .findAny();
            if (userUserDOOptional.isPresent()) {
                candidate.setPresenteeFriendId(userUserDOOptional.get().getId());
                String name = StringUtils.isNotNullOrEmpty(userUserDOOptional.get().getName())
                        ? userUserDOOptional.get().getName() : userUserDOOptional.get().getNickname();
                candidate.setPresenteeFriendName(name);
            } else {
                candidate.setPresenteeFriendName("");
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
    private void setPresentee(com.moseeker.thrift.gen.candidate.struct.Candidate
                                      candidate,
                              List<UserUserDO> presenteeUserList, CandidateRecomRecordDO
                                      candidateRecomRecordDO) {
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
            } else {
                candidate.setPresenteeName("");
                candidate.setPresenteeLogo("");
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

    @Autowired
    protected DefaultDSLContext create;


    @Override
    public Map<String, Object> getCandidateInfo(int hrId, int userId, int positionId) {
        logger.info("getCandidateInfo:{},{},{}", hrId, userId, positionId);

        Map<String, Object> result = new HashMap<>();

        UserHrAccountRecord userHrAccountRecord = create.select().from(UserHrAccount.USER_HR_ACCOUNT)
                .where(UserHrAccount.USER_HR_ACCOUNT.ID.eq(hrId)).fetchOneInto(UserHrAccountRecord.class);

        if (userHrAccountRecord == null) {
            throw CandidateException.NODATA_EXCEPTION.setMess("无效的HR账号");
        }

        UserUserRecord userRecord = create.select().from(UserUser.USER_USER).where(UserUser.USER_USER.ID.eq(userId)).fetchOneInto(UserUserRecord.class);

        if (userRecord == null) {
            throw CandidateException.NODATA_EXCEPTION.setMess("找不到该用户");
        }

        //判断该候选人是否属于该公司
        CandidateCompanyRecord candidateCompanyRecord = create.select().from(CandidateCompany.CANDIDATE_COMPANY)
                .where(CandidateCompany.CANDIDATE_COMPANY.COMPANY_ID.eq(userHrAccountRecord.getCompanyId()))
                .and(CandidateCompany.CANDIDATE_COMPANY.SYS_USER_ID.eq(userId))
                .fetchAnyInto(CandidateCompanyRecord.class);

        if (candidateCompanyRecord == null) {
            throw CandidateException.NO_PERMISSION_EXCEPTION;
        }

        CandidateRemarkRecord candidateRemarkRecord = create.select().from(CandidateRemark.CANDIDATE_REMARK)
                .where(CandidateRemark.CANDIDATE_REMARK.USER_ID.eq(userId))
                .and(CandidateRemark.CANDIDATE_REMARK.HRACCOUNT_ID.eq(hrId))
                .fetchAnyInto(CandidateRemarkRecord.class);

        // 如果是新数据需要添加一条新数据
        if (candidateRemarkRecord == null) {

            candidateRemarkRecord = new CandidateRemarkRecord();
            candidateRemarkRecord.setHraccountId(hrId);
            candidateRemarkRecord.setUserId(userId);
            candidateRemarkRecord.setStatus(1);
            if (userHrAccountRecord.getAccountType().intValue() == Constant.ACCOUNT_TYPE_SUPERACCOUNT) {
                handlerCandidateRemark(candidateRemarkRecord, userHrAccountRecord.getCompanyId(), userId);
            }
            create.attach(candidateRemarkRecord);
            candidateRemarkRecord.insert();
        } else {
            if (candidateRemarkRecord.getStatus() == 0 || candidateRemarkRecord.getStatus() == 2) {
                candidateRemarkRecord.setStatus(1);
                candidateRemarkRecord.update();
            }
        }

        result.putAll(candidateRemarkRecord.intoMap());

        // 检查该用户是否申请过职位
        int companyId = userHrAccountRecord.getCompanyId();

        int appNum = create.selectCount().from(JobApplication.JOB_APPLICATION)
                .where(JobApplication.JOB_APPLICATION.APPLIER_ID.eq(userId))
                .and(JobApplication.JOB_APPLICATION.COMPANY_ID.eq(companyId))
                .and(JobApplication.JOB_APPLICATION.APPLY_TYPE.in(0, 1))
                .and(JobApplication.JOB_APPLICATION.EMAIL_STATUS.eq(0))
                .fetchOne().value1();
        result.put("has_app", appNum > 0 ? 1 : 0);

        Set<Integer> userIds = new HashSet<>();            //存储需要检索的微信编号

        List<Map<String, Object>> chain = new LinkedList<>();

        CandidatePosition CP = CandidatePosition.CANDIDATE_POSITION;
        /* 查找相关职位 */
        List<CandidatePositionRecord> listA = create.select().from(CP)
                .where(CP.CANDIDATE_COMPANY_ID.eq(candidateCompanyRecord.getId()))
                .groupBy(CP.CANDIDATE_COMPANY_ID)
                .fetchInto(CandidatePositionRecord.class);
        List<CandidatePositionRecord> listB = create.select().from(CP)
                .where(CP.CANDIDATE_COMPANY_ID.eq(candidateCompanyRecord.getId()))
                .and(CP.SHARED_FROM_EMPLOYEE.eq(Integer.valueOf(1).byteValue()))
                .groupBy(CP.CANDIDATE_COMPANY_ID)
                .fetchInto(CandidatePositionRecord.class);
        List<CandidatePositionRecord> records = listA.size() == listB.size() ? listB : listA;

        CandidatePositionRecord position = records.get(0);

        if (position != null) {
            /* 查找相关的转发链路 */
            CandidateShareChain CSC = CandidateShareChain.CANDIDATE_SHARE_CHAIN;
            List<CandidateShareChainRecord> shareRecords = create.select().from(CSC)
                    .where(CSC.PRESENTEE_USER_ID.ne(CSC.RECOM_USER_ID))
                    .and(CSC.PRESENTEE_USER_ID.gt(0))
                    .and(CSC.RECOM_USER_ID.gt(0))
                    .and(CSC.POSITION_ID.eq(position.getPositionId()))
                    .orderBy(CSC.ID.desc())
                    .fetchInto(CandidateShareChainRecord.class);

            List<UserEmployeeRecord> employeeUserIds = getValidEmployeeUserIds(userHrAccountRecord.getCompanyId());

            chain = buildChain(userId, position.getPositionId(), shareRecords, employeeUserIds, userIds);

            if (userIds.size() > 0) {
                List<UserUserRecord> userInfos = create.select().from(UserUser.USER_USER)
                        .where(UserUser.USER_USER.ID.in(userIds))
                        .fetchInto(UserUserRecord.class);
                List<CandidateRemarkRecord> remarks = create.select().from(CandidateRemark.CANDIDATE_REMARK)
                        .where(CandidateRemark.CANDIDATE_REMARK.USER_ID.in(userIds))
                        .and(CandidateRemark.CANDIDATE_REMARK.HRACCOUNT_ID.eq(hrId))
                        .fetchInto(CandidateRemarkRecord.class);

                /* 将查询到的微信信息加入到链路中 */
                for (Map<String, Object> map : chain) {
                    for (UserUserRecord userInfo : userInfos) {
                        if (((Integer) map.get("user_id")).intValue() == userInfo.getId()) {
                            map.put("headimg", userInfo.getHeadimg());
                            if (StringUtils.isNullOrEmpty(userInfo.getName())) {
                                map.put("nickname", userInfo.getNickname());
                            } else {
                                map.put("nickname", userInfo.getName());
                            }
                        }
                    }
                    for (CandidateRemarkRecord remark : remarks) {
                        if (((Integer) map.get("user_id")).intValue() == remark.getUserId()) {
                            map.put("status", remark.getStatus());
                        }
                    }
                }
            }
        }
        result.put("sharechain", chain);
        result.put("sysuser", userRecord.intoMap());
        return result;
    }

    public void handlerCandidateRemark(CandidateRemarkRecord remark, int companyId, int userId) {
        List<UserHrAccountRecord> accountRecordList = create.select().from(UserHrAccount.USER_HR_ACCOUNT)
                .where(UserHrAccount.USER_HR_ACCOUNT.COMPANY_ID.eq(companyId))
                .and(UserHrAccount.USER_HR_ACCOUNT.ACCOUNT_TYPE.ne(Constant.ACCOUNT_TYPE_SUPERACCOUNT))
                .fetchInto(UserHrAccountRecord.class);
        List<Integer> accountIdList = accountRecordList.stream().map(m -> m.getId()).collect(Collectors.toList());
        CandidateRemarkRecord remarkRecord = create.select().from(CandidateRemark.CANDIDATE_REMARK)
                .where(CandidateRemark.CANDIDATE_REMARK.USER_ID.eq(userId))
                .and(CandidateRemark.CANDIDATE_REMARK.HRACCOUNT_ID.in(accountIdList))
                .orderBy(CandidateRemark.CANDIDATE_REMARK.CREATE_TIME.desc())
                .limit(1)
                .fetchOneInto(CandidateRemarkRecord.class);
        if (remarkRecord != null) {
            remark.setCurrentCompany(remarkRecord.getCurrentCompany());
            remark.setCurrentPosition(remarkRecord.getCurrentPosition());
        }
    }

    @Override
    public RecentPosition getRecentPosition(int hrId, int userId) {
        RecentPosition recentPosition = new RecentPosition();

        UserHrAccountDO hrAccountDO = hrAccountDao.getValidAccount(hrId);
        if (hrAccountDO != null && hrAccountDO.getId() > 0) {
            int companyId = hrAccountDO.getCompanyId();
            List<Integer> positionIdList;
            if (hrAccountDO.getAccountType() == HRAccountType.SupperAccount.getType()) {
                positionIdList = positionDao.getPositionIds(new ArrayList<Integer>() {{
                    add(companyId);
                }});
            } else {
                positionIdList = positionDao.getPositionIdByPublisher(hrId);
            }
            if (positionIdList != null && positionIdList.size() > 0) {
                CandidatePositionRecord recentViewedPosition = candidateDBDao.fetchRecentViewedPosition(userId, positionIdList);
                if (recentViewedPosition != null) {
                    recentPosition.setPositionId(recentViewedPosition.getPositionId());
                    JobPositionRecord positionRecord = positionDao.getPositionById(recentViewedPosition.getPositionId());
                    if (positionRecord != null) {
                        recentPosition.setPositionName(positionRecord.getTitle());
                    }
                    List<DictCityDO> positionCityRecordList = positionCityDao.getPositionCitys(recentPosition.getPositionId());
                    if (positionCityRecordList != null && positionCityRecordList.size() > 0) {
                        List<String> cities = new ArrayList<>();
                        positionCityRecordList.forEach(dictCityDO -> cities.add(dictCityDO.getName()));
                        recentPosition.setCities(cities);
                    }
                }
            }
        }

        return recentPosition;
    }

    @Override
    public void addApplicationReferral(int applicationId, int pscId, int directReferralUserId) {
        candidateDBDao.addApplicationPsc(applicationId, pscId, directReferralUserId);
    }

    @Override
    public CandidateApplicationReferralDO getApplicationReferralByApplication(int applicationId) {
        return candidateDBDao.getApplicationPscByApplicationId(applicationId);
    }

    @Override
    public com.moseeker.candidate.service.vo.PositionLayerInfo getPositionLayerInfo(int userId, int companyId, int positionId) throws TException {
        com.moseeker.candidate.service.vo.PositionLayerInfo layer = new com.moseeker.candidate.service.vo.PositionLayerInfo();
        JobPositionRecord position = positionDao.getPositionById(positionId);
        if (position == null || position.getCompanyId().intValue() != companyId) {
            throw CommonException.PROGRAM_PARAM_NOTEXIST;
        }
        Optional<CandidateCompanyDO> ccd = candidateDBDao.getCandidateCompanyByUserIDCompanyID(userId, companyId);
        HrWxWechatDO wechatDO = wechatDao.getHrWxWechatByCompanyId(companyId);
        if (wechatDO == null || !ccd.isPresent()) {
            throw CommonException.NODATA_EXCEPTION;
        }
        layer.setProfileCompleteness(profileEntity.getCompleteness(userId, "", 0));
        UserWxUserRecord wxUser = wxUserDao.getWxUserByUserIdAndWechatIdAndSubscribe(userId, wechatDO.getId());
        Optional<CandidatePositionDO> candidatePositionDOS = candidateDBDao.getCandidatePosition(positionId, userId);
        int positionNum = candidateDBDao.getCandidateCompanyByCandidateCompanyID(ccd.get().getId());
        if (candidatePositionDOS.isPresent()) {
            layer.setCurrentPositionCount(candidatePositionDOS.get().getViewNumber());
        }
        layer.setPositionViewCount(positionNum);
        layer.setIsSubscribe(0);
        if (wxUser != null) {
            layer.setIsSubscribe(1);
        }
        layer.setQrcode(wechatDO.getQrcode());
        layer.setType(wechatDO.getType());
        layer.setName(wechatDO.getName());
        layer.setPositionWxLayerProfile(ccd.get().getPositionWxLayerProfile());
        layer.setPositionWxLayerQrcode(ccd.get().getPositionWxLayerQrcode());

        return layer;
    }

    @Override
    public void closeElasticLayer(int userId, int companyId, int type) throws Exception{
        try {
            Optional<CandidateCompanyDO> candidateCompanyDOOptional = candidateDBDao.getCandidateCompanyByUserIDCompanyID(userId, companyId);
            if (candidateCompanyDOOptional.isPresent()) {
                CandidateCompanyDO candidateCompanyDO = candidateCompanyDOOptional.get();
                if (type == Constant.ELASTIC_LAYER_QRCODE) {
                    candidateDBDao.updateCandidateCompanySetPositionWxLayerQrcode(candidateCompanyDO.getId(), (byte) 1);
                } else if (type == Constant.ELASTIC_LAYER_PROFILE) {
                    candidateDBDao.updateCandidateCompanySetPositionWxLayerProfile(candidateCompanyDO.getId(), (byte) 1);
                }
            } else {
                throw CommonException.PROGRAM_PARAM_NOTEXIST;
            }
        } catch (BIZException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Response getCandidateRecoms(List<Integer> appIds) {
        List<CandidateRecomRecordDO> tempRecomRecords = create.select().from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID.in(appIds))
                .fetchInto(CandidateRecomRecordDO.class);
        return ResponseUtils.success(tempRecomRecords);
    }

    /**
     * 查找候选人的链路
     *
     * @param shareChainRecords 职位转发集合
     * @param employeeUserIds   需要检索的微信编号集合
     * @param userIds           用户集合
     */
    private List<Map<String, Object>> buildChain(int userId, int positionId,
                                                 List<CandidateShareChainRecord> shareChainRecords,
                                                 List<UserEmployeeRecord> employeeUserIds,
                                                 Set<Integer> userIds) {

        LinkedList<Map<String, Object>> chain = new LinkedList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        map.put("status", 0);

        logger.info("1st Add " + map.get("user_id") + " for position " + positionId);
        chain.add(map);

        userIds.add(userId);

        addPreNode(userId, positionId, -1, shareChainRecords, chain, employeeUserIds, userIds);

        // chain 生成完毕
        // 开始处理 sharechain 包含相同节点的问题
        chain = (LinkedList<Map<String, Object>>) transformChain(chain);
        return chain;
    }

    /**
     * 递归查找前一个链路数据
     *
     * @param user_id           查找该 user_id 之前的记录
     * @param shareChainRecords 职位转发集合
     * @param userIds           需要检索的微信编号集合
     */
    private void addPreNode(int user_id,
                            int positionId,
                            int recordId,
                            List<CandidateShareChainRecord> shareChainRecords,
                            LinkedList<Map<String, Object>> chain,
                            List<UserEmployeeRecord> employeeUserIds, Set<Integer> userIds) {

        if (shareChainRecords != null && shareChainRecords.size() > 0) {

            List<CandidateShareChainRecord> records;

            if (recordId == -1) {
                records = shareChainRecords.stream()
                        .filter(p -> p.getPositionId() == positionId &&
                                p.getPresenteeUserId() == user_id).collect(Collectors.toList());

                if (records.size() == 0)
                    return;
            } else {
                records = shareChainRecords.stream().filter(p -> p.getId().intValue() == recordId).collect(Collectors.toList());
            }

            if (records.size() > 0) {
                CandidateShareChainRecord latestShareChainRecord = records.get(0);
                if (latestShareChainRecord.getDepth() == 1) {
                    logger.info("depth is 1, end iteration");


                    Map<String, Object> map = new HashMap<>();
                    int recom_user_id = latestShareChainRecord.getRecomUserId().intValue();


                    logger.info("Add recom_user_id: " + recom_user_id + " into chain");

                    map.put("user_id", recom_user_id);
                    map.put("status", 0);

                    List<UserEmployeeRecord> matchedEmpoyeeInfoList = employeeUserIds.stream().filter(p -> p.getSysuserId() == recom_user_id).collect(Collectors.toList());
                    if (matchedEmpoyeeInfoList.size() > 0) {
                        UserEmployeeRecord matchedEmployee = matchedEmpoyeeInfoList.get(0);
                        map.put("employee_id", matchedEmployee.getId());
                    }

                    chain.addFirst(map);
                    userIds.add(recom_user_id);
                } else if (latestShareChainRecord.getDepth() > 1) {

                    logger.info("parentID:" + recordId + "depth = " + latestShareChainRecord.getDepth() + " , recursive run");

                    Map<String, Object> map = new HashMap<>();
                    int recom_user_id = latestShareChainRecord.getRecomUserId();

                    logger.info("Add recom_user_id: " + recom_user_id + " into chain");

                    map.put("user_id", recom_user_id);
                    map.put("status", 0);

                    List<UserEmployeeRecord> matchedEmpoyeeInfoList = employeeUserIds.stream().filter(p -> p.getSysuserId() == recom_user_id).collect(Collectors.toList());
                    if (matchedEmpoyeeInfoList.size() > 0) {
                        UserEmployeeRecord matchedEmployee = matchedEmpoyeeInfoList.get(0);
                        map.put("employee_id", matchedEmployee.getId());
                    }

                    chain.addFirst(map);
                    userIds.add(recom_user_id);

                    if (latestShareChainRecord.getParentId() <= 0) {
                        return;
                    }
                    addPreNode(user_id, positionId, latestShareChainRecord.getParentId(), shareChainRecords, chain, employeeUserIds, userIds);

                }
            }
        }
    }

    /**
     * 检查链路是否包含重复的人
     *
     * @param chain 原始链路
     * @return
     */
    private boolean containsDuplicatedNodes(List<Map<String, Object>> chain) {
        List<Integer> chainUserIdList = chain.stream().map(m -> (Integer) m.get("user_id")).collect(Collectors.toList());
        for (int index = chainUserIdList.size() - 1; index > 0; index--) {
            Integer checkTarget = chainUserIdList.get(index);
            long count = chainUserIdList.stream().filter(i -> i.equals(checkTarget)).count();
            if (count == 1) {
                continue;
            }
            if (count >= 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * 循环去除链路中重复的wxuser
     *
     * @param chain 原始链路
     * @return
     */
    private List<Map<String, Object>> transformChain(List<Map<String, Object>> chain) {
        while (containsDuplicatedNodes(chain)) {
            List<Integer> chainWxuserIdList = chain.stream().map(m -> (Integer) m.get("user_id")).collect(Collectors.toList());
            int startIndex = -1, endIndex = -1;
            for (int index = chainWxuserIdList.size() - 1; index > 0; index--) {
                Integer checkTarget = chainWxuserIdList.get(index);
                long count = chainWxuserIdList.stream().filter(i -> i.equals(checkTarget)).count();
                if (count == 1) {
                    continue;
                }
                if (count >= 2) {
                    for (int i = 0; i < chainWxuserIdList.size(); i++) {
                        if (chainWxuserIdList.get(i).equals(checkTarget)) {
                            startIndex = i;
                            break;
                        }
                    }
                    for (int i = chainWxuserIdList.size() - 1; i > 0; i--) {
                        if (chainWxuserIdList.get(i).equals(checkTarget)) {
                            endIndex = i;
                            break;
                        }
                    }
                    assert startIndex > -1;
                    assert endIndex > -1;
                    break;
                }
            }

            if (startIndex > -1 && endIndex > -1) {
                chain = new LinkedList<>(
                        Stream.concat(chain.subList(0, startIndex).stream(), chain.subList(endIndex, chain.size()).stream())
                                .collect(Collectors.toList()));
            }
        }
        return chain;
    }

    /**
     * 获取该公司 ID 下所有合法的员工的 user_id （在需要显示链路的时候即时查询）
     * 如果该公司属于某个集团公司，会返回该集团公司下所有公司的合法员工 user_id
     *
     * @param companyId 公司 ID
     */
    private List<UserEmployeeRecord> getValidEmployeeUserIds(Integer companyId) {
        // 如果返回空列表，即该公司不属于集团公司
        HrGroupCompanyRel groupCompanyRel = HrGroupCompanyRel.HR_GROUP_COMPANY_REL;
        List<HrGroupCompanyRelRecord> retCheckGroup = create.select().from(groupCompanyRel)
                .where(groupCompanyRel.GROUP_ID
                        .eq(create.select(groupCompanyRel.GROUP_ID)
                                .from(groupCompanyRel)
                                .where(groupCompanyRel.COMPANY_ID.eq(companyId))))
                .fetchInto(HrGroupCompanyRelRecord.class);
        // yiliang： user_employee.status 以后确认不再使用，所以在此不使用
        org.jooq.Condition condition;
        if (!retCheckGroup.isEmpty()) {
            List<Integer> companyIds = retCheckGroup.stream().map(e -> e.getCompanyId()).collect(Collectors.toList());
            condition = UserEmployee.USER_EMPLOYEE.COMPANY_ID.in(companyIds);
        } else {
            condition = UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(companyId);
        }
        return create.select().from(UserEmployee.USER_EMPLOYEE)
                .where(UserEmployee.USER_EMPLOYEE.DISABLE.eq(Integer.valueOf(0).byteValue()))
                .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq(Integer.valueOf(0).byteValue()))
                .and(condition)
                .fetchInto(UserEmployeeRecord.class);
    }

}
