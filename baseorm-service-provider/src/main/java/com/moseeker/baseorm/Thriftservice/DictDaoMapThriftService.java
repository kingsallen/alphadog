package com.moseeker.baseorm.Thriftservice;

import com.moseeker.baseorm.tool.QueryConvert;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.dictdb.DictCityMapDao;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityMapRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dict.struct.CityMap;

@Service
public class DictDaoMapThriftService implements com.moseeker.thrift.gen.dao.service.DictDao.Iface {

	@Autowired
	private DictCityMapDao cityMapDao;
	
	@Override
	public CityMap getDictMap(CommonQuery query) throws TException {
		CityMap cityMap = new CityMap();
		;
		try {
			DictCityMapRecord record = cityMapDao.getResource(QueryConvert.commonQueryConvertToQuery(query));
			if(record != null) {
				cityMap.setId(record.getId());
				if(record.getChannel() != null) {
					cityMap.setChannel(record.getChannel());
				}
				if(record.getCode() != null)
					cityMap.setCode(record.getCode());
				if(record.getCodeOther() != null)
					cityMap.setCode_other(record.getCodeOther());
				if(record.getCreateTime() != null)
					cityMap.setCreate_time((new DateTime(record.getCreateTime().getTime())).toString("yyyy-MM-dd HH:mm:ss"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//do nothing
		}
		return cityMap;
	}

	public DictCityMapDao getCityMapDao() {
		return cityMapDao;
	}

	public void setCityMapDao(DictCityMapDao cityMapDao) {
		this.cityMapDao = cityMapDao;
	}
}
