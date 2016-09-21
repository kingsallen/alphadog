package com.moseeker.common.email.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

import com.moseeker.common.email.attachment.Attachment;
import com.moseeker.common.email.config.EmailContent;
import com.moseeker.common.email.config.EmailPoolConfig;
import com.moseeker.common.email.config.EmailSessionConfig;
import com.moseeker.common.email.config.ServerConfig;
import com.moseeker.common.util.ConfigPropertiesUtil;

/**
 * Created by chendi on 3/31/16.
 * 
 * 基于迪迪在3月31号提交的email发送，将一些可配置信息，提出作为参数设置。并为发送邮件添加了一个线程池。
 * <p>Company: MoSeeker</P>  
 * <p>date: Sep 21, 2016</p>  
 */
public class Mail {
	
	private static ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
	private static final String serverDomain = propertiesReader.get("email.serverDomain", String.class);
    private static final Integer serverPort = propertiesReader.get("email.serverPort", Integer.class);
    private static final String userName = propertiesReader.get("email.userName", String.class);
    private static final String password = propertiesReader.get("email.password", String.class);

    private final Message message;				//邮件
    private ServerConfig serverConfig;			//服务器配置
    private ExecutorService executorService;	//线程池
    
    //利用构造者模式创建邮件类
    public Mail(MailBuilder builder) {
        this.message = builder.message;
        serverConfig = builder.serverConfig;
        executorService = builder.executorService;
    }

    //将构造好的邮件发送到邮件服务器
    public void send() {
    	executorService.submit(() -> {
    		 try {
				Transport transport = this.message.getSession().getTransport();
				    try {
				        transport.connect(serverConfig.getHost(), serverConfig.getPort(), serverConfig.getUsername(), serverConfig.getPassword());
				        transport.sendMessage(this.message, this.message.getAllRecipients());
				    } finally {
				        transport.close();
				    }
			} catch (Exception e) {
				e.printStackTrace();
			}
    	});
    }
    
    //发送一封邮件
    public void send(EmailContent emailContent) throws AddressException, MessagingException, IOException {
    	executorService.submit(() -> {
    		try {
    			buildHeader(message, emailContent);
    		    buildContent(message, emailContent);
    		    buildAttachment(message, emailContent);
				Transport transport = this.message.getSession().getTransport();
				    try {
				        transport.connect(serverConfig.getHost(), serverConfig.getPort(), serverConfig.getUsername(), serverConfig.getPassword());
				        transport.sendMessage(this.message, this.message.getAllRecipients());
				    } finally {
				        transport.close();
				    }
			} catch (Exception e) {
				e.printStackTrace();
			}
    	});
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
        
        private ExecutorService executorService = null;	//发送邮件的线程池
        
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
         * 利用指定的邮件服务器配置和线程池配置信息生成邮件构造器
         * @param serverConfig
         * @param poolConfig
         * @throws MessagingException
         */
        public MailBuilder(ServerConfig serverConfig, EmailPoolConfig poolConfig) throws MessagingException {
            this.serverConfig = serverConfig;
            executorService = new ThreadPoolExecutor(poolConfig.getCorePoolSize(), poolConfig.getMaximumPoolSize(),
            		poolConfig.getKeepAliveTime(), poolConfig.getUnit(),
            		poolConfig.getWorkQueue());
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
            return this;
        }
        
        /**
         * 创建邮件
         * @param emailContent
         * @return
         * @throws Exception
         */
        public Mail build(EmailContent emailContent) throws Exception {
            this.message = this.initMessage();
            this.buildHeader(emailContent).buildContent(emailContent).buildAttachment(emailContent);
            this.message.saveChanges();
            if(executorService == null) {
            	  executorService = new ThreadPoolExecutor(3, 10,
                          60L, TimeUnit.MILLISECONDS,
                          new LinkedBlockingQueue<Runnable>());
            }
            return new Mail(this);
        }

        /**
         * 创建邮件标题、发件人、收件人
         * @param emailContent
         * @return
         * @throws Exception
         */
        private MailBuilder buildHeader(EmailContent emailContent) throws Exception {
        	Mail.buildHeader(this.message, emailContent);
            return this;
        }

        /**
         * 创建邮件内容
         * @param emailContent
         * @return
         * @throws Exception
         */
        private MailBuilder buildContent(EmailContent emailContent) throws Exception {
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
     */
    private static void buildHeader(Message message, EmailContent emailContent) throws AddressException, MessagingException {
    	 message.setFrom(new InternetAddress(emailContent.getSender()));
         message.setSubject(emailContent.getSubject());
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
    	Multipart content = (Multipart) message.getContent();
        
        for (Attachment attachment : emailContent.getAttachments()) {
            content.addBodyPart(attachment.getAttachment());
        }
    }
}
