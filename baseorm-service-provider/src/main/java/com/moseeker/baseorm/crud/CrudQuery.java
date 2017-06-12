package com.moseeker.baseorm.crud;

import com.moseeker.common.util.query.Query;

import java.util.List;

/**
 * Created by zhangdi on 2017/3/20.
 * 提供给其他dao或者本dao操作的dao接口
 * 其他dao调用该接口方法将自己的DSLContext传入方法，以实现多表操作的事物和回滚
 */
interface CrudQuery<R> {
    <T> T getData(Query query, Class<T> sClass);

    <T> List<T> getDatas(Query query, Class<T> sClass);

    R getRecord(Query query);

    List<R> getRecords(Query query);

    int getCount(Query query);

}
