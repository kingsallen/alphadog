package com.moseeker.baseorm.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工激活状态
 * Created by jack on 28/12/2017.
 */
public enum EmployeeActiveState {

    Actived(0, "认证成功"), Cancel(1, "认证后取消认证"), Failure(2, "认证失败"), Init(3, "未认证"),
    MigrateToOtherCompany(4, "认证后又认证了其他公司"), UnFollow(5, "取消关注"), QuitJob(6, "离职");

    private EmployeeActiveState(int state, String name) {
        this.state = (byte)state;
        this.name = name;
    }

    private byte state;
    private String name;

    private static Map<Byte, EmployeeActiveState> stateMap = new HashMap<>();

    static {
        for (EmployeeActiveState employeeActiveState : values()) {
            stateMap.put(employeeActiveState.getState(), employeeActiveState);
        }
    }

    public static EmployeeActiveState instanceFromValue(byte value) {
        return stateMap.get(value);
    }

    public byte getState() {
        return state;
    }

    public String getName() {
        return name;
    }
}
