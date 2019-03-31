package com.moseeker.baseorm.dao.redpacketdb;

import com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketActivity;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moseeker.baseorm.db.redpacketdb.tables.RedpacketActivity.REDPACKET_ACTIVITY;
import static org.jooq.impl.DSL.using;

/**
 * @ClassName RedpacketActivityDao
 * @Description TODO
 * @Author jack
 * @Date 2019/3/8 4:20 PM
 * @Version 1.0
 */
@Repository
public class RedpacketActivityDao extends com.moseeker.baseorm.db.redpacketdb.tables.daos.RedpacketActivityDao {

    @Autowired
    public RedpacketActivityDao(Configuration configuration) {
        super(configuration);
    }

    public List<RedpacketActivity> fetchActiveActivitiesByCompanyId(int companyId, int status, List<Integer> typeList) {
        List<RedpacketActivity> result =  using(configuration())
                .selectFrom(REDPACKET_ACTIVITY)
                .where(REDPACKET_ACTIVITY.COMPANY_ID.eq(companyId))
                .and(REDPACKET_ACTIVITY.STATUS.eq((byte) status))
                .and(REDPACKET_ACTIVITY.TYPE.in(typeList))
                .fetch(mapper());
        return result;
    }

    public List<RedpacketActivity> fetchByIdList(List<Integer> activityIdList) {
        return using(configuration())
                .selectFrom(REDPACKET_ACTIVITY)
                .where(REDPACKET_ACTIVITY.ID.in(activityIdList))
                .fetch(mapper());
    }
}
