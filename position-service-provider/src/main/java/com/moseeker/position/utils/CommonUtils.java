package com.moseeker.position.utils;

/**
 * Created by YYF
 *
 * Date: 2017/4/18
 *
 * Project_name :alphadog
 */
public class CommonUtils {

    /**
     * 学历要求
     */
    public static String DegreeRequire(Integer degree) {
        StringBuffer degreeRequire = new StringBuffer();
        if (degree.intValue() == 0) {
            degreeRequire.append("无");
        } else if (degree.intValue() == 1) {
            degreeRequire.append("大专");
        } else if (degree.intValue() == 2) {
            degreeRequire.append("本科");
        } else if (degree.intValue() == 3) {
            degreeRequire.append("硕士");
        } else if (degree.intValue() == 4) {
            degreeRequire.append("MBA");
        } else if (degree.intValue() == 5) {
            degreeRequire.append("博士");
        } else if (degree.intValue() == 6) {
            degreeRequire.append("中专");
        } else if (degree.intValue() == 7) {
            degreeRequire.append("高中");
        } else if (degree.intValue() == 8) {
            degreeRequire.append("博士后");
        } else if (degree.intValue() == 9) {
            degreeRequire.append("初中");
        }
        return degreeRequire.toString();
    }


}
