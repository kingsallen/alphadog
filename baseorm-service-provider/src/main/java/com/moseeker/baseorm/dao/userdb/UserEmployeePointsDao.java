package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeePointsRecordRecord;
import com.moseeker.baseorm.pojo.EmployeePointsRecordPojo;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeePointsRecordDO;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.ResultOrRows;
import org.jooq.impl.TableImpl;
import org.slf4j.LoggerFactory;
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


    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

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
        if (records != null && records.size() > 0) {
            Result<Record> records1 = records.get(0).result();
            for (Record record : records1) {
                if (record.get("timespan") == null) {
                    continue;
                }
                if (record.get("award") == null) {
                    continue;
                }
                if (record.get("last_update_time") == null) {

                    continue;
                }
                EmployeePointsRecordPojo employeePointsRecordPojo = new EmployeePointsRecordPojo();
                employeePointsRecordPojo.setTimespan((String) record.get("timespan"));
                employeePointsRecordPojo.setAward(((BigInteger) record.get("award")).intValue());
                LocalDateTime localDateTime = LocalDateTime.parse(String.valueOf(record.get("last_update_time")), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                employeePointsRecordPojo.setLast_update_time(localDateTime);
                recordPojos.add(employeePointsRecordPojo);
            }
            logger.info(recordPojos.size() + "");
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
        if (records != null && records.size() > 0) {
            Result<Record> records1 = records.get(0).result();
            for (Record record : records1) {
                if (record.get("timespan") == null) {
                    continue;
                }
                if (record.get("award") == null) {
                    continue;
                }
                if (record.get("last_update_time") == null) {

                    continue;
                }
                EmployeePointsRecordPojo employeePointsRecordPojo = new EmployeePointsRecordPojo();
                employeePointsRecordPojo.setTimespan((String) record.get("timespan"));
                employeePointsRecordPojo.setAward(((BigInteger) record.get("award")).intValue());
                LocalDateTime localDateTime = LocalDateTime.parse(String.valueOf(record.get("last_update_time")), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                employeePointsRecordPojo.setLast_update_time(localDateTime);
                recordPojos.add(employeePointsRecordPojo);
            }
            logger.info(recordPojos.size() + "");
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
        Result<Record> records1 = records.get(0).result();
        for (Record record : records1) {
            if (record.get("timespan") == null) {
                continue;
            }
            if (record.get("award") == null) {
                continue;
            }
            if (record.get("last_update_time") == null) {

                continue;
            }
            EmployeePointsRecordPojo employeePointsRecordPojo = new EmployeePointsRecordPojo();
            employeePointsRecordPojo.setTimespan((String) record.get("timespan"));
            employeePointsRecordPojo.setAward(((BigInteger) record.get("award")).intValue());
            LocalDateTime localDateTime = LocalDateTime.parse(String.valueOf(record.get("last_update_time")), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            employeePointsRecordPojo.setLast_update_time(localDateTime);
            recordPojos.add(employeePointsRecordPojo);
        }
        logger.info(recordPojos.size() + "");
        return recordPojos;
    }

}
