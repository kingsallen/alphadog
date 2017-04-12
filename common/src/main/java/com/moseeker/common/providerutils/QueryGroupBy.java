package com.moseeker.common.providerutils;

import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Order;

/**
 * Created by zhangdi on 2017/3/21.
 */
public class QueryGroupBy extends QueryDao {

    public QueryGroupBy(CommonQuery commonQuery) {
        this.commonQuery = commonQuery;
    }

    public QueryGroupBy groupBy(String... fields) {
        for (String filed : fields) {
            this.commonQuery.addToGroups(filed);
        }
        return new QueryGroupBy(commonQuery);
    }

    public QueryPageSize pageSize(int size) {
        return new QueryPageSize(commonQuery, size);
    }

    public QueryOrderBy orderBy(String field, Order order) {
        commonQuery.addToOrders(new com.moseeker.thrift.gen.common.struct.OrderBy(field, order));
        return new QueryOrderBy(commonQuery);
    }

}
