package com.moseeker.common.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.thrift.TBase;
import org.jooq.impl.UpdatableRecordImpl;

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

	public static void copyUseMethod(Object dest, Object orig) {
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static <T extends TBase<?, ?>> T dbToStruct(
			UpdatableRecordImpl<?> r, Class<T> clazz) {
		// r.getClass().
		return null;
	}

	public static TBase<?, ?> dbToStruct(UpdatableRecordImpl<?> r, TBase<?, ?> t) {

		return null;
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

		if (clazzType.isAssignableFrom(String.class)) {
			return (T) converToString(value);
		} else if (clazzType.isAssignableFrom(Long.class)
				|| clazzType.isAssignableFrom(long.class)) {
			return (T) converToLong(value);
		} else if (clazzType.isAssignableFrom(Byte.class)
				|| clazzType.isAssignableFrom(byte.class)) {
			return (T) converToByte(value);
		} else if (clazzType.isAssignableFrom(Integer.class)
				|| clazzType.isAssignableFrom(int.class)) {
			return (T) converToInteger(value);
		} else if (clazzType.isAssignableFrom(Float.class)
				|| clazzType.isAssignableFrom(float.class)) {
			return (T) converToFloat(value);
		} else if (clazzType.isAssignableFrom(Double.class)
				|| clazzType.isAssignableFrom(double.class)) {
			return (T) converToDouble(value);
		} else if (clazzType.isAssignableFrom(Boolean.class)
				|| clazzType.isAssignableFrom(boolean.class)) {
			return (T) convertToBoolean(value);
		} else {
			return (T) value.toString();
		}
	}

	public static Boolean convertToBoolean(Object value) {
		if (value instanceof String) {
			return Boolean.valueOf((String) value);
		} else if (value instanceof Boolean) {
			return (Boolean) value;
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
		} else {
			return null;
		}
	}

	public static Double converToDouble(Object value) {
		if (value instanceof String) {
			return Double.valueOf((String) value);
		} else if (value instanceof Boolean) {
			if ((Boolean) value) {
				return Double.valueOf(1);
			} else {
				return Double.valueOf(0);
			}
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
		} else {
			return null;
		}
	}

	public static Float converToFloat(Object value) {
		if (value instanceof String) {
			return Float.valueOf((String) value);
		} else if (value instanceof Boolean) {
			if ((Boolean) value) {
				return Float.valueOf(1);
			} else {
				return Float.valueOf(0);
			}
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
		} else {
			return null;
		}
	}

	public static Integer converToInteger(Object value) {
		if (value instanceof String) {
			return Integer.valueOf((String) value);
		} else if (value instanceof Boolean) {
			if ((Boolean) value) {
				return Integer.valueOf(1);
			} else {
				return Integer.valueOf(0);
			}
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
		} else {
			return null;
		}
	}

	public static Byte converToByte(Object value) {
		if (value instanceof String) {
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
		} else if (value instanceof Integer) {
			return new Byte(((Integer) value).byteValue());
		} else if (value instanceof Float) {
			return new Byte(((Float) value).byteValue());
		} else if (value instanceof Long) {
			return Byte.valueOf(((Long) value).byteValue());
		} else if (value instanceof Double) {
			return (Byte) ((Double) value).byteValue();
		} else {
			return null;
		}
	}

	public static Long converToLong(Object value) {
		if (value instanceof String) {
			return Long.valueOf((String) value);
		} else if (value instanceof Byte) {
			return Long.valueOf((Byte) value);
		} else if (value instanceof Boolean) {
			if ((Boolean) value) {
				return Long.valueOf(1);
			} else {
				return Long.valueOf(0);
			}
		} else if (value instanceof Integer) {
			return Long.valueOf((Integer) value);
		} else if (value instanceof Float) {
			return Long.valueOf(((Float) value).longValue());
		} else if (value instanceof Long) {
			return (Long) value;
		} else if (value instanceof Double) {
			return (Long) ((Double) value).longValue();
		} else {
			return null;
		}
	}

	public static String converToString(Object value) {
		if (value instanceof String) {
			return (String) value;
		} else if (value instanceof Integer || value instanceof Byte
				|| value instanceof Long || value instanceof Float
				|| value instanceof Boolean) {
			return String.valueOf(value);
		} else {
			return null;
		}
	}
}
