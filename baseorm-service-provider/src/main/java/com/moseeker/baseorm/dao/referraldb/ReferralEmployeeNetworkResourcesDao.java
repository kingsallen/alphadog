package com.moseeker.baseorm.dao.referraldb;

import static com.moseeker.baseorm.db.referraldb.tables.ReferralEmployeeNetworkResources.REFERRAL_EMPLOYEE_NETWORK_RESOURCES;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralEmployeeNetworkResourcesRecord;
import java.util.List;
import java.util.Set;
import org.jooq.Configuration;
import static org.jooq.impl.DSL.using;
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


    public List<ReferralEmployeeNetworkResourcesRecord> fetchByPostUserIdPage(int postUserId, Set<Integer> presenteeUserId, int page, int size){
        return using(configuration()).selectFrom(REFERRAL_EMPLOYEE_NETWORK_RESOURCES)
                .where(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.POST_USER_ID.eq(postUserId))
                .and(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.PRESENTEE_USER_ID.notIn(presenteeUserId))
                .and(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.DISABLE.eq((byte)0))
                .orderBy(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.ID.asc())
                .offset((page-1)*size)
                .limit(size)
                .fetch();
    }

    public int fetchByPostUserIdCount(int postUserId, Set<Integer> presenteeUserId){
        return using(configuration()).selectFrom(REFERRAL_EMPLOYEE_NETWORK_RESOURCES)
                .where(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.POST_USER_ID.eq(postUserId))
                .and(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.PRESENTEE_USER_ID.notIn(presenteeUserId))
                .and(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.DISABLE.eq((byte)0))
                .fetchCount();
    }


    public List<ReferralEmployeeNetworkResourcesRecord> fetchByPostUserId(int postUserId){
        return using(configuration()).selectFrom(REFERRAL_EMPLOYEE_NETWORK_RESOURCES)
                .where(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.POST_USER_ID.eq(postUserId))
                .orderBy(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.ID.asc())
                .fetch();
    }

    public void updateReferralEmployeeNetworkResourcesRecord(List<ReferralEmployeeNetworkResourcesRecord>  records){
        using(configuration()).batchUpdate(records).execute();
    }

    public void insertReferralEmployeeNetworkResourcesRecord(List<ReferralEmployeeNetworkResourcesRecord>  records){
        using(configuration()).batchInsert(records).execute();
    }


}
