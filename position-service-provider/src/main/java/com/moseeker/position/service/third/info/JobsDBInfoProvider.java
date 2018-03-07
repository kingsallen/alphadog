package com.moseeker.position.service.third.info;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.RefreshConstant;
import com.moseeker.common.util.StructSerializer;
import com.moseeker.position.service.third.base.AbstractThirdInfoProvider;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JobsDBInfoProvider extends AbstractThirdInfoProvider{
    Logger logger= LoggerFactory.getLogger(JobsDBInfoProvider.class);

    @Override
    public ChannelType getChannel() {
        return ChannelType.JOBSDB;
    }

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @Override
    public String provide(ThirdPartyAccountInfoParam param) throws Exception {
        JSONObject obj=new JSONObject();

        int accountId = getThirdPartyAccount(param).getThirdPartyAccountId();

        //获取redis中的字段(提供食宿、计算机能力、语言能力等等)
        String str=redisClient.get(RefreshConstant.APP_ID, RefreshConstant.VERY_EAST_REDIS_PARAM_KEY,"");
        logger.info("jobsdb redis param :"+str);
        if(str==null){
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,accountId+"在JobsDB不存在work location、Salary");
        }

        //取出字段以后合并到obj中
        obj=mergeJsonObject(JSON.parseObject(str),obj);

        return StructSerializer.toString(obj);
    }
}
