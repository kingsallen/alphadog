package com.moseeker.rpccenter.config;

import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.common.NetUtils;
import com.moseeker.rpccenter.exception.IncompleteException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public boolean check() throws IncompleteException {

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

    private ZKConfig(String IP, int port, int interval, String root, String servers, Set<String> serverNames,
                     String zkSeparator, int sessionTimeOut, int connectionTimeOut, int baseSleepTimeMS,
                     int maxRetry) {
        this.ip = IP;
        this.port = port;
        this.interval = interval;
        this.root = root;
        this.zkSeparator = zkSeparator;
        this.sessionTimeOut = sessionTimeOut;
        this.connectionTimeOut = connectionTimeOut;
        this.baseSleepTimeMS = baseSleepTimeMS;
        this.maxRetry = maxRetry;
        this.servers = servers;
        this.serverNames = new ArrayList<>();
        if(serverNames.size() > 0) {
            serverNames.forEach(serverName -> this.serverNames.add(serverName));
        }
    }

    public static class Builder {

        private String IP;                                  //zookeeper IP地址
        private int port;                                   //zookeeper 端口
        private int interval = 5 * 60;                      //心跳时间间隔
        private String root = "server_menu";                //根目录
        private Set<String> serverNames = new HashSet<>();  //服务名称
        private String servers = "services";                //服务子目录
        private String zkSeparator = "/";                   //zookeeper服务分隔符
        private int sessionTimeOut = 60000;                 //session超时时间
        private int connectionTimeOut = 15000;              //连接超时时间
        private int baseSleepTimeMS = 3000;                 //重连策略，间隔时间
        private int maxRetry = 5;                           //最大重连次数

        /**
         * 初始化构造器
         * 该构造方法将自动获取可用的端口号和本地网卡的IP地址
         */
        public Builder() {
            port = NetUtils.getAvailablePort();
            IP = NetUtils.getLocalHost();
        }

        /**
         * 初始化构造器
         * @param IP IP地址
         * @param port 端口号
         */
        public Builder(String IP, int port) {
            if(StringUtils.isNotNullOrEmpty(IP)) {
                this.IP = IP;
            } else {
                this.IP = NetUtils.getLocalHost();
            }
            if(port > 0) {
                this.port = port;
            } else {
                this.port = NetUtils.getAvailablePort();
            }
        }

        /**
         * 修改时间间隔参数
         * @param interval 心跳时间间隔
         * @return 构造者本身
         */
        public Builder setInterval(int interval) {
            if(interval > 0)
                this.interval = interval;
            return this;
        }

        /**
         * 修改根目录
         * @param root 根目录
         * @return 构造者本身
         */
        public Builder setRoot(String root) {
            if(StringUtils.isNotNullOrEmpty(root))
                this.root = root;
            return this;
        }

        public Builder addServerName(String serverName) {
            if(StringUtils.isNotNullOrEmpty(serverName))
                this.serverNames.add(serverName);
            return this;
        }

        /**
         * 添加服务名称
         * @param serverNames 服务名称
         * @return 构造者本身
         */
        public Builder addServerNames(List<String> serverNames) {
            if(serverNames != null && serverNames.size() > 0) {
                serverNames.forEach(serverName -> {
                    this.serverNames.add(serverName);
                });
            }
            return this;
        }

        /**
         * 修改服务名称后的目录名字
         * @param servers zookeeper目录，跟在服务名称后面
         * @return 构造者本身
         */
        public Builder setServers(String servers) {
            if(StringUtils.isNotNullOrEmpty(servers))
                this.servers = servers;
            return this;
        }

        /**
         * 修改zk默认的目录分隔符
         * @param zkSeparator zk默认的目录分隔符
         * @return 构造者本身
         */
        public Builder setZkSeparator(String zkSeparator) {
            if(StringUtils.isNotNullOrEmpty(zkSeparator))
                this.zkSeparator = zkSeparator;
            return this;
        }

        /**
         * 修改zk的会话超时时间
         * @param sessionTimeOut zk的会话超时时间
         * @return 构造者本身
         */
        public Builder setSessionTimeOut(int sessionTimeOut) {
            if(sessionTimeOut > 0)
                this.sessionTimeOut = sessionTimeOut;
            return this;
        }

        /**
         * 修改连接zk的超时时间
         * @param connectionTimeOut 连接zk的超时时间
         * @return 构造者本身
         */
        public Builder setConnectionTimeOut(int connectionTimeOut) {
            if(connectionTimeOut > 0)
                this.connectionTimeOut = connectionTimeOut;
            return this;
        }

        /**
         * 修改查询是否启动方法，如果为未启动时，重试的时间间隔
         * @param baseSleepTimeMS 重试的时间间隔
         * @return 构造者本身
         */
        public Builder setBaseSleepTimeMS(int baseSleepTimeMS) {
            if(baseSleepTimeMS > 0)
                this.baseSleepTimeMS = baseSleepTimeMS;
            return this;
        }

        /**
         * 修改重试次数
         * @param maxRetry 重试次数
         * @return 构造者本身
         */
        public Builder setMaxRetry(int maxRetry) {
            if(maxRetry > 0)
                this.maxRetry = maxRetry;
            return this;
        }

        /**
         * 创建ZK配置信息
         * @return ZKConfig
         */
        public ZKConfig createConfig() {
            ZKConfig config = new ZKConfig(IP, port, interval, root, servers, serverNames, zkSeparator,
                    sessionTimeOut, connectionTimeOut, baseSleepTimeMS, maxRetry);

            return config;
        }
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getInterval() {
        return interval;
    }

    public String getRoot() {
        return root;
    }

    public List<String> getServerNames() {
        return serverNames;
    }

    public String getServers() {
        return servers;
    }

    public String getZkSeparator() {
        return zkSeparator;
    }

    public int getSessionTimeOut() {
        return sessionTimeOut;
    }

    public int getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public int getBaseSleepTimeMS() {
        return baseSleepTimeMS;
    }

    public int getMaxRetry() {
        return maxRetry;
    }
}
