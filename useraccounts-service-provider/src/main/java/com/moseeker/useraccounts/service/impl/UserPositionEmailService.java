package com.moseeker.useraccounts.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.moseeker.baseorm.dao.userdb.UserPositionEmailDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.common.util.query.Query;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;

@Service
public class UserPositionEmailService {
	@Autowired
	private UserPositionEmailDao userPositionEmailDao;
	@Autowired
	private UserUserDao userUserDao;
	private SearchengineServices.Iface searchengineServices = ServiceManager.SERVICEMANAGER.getService(SearchengineServices.Iface.class);
	//处理职位推荐邮件的的插入或者更新
	public int postUserPositionEmail(int userId,String conditions){
		return userPositionEmailDao.insertOrUpdateData(userId, conditions);
	}
	//处理发送职位推荐邮件
	public Response sendEmailRecommendPosition(int userId,String conditions) throws Exception{
		Map<String,Object> data=this.getUserInfobyId(userId);
		String email=(String) data.get("email");
		Map<String,Object> condition=(Map<String,Object>) JSON.parse(conditions);
		Map<String,Object> emailData=getRecommendPosition(condition);
		sendPositionEmail(email,emailData);
		return null;
	}
	//根据userid获取email的信息
	public Map<String,Object> getUserInfobyId(int userId){
		Query query=new Query.QueryBuilder().select("email").where("id",userId).buildQuery();
		Map<String,Object> user=userUserDao.getMap(query);
		return user;
	}
	//根据条件获取职位
	public Map<String,Object> getRecommendPosition(Map<String,Object> map) throws Exception{
		 String keyWord=(String) map.get("keyWord");
		 String citys=(String) map.get("citys");
		 String industry=(String) map.get("industry");
		 int page=1;
		 int pageSize=10;
		 String salaryCode=(String) map.get("salaryCode");
		 Calendar lastDate = Calendar.getInstance();
		 SimpleDateFormat format=new SimpleDateFormat("YYYY-mm-ddTHH:MM:ss");
	     lastDate.roll(Calendar.DATE, -7);//日期回滚7天
	     String startTime=format.format(lastDate.getTime());
	     Response res=searchengineServices.positionQuery(keyWord, citys, industry, salaryCode, page, pageSize, startTime, null);
	     if(res.getStatus()==0&&!Strings.isNullOrEmpty(res.getData())){
	    	 String data=res.getData();
	    	 Map<String,Object> result=JSON.parseObject(data);
	    	 return result;
	     }
		return null;
	}
	//发送邮件
	public void sendPositionEmail(String email,Map<String,Object> data){
		
	}
	//发送邮箱验证
	public void sendEmailvalidation(String email,int userId){
		
	}
	
}
