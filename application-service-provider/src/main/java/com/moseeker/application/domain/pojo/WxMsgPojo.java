package com.moseeker.application.domain.pojo;

import java.util.List;

/**
 * Created by moseeker on 2018/7/12.
 */
public class WxMsgPojo {
    List<CVCheckedWXMsgPojo> msgPojoList;
    List<ReferralWxMsgPojo> wxMsgPojoList;

    public List<CVCheckedWXMsgPojo> getMsgPojoList() {
        return msgPojoList;
    }

    public void setMsgPojoList(List<CVCheckedWXMsgPojo> msgPojoList) {
        this.msgPojoList = msgPojoList;
    }

    public List<ReferralWxMsgPojo> getWxMsgPojoList() {
        return wxMsgPojoList;
    }

    public void setWxMsgPojoList(List<ReferralWxMsgPojo> wxMsgPojoList) {
        this.wxMsgPojoList = wxMsgPojoList;
    }
}
