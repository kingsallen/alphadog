package com.moseeker.common.constants;

import com.moseeker.common.util.StringUtils;
import java.util.*;

/**
 * Created by moseeker on 2018/6/26.
 */
public enum ProfileOtherCareerType {
    companyBrand("companyBrand","最近工作的公司/品牌"),
    ReferenceName("ReferenceName","最近职位"),
    ReferenceRelation("ReferenceRelation","当前行业"),
    ReferenceContact("ReferenceContact","职位方向"),
    workyears("workyears","从业年数"),
    salary("salary","当前年薪"),
    expectsalary("expectsalary","期望年薪"),
    CareerGoals("CareerGoals","职业目标"),
    QualificationName("QualificationName","资格认证名称"),
    QualificationStartDate("QualificationStartDate","认证-开始时间"),
    QualificationEndDate("QualificationEndDate","认证-结束时间"),
    cpa("cpa","CPA证书"),
    servedoffice("servedoffice","曾供职事务所"),
    icanstart("icanstart","到岗时间"),
    workdays("workdays","每周到岗天数(实习)"),
    nightjob("nightjob","是否接受夜班"),
    trip("trip","是否接受长期出差"),
    frequency("frequency","选择班次"),
    carnoc_expect_position("carnoc_expect_position","民航欲从事的岗位类型")
    ;

    ProfileOtherCareerType(String key, String value) {
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

    private static Map<String, ProfileOtherCareerType> map = new HashMap<>();

    static {
        for (ProfileOtherCareerType csat : values()) {
            map.put(csat.getValue(), csat);
        }
    }

    private static Map<String, ProfileOtherCareerType> keymap = new HashMap<>();

    static {
        for (ProfileOtherCareerType csat : values()) {
            keymap.put(csat.getKey(), csat);
        }
    }

    public static ProfileOtherCareerType instanceFromValue(String value) {
        if (StringUtils.isNotNullOrEmpty(value)) {
            return map.get(value);
        } else {
            return null;
        }
    }

    public static ProfileOtherCareerType instanceFromKey(String key) {
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
            ProfileOtherCareerType type = instanceFromKey(entry.getKey());
            if(type != null) {
                messages.add(new Message(type.getValue(), entry.getValue()));
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
        List<Message> messages = new ArrayList<>();
        for(Map<String, Object> entry : list){
            if(entry.get("key")!=null) {
                ProfileOtherCareerType type = instanceFromValue((String)entry.get("key"));
                if (type != null) {
                    messages.add(new Message(type.getValue(), entry.get("value")));
                }
            }
        }
        return messages;
    }
}
