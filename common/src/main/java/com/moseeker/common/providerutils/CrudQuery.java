package com.moseeker.common.providerutils;

import com.moseeker.thrift.gen.common.struct.CommonQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangdi on 2017/3/20.
 * 提供给其他dao或者本dao操作的dao接口
 * 其他dao调用该接口方法将自己的DSLContext传入方法，以实现多表操作的事物和回滚
 */
interface CrudQuery<S, R> {
    <T> T getData(CommonQuery query, Class<T> sClass);

    S getData(CommonQuery query);

    <T> List<T> getDatas(CommonQuery query, Class<T> sClass);

    List<S> getDatas(CommonQuery query);

    R getRecord(CommonQuery query);

    List<R> getRecords(CommonQuery query);

    List<Map<String, Object>> getMaps(CommonQuery query);

    Map<String, Object> getMap(CommonQuery query);

    int getCount(CommonQuery query);

}
