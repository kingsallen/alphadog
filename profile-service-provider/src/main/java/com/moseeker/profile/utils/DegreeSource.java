package com.moseeker.profile.utils;

/**
 * Created by YYF
 *
 * Date: 2017/8/30
 *
 * Project_name :alphadog
 */
public enum DegreeSource {


    UNDERGRADUATE(1, "本科");

    private int value;
    private String degree;


    DegreeSource(int value, String degree) {
        this.value = value;
        this.degree = degree;
    }
}
