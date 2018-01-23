package com.moseeker.baseorm.constant;

/**
 * 员工激活状态
 * Created by jack on 28/12/2017.
 */
public enum EmployeeActivedState {

    Actived(0, "认证成功"), Cancel(1, "认证后取消认证"), Failure(2, "认证失败"), Init(3, "未认证"),
    MigrateToOtherCompany(4, "认证后又认证了其他公司");

    private EmployeeActivedState(int state, String name) {
        this.state = (byte)state;
        this.name = name;
    }

    private byte state;
    private String name;

    public byte getState() {
        return state;
    }

    public String getName() {
        return name;
    }
}
