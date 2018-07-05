package com.moseeker.position.service.schedule.bean;

/**
 * @author cjm
 * @date 2018-07-04 17:15
 **/
public class PositionSyncStateRefreshBean {

    private int hrThirdPartyPositionId;

    private int channel;

    public PositionSyncStateRefreshBean(int hrThirdPartyPositionId, int channel) {
        this.hrThirdPartyPositionId = hrThirdPartyPositionId;
        this.channel = channel;
    }

    public int getChannel() {
        return channel;
    }

    public int getHrThirdPartyPositionId() {
        return hrThirdPartyPositionId;
    }

}

