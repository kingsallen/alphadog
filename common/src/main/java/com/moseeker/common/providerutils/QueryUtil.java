package com.moseeker.common.providerutils;

import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.*;

import java.util.Map;

/**
 * 该类只为兼容以前的调用方式，后续接口直接使用CommonQuery，或者使用新的工具类
 */

public class QueryUtil extends Query {

    Query.QueryBuilder queryBuilder = null;

    public QueryUtil() {
        queryBuilder = new Query.QueryBuilder();
    }

    @Deprecated
    public QueryUtil addEqualFilter(String key, Object value) {
        queryBuilder.and(key, value);
        return this;
    }

    @Deprecated
    public QueryUtil setEqualFilter(Map<String, String> equalFilter) {
        if(equalFilter != null) {
            equalFilter.forEach((key, value) -> {
                queryBuilder.and(key, value);
            });
        }
        return this;
    }

    @Deprecated
    public QueryUtil addGroupBy(String field) {
        if (field == null) {
            queryBuilder.groupBy(field);
        }
        return this;
    }

    @Deprecated
    public QueryUtil addOrderBy(String filed, Order order) {
        if (StringUtils.isNotNullOrEmpty(filed) && order != null) {
            queryBuilder.orderBy(filed, order);
        }
        return this;
    }

    @Deprecated
    public QueryUtil addSelectAttribute(String field) {
        if (StringUtils.isNotNullOrEmpty(field)) {
            queryBuilder.select(field);
        }
        return this;
    }

    public QueryUtil orderBy(String field) {
        return orderBy(field, Order.ASC);
    }

    public QueryUtil orderBy(String field, Order order) {
        OrderBy orderBy = new OrderBy(field, order);
        queryBuilder.orderBy(orderBy);
        return this;
    }

    @Deprecated
    public void setPer_page(int per_page) {
        queryBuilder.setPageSize(per_page);
    }

    @Deprecated
    public QueryUtil addGroup(String field) {
        addToGroups(field);
        return this;
    }

    public static QueryUtil select() {
        return new QueryUtil();
    }

    public static QueryUtil select(String... fields) {
        QueryUtil queryUtil = select();
        if(fields != null) {
            for (String field : fields) {
                queryUtil.addSelect(field);
            }

        }
        return new QueryUtil().select(fields);
    }

    public static QueryUtil select(Select... selects) {
        return new QueryUtil().select(selects);
    }

    public static QueryUtil where(Condition condition) {
        return new QueryUtil().select().where(condition);
    }

    public void setPageSize(int pageSize) {
        this.setPageSize(pageSize);
    }


    public void setPageNo(int pageNo) {
        this.setPageNo(pageNo);
    }
}
