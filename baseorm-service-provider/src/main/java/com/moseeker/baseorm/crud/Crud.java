package com.moseeker.baseorm.crud;

import com.moseeker.common.util.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhangdi on 2017/3/20.
 * 提供给其他dao或者本dao操作的dao接口
 * 其他dao调用该接口方法将自己的DSLContext传入方法，以实现多表操作的事物和回滚
 */
public abstract class Crud<S, R> implements CrudAdd<R>, CrudDelete<R>, CrudUpdate<R>, CrudQuery<R> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Class<S> sClass;
    protected Class<R> rClass;

    public Crud(Class<S> sClass) {
        Type t = this.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) t).getActualTypeArguments();
        this.sClass = sClass;
        this.rClass = (Class<R>) params[1];
    }

    public abstract R dataToRecord(Object s);

    public abstract S recordToData(R r);

    public abstract <T> T recordToData(R r, Class<T> tClass);

    public S addData(S s) {
        R r = dataToRecord(s);
        this.addRecord(r);
        return recordToData(r);
    }

    public List<S> addAllData(List<S> ss) {
        List<R> rList = addAllRecord(ss.stream().map(data -> dataToRecord(data)).collect(Collectors.toList()));
        if (rList != null && rList.size() > 0) {
            return rList.stream().map(r -> recordToData(r)).collect(Collectors.toList());
        }
        return null;
    }

    public int deleteData(S s) {
        return deleteRecord(dataToRecord(s));
    }

    public int[] deleteDatas(List<S> s) {
        List<R> records = s.stream().map(data -> dataToRecord(data)).collect(Collectors.toList());
        return deleteRecords(records);
    }

    public int updateData(S s) {
        return updateRecord(dataToRecord(s));
    }

    public int[] updateDatas(List<S> ss) {
        return updateRecords(ss.stream().map(data -> dataToRecord(data)).collect(Collectors.toList()));
    }

    public List<S> getDatas(Query query) {
        return getDatas(query, sClass);
    }

    public S getData(Query query) {
        return getData(query, sClass);
    }

    @Override
    public <T> T getData(Query query, Class<T> sClass) {
        R r = getRecord(query);
        if (r != null) {
            return recordToData(r, sClass);
        } else {
            return null;
        }
    }

    @Override
    public <T> List<T> getDatas(Query query, Class<T> sClass) {
        List<R> rs = getRecords(query);

        if (rs == null) {
            return new ArrayList<>();
        }

        List<T> ts = new ArrayList<>();
        for (R r : rs) {
            ts.add(recordToData(r, sClass));
        }
        return ts;
    }
}
