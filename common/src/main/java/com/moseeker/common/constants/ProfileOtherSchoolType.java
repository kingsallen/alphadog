package com.moseeker.common.constants;

import com.moseeker.common.util.StringUtils;
import java.util.*;

/**
 * Created by moseeker on 2018/6/26.
 */
public enum ProfileOtherSchoolType {
    degree("degree","最高学历"),
    ReferenceName("ReferenceName","最高学历证明人"),
    ReferenceRelation("ReferenceRelation","证明人关系"),
    ReferenceContact("ReferenceContact","证明人联系方式"),
    StudentFrom("StudentFrom","生源地"),
    IsFreshGraduated("IsFreshGraduated","应届/往届"),
    CollegeCity("CollegeCity","学校所在城市"),
    graduation("graduation","预计毕业时间"),
    CollegeContact("CollegeContact","院系联系人"),
    CollegeContactTel("CollegeContactTel","院系联系人电话"),
    majorrank("majorrank","专业排名"),
    cet4("cet4","四级成绩"),
    cet6("cet6","六级成绩"),
    toefl("toefl","托福成绩"),
    ielts("ielts","雅思成绩"),
    gmat("gmat","GMAT成绩"),
    gre("gre","GRE成绩"),
    gpa("gpa","GPA"),
    sat_act("sat_act","SAT/ACT"),
    TEM4("TEM4","专四成绩"),
    TEM8("TEM8","专八成绩"),
    JapaneseLevel("JapaneseLevel","日语等级")
    ;

    ProfileOtherSchoolType(String key, String value) {
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

    private static Map<String, ProfileOtherSchoolType> map = new HashMap<>();

    static {
        for (ProfileOtherSchoolType csat : values()) {
            map.put(csat.getValue(), csat);
        }
    }

    private static Map<String, ProfileOtherSchoolType> keymap = new HashMap<>();

    static {
        for (ProfileOtherSchoolType csat : values()) {
            keymap.put(csat.getKey(), csat);
        }
    }

    public static ProfileOtherSchoolType instanceFromValue(String value) {
        if (StringUtils.isNotNullOrEmpty(value)) {
            return map.get(value);
        } else {
            return null;
        }
    }

    public static ProfileOtherSchoolType instanceFromKey(String key) {
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
            ProfileOtherSchoolType type = instanceFromKey(entry.getKey());
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
                ProfileOtherSchoolType type = instanceFromValue((String)entry.get("key"));
                if (type != null) {
                    int lastline=0;
                    if(i==list.size()-1){
                        lastline=1;
                    }
                    messages.add(new Message(type.getValue(), entry.get("value"),i%2,lastline));
                    i++;
                }
            }
        }
        return messages;
    }
}
