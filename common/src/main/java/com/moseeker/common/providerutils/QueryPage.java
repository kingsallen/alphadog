package com.moseeker.common.providerutils;

import com.moseeker.thrift.gen.common.struct.CommonQuery;

/**
 * Created by zhangdi on 2017/3/22.
 */
public class QueryPage extends QueryDao {
    public QueryPage(CommonQuery commonQuery, int page) {
        this.commonQuery = commonQuery;
        if (page > 0) {
            commonQuery.setPage(page);
        }
    }
}
