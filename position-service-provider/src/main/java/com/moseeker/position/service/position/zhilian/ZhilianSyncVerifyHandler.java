package com.moseeker.position.service.position.zhilian;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.hrdb.HrWxTemplateMessageDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccount;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.baseorm.db.hrdb.tables.HrWxTemplateMessage;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserWxUser;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.PositionSyncVerify;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.position.service.position.base.sync.PositionSyncVerifyHandler;
import com.moseeker.position.service.position.base.sync.PositionSyncVerifyHandlerUtil;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;
import com.moseeker.thrift.gen.useraccounts.struct.UserHrAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ZhilianSyncVerifyHandler implements PositionSyncVerifyHandler<String,String>{
    Logger logger= LoggerFactory.getLogger(ZhilianSyncVerifyHandler.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private JobPositionDao positionDao;

    @Autowired
    private HRThirdPartyPositionDao thirdPartyPositionDao;

    @Autowired
    private UserHrAccountDao userHrAccountDao;

    @Autowired
    private UserWxUserDao userWxUserDao;

    @Autowired
    private PositionSyncVerifyHandlerUtil verifyHandlerUtil;

    @Autowired
    private HrWxTemplateMessageDao templateMessageDao;

    @Override
    public void verifyHandler(String param) throws BIZException {
        JSONObject jsonParam= JSON.parseObject(param);
        Integer accountId=jsonParam.getInteger("accountId");
        Integer channel=jsonParam.getInteger("channel");
        String mobile=jsonParam.getString("mobile");
        Integer positionId=jsonParam.getInteger("positionId");

        if(accountId==null || channel==null || StringUtils.isNullOrEmpty(mobile) || positionId==null){
            logger.error("智联验证处理参数错误：{}",param);
            throw new RuntimeException("智联验证处理参数错误");
        }


        JobPositionRecord jobPosition=positionDao.getPositionById(positionId);
        if(jobPosition == null){
            logger.error("智联验证处理职位不存在：{}",param);
            throw new RuntimeException("智联验证处理职位不存在");
        }

        Query query=new Query.QueryBuilder()
                .where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID.getName(),positionId)
                .and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.THIRD_PARTY_ACCOUNT_ID.getName(),accountId)
                .and(new Condition(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.IS_SYNCHRONIZATION.getName(),(short)0, ValueOp.NEQ))
                .buildQuery();
        HrThirdPartyPositionDO thirdPartyPosition = thirdPartyPositionDao.getSimpleData(query);

        ChannelType channelType=ChannelType.instaceFromInteger(channel);

        UserHrAccountDO userHrAccountDO=userHrAccountDao.getValidAccount(jobPosition.getPublisher());

        UserWxUserDO userWxUserDO=userWxUserDao.getWXUserById(userHrAccountDO.getWxuserId());

        query=new Query.QueryBuilder()
                .where(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.WECHAT_ID.getName(),userWxUserDO.getId())
                .and(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.SYS_TEMPLATE_ID.getName(), Constant.POSITION_SYNC_VERIFY_INFO)
                .buildQuery();
        HrWxWechatDO hrWxWechatDO=verifyHandlerUtil.getMoseekerWxWechat();
        String openId=userWxUserDO.getOpenid();
        String url=verifyHandlerUtil.deliveryUrl();
        String link=verifyHandlerUtil.getCodeLink(jsonParam);
        HrWxTemplateMessageDO template=templateMessageDao.getData(query);

        Map<String, Object> applierTemplate = new HashMap<>();
        Map<String,MessageTplDataCol> colMap =new HashMap<>();

        //消息头
        MessageTplDataCol first = new MessageTplDataCol();
        first.setColor("#173177");
        first.setValue("您好，您正在同步职位至"+channelType.getAlias()+"，需要您进行手机验证");
        colMap.put("first",first);

        //消息尾
        MessageTplDataCol remark = new MessageTplDataCol();
        remark.setColor("#173177");
        remark.setValue("请点击详情填写"+channelType.getAlias()+"发送的验证码，此验证码仅用于本次职位同步");
        colMap.put("remark",remark);

        //申请人
        MessageTplDataCol job = new MessageTplDataCol();
        job.setColor("#173177");
        job.setValue(userHrAccountDO.getUsername());
        colMap.put("job",job);

        //申请时间
        MessageTplDataCol time=new MessageTplDataCol();
        time.setColor("#173177");
        time.setValue(thirdPartyPosition.getSyncTime());
        colMap.put("time",time);;

        applierTemplate.put("data", colMap);
        applierTemplate.put("touser", openId);
        applierTemplate.put("template_id", template.getWxTemplateId());
        applierTemplate.put("url", link);
        applierTemplate.put("topcolor", template.getTopcolor());
        String result=null;
        try {
            result  = HttpClient.sendPost(url, JSON.toJSONString(applierTemplate));
        }catch (ConnectException e){
            logger.error("send zhilian mobile code verify info error! param: {}",param);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"send zhilian mobile code verify info error! param");
        }


        Map<String, Object> params = JSON.parseObject(result);
        verifyHandlerUtil.insertLogWxMessageRecord(hrWxWechatDO, template, openId, link, colMap ,params);

        if(params!= null && "0".equals(params.get("errcode"))){
            return ;
        }
        throw new BIZException((int)params.get("errcode"), (String)params.get("errmsg"));
    }

    @Override
    public void syncVerifyInfo(String info) throws BIZException{
        if(StringUtils.isNullOrEmpty(info)){
            logger.error("智联验证信息为空，无法发送消息给爬虫端");
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"智联验证信息，无法发送消息给爬虫端");
        }
        JSONObject jsonObject= JSON.parseObject(info);
        String accountId=jsonObject.getString("accountId");
        if(StringUtils.isNullOrEmpty(accountId)){
            logger.error("智联验证信息accountId为空，无法发送消息给爬虫端,info : "+info);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"智联验证信息第三方账号为空，无法发送消息给爬虫端！");
        }

        //同一信息都是info，但是智联需要code
        jsonObject.put("code",jsonObject.get("info"));

        String rountingKey=PositionSyncVerify.MOBILE_VERIFY_RESPONSE_ROUTING_KEY.replace("{}",accountId);
        amqpTemplate.send(
                PositionSyncVerify.MOBILE_VERIFY_EXCHANGE
                , rountingKey
                , MessageBuilder.withBody(info.getBytes()).build());
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.ZHILIAN;
    }
}





















