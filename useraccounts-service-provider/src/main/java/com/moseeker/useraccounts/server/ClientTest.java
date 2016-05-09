package com.moseeker.useraccounts.server;

import org.apache.thrift.TException;

import com.moseeker.servicemanager.util.ServiceUtil;
import com.moseeker.thrift.gen.useraccounts.struct.userloginreq;
import com.moseeker.thrift.gen.useraccounts.struct.userloginresp;


public class ClientTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices.Iface profileImportService = ServiceUtil.getService(com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices.Iface.class);
		
		userloginreq ureg = new userloginreq();
		ureg.setUnionid("123444");		

		try {
			userloginresp resp = profileImportService.postuserlogin(ureg);
			System.out.print(resp);
		} catch (TException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		
		
//		TTransport transport = new TSocket("192.168.31.88", 19091);
//		TProtocol protocol = new  TBinaryProtocol(transport);
//		com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices.Client client = new com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices.Client(protocol);		
//		userloginreq ureg = new userloginreq();
//		ureg.setUnionid("123444");
//		try {
//			client.postuserlogin(ureg);
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		transport.close();
	}

}
