package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeePointsRecordRecord;
import com.moseeker.baseorm.pojo.EmployeePointsRecordPojo;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeePointsRecordDO;

import org.jooq.ResultOrRows;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 11/04/2017.
 */
@Repository
public class UserEmployeePointsDao extends JooqCrudImpl<UserEmployeePointsRecordDO, UserEmployeePointsRecordRecord> {

    public UserEmployeePointsDao() {
        super(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD, UserEmployeePointsRecordDO.class);
    }

    public UserEmployeePointsDao(TableImpl<UserEmployeePointsRecordRecord> table, Class<UserEmployeePointsRecordDO> userEmployeePointsRecordDOClass) {
        super(table, userEmployeePointsRecordDOClass);
    }

    /**
     * 按月获取积分
     *
     * @param employeeIds
     */
    public List<EmployeePointsRecordPojo> getAwardByMonth(Integer employeeIds) {
        String sql = "select date_format(up.`_create_time`,'%Y-%m') as timespan, up.`employee_id`, sum(up.`award`) as award,\n" +
                "   date_format(max(up.`_create_time`),'%Y-%m-%d %H:%i:%s') as last_update_time from userdb.`user_employee_points_record` up where up.employee_id in (" + employeeIds
                + ") group by  up.employee_id,timespan order by timespan";
        List<ResultOrRows> records = create.fetchMany(sql).resultsOrRows();
        List<EmployeePointsRecordPojo> recordPojos = new ArrayList<>();
        for (ResultOrRows resultOrRows : records) {
            if (resultOrRows.result().getValues("timespan") == null || resultOrRows.result().getValues("timespan").size() == 0) {
                continue;
            }
            if (resultOrRows.result().getValues("award") == null || resultOrRows.result().getValues("award").size() == 0) {
                continue;
            }
            if (resultOrRows.result().getValues("last_update_time") == null || resultOrRows.result().getValues("last_update_time").size() == 0) {
                continue;
            }
            EmployeePointsRecordPojo employeePointsRecordPojo = new EmployeePointsRecordPojo();
            employeePointsRecordPojo.setTimespan((String) resultOrRows.result().getValues("timespan").get(0));
            employeePointsRecordPojo.setAward(((BigInteger) resultOrRows.result().getValues("award").get(0)).intValue());
            LocalDateTime localDateTime = LocalDateTime.parse(String.valueOf(resultOrRows.result().getValues("last_update_time").get(0)), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            employeePointsRecordPojo.setLast_update_time(localDateTime);
            recordPojos.add(employeePointsRecordPojo);
        }
        return recordPojos;
    }


    /**
     * 按季获取积分
     *
     * @param employeeIds
     */
    public List<EmployeePointsRecordPojo> getAwardByQuarter(Integer employeeIds) {
        String sql = "select concat(date_format(up.`_create_time`, '%Y'),FLOOR((date_format(up.`_create_time`, '%m')+2)/3)) as timespan, up.`employee_id`, sum(up.`award`) as award," +
                "        date_format(max(up.`_create_time`),'%Y-%m-%d %H:%i:%s') as last_update_time\n" +
                "        from userdb.`user_employee_points_record` up where  up.employee_id in (" + employeeIds +
                ") group by up.employee_id, timespan order by timespan";

        List<ResultOrRows> records = create.fetchMany(sql).resultsOrRows();
        List<EmployeePointsRecordPojo> recordPojos = new ArrayList<>();
        for (ResultOrRows resultOrRows : records) {
            if (resultOrRows.result().getValues("timespan") == null || resultOrRows.result().getValues("timespan").size() == 0) {
                continue;
            }
            if (resultOrRows.result().getValues("award") == null || resultOrRows.result().getValues("award").size() == 0) {
                continue;
            }
            if (resultOrRows.result().getValues("last_update_time") == null || resultOrRows.result().getValues("last_update_time").size() == 0) {
                continue;
            }
            EmployeePointsRecordPojo employeePointsRecordPojo = new EmployeePointsRecordPojo();
            employeePointsRecordPojo.setTimespan((String) resultOrRows.result().getValues("timespan").get(0));
            employeePointsRecordPojo.setAward(((BigInteger) resultOrRows.result().getValues("award").get(0)).intValue());
            LocalDateTime localDateTime = LocalDateTime.parse(String.valueOf(resultOrRows.result().getValues("last_update_time").get(0)), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            employeePointsRecordPojo.setLast_update_time(localDateTime);
            recordPojos.add(employeePointsRecordPojo);
        }
        return recordPojos;
    }


    /**
     * 按年获取积分
     *
     * @param employeeIds
     */
    public List<EmployeePointsRecordPojo> getAwardByYear(Integer employeeIds) {
        String sql = "select date_format(up._create_time,'%Y') as timespan, sum(up.`award`) as award, up.`employee_id`," +
                "        date_format(max(up.`_create_time`),'%Y-%m-%d %H:%i:%s') as last_update_time from userdb.`user_employee_points_record` up  where up.employee_id in (" + employeeIds +
                " ) group by  up.employee_id,timespan order by timespan";
        List<ResultOrRows> records = create.fetchMany(sql).resultsOrRows();
        List<EmployeePointsRecordPojo> recordPojos = new ArrayList<>();
        for (ResultOrRows resultOrRows : records) {
            if (resultOrRows.result().getValues("timespan") == null || resultOrRows.result().getValues("timespan").size() == 0) {
                continue;
            }
            if (resultOrRows.result().getValues("award") == null || resultOrRows.result().getValues("award").size() == 0) {
                continue;
            }
            if (resultOrRows.result().getValues("last_update_time") == null || resultOrRows.result().getValues("last_update_time").size() == 0) {
                continue;
            }
            EmployeePointsRecordPojo employeePointsRecordPojo = new EmployeePointsRecordPojo();
            employeePointsRecordPojo.setTimespan((String) resultOrRows.result().getValues("timespan").get(0));
            employeePointsRecordPojo.setAward(((BigInteger) resultOrRows.result().getValues("award").get(0)).intValue());
            LocalDateTime localDateTime = LocalDateTime.parse(String.valueOf(resultOrRows.result().getValues("last_update_time").get(0)), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            employeePointsRecordPojo.setLast_update_time(localDateTime);
            recordPojos.add(employeePointsRecordPojo);
        }
        return recordPojos;
    }


}
