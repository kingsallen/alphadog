package com.moseeker.useraccounts.service.impl.pojos;

/**
 * Created by jack on 20/02/2017.
 */
public class ApplicationOperationRecordVO {

    private String date;
    private String event;
    private int hide;
    private int step_status;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getHide() {
        return hide;
    }

    public void setHide(int hide) {
        this.hide = hide;
    }

    public int getStep_status() {
        return step_status;
    }

    public void setStep_status(int step_status) {
        this.step_status = step_status;
    }
}
