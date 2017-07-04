package com.moseeker.company.service.impl;

import com.moseeker.baseorm.dao.campaigndb.CampaignPcBannerDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.company.constant.ResultMessage;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignPcBannerDO;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	
	ChaosServices.Iface chaosService = ServiceManager.SERVICEMANAGER.getService(ChaosServices.Iface.class);

    @Autowired
    protected HrCompanyDao companyDao;
    
    @Autowired
    protected HrWxWechatDao wechatDao;
    
    @Autowired
    private CampaignPcBannerDao campaignPcBannerDao;

    @Autowired
    private HRThirdPartyAccountDao hrThirdPartyAccountDao;

    public Response getResource(CommonQuery query) throws TException {
        try {
            Hrcompany data = companyDao.getData(QueryConvert.commonQueryConvertToQuery(query), Hrcompany.class);
            return ResponseUtils.success(data);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response getResources(CommonQuery query) throws TException {
        try {
            List<Hrcompany> list = companyDao.getDatas(QueryConvert.commonQueryConvertToQuery(query), Hrcompany.class);
            return ResponseUtils.success(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }

    }

    @CounterIface
    public Response getAllCompanies(CommonQuery query) {
        try {
            List<Hrcompany> structs =  companyDao.getDatas(QueryConvert.commonQueryConvertToQuery(query), Hrcompany.class);
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

	@CounterIface
	public Response add(Hrcompany company) throws TException {
		ValidateUtil vu = new ValidateUtil();
		vu.addRequiredStringValidate("公司名称", company.getName(), null, null);
		vu.addRequiredValidate("来源", company.getSource());
        String message = vu.validate();
        if(StringUtils.isNullOrEmpty(message)) {
            boolean repeatName = companyDao.checkRepeatNameWithSuperCompany(company.getName());
            if(repeatName) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_NAME_REPEAT);
            } else {
                try {
                    HrCompanyRecord record = BeanUtils.structToDB(company, HrCompanyRecord.class);
                    boolean scaleIllegal = companyDao.checkScaleIllegal(record.getScale());
                    if(!scaleIllegal) {
                        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
                    }
                    boolean propertyIllegal = companyDao.checkPropertyIllegal(record.getScale());
                    if(!propertyIllegal) {
                        return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_PROPERTIY_ELLEGAL);
                    }
                    int companyId = companyDao.addRecord(record).getId();
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
	@CounterIface
	public Response getWechat(long companyId, long wechatId) {
		
		if(companyId == 0 && wechatId == 0) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
		}
		Query.QueryBuilder qu = new Query.QueryBuilder();
		qu.setPageSize(Integer.MAX_VALUE);
		if(wechatId > 0) {
			qu.where("id", String.valueOf(wechatId));
		} else if(companyId > 0) {
			qu.where("company_id", String.valueOf(companyId));
		}
		try {
			HrWxWechatRecord record = wechatDao.getRecord(qu.buildQuery());
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
	
	/**
	 * 判断是否有权限发布职位
	 * @param companyId 公司编号
	 * @param channel 渠道号
	 * @return
	 */
	@CounterIface
	public Response ifSynchronizePosition(int companyId, int channel) {
		Response response = ResultMessage.PROGRAM_EXHAUSTED.toResponse();
		Query.QueryBuilder qu = new Query.QueryBuilder();
		qu.where("company_id", String.valueOf(companyId)).and("channel", String.valueOf(channel));
		try {
			ThirdPartAccountData data = hrThirdPartyAccountDao.getData(qu.buildQuery(), ThirdPartAccountData.class);
			if(data.getId() == 0 || data.getBinding() != 1) {
				response = ResultMessage.THIRD_PARTY_ACCOUNT_UNBOUND.toResponse();
			}
			if(data.getRemain_num() == 0) {
				response = ResultMessage.THIRD_PARTY_ACCOUNT_HAVE_NO_REMAIN_NUM.toResponse();
			}
			if(data.getId() > 0 && data.binding == 1 && data.getRemain_num() > 0) {
				response = ResultMessage.SUCCESS.toResponse();
			} else {
				response = ResultMessage.THIRD_PARTY_ACCOUNT_UNBOUND.toResponse();
			}
		} catch (Exception e) {
			response = ResultMessage.PROGRAM_EXHAUSTED.toResponse();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		return response;
	}
	/*
	 * 获取pc端获取banner图
	 */
	@CounterIface
	public Response getPcBannerByPage(int page,int pageSize) throws Exception{
		Query query=new Query.QueryBuilder().setPageNum(page).setPageSize(pageSize).buildQuery();
		List<CampaignPcBannerDO> list=campaignPcBannerDao.getDatas(query);
		if(StringUtils.isEmptyList(list)){
			ResponseUtils.success("");
		}
		return ResponseUtils.success(list);
	}
}
