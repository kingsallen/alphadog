package com.moseeker.company.service.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.company.constant.BindingStatus;
import com.moseeker.company.constant.ResultMessage;
import com.moseeker.company.dao.CompanyDao;
import com.moseeker.company.dao.WechatDao;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices;
import com.moseeker.thrift.gen.foundation.chaos.struct.ThirdPartyAccountStruct;
import com.moseeker.thrift.gen.useraccounts.struct.BindAccountStruct;

@Service
public class CompanyService extends JOOQBaseServiceImpl<Hrcompany, HrCompanyRecord> {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	com.moseeker.thrift.gen.dao.service.CompanyDao.Iface companyDao = ServiceManager.SERVICEMANAGER.getService(com.moseeker.thrift.gen.dao.service.CompanyDao.Iface.class);
	ChaosServices.Iface chaosService = ServiceManager.SERVICEMANAGER.getService(ChaosServices.Iface.class);
	com.moseeker.thrift.gen.dao.service.UserHrAccountDao.Iface hraccountDao = ServiceManager.SERVICEMANAGER
			.getService(com.moseeker.thrift.gen.dao.service.UserHrAccountDao.Iface.class);
	
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

    @CounterIface
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

	@CounterIface
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
		
		if(companyId == 0 && wechatId == 0) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
		}
		QueryUtil qu = new QueryUtil();
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

	/**
	 * 同步第三方职位信息
	 * @param id
	 * @param channel
	 * @return
	 */
	public Response synchronizeThirdpartyAccount(int id, byte channel) {
		//查找第三方帐号
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("company_id", String.valueOf(id));
		qu.addEqualFilter("channel", String.valueOf(channel));
		try {
			ThirdPartAccountData data = companyDao.getThirdPartyAccount(qu);
			//如果是绑定状态，则进行
			//判断是否已经判定第三方帐号
			if(data.getId() > 0 && data.getBinding() == BindingStatus.BOUND.getValue()) {
				ThirdPartyAccountStruct thirdPartyAccount = new ThirdPartyAccountStruct();
				thirdPartyAccount.setChannel((byte)data.getChannel());
				thirdPartyAccount.setMemberName(data.getMembername());
				thirdPartyAccount.setUsername(data.getUsername());
				thirdPartyAccount.setPassword(data.getPassword());
				//获取剩余点数
				ThirdPartyAccountStruct synchronizeResult = chaosService.synchronization(thirdPartyAccount);
				if(synchronizeResult != null && synchronizeResult.getStatus() == 0) {
					BindAccountStruct  thirdPartyAccount1 = new BindAccountStruct();
					thirdPartyAccount1.setBinding(1);
					thirdPartyAccount1.setChannel(channel);
					thirdPartyAccount1.setCompany_id(id);
					thirdPartyAccount1.setMember_name(data.getMembername());
					thirdPartyAccount1.setPassword(data.getPassword());
					thirdPartyAccount1.setUsername(data.getUsername());
					thirdPartyAccount1.setRemainNum(synchronizeResult.getRemainNum());
					//更新第三方帐号信息
					Response response = hraccountDao.upsertThirdPartyAccount(thirdPartyAccount1);
					if(response.getStatus() == 0) {
						HashMap<String, Object> result = new HashMap<>();
						result.put("remain_num", synchronizeResult.getRemainNum());
						result.put("sync_time", (new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
						return ResultMessage.SUCCESS.toResponse();
					} else {
						return ResultMessage.THIRD_PARTY_ACCOUNT_SYNC_FAILED.toResponse();
					}
				} else {
					return ResultMessage.THIRD_PARTY_ACCOUNT_SYNC_FAILED.toResponse();
				}
			} else {
				return ResultMessage.THIRD_PARTY_ACCOUNT_UNBOUND.toResponse();
			}
		} catch (TException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return ResultMessage.PROGRAM_EXCEPTION.toResponse();
		} finally {
			//do nothing
		}
	}
}
