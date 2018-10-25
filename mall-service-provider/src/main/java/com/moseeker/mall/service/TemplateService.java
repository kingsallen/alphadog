package com.moseeker.mall.service;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HrWxTemplateMessageDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.query.Query;
import com.moseeker.mall.vo.TemplateBaseVO;
import com.moseeker.mall.vo.TemplateDataValueVO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
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

    @Autowired
    private HrWxWechatDao hrWxWechatDao;

    @Autowired
    private HrWxTemplateMessageDao wxTemplateMessageDao;

    @Autowired
    private Environment env;
    /**
     * 发送积分变动消息模板
     * @param templateDataValueVO 积分提醒消息模板values
     * @param openId 微信用户openid
     * @author  cjm
     * @date  2018/10/24
     * @return 微信返回的模板消息发送发送结果
     */
    public Map<String, TemplateBaseVO> sendCreditUpdateTemplate(TemplateDataValueVO templateDataValueVO, String openId, String url) throws ConnectException {
        //仟寻招聘助手（复制的老代码）
        HrWxWechatDO hrWxWechatDO = getHrwxWechatDOBySignature();
        HrWxTemplateMessageDO hrWxTemplateMessageDO = getHrWxTemplateMessageByWechatIdAndSysTemplateId(hrWxWechatDO);
        String requestUrl = getAwardTemplateUrl(hrWxWechatDO);
        Map<String, Object> requestMap = new HashMap<>(1 >> 4);
        Map<String, TemplateBaseVO> dataMap = createDataMap(templateDataValueVO);
        requestMap.put("data", dataMap);
        requestMap.put("touser", openId);
        requestMap.put("template_id", hrWxTemplateMessageDO.getWxTemplateId());
        requestMap.put("url", url);
        requestMap.put("topcolor", hrWxTemplateMessageDO.getTopcolor());
        String result = HttpClient.sendPost(requestUrl, JSON.toJSONString(requestMap));
        Map<String, Object> params = JSON.parseObject(result);
        if("0".equals(params.get("errcode"))){
            return dataMap;
        }
        return null;
    }

    private HrWxWechatDO getHrwxWechatDOBySignature() {
        return hrWxWechatDao.getData(new Query.QueryBuilder().where(HrWxWechat.HR_WX_WECHAT.SIGNATURE.getName(), getSignature()).buildQuery());
    }

    private HrWxTemplateMessageDO getHrWxTemplateMessageByWechatIdAndSysTemplateId(HrWxWechatDO hrWxWechatDO) {
        return  wxTemplateMessageDao.getData(new Query.QueryBuilder().where("wechat_id",
                hrWxWechatDO.getId()).and("sys_template_id", Constant.TEMPLATES_NEW_RESUME_TPL).and("disable","0").buildQuery());
    }

    private String getSignature(){
        return env.getProperty("wechat.helper.signature");
    }

    private String getAwardTemplateUrl(HrWxWechatDO hrWxWechatDO){
        return env.getProperty("message.template.delivery.url").replace("{}", hrWxWechatDO.getAccessToken());
    }

    private Map<String,TemplateBaseVO> createDataMap(TemplateDataValueVO templateDataValueVO) {
        Map<String, TemplateBaseVO> dataMap = new HashMap<>(1 >> 4);
        TemplateBaseVO first = createTplVO(templateDataValueVO.getFirst());
        TemplateBaseVO account = createTplVO(templateDataValueVO.getAccount());
        TemplateBaseVO time = createTplVO(templateDataValueVO.getTime());
        TemplateBaseVO type = createTplVO(templateDataValueVO.getType());
        TemplateBaseVO creditChange = createTplVO(templateDataValueVO.getCreditChange());
        TemplateBaseVO creditName = createTplVO(templateDataValueVO.getCreditName());
        TemplateBaseVO remark = createTplVO(templateDataValueVO.getRemark());
        TemplateBaseVO number = createTplVO(templateDataValueVO.getNumber());
        TemplateBaseVO amount = createTplVO(templateDataValueVO.getAmount());
        dataMap.put("first", first);
        dataMap.put("account", account);
        dataMap.put("time", time);
        dataMap.put("type", type);
        dataMap.put("creditChange", creditChange);
        dataMap.put("creditName", creditName);
        dataMap.put("remark", remark);
        dataMap.put("number", number);
        dataMap.put("amount", amount);
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
