package com.moseeker.company.service.impl;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrGroupCompanyRelDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.company.constant.ResultMessage;
import com.moseeker.company.exception.ExceptionCategory;
import com.moseeker.company.exception.ExceptionFactory;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.struct.CompanyForVerifyEmployee;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrGroupCompanyRelDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HrCompanyDao companyDao;
    
    @Autowired
    protected HrWxWechatDao wechatDao;

    @Autowired
	HrGroupCompanyRelDao hrGroupCompanyRelDao;

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

	/**
	 * 查找公司所属的集团下的所有公司信息
	 * @param companyId 公司编号
	 * @return 公司集合
	 * @throws BIZException 业务异常
	 */
	public List<CompanyForVerifyEmployee> getGroupCompanies(int companyId) throws BIZException {
		HrGroupCompanyRelDO groupCompanyDO= findGroupCompanyRelByCompanyId(companyId);
		if (groupCompanyDO == null) {
			throw ExceptionFactory.buildException(ExceptionCategory.COMPANY_NOT_BELONG_GROUPCOMPANY);
		}

		Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
		queryBuilder.clear();
		queryBuilder.where("group_id", groupCompanyDO.getGroupId());
		List<HrGroupCompanyRelDO> groupCompanyRelDOList = hrGroupCompanyRelDao.getDatas(queryBuilder.buildQuery());

		if (groupCompanyRelDOList == null || groupCompanyRelDOList.size() == 0) {
			throw ExceptionFactory.buildException(ExceptionCategory.COMPANY_NOT_BELONG_GROUPCOMPANY);
		}

		List<Integer> companyIdList = groupCompanyRelDOList.stream().map(gc -> gc.getCompanyId()).collect(Collectors.toList());

		queryBuilder.clear();
		Condition condition = Condition.buildCommonCondition("id", companyIdList, ValueOp.IN);
		queryBuilder.where(condition);
		List<HrCompanyDO> companyDOList = companyDao.getDatas(queryBuilder.buildQuery());
		if (companyDOList == null || companyDOList.size() == 0) {
			throw ExceptionFactory.buildException(ExceptionCategory.PROGRAM_DATA_EMPTY);
		}

		queryBuilder.clear();
		queryBuilder.select("id").select("company_id").select("signature");
		Condition condition1 = Condition.buildCommonCondition("company_id", companyIdList, ValueOp.IN);
		queryBuilder.where(condition1);

		List<HrWxWechatDO> hrWxWechatDOList = wechatDao.getDatas(queryBuilder.buildQuery());

		List<CompanyForVerifyEmployee> companyForVerifyEmployeeList = companyDOList.stream().map(companyDO -> {
			CompanyForVerifyEmployee companyForVerifyEmployee = new CompanyForVerifyEmployee();
			companyForVerifyEmployee.setId(companyDO.getId());
			companyForVerifyEmployee.setName(companyDO.getName());
			companyForVerifyEmployee.setAbbreviation(companyDO.getAbbreviation());
			return companyForVerifyEmployee;
		}).collect(Collectors.toList());

		if (companyForVerifyEmployeeList != null && companyForVerifyEmployeeList.size() > 0
				&& hrWxWechatDOList != null && hrWxWechatDOList.size() > 0) {
			for (CompanyForVerifyEmployee companyForVerifyEmployee : companyForVerifyEmployeeList) {
				for (HrWxWechatDO wechatDO : hrWxWechatDOList) {
					if (companyForVerifyEmployee.getId() == wechatDO.getCompanyId()) {
						companyForVerifyEmployee.setSignature(wechatDO.getSignature());
						break;
					}
				}
			}
		}

		return companyForVerifyEmployeeList;
	}

	/**
	 * 判断一家公司是否属于集团公司
	 * @param companyId 公司编号
	 * @return 是否是集团公司 true:集团公司 false:非集团公司
	 * @throws BIZException 业务异常
	 */
	public boolean isGroupCompanies(int companyId) throws BIZException {
		HrGroupCompanyRelDO hrGroupCompanyRelDO = findGroupCompanyRelByCompanyId(companyId);
		if (hrGroupCompanyRelDO != null) {
			return true;
		} else {
			return false;
		}
	}

	private HrGroupCompanyRelDO findGroupCompanyRelByCompanyId(int companyId) {
		Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
		queryBuilder.where("company_id", companyId);
		return hrGroupCompanyRelDao.getData(queryBuilder.buildQuery());
	}
}
