package com.moseeker.common.log;

import java.io.Serializable;
import java.util.Date;

/**
 * es 日志
 * Created by jack on 08/03/2017.
 */
public class LogVO implements Serializable {

    private Date req_time;
    private String hostname;
    private int appid;
    private int http_code;
    private int status_code;
    private long opt_time;
    private String req_uri;
    private String res_type;
    private String session_id;
    private String refer;
    private String event;
    private int user_id;
    private String remote_ip;
    private String cookie;
    private String useragent;
    private Object req_params;
    private Object customs;

    public Date getReq_time() {
        return req_time;
    }

    public void setReq_time(Date req_time) {
        this.req_time = req_time;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public int getHttp_code() {
        return http_code;
    }

    public void setHttp_code(int http_code) {
        this.http_code = http_code;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public long getOpt_time() {
        return opt_time;
    }

    public void setOpt_time(long opt_time) {
        this.opt_time = opt_time;
    }

    public String getReq_uri() {
        return req_uri;
    }

    public void setReq_uri(String req_uri) {
        this.req_uri = req_uri;
    }

    public String getRes_type() {
        return res_type;
    }

    public void setRes_type(String res_type) {
        this.res_type = res_type;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getRemote_ip() {
        return remote_ip;
    }

    public void setRemote_ip(String remote_ip) {
        this.remote_ip = remote_ip;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getUseragent() {
        return useragent;
    }

    public void setUseragent(String useragent) {
        this.useragent = useragent;
    }

    public Object getReq_params() {
        return req_params;
    }

    public void setReq_params(Object req_params) {
        this.req_params = req_params;
    }

    public Object getCustoms() {
        return customs;
    }

    public void setCustoms(Object customs) {
        this.customs = customs;
    }
}
