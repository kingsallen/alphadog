package com.moseeker.chat.thriftservice;

import com.moseeker.chat.service.ChatService;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.thrift.gen.chat.service.ChatService.Iface;
import com.moseeker.thrift.gen.chat.struct.*;
import com.moseeker.thrift.gen.common.struct.CURDException;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jack on 08/03/2017.
 */
@Service
public class ChatThriftService implements Iface {

    @Autowired
    ChatService chatService;

    @Override
    public HRChatRoomsVO listHRChatRoom(int hrId, int pageNo, int pageSize) throws CURDException, TException {
        try {
            return chatService.listHRChatRoom(hrId, pageNo, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CURDException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,e.getMessage());
        }
    }

    @Override
    public UserChatRoomsVO listUserChatRoom(int userId, int pageNo, int pageSize) throws CURDException, TException {
        return chatService.listUserChatRoom(userId, pageNo, pageSize);
    }

    @Override
    public ChatsVO listChatLogs(int roomId, int pageNo, int pageSize) throws CURDException, TException {
        return chatService.listChatLogs(roomId, pageNo, pageSize);
    }

    @Override
    public void saveChat(int roomId, String content, int positionId, byte speaker) throws CURDException, TException {
        chatService.saveChat(roomId, content, positionId, speaker);
    }

    @Override
    public ResultOfSaveRoomVO enterRoom(int userId, int hrId, int positionId, int roomId) throws CURDException, TException {
        return chatService.enterChatRoom(userId, hrId, positionId, roomId);
    }

    @Override
    public ChatVO getChat(int roomId, byte speaker) throws CURDException, TException {
        return chatService.getChat(roomId, speaker);
    }

    @Override
    public void leaveChatRoom(int roomId, byte speaker) throws TException {
        chatService.leaveChatRoom(roomId, speaker);
    }
}
