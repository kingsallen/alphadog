package com.moseeker.function.service.choas;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.function.server.FunctionServer;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.dao.service.CompanyDao;
import com.moseeker.thrift.gen.dao.service.PositionDao;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;
import com.moseeker.thrift.gen.dao.struct.ThirdPartyPositionData;
import com.moseeker.thrift.gen.position.struct.Position;

/**
 * 监听同步完成队列
 * @author wjf
 *
 */
public class PositionSyncListener {
	
	private static Logger logger = LoggerFactory.getLogger(PositionSyncListener.class);
	
	CompanyDao.Iface companyDao = ServiceManager.SERVICEMANAGER
			.getService(CompanyDao.Iface.class);
	PositionDao.Iface positionDao = ServiceManager.SERVICEMANAGER
			.getService(PositionDao.Iface.class);
	
	public void startTask() {
		new Thread(()-> {
			task();
		}).start();
	}
	
	private void task() {
		try {
			String sync = fetchCompledPosition();
			logger.info("completed queue :"+sync);
			PositionForSyncResultPojo pojo = JSONObject.parseObject(sync, PositionForSyncResultPojo.class);
			
			writeBack(pojo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		task();
	}

	/**
	 * 监听职位同步完成队列
	 * @return
	 */
	private String fetchCompledPosition() {
		RedisClient redisClient = RedisClientFactory.getCacheClient();
		return redisClient.brpop(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_SYNCHRONIZATION_COMPLATED_QUEUE.toString()).get(1);
	}
	/**
	 * 回写数据
	 * @param pojo
	 */
	private void writeBack(PositionForSyncResultPojo pojo) {
		List<ThirdPartyPositionData> datas = new ArrayList<>();
		ThirdPartyPositionData data = new ThirdPartyPositionData();
		data.setChannel(Byte.valueOf(pojo.getChannel()));
		data.setPosition_id(Integer.valueOf(pojo.getPosition_id()));
		if(pojo.getStatus() == 0) {
			data.setIs_synchronization((byte)PositionSync.bound.getValue());
		} else {
			data.setIs_synchronization((byte)PositionSync.failed.getValue());
			if(pojo.getStatus() == 2) {
				data.setSync_fail_reason(Constant.POSITION_SYNCHRONIZATION_FAILED);
			} else {
				data.setSync_fail_reason(pojo.getMessage());
			}
		}
		datas.add(data);
		
		try {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("id", pojo.getPosition_id());
			logger.info("completed queue search position:"+pojo.getPosition_id());
			Position p = positionDao.getPosition(qu);
			if(p != null && p.getId() > 0) {
				logger.info("completed queue position existî");
				logger.info("completed queue update thirdpartyposition to synchronized");
				companyDao.upsertThirdPartyPositions(datas);
				if(pojo.getStatus() == 0) {
					ThirdPartAccountData d = new ThirdPartAccountData();
					d.setCompany_id(p.getCompany_id());
					d.setRemain_num(pojo.getRemain_number());
					d.setChannel(Integer.valueOf(pojo.getChannel().trim()));
					d.setSync_time(pojo.getSync_time());
					//positionDao.updatePosition(p);
					logger.info("completed queue update thirdpartyposition to synchronized");
					companyDao.updatePartyAccountByCompanyIdChannel(d);
				}
			}
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			//do nothing
		}
	}
}
