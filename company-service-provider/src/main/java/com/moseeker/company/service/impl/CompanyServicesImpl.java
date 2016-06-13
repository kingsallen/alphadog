package com.moseeker.company.service.impl;

import java.text.ParseException;
import java.util.List;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(this.getClass());

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
        return (HrCompanyRecord) BeanUtils.structToDB(company, HrCompanyRecord.class);
    }

    @Override
    protected Hrcompany DBToStruct(HrCompanyRecord r) {
        return (Hrcompany) BeanUtils.DBToStruct(Hrcompany.class, r);

    }

    public Response getAllCompanies(CommonQuery query) {
        if (dao == null) {
            initDao();
        }
        try {
            List<HrCompanyRecord> records = dao.getAllCompanies(query);
            List<Hrcompany> structs = DBsToStructs(records);

            if (!structs.isEmpty()) {
                return ResponseUtils.success(structs);
            }

        } catch (Exception e) {
            logger.error("getResources error", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
    }
}

