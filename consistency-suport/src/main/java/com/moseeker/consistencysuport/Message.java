package com.moseeker.consistencysuport;

/**
 *
 * 客户端回复的消息
 *
 * Created by jack on 09/04/2018.
 */
public class Message {

    private String businessName;
    private MessageType messageType;
    private String messageId;

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
