package com.moseeker.application.thrift;

import com.alibaba.fastjson.JSON;
import com.moseeker.application.exception.ApplicationException;
import com.moseeker.application.service.impl.JobApplicataionService;
import com.moseeker.application.service.impl.vo.ApplicationRecord;
import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.thrift.gen.application.service.JobApplicationServices.Iface;
import com.moseeker.thrift.gen.application.struct.ApplicationRecordsForm;
import com.moseeker.thrift.gen.application.struct.ApplicationResponse;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.JobResumeOther;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 申请服务实现类
 * <p>
 *
 * Created by zzh on 16/5/24.
 */
@Service
public class JobApplicataionServicesImpl implements Iface {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JobApplicataionService service;
    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    private static String saveChannelApplicationUrl;

    static {
        ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
        try {
            configUtils.loadResource("common.properties");
            saveChannelApplicationUrl = configUtils.get("alphacloud.company.save.channel_application_relation.url", String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean healthCheck() throws TException {
        return true;
    }

    /**
     * 创建申请
     *
     * @param jobApplication 申请参数
     * @return 新创建的申请记录ID
     */
    @Override
    public Response postApplication(JobApplication jobApplication){
        try{
            Response response = service.postApplication(jobApplication);
            String channelCode = jobApplication.getChannel_code();
            Integer sourceId = jobApplication.getChannel_source_id();
            Object appId = JSON.parseObject(response.getData()).get("jobApplicationId");
            if (appId != null && channelCode != null && sourceId != null) {
                Integer jobApplicationId = (Integer) appId;
                saveChannelApplicationRelationRequest(jobApplicationId, channelCode, sourceId);
            }
            return response;
        } catch (CommonException e) {
            // todo redis删除
            redisClient.del(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.APPLICATION_SINGLETON.toString(),
                    jobApplication.getApplier_id() + "", jobApplication.getPosition_id() + "");
            return new Response(e.getCode(), e.getMessage());
        } catch(Exception e){
            // todo redis删除
            redisClient.del(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.APPLICATION_SINGLETON.toString(),
                    jobApplication.getApplier_id() + "", jobApplication.getPosition_id() + "");
            logger.error(e.getMessage(),e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    /**
     * 发送保存申请和渠道关联关系的请求
     * @param jobApplicationId
     * @param channelCode
     * @throws ConnectException
     */
    private void saveChannelApplicationRelationRequest(Integer jobApplicationId,String channelCode,Integer sourceId) throws ConnectException {
        Map<String, Object> params = new HashMap<>();
        params.put("jobApplicationId", jobApplicationId);
        params.put("channelCode", channelCode);
        params.put("sourceId", sourceId);
        HttpClient.sendPost(saveChannelApplicationUrl, JSON.toJSONString(params));
    }


    /**
     * 更新申请数据
     *
     * @param jobApplication 用户实体
     */
    @Override
    public Response putApplication(JobApplication jobApplication){
        try{
            return service.putApplication(jobApplication);
        } catch (CommonException e) {
            return new Response(e.getCode(), e.getMessage());
        } catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
        }
    }

    /**
     * 删除申请记录
     *
     * @param applicationId 申请Id
     */
    @Override
    public Response deleteApplication(long applicationId){
        try{
            return service.deleteApplication(applicationId);
        } catch (CommonException e) {
            return new Response(e.getCode(), e.getMessage());
        } catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.APPLICATION_ARCHIVE_FAILED);
        }
    }

    /**
     * 创建申请
     *
     * @param jobResumeOther 申请参数
     * @return 新创建的申请记录ID
     */
    @Override
    public Response postJobResumeOther(JobResumeOther jobResumeOther) throws TException {
        try{
            return service.postJobResumeOther(jobResumeOther);
        } catch (CommonException e) {
            return new Response(e.getCode(), e.getMessage());
        }  catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    /**
     * 判断当前用户是否申请了该职位
     *
     * @param userId     用户ID
     * @param positionId 职位ID
     * @return true : 申请, false: 没申请过
     */
    @Override
    public Response getApplicationByUserIdAndPositionId(long userId, long positionId, long companyId) throws TException {
        return service.getApplicationByUserIdAndPositionId(userId, positionId, companyId);
    }

    /**
     * 一个用户在一家公司的每月的申请次数校验
     * 超出申请次数限制, 每月每家公司一个人只能申请10次
     * <p>
     *
     * @param userId    用户id
     * @param companyId 公司id
     */
    public Response validateUserApplicationCheckCountAtCompany(long userId, long companyId, long positionId) {
        return service.validateUserApplicationCheckCountAtCompany(userId, companyId, positionId);
    }

    @Override
    public Response validateUserApplicationTypeCheckCountAtCompany(long userId, long companyId) throws TException {
        return service.validateUserApplicationTypeCheckCountAtCompany(userId, companyId);
    }

    @Override
    public ApplicationResponse getAccountIdAndCompanyId(long jobApplicationId) throws TException {
        return service.getAccountIdAndCompanyId(jobApplicationId);
    }

    /**
     * 根据指定渠道 channel=5（支付宝），指定时间段（"2017-05-10 14:57:14"）， 返回给第三方渠道同步的申请状态。
     * @param channel
     * @param start_time
     * @param end_time
     * @return
     * @throws TException
     */
    @Override
    public Response getApplicationListForThirdParty(int channel, String start_time, String end_time) throws TException {
        return service.getApplicationListForThirdParty(channel, start_time, end_time);
    }

    /**
     * 获取HR下面有多少未读简历
     * @param user_id
     * @return
     * @throws TException
     */
    @Override
    public Response getHrIsViewApplication(int user_id) throws TException {
        Response response = service.getHrApplicationNum(user_id);
        return response;
    }
    /**
     * HR查看申请
     * @param hrId HR编号
     * @param applicationIdList 申请编号集合
     * @throws BIZException 业务异常
     * @throws TException thrift异常
     */
    @Override
    public void viewApplications(int hrId, List<Integer> applicationIdList) throws BIZException, TException {
        //参数校验
        ValidateUtil vu = new ValidateUtil();
        vu.addIntTypeValidate("HR", hrId, null, null, 0, Integer.MAX_VALUE);
        vu.addRequiredOneValidate("申请", applicationIdList, null, null);
        String result = vu.validate();
        if (StringUtils.isNotBlank(result)) {
            throw ExceptionConvertUtil.convertCommonException(ApplicationException.validateFailed(result));
        }
        try {
            service.viewApplications(hrId, applicationIdList);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ApplicationException.PROGRAM_EXCEPTION;
        }
    }

    @Override
    public List<Integer> employeeProxyApply(int referenceId, int applierId, List<Integer> positionIdList)
            throws BIZException, TException {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredOneValidate("职位", positionIdList);
        validateUtil.addUpperLimitValidate("职位", positionIdList);
        validateUtil.addIntTypeValidate("求职者", applierId, 1, null);
        validateUtil.addIntTypeValidate("推荐者", referenceId, 1, null);
        String result = validateUtil.validate();
        if (StringUtils.isNotBlank(result)) {
            throw ExceptionUtils.convertException(CommonException.validateFailed(result));
        }

        try {
            return service.employeeProxyApply(referenceId, applierId, positionIdList);
        } catch (Exception e) {
            for(Integer position : positionIdList){
                redisClient.del(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.APPLICATION_SINGLETON.toString(),
                        applierId + "", position + "");
            }
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public List<ApplicationRecordsForm> getApplications(int userId, int companyId) throws TException {
        try {
            List<ApplicationRecordsForm> data = new ArrayList<>();
            List<ApplicationRecord> list = service.getApplications(userId, companyId);
            if (list != null && list.size() > 0) {
                data = list
                        .stream()
                        .map(applicationRecord -> {
                            ApplicationRecordsForm form = new ApplicationRecordsForm();
                            BeanUtils.copyProperties(applicationRecord, form);
                            return form;
                        })
                        .collect(Collectors.toList());
            }
            return data;
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public int appSendEmail(int appId) throws BIZException, TException {
        try{
            int result=service.appSendEmail(appId);
            return result;
        }catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ApplicationException.PROGRAM_EXCEPTION;
        }
    }

    /**
    * 校验公司下面的appid是否存在
    *
    * @param   appId
    * @param companyId
    * @Author  lee
    * @Date  2019/3/6 下午6:58
    */
    @Override
    public int validateAppid(int appId, int companyId) throws BIZException, TException {
        try {
            int result=service.validateAppid(appId, companyId);
            return result;
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ApplicationException.PROGRAM_EXCEPTION;
        }
    }


    /**
     * 清除一个公司一个人申请次数限制的redis key 给sysplat用
     *
     * @param userId    用户id
     * @param companyId 公司id
     */
    public Response deleteRedisKeyApplicationCheckCount(long userId, long companyId) throws TException {
        try {
            return service.deleteRedisKeyApplicationCheckCount(userId, companyId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }


    @Override
    public Response postApplicationIfNotApply(JobApplication application) throws TException {
        try{
            return service.postApplication(application);
        }  catch (CommonException e) {
            return new Response(e.getCode(), e.getMessage());
        } catch(Exception e){

            logger.error(e.getMessage(),e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }
}
