package com.moseeker.useraccounts.service.impl;

import com.google.common.collect.Lists;
import com.moseeker.baseorm.constant.WechatAuthorized;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateRecomRecordRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralSeekRecommendRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.biztools.RecruitmentScheduleEnum;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.exception.RecruitmentScheduleLastStepNotExistException;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.ReferralEntity;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidatePositionDO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateRecomRecordDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrOperationRecordDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserCollectPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import com.moseeker.useraccounts.annotation.RadarSwitchLimit;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.impl.biztools.UserCenterBizTools;
import com.moseeker.useraccounts.service.impl.vo.UserCenterInfoVO;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户个人中心功能相关接口
 *
 * @author jack
 */
@Service
@CounterIface
public class UserCenterService {

    Logger logger = LoggerFactory.getLogger(UserCenterService.class);

    @Autowired
    private UserCenterBizTools bizTools;

    @Autowired
    private HrWxWechatDao wechatDao;

    @Autowired
    EmployeeEntity employeeEntity;

    @Autowired
    private UserUserDao userUserDao;

    @Autowired
    private UserWxUserDao wxUserDao;

    @Autowired
    private ReferralEntity referralEntity;
    /**
     * 查询申请记录
     *
     * @param userId 用户编号
     * @return 申请记录
     * @throws CommonException
     */
    public List<ApplicationRecordsForm> getApplication(int userId) throws CommonException {
        logger.info("UserCenterService getApplication userId:{}", userId);
        List<ApplicationRecordsForm> applications = new ArrayList<>();

        //参数有效性校验
        if (userId > 0) {
            //查询申请记录
            List<JobApplicationDO> apps = bizTools.getAppsForUser(userId);
            if (apps != null && apps.size() > 0) {
                //查询申请记录对应的职位数据
                List<JobPositionDO> positions = bizTools.getPositions(apps.stream().map(app -> Integer.valueOf(app.getPositionId())).collect(Collectors.toList()));
                logger.info("UserCenterService getApplication positions:{}", positions);
                List<Integer> companyIdList = apps.stream().map(app -> Integer.valueOf(app.getCompanyId())).collect(Collectors.toList());
                List<Hrcompany> companies = bizTools.getCompanies(companyIdList);
                logger.info("UserCenterService getApplication companies:{}", companies);
                List<HrOperationRecordDO> operationRecordDOList = bizTools.listLastHrOperationRecordPassedReject(apps.stream().map(app -> app.getAppTplId()).collect(Collectors.toSet()));
                logger.info("UserCenterService getApplication operationRecordDOList:{}", operationRecordDOList);

                //查找signature
                Query.QueryBuilder findWechatQuery = new Query.QueryBuilder();
                findWechatQuery.select(HrWxWechat.HR_WX_WECHAT.COMPANY_ID.getName())
                        .select(HrWxWechat.HR_WX_WECHAT.ID.getName())
                        .select(HrWxWechat.HR_WX_WECHAT.SIGNATURE.getName())
                        .where(new Condition(HrWxWechat.HR_WX_WECHAT.COMPANY_ID.getName(), companyIdList, ValueOp.IN))
                        .and(HrWxWechat.HR_WX_WECHAT.AUTHORIZED.getName(), WechatAuthorized.AUTHORIZED.getValue());
                Map<Integer, String> signatureMap = wechatDao.getDatas(
                        findWechatQuery.buildQuery())
                        .stream()
                        .collect(Collectors.toMap(k->k.getCompanyId(), v->v.getSignature(),
                                (companyId1, companyId2) -> companyId1));
                //List<ConfigSysPointConfTplDO> tpls = bizTools.getAwardConfigTpls();

                applications = apps.stream().map(app -> {
                    ApplicationRecordsForm ar = new ApplicationRecordsForm();
                    ar.setId(app.getId());
                    RecruitmentScheduleEnum recruitmentScheduleEnum = RecruitmentScheduleEnum.createFromID(app.getAppTplId());
                    ar.setStatus_name(recruitmentScheduleEnum.getStatus());
                    ar.setTime(app.getSubmitTime());
                    if (positions != null) {
                        Optional<JobPositionDO> op = positions.stream().filter(position -> position.getId() == app.getPositionId()).findFirst();
                        if (op.isPresent()) {
                            ar.setPosition_title(op.get().getTitle());
                        }
                    }
                    if (companies != null) {
                        Optional<Hrcompany> op = companies.stream().filter(company -> company.getId() == app.getCompanyId()).findFirst();
                        if (op.isPresent()) {
                            ar.setCompany_name(op.get().getAbbreviation());
                        }
                    }
                    //设置最后一个非拒绝申请记录
                    int preID = 0;
                    if(operationRecordDOList != null) {
                        Optional<HrOperationRecordDO> operationRecordDOOptional = operationRecordDOList.stream()
                                .filter(operation -> operation.getOperateTplId() == app.getAppTplId()).findFirst();
                        if(operationRecordDOOptional.isPresent() && operationRecordDOOptional.get().getOperateTplId() != recruitmentScheduleEnum.getId()) {
                            recruitmentScheduleEnum.setLastStep(operationRecordDOOptional.get().getOperateTplId());
                            preID = operationRecordDOOptional.get().getOperateTplId();
                        }
                    }
                    if (org.apache.commons.lang.StringUtils.isNotBlank(signatureMap.get(app.getCompanyId()))) {
                        ar.setSignature(signatureMap.get(app.getCompanyId()));
                    }
                    logger.info("UserCenterService getApplication recruitmentScheduleEnum:{}", recruitmentScheduleEnum);
                    ar.setStatus_name(recruitmentScheduleEnum.getAppStatusDescription((byte)app.getApplyType(), (byte)app.getEmailStatus(), preID));
                    return ar;
                }).collect(Collectors.toList());
            }
        } else {
            //do nothing
        }
        if(applications.size() > 0) {
            applications.forEach(application -> {
                logger.info("ApplicationRecordsForm getApplication application:{}", application);
            });
        } else {
            logger.info("ApplicationRecordsForm getApplication have no application");
        }
        return applications;
    }

    /**
     * 查询用户收藏的职位
     *
     * @param userId 用户编号
     * @return 收藏的职位集合
     * @throws CommonException
     */
    public List<FavPositionForm> getFavPositions(int userId) throws CommonException {
        List<FavPositionForm> favPositions = new ArrayList<>();
        //参数校验
        if (userId > 0) {
            //查询用户的收藏职位列表
            List<UserCollectPositionDO> favPositionRecords = bizTools.getFavPositions(userId);
            if (favPositionRecords != null && favPositionRecords.size() > 0) {
                //差用用户收藏职位的职位详情
                List<JobPositionDO> positions = bizTools.getPositions(favPositionRecords.stream().map(favP -> Integer.valueOf(favP.getPositionId())).collect(Collectors.toList()));
                //拼装职位收藏记录
                if (positions != null && positions.size() > 0) {
                    favPositions = favPositionRecords.stream()
                            .filter(favP -> {            //过滤不存在职位的职位收藏记录
                                boolean flag = false;
                                for (JobPositionDO p : positions) {
                                    if (p.getId() == favP.getPositionId()) {
                                        flag = true;
                                    }
                                }
                                return flag;
                            })
                            .map(record -> {
                                FavPositionForm form = new FavPositionForm();
                                Optional<JobPositionDO> op = positions.stream().filter(p -> p.getId() == record.getPositionId()).findFirst();
                                if (op.isPresent()) {
                                    form.setId(op.get().getId());
                                    form.setTitle(op.get().getTitle());
                                    form.setDepartment(op.get().getDepartment());
                                    form.setTime(record.getUpdateTime());
                                    form.setCity(op.get().getCity());
                                    form.setSalary_top(NumberUtils.toInt(op.get().getSalaryTop()+"", 0));
                                    form.setSalary_bottom(NumberUtils.toInt(op.get().getSalaryBottom()+"", 0));
                                    form.setUpdate_time(op.get().getUpdateTime());
                                    form.setStatus((byte) op.get().getStatus());
                                }
                                return form;
                            }).collect(Collectors.toList());
                }
            }
        } else {
            //do nothing
        }

        return favPositions;
    }

    @RadarSwitchLimit
    public RecommendationScoreVO getRecommendationsV2(int companyId, int userId) throws CommonException{
        RecommendationScoreVO scoreVO = new RecommendationScoreVO();
        if(!employeeEntity.isEmployee(userId, companyId)){
            throw UserAccountException.PERMISSION_DENIED;
        }
        try {
            ThreadPool tp = ThreadPool.Instance;
            int totalCount = 0;             //转发记录总数
            int interestedCount = 0;         //被推荐的转发记录数
            /** 并行查找统计信息 */
            Future<Set<Integer>> employeeUserFuture =  tp.startTast(() -> employeeEntity.getActiveEmployeeUserIdList(companyId));
            List<Integer> positionIdList = bizTools.listPositionIdByUserId(userId);
            if (positionIdList == null) {
                return scoreVO;
            }
            Future<List<CandidateRecomRecordRecord>> totalCountFuture = tp.startTast(() -> bizTools.listCandidateRecomRecords(
                    userId, positionIdList, employeeUserFuture.get(), companyId));
            Future<List<ReferralSeekRecommendRecord>> interestedCountFuture = tp.startTast(() -> referralEntity.fetchEmployeeSeekRecommend(
                    userId, positionIdList, employeeUserFuture.get() ));
            totalCount = totalCountFuture.get().size();
            interestedCount = interestedCountFuture.get().size();
            scoreVO.setInterested_count(interestedCount);
            scoreVO.setLink_viewed_count(totalCount);
        } catch (CommonException e) {
            logger.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        } catch (ExecutionException e) {
            logger.error(e.getMessage(), e);
        }
        return scoreVO;
    }

    /**
     * 查询历史推荐记录
     *
     * @param userId 用户编号
     * @return 历史推荐记录
     * @throws CommonException thrift异常类
     */
    @SuppressWarnings("unchecked")
    public RecommendationVO getRecommendations(int userId, byte type, int pageNo, int pageSize) throws CommonException {
        logger.info("UserCenterService getRecommendations userId:{}, type:{}, pageNo:{}, pageSize:{}", userId, type, pageNo, pageSize);
        RecommendationVO recommendationForm = new RecommendationVO();
        try {

            ThreadPool tp = ThreadPool.Instance;
            int totalCount = 0;             //转发记录总数
            int interestedCount = 0;         //被推荐的转发记录数
            int applyCount = 0;             //有过申请的转发记录数
            /** 并行查找三个统计信息 */

            List<Integer> positionIdList = bizTools.listPositionIdByUserId(userId);
            logger.info("UserCenterService getRecommendations positionIdList:{}", positionIdList);
            if (positionIdList == null) {
                return recommendationForm;
            }

            List<Integer> presenteeUserIdList = referralEntity.fetchReferenceIdList(userId);

            Future<Integer> totalCountFuture = tp.startTast(() -> bizTools.countCandidateRecomRecord(userId, positionIdList, presenteeUserIdList));
            Future<Integer> interestedCountFuture = tp.startTast(() -> bizTools.countInterestedCandidateRecomRecord(userId, positionIdList));
            Future<Integer> applyCountFuture = tp.startTast(() -> bizTools.countAppliedCandidateRecomRecord(userId, positionIdList));
            totalCount = totalCountFuture.get();
            interestedCount = interestedCountFuture.get();
            applyCount = applyCountFuture.get();
            RecommendationScoreVO scoreVO = new RecommendationScoreVO();
            scoreVO.setApplied_count(applyCount);
            scoreVO.setInterested_count(interestedCount);
            scoreVO.setLink_viewed_count(totalCount);
            recommendationForm.setScore(scoreVO);


            logger.info("UserCenterService getRecommendations before listCandidateRecomRecords userId:{}, type:{}, positionIdList:{}, presenteeUserIdList:{}, pageNo:{}, pageSize:{}", userId, type, positionIdList, presenteeUserIdList, pageNo, pageSize);
            /** 分页查找相关职位转发记录 */
            List<CandidateRecomRecordDO> recomRecordDOList = bizTools.listCandidateRecomRecords(userId, type, positionIdList, presenteeUserIdList, pageNo, pageSize);
            logger.info("UserCenterService getRecommendations recomRecordDOList.size():{}, recomRecordDOList:{}", recomRecordDOList.size(), recomRecordDOList);
            logger.info("totalCount:{},applyCount:{},interestedCount:{}", totalCount,applyCount,interestedCount);
            if (totalCount > 0 || interestedCount > 0 || applyCount > 0) {
                recommendationForm.setHasRecommends(true);
            } else {
                recommendationForm.setHasRecommends(false);
            }

            if (recomRecordDOList.size() > 0) {

                Set<Integer> positionIDSet = new HashSet<>();           //转发记录涉及到的职位编号，需要根据这些职位编号查找职位名称
                Set<Integer> presenteeIDSet = new HashSet<>();          //转发记录涉及到的浏览者编号，需要根据这些编号查找用户名称、昵称等信息
                Set<Integer> repostIDSet = new HashSet<>();             //转发记录涉及到的转发者编号，需要根据这些编号查找用户名称、昵称等信息
                Set<Integer> appIDSet = new HashSet<>();                //转发职位涉及到的申请编号，需要根据这些申请编号查找申请人等信息
                List<Map<Integer, Integer>> cps = new ArrayList<>();    //转发职位涉及到的职位编号和浏览者编号，需要根据这些信息查找候选人浏览职位的记录，以便确认是否感兴趣和浏览该职位的次数

                recomRecordDOList.forEach(candidateRecomRecordDO -> {

                    positionIDSet.add(candidateRecomRecordDO.getPositionId());
                    if (candidateRecomRecordDO.getPresenteeUserId() > 0) {
                        presenteeIDSet.add(candidateRecomRecordDO.getPresenteeUserId());
                    }
                    if (candidateRecomRecordDO.getRepostUserId() > 0) {
                        repostIDSet.add(candidateRecomRecordDO.getRepostUserId());
                    }
                    if (candidateRecomRecordDO.getAppId() > 0) {
                        appIDSet.add(candidateRecomRecordDO.getAppId());
                    }
                    cps.add(new HashMap<Integer, Integer>() {
                        {
                            put(candidateRecomRecordDO.getPositionId(), candidateRecomRecordDO.getPresenteeUserId());
                        }
                    });
                });

                /** 并行处理数据查询 */
                Future psotionFuture = tp.startTast(()-> bizTools.listJobPositions(positionIDSet));
                Future presenteeFuture = tp.startTast(()-> bizTools.listPresentees(presenteeIDSet));
                Future repostFuture = tp.startTast(()-> bizTools.listReposts(repostIDSet));
                Future appFuture = tp.startTast(()-> bizTools.listApps(appIDSet));
                Future candidateFuture = tp.startTast(()-> bizTools.listCandidatePositionsByPositionIDUserID(cps));

                List<JobPositionDO> positions = (List<JobPositionDO>) psotionFuture.get();
                List<UserUserDO> presentees = (List<UserUserDO>) presenteeFuture.get();
                List<UserUserDO> reposts = (List<UserUserDO>) repostFuture.get();
                List<JobApplicationDO> apps = (List<JobApplicationDO>) appFuture.get();
                List<CandidatePositionDO> candidatePositionDOList = (List<CandidatePositionDO>) candidateFuture.get();

                Set<Integer> rejectAppIdSet = new HashSet<>();
                if(apps != null && apps.size() > 0) {
                    apps.stream().filter(app -> app.getAppTplId() == 4).forEach(app -> rejectAppIdSet.add(app.getId()));
                }

                List<HrOperationRecordDO> operationList =  bizTools.listLastHrOperationRecordPassedReject(rejectAppIdSet);
                List<RecommendationRecordVO> recommendationRecordVOList = new ArrayList<>();

                /** 生成RecommendationRecordVO记录 */
                recomRecordDOList.forEach(candidateRecomRecordDO -> {
                    RecommendationRecordVO recommendationRecordVO = new RecommendationRecordVO();

                    recommendationRecordVO.setId(candidateRecomRecordDO.getId());
                    recommendationRecordVO.setClick_time(candidateRecomRecordDO.getClickTime());
                    recommendationRecordVO.setRecom_status((byte)candidateRecomRecordDO.getIsRecom());

                    logger.info("getRecommendations recommendationRecordVO id:{}, click_time:{}, recom_status:{}", recommendationRecordVO.getId(), recommendationRecordVO.getClick_time(), recommendationRecordVO.getRecom_status());

                    /** 匹配职位名称 */
                    if (positions != null && positions.size() > 0) {
                        positions.stream().filter(position -> position.getId() == candidateRecomRecordDO.getPositionId() && position.getId() > 0)
                                .forEach(position -> {
                                    recommendationRecordVO.setPosition(position.getTitle());
                                });
                    }
                    /** 匹配浏览者的编号、名称以及头像 */
                    if (presentees != null && presentees.size() > 0) {
                        presentees.stream().filter(presentee -> presentee.getId() == candidateRecomRecordDO.getPresenteeUserId())
                                .forEach(presentee -> {
                                    recommendationRecordVO.setApplier_name(StringUtils.isNotNullOrEmpty(presentee.getName()) ? presentee.getName() : presentee.getNickname());
                                    recommendationRecordVO.setHeadimgurl(presentee.getHeadimg());
                                });
                    }
                    /** 匹配转发者的名称 */
                    if (candidateRecomRecordDO.getPostUserId() != candidateRecomRecordDO.getRepostUserId()) {
                        logger.info("getRecommendations postUserId:{}, rePostUserId:{}", candidateRecomRecordDO.getPostUserId(), candidateRecomRecordDO.getRepostUserId());
                        if (reposts != null && reposts.size() > 0) {
                            reposts.stream().filter(repost -> repost.getId() == candidateRecomRecordDO.getRepostUserId())
                                    .forEach(repost ->
                                            recommendationRecordVO.setApplier_rel(StringUtils.isNotNullOrEmpty(repost.getName())
                                                    ? repost.getName() : repost.getNickname()));
                        }
                    }

                    /** 计算招聘进度 */
                    recommendationRecordVO.setStatus((short)0);
                    if(apps != null && apps.size() > 0) {
                        apps.stream().filter(app -> app.getId() == candidateRecomRecordDO.getAppId()).forEach(app -> {
                            RecruitmentScheduleEnum recruitmentScheduleEnum = RecruitmentScheduleEnum.createFromID(app.getAppTplId());
                            if(recruitmentScheduleEnum != null && operationList != null && operationList.size() > 0) {
                                Optional<HrOperationRecordDO> oprationOP = operationList.stream().filter(operation -> operation.getAppId() == app.getId()).findFirst();
                                if(oprationOP.isPresent()) {
                                    try {
                                        recruitmentScheduleEnum.setLastStep(oprationOP.get().getOperateTplId());
                                    } catch (RecruitmentScheduleLastStepNotExistException e) { //设置了一个意外的上衣进度的状态值，上衣状态值调整成0
                                        logger.error(e.getMessage(), e);
                                    }
                                }
                            }
                            //生成招聘进度状态 form.setStatus()

                            recommendationRecordVO.setStatus((short) recruitmentScheduleEnum.getStatusForRecommendationInPersonalCenter());
                        });
                    }
                    recommendationRecordVO.setIs_interested((byte)0);
                    recommendationRecordVO.setView_number(0);
                    if(candidatePositionDOList != null && candidatePositionDOList.size() > 0) {
                        candidatePositionDOList.stream()
                                .filter(candidatePosition ->
                                        candidatePosition.getPositionId() == candidateRecomRecordDO.getPositionId()
                                                && candidatePosition.getUserId() == candidateRecomRecordDO.getPresenteeUserId())
                                .forEach(candidatePosition -> {
                                    recommendationRecordVO.setIs_interested(candidatePosition.getIsInterested());
                                    recommendationRecordVO.setView_number(candidatePosition.getViewNumber());
                                });
                    }
                    recommendationRecordVOList.add(recommendationRecordVO);

                });
                recommendationForm.setRecommends(recommendationRecordVOList);
            }
        } catch (CommonException e) {
            logger.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        } catch (ExecutionException e) {
            logger.error(e.getMessage(), e);
        }
        return recommendationForm;
    }

    public ApplicationDetailVO getApplicationDetail(int userId, int appId) throws CommonException {
        logger.info("--------getApplicationDetail---------");
        logger.info("params   userId:{}, appId:{}", userId, appId);
        ApplicationDetailVO applicationDetailVO = new ApplicationDetailVO();

        if(userId > 0 && appId > 0) {
            try {
                JobApplicationDO applicationDO = bizTools.getApplication(appId);
                if (applicationDO.getApplierId() != userId) {
                    throw UserAccountException.NO_PERMISSION_EXCEPTION;
                }
                logger.info("applicationDO:{}",applicationDO);
                if(applicationDO != null && applicationDO.getId() > 0) {
                    applicationDetailVO.setPid(applicationDO.getPositionId());
                    //查找申请记录
                    RecruitmentScheduleEnum recruitmentScheduleEnum = RecruitmentScheduleEnum.createFromID(applicationDO.getAppTplId());

                    /** 并行查询公司、职位、HR操作记录信息*/
                    ThreadPool tp = ThreadPool.Instance;
                    Future positionDOFuture = tp.startTast(() -> bizTools.getPosition(applicationDO.getPositionId()));
                    Future companyDOFuture = tp.startTast(() -> bizTools.getCompany(applicationDO.getCompanyId()));
                    Future operationFuture = null;
                    if(recruitmentScheduleEnum.getId() == RecruitmentScheduleEnum.REJECT.getId()) {
                        operationFuture = tp.startTast(() -> bizTools.listLastHrOperationRecordPassedReject(new HashSet<Integer>() {
                            {
                                add(applicationDO.getId());
                            }
                        }));
                    }

                    Future<UserEmployeeDO> employeeDOFuture = tp.startTast(() -> employeeEntity.getActiveEmployeeDOByUserId(applicationDO.getRecommenderUserId()));
                    UserEmployeeDO employeeDO = null;
                    try {
                        employeeDO = employeeDOFuture.get();
                        if (employeeDO != null && org.apache.commons.lang.StringUtils.isBlank(employeeDOFuture.get().getCname())) {
                            UserUserDO userUserDO = userUserDao.getUser(employeeDO.getSysuserId());
                            if (userUserDO != null) {
                                employeeDO.setCname(org.apache.commons.lang.StringUtils.isNotBlank(userUserDO.getName())
                                        ? userUserDO.getName() : userUserDO.getNickname());
                            }
                            if (org.apache.commons.lang.StringUtils.isBlank(employeeDO.getCname())) {
                                UserWxUserRecord record = wxUserDao.getWXUserByUserId(userId);
                                if (record != null) {
                                    employeeDO.setCname(record.getNickname());
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }

                    /** 查找HR操作记录 */
                    Future timeLineListFuture = tp.startTast(() -> bizTools.listHrOperationRecord(appId));
                    try {
                        JobPositionDO positionDO = (JobPositionDO)positionDOFuture.get();
                        if(positionDO != null) {
                            logger.info("title:{}", positionDO.getTitle());
                            applicationDetailVO.setPosition_title(positionDO.getTitle());
                        }
                        HrCompanyDO companyDO = (HrCompanyDO) companyDOFuture.get();
                        if(companyDO != null){
                            if(StringUtils.isNotNullOrEmpty(companyDO.getName())) {
                                applicationDetailVO.setCompany_name(companyDO.getName());
                            } else {
                                applicationDetailVO.setCompany_name(companyDO.getAbbreviation());
                            }
                            logger.info("company_name:{}", companyDO.getName());
                        }
                        applicationDetailVO.setStep_status((byte) recruitmentScheduleEnum.getStepStatusForApplicationDetail((byte) applicationDO.getEmailStatus()));
                        applicationDetailVO.setStep((byte) recruitmentScheduleEnum.getStepForApplicationDetail());
                        if(operationFuture != null) {
                            List<HrOperationRecordDO> operationrecordDOList = (List<HrOperationRecordDO>)operationFuture.get();
                            if(operationrecordDOList != null && operationrecordDOList.size() > 0) {
                                HrOperationRecordDO operationRecordDO = operationrecordDOList.get(0);

                                recruitmentScheduleEnum.setLastStep(operationRecordDO.getOperateTplId());
                                applicationDetailVO.setStep_status((byte) recruitmentScheduleEnum.getStepStatusForApplicationDetail((byte) applicationDO.getEmailStatus()));
                                applicationDetailVO.setStep((byte) recruitmentScheduleEnum.getStepForApplicationDetail());
                            }
                        }
                        List<HrOperationRecordDO> operationrecordDOList = (List<HrOperationRecordDO>) timeLineListFuture.get();
                        if(operationrecordDOList != null && operationrecordDOList.size() > 0) {
                            logger.info("operationrecordDOList : {}", operationrecordDOList);
                            List<ApplicationOperationRecordVO> applicationOperationRecordVOList = new ArrayList<>();
                            Iterator<HrOperationRecordDO> it = operationrecordDOList.iterator();
                            int applyCount = 0;         //只显示第一条投递操作
                            int count = 0;
                            while(it.hasNext()) {
                                HrOperationRecordDO oprationRecord = it.next();
                                if(filterTimeLine(oprationRecord.getOperateTplId())) {
                                    continue;
                                }
                                ApplicationOperationRecordVO applicationOprationRecordVO = new ApplicationOperationRecordVO();
                                applicationOprationRecordVO.setHide(0);
                                applicationOprationRecordVO.setDate(oprationRecord.getOptTime());
                                if(oprationRecord.getOperateTplId() == RecruitmentScheduleEnum.REJECT.getId()) {
                                    applicationOprationRecordVO.setStep_status(2);
                                }
                                int preID = 0;
                                if(count > 0) {
                                    int j = count-1;
                                    while(operationrecordDOList.get(j).getOperateTplId() == RecruitmentScheduleEnum.REJECT.getId() && j > 0) {
                                        j--;
                                    }
                                    logger.info("preID j:{} count:{}", j, count);
                                    if(operationrecordDOList.get(j).getOperateTplId() != RecruitmentScheduleEnum.REJECT.getId()) {
                                        preID = operationrecordDOList.get(j).getOperateTplId();
                                    }
                                    logger.info("preID :{}", preID);
                                }
                                logger.info("UserCenterService getApplicationDetail oprationRecord: {}", oprationRecord);
                                RecruitmentScheduleEnum recruitmentScheduleEnum1 = RecruitmentScheduleEnum.createFromID(oprationRecord.getOperateTplId());
                                logger.info("UserCenterService getApplicationDetail recruitmentScheduleEnum1: {}", recruitmentScheduleEnum1);
                                if (recruitmentScheduleEnum1 != null) {
                                    applicationOprationRecordVO.setEvent(recruitmentScheduleEnum1.getAppStatusDescription((byte) applicationDO.getApplyType(), (byte) applicationDO.getEmailStatus(), preID, employeeDO != null? employeeDO.getCname():""));
                                }
                                /** 如果前一条操作记录也是拒绝的操作记录，那么这一条操作记录隐藏 */
                                if(recruitmentScheduleEnum.getId() == RecruitmentScheduleEnum.REJECT.getId()
                                        && recruitmentScheduleEnum.getLastID() == RecruitmentScheduleEnum.REJECT.getId()) {
                                    applicationOprationRecordVO.setHide(1);
                                }
                                /** HR操作记录中，只显示第一条投递成功的操作记录。其余的全部隐藏 */
                                if(oprationRecord.getOperateTplId() == RecruitmentScheduleEnum.APPLY.getId()) {
                                    if(applyCount > 1) {
                                        applicationOprationRecordVO.setHide(1);
                                    }
                                    applyCount ++;
                                }
                                logger.info("UserCenterService getApplicationDetail applicationOprationRecordVO : {}", applicationOprationRecordVO);
                                applicationOperationRecordVOList.add(applicationOprationRecordVO);
                                count ++;
                            }
                            applicationDetailVO.setStatus_timeline(applicationOperationRecordVOList);
                        }

                    } catch (InterruptedException e) {
                        logger.error(e.getMessage(), e);
                    } catch (ExecutionException e) {
                        logger.error(e.getMessage(), e);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }

                }
            } catch (CommonException e) {
                logger.warn(e.getMessage(), e);
            }
        }
        logger.info("getApplicationDetail:"+applicationDetailVO);
        return applicationDetailVO;
    }

    /**
     * 查找积分
     *
     * @param userId
     * @return
     * @throws CommonException
     */
    public List<AwardRecordForm> getAwardRecords(int userId) throws CommonException {
        List<AwardRecordForm> awards = new ArrayList<>();

        //参数校验
        if (userId > 0) {

        }
        //判断用户是否是员工
        //查找积分记录
        //查找申请
        //查找职位
        //封装数据

        return awards;
    }


    public boolean isEmployee(int userId) throws CommonException {
        boolean flag = false;

        return flag;
    }

    public UserCenterInfoVO getCenterUserInfo(int userId, int companyId) throws UserAccountException {
        UserUserDO userUserDO = userUserDao.getUser(userId);
        if (userUserDO == null) {
            throw UserAccountException.ERMPLOYEE_REFERRAL_USER_NOT_EXIST;
        }
        UserCenterInfoVO info = new UserCenterInfoVO();
        info.setUserId(userId);
        info.setHeadimg(userUserDO.getHeadimg());
        info.setName(org.apache.commons.lang.StringUtils.isNotBlank(userUserDO.getName())
                ? userUserDO.getName():userUserDO.getNickname());
        UserEmployeeDO employeeDO = employeeEntity.getCompanyEmployee(userId, companyId);
        if (employeeDO != null) {
            info.setEmployeeId(employeeDO.getId());
            if (org.apache.commons.lang.StringUtils.isNotBlank(employeeDO.getCname())) {
                info.setName(employeeDO.getCname());
            }
        }
        if (org.apache.commons.lang.StringUtils.isBlank(info.getName())
                || org.apache.commons.lang.StringUtils.isBlank(info.getHeadimg())) {
            UserWxUserRecord record = wxUserDao.getWXUserByUserId(userId);
            if (record != null) {
                if (org.apache.commons.lang.StringUtils.isBlank(info.getHeadimg())) {
                    info.setHeadimg(record.getHeadimgurl());
                }
                if (org.apache.commons.lang.StringUtils.isBlank(info.getName())) {
                    info.setName(record.getNickname());
                }
            }
        }
        return info;
    }

    /**
     * 功能描述 : 如果是这几个状态，在申请进度中不显示
     *          2 面试未反馈 5 面试已反馈 8 面试未反馈 9 面试未反馈 11 待入职
     * @author : JackYang
     * @date : 2019-03-29 14:47
     * @param operateTplId :
     * @return : boolean
     */
    private boolean filterTimeLine(Integer operateTplId) {
        List<Integer>  appTplIds = Lists.newArrayList(2,5,8,9,11);
        return appTplIds.contains(operateTplId);
    }
}
