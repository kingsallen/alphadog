package com.moseeker.servicemanager.common;

import static com.moseeker.db.userdb.tables.Companyfollowers.COMPANYFOLLOWERS;
import static com.moseeker.servicemanager.db.configdb.tables.AdminnotificationEvents.ADMINNOTIFICATION_EVENTS;
import static com.moseeker.servicemanager.db.configdb.tables.AdminnotificationGroupmembers.ADMINNOTIFICATION_GROUPMEMBERS;
import static com.moseeker.servicemanager.db.configdb.tables.AdminnotificationMembers.ADMINNOTIFICATION_MEMBERS;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.dbconnection.DatabaseConnectionHelper;
import com.moseeker.thrift.gen.companyfollowers.Companyfollower;
import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerServices;

public class ResponseLogNotification {
	public String message = null;
	public String data = null;
	public int status = 0;
	public int errcode;

	private final static int appid = 0;
	private final static String logkey = "LOG";
	private final static String eventkey = "RESTFUL_API_ERROR";

	public static String success(HttpServletRequest request, String jsondata) {

		ResponseLogNotification response = new ResponseLogNotification();
		response.setStatus(0);
		response.setData(jsondata);
		String jsonresponse = JSON.toJSONString(response);
		logRequestResponse(request, jsonresponse);
		return jsonresponse;

	}

	public static String fail(HttpServletRequest request, String message) {
		ResponseLogNotification response = new ResponseLogNotification();
		response.setStatus(1);
		response.setErrcode(0);
		response.setMessage(message);
		String jsonresponse = JSON.toJSONString(response);
		logRequestResponse(request, jsonresponse);
		sendNotification(Integer.parseInt(request.getParameter("appid")), eventkey, message);
		return jsonresponse;
	}

	public static String fail(HttpServletRequest request, int errcode, String message) {
		ResponseLogNotification response = new ResponseLogNotification();
		response.setStatus(1);
		response.setErrcode(errcode);
		response.setMessage(message);
		String jsonresponse = JSON.toJSONString(response);
		logRequestResponse(request, jsonresponse);
		sendNotification(appid, eventkey, message);
		return jsonresponse;
	}

	public static String fail(HttpServletRequest request, int errcode, String message, String jsondata) {
		ResponseLogNotification response = new ResponseLogNotification();
		response.setStatus(1);
		response.setErrcode(errcode);
		response.setMessage(message);
		response.setData(jsondata);
		String jsonresponse = JSON.toJSONString(response);
		logRequestResponse(request, jsonresponse);
		sendNotification(appid, eventkey, message + "," + jsondata);
		return jsonresponse;
	}

	private static void logRequestResponse(HttpServletRequest request, String response) {
		Map reqResp = new HashMap();
		reqResp.put("appid", request.getParameter("appid"));
		reqResp.put("request", request.getParameterMap());
		reqResp.put("response", response);
		reqResp.put("remote_ip", Spring.getRemoteIp(request));
		reqResp.put("web_server_ip", "192.22.22.22");

		try {
			RedisClientFactory.getInstance().getLog().lpush(appid, logkey, JSON.toJSONString(reqResp));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		sendNotification(0, "MYSQL_CONNECT_ERROR", "mysql ip : 123.44.44.44");
	}

	public static void sendNotification(int appid, String event_key, String event_details) {
		String notificationText = "项目" + appid + " 发生异常，" + event_details;
		DSLContext create;
		try {
			create = DatabaseConnectionHelper.getJooqDSL();
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
					//send (emails, notificationText);
				}
				if (smsEnabled.equals("1")) {
					//send (mobiles, notificationText);
				}
				if (wxtemplateEnabled.equals("1")) {
					//send (mobiles, notificationText);
				}				
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setMessage(String message) {
		this.message = message;
	}

	private void setStatus(int status) {
		this.status = status;
	}

	private void setData(String jsondata) {
		this.data = jsondata;
	}

	private void setErrcode(int errcode) {
		this.errcode = errcode;
	}

}
