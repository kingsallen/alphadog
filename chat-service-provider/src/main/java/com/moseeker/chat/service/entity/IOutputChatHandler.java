package com.moseeker.chat.service.entity;

import com.moseeker.thrift.gen.chat.struct.ChatVO;

public interface IOutputChatHandler extends IChatMsg {
    /**
     * 根据不同的消息类型，提供给前台的chat结构，对应有不同的处理,
     * @param chat 处理前的聊天内容
     * @return 处理后的聊天内容
     */
    ChatVO outputHandler(ChatVO chat);
}
