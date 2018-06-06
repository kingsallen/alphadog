package com.moseeker.entity;

import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserHrAccount;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
