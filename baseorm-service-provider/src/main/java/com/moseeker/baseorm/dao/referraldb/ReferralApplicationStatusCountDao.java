package com.moseeker.baseorm.dao.referraldb;


import static com.moseeker.baseorm.db.referraldb.tables.ReferralApplicationStatusCount.REFERRAL_APPLICATION_STATUS_COUNT;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralApplicationStatusCount;
import com.moseeker.common.util.StringUtils;
import java.util.List;
import org.jooq.Configuration;
import static org.jooq.impl.DSL.using;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Date: 2018/9/27
 * @Author: JackYang
 */
@Repository
public class ReferralApplicationStatusCountDao extends com.moseeker.baseorm.db.referraldb.tables.daos.ReferralApplicationStatusCountDao {

    @Autowired
    public ReferralApplicationStatusCountDao(Configuration configuration) {
        super(configuration);
    }

    public void addReferralApplicationStatusCount(ReferralApplicationStatusCount statusCount){
        using(configuration())
                .insertInto(REFERRAL_APPLICATION_STATUS_COUNT)
                .columns(REFERRAL_APPLICATION_STATUS_COUNT.APPLICATION_ID, REFERRAL_APPLICATION_STATUS_COUNT.APPICATION_TPL_STATUS,
                        REFERRAL_APPLICATION_STATUS_COUNT.COUNT)
                .values(statusCount.getApplicationId(), statusCount.getAppicationTplStatus(), statusCount.getCount())
                .onDuplicateKeyIgnore()
                .execute();
    }

    public int updateReferralApplicationStatusCount(ReferralApplicationStatusCount statusCount){
        return using(configuration())
                .update(REFERRAL_APPLICATION_STATUS_COUNT)
                .set(REFERRAL_APPLICATION_STATUS_COUNT.COUNT,statusCount.getCount())
                .where(REFERRAL_APPLICATION_STATUS_COUNT.APPLICATION_ID.eq(statusCount.getApplicationId()))
                .and(REFERRAL_APPLICATION_STATUS_COUNT.APPICATION_TPL_STATUS.eq(statusCount.getAppicationTplStatus()))
                .and(REFERRAL_APPLICATION_STATUS_COUNT.COUNT.eq(statusCount.getCount()-1))
                .execute();
    }


    /**
     *
     * @param tplId
     * @param applicationId
     * @return
     */
    public ReferralApplicationStatusCount fetchApplicationStatusCountByAppicationIdAndTplId(Integer tplId, Integer applicationId) {
        List<ReferralApplicationStatusCount> result = using(configuration())
                .selectFrom(REFERRAL_APPLICATION_STATUS_COUNT)
                .where(REFERRAL_APPLICATION_STATUS_COUNT.APPLICATION_ID.eq(applicationId))
                .and(REFERRAL_APPLICATION_STATUS_COUNT.APPICATION_TPL_STATUS.eq(tplId))
                .fetchInto(ReferralApplicationStatusCount.class);
        if(StringUtils.isEmptyList(result)){
            return null;
        }else{
            return result.get(0);
        }
    }



}
