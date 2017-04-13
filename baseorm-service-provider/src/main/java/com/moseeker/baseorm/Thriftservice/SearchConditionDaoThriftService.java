package com.moseeker.baseorm.Thriftservice;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.hrdb.HrSearchConditionDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrSearchConditionRecord;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.service.SearcheConditionDao.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.SearchCondition;

@Service
public class SearchConditionDaoThriftService implements Iface {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private HrSearchConditionDao dao;

	@Override
	public List<SearchCondition> getResources(CommonQuery query)
			throws TException {
		List<SearchCondition> list = new ArrayList<SearchCondition>();
		try {
			dao.getResources(query).forEach(record -> {
				list.add((SearchCondition) BeanUtils.DBToStruct(SearchCondition.class, record));
			});
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	@Override
	public SearchCondition getResource(CommonQuery query) throws TException {
		try {
			return (SearchCondition) BeanUtils.DBToStruct(SearchCondition.class, dao.getResource(query));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TException(MessageFormat.format("根据commonQuery={0}查询结果集出错", query.toString()));
		}
	}

	@Override
	public int getResourceCount(CommonQuery query) throws TException {
		try {
			return dao.getResourceCount(query);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return 0;
	}

	@Override
	public int postResource(SearchCondition searchCondition) throws TException {
		try {
			return dao.postResource((HrSearchConditionRecord) BeanUtils.structToDB(searchCondition, HrSearchConditionRecord.class));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return 0;
	}

	@Override
	public int delResource(int hrAccountId, int id) throws TException {
		return dao.delResource(hrAccountId, id);
	}

}
