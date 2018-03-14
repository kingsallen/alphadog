package com.moseeker.chat.thriftservice;

import com.moseeker.chat.service.ChatService;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.thrift.gen.chat.service.ChatService.Iface;
import com.moseeker.thrift.gen.chat.struct.*;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CURDException;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jack on 08/03/2017.
 */
@Service
public class ChatThriftService implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ChatService chatService;

    @Override
    public HRChatRoomsVO listHRChatRoom(int hrId, int pageNo, int pageSize) throws  TException {
        try {
            return chatService.listHRChatRoom(hrId, pageNo, pageSize);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CURDException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,e.getMessage());
        }
    }

    @Override
    public HRChatRoomsIndexVO listHRChatRoomByIndex(int hrId, String keyword, int userId, int pageSize) throws BIZException, TException {
        try {
            ValidateUtil validateUtil = new ValidateUtil();
            return chatService.listHRChatRoomByIndex(hrId, keyword, userId, pageSize);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CURDException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,e.getMessage());
        }
    }

    @Override
    public UserChatRoomsVO listUserChatRoom(int userId, int pageNo, int pageSize) throws  TException {
        try {
            return chatService.listUserChatRoom(userId, pageNo, pageSize);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CURDException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,e.getMessage());
        }

    }

    @Override
    public ChatsVO listChatLogs(int roomId, int pageNo, int pageSize) throws  TException {
        try {
            return chatService.listChatLogs(roomId, pageNo, pageSize);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CURDException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,e.getMessage());
        }
    }

    @Override
    public int saveChat(ChatVO chat) throws  TException {
        try {
            return chatService.saveChat(chat);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CURDException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,e.getMessage());
        }
    }


    @Override
    public ResultOfSaveRoomVO enterRoom(int userId, int hrId, int positionId, int roomId, boolean is_gamma) throws TException {
        return chatService.enterChatRoom(userId, hrId, positionId, roomId, is_gamma);
    }

    @Override
    public ChatVO getChat(int roomId, byte speaker) throws  TException {
        try {
            return chatService.getChat(roomId, speaker);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CURDException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,e.getMessage());
        }
    }

    @Override
    public void leaveChatRoom(int roomId, byte speaker) throws TException {
        try {
            chatService.leaveChatRoom(roomId, speaker);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CURDException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,e.getMessage());
        }
    }
}
