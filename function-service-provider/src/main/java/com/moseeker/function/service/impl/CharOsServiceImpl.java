package com.moseeker.function.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.function.service.CharOsService;
import com.moseeker.function.util.HttpUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.thirdpart.service.OrmThirdPartService;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartAccount;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartParamer;
/*
 * 本方法通过调用chaos端口，判断是否绑定，如果绑定则记录到数据库，如果没有绑定那么返回具体错误
 * 
 */
@Service
public class CharOsServiceImpl implements CharOsService{
	OrmThirdPartService.Iface ormservice = ServiceManager.SERVICEMANAGER.getService(OrmThirdPartService.Iface.class);
	@Override
	public Response getInformationFromChaos(ThirdPartParamer param) {
		String url="/position/binding";
		JSONObject obj=HttpUtil.getHttpFormChaos(param,url);
//		JSONObject obj=new JSONObject();
//		obj.put("status", 0);
//		obj.put("message", "申请成功");
//		obj.put("data", 10);
		String status=obj.getString("status");
		if("0".equals(status)){
			ThirdPartAccount account=new ThirdPartAccount();
			account.setBinding(1);
			account.setUsername(param.getUsername());
			account.setChannel(param.getChannel());
			account.setCompany_id(param.getCompany_id());
			account.setMembername(param.getMember_name());
			String name="51job";
			if(param.getChannel()==2){
				name="猎聘";
			}else if(param.getChannel()==3){
				name="智联";
			}else if(param.getChannel()==4){
				name="linkedin";
			}
			account.setName(name);
			account.setPassword(param.getPassword());
			account.setRemain_num(obj.getIntValue("data"));
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date=new Date();
			String time=dateformat.format(date);
			account.setSync_time(time);
			try{
				return ormservice.addThirdPartAccount(account);
			}catch(Exception e){
				e.printStackTrace();
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
				
			}
			
		}
		
		return ResponseUtils.fail(obj.toJSONString());
	}
	public Response getRemind(ThirdPartParamer param){
		String url="";
		try{
			JSONObject obj=HttpUtil.getHttpFormChaos(param,url);
			String status=obj.getString("status");
			if("0".equals(status)){
				ThirdPartAccount account=new ThirdPartAccount();
				account.setUsername(param.getUsername());
				account.setChannel(param.getChannel());
				account.setCompany_id(param.getCompany_id());
				account.setMembername(param.getMember_name());
				int remain_num=obj.getIntValue("data");
				account.setRemain_num(remain_num);
				return ormservice.updateThirdPartAccount(account);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	  return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
	}
}
