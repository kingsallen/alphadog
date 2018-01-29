package com.moseeker.baseorm.util;

import com.moseeker.baseorm.base.AbstractDictOccupationDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OccupationUtil {
    Logger logger= LoggerFactory.getLogger(OccupationUtil.class);

    @Autowired
    List<AbstractDictOccupationDao> daos;

    public AbstractDictOccupationDao getOccupationDaoInstance(int channel) throws BIZException {
        ChannelType channelType=ChannelType.instaceFromInteger(channel);
        if(channelType==null){
            logger.error("wrong channel : {}",channel);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"wrong channel");
        }

        return getOccupationDaoInstance(channelType);
    }

    public AbstractDictOccupationDao getOccupationDaoInstance(ChannelType channelType) throws BIZException {
        if(daos==null || daos.isEmpty()){
            logger.error("no AbstractDictOccupationDao");
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"no AbstractDictOccupationDao");
        }
        for(AbstractDictOccupationDao dao:daos){
            if(dao.getChannelType()==channelType){
                return dao;
            }
        }
        logger.error("no matched AbstractDictOccupationDao");
        throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"no matched AbstractDictOccupationDao");
    }
}
