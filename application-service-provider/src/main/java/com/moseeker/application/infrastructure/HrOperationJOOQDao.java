package com.moseeker.application.infrastructure;

import com.moseeker.baseorm.db.hrdb.tables.daos.HrOperationRecordDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationRecord;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import static com.moseeker.baseorm.db.hrdb.tables.HrOperationRecord.HR_OPERATION_RECORD;
import static org.jooq.impl.DSL.max;
import static org.jooq.impl.DSL.using;

/**
 * 由于历史遗留问题导致 HrOperationRecordDao 使用的是struct对应的类。此类对于jooq并非天然支持，所以尝试使用jooq自带生成的数据访问类替换
 * Created by jack on 17/01/2018.
 */
public class HrOperationJOOQDao extends HrOperationRecordDao {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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

    /**
     * 查找拒绝申请的招聘进度是在哪个状态被拒绝的
     * @param applicationIdList 申请编号列表
     * @return 当前处于拒绝状态的申请，在拒绝之前是什么状态 key是申请的编号，value是申请的状态值
     */
    public Map<Integer, Integer> fetchStatesByAppIds(List<Integer> applicationIdList) {

        Map<Integer, Integer> map = new HashMap<>();
        if (applicationIdList != null && applicationIdList.size() > 0) {
            Result<Record1<Integer>> result = using(configuration())
                    .select(max(HR_OPERATION_RECORD.ID))
                    .from(HR_OPERATION_RECORD)
                    .where(HR_OPERATION_RECORD.APP_ID.in(applicationIdList))
                    .and(HR_OPERATION_RECORD.OPERATE_TPL_ID.ne(4))
                    .groupBy(HR_OPERATION_RECORD.APP_ID)
                    .fetch();

            logger.info("HrOperationJOOQDao fetchStatesByAppIds result: {}", result);
            if (result != null && result.size() > 0) {
                List<Integer> max = result.stream().map(record -> record.value1()).collect(Collectors.toList());

                logger.info("HrOperationJOOQDao fetchStatesByAppIds max: {}", max);
                Result<Record2<Long, Integer>> operationRecordResult = using(configuration())
                        .select(HR_OPERATION_RECORD.APP_ID, HR_OPERATION_RECORD.OPERATE_TPL_ID)
                        .from(HR_OPERATION_RECORD)
                        .where(HR_OPERATION_RECORD.ID.in(max))
                        .fetch();

                logger.info("HrOperationJOOQDao fetchStatesByAppIds operationRecordResult: {}", operationRecordResult);
                if (operationRecordResult != null && operationRecordResult.size() > 0) {
                    operationRecordResult.forEach(record2 -> {
                        map.put(record2.value1().intValue(), record2.value2());
                    });
                }
            }

        }

        logger.info("HrOperationJOOQDao fetchStatesByAppIds map: {}", map);
        return map;
    }
}