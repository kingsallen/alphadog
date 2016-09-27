package com.moseeker.common.email.mail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.common.email.attachment.Attachment;
import com.moseeker.common.email.config.EmailContent;
import com.moseeker.common.email.config.EmailSessionConfig;
import com.moseeker.common.email.config.ServerConfig;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.StringUtils;

/**
 * Created by chendi on 3/31/16.
 * 
 * 基于迪迪在3月31号提交的email发送，将一些可配置信息提出作为参数设置，即可以配置指定邮件服务器。并为发送邮件添加了一个线程池。
 * <p>Company: MoSeeker</P>  
 * <p>date: Sep 21, 2016</p>  
 */
public class Mail {
	
	private static Logger logger = LoggerFactory.getLogger(Mail.class);
	
	private static ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
	private static final String serverDomain = propertiesReader.get("email.serverDomain", String.class);
    private static final Integer serverPort = propertiesReader.get("email.serverPort", Integer.class);
    private static final String userName = propertiesReader.get("email.userName", String.class);
    private static final String password = propertiesReader.get("email.password", String.class);
    private static final String sender = propertiesReader.get("email.senderAddress", String.class);

    private final Message message;				//邮件
    private final ServerConfig serverConfig;			//服务器配置
    
    //利用构造者模式创建邮件类
    public Mail(MailBuilder builder) {
        this.message = builder.message;
        this.serverConfig = builder.serverConfig;
    }
    
    //将构造好的邮件发送到邮件服务器
    public void send() {
		 try {
			Transport transport = this.message.getSession().getTransport();
			    try {
			        transport.connect(serverConfig.getHost(), serverConfig.getPort(), serverConfig.getUsername(), serverConfig.getPassword());
			        transport.sendMessage(this.message, this.message.getAllRecipients());
			    } finally {
			        transport.close();
			        logger.info("from:"+message.getFrom() +" to:"+message.getAllRecipients()+" topic:"+message.getSubject());
			    }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
    }
    
    /**
     * 发送一封邮件
     * @param emailContent 邮件内容
     * @throws AddressException 邮件地址异常
     * @throws MessagingException	邮件消息异常
     * @throws IOException	IO相关的异常
     */
    public void send(EmailContent emailContent) throws AddressException, MessagingException, IOException {
		try {
			buildHeader(message, emailContent);
		    buildContent(message, emailContent);
		    buildAttachment(message, emailContent);
		    message.saveChanges();
			Transport transport = this.message.getSession().getTransport();
			    try {
			        transport.connect(serverConfig.getHost(), serverConfig.getPort(), serverConfig.getUsername(), serverConfig.getPassword());
			        transport.sendMessage(this.message, this.message.getAllRecipients());
			    } finally {
			        transport.close();
			        logger.info("from:"+emailContent.getSender() +" to:"+emailContent.getRecipients()+" topic:"+emailContent.getSubject());
			    }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
    }
    
    /**
     * 
     * 邮件构造者
     * <p>Company: MoSeeker</P>  
     * <p>date: Sep 21, 2016</p>  
     * <p>Email: wjf2255@gmail.com</p>
     * @author wjf
     * @version
     */
    public static class MailBuilder {

        private Message message;					//邮件消息
        private ServerConfig serverConfig;			//服务器配置信息
        private EmailSessionConfig sessionConfig;	//连接配置
        
        /**
         * 根据默认邮件服务器生成邮件构造器
         * @throws MessagingException
         */
        public MailBuilder() throws MessagingException {
        	serverConfig = new ServerConfig(serverDomain, serverPort, userName, password);
        	
        }

        /**
         * 利用指定邮件服务器配置信息 生成邮件构造器
         * @param serverConfig
         * @throws MessagingException
         */
        public MailBuilder(ServerConfig serverConfig) throws MessagingException {
            this.serverConfig = serverConfig;
        }
        
        /**
         * 配置session信息
         * @param sessionConfig
         * @return
         */
        public MailBuilder buildSessionConfig(EmailSessionConfig sessionConfig) {
            this.sessionConfig = sessionConfig;
            return this;
        }
        
        /**
         * 配置邮件内容
         * @param emailContent
         * @return
         * @throws Exception
         */
        public MailBuilder buildEmailContent(EmailContent emailContent) throws Exception {
        	buildHeader(emailContent).buildContent(emailContent).buildAttachment(emailContent);
        	this.message.saveChanges();
            return this;
        }
        
        /**
         * 创建邮件
         * @param emailContent
         * @return
         * @throws IOException 
         * @throws Exception
         */
        public Mail build(EmailContent emailContent) throws IOException, Exception {
            this.message = this.initMessage();
            this.buildHeader(emailContent).buildContent(emailContent).buildAttachment(emailContent);
            this.message.saveChanges();
            return new Mail(this);
        }
        
        /**
         * 创建邮件
         * @param emailContent
         * @return
         * @throws MessagingException 
         * @throws Exception
         */
        public Mail buildMailServer() throws MessagingException {
            this.message = this.initMessage();
            this.message.saveChanges();
            return new Mail(this);
        }

        /**
         * 创建邮件标题、发件人、收件人
         * @param emailContent
         * @return
         * @throws MessagingException 
         * @throws AddressException 
         * @throws UnsupportedEncodingException 
         * @throws Exception
         */
        private MailBuilder buildHeader(EmailContent emailContent) throws AddressException, MessagingException, UnsupportedEncodingException {
        	Mail.buildHeader(this.message, emailContent);
            return this;
        }

        /**
         * 创建邮件内容
         * @param emailContent
         * @return
         * @throws MessagingException 
         * @throws IOException 
         * @throws Exception
         */
        private MailBuilder buildContent(EmailContent emailContent) throws IOException, MessagingException {
            Mail.buildContent(message, emailContent);
            return this;
        }

        /**
         * 创建附件
         * @param emailContent
         * @return
         * @throws Exception
         */
        private MailBuilder buildAttachment(EmailContent emailContent) throws Exception {
        	Mail.buildAttachment(message, emailContent);
            return this;
        }

        /**
         * 初始化邮件内容
         * @return
         * @throws MessagingException
         */
        private Message initMessage() throws MessagingException {
            Properties properties = new Properties();
            if(sessionConfig == null) {
            	properties.setProperty("mail.smtp.auth", "true");
                properties.setProperty("mail.transport.protocol", "smtp");
            } else {
            	properties.setProperty("mail.smtp.auth", String.valueOf(sessionConfig.isAuth()));
                properties.setProperty("mail.transport.protocol", sessionConfig.getProtocol());
            }
            Session session = Session.getDefaultInstance(properties);
            Message message = new MimeMessage(session);
            Multipart multipart = new MimeMultipart("mixed");
            message.setContent(multipart);
            return message;
        }
    }
   
    /**
     * 创建邮件标题、收件人、发件人
     * @param message
     * @param emailContent
     * @throws AddressException
     * @throws MessagingException
     * @throws UnsupportedEncodingException 
     */
    private static void buildHeader(Message message, EmailContent emailContent) throws AddressException, MessagingException, UnsupportedEncodingException {
    	if(StringUtils.isNullOrEmpty(emailContent.getSender())) {
    		message.setFrom(new InternetAddress(sender));
    	} else {
    		message.setFrom(new InternetAddress(emailContent.getSender()));
    		//message.setFrom(new InternetAddress("仟寻 <info@moseeker.net>", "仟寻 <info@moseeker.net>"));
    		//message.setFrom(new InternetAddress(MimeUtility.mimeCharset("仟寻 <info@moseeker.net>")));
    		//message.setFrom(new InternetAddress(MimeUtility.encodeText("仟寻 <info@moseeker.net>",MimeUtility.mimeCharset("UTF-8"), null)));
    	}
    	if(StringUtils.isNotNullOrEmpty(emailContent.getSubject())) {
    		message.setSubject(emailContent.getSubject());
    	} else {
    		message.setSubject(Constant.EMAIL_VERIFIED_SUBJECT);
    	}
    	ArrayList<InternetAddress> recipients = new ArrayList<>();
    	for (String recipient : emailContent.getRecipients()) {
    		recipients.add(new InternetAddress(recipient));
    	}
    	message.setRecipients(RecipientType.TO, recipients.toArray(new InternetAddress[emailContent.getRecipients().size()]));
    }
    
    /**
     * 配置邮件内容
     * @param message
     * @param emailContent
     * @throws IOException
     * @throws MessagingException
     */
    private static void buildContent(Message message, EmailContent emailContent) throws IOException, MessagingException {
    	 Multipart content = (Multipart) message.getContent();
         MimeBodyPart body = new MimeBodyPart();
         content.addBodyPart(body);
         body.setText(emailContent.getContent(), emailContent.getCharset(), emailContent.getSubType());
    }
    
    /**
     * 配置附件信息
     * @param message
     * @param emailContent
     * @throws IOException
     * @throws MessagingException
     */
    private static void buildAttachment(Message message, EmailContent emailContent) throws IOException, MessagingException {
        if(emailContent.getAttachments() != null) {
        	Multipart content = (Multipart) message.getContent();
        	for (Attachment attachment : emailContent.getAttachments()) {
                content.addBodyPart(attachment.getAttachment());
            }
        }
    }
}
