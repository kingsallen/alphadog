package com.moseeker.common.providerutils.daoutils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.thrift.TBase;
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
 * @param <S> 基于Thrift通信的数据结构
 */
@SuppressWarnings("rawtypes")
public abstract class BaseDaoImpl<R extends UpdatableRecordImpl<R>, T extends TableImpl<R>, S extends TBase>
		implements BaseDao<S> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 需要制定JOOQ
	 */
	protected TableLike<R> tableLike;

	protected abstract void initJOOQEntity();

	public List<S> getResources(CommonQuery query) throws Exception {
		initJOOQEntity();
		List<S> structs = new ArrayList<>();
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
					@SuppressWarnings("unchecked")
					S k = DBToStruct((R) r);
					structs.add(k);
				}
			}
		return structs;
	}
	
	@SuppressWarnings({"unchecked" })
	public int getResourceCount(CommonQuery query) throws Exception {
		initJOOQEntity();
		int totalCount = 0;
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
		return totalCount;
	}

	public int postResources(List<S> structs) throws Exception {
		initJOOQEntity();
		int insertret = 0;
		List<R> records = structsToDBs(structs);
		if (records != null && records.size() > 0) {
			DSLContext create = DatabaseConnectionHelper.getConnection()
					.getJooqDSL();
			insertret = create.batchInsert(records).execute()[0];
		}
		return insertret;
	}

	public int putResources(List<S> structs) throws Exception {
		initJOOQEntity();
		int insertret = 0;
		if (structs != null && structs.size() > 0) {
			List<R> records = structsToDBs(structs);
			DSLContext create = DatabaseConnectionHelper.getConnection()
					.getJooqDSL();
			insertret = create.batchUpdate(records).execute()[0];
		}

		return insertret;
	}

	public int delResources(List<S> structs) throws Exception {
		initJOOQEntity();
		int insertret = 0;
		if (structs != null && structs.size() > 0) {
			List<R> records = structsToDBs(structs);
			DSLContext create = DatabaseConnectionHelper.getConnection()
					.getJooqDSL();
			insertret = create.batchDelete(records).execute()[0];
		}
		return insertret;
	}
	
	@SuppressWarnings({"unchecked" })
	public S getResource(CommonQuery query) throws Exception {
		initJOOQEntity();
		S struct = null;
		DSLContext create = DatabaseConnectionHelper.getConnection()
				.getJooqDSL();

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
			struct = DBToStruct((R) result.get(0));
		}
		return struct;
	}

	public int postResource(S struct) throws Exception {
		initJOOQEntity();
		int insertret = 0;
		if (struct != null) {
			R record = structToDB(struct);
			DSLContext create = DatabaseConnectionHelper.getConnection()
					.getJooqDSL();
			insertret = create.batchInsert(record).execute()[0];
		}

		return insertret;
	}

	public int putResource(S struct) throws Exception {
		initJOOQEntity();
		int insertret = 0;
		if (struct != null) {
			List<S> structs = new ArrayList<>();
			structs.add(struct);
			insertret = putResources(structs);
		}
		return insertret;
	}

	public int delResource(S struct) throws Exception {
		initJOOQEntity();
		int insertret = 0;
		if (struct != null) {
			R record = structToDB(struct);
			DSLContext create = DatabaseConnectionHelper.getConnection()
					.getJooqDSL();
			insertret = create.batchDelete(record).execute()[0];
		}

		return insertret;
	}

	/**
	 * 类型转换。提供将对象转成指定的类型的功能
	 *
	 * @param value
	 *            被转换的对象
	 * @param clazzType
	 *            指定一个转成的类型
	 * @return 返回转换的结果
	 */
	/*
	 * @SuppressWarnings("unchecked") protected <L> L convertTo(Object value,
	 * Class<L> clazzType) { if (clazzType.isAssignableFrom(String.class)) {
	 * return (L) value.toString(); } else if
	 * (clazzType.isAssignableFrom(Long.class)) { return (L) new
	 * Long(value.toString()); } else if
	 * (clazzType.isAssignableFrom(Byte.class)) { return (L) new
	 * Byte(value.toString()); } else if
	 * (clazzType.isAssignableFrom(Integer.class)) { return (L) new
	 * Integer(value.toString()); } else if
	 * (clazzType.isAssignableFrom(Float.class)) { return (L) new
	 * Float(value.toString()); } else if
	 * (clazzType.isAssignableFrom(Float.class)) { return (L) new
	 * Double(value.toString()); } else if
	 * (clazzType.isAssignableFrom(Boolean.class)) { return (L) new
	 * Boolean(value.toString()); } else { return (L) value.toString(); } }
	 */

	protected abstract S DBToStruct(R r);

	protected abstract R structToDB(S structK) throws ParseException;

	protected List<R> structsToDBs(List<S> structKs) throws Exception {
		List<R> records = new ArrayList<>();
		if (structKs != null && structKs.size() > 0) {
			for (S structK : structKs) {
				records.add(structToDB(structK));
			}
		}
		return records;
	}

	protected List<S> DBToStructs(List<R> records) {
		List<S> structs = new ArrayList<>();
		if (records != null && records.size() > 0) {
			for (R record : records) {
				structs.add(DBToStruct(record));
			}
		}
		return structs;
	}
	
	protected void structToDB(S strcuct, R r) {
		java.lang.reflect.Field[] fields = strcuct.getClass().getFields();
		if(fields != null && fields.length > 0) {
			for(int i=0; i<fields.length;i++) {
				
			}
			/*Method method = clazz.getMethod(methodName,
					fields[i].getType());*/
		}
	}
	
	protected void DBToStruct(R r, S struct) {
		
	}
}
