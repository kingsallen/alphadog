package com.moseeker.common.constants;

import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.chat.struct.ChatVO;

public enum ChatMsgType {
    HTML("html") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && !StringUtils.isNullOrEmpty(chatVO.getContent());
        }
    },
    QRCODE("qrcode") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && !StringUtils.isNullOrEmpty(chatVO.getAssetUrl());
        }
    },
    IMAGE("image") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && !StringUtils.isNullOrEmpty(chatVO.getAssetUrl());
        }
    },
    BUTTON_RADIO("button_radio") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && !StringUtils.isNullOrEmpty(chatVO.getBtnContent());
        }
    },
    VOICE("voice") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && !StringUtils.isNullOrEmpty(chatVO.getMsgType()) && "voice".equals(chatVO.getMsgType());
        }
    },
    JOB("job") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            String content = chatVO.getContent();
            return chatVO != null && !StringUtils.isNullOrEmpty(content) && content.trim().startsWith("{") && content.trim().endsWith("}");
        }
    }
    ;

    ChatMsgType(String value) {
        this.value = value;
    }

    private String value;

    public String value(){
        return value;
    }

    /**
     * 根据消息类型，校验需要入库的聊天内容
     * @param chatVO 聊天内容
     * @return true：校验通过，false：校验不通过
     */
    public abstract boolean vaildChat(ChatVO chatVO);

    public static ChatMsgType toChatMsgType(String msgType){
        for(ChatMsgType chatMsgType : values()){
            if(chatMsgType.value.equals(msgType)){
                return chatMsgType;
            }
        }
        return null;
    }

}
