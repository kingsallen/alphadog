package com.moseeker.position.service.position.base.sync.verify;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.position.service.position.base.sync.PositionSyncVerifyHandlerUtil;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class MobileEnvironVerifyHandler implements PositionSyncVerifyReceiver<String> {
    Logger logger = LoggerFactory.getLogger(MobileEnvironVerifyHandler.class);

    @Autowired
    private UserHrAccountDao userHrAccountDao;

    @Autowired
    private PositionSyncVerifyHandlerUtil verifyHandlerUtil;

    @Resource(name = "cacheClient")
    protected RedisClient redisClient;

    @Autowired
    protected MobileVeifyHandler mobileVeifyHandler;

    @Override
    public void receive(String json) throws BIZException {
        JSONObject jsonParam = JSON.parseObject(json);

        logger.info("verifyHandler jsonParam:{}",jsonParam.toJSONString());
        Integer accountId=jsonParam.getInteger("accountId");
        Integer channel=jsonParam.getInteger("channel");

        AbstractPositionSyncVerifyHandler.JsonVerifyParam verifyParam= AbstractPositionSyncVerifyHandler.JsonVerifyParam.newInstance(json);
        mobileVeifyHandler.alreadyInRedis(verifyParam);

        ChannelType channelType=ChannelType.instaceFromInteger(channel);

        UserHrAccountDO userHrAccountDO=userHrAccountDao.getValidAccount(accountId);

        HrThirdPartyPositionDO thirdPartyPosition = new HrThirdPartyPositionDO();
        thirdPartyPosition.setSyncTime(FastDateFormat.getInstance(DateUtils.SHOT_TIME).format(new Date()));

        //发送消息模板
        verifyHandlerUtil.sendMobileCodeTemplate(channelType,userHrAccountDO,thirdPartyPosition,jsonParam);

        logger.info("verifyHandler success jsonParam:{}",jsonParam.toJSONString());
    }


}
