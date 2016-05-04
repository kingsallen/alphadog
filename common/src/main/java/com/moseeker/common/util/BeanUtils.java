package com.moseeker.common.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.thrift.TBase;
import org.jooq.impl.UpdatableRecordImpl;

/**
 * 
 * 利用反射的方式，对于get,set方法去除"get"和"set"剔除后相同,并且数据类型相同，
 * 进行值传递。比如被复制的方法存在getId返回int，存在复制对象存在setId(int id)，
 * 则调用被复制对象的的getId()拿到值作为参数，调用复制对象的setId(int id)。
 * 利用这种方式进行类复制。
 * <p>Company: MoSeeker</P>  
 * <p>date: Apr 21, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
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

	public static <T extends TBase<?, ?>> T dbToStruct(UpdatableRecordImpl<?> r, Class<T> clazz) {
		//r.getClass().
		return null;
	}
	
	public static TBase<?,?> dbToStruct(UpdatableRecordImpl<?> r, TBase<?,?> t) {
		
		return null;
	}
	
	 /**
     * 类型转换。提供将对象转成指定的类型的功能
     *
     * @param value     被转换的对象
     * @param clazzType 指定一个转成的类型
     * @return 返回转换的结果
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertTo(Object value, Class<T> clazzType) {
        if (clazzType.isAssignableFrom(String.class)) {
            return (T) value.toString();
        } else if (clazzType.isAssignableFrom(Long.class) || clazzType.isAssignableFrom(long.class)) {
            return (T) value;
        } else if (clazzType.isAssignableFrom(Byte.class) || clazzType.isAssignableFrom(byte.class)) {
            return (T) value;
        } else if (clazzType.isAssignableFrom(Integer.class) || clazzType.isAssignableFrom(int.class)) {
            return (T) value;
        } else if (clazzType.isAssignableFrom(Float.class) || clazzType.isAssignableFrom(float.class)) {
            return (T) value;
        } else if (clazzType.isAssignableFrom(Boolean.class) || clazzType.isAssignableFrom(boolean.class)) {
            return (T) value;
        } else {
            return null;
        }
    }
    
    /**
     * 类型转换。提供将对象转成指定的类型的功能
     *
     * @param value     被转换的对象
     * @param clazzType 指定一个转成的类型
     * @return 返回转换的结果
     */
    @SuppressWarnings("unchecked")
    public static <T> T StringConvertTo(String value, Class<T> clazzType) {
        if (clazzType.isAssignableFrom(String.class)) {
            return (T) value;
        } else if (clazzType.isAssignableFrom(Long.class) || clazzType.isAssignableFrom(long.class)) {
            return (T) new Long(value);
        } else if (clazzType.isAssignableFrom(Byte.class) || clazzType.isAssignableFrom(byte.class)) {
            return (T) new Byte(value);
        } else if (clazzType.isAssignableFrom(Integer.class) || clazzType.isAssignableFrom(int.class)) {
            return (T) new Integer(value);
        } else if (clazzType.isAssignableFrom(Float.class) || clazzType.isAssignableFrom(float.class)) {
            return (T) new Float(value);
        } else if (clazzType.isAssignableFrom(Boolean.class) || clazzType.isAssignableFrom(boolean.class)) {
            return (T) new Boolean(value);
        } else {
            return (T) value.toString();
        }
    }
}
