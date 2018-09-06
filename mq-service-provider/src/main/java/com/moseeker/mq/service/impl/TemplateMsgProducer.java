package com.moseeker.mq.service.impl;

import com.moseeker.baseorm.dao.hrdb.HrWxNoticeMessageDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.HrWxNoticeMessage;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.exception.RedisException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.mq.service.WarnService;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxNoticeMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import java.util.UUID;
import javax.annotation.Resource;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息队列服务
 *
 * Created by zzh on 16/8/3.
 */
@Service
@CounterIface
public class TemplateMsgProducer {

	Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @Autowired
    private HrWxWechatDao hrWxWechatDao;

    @Autowired
    private HrWxNoticeMessageDao noticeMessageDao;

    @Autowired
	TemlateMsgHttp temlateMsgHttp;

	/**
	 * 消息模板通知接口
	 *
	 * @param messageTemplateNoticeStruct
	 *            通知需要的数据
	 * @return
	 * @throws TException
	 */
	public Response messageTemplateNotice(MessageTemplateNoticeStruct messageTemplateNoticeStruct) {
		try {
			Response response = validateMessageTemplateNotice(messageTemplateNoticeStruct);
			if (response.status > 0) {
				return response;
			}
            Query query = new Query.QueryBuilder().where(HrWxWechat.HR_WX_WECHAT.COMPANY_ID.getName(),messageTemplateNoticeStruct.getCompany_id())
                    .buildQuery();
            HrWxWechatDO hrWxWechatDO = hrWxWechatDao.getData(query);
            if(hrWxWechatDO == null )
                return ResponseUtils.fail(ConstantErrorCodeMessage.MQ_WECHAT_NOT_INFO);
            Query noticeQuery = new Query.QueryBuilder().where(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.WECHAT_ID.getName(),hrWxWechatDO.getId())
                    .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.NOTICE_ID.getName(),messageTemplateNoticeStruct.getSys_template_id())
                    .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.DISABLE.getName(),"0")
                    .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.STATUS.getName(),"0")
                    .buildQuery();
            HrWxNoticeMessageDO noticeMessageDO = noticeMessageDao.getData(noticeQuery);
            if(noticeMessageDO != null){
                return  ResponseUtils.fail(ConstantErrorCodeMessage.MQ_TEMPLATE_NOTICE_CLOSE);
            }
			messageTemplateNoticeStruct.setId(UUID.randomUUID().toString()+System.currentTimeMillis());
			String json = BeanUtils.convertStructToJSON(messageTemplateNoticeStruct);
			if (messageTemplateNoticeStruct.getDelay() > 0) {
				redisClient.zadd(AppId.APPID_ALPHADOG.getValue(),
						KeyIdentifier.MQ_MESSAGE_NOTICE_TEMPLATE_DELAY.toString(),
						messageTemplateNoticeStruct.getDelay()*1000+System.currentTimeMillis(), json);
				return ResponseUtils.success(null);
			} else {
				Long res = redisClient.lpush(Constant.APPID_ALPHADOG,
						Constant.REDIS_KEY_IDENTIFIER_MQ_MESSAGE_NOTICE_TEMPLATE, json);
				if (res != null) {
					return ResponseUtils.success(res);
				}
			}
		} catch (RedisException e) {
			logger.error(e.getMessage(),e);
			WarnService.notify(e);
		} catch (Exception e) {
			logger.error("MqServiceImpl messageTemplateNotice error: ", e);
		} finally {
			// do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
	}

	/**
	 * 消息模板通知-数据校验
	 *
	 * @param messageTemplateNoticeStruct
	 * @return
	 */
	private Response validateMessageTemplateNotice(MessageTemplateNoticeStruct messageTemplateNoticeStruct) {
		Response response = new Response(0, "ok");

		if (messageTemplateNoticeStruct.getUser_id() == 0&&messageTemplateNoticeStruct.getWx_id()==0) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "user_id"));
		}
		if (messageTemplateNoticeStruct.getSys_template_id() == 0) {
			return ResponseUtils
					.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "sys_template_id"));
		}
		if (messageTemplateNoticeStruct.getCompany_id() == 0) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "company_id"));
		}
		if (messageTemplateNoticeStruct.getData() == null || messageTemplateNoticeStruct.getData().isEmpty()) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "data"));
		}

		return response;
	}
}
