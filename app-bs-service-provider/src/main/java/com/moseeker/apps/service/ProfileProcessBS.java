package com.moseeker.apps.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.moseeker.common.util.StringUtils;
import com.moseeker.db.userdb.tables.UserWxUser;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;

@Service
public class ProfileProcessBS {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private  MqService.Iface mqService = ServiceManager.SERVICEMANAGER.getService(MqService.Iface.class);
	
	/**
	 * 发送消息模板给求职者
	 * @param userId
	 * @param userName
	 * @param companyId
	 * @param status
	 * @param companyName
	 * @param positionName
	 * @param applicationId
	 */
	public void sendTplToSeeker(int userId, String userName, int companyId, int status, String companyName, String positionName, int applicationId) {
		if (StringUtils.isNullOrEmpty(positionName)) {
			return;
		}
		int sysTemplateId = 9;
		String first = null, statusName = null, remark = null;
		
		String color = "#173177";
		String url = "https://platform1.dqprism.com/mobile/application?wechat_signature=YWNkNmIyYWExOGViOTRkODMyMzk5N2MxM2NkZDZlOTUxNmRjYzJiYQ==&m=checkstatus&app_id="+applicationId;
		Map<String,MessageTplDataCol> data = new HashMap<String, MessageTplDataCol>();
		switch (status) {
			case 12:
		        first = "恭喜您成功入职";
		        statusName = "成功入职";
		        remark = "入职愉快！";
		        break;
			case 13:
				first = "您好！感谢对我司的关注！但您与我司需求匹配度上存在一定差异，现将您的资料纳入公司人才储备库中，后续有机会再与您联系。";
			    statusName = "纳入人才库";
			    remark = "试试看其它职位吧！加油！";
			    break;
			case 4:
				first = "您好，您的简历已被查阅";
			    statusName = "已查看";
			    remark = "请耐心等待";
			    break;
			case 7:
				first = "恭喜你，你的简历已通过评审！";
			    statusName = "通过评审";
			    remark = "请耐心等待后续通知";
			    break;
			case 10:
			 	first = "恭喜你，面试成功！";
				statusName = "面试成功";
				remark = "请耐心等待公司后续通知";
		}
		MessageTplDataCol firstMs = new MessageTplDataCol();
		firstMs.setColor(color);
		firstMs.setValue(first);
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
        keyThreeMs.setValue(statusName);
        data.put("keyword3", keyThreeMs);
        MessageTplDataCol remarkMs = new MessageTplDataCol();
        remarkMs.setColor(color);
        remarkMs.setValue(remark);
        data.put("remark", remarkMs);
        MessageTemplateNoticeStruct templateNoticeStruct = new MessageTemplateNoticeStruct();
        templateNoticeStruct.setCompany_id(companyId);
        templateNoticeStruct.setData(data);
        templateNoticeStruct.setUser_id(userId);
        templateNoticeStruct.setSys_template_id(sysTemplateId);
        templateNoticeStruct.setUrl(url);
        try {
			mqService.messageTemplateNotice(templateNoticeStruct);
		} catch (TException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 发送消息模板给推荐人
	 * @param userName
	 * @param recomId
	 * @param openId
	 * @param positionName
	 * @param accessToken
	 */
	public void sendTplToRecomer(int status, String userName, String companyName, int recomId, String positionName, int applicationId) {
		String first = null, statusName = null, remark = null;
		int sysTemplateId = 9;
		String color = "#173177";
		String url = "https://platform1.dqprism.com/mobile/application?wechat_signature=YWNkNmIyYWExOGViOTRkODMyMzk5N2MxM2NkZDZlOTUxNmRjYzJiYQ==&m=checkstatus&app_id="+applicationId;
		Map<String,MessageTplDataCol> data = new HashMap<String, MessageTplDataCol>();
		switch (status) {
			case 12:
		        first = String.format("您推荐的{0}已经成功入职", userName);
		        statusName = "成功入职";
		        remark = "感谢您对公司人才招聘的贡献！";
		        break;
			case 13:
				first = String.format("您推荐的【{0}】经评定与我司需求匹配度上存在一定差异，现将其资料纳入公司人才库中，后续有机会再与其联系。", userName);
			    statusName = "纳入人才库";
			    remark = "感谢您对公司人才招聘的贡献！";
			    break;
			case 4:
				first = "您好，您的简历已被查阅";
			    statusName = "已查看";
			    remark = "请耐心等待";
			    break;
			case 7:
				first = "恭喜你，你的简历已通过评审！";
			    statusName = "通过评审";
			    remark = "请耐心等待后续通知";
			    break;
			case 10:
			 	first = "恭喜你，面试成功！";
				statusName = "面试成功";
				remark = "请耐心等待公司后续通知";
		}
		MessageTplDataCol firstMs = new MessageTplDataCol();
		firstMs.setColor(color);
		firstMs.setValue(first);
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
        keyThreeMs.setValue(statusName);
        data.put("keyword3", keyThreeMs);
        MessageTplDataCol remarkMs = new MessageTplDataCol();
        remarkMs.setColor(color);
        remarkMs.setValue(remark);
        MessageTemplateNoticeStruct templateNoticeStruct = new MessageTemplateNoticeStruct();
        templateNoticeStruct.setData(data);
        templateNoticeStruct.setUser_id(recomId);
        templateNoticeStruct.setSys_template_id(sysTemplateId);
        templateNoticeStruct.setUrl(url);
        try {
			mqService.messageTemplateNotice(templateNoticeStruct);
		} catch (TException e) {
			log.error(e.getMessage(), e);
		}
	}
}
