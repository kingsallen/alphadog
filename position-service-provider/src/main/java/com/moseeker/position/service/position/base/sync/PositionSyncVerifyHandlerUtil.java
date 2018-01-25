package com.moseeker.position.service.position.base.sync;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.logdb.LogWxMessageRecordDao;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.logdb.LogWxMessageRecordDO;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * 职位同步验证工具类
 */
@Component
public class PositionSyncVerifyHandlerUtil {
    Logger logger= LoggerFactory.getLogger(PositionSyncVerifyHandlerUtil.class);

    @Resource(name = "cacheClient")
    protected RedisClient redisClient;


    public String getParam(String redisKey){
        return redisClient.get(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.POSITION_SYNC_VERIFY_TIMEOUT.toString(),redisKey);
    }

    public void delParam(String redisKey){
        redisClient.del(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.POSITION_SYNC_VERIFY_TIMEOUT.toString(),redisKey);
    }

    public void setParam(String redisKey,String value){
        redisClient.set(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.POSITION_SYNC_VERIFY_TIMEOUT.toString(),redisKey,value);
    }

}
