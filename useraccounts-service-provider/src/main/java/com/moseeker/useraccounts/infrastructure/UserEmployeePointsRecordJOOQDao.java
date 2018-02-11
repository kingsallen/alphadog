package com.moseeker.useraccounts.infrastructure;

import com.moseeker.baseorm.db.userdb.tables.daos.UserEmployeePointsRecordDao;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeePointsRecordRecord;
import org.jooq.Configuration;

import java.util.ArrayList;
import java.util.List;

import static org.jooq.impl.DSL.using;

/**
 *
 * 由于历史遗留问题导致 UserEmployeePointsRecordDao 使用的是struct对应的类。此类对于jooq并非天然支持，所以尝试使用jooq自带生成的数据访问类替换
 *
 * Created by jack on 22/01/2018.
 */
public class UserEmployeePointsRecordJOOQDao extends UserEmployeePointsRecordDao {

    public UserEmployeePointsRecordJOOQDao(Configuration configuration) {
        super(configuration);
    }

    /**
     * 添加员工积分记录
     * @param userEmployeePointsRecordList 员工积分记录
     * @return 员工积分记录
     */
    public List<UserEmployeePointsRecord> addEmployeeAwards(List<UserEmployeePointsRecord> userEmployeePointsRecordList) {

        List<UserEmployeePointsRecord> list = new ArrayList<>();

        if (userEmployeePointsRecordList != null && userEmployeePointsRecordList.size() > 0) {
            for (UserEmployeePointsRecord userEmployeePointsRecord : userEmployeePointsRecordList) {
                UserEmployeePointsRecordRecord userEmployeePointsRecordRecord = new UserEmployeePointsRecordRecord();
                userEmployeePointsRecordRecord.from(userEmployeePointsRecord);
                using(configuration()).attach(userEmployeePointsRecordRecord);
                list.add(userEmployeePointsRecordRecord.into(UserEmployeePointsRecord.class));
            }
        }

        return list;
    }
}
