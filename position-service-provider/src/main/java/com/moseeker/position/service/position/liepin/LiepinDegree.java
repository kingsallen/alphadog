package com.moseeker.position.service.position.liepin;

/**
 * Created by zhangdi on 2017/6/23.
 */
public enum  LiepinDegree {
    /**
     *
     <li data-value="000"><a href="javascript:;">不限</a></li>
     <li data-value="050"><a href="javascript:;">大专</a></li>
     <li data-value="040"><a href="javascript:;">本科</a></li>
     <li data-value="030"><a href="javascript:;">硕士</a></li>
     <li data-value="020"><a href="javascript:;">MBA/EMBA</a></li>
     <li data-value="010"><a href="javascript:;">博士</a></li>
     <li data-value="005"><a href="javascript:;">博士后</a></li>
     <li data-value="060"><a href="javascript:;">中专/中技</a></li>
     </ul>
     */
    NotRequired("不限", "000"),
    JuniorCollege("大专","050"),
    College("本科","040"),
    Master("硕士","030"),
    MBA("MBA/EMBA","020"),
    Doctor("博士","010"),
    PostDoctor("博士后","005"),
    MiddleCollege("中专/中技","060");

    //feature废弃字段hr3.5.9.3
    /*Polytechnic("中技","070"),
    HighSchool("高中","080"),
    Junior("初中","090");*/

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
