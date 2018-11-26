package com.moseeker.position.constants.position.job58;

import java.util.HashMap;
import java.util.Map;

/**
 * 58 与仟寻的学历对应
 * @author cjm
 * @date 2018-11-24 14:46
 **/
public enum Job58PositionDegree {

    NONE(0, 1, "无", "不限"),
    SENIOR(7, 2, "高中", "高中"),
    SECONDARY_SPECIALIZED(6, 4, "中专", "中专"),
    JUNIOR_COLLEGE(1, 5, "大专", "大专"),
    UNDERGRADUATE(2, 6, "本科", "本科"),
    MASTER(3, 7, "硕士", "硕士"),
    MBA(4, 7, "MBA", "硕士"),
    DOCTOR(5, 8, "博士", "博士"),
    ;

    private int moseekerDegreeNo;
    private int job58DegreeNo;
    private String moseekerDegreeValue;
    private String job58DegreeValue;

    Job58PositionDegree(int moseekerDegreeNo, int job58DegreeNo, String moseekerDegreeValue, String job58DegreeValue) {
        this.moseekerDegreeNo = moseekerDegreeNo;
        this.job58DegreeNo = job58DegreeNo;
        this.moseekerDegreeValue = moseekerDegreeValue;
        this.job58DegreeValue = job58DegreeValue;
    }

    public int getMoseekerDegreeNo() {
        return moseekerDegreeNo;
    }

    public void setMoseekerDegreeNo(int moseekerDegreeNo) {
        this.moseekerDegreeNo = moseekerDegreeNo;
    }

    public int getJob58DegreeNo() {
        return job58DegreeNo;
    }

    public void setJob58DegreeNo(int job58DegreeNo) {
        this.job58DegreeNo = job58DegreeNo;
    }

    public String getMoseekerDegreeValue() {
        return moseekerDegreeValue;
    }

    public void setMoseekerDegreeValue(String moseekerDegreeValue) {
        this.moseekerDegreeValue = moseekerDegreeValue;
    }

    public String getJob58DegreeValue() {
        return job58DegreeValue;
    }

    public void setJob58DegreeValue(String job58DegreeValue) {
        this.job58DegreeValue = job58DegreeValue;
    }

    public static Job58PositionDegree getPositionDegree(int moseekerDegreeNo){
        return intToEnum.get(moseekerDegreeNo);
    }
    private static Map<Integer, Job58PositionDegree> intToEnum = new HashMap<>();
    static { // Initialize map from constant name to enum constant
        for (Job58PositionDegree op : values()){
            intToEnum.put(op.getMoseekerDegreeNo(), op);
        }
    }
}
