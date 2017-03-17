package com.moseeker.useraccounts;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Test;

import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices.Client;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices.Client.Factory;

/**
 * 用户服务 客户端测试类
 * <p>
 *
 * Created by zzh on 16/5/25.
 */
public class UseraccountsServiceImplTest {

    //@Test
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
}
