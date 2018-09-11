package com.moseeker.profile.service.impl.talentpoolmvhouse.vo;

/**
 * 基础服务向chaos发送请求的vo
 * @author cjm
 * @date 2018-07-24 13:47
 **/
public class MvHouseVO {
    private int account_id;
    private ProfileMoveOperationInfoVO operation_info;
    private String member_name;
    private int operation_id;
    private String user_name;
    private String password;
    private int channel;
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public ProfileMoveOperationInfoVO getOperation_info() {
        return operation_info;
    }

    public void setOperation_info(ProfileMoveOperationInfoVO operation_info) {
        this.operation_info = operation_info;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public int getOperation_id() {
        return operation_id;
    }

    public void setOperation_id(int operation_id) {
        this.operation_id = operation_id;
    }

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

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "MvHouseVO{" +
                "account_id=" + account_id +
                ", operation_info='" + operation_info + '\'' +
                ", member_name='" + member_name + '\'' +
                ", operation_id=" + operation_id +
                ", user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                ", channel=" + channel +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
