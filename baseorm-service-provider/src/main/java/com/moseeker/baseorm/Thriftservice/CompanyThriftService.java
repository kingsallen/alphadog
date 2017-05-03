package com.moseeker.baseorm.Thriftservice;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.CompanyDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountRecord;
import com.moseeker.baseorm.service.HrCompanyService;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.dao.service.CompanyDao.Iface;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;
import com.moseeker.thrift.gen.dao.struct.ThirdPartyPositionData;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
	
	@Autowired
	private HRThirdPartyPositionDao thirdPartyPositionDao;
	
	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private HrCompanyService hrCompanyService;
	@Override
	public ThirdPartAccountData getThirdPartyAccount(CommonQuery query) throws TException {
		logger.info("getThirdPartyAccount");
		ThirdPartAccountData data =  new ThirdPartAccountData();
		try {
			HrThirdPartyAccountRecord record = thirdPartyAccountDao.getRecord(QueryConvert.commonQueryConvertToQuery(query));
			if(record != null) {
				copy(data, record);
			}
			logger.info("data:"+JSON.toJSONString(data));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		return data;
	}
	
	@Override
	public List<ThirdPartAccountData> getThirdPartyBindingAccounts(CommonQuery query) throws TException {
		List<ThirdPartAccountData> datas = new ArrayList<>();
		try {
			List<HrThirdPartyAccountRecord> records = thirdPartyAccountDao.getThirdPartyBindingAccounts(QueryConvert.commonQueryConvertToQuery(query));
			if(records != null && records.size() > 0) {
				records.forEach(r -> {
					ThirdPartAccountData data = new ThirdPartAccountData();
					copy(data, r);
					datas.add(data);
				});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		return datas;
	}
	
	/**
	 * 查询第三方职位
	 */
	@Override
	public List<ThirdPartyPositionData> getThirdPartyPositions(CommonQuery query) throws TException {
		
		return thirdPartyPositionDao.getThirdPartyPositions(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public Response upsertThirdPartyPositions(List<ThirdPartyPositionData> positions) throws TException {
		
		return thirdPartyPositionDao.upsertThirdPartyPositions(positions);
	}
	
	@Override
	public Response updatePartyAccountByCompanyIdChannel(ThirdPartAccountData account) throws TException {
		int count = thirdPartyAccountDao.updatePartyAccountByCompanyIdChannel(account);
		if(count > 0) {
			return ResponseUtils.success(null);
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
	}
	
	@Override
	public HrCompanyDO getCompany(CommonQuery query) throws TException {
		return companyDao.getCompany(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public List<Hrcompany> getCompanies(CommonQuery query) throws TException {
		return companyDao.getCompanies(QueryConvert.commonQueryConvertToQuery(query));
	}

	private void copy(ThirdPartAccountData data, HrThirdPartyAccountRecord record) {
		data.setId(record.getId());
		data.setBinding(record.getBinding());
		data.setChannel(record.getChannel());
		data.setCompany_id(record.getCompanyId().intValue());
		data.setMembername(record.getMembername());
		data.setUsername(record.getUsername());
		data.setPassword(record.getPassword());
		data.setRemain_num(record.getRemainNum().intValue());
		data.setSync_time(new DateTime(record.getSyncTime()).toString("yyyy-MM-dd"));
		data.setUsername(record.getUsername());
	}
	
	public HRThirdPartyAccountDao getThirdPartyAccountDao() {
		return thirdPartyAccountDao;
	}

	public void setThirdPartyAccountDao(HRThirdPartyAccountDao thirdPartyAccountDao) {
		this.thirdPartyAccountDao = thirdPartyAccountDao;
	}

	public HRThirdPartyPositionDao getThirdPartyPositionDao() {
		return thirdPartyPositionDao;
	}

	public void setThirdPartyPositionDao(HRThirdPartyPositionDao thirdPartyPositionDao) {
		this.thirdPartyPositionDao = thirdPartyPositionDao;
	}

	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}
    /*
     * (non-Javadoc)
     * @see com.moseeker.thrift.gen.dao.service.CompanyDao.Iface#getHrCompanyConfig(com.moseeker.thrift.gen.common.struct.Query)
     * auth:zzt
     * time:2016 12 7
     * function:查询hrcompanyconf
     */
	@Override
	public Response getHrCompanyConfig(CommonQuery query){
		return hrCompanyService.getCompanyConf(QueryConvert.commonQueryConvertToQuery(query));
	}
}
