package com.moseeker.baseorm.crud;

import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.CommonUpdate;
import com.moseeker.thrift.gen.common.struct.Condition;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangdi on 2017/3/20.
 * 提供给其他dao或者本dao操作的dao接口
 * 其他dao调用该接口方法将自己的DSLContext传入方法，以实现多表操作的事物和回滚
 */
interface CrudUpdate<S, R> {

    int update(CommonUpdate commonUpdate) throws SQLException;

    int updateData(S s) throws SQLException;

    int updateRecord(R r) throws SQLException;

    int[] updateDatas(List<S> s) throws SQLException;

    int[] updateRecords(List<R> rs) throws SQLException;

}
