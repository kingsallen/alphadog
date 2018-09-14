package com.moseeker.profile.service.impl.talentpoolmvhouse.constant;

import com.moseeker.thrift.gen.common.struct.BIZException;

/**
 * @author cjm
 * @date 2018-09-10 18:11
 **/
public enum ProfileMoveChannelEnum {
    JOB51(1, "51job"),
    ZHILIAN(3, "zhaopin");

    private int channel;
    private String name;

    ProfileMoveChannelEnum(int channel, String name) {
        this.channel = channel;
        this.name = name;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
