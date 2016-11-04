package com.moseeker.baseorm.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moseeker.baseorm.dao.ThirdPartAccountDao;
import com.moseeker.baseorm.dao.ThirdPartPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartAccountRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartPositionRecord;
import com.moseeker.baseorm.service.ThirdpartAccountService;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartAccount;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartPosition;

@Service
public class ThirdpartAccountServiceImpl implements ThirdpartAccountService {
	
	@Autowired
	private ThirdPartAccountDao thirdpartAccount;
	@Autowired
	private ThirdPartPositionDao thirdPartPosition;
	
	//添加绑定的第三方平台账号
	/*
	 * (non-Javadoc)
	 * @see com.moseeker.thrift.gen.thirdpart.service.OrmThirdPartService.Iface#addThirdPartAccount(com.moseeker.thrift.gen.thirdpart.struct.ThirdPartAccount)
	 */
	@Override
	public Response add_ThirdPartAccount(ThirdPartAccount account){
		// TODO Auto-generated method stub
		HrThirdPartAccountRecord record= (HrThirdPartAccountRecord) BeanUtils.structToDB(account, HrThirdPartAccountRecord.class);
		try{
			CommonQuery query=new CommonQuery();
			HashMap map=new HashMap();
			map.put("company_id", account.getCompany_id());
			map.put("channel", account.getChannel());
			query.setEqualFilter(map);
			int flag=thirdpartAccount.getResourceCount(query);
			int result=0;
			if(flag>0){
				result=thirdpartAccount.putResource(record);
			}else{
				result=thirdpartAccount.postResource(record);
			}
			if(result>0){
				return ResponseUtils.success(String.valueOf(result));
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
	}
	/*
	 * (non-Javadoc)
	 * @see com.moseeker.thrift.gen.thirdpart.service.OrmThirdPartService.Iface#updateThirdPartAccount(com.moseeker.thrift.gen.thirdpart.struct.ThirdPartAccount)
	 * 更新第三方平台账户信息
	 */
	@Override
	public Response update_ThirdPartAccount(ThirdPartAccount account){
		// TODO Auto-generated method stub
		try{
				HrThirdPartAccountRecord record=getForUpdateAccount(account);
				if(record!=null){
					int result=thirdpartAccount.putResource(record);
					return ResponseUtils.success(String.valueOf(result));
				}else{
					return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
				}
				
		}catch(Exception e){
			e.printStackTrace();
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
	}
	/*
	 * (non-Javadoc)
	 * @see com.moseeker.thrift.gen.thirdpart.service.OrmThirdPartService.Iface#addThirdPartPosition(com.moseeker.thrift.gen.thirdpart.struct.ThirdPartPosition)
	 * 添加一条职位信息
	 */
	@Override
	public Response add_ThirdPartPosition(ThirdPartPosition position){
		HrThirdPartPositionRecord record=(HrThirdPartPositionRecord) BeanUtils.structToDB(position, HrThirdPartPositionRecord.class);
		try{
			CommonQuery query=new CommonQuery();
			HashMap map=new HashMap();
			map.put("position_id", position.getPosition_id());
			map.put("third_part_position_id", position.getThird_part_position_id());
			map.put("channel", position.getChannel());
			query.setEqualFilter(map);
			int  flag=thirdPartPosition.getResourceCount(query);
			if(flag>0){
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			}else{
				int result=thirdPartPosition.postResource(record);
				return ResponseUtils.success(String.valueOf(result));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	    return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
	}
	/*
	 * (non-Javadoc)
	 * @see com.moseeker.thrift.gen.thirdpart.service.OrmThirdPartService.Iface#updateThirdPartPosition(com.moseeker.thrift.gen.thirdpart.struct.ThirdPartPosition)
	 * 更新一条职位信息
	 */
	@Override
	public Response update_ThirdPartPosition(ThirdPartPosition position){
		// TODO Auto-generated method stub
		
		try{
			HrThirdPartPositionRecord record=getForUpdatePosition(position);
			if(record!=null){				
				int result=thirdPartPosition.putResource(record);
				return ResponseUtils.success(String.valueOf(result));
			}else{
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
	}
	/*
	 * (non-Javadoc)
	 * @see com.moseeker.baseorm.service.ThirdpartAccountService#add_ThirdPartPositions(java.util.List)
	 * 批量添加同步职位的信息
	 */
	@Override
	public Response add_ThirdPartPositions(List<ThirdPartPosition> positions) {
		// TODO Auto-generated method stub
		try{
			if(positions!=null&&positions.size()>0){
				List<HrThirdPartPositionRecord> list=new ArrayList<HrThirdPartPositionRecord>();
				for(ThirdPartPosition position:positions){
					HrThirdPartPositionRecord record=(HrThirdPartPositionRecord) BeanUtils.structToDB(position, HrThirdPartPositionRecord.class);
					list.add(record);
				}
				int result=thirdPartPosition.postResources(list);
				return ResponseUtils.success(String.valueOf(result));
			}else{
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
	}
/*
 * (non-Javadoc)
 * @see com.moseeker.baseorm.service.ThirdpartAccountService#update_ThirdPartPositions(java.util.List)
 * 批量更新同步职位信息
 */
	@Override
	public Response update_ThirdPartPositions(List<ThirdPartPosition> positions) {
		// TODO Auto-generated method stub
		try{
			if(positions!=null&&positions.size()>0){
				List<HrThirdPartPositionRecord> list=new ArrayList<HrThirdPartPositionRecord>();
				for(ThirdPartPosition position:positions){
					HrThirdPartPositionRecord record=getForUpdatePosition(position);
					if(record!=null){
						list.add(record);
					}					
				}
				int result=thirdPartPosition.putResources(list);
				return ResponseUtils.success(String.valueOf(result));
			}else{
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
	}
	/*
	 * (non-Javadoc)
	 * @see com.moseeker.baseorm.service.ThirdpartAccountService#getSingleAccountByCompanyId(int)
	 * 通过companyId获取一条account信息
	 */
	@Override
	public Response getSingleAccountByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		CommonQuery query=new CommonQuery();
		HashMap map=new HashMap();
		map.put("company_id",companyId );
		map.put("limit", 1);
		query.setEqualFilter(map);
		try{
			HrThirdPartAccountRecord record= thirdpartAccount.getResource(query);
			ThirdPartAccount account=(ThirdPartAccount) BeanUtils.DBToStruct(ThirdPartAccount.class, record);
			if(record!=null){
				return ResponseUtils.success(account);
			}else{
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			}
		}catch(Exception e){
			e.printStackTrace();
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
		}
		
	}
	
	public HrThirdPartAccountRecord getForUpdateAccount(ThirdPartAccount account){
		CommonQuery query=new CommonQuery();
		HashMap map=new HashMap();
		map.put("company_id", account.getCompany_id());
		map.put("channel", account.getChannel());
		query.setEqualFilter(map);
		try {
			HrThirdPartAccountRecord flag = thirdpartAccount.getResource(query);
			if(flag!=null){
				account.setId(flag.getId());
				HrThirdPartAccountRecord record= (HrThirdPartAccountRecord) BeanUtils.structToDB(account, HrThirdPartAccountRecord.class);
				return record;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public HrThirdPartPositionRecord getForUpdatePosition(ThirdPartPosition position){
		CommonQuery query=new CommonQuery();
		HashMap map=new HashMap();
		map.put("position_id", position.getPosition_id());
		map.put("channel", position.getChannel());
		query.setEqualFilter(map);
		try{
			HrThirdPartPositionRecord  flag=thirdPartPosition.getResource(query);
			if(flag!=null){
				position.setId(Integer.parseInt(flag.getId()+""));
				HrThirdPartPositionRecord record=(HrThirdPartPositionRecord) BeanUtils.structToDB(position, HrThirdPartPositionRecord.class);
				return record;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
