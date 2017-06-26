package com.moseeker.function.service.chaos.position;

import java.io.Serializable;

/**
 * Created by zhangdi on 2017/6/26.
 */
public class PositionLiepin implements Serializable {

    private int account_id;
    private String username;
    private String password;
    private int channel;
    private int position_id;
    private PositionLiepinWithAccount position_info;

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public PositionLiepinWithAccount getPosition_info() {
        return position_info;
    }

    public void setPosition_info(PositionLiepinWithAccount position_info) {
        this.position_info = position_info;
    }
}
