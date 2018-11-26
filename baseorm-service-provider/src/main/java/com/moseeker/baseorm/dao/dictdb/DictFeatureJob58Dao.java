package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.db.dictdb.tables.records.Dict_58jobFeatureRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import static com.moseeker.baseorm.db.dictdb.tables.Dict_58jobFeature.DICT_58JOB_FEATURE;
/**
 * @author cjm
 * @date 2018-11-23 14:21
 **/
@Repository
public class DictFeatureJob58Dao {

    @Autowired
    private DSLContext create;

    public List<Dict_58jobFeatureRecord> getAllFeature(){
        return create.selectFrom(DICT_58JOB_FEATURE)
                .fetchInto(Dict_58jobFeatureRecord.class);
    }

}
