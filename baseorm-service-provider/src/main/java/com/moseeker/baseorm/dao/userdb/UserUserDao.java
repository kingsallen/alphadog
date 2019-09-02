package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobApplication;
import com.moseeker.baseorm.db.userdb.Userdb;
import com.moseeker.baseorm.db.userdb.tables.*;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.constants.UserSource;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.useraccounts.struct.User;
import org.jooq.*;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        return records;
    }

    public UserUserRecord getUserById(int id) {
        UserUserRecord record = null;
        try {
            Result<UserUserRecord> result = create.selectFrom(UserUser.USER_USER)
                    .where(UserUser.USER_USER.ID.equal(id))
                    .and(UserUser.USER_USER.IS_DISABLE.equal((byte) 0)).fetch();
            if (result != null && result.size() > 0) {
                record = result.get(0);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return record;
    }

    public UserUserRecord getUserByIdAndSource(int id,int source) {
        UserUserRecord record = null;
        try {
            Result<UserUserRecord> result = create.selectFrom(UserUser.USER_USER)
                    .where(UserUser.USER_USER.ID.equal(id))
                    .and(UserUser.USER_USER.IS_DISABLE.equal((byte) source)).fetch();
            if (result != null && result.size() > 0) {
                record = result.get(0);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return record;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void combineAccount(int orig, int dest) throws Exception {
        /*create.update(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD)
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
                .execute();*/
        create.update(UserWxUser.USER_WX_USER)
                .set(UserWxUser.USER_WX_USER.SYSUSER_ID, orig)
                .where(UserWxUser.USER_WX_USER.SYSUSER_ID.equal(dest))
                .execute();
        logger.info("BindOnAccountService combineAccount change wx_user_wx.sysuser_id from {} to {}", dest, orig);
        /*create.update(UserWxViewer.USER_WX_VIEWER)
                .set(UserWxViewer.USER_WX_VIEWER.SYSUSER_ID, orig)
                .where(UserWxViewer.USER_WX_VIEWER.SYSUSER_ID.equal(dest))
                .execute();
        create.update(CandidateCompany.CANDIDATE_COMPANY)
                .set(CandidateCompany.CANDIDATE_COMPANY.SYS_USER_ID, (int)(orig))
                .where(CandidateCompany.CANDIDATE_COMPANY.SYS_USER_ID.equal((int)(dest)))
                .execute();*/
        /*create.update(JobApplication.JOB_APPLICATION)
                .set(JobApplication.JOB_APPLICATION.APPLIER_ID, (int)(orig))
                .where(JobApplication.JOB_APPLICATION.APPLIER_ID.equal((int)(dest)))
                .execute();*/
        /*create.update(UserEmployee.USER_EMPLOYEE)
                .set(UserEmployee.USER_EMPLOYEE.SYSUSER_ID, orig)
                .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.equal(dest))
                .execute();*/
        mergeData(create, orig, dest);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void combineAccountBd(int orig, int dest) throws Exception {
        /*create.update(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD)
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
                .execute();*/
        create.update(UserBdUser.USER_BD_USER)
                .set(UserBdUser.USER_BD_USER.USER_ID, orig)
                .where(UserBdUser.USER_BD_USER.USER_ID.equal(dest))
                .execute();
        mergeData(create, orig, dest);
    }

    private void mergeData(DSLContext create, int orig, int dest) {

        /*try {
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
        }*/
        /*try {
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
        }*/

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
        }
        try {
            create.update(UserFavPosition.USER_FAV_POSITION)
                    .set(UserFavPosition.USER_FAV_POSITION.SYSUSER_ID, orig)
                    .where(UserFavPosition.USER_FAV_POSITION.SYSUSER_ID.equal(dest))
                    .execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }*/
        try {
            create.update(UserIntention.USER_INTENTION)
                    .set(UserIntention.USER_INTENTION.SYSUSER_ID, orig)
                    .where(UserIntention.USER_INTENTION.SYSUSER_ID.equal(dest))
                    .execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        /*try {
            create.update(UserWxViewer.USER_WX_VIEWER)
                    .set(UserWxViewer.USER_WX_VIEWER.SYSUSER_ID, orig)
                    .where(UserWxViewer.USER_WX_VIEWER.SYSUSER_ID.equal(dest))
                    .execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }*/

        create.update(JobApplication.JOB_APPLICATION)
                .set(JobApplication.JOB_APPLICATION.APPLIER_ID, orig)
                .where(JobApplication.JOB_APPLICATION.APPLIER_ID.equal(dest))
                .execute();
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


    public UserUserDO getUser(int userId){
        com.moseeker.common.util.query.Query query = new Query.QueryBuilder().where(UserUser.USER_USER.ID.getName(),userId).buildQuery();
        return getData(query);
    }

    public Object customSelect(String tableName, String selectColumn, int userId) {
        String sql = "select " + selectColumn + " from " + Userdb.USERDB.getName() + "." + tableName + " where id = " + userId;
        Record result = create.fetchOne(sql);
        return result == null ? "" : result.get(selectColumn);
    }

    /**
     * 根据用户名称查找制定范围内的用户
     * @param userIdList 制定范围内的用户编号
     * @param keyword
     * @return
     */
    public List<Integer> fetchIdByIdListAndName(List<Integer> userIdList, String keyword) {
        if (userIdList == null || userIdList.size() == 0) {
            return new ArrayList<>();
        }
        String key = "%"+keyword+"%";
        Result<Record1<Integer>> result = create.select(UserUser.USER_USER.ID)
                .from(UserUser.USER_USER)
                .where(UserUser.USER_USER.ID.in(userIdList))
                .and(UserUser.USER_USER.NAME.like(key))
                .fetch();

        Result<Record1<Integer>> result1 = create.select(UserUser.USER_USER.ID)
                .from(UserUser.USER_USER)
                .where(UserUser.USER_USER.ID.in(userIdList))
                .and(UserUser.USER_USER.NAME.isNull().or(UserUser.USER_USER.NAME.eq("")))
                .and(UserUser.USER_USER.NICKNAME.like(key))
                .fetch();
        if (result == null) {
            result = result1;
        } else {
            result.addAll(result1);
        }
        if (result != null) {
            return result
                    .stream()
                    .map(record -> record.value1())
                    .distinct()
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public List<String> fetchIdByIdListAndName(List<Integer> chatUserIdList, String keyword, int pageSize) {
        if (chatUserIdList == null || chatUserIdList.size() == 0) {
            return new ArrayList<>();
        }
        String key = "%"+keyword+"%";
        Result<Record1<String>> result = create.select(UserUser.USER_USER.NAME)
                .from(UserUser.USER_USER)
                .where(UserUser.USER_USER.ID.in(chatUserIdList))
                .and(UserUser.USER_USER.NAME.like(key))
                .fetch();

        Result<Record1<String>> result1 = create.select(UserUser.USER_USER.NICKNAME)
                .from(UserUser.USER_USER)
                .where(UserUser.USER_USER.ID.in(chatUserIdList))
                .and(UserUser.USER_USER.NAME.isNull().or(UserUser.USER_USER.NAME.eq("")))
                .and(UserUser.USER_USER.NICKNAME.like(key))
                .fetch();

        if (result != null) {
            result.addAll(result1);
        } else {
            result = result1;
        }

        if (result != null) {
            return result
                    .stream()
                    .map(record -> record.value1())
                    .distinct()
                    .limit(pageSize)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public UserUserDO getUnIdentifyUserByMobile(String mobile) {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(UserUser.USER_USER.MOBILE.getName(), mobile)
                .and(UserUser.USER_USER.SOURCE.getName(), 103);
        return getData(queryBuilder.buildQuery());
    }

    /**
     * 根据手机号码查找按照公司隔离的用户信息。目前人才库上传和员工主动上传是按照公司隔离。
     * @param phone 手机号码
     * @param countryCode 国家代码
     * @return 用户集合
     */
    public List<UserUserRecord> getCompanyUserUser(String phone, String countryCode) {
        List<Short> sources = new ArrayList<>();
        sources.add((short) UserSource.TALENT_UPLOAD.getValue());
        sources.add((short) UserSource.EMPLOYEE_REFERRAL_CHATBOT.getValue());
        sources.add((short) UserSource.EMPLOYEE_REFERRAL.getValue());
        return create
                .selectFrom(UserUser.USER_USER)
                .where(UserUser.USER_USER.MOBILE.eq(Long.valueOf(phone)))
                .and(UserUser.USER_USER.COUNTRY_CODE.eq(countryCode))
                .and(UserUser.USER_USER.SOURCE.in(sources))
                .and(UserUser.USER_USER.IS_DISABLE.eq((byte) AbleFlag.OLDENABLE.getValue()))
                .fetch();
    }

    /**
     * 根据手机号码查找内推用户
     * @param phone 手机号码
     * @param countryCode 国家代码
     * @return 用户信息
     */
    public List<UserUserRecord> getReferralUser(String phone, String countryCode, short source) {
        return create
                .selectFrom(UserUser.USER_USER)
                .where(UserUser.USER_USER.MOBILE.eq(Long.valueOf(phone)))
                .and(UserUser.USER_USER.COUNTRY_CODE.eq(countryCode))
                .and(UserUser.USER_USER.SOURCE.eq(source))
                .and(UserUser.USER_USER.IS_DISABLE.eq((byte) AbleFlag.OLDENABLE.getValue()))
                .fetch();
    }

    /**
     * 根据手机号码查找内推用户
     * @param phone 手机号码
     * @param countryCode 国家代码
     * @return 用户信息
     */
    public List<UserUserRecord> getReferralUser(String phone, String countryCode) {
        return create
                .selectFrom(UserUser.USER_USER)
                .where(UserUser.USER_USER.MOBILE.eq(Long.valueOf(phone)))
                .and(UserUser.USER_USER.COUNTRY_CODE.eq(countryCode))
                .and(UserUser.USER_USER.SOURCE.eq((short)UserSource.EMPLOYEE_REFERRAL.getValue()))
                .and(UserUser.USER_USER.IS_DISABLE.eq((byte) AbleFlag.OLDENABLE.getValue()))
                .fetch();
    }

    public List<UserUserRecord> fetchByIdList(List<Integer> userIdList) {
        return create
                .selectFrom(UserUser.USER_USER)
                .where(UserUser.USER_USER.ID.in(userIdList))
                .fetch();
    }

    public List<UserUserRecord> fetchByName(String queryName) {
        return create
                .selectFrom(UserUser.USER_USER)
                .where(UserUser.USER_USER.NAME.eq(queryName))
                .and(UserUser.USER_USER.IS_DISABLE.eq((byte) AbleFlag.OLDENABLE.getValue()))
                .fetch();
    }

    public List<UserUserDO> fetchDOByIdList(List<Integer> userIdList) {
        return create
                .selectFrom(UserUser.USER_USER)
                .where(UserUser.USER_USER.ID.in(userIdList))
                .fetchInto(UserUserDO.class);
    }

    public List<UserUserRecord> fetchByUnionid(String unionid){
        return create
                .selectFrom(UserUser.USER_USER)
                .where(UserUser.USER_USER.UNIONID.eq(unionid))
                .fetch();
    }
}
