package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.Query.QueryBuilder;
import com.moseeker.thrift.gen.dao.struct.UserEmployeeDO;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.jooq.impl.DSL.sum;

@Repository
public class UserEmployeeDao extends JooqCrudImpl<UserEmployeeDO, UserEmployeeRecord> {

    public UserEmployeeDao() {
        super(UserEmployee.USER_EMPLOYEE, UserEmployeeDO.class);
    }

    public UserEmployeeDao(TableImpl<UserEmployeeRecord> table, Class<UserEmployeeDO> userEmployeeDOClass) {
        super(table, userEmployeeDOClass);
    }

    public UserEmployeeDO getEmployee(com.moseeker.common.util.query.Query query) {
        UserEmployeeDO employee = new UserEmployeeDO();

        try {
            UserEmployeeRecord record = this.getRecord(query);
            if (record != null) {
                employee = BeanUtils.DBToStruct(UserEmployeeDO.class, record);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }

        return employee;
    }

    public List<UserEmployeeRecord> getEmployeeByWeChat(Integer companyId, List<Integer> weChatIds) throws Exception {
        List<UserEmployeeRecord> list = new ArrayList<UserEmployeeRecord>();
        SelectJoinStep<Record> table = create.select().from(UserEmployee.USER_EMPLOYEE);
        table.where(UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(companyId))
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte) 0));
        Result<Record> result = table.fetch();
        if (result != null && result.size() > 0) {
            for (Record r : result) {
                list.add((UserEmployeeRecord) r);
            }
        }
        return list;
    }

    public int delResource(com.moseeker.common.util.query.Query query) throws Exception {
        if (query != null && query.getConditions() != null) {
            List<UserEmployeeRecord> records = getRecords(query);
            if (records.size() > 0) {
                return deleteRecords(records).length;
            }
        }
        return 0;
    }

    public int[] postPutUserEmployeeBatch(List<UserEmployeeStruct> userEmployees) throws Exception {
        int[] successArray = new int[userEmployees.size()];
        int i = 0;
        for (UserEmployeeStruct struct : userEmployees) {
        	Query.QueryBuilder builder=null;
        	
//            QueryUtil queryUtil = null;
            if (struct.isSetCompany_id() && struct.isSetCustom_field()) {
            	builder=new Query.QueryBuilder();
            	builder.where("company_id", struct.getCompany_id())
            	.and("custom_field", struct.getCustom_field());
//                queryUtil = new QueryUtil();
//                queryUtil.addEqualFilter("company_id", String.valueOf(struct.getCompany_id()));
//                queryUtil.addEqualFilter("custom_field", struct.getCustom_field());
            } else if (struct.isSetCompany_id() && struct.isSetCname() && struct.isSetCfname()) {
            	builder=new Query.QueryBuilder();
            	builder.where("company_id", struct.getCompany_id()).and("cname", struct.getCname());
//                queryUtil = new QueryUtil();
//                queryUtil.addEqualFilter("company_id", String.valueOf(struct.getCompany_id()));
//                queryUtil.addEqualFilter("cname", struct.getCname());
            }
//            queryUtil.setPageSize(Integer.MAX_VALUE);

            if (builder != null) {
                List<UserEmployeeRecord> userEmployeeRecords = getRecords(builder.buildQuery());
                if (userEmployeeRecords.size() > 0) {
                    int innserSuccessFlag = 0;
                    for (UserEmployeeRecord record : userEmployeeRecords) {
                        UserEmployeeRecord userEmployeeRecord = BeanUtils.structToDB(struct, UserEmployeeRecord.class);
                        userEmployeeRecord.setId(record.getId());
                        try {
                            innserSuccessFlag += updateRecord(userEmployeeRecord);
                        } catch (Exception e) {
                        }
                    }
                    successArray[i] = innserSuccessFlag > 0 ? 1 : 0;
                } else {
                    try {
                        UserEmployeeRecord record = addRecord(BeanUtils.structToDB(struct, UserEmployeeRecord.class));
                        successArray[i] = record.getId() > 0 ? 1 : 0;
                    } catch (Exception e) {
                        successArray[i] = 0;
                    }
                }
            } else {
                successArray[i] = 0;
            }
            i++;
        }
        return successArray;
    }

    public int updateUserEmployeePoint(int id) {
        int count = 0;
        Result<Record1<BigDecimal>> result = create.select(sum(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.AWARD))
                .from(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD)
                .where(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.EMPLOYEE_ID.equal((long) id)).fetch();
        if (result != null) {
            Record1<BigDecimal> record1 = result.get(0);
            BigDecimal sum = (BigDecimal) record1.get(0);
            UserEmployeeRecord userEmployeeRecord = new UserEmployeeRecord();
            userEmployeeRecord.setId(id);
            userEmployeeRecord.setAward(sum.intValue());
            create.attach(userEmployeeRecord);
            userEmployeeRecord.update();
            count = sum.intValue();
        }
        return count;
    }
}
