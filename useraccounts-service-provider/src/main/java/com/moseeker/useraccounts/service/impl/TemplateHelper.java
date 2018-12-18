package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.configdb.ConfigSysTemplateMessageLibraryDao;
import com.moseeker.baseorm.dao.hrdb.HrWxTemplateMessageDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.logdb.LogWxMessageRecordDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysTemplateMessageLibraryRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.impl.vo.InviteTemplateVO;
import com.moseeker.useraccounts.service.impl.vo.TemplateBaseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

@Component
public class TemplateHelper {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HrWxWechatDao hrWxWechatDao;

    @Autowired
    private ConfigSysTemplateMessageLibraryDao templateDao;

    @Autowired
    private LogWxMessageRecordDao wxMessageRecordDao;

    @Autowired
    private HrWxTemplateMessageDao wxTemplateMessageDao;

    @Autowired
    private UserWxUserDao userWxUserDao;

    public void sendInviteTemplate(int endUserId, int companyId, InviteTemplateVO inviteTemplateVO, String requestUrl, String redirectUrl) throws ConnectException, BIZException {
        HrWxWechatDO hrWxWechatDO = hrWxWechatDao.getData(new Query.QueryBuilder().where(HrWxWechat.HR_WX_WECHAT.COMPANY_ID.getName(), companyId).buildQuery());
        UserWxUserRecord userWxUserRecord = userWxUserDao.getWxUserByUserIdAndWechatId(endUserId, hrWxWechatDO.getId());
        HrWxTemplateMessageDO hrWxTemplateMessageDO = getHrWxTemplateMessageByWechatIdAndSysTemplateId(hrWxWechatDO, inviteTemplateVO.getTemplateId());
        Map<String, Object> requestMap = new HashMap<>(1 >> 4);
        Map<String, TemplateBaseVO> dataMap = createDataMap(inviteTemplateVO);
        requestMap.put("data", dataMap);
        requestMap.put("touser", userWxUserRecord.getOpenid());
        requestMap.put("template_id", hrWxTemplateMessageDO.getWxTemplateId());
        requestMap.put("url", redirectUrl);
        requestMap.put("topcolor", hrWxTemplateMessageDO.getTopcolor());
        String result = HttpClient.sendPost(requestUrl, JSON.toJSONString(requestMap));
        Map<String, Object> params = JSON.parseObject(result);
        requestMap.put("response", params);
        requestMap.put("accessToken", hrWxWechatDO.getAccessToken());
        logger.info("====================requestMap:{}", requestMap);
        // 插入模板消息发送记录
        wxMessageRecordDao.insertLogWxMessageRecord(inviteTemplateVO.getTemplateId(), hrWxWechatDO.getId(), requestMap);
    }

    private Map<String,TemplateBaseVO> createDataMap(InviteTemplateVO inviteTemplateVO) {
        ConfigSysTemplateMessageLibraryRecord record = templateDao.getConfigSysTemplateMessageLibraryDOByidListAndDisable(inviteTemplateVO.getTemplateId());
        JSONObject color = JSONObject.parseObject(record.getColorJson());
        Map<String, TemplateBaseVO> dataMap = new HashMap<>(1 >> 4);
        TemplateBaseVO first = createTplVO(inviteTemplateVO.getFirst(), color.getString("first"));
        TemplateBaseVO keyWord1 = createTplVO(inviteTemplateVO.getKeyWord1(), color.getString("keyword1"));
        TemplateBaseVO keyWord2 = createTplVO(inviteTemplateVO.getKeyWord2(), color.getString("keyword2"));
        TemplateBaseVO keyWord3 = createTplVO(inviteTemplateVO.getKeyWord3(), color.getString("keyword3"));
        TemplateBaseVO remark = createTplVO(inviteTemplateVO.getRemark(), color.getString("remark"));
        dataMap.put("first", first);
        dataMap.put("keyword1", keyWord1);
        dataMap.put("keyword2", keyWord2);
        dataMap.put("keyword3", keyWord3);
        dataMap.put("remark", remark);
        return dataMap;
    }

    private TemplateBaseVO createTplVO(String color, String value){
        TemplateBaseVO templateBaseVO = new TemplateBaseVO();
        templateBaseVO.setColor(color);
        templateBaseVO.setValue(value);
        return templateBaseVO;
    }

    private HrWxTemplateMessageDO getHrWxTemplateMessageByWechatIdAndSysTemplateId(HrWxWechatDO hrWxWechatDO, int sysTemplateId) throws BIZException {
        HrWxTemplateMessageDO hrWxTemplateMessageDO = wxTemplateMessageDao.getData(new Query.QueryBuilder().where("wechat_id",
                hrWxWechatDO.getId()).and("sys_template_id", sysTemplateId).and("disable","0").buildQuery());
        if(hrWxTemplateMessageDO == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MQ_TEMPLATE_NOTICE_CLOSE);
        }
        return hrWxTemplateMessageDO;
    }
}
