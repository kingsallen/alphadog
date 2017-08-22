package com.moseeker.common.util.query;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.exception.ConditionNotExist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将张弟设计的代码从thrift struct 转到common项目中
 * Created by jack on 18/04/2017.
 */
public class Query {

    protected java.util.List<Select> attributes; // optional
    protected Condition conditions; // optional
    protected List<OrderBy> orders; // optional
    protected List<String> groups; // optional
    protected int pageSize; // optional
    protected int pageNum; // optional
    protected Map<String, String> extras; // optional

    protected Query() {
        this.attributes = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.extras = new HashMap<>();
    }


    public static class QueryBuilder {

        private java.util.List<Select> attributes; // optional
        private Condition conditions; // optional
        private List<OrderBy> orders; // optional
        private List<String> groups; // optional
        private int pageSize; // optional
        private int pageNum; // optional
        private Map<String, String> extras; // optional
        private Condition index;

        public QueryBuilder() {
            this.attributes = new ArrayList<>();
            this.orders = new ArrayList<>();
            this.groups = new ArrayList<>();
            this.extras = new HashMap<>();
        }

        public QueryBuilder select(String field) {
            return select(field, SelectOp.FIELD);
        }

        public QueryBuilder select(String field, SelectOp selectOp) {
            if (StringUtils.isNotNullOrEmpty(field)) {
                Select select = new Select(field, selectOp);
                attributes.add(select);
            }
            return this;
        }

        public QueryBuilder select(Select select) {
            if (select != null) {
                attributes.add(select);
            }
            return this;
        }

        public QueryBuilder removeSelect(String field) {
            if (StringUtils.isNotNullOrEmpty(field)) {
                attributes.remove(field);
            }
            return this;
        }

        public QueryBuilder where(String field, Object value) throws ConditionNotExist {
            Condition condition = new Condition(field, value);
            return where(condition);
        }

        public QueryBuilder where(Condition condition) throws ConditionNotExist {
            return and(condition);
        }

        public QueryBuilder and(String field, Object value) throws ConditionNotExist {
            Condition condition = new Condition(field, value);
            return and(condition);
        }

        public QueryBuilder and(Condition condition) throws ConditionNotExist {
            if (this.index != null) {
                index.andCondition(condition);
            } else {
                setConditions(condition);
            }
            index = condition;
            return this;
        }

        public QueryBuilder or(String field, Object value) throws ConditionNotExist {
            Condition condition = new Condition(field, value);
            return or(condition);
        }

        public QueryBuilder or(Condition condition) throws ConditionNotExist {
            if (condition == null) {
                throw new ConditionNotExist();
            }
            if (this.index != null) {
                index.addCondition(condition, ConditionOp.OR);
            } else {
                setConditions(condition);
            }
            index = condition;
            return this;
        }

        public QueryBuilder andInnerCondition(String field, Object value) throws ConditionNotExist {
            Condition condition = new Condition(field, value);
            return andInnerCondition(condition);
        }

        public QueryBuilder andInnerCondition(Condition condition) throws ConditionNotExist {
            return addInnerCondition(condition, ConditionOp.AND);
        }

        public QueryBuilder orInnerCondition(String field, Object value) throws ConditionNotExist {
            Condition condition = new Condition(field, value);
            return orInnerCondition(condition);
        }

        public QueryBuilder orInnerCondition(Condition condition) throws ConditionNotExist {
            return addInnerCondition(condition, ConditionOp.OR);
        }

        public QueryBuilder addInnerCondition(Condition condition, ConditionOp op) throws ConditionNotExist {
            if (index == null) {
                throw new ConditionNotExist();
            }
            index.addInnerCondition(condition, op);
            index = condition;
            return this;
        }

        public QueryBuilder orOutCondition(String field, Object value) throws ConditionNotExist {
            Condition condition = new Condition(field, value);
            return orOutCondition(condition);
        }

        public QueryBuilder orOutCondition(Condition condition) throws ConditionNotExist {
            return addOutCondition(condition, ConditionOp.OR);
        }

        public QueryBuilder andOutCondition(String field, Object value) throws ConditionNotExist {
            Condition condition = new Condition(field, value);
            return andOutCondition(condition);
        }

        public QueryBuilder andOutCondition(Condition condition) throws ConditionNotExist {
            return addOutCondition(condition, ConditionOp.AND);
        }

        public QueryBuilder addOutCondition(Condition condition, ConditionOp op) throws ConditionNotExist {
            if (index == null || index.getOutCondition() == null) {
                throw new ConditionNotExist();
            }
            if (index.getOutCondition().getConditionJoin() == null) {
                index.getOutCondition().addCondition(condition, op);
            } else {
                Condition condition1 = index;
                while (condition1.getConditionJoin() != null && condition1.getConditionJoin().getCondition() != null) {
                    condition1 = condition1.getConditionJoin().getCondition();
                }
                if (condition1.getConditionJoin() == null) {
                    condition1.addCondition(condition, op);
                } else {
                    condition1.getConditionJoin().setCondition(condition);
                }
            }
            index = condition;
            return this;
        }

        public QueryBuilder groupBy(String field) {
            if (groups == null) {
                groups = new ArrayList<>();
            }
            if (StringUtils.isNotNullOrEmpty(field)) {
                groups.add(field);
            }
            return this;
        }

        public QueryBuilder orderBy(String field) {
            if (orders == null) {
                orders = new ArrayList<>();
            }
            if (StringUtils.isNotNullOrEmpty(field)) {
                OrderBy orderBy = new OrderBy(field, Order.ASC);
                orders.add(orderBy);
            }
            return this;
        }

        public QueryBuilder orderBy(String field, Order order) {
            if (orders == null) {
                orders = new ArrayList<>();
            }
            if (StringUtils.isNotNullOrEmpty(field)) {
                OrderBy orderBy = new OrderBy(field, order);
                orders.add(orderBy);
            }
            return this;
        }

        public QueryBuilder orderBy(OrderBy orderBy) {
            if (orders == null) {
                orders = new ArrayList<>();
            }
            if (orderBy != null && orderBy.getField() != null && orderBy.getOrder() != null) {
                orders.add(orderBy);
            }
            return this;
        }

        public QueryBuilder setPageNum(int pageNum) {
            this.pageNum = pageNum;
            return this;
        }

        public QueryBuilder setPageSize(int pageSize) {
            this.pageSize = pageSize;
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

        public void clear() {
            this.attributes = new ArrayList<>(); // optional
            this.conditions = null; // optional
            this.orders = new ArrayList<>(); // optional
            this.groups = new ArrayList<>(); // optional
            this.pageSize = 0; // optional
            this.pageNum = 0; // optional
            this.extras = new HashMap<>(); // optional
            this.index = null;
        }

        private void setConditions(Condition conditions) throws ConditionNotExist {
            if (conditions == null || StringUtils.isNullOrEmpty(conditions.getField()) || conditions.getValue() == null
                    || conditions.getValueOp() == null) {
                throw new ConditionNotExist();
            }
            this.conditions = conditions;
            index = conditions;
        }
    }

    public List<Select> getAttributes() {
        return attributes;
    }

    public Condition getConditions() {
        return conditions;
    }

    public List<OrderBy> getOrders() {
        return orders;
    }

    public List<String> getGroups() {
        return groups;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
