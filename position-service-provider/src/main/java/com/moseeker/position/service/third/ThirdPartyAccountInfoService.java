package com.moseeker.position.service.third;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.position.service.third.base.AbstractThirdInfoProvider;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.thirdpart.struct.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ThirdPartyAccountInfoService {
    Logger logger= LoggerFactory.getLogger(this.getClass());

    Map<ChannelType,AbstractThirdInfoProvider> providers=new HashMap<>();

    @Autowired
    public ThirdPartyAccountInfoService(List<AbstractThirdInfoProvider> providers){
        this.providers.putAll(providers.stream().collect(Collectors.toMap(p->p.getChannel(),p->p)));
    }


    public ThirdPartyAccountInfo getAllInfo(ThirdPartyAccountInfoParam param) throws Exception {
        logger.info("ThirdPartyAccountInfoParam:{}",param);
        ThirdPartyAccountInfo info=new ThirdPartyAccountInfo();

        ChannelType channel=ChannelType.instaceFromInteger(param.getChannel());

        if(channel==null){
            logger.info("Wrong Channel:{} ",param.getChannel());
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"Wrong Channel");
        }

        if(!providers.containsKey(channel)){
            logger.info("The Channel:{} does not has the InfoProvider",channel);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"The Channel does not has the InfoProvider");
        }

        String json=providers.get(channel).provide(param);
        info.setJson(json);

        logger.info("ThirdPartyInfo json result : "+json);
        return info;
    }













}
