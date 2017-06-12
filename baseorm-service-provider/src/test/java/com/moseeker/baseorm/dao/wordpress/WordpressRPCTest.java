package com.moseeker.baseorm.dao.wordpress;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.thrift.gen.dao.struct.WordpressPosts;
import com.moseeker.thrift.gen.dao.struct.WordpressTermRelationships;

public class WordpressRPCTest {

	////@Test
	/*public void test() {
		TTransport transport = null;
		try {
			transport = new TFastFramedTransport(new TSocket("127.0.0.1", 19080, 60*1000));
			TProtocol protocol = new TCompactProtocol(transport);
			transport.open();
			TMultiplexedProtocol mulProtocol= new TMultiplexedProtocol(protocol, "com.moseeker.thrift.gen.orm.service.WordpressDao");
			Factory factory = new Factory();
			Client client = factory.getClient(mulProtocol);
			WordpressTermRelationships ships = client.getLastRelationships(2);
			System.out.println(ships.getObjectId());
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("id", String.valueOf(12));
			WordpressPosts post = client.getPost(qu);
			System.out.println(post.getPostTitle());
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(transport != null) {
				transport.close();
			}
		}
	}*/
}
