package com.moseeker.chat.constant;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 聊天基础产生来源 0 人工输入 1 系统生成 2 AI生成
 *
 */
public enum ChatOrigin {
    Human((byte)0, "人工输入"), System((byte)1, "系统生成"), AI((byte)2, "AI生成");

    private byte value;
    private String name;

    private static Map<Byte, ChatOrigin> storage = new HashMap<>();

    static {
        for (ChatOrigin chatOrigin : values()) {
            storage.put(chatOrigin.getValue(), chatOrigin);
        }
    }

    private ChatOrigin(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Optional<ChatOrigin> instanceFromValue(byte value) {
        return Optional.ofNullable(storage.get(value));
    }

    public byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
