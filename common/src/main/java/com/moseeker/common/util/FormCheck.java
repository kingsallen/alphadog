package com.moseeker.common.util;

import com.moseeker.common.exception.ParamNullException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class FormCheck {

	private static String MOBILE_EXP = "^[1][3,4,5,6,7,8,9][0-9]{9}$";
	private static String GLOBAL_MOBILE_EXP = "^\\d{6,15}$";
	private static String EMAIL_EXP = "^([a-zA-Z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z]{2,6})$";
	private static String URL_EXP = "[a-zA-z]+://[^\\s]*";
	private static String EMPLOYEE_NAME = "^[\u4e00-\u9fa5a-zA-Z.·•\\s\\d]{2,100}$";

	public static String getMobileExp() {
		return GLOBAL_MOBILE_EXP;
	}
	
	public static String getGlobalMobileExp() {
		return GLOBAL_MOBILE_EXP;
	}

	public static String getEmailExp() {
		return EMAIL_EXP;
	}

	public static String getUrlExp() {
		return URL_EXP;
	}

	public static String getEmployeeName() {
		return EMPLOYEE_NAME;
	}

	/**
	 * 校验是否是只包含中文和英文字母、以及名字中用到的字符
	 * @param str 被校验的字符串
	 * @return true 符合要求，false不符合要求
	 */
	public static boolean isChineseAndCharacter(String str) {
		Pattern p = Pattern.compile(getEmployeeName());
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * @param str
	 *            被校验的字符串
	 * @return 是否校验通过，true：包含特殊字符；false：不包含特殊字符
	 * @throws PatternSyntaxException
	 *             校验规则异常，主要由于正则表达式出错
	 */
	public static boolean specialCharactor(String str)
			throws PatternSyntaxException, ParamNullException {
		if (str == null || str.trim().equals("")) {
			throw new ParamNullException();
		}
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 判断是否包含特殊字符
	 * 
	 * @param str
	 *            被校验的字符串
	 * @return 是否校验通过，true：包含特殊字符；false：不包含特殊字符
	 * @throws PatternSyntaxException
	 *             校验规则异常，主要由于正则表达式出错
	 */
	public static boolean shortSpecialCharactor(String str)
			throws PatternSyntaxException, ParamNullException {
		if (str == null || str.trim().equals("")) {
			throw new ParamNullException();
		}
		String regEx = "[~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 判断是否由6-20位的ascii编码组成的字符串
	 * 
	 * @param str
	 *            被校验的对象
	 * @return 是否校验通过，true：由6-20位的ascii编码组成的字符串；false：由6-20位的ascii编码组成的字符串
	 * @throws PatternSyntaxException
	 *             校验规则异常，主要由于正则表达式出错
	 */
	public static boolean pwdSpecialCharactor(String str)
			throws PatternSyntaxException, ParamNullException {
		if (str == null || str.trim().equals("")) {
			throw new ParamNullException();
		}
		String regEx = "^[\\x21-\\x73]{6,20}$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 判断是否是邮箱
	 * 
	 * @param str
	 * @return 校验规则异常，主要由于正则表达式出错
	 */
	public static boolean isEmail(String str) throws ParamNullException {
		if (str == null || str.trim().equals("")) {
			throw new ParamNullException();
		}
		Pattern p = Pattern.compile(getEmailExp());
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 判断是否是URL地址。不精确判断
	 * 
	 * @param str
	 *            被校验的对象
	 * @return 是否校验通过，true：由6-20位的ascii编码组成的字符串；false：由6-20位的ascii编码组成的字符串
	 */
	@Deprecated
	public static boolean isURL(String str) throws ParamNullException {
		if (str == null || str.trim().equals("")) {
			throw new ParamNullException();
		}
		Pattern p = Pattern.compile(URL_EXP);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 判断的依据是unicode中文字符的范围
	 * 
	 * @param str
	 *            被校验的对象
	 * @return
	 */
	public static boolean isChineseCharactor(String str)
			throws ParamNullException {
		if (str == null || str.trim().equals("")) {
			throw new ParamNullException();
		}
		String regEx = "^[\u4e00-\u9fa5]+$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 判断是否是电话号码
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPhone(String str) throws ParamNullException {
		if (str == null || str.trim().equals("")) {
			throw new ParamNullException();
		}
		String regEx = "^\\d{3}-\\d{8}|\\d{4}-\\d{7}$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	public static boolean isCharacter(String str) throws ParamNullException {
		if (StringUtils.isNullOrEmpty(str)) {
			throw new ParamNullException();
		}
		String regEx = "^[a-zA-z]*$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 判断是否是手机号码
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str) throws ParamNullException {
		if (str == null || str.trim().equals("")) {
			throw new ParamNullException();
		}
		Pattern p = Pattern.compile(getMobileExp());
		Matcher m = p.matcher(str);
		return m.find();
	}

	public static boolean isNumber(String str) throws ParamNullException {
		if (str == null || str.trim().equals("")) {
			throw new ParamNullException();
		}
		String regEx = "^[-]{0,1}[0-9]+\\.{0,1}[0-9]{0,2}$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	public static boolean isInteger(String str) throws ParamNullException {
		if (str == null || str.trim().equals("")) {
			throw new ParamNullException();
		}
		String regEx = "^([\\-]{0,1}[1-9]+[0-9]*)$|^0$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	public static boolean isDate(String str) throws ParamNullException {
		if (str == null || str.trim().equals("")) {
			throw new ParamNullException();
		}
		String regEx = "^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-"
				+ "(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|"
				+ "(02-(0[1-9]|[1][0-9]|2[0-8]))))$|^((([0-9]{2})(0[48]|[2468][048]|[13579][26])|"
				+ "((0[48]|[2468][048]|[3579][26])00))-02-29)$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	public static boolean isDateTime(String str) throws ParamNullException {
		if (str == null || str.trim().equals("")) {
			throw new ParamNullException();
		}
		String regEx = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-"
				+ "(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|"
				+ "(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|"
				+ "((0[48]|[2468][048]|[3579][26])00))-02-29)\\s{1,3}([0-1]{1}[0-9]{1}|[2]{1}[0-3]{1}):[0-5]{1}[0-9]{1}:[0-5]{1}[0-9]{1}";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	//@Test
	public void pwTest() {
		String str = "123【】6!@#$%^&*()-=_+";
		System.out.println(pwdSpecialCharactor(str));
	}

	//@Test
	public void isEmailTest() {
		// String str = "wengjianfei@moseeker.com";
		// String str = "hello@moseeker";
		// String str = "hello@moseeker.";
		// String str = "@moseeker.com";
		String str = "wengjianfei@mosee.com.cn";
		System.out.println(isEmail(str));
	}

	//@Test
	public void isIntegerTest() {
		String str = "011";
		System.out.println(isInteger(str));
	}

	//@Test
	public void isDateTest() {
		String date = "2012-02-30";
		System.out.println("date." + isDate(date));
	}

	//@Test
	public void isDateTimeTest() {
		String date = "2012-02-29 23:59:59";
		System.out.println("date." + isDateTime(date));
	}
}
