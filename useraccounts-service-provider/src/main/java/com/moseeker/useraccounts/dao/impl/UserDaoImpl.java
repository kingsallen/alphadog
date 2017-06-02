package com.moseeker.useraccounts.dao.impl;

import java.sql.Connection;

import com.moseeker.db.candidatedb.tables.*;
import com.moseeker.db.hrdb.tables.HrChatUnreadCount;
import com.moseeker.db.userdb.tables.*;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.hrdb.tables.HrWxHrChatList;
import com.moseeker.db.jobdb.tables.JobApplication;
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
            create.update(UserWxUser.USER_WX_USER)
                    .set(UserWxUser.USER_WX_USER.SYSUSER_ID, orig)
                    .where(UserWxUser.USER_WX_USER.SYSUSER_ID.equal(dest))
                    .execute();
            conn.commit();
            conn.setAutoCommit(true);
    	}  catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    
    public void combineAccountBd(int orig, int dest) throws Exception {
		try(Connection conn = DBConnHelper.DBConn.getConn(); DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);) {
            create.update(UserBdUser.USER_BD_USER)
                    .set(UserBdUser.USER_BD_USER.USER_ID, orig)
                    .where(UserBdUser.USER_BD_USER.USER_ID.equal(dest))
                    .execute();
	        conn.commit();
	        conn.setAutoCommit(true);
		}  catch (Exception e) {
	        logger.error(e.getMessage(), e);
	    }
    }

    private void mergeData(int orig, int dest) {
        try {
            create.update(CandidateCompany.CANDIDATE_COMPANY)
                    .set(CandidateCompany.CANDIDATE_COMPANY.SYS_USER_ID, UInteger.valueOf(orig))
                    .where(CandidateCompany.CANDIDATE_COMPANY.SYS_USER_ID.equal(UInteger.valueOf(dest)))
                    .execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            create.update(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD)
                    .set(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.RECOM_USER_ID, UInteger.valueOf(orig))
                    .where(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.RECOM_USER_ID.equal(UInteger.valueOf(dest)))
                    .execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            create.update(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD)
                    .set(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.PRESENTEE_USER_ID, UInteger.valueOf(orig))
                    .where(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.PRESENTEE_USER_ID.equal(UInteger.valueOf(dest)))
                    .execute();
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
        }
        try {
            create.update(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                    .set(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID, UInteger.valueOf(orig))
                    .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID.equal(UInteger.valueOf(dest)))
                    .execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            create.update(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                    .set(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.REPOST_USER_ID, UInteger.valueOf(orig))
                    .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.REPOST_USER_ID.equal(UInteger.valueOf(dest)))
                    .execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            create.update(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                    .set(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID, UInteger.valueOf(orig))
                    .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(UInteger.valueOf(dest)))
                    .execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            create.update(CandidateRemark.CANDIDATE_REMARK)
                    .set(CandidateRemark.CANDIDATE_REMARK.USER_ID, UInteger.valueOf(orig))
                    .where(CandidateRemark.CANDIDATE_REMARK.USER_ID.equal(UInteger.valueOf(dest)))
                    .execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            create.update(CandidateShareChain.CANDIDATE_SHARE_CHAIN)
                    .set(CandidateShareChain.CANDIDATE_SHARE_CHAIN.ROOT_RECOM_USER_ID, UInteger.valueOf(orig))
                    .where(CandidateShareChain.CANDIDATE_SHARE_CHAIN.ROOT_RECOM_USER_ID.equal(UInteger.valueOf(dest)))
                    .execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            create.update(CandidateShareChain.CANDIDATE_SHARE_CHAIN)
                    .set(CandidateShareChain.CANDIDATE_SHARE_CHAIN.ROOT2_RECOM_USER_ID, UInteger.valueOf(orig))
                    .where(CandidateShareChain.CANDIDATE_SHARE_CHAIN.ROOT2_RECOM_USER_ID.equal(UInteger.valueOf(dest)))
                    .execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            create.update(CandidateShareChain.CANDIDATE_SHARE_CHAIN)
                    .set(CandidateShareChain.CANDIDATE_SHARE_CHAIN.RECOM_USER_ID, UInteger.valueOf(orig))
                    .where(CandidateShareChain.CANDIDATE_SHARE_CHAIN.RECOM_USER_ID.equal(UInteger.valueOf(dest)))
                    .execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            create.update(CandidateShareChain.CANDIDATE_SHARE_CHAIN)
                    .set(CandidateShareChain.CANDIDATE_SHARE_CHAIN.PRESENTEE_USER_ID, UInteger.valueOf(orig))
                    .where(CandidateShareChain.CANDIDATE_SHARE_CHAIN.PRESENTEE_USER_ID.equal(UInteger.valueOf(dest)))
                    .execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        //聊天室暂时不迁移
        /*try {
            create.update(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT)
                    .set(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.USER_ID, UInteger.valueOf(orig))
                    .where(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.USER_ID.equal(UInteger.valueOf(dest)))
                    .execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            create.update(HrWxHrChatList.HR_WX_HR_CHAT_LIST)
                    .set(HrWxHrChatList.HR_WX_HR_CHAT_LIST.SYSUSER_ID, orig)
                    .where(HrWxHrChatList.HR_WX_HR_CHAT_LIST.SYSUSER_ID.equal(dest))
                    .execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }*/

        /*try {
            create.update(UserCompanyFollow.USER_COMPANY_FOLLOW)
                    .set(UserCompanyFollow.USER_COMPANY_FOLLOW.USER_ID, orig)
                    .where(UserCompanyFollow.USER_COMPANY_FOLLOW.USER_ID.equal(dest))
                    .execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }*/
        try {
            create.update(UserFavPosition.USER_FAV_POSITION)
                    .set(UserFavPosition.USER_FAV_POSITION.SYSUSER_ID, orig)
                    .where(UserFavPosition.USER_FAV_POSITION.SYSUSER_ID.equal(dest))
                    .execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            create.update(UserIntention.USER_INTENTION)
                    .set(UserIntention.USER_INTENTION.SYSUSER_ID, orig)
                    .where(UserIntention.USER_INTENTION.SYSUSER_ID.equal(dest))
                    .execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            create.update(UserWxViewer.USER_WX_VIEWER)
                    .set(UserWxViewer.USER_WX_VIEWER.SYSUSER_ID, orig)
                    .where(UserWxViewer.USER_WX_VIEWER.SYSUSER_ID.equal(dest))
                    .execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }


        try {
            create.update(JobApplication.JOB_APPLICATION)
                    .set(JobApplication.JOB_APPLICATION.APPLIER_ID, UInteger.valueOf(orig))
                    .where(JobApplication.JOB_APPLICATION.APPLIER_ID.equal(UInteger.valueOf(dest)))
                    .execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            create.update(UserEmployee.USER_EMPLOYEE)
                    .set(UserEmployee.USER_EMPLOYEE.SYSUSER_ID, orig)
                    .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.equal(dest))
                    .execute();
        } catch (Exception e) {
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
//                        .dao(userUserRecord.getId())
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

			user = create.selectFrom(UserUser.USER_USER).where(condition).limit(1).fetchOne().into(User.class);

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
