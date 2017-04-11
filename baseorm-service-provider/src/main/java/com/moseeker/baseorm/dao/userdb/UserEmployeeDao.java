package com.moseeker.baseorm.dao.userdb;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.UserEmployeeDO;

import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import org.apache.thrift.TException;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.moseeker.baseorm.util.BaseDaoImpl;
import com.moseeker.common.dbutils.DBConnHelper;

@Service
public class UserEmployeeDao extends BaseDaoImpl<UserEmployeeRecord, UserEmployee> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void initJOOQEntity() {
        this.tableLike = UserEmployee.USER_EMPLOYEE;
    }

    public UserEmployeeDO getEmployee(CommonQuery query) {
        UserEmployeeDO employee = new UserEmployeeDO();

        try {
            UserEmployeeRecord record = this.getResource(query);
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
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            SelectJoinStep<Record> table = create.select().from(UserEmployee.USER_EMPLOYEE);
            table.where(UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(companyId))
                    .and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte) 0)
                            .and(UserEmployee.USER_EMPLOYEE.WXUSER_ID.notEqual(0))
                            .and(UserEmployee.USER_EMPLOYEE.WXUSER_ID.in(weChatIds))
                    );
            Result<Record> result = table.fetch();
            if (result != null && result.size() > 0) {
                for (Record r : result) {
                    list.add((UserEmployeeRecord) r);
                }
            }
        } catch (Exception e) {
            logger.error("error", e);
            throw new Exception(e);
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                conn = null;
            }
        }
        return list;
    }

    public int delResource(CommonQuery query) throws Exception {
        if (query != null && query.getEqualFilter() != null && query.getEqualFilter().size() > 0) {
            List<UserEmployeeRecord> records = getResources(query);
            if (records.size() > 0) {
                return delResources(records);
            }
        }
        return 0;
    }

    public int[] postPutUserEmployeeBatch(List<UserEmployeeStruct> userEmployees) throws Exception {
        int[] successArray = new int[userEmployees.size()];
        int i = 0;
        for (UserEmployeeStruct struct : userEmployees) {
            QueryUtil queryUtil = null;
            if (struct.isSetCompany_id() && struct.isSetCustom_field()) {
                queryUtil = new QueryUtil();
                queryUtil.addEqualFilter("company_id", String.valueOf(struct.getCompany_id()));
                queryUtil.addEqualFilter("custom_field", struct.getCustom_field());
            } else if (struct.isSetCompany_id() && struct.isSetCname() && struct.isSetCfname()) {
                queryUtil = new QueryUtil();
                queryUtil.addEqualFilter("company_id", String.valueOf(struct.getCompany_id()));
                queryUtil.addEqualFilter("cname", struct.getCname());
            }
            queryUtil.setPer_page(Integer.MAX_VALUE);

            if (queryUtil != null) {
                List<UserEmployeeRecord> userEmployeeRecords = getResources(queryUtil);
                if (userEmployeeRecords.size() > 0) {
                    int innserSuccessFlag = 0;
                    for (UserEmployeeRecord record : userEmployeeRecords) {
                        UserEmployeeRecord userEmployeeRecord = BeanUtils.structToDB(struct, UserEmployeeRecord.class);
                        userEmployeeRecord.setId(record.getId());
                        try {
                            innserSuccessFlag += putResource(userEmployeeRecord);
                        }catch (Exception e){
                        }
                    }
                    successArray[i] = innserSuccessFlag > 0 ? 1 : 0;
                } else {
                    try {
                        successArray[i] = postResource(BeanUtils.structToDB(struct, UserEmployeeRecord.class));
                    }catch (Exception e){
                        successArray[i] = 0;
                    }
                }
            }else{
                successArray[i] = 0;
            }
            i++;
        }
        return successArray;
    }
}
