package com.moseeker.rpccenter.config;

import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.exception.RpcException;

import java.util.List;

/**
 *
 * Created by jack on 06/02/2017.
 */
public class ZKConfig implements ConfigCheck {

    private String ip;                          //zookeeper IP地址
    private int port;                           //zookeeper 端口
    private int interval = 5 * 60;              //心跳时间间隔
    private String root = "server_menu";        //根目录
    private List<String> serverNames;           //服务名称
    private String servers = "services";        //服务子目录
    private String zkSeparator = "/";           //zookeeper服务分隔符
    private int sessionTimeOut = 60000;         //session超时时间
    private int connectionTimeOut = 15000;      //连接超时时间
    private int baseSleepTimeMS = 3000;         //重连策略，间隔时间
    private int maxRetry = 5;                   //最大重连次数

    @Override
    public boolean check() throws RpcException {
        if(StringUtils.isNullOrEmpty(ip)) {
            return false;
        }
        if(StringUtils.isNullOrEmpty(root)) {
            return false;
        }
        if(serverNames == null || serverNames.size() == 0) {
            return false;
        }
        if(StringUtils.isNullOrEmpty(zkSeparator)) {
            return false;
        }
        if(port == 0) {
            return false;
        }
        return true;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public List<String> getServerNames() {
        return serverNames;
    }

    public void setServerNames(List<String> serverNames) {
        this.serverNames = serverNames;
    }

    public String getZkSeparator() {
        return zkSeparator;
    }

    public void setZkSeparator(String zkSeparator) {
        this.zkSeparator = zkSeparator;
    }

    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    public int getSessionTimeOut() {
        return sessionTimeOut;
    }

    public void setSessionTimeOut(int sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
    }

    public int getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public int getBaseSleepTimeMS() {
        return baseSleepTimeMS;
    }

    public void setBaseSleepTimeMS(int baseSleepTimeMS) {
        this.baseSleepTimeMS = baseSleepTimeMS;
    }

    public int getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(int maxRetry) {
        this.maxRetry = maxRetry;
    }
}
