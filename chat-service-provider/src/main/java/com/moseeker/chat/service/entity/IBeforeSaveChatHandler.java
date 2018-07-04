package com.moseeker.chat.service.entity;

import com.moseeker.thrift.gen.chat.struct.ChatVO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatDO;

public interface IBeforeSaveChatHandler extends IChatMsg {
    /**
     * 根据不同的消息类型，存入数据库的chat结构，对应有不同的处理
     * @param chat 处理前的聊天内容
     * @return 处理后的聊天内容
     */
    HrWxHrChatDO beforeSave(HrWxHrChatDO chat);
}
