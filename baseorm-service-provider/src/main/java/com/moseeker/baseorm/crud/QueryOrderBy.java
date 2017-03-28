package com.moseeker.baseorm.crud;

import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Order;

/**
 * Created by zhangdi on 2017/3/22.
 */
public class QueryOrderBy extends QueryDao {

    public QueryOrderBy(CommonQuery commonQuery) {
        this.commonQuery = commonQuery;
    }

    public QueryOrderBy orderBy(String field, Order order) {
        this.commonQuery.addToOrders(new com.moseeker.thrift.gen.common.struct.OrderBy(field, order));
        return this;
    }

    public QueryPageSize pageSize(int size) {
        return new QueryPageSize(commonQuery, size);
    }

}
