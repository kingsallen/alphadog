package com.moseeker.baseorm.dao.redpacketdb;/**
 * Created by zztaiwll on 19/1/14.
 */

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketActivity;
import com.moseeker.baseorm.db.redpacketdb.tables.records.RedpacketActivityRecord;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moseeker.baseorm.db.redpacketdb.tables.RedpacketActivity.REDPACKET_ACTIVITY;

/**
 * @version 1.0
 * @className RedpacketActivityJOOQDao
 * @Description TODO
 * @Author zztaiwll
 * @DATE 19/1/14 下午2:10
 **/
@Repository
public class RedpacketActivityJOOQDao extends JooqCrudImpl<RedpacketActivity,RedpacketActivityRecord> {
    public RedpacketActivityJOOQDao(){
        super(REDPACKET_ACTIVITY,RedpacketActivity.class);
    }
    public RedpacketActivityJOOQDao(TableImpl<RedpacketActivityRecord> table, Class<RedpacketActivity> redpacketActivityClass) {
        super(table, redpacketActivityClass);
    }

    public List<RedpacketActivity> getRedpacketActivityList(List<Integer> idList){
        List<RedpacketActivity> result=create.selectFrom(REDPACKET_ACTIVITY).where(REDPACKET_ACTIVITY.ID.in(idList)).and(REDPACKET_ACTIVITY.STATUS.gt((byte)0)).fetchInto(RedpacketActivity.class);
        return result;
    }

    public RedpacketActivity getRedpacketActivity(int id){
        RedpacketActivity result=create.selectFrom(REDPACKET_ACTIVITY).where(REDPACKET_ACTIVITY.ID.eq(id)).and(REDPACKET_ACTIVITY.STATUS.gt((byte)0)).fetchOneInto(RedpacketActivity.class);
        return result;
    }

    public List<RedpacketActivity> getRedpacketActivityDataList(List<Integer> typeList,int companyId,int status){
        List<RedpacketActivity> result=create.selectFrom(REDPACKET_ACTIVITY).where(REDPACKET_ACTIVITY.TYPE.in(typeList))
                .and(REDPACKET_ACTIVITY.STATUS.gt((byte)status)).and(REDPACKET_ACTIVITY.COMPANY_ID.eq(companyId)).fetchInto(RedpacketActivity.class);
        return result;
    }


}
