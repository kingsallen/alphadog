package com.moseeker.position.service.position;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.util.JsonToMap;
import com.moseeker.common.util.StructSerializer;
import com.moseeker.position.service.position.base.sync.PositionTransfer;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 职位转换
 *
 * @author wjf
 */
@Service
public class PositionChangeUtil {

    private static Logger logger = LoggerFactory.getLogger(PositionChangeUtil.class);

    @Autowired
    List<PositionTransfer> transferList;

    /**
     * 将仟寻职位转成第卅方职位
     *
     * @param jsonForm
     * @param positionDB
     * @param account
     * @return
     */
    public PositionTransfer.TransferResult changeToThirdPartyPosition(JSONObject jsonForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) throws Exception {
        logger.info("changeToThirdPartyPosition---------------------jsonForm : {},positionDB : {}, account : {}",jsonForm,positionDB,account);

        int channel=jsonForm.getIntValue("channel");

        ChannelType channelType = ChannelType.instaceFromInteger(channel);
        if(channelType==null){
            logger.error("change To ThirdPartyPosition no matched channelType : {}",channel);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"change To ThirdPartyPosition no matched channelType");
        }

        PositionTransfer transfer=transferSimpleFactory(channelType);

        PositionTransfer.TransferResult result=transfer.changeToThirdPartyPosition(jsonForm,positionDB,account);
        logger.info("changeToThirdPartyPosition result:{}",result);

        return result;
    }

    public Object toThirdPartyPosition(int channel,Map<String,String> data) throws BIZException {
        ChannelType channelType = ChannelType.instaceFromInteger(channel);
        if(channelType==null){
            logger.error("change To ThirdPartyPosition no matched channelType : {}",channel);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"change To ThirdPartyPosition no matched channelType");
        }

        PositionTransfer transfer=transferSimpleFactory(channelType);

        try {
            Object ThirdParty=transfer.toExtThirdPartyPosition(data);
            return ThirdParty;
        }catch (Exception e){
            logger.error("to ExtThirdPartyPosition error channel:{},data:{}",channel,JSON.toJSONString(data));
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"to ExtThirdPartyPosition error");
        }
    }

    public PositionTransfer transferSimpleFactory(ChannelType channelType) throws BIZException {
        for(PositionTransfer transfer:transferList){
            if(channelType==transfer.getChannel()){
                return transfer;
            }
        }
        logger.error("no matched PositionTransfer {}",channelType);
        throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"no matched PositionTransfer");
    }

    public static Map<String,Object> objectToMap(Object object){
        if(object== EmptyExtThirdPartyPosition.EMPTY){
            return new HashMap<>();
        }
        String json= StructSerializer.toString(object);
        Map<String,Object> result= JsonToMap.parseJSON2Map(json);

        return result;
    }

    public static String objectToStr(Object object){
        if(object== EmptyExtThirdPartyPosition.EMPTY){
            return "";
        }
        String json= StructSerializer.toString(object);

        return json;
    }
}
