package com.moseeker.entity;

import com.moseeker.baseorm.constant.ReferralScene;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolTalentDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserReferralRecordDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolTalentRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserReferralRecordRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.constants.UserSource;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by lucky8987 on 17/6/29.
 */
@Service
public class UserAccountEntity {

    private static final Logger log = LoggerFactory.getLogger(UserAccountEntity.class);

    @Autowired
    private UserUserDao userDao;

    @Autowired
    private UserHrAccountDao userHrAccountDao;

    @Autowired
    private TalentpoolTalentDao talentDao;

    @Autowired
    private UserReferralRecordDao referralRecordDao;

    @Autowired
    private UserWxUserDao wxUserDao;


    /**
     * 获取用户的称呼
     * @param userId 用户id
     * @return
     */
    public String genUsername(int userId) {
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("id", String.valueOf(userId));
        UserUserDO user;
        String username = "";
        try {
            user = userDao.getData(qu.buildQuery());
            if(user != null && user.getUsername() != null) {
                if(StringUtils.isNotNullOrEmpty(user.getName())) {
                    username = user.getName();
                } else if(StringUtils.isNotNullOrEmpty(user.getNickname())) {
                    username = user.getNickname();
                } else {
                    username = user.getUsername();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return username;
    }

    /*
     * 账号合并完善账号信息
	 *
	 * @param userMobile
	 *            需要完善的账号
	 * @param userUnionid
	 *            信息来源
	 * 在useraccount账号和并服务有相关的代码
     */
    public UserUserRecord consummateUserAccount(UserUserRecord userMobile, UserUserRecord userUnionid) {
		/* 完善用户名称 */
        if (StringUtils.isNullOrEmpty(userMobile.getName()) && StringUtils.isNotNullOrEmpty(userUnionid.getName())) {
            userMobile.setName(userUnionid.getName());
        }
		/* 完善用户昵称 */
        if (StringUtils.isNullOrEmpty(userMobile.getNickname())
                && StringUtils.isNotNullOrEmpty(userUnionid.getNickname())) {
            userMobile.setNickname(userUnionid.getNickname());
        }
		/* 完善用户级别，预计rank越高，表示用户等级越高。 */
        if ((userUnionid.getRank() != null && userMobile.getRank() == null) || (userUnionid.getRank() != null
                && userMobile.getRank() != null && userUnionid.getRank() > userMobile.getRank())) {
            userMobile.setRank(userUnionid.getRank());
        }
		/* 完善用户未验证的手机号码 */
        if (userUnionid.getMobile() != null && userUnionid.getMobile() > 0
                && (userMobile.getMobile() == null || userMobile.getMobile() == 0)) {
            userMobile.setMobile(userUnionid.getMobile());
        }
		/* 完善用户邮箱 */
        if (StringUtils.isNullOrEmpty(userMobile.getEmail()) && StringUtils.isNotNullOrEmpty(userUnionid.getEmail())) {
            userMobile.setEmail(userUnionid.getEmail());
        }
		/* 完善用户头像 */
        if (StringUtils.isNullOrEmpty(userMobile.getHeadimg())
                && StringUtils.isNotNullOrEmpty(userUnionid.getHeadimg())) {
            userMobile.setHeadimg(userUnionid.getHeadimg());
        }
		/* 完善国家代码 */
        if (userUnionid.getNationalCodeId() != null && userUnionid.getNationalCodeId() != 1
                && (userMobile.getNationalCodeId() == null || userMobile.getNationalCodeId() == 1)) {
            userMobile.setNationalCodeId(userUnionid.getNationalCodeId());
        }
		/* 完善感兴趣的公司 */
        if (StringUtils.isNullOrEmpty(userMobile.getCompany())
                && StringUtils.isNotNullOrEmpty(userUnionid.getCompany())) {
            userMobile.setCompany(userUnionid.getCompany());
        }
		/* 完善感兴趣的职位 */
        if (StringUtils.isNullOrEmpty(userMobile.getPosition())
                && StringUtils.isNotNullOrEmpty(userUnionid.getPosition())) {
            userMobile.setPosition(userUnionid.getPosition());
        }
        userDao.updateRecord(userMobile);
        return userMobile;
    }
    /*
     合并账号
     */
    public UserUserRecord combineAccount( int  userId, int newUserId) {
        UserUserRecord userUnionid=getUserRecordbyId(userId);
        UserUserRecord userMobile=getUserRecordbyId(newUserId);
        if(userMobile==null){
            return userUnionid;
        }
        if(userUnionid==null){
            return userMobile;
        }
        if(userId==newUserId){
            return userMobile;
        }
        userUnionid.setParentid(userMobile.getId());
        userUnionid.setIsDisable((byte)1);
        if (userDao.updateRecord(userUnionid) > 0) {
            return consummateUserAccount(userMobile, userUnionid);
        }
        return null;
    }
    /*
     根据id获取useruserRecord
     */
    public UserUserRecord getUserRecordbyId(int userId){
        Query query=new Query.QueryBuilder().where("id",userId).and("is_disable",0).buildQuery();
        UserUserRecord userRecord=userDao.getRecord(query);
        return userRecord;
    }

    /**
     * 根据手机号码查找按公司隔离的用户
     * @param phone 手机号码
     * @param companyId 公司编号
     * @return 用户信息
     */
    public UserUserRecord getCompanyUser(String phone, int companyId) {

        UserUserRecord userUserRecord = null;
        String countryCode="86";
        if(phone.contains("-")){
            String [] phoneArray=phone.split("-");
            countryCode=phoneArray[0];
            phone=phoneArray[1];
        }
        List<UserUserRecord> list = userDao.getCompanyUserUser(phone, countryCode);
        if (list != null && list.size() > 0) {

            userUserRecord = findTalent(list, companyId);
            if (userUserRecord == null) {
                userUserRecord = findEmployeeReferral(list, companyId);
            }
        }
        return userUserRecord;
    }
    /**
     * 根据手机号码查找内推用户
     * @param phone 手机号码
     * @param companyId 公司编号
     * @return 用户信息
     */
    public UserUserRecord getReferralUser(String phone, int companyId) {

        UserUserRecord userUserRecord = null;
        String countryCode="86";
        if(phone.contains("-")){
            String [] phoneArray=phone.split("-");
            countryCode=phoneArray[0];
            phone=phoneArray[1];
        }
        List<UserUserRecord> list = userDao.getReferralUser(phone, countryCode);
        if (list != null && list.size() > 0) {
            userUserRecord = findEmployeeReferral(list, companyId);
        }
        return userUserRecord;
    }

    /**
     * 根据手机号码查找内推用户
     * @param phone 手机号码
     * @param companyId 公司编号
     * @return 用户信息
     */
    public UserUserRecord getReferralUser(String phone, int companyId, ReferralScene referralScene) {

        UserUserRecord userUserRecord = null;
        String countryCode="86";
        if(phone.contains("-")){
            String [] phoneArray=phone.split("-");
            countryCode=phoneArray[0];
            phone=phoneArray[1];
        }
       /* short source = (short) UserSource.EMPLOYEE_REFERRAL.getValue();
        if(referralScene.getScene() == ReferralScene.ChatBot.getScene()){
            source = (short) UserSource.EMPLOYEE_REFERRAL_CHATBOT.getValue();
        }*/
        List<UserUserRecord> list = userDao.getAllReferralUser(phone, countryCode);
        if (list != null && list.size() > 0) {
            userUserRecord = findEmployeeReferral(list, companyId);
        }
        return userUserRecord;
    }

    /**
     * 查找制定公司下的员工内推的用户信息
     * @param list 用户编号
     * @param companyId 公司编号
     * @return 用户信息
     */
    private UserUserRecord findEmployeeReferral(List<UserUserRecord> list, int companyId) {
        UserUserRecord userUserRecord = null;
        List<Integer> userIdList = list.stream().map(UserUserRecord::getId).collect(Collectors.toList());
        List<UserReferralRecordRecord> referralRecords = referralRecordDao.getRecordsByUserIdListCompanyId(userIdList, companyId);
        if (referralRecords != null && referralRecords.size() > 0) {

            for (UserUserRecord userRecord : list) {
                Optional<UserReferralRecordRecord> optional = referralRecords
                        .stream()
                        .filter(recordRecord -> recordRecord.getUserId().equals(userRecord.getId()))
                        .findAny();
                if (optional.isPresent()) {
                    userUserRecord = userRecord;
                    break;
                }
            }
        }
        return userUserRecord;
    }

    /**
     * 查找指定公司下的人才的用户信息
     * @param list 用户编号
     * @param companyId 公司编号
     * @return 人才的用户信息
     */
    private UserUserRecord findTalent(List<UserUserRecord> list, int companyId) {
        UserUserRecord userUserRecord = null;
        List<Integer> userIdList = list.stream().map(UserUserRecord::getId).collect(Collectors.toList());
        List<TalentpoolTalentRecord> talents = talentDao.getTalents(userIdList,companyId);

        if (talents != null && talents.size() > 0) {
            for (UserUserRecord userRecord : list) {
                Optional<TalentpoolTalentRecord> optional = talents
                        .stream()
                        .filter(talentpoolTalentRecord -> talentpoolTalentRecord.getUserId().equals(userRecord.getId()))
                        .findAny();
                if (optional.isPresent()) {
                    userUserRecord = userRecord;
                    break;
                }
            }
        }
        return userUserRecord;
    }

    public void updateUserRecord(UserUserRecord userRecord) {
        int  execute = userDao.updateRecord(userRecord);
        log.info("updateUserRecord execute:{}", execute);
    }

    /**
     * 查询用户名称
     * @param idList 用户编号
     * @return 用户编号和用户名称
     */
    public Map<Integer, String> fetchUserName(List<Integer> idList) {
        Map<Integer, String> result = new HashMap<>();
        List<UserUserRecord> userUserRecords = userDao.fetchByIdList(idList);
        if (userUserRecords != null && userUserRecords.size() > 0) {

            List<UserWxUserRecord> wxUserRecords = wxUserDao.getWXUserMapByUserIds(idList);

            userUserRecords.forEach(userUserRecord -> {
                String name = "";
                if (org.apache.commons.lang.StringUtils.isNotBlank(userUserRecord.getName())
                        || org.apache.commons.lang.StringUtils.isNotBlank(userUserRecord.getNickname())) {
                    name = org.apache.commons.lang.StringUtils.isNotBlank(userUserRecord.getName())
                            ? userUserRecord.getName():userUserRecord.getNickname();
                } else {
                    if (wxUserRecords != null && wxUserRecords.size() > 0) {
                        Optional<UserWxUserRecord> optional = wxUserRecords
                                .stream()
                                .filter(userWxUserRecord1 -> userWxUserRecord1.getSysuserId().equals(userUserRecord.getId()))
                                .findAny();
                        if (optional.isPresent()) {
                            name = optional.get().getNickname();
                        }
                    }
                }
                result.put(userUserRecord.getId(), name);
            });
        }

        return result;
    }
}
