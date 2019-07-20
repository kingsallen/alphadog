package com.moseeker.servicemanager.web.controller.chat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.*;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ChatMsgType;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.chat.service.ChatService;
import com.moseeker.thrift.gen.chat.struct.*;
import com.moseeker.thrift.gen.common.struct.Response;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 14/03/2018.
 */
@Controller
@CounterIface
@RequestMapping("api/v1")
public class ChatController {

    ChatService.Iface chatService = ServiceManager.SERVICE_MANAGER.getService(ChatService.Iface.class);

    private Logger logger = LoggerFactory.getLogger(ChatController.class);

    public PropertyPreFilter setPropertyPreFilter = new SetPropertyPreFilter();
    public ValueFilter contentValueFilter = new ContentValueFilter();

    private SerializeConfig serializeConfig = new SerializeConfig(); // 生产环境中，parserConfig要做singleton处理，要不然会存在性能问题

    public ChatController(){
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    @RequestMapping(value = "/chat-rooms/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getChatRooms(HttpServletRequest request, @PathVariable int id) {
        try {

            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer hrId = params.getInt("hr_id");

            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addIntTypeValidate("HR", hrId, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addIntTypeValidate("聊天室", id, null, null, 1, Integer.MAX_VALUE);

            String message = validateUtil.validate();

            if (StringUtils.isBlank(message)) {
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

    @RequestMapping(value = "/chat-rooms", method = RequestMethod.GET)
    @ResponseBody
    public String getChatRooms(HttpServletRequest request, HttpServletResponse response) {
        try {

            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer hrId = params.getInt("hr_id");
            Integer roomId = params.getInt("room_id");
            String keyword = params.getString("keyword");
            Boolean apply = params.getBoolean("apply");
            Integer pageSize = params.getInt("page_size");

            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addIntTypeValidate("HR", hrId, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addStringLengthValidate("关键词", keyword, null, null, 0, 100);
            validateUtil.addIntTypeValidate("每页数量", pageSize, null, null, 0, 1000);

            String message = validateUtil.validate();

            if (StringUtils.isBlank(message)) {
                if (roomId == null) {
                    roomId = 0;
                }
                if (apply == null) {
                    apply = true;
                }
                if (pageSize == null || pageSize == 0) {
                    pageSize = 10;
                }
                HRChatRoomsIndexVO result = chatService.listHRChatRoomByIndex(hrId, keyword, roomId, apply, pageSize);
                return ResponseLogNotification.successJson(request, result);
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
            List<String> roomIdStrList = new ArrayList<>();
            if (params.get("room_ids") instanceof String) {
                roomIdStrList.add(params.getString("room_ids"));
            } else {
                roomIdStrList = (List<String>) params.get("room_ids");
            }
            List<Integer> roomIdList = new ArrayList<>();
            if (roomIdStrList != null && roomIdStrList.size() > 0) {
                roomIdStrList.forEach(idStr -> roomIdList.add(Integer.valueOf(idStr)));
            }

            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addRequiredOneValidate("聊天室编号", roomIdList, null, null);

            String message = validateUtil.validate();

            if (StringUtils.isBlank(message)) {
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
    public String getRoomLastMessages(HttpServletRequest request, @PathVariable int id) {
        try {
            if (id > 0) {
                List<ChatVO> chatVOList = chatService.listLastMessage(new ArrayList<Integer>() {{
                    this.add(id);
                }});
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
            validateUtil.addIntTypeValidate("聊天记录", lastReadId, null, null, 0, Integer.MAX_VALUE);

            String message = validateUtil.validate();

            if (StringUtils.isBlank(message)) {
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

            if (StringUtils.isBlank(message)) {
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

            if (StringUtils.isBlank(message)) {
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
    public String getChatRoom(HttpServletRequest request, @PathVariable int id) {
        try {

            ParamUtils.parseRequestParam(request);

            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addIntTypeValidate("聊天室", id, null, null, 1, Integer.MAX_VALUE);

            String message = validateUtil.validate();

            if (StringUtils.isBlank(message)) {
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

    @RequestMapping(value = "/chat-room/{id}/messages", method = RequestMethod.POST)
    @ResponseBody
    public String postChatMessage(HttpServletRequest request, @PathVariable int id) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);

            String content = params.getString("content");
            int roomId = params.getInt("roomId");
            Integer speaker = params.getInt("speaker");
            Integer origin = params.getInt("origin");
            String msgType = params.getString("msgType");
            String picURL = params.getString("assetUrl");
            String btnContent = params.getString("btnContent");
            Integer positionId = params.getInt("positionId");
            Integer duration = params.getInt("duration");
            String serverId = params.getString("serverId");

            logger.info("params:{}", params);

            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addIntTypeValidate("聊天室", id, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredStringValidate("聊天内容", content, null, null);
            validateUtil.addStringLengthValidate("聊天内容", content, null, null, 1, 1000);
            validateUtil.addRequiredValidate("聊天者角色", speaker, null, null);
            validateUtil.addRequiredValidate("来源", origin, null, null);
            validateUtil.addIntTypeValidate("来源", origin, null, null, 0, 3);
            validateUtil.addStringLengthValidate("消息类型", msgType, null, null, 0, 50);
            validateUtil.addStringLengthValidate("图片URL", picURL, null, null, 0, 256);
            validateUtil.addStringLengthValidate("空间类信息", btnContent, null, null, 0, 1000);
            validateUtil.addIntTypeValidate("音频时长", duration, null, null, 0, 60);
            validateUtil.addStringLengthValidate("语音文件", serverId, null, null, 0, 256);
            validateUtil.addIntTypeValidate("职位id", positionId, null, null, 1, Integer.MAX_VALUE);
            String message = validateUtil.validate();

            if (StringUtils.isBlank(message)) {
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
                    if (msgType.equals(ChatMsgType.VOICE.value())) {
                        if (StringUtils.isBlank(serverId)) {
                            return ResponseLogNotification.failJson(request, "语音信息为空");
                        }
                        chatVO.setDuration(duration.byteValue());
                        chatVO.setServerId(serverId);
                    }
                }
                if (picURL != null) {
                    chatVO.setAssetUrl(picURL);
                }
                if (btnContent != null) {
                    chatVO.setBtnContent(btnContent);
                }
                logger.info("chatVo:{}", chatVO);
                return ResponseLogNotification.successJson(request, chatService.saveChat(chatVO));
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/chat-room/leave-room", method = RequestMethod.POST)
    @ResponseBody
    public String hrLeaveRoom(HttpServletRequest request) {
        try {

            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer speaker = params.getInt("speaker");
            Integer id = params.getInt("id");
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addRequiredValidate("用户", id, null, null);
            validateUtil.addIntTypeValidate("用户", id, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredValidate("角色", speaker, null, null);

            String message = validateUtil.validate();

            if (StringUtils.isBlank(message)) {
                chatService.roleLeaveChatRoom(id, speaker.byteValue());
                return ResponseLogNotification.successJson(request, "success");
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/chat-room/{id}/leave-room", method = RequestMethod.POST)
    @ResponseBody
    public String hrLeaveRoom(HttpServletRequest request, @PathVariable int id) {
        try {

            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer speaker = params.getInt("speaker");
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addRequiredValidate("聊天室", id, null, null);
            validateUtil.addRequiredValidate("角色", speaker, null, null);

            String message = validateUtil.validate();

            if (StringUtils.isBlank(message)) {
                chatService.leaveChatRoom(id, speaker.byteValue());
                return ResponseLogNotification.successJson(request, "success");
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/chat/voice/pullVoiceFile", method = RequestMethod.GET)
    @ResponseBody
    public String pullVoiceFile(HttpServletRequest request, HttpServletResponse response) {
        ByteArrayOutputStream bos = null;
        FileInputStream in = null;
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String serverId = params.getString("serverId");
            int hrId = params.getInt("hrId");
            int userId = params.getInt("userId");
            int roomId = params.getInt("roomId");
            logger.info("=========serverId:{},hrId:{},userId:{},roomId:{}===============", serverId, hrId, userId, roomId);
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addRequiredValidate("素材id", serverId, null, null);
            validateUtil.addStringLengthValidate("素材id", serverId, null, null, 1, 256);
            validateUtil.addIntTypeValidate("聊天室id", roomId, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addIntTypeValidate("用户id", userId, null, null, 0, Integer.MAX_VALUE);
            validateUtil.addIntTypeValidate("hrid", hrId, null, null, 0, Integer.MAX_VALUE);

            String message = validateUtil.validate();
            logger.info("=========message:{}=============", message);
            if (StringUtils.isBlank(message)) {
                Response urlResponse = chatService.pullVoiceFile(serverId, roomId, userId, hrId);
                Integer status = urlResponse.getStatus();
                String voiceLocalUrl = null;
                if (0 == status) {
                    voiceLocalUrl = JSONObject.parseObject(urlResponse.getData()).getString("voiceLocalUrl");
                    logger.info("=========voiceLocalUrl:{}================", voiceLocalUrl);
                    File file = new File(voiceLocalUrl);
                    if(!file.exists()){
                        return ResponseLogNotification.failJson(request, "文件不存在");
                    }
                    in = new FileInputStream(file);
                    long fileLength = file.length();
                    if (fileLength > Integer.MAX_VALUE) {
                        return ResponseLogNotification.failJson(request, "文件过大");
                    }
                    bos = new ByteArrayOutputStream((int) file.length());
                    byte[] bytes = new byte[8192];
                    int len = 0;
                    while ((len = in.read(bytes)) != -1) {
                        bos.write(bytes, 0, len);
                    }
                    bos.flush();
                    byte[] returnByte = bos.toByteArray();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("fileBytes", returnByte);
                    jsonObject.put("fileLength", fileLength);
                    logger.info("=========jsonObject:{}===============================", jsonObject);
                    return ResponseLogNotification.successJson(request, jsonObject);
                }
                return ResponseLogNotification.failJson(request, urlResponse.getMessage());
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("================拉取出错==================");
            return ResponseLogNotification.failJson(request, e);
        } finally {
            if(null != bos){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != in){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping(value = "/chat/voice/clearVoiceLimitFrequency", method = RequestMethod.POST)
    @ResponseBody
    public String clearVoiceLimitFrequency(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer companyId = params.getInt("companyId");
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addRequiredValidate("公司id", companyId, null, null);
            validateUtil.addIntTypeValidate("公司id", companyId, null, null, 1, Integer.MAX_VALUE);
            String message = validateUtil.validate();

            if (StringUtils.isBlank(message)) {
                return ResponseLogNotification.success(request, chatService.clearVoiceLimitFrequency(companyId));
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/chat/voice/queryVoiceLimitFrequency", method = RequestMethod.GET)
    @ResponseBody
    public String queryVoiceLimitFrequency(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer companyId = params.getInt("companyId");
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addRequiredValidate("公司id", companyId, null, null);
            validateUtil.addIntTypeValidate("公司id", companyId, null, null, 1, Integer.MAX_VALUE);
            String message = validateUtil.validate();
            if (StringUtils.isBlank(message)) {
                return ResponseLogNotification.success(request, chatService.queryVoiceLimitFrequency(companyId));
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/chat/voice/sendWarnEmail", method = RequestMethod.GET)
    @ResponseBody
    public String sendWarnEmail(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer hrId = params.getInt("hrId");
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addRequiredValidate("hrId", hrId, null, null);
            validateUtil.addIntTypeValidate("hrId", hrId, null, null, 1, Integer.MAX_VALUE);
            String message = validateUtil.validate();
            if (StringUtils.isBlank(message)) {
                return ResponseLogNotification.success(request, chatService.sendWarnEmail(hrId));
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/chat/listChatLogs", method = RequestMethod.GET)
    @ResponseBody
    public String listChatLogs(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer roomId = params.getInt("room_id");
            Integer pageNo = params.getInt("page_no");
            Integer pageSize = params.getInt("page_size");

            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addRequiredValidate("roomId", roomId, null, null);
            validateUtil.addRequiredValidate("pageNo", pageNo, null, null);
            validateUtil.addRequiredValidate("pageSize", pageSize, null, null);
            String message = validateUtil.validate();
            if (StringUtils.isBlank(message)) {
                ChatsVO result = chatService.listChatLogs(roomId,pageNo,pageSize);
                SerializeFilter[] filters = {setPropertyPreFilter,contentValueFilter};
                Response response = ResponseUtils.successWithoutStringify(JSON.toJSONString(result,serializeConfig,filters));
                return ResponseLogNotification.success(request, response);
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }

    private class SetPropertyPreFilter implements PropertyPreFilter {

        @Override
        public boolean apply(JSONSerializer serializer, Object object, String name) {
            if(name.startsWith("set")){
                return false;
            }
            return true;
        }
    }

    private class ContentValueFilter implements ValueFilter {
        @Override
        public Object process(Object object, String name, Object value) {
            if(object instanceof ChatVO
                    && ChatVO._Fields.CONTENT.getFieldName().equals(name)
                    && value instanceof String){
                ChatVO chat = (ChatVO)object;
                if(chat.getMsgType().equals(ChatMsgType.JOB.value())){
                    return JSON.parseObject(chat.getCompoundContent());
                }
            }
            return value;
        }
    }
}
