package com.moseeker.chat.service.entity;

import com.moseeker.common.constants.ChatMsgType;
import com.moseeker.thrift.gen.chat.struct.ChatVO;

public interface IChatHandler {

    /**
     * 根据不同的消息类型，提供给前台的chat结构，所以有不同的处理,
     * @param chat 处理前的聊天内容
     * @return 处理后的聊天内容
     */
    ChatVO outputHandler(ChatVO chat);

    /**
     * 返回聊天消息类型
     * @return 聊天消息类型
     */
    ChatMsgType msgType();
}
