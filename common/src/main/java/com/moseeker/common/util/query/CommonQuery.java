package com.moseeker.common.util.query;

import com.moseeker.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 将张弟设计的代码从thrift struct 转到common项目中
 * Created by jack on 18/04/2017.
 */
public class CommonQuery {

    private java.util.List<Select> attributes; // optional
    private Condition conditions; // optional
    private List<OrderBy> orders; // optional
    private List<String> groups; // optional
    private int pageSize; // optional
    private int pageNum; // optional
    private Map<String,String> extras; // optional

    public CommonQuery() {
        this.attributes = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public void addSelect(String field) {
        Select select = new Select(field, SelectOp.FIELD);
        this.attributes.add(select);
    }

    public void addSelect(Select select) {
        this.attributes.add(select);
    }

    public void addToOrders(OrderBy orderBy) {
        this.orders.add(orderBy);
    }

    public void addToGroups(String field) {
        this.groups.add(field);
    }

    public static class QueryBuilder {

        private java.util.List<Select> attributes; // optional
        private Condition conditions; // optional
        private List<OrderBy> orders; // optional
        private List<String> groups; // optional
        private int pageSize; // optional
        private int pageNum; // optional
        private Map<String,String> extras; // optional

        public QueryBuilder() {
            this.attributes = new ArrayList<>();
            this.orders = new ArrayList<>();
        }

        public QueryBuilder select(String field) {
            return select(field, SelectOp.FIELD);
        }

        public QueryBuilder select(String field, SelectOp selectOp) {
            if(StringUtils.isNullOrEmpty(field)) {
                Select select = new Select(field, SelectOp.FIELD);
                attributes.add(select);
            }
            return this;
        }

        public QueryBuilder select(Select select) {
            if(select != null) {
                attributes.add(select);
            }
            return this;
        }

        public QueryBuilder where(String field, Object value) {
            ValueCondition valueCondition = new ValueCondition(field, value, ValueOp.EQ);
            CommonCondition commonCondition = new CommonCondition();
            return this;
        }
    }
}
