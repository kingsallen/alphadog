package com.moseeker.useraccounts.service.impl;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.biztools.ApplyType;
import com.moseeker.common.biztools.EmailStatus;
import com.moseeker.common.biztools.RecruitmentScheduleEnum;
import com.moseeker.common.exception.RecruitmentScheduleLastStepNotExistException;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.dao.struct.*;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import com.moseeker.useraccounts.service.impl.biztools.UserCenterBizTools;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

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

    /**
     * 查询申请记录
     *
     * @param userId 用户编号
     * @return 申请记录
     * @throws TException
     */
    public List<ApplicationRecordsForm> getApplication(int userId) throws TException {
        List<ApplicationRecordsForm> applications = new ArrayList<>();

        //参数有效性校验
        if (userId > 0) {
            //查询申请记录
            List<JobApplicationDO> apps = bizTools.getAppsForUser(userId);
            if (apps != null && apps.size() > 0) {
                //查询申请记录对应的职位数据
                List<JobPositionDO> positions = bizTools.getPositions(apps.stream().mapToInt(app -> app.getPositionId()).toArray());
                List<Hrcompany> companies = bizTools.getCompanies(apps.stream().mapToInt(app -> app.getCompanyId()).toArray());
                List<HrOperationRecordDO> operationRecordDOList = bizTools.listLastHrOperationRecordPassedReject(apps.stream().map(app -> app.getAppTplId()).collect(Collectors.toSet()));
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
                    ar.setStatus_name(recruitmentScheduleEnum.getAppStatusDescription(app.getApplyType(), app.getEmailStatus(), preID));

                    return ar;
                }).collect(Collectors.toList());
            }
        } else {
            //do nothing
        }
        if(applications.size() > 0) {
            applications.forEach(application -> {
                logger.info("ApplicationRecordsForm:"+application);
            });
        }
        return applications;
    }

    /**
     * 查询用户收藏的职位
     *
     * @param userId 用户编号
     * @return 收藏的职位集合
     * @throws TException
     */
    public List<FavPositionForm> getFavPositions(int userId) throws TException {
        List<FavPositionForm> favPositions = new ArrayList<>();
        //参数校验
        if (userId > 0) {
            //查询用户的收藏职位列表
            List<UserFavPositionDO> favPositionRecords = bizTools.getFavPositions(userId, 0);
            if (favPositionRecords != null && favPositionRecords.size() > 0) {
                //差用用户收藏职位的职位详情
                List<JobPositionDO> positions = bizTools.getPositions(favPositionRecords.stream().mapToInt(favP -> favP.getPositionId()).toArray());
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
                                    form.setTime(record.getUpdate_time());
                                    form.setCity(op.get().getCity());
                                    form.setSalary_top(op.get().getSalaryTop());
                                    form.setSalary_bottom(op.get().getSalaryBottom());
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

    /**
     * 查询历史推荐记录
     *
     * @param userId 用户编号
     * @return 历史推荐记录
     * @throws TException thrift异常类
     */
    @SuppressWarnings("unchecked")
    public RecommendationVO getRecommendations(int userId, byte type, int pageNo, int pageSize) throws TException {
        RecommendationVO recommendationForm = new RecommendationVO();
        try {

            ThreadPool tp = ThreadPool.Instance;
            int totalCount = 0;             //转发记录总数
            int interestedCount = 0;         //被推荐的转发记录数
            int applyCount = 0;             //有过申请的转发记录数
            /** 并行查找三个统计信息 */
            Future<Integer> totalCountFuture = tp.startTast(() -> bizTools.countCandidateRecomRecord(userId), totalCount);
            Future<Integer> interestedCountFuture = tp.startTast(() -> bizTools.countInterestedCandidateRecomRecord(userId), interestedCount);
            Future<Integer> applyCountFuture = tp.startTast(() -> bizTools.countAppliedCandidateRecomRecord(userId), applyCount);
            totalCount = totalCountFuture.get();
            interestedCount = interestedCountFuture.get();
            applyCount = applyCountFuture.get();
            RecommendationScoreVO scoreVO = new RecommendationScoreVO();
            scoreVO.setApplied_count(applyCount);
            scoreVO.setInterested_count(interestedCount);
            scoreVO.setLink_viewed_count(totalCount);
            recommendationForm.setScore(scoreVO);

            /** 分页查找相关职位转发记录 */
            List<CandidateRecomRecordDO> recomRecordDOList = bizTools.listCandidateRecomRecords(userId, type, pageNo, pageSize);
            if (recomRecordDOList.size() > 0) {
                recommendationForm.setHasRecommends(true);

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

                    recommendationRecordVO.setClick_time(candidateRecomRecordDO.getClickTime());
                    recommendationRecordVO.setRecom_status(candidateRecomRecordDO.getIsRecom());

                    /** 匹配职位名称 */
                    if (positions != null && positions.size() > 0) {
                        positions.stream().filter(position -> position.getId() == candidateRecomRecordDO.getPositionId())
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
                    if (reposts != null && reposts.size() > 0) {
                        reposts.stream().filter(repost -> repost.getId() == candidateRecomRecordDO.getRepostUserId())
                                .forEach(repost ->
                                        recommendationRecordVO.setApplier_rel(StringUtils.isNotNullOrEmpty(repost.getName())
                                                ? repost.getName() : repost.getNickname()));
                    }
                    /** 计算招聘进度 */
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
                    if(candidatePositionDOList != null && candidatePositionDOList.size() > 0) {
                        candidatePositionDOList.stream()
                                .filter(candidatePosition ->
                                        candidatePosition.getPositionId() == candidateRecomRecordDO.getPositionId()
                                                && candidatePosition.getUserId() == candidateRecomRecordDO.getPresenteeUserId())
                                .forEach(candidatePosition -> {
                                    recommendationRecordVO.setIs_interested(candidatePosition.isIsInterested()?(byte)1:0);
                                    recommendationRecordVO.setView_number(candidatePosition.getViewNumber());
                                });
                    }
                    recommendationRecordVOList.add(recommendationRecordVO);

                });
            } else {
                recommendationForm.setHasRecommends(false);
            }
        } catch (TException e) {
            logger.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return recommendationForm;
    }

    public ApplicationDetailVO getApplicationDetail(int userId, int appId) {
        logger.info("--------getApplicationDetail---------");
        logger.info("params   userId:{}, appId:{}", userId, appId);
        ApplicationDetailVO applicationDetailVO = new ApplicationDetailVO();

        if(userId > 0 && appId > 0) {
            try {
                JobApplicationDO applicationDO = bizTools.getApplication(appId);
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
                    /** 查找HR操作记录 */
                    Future timeLineListFuture = tp.startTast(() -> bizTools.listHrOperationRecord(appId));
                    try {
                        JobPositionDO positionDO = (JobPositionDO)positionDOFuture.get();
                        if(positionDO != null) {
                            logger.info("title:{}", positionDO.getTitle());
                            applicationDetailVO.setPosition_title(positionDO.getTitle());
                        }
                        HrCompanyDO companyDO = (HrCompanyDO) companyDOFuture.get();
                        if(companyDO != null) {
                            if(StringUtils.isNotNullOrEmpty(companyDO.getName())) {
                                applicationDetailVO.setCompany_name(companyDO.getName());
                            } else {
                                applicationDetailVO.setCompany_name(companyDO.getAbbreviation());
                            }
                            logger.info("company_name:{}", companyDO.getName());
                        }

                        if(operationFuture != null) {
                            List<HrOperationRecordDO> operationrecordDOList = (List<HrOperationRecordDO>)operationFuture.get();
                            if(operationrecordDOList != null && operationrecordDOList.size() > 0) {
                                HrOperationRecordDO operationRecordDO = operationrecordDOList.get(0);

                                recruitmentScheduleEnum.setLastStep(operationRecordDO.getOperateTplId());
                                applicationDetailVO.setStep_status((byte) recruitmentScheduleEnum.getStepStatusForApplicationDetail());
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
                                ApplicationOperationRecordVO applicationOprationRecordVO = new ApplicationOperationRecordVO();
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
                                RecruitmentScheduleEnum recruitmentScheduleEnum1 = RecruitmentScheduleEnum.createFromID(oprationRecord.getOperateTplId());
                                applicationOprationRecordVO.setEvent(recruitmentScheduleEnum1.getAppStatusDescription(applicationDO.getApplyType(), applicationDO.getEmailStatus(), preID));
                                /** 如果投递是Email投递， */
                                if(applicationDO.getApplyType() == ApplyType.EMAIL.getValue()
                                        && applicationDO.getEmailStatus() != EmailStatus.NOMAIL.getValue()
                                        && applicationDO.getAppTplId() == RecruitmentScheduleEnum.APPLY.getId()) {
                                    applicationOprationRecordVO.setHide(1);
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
                                applicationOperationRecordVOList.add(applicationOprationRecordVO);
                                count ++;
                            }
                            applicationDetailVO.setStatus_timeline(applicationOperationRecordVOList);
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }
            } catch (TException e) {
                logger.error(e.getMessage(), e);
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
     * @throws TException
     */
    public List<AwardRecordForm> getAwardRecords(int userId) throws TException {
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


    public boolean isEmployee(int userId) throws TException {
        boolean flag = false;

        return flag;
    }
}