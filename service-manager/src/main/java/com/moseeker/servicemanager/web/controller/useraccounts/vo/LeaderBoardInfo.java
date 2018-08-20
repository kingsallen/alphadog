package com.moseeker.servicemanager.web.controller.useraccounts.vo;

/**
 * @Author: jack
 * @Date: 2018/8/19
 */
public class LeaderBoardInfo {

    private int id;
    private String username;
    private int point;
    private String icon;
    private int level;
    private int praise;
    private boolean praised;

    public static LeaderBoardInfo instanceFrom(
            com.moseeker.thrift.gen.employee.struct.LeaderBoardInfo leaderBoardInfo) {
        if (leaderBoardInfo != null) {
            LeaderBoardInfo lbi = new LeaderBoardInfo();
            lbi.setIcon(leaderBoardInfo.getIcon());
            lbi.setId(leaderBoardInfo.getId());
            lbi.setLevel(leaderBoardInfo.getLevel());
            lbi.setPoint(leaderBoardInfo.getPoint());
            lbi.setPraise(leaderBoardInfo.getPraise());
            lbi.setPraised(leaderBoardInfo.isPraised());
            lbi.setUsername(leaderBoardInfo.getUsername());
            return lbi;
        } else {
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public boolean isPraised() {
        return praised;
    }

    public void setPraised(boolean praised) {
        this.praised = praised;
    }
}
