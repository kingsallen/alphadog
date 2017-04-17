package com.moseeker.baseorm.crud;

import com.moseeker.common.util.BeanUtils;
import org.jooq.Field;
import org.jooq.impl.TableImpl;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by moseeker on 2017/3/23.
 */
public class FieldUtils {


    public static AbstractMap.SimpleEntry<Collection<Field<?>>, Collection<?>> convertInsertFieldMap(TableImpl<?> table, Map<String, String> filedValues) {
        List<Field<?>> fields = new ArrayList<>();
        List<?> values = new ArrayList<>();
        for (String field : filedValues.keySet()) {
            Field<?> f = table.field(field);
            if (f != null) {
                fields.add(f);
                values.add(BeanUtils.convertTo(filedValues.get(field), f.getType()));
            }
        }
        return new AbstractMap.SimpleEntry<>(fields, values);
    }


}
