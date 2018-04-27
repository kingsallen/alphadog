package com.moseeker.baseorm.dao.thirdpartydb;

import com.moseeker.baseorm.base.AbstractThirdPartyPositionDao;
import com.moseeker.baseorm.base.IThirdPartyPositionDao;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyJobsdbPosition;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyJobsdbPositionRecord;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyJobsDBPositionDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ThirdpartyJobsDBPositionDao extends AbstractThirdPartyPositionDao<ThirdpartyJobsDBPositionDO, ThirdpartyJobsdbPositionRecord> {

    public ThirdpartyJobsDBPositionDao() {
        super(ThirdpartyJobsdbPosition.THIRDPARTY_JOBSDB_POSITION, ThirdpartyJobsDBPositionDO.class);
    }

    public ThirdpartyJobsDBPositionDao(TableImpl<ThirdpartyJobsdbPositionRecord> table, Class<ThirdpartyJobsDBPositionDO> thirdpartyVeryEastPositionDOClass) {
        super(table, thirdpartyVeryEastPositionDOClass);
    }

    @Override
    public TwoParam<HrThirdPartyPositionDO, ThirdpartyJobsDBPositionDO> getData(HrThirdPartyPositionDO thirdPartyPositionDO) {
        Query query = new Query.QueryBuilder()
                .where(ThirdpartyJobsdbPosition.THIRDPARTY_JOBSDB_POSITION.PID.getName(), thirdPartyPositionDO.getId())
                .and(ThirdpartyJobsdbPosition.THIRDPARTY_JOBSDB_POSITION.STATUS.getName(), 0).buildQuery();
        ThirdpartyJobsDBPositionDO job1001 = getData(query);
        TwoParam<HrThirdPartyPositionDO, ThirdpartyJobsDBPositionDO> result = new TwoParam<>(thirdPartyPositionDO, job1001);
        return result;
    }

    @Override
    public List<TwoParam<HrThirdPartyPositionDO, ThirdpartyJobsDBPositionDO>> getDatas(List<HrThirdPartyPositionDO> list) {
        List<Integer> ids = list.stream()
                .map(p -> p.getId())
                .collect(Collectors.toList());
        Query query = new Query.QueryBuilder().where(new Condition(ThirdpartyJobsdbPosition.THIRDPARTY_JOBSDB_POSITION.PID.getName(), ids, ValueOp.IN)).buildQuery();

        List<ThirdpartyJobsDBPositionDO> job1001s = getDatas(query);

        List<TwoParam<HrThirdPartyPositionDO, ThirdpartyJobsDBPositionDO>> results = new ArrayList<>();

        for (HrThirdPartyPositionDO thirdPartyPositionDO : list) {
            for (ThirdpartyJobsDBPositionDO job1001 : job1001s) {
                if (thirdPartyPositionDO.getId() == job1001.getPid()) {
                    results.add(new TwoParam<>(thirdPartyPositionDO, job1001));
                }
            }
        }

        return results;
    }

    @Override
    public TwoParam<HrThirdPartyPositionDO, ThirdpartyJobsDBPositionDO> addData(HrThirdPartyPositionDO thirdPartyPositionDO, ThirdpartyJobsDBPositionDO thirdpartyJobsDBPositionDO) {
        if (thirdPartyPositionDO == null || thirdPartyPositionDO.getId() == 0) {
            return null;
        }
        thirdpartyJobsDBPositionDO.setPid(thirdPartyPositionDO.getId());
        thirdpartyJobsDBPositionDO = addData(thirdpartyJobsDBPositionDO);
        return new TwoParam<>(thirdPartyPositionDO, thirdpartyJobsDBPositionDO);
    }

    @Override
    public ThirdpartyJobsDBPositionDO setId(HrThirdPartyPositionDO thirdPartyPositionDO, ThirdpartyJobsDBPositionDO thirdpartyJobsDBPositionDO) {
        TwoParam<HrThirdPartyPositionDO, ThirdpartyJobsDBPositionDO> result = getData(thirdPartyPositionDO);
        thirdpartyJobsDBPositionDO.setId(result.getR2().getId());
        return thirdpartyJobsDBPositionDO;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOBSDB;
    }
}
