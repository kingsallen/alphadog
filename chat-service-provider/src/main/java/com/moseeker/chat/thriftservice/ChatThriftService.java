package com.moseeker.chat.thriftservice;

import com.alibaba.fastjson.JSON;
import com.moseeker.chat.service.ChatService;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.thrift.gen.chat.service.ChatService.Iface;
import com.moseeker.thrift.gen.chat.struct.*;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CURDException;
import com.moseeker.thrift.gen.common.struct.Response;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public HRChatRoomsIndexVO listHRChatRoomByIndex(int hrId, String keyword, int roomId, boolean apply, int pageSize) throws BIZException, TException {
        try {
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addIntTypeValidate("HR", hrId, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredStringValidate("关键词", keyword, null, null);
            validateUtil.addStringLengthValidate("关键词", keyword, null, null, 0, 100);
            validateUtil.addIntTypeValidate("聊天室", roomId, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addIntTypeValidate("每页数量", pageSize, null, null, 0, 1000);
            String result = validateUtil.validate();
            if (StringUtils.isNotBlank(result)) {
                ExceptionUtils.convertException(CommonException.validateFailed(result));
            }
            return chatService.listHRChatRoomByIndex(hrId, keyword, roomId, apply, pageSize);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public UserChatRoomsVO listUserChatRoom(int userId, int pageNo, int pageSize) throws  TException {
        try {
            return chatService.listUserChatRoom(userId, pageNo, pageSize);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ExceptionUtils.convertException(e);
        }

    }

    @Override
    public ChatsVO listChatLogs(int roomId, int pageNo, int pageSize) throws  TException {
        try {
            return chatService.listChatLogs(roomId, pageNo, pageSize);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public int saveChat(ChatVO chat) throws  TException {
        try {
            return chatService.saveChat(chat);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(chat) + e.getMessage(), e);
            throw ExceptionUtils.convertException(e);
        }
    }


    // 异常和null处理
    @Override
    public ResultOfSaveRoomVO enterRoom(int userId, int hrId, int positionId, int roomId, boolean is_gamma) throws TException {
        try {
            ResultOfSaveRoomVO roomVO = chatService.enterChatRoom(userId, hrId, positionId, roomId, is_gamma);
            if (roomVO == null) {
                throw ExceptionUtils.convertException(CommonException.validateFailed("进入聊天室失败!"));
            }
            return roomVO;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CURDException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,e.getMessage());
        }
    }

    @Override
    public ChatVO getChat(int roomId, byte speaker) throws  TException {
        try {
            return chatService.getChat(roomId, speaker);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public List<ChatVO> listLastMessage(List<Integer> roomIdList) throws BIZException, TException {

        try {
            return chatService.listLastMessage(roomIdList);
        } catch (CommonException e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public ChatHistory listMessage(int roomId, int chatId, int pageSize) throws BIZException, TException {

        if (roomId <= 0) {
            throw ExceptionUtils.convertException(CommonException.validateFailed("参数不正确!"));
        }

        try {
            return chatService.listMessage(roomId, chatId, pageSize);
        } catch (CommonException e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public HRChatRoomVO getChatRoom(int roomId, int hrId) throws BIZException, TException {
        try {
            return chatService.getChatRoom(roomId, hrId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public List<String> getChatSug(int hrId, boolean applied, String keyword) throws BIZException, TException {
        try {
            return chatService.getChatSug(hrId, applied, keyword);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public int getHRUnreadCount(int hrId) throws BIZException, TException {
        try {
            return chatService.getHRUnreadCount(hrId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public HrVO getHrInfo(int roomId) throws BIZException, TException {
        try {
            return chatService.getHrInfo(roomId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public void updateApplyStatus(int userId, int positionId) throws BIZException, TException {
        try {
            if (userId > 0 && positionId > 0) {
                chatService.updateApplyStatus(userId, positionId);
            }
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public void leaveChatRoom(int roomId, byte speaker) throws TException {
        try {
            chatService.leaveChatRoom(roomId, speaker);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public void roleLeaveChatRoom(int roleId, byte speaker) throws TException {
        chatService.roleLeaveChatRoom(roleId, speaker);
    }

    @Override
    public Response pullVoiceFile(String serverId, int roomId, int userId, int hrId) throws BIZException, TException {
        try {
            return chatService.pullVoiceFile(serverId, roomId, userId, hrId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response clearVoiceLimitFrequency(int companyId) throws BIZException, TException {
        try {
            return chatService.clearVoiceLimitFrequency(companyId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response queryVoiceLimitFrequency(int companyId) throws BIZException, TException {
        try {
            return chatService.queryVoiceLimitFrequency(companyId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response sendWarnEmail(int hrId) throws BIZException, TException {
        try {
            return chatService.sendWarnEmail(hrId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }
}
