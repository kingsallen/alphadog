package com.moseeker.useraccounts.service.impl.pojos;

public class KafkaInviteApplyPojo extends KafkaBaseDto {
    private Integer invited;
    private Integer employee_id;

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public Integer getInvited() {
        return invited;
    }

    public void setInvited(Integer invited) {
        this.invited = invited;
    }
}
