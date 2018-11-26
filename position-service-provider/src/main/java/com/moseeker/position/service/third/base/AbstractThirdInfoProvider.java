package com.moseeker.position.service.third.base;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class AbstractThirdInfoProvider implements JsonThirdPartyInfoProvider {
    Logger logger= LoggerFactory.getLogger(this.getClass());

    protected final static String COMPANY="company";
    protected final static String ADDRESS="address";
    protected final static String DEPARTMENT="department";
    protected final static String FEATURE = "features";

    @Autowired
    HRThirdPartyAccountHrDao hrThirdPartyAccountHrDao;

    @Autowired
    protected HRThirdPartyAccountDao hrThirdPartyAccountDao;

    /**
     * 获取HR账号在某个渠道下的第三方账
     * @param param
     * @return
     * @throws TException 当没有获取HR账号在某个渠道下的第三方账号时，抛出异常
     */
    protected HrThirdPartyAccountHrDO getThirdPartyAccount(ThirdPartyAccountInfoParam param) throws Exception{
        long hrId=param.getHrId();
        int channel=param.getChannel();

        logger.info("获取"+hrId+"HR在渠道"+channel+"下的第三方账号");
        HrThirdPartyAccountHrDO hrThirdPartyAccountHrDO=hrThirdPartyAccountHrDao.getData(hrId,channel);

        if(hrThirdPartyAccountHrDO == null || hrThirdPartyAccountHrDO.getId() == 0){
            throw new BIZException(ConstantErrorCodeMessage.NO_BIND_THIRD_PARTY_ACCOUNT_STATUS,param.getHrId()+"账号在渠道"+param.getChannel()+"没有绑定的第三方账号");
        }

        logger.info("获取到的第三方账号为:"+hrThirdPartyAccountHrDO.getThirdPartyAccountId());
        return hrThirdPartyAccountHrDO;
    }

    protected JSONObject mergeJsonObject(JSONObject src,JSONObject dest){
        if(src == null || dest == null){
            return dest;
        }
        /*Iterator<Map.Entry<String,Object>> it= src.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,Object> e=it.next();
            dest.put(e.getKey(),e.getValue());
        }*/
        dest.putAll(src);
        return dest;
    }

    public abstract ChannelType getChannel();
}
