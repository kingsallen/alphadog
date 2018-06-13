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
import com.moseeker.useraccounts.service.thirdpartyaccount.base.IBindRequest;
import com.moseeker.useraccounts.utils.HttpClientUtil;
import com.moseeker.useraccounts.utils.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class LiePinUserAccountBindHandler implements IBindRequest{

    private Logger logger = LoggerFactory.getLogger(LiePinUserAccountBindHandler.class);

    @Override
    public HrThirdPartyAccountDO bind(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) throws Exception {
        try{
            String username = hrThirdPartyAccount.getUsername();
            String password = hrThirdPartyAccount.getPassword();

            String resultJson = sendRequest2Liepin(username, password);

            logger.info("==============LiePinBindResultJson:{}================", resultJson);

            if(StringUtils.isNullOrEmpty(resultJson)){
                logger.info("================用户绑定时http请求结果为空=================");
                throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, "用户绑定时http请求结果为空");
            }
            JSONObject result = JSONObject.parseObject(resultJson);

            // 请求结果处理
            if("0".equals(String.valueOf(result.get("code")))){
                JSONObject userInfo = JSONObject.parseObject(result.getString("data"));
                String userId = userInfo.getString("usere_id");
                String token = userInfo.getString("token");
                hrThirdPartyAccount.setExt(token);
                hrThirdPartyAccount.setExt2(userId);
                hrThirdPartyAccount.setBinding((short) BindingStatus.BOUND.getValue());

                logger.info("==================请求绑定成功，hrThirdPartyAccount:{}==================", hrThirdPartyAccount);
            }else{

                throw new BIZException(Integer.parseInt(String.valueOf(result.get("code"))), String.valueOf(result.get("message")));
            }
        }catch (BIZException e){

            logger.info("=================errormsg:{}===================", e.getMessage());
            e.printStackTrace();
            hrThirdPartyAccount.setBinding((short) BindingStatus.ERROR.getValue());
            hrThirdPartyAccount.setErrorMessage(e.getMessage());
        }catch (Exception e){

            logger.info("=================猎聘对接用户绑定时后台异常===================");
            e.printStackTrace();
            hrThirdPartyAccount.setBinding((short)BindingStatus.ERROR.getValue());
            hrThirdPartyAccount.setErrorMessage(BindThirdPart.BIND_TIMEOUT_MSG);
        }
        return hrThirdPartyAccount;
    }

    public String sendRequest2Liepin(String username, String password) throws Exception {

        // 构造请求数据
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("usere_login", username);
        requestMap.put("password", password);
        String t = new SimpleDateFormat("yyyyMMdd").format(new Date());
        requestMap.put("t", t);

        //生成签名
        String sign = Md5Utils.getMD5SortKey(Md5Utils.mapToList(requestMap), requestMap);
        requestMap.put("sign", sign);

        //设置请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("channel", "qianxun");

        //发送请求
        return HttpClientUtil.sentHttpPostRequest(UserAccountConstant.LP_USER_BIND_URL, headers, requestMap);
    }


    @Override
    public ChannelType getChannelType() {
        return ChannelType.LIEPIN;
    }
}
