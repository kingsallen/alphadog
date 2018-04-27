package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.UserWxEntity;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeBatchForm;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import com.moseeker.useraccounts.domain.AwardEntity;
import com.moseeker.useraccounts.infrastructure.AwardRepository;
import com.moseeker.useraccounts.service.aggregate.ApplicationsAggregateId;
import com.moseeker.useraccounts.service.constant.AwardEvent;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by eddie on 2017/3/9.
 */
@Service
public class UserEmployeeServiceImpl {

    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserEmployeeDao userEmployeeDao;

    @Autowired
    EmployeeEntity employeeEntity;

    @Autowired
    AwardRepository awardRepository;

    @Autowired
    private UserWxEntity userWxEntity;



    @Resource(name = "cacheClient")
    private RedisClient client;

    public Response getUserEmployee(CommonQuery query) throws TException {
        return getResource(query);
    }

    public Response getUserEmployees(CommonQuery query) throws TException {
        return getResources(query);
    }

    public Response delUserEmployee(CommonQuery query) throws TException {
        return delResource(query);
    }

    public Response postPutUserEmployeeBatch(UserEmployeeBatchForm batchForm) throws TException {
        try {
            if (batchForm.as_task) {
                new Thread(() -> {
                    try {
                        employeeEntity.postPutUserEmployeeBatch(batchForm);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
                Response response = new Response();
                response.setStatus(0);
                response.setMessage("任务已提交，数据处理中");
                response.setData(JSON.toJSONString(new int[0]));
                return response;
            } else {
                return ResponseUtils.success(employeeEntity.postPutUserEmployeeBatch(batchForm));
            }
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response getResource(CommonQuery query) throws TException {
        try {
            UserEmployeeRecord result = userEmployeeDao.getRecord(QueryConvert.commonQueryConvertToQuery(query));
            if (result != null) {
                return ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(result.into(UserEmployeeStruct.class)));
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response getResources(CommonQuery query) throws TException {
        try {
            List<UserEmployeeStruct> result = userEmployeeDao.getDatas(QueryConvert.commonQueryConvertToQuery(query),UserEmployeeStruct.class);
//            List<UserEmployeeRecord> result = userEmployeeDao.getRecords(QueryConvert.commonQueryConvertToQuery(query));
            if (result != null) {
//                List<UserEmployeeStruct> userEmployeeStructs = new ArrayList<>(result.size());
                List<Map<String,Object>> list=new ArrayList<>();
                for (UserEmployeeStruct uer : result) {
//                    userEmployeeStructs.add(uer.into(UserEmployeeStruct.class));
                    String employees=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(uer);
                    Map<String,Object> employeeData= JSON.parseObject(employees, Map.class);
//                    logger.info(JSON.toJSONString(employeeData));
                    list.add(employeeData);
                }
                return ResponseUtils.success(list);
//                return ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(userEmployeeStructs));
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response getResourceCount(CommonQuery query) throws TException {
        try {
            int count = userEmployeeDao.getCount(QueryConvert.commonQueryConvertToQuery(query));
            return ResponseUtils.success(count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response postResource(UserEmployeeStruct record) throws TException {
        try {
            int result = userEmployeeDao.addRecord(BeanUtils.structToDB(record, UserEmployeeRecord.class)).getId();
            if (result > 0) {
                return ResponseUtils.success(result);
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
            }
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    private List<UserEmployeeRecord> convertDB(List<UserEmployeeStruct> employeeStructs) {
        List<UserEmployeeRecord> list = new ArrayList<>();
        for (UserEmployeeStruct struct : employeeStructs) {
            list.add(BeanUtils.structToDB(struct, UserEmployeeRecord.class));
        }
        return list;
    }

    public Response postResources(List<UserEmployeeStruct> records) throws TException {
        try {
            List<UserEmployeeRecord> employeeRecords = userEmployeeDao.addAllRecord(convertDB(records));
            return ResponseUtils.success(employeeRecords);
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response putResource(UserEmployeeStruct record) throws TException {
        try {
            int result = userEmployeeDao.updateRecord(BeanUtils.structToDB(record, UserEmployeeRecord.class));
            if (result > 0) {
                return ResponseUtils.success(result);
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
            }
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response putResources(List<UserEmployeeStruct> records) throws TException {
        try {
            int[] result = userEmployeeDao.updateRecords(convertDB(records));
            return ResponseUtils.success(result);
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response delResource(CommonQuery query) throws TException {
        try {
            Query newQuery = QueryConvert.commonQueryConvertToQuery(query);
            if (newQuery.getConditions() == null) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
            }

            List<UserEmployeeDO> employeeDOS = userEmployeeDao.getDatas(newQuery);

            if(employeeDOS.size() == 0){
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
            }

            employeeEntity.removeEmployee(employeeDOS.stream().map(item->item.getId()).collect(Collectors.toList()));

            return ResponseUtils.success(true);
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    /**
     * 积分事件中，涉及申请的事件出发积分添加
     * @param applicationIdList 申请编号集合
     * @param awardEvent 积分事件
     */
    public void addEmployeeAward(List<Integer> applicationIdList, AwardEvent awardEvent) {
        /** 初始化业务编号 */
        ApplicationsAggregateId applicationsAggregateId = new ApplicationsAggregateId(applicationIdList, awardEvent);

        //添加积分
        AwardEntity awardEntity = awardRepository.loadAwardEntity(applicationsAggregateId);
        awardEntity.addAward();
    }
    /*
     获取经过认证的员工信息
     */
    public List<UserEmployeeDO> getUserEmployeeEmailValidate(int companyId,String email){
        List<UserEmployeeDO> dataList=getEmployeeData(companyId, email);
        dataList=userWxEntity.handlerData(dataList);
        return dataList;
    }
    private List<UserEmployeeDO> getEmployeeData(int companyId,String email){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and(new Condition("email","%"+email+"%", ValueOp.LIKE))
                .and("disable",1).and("email_isvalid",1).orderBy("update_time", Order.DESC).buildQuery();
        List<UserEmployeeDO>  list=userEmployeeDao.getDatas(query);
        return list;
    }


    /*
     获取最近转发过的员工
     */
    public List<UserEmployeeDO> getPastUserEmployeeEmail(int companyId){
        String result=client.get(Constant.APPID_ALPHADOG, KeyIdentifier.PAST_USER_EMPLOYEE_VALIDATE.toString(),String.valueOf(companyId));
        if(StringUtils.isNotNullOrEmpty(result)){
            List<UserEmployeeDO> list=JSON.parseArray(result,UserEmployeeDO.class);
            return list;
        }
        return new ArrayList<>();
    }

}
