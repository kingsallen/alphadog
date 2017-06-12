package com.moseeker.baseorm.dao.analyticsd;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.analytics.tables.StJobSimilarity;
import com.moseeker.baseorm.db.analytics.tables.records.StJobSimilarityRecord;
import com.moseeker.thrift.gen.dao.struct.analytics.StJobSimilarityDO;
/*
 * y zzt
 * 2017-05-15
 */
@Repository
public class StJobSimilarityDao extends JooqCrudImpl<StJobSimilarityDO, StJobSimilarityRecord> {

    public StJobSimilarityDao() {
        super(StJobSimilarity.ST_JOB_SIMILARITY, StJobSimilarityDO.class);
    }

    public StJobSimilarityDao(TableImpl<StJobSimilarityRecord> table, Class<StJobSimilarityDO> StJobSimilarityDOClass) {
        super(table, StJobSimilarityDOClass);
    }

}
