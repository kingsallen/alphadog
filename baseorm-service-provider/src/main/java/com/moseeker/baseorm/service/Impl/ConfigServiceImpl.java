package com.moseeker.baseorm.service.Impl;

import com.moseeker.baseorm.dao.configdb.ConfigSysPointsConfTplDao;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysPointsConfTplRecord;
import com.moseeker.baseorm.service.ConfigService;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.config.ConfigSysPointsConfTpl;
import com.moseeker.thrift.gen.config.HrAwardConfigTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {
	
	@Autowired
	private ConfigSysPointsConfTplDao configSysPointsConfTplDao;
	
	@Override
	public Response getConfigSysPointsConfTpl(Query query) {
		// TODO Auto-generated method stub
		try{
			ConfigSysPointsConfTpl result=
					(ConfigSysPointsConfTpl) BeanUtils.DBToStruct(ConfigSysPointsConfTpl.class,configSysPointsConfTplDao.getRecord(query) );
			return ResponseUtils.success(result);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
		
	}
	public Response getConfigSysPointsConfTpls(Query query) {
		// TODO Auto-generated method stub
		
		try{
			List<ConfigSysPointsConfTplRecord> result=configSysPointsConfTplDao.getRecords(query);
			List<ConfigSysPointsConfTpl> list=this.convertStruct(result);
			
			return ResponseUtils.success(list);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
	private List<ConfigSysPointsConfTpl> convertStruct(List<ConfigSysPointsConfTplRecord> result){
		List<ConfigSysPointsConfTpl> list=new ArrayList<ConfigSysPointsConfTpl>();
		if(result!=null&&result.size()>0){
			for(ConfigSysPointsConfTplRecord record:result){
				list.add((ConfigSysPointsConfTpl) BeanUtils.DBToStruct(ConfigSysPointsConfTpl.class, record));
			}
		}
		return list;
		
		
	}
	@Override
	public Response getRecruitProcess(int CompanyId) {
		// TODO Auto-generated method stub
		try{
		List<HrAwardConfigTemplate>	list=configSysPointsConfTplDao.findRecruitProcesses(CompanyId);
		return ResponseUtils.success(list);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
}
