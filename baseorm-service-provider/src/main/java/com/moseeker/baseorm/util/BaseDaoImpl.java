package com.moseeker.baseorm.util;

import com.moseeker.baseorm.crud.LocalQuery;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.query.Query;
import org.jooq.*;
import org.jooq.impl.TableImpl;
import org.jooq.impl.UpdatableRecordImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 实现通用数据操作接口的抽象类
 * <p>Company: MoSeeker</P>
 * <p>date: May 5, 2016</p>
 * <p>Email: wjf2255@gmail.com</p>
 *
 * @param <R> 表示JOOQ表记录的ORM类
 * @param <T> 表示JOOQ表的ORM类
 * @author wjf
 * @version Beta
 * @Deprecated 新的dao类继承JooqCrudImpl或者直接使用JooqCrudImpl
 * 参见
 *
 */
@SuppressWarnings("rawtypes")
public abstract class BaseDaoImpl<R extends UpdatableRecordImpl<R>, T extends TableImpl<R>> implements BaseDao<R> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());


	/**
	 * 需要制定JOOQ
	 */
	protected TableImpl<R> tableLike;

	protected abstract void initJOOQEntity();

	@SuppressWarnings("unchecked")
	public List<R> getResources(Query query) throws Exception {
		initJOOQEntity();
		List<R> records;
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

			LocalQuery localQuery = new LocalQuery(create, tableLike, query);

			ResultQuery resultQuery = localQuery.convertToResultQuery();

			records = resultQuery.fetchInto(tableLike.getRecordType());
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
	public R getResource(Query query) throws Exception {
		initJOOQEntity();
		R record;
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

			LocalQuery<R> localQuery = new LocalQuery<>(create, tableLike, query);

			ResultQuery<R> resultQuery = localQuery.convertToResultQuery();

			record = resultQuery.fetchOneInto(tableLike.getRecordType());
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

	@SuppressWarnings({"unchecked"})
	public int getResourceCount(Query query) throws Exception {
		initJOOQEntity();
		int totalCount = 0;
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

			LocalQuery localQuery = new LocalQuery(create, tableLike, query);

			SelectConditionStep select = localQuery.convertToSelect();
			totalCount = create.fetchCount(select);
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
