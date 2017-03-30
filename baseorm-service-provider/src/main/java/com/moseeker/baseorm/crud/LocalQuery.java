package com.moseeker.baseorm.crud;

import com.moseeker.common.exception.OrmException;
import com.moseeker.thrift.gen.common.struct.*;
import com.moseeker.thrift.gen.common.struct.Select;
import org.jooq.*;
import org.jooq.SelectField;
import org.jooq.impl.TableImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhangdi on 2017/3/17.
 * CommonQuery包装类，实现CommonQuery和jooq语法对接
 */
public class LocalQuery<R extends Record> extends CommonQuery {
    DSLContext create;
    TableImpl table;
    CommonQuery commonQuery;
    LocalCondition<R> localCondition;


    public LocalQuery(DSLContext create, TableImpl<R> table, CommonQuery commonQuery) {
        this.create = create;
        this.table = table;
        this.localCondition = new LocalCondition<R>(table);
        this.commonQuery = commonQuery;
        if (create == null) {
            throw new NullPointerException();
        }
        if (table == null) {
            throw new NullPointerException();
        } else if (commonQuery == null) {
            this.commonQuery = new CommonQuery();
        }
    }

    @Override
    public int getPage() {
        return commonQuery.getPage() > 0 ? commonQuery.getPage() : 1;
    }

    @Override
    public int getPageSize() {
        return commonQuery.getPageSize() > 0 ? commonQuery.getPageSize() : 10;
    }

    @Override
    public List<Select> getAttributes() {
        return commonQuery.getAttributes() == null ? new ArrayList<>() : commonQuery.getAttributes();
    }

    @Override
    public com.moseeker.thrift.gen.common.struct.Condition getConditions() {
        return commonQuery.getConditions();
    }

    @Override
    public List<OrderBy> getOrders() {
        return commonQuery.getOrders() == null ? new ArrayList<>() : commonQuery.getOrders();
    }

    @Override
    public List<String> getGroups() {
        return commonQuery.getGroups() == null ? new ArrayList<>() : commonQuery.getGroups();
    }

    public Collection<? extends SelectField<?>> buildSelect() {
        return getAttributes().stream()
                .map(select -> {
                    Field<?> field = table.field(select.field);
                    if (field == null) {
                        throw new OrmException("field '" + select.field + "' not found in table " + table.getName());
                    } else {
                        switch (select.getOp()) {
                            case AVG:
                                return field.avg().as(select.field + "_avg");
                            case COUNT:
                                return field.count().as(select.field + "_count");
                            case COUNT_DISTINCT:
                                return field.countDistinct().as(select.field + "_count_distinct");
                            case TRIM:
                                return field.trim().as(select.field + "_trim");
                            case LCASE:
                                return field.lower().as(select.field + "_lower");
                            case LEN:
                                return field.length().as(select.field + "_length");
                            case MAX:
                                return field.max().as(select.field + "_max");
                            case MIN:
                                return field.min().as(select.field + "_min");
                            case ROUND:
                                return field.round().as(select.field + "_round");
                            case SUM:
                                return field.sum().as(select.field + "_sum");
                            case UCASE:
                                return field.upper().as(select.field + "_ucase");
                            default:
                                return field;
                        }
                    }
                })
                .collect(Collectors.toSet());
    }

    public Collection<? extends Field<?>> buildGroup() {
        return getAttributes().stream()
                .map(groupField -> {
                    Field<?> field = table.field(groupField.field);
                    if (field == null) {
                        throw new OrmException("field '" + groupField.field + "' not found in table " + table.getName());
                    } else {
                        return field;
                    }
                })
                .collect(Collectors.toSet());
    }

    /**
     * 所有Condition组装
     *
     * @return
     */
    public org.jooq.Condition buildConditions() {
<<<<<<< Updated upstream
        return localCondition.convertCondition(getConditions());
=======
        return convertCondition(getConditions());
>>>>>>> Stashed changes
    }

    /**
     * 所有Order的组装
     *
     * @return
     */
    public Collection<? extends SortField<?>> buildOrder() {
        return getOrders().stream()
                .map(orderBy -> {
                    Field<?> field = table.field(orderBy.field);
                    if (field == null) {
                        throw new OrmException("field '" + orderBy.field + "' not found in table " + table.getName());
                    } else {
                        switch (orderBy.getOrder()) {
                            case DESC:
                                return field.desc();
                            default:
                                return field.asc();
                        }
                    }
                })
                .collect(Collectors.toSet());
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
