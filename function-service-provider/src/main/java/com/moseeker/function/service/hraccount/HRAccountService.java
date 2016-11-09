package com.moseeker.function.service.hraccount;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.orm.service.UserHrAccountDao;
import com.moseeker.thrift.gen.useraccounts.struct.BindAccountStruct;

@Service
public class HRAccountService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	UserHrAccountDao.Iface hraccountDao = ServiceManager.SERVICEMANAGER.getService(UserHrAccountDao.Iface.class);

	/**
	 * 是否允许执行绑定
	 * @param userId
	 * @param channel
	 * @return 0表示允许，1表示已经绑定，2...
	 */
	@CounterIface
	public Response allowBind(int userId, int companyId, byte channelType) {
		try {
			Response response = null;
			if(companyId > 0) {
				QueryUtil qu = new QueryUtil();
				qu.addEqualFilter("company_id", String.valueOf(companyId));
				response = hraccountDao.getThirdPartyAccount(qu);
			} else {
				QueryUtil qu = new QueryUtil();
				qu.addEqualFilter("id", String.valueOf(userId));
				Response hraccountResponse = hraccountDao.getAccount(qu);
				if(hraccountResponse.getStatus() == 0) {
					JSONObject hraccount = JSONObject.parseObject(hraccountResponse.getData());
					if(hraccount.getInteger("company_id") != null) {
						int companyId1 = hraccount.getInteger("company_id");
						QueryUtil qu1 = new QueryUtil();
						qu1.addEqualFilter("company_id", String.valueOf(companyId1));
						response = hraccountDao.getThirdPartyAccount(qu1);
					} else {
						//数据异常
						return ResponseUtils.fail(ConstantErrorCodeMessage.HRACCOUNT_ELLEGLE_DATA);
					}
				} else {
					//请求不到数据
					return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
				}
			}
			if(response.getStatus() == 90010) {
				return ResponseUtils.success(null);
			} else if(response.getStatus() == 0) {
				JSONObject thirdPartyAccount = JSONObject.parseObject(response.getData());
				if(thirdPartyAccount.getInteger("binding") == 0 || thirdPartyAccount.getInteger("binding") == 3) {
					return ResponseUtils.success(null);
				} else if(thirdPartyAccount.getInteger("binding") == 1) {
					return ResponseUtils.fail(ConstantErrorCodeMessage.HRACCOUNT_ALREADY_BOUND);
				} else if(thirdPartyAccount.getInteger("binding") == 2) {
					return ResponseUtils.fail(ConstantErrorCodeMessage.HRACCOUNT_BINDING);
				} else {
					return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
				}
			}
		} catch (TException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			//do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
	}

	public Response createThirdPartyAccount(BindAccountStruct account) {
		
		try {
			return hraccountDao.createThirdPartyAccount(account);
		} catch (TException e) {
			e.printStackTrace();
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			//do nothing
		}
	}
}
