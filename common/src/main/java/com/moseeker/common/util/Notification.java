package com.moseeker.common.util;

import static com.moseeker.common.redis.cache.db.configdb.tables.AdminnotificationEvents.ADMINNOTIFICATION_EVENTS;
import static com.moseeker.common.redis.cache.db.configdb.tables.AdminnotificationGroupmembers.ADMINNOTIFICATION_GROUPMEMBERS;
import static com.moseeker.common.redis.cache.db.configdb.tables.AdminnotificationMembers.ADMINNOTIFICATION_MEMBERS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Exchanger;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import com.moseeker.common.dbconnection.DatabaseConnectionHelper;
import com.moseeker.common.email.Email;

public class Notification {

    public static void sendMyCatConnectionError(String errorMessage) {
        ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
        String subject = propertiesReader.get("mycat.error.subject", String.class);
        String content = propertiesReader.get("mycat.error.content", String.class);
        List<String> recipients = Arrays.asList(propertiesReader.get("mycat.error.recipients", String.class).split(","));
        try {
            Email mycatConnectionErrorEmail = new Email();
            mycatConnectionErrorEmail.setSubject(subject)
                    .setBody(content + ":\n" + errorMessage)
                    .addRecipients(recipients)
                    .send();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void sendNotification(int appid, String event_key, String event_details) {
        String notificationText = "项目" + appid + " 发生异常，" + event_details;
        DSLContext create;
        try {
            create = DatabaseConnectionHelper.getConnection().getJooqDSL();
            Record result = create.select().from(ADMINNOTIFICATION_EVENTS)
                    .where(ADMINNOTIFICATION_EVENTS.EVENT_KEY.equal(event_key)).fetchAny();
            if (result != null) {
                String emailEnabled = String.valueOf(result.getValue(ADMINNOTIFICATION_EVENTS.ENABLE_NOTIFYBY_EMAIL));
                String smsEnabled = String.valueOf(result.getValue(ADMINNOTIFICATION_EVENTS.ENABLE_NOTIFYBY_SMS));
                String wxtemplateEnabled = String
                        .valueOf(result.getValue(ADMINNOTIFICATION_EVENTS.ENABLE_NOTIFYBY_WECHATTEMPLATEMESSAGE));
                Integer groupid = result.getValue(ADMINNOTIFICATION_EVENTS.GROUPID);


                Result<Record> members = create.select()
                        .from(ADMINNOTIFICATION_GROUPMEMBERS.join(ADMINNOTIFICATION_MEMBERS)
                                .on(ADMINNOTIFICATION_GROUPMEMBERS.MEMBERID.equal(ADMINNOTIFICATION_MEMBERS.ID)))
                        .where(ADMINNOTIFICATION_GROUPMEMBERS.GROUPID.equal(groupid))
                        .and(ADMINNOTIFICATION_MEMBERS.STATUS.equal(Byte.parseByte("1")))
                        .fetch();
                List emails = new ArrayList();
                List mobiles = new ArrayList();

                for (Record r : members) {
                    String email = r.getValue(ADMINNOTIFICATION_MEMBERS.EMAIL);
                    emails.add(email);
                    System.out.println(email);

                    String mobile = r.getValue(ADMINNOTIFICATION_MEMBERS.MOBILEPHONE);
                    mobiles.add(mobile);
                    System.out.println(mobile);

                }
                if (emailEnabled.equals("1")) {
                    System.out.println("start send emails.");
                    System.out.println(emails);
                    try {
                        Email registerSuccessEmail = new Email();
                        registerSuccessEmail.setSubject("报警通知")
                                .setBody(notificationText)
                                .addRecipients(emails)
                                .send();

                    } catch (AddressException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (MessagingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (smsEnabled.equals("1")) {
                    //send (mobiles, notificationText);
                }
                if (wxtemplateEnabled.equals("1")) {
                    //send (mobiles, notificationText);
                }

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}

