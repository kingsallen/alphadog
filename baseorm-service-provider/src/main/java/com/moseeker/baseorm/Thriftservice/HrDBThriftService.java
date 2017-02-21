package com.moseeker.baseorm.Thriftservice;

import java.util.List;
import java.util.Set;

import com.moseeker.baseorm.dao.hrdb.CompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrOperationRecordDao;
import com.moseeker.thrift.gen.dao.struct.*;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.service.HrDBService;
import com.moseeker.baseorm.service.HrDaoService;
import com.moseeker.thrift.gen.application.struct.ProcessValidationStruct;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.HrDBDao.Iface;

@Service
public class HrDBThriftService implements Iface {
	
	@Autowired
	private HrDBService hrDBService;
	
	@Autowired
    private HrDaoService hrDaoService;

	@Autowired
	private HrOperationRecordDao hrOperationRecordDao;

	@Autowired
	private CompanyDao companyDao;

	@Override
	public Response getHrHistoryOperations(List<ProcessValidationStruct> record) throws TException {
		return hrDBService.getHrHistoryOpertation(record);
	}

	@Override
	public List<HrOperationRecordDO> listHrOperationRecord(CommonQuery query) throws TException {
		return hrOperationRecordDao.listResources(query);
	}

	@Override
	public List<HrOperationRecordDO> listLatestOperationRecordByAppIdSet(Set<Integer> appidSet) throws TException {
		return hrOperationRecordDao.listLatestOperationRecordByAppIdSet(appidSet);
	}

	@Override
	public HrEmployeeCertConfDO getEmployeeCertConf(CommonQuery query) throws TException {
		return hrDaoService.getEmployeeCertConf(query);
	}

	@Override
	public List<HrEmployeeCustomFieldsDO> getEmployeeCustomFields(CommonQuery query) throws TException {
		return hrDaoService.getEmployeeCustomFields(query);
	}

	@Override
	public List<HrPointsConfDO> getPointsConfs(CommonQuery query) throws TException {
		return hrDaoService.getPointsConfs(query);
	}

	@Override
	public HrCompanyDO getCompany(CommonQuery query) throws TException {
		return companyDao.getCompany(query);
	}

	public HrHbConfigDO getHbConfig(CommonQuery query) throws TException {
		return hrDaoService.getHbConfig(query);
	}

	@Override
	public List<HrHbConfigDO> getHbConfigs(CommonQuery query) throws TException {
		return hrDaoService.getHbConfigs(query);
	}

	@Override
	public HrHbPositionBindingDO getHbPositionBinding(CommonQuery query) throws TException {
		return hrDaoService.getHbPositionBinding(query);
	}

	@Override
	public List<HrHbPositionBindingDO> getHbPositionBindings(CommonQuery query) throws TException {
		return hrDaoService.getHbPositionBindings(query);
	}

	@Override
	public HrHbItemsDO getHbItem(CommonQuery query) throws TException {
		return hrDaoService.getHbItem(query);
	}

	@Override
	public List<HrHbItemsDO> getHbItems(CommonQuery query) throws TException {
		return hrDaoService.getHbItems(query);
	}

	@Override
	public HrHbScratchCardDO getHbScratchCard(CommonQuery query) throws TException {
		return hrDaoService.getHbScratchCard(query);
	}

	@Override
	public HrHbSendRecordDO getHbSendRecord(CommonQuery query) throws TException {
		return hrDaoService.getHbSendRecord(query);
	}

	@Override
	public Response postHrOperationrecords(List<HrOperationRecordDO> record)
			throws TException {
		return hrDBService.postHrOperations(record);
	}

	@Override
	public Response postHrOperationrecord(HrOperationRecordDO record)
			throws TException {
		return hrDBService.postHrOperation(record);
	}
}
