package com.moseeker.function.service.chaos;

import java.net.ConnectException;
import java.util.List;

import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronizationWithAccount;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.PositionRefreshType;
import com.moseeker.common.exception.CacheConfigNotExistException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.UrlUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.PositionDao;
import com.moseeker.thrift.gen.dao.struct.ThirdPartyPositionData;
import com.moseeker.thrift.gen.foundation.chaos.struct.ThirdPartyAccountStruct;

/**
 * 
 * 第三方渠道（比如51，智联）服务 
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 6, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
@Service
public class ChaosServiceImpl {
	
	Logger logger = LoggerFactory.getLogger(ChaosServiceImpl.class);
	
	
	PositionDao.Iface positionDao = ServiceManager.SERVICEMANAGER.getService(PositionDao.Iface.class);

	public Response bind(String username, String password, String memberName, byte channel) {
		logger.info("ChaosServiceImpl bind");
		try {
			String domain = "";
			try {
				ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
				configUtils.loadResource("chaos.properties");
				domain = configUtils.get("chaos.domain", String.class);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
			ChannelType chnnelType = ChannelType.instaceFromInteger(channel);
			String bindURI = chnnelType.getBindURI(domain);
			logger.info("ChaosServiceImpl bind bindURI:"+bindURI);
			String params = ChaosTool.getParams(username, password, memberName, chnnelType);
			logger.info("ChaosServiceImpl bind params:"+params);
			String data = UrlUtil.sendPost(bindURI, params, Constant.CONNECTION_TIME_OUT, Constant.READ_TIME_OUT);
			/**
			 * TODO remainProfileNum
			 */
			logger.info("ChaosServiceImpl bind data:"+data);
			//String data = "{\"status\":0,\"message\":\"success\", \"data\":{\"remain_number\":1,\"resume_number\":2}}";
			Response response = ChaosTool.createResponse(data);
			return response;
		//} catch (Exception e) {
		} catch (ConnectException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
				return ResponseUtils.fail(ConstantErrorCodeMessage.HRACCOUNT_BINDING_TIMEOUT);
		} finally {
			//do nothing
		}
	}

	/**
	 * 同步第三方帐号
	 * @param thirdPartyAccount
	 * @return
	 */
	public ThirdPartyAccountStruct synchronization(ThirdPartyAccountStruct thirdPartyAccount) {
		
		if(thirdPartyAccount != null) {
			String domain = "";
			try {
				ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
				configUtils.loadResource("chaos.properties");
				domain = configUtils.get("chaos.domain", String.class);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
			ChannelType chnnelType = ChannelType.instaceFromInteger(thirdPartyAccount.getChannel());
			String synchronizationURI = chnnelType.getRemain(domain);
			String params = ChaosTool.getParams(thirdPartyAccount.getUsername(), thirdPartyAccount.getPassword(), thirdPartyAccount.getMemberName(), chnnelType);
			try {
				logger.info("ChaosServiceImpl refresh refreshURI:"+synchronizationURI);
				String data = UrlUtil.sendPost(synchronizationURI, params, Constant.CONNECTION_TIME_OUT, Constant.READ_TIME_OUT);
				logger.info("ChaosServiceImpl refresh params:"+params);
				//String data = "{\"status\":0,\"message\":\"success\", \"data\":{\"remain_number\":1,\"resume_number\":2}}";
				if(data != null) {
					JSONObject result = JSON.parseObject(data);
					if(result.getInteger("status") != null && result.getInteger("status") == 0) {
						JSONObject successData = JSON.parseObject(result.getString("data"));
						thirdPartyAccount.setRemainNum(successData.getIntValue("remain_number"));
						thirdPartyAccount.setRemainProfileNum(successData.getIntValue("resume_number"));
					} else {
						thirdPartyAccount.setStatus(result.getInteger("status"));
					}
				}
			//} catch (Exception e) {
			} catch (ConnectException e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				thirdPartyAccount.setStatus(-1);
			} finally {
				//do nothing
			}
			//String data = "{\"status\":0,\"message\":\"success\", \"data\":3}";
		}
		return thirdPartyAccount;
	}

	/**
	 * 同步职位到第三方
	 * @param positions
	 * @return
	 */
	public Response synchronizePosition(List<ThirdPartyPositionForSynchronizationWithAccount> positions) {
		
		try {
			if(positions != null && positions.size() > 0) {
				String email = "";
				try {
					ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
					configUtils.loadResource("chaos.properties");
					email = configUtils.get("chaos.email", String.class);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				} finally {
					//do nothing
				}
				DateTime dt = new DateTime();
				int second = dt.getSecondOfDay();
				for(ThirdPartyPositionForSynchronizationWithAccount position : positions) {
					position.getPosition_info().setEmail("cv_"+position.getPosition_id()+email);
					String positionJson = JSON.toJSONString(position);
					logger.info("synchronize position:"+positionJson);
					
					RedisClient redisClient = RedisClientFactory.getCacheClient();
					redisClient.lpush(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_SYNCHRONIZATION_QUEUE.toString(), positionJson);
					if(second < 60*60*24) {
						redisClient.set(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_REFRESH.toString(), String.valueOf(position.getPosition_id()), position.getAccount_id(), "1", 60*60*24-second);
					}
				}
				return ResponseUtils.success(null);
			} else {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
			}
		} catch (CacheConfigNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXHAUSTED);
		} finally {
			//do nothing
		}
	}

	public Response refreshPosition(ThirdPartyPositionForSynchronizationWithAccount position) {
		ThirdPartyPositionData p = new ThirdPartyPositionData();
		try {
			String positionJson = JSON.toJSONString(position);
			RedisClient redisClient = RedisClientFactory.getCacheClient();
			redisClient.lpush(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_REFRESH_QUEUE.toString(), positionJson);
			p.setChannel(Byte.valueOf(position.getChannel()));
			p.setPosition_id(Integer.valueOf(position.getPosition_id()));
			p.setIs_refresh((byte)PositionRefreshType.refreshing.getValue());
			p.setRefresh_time((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
			p.setAccount_id(position.getAccount_id());
			positionDao.upsertThirdPartyPositions(p);
			
			DateTime dt = new DateTime();
			int second = dt.getSecondOfDay();
			if(second < 60*60*24) {
				redisClient.set(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_REFRESH.toString(), String.valueOf(position.getPosition_id()), String.valueOf(position.getAccount_id()), "1", 60*60*24-second);
			}
		} catch (TException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXHAUSTED);
		} finally {
			//do nothing
		}
		
		return ResponseUtils.success(p);
	}
}
