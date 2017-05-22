package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateCompany;
import com.moseeker.baseorm.db.candidatedb.tables.CandidatePositionShareRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrWxHrChatList;
import com.moseeker.baseorm.db.jobdb.tables.JobApplication;
import com.moseeker.baseorm.db.userdb.tables.*;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.useraccounts.struct.User;
import org.jooq.Condition;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
* @author xxx
* UserUserDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class UserUserDao extends JooqCrudImpl<UserUserDO, UserUserRecord> {

    public UserUserDao() {
        super(UserUser.USER_USER, UserUserDO.class);
    }

    public UserUserDao(TableImpl<UserUserRecord> table, Class<UserUserDO> userUserDOClass) {
        super(table, userUserDOClass);
    }

    public List<UserUserRecord> getUserByIds(List<Integer> cityCodes) {

        List<UserUserRecord> records = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            SelectWhereStep<UserUserRecord> select = create.selectFrom(UserUser.USER_USER);
            SelectConditionStep<UserUserRecord> selectCondition = null;
            if (cityCodes != null && cityCodes.size() > 0) {
                for (int i = 0; i < cityCodes.size(); i++) {
                    if (i == 0) {
                        selectCondition = select.where(UserUser.USER_USER.ID.equal((int)(cityCodes.get(i))));
                    } else {
                        selectCondition.or(UserUser.USER_USER.ID.equal((int)(cityCodes.get(i))));
                    }
                }
            }
            records = selectCondition.fetch();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            } finally {
                // do nothing
            }
        }

        return records;
    }

    public UserUserRecord getUserById(int id) {
        UserUserRecord record = null;
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            Result<UserUserRecord> result = create.selectFrom(UserUser.USER_USER)
                    .where(UserUser.USER_USER.ID.equal((int) (id)))
                    .and(UserUser.USER_USER.IS_DISABLE.equal((byte) 0)).fetch();
            if (result != null && result.size() > 0) {
                record = result.get(0);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            } finally {
                // do nothing
            }
        }

        return record;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void combineAccount(int orig, int dest) throws Exception {
        create.update(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD)
                .set(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.RECOM_USER_ID, (int)(orig))
                .where(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.RECOM_USER_ID.equal((int)(dest)))
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
                .set(CandidateCompany.CANDIDATE_COMPANY.SYS_USER_ID, (int)(orig))
                .where(CandidateCompany.CANDIDATE_COMPANY.SYS_USER_ID.equal((int)(dest)))
                .execute();
        create.update(JobApplication.JOB_APPLICATION)
                .set(JobApplication.JOB_APPLICATION.APPLIER_ID, (int)(orig))
                .where(JobApplication.JOB_APPLICATION.APPLIER_ID.equal((int)(dest)))
                .execute();
        create.update(UserEmployee.USER_EMPLOYEE)
                .set(UserEmployee.USER_EMPLOYEE.SYSUSER_ID, orig)
                .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.equal(dest))
                .execute();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void combineAccountBd(int orig, int dest) throws Exception {
        create.update(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD)
                .set(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.RECOM_USER_ID, (int)(orig))
                .where(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.RECOM_USER_ID.equal((int)(dest)))
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
        create.update(UserBdUser.USER_BD_USER)
                .set(UserBdUser.USER_BD_USER.USER_ID, orig)
                .where(UserBdUser.USER_BD_USER.USER_ID.equal(dest))
                .execute();
        create.update(UserWxViewer.USER_WX_VIEWER)
                .set(UserWxViewer.USER_WX_VIEWER.SYSUSER_ID, orig)
                .where(UserWxViewer.USER_WX_VIEWER.SYSUSER_ID.equal(dest))
                .execute();
        create.update(CandidateCompany.CANDIDATE_COMPANY)
                .set(CandidateCompany.CANDIDATE_COMPANY.SYS_USER_ID, (int)(orig))
                .where(CandidateCompany.CANDIDATE_COMPANY.SYS_USER_ID.equal((int)(dest)))
                .execute();
        create.update(JobApplication.JOB_APPLICATION)
                .set(JobApplication.JOB_APPLICATION.APPLIER_ID, (int)(orig))
                .where(JobApplication.JOB_APPLICATION.APPLIER_ID.equal((int)(dest)))
                .execute();
        create.update(UserEmployee.USER_EMPLOYEE)
                .set(UserEmployee.USER_EMPLOYEE.SYSUSER_ID, orig)
                .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.equal(dest))
                .execute();
    }

    /**
     * 创建用户
     *
     * @param user 用户的thrift struct 实体
     *
     * */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int createUser(com.moseeker.thrift.gen.useraccounts.struct.User user) throws Exception{

        // 用户记录转换
        UserUserRecord userUserRecord = (UserUserRecord) BeanUtils.structToDB(user, UserUserRecord.class);

        // 添加用户数据
        create.attach(userUserRecord);
        userUserRecord.insert();

        // 根据用户数据初始化用户配置表
        create.insertInto(UserSettings.USER_SETTINGS, UserSettings.USER_SETTINGS.USER_ID)
                .values(userUserRecord.getId())
                .execute();

        return userUserRecord.getId().intValue();
    }

    /**
     * 获取用户数据
     * <p>
     *
     * @param userId 用户ID
     *
     * */
    public User getUserById(long userId) throws Exception{
        Condition condition = UserUser.USER_USER.ID.equal((int) (userId));
        return create.selectFrom(UserUser.USER_USER).where(condition).limit(1).fetchOne().into(User.class);
    }
}
