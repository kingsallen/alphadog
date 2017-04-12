package com.moseeker.common.providerutils;

import com.moseeker.thrift.gen.common.struct.CommonUpdate;

import java.util.Map;

/**
 * Created by zhangdi on 2017/3/28.
 */
public class UpdateSet {
    private CommonUpdate update;

    public UpdateSet() {
        update = new CommonUpdate();
    }

    public UpdateSet(String field, String value) {
        this();
        set(field, value);
    }

    public UpdateSet(Map<String, String> fieldValue) {
        this();
        for (String field : fieldValue.keySet()) {
            set(field, fieldValue.get(field));
        }
    }

    public UpdateSet set(String field, String value) {
        update.putToFieldValues(field, value);
        return this;
    }

    public CommonUpdate getCommonUpdate() {
        return update;
    }

    public UpdateCondition where(CommonCondition updateCondition) {
        return UpdateCondition.initWithCommonUpdate(update, updateCondition);
    }
}
