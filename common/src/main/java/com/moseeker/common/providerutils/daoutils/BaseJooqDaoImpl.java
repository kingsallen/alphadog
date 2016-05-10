package com.moseeker.common.providerutils.daoutils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.thrift.TException;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.jooq.SelectQuery;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.TableLike;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.TableImpl;
import org.jooq.impl.UpdatableRecordImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.common.dbutils.DatabaseConnectionHelper;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;

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
public abstract class BaseJooqDaoImpl<R extends UpdatableRecordImpl<R>, T extends TableImpl<R>>
		implements BaseDao<R> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 需要制定JOOQ
	 */
	protected TableLike<R> tableLike;

	protected abstract void initJOOQEntity();

	@SuppressWarnings("unchecked")
	public List<R> getResources(CommonQuery query) throws TException {
		initJOOQEntity();
		List<R> records = new ArrayList<>();
		try {
			DSLContext create = DatabaseConnectionHelper.getConnection()
					.getJooqDSL();

			SelectJoinStep<Record> table = create.select().from(tableLike);

			if (query.getEqualFilter() != null
					&& query.getEqualFilter().size() > 0) {
				Map<String, String> equalFilter = query.getEqualFilter();
				for (Entry<String, String> entry : equalFilter.entrySet()) {
					Field<?> field = tableLike.field(entry.getKey());
					if (field != null) {
						table.where(field.strictEqual(BeanUtils.convertTo(
								entry.getValue(), field.getType())));
					}
				}
			}

			if (!StringUtils.isNullOrEmpty(query.getSortby())) {
				String[] sortBy = query.getSortby().split(",");
				String[] order = query.getOrder().split(",");

				List<SortField<?>> fields = new ArrayList<>(sortBy.length);
				SortOrder so = SortOrder.ASC;
				for (int i = 0; i < sortBy.length; i++) {
					Field<?> field = table.field(sortBy[i]);
					if (sortBy.length == order.length
							&& !StringUtils.isNullOrEmpty(order[i])
							&& order[i].toLowerCase().equals("desc")) {
						so = SortOrder.DESC;
					}
					if (field != null) {
						switch (so) {
							case ASC:
								fields.add(field.asc());
								break;
							case DESC:
								fields.add(field.desc());
								break;
							default:
						}
					}
				}
				Field<?>[] fieldArray = null;
				table.orderBy(fields.toArray(fieldArray));
			}

			/* 分段查找数据库结果集 */
			int offset = 0;
			int limit = 0;
			if (query.getLimit() > 0) {
				if (query.getOffset() > 0) {
					offset = query.getOffset();
				}
				limit = query.getLimit();
			}
			if (limit > 0) {
				table.limit(offset, limit);
			}

			Result<Record> result = table.fetch();

			if (result != null && result.size() > 0) {
				for (Record r : result) {
					records.add((R)r);
				}
			}
		} catch (DataAccessException | SQLException e) {
			logger.error("error", e);
			throw new TException();
		} finally {
			//do nothing
		}
		return records;
	}
	
	@SuppressWarnings({"unchecked" })
	public int getResourceCount(CommonQuery query) throws TException {
		initJOOQEntity();
		int totalCount = 0;
		try {
			DSLContext create = DatabaseConnectionHelper.getConnection()
					.getJooqDSL();

			SelectQuery<?> selectQuery = create.selectQuery();
			selectQuery.addFrom(tableLike);

			if (query.getEqualFilter() != null && query.getEqualFilter().size() > 0) {
				Map<String, String> equalFilter = query.getEqualFilter();
				for (Entry<String, String> entry : equalFilter.entrySet()) {
					Field field = tableLike.field(entry.getKey());
					if (field != null) {
						selectQuery.addConditions(field.strictEqual(BeanUtils.convertTo(
								entry.getValue(), field.getType())));
					}
				}
			}
			totalCount = create.fetchCount(selectQuery);
		} catch (DataAccessException | SQLException e) {
			logger.error("error", e);
			throw new TException();
		} finally {
			//do nothing
		}
		return totalCount;
	}

	public int postResources(List<R> records) throws TException {
		initJOOQEntity();
		int insertret = 0;
		try {
			if (records != null && records.size() > 0) {
				DSLContext create = DatabaseConnectionHelper.getConnection()
						.getJooqDSL();
				insertret = create.batchInsert(records).execute()[0];
			}
		} catch (DataAccessException | SQLException e) {
			logger.error("error", e);
			throw new TException();
		} finally {
			//do nothing
		}

		return insertret;
	}

	public int putResources(List<R> records) throws TException {
		initJOOQEntity();
		int insertret = 0;
		if (records != null && records.size() > 0) {
			try {
				DSLContext create = DatabaseConnectionHelper.getConnection()
						.getJooqDSL();
				insertret = create.batchUpdate(records).execute()[0];
			} catch (DataAccessException | SQLException e) {
				logger.error("error", e);
				throw new TException();
			} finally {
				//do nothing
			}
		}

		return insertret;
	}

	public int delResources(List<R> records) throws TException {
		initJOOQEntity();
		int insertret = 0;
		if (records != null && records.size() > 0) {
			try {
				DSLContext create = DatabaseConnectionHelper.getConnection()
						.getJooqDSL();
				insertret = create.batchDelete(records).execute()[0];
			} catch (DataAccessException | SQLException e) {
				logger.error("error", e);
				throw new TException();
			} finally {
				//do nothing
			}
		}

		return insertret;
	}
	
	@SuppressWarnings({"unchecked" })
	public R getResource(CommonQuery query) throws TException {
		initJOOQEntity();
		R record = null;
		try {
			DSLContext create = DatabaseConnectionHelper.getConnection()
					.getJooqDSL();

			SelectJoinStep<Record> table = create.select().from(tableLike);

			if (query.getEqualFilter() != null
					&& query.getEqualFilter().size() > 0) {
				Map<String, String> equalFilter = query.getEqualFilter();
				for (Entry<String, String> entry : equalFilter.entrySet()) {
<<<<<<< HEAD
					Field field = table.field(entry.getKey());
=======
					Field field = tableLike.field(entry.getKey());
>>>>>>> feature/useraccount-service-privider
					if (field != null) {
						table.where(field.strictEqual(BeanUtils.convertTo(
								entry.getValue(), field.getType())));
					}
				}
			}

			if (!StringUtils.isNullOrEmpty(query.getSortby())) {
				String[] sortBy = query.getSortby().split(",");
				String[] order = query.getOrder().split(",");

				List<SortField<?>> fields = new ArrayList<>(sortBy.length);
				SortOrder so = SortOrder.ASC;
				for (int i = 0; i < sortBy.length; i++) {
					Field<?> field = table.field(sortBy[i]);
					if (sortBy.length == order.length
							&& !StringUtils.isNullOrEmpty(order[i])
							&& order[i].toLowerCase().equals("desc")) {
						so = SortOrder.DESC;
					}
					if (field != null) {
						switch (so) {
						case ASC:
							fields.add(field.asc());
							break;
						case DESC:
							fields.add(field.desc());
							break;
						default:
						}
					}
				}
				Field<?>[] fieldArray = null;
				table.orderBy(fields.toArray(fieldArray));
			}

			table.limit(1);

			Result<Record> result = table.fetch();

			if (result != null && result.size() > 0) {
				record = (R) result.get(0);
			}
		} catch (DataAccessException | SQLException e) {
			logger.error("error", e);
			throw new TException();
		} finally {
			//do nothing
		}
		return record;
	}

	public int postResource(R record) throws TException {
		initJOOQEntity();
		int insertret = 0;
		if (record != null) {
			try {
				DSLContext create = DatabaseConnectionHelper.getConnection()
						.getJooqDSL();
				insertret = create.batchInsert(record).execute()[0];
			} catch (DataAccessException | SQLException e) {
				logger.error("error", e);
				throw new TException();
			} finally {
				//do nothing
			}
		}

		return insertret;
	}

	public int putResource(R record) throws TException {
		initJOOQEntity();
		int insertret = 0;
		if (record != null) {
			List<R> records = new ArrayList<>();
			records.add(record);
			insertret = putResources(records);
		}
		return insertret;
	}

	public int delResource(R record) throws TException {
		initJOOQEntity();
		int insertret = 0;
		if (record != null) {
			try {
				DSLContext create = DatabaseConnectionHelper.getConnection()
						.getJooqDSL();
				insertret = create.batchDelete(record).execute()[0];
			} catch (DataAccessException | SQLException e) {
				logger.error("error", e);
				throw new TException();
			} finally {
				//do nothing
			}
		}

		return insertret;
	}
}
