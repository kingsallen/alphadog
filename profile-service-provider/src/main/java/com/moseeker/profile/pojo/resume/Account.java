
package com.moseeker.profile.pojo.resume;


public class Account {

    private int usage_limit;
    private int usage_remaining;
    private int uid;

    public void setUsage_limit(int usage_limit) {
        this.usage_limit = usage_limit;
    }

    public int getUsage_limit() {
        return usage_limit;
    }

    public void setUsage_remaining(int usage_remaining) {
        this.usage_remaining = usage_remaining;
    }

    public int getUsage_remaining() {
        return usage_remaining;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

}