package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobPcRecommendPositionItem;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPcRecommendPositionItemRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPcRecommendPositionItemDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 17/9/20.
 */
@Service
public class JobPcRecommendPositionItemDao extends JooqCrudImpl<JobPcRecommendPositionItemDO,JobPcRecommendPositionItemRecord> {
    public JobPcRecommendPositionItemDao(){
        super(JobPcRecommendPositionItem.JOB_PC_RECOMMEND_POSITION_ITEM,JobPcRecommendPositionItemDO.class);
    }
    public JobPcRecommendPositionItemDao(TableImpl<JobPcRecommendPositionItemRecord> table,Class<JobPcRecommendPositionItemDO> JobPcRecommendPositionItemRecordClass){
        super(table,JobPcRecommendPositionItemRecordClass);
    }
}
