package com.moseeker.baseorm.dao.referraldb;


import com.moseeker.baseorm.db.referraldb.tables.ReferralApplicationStatusCount;
import com.moseeker.baseorm.db.referraldb.tables.ReferralEmployeeBonusRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralApplicationStatusCountRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralEmployeeBonusRecordRecord;
import com.moseeker.common.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.jooq.Configuration;
import org.jooq.Result;
import org.jooq.SortField;
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


    /**
     *
     * @param tplId
     * @param applicationId
     * @return
     */
    public ReferralApplicationStatusCountRecord fetchApplicationStatusCountByAppicationIdAndTplId(Integer tplId, Integer applicationId) {
        List<ReferralApplicationStatusCountRecord> result = using(configuration())
                .selectFrom(ReferralApplicationStatusCount.REFERRAL_APPLICATION_STATUS_COUNT)
                .where(ReferralApplicationStatusCount.REFERRAL_APPLICATION_STATUS_COUNT.APPLICATION_ID.eq(applicationId))
                .and(ReferralApplicationStatusCount.REFERRAL_APPLICATION_STATUS_COUNT.APPICATION_TPL_STATUS.eq(tplId))
                .fetch();
        if(StringUtils.isEmptyList(result)){
            return null;
        }else{
            return result.get(0);
        }
    }



}
