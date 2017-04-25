package com.moseeker.common.util.query;

import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.exception.ConditionNotExist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 将张弟设计的代码从thrift struct 转到common项目中
 * Created by jack on 18/04/2017.
 */
public class Query {

    private java.util.List<Select> attributes; // optional
    private Condition conditions; // optional
    private List<OrderBy> orders; // optional
    private List<String> groups; // optional
    private int pageSize; // optional
    private int pageNum; // optional
    private Map<String,String> extras; // optional

    private Query() {
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
        private Condition index;

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

        public QueryBuilder where(String field, Object value) throws ConditionNotExist {
            Condition condition = new Condition(field, value);
            setConditions(condition);
            index = condition;
            return this;
        }

        public QueryBuilder where(Condition condition) throws ConditionNotExist {
            setConditions(condition);
            index = condition;
            return this;
        }

        public QueryBuilder and(Condition condition) throws ConditionNotExist {
            if(this.index != null) {
                conditions.addCondition(condition);
            } else {
                setConditions(condition);
            }
            index = condition;
            return this;
        }

        public QueryBuilder andInnerCondition(Condition condition) throws ConditionNotExist {
            return andInnerCondition(condition, ConditionOp.AND);
        }

        public QueryBuilder andInnerCondition(Condition condition, ConditionOp op) throws ConditionNotExist {
            if(index == null) {
                throw new ConditionNotExist();
            }
            ConditionJoin conditionJoin = new ConditionJoin(op, null, condition);
            index.addJoinCondition(conditionJoin);
            index = condition;
            return this;
        }

        public QueryBuilder andOutCondition(Condition condition, ConditionOp op) throws ConditionNotExist {
            if (index.getOutCondition() == null) {
                throw new ConditionNotExist();
            }
            index.getOutCondition().addCondition(condition, op);
            index = condition;
            return this;
        }

        public QueryBuilder andOutCondition(Condition condition) throws ConditionNotExist {
            if (index.getOutCondition() == null) {
                throw new ConditionNotExist();
            }
            index.getOutCondition().addCondition(condition, ConditionOp.AND);
            index = condition;
            return this;
        }

        public QueryBuilder groupBy(String field) {
            if (groups == null) {
                groups = new ArrayList<>();
            }
            if(StringUtils.isNotNullOrEmpty(field)) {
                groups.add(field);
            }
            return this;
        }

        public QueryBuilder orderBy(String field) {
            if (orders == null) {
                orders = new ArrayList<>();
            }
            if(StringUtils.isNotNullOrEmpty(field)) {
                OrderBy orderBy = new OrderBy(field, Order.ASC);
                orders.add(orderBy);
            }
            return this;
        }

        public QueryBuilder orderBy(String field, Order order) {
            if (orders == null) {
                orders = new ArrayList<>();
            }
            if(StringUtils.isNotNullOrEmpty(field)) {
                OrderBy orderBy = new OrderBy(field, order);
                orders.add(orderBy);
            }
            return this;
        }

        public QueryBuilder orderBy(OrderBy orderBy) {
            if (orders == null) {
                orders = new ArrayList<>();
            }
            if(orderBy != null && orderBy.getField() != null && orderBy.getOrder() != null) {
                orders.add(orderBy);
            }
            return this;
        }

        public Query buildQuery() {
            Query query = new Query();
            query.attributes = this.attributes;
            query.conditions = this.conditions;
            query.extras = this.extras;
            query.groups = this.groups;
            query.orders = this.orders;
            query.pageNum = this.pageNum;
            query.pageSize = this.pageSize;

            return query;
        }

        private void setConditions(Condition conditions) throws ConditionNotExist {
            if(conditions == null || StringUtils.isNullOrEmpty(conditions.getField()) || conditions.getValue() == null
                    || conditions.getValueOp() == null) {
                throw new ConditionNotExist();
            }
            this.conditions = conditions;
            index = conditions;
        }
    }
}
