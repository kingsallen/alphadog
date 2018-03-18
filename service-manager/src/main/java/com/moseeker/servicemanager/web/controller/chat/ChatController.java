package com.moseeker.servicemanager.web.controller.chat;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.common.validation.rules.DateType;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.chat.service.ChatService;
import com.moseeker.thrift.gen.chat.struct.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 14/03/2018.
 */
@Controller
@CounterIface
@RequestMapping("api/v1")
public class ChatController {

    ChatService.Iface chatService = ServiceManager.SERVICEMANAGER.getService(ChatService.Iface.class);

    @RequestMapping(value = "/chat-room", method = RequestMethod.GET)
    @ResponseBody
    public String getChatRooms(HttpServletRequest request, HttpServletResponse response) {
        try {

            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer hrId = params.getInt("hr_id");
            Integer userId = params.getInt("user_id");
            String keyword = params.getString("keyword");
            Boolean apply = params.getBoolean("apply");
            Integer pageSize = params.getInt("page_size");

            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addIntTypeValidate("HR", hrId, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredStringValidate("关键词", keyword, null, null);
            validateUtil.addStringLengthValidate("关键词", keyword, null, null, 0, 100);
            validateUtil.addIntTypeValidate("用户", userId, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addIntTypeValidate("每页数量", pageSize, null, null, 0, 1000);

            String message = validateUtil.validate();

            if (StringUtils.isNotBlank(message)) {
                if (apply == null) {
                    apply = true;
                }
                if (pageSize == null || pageSize == 0) {
                    pageSize = 10;
                }
                HRChatRoomsIndexVO result = chatService.listHRChatRoomByIndex(hrId, keyword, userId, apply, pageSize);
                return ResponseLogNotification.successJson(request, result);
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/chat-room/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getChatRooms(HttpServletRequest request, @RequestParam int id) {
        try {

            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer hrId = params.getInt("hr_id");

            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addIntTypeValidate("HR", hrId, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addIntTypeValidate("聊天室", id, null, null, 1, Integer.MAX_VALUE);

            String message = validateUtil.validate();

            if (StringUtils.isNotBlank(message)) {
                HRChatRoomVO roomVO = chatService.getChatRoom(id, hrId);
                return ResponseLogNotification.successJson(request, roomVO);
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/chat-room/last-messages", method = RequestMethod.GET)
    @ResponseBody
    public String getRoomLastMessages(HttpServletRequest request, HttpServletResponse response) {
        try {

            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            List<Integer> roomIdList = (List<Integer>) params.get("room_ids");

            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addRequiredOneValidate("聊天室编号", roomIdList, null, null);

            String message = validateUtil.validate();

            if (StringUtils.isNotBlank(message)) {
                List<ChatVO> chatVOList = chatService.listLastMessage(roomIdList);
                return ResponseLogNotification.successJson(request, chatVOList);
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/chat-room/{id}/last-messages", method = RequestMethod.GET)
    @ResponseBody
    public String getRoomLastMessages(HttpServletRequest request, @RequestParam int id) {
        try {
            if (id > 0) {
                List<ChatVO> chatVOList = chatService.listLastMessage(new ArrayList<Integer>(){{this.add(id);}});
                ChatVO chatVO = null;
                if (chatVOList != null && chatVOList.size() > 0) {
                    chatVO = chatVOList.get(0);
                }
                return ResponseLogNotification.successJson(request, chatVO);
            } else {
                return ResponseLogNotification.fail(request, "参数错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/chat-room/{id}/messages", method = RequestMethod.GET)
    @ResponseBody
    public String getChatMessages(HttpServletRequest request, @PathVariable int id) {
        try {

            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int lastReadId = params.getInt("last_read_id");
            int pageSize = params.getInt("page_size");

            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addIntTypeValidate("聊天室编号", id, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addIntTypeValidate("聊天记录", lastReadId, null, null, 1, Integer.MAX_VALUE);

            String message = validateUtil.validate();

            if (StringUtils.isNotBlank(message)) {
                ChatHistory chatHistory = chatService.listMessage(id, lastReadId, pageSize);
                return ResponseLogNotification.successJson(request, chatHistory);
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/chat-sug", method = RequestMethod.GET)
    @ResponseBody
    public String getChatSug(HttpServletRequest request) {
        try {

            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String keyword = params.getString("keyword");
            boolean applied = params.getBoolean("applied");
            int hrId = params.getInt("hr_id");

            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addIntTypeValidate("HR 编号", hrId, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredStringValidate("keyword", keyword, null, null);

            String message = validateUtil.validate();

            if (StringUtils.isNotBlank(message)) {
                List<String> sugList = chatService.getChatSug(hrId, applied, keyword);
                return ResponseLogNotification.successJson(request, sugList);
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/unread-count", method = RequestMethod.GET)
    @ResponseBody
    public String getHRUnreadCount(HttpServletRequest request) {
        try {

            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int hrId = params.getInt("hr_id");

            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addIntTypeValidate("HR 编号", hrId, null, null, 1, Integer.MAX_VALUE);

            String message = validateUtil.validate();

            if (StringUtils.isNotBlank(message)) {
                int count = chatService.getHRUnreadCount(hrId);
                return ResponseLogNotification.successJson(request, count);
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/chat-room/{id}/hr-detail", method = RequestMethod.GET)
    @ResponseBody
    public String getChatRoom(HttpServletRequest request, @RequestParam int id) {
        try {

            ParamUtils.parseRequestParam(request);

            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addIntTypeValidate("聊天室", id, null, null, 1, Integer.MAX_VALUE);

            String message = validateUtil.validate();

            if (StringUtils.isNotBlank(message)) {
                HrVO hrVO = chatService.getHrInfo(id);
                return ResponseLogNotification.successJson(request, hrVO);
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/chat-room/%s/messages", method = RequestMethod.POST)
    @ResponseBody
    public String postChatMessage(HttpServletRequest request, @RequestParam int id) {
        try {

            Params<String, Object> params = ParamUtils.parseRequestParam(request);

            String content = params.getString("content");
            int roomId = params.getInt("room_id");
            Integer speaker = params.getInt("speaker");
            Integer origin = params.getInt("origin");
            String msgType = params.getString("msg_type");
            String picURL = params.getString("pic_url");
            String btnContent = params.getString("btn_content");
            Integer positionId = params.getInt("position_id");
            String createTime = params.getString("create_time");

            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addIntTypeValidate("聊天室", id, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredStringValidate("聊天内容", content,null, null);
            validateUtil.addStringLengthValidate("聊天内容", content, null, null, 1, 1000);
            validateUtil.addRequiredValidate("聊天者角色", speaker, null, null);
            validateUtil.addRequiredValidate("来源", origin, null, null);
            validateUtil.addIntTypeValidate("来源", origin, null, null, 0, 3);
            validateUtil.addStringLengthValidate("消息类型", msgType, null, null, 0, 50);
            validateUtil.addStringLengthValidate("图片URL", picURL, null, null, 0, 256);
            validateUtil.addStringLengthValidate("空间类信息", btnContent, null, null, 0, 1000);
            validateUtil.addDateValidate("创建时间", createTime, DateType.longDate, null, null);

            String message = validateUtil.validate();

            if (StringUtils.isNotBlank(message)) {

                ChatVO chatVO = new ChatVO();
                chatVO.setOrigin(origin.byteValue());
                chatVO.setRoomId(roomId);
                chatVO.setContent(content);
                chatVO.setSpeaker(speaker.byteValue());
                if (positionId != null) {
                    chatVO.setPositionId(positionId);
                }
                if (msgType != null) {
                    chatVO.setMsgType(msgType);
                }
                if (picURL != null) {
                    chatVO.setPicUrl(picURL);
                }
                if (btnContent != null) {
                    chatVO.setBtnContent(btnContent);
                }
                if (createTime != null) {
                    chatVO.setCreate_time(createTime);
                }
                int chatId = chatService.saveChat(chatVO);
                return ResponseLogNotification.successJson(request, chatId);
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }
}
