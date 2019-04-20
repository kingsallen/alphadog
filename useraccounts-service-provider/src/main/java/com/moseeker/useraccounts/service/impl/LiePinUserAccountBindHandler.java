package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.BindThirdPart;
import com.moseeker.common.constants.BindingStatus;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.useraccounts.constant.UserAccountConstant;
import com.moseeker.useraccounts.service.thirdpartyaccount.EmailNotification;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.IBindRequest;
import com.moseeker.useraccounts.utils.AESUtils;
import com.moseeker.useraccounts.utils.HttpClientUtil;
import com.moseeker.useraccounts.utils.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 猎聘用户账号绑定（登录）
 *
 * @author cjm
 * @date 2018-05-28 13:24
 **/
@Component
public class LiePinUserAccountBindHandler implements IBindRequest {

    private Logger logger = LoggerFactory.getLogger(LiePinUserAccountBindHandler.class);

    private static final String CHANNEL;

    private static final String SECRECT_KEY;

    static{
        SECRECT_KEY = EmailNotification.getConfig("liepin_position_api_secretkey");
        CHANNEL = EmailNotification.getConfig("liepin_position_api_channel");
    }

    @Autowired
    EmailNotification emailNotification;

    private String bindEmailSubject = "用户绑定失败";

    @Override
    public HrThirdPartyAccountDO bind(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) throws Exception {
        try {
            hrThirdPartyAccount = bindAdaptor(hrThirdPartyAccount, extras);
        } catch (BIZException e) {
            logger.warn("=================errormsg:{},username:{}===================", e.getMessage(), hrThirdPartyAccount.getUsername());
            hrThirdPartyAccount.setBinding((short) BindingStatus.INFOWRONG.getValue());
            hrThirdPartyAccount.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            emailNotification.sendCustomEmail(emailNotification.getRefreshMails(), e.getMessage() + "</br>用户账号:"
                    + hrThirdPartyAccount.getUsername(), bindEmailSubject);
            hrThirdPartyAccount.setBinding((short) BindingStatus.ERROR.getValue());
            hrThirdPartyAccount.setErrorMessage(BindThirdPart.BIND_EXP_MSG);
            logger.error(e.getMessage(), e);
        }
        return hrThirdPartyAccount;
    }

    /**
     * 此方法是为了将绑定业务提出，单纯的绑定操作，绑定成功返回第三方账号对象，绑定失败就抛异常
     * @param hrThirdPartyAccount 第三方账号
     * @param extras 额外数据，猎聘渠道没用到
     * @author  cjm
     * @date  2018/7/9
     * @return HrThirdPartyAccountDO
     */
    public HrThirdPartyAccountDO bindAdaptor(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) throws Exception {
        String username = hrThirdPartyAccount.getUsername();

        String passwordHex = hrThirdPartyAccount.getPassword();

        String password = decryPwd(passwordHex);

        String resultJson = sendRequest2Liepin(username, password);

        if (StringUtils.isNullOrEmpty(resultJson)) {
            logger.info("================用户绑定时http请求结果为空=================");
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, "用户绑定时http请求结果为空");
        }
        logger.info("=========================bindResultJson:{}", resultJson);
        JSONObject result = JSONObject.parseObject(resultJson);

        // 请求结果处理
        if ("0".equals(String.valueOf(result.get("code")))) {
            JSONObject userInfo = JSONObject.parseObject(result.getString("data"));
            String userId = userInfo.getString("usere_id");
            String token = userInfo.getString("token");
            hrThirdPartyAccount.setExt(userId);
            hrThirdPartyAccount.setExt2(token);
            hrThirdPartyAccount.setBinding((short) BindingStatus.BOUND.getValue());
            return hrThirdPartyAccount;
        } else {
            throw new BIZException(result.getInteger("code"), result.getString("message"));
        }
    }

    /**
     * 将数据库中存的aes加密的密码解密
     *
     * @param passwordHex aes加密后的十六位密码
     * @return
     * @author cjm
     * @date 2018/6/15
     */
    public String decryPwd(String passwordHex) throws Exception {
        byte[] target = AESUtils.decrypt(passwordHex);
        return new String(target, "UTF-8").trim();
    }

    /**
     *
     * @param   username  用户名
     * @param   password  密码
     * @author  cjm
     * @date  2018/7/9
     * @return 返回请求结果，请求成功时，猎聘返回的是一个json字符串
     */
    private String sendRequest2Liepin(String username, String password) throws Exception {

        // 构造请求数据
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("usere_login", username);
        requestMap.put("password", password);
        String t = new SimpleDateFormat("yyyyMMdd").format(new Date());
        requestMap.put("t", t);

        //生成签名
        String sign = Md5Utils.getMD5SortKey(SECRECT_KEY, Md5Utils.mapToList(requestMap), requestMap);
        requestMap.put("sign", sign);

        //设置请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("channel", CHANNEL);

        //发送请求
        return HttpClientUtil.sentHttpPostRequest(UserAccountConstant.liepinUserBindUrl, headers, requestMap);
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.LIEPIN;
    }
}
