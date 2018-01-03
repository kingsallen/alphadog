package com.moseeker.baseorm.tool;

import com.moseeker.baseorm.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.MyCollectors;
import com.moseeker.common.util.StringUtils;
import org.apache.log4j.Logger;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.TableRecord;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * jooq record 工具
 * Created by jack on 19/12/2017.
 */
public class RecordTool {

    private static Logger logger = Logger.getLogger(RecordTool.class);


    /**
     * 合并两条数据。将desc中更改的属性，根据属性名称一致作为条件，给orig对象对应的属性赋值
     * @param orig 被更新的对象
     * @param dest 值对象
     * @return 被更新的对象
     */
    @SuppressWarnings("rawtypes")
    public static <R extends Record> void recordToRecord(R dest, R orig) {
        if (dest == null || orig == null) {
            return;
        }

        Map<String, Field> changedFieldList = Arrays.stream(orig.fields())
                .filter(f -> orig.changed(f))
                .collect(Collectors.toMap(k -> k.getName(), v->v, (oldKey, newKey) -> newKey));

        if (changedFieldList != null && changedFieldList.size() > 0) {
            Map<String, Method> oriGetMeths = Arrays.asList(orig.getClass().getMethods()).stream().filter(f -> !f.getName().equals("getClass")).filter(f -> (f.getName().startsWith("get") || f.getName().startsWith("is")) && f.getParameterTypes().length == 0).collect(Collectors.toMap(k -> k.getName(), v -> v, (oldKey, newKey) -> newKey));

            Map<String, Method> destMap = Arrays.asList(dest.getClass().getMethods()).stream().filter(f -> f.getName().length() > 3 && f.getName().startsWith("set")).collect(Collectors.toMap(k -> k.getName(), v -> v, (oldKey, newKey) -> newKey));

            changedFieldList.forEach((fieldName, field) -> {
                String getKey;
                String subKey = Arrays.stream(fieldName.split("_"))
                        .map(fieldNameSub -> fieldNameSub.substring(0, 1).toUpperCase()+fieldNameSub.substring(1))
                        .collect(Collectors.joining());
                String setKey = "set" + subKey;
                if (field.getType().isAssignableFrom(Boolean.TYPE)) {
                    getKey = "is"+subKey;
                } else {
                    getKey = "get"+subKey;
                }
                if (oriGetMeths.containsKey(getKey) && destMap.containsKey(setKey)) {
                    try {
                        Object object = BeanUtils.convertTo(oriGetMeths.get(getKey).invoke(orig, new Object[]{}),
                                destMap.get(setKey).getParameterTypes()[0]);
                        destMap.get(setKey).invoke(dest, object);
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(), e);
                    } catch (InvocationTargetException e) {
                        logger.error(e.getMessage(), e);
                    }
                }

            });
        }

    }
}
