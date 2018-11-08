package com.moseeker.mall.service;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HrWxTemplateMessageDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.logdb.LogWxMessageRecordDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.query.Query;
import com.moseeker.mall.vo.TemplateBaseVO;
import com.moseeker.mall.vo.TemplateDataValueVO;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.logdb.LogWxMessageRecordDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息模板服务
 *
 * @author cjm
 * @date 2018-10-24 14:21
 **/
@Service
public class TemplateService {

    private static Logger logger = LoggerFactory.getLogger(TemplateService.class);

    private static final String COLOR = "#173177";

    private final HrWxWechatDao hrWxWechatDao;

    private final HrWxTemplateMessageDao wxTemplateMessageDao;

    private final Environment env;

    private final LogWxMessageRecordDao wxMessageRecordDao;

    private final UserWxUserDao userWxUserDao;

    @Autowired
    public TemplateService(HrWxWechatDao hrWxWechatDao, HrWxTemplateMessageDao wxTemplateMessageDao, Environment env,
                           LogWxMessageRecordDao wxMessageRecordDao, UserWxUserDao userWxUserDao) {
        this.hrWxWechatDao = hrWxWechatDao;
        this.wxTemplateMessageDao = wxTemplateMessageDao;
        this.env = env;
        this.wxMessageRecordDao = wxMessageRecordDao;
        this.userWxUserDao = userWxUserDao;
    }

    /**
     *
     * @param  sysUserId user_user.id
     * @param  companyId 公司id
     * @param  templateId 消息模板id
     * @param  title 消息模板标题自定义
     * @param  keyWord1 关键词
     * @param  keyWord2 关键词
     * @param  keyWord3 关键词
     * @param  keyWord4 关键词
     * @param  remark 消息模板remark
     * @param  url 点击消息模板跳转链接
     * @author  cjm
     * @date  2018/10/25
     */
    public void sendAwardTemplate(int sysUserId, int companyId, int templateId, String title,
                                   String keyWord1, String keyWord2, String keyWord3, String keyWord4, String remark, String url) {
        TemplateDataValueVO templateDataValueVO = new TemplateDataValueVO();
        HrWxWechatDO hrWxWechatDO = getHrwxWechatDOByCompanyId(companyId);
        templateDataValueVO.setFirst(title);
        templateDataValueVO.setTemplateId(templateId);
        templateDataValueVO.setCompanyId(companyId);
        templateDataValueVO.setKeyWord1(keyWord1);
        templateDataValueVO.setKeyWord2(keyWord2);
        templateDataValueVO.setKeyWord3(keyWord3);
        templateDataValueVO.setKeyWord4(keyWord4);
        templateDataValueVO.setRemark(remark);
        try {
            UserWxUserRecord userWxUserRecord = userWxUserDao.getWxUserByUserIdAndWechatId(sysUserId, hrWxWechatDO.getId());

            Map<String, Object> templateValueMap = sendCreditUpdateTemplate(userWxUserRecord.getOpenid(), templateDataValueVO, hrWxWechatDO, url);
            // 插入模板消息发送记录
            insertLogWxMessageRecord(templateId, hrWxWechatDO.getId(), templateValueMap);
        }catch (BIZException e){
            logger.info("==============消息模板发送业务失败:{}", e.getMessage());
        }catch (ConnectException e){
            logger.info("=========================积分消息模板发送超时：templateDataValueVO:{}", templateDataValueVO);
        }catch (Exception e){
            logger.info("==============消息模板发送失败:{}", e.getMessage());
        }
    }

    /**
     * 发送积分变动消息模板
     * @param templateDataValueVO 积分提醒消息模板values
     * @param openId 微信用户openId
     * @param url 消息模板的url
     * @author  cjm
     * @date  2018/10/24
     * @return 微信返回的模板消息发送发送结果
     */
    private Map<String, Object> sendCreditUpdateTemplate(String openId, TemplateDataValueVO templateDataValueVO, HrWxWechatDO hrWxWechatDO, String url) throws ConnectException, BIZException {
        HrWxTemplateMessageDO hrWxTemplateMessageDO = getHrWxTemplateMessageByWechatIdAndSysTemplateId(hrWxWechatDO, templateDataValueVO.getTemplateId());
        String requestUrl = getAwardTemplateUrl(hrWxWechatDO);
        Map<String, Object> requestMap = new HashMap<>(1 >> 4);
        Map<String, TemplateBaseVO> dataMap = createDataMap(templateDataValueVO);
        url = url.replace("{}", hrWxWechatDO.getSignature());
        requestMap.put("data", dataMap);
        requestMap.put("touser", openId);
        requestMap.put("template_id", hrWxTemplateMessageDO.getWxTemplateId());
        requestMap.put("url", url);
        requestMap.put("topcolor", hrWxTemplateMessageDO.getTopcolor());
        String result = HttpClient.sendPost(requestUrl, JSON.toJSONString(requestMap));
        Map<String, Object> params = JSON.parseObject(result);
        requestMap.put("response", params);
        requestMap.put("accessToken", hrWxWechatDO.getAccessToken());
        logger.info("====================requestMap:{}", requestMap);
        return requestMap;
    }

    /**
     * 插入消息模板记录
     * @param  templateId 消息模板短id
     * @param  wechatId 公众号id
     * @param  templateValueMap 发送消息模板的请求参数和返回参数
     * @author  cjm
     * @date  2018/10/25
     */
    @SuppressWarnings("unchecked")
    private void insertLogWxMessageRecord(int templateId, int wechatId, Map<String, Object> templateValueMap) {
        LogWxMessageRecordDO messageRecord = new LogWxMessageRecordDO();
        messageRecord.setTemplateId(templateId);
        messageRecord.setOpenId(String.valueOf(templateValueMap.get("touser")));
        messageRecord.setAccessToken(String.valueOf(templateValueMap.get("accessToken")));
        messageRecord.setJsondata(JSON.toJSONString(templateValueMap.get("data")));
        messageRecord.setUrl(String.valueOf(templateValueMap.get("url")));
        messageRecord.setWechatId(wechatId);
        messageRecord.setTopcolor(String.valueOf(templateValueMap.get("topcolor")));
        Map<String, Object> response = (Map<String, Object>)templateValueMap.get("response");
        String errcode = String.valueOf(response.get("errcode"));
        if("0".equals(errcode)){
            messageRecord.setSendstatus("success");
            messageRecord.setMsgid(Long.parseLong(String.valueOf(response.get("msgid"))));
        }else {
            messageRecord.setSendstatus("failed");
        }
        messageRecord.setSendtime(DateUtils.dateToShortTime(new Date()));
        messageRecord.setUpdatetime(DateUtils.dateToShortTime(new Date()));
        messageRecord.setSendtype(0);
        messageRecord.setErrcode(Integer.parseInt(errcode));
        messageRecord.setErrmsg(String.valueOf(response.get("errmsg")));
        wxMessageRecordDao.addData(messageRecord);
    }

    private HrWxWechatDO getHrwxWechatDOByCompanyId(int companyId) {
        return hrWxWechatDao.getData(new Query.QueryBuilder().where(HrWxWechat.HR_WX_WECHAT.COMPANY_ID.getName(), companyId).buildQuery());
    }

    private HrWxTemplateMessageDO getHrWxTemplateMessageByWechatIdAndSysTemplateId(HrWxWechatDO hrWxWechatDO, int sysTemplateId) throws BIZException {
        HrWxTemplateMessageDO hrWxTemplateMessageDO = wxTemplateMessageDao.getData(new Query.QueryBuilder().where("wechat_id",
                hrWxWechatDO.getId()).and("sys_template_id", sysTemplateId).and("disable","0").buildQuery());
        if(hrWxTemplateMessageDO == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_TEMPLATE_SWITCH_CLOSE);
        }
        return hrWxTemplateMessageDO;
    }

    private String getAwardTemplateUrl(HrWxWechatDO hrWxWechatDO){
        return env.getProperty("message.template.delivery.url").replace("{}", hrWxWechatDO.getAccessToken());
    }

    private Map<String,TemplateBaseVO> createDataMap(TemplateDataValueVO templateDataValueVO) {
        Map<String, TemplateBaseVO> dataMap = new HashMap<>(1 >> 4);
        TemplateBaseVO first = createTplVO(templateDataValueVO.getFirst());
        TemplateBaseVO keyWord1 = createTplVO(templateDataValueVO.getKeyWord1());
        TemplateBaseVO keyWord2 = createTplVO(templateDataValueVO.getKeyWord2());
        TemplateBaseVO keyWord3 = createTplVO(templateDataValueVO.getKeyWord3());
        TemplateBaseVO keyWord4 = createTplVO(templateDataValueVO.getKeyWord4());
        TemplateBaseVO remark = createTplVO(templateDataValueVO.getRemark());
        dataMap.put("first", first);
        dataMap.put("keyword1", keyWord1);
        dataMap.put("keyword2", keyWord2);
        dataMap.put("keyword3", keyWord3);
        dataMap.put("keyword4", keyWord4);
        dataMap.put("remark", remark);
        return dataMap;
    }

    private TemplateBaseVO createTplVO(String value){
        return createTplVO(COLOR, value);
    }

    private TemplateBaseVO createTplVO(String color, String value){
        TemplateBaseVO templateBaseVO = new TemplateBaseVO();
        templateBaseVO.setColor(color);
        templateBaseVO.setValue(value);
        return templateBaseVO;
    }

}
