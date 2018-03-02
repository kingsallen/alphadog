package com.moseeker.position.service.position.zhilian;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.hrdb.HrWxTemplateMessageDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.baseorm.db.hrdb.tables.HrWxTemplateMessage;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.PositionSyncVerify;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.position.service.position.base.sync.verify.*;
import com.moseeker.position.service.position.base.sync.PositionSyncVerifyHandlerUtil;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ZhilianSyncVerifyHandler implements PositionSyncVerifier<String> {
    Logger logger= LoggerFactory.getLogger(ZhilianSyncVerifyHandler.class);

    @Resource(type = MobileVeifyHandler.class)
    private PositionSyncVerifyHandler verifyHandler;

    @Resource(type = MobileVeifyHandler.class)
    private PositionSyncVerifyReceiver verifyReceiver;

    @Override
    public void handler(String info) throws BIZException {
        logger.info("zhilian syncVerifyInfo info:{}",info);
        try {
            verifyHandler.handler(info);
        }catch (BIZException e){
            logger.error("zhilian syncVerifyInfo BIZException info:{},error:{}",info,e);
            throw e;
        }catch (Exception e){
            logger.error("zhilian syncVerifyInfo Exception info:{},error:{}",info,e);
            throw new SysBIZException();
        }

    }

    @Override
    public void receive(String param) throws BIZException {
        logger.info("zhilian verifyHandler param:{}",param);
        try {
            verifyReceiver.receive(param);
        }catch (BIZException e){
            logger.error("zhilian verifyHandler BIZException param:{},error:{}",param,e);
            throw e;
        }catch (Exception e){
            logger.error("zhilian syncVerifyInfo Exception param:{},error:{}",param,e);
            throw new SysBIZException();
        }
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.ZHILIAN;
    }
}





















