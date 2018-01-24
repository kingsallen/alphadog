package com.moseeker.application.domain;

import com.moseeker.application.infrastructure.DaoManagement;
import com.moseeker.application.service.event.ViewApplicationListEvent;
import com.moseeker.baseorm.config.HRAccountType;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationRecord;
import com.moseeker.common.exception.CommonException;
import org.springframework.context.ApplicationContext;

import java.util.List;
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

    public int getCompanyId() {
        return companyId;
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
        //申请被查阅
        List<HrOperationRecord> operationRecordList = applicationBatchEntity.viewed(this);

        if (operationRecordList != null && operationRecordList.size() > 0) {

            //持久化HR操作申请的记录
            daoManagement.getHrOperationJOOQDao().insert(operationRecordList);

            //发布HR查看申请事件
            publishEvent(operationRecordList
                    .stream()
                    .map(record -> record.getAppId().intValue())
                    .collect(Collectors.toList()));
        }
    }

    /**
     * 发布HR查看申请的事件
     * @param applicationIdList 查看的申请编号
     */
    private void publishEvent(List<Integer> applicationIdList) {
        ViewApplicationListEvent viewApplicationListEvent
                = new ViewApplicationListEvent(applicationIdList);
        applicationContext.publishEvent(viewApplicationListEvent); //发布查看申请事件
    }
}
