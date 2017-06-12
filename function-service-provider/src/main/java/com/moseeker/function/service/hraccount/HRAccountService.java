package com.moseeker.function.service.hraccount;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.struct.BindAccountStruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HRAccountService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private UserHrAccountDao hraccountDao;

    @Autowired
    private HRThirdPartyAccountDao hrThirdPartyAccountDao;

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
                Query.QueryBuilder qu = new Query.QueryBuilder();
				qu.where("company_id", String.valueOf(companyId)).and("channel", String.valueOf(channelType));
                HrThirdPartyAccountRecord record = hrThirdPartyAccountDao.getRecord(qu.buildQuery());
                if (record != null) {
                    response = ResponseUtils.success(record.intoMap());
                } else {
                    response = ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
                }
			} else {
				Query.QueryBuilder qu = new Query.QueryBuilder();
				qu.where("id", String.valueOf(userId));
                UserHrAccountRecord accountRecord = hraccountDao.getRecord(qu.buildQuery());
                if(accountRecord != null && accountRecord.getCompanyId() != null) {
                    Query.QueryBuilder qu1 = new Query.QueryBuilder();
                    qu1.where("company_id", accountRecord.getCompanyId()).and("channel", String.valueOf(channelType));
                    HrThirdPartyAccountRecord record = hrThirdPartyAccountDao.getRecord(qu1.buildQuery());
                    if (record != null) {
                        response = ResponseUtils.success(record.intoMap());
                    } else {
                        response = ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
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
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			//do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
	}

	@CounterIface
	public Response createThirdPartyAccount(BindAccountStruct account) {
        return hrThirdPartyAccountDao.upsertThirdPartyAccount(account);
	}
}
