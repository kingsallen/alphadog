package com.moseeker.baseorm.crud;

import com.moseeker.common.exception.OrmException;
import com.moseeker.common.util.query.Query;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by zhangdi on 2017/3/17.
 * CommonQuery包装类，实现CommonQuery和jooq语法对接
 */
public class LocalQuery<R extends Record> {
    DSLContext create;
    TableImpl table;
    Query query;
    LocalCondition<R> localCondition;


    public LocalQuery(DSLContext create, TableImpl<R> table, Query query) {
        this.create = create;
        this.table = table;
        this.localCondition = new LocalCondition<R>(table);
        this.query = query;
        if (create == null) {
            throw new NullPointerException();
        }
        if (table == null) {
            throw new NullPointerException();
        } else if (query == null) {
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            this.query = queryBuilder.buildQuery();
        }
    }

    public int getPage() {
        return query.getPageNum() > 0 ? query.getPageNum() : 1;
    }

    public int getPageSize() {
        return query.getPageSize() > 0 ? query.getPageSize() : 10;
    }

    public Collection<? extends SelectField<?>> buildSelect() {
        return query.getAttributes().stream()
                .map(select -> {
                    Field<?> field = table.field(select.getField());
                    if (field == null) {
                        throw new OrmException("field '" + select.getField() + "' not found in table " + table.getName());
                    } else {
                        switch (select.getSelectOp()) {
                            case AVG:
                                return field.avg().as(select.getField() + "_avg");
                            case COUNT:
                                return field.count().as(select.getField() + "_count");
                            case COUNT_DISTINCT:
                                return field.countDistinct().as(select.getField() + "_count_distinct");
                            case TRIM:
                                return field.trim().as(select.getField() + "_trim");
                            case LCASE:
                                return field.lower().as(select.getField() + "_lower");
                            case LEN:
                                return field.length().as(select.getField() + "_length");
                            case MAX:
                                return field.max().as(select.getField() + "_max");
                            case MIN:
                                return field.min().as(select.getField() + "_min");
                            case ROUND:
                                return field.round().as(select.getField() + "_round");
                            case SUM:
                                return field.sum().as(select.getField() + "_sum");
                            case UCASE:
                                return field.upper().as(select.getField() + "_ucase");
                            default:
                                return field;
                        }
                    }
                })
                .collect(Collectors.toList());
    }

    public Collection<? extends Field<?>> buildGroup() {
        return query.getGroups().stream()
                .map(groupField -> {
                    Field<?> field = table.field(groupField);
                    if (field == null) {
                        throw new OrmException("field '" + groupField + "' not found in table " + table.getName());
                    } else {
                        return field;
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * 所有Condition组装
     *
     * @return
     */
    public org.jooq.Condition buildConditions() {
        return localCondition.parseConditionUtil(query.getConditions());
    }

    /**
     * 所有Order的组装
     *
     * @return
     */
    public Collection<? extends SortField<?>> buildOrder() {
        return query.getOrders().stream()
                .map(orderBy -> {
                    Field<?> field = table.field(orderBy.getField());
                    if (field == null) {
                        throw new OrmException("field '" + orderBy.getField() + "' not found in table " + table.getName());
                    } else {
                        switch (orderBy.getOrder()) {
                            case DESC:
                                return field.desc();
                            default:
                                return field.asc();
                        }
                    }
                })
                .collect(Collectors.toList());
    }

    public SelectConditionStep convertToSelect() {
        return create
                .select(buildSelect())
                .from(table)
                .where(buildConditions());
    }

    public ResultQuery<R> convertToResultQuery() {
        return convertToSelect()
                .groupBy(buildGroup())
                .orderBy(buildOrder())
                .limit((getPage() - 1) * getPageSize(), getPageSize());
    }
}
