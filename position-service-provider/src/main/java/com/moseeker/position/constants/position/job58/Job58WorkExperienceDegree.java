package com.moseeker.position.constants.position.job58;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cjm
 * @date 2018-11-27 15:23
 **/
public enum Job58WorkExperienceDegree {
    NONE(1, "不限"),
    UNDER_ONE(4, "1年以下"),
    ONE_TWO(5, "1-2年"),
    THREE_FIVE(6, "3-5年"),
    SIX_SEVEN(7, "6-7年"),
    EIGHT_TEN(8, "8-10年"),
    MORE_TEN(9, "10年以上"),
    ;
    int degree;
    String description;
    Job58WorkExperienceDegree(int degree, String description){
        this.degree = degree;
        this.description = description;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public static Job58WorkExperienceDegree getWorkExperienceDegree(int workExperience){
        if(workExperience == 0){
            return NONE;
        }else if(workExperience <= 1){
            return UNDER_ONE;
        }else if(workExperience <= 2){
            return ONE_TWO;
        }else if(workExperience <= 5){
            return THREE_FIVE;
        }else if(workExperience <= 7){
            return SIX_SEVEN;
        }else if(workExperience <= 10){
            return EIGHT_TEN;
        }else {
            return MORE_TEN;
        }
    }

}
