package com.moseeker.baseorm.dao.thirdpartydb;

import com.moseeker.baseorm.base.AbstractThirdPartyPositionDao;
import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyJob58Position;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyJob58PositionRecord;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyJob58PositionDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cjm
 * @date 2018-11-27 10:17
 **/
@Repository
public class ThirdpartyJob58PositionDao extends AbstractThirdPartyPositionDao<ThirdpartyJob58PositionDO, ThirdpartyJob58PositionRecord> {

    public ThirdpartyJob58PositionDao() {
        super(ThirdpartyJob58Position.THIRDPARTY_JOB58_POSITION, ThirdpartyJob58PositionDO.class);
    }

    public ThirdpartyJob58PositionDao(TableImpl<ThirdpartyJob58PositionRecord> table, Class<ThirdpartyJob58PositionDO> thirdpartyJob58PositionDOClass) {
        super(table, thirdpartyJob58PositionDOClass);
    }

    @Override
    public TwoParam<HrThirdPartyPositionDO, ThirdpartyJob58PositionDO> getData(HrThirdPartyPositionDO thirdPartyPositionDO) {
        Query query = new Query.QueryBuilder()
                .where(ThirdpartyJob58Position.THIRDPARTY_JOB58_POSITION.PID.getName(), thirdPartyPositionDO.getId())
                .and(ThirdpartyJob58Position.THIRDPARTY_JOB58_POSITION.STATUS.getName(), 0).buildQuery();
        ThirdpartyJob58PositionDO job58PositionDO = getData(query);
        return new TwoParam<>(thirdPartyPositionDO, job58PositionDO);
    }

    @Override
    public List<TwoParam<HrThirdPartyPositionDO, ThirdpartyJob58PositionDO>> getDatas(List<HrThirdPartyPositionDO> list) {
        List<Integer> ids = list.stream()
                .map(HrThirdPartyPositionDO::getId)
                .collect(Collectors.toList());
        Query query = new Query.QueryBuilder().where(new Condition(ThirdpartyJob58Position.THIRDPARTY_JOB58_POSITION.PID.getName(), ids, ValueOp.IN)).buildQuery();

        List<ThirdpartyJob58PositionDO> job58PositionDOS = getDatas(query);

        List<TwoParam<HrThirdPartyPositionDO, ThirdpartyJob58PositionDO>> results = new ArrayList<>();

        for (HrThirdPartyPositionDO thirdPartyPositionDO : list) {
            for (ThirdpartyJob58PositionDO job58 : job58PositionDOS) {
                if (thirdPartyPositionDO.getId() == job58.getPid()) {
                    results.add(new TwoParam<>(thirdPartyPositionDO, job58));
                }
            }
        }

        return results;
    }

    @Override
    public TwoParam<HrThirdPartyPositionDO, ThirdpartyJob58PositionDO> addData(HrThirdPartyPositionDO thirdPartyPositionDO, ThirdpartyJob58PositionDO thirdpartyJob58PositionDO) {
        if (thirdPartyPositionDO == null || thirdPartyPositionDO.getId() == 0) {
            return null;
        }
        thirdpartyJob58PositionDO.setPid(thirdPartyPositionDO.getId());
        thirdpartyJob58PositionDO = addData(thirdpartyJob58PositionDO);
        return new TwoParam<>(thirdPartyPositionDO, thirdpartyJob58PositionDO);
    }

    @Override
    public ThirdpartyJob58PositionDO setId(HrThirdPartyPositionDO thirdPartyPositionDO, ThirdpartyJob58PositionDO thirdpartyJob58PositionDO) {
        TwoParam<HrThirdPartyPositionDO, ThirdpartyJob58PositionDO> result = getData(thirdPartyPositionDO);
        thirdpartyJob58PositionDO.setId(result.getR2().getId());
        return thirdpartyJob58PositionDO;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB58;
    }
}
