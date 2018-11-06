package com.moseeker.baseorm.crud;

import com.moseeker.baseorm.exception.CoditionException;
import com.moseeker.baseorm.exception.ExceptionCategory;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SelectField;
import org.jooq.SelectJoinStep;
import org.jooq.SortField;
import org.jooq.impl.TableImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by zhangdi on 2017/3/17.
 * CommonQuery包装类，实现CommonQuery和jooq语法对接
 */
class LocalQuery<R extends Record> {

    Logger logger = LoggerFactory.getLogger(LocalQuery.class);

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

    /**
     * 获取页码。
     * 如果大于0，则返回页码，否则返回 1
     *
     * @return 当前有效地页码
     */
    public int getPage() {
        return query.getPageNum() > 0 ? query.getPageNum() : 1;
    }

    /**
     * 返回每页显示的信息数量
     * 如果大于0，则返回当前的每页显示的数量；否则返回10
     */
    public int getPageSize() {
        return query.getPageSize() > 0 ? query.getPageSize() : 10;
    }

    public Collection<? extends SelectField<?>> buildSelect() {
        if (query != null && query.getAttributes() != null) {
            return query.getAttributes().stream()
                    .map(select -> {
                        Field<?> field = table.field(select.getField());
                        if (field == null) {
                            logger.warn("field {},not found in table {}", select.getField(), table.getName());
                            throw CoditionException.SELECT_FIELD_NOEXIST.setMess("查询的" + table.getName() + "表中" + select.getField() + "字段不存在");
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
                    .filter(field -> field != null)
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    /**
     * 生成group条件
     */
    public Collection<? extends Field<?>> buildGroup() {
        if (query != null && query.getGroups() != null) {
            return query.getGroups().stream()
                    .map(groupField -> {
                        Field<?> field = table.field(groupField);
                        if (field == null) {
                            logger.warn("field {},not found in table {}", groupField, table.getName());
                            throw CoditionException.GROUPBY_FIELD_NOEXIST.setMess("查询的" + table.getName() + "表按" + groupField + "分组的字段不存在");
                        } else {
                            return field;
                        }
                    })
                    .filter(filed -> filed != null)
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    /**
     * 所有Condition组装
     */
    public org.jooq.Condition buildConditions() {
        return localCondition.parseConditionUtil(query.getConditions());
    }

    /**
     * 所有Order的组装
     */
    public Collection<? extends SortField<?>> buildOrder() {
        if (query != null && query.getOrders() != null) {
            return query.getOrders().stream()
                    .filter(orderBy -> orderBy != null)
                    .map(orderBy -> {
                        Field<?> field = table.field(orderBy.getField());
                        if (field == null) {
                            logger.warn("field {},not found in table {}", orderBy.getField(), table.getName());
                            throw CoditionException.ORDER_FIELD_NOEXIST.setMess("查询的" + table.getName() + "表按" + orderBy.getField() + "排序字段不存在");
                        } else {
                            switch (orderBy.getOrder()) {
                                case DESC:
                                    return field.desc();
                                default:
                                    return field.asc();
                            }
                        }
                    })
                    .filter(filed -> filed != null)
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    /**
     * 返回解析的查询条件。
     * 该条件过滤了order条件和limit条件
     */
    public SelectJoinStep<Record1<Integer>> convertForCount() {
        SelectJoinStep<Record1<Integer>> select = create.selectCount().from(table);
        org.jooq.Condition condition = buildConditions();
        if (condition != null) {
            select.where(condition);
        }
        Collection<? extends Field<?>> groups = buildGroup();
        if (groups != null && groups.size() > 0) {
            select.groupBy(groups);
        }
        logger.debug(select.getSQL());
        return select;
    }

    /**
     * 组装除limit之外的所有查询字段和查询条件
     *
     * @return
     */
    public SelectJoinStep<Record> convertToResult() {
        SelectJoinStep<Record> select = null;
        Collection<? extends SelectField<?>> selectFields = buildSelect();
        if (selectFields != null && selectFields.size() > 0) {
            select = create.select(selectFields).from(table);
        } else {
            select = create.select().from(table);
        }
        org.jooq.Condition condition = buildConditions();
        if (condition != null) {
            select.where(condition);
        }
        Collection<? extends Field<?>> groups = buildGroup();
        if (groups != null && groups.size() > 0) {
            select.groupBy(groups);
        }
        Collection<? extends SortField<?>> orders = buildOrder();
        if (orders != null && orders.size() > 0) {
            select.orderBy(orders);
        }
        logger.debug(select.getSQL());
        return select;
    }

    /**
     * 组装limit
     * 返回解析的查询条件。解析条件包括查询的字段，过滤条件，分组条件，排序条件
     */
    public SelectJoinStep<Record> convertToResultLimit() {
        SelectJoinStep<Record> select = convertToResult();
        if (query.getPageSize() > 0) {
            select.limit((getPage() - 1) * getPageSize(), getPageSize());
        }
        logger.info("convertToResultLimit：{}"+select.getSQL());
        return select;
    }

    /**
     * 组装只获取一条数据的select
     *
     * @return
     */
    public SelectJoinStep<Record> convertToOneResult() {
        SelectJoinStep<Record> select = convertToResult();
        select.limit(1);
        return select;
    }
}
