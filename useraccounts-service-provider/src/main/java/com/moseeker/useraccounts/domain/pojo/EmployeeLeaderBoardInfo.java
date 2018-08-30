package com.moseeker.useraccounts.domain.pojo;

/**
 * @Author: jack
 * @Date: 2018/8/20
 */
public class EmployeeLeaderBoardInfo {
    private int id;
    private int award;
    private int sort;
    private long lastUpdateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
