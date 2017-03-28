package com.moseeker.baseorm.crud;

import com.moseeker.thrift.gen.common.struct.*;

/**
 * Created by zhangdi on 2017/3/21.
 */
public class QueryCondition extends CommonCondition<QueryCondition> {
    CommonQuery commonQuery;

    private QueryCondition(CommonQuery commonQuery, CommonCondition condition) {
        this.commonQuery = commonQuery;
        init(condition);
    }

    public static QueryCondition initWithCommonQuery(CommonQuery commonQuery, CommonCondition condition) {
        return new QueryCondition(commonQuery, condition);
    }

    public CommonQuery getCommonQuery() {
        commonQuery.setConditions(this);
        return commonQuery;
    }

    public QueryGroupBy groupBy(String... fields) {
        commonQuery.setConditions(this);
        for (String filed : fields) {
            this.commonQuery.addToGroups(filed);
        }
        return new QueryGroupBy(commonQuery);
    }

    public QueryOrderBy orderBy(String field, Order order) {
        commonQuery.setConditions(this);
        commonQuery.addToOrders(new com.moseeker.thrift.gen.common.struct.OrderBy(field, order));
        return new QueryOrderBy(commonQuery);
    }

    public QueryPageSize pageSize(int size) {
        return new QueryPageSize(commonQuery, size);
    }

}
