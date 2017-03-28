package com.moseeker.common.providerutils;

import com.moseeker.thrift.gen.common.struct.*;

import java.util.ArrayList;
import java.util.Map;

public class QueryUtil extends CommonQuery {

    private static final long serialVersionUID = 2531526866610292082L;

    public QueryUtil addEqualFilter(String key, String value) {
        ValueCondition valueCondition = new ValueCondition(key, value, ValueOp.EQ);
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

    public QueryUtil setEqualFilter(Map<String, String> equalFilter) {
        if (equalFilter.size() == 1) {
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

    public QueryUtil addGroupBy(String field) {
        if (groups == null) {
            groups = new ArrayList<>();
        }
        groups.add(field);
        return this;
    }

    public QueryUtil addOrderBy(String filed, Order order) {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        orders.add(new OrderBy(filed, order));
        return this;
    }
}
