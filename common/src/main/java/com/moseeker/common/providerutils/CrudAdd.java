package com.moseeker.common.providerutils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangdi on 2017/3/20.
 * 提供给其他dao或者本dao操作的dao接口
 * 其他dao调用该接口方法将自己的DSLContext传入方法，以实现多表操作的事物和回滚
 */
interface CrudAdd<S, R> {

    int addData(S s) throws SQLException;

    int addRecord(R r) throws SQLException;

    int[] addAll(List<Map<String, String>> fieldValuesList) throws SQLException;

    int[] addAllData(List<S> ss) throws SQLException;

    int[] addAllRecord(List<R> rs) throws SQLException;

    int add(Map<String, String> fieldValues) throws SQLException;
}
