package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.db.userdb.tables.UserWxUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.*;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import static com.moseeker.baseorm.db.userdb.tables.UserWxUser.USER_WX_USER;
/**
* @author xxx
* UserWxUserDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class UserWxUserDao extends JooqCrudImpl<UserWxUserDO, UserWxUserRecord> {

    public UserWxUserDao() {
        super(UserWxUser.USER_WX_USER, UserWxUserDO.class);
    }

    public UserWxUserDao(TableImpl<UserWxUserRecord> table, Class<UserWxUserDO> userWxUserDOClass) {
        super(table, userWxUserDOClass);
    }

    public UserWxUserRecord getWXUserByUserId(int userId) {
        UserWxUserRecord wxuser = null;
        if(userId > 0) {
            wxuser = create.selectFrom(UserWxUser.USER_WX_USER)
                    .where(UserWxUser.USER_WX_USER.SYSUSER_ID.equal(userId))
                    .orderBy(UserWxUser.USER_WX_USER.CREATE_TIME.desc())
                    .limit(1)
                    .fetchOne();
        }
        return wxuser;
    }

    public List<UserWxUserRecord> getWXUsersByUserId(int userId) {
        if (userId > 0) {
            return create.selectFrom(UserWxUser.USER_WX_USER)
                    .where(UserWxUser.USER_WX_USER.SYSUSER_ID.eq(userId))
                    .fetch();
        } else {
            return new ArrayList<>();
        }
    }

    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserWxUser> getWXUsersByUserIdAndCompanyId(int userId, int companyId) {
        if (userId > 0) {
            return create.select(UserWxUser.USER_WX_USER.fields())
                    .from(UserWxUser.USER_WX_USER).innerJoin(HrWxWechat.HR_WX_WECHAT)
                    .on(UserWxUser.USER_WX_USER.WECHAT_ID.eq(HrWxWechat.HR_WX_WECHAT.ID))
                    .where(UserWxUser.USER_WX_USER.SYSUSER_ID.eq(userId))
                    .and(HrWxWechat.HR_WX_WECHAT.COMPANY_ID.eq(companyId))
                    .fetchInto(com.moseeker.baseorm.db.userdb.tables.pojos.UserWxUser.class);
        } else {
            return new ArrayList<>();
        }
    }

    public UserWxUserDO getWXUserById(int id) {
        Query query=new Query.QueryBuilder()
                .where(UserWxUser.USER_WX_USER.ID.getName(),id)
                .buildQuery();
        return getData(query);
    }

    /**
     * 根据ID批量获取微信用户MAP数据,Key为ID，Value为微信用户数据
     * @param ids   user_wx_user.id
     * @return
     */
    public Map<Integer,UserWxUserDO> getWXUserMapByIds(List<Integer> ids) {
        Query query=new Query.QueryBuilder()
                .where(new Condition(UserWxUser.USER_WX_USER.ID.getName(),ids, ValueOp.IN))
                .buildQuery();
        List<UserWxUserDO> result = getDatas(query);
        Map<Integer,UserWxUserDO> hrWxUserMap = new HashMap<>();
        if(!StringUtils.isEmptyList(result)){
            hrWxUserMap = result.stream().collect(Collectors.toMap(h->(int)h.getId(), h->h));
        }
        return hrWxUserMap;
    }

    /**
     * 合并两个C端账号时，把废弃的账号的微信用户的sysuser_id全指向有效的账号
     * @param vaildUserId   有效的C端账号ID
     * @param beDelUserId   废弃的C端账号ID
     * @return
     */
    public int combineWxUser(int vaildUserId, int beDelUserId){
        return create.update(USER_WX_USER)
                .set(USER_WX_USER.SYSUSER_ID,vaildUserId)
                .where(USER_WX_USER.SYSUSER_ID.eq(beDelUserId))
                .execute();
    }

    /**
     * 用户换绑微信账号的时候，需要把之前的user_wx_user的sysuser_id置为0
     * 如果用户之后想换回这次 被换绑的微信，被换绑的微信的sysuser_id不为0就会造成无法绑定
     * @param unionid   C端账号ID
     * @return
     */
    public int invalidOldWxUser(String unionid){
        return create.update(USER_WX_USER)
                .set(USER_WX_USER.SYSUSER_ID,0)
                .where(USER_WX_USER.UNIONID.eq(unionid))
                .execute();
    }

    public UserWxUserRecord getWxUserByUserIdAndWechatId(int userId, int wechatId) {
        return create.selectFrom(USER_WX_USER)
                .where(USER_WX_USER.SYSUSER_ID.eq(userId))
                .and(USER_WX_USER.WECHAT_ID.eq(wechatId))
                .limit(1)
                .fetchOne();
    }

    public UserWxUserRecord getWxUserByUserIdAndWechatIdAndSubscribe(int userId, int wechatId) {
        return create.selectFrom(USER_WX_USER)
                .where(USER_WX_USER.SYSUSER_ID.eq(userId))
                .and(USER_WX_USER.WECHAT_ID.eq(wechatId))
                .and(USER_WX_USER.IS_SUBSCRIBE.eq((byte)1))
                .limit(1)
                .fetchOne();
    }

    public List<UserWxUserRecord> fetchByIdList(List<Integer> wxUserIdList) {
        return create.selectFrom(USER_WX_USER)
                .where(USER_WX_USER.ID.in(wxUserIdList))
                .fetch();
    }

    public List<UserWxUserRecord> getWXUserMapByUserIds(List<Integer> idList) {
        return create.selectFrom(USER_WX_USER)
                .where(USER_WX_USER.SYSUSER_ID.in(idList))
                .fetch();
    }

    public List<UserWxUserDO> getWXUsersByUserIds(Set<Integer> idSet, Integer wechatId) {
        return create.selectFrom(USER_WX_USER)
                .where(USER_WX_USER.SYSUSER_ID.in(idSet))
                .and(USER_WX_USER.WECHAT_ID.eq(wechatId))
                .fetchInto(UserWxUserDO.class);
    }
}
