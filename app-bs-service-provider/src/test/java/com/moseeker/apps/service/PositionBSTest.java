package com.moseeker.apps.service;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Test;

import com.moseeker.thrift.gen.apps.positionbs.service.PositionBS.Client;
import com.moseeker.thrift.gen.apps.positionbs.service.PositionBS.Client.Factory;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import com.moseeker.thrift.gen.common.struct.Response;

public class PositionBSTest {

	@Test
    public void testDao() {
    	TTransport transport = null;
		try {
			transport = new TFastFramedTransport(new TSocket("127.0.0.1", 19089, 60*1000));
			TProtocol protocol = new TCompactProtocol(transport);
			transport.open();
			TMultiplexedProtocol mulProtocol= new TMultiplexedProtocol(protocol, "com.moseeker.thrift.gen.apps.positionbs.service.PositionBS");
			Factory factory = new Factory();
			Client client = factory.getClient(mulProtocol);
			ThirdPartyPositionForm form = new ThirdPartyPositionForm();
			Response response = client.synchronizePositionToThirdPartyPlatform(form);
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
