package com.moseeker.baseorm.crud;

import com.moseeker.thrift.gen.common.struct.*;


/**
 * Created by zhangdi on 2017/3/21.
 */
public class QuerySelect extends QueryDao {

    QuerySelect() {
        commonQuery = new CommonQuery();
    }


    public QuerySelect select() {
        return this;
    }

    public QuerySelect select(String... fields) {
        for (String field : fields) {
            commonQuery.addToAttributes(new Select(field, SelectOp.FIELD));
        }
        return this;
    }

    public QuerySelect select(Select... selects) {
        for (Select select : selects) {
            commonQuery.addToAttributes(select);
        }
        return this;
    }

    public QuerySelect and(Select select) {
        commonQuery.addToAttributes(select);
        return this;
    }

    public QuerySelect and(String field) {
        commonQuery.addToAttributes(new Select(field, SelectOp.FIELD));
        return this;
    }

    public QueryCondition where(CommonCondition condition) {
        return QueryCondition.initWithCommonQuery(commonQuery, condition);
    }

    public QueryGroupBy groupBy(String... fields) {
        for (String filed : fields) {
            this.commonQuery.addToGroups(filed);
        }
        return new QueryGroupBy(commonQuery);
    }

    public QueryOrderBy orderBy(String field, Order order) {
        commonQuery.addToOrders(new com.moseeker.thrift.gen.common.struct.OrderBy(field, order));
        return new QueryOrderBy(commonQuery);
    }

    public QueryPageSize pageSize(int size) {
        return new QueryPageSize(commonQuery, size);
    }
}
