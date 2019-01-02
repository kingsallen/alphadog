package com.moseeker.common.constants;

import com.moseeker.thrift.gen.chat.struct.ChatVO;
import org.apache.commons.lang.StringUtils;

/**
 *
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
    TELLREFERRAL("tellReferral");

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
