package com.moseeker.chat.service.entity;

import com.moseeker.common.constants.ChatMsgType;
import com.moseeker.thrift.gen.chat.struct.ChatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ChatFactory {

    @Autowired
    List<IChatHandler> chatHandlers;

    public ChatVO outputHandler(ChatVO chat){

        ChatMsgType msgType = ChatMsgType.toChatMsgType(chat.getMsgType());

        Optional<IChatHandler> optional = chatHandlers.stream().filter(c->c.msgType() == msgType).findFirst();

        if(optional.isPresent()){
            return optional.get().outputHandler(chat);
        }

        return chat;
    }

}
