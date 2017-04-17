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

import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Created by zhangdi on 2017/3/20.
 */
public class JooqCrudImpl<S, R extends UpdatableRecord<R>> implements Crud<S, R> {

    @Autowired
    DefaultDSLContext create;

    TableImpl<R> table;
    Class<S> sClass;

    public JooqCrudImpl(TableImpl<R> table, Class<S> sClass) {
        this.table = table;
        this.sClass = sClass;
        if (table == null) {
            throw new NullPointerException("table can not be null!");
        }
    }

    private R getRecord(S s) {
        return BeanUtils.structToDB(s, table.getRecordType());
    }

    @Override
    public int add(Map<String, String> fieldValues) {
        AbstractMap.SimpleEntry<Collection<Field<?>>, Collection<?>> entry = FieldUtils.convertInsertFieldMap(table, fieldValues);
        return create.insertInto(table, entry.getKey()).values(entry.getValue()).execute();
    }

    @Override
    public int[] addAll(List<Map<String, String>> fieldValuesList) {
        int[] result = new int[fieldValuesList.size()];
        IntStream.range(0, fieldValuesList.size()).forEach(index -> {
            result[index] = add(fieldValuesList.get(index));
        });
        return result;
    }

    @Override
    public int addData(S s) {
        return addRecord(getRecord(s));
    }

    @Override
    public int addRecord(R r) {
        create.attach(r);
        return create.insertInto(table).set(r).execute();
    }

    @Override
    public int[] addAllData(List<S> ss) {
        return addAllRecord(ss.stream().map(data -> getRecord(data)).collect(Collectors.toList()));
    }

    @Override
    public int[] addAllRecord(List<R> rs) {
        create.attach(rs);
        return create.batchInsert(rs).execute();
    }

    @Override
    public int deleteData(S s) throws SQLException {
        return deleteRecord(getRecord(s));
    }

    @Override
    public int[] deleteDatas(List<S> s) {
        List<R> records = s.stream().map(data -> getRecord(data)).collect(Collectors.toList());
        return deleteRecords(records);
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
    public int updateData(S s) {
        return updateRecord(getRecord(s));
    }

    @Override
    public int[] updateDatas(List<S> ss) {
        return updateRecords(ss.stream().map(data -> getRecord(data)).collect(Collectors.toList()));
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
    public List<S> getDatas(CommonQuery query) throws SQLException {
        return getDatas(query, sClass);
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
    public S getData(CommonQuery query) throws SQLException {
        return getData(query, sClass);
    }

    @Override
    public int getCount(CommonQuery query) {
        return create.fetchCount(new LocalQuery<R>(create, table, query).convertToSelect());
    }

    @Override
    public List<Map<String, Object>> getMaps(CommonQuery query) {
        return new LocalQuery<>(create, table, query).convertToResultQuery().fetchMaps();
    }

    @Override
    public Map<String, Object> getMap(CommonQuery query) {
        return new LocalQuery<>(create, table, query).convertToResultQuery().fetchOneMap();
    }
}