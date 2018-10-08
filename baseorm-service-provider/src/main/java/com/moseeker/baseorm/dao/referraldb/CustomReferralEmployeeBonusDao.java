package com.moseeker.baseorm.dao.referraldb;

import com.moseeker.baseorm.db.referraldb.tables.daos.ReferralEmployeeBonusRecordDao;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralEmployeeBonusRecordRecord;
import org.jooq.Configuration;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.moseeker.baseorm.db.referraldb.tables.ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD;
import static org.jooq.impl.DSL.using;

/**
 * @Author: jack
 * @Date: 2018/9/28
 */
@Repository
public class CustomReferralEmployeeBonusDao extends ReferralEmployeeBonusRecordDao {

    @Autowired
    public CustomReferralEmployeeBonusDao(Configuration configuration) {
        super(configuration);
    }


    public List<ReferralEmployeeBonusRecord> fetchByEmployeeIdOrderByClaim(int employeeId,int pageNum,int pageSize) {
        Result<ReferralEmployeeBonusRecordRecord> result =  using(configuration())
                .selectFrom(REFERRAL_EMPLOYEE_BONUS_RECORD)
                .where(REFERRAL_EMPLOYEE_BONUS_RECORD.EMPLOYEE_ID.eq(employeeId))
                .orderBy(REFERRAL_EMPLOYEE_BONUS_RECORD.CLAIM.asc(), REFERRAL_EMPLOYEE_BONUS_RECORD.UPDATE_TIME.desc()).
                        limit((pageNum - 1) * pageNum, pageSize)
                .fetch();
        if (result != null && result.size() > 0) {
            return result.into(ReferralEmployeeBonusRecord.class);
        } else {
            return new ArrayList<>();
        }
    }


}
