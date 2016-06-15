package com.moseeker.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.thrift.TBase;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;

/**
 * 
 * 利用反射的方式，对于get,set方法去除"get"和"set"剔除后相同,并且数据类型相同，
 * 进行值传递。比如被复制的方法存在getId返回int，存在复制对象存在setId(int id)，
 * 则调用被复制对象的的getId()拿到值作为参数，调用复制对象的setId(int id)。 利用这种方式进行类复制。
 * <p>
 * Company: MoSeeker
 * </P>
 * <p>
 * date: Apr 21, 2016
 * </p>
 * <p>
 * Email: wjf2255@gmail.com
 * </p>
 * 
 * @author wjf
 * @version
 */
public class BeanUtils {

	private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

	public static void main(String[] args) {
		String profile = "{\"status\": 0, \"resumes\": [{\"skills\": [], \"credentials\": [], \"attachment\": [], \"educations\": [{\"end_until_now\": 0, \"major_name\": \"\u8ba1\u7b97\u673a\u79d1\u5b66\u4e0e\u6280\u672f\", \"start\": \"2010-09\", \"degree_name\": \"3\", \"college_name\": \"\u5929\u6d25\u5de5\u4e1a\u5927\u5b66\", \"end\": \"2014-06\"}], \"basic\": {\"self_introduction\": \"\u6211\u662f\u4e00\u4e2a\u6d3b\u6cfc\u5f00\u6717\u7684\u4eba\uff0c\u7ecf\u8fc7\u8fd9\u4e00\u4e2a\u6708\u7684\u627e\u5de5\u4f5c\u6211\u7ec8\u4e8e\u660e\u767d\u4e86\u81ea\u5df1\u7684\u65b9\u5411\uff0c\u6211\u5e0c\u671b\u80fd\u627e\u5230\u4e00\u4e2a\u5408\u9002\u7684\u5de5\u4f5c\uff0c\u7136\u540e\u77e2\u5fd7\u4e0d\u6e1d\u7684\u575a\u6301\u4e0b\u53bb\u3002\", \"city_name\": \"\u5929\u6d25\", \"workstate\": \"4\", \"nationality_name\": \"\u5929\u6d25\", \"mobile\": \"15822226310\", \"username\": \"\u8e47\u667a\u534e\", \"birth\": \"1990-06\", \"gender\": \"1\", \"email\": \"chiwah.keen@gmail.com\"}, \"awards\": {\"name\": \"\", \"reward_date\": \"\", \"description\": \"\", \"level\": [\"\"]}, \"workexps\": [{\"salary_code\": 2, \"end_until_now\": 0, \"position_name\": \"\u9500\u552e\u4ee3\u8868\", \"description\": \"1\u3001\u6211\u7684\u65e5\u5e38\u5de5\u4f5c\u6709\u53d1\u5e03\u623f\u6e90\uff0c\u8054\u7cfb\u623f\u6e90\uff0c\u8054\u7cfb\u5ba2\u62372\u3001\u6211\u9500\u552e\u7684\u4ea7\u54c1\u662f\u4e8c\u624b\u79df\u8d413\u3001\u6211\u6240\u8d1f\u8d23\u7684\u4ea7\u54c1\u9500\u552e\u533a\u57df\u662f\u5929\u6d25\u5e02\u6cb3\u897f\u533a\u8d8a\u79c0\u8def4\u3001\u6211\u66fe\u53d6\u5f97\u7684\u9500\u552e\u4e1a\u7ee9\u662f\u7a81\u78345000\u5143\", \"start\": \"2012-07\", \"end\": \"2012-09\", \"company_name\": \"\u5929\u6d25\u4e2d\u539f\", \"industry_name\": \"\u623f\u5730\u4ea7/\u5efa\u7b51/\u5efa\u6750/\u5de5\u7a0b\", \"salary_type\": \"2\"}], \"intentions\": {\"salary_code\": \"0\", \"consider_venture_company_opportunities\": 0, \"industries\": [{\"industry_name\": \"I\"}, {\"industry_name\": \"T\"}, {\"industry_name\": \"\u670d\"}, {\"industry_name\": \"\u52a1\"}, {\"industry_name\": \"(\"}, {\"industry_name\": \"\u7cfb\"}, {\"industry_name\": \"\u7edf\"}, {\"industry_name\": \"/\"}, {\"industry_name\": \"\u6570\"}, {\"industry_name\": \"\u636e\"}, {\"industry_name\": \"/\"}, {\"industry_name\": \"\u7ef4\"}, {\"industry_name\": \"\u62a4\"}, {\"industry_name\": \")\"}], \"cities\": [{\"city_name\": \"\u5317\u4eac\"}], \"positions\": [{\"pposition_name\": \"\u9500\u552e\u4e1a\u52a1\u3001IT\u8d28\u91cf\u7ba1\u7406/\u6d4b\u8bd5/\u914d\u7f6e\u7ba1\u7406\"}], \"salary_type\": 2}, \"source\": \"4\", \"othermodifytime\": \"2016-05-27 10:31:45\", \"works\": [], \"import\": {}, \"projectexps\": [], \"languages\": []}]}";
		Map<String, Object> messagBean = (Map<String, Object>) JSON.parse(profile);
		List<Map<String, Object>> resumes = (List<Map<String, Object>>) messagBean.get("resumes");
		Map<String, Object> resume = resumes.get(0);
		ProfileBasicRecord record = MapToRecord((Map<String, Object>) resume.get("basic"), ProfileBasicRecord.class);
		System.out.println("self_introduction:" + record.getSelfIntroduction());

	}

	@SuppressWarnings("rawtypes")
	public static UpdatableRecordImpl structToDB(TBase dest, Class<? extends UpdatableRecordImpl> origClazz,
			Map<String, String> equalRules) {
		UpdatableRecordImpl orig = null;
		try {
			orig = origClazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("error", e);
		}
		structToDB(dest, orig, equalRules);
		return orig;
	}

	@SuppressWarnings("rawtypes")
	public static UpdatableRecordImpl structToDB(TBase dest, Class<? extends UpdatableRecordImpl> origClazz) {
		UpdatableRecordImpl orig = null;
		try {
			orig = origClazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("error", e);
		}
		structToDB(dest, orig, null);
		return orig;
	}

	/**
	 * struct 类和JOOQ类的属性和方法固定，可以预先加载成静态的属性和方法
	 * 
	 * @param dest
	 * @param orig
	 */
	public static void structToDB(@SuppressWarnings("rawtypes") TBase dest,
			@SuppressWarnings("rawtypes") UpdatableRecordImpl orig, Map<String, String> equalRules) {
		if (dest == null || orig == null) {
			return;
		}
		Field[] descFields = dest.getClass().getFields();
		Method[] destMethods = dest.getClass().getMethods();

		Method[] origMethods = orig.getClass().getMethods();

		int i = 0, j = 0, k = 0;
		if (descFields != null && descFields.length > 0 && destMethods != null && destMethods.length > 0) {
			for (i = 0; i < descFields.length; i++) {
				if (!descFields[i].getName().trim().equals("metaDataMap")) {
					Field field = descFields[i];
					String upperFirst = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
					String getMethodName = "get" + upperFirst;
					for (j = 0; j < destMethods.length; j++) {
						try {
							if (destMethods[j].getName().equals(getMethodName)) {
								/*if (defaultValue(field, destMethods[j], dest)) {
									continue;
								}*/
								Method isSetMethod = dest.getClass().getMethod("isSet" + upperFirst, new Class[] {});
								if ((Boolean) isSetMethod.invoke(dest, new Object[] {})) {
									String origMethodName = buiderRecordMethodName(field.getName(), MethodType.SET,
											equalRules);
									for (k = 0; k < origMethods.length; k++) {
										if (origMethods[k].getName().trim().equals(origMethodName)) {
											Object object = convertTo(destMethods[j].invoke(dest, new Object[] {}),
													origMethods[k].getParameterTypes()[0]);
											if (object != null) {
												origMethods[k].invoke(orig, object);
											}
											break;
										}
									}
								}
								break;
							}
						} catch (NoSuchMethodException | SecurityException | IllegalAccessException
								| IllegalArgumentException | InvocationTargetException e) {
							logger.error("error", e);
						} finally {
							// do nothing
						}
					}
				}
			}
		}
	}

	/*private static boolean defaultValue(Field field, Method destMethods, @SuppressWarnings("rawtypes") TBase dest) {
		if (field.getType().isAssignableFrom(int.class) || field.getType().isAssignableFrom(Integer.class)
				|| field.getType().isAssignableFrom(short.class) || field.getType().isAssignableFrom(Short.class)
				|| field.getType().isAssignableFrom(long.class) || field.getType().isAssignableFrom(Long.class)
				|| field.getType().isAssignableFrom(double.class) || field.getType().isAssignableFrom(Double.class)) {
			try {
				Integer object = convertTo(destMethods.invoke(dest, new Object[] {}), Integer.class);
				if(object != null && object.intValue() == -32768) {
					return true;
				} else {
					return false;
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				return true;
			}
		} else {
			return false;
		}
	}*/

	@SuppressWarnings("rawtypes")
	public static TBase DBToStruct(Class<? extends TBase> destClazz, UpdatableRecordImpl orig,
			Map<String, String> equalRules) {
		TBase base = null;
		try {
			base = destClazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("error", e);
		}
		DBToStruct(base, orig, equalRules);
		return base;
	}

	@SuppressWarnings("rawtypes")
	public static TBase DBToStruct(Class<? extends TBase> destClazz, UpdatableRecordImpl orig) {
		TBase base = null;
		try {
			base = destClazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("error", e);
		}
		DBToStruct(base, orig, null);
		return base;
	}

	public static void DBToStruct(@SuppressWarnings("rawtypes") TBase dest,
			@SuppressWarnings("rawtypes") UpdatableRecordImpl orig, Map<String, String> equalRules) {
		if (dest == null || orig == null) {
			return;
		}
		Field[] descFields = dest.getClass().getFields();
		Method[] destMethods = dest.getClass().getMethods();

		Method[] origMethods = orig.getClass().getMethods();

		int i = 0, j = 0, k = 0;
		if (descFields != null && descFields.length > 0 && destMethods != null && destMethods.length > 0) {
			for (i = 0; i < descFields.length; i++) {
				if (!descFields[i].getName().trim().equals("metaDataMap")) {
					Field field = descFields[i];
					String upperFirst = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
					String setMethodName = "set" + upperFirst;
					for (j = 0; j < destMethods.length; j++) {
						try {
							if (destMethods[j].getName().equals(setMethodName)) {
								String origMethodName = buiderRecordMethodName(field.getName(), MethodType.GET,
										equalRules);
								for (k = 0; k < origMethods.length; k++) {
									if (origMethods[k].getName().trim().equals(origMethodName)) {
										Object object = convertTo(origMethods[k].invoke(orig, new Object[] {}),
												destMethods[j].getParameterTypes()[0]);
										if (object != null) {
											destMethods[j].invoke(dest, object);
										}
										break;
									}
								}
								break;
							}
						} catch (SecurityException | IllegalAccessException | IllegalArgumentException
								| InvocationTargetException e) {
							logger.error("error", e);
						} finally {
							// do nothing
						}
					}
				}
			}
		}
	}

	public static <T> T MapToRecord(Map<String, Object> map, Class<T> clazz) {
		T t = null;
		Method[] methods = clazz.getMethods();
		if (map != null && methods != null && methods.length > 0 && map.size() > 0) {
			try {
				t = clazz.newInstance();
				for (Entry<String, Object> entry : map.entrySet()) {
					String origMethodName = buiderRecordMethodName(entry.getKey(), MethodType.SET, null);
					for (int i = 0; i < methods.length; i++) {
						if (methods[i].getName().equals(origMethodName)) {
							Object obj = convertTo(entry.getValue(), methods[i].getParameterTypes()[0]);
							if (obj != null) {
								methods[i].invoke(t, obj);
								break;
							}
						}
					}
				}
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				logger.error(e.getMessage(), e);
			} finally {
				// do nothing
			}
		}
		return t;
	}

	private enum MethodType {
		GET, SET;

		@Override
		public String toString() {
			return this.name().toLowerCase();
		}
	}

	private static String buiderRecordMethodName(String name, MethodType methodType, Map<String, String> equalRules) {
		if (name != null) {
			StringBuffer sb = new StringBuffer();
			sb.append(methodType);
			if (equalRules != null && equalRules.containsKey(name)) {
				sb.append(equalRules.get(name).substring(0, 1).toUpperCase());
				sb.append(equalRules.get(name).substring(1));
			} else {
				String[] splitArray = name.split("_");
				if (splitArray.length > 1) {
					for (int i = 0; i < splitArray.length; i++) {
						sb.append(splitArray[i].substring(0, 1).toUpperCase());
						sb.append(splitArray[i].substring(1));
					}
				} else {
					sb.append(name.substring(0, 1).toUpperCase());
					sb.append(name.substring(1));
				}
			}
			return sb.toString();
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param value
	 * @param clazzType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T StringConvertTo(String value, Class<T> clazzType) {
		if (clazzType.isAssignableFrom(String.class)) {
			return (T) value.toString();
		} else if (clazzType.isAssignableFrom(Long.class)) {
			return (T) new Long(value.toString());
		} else if (clazzType.isAssignableFrom(Byte.class)) {
			return (T) new Byte(value.toString());
		} else if (clazzType.isAssignableFrom(Integer.class)) {
			return (T) new Integer(value.toString());
		} else if (clazzType.isAssignableFrom(Float.class)) {
			return (T) new Float(value.toString());
		} else if (clazzType.isAssignableFrom(Float.class)) {
			return (T) new Double(value.toString());
		} else if (clazzType.isAssignableFrom(Boolean.class)) {
			return (T) new Boolean(value.toString());
		} else {
			return (T) value.toString();
		}
	}

	/**
	 * 
	 * @param value
	 * @param clazzType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertTo(Object value, Class<?> clazzType) {
		if (value == null || clazzType == null) {
			return null;
		}
		if (clazzType.isAssignableFrom(String.class)) {
			return (T) converToString(value);
		} else if (clazzType.isAssignableFrom(Long.class) || clazzType.isAssignableFrom(long.class)) {
			return (T) converToLong(value);
		} else if (clazzType.isAssignableFrom(Byte.class) || clazzType.isAssignableFrom(byte.class)) {
			return (T) converToByte(value);
		} else if (clazzType.isAssignableFrom(Integer.class) || clazzType.isAssignableFrom(int.class)) {
			return (T) converToInteger(value);
		} else if (clazzType.isAssignableFrom(Float.class) || clazzType.isAssignableFrom(float.class)) {
			return (T) converToFloat(value);
		} else if (clazzType.isAssignableFrom(Double.class) || clazzType.isAssignableFrom(double.class)) {
			return (T) converToDouble(value);
		} else if (clazzType.isAssignableFrom(Boolean.class) || clazzType.isAssignableFrom(boolean.class)) {
			return (T) convertToBoolean(value);
		} else if (clazzType.isAssignableFrom(java.sql.Date.class)) {
			return (T) convertToSQLDate(value);
		} else if (clazzType.isAssignableFrom(java.sql.Timestamp.class)) {
			return (T) convertToSQLTimestamp(value);
		} else if (clazzType.isAssignableFrom(UInteger.class)) {
			return (T) convertToUInteger(value);
		} else if (clazzType.isAssignableFrom(Short.class) || clazzType.isAssignableFrom(short.class)) {
			return (T) convertToShort(value);
		} else if (clazzType.isAssignableFrom(UByte.class)) {
			return (T) converToUByte(value);
		} else if (clazzType.isAssignableFrom(UShort.class)) {
			return (T) converToUShort(value);
		} else if (clazzType.isAssignableFrom(ULong.class)) {
			return (T) converToULong(value);
		} else {
			return null;
		}
	}

	private static ULong converToULong(Object value) {
		if (value instanceof String && !((String) value).trim().equals("")) {
			try {
				return ULong.valueOf((String) value);
			} catch (NumberFormatException e) {
				return ULong.valueOf(0);
			}
		} else if (value instanceof Boolean) {
			if ((Boolean) value) {
				return ULong.valueOf(1);
			} else {
				return ULong.valueOf(0);
			}
		} else if (value instanceof Integer) {
			return ULong.valueOf((Integer) value);
		} else if (value instanceof Byte) {
			return ULong.valueOf((Byte) value);
		} else if (value instanceof Float) {
			return ULong.valueOf(((Float) value).intValue());
		} else if (value instanceof Long) {
			return ULong.valueOf(((Long) value).shortValue());
		} else if (value instanceof Double) {
			return ULong.valueOf(((Double) value).shortValue());
		} else if (value instanceof UInteger) {
			return ULong.valueOf(((UInteger) value).intValue());
		} else if (value instanceof Date) {
			return ULong.valueOf((short) ((Date) value).getTime());
		} else if (value instanceof UShort) {
			return ULong.valueOf(((UShort) value).longValue());
		} else if (value instanceof UByte) {
			return ULong.valueOf(((UByte) value).shortValue());
		} else if (value instanceof ULong) {
			return (ULong) value;
		} else {
			return null;
		}
	}

	private static UShort converToUShort(Object value) {
		if (value instanceof String && !((String) value).trim().equals("")) {
			try {
				return UShort.valueOf((String) value);
			} catch (NumberFormatException e) {
				return UShort.valueOf(0);
			}
		} else if (value instanceof Boolean) {
			if ((Boolean) value) {
				return UShort.valueOf(1);
			} else {
				return UShort.valueOf(0);
			}
		} else if (value instanceof Integer) {
			return UShort.valueOf((Integer) value);
		} else if (value instanceof Byte) {
			return UShort.valueOf((Byte) value);
		} else if (value instanceof Float) {
			return UShort.valueOf(((Float) value).intValue());
		} else if (value instanceof Long) {
			return UShort.valueOf(((Long) value).shortValue());
		} else if (value instanceof ULong) {
			return UShort.valueOf(((ULong) value).shortValue());
		} else if (value instanceof Double) {
			return UShort.valueOf(((Double) value).shortValue());
		} else if (value instanceof UInteger) {
			return UShort.valueOf(((UInteger) value).intValue());
		} else if (value instanceof Date) {
			return UShort.valueOf((short) ((Date) value).getTime());
		} else if (value instanceof UShort) {
			return (UShort) value;
		} else if (value instanceof UByte) {
			return UShort.valueOf(((UByte) value).shortValue());
		} else {
			return null;
		}
	}

	private static UByte converToUByte(Object value) {
		if (value instanceof String && !((String) value).trim().equals("")) {
			try {
				return UByte.valueOf((String) value);
			} catch (NumberFormatException e) {
				return UByte.valueOf(0);
			}
		} else if (value instanceof Boolean) {
			if ((Boolean) value) {
				return UByte.valueOf(1);
			} else {
				return UByte.valueOf(0);
			}
		} else if (value instanceof Integer) {
			return UByte.valueOf((Integer) value);
		} else if (value instanceof Byte) {
			return UByte.valueOf((Byte) value);
		} else if (value instanceof Float) {
			return UByte.valueOf(((Float) value).intValue());
		} else if (value instanceof Long) {
			return UByte.valueOf((Long) value);
		} else if (value instanceof ULong) {
			return UByte.valueOf(((ULong) value).shortValue());
		} else if (value instanceof Double) {
			return UByte.valueOf(((Double) value).longValue());
		} else if (value instanceof UInteger) {
			return UByte.valueOf(((UInteger) value).intValue());
		} else if (value instanceof Date) {
			return UByte.valueOf(((Date) value).getTime());
		} else if (value instanceof UByte) {
			return (UByte) value;
		} else if (value instanceof UShort) {
			return UByte.valueOf(((UShort) value).shortValue());
		} else if (value instanceof Short) {
			return UByte.valueOf(((Short) value).shortValue());
		} else {
			return null;
		}
	}

	private static Short convertToShort(Object value) {
		if (value instanceof String && !((String) value).trim().equals("")) {
			try {
				return Short.valueOf((String) value);
			} catch (NumberFormatException e) {
				return 0;
			}
		} else if (value instanceof Boolean) {
			if ((Boolean) value) {
				return 1;
			} else {
				return 0;
			}
		} else if (value instanceof Integer) {
			return ((Integer) value).shortValue();
		} else if (value instanceof Byte) {
			return ((Byte) value).shortValue();
		} else if (value instanceof Float) {
			return ((Float) value).shortValue();
		} else if (value instanceof Long) {
			return ((Long) value).shortValue();
		} else if (value instanceof ULong) {
			return ((ULong) value).shortValue();
		} else if (value instanceof Double) {
			return ((Double) value).shortValue();
		} else if (value instanceof UInteger) {
			return ((UInteger) value).shortValue();
		} else if (value instanceof Date) {
			return Long.valueOf(((Date) value).getTime()).shortValue();
		} else if (value instanceof UByte) {
			return ((UByte) value).shortValue();
		} else if (value instanceof UShort) {
			return ((UShort) value).shortValue();
		} else {
			return null;
		}
	}

	private static UInteger convertToUInteger(Object value) {
		if (value instanceof String && !((String) value).trim().equals("")) {
			try {
				return UInteger.valueOf((String) value);
			} catch (NumberFormatException e) {
				return UInteger.valueOf(0);
			}
		} else if (value instanceof Boolean) {
			if ((Boolean) value) {
				return UInteger.valueOf(1);
			} else {
				return UInteger.valueOf(0);
			}
		} else if (value instanceof Short) {
			return UInteger.valueOf((Short) value);
		} else if (value instanceof Integer) {
			return UInteger.valueOf((Integer) value);
		} else if (value instanceof Byte) {
			return UInteger.valueOf((Byte) value);
		} else if (value instanceof Float) {
			return UInteger.valueOf(((Float) value).longValue());
		} else if (value instanceof Long) {
			return UInteger.valueOf((Long) value);
		} else if (value instanceof ULong) {
			return UInteger.valueOf(((ULong) value).intValue());
		} else if (value instanceof Double) {
			return UInteger.valueOf(((Double) value).longValue());
		} else if (value instanceof UInteger) {
			return (UInteger) value;
		} else if (value instanceof java.sql.Timestamp) {
			return UInteger.valueOf(((java.sql.Timestamp) value).getTime());
		} else if (value instanceof java.sql.Date) {
			return UInteger.valueOf(((java.sql.Date) value).getTime());
		} else if (value instanceof Date) {
			return UInteger.valueOf(((Date) value).getTime());
		} else if (value instanceof UByte) {
			return UInteger.valueOf(((UByte) value).intValue());
		} else if (value instanceof UShort) {
			return UInteger.valueOf(((UShort) value).intValue());
		} else {
			return null;
		}
	}

	private static java.sql.Timestamp convertToSQLTimestamp(Object value) {
		if (value instanceof String && !((String) value).trim().equals("")) {
			try {
				if(((String)value).length() == 10) {
					return new java.sql.Timestamp(DateUtils.shortDateToDate((String) value).getTime());
				} else if(((String)value).length() == 19) {
					return new java.sql.Timestamp(DateUtils.shortTimeToDate((String) value).getTime());
				} else {
					return new java.sql.Timestamp(DateUtils.shortTimeToDate((String) value).getTime());
				}
			} catch (ParseException e) {
				return null;
			}
		} else if (value instanceof Boolean) {
			return null;
		} else if (value instanceof UShort) {
			return new java.sql.Timestamp(((UShort) value).intValue());
		} else if (value instanceof Short) {
			return new java.sql.Timestamp((Short) value);
		} else if (value instanceof Integer) {
			return new java.sql.Timestamp((Integer) value);
		} else if (value instanceof Byte) {
			return new java.sql.Timestamp((Byte) value);
		} else if (value instanceof Float) {
			return new java.sql.Timestamp(((Float) value).longValue());
		} else if (value instanceof Long) {
			return new java.sql.Timestamp((Long) value);
		} else if (value instanceof ULong) {
			return new java.sql.Timestamp(((ULong) value).longValue());
		} else if (value instanceof Double) {
			return new java.sql.Timestamp(((Double) value).longValue());
		} else if (value instanceof UInteger) {
			return new java.sql.Timestamp(((UInteger) value).longValue());
		} else if (value instanceof java.sql.Timestamp) {
			return (java.sql.Timestamp) value;
		} else if (value instanceof java.sql.Date) {
			return new java.sql.Timestamp(((java.sql.Date) value).getTime());
		} else if (value instanceof Date) {
			return new java.sql.Timestamp(((Date) value).getTime());
		} else if (value instanceof UByte) {
			return new java.sql.Timestamp(((UByte) value).intValue());
		} else {
			return null;
		}
	}

	private static java.sql.Date convertToSQLDate(Object value) {
		if (value instanceof String && !((String) value).trim().equals("")) {
			try {
				return new java.sql.Date(DateUtils.nomalDateToDate((String) value).getTime());
			} catch (ParseException e) {
				return null;
			}
		} else if (value instanceof Boolean) {
			return null;
		} else if (value instanceof UShort) {
			return new java.sql.Date(((UShort) value).intValue());
		} else if (value instanceof Short) {
			return new java.sql.Date((Short) value);
		} else if (value instanceof Integer) {
			return new java.sql.Date((Integer) value);
		} else if (value instanceof Byte) {
			return new java.sql.Date((Byte) value);
		} else if (value instanceof Float) {
			return new java.sql.Date(((Float) value).longValue());
		} else if (value instanceof Long) {
			return new java.sql.Date((Long) value);
		} else if (value instanceof Double) {
			return new java.sql.Date(((Double) value).longValue());
		} else if (value instanceof UInteger) {
			return new java.sql.Date(((UInteger) value).longValue());
		} else if (value instanceof java.sql.Date) {
			return (java.sql.Date) value;
		} else if (value instanceof java.sql.Timestamp) {
			return new java.sql.Date(((java.sql.Timestamp) value).getTime());
		} else if (value instanceof Date) {
			return new java.sql.Date(((Date) value).getTime());
		} else if (value instanceof UByte) {
			return new java.sql.Date(((UByte) value).intValue());
		} else if (value instanceof ULong) {
			return new java.sql.Date(((ULong) value).longValue());
		} else {
			return null;
		}
	}

	public static Boolean convertToBoolean(Object value) {
		if (value instanceof String && !((String) value).trim().equals("")) {
			try {
				return Boolean.valueOf((String) value);
			} catch (Exception e) {
				return Boolean.FALSE;
			}
		} else if (value instanceof Boolean) {
			return (Boolean) value;
		} else if (value instanceof Short) {
			if ((Short) value > 0) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} else if (value instanceof UShort) {
			if (((UShort) value).shortValue() > 0) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} else if (value instanceof Integer) {
			if ((Integer) value > 0) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} else if (value instanceof Byte) {
			if ((Byte) value > 0) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} else if (value instanceof Float) {
			if ((Float) value > 0) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} else if (value instanceof Long) {
			if ((Long) value > 0) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} else if (value instanceof Double) {
			if ((Double) value > 0) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} else if (value instanceof UInteger) {
			if (((UInteger) value).intValue() > 0) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} else if (value instanceof UByte) {
			if (((UByte) value).byteValue() > 0) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} else if (value instanceof ULong) {
			if (((ULong) value).longValue() > 0) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} else {
			// sql.date sql.timestamp date timestamp
			if (value != null) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		}
	}

	public static Double converToDouble(Object value) {
		if (value instanceof String && !((String) value).trim().equals("")) {
			try {
				return Double.valueOf((String) value);
			} catch (NumberFormatException e) {
				return Double.valueOf(0);
			}
		} else if (value instanceof Boolean) {
			if ((Boolean) value) {
				return Double.valueOf(1);
			} else {
				return Double.valueOf(0);
			}
		} else if (value instanceof Short) {
			return Double.valueOf((Short) value);
		} else if (value instanceof UShort) {
			return ((UShort) value).doubleValue();
		} else if (value instanceof Integer) {
			return Double.valueOf((Integer) value);
		} else if (value instanceof Byte) {
			return Double.valueOf((Byte) value);
		} else if (value instanceof Float) {
			return Double.valueOf((Float) value);
		} else if (value instanceof Long) {
			return Double.valueOf((Long) value);
		} else if (value instanceof Double) {
			return (Double) value;
		} else if (value instanceof UInteger) {
			return ((UInteger) value).doubleValue();
		} else if (value instanceof Date) {
			return Double.valueOf(((Date) value).getTime());
		} else if (value instanceof UByte) {
			return ((UByte) value).doubleValue();
		} else if (value instanceof ULong) {
			return ((ULong) value).doubleValue();
		} else {
			return null;
		}
	}

	public static Float converToFloat(Object value) {
		if (value instanceof String && !((String) value).trim().equals("")) {
			try {
				return Float.valueOf((String) value);
			} catch (NumberFormatException e) {
				return Float.valueOf(0f);
			}
		} else if (value instanceof Boolean) {
			if ((Boolean) value) {
				return Float.valueOf(1);
			} else {
				return Float.valueOf(0);
			}
		} else if (value instanceof Short) {
			return (Float) ((Short) value).floatValue();
		} else if (value instanceof UShort) {
			return ((UShort) value).floatValue();
		} else if (value instanceof Integer) {
			return (Float) ((Integer) value).floatValue();
		} else if (value instanceof Byte) {
			return (Float) ((Byte) value).floatValue();
		} else if (value instanceof Float) {
			return (Float) value;
		} else if (value instanceof Long) {
			return (Float) ((Long) value).floatValue();
		} else if (value instanceof Double) {
			return (Float) ((Double) value).floatValue();
		} else if (value instanceof UInteger) {
			return (Float) ((UInteger) value).floatValue();
		} else if (value instanceof Date) {
			return (Float) Float.valueOf(((Date) value).getTime());
		} else if (value instanceof UByte) {
			return ((UByte) value).floatValue();
		} else if (value instanceof ULong) {
			return ((ULong) value).floatValue();
		} else {
			return null;
		}
	}

	public static Integer converToInteger(Object value) {
		if (value instanceof String && !((String) value).trim().equals("")) {
			try {
				return Integer.valueOf((String) value);
			} catch (NumberFormatException e) {
				return Integer.valueOf(0);
			}
		} else if (value instanceof Boolean) {
			if ((Boolean) value) {
				return Integer.valueOf(1);
			} else {
				return Integer.valueOf(0);
			}
		} else if (value instanceof Short) {
			return (Integer) ((Short) value).intValue();
		} else if (value instanceof UShort) {
			return ((UShort) value).intValue();
		} else if (value instanceof Integer) {
			return (Integer) value;
		} else if (value instanceof Byte) {
			return (Integer) ((Byte) value).intValue();
		} else if (value instanceof Float) {
			return (Integer) ((Float) value).intValue();
		} else if (value instanceof Long) {
			return (Integer) ((Long) value).intValue();
		} else if (value instanceof Double) {
			return (Integer) ((Double) value).intValue();
		} else if (value instanceof UInteger) {
			return ((UInteger) value).intValue();
		} else if (value instanceof Date) {
			return (int) ((Date) value).getTime();
		} else if (value instanceof UByte) {
			return ((UByte) value).intValue();
		} else if (value instanceof ULong) {
			return ((ULong) value).intValue();
		} else {
			return null;
		}
	}

	public static Byte converToByte(Object value) {
		if (value instanceof String && !((String) value).trim().equals("")) {
			return Byte.valueOf((String) value);
		} else if (value instanceof Byte) {
			return (Byte) value;
		} else if (value instanceof Boolean) {
			// todo 需要考虑一个类似l表示long基本数据类型 存在一个b表示该数值是byte的方式
			byte b = 1;
			if ((Boolean) value) {
				return Byte.valueOf(b);
			} else {
				b = 0;
				return Byte.valueOf(b);
			}
		} else if (value instanceof Short) {
			return (Byte) ((Short) value).byteValue();
		} else if (value instanceof UShort) {
			return ((UShort) value).byteValue();
		} else if (value instanceof Integer) {
			return (Byte) ((Integer) value).byteValue();
		} else if (value instanceof Float) {
			return (Byte) ((Float) value).byteValue();
		} else if (value instanceof Long) {
			return (Byte) ((Long) value).byteValue();
		} else if (value instanceof Double) {
			return (Byte) ((Double) value).byteValue();
		} else if (value instanceof UInteger) {
			return (Byte) ((UInteger) value).byteValue();
		} else if (value instanceof Date) {
			return (byte) ((Date) value).getTime();
		} else if (value instanceof UByte) {
			return ((UByte) value).byteValue();
		} else if (value instanceof ULong) {
			return ((ULong) value).byteValue();
		} else {
			return null;
		}
	}

	public static Long converToLong(Object value) {
		if (value instanceof String && !((String) value).trim().equals("")) {
			return Long.valueOf((String) value);
		} else if (value instanceof Byte) {
			return Long.valueOf((Byte) value);
		} else if (value instanceof Boolean) {
			if ((Boolean) value) {
				return Long.valueOf(1);
			} else {
				return Long.valueOf(0);
			}
		} else if (value instanceof Short) {
			return (Long) ((Short) value).longValue();
		} else if (value instanceof UShort) {
			return ((UShort) value).longValue();
		} else if (value instanceof Integer) {
			return Long.valueOf((Integer) value);
		} else if (value instanceof Float) {
			return Long.valueOf(((Float) value).longValue());
		} else if (value instanceof Long) {
			return (Long) value;
		} else if (value instanceof Double) {
			return (Long) ((Double) value).longValue();
		} else if (value instanceof UInteger) {
			return ((UInteger) value).longValue();
		} else if (value instanceof Date) {
			return ((Date) value).getTime();
		} else if (value instanceof UByte) {
			return ((UByte) value).longValue();
		} else if (value instanceof ULong) {
			return ((ULong) value).longValue();
		} else {
			return null;
		}
	}

	public static String converToString(Object value) {
		if (value instanceof String) {
			return (String) value;
		} else if (value instanceof java.sql.Timestamp) {
			return DateUtils.dateToShortTime(((java.sql.Timestamp) value));
		} else if (value instanceof Date) {
			return DateUtils.dateToNormalDate(((Date) value));
		} else if (value != null) {
			return value.toString();
		} else {
			return null;
		}
	}
}
