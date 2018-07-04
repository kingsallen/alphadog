package com.moseeker.position.service.schedule.bean;

/**
 * @author cjm
 * @date 2018-07-04 17:15
 **/
public class PositionSyncStateRefreshBean {

    private int hrThirdPartyPositionId;

    private int channel;

    private long timeout;

    public PositionSyncStateRefreshBean(int hrThirdPartyPositionId, int channel, long timeout) {
        this.hrThirdPartyPositionId = hrThirdPartyPositionId;
        this.channel = channel;
        this.timeout = timeout;
    }

    public int getChannel() {
        return channel;
    }

    public int getHrThirdPartyPositionId() {
        return hrThirdPartyPositionId;
    }

    public long getTimeout() {
        return timeout;
    }

}

