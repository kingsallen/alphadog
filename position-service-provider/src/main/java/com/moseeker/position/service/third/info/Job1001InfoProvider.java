package com.moseeker.position.service.third.info;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.util.StructSerializer;
import com.moseeker.position.constants.RefreshConstant;
import com.moseeker.position.service.third.ThirdPartyAccountAddressService;
import com.moseeker.position.service.third.ThirdPartyAccountCompanyService;
import com.moseeker.position.service.third.base.AbstractThirdInfoProvider;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class Job1001InfoProvider extends AbstractThirdInfoProvider {
    Logger logger= LoggerFactory.getLogger(Job1001InfoProvider.class);

    @Autowired
    ThirdPartyAccountCompanyService companyService;
    @Autowired
    ThirdPartyAccountAddressService addressService;
    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @Override
    public ChannelType getChannel() {
        return ChannelType.JOB1001;
    }

    @Override
    public String provide(ThirdPartyAccountInfoParam param) throws Exception {
        int accountId = getThirdPartyAccount(param).getThirdPartyAccountId();

        JSONObject obj=new JSONObject();
        obj.put(COMPANY,companyService.getCompanyByAccountId(accountId));
        obj.put(ADDRESS,addressService.getCompanyAddressByAccountId(accountId));

        //获取redis中的字段(提供食宿、计算机能力、语言能力等等)
        String str=redisClient.get(RefreshConstant.APP_ID, RefreshConstant.VERY_EAST_REDIS_PARAM_KEY,"");
        logger.info("Job1001 redis param :"+str);
        if(str==null){
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,accountId+"在Job1001没有发布站点");
        }

        //取出字段以后合并到obj中
        obj=mergeJsonObject(JSON.parseObject(str),obj);

        return StructSerializer.toString(obj);
    }
}
