package com.moseeker.profile.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.TableLike;
import org.jooq.impl.TableImpl;
import org.jooq.impl.UpdatableRecordImpl;

import com.moseeker.common.dbconnection.DatabaseConnectionHelper;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;

/**
 * 
 * 基础表的增伤改查操作 
 * <p>Company: MoSeeker</P>  
 * <p>date: Apr 27, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 * @param <K>
 * @param <T>
 */
public abstract class BasicDaoImpl<K extends UpdatableRecordImpl<K>, T extends TableImpl<K>> implements BasicDao<K> {
	
	protected TableLike<K> tableLike;
	
	protected abstract void initJOOQEntity();

	public Result<Record> getProfiles(CommonQuery query)
			throws SQLException {
		DSLContext create = DatabaseConnectionHelper.getConnection()
				.getJooqDSL();

		SelectJoinStep<Record> table = create.select().from(tableLike);

		if (query.getEqualFilter() != null && query.getEqualFilter().size() > 0) {
			Map<String, String> equalFilter = query.getEqualFilter();
			for(Entry<String, String> entry : equalFilter.entrySet()) {
				Field field = table.field(entry.getKey());
				if(field != null) {
					table.where(field.equal(convertTo(entry.getValue(), field.getType())));
				}
			}
		}
		
		if (!StringUtils.isNullOrEmpty(query.getSortby())) {
			String[] sortBy = query.getSortby().split(",");
			String[] order = query.getOrder().split(",");
			
			List<SortField<?>> fields = new ArrayList<>(sortBy.length);
			SortOrder so = SortOrder.ASC;
			for (int i = 0; i < sortBy.length - 1; i++) {
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
		return result;
	}
	
	public int postProfiles(List<K> records) throws SQLException {
		int insertret = 0;
		if(records != null && records.size() > 0) {
			DSLContext create = DatabaseConnectionHelper.getConnection().getJooqDSL();
			insertret = create.batchInsert(records).execute()[0];
		}
		
		return insertret;
	}
	
	public int putProfiles(List<K> records) throws SQLException {
		int insertret = 0;
		if(records != null && records.size() > 0) {
			DSLContext create = DatabaseConnectionHelper.getConnection().getJooqDSL();
			insertret = create.batchUpdate(records).execute()[0];
		}
		
		return insertret;
	}
	
	public int delProfiles(List<K> records) throws SQLException {
		int insertret = 0;
		if(records != null && records.size() > 0) {
			DSLContext create = DatabaseConnectionHelper.getConnection().getJooqDSL();
			insertret = create.batchDelete(records).execute()[0];
		}
		
		return insertret;
	}
	
	public int postProfile(K record) throws SQLException {
		int insertret = 0;
		if(record != null) {
			DSLContext create = DatabaseConnectionHelper.getConnection().getJooqDSL();
			insertret = create.batchInsert(record).execute()[0];
		}
		
		return insertret;
	}
	
	public int putProfile(K record) throws SQLException {
		int insertret = 0;
		if(record != null) {
			/*DSLContext create = DatabaseConnectionHelper.getConnection().getJooqDSL();
			Date date = new Date();
			create.updateQuery(tableLike).setRecord(record);*/
			List<K> records = new ArrayList<K>();
			insertret = putProfiles(records);
		}
		
		return insertret;
	}

	public int delProfile(K record) throws SQLException {
		int insertret = 0;
		if(record != null) {
			DSLContext create = DatabaseConnectionHelper.getConnection().getJooqDSL();
			insertret = create.batchDelete(record).execute()[0];
		}
		
		return insertret;
	}
	
	/**
     * 类型转换。提供将对象转成指定的类型的功能
     *
     * @param value     被转换的对象
     * @param clazzType 指定一个转成的类型
     * @return 返回转换的结果
     */
    @SuppressWarnings("unchecked")
    protected <L> L convertTo(Object value, Class<L> clazzType) {
        if (clazzType.isAssignableFrom(String.class)) {
            return (L) value.toString();
        } else if (clazzType.isAssignableFrom(Long.class)) {
            return (L) new Long(value.toString());
        } else if (clazzType.isAssignableFrom(Byte.class)) {
            return (L) new Byte(value.toString());
        } else if (clazzType.isAssignableFrom(Integer.class)) {
            return (L) new Integer(value.toString());
        } else if (clazzType.isAssignableFrom(Float.class)) {
            return (L) new Float(value.toString());
        } else if (clazzType.isAssignableFrom(Float.class)) {
            return (L) new Double(value.toString());
        } else if (clazzType.isAssignableFrom(Boolean.class)) {
            return (L) new Boolean(value.toString());
        } else {
            return (L) value.toString();
        }
    }
}
