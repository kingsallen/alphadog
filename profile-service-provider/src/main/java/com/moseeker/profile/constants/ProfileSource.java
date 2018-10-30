package com.moseeker.profile.constants;

/**
 * @Author: jack
 * @Date: 2018/9/6
 */
public enum ProfileSource {
    ChatBotReferral(221, "员工主动上传"),EmployeeReferral(222, "内推"),MOBILEReferral(223,"手机上传");;

    ProfileSource(int value, String name) {
        this.value = value;
        this.name = name;
    }

    private int value;
    private String name;

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
