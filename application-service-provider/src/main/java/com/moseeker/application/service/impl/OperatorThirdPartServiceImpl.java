package com.moseeker.application.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.application.service.OperatorThirdPartService;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.PositionServices;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.thirdpart.service.OrmThirdPartService;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartAccount;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartPosition;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdpartToredis;

@Service
public class OperatorThirdPartServiceImpl implements OperatorThirdPartService {
	OrmThirdPartService.Iface ormThirdPartService = ServiceManager.SERVICEMANAGER.getService(OrmThirdPartService.Iface.class);
	PositionServices.Iface ormPositionService = ServiceManager.SERVICEMANAGER.getService(PositionServices.Iface.class);
	private RedisClient redisClient = RedisClientFactory.getCacheClient();
	private static final String ADDREDIS="MQ_THIRDPART_ADDPOSITION";
	private static final String UPDATEREDIS="MQ_THIRDPART_UPDATEPOSITION";
	@Override
	public Response addPostitonToRedis(ThirdpartToredis positions) {
		try{
			int company_id=positions.getCompany_id();
			Response result=ormThirdPartService.getSingleThirdPartAccount(company_id);
			List<ThirdPartPosition> list=addToRedisAndGetList(positions,result,ADDREDIS);
			if(list!=null&&list.size()>0){
				return ormThirdPartService.addThirdPartPositions(list);
			}else{
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			}
			
		}catch(Exception e){
			e.printStackTrace();

		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
	}

	@Override
	public Response updatePostitonToRedis(ThirdpartToredis positions) {
		// TODO Auto-generated method stub
		try{
			int company_id= positions.getCompany_id();
			Response account=ormThirdPartService.getSingleThirdPartAccount(company_id);
			List<ThirdPartPosition> list=UpdateToRedisAndGetList(positions,account,UPDATEREDIS);
			if(list!=null){
				return ormThirdPartService.updateThirdPartPositions(list);
			}else{
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			}
				
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
	}

	@Override
	public void addPositionToOrm(ThirdPartPosition position) {
		// TODO Auto-generated method stub
		try{
			ormThirdPartService.addThirdPartPosition(position);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void updatePositionOrm(ThirdPartPosition position) {
		// TODO Auto-generated method stub
		try{
			ormThirdPartService.updateThirdPartPosition(position);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void refushPositionOrm(int positionId,int channel){
		
	}

	public List<ThirdPartPosition> addToRedisAndGetList(ThirdpartToredis map,Response result,String key){
		List<ThirdPartPosition> list=new ArrayList<ThirdPartPosition>();
		try{
			String param=map.getParams();
			ThirdPartAccount account=null;
			String username="";
			String password="";
			JSONObject data=new JSONObject();
			if(result.getStatus()==0){
				account=JSONObject.toJavaObject(JSONObject.parseObject(result.getData()),ThirdPartAccount.class);
				username=account.getUsername();
				password=account.getPassword();
				data.put("user_name",username);
				data.put("password",password);
			}else{
				return null;
			}
			if(!StringUtils.isNullOrEmpty(param)){
				JSONArray jsay=JSONArray.parseArray(param);
				ThirdPartPosition position=null;
				if(jsay!=null&&jsay.size()>0){
					for(int i=0;i<jsay.size();i++){
						JSONObject obj=jsay.getJSONObject(i);
						JSONArray jsy=obj.getJSONArray("channel");
						int job_id=obj.getIntValue("position_id");
						Response positionResult=ormPositionService.getPositionById(job_id);
						if(positionResult.getStatus()==0){
							Position getposition=JSONObject.toJavaObject(JSONObject.parseObject(positionResult.getData()), Position.class);
							data.put("job_num", getposition.getJobnumber());
						}else{
							return null;
						
						}
						data.put("job_id",job_id);
						
						SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-DD hh:mm:ss");
						String time=format.format(new Date());
						
						if(jsy!=null&&jsy.size()>0){
							for(int j=0;j<jsy.size();j++){
								JSONObject objs=jsy.getJSONObject(i);
								int channel=obj.getIntValue("type");
								String occupation=objs.getString("occupation");
								data.put("channel", channel);
								if(channel==1){
									data.put("member_name", account.getMembername());
								}
								position=new ThirdPartPosition();
								position.setIs_refresh(0);
								position.setIs_synchronization(2);
								position.setPosition_id(job_id);
								position.setSync_time(time);
								position.setChannel(channel);
								position.setOccupation(occupation);
								list.add(position);
								redisClient.lpush(Constant.APPID_ALPHADOG, key, data.toJSONString());
							}
						}
						
					}
				}				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	public List<ThirdPartPosition> UpdateToRedisAndGetList(ThirdpartToredis map,Response result,String key){
		List<ThirdPartPosition> list=new ArrayList<ThirdPartPosition>();
		try{
			String positions=(String) map.getPositions();
			JSONObject data=new JSONObject();
			ThirdPartAccount account=null;
			if(result.getStatus()==0){
				account=JSONObject.toJavaObject(JSONObject.parseObject(result.getData()),ThirdPartAccount.class);
				String username=account.getUsername();
				String password=account.getPassword();
				data.put("user_name",username);
				data.put("password",password);
			}else{
				return null;
			}
			if(!StringUtils.isNullOrEmpty(positions)){
				JSONArray jsay=JSONArray.parseArray(positions);
				ThirdPartPosition position=null;
				if(jsay!=null&&jsay.size()>0){
					for(int i=0;i<jsay.size();i++){
						JSONObject obj=jsay.getJSONObject(i);
						int job_id=obj.getIntValue("position_id");
						Response positionResult=ormPositionService.getPositionById(job_id);
						if(positionResult.getStatus()==0){
							Position getposition=JSONObject.toJavaObject(JSONObject.parseObject(positionResult.getData()), Position.class);
							data.put("job_number", getposition.getJobnumber());
						}else{
							return null;
						}
						data.put("job_id",job_id);
						String params=obj.getString("types");
						params=params.substring(1,params.length()-1);
						String[] jsy=params.split(",");
						if(jsy!=null&&jsy.length>0){
							for(int j=0;j<jsy.length;j++){
								int channel=Integer.parseInt(jsy[i]);
								data.put("channel", channel);
								if(channel==1){
									data.put("member_name", account.getMembername());
								}
								position=new ThirdPartPosition();
								position.setPosition_id(job_id);
								position.setChannel(channel);
								position.setIs_refresh(2);
								redisClient.lpush(Constant.APPID_ALPHADOG, key, data.toJSONString());
								list.add(position);
							}
						}
					}
				}
						
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
		
	}


	@Override
	public void SynOrUpThirdPartAccount(int type) {
		// TODO Auto-generated method stub
		try{
				List<String> list=null;
				if(type==1){//添加
					list=redisClient.brpop(Constant.APPID_ALPHADOG, ADDREDIS);
				}else{//更新
					list=redisClient.brpop(Constant.APPID_ALPHADOG, UPDATEREDIS);
				}
				if(list!=null&list.size()>0){		
					String msg=list.get(1);
					JSONObject obj=JSONObject.parseObject(msg);
					if(obj.getIntValue("status")==0){
						String third_part_position_id=obj.getString("job_number");
						int channel=obj.getIntValue("channel");
						String sync_time=obj.getString("sync_time");
						int job_id=obj.getIntValue("job_id");
						ThirdPartPosition position=new ThirdPartPosition();
						position.setChannel(channel);
						position.setSync_time(sync_time);
						position.setThird_part_position_id(third_part_position_id);
						if(type==1){
							position.setIs_synchronization(1);
						}else{
							position.setIs_refresh(1);
						}
						position.setPosition_id(job_id);
						ormThirdPartService.updateThirdPartPosition(position);
						
						Response positionResult=ormPositionService.getPositionById(job_id);
						if(positionResult.getStatus()==0){
							Position job=JSONObject.toJavaObject(JSONObject.parseObject(positionResult.getData()), Position.class);
							if(job!=null){
								int companyId=job.getCompany_id();
								ThirdPartAccount account=new ThirdPartAccount();
								account.setChannel(channel);
								account.setCompany_id(companyId);
								account.setSync_time(sync_time);
								account.setRemain_num(companyId);
								ormThirdPartService.updateThirdPartAccount(account);
							}
						}
					}
					
				}
		}catch(Exception e){
			e.printStackTrace();
		}
	}


}
