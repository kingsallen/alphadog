package com.moseeker.dict.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.db.dictdb.tables.records.DictIndustryRecord;
import com.moseeker.db.dictdb.tables.records.DictIndustryTypeRecord;
import com.moseeker.dict.dao.IndustryDao;
import com.moseeker.dict.dao.IndustryTypeDao;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.IndustryService.Iface;

@Service
public class IndusteryServiceImpl implements Iface {

	@Autowired
	private IndustryDao industryDao; 
	
	@Autowired
	private IndustryTypeDao industryTypeDao; 
	
	@Override
	public Response getIndustriesByCode(int code) throws TException {
		List<Map<String, Object>> industryMaps = new ArrayList<>();
		if(code == 0) {
			List<DictIndustryTypeRecord> industryTypes = industryTypeDao.getAll();
			if(industryTypes != null && industryTypes.size() > 0) {
				industryTypes.forEach(industryType -> {
					Map<String, Object> industryMap = industryType.intoMap();
					industryMap.put("type", 0);
					industryMaps.add(industryMap);
				});
			}
		} else {
			List<DictIndustryRecord> industries = industryDao.getIndustriesByType(code);
			if(industries != null && industries.size() > 0) {
				industries.forEach(industry -> {
					Map<String, Object> industryMap = industry.intoMap();
					industryMaps.add(industryMap);
				});
			}
		}
		
		return ResponseUtils.success(industryMaps);
	}

	public IndustryDao getIndustryDao() {
		return industryDao;
	}

	public void setIndustryDao(IndustryDao industryDao) {
		this.industryDao = industryDao;
	}
}
