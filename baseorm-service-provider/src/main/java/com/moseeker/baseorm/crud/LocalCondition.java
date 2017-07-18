package com.moseeker.baseorm.crud;

import com.moseeker.baseorm.exception.ExceptionCategory;
import com.moseeker.baseorm.exception.ExceptionFactory;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.*;
import com.moseeker.common.util.query.Condition;

import java.util.stream.Collectors;

import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moseeker on 2017/3/23.
 */
public class LocalCondition<R extends Record> {

    TableImpl<R> table;

    public LocalCondition(TableImpl<R> table) {
        this.table = table;
    }

    public org.jooq.Condition parseConditionUtil(Condition condition) {
        if (condition != null) {
            org.jooq.Condition jooqCondition = parseCondition(condition);
            return jooqCondition;
        }

        return null;
    }

    private <T> T convertTo(Object value, Class<T> tClass) {
        return BeanUtils.convertTo(value, tClass);
    }

    private <E> org.jooq.Condition connectValueCondition(Field<E> field, Object value, ValueOp valueOp) {

        switch (valueOp) {
            case EQ:
                return field.equal(convertTo(value, field.getType()));
            case NEQ:
                return field.notEqual(convertTo(value, field.getType()));
            case IN:
                return field.in(BeanUtils.converToList(value).stream().map(m -> convertTo(m, field.getType())).collect(Collectors.toList()));
            case NIN:
                return field.notIn(BeanUtils.converToList(value).stream().map(m -> convertTo(m, field.getType())).collect(Collectors.toList()));
            case GT:
                return field.greaterThan(convertTo(value, field.getType()));
            case GE:
                return field.greaterOrEqual(convertTo(value, field.getType()));
            case LT:
                return field.lessThan(convertTo(value, field.getType()));
            case LE:
                return field.lessOrEqual(convertTo(value, field.getType()));
            case BT:
                List list = (List) value;
                return field.between(convertTo(list.get(0), field.getType()), convertTo(list.get(1), field.getType()));
            case NBT:
                List list1 = (List) value;
                return field.notBetween(convertTo(list1.get(0), field.getType()), convertTo(list1.get(1), field.getType()));
            case LIKE:
                return field.like(convertTo(value, String.class));
            case NLIKE:
                return field.notLike(convertTo(value, String.class));
            default:
                return null;
        }
    }

    private org.jooq.Condition convertCondition(Condition condition) {
        if (condition != null) {
            Field<?> field = table.field(condition.getField());
            if (StringUtils.isEmptyObject(field)) {
                throw ExceptionFactory.buildException(ExceptionCategory.CONDITION_FIELD_NOEXIST);
            }
            org.jooq.Condition jooqCondition = connectValueCondition(field, condition.getValue(),
                    condition.getValueOp());
            return jooqCondition;
        }
        return null;

    }

    /**
     * 解析查询条件
     *
     * @param condition
     * @return
     */
    private org.jooq.Condition parseCondition(Condition condition) {


        org.jooq.Condition jooqCondition = null;
        if (condition != null) {
            jooqCondition = convertCondition(condition);
            if (jooqCondition != null) {
                List<ConditionCollection> conditionCollectionList = new ArrayList<>();
                parseInnerCondition(condition, conditionCollectionList);
                parseNextCondition(conditionCollectionList, condition);
                jooqCondition = packageCondition(jooqCondition, conditionCollectionList);

            }
        }
        return jooqCondition;
    }

    /**
     * 组装同级别的查询条件
     *
     * @param jooqCondition           第一个查询条件
     * @param conditionCollectionList 其余的查询条件集合
     * @return 查询条件
     */
    private org.jooq.Condition packageCondition(org.jooq.Condition jooqCondition, List<ConditionCollection> conditionCollectionList) {

        if (conditionCollectionList != null && conditionCollectionList.size() > 0) {
            for (ConditionCollection conditionCollection : conditionCollectionList) {
                jooqCondition = packageConditionOp(jooqCondition, conditionCollection.getCondition(), conditionCollection.getOp());
            }
        }
        return jooqCondition;
    }

    /**
     * 解析condition同级别的查询条件
     *
     * @param conditionCollectionList 解析结果
     * @param condition               查询条件
     */
    private void parseNextCondition(List<ConditionCollection> conditionCollectionList, Condition condition) {
        ConditionJoin temConditionJoin = condition.getConditionJoin();
        while (temConditionJoin != null && temConditionJoin.getCondition() != null) {
            org.jooq.Condition tempJooqCondition = convertCondition(temConditionJoin.getCondition());
            if (tempJooqCondition != null) {

                ConditionCollection conditionCollection = new ConditionCollection();
                conditionCollection.setCondition(tempJooqCondition);
                conditionCollection.setOp(temConditionJoin.getOp());
                conditionCollectionList.add(conditionCollection);

                parseInnerCondition(temConditionJoin.getCondition(), conditionCollectionList);
            }
            if (temConditionJoin.getCondition() != null && temConditionJoin.getCondition().getConditionJoin() != null) {
                temConditionJoin = temConditionJoin.getCondition().getConditionJoin();
            } else {
                temConditionJoin = null;
            }
        }
    }

    private void parseInnerCondition(Condition condition, List<ConditionCollection> conditionCollectionList) {
        if (condition.getConditionInnerJoin() != null && condition.getConditionInnerJoin().getCondition() != null) {
            org.jooq.Condition tempJooqCondition = parseCondition(condition.getConditionInnerJoin().getCondition());
            if (tempJooqCondition != null) {
                ConditionCollection conditionCollection = new ConditionCollection();
                conditionCollection.setCondition(tempJooqCondition);
                conditionCollection.setOp(condition.getConditionInnerJoin().getOp());
                conditionCollectionList.add(conditionCollection);
            }
        }
    }

    private org.jooq.Condition packageConditionOp(org.jooq.Condition jooqCondition, org.jooq.Condition nextCondition, ConditionOp op) {
        //org.jooq.Condition result = DSL.trueCondition();
        //result.and(jooqCondition);
        switch (op) {
            case AND:
                return jooqCondition.and(nextCondition);
            case OR:
                return jooqCondition.or(nextCondition);
            default:
                return jooqCondition;
        }
    }

    public class ConditionCollection {

        private org.jooq.Condition condition;
        private ConditionOp op;

        public org.jooq.Condition getCondition() {
            return condition;
        }

        public void setCondition(org.jooq.Condition condition) {
            this.condition = condition;
        }

        public ConditionOp getOp() {
            return op;
        }

        public void setOp(ConditionOp op) {
            this.op = op;
        }
    }
}
