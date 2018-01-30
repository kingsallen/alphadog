package com.moseeker.baseorm.dao.thirdpartydb;

import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.baseorm.base.IThirdPartyPositionDao;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyJob1001Position;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyJob1001PositionRecord;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyJob1001PositionDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ThirdpartyJob1001PositionDao  extends JooqCrudImpl<ThirdpartyJob1001PositionDO,ThirdpartyJob1001PositionRecord> implements IThirdPartyPositionDao<ThirdpartyJob1001PositionDO> {
    public ThirdpartyJob1001PositionDao() {
        super(ThirdpartyJob1001Position.THIRDPARTY_JOB1001_POSITION, ThirdpartyJob1001PositionDO.class);
    }

    public ThirdpartyJob1001PositionDao(TableImpl<ThirdpartyJob1001PositionRecord> table, Class<ThirdpartyJob1001PositionDO> thirdpartyJob1001PositionDOClass) {
        super(table, thirdpartyJob1001PositionDOClass);
    }

    @Override
    public TwoParam<HrThirdPartyPositionDO, ThirdpartyJob1001PositionDO> getData(HrThirdPartyPositionDO thirdPartyPositionDO) {
        Query query=new Query.QueryBuilder()
                .where(ThirdpartyJob1001Position.THIRDPARTY_JOB1001_POSITION.PID.getName(), thirdPartyPositionDO.getId())
                .and(ThirdpartyJob1001Position.THIRDPARTY_JOB1001_POSITION.STATUS.getName(),0).buildQuery();
        ThirdpartyJob1001PositionDO job1001=getData(query);
        TwoParam<HrThirdPartyPositionDO, ThirdpartyJob1001PositionDO> result=new TwoParam<>(thirdPartyPositionDO,job1001);
        return result;
    }

    @Override
    public List<TwoParam<HrThirdPartyPositionDO, ThirdpartyJob1001PositionDO>> getDatas(List<HrThirdPartyPositionDO> list) {
        List<Integer> ids=list.stream()
                .map(p->p.getId())
                .collect(Collectors.toList());
        Query query=new Query.QueryBuilder().where(new Condition(ThirdpartyJob1001Position.THIRDPARTY_JOB1001_POSITION.PID.getName(), ids, ValueOp.IN)).buildQuery();

        List<ThirdpartyJob1001PositionDO> job1001s=getDatas(query);

        List<TwoParam<HrThirdPartyPositionDO, ThirdpartyJob1001PositionDO>> results=new ArrayList<>();

        out:for(HrThirdPartyPositionDO thirdPartyPositionDO:list){
            for(ThirdpartyJob1001PositionDO job1001:job1001s){
                if(thirdPartyPositionDO.getId()==job1001.getPid()){
                    results.add(new TwoParam<>(thirdPartyPositionDO,job1001));
                    continue out;
                }
            }
            results.add(new TwoParam<>(thirdPartyPositionDO, new ThirdpartyJob1001PositionDO()));
        }

        return results;
    }

    @Override
    @Transactional
    public TwoParam<HrThirdPartyPositionDO, ThirdpartyJob1001PositionDO> addData(HrThirdPartyPositionDO thirdPartyPositionDO, ThirdpartyJob1001PositionDO thirdpartyJob1001PositionDO) {
        if(thirdPartyPositionDO==null || thirdPartyPositionDO.getId()==0){
            return null;
        }
        thirdpartyJob1001PositionDO.setPid(thirdPartyPositionDO.getId());
        thirdpartyJob1001PositionDO=addData(thirdpartyJob1001PositionDO);
        return new TwoParam<>(thirdPartyPositionDO,thirdpartyJob1001PositionDO);
    }

    @Override
    public ThirdpartyJob1001PositionDO setId(HrThirdPartyPositionDO thirdPartyPositionDO, ThirdpartyJob1001PositionDO thirdpartyJob1001PositionDO) {
        TwoParam<HrThirdPartyPositionDO, ThirdpartyJob1001PositionDO> result=getData(thirdPartyPositionDO);
        thirdpartyJob1001PositionDO.setId(result.getR2().getId());
        return thirdpartyJob1001PositionDO;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB1001;
    }
}
