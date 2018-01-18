package com.moseeker.chat.service.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.chat.base.ChatParser;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.chat.struct.ChatVO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatDO;

public abstract class AbstractChatParser implements ChatParser{

    public abstract String fieldName();

    @Override
    public HrWxHrChatDO parseToDO(HrWxHrChatDO chatDO, JSONObject vo) {
        if(chatDO==null || vo==null){
            return null;
        }

        Object field=vo.get(fieldName());

        if(field==null){
            return chatDO;
        }

        if(chatDO.getContent()==null){
            chatDO.setContent("");
        }

        String content=chatDO.getContent();

        JSONObject jsonContent=JSONObject.parseObject(content);

        if(jsonContent==null){
            return chatDO;
        }

        jsonContent.put(fieldName(),field);

        chatDO.setContent(jsonContent.toJSONString());
        return chatDO;
    }

    @Override
    public JSONObject parseToVO(JSONObject vo, HrWxHrChatDO chatDO) {
        if(chatDO==null || vo==null){
            return null;
        }

        String content=chatDO.getContent();

        if(StringUtils.isNullOrEmpty(content)){
            return vo;
        }

        JSONObject jsonContent= JSON.parseObject(content);

        if(jsonContent==null){
            return vo;
        }


        Object field=jsonContent.get(fieldName());

        if(field==null){
            return vo;
        }else{
            vo.put(fieldName(),field);
        }



        return vo;
    }
}
