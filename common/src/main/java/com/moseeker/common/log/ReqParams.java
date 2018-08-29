package com.moseeker.common.log;

/**
 * Created by zztaiwll on 18/2/12.
 */
public class ReqParams {
    private int type;
    private int user_id;
    private int company_id;
    private int wx_id;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getWx_id() {
        return wx_id;
    }

    public void setWx_id(int wx_id) {
        this.wx_id = wx_id;
    }
}
