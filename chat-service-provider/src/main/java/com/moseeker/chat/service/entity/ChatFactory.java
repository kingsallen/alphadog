package com.moseeker.chat.service.entity;

import com.moseeker.common.constants.ChatMsgType;
import com.moseeker.thrift.gen.chat.struct.ChatVO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ChatFactory {

    @Autowired
    List<IOutputChatHandler> chatHandlers;

    @Autowired
    List<IBeforeSaveChatHandler> beforeSaveChatHandlers;

    /**
     * 根据消息类型，找到对应的输出聊天内容处理类
     * 无，则直接返回
     * @param chat
     * @return
     */
    public ChatVO outputHandle(ChatVO chat){

        ChatMsgType msgType = ChatMsgType.toChatMsgType(chat.getMsgType());

        Optional<IOutputChatHandler> optional = chatHandlers.stream().filter(c->c.msgType() == msgType).findFirst();

        if(optional.isPresent()){
            return optional.get().outputHandler(chat);
        }

        return chat;
    }

    /**
     * 根据消息类型，找到对应的在保存聊天内容之前进行操作的处理类
     * 无，则直接返回
     * @param chat
     * @return
     */
    public HrWxHrChatDO beforeSaveHandle(HrWxHrChatDO chat){

        ChatMsgType msgType = ChatMsgType.toChatMsgType(chat.getMsgType());

        Optional<IBeforeSaveChatHandler> optional = beforeSaveChatHandlers.stream().filter(c->c.msgType() == msgType).findFirst();

        if(optional.isPresent()){
            return optional.get().beforeSave(chat);
        }

        return chat;
    }

}
