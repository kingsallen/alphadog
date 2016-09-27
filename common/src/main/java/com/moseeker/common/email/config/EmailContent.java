package com.moseeker.common.email.config;

import java.util.List;

import com.moseeker.common.email.attachment.Attachment;

/**
 * 
 * 邮件内容
 * <p>Company: MoSeeker</P>  
 * <p>date: Sep 21, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class EmailContent {

	private String sender;							//发送者
	private List<String> recipients;				//接收者
	private String content;							//邮件内容
	private String subject;							//邮件标题
	private List<Attachment> attachments;			//附件
	
	private String charset = "utf-8";				//编码格式
	private String subType = "text/html";				//邮件内容格式
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public List<String> getRecipients() {
		return recipients;
	}
	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
}
