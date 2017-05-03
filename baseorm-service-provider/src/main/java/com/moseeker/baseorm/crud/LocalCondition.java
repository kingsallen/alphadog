package com.moseeker.baseorm.crud;

import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.ConditionOp;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.ValueOp;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.TableImpl;

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
                return field.in((List)value);
            case NIN:
                return field.notIn((List)value);
            case GT:
                return field.greaterThan(convertTo(value, field.getType()));
            case GE:
                return field.greaterOrEqual(convertTo(value, field.getType()));
            case LT:
                return field.lessThan(convertTo(value, field.getType()));
            case LE:
                return field.lessOrEqual(convertTo(value, field.getType()));
            case BT:
                List list = (List)value;
                return field.between(convertTo(list.get(0), field.getType()), convertTo(list.get(1), field.getType()));
            case NBT:
                List list1 = (List)value;
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

        if(condition != null) {
            Field<?> field = table.field(condition.getField());
            if(field != null) {
                org.jooq.Condition jooqCondition = connectValueCondition(field, condition.getValue(),
                        condition.getValueOp());

                return jooqCondition;

            }
        }
        return null;

    }

    private org.jooq.Condition parseCondition(Condition condition) {

        org.jooq.Condition jooqCondition = null;
        if (condition != null) {
            jooqCondition = convertCondition(condition);
            if (jooqCondition != null) {
                if (condition.getConditionInnerJoin() != null && condition.getConditionInnerJoin().getCondition() != null) {
                    org.jooq.Condition tempCondition = parseCondition(condition.getConditionInnerJoin().getCondition());
                    jooqCondition = packageConditionOp(jooqCondition, tempCondition, condition.getConditionInnerJoin().getOp(), true);
                }
                if (condition.getConditionJoin() != null && condition.getConditionJoin().getCondition() != null) {
                    org.jooq.Condition tempCondition = parseCondition(condition.getConditionJoin().getCondition());
                    jooqCondition = packageConditionOp(jooqCondition, tempCondition, condition.getConditionJoin().getOp(), false);
                }

            }
        }
        return jooqCondition;
    }

    private org.jooq.Condition packageConditionOp(org.jooq.Condition jooqCondition, org.jooq.Condition nextCondition, ConditionOp op, boolean innerJoin) {
        switch (op) {
            case AND:
                if (innerJoin) {
                    return jooqCondition.and((nextCondition));
                } else {
                    return jooqCondition.and(nextCondition);
                }

            case OR:
                if (innerJoin) {
                    return jooqCondition.or((nextCondition));
                } else {
                    return jooqCondition.or(nextCondition);
                }

            default:
                return jooqCondition;
        }
    }

    public class ConditionCollection {

        private Condition condition;
        private ConditionOp op;

        public Condition getCondition() {
            return condition;
        }

        public void setCondition(Condition condition) {
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
