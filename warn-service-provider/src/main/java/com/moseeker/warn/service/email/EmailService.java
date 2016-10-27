package com.moseeker.warn.service.email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.moseeker.common.email.config.EmailSessionConfig;
import com.moseeker.common.email.mail.Mail;
import com.moseeker.common.email.mail.Message;
import com.moseeker.common.email.mail.Mail.MailBuilder;

public class EmailService {
	private static Logger logger = LoggerFactory.getLogger(EmailService.class);
	private String msg;
	public EmailService(String Msg){
		this.msg=Msg;
		
	}
	public void send(){
			try {
				    Message message = JSON.parseObject(msg, Message.class);
					EmailSessionConfig sessionConfig = new EmailSessionConfig(true, "smtp");
					MailBuilder mailBuilder = new MailBuilder();
					mailBuilder.buildSessionConfig(sessionConfig).build(message.getEmailContent());
					logger.info("msg=========:"+msg);
					Mail mail=new Mail(mailBuilder);
					mail.send();
				}catch(Exception e){
					e.printStackTrace();
				}

	}

}
