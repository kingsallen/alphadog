package com.moseeker.useraccounts;

import com.moseeker.baseorm.util.SmsSender;
import com.moseeker.common.util.DateUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices.Client;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices.Client.Factory;
import com.moseeker.useraccounts.config.AppConfig;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 用户服务 客户端测试类
 * <p>
 *
 * Created by zzh on 16/5/25.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class UseraccountsServiceImplTest {

	@Autowired
	protected SmsSender smsSender;

    ////@Test
    public void testDao() {
    	TTransport transport = null;
		try {
			transport = new TFastFramedTransport(new TSocket("127.0.0.1", 12121, 60*1000));
			TProtocol protocol = new TCompactProtocol(transport);
			transport.open();
			TMultiplexedProtocol mulProtocol= new TMultiplexedProtocol(protocol, "com.moseeker.useraccounts.thrift.UseraccountsServiceImpl");
			Factory factory = new Factory();
			Client client = factory.getClient(mulProtocol);
			Response response = client.postuserlogout(82712);
			System.out.println(response.getData());
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(transport != null) {
				transport.close();
			}
		}
    }


    @Test
	public  void testVoiceSms(){

		smsSender.sendSMS_signup_voice("18516778987", "897209");
	}

	@Test
	public void testAliyunNationalSms (){
		try {
			smsSender.sendSMS("9727501705",1,"1" );
			smsSender.sendSMS("4389210935",1,"1" );


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}

	@Test
	public void testDateUtil() throws Exception{
		try {
			String dateStr = LocalDateTime.parse("2019-11-19T10:43:17", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
			.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			Assert.assertEquals("2019-11-19 10:43:17",dateStr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}