package com.moseeker.application.domain;

import com.moseeker.application.domain.component.state.ApplicationStatus;
import com.moseeker.application.domain.pojo.Application;
import com.moseeker.application.exception.ApplicationException;
import com.moseeker.application.infrastructure.DaoManagement;
import com.moseeker.baseorm.config.HRAccountType;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationRecord;
import com.moseeker.common.exception.CommonException;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * HR实体
 * Created by jack on 17/01/2018.
 */
public class HREntity {

    private final int id;                       //编号
    private final int companyId;                //公司编号
    private final HRAccountType accountType;    //账号类型
    private final DaoManagement daoManagement;  //dao 层管理
    private final ApplicationContext applicationContext;

    public HREntity(int id, HRAccountType accountType, int companyId, DaoManagement daoManagement, ApplicationContext applicationContext) {
        this.id = id;
        this.accountType = accountType;
        this.daoManagement = daoManagement;
        this.applicationContext = applicationContext;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public HRAccountType getAccountType() {
        return accountType;
    }

    /**
     * HREntity 查阅申请
     * @param applicationBatchEntity 申请实体
     * @throws CommonException APPLICATION_HAVE_NO_PERMISSION(41010)
     */
    public void viewApplication(ApplicationBatchEntity applicationBatchEntity) throws CommonException {
        if (applicationBatchEntity == null || applicationBatchEntity.getApplicationList() == null
                || applicationBatchEntity.getApplicationList().size() == 0) {
            return;
        }
        //校验是否有权限浏览申请
        if (!validateAuthority(applicationBatchEntity.getApplicationList())) {
            throw ApplicationException.APPLICATION_HAVE_NO_PERMISSION;
        }
        //申请被查阅
        applicationBatchEntity.viewed();

        //添加HR对申请的操作记录
        List<Application> unViewedApplicationList = applicationBatchEntity.getApplicationList()
                .stream()
                .filter(application -> !application.isViewed())
                .collect(Collectors.toList());

        if (unViewedApplicationList != null && unViewedApplicationList.size() > 0) {
            List<HrOperationRecord> operationRecordList = unViewedApplicationList
                    .stream()
                    .map(application -> {
                        HrOperationRecord hrOperationRecord = new HrOperationRecord();
                        hrOperationRecord.setAdminId((long) this.id);
                        hrOperationRecord.setCompanyId((long) this.companyId);
                        hrOperationRecord.setAppId((long) application.getId());
                        hrOperationRecord.setOperateTplId(ApplicationStatus.CVChecked.getState());
                        return hrOperationRecord;
                    })
                    .collect(Collectors.toList());
            daoManagement.getHrOperationJOOQDao().insert(operationRecordList);
        }
    }

    /**
     * 检验HR是否有权限浏览这些申请
     * @param applicationEntityList 申请集合
     * @return true 有权限浏览；false 没有权限浏览申请
     */
    private boolean validateAuthority(List<Application> applicationEntityList) {

        Optional<Application> optional = applicationEntityList.stream().filter(applicationEntity -> {
            if (applicationEntity.getHrId() != null && applicationEntity.getHrId().size() > 0) {
                Optional<Integer> optionId = applicationEntity.getHrId().stream().filter(id -> id == this.getId()).findAny();
                if (optionId.isPresent()) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        })
        .findAny();
        return !optional.isPresent();
    }
}
