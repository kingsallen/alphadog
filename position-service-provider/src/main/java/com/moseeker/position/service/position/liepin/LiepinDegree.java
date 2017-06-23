package com.moseeker.position.service.position.liepin;

/**
 * Created by zhangdi on 2017/6/23.
 */
public enum  LiepinDegree {
    /**
     * <li data-value="000"><a href="javascript:;">不限</a></li>
     <li data-value="005"><a href="javascript:;">博士后</a></li>
     <li data-value="010"><a href="javascript:;">博士</a></li>
     <li data-value="020"><a href="javascript:;">MBA/EMBA</a></li>
     <li data-value="030"><a href="javascript:;">硕士</a></li>
     <li data-value="040"><a href="javascript:;">本科</a></li>
     <li data-value="050"><a href="javascript:;">大专</a></li>
     <li data-value="060"><a href="javascript:;">中专</a></li>
     <li data-value="070"><a href="javascript:;">中技</a></li>
     <li data-value="080"><a href="javascript:;">高中</a></li>
     <li data-value="090"><a href="javascript:;">初中</a></li>
     */

    None("", "-1"),
    NotRequired("不限", "000"),
    PostDoctor("博士后","005"),
    Doctor("博士","010"),
    MBA("MBA/EMBA","020"),
    Master("硕士","030"),
    College("本科","040"),
    JuniorCollege("大专","050"),
    MiddleCollege("中专","060"),
    Polytechnic("中技","070"),
    HighSchool("高中","080"),
    Junior("初中","090");

    private String name;
    private String value;

    private LiepinDegree(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
