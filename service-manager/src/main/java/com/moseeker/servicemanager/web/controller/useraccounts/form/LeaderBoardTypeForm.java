package com.moseeker.servicemanager.web.controller.useraccounts.form;

import com.moseeker.servicemanager.web.controller.AppIdForm;

/**
 * @Author: jack
 * @Date: 2018/8/20
 */
public class LeaderBoardTypeForm extends AppIdForm {
    private byte type;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
