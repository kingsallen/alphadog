package com.moseeker.entity.Constant;

/**
 * 简历字段长度限制常量
 * Created by jack on 09/11/2017.
 */
public enum ProfileAttributeLengthLimit {

    WorkExpDescriptionLengthLimit(900, "工作经历描述的长度限制"), WorkExpJobLengthLimit(100, "工作经历职位的长度限制");

    private int lengthLimit;
    private String attributeName;
    private String name;

    private ProfileAttributeLengthLimit(int lengthLimit, String attributeName) {
        this.lengthLimit = lengthLimit;
        this.attributeName = attributeName;
        name = attributeName+":"+900;
    }

    public int getLengthLimit() {
        return lengthLimit;
    }


    @Override
    public String toString() {
        return name;
    }
}
