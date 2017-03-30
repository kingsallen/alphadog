package com.moseeker.common.providerutils.daoutils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.moseeker.common.exception.OrmException;
import com.moseeker.thrift.gen.common.struct.*;
import org.jooq.*;
import org.jooq.Condition;
import org.jooq.SelectField;
import org.jooq.impl.TableImpl;
import org.jooq.impl.UpdatableRecordImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.StringUtils;

/**
 *
 * 实现通用数据操作接口的抽象类
 * <p>Company: MoSeeker</P>
 * <p>date: May 5, 2016</p>
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version Beta
 * @param <R> 表示JOOQ表记录的ORM类
 * @param <T> 表示JOOQ表的ORM类
 */
@SuppressWarnings("rawtypes")
public abstract class BaseDaoImpl<R extends UpdatableRecordImpl<R>, T extends TableImpl<R>>
<<<<<<< Updated upstream
        implements BaseDao<R> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private SelectSelectStep<Record> buildSelect(DSLContext create, CommonQuery query) {
        Set<SelectField<?>> fieldSet = Stream.of(query)
                .filter(query1 -> query1 != null)
                .flatMap(query1 -> Stream.of(query.getAttributes()))
                .filter(attributes -> attributes != null)
                .flatMap(attributes -> attributes.stream())
                .map(select -> {
                    Field<?> field = tableLike.field(select.field);
                    if (field == null) {
                        throw new OrmException("field '" + select.field + "' not found in table " + tableLike.getName());
                    } else {
                        return field;
                    }
                })
                .collect(Collectors.toSet());
        return create.select(fieldSet);
    }

    private Condition connectInnerCondition(InnerCondition innerCondition) {
        Condition c1 = convertCondition(innerCondition.getFirstCondition());
        Condition c2 = convertCondition(innerCondition.getSecondCondition());

        if (innerCondition.getConditionOp() == ConditionOp.AND) {
            return c1.and(c2);
        } else if (innerCondition.getConditionOp() == ConditionOp.OR) {
            return c1.or(c2);
        } else {
            throw new IllegalArgumentException("error condition");
        }
    }

    private <T> T convertTo(String value, Class<T> tClass) {
        return BeanUtils.convertTo(value, tClass);
    }

    private <E> Condition connectValueCondition(Field<E> field, String value, ValueOp valueOp) {
        switch (valueOp) {
            case EQ:
                return field.equal(convertTo(value, field.getType()));
            case NEQ:
                return field.notEqual(convertTo(value, field.getType()));
            case IN:
                if (value.startsWith("[") && value.endsWith("]")) {
                    List<E> list = BeanUtils.convertTo(value, field.getType());
                    return field.in(list);
                } else {
                    return null;
                }
            case NIN:
                if (value.startsWith("[") && value.endsWith("]")) {
                    List<E> list = BeanUtils.convertTo(value, field.getType());
                    return field.notIn(list);
                } else {
                    return null;
                }
            case GT:
                return field.greaterThan(convertTo(value, field.getType()));
            case GE:
                return field.greaterOrEqual(convertTo(value, field.getType()));
            case LT:
                return field.lessThan(convertTo(value, field.getType()));
            case LE:
                return field.lessOrEqual(convertTo(value, field.getType()));
            case BT:
                if (value.startsWith("[") && value.endsWith("]")) {
                    List<E> list = BeanUtils.convertTo(value, field.getType());
                    return field.in(list);
                } else {
                    return null;
                }
            case NBT:
            case LIKE:
            case NLIKE:
                return null;
            default:
                throw new IllegalArgumentException("error value constraint");
        }
    }

    private Condition connectValueCondition(ValueCondition valueCondition) {
        Field<?> field = tableLike.field(valueCondition.field);
        if (field != null) {
            return connectValueCondition(field, valueCondition.value, valueCondition.valueOp);
        } else {
            throw new IllegalArgumentException("error field:" + valueCondition.field);
        }
    }

    private Condition convertCondition(com.moseeker.thrift.gen.common.struct.Condition condition) {
        if (condition.getInnerCondition() != null) {
            return connectInnerCondition(condition.getInnerCondition());
        } else if (condition.getValueCondition() != null) {
            return connectValueCondition(condition.getValueCondition());
        } else {
            return null;
        }
    }

    /**
     * 所有Condition组装
     *
     * @param query
     * @return
     */
    private Condition buildConditions(CommonQuery query) {
        com.moseeker.thrift.gen.common.struct.Condition condition = query.getConditions();
        return convertCondition(condition);
    }


    /**
     * 所有Order的组装
     *
     * @param query
     * @return
     */
    private Collection<? extends SortField<?>> buildOrder(CommonQuery query) {
        return Stream.of(query)
                .filter(query1 -> query1 != null)
                .map(query1 -> query1.getOrders())
                .filter(orders -> orders != null)
                .flatMap(orders -> orders.stream())
                .map(orderBy -> {
                    Field<?> field = tableLike.field(orderBy.field);
                    if (field == null) {
                        throw new OrmException("field '" + orderBy.field + "' not found in table " + tableLike.getName());
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
    /**
     * 需要制定JOOQ
     */
    protected TableImpl<R> tableLike;

    protected abstract void initJOOQEntity();
    @SuppressWarnings("unchecked")
    public List<R> getResources(CommonQuery query) throws Exception {
        initJOOQEntity();
        List<R> records;
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

            SelectJoinStep<Record> table = buildSelect(create, query).from(tableLike);

            table.where(buildConditions(query));

            table.orderBy(buildOrder(query));
=======
		implements BaseDao<R> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private SelectSelectStep<Record> buildSelect(DSLContext create, CommonQuery query) {
		Set<SelectField<?>> fieldSet = Stream.of(query)
				.filter(query1 -> query1 != null)
				.flatMap(query1 -> Stream.of(query.getAttributes()))
				.filter(attributes -> attributes != null)
				.flatMap(attributes -> attributes.stream())
				.map(select -> {
					Field<?> field = tableLike.field(select.field);
					if (field == null) {
						throw new OrmException("field '" + select.field + "' not found in table " + tableLike.getName());
					} else {
						return field;
					}
				})
				.collect(Collectors.toSet());
		return create.select(fieldSet);
	}

	private Condition connectInnerCondition(InnerCondition innerCondition) {
		Condition c1 = convertCondition(innerCondition.getFirstCondition());
		Condition c2 = convertCondition(innerCondition.getSecondCondition());

		if (innerCondition.getConditionOp() == ConditionOp.AND) {
			return c1.and(c2);
		} else if (innerCondition.getConditionOp() == ConditionOp.OR) {
			return c1.or(c2);
		} else {
			throw new IllegalArgumentException("error condition");
		}
	}

	private <T> T convertTo(String value, Class<T> tClass) {
		return BeanUtils.convertTo(value, tClass);
	}

	private <E> Condition connectValueCondition(Field<E> field, String value, ValueOp valueOp) {
		switch (valueOp) {
			case EQ:
				return field.equal(convertTo(value, field.getType()));
			case NEQ:
				return field.notEqual(convertTo(value, field.getType()));
			case IN:
				if (value.startsWith("[") && value.endsWith("]")) {
					List<E> list = BeanUtils.convertTo(value, field.getType());
					return field.in(list);
				} else {
					return null;
				}
			case NIN:
				if (value.startsWith("[") && value.endsWith("]")) {
					List<E> list = BeanUtils.convertTo(value, field.getType());
					return field.notIn(list);
				} else {
					return null;
				}
			case GT:
				return field.greaterThan(convertTo(value, field.getType()));
			case GE:
				return field.greaterOrEqual(convertTo(value, field.getType()));
			case LT:
				return field.lessThan(convertTo(value, field.getType()));
			case LE:
				return field.lessOrEqual(convertTo(value, field.getType()));
			case BT:
				if (value.startsWith("[") && value.endsWith("]")) {
					List<E> list = BeanUtils.convertTo(value, field.getType());
					return field.in(list);
				} else {
					return null;
				}
			case NBT:
			case LIKE:
			case NLIKE:
				return null;
			default:
				throw new IllegalArgumentException("error value constraint");
		}
	}

	private Condition connectValueCondition(ValueCondition valueCondition) {
		Field<?> field = tableLike.field(valueCondition.field);
		if (field != null) {
			return connectValueCondition(field, valueCondition.value, valueCondition.valueOp);
		} else {
			throw new IllegalArgumentException("error field:" + valueCondition.field);
		}
	}

	private Condition convertCondition(com.moseeker.thrift.gen.common.struct.Condition condition) {
		if (condition.getInnerCondition() != null) {
			return connectInnerCondition(condition.getInnerCondition());
		} else if (condition.getValueCondition() != null) {
			return connectValueCondition(condition.getValueCondition());
		} else {
			return null;
		}
	}

	/**
	 * 所有Condition组装
	 *
	 * @param query
	 * @return
	 */
	private Condition buildConditions(CommonQuery query) {
		com.moseeker.thrift.gen.common.struct.Condition condition = query.getConditions();
		return convertCondition(condition);
	}


	/**
	 * 所有Order的组装
	 *
	 * @param query
	 * @return
	 */
	private Collection<? extends SortField<?>> buildOrder(CommonQuery query) {
		return Stream.of(query)
				.filter(query1 -> query1 != null)
				.map(query1 -> query1.getOrders())
				.filter(orders -> orders != null)
				.flatMap(orders -> orders.stream())
				.map(orderBy -> {
					Field<?> field = tableLike.field(orderBy.field);
					if (field == null) {
						throw new OrmException("field '" + orderBy.field + "' not found in table " + tableLike.getName());
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
	/**
	 * 需要制定JOOQ
	 */
	protected TableImpl<R> tableLike;

	protected abstract void initJOOQEntity();
	@SuppressWarnings("unchecked")
	public List<R> getResources(CommonQuery query) throws Exception {
		initJOOQEntity();
		List<R> records;
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

			SelectJoinStep<Record> table = buildSelect(create, query).from(tableLike);

			table.where(buildConditions(query));

			table.orderBy(buildOrder(query));
>>>>>>> Stashed changes

			/* 分段查找数据库结果集 */
            int page = 1;
            int per_page = 0;
            if (query.getPage() > 0) {
                page = query.getPage();
            }
            per_page = query.getPageSize() > 0 ? query.getPageSize() : 10;
            table.limit((page - 1) * per_page, per_page);

            records = table.fetchInto(tableLike.getRecordType());
        } catch (Exception e) {
            logger.error("error", e);
            throw new Exception(e);
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        return records;
    }

    @SuppressWarnings({"unchecked"})
    public int getResourceCount(CommonQuery query) throws Exception {
        initJOOQEntity();
        int totalCount = 0;
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

            SelectQuery<?> selectQuery = create.selectQuery();
            selectQuery.addFrom(tableLike);

            buildSelect(create, query);

            if (query.getGroups() != null && query.getGroups().size() > 0) {
                Field[] fields = (Field[]) query.getGroups().stream().filter(group -> tableLike.field(group) != null).map(group -> tableLike.field(group)).toArray();
                if (fields != null && fields.length > 0) {
                    selectQuery.addGroupBy(fields);
                }
            }
            totalCount = create.fetchCount(selectQuery);
        } catch (Exception e) {
            logger.error("error", e);
            throw new Exception(e);
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        return totalCount;
    }

    public int postResources(List<R> records) throws Exception {
        initJOOQEntity();
        int insertret = 0;
        Connection conn = null;
        try {
            if (records != null && records.size() > 0) {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

                int[] insertarray = create.batchInsert(records).execute();
                if (insertarray.length == 0) {
                    return 0;
                } else {
                    insertret = insertarray[0];
                }
            }
        } catch (Exception e) {
            logger.error("error", e);
            throw new Exception(e);
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }

        return insertret;
    }

    public int putResources(List<R> records) throws Exception {
        initJOOQEntity();
        int insertret = 0;
        Connection conn = null;
        if (records != null && records.size() > 0) {
            try {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
                insertret = create.batchUpdate(records).execute()[0];
            } catch (Exception e) {
                logger.error("error", e);
                throw new Exception(e);
            } finally {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            }
        }

        return insertret;
    }

    public int delResources(List<R> records) throws Exception {
        initJOOQEntity();
        int insertret = 0;
        if (records != null && records.size() > 0) {
            Connection conn = null;
            try {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
                insertret = create.batchDelete(records).execute()[0];
            } catch (Exception e) {
                logger.error("error", e);
                throw new Exception(e);
            } finally {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            }
        }

        return insertret;
    }

    @SuppressWarnings({"unchecked"})
    public R getResource(CommonQuery query) throws Exception {
        initJOOQEntity();
        R record = null;
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

            buildSelect(create, query);

            SelectJoinStep<Record> table = buildSelect(create, query).from(tableLike);

            table.where(buildConditions(query));

            buildOrder(query);

            table.limit(1);

            record = table.fetchOneInto(tableLike.getRecordType());
        } catch (Exception e) {
            logger.error("error", e);
            throw new Exception(e);
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        return record;
    }

    public int postResource(R record) throws Exception {
        initJOOQEntity();
        int insertret = 0;
        Connection conn = null;
        if (record != null) {
            try {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
                create.attach(record);
                record.insert();
                if (record.key() != null) {
                    Record key = record.key();
                    int keyValue = BeanUtils.converToInteger(key.get(0));
                    return keyValue;
                }
            } catch (Exception e) {
                logger.error("error", e);
                throw new Exception(e);
            } finally {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            }
        }

        return insertret;
    }

    public int putResource(R record) throws Exception {
        initJOOQEntity();
        int insertret = 0;
        Connection conn = null;
        try {
            if (record != null) {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
                create.attach(record);
                insertret = record.update();
            }
        } catch (Exception e) {
            logger.error("error", e);
            throw new Exception(e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return insertret;
    }

    public int delResource(R record) throws Exception {
        initJOOQEntity();
        int insertret = 0;
        if (record != null) {
            Connection conn = null;
            try {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
                create.attach(record);
                insertret = record.delete();
            } catch (Exception e) {
                logger.error("error", e);
                throw new Exception(e);
            } finally {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            }
        }

        return insertret;
    }
}
