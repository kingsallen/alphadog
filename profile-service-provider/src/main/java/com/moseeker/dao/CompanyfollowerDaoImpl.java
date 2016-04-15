package com.moseeker.dao;

import static com.moseeker.db.userdb.tables.Companyfollowers.COMPANYFOLLOWERS;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectJoinStep;

import com.moseeker.common.dbconnection.DatabaseConnectionHelper;
import com.moseeker.thrift.gen.companyfollowers.Companyfollower;
import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerQuery;

public class CompanyfollowerDaoImpl {

	public List<Companyfollower> getCompanyfollowers(
			CompanyfollowerQuery query) throws SQLException {
		
		List<Companyfollower> companyfollowers = new ArrayList<>();
		DSLContext create = DatabaseConnectionHelper.getConnection().getJooqDSL();

		SelectJoinStep<Record> table = create.select().from(COMPANYFOLLOWERS);

		if (query.getLimit() > 0) {
			table.limit(query.getLimit());
		} else {
			table.limit(10);
		}

		if (query.getUserid() > 0) {
			table.where(COMPANYFOLLOWERS.USERID.equal(query.getUserid()));
		}

		Result<Record> result = table.fetch();
		for (Record r : result) {
			// System.out.print(r);
			Companyfollower follower = new Companyfollower();
			follower.setId(r.getValue(COMPANYFOLLOWERS.ID));
			follower.setUserid(r.getValue(COMPANYFOLLOWERS.USERID));
			follower.setCompanyid(r.getValue(COMPANYFOLLOWERS.COMPANYID));
			companyfollowers.add(follower);
		}
		return companyfollowers;
	}

	public int postCompanyfollowers(int userid, int companyid) throws SQLException {
		int insertret = 0;
		DSLContext create = DatabaseConnectionHelper.getConnection().getJooqDSL();

		DateFormat dateFormatterChina = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);// 格式化输出
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");// 获取时区
		dateFormatterChina.setTimeZone(timeZoneChina);// 设置系统时区

		// insert
		insertret = create
				.insertInto(COMPANYFOLLOWERS, COMPANYFOLLOWERS.USERID, COMPANYFOLLOWERS.COMPANYID,
						COMPANYFOLLOWERS.CREATE_TIME)
				.values(userid, companyid, new Timestamp(System.currentTimeMillis())).execute();
		return insertret;
	}

	public int delCompanyfollowers(int userid, int companyid) throws SQLException {
		int delret = 0;
		// Connection is the only JDBC resource that we need
		// PreparedStatement and ResultSet are handled by jOOQ, internally
		DSLContext create = DatabaseConnectionHelper.getConnection().getJooqDSL();

		// delete
		delret = create.delete(COMPANYFOLLOWERS).where(COMPANYFOLLOWERS.USERID.equal(userid))
				.and(COMPANYFOLLOWERS.COMPANYID.equal(companyid)).execute();
		System.out.println("delete result: " + delret);

		return delret;
	}


	
}
