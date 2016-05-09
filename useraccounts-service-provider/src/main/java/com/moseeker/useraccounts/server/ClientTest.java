package com.moseeker.useraccounts.server;

import org.apache.thrift.TException;

import com.moseeker.servicemanager.util.ServiceUtil;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.struct.userloginreq;


public class ClientTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices.Iface profileImportService = ServiceUtil.getService(com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices.Iface.class);
		
		userloginreq ureg = new userloginreq();
		ureg.setUnionid("unid0001");		

		try {
			Response resp = profileImportService.postuserlogin(ureg);
			System.out.print(resp);
		} catch (TException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

}
