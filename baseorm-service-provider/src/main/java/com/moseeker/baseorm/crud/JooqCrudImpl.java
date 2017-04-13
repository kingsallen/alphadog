package com.moseeker.baseorm.crud;

import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.CommonUpdate;
import com.moseeker.thrift.gen.common.struct.Condition;
import org.jooq.Field;
import org.jooq.UpdatableRecord;
import org.jooq.UpdateSetFirstStep;
import org.jooq.UpdateSetMoreStep;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * Created by zhangdi on 2017/3/20.
 */
public class JooqCrudImpl<S, R extends UpdatableRecord<R>> extends Crud<S, R> {

    @Autowired
    DefaultDSLContext create;

    TableImpl<R> table;

    public JooqCrudImpl(TableImpl<R> table, Class<S> sClass) {
        super(sClass);
        this.table = table;
        if (table == null) {
            throw new NullPointerException("table can not be null!");
        }
    }

    public R dataToRecord(S s) {
        return BeanUtils.structToDB(s, table.getRecordType());
    }

    public S recordToData(R r){
        return r.into(sClass);
    }


    @Override
    public int addRecord(R r) {
        create.attach(r);
        return create.insertInto(table).set(r).execute();
    }

    @Override
    public int[] addAllRecord(List<R> rs) {
        create.attach(rs);
        return create.batchInsert(rs).execute();
    }

    @Override
    public int deleteRecord(R r) {
        return create.executeDelete(r);
    }

    @Override
    public int[] deleteRecords(List<R> rs) {
        return create.batchDelete(rs).execute();
    }

    @Override
    public int delete(Condition conditions) {
        return create.deleteFrom(table).where(new LocalCondition<>(table).convertCondition(conditions)).execute();
    }

    @Override
    public int updateRecord(R r) {
        create.attach(r);
        return create.executeUpdate(r);
    }

    @Override
    public int[] updateRecords(List<R> rs) {
        return create.batchUpdate(rs).execute();
    }

    @Override
    public int update(CommonUpdate commonUpdate) {
        UpdateSetFirstStep updateSetFirstStep = create.update(table);
        UpdateSetMoreStep updateSetMoreStep = null;
        for (String field : commonUpdate.getFieldValues().keySet()) {
            Field<?> f = table.field(field);
            if (field != null) {
                updateSetMoreStep = updateSetFirstStep.set(f, (Object) BeanUtils.convertTo(commonUpdate.getFieldValues().get(field), f.getType()));
            }
        }
        return updateSetMoreStep.where(new LocalCondition<>(table).convertCondition(commonUpdate.getConditions())).execute();
    }


    @Override
    public <T> T getData(CommonQuery query, Class<T> sClass) {
        return new LocalQuery<R>(create, table, query).convertToResultQuery().fetchOneInto(sClass);
    }

    @Override
    public <T> List<T> getDatas(CommonQuery query, Class<T> sClass) {
        return new LocalQuery<>(create, table, query).convertToResultQuery().fetchInto(sClass);
    }

    @Override
    public R getRecord(CommonQuery query) {
        return new LocalQuery<>(create, table, query).convertToResultQuery().fetchOne();
    }

    @Override
    public List<R> getRecords(CommonQuery query) {
        return new LocalQuery<>(create, table, query).convertToResultQuery().fetchInto(table.getRecordType());
    }

    @Override
    public int getCount(CommonQuery query) {
        return create.fetchCount(new LocalQuery<R>(create, table, query).convertToSelect());
    }
}