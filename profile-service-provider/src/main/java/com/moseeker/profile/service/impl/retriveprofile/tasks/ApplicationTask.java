package com.moseeker.profile.service.impl.retriveprofile.tasks;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.profile.service.impl.retriveprofile.Task;
import com.moseeker.profile.service.impl.retriveprofile.parameters.ApplicationTaskParam;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.application.service.JobApplicationServices;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.common.struct.Response;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 处理申请业务 Created by jack on 10/07/2017.
 */
@Component
public class ApplicationTask implements Task<ApplicationTaskParam, Integer> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    JobApplicationServices.Iface jobApplicationServices = ServiceManager.SERVICE_MANAGER.getService(JobApplicationServices.Iface.class);

    public Integer handler(ApplicationTaskParam param) throws CommonException {

        JobApplication application = initApplication(param.getUserId(), param.getPositionId(),
                param.getOrigin());

        try {
            Response response = jobApplicationServices.postApplication(application);
            if (response.getStatus() == 0) {
                JSONObject jsonObject = JSON.parseObject(response.getData());
                return (Integer)jsonObject.get("jobApplicationId");
            }
            return 0;
        } catch (TException e) {
            throw ExceptionUtils.convertToCommonException(e);
        }
    }

    /**
     * 初始化一个申请记录
     *
     * @param applierId  申请编号
     * @param positionId 职位编号
     * @return
     */
    private JobApplication initApplication(int applierId, int positionId, int origin) {
        JobApplication jobApplicationRecord = new JobApplication();
        jobApplicationRecord.setApplier_id(applierId);
        jobApplicationRecord.setPosition_id(positionId);
        jobApplicationRecord.setOrigin(origin);
        jobApplicationRecord.setApp_tpl_id(Constant.RECRUIT_STATUS_APPLY);
        return jobApplicationRecord;
    }
}
