package com.moseeker.useraccounts.domain;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.impl.pojos.UpVoteData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @Author: jack
 * @Date: 2018/8/16
 */
@Component
@CounterIface
public class UserEmployeeEntity {

    private ThreadPool threadPool = ThreadPool.Instance;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EmployeeEntity employeeEntity;

    public UpVoteData findEmployee(int employeeId, int colleague) throws UserAccountException {

        Future<UserEmployeeDO> senderFuture = threadPool.startTast(() -> employeeEntity.getEmployeeByID(employeeId));
        Future<UserEmployeeDO> receiverFuture = threadPool.startTast(() -> employeeEntity.getEmployeeByID(colleague));

        UserEmployeeDO sender;
        try {
            sender = senderFuture.get();
        } catch (Exception e) {
            sender = null;
            logger.error(e.getMessage(), e);
        }
        UserEmployeeDO receiver;
        try {
            receiver = receiverFuture.get();
        } catch (Exception e) {
            receiver = null;
            logger.error(e.getMessage(), e);
        }

        if (receiver == null || receiver.getId() == 0) {
            throw UserAccountException.AWARD_EMPLOYEE_ELEGAL;
        }

        if (sender == null || sender.getId() == 0) {
            throw UserAccountException.AWARD_EMPLOYEE_ELEGAL;
        }

        List<Integer> companyIdList = employeeEntity.getCompanyIds(sender.getCompanyId());
        if (!companyIdList.contains(receiver.getCompanyId())) {
            throw UserAccountException.AWARD_EMPLOYEE_ELEGAL;
        }
        return new UpVoteData(sender, receiver);
    }
}
