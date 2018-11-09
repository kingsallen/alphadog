package com.moseeker.baseorm.dao.referraldb;

import com.moseeker.baseorm.config.ClaimType;
import com.moseeker.baseorm.db.referraldb.tables.ReferralLog;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralLogRecord;
import com.moseeker.common.util.StringUtils;
import org.jooq.Configuration;
import org.jooq.Param;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.*;

/**
 * @Author: jack
 * @Date: 2018/9/6
 */
@Repository
public class ReferralLogDao extends com.moseeker.baseorm.db.referraldb.tables.daos.ReferralLogDao {

    @Autowired
    public ReferralLogDao(Configuration configuration) {
        super(configuration);
    }

    public int createReferralLog(int employeeId, int referenceId, int position, int referralType, int attachmentId) {

        Param<Integer> employeeIdParam = param(ReferralLog.REFERRAL_LOG.EMPLOYEE_ID.getName(), employeeId);
        Param<Integer> referenceIdParam = param(ReferralLog.REFERRAL_LOG.REFERENCE_ID.getName(), referenceId);
        Param<Integer> positionIdParam = param(ReferralLog.REFERRAL_LOG.POSITION_ID.getName(), position);
        Param<Timestamp> referralTime = param(ReferralLog.REFERRAL_LOG.REFERRAL_TIME.getName(), new Timestamp(System.currentTimeMillis()));
        Param<Integer> referralTypeParam = param(ReferralLog.REFERRAL_LOG.TYPE.getName(), referralType);
        Param<Integer> attachmentParam = param(ReferralLog.REFERRAL_LOG.ATTEMENT_ID.getName(), attachmentId);

        ReferralLogRecord referralLogRecord = using(configuration()).insertInto(
                ReferralLog.REFERRAL_LOG,
                ReferralLog.REFERRAL_LOG.EMPLOYEE_ID,
                ReferralLog.REFERRAL_LOG.REFERENCE_ID,
                ReferralLog.REFERRAL_LOG.POSITION_ID,
                ReferralLog.REFERRAL_LOG.REFERRAL_TIME,
                ReferralLog.REFERRAL_LOG.TYPE,
                ReferralLog.REFERRAL_LOG.ATTEMENT_ID
        ).select(
                select(
                        employeeIdParam,
                        referenceIdParam,
                        positionIdParam,
                        referralTime,
                        referralTypeParam,
                        attachmentParam
                ).whereNotExists(
                        selectOne()
                        .from(ReferralLog.REFERRAL_LOG)
                        .where(ReferralLog.REFERRAL_LOG.EMPLOYEE_ID.eq(employeeId))
                        .and(ReferralLog.REFERRAL_LOG.REFERENCE_ID.eq(referenceId))
                        .and(ReferralLog.REFERRAL_LOG.POSITION_ID.eq(position))
                )
        ).returning().fetchOne();
        if (referralLogRecord != null) {
            return referralLogRecord.getId();
        } else {
            return 0;
        }
    }

    /**
     * 认领推荐记录
     * @param referralLog 认领记录
     * @param userId 认领人
     * @return true 认领成功 false 认领失败
     */
    public boolean claim(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog referralLog, int userId) {
        int execute = using(configuration())
                .update(ReferralLog.REFERRAL_LOG)
                .set(ReferralLog.REFERRAL_LOG.REFERENCE_ID, userId)
                .set(ReferralLog.REFERRAL_LOG.CLAIM, ClaimType.Claimed.getValue())
                .set(ReferralLog.REFERRAL_LOG.CLAIM_TIME, new Timestamp(System.currentTimeMillis()))
                .set(ReferralLog.REFERRAL_LOG.OLD_REFERENCE_ID, referralLog.getReferenceId())
                .where(ReferralLog.REFERRAL_LOG.ID.eq(referralLog.getId()))
                .and(ReferralLog.REFERRAL_LOG.CLAIM.eq(ClaimType.UnClaim.getValue()))
                .andNotExists(
                        selectOne()
                        .from(
                                selectFrom(ReferralLog.REFERRAL_LOG)
                                .where(ReferralLog.REFERRAL_LOG.EMPLOYEE_ID.eq(referralLog.getEmployeeId()))
                                .and(ReferralLog.REFERRAL_LOG.POSITION_ID.eq(referralLog.getPositionId()))
                                .and(ReferralLog.REFERRAL_LOG.REFERENCE_ID.eq(userId))
                        )
                )
                .execute();
        return execute == 1;
    }

    public com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog
    fetchByEmployeeIdReferenceIdUserId(Integer employeeId, Integer referenceId, int positionId) {
        ReferralLogRecord referralLogRecord = using(configuration())
                .selectFrom(ReferralLog.REFERRAL_LOG)
                .where(ReferralLog.REFERRAL_LOG.EMPLOYEE_ID.eq(employeeId))
                .and(ReferralLog.REFERRAL_LOG.REFERENCE_ID.eq(referenceId))
                .and(ReferralLog.REFERRAL_LOG.POSITION_ID.eq(positionId))
                .fetchOne();
        if (referralLogRecord != null) {
            return referralLogRecord.into(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog.class);
        } else {
            return null;
        }
    }

    public com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog
    fetchByEmployeeIdReferenceId(Integer employeeId, Integer referenceId) {
        ReferralLogRecord referralLogRecord = using(configuration())
                .selectFrom(ReferralLog.REFERRAL_LOG)
                .where(ReferralLog.REFERRAL_LOG.EMPLOYEE_ID.eq(employeeId))
                .and(ReferralLog.REFERRAL_LOG.REFERENCE_ID.eq(referenceId))
                .fetchOne();
        if (referralLogRecord != null) {
            return referralLogRecord.into(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog.class);
        } else {
            return null;
        }
    }

    public List<Integer> fetchReferenceIdByEmployeeId(int employeeId) {
        Result<Record1<Integer>> result = using(configuration())
                .select(ReferralLog.REFERRAL_LOG.REFERENCE_ID)
                .from(ReferralLog.REFERRAL_LOG)
                .where(ReferralLog.REFERRAL_LOG.EMPLOYEE_ID.eq(employeeId))
                .fetch();
        if (result != null) {
            return result.stream().map(record1 -> record1.value1()).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog>
        fetchByEmployeeIdsAndRefenceId(List<Integer> employeeIds, Integer referenceId){
        List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog> referralLogs =using(configuration())
                .selectFrom(ReferralLog.REFERRAL_LOG)
                .where(ReferralLog.REFERRAL_LOG.EMPLOYEE_ID.in(employeeIds))
                .and(ReferralLog.REFERRAL_LOG.REFERENCE_ID.eq(referenceId))
                .and(ReferralLog.REFERRAL_LOG.OLD_REFERENCE_ID.ne(0))
                .orderBy(ReferralLog.REFERRAL_LOG.CREATE_TIME.desc())
                .fetchInto(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog.class);
        if(StringUtils.isEmptyList(referralLogs)){
            return  new ArrayList<>();
        }
        return referralLogs;

    }
}
