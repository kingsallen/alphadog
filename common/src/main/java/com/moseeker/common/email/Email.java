package com.moseeker.common.email;

import com.moseeker.common.util.ConfigPropertiesUtil;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;
import java.util.ArrayList;
import com.moseeker.common.email.attachment.Attachment;

/**
 * Created by chendi on 3/31/16.
 */

public class Email {
    private static String senderAddress = null;
    private static String serverDomain = null;
    private static Integer serverPort = null;
    private static String userName = null;
    private static String password = null;

    static {
        try {
            ConfigPropertiesUtil emailPropertiesReader = EmailPropertiesReader.getEmailPropertiesReader();
            senderAddress = emailPropertiesReader.get("email.senderAddress", String.class);
            serverDomain = emailPropertiesReader.get("email.serverDomain", String.class);
            serverPort = emailPropertiesReader.get("email.serverPort", Integer.class);
            userName = emailPropertiesReader.get("email.userName", String.class);
            password = emailPropertiesReader.get("email.password", String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Message message;
    private ArrayList<InternetAddress> recipients;

    public Email() throws MessagingException {

        this.message = this.generateMessage();
        this.recipients = new ArrayList<InternetAddress>();

    }

    private Message generateMessage() throws MessagingException {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.transport.protocol", "smtp");

        Session session = Session.getDefaultInstance(properties);
        session.setDebug(true); // debug mode

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderAddress)); // header -- from

        Multipart multipart = new MimeMultipart("mixed");
        message.setContent(multipart);

        return message;
    }

    // header -- subject
    public Email setSubject(String subject) throws MessagingException {
        this.message.setSubject(subject);
        return this;
    }

    // header - recipient
    public Email addRecipient(String recipientEmailAddress) throws AddressException {
        this.recipients.add(new InternetAddress(recipientEmailAddress));
        return this;
    }

    public Email addRecipients(List<String> recipientsEmailAddresses) throws AddressException {
        ArrayList<InternetAddress> recipientsInternetAddresses = new ArrayList<>();

        for(String recipientEmailAddress: recipientsEmailAddresses) {
            recipientsInternetAddresses.add(new InternetAddress(recipientEmailAddress));
        }

        this.recipients.addAll(recipientsInternetAddresses);
        return this;
    }

    // email content body
    public Email setBody(String text) throws Exception {
        MimeBodyPart body = new MimeBodyPart();
        ((Multipart)this.message.getContent()).addBodyPart(body);
        body.setText(text, "utf-8", "html");
        return this;
    }

    // email attachment
    public Email addAttachment(Attachment attachment) throws Exception {
        ((Multipart)this.message.getContent()).addBodyPart(attachment.getAttachment());
        this.message.saveChanges();
        return this;
    }

    public void send() throws MessagingException {
        // TODO: validate email settings
        this.message.setRecipients(RecipientType.TO, this.recipients.toArray(new InternetAddress[this.recipients.size()]));
        Transport transport = this.message.getSession().getTransport();
        try {
            transport.connect(serverDomain, serverPort, userName, password);
            transport.sendMessage(this.message, this.message.getAllRecipients());
        } finally {
            transport.close();
        }
    }

}

class EmailPropertiesReader {

    private static String emailConfigPropertiesFileName = "emailConfig.properties";

    public static ConfigPropertiesUtil getEmailPropertiesReader() throws Exception {
        ConfigPropertiesUtil emailConfigPropertiesUtil = ConfigPropertiesUtil.getInstance();
        emailConfigPropertiesUtil.loadResource(emailConfigPropertiesFileName);
        return emailConfigPropertiesUtil;
    }

}

