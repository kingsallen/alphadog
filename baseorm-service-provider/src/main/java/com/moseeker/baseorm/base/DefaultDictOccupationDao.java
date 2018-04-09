package com.moseeker.baseorm.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.ValueOp;
import org.jooq.Field;
import org.jooq.UpdatableRecord;
import org.jooq.impl.TableImpl;

import java.util.HashMap;
import java.util.Map;

public abstract class DefaultDictOccupationDao<S, R extends UpdatableRecord<R>> extends AbstractDictOccupationDao<S, R> {

    public DefaultDictOccupationDao(TableImpl<R> table, Class<S> sClass) {
        super(table, sClass);
    }

    private static final String CODE = "code";
    private static final String PARENT_ID = "parent_id";
    private static final String LEVEL = "level";
    private static final String CODE_OTHER = "code_other";
    private static final String STATUS = "status";

    @Override
    protected Condition statusCondition() {
        return new Condition(fieldIgnoreCase(STATUS), 1);
    }

    @Override
    protected Map<String, Object> queryEQParam(JSONObject obj) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(fieldIgnoreCase(CODE), obj.getInteger(CODE));
        paramMap.put(fieldIgnoreCase(PARENT_ID), obj.getInteger(PARENT_ID));
        paramMap.put(fieldIgnoreCase(LEVEL), obj.getInteger(LEVEL));
        return paramMap;
    }

    @Override
    protected boolean isTopOccupation(S s) {
        return s != null && getParentId(s) == 0;
    }

    @Override
    protected Condition conditionToSearchFather(S s) {
        return new Condition(fieldIgnoreCase(CODE), getParentId(s));
    }

    @Override
    public int deleteAll() {
        Condition condition = new Condition(fieldIgnoreCase(CODE), 0, ValueOp.GE);
        return delete(condition);
    }

    @Override
    protected String otherCodeName() {
        return fieldIgnoreCase(CODE_OTHER);
    }

    /**
     * 因为jooq生成的table.field对应的大小写未知，可能全大写(STATUS)，可能小写(status)
     * 所以需要找到字段对应的jooq字段名称
     * @param fildName
     * @return
     */
    private String fieldIgnoreCase(String fildName){
        for(Field field:table.fields()){
            if(field.getName().equalsIgnoreCase(fildName)){
                return field.getName();
            }
        }
        throw new NullPointerException();
    }

    private int getParentId(S s) {
        if (s == null) {
            return 0;
        }
        return JSON.parseObject(JSON.toJSONString(s)).getIntValue("parentId");
    }
}
