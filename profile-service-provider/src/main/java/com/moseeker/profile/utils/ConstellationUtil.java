package com.moseeker.profile.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by moseeker on 2018/1/29.
 */
public class ConstellationUtil {
    private final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22 };
    private final static String[] constellationArr = new String[] { "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座" };


    public static String getConstellation(Date date) {
        int month;
        int day;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        month = c.get(Calendar.MONTH) ;
        day = c.get(Calendar.DAY_OF_MONTH);
        return day < dayArr[month] ? constellationArr[month] : constellationArr[month];
    }
}
