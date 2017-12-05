package com.moseeker.function.service.chaos.position;

import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronizationWithAccount;

import java.io.Serializable;

/**
 * Created by zhangdi on 2017/6/22.
 */
public class PositionZhilianWithAccount implements Serializable {

    public String user_name;
    public String password;
    public String position_id;
    public String channel;
    public PositionZhilian position_info;
    public String company;
    public String account_id;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition_id() {
        return position_id;
    }

    public void setPosition_id(String position_id) {
        this.position_id = position_id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public PositionZhilian getPosition_info() {
        return position_info;
    }

    public void setPosition_info(PositionZhilian position_info) {
        this.position_info = position_info;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "PositionZhilianWithAccount{" +
                "user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                ", position_id='" + position_id + '\'' +
                ", channel='" + channel + '\'' +
                ", position_info=" + position_info +
                ", company='" + company + '\'' +
                ", account_id='" + account_id + '\'' +
                '}';
    }

    public static PositionZhilianWithAccount copyFromSyncPosition(ThirdPartyPositionForSynchronizationWithAccount syncPosition) {

        if (syncPosition == null) return null;

        PositionZhilianWithAccount position51WithAccount = new PositionZhilianWithAccount();
        position51WithAccount.setUser_name(syncPosition.getUser_name());
        position51WithAccount.setPassword(syncPosition.getPassword());
        position51WithAccount.setPosition_id(String.valueOf(syncPosition.getPosition_id()));
        position51WithAccount.setChannel(String.valueOf(syncPosition.getChannel()));
        position51WithAccount.setAccount_id(String.valueOf(syncPosition.getAccount_id()));
        position51WithAccount.setPosition_info(PositionZhilian.copyFromSyncPosition(syncPosition.getPosition_info()));

        return position51WithAccount;
    }
}
