package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictConstant;
import com.moseeker.baseorm.db.dictdb.tables.records.DictConstantRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.CompanyType;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import java.util.ArrayList;
import java.util.List;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrCompanyDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrCompanyDao extends JooqCrudImpl<HrCompanyDO, HrCompanyRecord> {

    public HrCompanyDao() {
        super(HrCompany.HR_COMPANY, HrCompanyDO.class);
    }

    public HrCompanyDao(TableImpl<HrCompanyRecord> table, Class<HrCompanyDO> hrCompanyDOClass) {
        super(table, hrCompanyDOClass);
    }

    public HrCompanyDO getCompanyById(int id){
        Query query = new Query.QueryBuilder().where(HrCompany.HR_COMPANY.ID.getName(),id).buildQuery();
        return getData(query);
    }

    public List<HrCompanyRecord> getAllCompanies(CommonQuery query) throws Exception {
        List<HrCompanyRecord> records = new ArrayList<>();
        try {
            records = getRecords(QueryConvert.commonQueryConvertToQuery(query));
        } catch (Exception e) {
            logger.error("error", e);
            throw new Exception();
        }
        return records;
    }

    public boolean checkRepeatNameWithSuperCompany(String name) {
        boolean repeatName = false;
        Result<Record1<Integer>> result = create.select(HrCompany.HR_COMPANY.ID)
                .from(HrCompany.HR_COMPANY.join(UserHrAccount.USER_HR_ACCOUNT)
                        .on(HrCompany.HR_COMPANY.HRACCOUNT_ID.equal(UserHrAccount.USER_HR_ACCOUNT.ID)))
                .where(HrCompany.HR_COMPANY.NAME.like(name))
                .and(UserHrAccount.USER_HR_ACCOUNT.ACCOUNT_TYPE.equal(Constant.ACCOUNT_TYPE_SUPERACCOUNT)).fetch();
        if (result != null && result.size() > 0) {
            repeatName = true;
        }
        return repeatName;
    }


    public boolean checkScaleIllegal(Byte scale) {
        boolean scaleIllegal = false;
        if (scale != null && scale.intValue() > 0) {
            List<DictConstantRecord> dictScales = create.selectFrom(DictConstant.DICT_CONSTANT)
                    .where(DictConstant.DICT_CONSTANT.PARENT_CODE.equal(Constant.DICT_CONSTANT_COMPANY_SCAL))
                    .and(DictConstant.DICT_CONSTANT.CODE.equal(scale.intValue())).fetch();
            if(dictScales != null && dictScales.size() > 0) {
                scaleIllegal = true;
            }
        } else {
            scaleIllegal = true;
        }
        return scaleIllegal;
    }

    public boolean checkPropertyIllegal(Byte property) {
        boolean scaleIllegal = false;
        if (property != null && property.intValue() > 0) {
            List<DictConstantRecord> dictScales = create.selectFrom(DictConstant.DICT_CONSTANT)
                    .where(DictConstant.DICT_CONSTANT.PARENT_CODE.equal(Constant.DICT_CONSTANT_COMPANY_PROPERTY))
                    .and(DictConstant.DICT_CONSTANT.CODE.equal(property.intValue())).fetch();
            if(dictScales != null && dictScales.size() > 0) {
                scaleIllegal = true;
            }
        } else {
            scaleIllegal = true;
        }
        return scaleIllegal;
    }

    public List<HrCompanyRecord> getCompaniesByIds(List<Integer> companyIds) {
        List<HrCompanyRecord> records = new ArrayList<>();
        if(companyIds != null && companyIds.size() > 0) {
            SelectWhereStep<HrCompanyRecord> select = create.selectFrom(HrCompany.HR_COMPANY);
            SelectConditionStep<HrCompanyRecord> selectCondition = null;
            for(int i=0; i<companyIds.size(); i++) {
                if(i == 0) {
                    selectCondition = select.where(HrCompany.HR_COMPANY.ID.equal((int)(companyIds.get(i))));
                } else {
                    selectCondition.or(HrCompany.HR_COMPANY.ID.equal((int)(companyIds.get(i))));
                }
            }
            records = selectCondition.fetch();
        }
        return records;
    }

    public HrCompanyRecord getCompanyRecordById(int companyId) {
        HrCompanyRecord record = null;
        if(companyId > 0) {
            Result<HrCompanyRecord> result = create.selectFrom(HrCompany.HR_COMPANY)
                    .where(HrCompany.HR_COMPANY.ID.equal((int)(companyId)))
                    .limit(1).fetch();
            if(result != null && result.size() > 0) {
                record = result.get(0);
            }
        }
        return record;
    }

    public List<Hrcompany> getCompanies(Query query) {
        List<Hrcompany> companies = new ArrayList<>();
        companies = getDatas(query, Hrcompany.class);
        return companies;
    }
    
	/*
	获取公司信息的列表
	 */
	public List<HrCompanyDO> getHrCompanyByCompanyIds(List<Integer> ids){
		if(StringUtils.isEmptyList(ids)){
			 return  null;
		 }
		Query query=new Query.QueryBuilder().where(new Condition("id",ids.toArray(),ValueOp.IN)).buildQuery();
		List<HrCompanyDO> list=this.getDatas(query);
		return list;
	}

	/*
	根据id获取公司的
	 */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany getHrCompanyById(int companyId){
        List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany> companys=
         create.selectFrom(HrCompany.HR_COMPANY).where(HrCompany.HR_COMPANY.ID.eq(companyId))
                .fetchInto(com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany.class);
        if(StringUtils.isEmptyList(companys)){
            return null;
        }
        return companys.get(0);
    }
    /*
	根据id获取公司的
	 */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany> getChildHrCompanyById(int companyId){
        List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany> companys=
                create.selectFrom(HrCompany.HR_COMPANY).where(HrCompany.HR_COMPANY.PARENT_ID.eq(companyId)).and(HrCompany.HR_COMPANY.DISABLE.eq((byte)1))
                        .fetchInto(com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany.class);
        if(StringUtils.isEmptyList(companys)){
            return null;
        }
        return companys;
    }

    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany> list(List<Integer> companyIdList) {
        if (companyIdList == null || companyIdList.size() == 0) {
            return new ArrayList<>(0);
        }
        Result<HrCompanyRecord> results = create
                .selectFrom(HrCompany.HR_COMPANY)
                .where(HrCompany.HR_COMPANY.ID.in(companyIdList))
                .fetch();
        return convertToPojos(results);
    }

    public int count() {
        return create
                .selectCount()
                .from(HrCompany.HR_COMPANY)
                .where(HrCompany.HR_COMPANY.TYPE.eq((byte) CompanyType.PAY.getCode()))
                .and(HrCompany.HR_COMPANY.PARENT_ID.eq(0))
                .fetchOne()
                .value1();
    }

    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany> list(int pageNo, int pageSize) {

        Result<HrCompanyRecord> result = create
                .selectFrom(HrCompany.HR_COMPANY)
                .where(HrCompany.HR_COMPANY.TYPE.eq((byte) CompanyType.PAY.getCode()))
                .and(HrCompany.HR_COMPANY.PARENT_ID.eq(0))
                .limit((pageNo-1)*pageSize,pageSize)
                .fetch();
        return convertToPojos(result);

    }

    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany> convertToPojos(Result<HrCompanyRecord> result) {
        if (result != null && result.size() > 0) {
            return result.into(com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany.class);
        } else {
            return new ArrayList<>(0);
        }
    }
}
