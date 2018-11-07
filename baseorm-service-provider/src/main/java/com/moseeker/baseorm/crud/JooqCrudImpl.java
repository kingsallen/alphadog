package com.moseeker.baseorm.crud;

import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.Update;
import org.jooq.*;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by zhangdi on 2017/3/20.
 */
public class JooqCrudImpl<S, R extends UpdatableRecord<R>> extends Crud<S, R> {

    @Autowired
    protected DefaultDSLContext create;

    protected TableImpl<R> table;

    public JooqCrudImpl(TableImpl<R> table, Class<S> sClass) {
        super(sClass);
        this.table = table;
        if (table == null) {
            throw new NullPointerException("table can not be null!");
        }
    }

    public R dataToRecord(Object s) {
        return BeanUtils.structToDB(s, table.getRecordType());
    }

    /**
     * do文件全部更新到record
     * @param s
     * @return
     */
    public R dataToRecordAll(Object s) {
        return BeanUtils.structToDBAll(s, table.getRecordType());
    }

    public S recordToData(R r) {
        return BeanUtils.DBToStruct(sClass, r);
    }

    @Override
    public <T> T recordToData(R r, Class<T> tClass) {
        return BeanUtils.DBToStruct(tClass, r);
    }

    @Override
    public R addRecord(R r) {
        create.execute("set names utf8mb4");
        create.attach(r);
        r.insert();
        return r;
    }

    @Override
    public List<R> addAllRecord(List<R> rs) {

        create.execute("set names utf8mb4");
        create.attach(rs);
        create.batchInsert(rs).execute();
        return rs;
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
        return create.deleteFrom(table).where(new LocalCondition<>(table).parseConditionUtil(conditions)).execute();
    }

    @Override
    public int updateRecord(R r) {
        create.execute("set names utf8mb4");
        create.attach(r);
        return create.executeUpdate(r);
    }

    @Override
    public int[] updateRecords(List<R> rs) {
        create.execute("set names utf8mb4");
        return create.batchUpdate(rs).execute();
    }

    @Override
    public int update(Update update) {
        create.execute("set names utf8mb4");
        UpdateSetFirstStep updateSetFirstStep = create.update(table);
        UpdateSetMoreStep updateSetMoreStep = null;
        for (String field : update.getFieldValues().keySet()) {
            Field<?> f = table.field(field);
            if (field != null) {
                updateSetMoreStep = updateSetFirstStep.set(f, (Object) BeanUtils.convertTo(update.getFieldValues().get(field), f.getType()));
            }
        }
        return updateSetMoreStep.where(new LocalCondition<>(table).parseConditionUtil(update.getConditions())).execute();
    }

    @Override
    public R getRecord(Query query) {
        return new LocalQuery<>(create, table, query).convertToOneResult().fetchAnyInto(table.getRecordType());
    }

    @Override
    public List<R> getRecords(Query query) {
        return new LocalQuery<>(create, table, query).convertToResultLimit().fetchInto(table.getRecordType());
    }

    @Override
    public int getCount(Query query) {
        return new LocalQuery<>(create, table, query).convertForCount().fetchOne().value1();
    }


    public Map<String,Object> getMap(Query query) {
        Map<String,Object> map= new LocalQuery<>(create, table, query).convertToOneResult().fetchAnyMap();
        if(map==null||map.isEmpty()){
            return null;
        }
        for(String key:map.keySet()){
            if(key.contains("_time")&&map.get(key) instanceof Date){
                String time=map.get(key).toString();
                if(time.length()>19){
                    time=time.substring(0,19);
                }
                map.put(key,time);
            }
        }
        return map;
    }

    public List<Map<String,Object>> getMaps(Query query) {
        List<Map<String,Object>> list=new LocalQuery<>(create, table, query).convertToResultLimit().fetchMaps();
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        for(Map<String,Object> map:list){
            for(String key:map.keySet()){
                if(key.contains("_time")&&map.get(key) instanceof Date){
                    String time=map.get(key).toString();
                    if(time.length()>19){
                        time=time.substring(0,19);
                    }
                    map.put(key,time);
                }
            }
        }
        return list;
    }
}