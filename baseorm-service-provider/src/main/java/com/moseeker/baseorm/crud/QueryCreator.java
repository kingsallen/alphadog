package com.moseeker.baseorm.crud;

import com.moseeker.thrift.gen.common.struct.Select;

/**
 * Created by zhangdi on 2017/3/22.
 */
public class QueryCreator {

    public static QuerySelect select() {
        return new QuerySelect().select();
    }

    public static QuerySelect select(String... fields) {
        return new QuerySelect().select(fields);
    }

    public static QuerySelect select(Select... selects) {
        return new QuerySelect().select(selects);
    }

    public static QueryCondition where(QueryCondition queryCondition) {
        return new QuerySelect().select().where(queryCondition);
    }

    public static CommonCondition where(CommonCondition commonCondition) {
        return new CommonSelect().select().where(commonCondition);
    }
}
