package com.moseeker.common.constants;

public enum ChatMsgType {
    HTML("html"),
    QRCODE("qrcode"),
    IMAGE("image"),
    BUTTON_RADIO("button_radio")
    ;

    ChatMsgType(String value) {
        this.value = value;
    }

    private String value;

    public String value(){
        return value;
    }


}
