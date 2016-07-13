package com.moseeker.dict.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.db.dictdb.tables.records.DictPositionRecord;
import com.moseeker.dict.dao.PositionDao;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.IndustryService.Iface;

@Service
public class PositionServiceImpl implements Iface {

	@Autowired
	private PositionDao positionDao; 
	
	@Override
	public Response getIndustriesByCode(int code) throws TException {
		List<Map<String, Object>> industryMaps = new ArrayList<>();
		List<DictPositionRecord> positions = positionDao.getIndustriesByParentCode(code);
		if(positions != null && positions.size() > 0) {
			positions.forEach(position -> {
				industryMaps.add(position.intoMap());
			});
		}
		return ResponseUtils.success(industryMaps);
	}

	public PositionDao getPositionDao() {
		return positionDao;
	}

	public void setPositionDao(PositionDao positionDao) {
		this.positionDao = positionDao;
	}

}
