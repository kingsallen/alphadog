package com.moseeker.baseorm.dao.referraldb;

import com.moseeker.baseorm.db.referraldb.tables.ReferralPositionRel;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralPositionRelRecord;
import org.jooq.Configuration;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.using;

/**
 * @Date: 2018/9/4
 * @Author: JackYang
 */
@Repository
public class ReferralPositionRelDao extends com.moseeker.baseorm.db.referraldb.tables.daos.ReferralPositionRelDao{

    @Autowired
    public ReferralPositionRelDao(Configuration configuration) {
        super(configuration);
    }


    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralPositionRel> fetchReferralRecords(List<Integer> positionIds) {

        Result<ReferralPositionRelRecord>  result = using(configuration())
                .selectFrom(ReferralPositionRel.REFERRAL_POSITION_REL)
                .where(ReferralPositionRel.REFERRAL_POSITION_REL.POSITION_ID.in(positionIds))
                .fetch();
        if (result != null && result.size() > 0) {
            return result
                    .stream()
                    .map(record -> record.into(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralPositionRel.class))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }


}
