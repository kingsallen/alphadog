package com.moseeker.position.constants.position;

import com.moseeker.common.constants.ChannelType;

import java.util.HashMap;
import java.util.Map;

/**
 * 猎聘职位枚举
 *
 * @author cjm
 * @date 2018-05-30 15:34
 **/
public enum LiePinPositionDegree {

    NONE(0, "", "无", "不限"),
    JUNIOR_COLLEGE(1, "050", "大专", "大专"),
    UNDERGRADUATE(2, "040", "本科", "本科"),
    MASTER(3, "030", "硕士", "硕士"),
    MBA(4, "020", "MBA", "MBA/EMBA"),
    DOCTOR(5, "010", "博士", "博士"),
    SECONDARY_SPECIALIZED(6, "060", "中专", "中专"),
    SENIOR(7, "080", "高中", "高中"),
    POST_DOCTIORAL(8, "005", "博士后", "博士后"),
    JUNIOR(9, "090", "初中", "初中"),;

    private int moseekerDegreeNo;
    private String liePinDegreeNo;
    private String moseekerDegreeValue;
    private String liePinDegreeValue;

    LiePinPositionDegree(int moseekerDegreeNo, String liePinDegreeNo, String moseekerDegreeValue, String liePinDegreeValue) {
        this.moseekerDegreeNo = moseekerDegreeNo;
        this.liePinDegreeNo = liePinDegreeNo;
        this.moseekerDegreeValue = moseekerDegreeValue;
        this.liePinDegreeValue = liePinDegreeValue;
    }

    public int getMoseekerDegreeNo() {
        return moseekerDegreeNo;
    }

    public void setMoseekerDegreeNo(int moseekerDegreeNo) {
        this.moseekerDegreeNo = moseekerDegreeNo;
    }

    public String getLiePinDegreeNo() {
        return liePinDegreeNo;
    }

    public void setLiePinDegreeNo(String liePinDegreeNo) {
        this.liePinDegreeNo = liePinDegreeNo;
    }

    public String getMoseekerDegreeValue() {
        return moseekerDegreeValue;
    }

    public void setMoseekerDegreeValue(String moseekerDegreeValue) {
        this.moseekerDegreeValue = moseekerDegreeValue;
    }

    public String getLiePinDegreeValue() {
        return liePinDegreeValue;
    }

    public void setLiePinDegreeValue(String liePinDegreeValue) {
        this.liePinDegreeValue = liePinDegreeValue;
    }

    public static LiePinPositionDegree getPositionDegree(int moseekerDegreeNo){
        return intToEnum.get(moseekerDegreeNo);
    }
    private static Map<Integer, LiePinPositionDegree> intToEnum = new HashMap<>();
    static { // Initialize map from constant name to enum constant
        for (LiePinPositionDegree op : values()){
            intToEnum.put(op.getMoseekerDegreeNo(), op);
        }
    }
}
