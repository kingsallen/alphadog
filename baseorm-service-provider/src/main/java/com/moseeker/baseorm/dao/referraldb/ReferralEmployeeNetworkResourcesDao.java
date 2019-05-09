package com.moseeker.baseorm.dao.referraldb;

import static com.moseeker.baseorm.db.jobdb.tables.JobPosition.JOB_POSITION;
import static com.moseeker.baseorm.db.referraldb.tables.ReferralEmployeeNetworkResources.REFERRAL_EMPLOYEE_NETWORK_RESOURCES;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralEmployeeNetworkResourcesRecord;
import com.moseeker.common.util.StringUtils;
import java.util.ArrayList;
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
        return using(configuration()).selectFrom(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.leftJoin(JOB_POSITION)
                .on(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.POSITION_ID.eq(JOB_POSITION.ID)))
                .where(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.POST_USER_ID.eq(postUserId))
                .and(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.PRESENTEE_USER_ID.notIn(presenteeUserId))
                .and(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.DISABLE.eq((byte)0))
                .and(JOB_POSITION.STATUS.eq((byte)0))
                .orderBy(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.ID.asc())
                .offset((page-1)*size)
                .limit(size)
                .fetchInto(ReferralEmployeeNetworkResourcesRecord.class);
    }

    public int fetchByPostUserIdCount(int postUserId, Set<Integer> presenteeUserId,int companyId){
        return using(configuration()).selectCount().from(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.leftJoin(JOB_POSITION)
                .on(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.POSITION_ID.eq(JOB_POSITION.ID)))
                .where(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.POST_USER_ID.eq(postUserId))
                .and(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.PRESENTEE_USER_ID.notIn(presenteeUserId))
                .and(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.DISABLE.eq((byte)0))
                .and(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.COMPANY_ID.eq(companyId))
                .and(JOB_POSITION.STATUS.eq((byte)0))
                .fetchOne().value1();
    }


    public List<ReferralEmployeeNetworkResourcesRecord> fetchByPostUserId(int postUserId){
        List<ReferralEmployeeNetworkResourcesRecord> list =  using(configuration()).selectFrom(REFERRAL_EMPLOYEE_NETWORK_RESOURCES)
                .where(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.POST_USER_ID.eq(postUserId))
                .orderBy(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.ID.asc())
                .fetch();
        if(StringUtils.isEmptyList(list)){
            return new ArrayList<>();
        }
        return list;
    }

    public void updateReferralEmployeeNetworkResourcesRecord(List<ReferralEmployeeNetworkResourcesRecord>  records){
        if(!StringUtils.isEmptyList(records)) {
            using(configuration()).batchUpdate(records).execute();
        }
    }

    public void updateNetworkResourcesRecordByPosyUserIds(List<Integer>  postUserIds, byte disable){
        if(!StringUtils.isEmptyList(postUserIds)) {
            using(configuration()).update(REFERRAL_EMPLOYEE_NETWORK_RESOURCES)
                    .set(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.DISABLE, disable)
                    .where(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.POST_USER_ID.in(postUserIds))
                    .execute();
        }
    }

    public void insertReferralEmployeeNetworkResourcesRecord(List<ReferralEmployeeNetworkResourcesRecord>  records){
        if(!StringUtils.isEmptyList(records)) {
            using(configuration()).batchInsert(records).execute();
        }
    }

    public void updateStatusByCompanyId(int companyId) {
        using(configuration()).update(REFERRAL_EMPLOYEE_NETWORK_RESOURCES)
                .set(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.DISABLE, (byte)1)
                .where(REFERRAL_EMPLOYEE_NETWORK_RESOURCES.COMPANY_ID.eq(companyId))
                .execute();
    }
}
