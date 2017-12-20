package com.moseeker.baseorm.tool;

import com.moseeker.baseorm.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.MyCollectors;
import com.moseeker.common.util.StringUtils;
import org.apache.log4j.Logger;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.TableRecord;

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
     * struct 类和JOOQ类的属性和方法固定，可以预先加载成静态的属性和方法
     *
     * @param dest struct 对象
     * @param orig record 对象
     */
    @SuppressWarnings("rawtypes")
    public static <R extends Record> void recordToRecord(R dest, R orig) {
        if (dest == null || orig == null) {
            return;
        }

        // 将strut对象的属性名和get方法提取成map
        Map<String, Method> destGetMeths = Arrays.asList(dest.getClass().getMethods()).stream().filter(f -> (f.getName().startsWith("get") || f.getName().startsWith("is")) && f.getParameterTypes().length == 0).collect(Collectors.toMap(k -> k.getName(), v -> v, (oldKey, newKey) -> newKey));
        Map<String, Method> destMap = Arrays.asList(dest.getClass().getFields()).stream().filter(f -> !f.getName().equals("metaDataMap")).collect(MyCollectors.toMap(k -> StringUtils.humpName(k.getName()), v -> {
            String fileName = v.getName().substring(0, 1).toUpperCase() + v.getName().substring(1);
            Method isSetMethod;
            try {
                isSetMethod = dest.getClass().getMethod("isSet" + fileName, new Class[]{});
                if ((Boolean) isSetMethod.invoke(dest, new Object[]{})) {
                    if (destGetMeths.containsKey("get".concat(fileName))) {
                        return destGetMeths.get("get".concat(fileName));
                    } else if (v.getType().isAssignableFrom(boolean.class)
                            && destGetMeths.containsKey("is".concat(fileName))) {
                        return destGetMeths.get("is".concat(fileName));
                    } else {
                        return null;
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            return null;
        }));

        // 将DO对象的属性名和set方法提取成map
        Map<String, Method> origMap = Arrays.asList(orig.getClass().getMethods()).stream().filter(f -> f.getName().length() > 3 && f.getName().startsWith("set")).collect(Collectors.toMap(k -> {
            String fileName = k.getName().substring(3, 4).toLowerCase() + k.getName().substring(4);
            return StringUtils.humpName(fileName);
        }, v -> v, (oldKey, newKey) -> newKey));

        // 赋值
        Set<String> origKey = origMap.keySet();
        for (String field : origKey) {
            if (destMap.containsKey(field) && origMap.get(field) != null && destMap.get(field) != null) {
                Object object;
                try {
                    object = BeanUtils.convertTo(destMap.get(field).invoke(dest, new Object[]{}),
                            origMap.get(field).getParameterTypes()[0]);
                    origMap.get(field).invoke(orig, object);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

    }

    /**
     * 合并两条数据。将desc中更改的属性，根据属性名称一致作为条件，给orig对象对应的属性赋值
     * @param orig 被更新的对象
     * @param dest 值对象
     * @return 被更新的对象
     */
    public static TableRecord mergeRecord(TableRecord orig, TableRecord dest) {
        List<Field<?>> changedFieldList = Arrays.stream(dest.fields())
                .filter(f -> dest.changed(f))
                .collect(Collectors.toList());
        if (changedFieldList != null && changedFieldList.size() > 0) {
            changedFieldList.forEach((Field field) -> {
                if (orig.field(field.getName()) != null) {

                }
            });
        }
        return orig;
    }

    public static void main(String[] args) {
        ProfileCredentialsRecord profileCredentialsRecord = new ProfileCredentialsRecord();
        profileCredentialsRecord.setId(1);
        profileCredentialsRecord.setName("test");

        ProfileCredentialsRecord profileCredentialsRecord1 = new ProfileCredentialsRecord();
        profileCredentialsRecord1.setName("test");
        profileCredentialsRecord1.setCode("1111");
        profileCredentialsRecord1.setOrganization("test");
        profileCredentialsRecord1.set(profileCredentialsRecord1.field1(), 1);
        mergeRecord(profileCredentialsRecord, profileCredentialsRecord1);
        System.out.println(profileCredentialsRecord);
    }
}
