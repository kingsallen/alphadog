package com.moseeker.profile.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YYF
 *
 * Date: 2017/8/30
 *
 * Project_name :alphadog
 */
public enum DegreeSource {

    NOCHOICE(0, "未选择",""),
    MIDDLESCHOOL(1, "初中及以下","Junior High and Below","Junior High School"),
    SECONDARY(2, "中专","Technical School"),
    HIGHTSCHOOL(3, "高中","High School","Senior High School"),
    JUNIORCOLLEGE(4, "大专","Junior College"),
    UNDERGRADUATE(5, "本科","Bachelor","Undergraduate"),
    MASTER(6, "硕士","研究生","Master"),
    DOCTOR(7, "博士","Doctorate"),
    POSTDOCTOR(8, "博士以上","Postdoctor"),
    OTHER(9, "其他","Other");

    private int value;
    private String[] degree;


    DegreeSource(int value, String ...degree) {
        this.value = value;
        this.degree = degree;
    }

    public static final Map<String, Integer> intToEnum = new HashMap();

    static {
        for (DegreeSource op : values()) {
            for (String degree : op.getDegree()) {
                intToEnum.put(degree.toLowerCase(), op.getValue());
            }
        }
    }

    public static int get(String degree){
        if(intToEnum.containsKey(degree.toLowerCase())){
            return intToEnum.get(degree.toLowerCase());
        }
        return NOCHOICE.value;
    }



    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String[] getDegree() {
        return degree;
    }

    public void setDegree(String[] degree) {
        this.degree = degree;
    }
}
