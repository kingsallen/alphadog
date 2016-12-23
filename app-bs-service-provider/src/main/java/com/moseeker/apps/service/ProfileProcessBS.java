package com.moseeker.apps.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.moseeker.apps.constants.TemplateMs;
import com.moseeker.apps.constants.TemplateMs.MsInfo;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;

@Service
public class ProfileProcessBS {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private  MqService.Iface mqService = ServiceManager.SERVICEMANAGER.getService(MqService.Iface.class);
	
	/**
	 * 发送消息模板
	 * @param userId
	 * @param userName
	 * @param companyId
	 * @param status
	 * @param companyName
	 * @param positionName
	 * @param applicationId
	 * @param tm
	 */
	public void sendTplToSeeker(int userId, String userName, int companyId, int status, String companyName, String positionName, int applicationId, TemplateMs tm) {
		if (StringUtils.isNullOrEmpty(positionName)) {
			return;
		}
		String color = "#173177";
		String url = "https://platform1.dqprism.com/mobile/application?wechat_signature=YWNkNmIyYWExOGViOTRkODMyMzk5N2MxM2NkZDZlOTUxNmRjYzJiYQ==&m=checkstatus&app_id="+applicationId;
		Map<String,MessageTplDataCol> data = new HashMap<String, MessageTplDataCol>();
		MsInfo msInfo = tm.processStatus(status, userName);
		if (msInfo != null) {
			MessageTplDataCol firstMs = new MessageTplDataCol();
			firstMs.setColor(color);
			firstMs.setValue(msInfo.getResult());
	        data.put("first", firstMs);
	        MessageTplDataCol keyOneMs = new MessageTplDataCol();
	        keyOneMs.setColor(color);
	        keyOneMs.setValue(companyName);
	        data.put("keyword1", keyOneMs);
	        MessageTplDataCol keyTwoMs = new MessageTplDataCol();
	        keyTwoMs.setColor(color);
	        keyTwoMs.setValue(positionName);
	        data.put("keyword2", keyTwoMs);
	        MessageTplDataCol keyThreeMs = new MessageTplDataCol();
	        keyThreeMs.setColor(color);
	        keyThreeMs.setValue(msInfo.getResult());
	        data.put("keyword3", keyThreeMs);
	        MessageTplDataCol remarkMs = new MessageTplDataCol();
	        remarkMs.setColor(color);
	        remarkMs.setValue(msInfo.getRemark());
	        data.put("remark", remarkMs);
	        MessageTemplateNoticeStruct templateNoticeStruct = new MessageTemplateNoticeStruct();
	        templateNoticeStruct.setCompany_id(companyId);
	        templateNoticeStruct.setData(data);
	        templateNoticeStruct.setUser_id(userId);
	        templateNoticeStruct.setSys_template_id(tm.getSystemlateId());
	        templateNoticeStruct.setUrl(url);
	        try {
				mqService.messageTemplateNotice(templateNoticeStruct);
			} catch (TException e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	
}
