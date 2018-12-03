package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.useraccounts.constant.Job58Constant;
import com.moseeker.useraccounts.constant.UserAccountConstant;
import com.moseeker.useraccounts.service.impl.pojos.Job58BindUserInfo;
import com.moseeker.useraccounts.service.impl.vo.Job58BaseVO;
import com.moseeker.useraccounts.service.impl.vo.Job58BindVO;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.AbstractBindProcessor;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.ThirdPartyAccountContext;
import com.moseeker.useraccounts.service.thirdpartyaccount.util.BindCheck;
import com.moseeker.useraccounts.utils.HttpClientUtil;
import com.moseeker.useraccounts.utils.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author cjm
 * @date 2018-11-28 10:05
 **/
@Component
public class Job58BindProcessor extends AbstractBindProcessor {

    private static Logger logger = LoggerFactory.getLogger(Job58BindProcessor.class);

    @Autowired
    HRThirdPartyAccountDao thirdPartyAccountDao;
    @Autowired
    ThirdPartyAccountContext context;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HrThirdPartyAccountDO postProcessorBeforeBind(int hrId, HrThirdPartyAccountDO account) throws Exception {
        String code = account.getExt();
        Job58BindVO job58BindVO = new Job58BindVO(code, System.currentTimeMillis(), Job58Constant.APP_KEY);
        account = handleBind(account, job58BindVO, UserAccountConstant.job58UserBindUrl);
        HrThirdPartyAccountDO oldAccount = thirdPartyAccountDao.getEQThirdPartyAccount(account);
        if (BindCheck.isNotNullAccount(oldAccount)) {
            oldAccount.setExt(account.getExt());
            oldAccount.setUsername(account.getUsername());
            oldAccount.setExt2(account.getExt2());
            // 如果是之前存在的账号，为防止本次刷新token将之前的token失效，将本次刷新结果更新至数据库
            context.updateBinding(oldAccount);
            return oldAccount;
        }
        return account;
    }

    public HrThirdPartyAccountDO handleBind(HrThirdPartyAccountDO account, Object obj, String url) throws Exception {
        // 请求绑定
        JSONObject result = doBind(obj, url);
        // 请求结果处理
        if ("0".equals(String.valueOf(result.get("code")))) {
            JSONObject bindInfo = JSONObject.parseObject(result.getString("data"));
            // 请求58的公共入参
            Job58BaseVO job58BaseVO = createJob58BaseVO(bindInfo);
            // 发送请求获取58用户信息
            JSONObject userInfo = sendRequestGetUserInfo(job58BaseVO);
            // 根据58返回结果创建对象用于存在第三方账号表的ext字段中
            Job58BindUserInfo job58BindUserInfo = createJob58BindUserInfo(bindInfo);
            account.setUsername(userInfo.getString("name"));
            account.setExt(JSON.toJSONString(job58BindUserInfo));
        } else {
            throw new BIZException(result.getInteger("code"), result.getString("message"));
        }
        return account;
    }

    /**
     * 用于绑定/刷新token
     * @param obj 绑定dto和刷新dto
     * @author  cjm
     * @date  2018/11/28
     * @return 绑定结果
     */
    private JSONObject doBind(Object obj, String url) throws Exception {
        TypeReference<Map<String, String>> reference = new TypeReference<Map<String, String>>() {};
        Map<String, String> requestMap = JSONObject.parseObject(JSON.toJSONString(obj), reference);
        String sign = Md5Utils.getMD5SortKeyWithEqual(Job58Constant.SECRECT_KEY, Md5Utils.mapToList(requestMap), requestMap);
        requestMap.put("sig", sign);
        logger.info("======================requestParam:{}", JSON.toJSONString(requestMap));
        String bindResult = HttpClientUtil.sentFormHttpPostRequest(url, requestMap);
        if (StringUtils.isNullOrEmpty(bindResult)) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.THIRD_PARTY_ACCOUNT_58_REQUEST_ERROR);
        }
        logger.info("=========================bindResultJson:{}", bindResult);
        return JSONObject.parseObject(bindResult);
    }

    @Override
    public HrThirdPartyAccountDO postProcessorAfterBind(int hrId, HrThirdPartyAccountDO account) {
        return account;
    }

    /**
     * 发送请求获取58用户信息
     *
     * @param job58BaseVO 获取用户信息的公共入参
     * @return 成功获取时的用户信息
     * @author cjm
     * @date 2018/11/22
     */
    private JSONObject sendRequestGetUserInfo(Job58BaseVO job58BaseVO) throws Exception {
        JSONObject userInfo;
        TypeReference<Map<String, String>> reference = new TypeReference<Map<String, String>>() {
        };
        Map<String, String> requestMap = JSONObject.parseObject(JSON.toJSONString(job58BaseVO), reference);
        String sign = Md5Utils.getMD5SortKeyWithEqual(Job58Constant.SECRECT_KEY, Md5Utils.mapToList(requestMap), requestMap);
        requestMap.put("sig", sign);
        logger.info("======================requestParam:{}", JSON.toJSONString(requestMap));
        String response = HttpClientUtil.sentFormHttpPostRequest(UserAccountConstant.job58UserGetUrl, requestMap);
        if (StringUtils.isNullOrEmpty(response)) {
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
     *
     * @param bindInfo 绑定信息
     * @return ext字段json串
     * @author cjm
     * @date 2018/11/22
     */
    private Job58BindUserInfo createJob58BindUserInfo(JSONObject bindInfo) {
        String accessToken = bindInfo.getString("access_token");
        String refreshToken = bindInfo.getString("refresh_token");
        String openId = bindInfo.getString("openid");
        return new Job58BindUserInfo(openId, accessToken, refreshToken);
    }

    /**
     * 请求58的公共入参
     *
     * @param bindInfo 绑定信息
     * @return ext字段json串
     * @author cjm
     * @date 2018/11/22
     */
    private Job58BaseVO createJob58BaseVO(JSONObject bindInfo) {
        String accessToken = bindInfo.getString("access_token");
        String openId = bindInfo.getString("openid");
        return new Job58BaseVO(openId, accessToken, System.currentTimeMillis(), Job58Constant.APP_KEY);
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB58;
    }
}
