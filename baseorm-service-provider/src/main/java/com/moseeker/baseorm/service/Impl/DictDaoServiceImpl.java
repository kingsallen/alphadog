package com.moseeker.baseorm.service.Impl;

import com.moseeker.baseorm.dao.dictdb.Dict51OccupationDao;
import com.moseeker.baseorm.dao.dictdb.DictZpinOccupationDao;
import com.moseeker.baseorm.service.DictDaoService;
import com.moseeker.baseorm.tool.OrmTools;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class DictDaoServiceImpl implements DictDaoService{
	/**
	 * auth:zzt
	 * time:2016-11-18
	 * use:  查询51活智联所有的职位职能
	 */
	@Autowired
	private Dict51OccupationDao dict51Dao;
	@Autowired
	private  DictZpinOccupationDao dictZpinDao;
	@Override
	public Response occupations51() {
		// TODO Auto-generated method stub
		return OrmTools.getAll(dict51Dao);
	}
	@Override
	public Response occupationsZPin() {
		// TODO Auto-generated method stub
		return OrmTools.getAll(dictZpinDao);
	}
	public Response occupation51(Query query){
		return OrmTools.getSingle(dict51Dao,query);
	}
	public Response occupationZPin(Query query){
		return OrmTools.getSingle(dictZpinDao,query);
	}
}
