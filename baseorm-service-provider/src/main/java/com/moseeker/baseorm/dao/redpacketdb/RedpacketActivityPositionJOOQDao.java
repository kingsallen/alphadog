package com.moseeker.baseorm.dao.redpacketdb;/**
 * Created by zztaiwll on 19/1/14.
 */

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketActivityPosition;
import com.moseeker.baseorm.db.redpacketdb.tables.records.RedpacketActivityPositionRecord;
import com.moseeker.common.constants.AbleFlag;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.moseeker.baseorm.db.redpacketdb.tables.RedpacketActivity.REDPACKET_ACTIVITY;
import static com.moseeker.baseorm.db.redpacketdb.tables.RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION;
import static org.jooq.impl.DSL.concat;
import static org.jooq.impl.DSL.using;

/**
 * @version 1.0
 * @className RedpacketActivityPositionJOOQDao
 * @Description TODO
 * @Author zztaiwll
 * @DATE 19/1/14 下午12:04
 **/
@Repository
public class RedpacketActivityPositionJOOQDao extends JooqCrudImpl<RedpacketActivityPosition, RedpacketActivityPositionRecord> {
    public RedpacketActivityPositionJOOQDao(){
        super(REDPACKET_ACTIVITY_POSITION,RedpacketActivityPosition.class);
    }
    public RedpacketActivityPositionJOOQDao(TableImpl<RedpacketActivityPositionRecord> table,Class<RedpacketActivityPosition> RedpacketActivityPositionClass){
        super(REDPACKET_ACTIVITY_POSITION,RedpacketActivityPosition.class);
    }
    /*
        * @Author zztaiwll
        * @Description  根据红包配置id获取红包职位id列表
        * @Date 下午1:54 19/1/14
        * @Param [pidList]
        * @return java.util.List<java.lang.Integer>
        **/
    public List<Integer> getPositionIdListByConfigId(int configId){
        List<Integer>  result=create.select(REDPACKET_ACTIVITY_POSITION.POSITION_ID).from(REDPACKET_ACTIVITY_POSITION).where(REDPACKET_ACTIVITY_POSITION.ACTIVITY_ID.eq(configId))
                .and(REDPACKET_ACTIVITY_POSITION.LEFT_AMOUNT.gt(0)).and(REDPACKET_ACTIVITY_POSITION.ENABLE.eq((byte)1)).fetchInto(Integer.class);
        return result;
    }


    public List<Integer> getHbPositionList(List<Integer> pidList){
       Result<Record1<Integer>> result = create
               .select(REDPACKET_ACTIVITY_POSITION.POSITION_ID)
               .from(REDPACKET_ACTIVITY_POSITION)
               .where(REDPACKET_ACTIVITY_POSITION.POSITION_ID.in(pidList))
               .and(REDPACKET_ACTIVITY_POSITION.ENABLE.eq((byte) AbleFlag.ENABLE.getValue()))
               .fetch();
       if (result != null) {
           return result
                   .stream()
                   .map(record1 -> record1.value1())
                   .collect(Collectors.toList());
       } else {
           return new ArrayList<>(0);
       }
    }

    public List<RedpacketActivityPosition> getDataList(List<Integer> pidList,List<Integer> activeIdList){
        List<RedpacketActivityPosition> result=create.select(REDPACKET_ACTIVITY_POSITION.POSITION_ID).from(REDPACKET_ACTIVITY_POSITION)
                .where(REDPACKET_ACTIVITY_POSITION.ACTIVITY_ID.in(pidList))
                .and(REDPACKET_ACTIVITY_POSITION.ACTIVITY_ID.in(activeIdList)).fetchInto(RedpacketActivityPosition.class);
        return result;
    }

    public boolean isInActivity(int positionId) {
        Record1<Integer> record1 = create.selectOne()
                .from(REDPACKET_ACTIVITY_POSITION)
                .innerJoin(REDPACKET_ACTIVITY)
                .on(REDPACKET_ACTIVITY_POSITION.ACTIVITY_ID.eq(REDPACKET_ACTIVITY.ID))
                .where(REDPACKET_ACTIVITY_POSITION.POSITION_ID.eq(positionId))
                .and(REDPACKET_ACTIVITY_POSITION.ENABLE.eq((byte) 1))
                .and(REDPACKET_ACTIVITY.STATUS.eq((byte) 3))
                .limit(1)
                .fetchOne();
        return record1 != null && record1.value1() != null;
    }

    public List<RedpacketActivityPosition> listByActivityId(int hbConfigId, boolean enable, int start, int size) {
        return create
                .selectFrom(REDPACKET_ACTIVITY_POSITION)
                .where(REDPACKET_ACTIVITY_POSITION.ACTIVITY_ID.eq(hbConfigId))
                .and(REDPACKET_ACTIVITY_POSITION.ENABLE.eq((byte) (enable?1:0)))
                .limit(start, size)
                .fetchInto(RedpacketActivityPosition.class);
    }

    public List<RedpacketActivityPosition> listByPidListAndHbConfigList(List<Integer> pids, List<Integer> hrHbIdList) {
        return create
                .selectFrom(REDPACKET_ACTIVITY_POSITION)
                .where(REDPACKET_ACTIVITY_POSITION.ACTIVITY_ID.in(hrHbIdList))
                .and(REDPACKET_ACTIVITY_POSITION.POSITION_ID.in(pids))
                .fetchInto(RedpacketActivityPosition.class);
    }

    public List<RedpacketActivityPosition> fetchRuningPositionsByPositionIdList(List<Integer> positionIdList) {
        Result<Record> records = create
                .select(REDPACKET_ACTIVITY_POSITION.fields())
                .from(REDPACKET_ACTIVITY_POSITION)
                .innerJoin(REDPACKET_ACTIVITY)
                .on(REDPACKET_ACTIVITY_POSITION.ACTIVITY_ID.eq(REDPACKET_ACTIVITY.ID))
                .where(REDPACKET_ACTIVITY.STATUS.eq((byte) 3)
                .and(REDPACKET_ACTIVITY_POSITION.POSITION_ID.in(positionIdList))
                .and(REDPACKET_ACTIVITY_POSITION.LEFT_AMOUNT.gt(0))
                .and(REDPACKET_ACTIVITY_POSITION.ENABLE.eq((byte) AbleFlag.ENABLE.getValue())))
                .fetch();
        if (records != null && records.size() > 0) {
            return records.stream().map(record -> record.into(RedpacketActivityPosition.class)).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public int countEnableByActivityId(int activityId) {
        return create
                .selectCount()
                .from(REDPACKET_ACTIVITY_POSITION)
                .where(REDPACKET_ACTIVITY_POSITION.ACTIVITY_ID.eq(activityId))
                .and(REDPACKET_ACTIVITY_POSITION.ENABLE.eq((byte) AbleFlag.ENABLE.getValue()))
                .fetchOne()
                .value1();
    }
}
