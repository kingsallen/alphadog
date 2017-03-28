package com.moseeker.dict.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.db.dictdb.tables.records.DictIndustryRecord;
import com.moseeker.db.dictdb.tables.records.DictIndustryTypeRecord;
import com.moseeker.dict.dao.IndustryDao;
import com.moseeker.dict.dao.IndustryTypeDao;
import com.moseeker.thrift.gen.common.struct.Response;

@Service
public class IndusteryService {
	
	Logger logger = LoggerFactory.getLogger(IndusteryService.class);

	@Autowired
	private IndustryDao industryDao; 
	
	@Autowired
	private IndustryTypeDao industryTypeDao; 
	
	@CounterIface
	public Response getIndustriesByCode(String code) throws TException {
		List<Map<String, Object>> industryMaps = new ArrayList<>();
		if(code == null || code.trim().equals("")) {
			List<DictIndustryTypeRecord> industryTypes = industryTypeDao.getAll();
			if(industryTypes != null && industryTypes.size() > 0) {
				industryTypes.forEach(industryType -> {
					Map<String, Object> industryMap = new HashMap<>();
					industryMap.put("type", 0);
					industryMap.put("code", industryType.getCode().intValue());
					industryMap.put("name", industryType.getName());
					industryMaps.add(industryMap);
				});
			}
			QueryUtil qu = new QueryUtil();
			qu.setPageSize(Integer.MAX_VALUE);
			List<DictIndustryRecord> industries = null;;
			try {
				industries = industryDao.getResources(qu);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			if(industries != null && industries.size() > 0) {
				industries.forEach(industry -> {
					Map<String, Object> industryMap = new HashMap<>();
					industryMap.put("type", industry.getType().intValue());
					industryMap.put("code", industry.getCode().intValue());
					industryMap.put("name", industry.getName());
					industryMaps.add(industryMap);
				});
			}
			
		} else if(code.equals("0")) {
			List<DictIndustryTypeRecord> industryTypes = industryTypeDao.getAll();
			if(industryTypes != null && industryTypes.size() > 0) {
				industryTypes.forEach(industryType -> {
					Map<String, Object> industryMap = new HashMap<>();
					industryMap.put("type", 0);
					industryMap.put("code", industryType.getCode().intValue());
					industryMap.put("name", industryType.getName());
					industryMaps.add(industryMap);
				});
			}
		} else {
			List<DictIndustryRecord> industries = industryDao.getIndustriesByType(Integer.valueOf(code));
			if(industries != null && industries.size() > 0) {
				industries.forEach(industry -> {
					Map<String, Object> industryMap = new HashMap<>();
					industryMap.put("type", industry.getType().intValue());
					industryMap.put("code", industry.getCode().intValue());
					industryMap.put("name", industry.getName());
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
