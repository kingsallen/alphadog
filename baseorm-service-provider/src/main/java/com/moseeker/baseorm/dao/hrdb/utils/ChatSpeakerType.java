package com.moseeker.baseorm.dao.hrdb.utils;

/**
 * 聊天对象的类别，0表示C端用户，1表示HR
 * Created by jack on 09/03/2017.
 */
public enum ChatSpeakerType {
    USER(0), HR(1);

    private int value;
    private ChatSpeakerType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
