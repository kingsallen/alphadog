package com.moseeker.function.service.chaos.position;

import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronizationWithAccount;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangdi on 2017/6/26.
 */
public class PositionLiepinWithAccount implements Serializable {
    private int account_id;
    private String username;
    private String password;
    private int channel;
    private int position_id;
    private PositionLiepin position_info;

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

    public PositionLiepin getPosition_info() {
        return position_info;
    }

    public void setPosition_info(PositionLiepin position_info) {
        this.position_info = position_info;
    }

    public static Object copyFromSyncPosition(ThirdPartyPositionForSynchronizationWithAccount position) {

        PositionLiepinWithAccount positionLiepinWithAccount = new PositionLiepinWithAccount();

        positionLiepinWithAccount.setAccount_id(position.getAccount_id());
        positionLiepinWithAccount.setUsername(position.getUser_name());
        positionLiepinWithAccount.setPassword(position.getPassword());
        positionLiepinWithAccount.setChannel(position.getChannel());
        positionLiepinWithAccount.setPosition_id(position.getPosition_id());
        positionLiepinWithAccount.setPosition_info(PositionLiepin.copyFromSyncPosition(position.getPosition_info()));

        return positionLiepinWithAccount;

    }
}
