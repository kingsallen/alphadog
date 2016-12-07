package com.moseeker.warn.service.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.email.config.EmailContent;
import com.moseeker.common.email.mail.Message;
import com.moseeker.warn.dto.Event;
import com.moseeker.warn.dto.Member;

public class OperatorEmailMessage {
    private Map<String,Object> map;
    public OperatorEmailMessage(Map<String,Object> map){
    	this.map=map;
    }
    public String Operator(){
    	Event evt=(Event) map.get("config");
    	String location=(String) map.get("location");
    	Message message=new Message();
    	String desc=evt.getEventDesc();
    	String name=evt.getEventName();
    	List<Member> list=evt.getMembers();
    	EmailContent emailContent = new EmailContent();
    	List<String> recipients=new ArrayList<String>();
    	for(Member mem:list){
    		String email=mem.getEmail();
    		recipients.add(email);    		
    	}
    	emailContent.setContent("<html><head><title>"+name+"</title></head><body>"+name+location+"</body></html>");
    	emailContent.setSenderName("zhangzeteng@moseeker.com");
		emailContent.setSenderDisplay("仟寻");
    	emailContent.setSubject("基础邮件报警系统");
    	emailContent.setSubType("html");
    	emailContent.setRecipients(recipients);
    	message.setEmailContent(emailContent);
    	message.setAppId(Constant.APPID_ALPHADOG);
    	String msg=JSON.toJSON(message).toString();
    	return msg;
    }
}
