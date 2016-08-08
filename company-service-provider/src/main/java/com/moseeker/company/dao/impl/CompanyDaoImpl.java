package com.moseeker.company.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.company.dao.CompanyDao;
import com.moseeker.db.dictdb.tables.DictConstant;
import com.moseeker.db.dictdb.tables.records.DictConstantRecord;
import com.moseeker.db.hrdb.tables.HrCompany;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.db.userdb.tables.UserHrAccount;
import com.moseeker.thrift.gen.common.struct.CommonQuery;

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

			if (query.getEqualFilter() != null && query.getEqualFilter().size() > 0) {
				Map<String, String> equalFilter = query.getEqualFilter();
				for (Map.Entry<String, String> entry : equalFilter.entrySet()) {
					Field<?> field = tableLike.field(entry.getKey());
					if (field != null) {
						table.where(field.strictEqual(BeanUtils.convertTo(entry.getValue(), field.getType())));
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
					if (sortBy.length == order.length && !StringUtils.isNullOrEmpty(order[i])
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

			// /* 分段查找数据库结果集 */
			// int page = 1;
			// int per_page = 0;
			// if (query.getPage() > 0) {
			// page = query.getPage();
			// }
			// per_page = query.getPer_page()>0 ? query.getPer_page() : 10 ;
			// table.limit((page-1)*per_page, per_page);

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
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
			// do nothing
		}
		return records;
	}

	@Override
	public boolean checkRepeatNameWithSuperCompany(String name) {
		boolean repeatName = false;
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			Result<Record1<UInteger>> result = create.select(HrCompany.HR_COMPANY.ID)
					.from(HrCompany.HR_COMPANY.join(UserHrAccount.USER_HR_ACCOUNT)
							.on(HrCompany.HR_COMPANY.HRACCOUNT_ID.equal(UserHrAccount.USER_HR_ACCOUNT.ID)))
					.where(HrCompany.HR_COMPANY.NAME.like(name))
					.and(UserHrAccount.USER_HR_ACCOUNT.ACCOUNT_TYPE.equal(Constant.ACCOUNT_TYPE_SUPERACCOUNT)).fetch();
			if (result != null && result.size() > 0) {
				repeatName = true;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return repeatName;
	}

	@Override
	public void transactionTest() {
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			conn.setAutoCommit(false);
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			ProfileProfileRecord profile = new ProfileProfileRecord();
			profile.setUuid("transaction test");
			profile.setDisable(UByte.valueOf(0));
			profile.setUserId(UInteger.valueOf(1));
			create.attach(profile);
			profile.insert();

			ProfileProfileRecord profile2 = new ProfileProfileRecord();
			profile2.setUuid("transaction testdele1");
			profile2.setDisable(UByte.valueOf(0));
			profile2.setUserId(UInteger.valueOf(2));
			create.attach(profile2);
			profile2.insert();

			ProfileProfileRecord profile1 = new ProfileProfileRecord();
			profile1.setUuid("transaction testdele1");
			profile1.setDisable(UByte.valueOf(0));
			profile1.setUserId(UInteger.valueOf(2));
			create.attach(profile1);
			profile1.insert();
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (conn != null && !conn.isClosed()) {
					conn.rollback();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public boolean checkScaleIllegal(UByte scale) {
		boolean scaleIllegal = false;
		if (scale != null && scale.intValue() > 0) {
			Connection conn = null;
			try {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				List<DictConstantRecord> dictScales = create.selectFrom(DictConstant.DICT_CONSTANT)
						.where(DictConstant.DICT_CONSTANT.PARENT_CODE.equal(Constant.DICT_CONSTANT_COMPANY_SCAL))
						.and(DictConstant.DICT_CONSTANT.CODE.equal(scale.intValue())).fetch();
				if(dictScales != null && dictScales.size() > 0) {
					scaleIllegal = true;
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		} else {
			scaleIllegal = true;
		}
		return scaleIllegal;
	}

	@Override
	public boolean checkPropertyIllegal(UByte property) {
		boolean scaleIllegal = false;
		if (property != null && property.intValue() > 0) {
			Connection conn = null;
			try {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				List<DictConstantRecord> dictScales = create.selectFrom(DictConstant.DICT_CONSTANT)
						.where(DictConstant.DICT_CONSTANT.PARENT_CODE.equal(Constant.DICT_CONSTANT_COMPANY_SCAL))
						.and(DictConstant.DICT_CONSTANT.CODE.equal(property.intValue())).fetch();
				if(dictScales != null && dictScales.size() > 0) {
					scaleIllegal = true;
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		} else {
			scaleIllegal = true;
		}
		return scaleIllegal;
	}
}
