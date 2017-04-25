package com.moseeker.common.util.query;

import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.exception.ConditionNotExist;

/**
 * 将张弟设计的代码从thrift struct 转到common项目中
 * Created by jack on 18/04/2017.
 */
public class Condition {

    private String field;           //属性名称
    private Object value;           //比较对象
    private ValueOp valueOp;            //比较操作

    private ConditionJoin conditionJoin; //联合条件
    private Condition outCondition;      //上一级别的查询条件

    public static Condition buildCommonCondition(String field, Object value, ValueOp op) throws ConditionNotExist {
        if(StringUtils.isNullOrEmpty(field) || value == null || op == null) {
            throw new ConditionNotExist();
        }
        return new Condition(field, value, op);
    }

    public static Condition buildCommonCondition(String field, Object value) throws ConditionNotExist {
        if(StringUtils.isNullOrEmpty(field) || value == null) {
            throw new ConditionNotExist();
        }
        return new Condition(field, value, ValueOp.EQ);
    }

    public Condition(String field, Object value) throws ConditionNotExist {
        if(StringUtils.isNullOrEmpty(field) || value == null) {
            throw new ConditionNotExist();
        }
        this.field = field;
        this.value = value;
        this.valueOp = ValueOp.EQ;
    }

    public Condition(String field, Object value, ValueOp op) throws ConditionNotExist {
        if(StringUtils.isNullOrEmpty(field) || value == null || op == null) {
            throw new ConditionNotExist();
        }
        this.field = field;
        this.value = value;
        this.valueOp = op;
    }

    public Condition addInnerCondition(Condition condition, ConditionOp op) throws ConditionNotExist {
        if(condition == null || op == null) {
            throw new ConditionNotExist();
        }
        ConditionJoin conditionJoin = new ConditionJoin(op, null, condition);
        this.conditionJoin = conditionJoin;
        condition.setOutCondition(this);
        return condition;
    }

    public Condition addCondition(Condition condition, ConditionOp op) throws ConditionNotExist {
        if(condition == null || op == null) {
            throw new ConditionNotExist();
        }
        ConditionJoin conditionJoin = new ConditionJoin(op, condition, null);
        this.conditionJoin = conditionJoin;
        return condition;
    }

    public Condition addJoinCondition(ConditionJoin condition) throws ConditionNotExist {
        if(condition == null) {
            throw new ConditionNotExist();
        }
        this.conditionJoin = conditionJoin;
        condition.getCondition().setOutCondition(this);
        return conditionJoin.getCondition();
    }

    public Condition addCondition(Condition condition) throws ConditionNotExist {
        if(condition == null) {
            throw new ConditionNotExist();
        }
        return addCondition(condition, ConditionOp.AND);
    }

    public Condition equal(String field, Object value, ValueOp op) {
        if(StringUtils.isNullOrEmpty(field) || value == null || op == null) {
            throw new ConditionNotExist();
        }
        Condition condition = new Condition(field, value, op);
        ConditionJoin conditionJoin = new ConditionJoin(ConditionOp.AND, condition, null);
        this.conditionJoin = conditionJoin;
        return condition;
    }

    public Condition equal(Condition condition) throws ConditionNotExist {
        if(condition == null) {
            throw new ConditionNotExist();
        }
        ConditionJoin conditionJoin = new ConditionJoin(ConditionOp.AND, condition, null);
        this.conditionJoin = conditionJoin;
        return condition;
    }

    public Condition notEqual(String field, Object value) throws ConditionNotExist {
        if(StringUtils.isNullOrEmpty(field) || value == null) {
            throw new ConditionNotExist();
        }
        Condition condition = new Condition(field, value, ValueOp.NEQ);
        ConditionJoin conditionJoin = new ConditionJoin(ConditionOp.AND, condition, null);
        this.conditionJoin = conditionJoin;
        return condition;
    }

    public Condition greater(String field, Object value) throws ConditionNotExist {
        if(StringUtils.isNullOrEmpty(field) || value == null) {
            throw new ConditionNotExist();
        }
        Condition condition = new Condition(field, value, ValueOp.GT);
        ConditionJoin conditionJoin = new ConditionJoin(ConditionOp.AND, condition, null);
        this.conditionJoin = conditionJoin;
        return condition;
    }

    public Condition greaterOrEqual(String field, Object value) throws ConditionNotExist {
        if(StringUtils.isNullOrEmpty(field) || value == null) {
            throw new ConditionNotExist();
        }
        Condition condition = new Condition(field, value, ValueOp.GE);
        ConditionJoin conditionJoin = new ConditionJoin(ConditionOp.AND, condition, null);
        this.conditionJoin = conditionJoin;
        return condition;
    }

    public Condition less(String field, Object value) throws ConditionNotExist {
        if(StringUtils.isNullOrEmpty(field) || value == null) {
            throw new ConditionNotExist();
        }
        Condition condition = new Condition(field, value, ValueOp.LT);
        ConditionJoin conditionJoin = new ConditionJoin(ConditionOp.AND, condition, null);
        this.conditionJoin = conditionJoin;
        return condition;
    }

    public Condition lessOrEqual(String field, Object value) throws ConditionNotExist {
        if(StringUtils.isNullOrEmpty(field) || value == null) {
            throw new ConditionNotExist();
        }
        Condition condition = new Condition(field, value, ValueOp.LE);
        ConditionJoin conditionJoin = new ConditionJoin(ConditionOp.AND, condition, null);
        this.conditionJoin = conditionJoin;
        return condition;
    }

    public Condition in(String field, Object value) throws ConditionNotExist {
        if(StringUtils.isNullOrEmpty(field) || value == null) {
            throw new ConditionNotExist();
        }
        Condition condition = new Condition(field, value, ValueOp.IN);
        ConditionJoin conditionJoin = new ConditionJoin(ConditionOp.AND, condition, null);
        this.conditionJoin = conditionJoin;
        return condition;
    }

    public Condition notIn(String field, Object value) throws ConditionNotExist {
        if(StringUtils.isNullOrEmpty(field) || value == null) {
            throw new ConditionNotExist();
        }
        Condition condition = new Condition(field, value, ValueOp.NIN);
        ConditionJoin conditionJoin = new ConditionJoin(ConditionOp.AND, condition, null);
        this.conditionJoin = conditionJoin;
        return condition;
    }

    public Condition between(String field, Object value) throws ConditionNotExist {
        if(StringUtils.isNullOrEmpty(field) || value == null) {
            throw new ConditionNotExist();
        }
        Condition condition = new Condition(field, value, ValueOp.BT);
        ConditionJoin conditionJoin = new ConditionJoin(ConditionOp.AND, condition, null);
        this.conditionJoin = conditionJoin;
        return condition;
    }

    public Condition notBetween(String field, Object value) throws ConditionNotExist {
        if(StringUtils.isNullOrEmpty(field) || value == null) {
            throw new ConditionNotExist();
        }
        Condition condition = new Condition(field, value, ValueOp.NBT);
        ConditionJoin conditionJoin = new ConditionJoin(ConditionOp.AND, condition, null);
        this.conditionJoin = conditionJoin;
        return condition;
    }

    public Condition like(String field, Object value) throws ConditionNotExist {
        if(StringUtils.isNullOrEmpty(field) || value == null) {
            throw new ConditionNotExist();
        }
        Condition condition = new Condition(field, value, ValueOp.LIKE);
        ConditionJoin conditionJoin = new ConditionJoin(ConditionOp.AND, condition, null);
        this.conditionJoin = conditionJoin;
        return condition;
    }

    public Condition unLike(String field, Object value) throws ConditionNotExist {
        if(StringUtils.isNullOrEmpty(field) || value == null) {
            throw new ConditionNotExist();
        }
        Condition condition = new Condition(field, value, ValueOp.NLIKE);
        ConditionJoin conditionJoin = new ConditionJoin(ConditionOp.AND, condition, null);
        this.conditionJoin = conditionJoin;
        return condition;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }

    public ValueOp getValueOp() {
        return valueOp;
    }

    public ConditionJoin getConditionJoin() {
        return conditionJoin;
    }

    public Condition getOutCondition() {
        return outCondition;
    }

    void setOutCondition(Condition outCondition) {
        this.outCondition = outCondition;
    }
}
