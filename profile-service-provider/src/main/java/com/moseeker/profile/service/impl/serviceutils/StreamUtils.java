package com.moseeker.profile.service.impl.serviceutils;

import org.apache.http.Consts;

import java.nio.ByteBuffer;

/**
 * 二进制操作工具
 * @Author: jack
 * @Date: 2018/9/4
 */
public class StreamUtils {

    /**
     * 将ByteBuffer数据转成String
     * @param byteBuffer 字节数据
     * @return base64转码后的字符串
     */
    public static String ByteBufferToBase64String(ByteBuffer byteBuffer) {
        byte[] arr = new byte[byteBuffer.remaining()];
        byteBuffer.get(arr);
        byteBuffer.clear();
        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(arr), Consts.UTF_8);
    }
}
