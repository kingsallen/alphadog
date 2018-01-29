package com.moseeker.baseorm.dao.thirdpartydb;

import com.moseeker.baseorm.base.IThirdPartyPositionDao;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyVeryeastPosition;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyVeryeastPositionRecord;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyVeryEastPositionDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ThirdpartyVeryeastPositionDao extends JooqCrudImpl<ThirdpartyVeryEastPositionDO,ThirdpartyVeryeastPositionRecord> implements IThirdPartyPositionDao<ThirdpartyVeryEastPositionDO>{
    public ThirdpartyVeryeastPositionDao() {
        super(ThirdpartyVeryeastPosition.THIRDPARTY_VERYEAST_POSITION,ThirdpartyVeryEastPositionDO.class);
    }

    public ThirdpartyVeryeastPositionDao(TableImpl<ThirdpartyVeryeastPositionRecord> table, Class<ThirdpartyVeryEastPositionDO> thirdpartyVeryEastPositionDOClass) {
        super(table, thirdpartyVeryEastPositionDOClass);
    }


    @Override
    public TwoParam<HrThirdPartyPositionDO, ThirdpartyVeryEastPositionDO> getData(HrThirdPartyPositionDO thirdPartyPositionDO) {
        Query query=new Query.QueryBuilder()
                .where(ThirdpartyVeryeastPosition.THIRDPARTY_VERYEAST_POSITION.PID.getName(), thirdPartyPositionDO.getId())
                .and(ThirdpartyVeryeastPosition.THIRDPARTY_VERYEAST_POSITION.STATUS.getName(),0).buildQuery();
        ThirdpartyVeryEastPositionDO job1001=getData(query);
        TwoParam<HrThirdPartyPositionDO, ThirdpartyVeryEastPositionDO> result=new TwoParam<>(thirdPartyPositionDO,job1001);
        return result;
    }

    @Override
    public List<TwoParam<HrThirdPartyPositionDO, ThirdpartyVeryEastPositionDO>> getDatas(List<HrThirdPartyPositionDO> list) {
        List<Integer> ids=list.stream()
                .map(p->p.getId())
                .collect(Collectors.toList());
        Query query=new Query.QueryBuilder().where(new Condition("pid", ids, ValueOp.IN)).buildQuery();

        List<ThirdpartyVeryEastPositionDO> job1001s=getDatas(query);

        List<TwoParam<HrThirdPartyPositionDO, ThirdpartyVeryEastPositionDO>> results=new ArrayList<>();

        for(HrThirdPartyPositionDO thirdPartyPositionDO:list){
            for(ThirdpartyVeryEastPositionDO job1001:job1001s){
                if(thirdPartyPositionDO.getId()==job1001.getPid()){
                    results.add(new TwoParam<>(thirdPartyPositionDO,job1001));
                }
            }
        }

        return results;
    }

    @Override
    @Transactional
    public TwoParam<HrThirdPartyPositionDO, ThirdpartyVeryEastPositionDO> addData(HrThirdPartyPositionDO thirdPartyPositionDO, ThirdpartyVeryEastPositionDO thirdpartyVeryEastPositionDO) {
        if(thirdPartyPositionDO==null || thirdPartyPositionDO.getId()==0){
            return null;
        }
        thirdpartyVeryEastPositionDO.setPid(thirdPartyPositionDO.getId());
        thirdpartyVeryEastPositionDO=addData(thirdpartyVeryEastPositionDO);
        return new TwoParam<>(thirdPartyPositionDO,thirdpartyVeryEastPositionDO);
    }

    @Override
    public ThirdpartyVeryEastPositionDO setId(HrThirdPartyPositionDO thirdPartyPositionDO, ThirdpartyVeryEastPositionDO thirdpartyVeryEastPositionDO) {
        TwoParam<HrThirdPartyPositionDO, ThirdpartyVeryEastPositionDO> result=getData(thirdPartyPositionDO);
        thirdpartyVeryEastPositionDO.setId(result.getR2().getId());
        return thirdpartyVeryEastPositionDO;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.VERYEAST;
    }
}
