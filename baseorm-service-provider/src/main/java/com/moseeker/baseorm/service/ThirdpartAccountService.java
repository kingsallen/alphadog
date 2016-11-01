package com.moseeker.baseorm.service;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.ThirdPartAccountDao;
import com.moseeker.baseorm.db.hrdb.tables.records.ThirdPartAccountRecord;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.thirdpart.service.OrmThirdPartService.Iface;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartAccount;

@Service
public class ThirdpartAccountService implements Iface {
	
	@Autowired
	private ThirdPartAccountDao thirdpartAccount;
	
	public List<ThirdPartAccountRecord> getAllCount(CommonQuery query) throws Exception{
		List<ThirdPartAccountRecord> list= thirdpartAccount.getResources(query);
		return list;
	}
	//添加绑定的第三方平台账号
	/*
	 * (non-Javadoc)
	 * @see com.moseeker.thrift.gen.thirdpart.service.OrmThirdPartService.Iface#addThirdPartAccount(com.moseeker.thrift.gen.thirdpart.struct.ThirdPartAccount)
	 */
	@Override
	public Response addThirdPartAccount(ThirdPartAccount account) throws TException {
		// TODO Auto-generated method stub
		ThirdPartAccountRecord record= (ThirdPartAccountRecord) BeanUtils.structToDB(account, ThirdPartAccountRecord.class);
		try{
			int result=thirdpartAccount.postResource(record);
			if(result>0){
				return ResponseUtils.success(String.valueOf(result));
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
	}

}
