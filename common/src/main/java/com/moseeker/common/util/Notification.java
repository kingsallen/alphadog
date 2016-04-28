package com.moseeker.common.util;

import static com.moseeker.db.dqv4.tables.ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS;
import static com.moseeker.db.dqv4.tables.ConfigAdminnotificationGroupmembers.CONFIG_ADMINNOTIFICATION_GROUPMEMBERS;
import static com.moseeker.db.dqv4.tables.ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            Email mycatConnectionErrorEmail = new Email.EmailBuilder(recipients)
                                                                .setSubject(subject)
                                                                .setContent(content)
                                                                .build();
            mycatConnectionErrorEmail.send();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void sendNotification(int appid, String event_key, String event_details) {
        String notificationText = "项目" + appid + " 发生异常，" + event_details;
        DSLContext create;
        try {
            create = DatabaseConnectionHelper.getConnection().getJooqDSL();
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
                    System.out.println(email);

                    String mobile = r.getValue(CONFIG_ADMINNOTIFICATION_MEMBERS.MOBILEPHONE);
                    mobiles.add(mobile);
                    System.out.println(mobile);

                }
                if (emailEnabled.equals("1")) {
                    System.out.println("start send emails.");
                    System.out.println(emails);
                    try {
                        Email registerSuccessEmail = new Email.EmailBuilder(emails)
                                                                .setSubject("报警通知")
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
        }
    }


    /**
	 * 发送报警邮件
	 * @param host redis集群的host
	 * @param port redis集群的port
	 */
	public static void sendLostRedisWarning(String host, String port) {
		StringBuffer sb = new StringBuffer();
		String hostName = getHostName();
		if(!StringUtils.isNullOrEmpty(hostName)) {
			sb.append("host:");
			sb.append(hostName);
			sb.append("--");
		}
		sb.append("redis_host:");
		sb.append(host);
		sb.append("--");
		sb.append("redis_port:");
		sb.append(port);
		sb.append("--");
		sb.append("连接不上Redis集群! 可能是redis配置导致！");
		sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, sb.toString());
	}
	
	/**
	 * 程序异常时发送报警信息
	 * @param e 异常类
	 */
	public static void sendProgressError(Exception e) {
		StringBuffer sb = new StringBuffer();
		String hostName = getHostName();
		if(!StringUtils.isNullOrEmpty(hostName)) {
			sb.append("host:");
			sb.append(hostName);
			sb.append("--");
		}
		ConfigPropertiesUtil configUtil = ConfigPropertiesUtil.getInstance();
		int appId = configUtil.get("appid", int.class);
		String eventkEY = configUtil.get("event_key", String.class);
		sb.append("  ");
		sb.append(e.getMessage());
		sendNotification(appId, eventkEY, sb.toString());
	}
	
	/**
	 * 发送带hostname的报警信息
	 * @param appid 项目编号
	 * @param event_key 标识符
	 * @param event_details 错误信息
	 */
	public static void sendWarningWithHostName(int appid, String event_key, String event_details) {
		StringBuffer sb = new StringBuffer();
		String hostName = getHostName();
		sb.append("host:");
		sb.append(hostName);
		sb.append("  ");
		sb.append(event_details);
		sendNotification(appid, event_key, sb.toString());
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

