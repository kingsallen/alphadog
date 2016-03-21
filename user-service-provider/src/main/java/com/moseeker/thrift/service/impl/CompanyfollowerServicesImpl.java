package com.moseeker.thrift.service.impl;

import org.apache.thrift.TException;

import com.moseeker.thrift.gen.companyfollowers.Companyfollower;
import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerServices;
import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerQuery;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static com.moseeker.db.userdb.tables.Companyfollowers.COMPANYFOLLOWERS;
import com.moseeker.db.userdb.tables.records.CompanyfollowersRecord;

import java.sql.*;

import org.jooq.*;
import org.jooq.impl.*;

public class CompanyfollowerServicesImpl implements
CompanyfollowerServices.Iface {

	private static String userName = "www";
	private static String password = "moseeker.com";
	private static String url = "jdbc:mysql://192.168.31.66:3306/";
	
	@Override
	public List<Companyfollower> getCompanyfollowers(CompanyfollowerQuery query)
			throws TException {
		// TODO Auto-generated method stub
		int appid = query.getAppid();
		List<Companyfollower> companyfollowers = new ArrayList<Companyfollower>();
		
		try (Connection conn = DriverManager.getConnection(url, userName,
				password)) {
			DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
			SelectJoinStep<Record> table = create.select().from(COMPANYFOLLOWERS);			
			
			if (query.getLimit() > 0 ){
				table.limit(query.getLimit());
			}else{
				table.limit(10);
			}
			
			if (query.getUserid() > 0 ){
				table.where(COMPANYFOLLOWERS.USERID.equal(query.getUserid()));
			}
			
			Result<Record> result = table.fetch();
			for (Record r : result) {
				//System.out.print(r);
				Companyfollower follower = new Companyfollower();
				follower.setId(r.getValue(COMPANYFOLLOWERS.ID));
				follower.setUserid(r.getValue(COMPANYFOLLOWERS.USERID));
				follower.setCompanyid(r.getValue(COMPANYFOLLOWERS.COMPANYID));
				companyfollowers.add(follower);
			}
						
		}
		// For the sake of this tutorial, let's keep exception handling simple
		catch (Exception e) {
			e.printStackTrace();
		}
		return companyfollowers;
	}

	@Override
	public int postCompanyfollowers(int userid, int companyid)
			throws TException {
		// TODO Auto-generated method stub

		int insertret = 0;
		// Connection is the only JDBC resource that we need
		// PreparedStatement and ResultSet are handled by jOOQ, internally
		try (Connection conn = DriverManager.getConnection(url, userName,
				password)) {
			DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

			DateFormat dateFormatterChina = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);//格式化输出
			TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");//获取时区
			dateFormatterChina.setTimeZone(timeZoneChina);//设置系统时区
			
			// insert
			insertret = create
					.insertInto(COMPANYFOLLOWERS, COMPANYFOLLOWERS.USERID,COMPANYFOLLOWERS.COMPANYID, COMPANYFOLLOWERS.CREATE_TIME)
					.values(userid, companyid, new Timestamp(System.currentTimeMillis()))
					.execute();
			System.out.println(create
					.insertInto(COMPANYFOLLOWERS, COMPANYFOLLOWERS.USERID,COMPANYFOLLOWERS.COMPANYID)
					.values(userid, companyid).getSQL());
			
			System.out.println("insert result: " + insertret);
		}
		// For the sake of this tutorial, let's keep exception handling simple
		catch (Exception e) {
			e.printStackTrace();
		}
		return insertret;
	}

	@Override
	public int delCompanyfollowers(int userid, int companyid) throws TException {
		// TODO Auto-generated method stub
		int delret = 0;
		// Connection is the only JDBC resource that we need
		// PreparedStatement and ResultSet are handled by jOOQ, internally
		try (Connection conn = DriverManager.getConnection(url, userName,
				password)) {
			DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

			// delete
			delret = create.delete(COMPANYFOLLOWERS)
					.where(COMPANYFOLLOWERS.USERID.equal(userid))
					.and(COMPANYFOLLOWERS.COMPANYID.equal(companyid)).execute();
			System.out.println("delete result: " + delret);

		}
		// For the sake of this tutorial, let's keep exception handling simple
		catch (Exception e) {
			e.printStackTrace();
		}
		return delret;
	}

}
