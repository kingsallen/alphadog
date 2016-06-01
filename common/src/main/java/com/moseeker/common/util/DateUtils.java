package com.moseeker.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;

public class DateUtils {
	
	private static final String SHOT_DATE = "yy-MM-dd";
	private static final String NORMAL_DATE = "yyyy-MM-dd";
	private static final String SHOT_TIME = "yyyy-MM-dd HH:mm:ss";
	private static final String LONG_TIME = "yyyy-MM-dd HH:mm:ss sss";
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
}
