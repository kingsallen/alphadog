package com.moseeker.servicemanager.web.controller.useraccounts;

import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moseeker.common.annotation.iface.CounterIface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;

@Controller
@CounterIface
public class UserWxUserController {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private UserWxUserDao wxuser;
	
	@RequestMapping(value = "user/wxuser", method=RequestMethod.GET)
	@ResponseBody
	public String getUserWxUser(HttpServletRequest request, HttpServletResponse response) {
		// PrintWriter writer = null;
		try {
			// GET方法 通用参数解析并赋值
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);

            UserWxUserRecord wxUserRecord = wxuser.getRecord(QueryConvert.commonQueryConvertToQuery(query));
            if (wxUserRecord != null) {
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("id", wxUserRecord.getId().longValue());
                result.put("wechat_id", wxUserRecord.getWechatId().intValue());
                result.put("group_id", wxUserRecord.getGroupId().intValue());
                result.put("sysuser_id", wxUserRecord.getSysuserId().intValue());
                result.put("is_subscribe", wxUserRecord.getIsSubscribe().byteValue());
                result.put("openid", wxUserRecord.getOpenid());
                result.put("nickname", wxUserRecord.getNickname());
                result.put("sex", wxUserRecord.getSex().intValue());
                result.put("city", wxUserRecord.getCity());
                result.put("country", wxUserRecord.getCountry());
                result.put("province", wxUserRecord.getProvince());
                result.put("language", wxUserRecord.getLanguage());
                result.put("headimgurl", wxUserRecord.getHeadimgurl());
                result.put("subscribe_time", wxUserRecord.getSubscribeTime());
                result.put("unsubscibe_time", wxUserRecord.getUnsubscibeTime());
                result.put("unionid", wxUserRecord.getUnionid());
                result.put("reward", wxUserRecord.getReward().intValue());
                result.put("auto_sync_info", wxUserRecord.getAutoSyncInfo().byteValue());
                result.put("create_time", wxUserRecord.getCreateTime());
                result.put("update_time", wxUserRecord.getUpdateTime());
                result.put("source", wxUserRecord.getSource().byteValue());
                return ResponseLogNotification.success(request, ResponseUtils.success(result));
            } else {
                return ResponseLogNotification.fail(request, ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY));
            }
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}
}
