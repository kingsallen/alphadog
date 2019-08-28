package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.constant.ActivityStatus;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrCompanyConf;
import com.moseeker.baseorm.db.hrdb.tables.HrEmployeeCustomFields;
import com.moseeker.baseorm.db.hrdb.tables.HrHbConfig;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeeCustomFieldsRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbConfigRecord;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeCustomFieldsDO;
import org.jooq.Field;
import org.jooq.Result;
import org.jooq.UpdateSetFirstStep;
import org.jooq.UpdateSetMoreStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by yiliangt5 on 09/02/2017.
 */

@Repository
public class HrEmployeeCustomFieldsDao extends JooqCrudImpl<HrEmployeeCustomFieldsDO, HrEmployeeCustomFieldsRecord> {

    // 0:部门，1:职位，2:城市，3:自定义字段
    public static final byte FIELD_TYPE_POSITION = 1 ;
    public static final byte FIELD_TYPE_CITY = 2 ;

    public HrEmployeeCustomFieldsDao() {
        super(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS, HrEmployeeCustomFieldsDO.class);
    }

    public HrEmployeeCustomFieldsDao(TableImpl<HrEmployeeCustomFieldsRecord> table, Class<HrEmployeeCustomFieldsDO> hrEmployeeCustomFieldsDOClass) {
        super(table, hrEmployeeCustomFieldsDOClass);
    }

    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields> getEmployeeExtConfByCompanyId(int companyId) {

        return create.selectFrom(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS)
                .where(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.COMPANY_ID.eq(companyId))
                .fetchInto(com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields.class);
    }

    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields> fetchByIdList(Integer companyId, Set<Integer> idList) {
        if (companyId == null || companyId <= 0 || idList == null || idList.isEmpty()) {
            return new ArrayList<>();
        }
        Result<HrEmployeeCustomFieldsRecord> result = create
                .selectFrom(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS)
                .where(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.COMPANY_ID.eq(companyId))
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.STATUS.eq(AbleFlag.OLDENABLE.getValue()))
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.DISABLE.eq((byte) AbleFlag.OLDENABLE.getValue()))
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.ID.in(idList))
                .fetch();
        return convertToPojo(result);
    }

    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields> listSelectOptionByIdList(Integer companyId, Set<Integer> idList) {
        if (companyId == null || companyId <= 0 || idList == null || idList.isEmpty()) {
            return new ArrayList<>();
        }
        Result<HrEmployeeCustomFieldsRecord> result = create
                .selectFrom(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS)
                .where(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.COMPANY_ID.eq(companyId))
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.STATUS.eq(AbleFlag.OLDENABLE.getValue()))
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.DISABLE.eq((byte) AbleFlag.OLDENABLE.getValue()))
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.ID.in(idList))
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.OPTION_TYPE.eq(0))
                .fetch();
        return convertToPojo(result);
    }

    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields> fetchRequiredByCompanyId(int companyId) {
        Result<HrEmployeeCustomFieldsRecord> result = create
                .selectFrom(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS)
                .where(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.COMPANY_ID.eq(companyId))
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.STATUS.eq(AbleFlag.OLDENABLE.getValue()))
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.DISABLE.eq((byte) AbleFlag.OLDENABLE.getValue()))
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.MANDATORY.eq(1))
                .fetch();
        return convertToPojo(result);
    }

    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields> listSystemCustomFieldByCompanyIdList(List<Integer> companyIds) {
        Result<HrEmployeeCustomFieldsRecord> result = create
                .selectFrom(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS)
                .where(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.COMPANY_ID.in(companyIds))
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.STATUS.eq(AbleFlag.OLDENABLE.getValue()))
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.DISABLE.eq((byte) AbleFlag.OLDENABLE.getValue()))
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.FIELD_TYPE.in(new ArrayList<Integer>(){{add(0); add(1); add(2);}}))
                .fetch();
        return convertToPojo(result);
    }

    /**
     * 仅仅启用系统字段 部门、职位、城市
     * @param companyId 关联公司ID
     * @return
     */
    public int enableOnlySystemCustomFields(int companyId) {
        // 自定义字段不启用
        create.update(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS)
                .set(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.DISABLE, (byte)1)
                .where(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.STATUS.eq(AbleFlag.OLDENABLE.getValue()))
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.FIELD_TYPE.gt((byte)2))
                .execute();
        // 启用系统字段
        return create.update(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS)
                .set(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.DISABLE, (byte)0)
                .set(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.MANDATORY,0) // 设置为非必填，以免企业微信认证因系统字段为空检验失败
                .where(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.STATUS.eq(AbleFlag.OLDENABLE.getValue()))
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.FIELD_TYPE.in(new ArrayList<Integer>(){{add(0); add(1); add(2);}}))
                .execute();
    }

    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields> listCustomFieldByCompanyIdList(List<Integer> companyIdList) {
        Result<HrEmployeeCustomFieldsRecord> result = create
                .selectFrom(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS)
                .where(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.COMPANY_ID.in(companyIdList))
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.STATUS.eq(AbleFlag.OLDENABLE.getValue()))
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.DISABLE.eq((byte) AbleFlag.OLDENABLE.getValue()))
                .fetch();
        return convertToPojo(result);
    }

    private List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields> convertToPojo(List<HrEmployeeCustomFieldsRecord> result) {
        if (result != null && result.size() > 0) {
            return result.parallelStream()
                    .map(record -> {
                        com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields pojo = record.into(com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields.class);
                        return pojo;
                    })
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>(0);
        }
    }

}
