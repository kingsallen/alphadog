package com.moseeker.baseorm.dao.logdb;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.logdb.tables.LogWxMessageRecord;
import com.moseeker.baseorm.db.logdb.tables.records.LogWxMessageRecordRecord;
import com.moseeker.common.util.DateUtils;
import com.moseeker.thrift.gen.dao.struct.logdb.LogWxMessageRecordDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

/**
* @author xxx
* LogWxMessageRecordDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class LogWxMessageRecordDao extends JooqCrudImpl<LogWxMessageRecordDO, LogWxMessageRecordRecord> {

    public LogWxMessageRecordDao() {
        super(LogWxMessageRecord.LOG_WX_MESSAGE_RECORD, LogWxMessageRecordDO.class);
    }


    public LogWxMessageRecordDao(TableImpl<LogWxMessageRecordRecord> table, Class<LogWxMessageRecordDO> logWxMessageRecordDOClass) {
        super(table, logWxMessageRecordDOClass);
    }

    /**
     * todo 商城服务中有相同的代码，可以删除使用这个方法
     * @param templateId 模板消息id
     * @param wechatId 公众号id
     * @param templateValueMap 模板消息内容
     * @author  cjm
     * @date  2018/12/11
     */
    public LogWxMessageRecordDO insertLogWxMessageRecord(int templateId, int wechatId, Map<String, Object> templateValueMap) {
        LogWxMessageRecordDO messageRecord = new LogWxMessageRecordDO();
//        messageRecord.setTemplateId(templateId);
        messageRecord.setTemplateId(Integer.parseInt(String.valueOf(templateValueMap.get("template_id"))));
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
        messageRecord = addData(messageRecord);
        return messageRecord;
    }
}
