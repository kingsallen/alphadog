package com.moseeker.useraccounts.service.impl.pojos;

public class KafkaInviteApplyPojo extends KafkaBaseDto {
    private Integer invited;

    public Integer getInvited() {
        return invited;
    }

    public void setInvited(Integer invited) {
        this.invited = invited;
    }
}
