package com.moseeker.baseorm.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.ValueOp;
import org.jooq.UpdatableRecord;
import org.jooq.impl.TableImpl;

import java.util.HashMap;
import java.util.Map;

public abstract class DefaultDictOccupationDao<S, R extends UpdatableRecord<R>> extends AbstractDictOccupationDao<S, R> {

    public DefaultDictOccupationDao(TableImpl<R> table, Class<S> sClass) {
        super(table, sClass);
    }

    private static final String CODE = "CODE";
    private static final String PARENT_ID = "PARENT_ID";
    private static final String LEVEL = "LEVEL";
    private static final String CODE_OTHER = "CODE_OTHER";
    private static final String STATUS = "STATUS";

    @Override
    protected Condition statusCondition() {
        return new Condition(STATUS, 1);
    }

    @Override
    protected Map<String, Object> queryEQParam(JSONObject obj) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(CODE, obj.getInteger("code"));
        paramMap.put(PARENT_ID, obj.getInteger("parent_id"));
        paramMap.put(LEVEL, obj.getInteger("level"));
        return paramMap;
    }

    @Override
    protected boolean isTopOccupation(S s) {
        return s != null && getParentId(s) == 0;
    }

    @Override
    protected Condition conditionToSearchFather(S s) {
        return new Condition(CODE, getParentId(s));
    }

    @Override
    public int deleteAll() {
        Condition condition = new Condition(CODE, 0, ValueOp.NEQ);
        return delete(condition);
    }

    @Override
    protected String otherCodeName() {
        return CODE_OTHER;
    }

    private int getParentId(S s) {
        if (s == null) {
            return 0;
        }
        return JSON.parseObject(JSON.toJSONString(s)).getIntValue("parentId");
    }
}
