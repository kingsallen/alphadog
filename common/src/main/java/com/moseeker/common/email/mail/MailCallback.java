package com.moseeker.common.email.mail;

import com.moseeker.common.email.config.EmailContent;

/**
 * 
 * 用于邮件内容生成比较费时的创建邮件方式 
 * <p>Company: MoSeeker</P>  
 * <p>date: Sep 26, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
@FunctionalInterface
public interface MailCallback {

	public EmailContent buildContent(String redisMsg);
}
