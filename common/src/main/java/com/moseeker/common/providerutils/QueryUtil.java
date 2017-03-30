package com.moseeker.common.providerutils;

import com.moseeker.thrift.gen.common.struct.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * 该类只为兼容以前的调用方式，后续接口直接使用CommonQuery，或者使用新的工具类
 */
@Deprecated
public class QueryUtil extends CommonQuery {

    private static final long serialVersionUID = 2531526866610292082L;

<<<<<<< Updated upstream
    @Deprecated
    public QueryUtil addEqualFilter(String key, Object value) {
        ValueCondition valueCondition = new ValueCondition(key, String.valueOf(value), ValueOp.EQ);
=======
    public QueryUtil addEqualFilter(String key, String value) {
        ValueCondition valueCondition = new ValueCondition(key, value, ValueOp.EQ);
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
        if(equalFilter==null || equalFilter.size() == 0){
            conditions = null;
        }else if (equalFilter.size() == 1) {
=======
        if (equalFilter.size() == 1) {
>>>>>>> Stashed changes
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
        addToAttributes(new Select(field, SelectOp.FIELD));
        return this;
    }

    String tmpOrder;

    @Deprecated
    public void setOrder(String order) {
        this.tmpOrder = order;
        checkOrder();
    }

    String tmpSort;

    @Deprecated
    public void setSortby(String sortby) {
        this.tmpSort = sortby;
        checkOrder();
    }


    private void checkOrder() {
        if (tmpSort != null && tmpOrder != null) {
            if ("desc".equals(tmpOrder.trim())) {
                addToOrders(new OrderBy(tmpSort, Order.DESC));
            } else {
                addToOrders(new OrderBy());
            }
            tmpOrder = null;
            tmpSort = null;
        }
    }

    @Deprecated
    public void setPer_page(int per_page) {
        this.pageSize = per_page;
    }

    @Deprecated
    public QueryUtil addGroup(String field) {
        addToGroups(field);
        return this;
    }
}
