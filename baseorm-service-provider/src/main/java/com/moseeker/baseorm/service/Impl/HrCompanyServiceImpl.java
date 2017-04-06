package com.moseeker.baseorm.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.hrdb.HRCompanyConfDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyConfRecord;
import com.moseeker.baseorm.service.HrCompanyService;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.HRCompanyConfData;
/**
 * 
 * @author zztaiwll
 *  time:2016 12 7
 *  function:操作hrcompanyConf表
 */
@Service
public class HrCompanyServiceImpl implements HrCompanyService {
	
	Logger logger = LoggerFactory.getLogger(HrCompanyServiceImpl.class);
	
	@Autowired
	private HRCompanyConfDao hrCompantDao;
	@Override
	public Response getCompanyConf(CommonQuery query) {
		// TODO Auto-generated method stub
		try{
			HrCompanyConfRecord result=hrCompantDao.getResource(query);
			if(result!=null){
				return ResponseUtils.success(BeanUtils.DBToStruct(HRCompanyConfData.class,result));
			}else{
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}

}
