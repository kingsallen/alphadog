package com.moseeker.baseorm.dao.redpacketdb;/**
 * Created by zztaiwll on 19/1/14.
 */

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.redpacketdb.tables.daos.RedpacketActivityPositionDao;
import com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketActivityPosition;
import com.moseeker.baseorm.db.redpacketdb.tables.records.RedpacketActivityPositionRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moseeker.baseorm.db.redpacketdb.tables.RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION;
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
    /*
     * @Author zztaiwll
     * @Description  根据职位列表获取红包职位列表
     * @Date 下午1:54 19/1/14
     * @Param [pidList]
     * @return java.util.List<java.lang.Integer>
     **/
    public List<RedpacketActivityPosition> getHbPositionList(List<Integer> pidList){
       List<RedpacketActivityPosition> result=create.select(REDPACKET_ACTIVITY_POSITION.POSITION_ID).from(REDPACKET_ACTIVITY_POSITION).where(REDPACKET_ACTIVITY_POSITION.ACTIVITY_ID.in(pidList)).fetchInto(RedpacketActivityPosition.class);
       return result;
    }

    public List<RedpacketActivityPosition> getDataList(List<Integer> pidList,List<Integer> activeIdList){
        List<RedpacketActivityPosition> result=create.select(REDPACKET_ACTIVITY_POSITION.POSITION_ID).from(REDPACKET_ACTIVITY_POSITION)
                .where(REDPACKET_ACTIVITY_POSITION.ACTIVITY_ID.in(pidList))
                .and(REDPACKET_ACTIVITY_POSITION.ACTIVITY_ID.in(activeIdList)).fetchInto(RedpacketActivityPosition.class);
        return result;
    }
}
