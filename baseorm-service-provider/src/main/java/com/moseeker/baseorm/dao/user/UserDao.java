package com.moseeker.baseorm.dao.user;

import java.sql.Connection;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.userdb.tables.UserUser;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.thrift.gen.useraccounts.struct.User;
/**
 * 
 * HR帐号数据库持久类 
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 9, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
@Service
public class UserDao extends BaseDaoImpl<UserUserRecord, UserUser> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = UserUser.USER_USER;
	}

	/**
	 * 对用户数据持久化，并返回结果
	 * @param user
	 * @return
	 */
	public User saveUser(User user) {
		UserUserRecord record = (UserUserRecord)BeanUtils.structToDB(user, UserUserRecord.class);
		try (
				Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				) {
			create.attach(record);
			record.insert();
			record.into(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		return user;
	}

}
