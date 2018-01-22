package com.moseeker.position.service.position.base.sync;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.logdb.LogWxMessageRecordDao;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.logdb.LogWxMessageRecordDO;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * 职位同步验证工具类
 */
@Component
public class PositionSyncVerifyHandlerUtil {
    @Autowired
    private Environment env;
    @Autowired
    private LogWxMessageRecordDao wxMessageRecordDao;
    @Autowired
    private HrWxWechatDao wxWechatDao;

    public String deliveryUrl(){
        String url="";
        url=env.getProperty("message.template.sync.code.url");
        return url;

    }


    public String getCodeLink(Map<String,Object> param){
        StringBuilder link=new StringBuilder(env.getProperty("sync.code.url"));

        if(param!=null && !param.isEmpty()){
            link.append("?");
        }

        for(Map.Entry<String,Object> entry:param.entrySet()){
            link.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        link.deleteCharAt(link.lastIndexOf("&"));

        return link.toString();
    }

    public HrWxWechatDO getMoseekerWxWechat(){
        String signature=env.getProperty("wechat.helper.signature");
        Query query=new Query.QueryBuilder()
                .where(HrWxWechat.HR_WX_WECHAT.SIGNATURE.getName(),signature)
                .buildQuery();

        return wxWechatDao.getData(query);
    }

    /**
     * 插入模板消息发送结果日志
     * @param hrWxWechatDO
     * @param template
     * @param openId
     * @param link
     * @param colMap
     * @param result
     * @return
     */
    public int insertLogWxMessageRecord(HrWxWechatDO hrWxWechatDO, HrWxTemplateMessageDO template,
                                         String openId, String link, Map<String, MessageTplDataCol> colMap, Map<String, Object> result){
        LogWxMessageRecordDO messageRecord = new LogWxMessageRecordDO();
        messageRecord.setTemplateId(template.getId());
        if(hrWxWechatDO != null) {
            messageRecord.setWechatId(hrWxWechatDO.getId());
        }
        if(result != null && result.get("msgid") != null) {
            messageRecord.setMsgid((long)result.get("msgid"));
        }else{
            messageRecord.setMsgid(0);
        }
        messageRecord.setOpenId(openId);
        messageRecord.setUrl(link);
        messageRecord.setTopcolor(template.getTopcolor());
        messageRecord.setJsondata(JSON.toJSONString(colMap));
        if(result != null && result.get("errcode") != null) {
            messageRecord.setErrcode((int) result.get("errcode"));
        }else{
            messageRecord.setErrcode(-3);
        }
        if(result != null && result.get("errmsg") != null) {
            messageRecord.setErrmsg((String)result.get("errmsg"));
        }else{
            messageRecord.setErrmsg("");
        }
        messageRecord.setSendtime(DateUtils.dateToShortTime(new Date()));
        messageRecord.setUpdatetime(DateUtils.dateToShortTime(new Date()));
        messageRecord.setSendtype(0);
        if(hrWxWechatDO != null) {
            messageRecord.setAccessToken(hrWxWechatDO.getAccessToken());
        }
        return wxMessageRecordDao.addData(messageRecord).getId();
    }
}
