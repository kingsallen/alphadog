package com.moseeker.function.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.SensitiveWordDB;
import com.moseeker.function.service.SensitiveWordService;

@Service
@CounterIface
public class SensitiveWordServiceImpl implements SensitiveWordService {

	private SensitiveWordDB db = SensitiveWordDB.getSingleton(); // 敏感词过滤
	
	@Override
	public List<Integer> verifySensitiveWords(List<String> contents) {
		List<Integer> result = new ArrayList<>();
		if(contents !=null && contents.size() > 0) {
			for(int i=0; i<contents.size(); i++) {
				int legal = 0;
				if (StringUtils.isNotNullOrEmpty(contents.get(i))) {
					if(db.sensitiveExamin(contents.get(i))) {
						legal = 1;
					}
				}
				result.add(legal);
			}
		}
		return result;
	}
}
