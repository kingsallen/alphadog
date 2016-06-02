package com.moseeker.rpccenter.config;

import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.moseeker.rpccenter.exception.RpcException;

import com.moseeker.rpccenter.common.Constants;

/**
 * Created by zzh on 16/3/28.
 */
public class RegistryConfig implements IConfigCheck{

    /** javabean的id */
    private String id;

    /** 链接字符串 */
    private String connectstr;

    /** 会话超时时间 */
    private int timeout = 10000;

    /** 重试次数，默认重试为1次 */
    private int retry = 3;

    /** 共享一个zk链接，默认为true */
    private boolean singleton = true;

    /** 全局path前缀,常用来区分不同的应用 */
    private String namespace = Constants.ZK_NAMESPACE_ROOT;

    /** 授权字符串(server端配置，client端不用设置) */
    private String auth;

    /** {@link CuratorFramework} */
    private CuratorFramework zkClient;

    /**
     * 获取zkClient
     * <p>
     *
     * @return {@link CuratorFramework}
     * @throws Exception
     */
    public CuratorFramework obtainZkClient() throws RpcException {
        check(); // 配置检查

        if (singleton) {
            if (zkClient == null) {
                zkClient = create();
                zkClient.start();
            }
            return zkClient;
        }
        zkClient = create();
        return zkClient;
    }

    /**
     * 创建CuratorFramework实例
     * <p>
     *
     * @return {@link CuratorFramework}
     * @throws Exception
     */
    private CuratorFramework create() throws RpcException {
        return create(connectstr, timeout, namespace, retry);
    }

    /**
     * 创建CuratorFramework实例
     * <p>
     *
     * @param connectString
     * @param sessionTimeout
     * @param namespace
     * @return {@link CuratorFramework}
     */
    private CuratorFramework create(String connectString, Integer sessionTimeout, String namespace, int retry) {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();

        if (StringUtils.isNotEmpty(auth)) {
            builder.authorization("digest", auth.getBytes());
        }

        return builder.connectString(connectString).sessionTimeoutMs(sessionTimeout).connectionTimeoutMs(sessionTimeout).namespace(namespace).retryPolicy(new ExponentialBackoffRetry(
                1000,
                retry)).defaultData(null).build();
    }

    /**
     * 关闭链接
     * <p>
     */
    public void close() {
        if (zkClient != null) {
            zkClient.close();
        }
    }

    public void check() throws RpcException {
        if (StringUtils.isEmpty(connectstr)) {
            throw new RpcException(RpcException.CONFIG_EXCEPTION, "The params 'connectstr' cannot empty!");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConnectstr() {
        return connectstr;
    }

    public void setConnectstr(String connectstr) {
        this.connectstr = connectstr;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

}
