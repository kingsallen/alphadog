package com.moseeker.baseorm.crud;

<<<<<<< Updated upstream
=======
import com.moseeker.thrift.gen.common.struct.*;
import com.moseeker.thrift.gen.common.struct.Condition;
import org.jooq.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

>>>>>>> Stashed changes
/**
 * Created by zhangdi on 2017/3/20.
 * 提供给其他dao或者本dao操作的dao接口
 * 其他dao调用该接口方法将自己的DSLContext传入方法，以实现多表操作的事物和回滚
 */
<<<<<<< Updated upstream
interface Crud<S, R> extends CrudAdd<S,R>,CrudDelete<S,R>,CrudUpdate<S,R>,CrudQuery<S,R>{
=======
interface Crud<S, R extends UpdatableRecord<R>> {

    Connection newConnection() throws SQLException;

    int add(Connection conn, Map<String, String> fieldValues);

    int addData(Connection conn, S s);

    int addRecord(Connection conn, R r);

    int[] addAll(Connection conn, List<Map<String, String>> fieldValuesList);

    int[] addAllData(Connection conn, List<S> ss);

    int[] addAllRecord(Connection conn, List<R> rs);

    int delete(Connection conn, Condition conditions);

    int deleteData(Connection conn, S s);

    int deleteRecord(Connection conn, R r);

    int[] deleteDatas(Connection conn, List<S> s);

    int[] deleteRecords(Connection conn, List<R> rs);

    int delete(Connection conn, CommonQuery query);

    int update(Connection conn, CommonUpdate commonUpdate);

    int updateData(Connection conn, S s);

    int updateRecord(Connection conn, R r);

    int[] updateDatas(Connection conn, List<S> s);

    int[] updateRecords(Connection conn, List<R> rs);

    S getData(Connection conn, CommonQuery query, Class<S> sClass);

    List<S> getDatas(Connection conn, CommonQuery query, Class<S> sClass);

    R getRecord(Connection conn, CommonQuery query);

    List<R> getRecords(Connection conn, CommonQuery query);

    List<Map<String, Object>> getMaps(Connection conn, CommonQuery query);

    Map<String, Object> getMap(Connection conn, CommonQuery query);

    int getCount(Connection conn, CommonQuery query);

    int add(Map<String, String> fieldValues) throws SQLException;

    int addData(S s) throws SQLException;

    int addRecord(R r) throws SQLException;

    int[] addAll(List<Map<String, String>> fieldValuesList) throws SQLException;

    int[] addAllData(List<S> ss) throws SQLException;

    int[] addAllRecord(List<R> rs) throws SQLException;

    int delete(Condition conditions) throws SQLException;

    int deleteData(S s) throws SQLException;

    int deleteRecord(R r) throws SQLException;

    int[] deleteDatas(List<S> s) throws SQLException;

    int[] deleteRecords(List<R> rs) throws SQLException;

    int delete(CommonQuery query) throws SQLException;

    int update(CommonUpdate commonUpdate) throws SQLException;

    int updateData(S s) throws SQLException;

    int updateRecord(R r) throws SQLException;

    int[] updateDatas(List<S> s) throws SQLException;

    int[] updateRecords(List<R> rs) throws SQLException;

    S getData(CommonQuery query, Class<S> sClass) throws SQLException;

    List<S> getDatas(CommonQuery query, Class<S> sClass) throws SQLException;

    R getRecord(CommonQuery query) throws SQLException;

    List<R> getRecords(CommonQuery query) throws SQLException;

    List<Map<String, Object>> getMaps(CommonQuery query) throws SQLException;

    Map<String, Object> getMap(CommonQuery query) throws SQLException;
>>>>>>> Stashed changes

    int getCount(CommonQuery query) throws SQLException;

}
