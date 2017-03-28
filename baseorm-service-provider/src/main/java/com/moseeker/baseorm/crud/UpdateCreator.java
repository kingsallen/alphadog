package com.moseeker.baseorm.crud;

/**
 * Created by zhangdi on 2017/3/22.
 */
public class UpdateCreator {

    public static UpdateSet set(String field, String value) {
        return new UpdateSet(field, value);
    }
}
