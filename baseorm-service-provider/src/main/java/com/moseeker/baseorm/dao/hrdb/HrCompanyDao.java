package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictConstant;
import com.moseeker.baseorm.db.dictdb.tables.records.DictConstantRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.Constant;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
                    .where(DictConstant.DICT_CONSTANT.PARENT_CODE.equal(Constant.DICT_CONSTANT_COMPANY_SCAL))
                    .and(DictConstant.DICT_CONSTANT.CODE.equal(property.intValue())).fetch();
            if(dictScales != null && dictScales.size() > 0) {
                scaleIllegal = true;
            }
        } else {
            scaleIllegal = true;
        }
        return scaleIllegal;
    }
}
