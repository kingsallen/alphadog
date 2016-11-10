package test.com.moseeker.application;

import java.util.List;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.application.service.impl.OperatorThirdPartServiceImpl;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdpartToredis;

public class ThirdPartToRedisTest {
	private RedisClient redisClient = RedisClientFactory.getCacheClient();
	private static final String ADDREDIS="MQ_THIRDPART_ADDPOSITION";
	private static final String UPDATEREDIS="MQ_THIRDPART_UPDATEPOSITION";
	
	public void addToRedis(){
		JSONArray jsay=new JSONArray();
		JSONObject objs=new JSONObject();
		objs.put("position_id", 1);
		JSONArray jsy=new JSONArray();
		JSONObject bj=new JSONObject();
		bj.put("type", 1);
		bj.put("occupation", "1111111");
		jsy.add(bj);
		bj.put("type", 2);
		bj.put("occupation", "22222");
		jsy.add(bj);
		objs.put("channel", jsy);
		jsay.add(objs);
		objs.put("position_id", 2);
		bj.put("type", 1);
		bj.put("occupation", "1111111");
		bj.put("type", 1);
		bj.put("occupation", "1111111");
		jsy.add(bj);
		bj.put("type", 2);
		bj.put("occupation", "22222");
		jsy.add(bj);
		objs.put("channel", jsy);
		jsay.add(objs);
		String params=jsay.toJSONString();
		ThirdpartToredis redis=new ThirdpartToredis();
		redis.setParams(params);
		redis.setUser_id(1);
		redis.setAppid(1);
		redis.setCompany_id(2);
		AnnotationConfigApplicationContext context = initSpring();
		OperatorThirdPartServiceImpl service= context.getBean(OperatorThirdPartServiceImpl.class);
		System.out.println(service.addPostitonToRedis(redis));		
		
	}
	
	public void getaddList(){
		List<String> list=redisClient.brpop(Constant.APPID_ALPHADOG, ADDREDIS);
		if(list.size()>0){
			for(String msg:list){
				System.out.println("=============================");
				System.out.println(msg);
			}
		}else{
			System.out.println("no list================");
		}
		while(true){
			getaddList();
		}

	}
	
	public void getUpdateList(){
		List<String> list=redisClient.brpop(Constant.APPID_ALPHADOG, UPDATEREDIS);
		if(list.size()>0){
			for(String msg:list){
				System.out.println("=============================");
				System.out.println(msg);
			}
		}else{
			System.out.println("no list================");
		}

	}
	public static AnnotationConfigApplicationContext initSpring() {
		AnnotationConfigApplicationContext annConfig = new AnnotationConfigApplicationContext();
		annConfig.scan("com.moseeker.application");
		annConfig.refresh();
		return annConfig;
	}

}
