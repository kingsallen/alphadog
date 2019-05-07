package com.moseeker.dict.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moseeker.baseorm.dao.dictdb.DictIndustryDao;
import com.moseeker.baseorm.dao.dictdb.DictIndustryTypeDao;
import com.moseeker.baseorm.dao.dictdb.DictMarsMajorDao;
import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryTypeRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictMarsMajorRecord;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.Response;

@Service
public class IndusteryService {
	
	Logger logger = LoggerFactory.getLogger(IndusteryService.class);

	@Autowired
	private DictIndustryDao industryDao;
	
	@Autowired
	private DictIndustryTypeDao industryTypeDao;

	@Autowired
	private DictMarsMajorDao marsMajorDao;
	
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
			List<DictIndustryRecord> industries = null;
			try {
				industries = industryDao.getRecords(qu);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			if(industries != null && industries.size() > 0) {
				industries.forEach(industry -> {
					Map<String, Object> industryMap = new HashMap<>();
					industryMap.put("type", industry.getType());
					industryMap.put("code", industry.getCode().intValue());
					industryMap.put("name", industry.getName());
					industryMap.put("ename", industry.getEname());
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
					industryMap.put("type", industry.getType());
					industryMap.put("code", industry.getCode().intValue());
					industryMap.put("name", industry.getName());
					industryMap.put("ename", industry.getEname());
					industryMaps.add(industryMap);
				});
			}
		}
		
		return ResponseUtils.success(industryMaps);
	}

	public Response getMarsIndustriesByCode(String code) {
		List<Map<String, Object>> marsIndustryMaps = new ArrayList<>();
		if(code == null || code.trim().equals("")) {
			List<DictMarsMajorRecord> industryTypes = marsMajorDao.getAll();
			if(industryTypes != null && industryTypes.size() > 0) {
				industryTypes.forEach(industryType -> {
					Map<String, Object> industryMap = new HashMap<>();
					industryMap.put("type", industryType.getParentId());
					industryMap.put("code", industryType.getId());
					industryMap.put("name", industryType.getName());
					marsIndustryMaps.add(industryMap);
				});
			}
		} else if(code.equals("0")) {
			List<DictMarsMajorRecord> industryTypes = marsMajorDao.getIndustriesByType(0);
			if(industryTypes != null && industryTypes.size() > 0) {
				industryTypes.forEach(industryType -> {
					Map<String, Object> industryMap = new HashMap<>();
					industryMap.put("type", 0);
					industryMap.put("code", industryType.getId());
					industryMap.put("name", industryType.getName());
					marsIndustryMaps.add(industryMap);
				});
			}
		} else {
			List<DictMarsMajorRecord> industries = marsMajorDao.getIndustriesByType(Integer.valueOf(code));
			if(industries != null && industries.size() > 0) {
				industries.forEach(industry -> {
					Map<String, Object> industryMap = new HashMap<>();
					industryMap.put("type", industry.getParentId());
					industryMap.put("code", industry.getId());
					industryMap.put("name", industry.getName());
					marsIndustryMaps.add(industryMap);
				});
			}
		}

		return ResponseUtils.success(marsIndustryMaps);
	}
}
