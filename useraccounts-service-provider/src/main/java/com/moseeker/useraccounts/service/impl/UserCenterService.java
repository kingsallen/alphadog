package com.moseeker.useraccounts.service.impl;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.biztools.RecruitmentScheduleEnum;
import com.moseeker.common.exception.RecruitmentScheduleLastStepNotExistException;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.dao.struct.*;
import com.moseeker.thrift.gen.useraccounts.struct.ApplicationRecordsForm;
import com.moseeker.thrift.gen.useraccounts.struct.AwardRecordForm;
import com.moseeker.thrift.gen.useraccounts.struct.FavPositionForm;
import com.moseeker.thrift.gen.useraccounts.struct.RecommendationForm;
import com.moseeker.useraccounts.service.impl.biztools.ApplyType;
import com.moseeker.useraccounts.service.impl.biztools.EmailStatus;
import com.moseeker.useraccounts.service.impl.biztools.UserCenterBizTools;
import com.moseeker.useraccounts.service.impl.pojos.ApplicationDetailVO;
import com.moseeker.useraccounts.service.impl.pojos.ApplicationOperationRecordVO;
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
                List<JobPositionDO> positions = bizTools.getPositions(apps.stream().mapToInt(app -> (int) app.getPositionId()).toArray());
                List<Hrcompany> companies = bizTools.getCompanies(apps.stream().mapToInt(app -> (int) app.getCompanyId()).toArray());
                List<ConfigSysPointConfTplDO> tpls = bizTools.getAwardConfigTpls();

                applications = apps.stream().map(app -> {
                    ApplicationRecordsForm ar = new ApplicationRecordsForm();
                    ar.setId((int) app.getId());
                    ar.setStatus((byte) app.getAppTplId());
                    ar.setTime(app.getCreateTime());
                    if (positions != null) {
                        Optional<JobPositionDO> op = positions.stream().filter(position -> position.getId() == app.getPositionId()).findFirst();
                        if (op.isPresent()) {
                            ar.setTitle(op.get().getTitle());
                        }
                    }
                    if (companies != null) {
                        Optional<Hrcompany> op = companies.stream().filter(company -> company.getId() == app.getCompanyId()).findFirst();
                        if (op.isPresent()) {
                            ar.setDepartment(op.get().getAbbreviation());
                        }
                    }

                    if (tpls != null) {
                        Optional<ConfigSysPointConfTplDO> op = tpls.stream().filter(tpl -> tpl.getId() == app.getAppTplId()).findFirst();
                        if (op.isPresent()) {
                            ar.setStatus((byte) op.get().getRecruitOrder());
                        }
                    }

                    return ar;
                }).collect(Collectors.toList());
            }
        } else {
            //do nothing
        }

        return applications;
    }

    /**
     * 查询职位收藏
     *
     * @param userId 用户编号
     * @return 感兴趣职位集合
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
    public List<RecommendationForm> listRecommendations(int userId, int pageNo, int pageSize) throws TException {
        List<RecommendationForm> forms = new ArrayList<>();
        try {
            List<CandidateRecomRecordDO> recomRecordDOList = bizTools.listCandidateRecomRecords(userId, pageNo, pageSize);
            if (recomRecordDOList.size() > 0) {

                Set<Integer> positionIDSet = new HashSet<>();
                Set<Integer> presenteeIDSet = new HashSet<>();
                Set<Integer> repostIDSet = new HashSet<>();
                Set<Integer> appIDSet = new HashSet<>();
                List<Map<Integer, Integer>> cps = new ArrayList<>();

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
                ThreadPool tp = ThreadPool.Instance;
                Future psotionFuture = tp.startTast(()-> bizTools.listJobPositions(positionIDSet), List.class);
                Future presenteeFuture = tp.startTast(()-> bizTools.listPresentees(presenteeIDSet), List.class);
                Future repostFuture = tp.startTast(()-> bizTools.listReposts(repostIDSet), List.class);
                Future appFuture = tp.startTast(()-> bizTools.listApps(appIDSet), List.class);
                Future candidateFuture = tp.startTast(()-> bizTools.listCandidatePositionsByPositionIDUserID(cps), List.class);

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

                recomRecordDOList.forEach(candidateRecomRecordDO -> {
                    RecommendationForm form = new RecommendationForm();

                    form.setApp_id(candidateRecomRecordDO.getAppId());
                    form.setClick_time(candidateRecomRecordDO.getClickTime());
                    form.setRecom_time(candidateRecomRecordDO.getRecomTime());
                    form.setRecom_status(candidateRecomRecordDO.getIsRecom());
                    form.setCandidate_recom_record_id(candidateRecomRecordDO.getId());

                    if (positions != null && positions.size() > 0) {
                        positions.stream().filter(position -> position.getId() == candidateRecomRecordDO.getPositionId())
                                .forEach(position -> {
                            form.setPosition(position.getTitle());
                        });
                    }
                    if (presentees != null && presentees.size() > 0) {
                        presentees.stream().filter(presentee -> presentee.getId() == candidateRecomRecordDO.getPresenteeUserId())
                                .forEach(presentee -> {
                            form.setApplier_id(presentee.getId());
                            form.setApplier_name(StringUtils.isNotNullOrEmpty(presentee.getName()) ? presentee.getName() : presentee.getNickname());
                            form.setHeadimgurl(presentee.getHeadimg());
                        });
                    }
                    if (reposts != null && reposts.size() > 0) {
                        reposts.stream().filter(repost -> repost.getId() == candidateRecomRecordDO.getRepostUserId())
                                .forEach(repost ->
                                        form.setRecom_2nd_nickname(StringUtils.isNotNullOrEmpty(repost.getName())
                                                ? repost.getName() : repost.getNickname()));
                    }
                    if(apps != null && apps.size() > 0) {
                        apps.stream().filter(app -> app.getId() == candidateRecomRecordDO.getAppId()).forEach(app -> {
                            form.setApp_time(app.getSubmitTime());
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

                            form.setStatus((short) recruitmentScheduleEnum.getStatusForRecommendationInPersonalCenter());
                        });
                    }
                    if(candidatePositionDOList != null && candidatePositionDOList.size() > 0) {
                        candidatePositionDOList.stream()
                                .filter(candidatePosition ->
                                        candidatePosition.getPositionId() == candidateRecomRecordDO.getPositionId()
                                                && candidatePosition.getUserId() == candidateRecomRecordDO.getPresenteeUserId())
                                .forEach(candidatePosition -> {
                                    form.setIs_interested(candidatePosition.isIsInterested()?(byte)1:0);
                                    form.setView_number(candidatePosition.getViewNumber());
                                });
                    }
                    forms.add(form);

                });
            }
        } catch (TException e) {
            logger.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return forms;
    }

    public ApplicationDetailVO getApplicationDetail(int userId, int appId) {

        ApplicationDetailVO applicationDetailVO = new ApplicationDetailVO();

        if(userId > 0 && appId > 0) {
            try {
                JobApplicationDO applicationDO = bizTools.getApplication(appId);
                if(applicationDO != null && applicationDO.getId() > 0) {
                    applicationDetailVO.setPid(appId);
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
                            applicationDetailVO.setPosition_title(positionDO.getTitle());
                        }
                        HrCompanyDO companyDO = (HrCompanyDO) companyDOFuture.get();
                        if(companyDO != null) {
                            if(StringUtils.isNotNullOrEmpty(companyDO.getName())) {
                                applicationDetailVO.setCompany_name(companyDO.getName());
                            } else {
                                applicationDetailVO.setCompany_name(companyDO.getAbbreviation());
                            }
                        }

                        if(operationFuture != null) {
                            List<HrOperationRecordDO> operationrecordDOList = (List<HrOperationRecordDO>)operationFuture.get();
                            if(operationrecordDOList != null && operationrecordDOList.size() > 0) {
                                HrOperationRecordDO operationRecordDO = operationrecordDOList.get(0);
                                recruitmentScheduleEnum.setLastStep(operationRecordDO.getOperateTplId());
                                applicationDetailVO.setStep_status(recruitmentScheduleEnum.getStepStatusForApplicationDetail());
                                applicationDetailVO.setStep(recruitmentScheduleEnum.getStepForApplicationDetail());
                            }
                        }
                        List<HrOperationRecordDO> operationrecordDOList = (List<HrOperationRecordDO>) timeLineListFuture.get();
                        if(operationrecordDOList != null && operationrecordDOList.size() > 0) {
                            Iterator<HrOperationRecordDO> it = operationrecordDOList.iterator();
                            int applyCount = 0;         //只显示第一条投递操作
                            while(it.hasNext()) {
                                HrOperationRecordDO oprationRecord = it.next();
                                ApplicationOperationRecordVO applicationOprationRecordVO = new ApplicationOperationRecordVO();
                                applicationOprationRecordVO.setDate(oprationRecord.getOptTime());
                                if(oprationRecord.getOperateTplId() == RecruitmentScheduleEnum.REJECT.getId()) {
                                    applicationOprationRecordVO.setStep_status(2);
                                }
                                /** 如果上一条是拒绝，这一条是其他操作记录，那么现实"HR将您纳入候选名单" */
                                if(recruitmentScheduleEnum.getId() != RecruitmentScheduleEnum.REJECT.getId()
                                        && recruitmentScheduleEnum.getLastID() == RecruitmentScheduleEnum.REJECT.getId()) {
                                    applicationOprationRecordVO.setEvent("HR将您纳入候选名单");
                                }
                                /** 如果投递时邮件投递，并且投递状态是成功投递 */
                                if (applicationDO.getApplyType() == ApplyType.EMAIL.getValue()){
                                    if(applicationDO.getAppTplId() == RecruitmentScheduleEnum.APPLY.getId()) {
                                        switch (applicationDO.getEmailStatus()) {
                                            case 1: applicationOprationRecordVO.setEvent(EmailStatus.NOT_ANSWER_EMAIL.getMessage());break;
                                            case 2: applicationOprationRecordVO.setEvent(EmailStatus.ATTACHMENT_NOT_SUPPORT.getMessage());break;
                                            case 3: applicationOprationRecordVO.setEvent(EmailStatus.ATTACHMENT_MORE_THEN_MAXIMUN.getMessage());break;
                                            case 8: applicationOprationRecordVO.setEvent(EmailStatus.MAIL_NOT_FOUND.getMessage());break;
                                            case 9: applicationOprationRecordVO.setEvent(EmailStatus.MAIL_PARSING_FAILED.getMessage());break;
                                        }
                                    }
                                }
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
                            }
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
