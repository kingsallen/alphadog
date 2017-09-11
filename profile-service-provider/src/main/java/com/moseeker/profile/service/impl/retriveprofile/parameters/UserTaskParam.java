package com.moseeker.profile.service.impl.retriveprofile.parameters;

import com.moseeker.common.exception.CommonException;

import java.util.Map;

/**
 * userTask参数
 * Created by jack on 19/07/2017.
 */
public class UserTaskParam {

    private String mobile;
    private String email;
    private String name;
    public void parse(Map<String, Object> param) throws CommonException {
        if (param != null) {
            if (param.get("mobile") != null) {
                mobile = (String)param.get("mobile");
            }
            if (param.get("email") != null) {
                email = (String)param.get("email");
            }
            if (param.get("name") != null) {
                name = (String)param.get("name");
            }
        }
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
