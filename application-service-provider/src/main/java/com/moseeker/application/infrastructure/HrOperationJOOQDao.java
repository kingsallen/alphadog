package com.moseeker.application.infrastructure;

import com.moseeker.baseorm.db.hrdb.tables.daos.HrOperationRecordDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationRecord;
import org.jooq.Condition;
import org.jooq.Configuration;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static com.moseeker.baseorm.db.hrdb.tables.HrOperationRecord.HR_OPERATION_RECORD;
import static org.jooq.impl.DSL.using;

/**
 * 由于历史遗留问题导致 HrOperationRecordDao 使用的是struct对应的类。此类对于jooq并非天然支持，所以尝试使用jooq自带生成的数据访问类替换
 * Created by jack on 17/01/2018.
 */
public class HrOperationJOOQDao extends HrOperationRecordDao {

    public HrOperationJOOQDao(Configuration configuration) {
        super(configuration);
    }

    public void insertIfNotExistAdminAndAppId(List<HrOperationRecord> operationRecordList) {

        Condition condition = null;
        for (HrOperationRecord hrOperationRecord : operationRecordList) {
            if (condition == null) {
                condition = HR_OPERATION_RECORD.ADMIN_ID.eq(hrOperationRecord.getAdminId())
                        .and(HR_OPERATION_RECORD.APP_ID.eq(hrOperationRecord.getAppId()));
            } else {
                condition = condition.or(HR_OPERATION_RECORD.ADMIN_ID.eq(hrOperationRecord.getAdminId())
                        .and(HR_OPERATION_RECORD.APP_ID.eq(hrOperationRecord.getAppId())));
            }
        }

        List<HrOperationRecord> exists = using(configuration())
                .selectFrom(HR_OPERATION_RECORD)
                .where(condition)
                .fetchInto(HrOperationRecord.class);

        if (exists != null && exists.size() > 0) {
            Iterator<HrOperationRecord> iterator = operationRecordList.iterator();
            while (iterator.hasNext()) {
                HrOperationRecord hrOperationRecord = iterator.next();
                Optional<HrOperationRecord> optional = exists
                        .stream()
                        .filter(hrOperationRecord1 -> hrOperationRecord1.getAdminId() == hrOperationRecord.getAdminId()
                                && hrOperationRecord1.getAppId() == hrOperationRecord.getAppId())
                        .findAny();
                if (optional.isPresent()) {
                    iterator.remove();
                }
            }
        }

        if (operationRecordList.size() > 0) {
            using(configuration())
                    .insertInto(HR_OPERATION_RECORD)
                    .columns(HR_OPERATION_RECORD.ADMIN_ID, HR_OPERATION_RECORD.COMPANY_ID,
                            HR_OPERATION_RECORD.APP_ID, HR_OPERATION_RECORD.OPT_TIME, HR_OPERATION_RECORD.OPERATE_TPL_ID)
                    .values(operationRecordList)
                    .execute();
        }
    }

}