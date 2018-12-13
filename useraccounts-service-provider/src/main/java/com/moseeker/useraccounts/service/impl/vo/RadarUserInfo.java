package com.moseeker.useraccounts.service.impl.vo;

import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;

public class RadarUserInfo {

    private Integer uid;
    private String name;
    private String nickname;
    private String avatar;
    private Integer degree;
    private Integer order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public RadarUserInfo initFromUserWxUser(UserWxUserDO userWxUserDO){
        this.avatar = userWxUserDO.getHeadimgurl();
        this.nickname = userWxUserDO.getNickname();
        this.uid = userWxUserDO.getSysuserId();
        return this;
    }
}
