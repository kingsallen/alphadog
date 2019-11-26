package com.moseeker.application.service.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.moseeker.application.domain.ChannelEntity;
import com.moseeker.application.service.application.ChannelApplicationOriginConverter;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.HttpClient;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.common.struct.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.util.List;
import java.util.Map;

/**
 * @author: huangwenjian
 * @desc:
 * @since: 2019-11-25 22:40
 */
@Aspect
@Component
@Order(value = 1)
public class HandleChannelApplicationAspect {

    @Autowired
    private ChannelApplicationOriginConverter converter;

    private static String saveChannelApplicationUrl;

    static {
        ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
        try {
            configUtils.loadResource("common.properties");
            saveChannelApplicationUrl = configUtils.get("alphacloud.application.save.channel_application_relation.url", String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Pointcut("@annotation(com.moseeker.application.service.annotation.HandleChannelApplication)")
    public void postApplicationPointCut() {

    }

    @Around(value = "postApplicationPointCut()")
    public Object handleBeforePostApplication(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (args.length == 0) {
            throw CommonException.PROGRAM_EXCEPTION;
        } else if (!(args[0] instanceof JobApplication)) {
            throw CommonException.PROGRAM_EXCEPTION;
        }
        JobApplication jobApplication = (JobApplication) args[0];
        List<ChannelEntity> channelEntities = handleJobApplicationBefore(jobApplication);
        args[0] = jobApplication;
        Object result = joinPoint.proceed(args);
        if (!(result instanceof Response)) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
        Response response = (Response) result;
        if (response.status == 0) {
            this.saveChannelApplicationRelation(jobApplication, channelEntities, response);
        }
        return result;
    }

    /**
     * 对jobApplication进行处理
     * @param jobApplication
     * @return
     */
    private List<ChannelEntity> handleJobApplicationBefore(JobApplication jobApplication) {
        Integer origin = jobApplication.getOrigin();
        List<Map<String, String>> channelParams = jobApplication.getChannel();
        List<ChannelEntity> channels = Lists.newArrayList();
        Map<Integer, List<ChannelEntity>> channelMap = Maps.newHashMap();
        // 如果origin为空,channelCode和sourceId不为空,代表是整改后的渠道
        if ((origin == null || origin == 0) && (channelParams != null && !channelParams.isEmpty())) {
            origin = 0;
            for (Map<String, String> paramMap : channelParams) {
                JSONObject jo = JSON.parseObject(JSON.toJSONString(paramMap));
                String code = jo.getString("code");
                String source_id = jo.getString("source_id");
                origin += converter.channel2Origin(code, Integer.valueOf(source_id));
                ChannelEntity entity = new ChannelEntity();
                channels.add(entity);
            }
        }
        // origin不为0,说明是以前的渠道,转为新的channelCode和sourceId
        else if ((origin != null && origin != 0)) {
            channels = converter.origin2Channel(origin);
        }
        jobApplication.setOrigin(origin);
        return channels;
    }

    /**
     * 保存channel和application的关联关系
     * @param jobApplication"
     * @param response
     * @throws ConnectException
     */
    private void saveChannelApplicationRelation(JobApplication jobApplication, List<ChannelEntity> channels, Response response) throws ConnectException {
        Integer companyId = Math.toIntExact(jobApplication.getCompany_id());
        Integer applicationId = (Integer) JSON.parseObject(response.getData()).get("jobApplicationId");
        Integer applierId = Math.toIntExact(jobApplication.getApplier_id());
        doSaveChannelApplicationRelation(applicationId, companyId, applierId, channels);
    }

    /**
     * 发送保存申请和渠道关联关系的请求
     * @param applicationId
     * @param companyId
     * @param channels
     * @throws ConnectException
     */
    private void doSaveChannelApplicationRelation(Integer applicationId, Integer companyId, Integer applierId, List<ChannelEntity> channels) throws ConnectException {
        Map<String, Object> params = Maps.newHashMap();
        params.put("applicationId", applicationId);
        params.put("companyId", companyId);
        params.put("applierId", applierId);
        params.put("channels", channels);
        HttpClient.sendPost(saveChannelApplicationUrl, JSON.toJSONString(params));
    }
}
