package com.moseeker.useraccounts.service.thirdpartyaccount.base;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.BindThirdPart;
import com.moseeker.common.constants.BindingStatus;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.EmojiFilter;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ChaosHandler implements IBindRequest {
    Logger logger = LoggerFactory.getLogger(ChaosHandler.class);

    ChaosServices.Iface chaosService = ServiceManager.SERVICE_MANAGER.getService(ChaosServices.Iface.class);

    public HrThirdPartyAccountDO bind(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) throws Exception {
        String data = "";
        try {
            data = chaosService.binding(hrThirdPartyAccount, extras);
            logger.info("bind chaos result " + data);
        } catch (Exception e) {
            logger.info("ChaosServiceImpl bind ConnectException");
            //绑定超时发送邮件
            hrThirdPartyAccount.setBinding((short) BindingStatus.ERROR.getValue());
            hrThirdPartyAccount.setErrorMessage(BindThirdPart.BIND_TIMEOUT_MSG);
            return hrThirdPartyAccount;
        }
        return handleBind(hrThirdPartyAccount, data);
    }

    public HrThirdPartyAccountDO bindConfirm(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras, boolean confirm) throws Exception {
        String data = chaosService.bindConfirm(hrThirdPartyAccount, extras, confirm);
        JSONObject jsonObject = JSONObject.parseObject(data);
        logger.info("bindConfirm chaos result " + data);
        int status = jsonObject.getIntValue("status");
        String message = jsonObject.getString("message");
        //发送成功
        if (status == 0 || status == 110) {
            return hrThirdPartyAccount;
        } else {
            throw new BIZException(status, message);
        }
    }

    public HrThirdPartyAccountDO bindMessage(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras, String code) throws Exception {
        String data = chaosService.bindMessage(hrThirdPartyAccount, extras, code);
        logger.info("bindMessage chaos result " + data);
        return bindMessageHandle(hrThirdPartyAccount, data);
    }

    public HrThirdPartyAccountDO handleBind(HrThirdPartyAccountDO hrThirdPartyAccount, String data) throws BIZException {
        JSONObject jsonObject = JSONObject.parseObject(data);
        int status = jsonObject.getIntValue("status");

        if (status == 0) {
            hrThirdPartyAccount.setBinding((short) BindingStatus.GETINGINFO.getValue());
            logger.info("绑定成功，binding标志为" + hrThirdPartyAccount.getBinding());
        } else {
            String message = jsonObject.getString("message");

            if (status == 1) {
                if (StringUtils.isNullOrEmpty(message)) {
                    message = BindThirdPart.BIND_UP_ERR_MSG;
                }
                throw new BIZException(1, message);
            } else if (status == 100) {
                hrThirdPartyAccount.setBinding((short) BindingStatus.NEEDCODE.getValue());
            } else if (status == 2) {
                hrThirdPartyAccount.setBinding((short) BindingStatus.ERROR.getValue());
                if (StringUtils.isNullOrEmpty(message)) {
                    message = BindThirdPart.BIND_EXP_MSG;
                } else {
                    message = EmojiFilter.unicodeToUtf8(message);
                }
            } else {
                if (StringUtils.isNullOrEmpty(message)) {
                    message = BindThirdPart.BIND_ERR_MSG;
                }
                throw new BIZException(1, message);
            }
            hrThirdPartyAccount.setErrorMessage(message);
        }

        return hrThirdPartyAccount;
    }

    public HrThirdPartyAccountDO bindMessageHandle(HrThirdPartyAccountDO hrThirdPartyAccount, String data) throws BIZException {
        JSONObject jsonObject = JSONObject.parseObject(data);
        int status = jsonObject.getIntValue("status");
        String message = jsonObject.getString("message");
        if (status == 0) {
            hrThirdPartyAccount.setBinding((short) BindingStatus.BOUND.getValue());
        } else if (status == 112) {//验证码错误
            if (StringUtils.isNullOrEmpty(message)) {
                message = "验证码错误";
            }
            throw new BIZException(112, message);
        } else if (status == 111) {//验证码超时
            if (StringUtils.isNullOrEmpty(message)) {
                message = "验证码超时";
            }
            throw new BIZException(111, message);
        } else if (status == 1) {
            if (StringUtils.isNullOrEmpty(message)) {
                message = "用户名或密码错误";
            }
            throw new BIZException(1, message);
        } else if (status == 9) {
            //发送绑定失败的邮件
            hrThirdPartyAccount.setBinding((short) BindingStatus.ERROR.getValue());
        } else {
            if (StringUtils.isNullOrEmpty(message)) {
                message = "绑定失败，请重试";
            }
            throw new BIZException(1, message);
        }
        hrThirdPartyAccount.setErrorMessage(message);
        return hrThirdPartyAccount;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.NONE;
    }
}
