package com.moseeker.common.constants;

/**
 * 聊天消息类型
 */
public enum ChatMsgType {
    HTML("html"),
    QRCODE("qrcode"),
    IMAGE("image"),
    BUTTON_RADIO("button_radio"),
    VOICE("voice"),
    JOB("job"),
    JOBCARD("jobCard"),
    cards("cards"),
    CITYSELECT("citySelect"),
    TEAMSELECT("teamSelect"),
    REDIRECT("redirect"),
    JOBSELECT("jobSelect"),
    EMPLOYEEBIND("employeeBind"),
    POSITIONSELECT("positionSelect"),
    INDUSTRYSELECT("industrySelect"),
    TEXTPLACEHOLDER("textPlaceholder"),
    SATISFACTION("satisfaction"),
    UPLOADRESUME("uploadResume"),
    TELLREFERRAL("tellReferral"),
    TEXT("text");

    ChatMsgType(String value) {
        this.value = value;
    }

    private String value;

    public String value(){
        return value;
    }

    public static ChatMsgType toChatMsgType(String msgType){
        for(ChatMsgType chatMsgType : values()){
            if(chatMsgType.value.equals(msgType)){
                return chatMsgType;
            }
        }
        return null;
    }

}
