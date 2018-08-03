package com.moseeker.common.constants;

import com.moseeker.common.util.StringUtils;
import java.util.*;

/**
 * Created by moseeker on 2018/6/26.
 */
public enum ProfileOtherIdentityType {
    englishname("englishname","英文名"),
    firstnamepinyin("firstnamepinyin","身份证姓名拼音"),
    familynamepinyin("familynamepinyin","身份证姓拼音"),
    fullnamepinyin("fullnamepinyin","身份证名拼音"),
    idnumber("idnumber","身份证号码"),
    Nation("Nation","民族"),
    residencetype("residencetype","户口类型"),
    residence("residence","户口所在地"),
    PoliticalStatus("PoliticalStatus","政治面貌"),
    marriage("marriage","婚姻状况"),
    height("height","身高（cm）"),
    weight("weight","体重（kg）"),
    Address("Address","通讯地址"),
    AddressProvince("AddressProvince","地址所在省/直辖市"),
    ZipCode("ZipCode","邮政编码"),
    EmergencyContact("EmergencyContact","紧急联系人"),
    EmergencyPhone("EmergencyPhone","紧急联系电话"),
    WorkpermitHK("WorkpermitHK","香港工作许可"),
    ;

    ProfileOtherIdentityType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    String key;
    String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    private static Map<String, ProfileOtherIdentityType> map = new HashMap<>();

    static {
        for (ProfileOtherIdentityType csat : values()) {
            map.put(csat.getValue(), csat);
        }
    }

    private static Map<String, ProfileOtherIdentityType> keymap = new HashMap<>();

    static {
        for (ProfileOtherIdentityType csat : values()) {
            keymap.put(csat.getKey(), csat);
        }
    }

    public static ProfileOtherIdentityType instanceFromValue(String value) {
        if (StringUtils.isNotNullOrEmpty(value)) {
            return map.get(value);
        } else {
            return null;
        }
    }

    public static ProfileOtherIdentityType instanceFromKey(String key) {
        if (StringUtils.isNotNullOrEmpty(key)) {
            return keymap.get(key);
        } else {
            return null;
        }
    }
    /*
        {
            "QualificationEndDate":"2018-06-01",
            "marriage":"未婚",
            "idnumber":"410185199212306666",
            "carnoc_expect_position":"机长",
            "degree":"本科",
            "residence":"苏州"
        }
     */
    public static List<Message> getMessageList(Map<String, Object> params){
        List<Message> messages = new ArrayList<>();
        Set<Map.Entry<String, Object>> entries = params.entrySet();
        for(Map.Entry<String, Object> entry : entries){
            ProfileOtherIdentityType type = instanceFromKey(entry.getKey());
            if(type != null) {
                messages.add(new Message(type.getValue(), entry.getValue(),0,0));
            }
        }
        return messages;
    }

    /*
        [
            {
                "value": "本科",
                "key": "最高学历"
            },
            {
                "value": "410185199212306666",
                "key": "身份证号码"
            },
            {
                "value": "机长",
                "key": "民航欲从事的岗位类型"
            },
            {
                "value": "接受",
                "key": "是否接受长期出差"
            }
        ]
     */
    public static List<Message> getMessageList(List<Map<String, Object>> list){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        int i=0;
        List<Message> messages = new ArrayList<>();
        for(Map<String, Object> entry : list){
            if(entry.get("key")!=null) {
                ProfileOtherIdentityType type = instanceFromValue((String)entry.get("key"));
                if (type != null) {
                    int lastline=0;
                    messages.add(new Message(type.getValue(), entry.get("value"),i%2,lastline));
                    i++;
                }
            }
        }
        if(!StringUtils.isEmptyList(messages)){
            messages.get(messages.size()-1).setLastline(1);
        }
        return messages;
    }
}
