package com.moseeker.useraccounts.service.impl.pojos;

import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;

/**
 * @Author: jack
 * @Date: 2018/8/16
 */
public class UpVoteData {

    private UserEmployeeDO sender;
    private UserEmployeeDO receiver;

    public UpVoteData(UserEmployeeDO sender, UserEmployeeDO receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public UserEmployeeDO getSender() {
        return sender;
    }

    public UserEmployeeDO getReceiver() {
        return receiver;
    }
}
