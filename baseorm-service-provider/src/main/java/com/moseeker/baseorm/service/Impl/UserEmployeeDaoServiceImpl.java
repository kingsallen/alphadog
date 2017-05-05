package com.moseeker.baseorm.service.Impl;

import com.moseeker.baseorm.dao.userdb.UserEmployeePointsRecordDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeePointsRecordRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.service.UserEmployeeDaoService;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeePointsRecordDO;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeePointStruct;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeePointSum;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserEmployeeDaoServiceImpl implements UserEmployeeDaoService {
    @Autowired
    private com.moseeker.baseorm.dao.userdb.UserEmployeeDao dao;
    @Autowired
    private UserEmployeePointsRecordDao dao1;

    @Override
    public Response getUserEmployeeByWeChats(Integer companyId, List<Integer> weChatIds) {
        // TODO Auto-generated method stub
        try {
            List<UserEmployeeRecord> result = dao.getEmployeeByWeChat(companyId, weChatIds);
            List<UserEmployeeStruct> data = this.convertStruct(result);
            return ResponseUtils.success(data);
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    private List<UserEmployeeStruct> convertStruct(List<UserEmployeeRecord> list) {
        List<UserEmployeeStruct> datas = new ArrayList<UserEmployeeStruct>();
        if (list != null && list.size() > 0) {
            for (UserEmployeeRecord record : list) {
                UserEmployeeStruct data = BeanUtils.DBToStruct(UserEmployeeStruct.class, record);
                datas.add(data);
            }
        }
        return datas;
    }

    public Response postUserEmployeePointsRecords(List<UserEmployeePointStruct> records) {
        try {
            List<UserEmployeePointsRecordRecord> list = new ArrayList<UserEmployeePointsRecordRecord>();
            for (UserEmployeePointStruct record : records) {
                list.add(BeanUtils.structToDB(record, UserEmployeePointsRecordRecord.class));
            }
            int result = dao1.addAllRecord(list).length;
            return ResponseUtils.success(result);
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response getSumPoint(List<Long> records) {
        try {
            List<UserEmployeePointSum> result = dao1.getSumRecord(records);
            return ResponseUtils.success(result);
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response putUserEmployees(List<UserEmployeeStruct> records) {
        // TODO Auto-generated method stub
        try {
            List<UserEmployeeRecord> list = new ArrayList<UserEmployeeRecord>();
            for (UserEmployeeStruct bean : records) {
                list.add(BeanUtils.structToDB(bean, UserEmployeeRecord.class));
            }
            int result = dao.updateRecords(list).length;
            return ResponseUtils.success(result);
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response putUserEmployeePointsRecords(List<UserEmployeePointStruct> records) {
        // TODO Auto-generated method stub
        try {
            List<UserEmployeePointsRecordRecord> list = new ArrayList<UserEmployeePointsRecordRecord>();
            for (UserEmployeePointStruct record : records) {
                list.add(BeanUtils.structToDB(record, UserEmployeePointsRecordRecord.class));
            }
            int result = dao1.updateRecords(list).length;
            return ResponseUtils.success(result);
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public List<UserEmployeePointsRecordDO> getUserEmployeePoints(int employeeId) {
        List<UserEmployeePointsRecordDO> result = new ArrayList<>();
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("employee_id", String.valueOf(employeeId));
        qu.orderBy("id", Order.DESC);
        qu.setPer_page(Integer.MAX_VALUE);
        result = dao1.getDatas(qu);
        return result;
    }

    @Override
    public Response putUserEmployee(UserEmployeePointsRecordDO employeeDo) {
        try {
            UserEmployeeRecord record = BeanUtils.structToDB(employeeDo, UserEmployeeRecord.class);
            int result = dao.updateRecord(record);
            return ResponseUtils.success(result);
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public List<UserEmployeeDO> getEmployeesDO(Query query) {
        List<UserEmployeeDO> result = new ArrayList<>();
        try {
            List<UserEmployeeRecord> records =
                    dao.getRecords(query);
            result = BeanUtils.DBToStruct(UserEmployeeDO.class, records);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Response putEmployeesDO(List<UserEmployeeDO> employeeDOs) {
        try {
            List<UserEmployeeRecord> list = new ArrayList<UserEmployeeRecord>();
            for (UserEmployeeDO employeeDo : employeeDOs) {
                list.add(BeanUtils.structToDB(employeeDo, UserEmployeeRecord.class));
            }
            int result = dao.updateRecords(list).length;
            return ResponseUtils.success(result);
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }
}
