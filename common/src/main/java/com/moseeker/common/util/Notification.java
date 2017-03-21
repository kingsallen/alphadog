package com.moseeker.common.util;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.email.Email;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

<<<<<<< HEAD
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.email.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
=======
import static com.moseeker.db.configdb.tables.ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS;
import static com.moseeker.db.configdb.tables.ConfigAdminnotificationGroupmembers.CONFIG_ADMINNOTIFICATION_GROUPMEMBERS;
import static com.moseeker.db.configdb.tables.ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS;
>>>>>>> master

public class Notification {

    private static Logger logger = LoggerFactory.getLogger(Notification.class);

    public static void sendMyCatConnectionError(String errorMessage) {
        ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
        String subject = propertiesReader.get("mycat.error.subject", String.class);
        String content ="数据库连接失败! .   host:" + getHostName() +", 详情:" + propertiesReader.get("mycat.error.content", String.class);
        List<String> recipients = Arrays.asList(propertiesReader.get("mycat.error.recipients", String.class).split(","));
        try {
            Email mycatConnectionErrorEmail = new Email.EmailBuilder(recipients)
                                                                .setSubject(subject)
                                                                .setContent(content)
                                                                .build();
            mycatConnectionErrorEmail.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LoggerFactory.getLogger(Notification.class).error(content);
    }
    
    public static void sendNotification(int appid, String event_key, String event_details) {
        String notificationText = "项目" + appid + " 发生异常，host:" + getHostName() +", 详情:" + event_details;
        LoggerFactory.getLogger(Notification.class).error(notificationText);
        Connection conn = null;
        try {
        	conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            Record result = create.select().from(CONFIG_ADMINNOTIFICATION_EVENTS)
                    .where(CONFIG_ADMINNOTIFICATION_EVENTS.EVENT_KEY.equal(event_key)).fetchAny();
            if (result != null) {
                String emailEnabled = String.valueOf(result.getValue(CONFIG_ADMINNOTIFICATION_EVENTS.ENABLE_NOTIFYBY_EMAIL));
                String smsEnabled = String.valueOf(result.getValue(CONFIG_ADMINNOTIFICATION_EVENTS.ENABLE_NOTIFYBY_SMS));
                String wxtemplateEnabled = String
                        .valueOf(result.getValue(CONFIG_ADMINNOTIFICATION_EVENTS.ENABLE_NOTIFYBY_WECHATTEMPLATEMESSAGE));
                Integer groupid = result.getValue(CONFIG_ADMINNOTIFICATION_EVENTS.GROUPID);


                Result<Record> members = create.select()
                        .from(CONFIG_ADMINNOTIFICATION_GROUPMEMBERS.join(CONFIG_ADMINNOTIFICATION_MEMBERS)
                                .on(CONFIG_ADMINNOTIFICATION_GROUPMEMBERS.MEMBERID.equal(CONFIG_ADMINNOTIFICATION_MEMBERS.ID)))
                        .where(CONFIG_ADMINNOTIFICATION_GROUPMEMBERS.GROUPID.equal(groupid))
                        .and(CONFIG_ADMINNOTIFICATION_MEMBERS.STATUS.equal(Byte.parseByte("1")))
                        .fetch();
                List<String> emails = new ArrayList<>();
                List<String> mobiles = new ArrayList<>();

                for (Record r : members) {
                    String email = r.getValue(CONFIG_ADMINNOTIFICATION_MEMBERS.EMAIL);
                    emails.add(email);
                    logger.info(email);

                    String mobile = r.getValue(CONFIG_ADMINNOTIFICATION_MEMBERS.MOBILEPHONE);
                    mobiles.add(mobile);
                    logger.info(mobile);

                }
                if (emailEnabled.equals("1")) {
                    logger.info("start send emails. emails : {}", emails);
                    try {
                        Email registerSuccessEmail = new Email.EmailBuilder(emails)
                                                                .setSubject("阿里云监控-基础服务-响应异常-发生告警通知")
                                                                .setContent(notificationText)
                                                                .build();
                        registerSuccessEmail.send();

                    } catch (AddressException e) {
                        // TODO Auto-generated catch block
                      //  e.printStackTrace();
                    } catch (MessagingException e) {
                        // TODO Auto-generated catch block
                      //  e.printStackTrace();
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
          //  e.printStackTrace();
        } finally {
        	try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
                LoggerFactory.getLogger(Notification.class).error(e.getMessage(), e);
			}
        }
    }

	/**
	 * 获取当前服务器的hostname
	 * @return hostname 服务器名称
	 */
	private static String getHostName() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			//nothing to do
		}
		if(addr != null) {
			return addr.getHostName();
		} 
		return "";
	}
}

