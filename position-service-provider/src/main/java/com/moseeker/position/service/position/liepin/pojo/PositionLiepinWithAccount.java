package com.moseeker.position.service.position.liepin.pojo;

import java.io.Serializable;

/**
 * Created by zhangdi on 2017/6/26.
 */
public class PositionLiepinWithAccount implements Serializable {
    private int account_id;
    private String user_name;
    private String password;
    private String channel;
    private int position_id;
    private PositionLiepin position_info;

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public PositionLiepin getPosition_info() {
        return position_info;
    }

    public void setPosition_info(PositionLiepin position_info) {
        this.position_info = position_info;
    }

}
