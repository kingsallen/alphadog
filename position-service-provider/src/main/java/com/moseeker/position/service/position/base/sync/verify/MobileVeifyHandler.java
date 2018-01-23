package com.moseeker.position.service.position.base.sync.verify;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HrWxTemplateMessageDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.hrdb.tables.HrWxTemplateMessage;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.query.Query;
import com.moseeker.position.service.position.base.sync.PositionSyncVerifyHandlerUtil;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;


@Component
public class MobileVeifyHandler {
    Logger logger= LoggerFactory.getLogger(MobileVeifyHandler.class);

    @Autowired
    private PositionSyncVerifyHandlerUtil verifyHandlerUtil;

    @Autowired
    private UserWxUserDao userWxUserDao;

    @Autowired
    private HrWxTemplateMessageDao templateMessageDao;

    public void verifyHandler(ChannelType channelType, UserHrAccountDO userHrAccountDO , HrThirdPartyPositionDO thirdPartyPosition, Map<String,Object> param) throws BIZException {
        UserWxUserDO userWxUserDO=userWxUserDao.getWXUserById(userHrAccountDO.getWxuserId());

        HrWxWechatDO hrWxWechatDO=verifyHandlerUtil.getMoseekerWxWechat();

        String openId=userWxUserDO.getOpenid();
        String url=verifyHandlerUtil.deliveryUrl();
        String link=verifyHandlerUtil.getCodeLink(param);

        Query query=new Query.QueryBuilder()
                .where(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.WECHAT_ID.getName(),hrWxWechatDO.getId())
                .and(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.SYS_TEMPLATE_ID.getName(), Constant.POSITION_SYNC_VERIFY_INFO)
                .buildQuery();
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
        MessageTplDataCol applier = new MessageTplDataCol();
        applier.setColor("#173177");
        applier.setValue(userHrAccountDO.getUsername());
        colMap.put("keyword1",applier);

        //申请时间
        MessageTplDataCol time=new MessageTplDataCol();
        time.setColor("#173177");
        time.setValue(thirdPartyPosition.getSyncTime());
        colMap.put("keyword2",time);;

        applierTemplate.put("data", colMap);
        applierTemplate.put("touser", openId);
        applierTemplate.put("template_id", template.getWxTemplateId());
        applierTemplate.put("url", link);
        applierTemplate.put("topcolor", template.getTopcolor());
        String result=null;
        try {
            result  = HttpClient.sendPost(url, JSON.toJSONString(applierTemplate));
        }catch (ConnectException e){
            logger.error("send zhilian mobile code verify info error! param: {}",JSON.toJSONString(param));
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"send zhilian mobile code verify info error! param");
        }


        Map<String, Object> params = JSON.parseObject(result);
        verifyHandlerUtil.insertLogWxMessageRecord(hrWxWechatDO, template, openId, link, colMap ,params);

        if(params!= null && "0".equals(params.get("errcode"))){
            return ;
        }
        throw new BIZException((int)params.get("errcode"), (String)params.get("errmsg"));
    }
}
