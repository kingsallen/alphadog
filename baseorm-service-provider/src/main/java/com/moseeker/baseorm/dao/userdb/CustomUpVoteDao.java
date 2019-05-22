package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.constant.UpVoteState;
import com.moseeker.baseorm.db.userdb.tables.UserEmployeeUpvote;
import com.moseeker.baseorm.db.userdb.tables.daos.UserEmployeeUpvoteDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeUpvoteRecord;
import org.jooq.Param;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     * @return 被点赞的次数
     */
    public int countUpVote(int employeeId) {
        Record1<Integer> count = using(configuration())
                .selectCount()
                .from(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE)
                .where(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER.eq(employeeId))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CANCEL.eq((byte) UpVoteState.UpVote.getValue()))
                .fetchOne();
        return count.value1();
    }

    /**
     * 查找点赞记录
     * @param receiver 被点赞员工
     * @param sender 点赞员工
     * @return 返回点赞记录
     */
    public com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeUpvote fetchUpVote(int receiver, int sender,
                                                                                      long start, long end) {
        UserEmployeeUpvoteRecord result = using(configuration())
                .selectFrom(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE)
                .where(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER.eq(receiver))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.SENDER.eq(sender))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME.gt(new Timestamp(start)))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME.le(new Timestamp(end)))
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
    public int insert(int companyId, int receiver, int sender, long startTime, long endTime) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        byte upvote = (byte)UpVoteState.UpVote.getValue();
        byte unUpvote = (byte)UpVoteState.UnUpVote.getValue();
        Param<Integer> senderParam = param(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.SENDER.getName(), sender);
        Param<Integer> receiverParam = param(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER.getName(), receiver);
        Param<Integer> companyIdParam = param(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.COMPANY_ID.getName(), companyId);
        Param<Timestamp> upvoteTimeParam = param(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME.getName(), currentTime);
        Param<Byte> upVoteParam = param(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CANCEL.getName(), upvote);

        Record1<Byte> cancelRecord = using(configuration()).select(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CANCEL)
                .from(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE)
                .where(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.COMPANY_ID.eq(companyId))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.SENDER.eq(sender))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER.eq(receiver)).fetchOne();

        byte cancel = null==cancelRecord?unUpvote:cancelRecord.value1();

        int execute = using(configuration())
                .insertInto(
                        UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE,
                        UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.SENDER,
                        UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER,
                        UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.COMPANY_ID,
                        UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME,
                        UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CANCEL
                )
                .values(
                        senderParam,
                        receiverParam,
                        companyIdParam,
                        upvoteTimeParam,
                        upVoteParam
                )
                .onDuplicateKeyUpdate()
                .set(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME,upvoteTimeParam)
                .set(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CANCEL,param(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CANCEL.getName(),
                        upvote==cancel?unUpvote:upvote))
                .execute();

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

    public UserEmployeeUpvoteRecord fetchBySenderAndReceiver(int sender, int receiver, long startTime, long endTime) {
        return using(configuration())
                .selectFrom(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE)
                .where(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER.eq(receiver))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.SENDER.eq(sender))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME.gt(new Timestamp(startTime)))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME.le(new Timestamp(endTime)))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CANCEL.eq((byte)UpVoteState.UpVote.getValue()))
                .fetchOne();
    }

    public int countUpVote(int employeeId, long start, long end) {

        return using(configuration())
                .selectCount()
                .from(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE)
                .where(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER.eq(employeeId))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME.gt(new Timestamp(start)))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME.le(new Timestamp(end)))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CANCEL.eq((byte) UpVoteState.UpVote.getValue()))
                .fetchOne().value1();
    }

    public int countExceptSelfUpVote(int employeeId, long start, long end) {

        return using(configuration())
                .selectCount()
                .from(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE)
                .where(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER.eq(employeeId))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.SENDER.ne(employeeId))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME.gt(new Timestamp(start)))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME.le(new Timestamp(end)))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CANCEL.eq((byte) UpVoteState.UpVote.getValue()))
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

    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeUpvote> fetchBySenderAndReceiverList(int sender, List<Integer> receiverIdList, long startTime, long endTime) {
        Result<UserEmployeeUpvoteRecord> result = using(configuration())
                .selectFrom(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE)
                .where(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.SENDER.eq(sender))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER.in(receiverIdList))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CANCEL.eq((byte)UpVoteState.UpVote.getValue()))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME.gt(new Timestamp(startTime)))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME.le(new Timestamp(endTime)))
                .fetch();
        if (result != null && result.size() > 0) {
            return result
                    .stream()
                    .map(record -> record.into(com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeUpvote.class))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public Map<Integer,Integer> countUpVoteByReceiverIdList(List<Integer> receiverIdList,
                                                            long startTime, long endTime) {

        Result<Record2<Integer, Integer>> result =
                using(configuration())
                .select(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER, UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.ID.count())
                .from(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE)
                .where(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER.in(receiverIdList))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME.gt(new Timestamp(startTime)))
                .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME.le(new Timestamp(endTime)))
                        .and(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CANCEL.eq((byte)UpVoteState.UpVote.getValue()))
                .groupBy(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER)
                .fetch();
        if (result != null && result.size() > 0) {
            return result.stream().collect(Collectors.toMap(Record2::value1, Record2::value2));
        } else {
            return new HashMap<>();
        }
    }
}
