package com.moseeker.useraccounts.domain;

import com.moseeker.baseorm.constant.UpVoteState;
import com.moseeker.baseorm.dao.userdb.CustomUpVoteDao;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeUpvote;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.useraccounts.exception.UserAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @Author: jack
 * @Date: 2018/8/16
 */
@Component
public class UpVoteEntity {

    @Autowired
    private CustomUpVoteDao upVoteDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 点赞
     * 如果第一次对员工点赞，则生成点赞记录。
     * 如果取消点赞再次点赞，则更新成点赞状态，并且更新点赞时间
     * @param receiver 被点赞的员工编号
     * @param sender 点赞的员工编号
     * @return 点赞记录编号
     * @throws UserAccountException 业务异常
     */
    public int upVote(UserEmployeeDO receiver, UserEmployeeDO sender) throws UserAccountException {

        UserEmployeeUpvote upVote = upVoteDao.fetchUpVote(receiver.getCompanyId(), receiver.getId(), sender.getId());
        if (upVote != null) {
            throw UserAccountException.EMPLOYEE_ALREADY_UP_VOTE;
        }

        return upVoteDao.insert(receiver.getCompanyId(), receiver.getId(), sender.getId());
    }

    /**
     * 取消点赞
     * @param receiver 被点赞员工记录
     * @param sender 点赞员工记录
     */
    public void cancelUpVote(UserEmployeeDO receiver, UserEmployeeDO sender) throws UserAccountException {

        UserEmployeeUpvote upVote = upVoteDao.fetchUpVote(receiver.getCompanyId(), receiver.getId(), sender.getId());
        if (upVote != null) {
            throw UserAccountException.EMPLOYEE_NOT_UP_VOTE;
        }
        upVote.setCancelTime(new Timestamp(System.currentTimeMillis()));
        upVote.setCancel((byte) UpVoteState.UnUpVote.getValue());
        upVoteDao.cancelUpVote(upVote.getId());
    }
}
