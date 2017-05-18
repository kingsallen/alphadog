package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.PositionRefreshType;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;
import com.moseeker.thrift.gen.dao.struct.ThirdPartyPositionData;
import com.moseeker.thrift.gen.position.struct.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 监听刷新完成队列
 * @author wjf
 *
 */
@Component
public class PositionRefreshConsumer {
	
	private static Logger logger = LoggerFactory.getLogger(PositionRefreshConsumer.class);

	@Autowired
    private JobPositionDao positionDao;

    @Autowired
    HRThirdPartyPositionDao thirdpartyPositionDao;

    @Autowired
    private HRThirdPartyAccountDao thirdPartyAccountDao;

    @PostConstruct
	public void startTask() {
		new Thread(()-> {
			while(true) {
				task();
			}
		}).start();
	}
	
	private void task() {
		try {
			String sync = fetchCompledPosition();
			if(StringUtils.isNotNullOrEmpty(sync)) {
				
				logger.info(" refresh completed queue :"+sync);
				
				PositionForSyncResultPojo pojo = JSONObject.parseObject(sync, PositionForSyncResultPojo.class);
				
				writeBack(pojo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
	}

	/**
	 * 监听职位同步完成队列
	 * @return
	 */
	private String fetchCompledPosition() {
		RedisClient redisClient = RedisClientFactory.getCacheClient();
		List<String> result = redisClient.brpop(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_REFRESH_COMPLETED_QUEUE.toString());
		if(result != null && result.size() > 0) {
			return result.get(1);
		} else {
			return null;
		}
	}
	/**
	 * 回写数据
	 * @param pojo
	 */
	@CounterIface
	private void writeBack(PositionForSyncResultPojo pojo) {
		ThirdPartyPositionData data = new ThirdPartyPositionData();
		data.setChannel(Byte.valueOf(pojo.getChannel()));
		data.setPosition_id(Integer.valueOf(pojo.getPosition_id()));
		if(pojo.getStatus() == 0) {
			data.setIs_refresh((byte)PositionRefreshType.refreshed.getValue());
			data.setRefresh_time(pojo.getSync_time());
		} else {
			data.setIs_refresh((byte)PositionRefreshType.failed.getValue());
			if(pojo.getStatus() == 2) {
				data.setSync_fail_reason(Constant.POSITION_SYNCHRONIZATION_FAILED);
			} else {
				data.setSync_fail_reason(pojo.getMessage());
			}
		}
		try {
            Query.QueryBuilder qu = new Query.QueryBuilder();
            qu.where("id", pojo.getPosition_id());
			logger.info("refresh completed queue search position:"+pojo.getPosition_id());
			Position p = positionDao.getData(qu.buildQuery(), Position.class);
			if(p != null && p.getId() > 0) {
				logger.info("refresh completed queue position existî");
				logger.info("refresh completed queue update thirdpartyposition to synchronized");
                thirdpartyPositionDao.upsertThirdPartyPosition(data);
				if(pojo.getStatus() == 0) {
					ThirdPartAccountData d = new ThirdPartAccountData();
					d.setCompany_id(p.getCompany_id());
					d.setRemain_num(pojo.getRemain_number());
					d.setChannel(Integer.valueOf(pojo.getChannel().trim()));
					d.setSync_time(pojo.getSync_time());
					//positionDao.updatePosition(p);
					logger.info("refresh completed queue update thirdpartyposition to synchronized");
                    thirdPartyAccountDao.updatePartyAccountByCompanyIdChannel(d);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
	}
}
