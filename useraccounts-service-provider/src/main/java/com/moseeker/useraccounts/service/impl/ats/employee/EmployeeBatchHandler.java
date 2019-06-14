package com.moseeker.useraccounts.service.impl.ats.employee;

import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.SearchengineEntity;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeBatchForm;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import com.moseeker.baseorm.constant.EmployeeAuthMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.moseeker.baseorm.db.userdb.tables.UserEmployee.USER_EMPLOYEE;

@Component
public class EmployeeBatchHandler {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeBatchHandler.class);

    @Autowired
    protected UserEmployeeDao employeeDao;

    @Autowired
    private SearchengineEntity searchengineEntity;

    @Autowired
    private EmployeeEntity employeeEntity;

    private final static int NEED_UPDATE = 2;
    private final static int NEED_ADD = 1;
    private final static int BATCH_SIZE = 500;
    private final static int BATCH_DELETE_SIZE = 500;


    public int[] postPutUserEmployeeBatch(UserEmployeeBatchForm batchForm) throws Exception {
        if (batchForm == null || batchForm.getData() == null || batchForm.getData().size() == 0) {
            return new int[0];
        }

        // 默认物理删除
        if (!batchForm.isSetCancel_auth()) {
            batchForm.setCancel_auth(false);
        }

        logger.info("postPutUserEmployeeBatch {},del_not_include:{},as_task:{},cancel_auth:{},auth_method:{},总数据:{}条",
                batchForm.getCompany_id(), batchForm.isDel_not_include(),batchForm.isAs_task() ,
                batchForm.isCancel_auth(), batchForm.getAuth_method(), batchForm.getData().size());

        //这批数据的特征值集合
        Map<String, Integer> uniqueFlags = new HashMap<>();

        int[] dataStatus = new int[batchForm.getData().size()];//对应的数据的操作类型1，插入，2：更新，0：无效的数据

        EmployeeEntityBiz.getUniqueFlagsAndStatus(batchForm, uniqueFlags, dataStatus);

        logger.info("postPutUserEmployeeBatch {},有效的数据:{}条", batchForm.getCompany_id(), uniqueFlags.size());

        EmployeeAuthMethod authMethod = EmployeeAuthMethod.getAuthMethod(batchForm.getAuth_method());
        Set<Integer> delIds = new HashSet<>();

        int page = 1;
        int pageSize = 1000;

        Query.QueryBuilder builder;
        List<UserEmployeeRecord> records;
        //每次取出1000条检查，把不在userEmployees里面的数据的id记录到delIds
        while (true) {
            logger.info("postPutUserEmployeeBatch {},检查数据:page:{}", batchForm.getCompany_id(), page);
            builder = new Query.QueryBuilder();
            builder.where(USER_EMPLOYEE.COMPANY_ID.getName(), batchForm.getCompany_id());
            builder.where(USER_EMPLOYEE.AUTH_METHOD.getName(), batchForm.getAuth_method());
            builder.setPageSize(pageSize);
            builder.setPageNum(page);
            records = employeeDao.getRecords(builder.buildQuery());

            //取完了
            if (records == null || records.size() == 0) {
                break;
            }
            int index;
            String flag;
            //开始检查
            for (UserEmployeeRecord record : records) {
                flag = authMethod.uniqueKey(record);
                if (uniqueFlags.containsKey(flag)) {
                    //这条数据需要更新
                    index = uniqueFlags.get(flag);
                    dataStatus[index] = NEED_UPDATE;
                    UserEmployeeStruct formData = batchForm.getData().get(index);
                    if(StringUtils.isEmptyList(formData.getUpdateIds())){
                        formData.setUpdateIds(new ArrayList<>());
                    }
                    formData.getUpdateIds().add(record.getId());    //一条ats传过来的UserEmployeeStruct可能对应多条数据库的user_employee数据
                } else {
                    //这条数据需要删除
                    delIds.add(record.getId());
                }
            }

            //取完了
            if (records.size() != pageSize) {
                break;
            }

            //继续取下1000条记录检查
            page++;

        }

        logger.info("postPutUserEmployeeBatch {},del_not_include:{},不在集合中的数据:{}条", batchForm.getCompany_id(), batchForm.isDel_not_include(), delIds.size());

        if (batchForm.isDel_not_include() && delIds.size() > 0) {
            delEmployees(delIds, batchForm);
        }

        //要更新的数据
        List<UserEmployeeStruct> updateDatas = new ArrayList<>();

        //要添加的数据
        List<UserEmployeeStruct> addDatas = new ArrayList<>();

        for (int i = 0; i < dataStatus.length; i++) {
            if (dataStatus[i] == NEED_ADD) {
                batchForm.getData().get(i).setAuth_method((byte)batchForm.auth_method);
                addDatas.add(batchForm.getData().get(i));
            } else if (dataStatus[i] == NEED_UPDATE) {
                updateDatas.add(batchForm.getData().get(i));
            }
        }

        logger.info("postPutUserEmployeeBatch {},需要添加的数据:{}条", batchForm.getCompany_id(), addDatas.size());
        logger.info("postPutUserEmployeeBatch {},需要更新的数据:{}条", batchForm.getCompany_id(), updateDatas.size());


        if (addDatas.size() > 0) {
            addEmployees(addDatas, batchForm);
        }

        if (updateDatas.size() > 0) {
            updateEmployees(createUpdateDatas(updateDatas), batchForm);
        }

        logger.info("postPutUserEmployeeBatch {},result:{},", batchForm.getCompany_id(), dataStatus.length < 500 ? Arrays.toString(dataStatus) : ("成功处理" + dataStatus.length + "条"));

        return dataStatus;
    }

    /**
     * 添加员工记录集合。 会向员工记录中添加数据的同时，往ES员工索引维护队列中增加维护员工记录的任务。
     *
     * @param userEmployeeList 员工记录集合
     * @return 添加好的员工记录。如果参数是空，那么返回值是null
     * @throws CommonException
     */
    private List<UserEmployeeRecord> addEmployeeRecordList(List<UserEmployeeRecord> userEmployeeList) throws Exception {
        if (userEmployeeList != null && userEmployeeList.size() > 0) {
            List<UserEmployeeRecord> employeeDOS = employeeDao.casBatchInsert(userEmployeeList);

            searchengineEntity.updateEmployeeAwards(employeeDOS.stream().map(m -> m.getId()).collect(Collectors.toList()));

            return employeeDOS;
        } else {
            return null;
        }
    }

    /**
     * 添加员工数据
     *
     * @param addDatas
     * @param batchForm
     */
    private void addEmployees(List<UserEmployeeStruct> addDatas, UserEmployeeBatchForm batchForm) throws Exception {
        //每次最多一次插入100条
        int start = 0;
        while ((start + BATCH_SIZE) < addDatas.size()) {
            addEmployeeRecordList(BeanUtils.structToDB(addDatas.subList(start, start + BATCH_SIZE), UserEmployeeRecord.class));
            start += BATCH_SIZE;
            logger.info("postPutUserEmployeeBatch {},批量插入数据{}条,剩余{}条", batchForm.getCompany_id(), BATCH_SIZE, addDatas.size() - start);
        }
        addEmployeeRecordList(BeanUtils.structToDB(addDatas.subList(start, addDatas.size()), UserEmployeeRecord.class));
        logger.info("postPutUserEmployeeBatch {},批量插入数据{}条,剩余{}条", batchForm.getCompany_id(), addDatas.size() - start, 0);
    }

    /**
     * 更新员工数据
     *
     * @param updateDatas
     * @param batchForm
     */
    private void updateEmployees(List<UserEmployeeRecord> updateDatas, UserEmployeeBatchForm batchForm) {
        //每次最多一次更新100条
        int start = 0;
        while ((start + BATCH_SIZE) < updateDatas.size()) {
            employeeDao.updateRecords(updateDatas.subList(start, start + BATCH_SIZE));
            start += BATCH_SIZE;
            logger.info("postPutUserEmployeeBatch {},批量更新数据{}条,剩余{}条", batchForm.getCompany_id(), BATCH_SIZE, updateDatas.size() - start);
        }
        employeeDao.updateRecords(updateDatas.subList(start, updateDatas.size()));
        searchengineEntity.updateEmployeeAwards(updateDatas.subList(start, updateDatas.size()).stream().map(m -> m.getId()).collect(Collectors.toList()));
        logger.info("postPutUserEmployeeBatch {},批量更新数据{}条,剩余{}条", batchForm.getCompany_id(), updateDatas.size() - start, 0);
    }

    /**
     * 一条ats传过来的UserEmployeeStruct可能对应多条数据库的user_employee数据
     * 所以一个UserEmployeeStruct，根据updateIds生成多个UserEmployeeRecord
     *
     * @param partUpdateDatas
     * @return
     */
    private List<UserEmployeeRecord> createUpdateDatas(List<UserEmployeeStruct> partUpdateDatas){
        List<UserEmployeeRecord> datasToBeUpdate = new ArrayList<>();
        for(UserEmployeeStruct updateData:partUpdateDatas){
            if(!StringUtils.isEmptyList(updateData.getUpdateIds())) {
                for (int updateId : updateData.getUpdateIds()) {
                    UserEmployeeRecord temp = BeanUtils.structToDB(updateData, UserEmployeeRecord.class);
                    temp.setId(updateId);
                    datasToBeUpdate.add(temp);
                }
            }
        }
        return datasToBeUpdate;
    }

    /**
     * 删除员工，500个一删
     *
     * @param delIds
     * @param batchForm
     */
    private void delEmployees(Set<Integer> delIds, UserEmployeeBatchForm batchForm) {
        logger.info("postPutUserEmployeeBatch {},删除数据:{}条", batchForm.getCompany_id(), delIds.size());
        //把不在userEmployees中的数据从数据库中删除
        Iterator<Integer> delIterator = delIds.iterator();
        List<Integer> delBatch = new ArrayList<>();

        //数据太多一次最多删除500个
        while (delIterator.hasNext()) {
            delBatch.add(delIterator.next());
            delIterator.remove();
            if (delBatch.size() >= BATCH_DELETE_SIZE) {
                delete(delBatch, batchForm.cancel_auth);
                delBatch.clear();
            }
        }

        if (delBatch.size() > 0) {
            delete(delBatch, batchForm.cancel_auth);
            delBatch.clear();
        }
    }

    /**
     * 根据条件删除员工数据
     *
     * @param delBatch    需要删除的ids
     * @param cancel_auth 是否取消认证
     */
    private void delete(List<Integer> delBatch, boolean cancel_auth) throws CommonException {
        if (cancel_auth) {
            employeeEntity.unbind(delBatch);
        } else {
            employeeEntity.removeEmployee(delBatch);
        }
    }
}
