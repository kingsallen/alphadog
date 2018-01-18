package com.moseeker.application.context;

import com.moseeker.application.context.agreegateid.ViewApplicationAggregateId;
import com.moseeker.application.domain.ApplicationEntity;
import com.moseeker.application.domain.HREntity;
import com.moseeker.application.domain.constant.ApplicationViewStatus;
import com.moseeker.application.domain.pojo.Application;
import com.moseeker.application.exception.ApplicationException;
import com.moseeker.application.infrastructure.DaoManagement;
import com.moseeker.baseorm.config.HRAccountType;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserHrAccount;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.validation.ValidateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 申请模块，承接应用层接口，组装领域模型。
 * Created by jack on 18/01/2018.
 */
@Component
public class ApplyContext {

    @Autowired
    DaoManagement daoManagement;

    @Autowired
    ApplicationContext applicationContext;

    /**
     * HR查看申请
     * 查看申请的是为了处理HR查看，导出，下载某一个申请人信息时，将申请人对应的“是否查看申请”（是否查看的标记是在申请记录上，
     * 但是业务逻辑做过改版，现有的业务逻辑应该在申请人上比较合适）标记为“已经查看”状态。
     * 查看申请时需要权限的，主账号可以查看公司下的任何简历，子账号只能查看自己发布职位收到的申请人的简历。
     * 查看简历还会触发发送模板消息和积分添加。如果申请有推荐人，并且该公司存在公司积分配置，
     * 那么会对推荐人添加对应积分。HR查看申请 对推荐人（申请是员工转发的候选人投递的，
     * 那么员工就是该申请的推荐人）添加对应积分（需要公司配置了该操作的积分项）。
     * @param viewApplicationAggregateId 参数
     * @throws CommonException 业务异常
     */
    @Transactional
    @CounterIface
    public void hrViewApplications(ViewApplicationAggregateId viewApplicationAggregateId) throws CommonException {

        //参数校验
        validateParameterBeforeViewApplication(viewApplicationAggregateId);

        //理论上获取用户信息应该访问用户服务
        UserHrAccount userHrAccount = daoManagement.getUserHrAccountDao().fetchActiveHRByID(viewApplicationAggregateId.getHrId());
        if (userHrAccount == null) {
            throw ApplicationException.APPLICATION_HR_ILLEGAL;
        }

        HRAccountType hrAccountType = HRAccountType.initFromType(userHrAccount.getAccountType());
        if (hrAccountType == null) {
            throw ApplicationException.APPLICATION_HR_ACCOUT_TYPE_ILLEGAL;
        }

        List<JobApplication> applicationList = daoManagement.getJobApplicationDao()
                .fetchActiveApplicationByIdList(viewApplicationAggregateId.getApplicationIds());
        if (applicationList == null || applicationList.size() == 0) {
            throw ApplicationException.APPLICATION_APPLICATION_ELLEGAL;
        }

        //查找申请和HR的关系--通过职位信息
        List<JobPosition> positionList = daoManagement.getPositionJOOQDao()
                .fetchPublisherByAppIds(applicationList
                        .stream()
                        .map(jobApplication -> jobApplication.getPositionId())
                        .collect(Collectors.toList()));

        final int supperAccountId;
        if (hrAccountType.equals(HRAccountType.SupperAccount)) {
            supperAccountId = userHrAccount.getId();
        } else {
            supperAccountId = daoManagement.getUserHrAccountDao().fetchSuperAccountIdByCompanyId(userHrAccount.getCompanyId());
        }

        //生成被查看的申请记录
        List<Application> applications = applicationList.stream().map(jobApplication -> {
            Application application = new Application();
            application.setStatus(jobApplication.getAppTplId());
            if (jobApplication.getIsViewed() != null
                    && jobApplication.getIsViewed() == ApplicationViewStatus.VIEWED.getStatus()) {
                application.setViewed(true);
            } else {
                application.setViewed(false);
            }
            if (positionList != null && positionList.size() > 0) {
                Optional<JobPosition> jobPositionOptional = positionList
                        .stream()
                        .filter(jobPosition -> jobApplication.getPositionId().intValue()
                                == jobPosition.getId()).findAny();
                List<Integer> hrIdList = new ArrayList<>();
                if (jobPositionOptional.isPresent()) {
                    hrIdList.add(jobPositionOptional.get().getPublisher());
                }
                if (!hrAccountType.equals(HRAccountType.SupperAccount) && supperAccountId > 0) {
                    hrIdList.add(supperAccountId);
                }
                application.setHrId(hrIdList);
            }
            application.setId(jobApplication.getId());
            return application;
        }).collect(Collectors.toList());


        /** 初始化HR实体和申请实体 */
        ApplicationEntity applicationEntity = new ApplicationEntity(daoManagement, applications, applicationContext);
        HREntity hrEntity = new HREntity(userHrAccount.getId(), hrAccountType, userHrAccount.getCompanyId(),
                daoManagement, applicationContext);

        //HR查看申请
        hrEntity.viewApplication(applicationEntity);
    }

    private void validateParameterBeforeViewApplication(ViewApplicationAggregateId viewApplicationAggregateId) throws CommonException {
        ValidateUtil vu = new ValidateUtil();
        vu.addIntTypeValidate("HR", viewApplicationAggregateId.getHrId(), null, null, 0, Integer.MAX_VALUE);
        vu.addRequiredOneValidate("申请", viewApplicationAggregateId.getApplicationIds(), null, null);
        String result = vu.validate();
        if (StringUtils.isNotBlank(result)) {
            throw ApplicationException.validateFailed(result);
        }
    }
}
