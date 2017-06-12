package com.moseeker.company.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Test;

import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices.Client;
import com.moseeker.thrift.gen.company.service.CompanyServices.Client.Factory;

public class CompanyThriftServiceTest {

	//@Test
	public void test() {
		TTransport transport = null;
		try {
			transport = new TFastFramedTransport(new TSocket("127.0.0.1", 19092, 3*1000));
			TProtocol protocol = new TCompactProtocol(transport);
			transport.open();
			TMultiplexedProtocol mulProtocol= new TMultiplexedProtocol(protocol, "com.moseeker.thrift.gen.company.service.CompanyServices");
			Factory factory = new Factory();
			Client client = factory.getClient(mulProtocol);
			//Response response = client.synchronizeThirdpartyAccount(96, (byte)3);
			//System.out.println(response.getData());
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
