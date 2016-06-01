package com.moseeker.dict.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.dictdb.tables.records.DictCollegeRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import org.jooq.Record;

import java.util.List;

/**
 * Created by chendi on 5/26/16.
 */
public interface CollegeDao  extends BaseDao<DictCollegeRecord> {
    List<DictCollegeRecord> getJoinedResults(CommonQuery query);
}
