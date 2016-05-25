package com.moseeker.application.service.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.application.dao.ApplicationDao;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.thrift.gen.application.struct.Application;
import com.moseeker.thrift.gen.application.service.ApplicationServices.Iface;

/**
 * 申请服务实现类
 * <p>
 *
 * Created by zzh on 16/5/24.
 */
@Service
public class ApplicataionServicesImpl extends JOOQBaseServiceImpl<Application, JobApplicationRecord> implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ApplicationDao applicationDao;

    @Override
    protected void initDao() {
        super.dao = this.applicationDao;
    }

    @Override
    protected JobApplicationRecord structToDB(Application application) throws ParseException {
        return (JobApplicationRecord) BeanUtils.structToDB(application, JobApplicationRecord.class);
    }

    @Override
    protected Application DBToStruct(JobApplicationRecord jobApplicationRecord) {
        return (Application) BeanUtils.DBToStruct(Application.class, jobApplicationRecord);
    }

    /**
     * 创建申请
     * @param application 申请参数
     * @return 新创建的申请记录ID
     * @throws TException
     */
    @Override
    public Response postResource(Application application) throws TException {
        try {
            // 必填项验证
            Response response = validateApplication(application);
            if (response.status > 0){
                return response;
            }

            // 添加申请
            int applicationId = applicationDao.postResource(this.structToDB(application));
            if (applicationId > 0) {
                Map<String, Object> hashmap = new HashMap<>();
                hashmap.put("application_id", applicationId);
                return ResponseUtils.success(hashmap); // 返回 application_id
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postResources application error: ", e);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail("application failed");
    }

    /**
     * 申请必填项校验
     * @param application
     * @return
     */
    private Response validateApplication(Application application){

        Response response = new Response(0, "ok");

        // 参数校验
        if(application == null){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        if(application.applier_id == 0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "applier_id"));
        }
        if(application.company_id == 0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "company_id"));
        }
        if(application.position_id == 0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "position_id"));
        }
        if(application.wechat_id == 0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "wechat_id"));
        }
        if(application.app_tpl_id == 0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "app_tpl_id"));
        }

        // 业务校验

        // TODO: 职位可申请校验, 把数据放进来, 当收集数据了, 申请记录显示时, 控制一下数据

        return response;
    }
}
