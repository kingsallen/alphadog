package com.moseeker.common.constants;

import com.moseeker.common.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum OmsSwitchEnum {

    /**
     * 不存在的开关
     */
    NONE(0,"无", false),
    IM_EMPLOYEE(1,"我是员工", false),
    FANS_RECOMMEND(2,"粉丝智能推荐", false),
    MEET_MOBOT(3,"meet mobot", false),
    EMPLOYEE_RECOMMEND(4,"员工智能推荐", false),
    SOCIAL_RECRUIT(5,"社招", false),
    ONCAMPUS_RECRUIT(6,"校招", false),
    SOCIAL_RADAR(7,"人脉雷达", false),
    REHIRE(8,"老员工回聘", false),
    FORTUNE500(9,"五百强", false),
    MULTI_IP_VISIT(10,"多IP访问", false), //
    REDPACKAGE_ACTIVITY(11,"红包活动", false),
    ATS_RECRUIT_PROCESS_UPGRADE(12,"ATS招聘流程升级", false),
    HUNTER_MANAGE(13,"猎头管理", false),
    WORK_WEICHAT(14,"企业微信版", false),// 允许使用企业微信进行员工认证
    IDCARD_RECOGNITION(15,"身份证识别", false),//身份证识别
    MOMO_WECHAT(17,"MOMO微信端", true),//身份证识别
    SM_MANUAL_IM(18,"社招版MoBot(人工对话模式)", false),
    SM_AI_IM(19,"社招版MoBot(人工+智能对话模式)", false),
    SM_TURNHR_IM(20,"社招版MoBot(请转HR（仅开启智能对话模式有效）)", false),
    CM_MANUAL_IM(21,"校招MoBot(人工对话模式)", false),
    CM_AI_IM(22,"校招MoBot(人工+智能对话模式)", false),
    CM_TURNHR_IM(23,"校招MoBot(请转HR（仅开启智能对话模式有效）)", false),
    EM_MANUAL_IM(24,"员工版MoBot(人工对话模式)", false),
    EM_AI_IM(25,"员工版MoBot(人工+智能对话模式)", false),
    EM_TURNHR_IM(26,"员工版MoBot(请转HR（仅开启智能对话模式有效）)", false),
    CM_PERFECT_RESUME(27,"校招粉丝完善简历",false),
    EMPLOYEE_REFERRAL(28,"员工内推职位推荐",false),
    LBS_POSITION_LIST(16,"LBS职位列表",false),//LBS职位列表
    EMPLOYEE_IM_CHAT(30,"员工IM聊天",true),
    CUSTOM_PRIVACY_WECHAT(31,"客户自定义隐私协议", false);

    private int value;
    private String name;
    private boolean valid;

    private static Map<Integer, OmsSwitchEnum> map = new HashMap<>();

    static {
        for (OmsSwitchEnum csat : values()) {
            map.put(csat.getValue(), csat);
        }
    }

    OmsSwitchEnum(int value, String name, boolean valid) {
        this.value = value;
        this.name = name;
        this.valid = valid;
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

    /**
     * 返回是否可用对应的byte值。可用返回1，不可用返回0
     * @return 是否可用对应的byte类型的值
     */
    public byte getValidToByte() {
        return (byte) (valid?1:0);
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * TODO: 既然存在None了，返回值就不应该是null
     * @param value
     * @return
     */
    public static OmsSwitchEnum instanceFromValue(Integer value) {
        if (value !=null && map.get(value) != null) {
            return map.get(value);
        }else{
            return null ;
        }

    }

    /**
     * TODO: 既然存在None了，返回值就不应该是null
     * @param name
     * @return
     */
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
