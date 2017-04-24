package com.moseeker.common.providerutils;

import com.moseeker.common.util.query.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * 该类只为兼容以前的调用方式，后续接口直接使用CommonQuery，或者使用新的工具类
 */

public class QueryUtil extends Query {

    @Deprecated
    public QueryUtil addEqualFilter(String key, Object value) {
        ValueCondition valueCondition = new ValueCondition(key, String.valueOf(value), ValueOp.EQ);
        if (conditions == null) {
            conditions = new Condition();
            conditions.setValueCondition(valueCondition);
        }

        if (conditions.getValueCondition() != null) {
            Condition conditions1 = new Condition();
            conditions1.setValueCondition(conditions.getValueCondition());
            Condition conditions2 = new Condition();
            conditions2.setValueCondition(valueCondition);
            conditions.setInnerCondition(new InnerCondition(conditions1, conditions2, ConditionOp.AND));
        } else {
            Condition replaceCondition = new Condition();
            Condition newCondition = new Condition();
            newCondition.setValueCondition(valueCondition);
            InnerCondition innerCondition = new InnerCondition(conditions, newCondition, ConditionOp.AND);
            replaceCondition.setInnerCondition(innerCondition);
            conditions = replaceCondition;
        }
        return this;
    }

    @Deprecated
    public QueryUtil setEqualFilter(Map<String, String> equalFilter) {
        if(equalFilter==null || equalFilter.size() == 0){
            conditions = null;
        }else if (equalFilter.size() == 1) {
            conditions = new Condition();
            for (String key : equalFilter.keySet()) {
                conditions.setValueCondition(new ValueCondition(key, equalFilter.get(key), ValueOp.EQ));
            }
        } else if (equalFilter.size() > 1) {
            Condition newCondition = null;
            for (String key : equalFilter.keySet()) {
                ValueCondition valueCondition = new ValueCondition(key, equalFilter.get(key), ValueOp.EQ);
                Condition valueConditionOuter = new Condition();
                valueConditionOuter.setValueCondition(valueCondition);
                if (newCondition == null) {
                    newCondition = valueConditionOuter;
                } else {
                    InnerCondition innerCondition = new InnerCondition(newCondition, valueConditionOuter, ConditionOp.AND);
                    newCondition = new Condition();
                    newCondition.setInnerCondition(innerCondition);
                }
            }
            conditions = newCondition;
        }
        return this;
    }

    @Deprecated
    public QueryUtil addGroupBy(String field) {
        if (groups == null) {
            groups = new ArrayList<>();
        }
        groups.add(field);
        return this;
    }

    @Deprecated
    public QueryUtil addOrderBy(String filed, Order order) {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        orders.add(new OrderBy(filed, order));
        return this;
    }

    @Deprecated
    public QueryUtil addSelectAttribute(String field) {
        addSelect(new Select(field, SelectOp.FIELD));
        return this;
    }

    /*@Deprecated
    public void setOrder(String order) {
        this.tmpOrder = order;
        checkOrder();
    }

    @Deprecated
    public void setSortby(String sortby) {
        this.tmpSort = sortby;
        checkOrder();
    }*/

    @Deprecated
    public void setPer_page(int per_page) {
        this.pageSize = per_page;
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

    public static QueryUtil where(CommonCondition commonCondition) {
        return new QueryUtil().select().where(commonCondition);
    }
}
