package com.moseeker.baseorm.crud;

import com.moseeker.thrift.gen.common.struct.CommonQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by moseeker on 2017/3/28.
 */
public class QueryDao {
    CommonQuery commonQuery;

    public CommonQuery getCommonQuery() {
        return commonQuery;
    }

    public <S, R> S getData(CrudQuery<S, R> dao) throws SQLException {
        return dao.getData(commonQuery);
    }

    public <S, R, T> T getData(CrudQuery<S, R> dao, Class<T> tClass) throws SQLException {
        return dao.getData(commonQuery, tClass);
    }

    public <S, R, T> List<S> getDatas(CrudQuery<S, R> dao) throws SQLException {
        return dao.getDatas(commonQuery);
    }

    public <S, R, T> List<T> getDatas(CrudQuery<S, R> dao, Class<T> tClass) throws SQLException {
        return dao.getDatas(commonQuery, tClass);
    }

    public <S,R> R getRecord(CrudQuery<S, R> dao) throws SQLException{
        return dao.getRecord(commonQuery);
    }

    public <S,R> List<R> getRecords(CrudQuery<S, R> dao) throws SQLException{
        return dao.getRecords(commonQuery);
    }

    public <S,R> List<Map<String, Object>> getMaps(CrudQuery<S, R> dao) throws SQLException{
        return dao.getMaps(commonQuery);
    }

    public <S,R> Map<String, Object> getMap(CrudQuery<S, R> dao) throws SQLException{
        return dao.getMap(commonQuery);
    }

    public <S,R> int getCount(CrudQuery<S, R> dao) throws SQLException{
        return dao.getCount(commonQuery);
    }
}
