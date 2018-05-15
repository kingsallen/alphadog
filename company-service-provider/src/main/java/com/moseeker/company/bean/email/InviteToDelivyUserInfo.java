package com.moseeker.company.bean.email;

/**
 * Created by zztaiwll on 18/4/26.
 */
public class InviteToDelivyUserInfo {
    private String name;
    private String email;
    private int userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
