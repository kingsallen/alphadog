package com.moseeker.common.util.query;

import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.exception.ConditionNotExist;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jack on 02/05/2017.
 */
public class Update {

    private Condition conditions; // optional
    private Map<String,String> fieldValues; // optional
    private Map<String,String> extras; // optional

    private Update() {}

    private Update(Condition condition, Map fieldValues, Map extras) {
        this.conditions = condition;
        this.fieldValues = fieldValues;
        this.extras = extras;
    }

    public static class UpdateBuilder {

        private Condition conditions; // optional
        private java.util.Map<String,Object> fieldValues = new HashMap<>(); // optional
        private java.util.Map<String,Object> extras = new HashMap<>(); // optional
        private Condition index;

        public UpdateBuilder() {}

        public UpdateBuilder set(String field, Object value) {

            fieldValues.put(field, value);

            return this;
        }

        public UpdateBuilder where(String field, Object value) throws ConditionNotExist {
            Condition condition = new Condition(field, value);
            setConditions(condition);
            index = condition;
            return this;
        }

        public UpdateBuilder where(Condition condition) throws ConditionNotExist {
            setConditions(condition);
            index = condition;
            return this;
        }

        public UpdateBuilder and(Condition condition) throws ConditionNotExist {
            if(this.index != null) {
                conditions.andCondition(condition);
            } else {
                setConditions(condition);
            }
            index = condition;
            return this;
        }

        public UpdateBuilder and(String field, Object value) throws ConditionNotExist {
            Condition condition = new Condition(field, value);
            if(this.index != null) {
                conditions.andCondition(condition);
            } else {
                setConditions(condition);
            }
            index = condition;
            return this;
        }

        public UpdateBuilder or(String field, Object value) throws ConditionNotExist {
            Condition condition = new Condition(field, value);
            if(this.index != null) {
                conditions.addCondition(condition, ConditionOp.OR);
            } else {
                setConditions(condition);
            }
            index = condition;
            return this;
        }

        public UpdateBuilder or(Condition condition) throws ConditionNotExist {
            if(condition == null) {
                throw new ConditionNotExist();
            }
            if(this.index != null) {
                conditions.addCondition(condition, ConditionOp.OR);
            } else {
                setConditions(condition);
            }
            index = condition;
            return this;
        }

        public UpdateBuilder andInnerCondition(Condition condition) throws ConditionNotExist {
            return addInnerCondition(condition, ConditionOp.AND);
        }

        public UpdateBuilder orInnerCondition(Condition condition) throws ConditionNotExist {
            return addInnerCondition(condition, ConditionOp.OR);
        }

        public UpdateBuilder addInnerCondition(Condition condition, ConditionOp op) throws ConditionNotExist {
            if(index == null) {
                throw new ConditionNotExist();
            }
            index.addInnerCondition(condition, op);
            index = condition;
            return this;
        }

        public UpdateBuilder addOutCondition(Condition condition, ConditionOp op) throws ConditionNotExist {
            if (index.getOutCondition() == null) {
                throw new ConditionNotExist();
            }
            index.getOutCondition().addCondition(condition, op);
            index = condition;
            return this;
        }

        public UpdateBuilder andOutCondition(Condition condition) throws ConditionNotExist {
            if (index.getOutCondition() == null) {
                throw new ConditionNotExist();
            }
            index.getOutCondition().addCondition(condition, ConditionOp.AND);
            index = condition;
            return this;
        }

        public Update buildUpdate() {
            Update update = new Update(this.conditions, this.fieldValues, this.extras);
            return update;
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

    public Condition getConditions() {
        return conditions;
    }

    public Map<String, String> getFieldValues() {
        return fieldValues;
    }

    public Map<String, String> getExtras() {
        return extras;
    }
}
