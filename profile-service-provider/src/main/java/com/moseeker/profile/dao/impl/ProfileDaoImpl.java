package com.moseeker.profile.dao.impl;

import static com.moseeker.db.profileDB.tables.Profile.PROFILE;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.stereotype.Component;

import com.moseeker.common.dbconnection.DatabaseConnectionHelper;
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.profileDB.tables.records.ProfileRecord;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;

@Component
public class ProfileDaoImpl implements ProfileDao<ProfileRecord> {

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Result<Record> getProfiles(CommonQuery query, ProfileRecord record)
			throws SQLException {
		DSLContext create = DatabaseConnectionHelper.getConnection()
				.getJooqDSL();

		SelectJoinStep<Record> table = create.select().from(PROFILE);

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

	@Override
	public int postProfiles(List<ProfileRecord> records) throws SQLException {
		int insertret = 0;
		if(records != null && records.size() > 0) {
			DSLContext create = DatabaseConnectionHelper.getConnection().getJooqDSL();
			Date date = new Date();
			Timestamp ts = new Timestamp(date.getTime());
			for(ProfileRecord record : records) {
				record.setCreateTime(ts);
				record.setUpdateTime(ts);
			}
			insertret = create.batchInsert(records).execute()[0];
		}
		
		return insertret;
	}
	
	@Override
	public int putProfiles(List<ProfileRecord> records) throws SQLException {
		int insertret = 0;
		if(records != null && records.size() > 0) {
			DSLContext create = DatabaseConnectionHelper.getConnection().getJooqDSL();

			Date date = new Date();
			Timestamp ts = new Timestamp(date.getTime());
			for(ProfileRecord record : records) {
				record.setUpdateTime(ts);
			}
			insertret = create.batchUpdate(records).execute()[0];
		}
		
		return insertret;
	}
	
	@Override
	public int delProfiles(List<ProfileRecord> records) throws SQLException {
		int insertret = 0;
		if(records != null && records.size() > 0) {
			DSLContext create = DatabaseConnectionHelper.getConnection().getJooqDSL();
			insertret = create.batchDelete(records).execute()[0];
		}
		
		return insertret;
	}
	
	@Override
	public int postProfile(ProfileRecord record) throws SQLException {
		int insertret = 0;
		if(record != null) {
			DSLContext create = DatabaseConnectionHelper.getConnection().getJooqDSL();
			Date date = new Date();
			Timestamp ts = new Timestamp(date.getTime());
			record.setCreateTime(ts);
			record.setUpdateTime(ts);
			insertret = create.batchInsert(record).execute()[0];
		}
		
		return insertret;
	}
	
	@Override
	public int putProfile(ProfileRecord record) throws SQLException {
		int insertret = 0;
		if(record != null) {
			DSLContext create = DatabaseConnectionHelper.getConnection().getJooqDSL();
			Date date = new Date();
			Timestamp ts = new Timestamp(date.getTime());
			record.setUpdateTime(ts);
			create.updateQuery(PROFILE).setRecord(record);
		}
		
		return insertret;
	}

	@Override
	public int delProfile(ProfileRecord record) throws SQLException {
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
    private <T> T convertTo(Object value, Class<T> clazzType) {
        if (clazzType.isAssignableFrom(String.class)) {
            return (T) value.toString();
        } else if (clazzType.isAssignableFrom(Long.class)) {
            return (T) new Long(value.toString());
        } else if (clazzType.isAssignableFrom(Byte.class)) {
            return (T) new Byte(value.toString());
        } else if (clazzType.isAssignableFrom(Integer.class)) {
            return (T) new Integer(value.toString());
        } else if (clazzType.isAssignableFrom(Float.class)) {
            return (T) new Float(value.toString());
        } else if (clazzType.isAssignableFrom(Float.class)) {
            return (T) new Double(value.toString());
        } else if (clazzType.isAssignableFrom(Boolean.class)) {
            return (T) new Boolean(value.toString());
        } else {
            return (T) value.toString();
        }
    }
}
