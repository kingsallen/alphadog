package com.moseeker.baseorm.dao.hrdb.utils;

import com.moseeker.baseorm.base.IThirdPartyPositionDao;
import com.moseeker.baseorm.dao.thirdpartydb.DefaultThirdPartyPositionDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ThirdPartyPositionDaoFactory {
    Logger logger= LoggerFactory.getLogger(ThirdPartyPositionDaoFactory.class);

    @Autowired
    List<IThirdPartyPositionDao> daos;

    @Autowired
    DefaultThirdPartyPositionDao defaultThirdPartyPositionDao;

    /**
     * 工厂类，根据渠道获取对应的dao
     * @param channel
     * @return
     * @throws BIZException
     */
    @Transactional
    public IThirdPartyPositionDao thirdPartyPositionDao(int channel) {
        ChannelType channelType=ChannelType.instaceFromInteger(channel);
        if(channelType==null){
            logger.error("no matched ChannelPositionDao! channel : {}",channel);
            throw new RuntimeException("no matched ChannelPositionDao channel");
        }
        for(IThirdPartyPositionDao dao:daos){
            if(dao.getChannelType()==channelType){
                return dao;
            }
        }
        logger.error("no matched thirdPartyPositionDao! channel : {}",channel);
        return defaultThirdPartyPositionDao;
    }

}
