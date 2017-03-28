package com.moseeker.baseorm.crud;

import com.moseeker.thrift.gen.common.struct.CommonQuery;

/**
 * Created by moseeker on 2017/3/22.
 */
public class QueryPageSize extends QueryDao {

    public QueryPageSize(CommonQuery commonQuery, int size) {
        this.commonQuery = commonQuery;
        if (size > 0) {
            commonQuery.setPageSize(size);
        }
    }

    public QueryPage page(int page) {
        return new QueryPage(commonQuery, page);
    }
}
