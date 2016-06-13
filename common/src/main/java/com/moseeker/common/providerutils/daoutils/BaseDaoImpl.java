package com.moseeker.common.providerutils.daoutils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.jooq.SelectQuery;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.TableLike;
import org.jooq.impl.TableImpl;
import org.jooq.impl.UpdatableRecordImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;

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
		implements BaseDao<R> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 需要制定JOOQ
	 */
	protected TableLike<R> tableLike;

	protected abstract void initJOOQEntity();

	@SuppressWarnings("unchecked")
	public List<R> getResources(CommonQuery query) throws Exception {
		initJOOQEntity();
		List<R> records = new ArrayList<>();
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

			SelectJoinStep<Record> table = create.select().from(tableLike);

			if (query.getEqualFilter() != null
					&& query.getEqualFilter().size() > 0) {
				Map<String, String> equalFilter = query.getEqualFilter();
				for (Entry<String, String> entry : equalFilter.entrySet()) {
					Field<?> field = tableLike.field(entry.getKey());
					if (field != null) {
						if(entry.getValue().startsWith("[") && entry.getValue().endsWith("]")) {
							String[] arrayValue = entry.getValue().substring(1, entry.getValue().length()-1).split(",");
							Condition condition = null;
							for(String value : arrayValue) {
								if(condition == null) {
									condition = field.strictEqual(BeanUtils.convertTo(
											value, field.getType()));
								} else {
									condition = condition.or(field.strictEqual(BeanUtils.convertTo(
											value, field.getType())));
								}
							}
							table.where(condition);
						} else {
							table.where(field.strictEqual(BeanUtils.convertTo(
									entry.getValue(), field.getType())));
						}
					}
				}
			}

			if (!StringUtils.isNullOrEmpty(query.getSortby())) {
				String[] sortBy = query.getSortby().split(",");
				String[] order = query.getOrder().split(",");

				List<SortField<?>> fields = new ArrayList<>(sortBy.length);
				SortOrder so = SortOrder.ASC;
				for (int i = 0; i < sortBy.length; i++) {
					Field<?> field = tableLike.field(sortBy[i]);
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
				table.orderBy(fields);
			}

			/* 分段查找数据库结果集 */
			int page = 1;
			int per_page = 0;
			if (query.getPage() > 0) {
					page = query.getPage();
			}
			per_page = query.getPer_page()>0 ? query.getPer_page() : 10 ;
			table.limit((page-1)*per_page, per_page);

			Result<Record> result = table.fetch();

			if (result != null && result.size() > 0) {
				for (Record r : result) {
					records.add((R)r);
				}
			}
		} catch (Exception e) {
			logger.error("error", e);
			throw new Exception(e);
		} finally {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		}
		return records;
	}
	
	@SuppressWarnings({"unchecked" })
	public int getResourceCount(CommonQuery query) throws Exception {
		initJOOQEntity();
		int totalCount = 0;
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

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
		} catch (Exception e) {
			logger.error("error", e);
			throw new Exception(e);
		} finally {
			if(conn != null && !conn.isClosed()) {
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
				if (insertarray.length == 0){
					return 0;
				}else{
					insertret = insertarray[0];
				}	
			}
		} catch (Exception e) {
			logger.error("error", e);
			throw new Exception(e);
		} finally {
			if(conn != null && !conn.isClosed()) {
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
				if(conn != null && !conn.isClosed()) {
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
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
			}
		}

		return insertret;
	}
	
	@SuppressWarnings({"unchecked" })
	public R getResource(CommonQuery query) throws Exception {
		initJOOQEntity();
		R record = null;
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

			SelectJoinStep<Record> table = create.select().from(tableLike);

			if (query.getEqualFilter() != null
					&& query.getEqualFilter().size() > 0) {
				Map<String, String> equalFilter = query.getEqualFilter();
				for (Entry<String, String> entry : equalFilter.entrySet()) {
					Field field = tableLike.field(entry.getKey());
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
		} catch (Exception e) {
			logger.error("error", e);
			throw new Exception(e);
		} finally {
			if(conn != null && !conn.isClosed()) {
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
				if(record.key() != null) {
					Record key = record.key();
					int keyValue = BeanUtils.converToInteger(key.get(0));
					return keyValue;
				}
			} catch (Exception e) {
				logger.error("error", e);
				throw new Exception(e);
			} finally {
				if(conn != null && !conn.isClosed()) {
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
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
			}
		}

		return insertret;
	}
}
