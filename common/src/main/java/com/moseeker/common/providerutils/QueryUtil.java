package com.moseeker.common.providerutils;

import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * 该类只为兼容以前的调用方式，后续接口直接使用CommonQuery，或者使用新的工具类
 */

@Deprecated
public class QueryUtil extends Query {

    public QueryUtil addEqualFilter(String key, Object value) {
        if (StringUtils.isNotNullOrEmpty(key)) {
            Condition condition = buildCondition(key, value);
            if (this.conditions == null) {
                this.conditions = condition;
            } else {
                this.conditions.andCondition(condition);
            }
        }

        return this;
    }

    public QueryUtil setEqualFilter(Map<String, String> equalFilter) {
        if(equalFilter != null) {
            equalFilter.forEach((key, value) -> {
                Condition condition = buildCondition(key, value);
                if (this.conditions == null) {
                    this.conditions = condition;
                } else {
                    this.conditions.andCondition(condition);
                }
            });
        }
        return this;
    }

    public QueryUtil addGroupBy(String field) {
        if (StringUtils.isNotNullOrEmpty(field)) {
            if (this.groups == null) {
                this.groups = new ArrayList<>();
            }
            this.groups.add(field);
        }
        return this;
    }

    public QueryUtil addOrderBy(String filed, Order order) {
        if (StringUtils.isNotNullOrEmpty(filed) && order != null) {
            OrderBy orderBy = new OrderBy(filed, order);
            if (this.orders == null) {
                this.orders = new ArrayList<>();
            }
            this.orders.add(orderBy);
        }
        return this;
    }

    public QueryUtil addSelectAttribute(String field) {
        if (StringUtils.isNotNullOrEmpty(field)) {
            Select select = new Select(field, SelectOp.FIELD);
            if (this.attributes == null) {
                this.attributes = new ArrayList<>();
            }
            this.attributes.add(select);
        }
        return this;
    }

    public QueryUtil orderBy(String field) {
        return addOrderBy(field, Order.ASC);
    }

    public void setPer_page(int per_page) {
        this.pageSize = per_page;
    }

    public QueryUtil addGroup(String field) {
        return addGroupBy(field);
    }


    public QueryUtil setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public QueryUtil setPageNo(int pageNo) {
        this.pageNum = pageNo;
        return this;
    }

    public QueryUtil orderBy(String field, Order order) {
        addOrderBy(field, order);
        return this;
    }

    private Condition buildCondition(String key, Object value) {
        Condition condition = null;
        if (value != null && value instanceof String && ((String) value).startsWith("[") && ((String) value).endsWith("]")) {
            String[] arrayValue = ((String) value).substring(1, ((String) value).length()-1).split(",");
            condition = new Condition(key, Arrays.asList(arrayValue), ValueOp.IN);
        } else {
            condition = new Condition(key, value);
        }
        return condition;
    }
}
