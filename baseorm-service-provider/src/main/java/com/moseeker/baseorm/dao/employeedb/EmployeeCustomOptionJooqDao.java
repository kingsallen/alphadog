package com.moseeker.baseorm.dao.employeedb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.employeedb.tables.pojos.EmployeeOptionValue;
import com.moseeker.baseorm.db.employeedb.tables.records.EmployeeOptionValueRecord;

import com.moseeker.baseorm.db.hrdb.tables.HrEmployeeCustomFields;
import com.moseeker.common.constants.AbleFlag;
import org.jooq.Condition;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.moseeker.baseorm.db.hrdb.tables.HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS;
import static com.moseeker.baseorm.db.employeedb.tables.EmployeeOptionValue.EMPLOYEE_OPTION_VALUE;

/**
 * TODO
 *
 * @Author jack
 * @Date 2019/6/13 7:00 PM
 * @Version 1.0
 */
@Repository
public class EmployeeCustomOptionJooqDao extends JooqCrudImpl<EmployeeOptionValue, EmployeeOptionValueRecord> {

    public EmployeeCustomOptionJooqDao() {
        super(EMPLOYEE_OPTION_VALUE, EmployeeOptionValue.class);
    }

    public int count(Integer fieldId, List<Integer> optionIdList) {
        if (fieldId == null || fieldId <= 0 || optionIdList == null || optionIdList.size() == 0) {
            return 0;
        }
        return create
                .selectCount()
                .from(EMPLOYEE_OPTION_VALUE)
                .where(EMPLOYEE_OPTION_VALUE.ID.in(optionIdList))
                .and(EMPLOYEE_OPTION_VALUE.CUSTOM_FIELD_ID.eq(fieldId))
                .fetchOne()
                .value1();
    }

    /**
     * 根据ID和自定义信息ID查找下拉项
     * @param customId 自定义信息编号
     * @param optionIdList 编号集合
     * @return 下拉项信息
     */
    public List<EmployeeOptionValue> listByCustomIdAndIdList(Integer customId, Set<Integer> optionIdList) {
        Result<EmployeeOptionValueRecord> result = create
                .selectFrom(EMPLOYEE_OPTION_VALUE)
                .where(EMPLOYEE_OPTION_VALUE.ID.in(optionIdList))
                .and(EMPLOYEE_OPTION_VALUE.CUSTOM_FIELD_ID.eq(customId))
                .orderBy(EMPLOYEE_OPTION_VALUE.PRIORITY)
                .fetch();
        return convertToPOJO(result);
    }

    /**
     * 根据自定义编号和下拉项编号查找存在的数量
     * @param params 自定义编号和下拉项编号
     * @return 符合条件的数量
     */
    public int countByCustomIdAndId(Map<Integer, Integer> params) {
        if (params != null && params.size() > 0) {
            Condition condition = null;
            for (Map.Entry<Integer, Integer> entry : params.entrySet()) {
                if (condition == null) {
                    condition = EMPLOYEE_OPTION_VALUE.CUSTOM_FIELD_ID.eq(entry.getKey())
                            .and(EMPLOYEE_OPTION_VALUE.ID.eq(entry.getValue()));
                } else {
                    condition = condition.or(EMPLOYEE_OPTION_VALUE.CUSTOM_FIELD_ID.eq(entry.getKey())
                            .and(EMPLOYEE_OPTION_VALUE.ID.eq(entry.getValue())));
                }
            }
            logger.info("EmployeeCustomOptionJooqDao countByCustomIdAndId condition:{}",condition);
            if (condition != null) {
                return create
                        .selectCount()
                        .from(EMPLOYEE_OPTION_VALUE)
                        .where(condition)
                        .fetchOne()
                        .value1();
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * 根据ID和自定义信息ID查找下拉项
     * @param customId 自定义信息编号
     * @param names 下拉项名称集合
     * @return 下拉项信息
     */
    public List<EmployeeOptionValue> listByCustomIdAndNames(Integer customId, Set<String> names) {
        Result<EmployeeOptionValueRecord> result = create
                .selectFrom(EMPLOYEE_OPTION_VALUE)
                .where(EMPLOYEE_OPTION_VALUE.NAME.in(names))
                .and(EMPLOYEE_OPTION_VALUE.CUSTOM_FIELD_ID.eq(customId))
                .orderBy(EMPLOYEE_OPTION_VALUE.PRIORITY)
                .fetch();
        return convertToPOJO(result);
    }

    /**
     * 查找指定自定义信息的下拉项
     * @param fieldIdList 自定义配置信息
     * @return 自定义信息下拉项
     */
    public List<EmployeeOptionValue> listByCustomIdList(List<Integer> fieldIdList) {
        Result<EmployeeOptionValueRecord> result = create
                .selectFrom(EMPLOYEE_OPTION_VALUE)
                .where(EMPLOYEE_OPTION_VALUE.CUSTOM_FIELD_ID.in(fieldIdList))
                .fetch();
        return convertToPOJO(result);
    }

    private List<EmployeeOptionValue> convertToPOJO(Result<EmployeeOptionValueRecord> result) {
        if (result != null && result.size() > 0) {
            return result.into(EmployeeOptionValue.class);
        } else {
            return new ArrayList<>(0);
        }
    }

    /**
     * 查找系统字段选项
     * @param companyId
     * @param fieldType
     * @return
     */
    public List<String> listSystemFieldOptions(int companyId, byte fieldType){
        List<String> options = create.select(EMPLOYEE_OPTION_VALUE.NAME)
                .from(HR_EMPLOYEE_CUSTOM_FIELDS)
                .innerJoin(EMPLOYEE_OPTION_VALUE)
                .on(HR_EMPLOYEE_CUSTOM_FIELDS.ID.eq(EMPLOYEE_OPTION_VALUE.CUSTOM_FIELD_ID))
                .where(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.COMPANY_ID.in(companyId))
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.STATUS.eq(AbleFlag.OLDENABLE.getValue()))
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.DISABLE.eq((byte) AbleFlag.OLDENABLE.getValue()))
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.FIELD_TYPE.eq(fieldType))
                .orderBy(EMPLOYEE_OPTION_VALUE.PRIORITY).fetch().into(String.class);
        return options;
    }

    /**
     * 查找系统字段选项
     * @param customId
     * @return
     */
    public  Map<Integer,String> listFieldOptions(int customId){
        Map<Integer,String> options = create.select(EMPLOYEE_OPTION_VALUE.ID,EMPLOYEE_OPTION_VALUE.NAME)
                .from(EMPLOYEE_OPTION_VALUE)
                .where(EMPLOYEE_OPTION_VALUE.CUSTOM_FIELD_ID.eq(customId))
                .orderBy(EMPLOYEE_OPTION_VALUE.PRIORITY).fetch().intoMap(EMPLOYEE_OPTION_VALUE.ID,EMPLOYEE_OPTION_VALUE.NAME);
        return options;
    }

    /**
     * 查找系统字段选项
     * @param customId
     * @return
     */
    public  Map<String,Integer> getFieldOptions(int customId,List<String> values){
        Map<String,Integer> options = create.select(EMPLOYEE_OPTION_VALUE.NAME,EMPLOYEE_OPTION_VALUE.ID)
                .from(EMPLOYEE_OPTION_VALUE)
                .where(EMPLOYEE_OPTION_VALUE.CUSTOM_FIELD_ID.eq(customId))
                .and(EMPLOYEE_OPTION_VALUE.NAME.in(values))
                .orderBy(EMPLOYEE_OPTION_VALUE.PRIORITY).fetch()
                .intoMap(EMPLOYEE_OPTION_VALUE.NAME,EMPLOYEE_OPTION_VALUE.ID);
        return options;
    }

    /*
     * 添加下拉选项
     * */
    public void addCustomOption(List<String> options,int customFieldId){
        List<EmployeeOptionValueRecord> hrEmployeeOptionValuePOS = new ArrayList<>();
        options.stream().forEach(e->{
            EmployeeOptionValueRecord hrEmployeeOptionValuePO = new EmployeeOptionValueRecord();
            hrEmployeeOptionValuePO.setCustomFieldId(customFieldId);
            hrEmployeeOptionValuePO.setName(e);
            hrEmployeeOptionValuePO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            hrEmployeeOptionValuePOS.add(hrEmployeeOptionValuePO);
        });
        create.execute("set names utf8mb4");
        create.attach(hrEmployeeOptionValuePOS);
        create.batchInsert(hrEmployeeOptionValuePOS).execute();
    }

    public int addCustomOption(String option,int customFieldId){
        EmployeeOptionValueRecord hrEmployeeOptionValuePO = new EmployeeOptionValueRecord();
        hrEmployeeOptionValuePO.setCustomFieldId(customFieldId);
        hrEmployeeOptionValuePO.setName(option);
        hrEmployeeOptionValuePO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        create.execute("set names utf8mb4");
        create.executeInsert(hrEmployeeOptionValuePO);
        return create.select(EMPLOYEE_OPTION_VALUE.ID).from(EMPLOYEE_OPTION_VALUE)
                .where(EMPLOYEE_OPTION_VALUE.NAME.eq(option))
                .and(EMPLOYEE_OPTION_VALUE.CUSTOM_FIELD_ID.eq(customFieldId))
                .orderBy(EMPLOYEE_OPTION_VALUE.PRIORITY).fetchOneInto(Integer.class);
    }

   /*
    public static void main(String[] args) throws SQLException {
        String userName = "daqi";
        String password = "7f1a45eac5985519829c929e7bbf0557";
        String url = "jdbc:mysql://db1.dqprism.com:3306/userdb";

        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            //Result<Record> result = create.select().from(EmployeeOptionValue.EMPLOYEE_OPTION_VALUE).fetch();

            //getSystemFieldOption(create,4,(byte) 2);
        }
    }*/
}
