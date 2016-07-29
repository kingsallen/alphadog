package com.moseeker.function.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.SensitiveWordDB;
import com.moseeker.function.service.SensitiveWordService;

@Service
public class SensitiveWordServiceImpl implements SensitiveWordService {

	private SensitiveWordDB db = SensitiveWordDB.getSingleton(); // 敏感词过滤
	
	@Override
	public List<Boolean> verifySensitiveWords(List<String> contents) {
		List<Boolean> result = new ArrayList<>();
		if(contents !=null && contents.size() > 0) {
			for(int i=0; i<contents.size(); i++) {
				boolean legal = false;
				if (StringUtils.isNotNullOrEmpty(contents.get(i))) {
					legal = db.sensitiveExamin(contents.get(i));
				}
				result.add(legal);
			}
		}
		return result;
	}
}
