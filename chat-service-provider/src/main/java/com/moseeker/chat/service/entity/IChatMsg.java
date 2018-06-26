package com.moseeker.chat.service.entity;

import com.moseeker.common.constants.ChatMsgType;

public interface IChatMsg {
    /**
     * 返回聊天消息类型
     * @return 聊天消息类型
     */
    ChatMsgType msgType();
}
