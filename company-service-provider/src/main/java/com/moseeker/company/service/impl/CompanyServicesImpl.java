package com.moseeker.company.service.impl;

import java.text.ParseException;
import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.company.dao.CompanyDao;
import com.moseeker.company.dao.WechatDao;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.struct.Hrcompany;

@Service
public class CompanyServicesImpl extends JOOQBaseServiceImpl<Hrcompany, HrCompanyRecord> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected CompanyDao dao;
    
    @Autowired
    protected WechatDao wechatDao;

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

	public Response add(Hrcompany company) throws TException {
		ValidateUtil vu = new ValidateUtil();
		vu.addRequiredStringValidate("公司名称", company.getName(), null, null);
		vu.addRequiredValidate("来源", company.getSource());
		 String message = vu.validate();
         if(StringUtils.isNullOrEmpty(message)) {
        	boolean repeatName = dao.checkRepeatNameWithSuperCompany(company.getName());
        	if(repeatName) {
        		return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_NAME_REPEAT);
        	} else {
        		try {
					HrCompanyRecord record = structToDB(company);
					boolean scaleIllegal = dao.checkScaleIllegal(record.getScale());
					if(!scaleIllegal) {
						return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
					}
					boolean propertyIllegal = dao.checkPropertyIllegal(record.getScale());
					if(!propertyIllegal) {
						return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_PROPERTIY_ELLEGAL);
					}
					int companyId = dao.postResource(record);
					return ResponseUtils.success(String.valueOf(companyId));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
        	}
         } else {
        	 return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", message));
         }
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
	}

	/**
	 * 
	 * @param companyId
	 * @param wechatId
	 * @return
	 */
	public Response getWechat(long companyId, long wechatId) {
		
		if(companyId == 0) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
		}
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("company_id", String.valueOf(companyId));
		if(wechatId > 0) {
			qu.addEqualFilter("id", String.valueOf(wechatId));
		} else if(companyId > 0) {
			qu.addEqualFilter("company_id", String.valueOf(companyId));
		}
		try {
			HrWxWechatRecord record = wechatDao.getResource(qu);
			if(record == null) {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			} else {
				return ResponseUtils.success(record.intoMap());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
}

