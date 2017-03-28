package com.moseeker.baseorm.crud;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.ConditionOp;
import com.moseeker.thrift.gen.common.struct.InnerCondition;
import com.moseeker.thrift.gen.common.struct.ValueCondition;
import com.moseeker.thrift.gen.common.struct.ValueOp;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.TableImpl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by moseeker on 2017/3/23.
 */
public class LocalCondition<R extends Record> {

    TableImpl<R> table;

    public LocalCondition(TableImpl<R> table) {
        this.table = table;
    }

    public <T> T convertTo(String value, Class<T> tClass) {
        return BeanUtils.convertTo(value, tClass);
    }

    public <E> org.jooq.Condition connectValueCondition(Field<E> field, String value, ValueOp valueOp) {
        switch (valueOp) {
            case EQ:
                return field.equal(convertTo(value, field.getType()));
            case NEQ:
                return field.notEqual(convertTo(value, field.getType()));
            case IN:
                if (value.startsWith("[") && value.endsWith("]")) {
                    return field.in(JSON.parseArray(value, String.class)
                            .stream()
                            .map(str -> convertTo(str, field.getType()))
                            .collect(Collectors.toList()));
                } else {
                    return null;
                }
            case NIN:
                if (value.startsWith("[") && value.endsWith("]")) {
                    return field.notIn(JSON.parseArray(value, String.class)
                            .stream()
                            .map(str -> convertTo(str, field.getType()))
                            .collect(Collectors.toList()));
                } else {
                    return null;
                }
            case GT:
                return field.greaterThan(convertTo(value, field.getType()));
            case GE:
                return field.greaterOrEqual(convertTo(value, field.getType()));
            case LT:
                return field.lessThan(convertTo(value, field.getType()));
            case LE:
                return field.lessOrEqual(convertTo(value, field.getType()));
            case BT:
                if (value.startsWith("[") && value.endsWith("]")) {
                    List<E> list = JSON.parseArray(value, String.class)
                            .stream()
                            .map(str -> convertTo(str, field.getType()))
                            .collect(Collectors.toList());
                    return list.size() > 1 ? field.between(list.get(0), list.get(1)) : null;
                } else {
                    return null;
                }
            case NBT:
                if (value.startsWith("[") && value.endsWith("]")) {
                    List<E> list = JSON.parseArray(value, String.class)
                            .stream()
                            .map(str -> convertTo(str, field.getType()))
                            .collect(Collectors.toList());
                    return list.size() > 1 ? field.notBetween(list.get(0), list.get(1)) : null;
                } else {
                    return null;
                }
            case LIKE:
                return field.like(value);
            case NLIKE:
                return field.notLike(value);
            default:
                throw new IllegalArgumentException("error value constraint");
        }
    }

    public org.jooq.Condition connectInnerCondition(InnerCondition innerCondition) {
        org.jooq.Condition c1 = convertCondition(innerCondition.getFirstCondition());
        org.jooq.Condition c2 = convertCondition(innerCondition.getSecondCondition());

        if (innerCondition.getConditionOp() == ConditionOp.AND) {
            return c1.and(c2);
        } else if (innerCondition.getConditionOp() == ConditionOp.OR) {
            return c1.or(c2);
        } else {
            throw new IllegalArgumentException("error condition");
        }
    }

    public org.jooq.Condition connectValueCondition(ValueCondition valueCondition) {
        Field<?> field = table.field(valueCondition.field);
        if (field != null) {
            return connectValueCondition(field, valueCondition.value, valueCondition.valueOp);
        } else {
            throw new IllegalArgumentException("error field:" + valueCondition.field);
        }
    }

    public org.jooq.Condition convertCondition(com.moseeker.thrift.gen.common.struct.Condition condition) {
        if (condition.getInnerCondition() != null) {
            return connectInnerCondition(condition.getInnerCondition());
        } else if (condition.getValueCondition() != null) {
            return connectValueCondition(condition.getValueCondition());
        } else {
            return null;
        }
    }
}
