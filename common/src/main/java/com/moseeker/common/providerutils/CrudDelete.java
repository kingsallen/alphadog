package com.moseeker.common.providerutils;

import com.moseeker.thrift.gen.common.struct.Condition;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by zhangdi on 2017/3/20.
 * 提供给其他dao或者本dao操作的dao接口
 * 其他dao调用该接口方法将自己的DSLContext传入方法，以实现多表操作的事物和回滚
 */
interface CrudDelete<S, R> {

    int delete(Condition conditions) throws SQLException;

    int deleteData(S s) throws SQLException;

    int deleteRecord(R r) throws SQLException;

    int[] deleteDatas(List<S> s) throws SQLException;

    int[] deleteRecords(List<R> rs) throws SQLException;
}
