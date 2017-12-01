package com.moseeker.position.service.position;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.base.PositionTransfer;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public JSONObject changeToThirdPartyPosition(JSONObject jsonForm, JobPositionDO positionDB,HrThirdPartyAccountDO account) throws Exception {
        logger.info("changeToThirdPartyPosition---------------------jsonForm : {},positionDB : {}, account : {}",jsonForm,positionDB,account);

        int channel=jsonForm.getIntValue("channel");

        ChannelType channelType = ChannelType.instaceFromInteger(channel);
        if(channelType==null){
            logger.error("change To ThirdPartyPosition no matched channelType : {}",channel);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"change To ThirdPartyPosition no matched channelType");
        }

        PositionTransfer transfer=transferSimpleFactory(channelType);

        String jsonPosition=JSON.toJSONString(transfer.changeToThirdPartyPosition(jsonForm,positionDB,account));
        logger.info("changeToThirdPartyPosition result:{}",jsonPosition);
        JSONObject position=JSONObject.parseObject(jsonPosition);

        return position;
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

    public static JSONObject tryToParseJsonForm(String json) throws BIZException {
        try {
            JSONObject obj=JSONObject.parseObject(json);
            return obj;
        }catch (Exception e){
            logger.info("try to parse json form failed : {}",json);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"try to parse json form failed");
        }
    }
}
