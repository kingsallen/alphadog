package com.moseeker.baseorm.dao.redpacketdb;/**
 * Created by zztaiwll on 19/1/14.
 */

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketRedpacket;
import com.moseeker.baseorm.db.redpacketdb.tables.records.RedpacketRedpacketRecord;
import org.jooq.Configuration;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moseeker.baseorm.db.redpacketdb.tables.RedpacketRedpacket.REDPACKET_REDPACKET;

/**
 * @version 1.0
 * @className RedpacketRedpacketJOOQDao
 * @Description TODO
 * @Author zztaiwll
 * @DATE 19/1/14 下午2:53
 **/
@Repository
public class RedpacketRedpacketJOOQDao extends JooqCrudImpl<RedpacketRedpacket,RedpacketRedpacketRecord> {
    public RedpacketRedpacketJOOQDao(){
        super(REDPACKET_REDPACKET,RedpacketRedpacket.class);
    }
    public RedpacketRedpacketJOOQDao(TableImpl<RedpacketRedpacketRecord> table, Class<RedpacketRedpacket> redpacketRedpacketClass) {
        super(table, redpacketRedpacketClass);
    }

    public List<RedpacketRedpacket> getRedpacketRedpacketByBingIdList(List<Integer> bingIdList){
        List<RedpacketRedpacket> list=create.selectFrom(REDPACKET_REDPACKET).where(REDPACKET_REDPACKET.ACTIVITY_ID.in(bingIdList)).fetchInto(RedpacketRedpacket.class);
        return list;
    }
}
