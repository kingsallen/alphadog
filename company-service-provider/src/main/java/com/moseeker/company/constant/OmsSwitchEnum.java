package com.moseeker.company.constant;

import com.moseeker.common.util.StringUtils;
import com.moseeker.company.exception.CompanyException;
import com.moseeker.company.exception.CompanySwitchException;

import java.util.HashMap;
import java.util.Map;

public enum  OmsSwitchEnum {

    none(0,"无"),
    我是员工(1,"我是员工"),
    粉丝智能推荐(2,"粉丝智能推荐"),
    meet_mobot(3,"meet mobot"),
    员工智能推荐(4,"员工智能推荐"),
    社招(5,"社招"),
    校招(6,"校招"),
    人脉雷达(7,"人脉雷达"),
    老员工回聘(8,"老员工回聘"),
    五百强(9,"五百强"),
    多IP访问(10,"多IP访问"),
    ATS招聘流程升级(12,"ATS招聘流程升级"),
    猎头管理(13,"猎头管理");

    private int value;
    private String name;

    private static Map<Integer, OmsSwitchEnum> map = new HashMap<>();

    static {
        for (OmsSwitchEnum csat : values()) {
            map.put(csat.getValue(), csat);
        }
    }

    OmsSwitchEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name;
    }

    public static OmsSwitchEnum instanceFromValue(Integer value) {
        if (value !=null && map.get(value) != null) {
            return map.get(value);
        }else{
            throw CompanySwitchException.MODULE_NAME_NOT_EXISTS;
        }

    }

    public static OmsSwitchEnum instanceFromName(String name) {
        if (StringUtils.isNotNullOrEmpty(name)) {
            for(Integer key: map.keySet()){
                if(map.get(key).getName().equals(name)){
                    return map.get(key);
                }
            }
        }
        throw CompanySwitchException.MODULE_NAME_NOT_EXISTS;
    }

}
