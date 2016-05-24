package com.moseeker.common.providerutils;

import java.util.HashMap;

import com.moseeker.thrift.gen.common.struct.CommonQuery;

public class QueryUtil extends CommonQuery {

	private static final long serialVersionUID = 2531526866610292082L;

	public void addEqualFilter(String key, String value) {
		if(this.equalFilter == null) {
			this.equalFilter = new HashMap<String, String>();
		}
		this.equalFilter.put(key, value);
	}
}
