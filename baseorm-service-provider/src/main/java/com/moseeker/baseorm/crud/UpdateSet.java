package com.moseeker.baseorm.crud;

import com.moseeker.thrift.gen.common.struct.CommonUpdate;

/**
 * Created by moseeker on 2017/3/28.
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

    public UpdateSet set(String field, String value) {
        update.putToFieldValues(field, value);
        return this;
    }

    public UpdateCondition where(CommonCondition updateCondition) {
        return UpdateCondition.initWithCommonUpdate(update, updateCondition);
    }
}
