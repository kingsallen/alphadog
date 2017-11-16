package com.moseeker.dict.service.impl;

import com.moseeker.baseorm.dao.dictdb.DictLiepinOccupationDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.dict.service.base.AbstractOccupationHandler;
import com.moseeker.dict.service.impl.occupation.Job51OccupationHandler;
import com.moseeker.dict.service.impl.occupation.LiepinOccupationHandler;
import com.moseeker.dict.service.impl.occupation.VeryEastOccupationHandler;
import com.moseeker.dict.service.impl.occupation.ZhilianOccupationHandler;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.Dict51OccupationDao;
import com.moseeker.baseorm.dao.dictdb.DictZpinOccupationDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.dict.enums.ConstantEnum;
import com.moseeker.thrift.gen.common.struct.Response;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DictOccupationService {
	/**
	 *   查询dict的职位字典
	 * @param single_layer 是否只返回一层
	 * @param channel 渠道类型
	 * @return
	 * @Auth zzt
	 * @time 2016－11－17
	 */
	Logger logger = org.slf4j.LoggerFactory.getLogger(DictOccupationService.class);

	@Autowired
    private Dict51OccupationDao dict51OccupationDao;

	@Autowired
    private DictZpinOccupationDao dictZpinOccupationDao;

	@Autowired
	private DictLiepinOccupationDao dictLiepinOccupationDao;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

	public static Map<Class,AbstractOccupationHandler> map;

	@Autowired
	public DictOccupationService(List<AbstractOccupationHandler> list){
		map=list.stream().collect(Collectors.toMap(h->h.getClass(), h->h));
	}

	/*
	 * 查询第三方职位职能
	 */
	@CounterIface
	public Response queryOccupation(String param){
		logger.info("queryOccupation param :"+param);
		JSONObject obj=JSONObject.parseObject(param);
		int single_layer=obj.getIntValue("single_layer");
		int channel=obj.getIntValue("channel");
		try{

			OccupationChannel occupationChannel=OccupationChannel.getInstance(channel);
			AbstractOccupationHandler occupationHandler=map.get(occupationChannel.clazz);

			if(single_layer==1){
				return ResponseUtils.success(occupationHandler.getSingle(obj));
			}else{
				String result=redisClient.get(Constant.APPID_ALPHADOG,ConstantEnum.JOB_OCCUPATION_KEY.toString(),occupationChannel.key);
				logger.info("redis occupation :"+result);
				if(!StringUtils.isEmpty(result)){
					Response res=JSONObject.toJavaObject(JSONObject.parseObject(result), Response.class);
					return res;
				}else{
					Response res=ResponseUtils.success(occupationHandler.getAll());
					logger.info("occupation handle result : {}",res);
					if(res.getStatus()==0&&!StringUtils.isEmpty(res.getData())&&!"[]".equals(res.getData())){
						redisClient.set(Constant.APPID_ALPHADOG,ConstantEnum.JOB_OCCUPATION_KEY.toString(),occupationChannel.key,JSONObject.toJSONString(res));
					}
					return res;
				}

			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
	}

	private enum OccupationChannel{
		Job51(ChannelType.JOB51.getValue(),"51JobList", Job51OccupationHandler.class),
		ZhiLian(ChannelType.ZHILIAN.getValue(),"zPinList",ZhilianOccupationHandler.class),
		LiePin(ChannelType.LIEPIN.getValue(),"liePinList",LiepinOccupationHandler.class),
		VeryEast(ChannelType.VERYEAST.getValue(),"veryEastList",VeryEastOccupationHandler.class);

		private OccupationChannel(int code,String key,Class<? extends AbstractOccupationHandler> clazz){
			this.code=code;
			this.key=key;
			this.clazz=clazz;
		}

		private int code;
		private String key;
		private Class clazz;

		public String key(){
			return key;
		}

		public static OccupationChannel getInstance(int code){
			for(OccupationChannel oc:values()){
				if(oc.code==code){
					return oc;
				}
			}
			return null;
		}
	}
}
