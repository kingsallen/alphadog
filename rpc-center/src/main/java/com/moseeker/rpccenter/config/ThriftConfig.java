package com.moseeker.rpccenter.config;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.common.NetUtils;
import com.moseeker.rpccenter.exception.IncompleteException;
import com.moseeker.rpccenter.exception.RegisterException;
import com.moseeker.rpccenter.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * thrift服务配置信息
 * Created by jack on 06/02/2017.
 */
public class ThriftConfig implements ConfigCheck {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Map<String, Object> servers;        //服务名称和具体服务对应关系
    private final int port;                           //服务的端口号
    private final String IP;                          //服务的IP地址
    private int initialCapacity = 1024;               //初始化缓存大小 TTransportFactory
    private int maxLength = 1024*1024*1024;           //frame大小
    private int selector = 4;                         //初始化selector数量
    private int worker = 8;                           //初始化worker数量
    private int retry = 3;                            //重试次数

    private ThriftConfig(String IP, int port, Map<String, Object> servers, int initialCapacity, int maxLength,
                         int selector, int worker, int retry){
        this.IP = IP;
        this.port = port;
        this.servers = servers;
        if(initialCapacity > 0)
            this.initialCapacity = initialCapacity;
        if(maxLength > 0)
            this.maxLength = maxLength;
        if(selector > 0)
            this.selector = selector;
        if(worker > 0)
            this.worker = worker;
        if(retry > 0) {
            this.retry = retry;
        }
    }

    public int getRetry() {
        return retry;
    }

    public int getSelector() {
        return selector;
    }

    public int getWorker() {
        return worker;
    }

    public Map<String, Object> getServers() {
        return servers;
    }

    public int getPort() {
        return port;
    }

    public String getIP() {
        return IP;
    }

    public int getInitialCapacity() {
        return initialCapacity;
    }

    public int getMaxLength() {
        return maxLength;
    }

    @Override
    public boolean check() throws IncompleteException {
        if(StringUtils.isNullOrEmpty(IP)) {
            return false;
        }
        if(port == 0) {
            return false;
        }
        if(this.servers == null || this.getServers().size() == 0) {
            return false;
        }

        return true;
    }

    /**
     * thrift服务配置信息创建工具
     */
    public static class Builder {

        Logger logger = LoggerFactory.getLogger(this.getClass());

        private int port;
        private String IP;
        private int initialCapacity = 1024;
        private int maxLength = 1024*1024*1024;
        private int selector = 4;
        private int worker = 8;
        private int retry = 3;
        private Map<String, Object> servers = new HashMap<>();

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
         * 添加服务
         * @param serverName 服务名称
         * @param object 类对象
         * @return 创建工具本身
         */
        public Builder addServer(String serverName, Object object) {
            servers.put(serverName, object);
            return this;
        }

        /**
         * 添加服务
         * @param services 服务名称
         * @return 创建工具本身
         */
        public Builder addAllServer(Map<String, Object> services) {
            servers.putAll(services);
            return this;
        }

        /**
         * 修改TFastFramedTransport缓存大小
         * @param initialCapicity TFastFramedTransport缓存大小
         * @return 创建工具本身
         */
        public Builder setInitialCapicity(int initialCapicity) {
            this.initialCapacity = initialCapicity;
            return this;
        }

        /**
         * 修改TFastFramedTransport frame大小
         * @param maxLength frame最大存储的字符数量
         * @return 创建工具本身
         */
        public Builder setMaxLength(int maxLength) {
            this.maxLength = maxLength;
            return this;
        }

        /**
         * 修改选择器线程数量
         * @param selector 选择器线程数量
         * @return 创建工具本身
         */
        public Builder setSelector(int selector) {
            this.selector = selector;
            return this;
        }

        /**
         * 修改工作线程数量
         * @param worker 工作线程数量
         * @return 创建工具本身
         */
        public Builder setWorker(int worker) {
            this.worker = worker;
            return this;
        }

        /**
         * 修改工作线程数量
         * @param retry 工作线程数量
         * @return 创建工具本身
         */
        public Builder setRetry(int retry) {
            this.retry = retry;
            return this;
        }

        /**
         * 创建配置信息
         * @return ThriftConfig
         * @throws IncompleteException 配置信息不全异常。由于缺少关键信息，导致创建失败
         */
        public ThriftConfig createConfig() throws IncompleteException {
            if(this.servers.size() == 0) {
                throw new IncompleteException();
            }
            ThriftConfig config = new ThriftConfig(IP, port, servers, initialCapacity, maxLength, selector, worker, retry);
            logger.info("NOC Builder createConfig ThriftConfig:{}", JSONObject.toJSONString(config));
            return config;
        }
    }
}
