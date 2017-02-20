package com.moseeker.common.providerutils;

import java.util.ArrayList;
import java.util.HashMap;

import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;

/**
 * 通用查询工具辅助类，方便添加属性
 */
public class QueryUtil extends CommonQuery {

	private static final long serialVersionUID = 2531526866610292082L;

	/**
	 * 添加查询条件，以后会用Condition替换
	 * @param key 数据库字段名称
	 * @param value 值
	 * @return 查询工具类本身
	 */
	public QueryUtil addEqualFilter(String key, String value) {
		if(this.equalFilter == null) {
			this.equalFilter = new HashMap<String, String>();
		}
		this.equalFilter.put(key, value);
		return this;
	}

	/**
	 * 添加查询条件
	 * @param key 数据库字段名称
	 * @param value 值
	 * @return 查询工具类本身
	 */
	public QueryUtil addEqualFilter(String key, Object value) {
		if(this.equalFilter == null) {
			this.equalFilter = new HashMap<String, String>();
		}
		this.equalFilter.put(key, BeanUtils.converToString(value));
		return this;
	}

	/**
	 * 添加group条件
	 * @param attribute 数据库字段名称
	 * @return 查询工具类本身
	 */
	public QueryUtil addGroup(String attribute) {
		if(this.grouops == null) {
			this.grouops = new ArrayList<>();
		}
		this.grouops.add(attribute);
		return this;
	}

	/**
	 * 添加制定查询字段条件
	 * @param attribute 制定查询返回的字段
	 * @return 查询工具类本身
	 */
	public QueryUtil addSelectAttribute(String attribute) {
		if(this.attributes == null) {
			this.attributes = new ArrayList<>();
		}
		this.attributes.add(attribute);
		return this;
	}
}
