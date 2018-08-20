package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.constant.UpVoteState;
import com.moseeker.baseorm.db.userdb.tables.UserEmployeeUpvote;
import com.moseeker.baseorm.db.userdb.tables.daos.UserEmployeeUpvoteDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeUpvoteRecord;
import org.joda.time.DateTime;
import org.jooq.Param;
import org.jooq.Record1;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.jooq.impl.DSL.*;

/**
 * @Author: jack
 * @Date: 2018/8/16
 */
@Repository
public class CustomUpVoteDao extends UserEmployeeUpvoteDao {

    @Autowired
    public CustomUpVoteDao(DefaultConfiguration configuration) {
        super(configuration);
    }

    /**
     * 计算点赞数
     * @param employeeId 员工编号
     * @param companyId 公司编号
     * @return 被点赞的次数
     */
    public int countUpVote(int employeeId, int companyId) {
        Record1<Integer> count = using(configuration())
                .selectCount()
                .from(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE)
                .where(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.COMPANY_ID.eq(companyId))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER.eq(employeeId))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CANCEL.eq((byte) UpVoteState.UpVote.getValue()))
                .fetchOne();
        return count.value1();
    }

    public com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeUpvote fetchUpVote(int companyId, int receiver, int sender) {

        UserEmployeeUpvoteRecord result = using(configuration())
                .selectFrom(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE)
                .where(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.COMPANY_ID.eq(companyId))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER.eq(receiver))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.SENDER.eq(sender))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CANCEL.eq((byte) UpVoteState.UpVote.getValue()))
                .fetchOne();
        if (result != null) {
            return result.into(com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeUpvote.class);
        } else {
            return null;
        }
    }

    /**
     * 添加点赞记录
     * @param companyId 公司信息
     * @param receiver 被点赞的员工编号
     * @param sender 点赞的员工编号
     * @return 点赞记录编号
     */
    public int insert(int companyId, int receiver, int sender) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Param<Integer> senderParam = param(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.SENDER.getName(), sender);
        Param<Integer> receiverParam = param(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER.getName(), receiver);
        Param<Integer> companyIdParam = param(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.COMPANY_ID.getName(), companyId);
        Param<Timestamp> upvoteTimeParam = param(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME.getName(), currentTime);
        Param<Byte> upVoteParam = param(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CANCEL.getName(), (byte)0);

        int execute = using(configuration())
                .insertInto(
                        UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE,
                        UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.SENDER,
                        UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER,
                        UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.COMPANY_ID,
                        UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME,
                        UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CANCEL
                )
                .select(
                        select(
                                senderParam,
                                receiverParam,
                                companyIdParam,
                                upvoteTimeParam,
                                upVoteParam
                        )
                        .whereNotExists(
                                selectOne()
                                .from(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE)
                                .where(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.COMPANY_ID.eq(companyId))
                                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER.eq(receiver))
                                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.SENDER.eq(sender))
                        )
                )
                .execute();

        UserEmployeeUpvoteRecord result = using(configuration())
                .selectFrom(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE)
                .where(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.COMPANY_ID.eq(companyId))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER.eq(receiver))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.SENDER.eq(sender))
                .fetchOne();

        if (result != null) {
            if (execute == 0 && result.getCancel().byteValue() != UpVoteState.UpVote.getValue()) {
                result.setCancel((byte) UpVoteState.UpVote.getValue());
                result.setUpdateTime(currentTime);
                using(configuration()).attach(result);
                result.update();
            }
            return result.getId();
        }
        return 0;
    }

    /**
     * 取消点赞。
     *
     * 将点赞记录置为取消点赞，并更新取消点赞时间
     * @param id 点赞记录编号
     */
    public void cancelUpVote(Integer id) {

        using(configuration())
                .update(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE)
                .set(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CANCEL, (byte)UpVoteState.UnUpVote.getValue())
                .set(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CANCEL_TIME, new Timestamp(System.currentTimeMillis()))
                .where(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.ID.eq(id))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CANCEL.eq((byte)UpVoteState.UpVote.getValue()))
                .execute();
    }

    public UserEmployeeUpvoteRecord fetchBySenderAndReceiver(int sender, int receiver) {
        return using(configuration())
                .selectFrom(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE)
                .where(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.SENDER.eq(sender))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER.eq(receiver))
                .fetchOne();
    }

    public int countUpVote(int employeeId, long start, long end) {

        return using(configuration())
                .selectCount()
                .from(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE)
                .where(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER.eq(employeeId))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME.gt(new Timestamp(start)))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME.le(new Timestamp(end)))
                .fetchOne().value1();
    }

    public int count(long lastFriday, long currentFriday) {
        return using(configuration())
                .selectCount()
                .from(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE)
                .where(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME.gt(new Timestamp(lastFriday)))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME.le(new Timestamp(currentFriday)))
                .fetchOne()
                .value1();
    }

    public List<UserEmployeeUpvoteRecord> fetchByInterval(int size, long start, long end) {
        return using(configuration())
                .selectFrom(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE)
                .where(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME.gt(new Timestamp(start)))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME.le(new Timestamp(end)))
                .limit(0,size)
                .fetch();
    }
}
