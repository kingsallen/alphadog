package com.moseeker.position.pojo;

import com.moseeker.baseorm.db.userdb.tables.pojos.UserHrAccount;

import java.util.List;

/**
 * Created by zztaiwll on 18/1/25.
 * 用于组织小程序端职位列表信息
 */
public class PositionMiniBean {

    private int trickTotal;
    private int underTatal;
    private UserHrAccount account;
    private List<PositionMiniInfo> positionList;

    public int getTrickTotal() {
        return trickTotal;
    }

    public void setTrickTotal(int trickTotal) {
        this.trickTotal = trickTotal;
    }

    public int getUnderTatal() {
        return underTatal;
    }

    public void setUnderTatal(int underTatal) {
        this.underTatal = underTatal;
    }

    public UserHrAccount getAccount() {
        return account;
    }

    public void setAccount(UserHrAccount account) {
        this.account = account;
    }

    public List<PositionMiniInfo> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<PositionMiniInfo> positionList) {
        this.positionList = positionList;
    }
}
