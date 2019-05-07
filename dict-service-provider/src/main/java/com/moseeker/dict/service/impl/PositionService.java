package com.moseeker.dict.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moseeker.baseorm.dao.dictdb.DictPositionDao;
import com.moseeker.baseorm.db.dictdb.tables.records.DictPositionRecord;
import com.moseeker.common.providerutils.QueryUtil;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.Response;

@Service
public class PositionService {

	@Autowired
	private DictPositionDao positionDao;
	
	@CounterIface
	public Response getPositionsByCode(String code) throws TException {
		List<Map<String, Object>> industryMaps = new ArrayList<>();
		List<DictPositionRecord> positions = null;
		try {
			if(code == null || code.equals("")) {
                QueryUtil query = new QueryUtil();
				query.setPageSize(Integer.MAX_VALUE);
				positions = positionDao.getRecords(query);
			} else {
				positions = positionDao.getIndustriesByParentCode(Integer.valueOf(code));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(positions != null && positions.size() > 0) {
			positions.forEach(position -> {
				Map<String, Object> positionMap = new HashMap<>();
				positionMap.put("name", position.getName());
				positionMap.put("code", position.getCode().intValue());
				positionMap.put("parent", position.getParent().intValue());
				positionMap.put("level", position.getLevel().intValue());
				//加入即可ename
				positionMap.put("ename", position.getLevel().intValue());
				industryMaps.add(positionMap);
			});
		}
		return ResponseUtils.success(industryMaps);
	}
}
