package com.moseeker.baseorm.Thriftservice;

import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.hr.HRThirdPartyAccountDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.service.CompanyDao.Iface;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;

/**
 * 提供公司数据以及公司相关数据的查询
 * @author wjf
 *
 */
@Service
public class CompanyThriftService implements Iface {
	
	private Logger logger = LoggerFactory.getLogger(CompanyThriftService.class);
	
	@Autowired
	private HRThirdPartyAccountDao thirdPartyAccountDao;

	@Override
	public ThirdPartAccountData getThirdPartyAccount(CommonQuery query) throws TException {
		ThirdPartAccountData data = null;
		try {
			HrThirdPartyAccountRecord record = thirdPartyAccountDao.getResource(query);
			if(record != null) {
				data = new ThirdPartAccountData();
				data.setId(record.getId());
				data.setBinding(record.getBinding());
				data.setChannel(record.getChannel());
				data.setCompany_id(record.getCompanyId().intValue());
				data.setMembername(record.getMembername());
				data.setName(record.getUsername());
				data.setPassword(record.getPassword());
				data.setRemain_num(record.getRemainNum().intValue());
				data.setSync_time(new DateTime(record.getSyncTime()).toString("yyyy-MM-dd"));
				data.setUsername(record.getUsername());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		return data;
	}

	public HRThirdPartyAccountDao getThirdPartyAccountDao() {
		return thirdPartyAccountDao;
	}

	public void setThirdPartyAccountDao(HRThirdPartyAccountDao thirdPartyAccountDao) {
		this.thirdPartyAccountDao = thirdPartyAccountDao;
	}

}
