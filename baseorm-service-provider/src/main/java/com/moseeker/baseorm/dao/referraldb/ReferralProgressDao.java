package com.moseeker.baseorm.dao.referraldb;

import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralProgress;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralProgressRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moseeker.baseorm.db.referraldb.tables.ReferralProgress.REFERRAL_PROGRESS;

@Repository
public class ReferralProgressDao {

    @Autowired
    private DSLContext create;

    public ReferralProgressRecord fetchByAppid(int appid){
        return create.selectFrom(REFERRAL_PROGRESS)
                .where(REFERRAL_PROGRESS.APP_ID.eq(appid))
                .fetchOneInto(ReferralProgressRecord.class);
    }

    public List<ReferralProgressRecord> fetchByAppids(List<Integer> appids){
        return create.selectFrom(REFERRAL_PROGRESS)
                .where(REFERRAL_PROGRESS.APP_ID.in(appids))
                .fetchInto(ReferralProgressRecord.class);
    }

    public ReferralProgressRecord insertRecord(ReferralProgressRecord referralProgress) {
        return create.insertInto(REFERRAL_PROGRESS)
                .set(referralProgress)
                .returning()
                .fetchOne();
    }

    public void updateRecord(ReferralProgressRecord referralProgress) {
        create.execute("set names utf8mb4");
        create.attach(referralProgress);
        create.executeUpdate(referralProgress);
    }
}
