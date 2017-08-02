package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecord;
import com.moseeker.baseorm.db.userdb.tables.UserWxUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeBatchForm;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

import static org.jooq.impl.DSL.count;
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


    /**
     * 为每条UserEmployeeStruct生成唯一的值，该值用来判定这条数据是否插入更新或者不处理
     *
     * @param employeeStructs
     * @return
     */
    private void getUniqueFlagsAndStatus(List<UserEmployeeStruct> employeeStructs, List<String> uniqueFlags, int[] dataStatus) {

        String flag;
        int index = 0;
        for (UserEmployeeStruct struct : employeeStructs) {
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
    }

    /**
     * 批量更新插入或删除
     *
     * @param batchForm
     * @return
     * @throws Exception
     */
    public int[] postPutUserEmployeeBatch(UserEmployeeBatchForm batchForm) throws Exception {


        if (batchForm.getData() == null || batchForm.getData().size() == 0) {
            return new int[0];
        }

        logger.info("postPutUserEmployeeBatch {},总数据:{}条", batchForm.getCompany_id(), batchForm.getData().size());

        Query.QueryBuilder builder = null;

        //这批数据的特征值集合
        List<String> uniqueFlags = new ArrayList<>();

        int[] dataStatus = new int[batchForm.getData().size()];//对应的数据的操作类型1，插入，2：更新，0：无效的数据

        getUniqueFlagsAndStatus(batchForm.getData(), uniqueFlags, dataStatus);

        logger.info("postPutUserEmployeeBatch {},有效的数据:{}条", batchForm.getCompany_id(), uniqueFlags.size());

        Set<Integer> delIds = new HashSet<>();
        int page = 1;
        int pageSize = 1000;

        List<UserEmployeeRecord> records;

        //每次取出1000条检查，把不在userEmployees里面的数据的id记录到delIds
        while (true) {
            logger.info("postPutUserEmployeeBatch {},检查数据:page:{}", batchForm.getCompany_id(), page);
            builder = new Query.QueryBuilder();
            builder.where("company_id", batchForm.getCompany_id());
            builder.setPageSize(pageSize);
            builder.setPageNum(page);
            records = getRecords(builder.buildQuery());

            //取完了
            if (records == null || records.size() == 0) {
                break;
            }
            int index;
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
                    batchForm.getData().get(index).setId(record.getId());
                } else {
                    flag2 = record.getCompanyId() + "_cname_" + record.getCname().trim();
                    if (uniqueFlags.contains(flag2)) {
                        //这条数据需要更新
                        index = uniqueFlags.indexOf(flag2);
                        dataStatus[index] = 2;
                        batchForm.getData().get(index).setId(record.getId());
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

        logger.info("postPutUserEmployeeBatch {},不在集合中的数据:{}条", batchForm.getCompany_id(), delIds.size());

        if (batchForm.isDel_not_include() && delIds.size() > 0) {
            logger.info("postPutUserEmployeeBatch {},删除数据:{}条", batchForm.getCompany_id(), delIds.size());
            //把不在userEmployees中的数据从数据库中删除
            int batchDeleteSize = 500;
            Iterator<Integer> delIterator = delIds.iterator();
            List<Integer> delBatch = new ArrayList<>();

            //数据太多一次最多删除500个
            while (delIterator.hasNext()) {
                delBatch.add(delIterator.next());
                delIterator.remove();
                if (delBatch.size() >= 500) {
                    Condition condition = new Condition("id", delBatch, ValueOp.IN);
                    delete(condition);
                    delBatch.clear();
                }
            }

            if (delBatch.size() > 0) {
                Condition condition = new Condition("id", delBatch, ValueOp.IN);
                delete(condition);
                delBatch.clear();
            }
        }

        //要更新的数据
        List<UserEmployeeStruct> updateDatas = new ArrayList<>();

        //要添加的数据
        List<UserEmployeeStruct> addDatas = new ArrayList<>();

        for (int i = 0; i < dataStatus.length; i++) {
            if (dataStatus[i] == 1) {
                addDatas.add(batchForm.getData().get(i));
            } else if (dataStatus[i] == 2) {
                updateDatas.add(batchForm.getData().get(i));
            }
        }

        logger.info("postPutUserEmployeeBatch {},需要添加的数据:{}条", batchForm.getCompany_id(), addDatas.size());
        logger.info("postPutUserEmployeeBatch {},需要更新的数据:{}条", batchForm.getCompany_id(), updateDatas.size());

        int batchSize = 500;

        if (addDatas.size() > 0) {
            //每次最多一次插入100条
            int start = 0;
            while ((start + batchSize) < addDatas.size()) {
                addAllRecord(BeanUtils.structToDB(addDatas.subList(start, start + batchSize), UserEmployeeRecord.class));
                start += batchSize;
                logger.info("postPutUserEmployeeBatch {},批量插入数据{}条,剩余{}条", batchForm.getCompany_id(), batchSize, addDatas.size() - start);
            }
            addAllRecord(BeanUtils.structToDB(addDatas.subList(start, addDatas.size()), UserEmployeeRecord.class));
            logger.info("postPutUserEmployeeBatch {},批量插入数据{}条,剩余{}条", batchForm.getCompany_id(), addDatas.size() - start, 0);
        }

        if (updateDatas.size() > 0) {
            //每次最多一次更新100条
            int start = 0;
            while ((start + batchSize) < updateDatas.size()) {
                updateRecords(BeanUtils.structToDB(updateDatas.subList(start, start + batchSize), UserEmployeeRecord.class));
                start += batchSize;
                logger.info("postPutUserEmployeeBatch {},批量更新数据{}条,剩余{}条", batchForm.getCompany_id(), batchSize, updateDatas.size() - start);
            }
            updateRecords(BeanUtils.structToDB(updateDatas.subList(start, updateDatas.size()), UserEmployeeRecord.class));
            logger.info("postPutUserEmployeeBatch {},批量更新数据{}条,剩余{}条", batchForm.getCompany_id(), updateDatas.size() - start, 0);
        }

        logger.info("postPutUserEmployeeBatch {},result:{},", batchForm.getCompany_id(), dataStatus.length < 500?Arrays.toString(dataStatus):("成功处理"+dataStatus.length+"条"));

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


    public void getListNum(String keyWord, List<Integer> companyIds) {
        org.jooq.Condition cname = UserEmployee.USER_EMPLOYEE.CNAME.like(keyWord);
        org.jooq.Condition customField = UserEmployee.USER_EMPLOYEE.CUSTOM_FIELD.like(keyWord);
        org.jooq.Condition email = UserEmployee.USER_EMPLOYEE.EMAIL.like(keyWord);
        org.jooq.Condition mobile = UserEmployee.USER_EMPLOYEE.MOBILE.like(keyWord);
        org.jooq.Condition nickname = UserWxUser.USER_WX_USER.NICKNAME.like(keyWord);
        org.jooq.Condition company = UserEmployee.USER_EMPLOYEE.COMPANY_ID.in(companyIds);
        create.select(count(UserEmployee.USER_EMPLOYEE.ID), UserEmployee.USER_EMPLOYEE.ACTIVATION).from(UserEmployee.USER_EMPLOYEE)
                .leftJoin(UserWxUser.USER_WX_USER)
                .on(UserEmployee.USER_EMPLOYEE.WXUSER_ID.equal(Integer.valueOf(String.valueOf(UserWxUser.USER_WX_USER.ID))))
                .where(company)
                .and(cname.or(customField).or(email).or(mobile).or(nickname))
                .orderBy(UserEmployee.USER_EMPLOYEE.ACTIVATION).fetch();
    }


}
