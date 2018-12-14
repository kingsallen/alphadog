package com.moseeker.baseorm.dao.referraldb;

import com.moseeker.baseorm.db.referraldb.tables.records.ReferralConnectionChainRecord;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.InsertSetStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import static com.moseeker.baseorm.db.referraldb.tables.ReferralConnectionChain.REFERRAL_CONNECTION_CHAIN;
/**
 * @author cjm
 * @date 2018-12-12 16:26
 **/
@Repository
public class ReferralConnectionChainDao {

    @Autowired
    private DSLContext create;

    public List<ReferralConnectionChainRecord> insertRecords(List<ReferralConnectionChainRecord> records){
        InsertSetStep<ReferralConnectionChainRecord> insertSetStep = create.insertInto(REFERRAL_CONNECTION_CHAIN);
        InsertSetMoreStep<ReferralConnectionChainRecord> insertSetMoreStep = null;
        for (int i=0; i<records.size(); i++) {
            if (i == 0) {
                insertSetMoreStep = insertSetStep.set(records.get(i));
            } else {
                insertSetMoreStep = insertSetMoreStep.newRecord().set(records.get(i));
            }
        }
        if(insertSetMoreStep == null){
            return new ArrayList<>();
        }
       return insertSetMoreStep.returning().fetch();
    }

    public ReferralConnectionChainRecord fetchRecordById(int chainId) {
        return create.selectFrom(REFERRAL_CONNECTION_CHAIN)
                .where(REFERRAL_CONNECTION_CHAIN.ID.eq(chainId))
                .fetchOne();
    }

    public List<ReferralConnectionChainRecord> fetchChainsByRootChainId(int parentChainId) {
        return create.selectFrom(REFERRAL_CONNECTION_CHAIN)
                .where(REFERRAL_CONNECTION_CHAIN.ROOT_PARENT_ID.eq(parentChainId))
                .fetchInto(ReferralConnectionChainRecord.class);
    }

    public ReferralConnectionChainRecord insertRecord(ReferralConnectionChainRecord newChainRecord) {
        return create.insertInto(REFERRAL_CONNECTION_CHAIN)
                .values(newChainRecord)
                .returning()
                .fetchOne();
    }
}
