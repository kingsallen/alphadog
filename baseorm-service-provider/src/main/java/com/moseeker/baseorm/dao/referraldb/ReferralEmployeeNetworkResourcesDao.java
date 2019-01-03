package com.moseeker.baseorm.dao.referraldb;

import static com.moseeker.baseorm.db.referraldb.tables.ReferralEmployeeNetworkResources.REFERRAL_EMPLOYEE_NETWORK_RESOURCES;
import static com.moseeker.baseorm.db.referraldb.tables.ReferralSeekRecommend.REFERRAL_SEEK_RECOMMEND;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralEmployeeNetworkResourcesRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralSeekRecommendRecord;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.jooq.Configuration;
import org.jooq.Param;
import static org.jooq.impl.DSL.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Author: jack
 * @Date: 2018/9/6
 */
@Repository
public class ReferralEmployeeNetworkResourcesDao extends com.moseeker.baseorm.db.referraldb.tables.daos.ReferralEmployeeNetworkResourcesDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ReferralEmployeeNetworkResourcesDao(Configuration configuration) {
        super(configuration);
    }


    public List<ReferralEmployeeNetworkResourcesRecord> fetchByPostUserIdPage(int postUserId, int page, int size){
        return using(configuration()).selectFrom(REFERRAL_EMPLOYEE_NETWORK_RESOURCES)
                .where(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.POST_USER_ID.eq(postUserId))
                .orderBy(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.ID.asc())
                .offset((page-1)*size)
                .limit(size)
                .fetch();
    }

    public int fetchByPostUserIdCount(int postUserId){
        return using(configuration()).selectFrom(REFERRAL_EMPLOYEE_NETWORK_RESOURCES)
                .where(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.POST_USER_ID.eq(postUserId))
                .fetchCount();
    }


}
