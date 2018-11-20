package com.moseeker.common.constants;

import com.moseeker.thrift.gen.chat.struct.ChatVO;
import org.apache.commons.lang.StringUtils;

/**
 *
 */
public enum ChatMsgType {
    HTML("html") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && StringUtils.isNotBlank(chatVO.getContent());
        }
    },
    QRCODE("qrcode") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && StringUtils.isNotBlank(chatVO.getAssetUrl());
        }
    },
    IMAGE("image") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && StringUtils.isNotBlank(chatVO.getAssetUrl());
        }
    },
    BUTTON_RADIO("button_radio") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && StringUtils.isNotBlank(chatVO.getCompoundContent());
        }
    },
    VOICE("voice") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && StringUtils.isNotBlank(chatVO.getMsgType()) && "voice".equals(chatVO.getMsgType());
        }
    },
    JOB("job") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && StringUtils.isNotBlank(chatVO.getCompoundContent())
                    && chatVO.getCompoundContent().trim().startsWith("{")
                    && chatVO.getCompoundContent().endsWith("}");
        }
    },
    JOBCARD("jobCard") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && StringUtils.isNotBlank(chatVO.getCompoundContent());
        }
    },
    cards("cards") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && StringUtils.isNotBlank(chatVO.getCompoundContent());
        }
    },
    CITYSELECT("citySelect") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && StringUtils.isNotBlank(chatVO.getCompoundContent());
        }
    },
    TEAMSELECT("teamSelect") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && StringUtils.isNotBlank(chatVO.getCompoundContent());
        }
    },
    REDIRECT("redirect") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && StringUtils.isNotBlank(chatVO.getCompoundContent());
        }
    },
    JOBSELECT("jobSelect") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && StringUtils.isNotBlank(chatVO.getCompoundContent());
        }
    },
    EMPLOYEEBIND("employeeBind") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && StringUtils.isNotBlank(chatVO.getCompoundContent());
        }
    },
    POSITIONSELECT("positionSelect") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && StringUtils.isNotBlank(chatVO.getCompoundContent());
        }
    },
    INDUSTRYSELECT("industrySelect") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && StringUtils.isNotBlank(chatVO.getCompoundContent());
        }
    },
    TEXTPLACEHOLDER("textPlaceholder") {
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && StringUtils.isNotBlank(chatVO.getCompoundContent());
        }
    },
    SATISFACTION("satisfaction"){
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && StringUtils.isNotBlank(chatVO.getCompoundContent());
        }
    },
    UPLOADRESUME("uploadResume"){
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && StringUtils.isNotBlank(chatVO.getCompoundContent());
        }
    },
    TELLREFERRAL("tellReferral"){
        @Override
        public boolean vaildChat(ChatVO chatVO) {
            return chatVO != null && StringUtils.isNotBlank(chatVO.getCompoundContent());
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
