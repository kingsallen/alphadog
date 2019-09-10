package com.moseeker.baseorm.dao.referraldb;

import com.moseeker.baseorm.config.ClaimType;
import com.moseeker.baseorm.db.referraldb.tables.ReferralLog;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralLogRecord;
import com.moseeker.common.util.StringUtils;
import org.jooq.Configuration;
import org.jooq.Param;
import org.jooq.Record1;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

    Logger logger = LoggerFactory.getLogger(this.getClass());

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

        ReferralLogRecord referralLogRecord = null;

        try{
            referralLogRecord = using(configuration()).insertInto(
                    ReferralLog.REFERRAL_LOG,
                    ReferralLog.REFERRAL_LOG.EMPLOYEE_ID,
                    ReferralLog.REFERRAL_LOG.REFERENCE_ID,
                    ReferralLog.REFERRAL_LOG.POSITION_ID,
                    ReferralLog.REFERRAL_LOG.REFERRAL_TIME,
                    ReferralLog.REFERRAL_LOG.TYPE,
                    ReferralLog.REFERRAL_LOG.ATTEMENT_ID
            ).values(
                    employeeIdParam,
                    referenceIdParam,
                    positionIdParam,
                    referralTime,
                    referralTypeParam,
                    attachmentParam
            ).returning().fetchOne();
        }catch (DuplicateKeyException e){
            logger.error(e.getMessage(),e);
            return 0;
        }

        /*ReferralLogRecord referralLogRecord = using(configuration()).insertInto(
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
                        .and(ReferralLog.REFERRAL_LOG.POSITION_ID.eq(position)).forUpdate()
                ).forUpdate()
        ).returning().fetchOne();*/
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
                .and(ReferralLog.REFERRAL_LOG.REFERENCE_ID.eq(referenceId)
                        .or(ReferralLog.REFERRAL_LOG.OLD_REFERENCE_ID.eq(referenceId)))
                .orderBy(ReferralLog.REFERRAL_LOG.CREATE_TIME.desc())
                .limit(1)
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

    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog> fetchByIds(List<Integer> referralLogIds) {
        List<ReferralLogRecord> referralLogRecords = using(configuration())
                .selectFrom(ReferralLog.REFERRAL_LOG)
                .where(ReferralLog.REFERRAL_LOG.ID.in(referralLogIds))
                .fetchInto(ReferralLogRecord.class);
        if(referralLogIds.size() != referralLogRecords.size()){
            return null;
        }else{
            List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog> referralLogs = new ArrayList<>();
            referralLogRecords.forEach(referralLogRecord -> referralLogs.add(referralLogRecord.into(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog.class)));
            return referralLogs;
        }
    }

    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog>
        fetchByEmployeeIdsAndRefenceId(Integer referenceId){
        List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog> referralLogs =using(configuration())
                .selectFrom(ReferralLog.REFERRAL_LOG)
                .where(ReferralLog.REFERRAL_LOG.REFERENCE_ID.eq(referenceId))
                .orderBy(ReferralLog.REFERRAL_LOG.CREATE_TIME.desc())
                .fetchInto(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog.class);
        if(StringUtils.isEmptyList(referralLogs)){
            return  new ArrayList<>();
        }
        return referralLogs;

    }

    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog>
    fetchByEmployeeIdsAndRefenceIdAndPosition( Integer referenceId, List<Integer> positionIds){
        List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog> referralLogs =using(configuration())
                .selectFrom(ReferralLog.REFERRAL_LOG)
                .where(ReferralLog.REFERRAL_LOG.REFERENCE_ID.eq(referenceId))
                .and(ReferralLog.REFERRAL_LOG.POSITION_ID.in(positionIds))
                .orderBy(ReferralLog.REFERRAL_LOG.CREATE_TIME.desc())
                .fetchInto(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog.class);
        if(StringUtils.isEmptyList(referralLogs)){
            return  new ArrayList<>();
        }
        return referralLogs;

    }

    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog>
    fetchByEmployeeIdAndReferenceIds(Integer employeeId, List<Integer> referenceIds, List<Integer> referencePids) {
        List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog> referralLogs = using(configuration())
                .selectFrom(ReferralLog.REFERRAL_LOG)
                .where(ReferralLog.REFERRAL_LOG.EMPLOYEE_ID.eq(employeeId))
                .and(ReferralLog.REFERRAL_LOG.REFERENCE_ID.in(referenceIds))
                .and(ReferralLog.REFERRAL_LOG.POSITION_ID.in(referencePids))
                .orderBy(ReferralLog.REFERRAL_LOG.CREATE_TIME.desc())
                .fetchInto(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog.class);
        if(StringUtils.isEmptyList(referralLogs)){
            return  new ArrayList<>();
        }
        return referralLogs;
    }

    /**
     * 根据职位和被推荐人查找内推记录
     * 再次内推时，需要查找历史上被认领的推荐记录，避免重复推荐
     * @param positionIds 职位信息
     * @param userId 被推荐人
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog> fetchByPositionIdListAndOldReferenceId(
            List<Integer> positionIds,
            int userId) {
        Result<ReferralLogRecord> result = using(configuration())
                .selectFrom(ReferralLog.REFERRAL_LOG)
                .where(ReferralLog.REFERRAL_LOG.POSITION_ID.in(positionIds))
                .and(ReferralLog.REFERRAL_LOG.OLD_REFERENCE_ID.eq(userId))
                .fetch();
        if (result != null && result.size() > 0) {
            return result.into(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog.class);
        } else {
            return new ArrayList<>(0);
        }
    }
}
