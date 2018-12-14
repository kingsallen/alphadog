package com.moseeker.useraccounts.pojo.neo4j;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * Created by moseeker on 2018/12/13.
 */
@NodeEntity(label = "UserUser")
public class UserNode {

    @GraphId
    Long id;
    private int user_id;
    @Property
    private int wxuser_id;
    @Property
    private String nickname;
    @Property
    private String headimgurl;
    @Property
    private int employee_company;
    @Property
    private int employee_id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getWxuser_id() {
        return wxuser_id;
    }

    public void setWxuser_id(int wxuser_id) {
        this.wxuser_id = wxuser_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public int getEmployee_company() {
        return employee_company;
    }

    public void setEmployee_company(int employee_company) {
        this.employee_company = employee_company;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
}
