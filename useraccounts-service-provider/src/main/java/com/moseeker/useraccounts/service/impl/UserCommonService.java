package com.moseeker.useraccounts.service.impl;

import com.moseeker.baseorm.redis.RedisClient;
import java.util.HashMap;

import com.moseeker.common.annotation.iface.CounterIface;
import javax.annotation.Resource;
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
@CounterIface
public class UserCommonService {
	
	Logger logger = LoggerFactory.getLogger(UserCommonService.class);
	
	WordpressService.Iface wordpressService = ServiceManager.SERVICE_MANAGER
			.getService(WordpressService.Iface.class);

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

	/**
	 * 获取平台最新版本的内容
	 * @param form
	 * @return
	 */
	public Response newsletter(NewsletterForm form) {
		
		if (form.getAccount_id() > 0) {
			String value = redisClient.get(AppId.APPID_ALPHADOG.getValue(),
					KeyIdentifier.NEWSLETTER_HRACCOUNT_READED.toString(), String.valueOf(form.getAccount_id()));
			if (value != null) {
				NewsletterData data = JSONObject.parseObject(value, NewsletterData.class);
				if(data.getShow_new_version() == 1) {
					data.setShow_new_version((byte)0);
                    redisClient.set(AppId.APPID_ALPHADOG.getValue(),
							KeyIdentifier.NEWSLETTER_HRACCOUNT_READED.toString(), String.valueOf(form.getAccount_id()), JSON.toJSONString(data));
				}
				return ResponseUtils.success(JSON.parse(value));
			} else {
				NewsletterData data = null;
				try {
					data = wordpressService.getNewsletter(form);
					if(data != null) {
						byte showNewVersion = data.getShow_new_version();
						if(showNewVersion == 1) {
							data.setShow_new_version((byte)0);
						}
                        redisClient.set(AppId.APPID_ALPHADOG.getValue(),
								KeyIdentifier.NEWSLETTER_HRACCOUNT_READED.toString(), String.valueOf(form.getAccount_id()), JSON.toJSONString(newsletterToMap(data)));
						if(showNewVersion == 1) {
							data.setShow_new_version(showNewVersion);
						}
					}
				} catch (TException e) {
					e.printStackTrace();
					logger.error(e.getMessage(), e);
				} finally {
					//do nothing
				}
				if(data != null) {
					return ResponseUtils.success(newsletterToMap(data)); 
				} else {
					return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
				}
			}
		} else {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
		}
	}
	
	private HashMap<String, Object> newsletterToMap(NewsletterData data) {
		HashMap<String, Object> result = new HashMap<String,Object>();
		result.put("show_new_version", data.getShow_new_version());
		result.put("url", data.getUrl());
		result.put("version", data.getVersion());
		result.put("update_list", data.getUpdate_list());
		result.put("update_time", data.getUpdate_time());
		result.put("title", data.getTitle());
		return result;
	}
}
