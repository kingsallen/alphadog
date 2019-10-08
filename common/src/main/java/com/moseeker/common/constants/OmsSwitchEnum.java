package com.moseeker.common.constants;

import com.moseeker.common.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum OmsSwitchEnum {

    NONE(0,"无"),
    IM_EMPLOYEE(1,"我是员工"),
    FANS_RECOMMEND(2,"粉丝智能推荐"),
    MEET_MOBOT(3,"meet mobot"),
    EMPLOYEE_RECOMMEND(4,"员工智能推荐"),
    SOCIAL_RECRUIT(5,"社招"),
    ONCAMPUS_RECRUIT(6,"校招"),
    SOCIAL_RADAR(7,"人脉雷达"),
    REHIRE(8,"老员工回聘"),
    FORTUNE500(9,"五百强"),
    MULTI_IP_VISIT(10,"多IP访问"), //
    REDPACKAGE_ACTIVITY(11,"红包活动"),
    ATS_RECRUIT_PROCESS_UPGRADE(12,"ATS招聘流程升级"),
    HUNTER_MANAGE(13,"猎头管理"),
    WORK_WEICHAT(14,"企业微信版"),// 允许使用企业微信进行员工认证
    IDCARD_RECOGNITION(15,"身份证识别"),//身份证识别
    LBS_POSITION_LIST(16,"LBS职位列表");//LBS职位列表

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
            return null ;
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
        return null ;
    }

}
