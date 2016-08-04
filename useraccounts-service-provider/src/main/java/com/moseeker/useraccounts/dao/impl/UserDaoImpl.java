package com.moseeker.useraccounts.dao.impl;

import java.sql.Connection;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.candidatedb.tables.CandidateCompany;
import com.moseeker.db.candidatedb.tables.CandidatePositionShareRecord;
import com.moseeker.db.hrdb.tables.HrWxHrChatList;
import com.moseeker.db.jobdb.tables.JobApplication;
import com.moseeker.db.userdb.tables.UserEmployee;
import com.moseeker.db.userdb.tables.UserFavPosition;
import com.moseeker.db.userdb.tables.UserIntention;
import com.moseeker.db.userdb.tables.UserSettings;
import com.moseeker.db.userdb.tables.UserUser;
import com.moseeker.db.userdb.tables.UserWxUser;
import com.moseeker.db.userdb.tables.UserWxViewer;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.useraccounts.dao.UserDao;
import com.moseeker.useraccounts.pojo.User;

/**
 * 用户数据接口
 *
 *
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<UserUserRecord, UserUser> implements UserDao {

    private Connection conn = null;

    private DSLContext create = null;

    @Override
    protected void initJOOQEntity() {
        this.tableLike = UserUser.USER_USER;
    }

    @Override
    public void combineAccount(int orig, int dest) throws Exception {
    	try(Connection conn = DBConnHelper.DBConn.getConn(); DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);) {
    		conn.setAutoCommit(false);
    		create.update(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD)
            .set(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.SYSUSER_ID, Long.valueOf(orig))
            .where(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.SYSUSER_ID.equal(Long.valueOf(dest)))
            .execute();
            create.update(HrWxHrChatList.HR_WX_HR_CHAT_LIST)
            .set(HrWxHrChatList.HR_WX_HR_CHAT_LIST.SYSUSER_ID, orig)
            .where(HrWxHrChatList.HR_WX_HR_CHAT_LIST.SYSUSER_ID.equal(dest))
            .execute();
            create.update(UserFavPosition.USER_FAV_POSITION)
            .set(UserFavPosition.USER_FAV_POSITION.SYSUSER_ID, orig)
            .where(UserFavPosition.USER_FAV_POSITION.SYSUSER_ID.equal(dest))
            .execute();
            create.update(UserIntention.USER_INTENTION)
            .set(UserIntention.USER_INTENTION.SYSUSER_ID, orig)
            .where(UserIntention.USER_INTENTION.SYSUSER_ID.equal(dest))
            .execute();
            create.update(UserWxUser.USER_WX_USER)
            .set(UserWxUser.USER_WX_USER.SYSUSER_ID, orig)
            .where(UserWxUser.USER_WX_USER.SYSUSER_ID.equal(dest))
            .execute();
            create.update(UserWxViewer.USER_WX_VIEWER)
            .set(UserWxViewer.USER_WX_VIEWER.SYSUSER_ID, orig)
            .where(UserWxViewer.USER_WX_VIEWER.SYSUSER_ID.equal(dest))
            .execute();
            create.update(CandidateCompany.CANDIDATE_COMPANY)
            .set(CandidateCompany.CANDIDATE_COMPANY.SYS_USER_ID, orig)
            .where(CandidateCompany.CANDIDATE_COMPANY.SYS_USER_ID.equal(dest))
            .execute();
            create.update(JobApplication.JOB_APPLICATION)
            .set(JobApplication.JOB_APPLICATION.APPLIER_ID, UInteger.valueOf(orig))
            .where(JobApplication.JOB_APPLICATION.APPLIER_ID.equal(UInteger.valueOf(dest)))
            .execute();
            create.update(UserEmployee.USER_EMPLOYEE)
            .set(UserEmployee.USER_EMPLOYEE.SYSUSER_ID, orig)
            .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.equal(dest))
            .execute();
            conn.commit();
            conn.setAutoCommit(true);
    	}  catch (Exception e) {
        	conn.rollback();
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 创建用户
     *
     * @param user 用户的thrift struct 实体
     *
     * */
    public int createUser(com.moseeker.thrift.gen.useraccounts.struct.User user) throws Exception{

        try{
            conn = DBConnHelper.DBConn.getConn();
            create = DBConnHelper.DBConn.getJooqDSL(conn);

            // 用户记录转换
            UserUserRecord userUserRecord = (UserUserRecord) BeanUtils.structToDB(user, UserUserRecord.class);

//            create.transaction(cofiguratioon -> {
//
//                // 添加用户数据
//                create.attach(userUserRecord);
//                userUserRecord.insert();
//
//                // 根据用户数据初始化用户配置表
//                create.insertInto(UserSettings.USER_SETTINGS, UserSettings.USER_SETTINGS.USER_ID)
//                        .values(userUserRecord.getId())
//                        .execute();
//
//            });

            // TODO 事务不好使, 需要研究一下

            // 添加用户数据
            create.attach(userUserRecord);
            userUserRecord.insert();

            // 根据用户数据初始化用户配置表
            create.insertInto(UserSettings.USER_SETTINGS, UserSettings.USER_SETTINGS.USER_ID)
                    .values(userUserRecord.getId())
                    .execute();

            return userUserRecord.getId().intValue();
        }catch (Exception e){
            logger.error(e.getMessage(), e);

        } finally {
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        return 0;
    }

    /**
     * 获取用户数据
     * <p>
     *
     * @param userId 用户ID
     *
     * */
    @Override
    public User getUserById(long userId) throws Exception{
        Condition condition = null;
        User user = null;
        try{
            conn = DBConnHelper.DBConn.getConn();
            create = DBConnHelper.DBConn.getJooqDSL(conn);

            condition = UserUser.USER_USER.ID.equal(UInteger.valueOf(userId));

			user = create.select().from(UserUser.USER_USER).where(condition).limit(1).fetchOne().into(User.class);

        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            if(conn != null && !conn.isClosed()) {
            	 conn.close();
            }
        }
        return user;
    }
}
