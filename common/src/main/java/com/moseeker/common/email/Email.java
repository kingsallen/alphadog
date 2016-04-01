package com.moseeker.common.email;

import com.moseeker.common.util.ConfigPropertiesUtil;

import java.util.List;
import java.util.Properties;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.Message;
import java.util.ArrayList;
import java.util.stream.Collector;

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
        session.setDebug(true);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderAddress));
        return message;
    }

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

    public Email setText(String text) throws MessagingException {
        this.message.setText(text);
        return this;
    }

    public Email setSubject(String subject) throws MessagingException {
        this.message.setSubject(subject);
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

