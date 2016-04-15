package com.moseeker.service.impl;

import static com.moseeker.db.userdb.tables.Companyfollowers.COMPANYFOLLOWERS;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.jooq.DSLContext;
import org.jooq.Record;

import com.moseeker.common.dbconnection.DatabaseConnectionHelper;
import com.moseeker.common.redis.RedisCallback;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.dao.CompanyfollowerDaoImpl;
import com.moseeker.thrift.gen.companyfollowers.Companyfollower;
import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerQuery;
import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerServices;

public class CompanyfollowerServicesImpl implements CompanyfollowerServices.Iface {

	private CompanyfollowerDaoImpl dao = new CompanyfollowerDaoImpl();
	
	@Override
	public List<Companyfollower> getCompanyfollowers(CompanyfollowerQuery query) throws TException {
		List<Companyfollower> companyfollowers = new ArrayList<>();
		//参数校验 对用户输入的参数做校验，可能需要适当做一些修改
		ValidateUtil vu = new ValidateUtil();
		vu.addRequiredValidate("项目编号", query.getAppid());
		String message = vu.validate();
		if(StringUtils.isNullOrEmpty(message)) {
			try {
				companyfollowers = dao.getCompanyfollowers(query);
			} catch (SQLException e) {
				throw new TException(e.getMessage());
			} finally {
				
			}
		} else {
			//
		}
		//其他业务，比如邮件通知等
		return companyfollowers;
	}

	@Override
	public int postCompanyfollowers(int userid, int companyid) throws TException {
		// TODO Auto-generated method stub

		int insertret = 0;
		//参数校验
		try {
			insertret = dao.postCompanyfollowers(userid, companyid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new TException(e.getMessage());
			//异常处理
		}
		return insertret;
	}

	@Override
	public int delCompanyfollowers(int userid, int companyid) throws TException {
		// TODO Auto-generated method stub
		int delret = 0;
		try {
			delret = dao.delCompanyfollowers(userid, companyid);
		} catch (SQLException e) {
			throw new TException(e.getMessage());
		}
		return delret;
	}

	public static void main(String[] args) {

		RedisClient client = RedisClientFactory.getCacheClient();
		String str = client.get(0, "DEFAULT", "user1", new RedisCallback() {
			@Override
			public String call() {

				DSLContext create;
				try {
					create = DatabaseConnectionHelper.getConnection().getJooqDSL();

					Record ret = create.select().from(COMPANYFOLLOWERS).where(COMPANYFOLLOWERS.USERID.equal(11))
							.fetchAny();
					System.out.println("ret result: " + ret);
					return String.valueOf(ret.getValue(COMPANYFOLLOWERS.ID));

				}
				// For the sake of this tutorial, let's keep exception handling
				// simple
				catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});

		System.out.print(str);
	}

}
