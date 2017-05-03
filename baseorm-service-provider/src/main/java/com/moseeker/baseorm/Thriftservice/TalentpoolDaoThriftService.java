package com.moseeker.baseorm.Thriftservice;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.moseeker.baseorm.tool.QueryConvert;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moseeker.baseorm.dao.hrdb.HrTalentpoolDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTalentpoolRecord;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.service.TalentpoolDao.Iface;
import com.moseeker.thrift.gen.dao.struct.Talentpool;

@Service
public class TalentpoolDaoThriftService implements Iface {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private HrTalentpoolDao dao;

	@Override
	public List<Talentpool> getResources(CommonQuery query) throws TException {
		List<Talentpool> list = new ArrayList<Talentpool>();
		try {
			dao.getResources(QueryConvert.commonQueryConvertToQuery(query)).forEach(record -> {
				list.add((Talentpool)BeanUtils.DBToStruct(Talentpool.class, record));
			});
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TException(MessageFormat.format("根据commonQuery={}查询结果集出错", query.toString()));
		}
		return list;
	}

	@Override
	public Talentpool getResource(CommonQuery query) throws TException {
		try {
			return (Talentpool)BeanUtils.DBToStruct(Talentpool.class, dao.getResource(QueryConvert.commonQueryConvertToQuery(query)));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TException(MessageFormat.format("根据commonQuery={}查询结果集出错", query.toString()));
		}
	}

	@Override
	public int postResource(Talentpool talentpool) throws TException {
		try {
			return dao.postResource((HrTalentpoolRecord)BeanUtils.structToDB(talentpool, HrTalentpoolRecord.class));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return 0;
	}

	@Override
	public int putResource(Talentpool talentpool) throws TException {
		try {
			return dao.putResource((HrTalentpoolRecord)BeanUtils.structToDB(talentpool, HrTalentpoolRecord.class));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return 0;
	}

}
