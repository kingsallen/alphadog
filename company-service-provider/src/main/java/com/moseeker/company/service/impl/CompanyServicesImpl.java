package com.moseeker.company.service.impl;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.company.dao.CompanyDao;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.thrift.gen.company.service.CompanyServices.Iface;
import com.moseeker.thrift.gen.company.struct.Hrcompany;

@Service
public class CompanyServicesImpl extends JOOQBaseServiceImpl<Hrcompany, HrCompanyRecord> implements Iface {

	@Autowired
	protected CompanyDao dao;
	
	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	public CompanyDao getDao() {
		return dao;
	}

	public void setDao(CompanyDao dao) {
		this.dao = dao;
	}

	@Override
	protected HrCompanyRecord structToDB(Hrcompany company) throws ParseException {
		// TODO Auto-generated method stub
		return (HrCompanyRecord)BeanUtils.structToDB(company, HrCompanyRecord.class);
	}

	@Override
	protected Hrcompany DBToStruct(HrCompanyRecord r) {
		return (Hrcompany)BeanUtils.DBToStruct(Hrcompany.class, r);

	}


	
}
