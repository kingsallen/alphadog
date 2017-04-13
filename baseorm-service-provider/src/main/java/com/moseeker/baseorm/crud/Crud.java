package com.moseeker.baseorm.crud;

import com.moseeker.thrift.gen.common.struct.CommonQuery;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhangdi on 2017/3/20.
 * 提供给其他dao或者本dao操作的dao接口
 * 其他dao调用该接口方法将自己的DSLContext传入方法，以实现多表操作的事物和回滚
 */
public abstract class Crud<S, R> implements CrudAdd<R>, CrudDelete<R>, CrudUpdate<R>, CrudQuery<R> {

    protected Class<S> sClass;

    public Crud(Class<S> sClass) {
        this.sClass = sClass;
    }

    public abstract R dataToRecord(S s);

    public abstract S recordToData(R r);

    public int addData(S s) {
        return addRecord(dataToRecord(s));
    }

    public int[] addAllData(List<S> ss) {
        return addAllRecord(ss.stream().map(data -> dataToRecord(data)).collect(Collectors.toList()));
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

    public List<S> getDatas(CommonQuery query) {
        return getDatas(query, sClass);
    }

    public S getData(CommonQuery query) {
        return getData(query, sClass);
    }
}
