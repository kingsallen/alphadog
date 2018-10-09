package com.moseeker.baseorm.dao.referraldb;


import com.moseeker.baseorm.db.referraldb.tables.ReferralEmployeeBonusRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralEmployeeBonusRecordRecord;
import org.jooq.Configuration;
import org.jooq.Result;
import org.jooq.SortField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.using;

/**
 * @Date: 2018/9/27
 * @Author: JackYang
 */
@Repository
public class ReferralEmployeeBonusRecordDao extends com.moseeker.baseorm.db.referraldb.tables.daos.ReferralEmployeeBonusRecordDao {

    @Autowired
    public ReferralEmployeeBonusRecordDao(Configuration configuration) {
        super(configuration);
    }


    /**
     * 获取员工最新增的奖金记录
     * @param employeeId
     * @param bonusStageDetailId
     * @return
     */
    public com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord fetchByEmployeeIdStageDetailIdLastOne(Integer employeeId,Integer bonusStageDetailId) {

        //排序
        List<SortField<?>> fields = new ArrayList<>(2);
        fields.add(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.ID.desc());
        ReferralEmployeeBonusRecordRecord referralEmployeeBonusRecordRecord = using(configuration()).selectFrom(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD).
                where(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.EMPLOYEE_ID.eq(employeeId))
                .and(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.BONUS_STAGE_DETAIL_ID.eq(bonusStageDetailId))
                .orderBy(fields).limit(1)
                .fetchOne();
        if (referralEmployeeBonusRecordRecord != null) {
            return referralEmployeeBonusRecordRecord.into(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord.class);
        } else {
            return null;
        }
    }


    /**
     * 获取员工的新增节点奖金记录
     * @param employeeId
     * @param bonusStageDetailId
     * @return
     */
    public com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord fetchByEmployeeIdStageDetailIdGTZero(Integer employeeId,Integer bonusStageDetailId) {

        //排序
        List<SortField<?>> fields = new ArrayList<>(2);
        fields.add(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.ID.desc());
        ReferralEmployeeBonusRecordRecord referralEmployeeBonusRecordRecord = using(configuration()).selectFrom(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD).
                where(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.EMPLOYEE_ID.eq(employeeId))
                .and(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.BONUS_STAGE_DETAIL_ID.eq(bonusStageDetailId))
                .and(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.BONUS.gt(0)).orderBy(fields).limit(1)
                .fetchOne();
        if (referralEmployeeBonusRecordRecord != null) {
            return referralEmployeeBonusRecordRecord.into(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord.class);
        } else {
            return null;
        }
    }

    /**
     * 获取员工的扣减节点奖金记录
     * @param employeeId
     * @param bonusStageDetailId
     * @return
     */
    public com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord fetchByEmployeeIdStageDetailIdLTZero(Integer employeeId,Integer bonusStageDetailId) {

        //排序
        List<SortField<?>> fields = new ArrayList<>(2);
        fields.add(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.ID.desc());

        ReferralEmployeeBonusRecordRecord referralEmployeeBonusRecordRecord = using(configuration()).selectFrom(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD).
                where(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.EMPLOYEE_ID.eq(employeeId))
                .and(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.BONUS_STAGE_DETAIL_ID.eq(bonusStageDetailId))
                .and(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.BONUS.lt(0)).orderBy(fields).limit(1)
                .fetchOne();
        if (referralEmployeeBonusRecordRecord != null) {
            return referralEmployeeBonusRecordRecord.into(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord.class);
        } else {
            return null;
        }
    }


    /**
     * 获取员工的新增节点奖金记录
     * @param employeeId
     * @param bonusStageDetailId
     * @return
     */
    public int countByEmployeeId(Integer employeeId) {

        int count =  using(configuration()).selectCount().from(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD).
                where(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.EMPLOYEE_ID.eq(employeeId))
                .fetchOne().value1().intValue();
        return count;
    }

    /**
     * 获取员工的新增节点奖金记录
     * @param employeeId
     * @param bonusStageDetailId
     * @return
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord> fetchByEmployeeId(Integer employeeId, Integer pageNumber, Integer pageSize) {

        List<SortField<?>> fields = new ArrayList<>(2);
        fields.add(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.ID.desc());

        Result<ReferralEmployeeBonusRecordRecord> result =  using(configuration()).selectFrom(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD).
                where(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.EMPLOYEE_ID.eq(employeeId))
                .orderBy(fields).limit((pageNumber - 1) * pageSize, pageSize)
                .fetch();
        if (result != null && result.size() > 0) {
            return result
                    .stream()
                    .map(record -> record.into(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord.class))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }    }

}
