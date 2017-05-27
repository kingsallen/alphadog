package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;
import com.moseeker.thrift.gen.dao.struct.ThirdPartyPositionData;
import com.moseeker.thrift.gen.position.struct.Position;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监听同步完成队列
 * @author wjf
 *
 */
@Component
public class PositionSyncConsumer {
	
	private static Logger logger = LoggerFactory.getLogger(PositionSyncConsumer.class);

    @Autowired
    private JobPositionDao positionDao;

    @Autowired
    HRThirdPartyPositionDao thirdpartyPositionDao;

    @Autowired
    private HRThirdPartyAccountDao thirdPartyAccountDao;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

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
		List<String> result = redisClient.brpop(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_SYNCHRONIZATION_COMPLETED_QUEUE.toString());
		if(result != null && result.size() > 0) {
			logger.info("completed queue :"+result.get(1));
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
		List<ThirdPartyPositionData> datas = new ArrayList<>();
		ThirdPartyPositionData data = new ThirdPartyPositionData();
		data.setChannel(Byte.valueOf(pojo.getChannel()));
		data.setPosition_id(Integer.valueOf(pojo.getPosition_id()));
		data.setThird_part_position_id(pojo.getJob_id());
		data.setAccount_id(String.valueOf(pojo.getAccount_id()));
		if(pojo.getStatus() == 0) {
			data.setIs_synchronization((byte)PositionSync.bound.getValue());
			data.setSync_time(pojo.getSync_time());
		} else {
			data.setIs_synchronization((byte)PositionSync.failed.getValue());
			if(pojo.getStatus() == 2) {
				data.setSync_fail_reason(Constant.POSITION_SYNCHRONIZATION_FAILED);
			} else {
				if(StringUtils.isNotNullOrEmpty(pojo.getPub_place_name())) {
					data.setSync_fail_reason(pojo.getMessage().replace("{}", pojo.getPub_place_name()));
				} else {
					data.setSync_fail_reason(pojo.getMessage());
				}
			}
		}
		datas.add(data);
		
		try {
            Query.QueryBuilder qu = new Query.QueryBuilder();
			qu.where("id", pojo.getPosition_id());
			logger.info("completed queue search position:{}", pojo.getPosition_id());
			Position p = positionDao.getData(qu.buildQuery(), Position.class);
			if(p != null && p.getId() > 0) {
				logger.info("completed queue position existî");
				logger.info("completed queue update thirdpartyposition to synchronized");
                thirdpartyPositionDao.upsertThirdPartyPositions(datas);
				if(pojo.getStatus() == 0) {
					ThirdPartAccountData d = new ThirdPartAccountData();
					d.setCompany_id(p.getCompany_id());
					d.setRemain_num(pojo.getRemain_number());
					d.setRemain_profile_num(pojo.getResume_number());
					d.setChannel(Integer.valueOf(pojo.getChannel().trim()));
					d.setSync_time(pojo.getSync_time());
					d.setId(pojo.getAccount_id());
					//positionDao.updatePosition(p);
					logger.info("completed queue update thirdpartyposition to synchronized");
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
