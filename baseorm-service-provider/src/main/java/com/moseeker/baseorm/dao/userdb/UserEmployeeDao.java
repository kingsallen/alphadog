package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;

import java.math.BigDecimal;
import java.util.*;

import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SelectJoinStep;

import static org.jooq.impl.DSL.sum;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

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
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte) 0))
                .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq((byte) 0));
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

    public int[] postPutUserEmployeeBatch(List<UserEmployeeStruct> userEmployees, int companyId, boolean delNotInclude) throws Exception {


        if (userEmployees == null || userEmployees.size() == 0) {
            return new int[0];
        }

        logger.info("postPutUserEmployeeBatch 总数据:{}条", userEmployees.size());

        Query.QueryBuilder builder = null;

        //这批数据的特征值集合
        List<String> uniqueFlags = new ArrayList<>();
        int[] dataStatus = new int[userEmployees.size()];//对应的数据的操作类型1，插入，2：更新，0：无效的数据
        String flag;
        int index = 0;
        for (UserEmployeeStruct struct : userEmployees) {
           if (struct.isSetCompany_id() && struct.isSetCustom_field()) {
                if (struct.getCustom_field() == null) {
                    struct.setCustom_field("");
                }
                flag = struct.getCompany_id() + "_custom_field_" + struct.getCustom_field().trim();
                if (uniqueFlags.contains(flag)) {
                    dataStatus[index] = 0;//重复的数据
                } else {
                    dataStatus[index] = 1;
                }
                uniqueFlags.add(flag);
            } else if (struct.isSetCompany_id() && struct.isSetCname()) {
                if (struct.getCname() == null) {
                    struct.setCname("");
                }
                flag = struct.getCompany_id() + "_cname_" + struct.getCname().trim();
                if (uniqueFlags.contains(flag)) {
                    dataStatus[index] = 0;//重复的数据
                } else {
                    dataStatus[index] = 1;
                }
                uniqueFlags.add(flag);
            } else {
                //无效
                dataStatus[index] = 0;
            }
            index++;
        }

        logger.info("postPutUserEmployeeBatch 有效的数据:{}条", uniqueFlags.size());

        Set<Integer> delIds = new HashSet<>();
        int page = 1;
        int pageSize = 1000;

        List<UserEmployeeRecord> records;

        //每次取出1000条检查，把不在userEmployees里面的数据的id记录到delIds
        while (true) {
            logger.info("postPutUserEmployeeBatch 检查数据:page:{}", page);
            builder = new Query.QueryBuilder();
            builder.where("company_id", companyId);
            builder.setPageSize(pageSize);
            builder.setPageNum(page);
            records = getRecords(builder.buildQuery());

            //取完了
            if (records == null || records.size() == 0) {
                break;
            }

            String flag1, flag2;
            //开始检查
            for (UserEmployeeRecord record : records) {
                if (record.getCustomField() == null) record.setCustomField("");
                if (record.getCname() == null) record.setCname("");
                flag1 = record.getCompanyId() + "_custom_field_" + record.getCustomField().trim();
                if (uniqueFlags.contains(flag1)) {
                    //这条数据需要更新
                    index = uniqueFlags.indexOf(flag1);
                    dataStatus[index] = 2;
                    userEmployees.get(index).setId(record.getId());
                } else {
                    flag2 = record.getCompanyId() + "_cname_" + record.getCname().trim();
                    if (uniqueFlags.contains(flag2)) {
                        //这条数据需要更新
                        index = uniqueFlags.indexOf(flag2);
                        dataStatus[index] = 2;
                        userEmployees.get(index).setId(record.getId());
                    } else {
                        //这条数据需要删除
                        delIds.add(record.getId());
                    }
                }
            }

            //取完了
            if (records.size() != pageSize) {
                break;
            }

            //继续取下1000条记录检查
            page++;

        }

        logger.info("postPutUserEmployeeBatch 不在集合中的数据:{}条", delIds.size());

        if (delNotInclude) {
            logger.info("postPutUserEmployeeBatch 删除数据:{}条", delIds.size());
            //把不在userEmployees中的数据从数据库中删除
            if (delIds.size() > 0) {
                Condition condition = new Condition("id", delIds, ValueOp.IN);
                delete(condition);
            }
        }

        //要更新的数据
        List<UserEmployeeStruct> updateDatas = new ArrayList<>();

        //要添加的数据
        List<UserEmployeeStruct> addDatas = new ArrayList<>();

        for (int i = 0; i < dataStatus.length; i++) {
            if (dataStatus[i] == 1) {
                addDatas.add(userEmployees.get(i));
            } else if (dataStatus[i] == 2) {
                updateDatas.add(userEmployees.get(i));
            }
        }

        logger.info("postPutUserEmployeeBatch 需要添加的数据:{}条", addDatas.size());
        logger.info("postPutUserEmployeeBatch 需要更新的数据:{}条", updateDatas.size());

        int batchSize = 500;

        if (addDatas.size() > 0) {
            //每次最多一次插入500条
            int start = 0;
            while ((start + batchSize) < addDatas.size()) {
                addAllRecord(BeanUtils.structToDB(addDatas.subList(start, start + batchSize), UserEmployeeRecord.class));
                start += batchSize;
                logger.info("postPutUserEmployeeBatch 批量插入数据{}条,剩余{}条", batchSize, addDatas.size() - start);
            }
            addAllRecord(BeanUtils.structToDB(addDatas.subList(start, addDatas.size()), UserEmployeeRecord.class));
            logger.info("postPutUserEmployeeBatch 批量插入数据{}条,剩余{}条", addDatas.size() - start, 0);
        }

        if (updateDatas.size() > 0) {
            //每次最多一次更新500条
            int start = 0;
            while ((start + batchSize) < updateDatas.size()) {
                updateRecords(BeanUtils.structToDB(updateDatas.subList(start, start + batchSize), UserEmployeeRecord.class));
                start += batchSize;
                logger.info("postPutUserEmployeeBatch 批量更新数据{}条,剩余{}条", batchSize, updateDatas.size() - start);
            }
            updateRecords(BeanUtils.structToDB(updateDatas.subList(start, updateDatas.size()), UserEmployeeRecord.class));
            logger.info("postPutUserEmployeeBatch 批量更新数据{}条,剩余{}条", updateDatas.size() - start, 0);
        }

        return dataStatus;
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
