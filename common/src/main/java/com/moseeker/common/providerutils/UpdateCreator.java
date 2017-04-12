package com.moseeker.common.providerutils;

import java.util.Map;

/**
 * Created by zhangdi on 2017/3/22.
 */
public class UpdateCreator {

    public static UpdateSet set(String field, String value) {
        return new UpdateSet(field, value);
    }

    public static UpdateSet set(Map<String,String> fieldValue) {
        return new UpdateSet(fieldValue);
    }
}
