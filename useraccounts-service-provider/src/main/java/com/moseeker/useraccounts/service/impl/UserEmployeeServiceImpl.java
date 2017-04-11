package com.moseeker.useraccounts.service.impl;

import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import org.apache.thrift.TException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eddie on 2017/3/9.
 */
@Service
public class UserEmployeeServiceImpl {

    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    com.moseeker.baseorm.dao.userdb.UserEmployeeDao userEmployeeDao;

    public Response getUserEmployee(CommonQuery query) throws TException {
        return getResource(query);
    }

    public Response getUserEmployees(CommonQuery query) throws TException {
        return getResources(query);
    }

    public Response delUserEmployee(CommonQuery query) throws TException {
        return delResource(query);
    }

    public Response postPutUserEmployeeBatch(List<UserEmployeeStruct> update) throws TException {
        try {
            return ResponseUtils.success(userEmployeeDao.postPutUserEmployeeBatch(update));
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response getResource(CommonQuery query) throws TException {
        try {
            UserEmployeeRecord result = userEmployeeDao.getResource(query);
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
            List<UserEmployeeRecord> result = userEmployeeDao.getResources(query);
            if (result != null) {
                List<UserEmployeeStruct> userEmployeeStructs = new ArrayList<>(result.size());
                for (UserEmployeeRecord uer : result) {
                    userEmployeeStructs.add(uer.into(UserEmployeeStruct.class));
                }
                return ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(userEmployeeStructs));
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
            int count = userEmployeeDao.getResourceCount(query);
            return ResponseUtils.success(count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response postResource(UserEmployeeStruct record) throws TException {
        try {
            int result = userEmployeeDao.postResource(BeanUtils.structToDB(record, UserEmployeeRecord.class));
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
            int result = userEmployeeDao.postResources(convertDB(records));
            return ResponseUtils.success(result);
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response putResource(UserEmployeeStruct record) throws TException {
        try {
            int result = userEmployeeDao.putResource(BeanUtils.structToDB(record, UserEmployeeRecord.class));
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
            int result = userEmployeeDao.putResources(convertDB(records));
            return ResponseUtils.success(result);
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response delResource(CommonQuery query) throws TException {
        try {
            int result = userEmployeeDao.delResource(query);
            if (result > 0) {
                return ResponseUtils.success(result);
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
            }
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

}
