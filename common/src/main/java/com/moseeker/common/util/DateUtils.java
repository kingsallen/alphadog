package com.moseeker.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

public class DateUtils {

    public static final String SHOT_DATE = "yy-MM-dd";
    public static final String NORMAL_DATE = "yyyy-MM-dd";
    public static final String SHOT_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String LONG_TIME = "yyyy-MM-dd HH:mm:ss sss";

    private static final SimpleDateFormat SHOT_DATE_SDF = new SimpleDateFormat(SHOT_DATE);
    private static final SimpleDateFormat normalDateSDF = new SimpleDateFormat(NORMAL_DATE);
    private static final SimpleDateFormat shotTimeSDF = new SimpleDateFormat(SHOT_TIME);
    private static final SimpleDateFormat longTimeSDF = new SimpleDateFormat(LONG_TIME);

    public static String dateToPattern(Date date, String pattern) {
        DateTime dt = new DateTime(date);
        return dt.toString(pattern);
    }

    public static String dateToShortDate(Date date) {
        return SHOT_DATE_SDF.format(date);
    }

    public static String dateToNormalDate(Date date) {
        return normalDateSDF.format(date);
    }

    public static String dateToShortTime(Date date) {
        return shotTimeSDF.format(date);
    }

    public static String dateToLongTime(Date date) {
        return longTimeSDF.format(date);
    }

    public static Date shortDateToDate(String shortDate) throws ParseException {
        Date date = SHOT_DATE_SDF.parse(shortDate);
        return date;
    }

    public static Date nomalDateToDate(String normalDate) throws ParseException {
        Date date = normalDateSDF.parse(normalDate);
        return date;
    }

    public static Date shortTimeToDate(String shortTime) throws ParseException {
        Date date = shotTimeSDF.parse(shortTime);
        return date;
    }

    public static Date longTimeToDate(String longTime) throws ParseException {
        Date date = longTimeSDF.parse(longTime);
        return date;
    }

    /**
     * 得到本月最后一天的日期
     *
     * @return Date
     * @Methods Name getLastDayOfMonth
     */
    public static long getLastDayOfMonth() {
        Calendar cDay = Calendar.getInstance();
        cDay.set(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                23,
                59,
                59
        );
        cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cDay.getTimeInMillis();
    }

    /**
     * 计算本月剩余秒数, 月末-当前
     *
     * @return
     */
    public static long calcCurrMonthSurplusSeconds() {
        return (getLastDayOfMonth() - System.currentTimeMillis()) / 1000;
    }

    /**
     * 当前季度的开始时间，即2012-01-1 00:00:00
     *
     * @return
     */
    public static LocalDateTime getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        LocalDateTime now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 6);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = LocalDateTime.parse(normalDateSDF.format(c.getTime()) + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时间，即2012-03-31 23:59:59
     *
     * @return
     */
    public static LocalDateTime getCurrentQuarterEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        LocalDateTime now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = LocalDateTime.parse(normalDateSDF.format(c.getTime()) + " 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }
}
