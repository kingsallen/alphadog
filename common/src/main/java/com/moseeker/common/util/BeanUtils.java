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
}
