package com.moseeker.common.annotation.notify;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface UpdateEs {
	
	/**
	 * 更新的表名
	 * @return
	 */
	String tableName();
	
	/**
	 * user_user.id的参数列表下标
	 * @return
	 */
	int argsIndex();
}
