package com.moseeker.profile.constants;

/**
 * 简历导入统计
 * Created by jack on 08/03/2017.
 */
public class StatisticsForChannelmportVO {

    private int user_id;
    private int profile_id;
    private long import_time;
    private byte import_channel;
    private byte profile_operation;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }

    public long getImport_time() {
        return import_time;
    }

    public void setImport_time(long import_time) {
        this.import_time = import_time;
    }

    public byte getImport_channel() {
        return import_channel;
    }

    public void setImport_channel(byte import_channel) {
        this.import_channel = import_channel;
    }

    public byte getProfile_operation() {
        return profile_operation;
    }

    public void setProfile_operation(byte profile_operation) {
        this.profile_operation = profile_operation;
    }
}
