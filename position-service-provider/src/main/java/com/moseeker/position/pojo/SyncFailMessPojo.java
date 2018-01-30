package com.moseeker.position.pojo;

public class SyncFailMessPojo {
    private int positionId;
    private int channel;
    private String message;

    public SyncFailMessPojo() {
    }

    public SyncFailMessPojo(int positionId, int channel, String message) {
        this.positionId = positionId;
        this.channel = channel;
        this.message = message;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
