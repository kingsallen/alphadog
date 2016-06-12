package com.moseeker.company.dao.impl;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import org.jooq.*;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.company.dao.CompanyDao;
import com.moseeker.db.hrdb.tables.HrCompany;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CompanyDaoImpl extends BaseDaoImpl<HrCompanyRecord, HrCompany> implements CompanyDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = HrCompany.HR_COMPANY;
	}

	@Override
	public List<HrCompanyRecord> getAllCompanies(CommonQuery query) throws Exception {
		initJOOQEntity();
		List<HrCompanyRecord> records = new ArrayList<>();
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

			SelectJoinStep<Record> table = create.select().from(tableLike);

			if (query.getEqualFilter() != null
					&& query.getEqualFilter().size() > 0) {
				Map<String, String> equalFilter = query.getEqualFilter();
				for (Map.Entry<String, String> entry : equalFilter.entrySet()) {
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

//			/* 分段查找数据库结果集 */
//			int page = 1;
//			int per_page = 0;
//			if (query.getPage() > 0) {
//				page = query.getPage();
//			}
//			per_page = query.getPer_page()>0 ? query.getPer_page() : 10 ;
//			table.limit((page-1)*per_page, per_page);

			Result<Record> result = table.fetch();

			if (result != null && result.size() > 0) {
				for (Record r : result) {
					records.add((HrCompanyRecord) r);
				}
			}
		} catch (Exception e) {
			logger.error("error", e);
			throw new Exception();
		} finally {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
			//do nothing
		}
		return records;
	}
}
