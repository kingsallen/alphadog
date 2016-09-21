package com.moseeker.common.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.moseeker.common.email.attachment.Attachment;
import com.moseeker.common.email.config.ServerConfig;
import com.moseeker.common.util.ConfigPropertiesUtil;

/**
 * Created by chendi on 3/31/16.
 */

public class Email {

    private static ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
    private static final String serverDomain = propertiesReader.get("email.serverDomain", String.class);
    private static final Integer serverPort = propertiesReader.get("email.serverPort", Integer.class);
    private static final String userName = propertiesReader.get("email.userName", String.class);
    private static final String password = propertiesReader.get("email.password", String.class);

    private final Message message;
    
    public Email(EmailBuilder builder) {
        this.message = builder.message;
    }

    public void send() {
    	
    	new Thread(() -> {
    		 try {
				Transport transport = this.message.getSession().getTransport();
				    try {
				        transport.connect(serverDomain, serverPort, userName, password);
				        transport.sendMessage(this.message, this.message.getAllRecipients());
				    } finally {
				        transport.close();
				    }
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}).start();
    }
    
    public static class EmailBuilder {

        static private ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
        // required
        private ArrayList<String> recipients = new ArrayList<>();

        // optional
        private String senderAddress = propertiesReader.get("email.senderAddress", String.class);
        private String subject = "";
        private String content = "";
        private ArrayList<Attachment> attachments = new ArrayList<>();

        private Message message;
        private ServerConfig serverConfig;

        public EmailBuilder(List<String> recipients) throws MessagingException {
            this.recipients.addAll(recipients);
            serverConfig = new ServerConfig(serverDomain, serverPort, userName, password);
        }

        public EmailBuilder(String recipient) throws MessagingException {
            this.recipients.add(recipient);
            serverConfig = new ServerConfig(serverDomain, serverPort, userName, password);
        }
        
        public EmailBuilder(ServerConfig serverConfig) throws MessagingException {
            this.serverConfig = serverConfig;
        }

        public EmailBuilder setSender(String sender) {
            this.senderAddress = sender;
            return this;
        }

        public EmailBuilder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public EmailBuilder setContent(String content) {
            this.content = content;
            return this;
        }

        public EmailBuilder addRecipient(String recipient) {
            this.recipients.add(recipient);
            return this;
        }

        public EmailBuilder addRecipients(List<String> recipients) {
            this.recipients.addAll(recipients);
            return this;
        }

        public EmailBuilder addAttachment(Attachment attachment) {
            this.attachments.add(attachment);
            return this;
        }

        public Email build() throws Exception {
            this.message = this.initMessage();
            this.buildHeader().buildContent().buildAttachment();
            this.message.saveChanges();
            return new Email(this);
        }

        private EmailBuilder buildHeader() throws Exception {
            this.message.setFrom(new InternetAddress(this.senderAddress));
            this.message.setSubject(this.subject);
            ArrayList<InternetAddress> recipients = new ArrayList<>();
            for (String recipient : this.recipients) {
                recipients.add(new InternetAddress(recipient));
            }
            this.message.setRecipients(RecipientType.TO, recipients.toArray(new InternetAddress[this.recipients.size()]));
            return this;
        }

        private EmailBuilder buildContent() throws Exception {
            Multipart content = (Multipart) this.message.getContent();
            MimeBodyPart body = new MimeBodyPart();
            content.addBodyPart(body);
            body.setText(this.content, "utf-8", "html");
            return this;
        }

        private EmailBuilder buildAttachment() throws Exception {
            Multipart content = (Multipart) this.message.getContent();
            for (Attachment attachment : this.attachments) {
                content.addBodyPart(attachment.getAttachment());
            }
            return this;
        }

        private Message initMessage() throws MessagingException {
            Properties properties = new Properties();
            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.transport.protocol", "smtp");
            Session session = Session.getDefaultInstance(properties);
            Message message = new MimeMessage(session);
            Multipart multipart = new MimeMultipart("mixed");
            message.setContent(multipart);
            return message;
        }

    }

}
