package com.moseeker.function.thrift.service;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.function.service.hraccount.HRAccountService;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.foundation.passport.service.HRAccountFoundationServices.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.BindAccountStruct;

/**
 * 
 * B端帐号管理类
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 8, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
@Service
public class HRAccountThriftService implements Iface{
	
	@Autowired
	private HRAccountService accountService;

	@Override
	public Response allowBind(int userId, int companyId, byte channelType) throws TException {
		return accountService.allowBind(userId, companyId, channelType);
	}

	@Override
	public Response createThirdPartyAccount(BindAccountStruct account) throws TException {
        return accountService.createThirdPartyAccount(account);
    }
	
	
}
