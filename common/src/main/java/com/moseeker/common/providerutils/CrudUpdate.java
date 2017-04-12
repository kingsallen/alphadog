package com.moseeker.common.providerutils;

import com.moseeker.thrift.gen.common.struct.CommonUpdate;

import java.util.List;

/**
 * Created by zhangdi on 2017/3/20.
 * 提供给其他dao或者本dao操作的dao接口
 * 其他dao调用该接口方法将自己的DSLContext传入方法，以实现多表操作的事物和回滚
 */
interface CrudUpdate<S, R> {

    int update(CommonUpdate commonUpdate);

    int updateData(S s);

    int updateRecord(R r);

    int[] updateDatas(List<S> s);

    int[] updateRecords(List<R> rs);

}
