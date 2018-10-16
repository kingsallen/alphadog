package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.records.UserRecommendRefusalRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.thrift.gen.dao.struct.userdb.UserRecommendRefusalDO;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Calendar;

import static com.moseeker.baseorm.db.userdb.tables.UserRecommendRefusal.USER_RECOMMEND_REFUSAL;

/**
 * @Author: jack
 * @Date: 2018/8/21
 */
@Repository
public class UserRecommendRefusalDao extends JooqCrudImpl<UserRecommendRefusalDO, UserRecommendRefusalRecord> {

    public UserRecommendRefusalDao() {
        super(USER_RECOMMEND_REFUSAL, UserRecommendRefusalDO.class);
    }

    public UserRecommendRefusalDao(TableImpl<UserRecommendRefusalRecord> table, Class<UserRecommendRefusalDO> sClass) {
        super(table, sClass);
    }

    public UserRecommendRefusalDO getLastestDataByUserId(int userId) {
        return create.selectFrom(USER_RECOMMEND_REFUSAL)
                .where(USER_RECOMMEND_REFUSAL.USER_ID.eq(userId))
                .orderBy(USER_RECOMMEND_REFUSAL.REFUSE_TIME.desc())
                .fetchOneInto(UserRecommendRefusalDO.class);
    }

    /**
     * 只有在数据库中不存在'拒绝推荐超时时间 > 当前时间'的数据，才插入
     *
     * @param userRecommendRefusalDO
     * @return
     */
    public int insertData(UserRecommendRefusalDO userRecommendRefusalDO) {
        Calendar refuseTime = Calendar.getInstance();
        userRecommendRefusalDO.setRefuseTime(DateUtils.dateToShortTime(refuseTime.getTime()));

        Calendar refuseTimeOut = Calendar.getInstance();
        refuseTimeOut.add(Calendar.MONTH, 6);
        userRecommendRefusalDO.setRefuseTimeout(DateUtils.dateToShortTime(refuseTimeOut.getTime()));

        UserRecommendRefusalRecord record = BeanUtils.structToDB(userRecommendRefusalDO, UserRecommendRefusalRecord.class);
        return create.insertInto(USER_RECOMMEND_REFUSAL)
                .columns(record.field1(), record.field2(), record.field3(), record.field4(), record.field5())
                .select(
                        DSL.select(
                                DSL.param(record.field1().getName(), record.value1())
                                , DSL.param(record.field2().getName(), record.value2())
                                , DSL.param(record.field3().getName(), record.value3())
                                , DSL.param(record.field4().getName(), record.value4())
                                , DSL.param(record.field5().getName(), record.value5())
                        ).whereNotExists(
                                DSL.selectOne()
                                        .from(USER_RECOMMEND_REFUSAL)
                                        .where(USER_RECOMMEND_REFUSAL.REFUSE_TIMEOUT.gt(new Timestamp(refuseTime.getTimeInMillis())))
                                        .and(USER_RECOMMEND_REFUSAL.USER_ID.eq(record.getUserId()))
                                        .and(USER_RECOMMEND_REFUSAL.WECHAT_ID.eq(record.getWechatId()))
                        )
                ).execute();

    }

    /**
     * 获取最近的拒绝推荐记录
     * @param userId C端用户ID
     * @param wechatId 公众号ID
     * @return 最近的拒绝推荐记录，一条
     */
    public UserRecommendRefusalDO getLastestRecommendRefusal(int userId, int wechatId) {
        return create.selectFrom(USER_RECOMMEND_REFUSAL)
                .where(USER_RECOMMEND_REFUSAL.USER_ID.eq(userId))
                .and(USER_RECOMMEND_REFUSAL.WECHAT_ID.eq(wechatId))
                .orderBy(USER_RECOMMEND_REFUSAL.REFUSE_TIME.desc())
                .limit(1)
                .fetchOneInto(UserRecommendRefusalDO.class);
    }
}
