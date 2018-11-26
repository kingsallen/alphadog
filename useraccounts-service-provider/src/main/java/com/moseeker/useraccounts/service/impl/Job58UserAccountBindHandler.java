package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.common.constants.BindingStatus;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.useraccounts.constant.UserAccountConstant;
import com.moseeker.useraccounts.service.impl.pojos.Job58BindUserInfo;
import com.moseeker.useraccounts.service.impl.vo.Job58BaseVO;
import com.moseeker.useraccounts.service.impl.vo.Job58BindVO;
import com.moseeker.useraccounts.service.thirdpartyaccount.EmailNotification;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.IBindRequest;
import com.moseeker.useraccounts.utils.HttpClientUtil;
import com.moseeker.useraccounts.utils.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author cjm
 * @date 2018-11-21 14:13
 **/
@Component
public class Job58UserAccountBindHandler implements IBindRequest {

    private Logger logger = LoggerFactory.getLogger(Job58UserAccountBindHandler.class);

    private static final String SECRECT_KEY;
    private static final String APP_KEY;

    static{
        SECRECT_KEY = EmailNotification.getConfig("58job_api_secret");
        APP_KEY = EmailNotification.getConfig("58job_api_app_key");
    }

    @Override
    public HrThirdPartyAccountDO bind(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) throws Exception {
        String code = hrThirdPartyAccount.getExt();
        Job58BindVO job58BindVO = new Job58BindVO(code, System.currentTimeMillis(), APP_KEY);
        hrThirdPartyAccount = bindAdaptor(hrThirdPartyAccount, job58BindVO);
        hrThirdPartyAccount.setSyncTime(DateUtils.dateToShortTime(new Date()));
        return hrThirdPartyAccount;
    }

    /**
     *
     * @param   hrThirdPartyAccount 第三方账号do
     * @param   obj 绑定或刷新token的请求vo
     * @author  cjm
     * @date  2018/11/23
     * @return   HrThirdPartyAccountDO
     */
    public HrThirdPartyAccountDO bindAdaptor(HrThirdPartyAccountDO hrThirdPartyAccount, Object obj) throws Exception {
        TypeReference<Map<String, String>> reference = new TypeReference<Map<String, String>>(){};
        Map<String, String> requestMap = JSONObject.parseObject(JSON.toJSONString(obj), reference);
        String sign = Md5Utils.getMD5SortKeyWithEqual(SECRECT_KEY, Md5Utils.mapToList(requestMap), requestMap);
        requestMap.put("sig", sign);
        logger.info("======================requestParam:{}", JSON.toJSONString(requestMap));
        String bindResult = HttpClientUtil.sentFormHttpPostRequest(UserAccountConstant.job58UserBindUrl, requestMap);
        if(StringUtils.isNullOrEmpty(bindResult)){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.THIRD_PARTY_ACCOUNT_58_REQUEST_ERROR);
        }
        logger.info("=========================bindResultJson:{}", bindResult);
        JSONObject result = JSONObject.parseObject(bindResult);
        // 请求结果处理
        if ("0".equals(String.valueOf(result.get("code")))) {
            JSONObject bindInfo = JSONObject.parseObject(result.getString("data"));
            // 请求58的公共入参
            Job58BaseVO job58BaseVO = createJob58BaseVO(bindInfo);
            // 发送请求获取58用户信息
            JSONObject userInfo = sendRequestGetUserInfo(job58BaseVO);
            // 根据58返回结果创建对象用于存在第三方账号表的ext字段中s
            Job58BindUserInfo job58BindUserInfo = createJob58BindUserInfo(bindInfo);

            hrThirdPartyAccount.setUsername(userInfo.getString("name"));
            hrThirdPartyAccount.setExt(JSON.toJSONString(job58BindUserInfo));
            hrThirdPartyAccount.setBinding((short) BindingStatus.BOUND.getValue());
        } else {
            throw new BIZException(result.getInteger("code"), result.getString("message"));
        }
        return hrThirdPartyAccount;
    }

    /**
     * 发送请求获取58用户信息
     * @param  job58BaseVO  获取用户信息的公共入参
     * @author  cjm
     * @date  2018/11/22
     * @return 成功获取时的用户信息
     */
    private JSONObject sendRequestGetUserInfo(Job58BaseVO job58BaseVO) throws Exception {
        JSONObject userInfo;
        TypeReference<Map<String, String>> reference = new TypeReference<Map<String, String>>(){};
        Map<String, String> requestMap = JSONObject.parseObject(JSON.toJSONString(job58BaseVO), reference);
        String sign = Md5Utils.getMD5SortKeyWithEqual(SECRECT_KEY, Md5Utils.mapToList(requestMap), requestMap);
        requestMap.put("sig", sign);
        logger.info("======================requestParam:{}", JSON.toJSONString(requestMap));
        String response = HttpClientUtil.sentFormHttpPostRequest(UserAccountConstant.job58UserGetUrl, requestMap);
        if(StringUtils.isNullOrEmpty(response)){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.THIRD_PARTY_ACCOUNT_58_REQUEST_ERROR);
        }
        logger.info("===============getUserInfoResponse:{}", response);
        JSONObject responseJson = JSONObject.parseObject(response);
        if ("0".equals(String.valueOf(responseJson.get("code")))) {
            userInfo = JSONObject.parseObject(responseJson.getString("data"));
        } else {
            throw new BIZException(1, responseJson.getString("message"));
        }
        return userInfo;
    }

    /**
     * 根据58返回结果创建对象用于存在第三方账号表的ext字段中
     * @param   bindInfo  绑定信息
     * @author  cjm
     * @date  2018/11/22
     * @return   ext字段json串
     */
    private Job58BindUserInfo createJob58BindUserInfo(JSONObject bindInfo) {
        String accessToken = bindInfo.getString("access_token");
        String refreshToken = bindInfo.getString("refresh_token");
        String openId = bindInfo.getString("openid");
        return new Job58BindUserInfo(openId, accessToken, refreshToken);
    }

    /**
     * 请求58的公共入参
     * @param   bindInfo  绑定信息
     * @author  cjm
     * @date  2018/11/22
     * @return   ext字段json串
     */
    private Job58BaseVO createJob58BaseVO(JSONObject bindInfo) {
        String accessToken = bindInfo.getString("access_token");
        String openId = bindInfo.getString("openid");
        return new Job58BaseVO(openId, accessToken, System.currentTimeMillis(), APP_KEY);
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB58;
    }
}
