package com.moseeker.useraccounts.service.impl;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.foundation.wordpress.service.WordpressService;
import com.moseeker.thrift.gen.foundation.wordpress.struct.NewsletterData;
import com.moseeker.thrift.gen.foundation.wordpress.struct.NewsletterForm;

/**
 * 
 * B端帐号和C端帐号的通用服务 
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 10, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
@Service
public class UserCommonService {
	
	Logger logger = LoggerFactory.getLogger(UserCommonService.class);
	
	WordpressService.Iface wordpressService = ServiceManager.SERVICEMANAGER
			.getService(WordpressService.Iface.class);

	/**
	 * 获取平台最新版本的内容
	 * @param form
	 * @return
	 */
	public Response newsletter(NewsletterForm form) {
		if (form.getAccount_id() > 0) {
			RedisClient redis = RedisClientFactory.getCacheClient();
			String value = redis.get(AppId.APPID_ALPHADOG.getValue(),
					KeyIdentifier.NEWSLETTER_HRACCOUNT_READED.toString(), String.valueOf(form.getAccount_id()));
			if (value != null) {
				NewsletterData data = JSONObject.parseObject(value, NewsletterData.class);
				if(data.getShow_new_version() == 1) {
					data.setShow_new_version((byte)0);
				}
				redis.set(AppId.APPID_ALPHADOG.getValue(),
					KeyIdentifier.NEWSLETTER_HRACCOUNT_READED.toString(), String.valueOf(form.getAccount_id()), JSON.toJSONString(data));
				return ResponseUtils.success(value);
			} else {
				NewsletterData data = null;
				try {
					data = wordpressService.getNewsletter(form);
				} catch (TException e) {
					e.printStackTrace();
					logger.error(e.getMessage(), e);
				}
				if(data != null) {
					return ResponseUtils.success(data); 
				} else {
					return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXHAUSTED);
				}
			}
		}
		return null;
	}
}
