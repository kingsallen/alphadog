package com.moseeker.rpccenter.registry;

import java.text.MessageFormat;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.rpccenter.common.Constants;
import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.rpccenter.loadbalance.common.DynamicHostSet;

/**
 * Created by zzh on 16/3/28.
 */
public class ZkServerRegistry implements IRegistry{

    /** LOGGER */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /** {@link CuratorFramework} */
    private final CuratorFramework zookeeper;

    /** 服务zookeeper目录 */
    private final String zkPath;

    /** 服务地址 */
    private final String address;

    /** 授权 */
    private final String auth;

    public ZkServerRegistry(CuratorFramework zookeeper, String zkPath, String address, String auth) {
        this.zookeeper = zookeeper;
        this.zkPath = zkPath;
        this.address = address;
        this.auth = auth;
    }

    public void register(String config) throws RpcException {

        if (zookeeper.getState() == CuratorFrameworkState.LATENT) {
            zookeeper.start();
            zookeeper.newNamespaceAwareEnsurePath(zkPath);
        }

        addListener(config);
        build(config);
    }

    /**
     * 添加监听器，防止网络异常或者zookeeper挂掉的情况
     * <p>
     *
     * @param config
     *            配置信息
     */
    private void addListener(final String config) {
        zookeeper.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
                if (connectionState == ConnectionState.LOST) {// session过期的情况
                    while (true) {
                        try {
                            if (curatorFramework.getZookeeperClient().blockUntilConnectedOrTimedOut()) {
                                if (build(config)) {
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            LOGGER.error(e.getMessage(), e);
                            break;
                        }
                    }
                }
            }
        });
    }

    /**
     * 构建节点
     * <p>
     *
     * @param config
     *            配置信息
     * @throw RpcException
     * @return 是否创建
     */
    private boolean build(String config) throws RpcException {
        // 创建父节点
        createParentsNode();

        // 创建子节点
        StringBuilder pathBuilder = new StringBuilder(zkPath);
        pathBuilder.append(Constants.ZK_SEPARATOR_DEFAULT).append(Constants.ZK_NAMESPACE_SERVERS).append(Constants.ZK_SEPARATOR_DEFAULT).append(address);
        try {
            if (zookeeper.checkExists().forPath(pathBuilder.toString()) == null) {
                zookeeper.create().withMode(CreateMode.EPHEMERAL).forPath(pathBuilder.toString(), config.getBytes(Constants.UTF8));
                return true;
            }
        } catch (Exception e) {
            String message = MessageFormat.format("Create node error in the path : {0}", pathBuilder.toString());
            LOGGER.error(message, e);
            throw new RpcException(message, e);
        }
        return false;
    }

    /**
     * 创建父节点
     * <p>
     *
     * @throws RpcException
     */
    private void createParentsNode() throws RpcException {
        String parentPath = zkPath + Constants.ZK_SEPARATOR_DEFAULT + Constants.ZK_NAMESPACE_SERVERS;
        try {
            if (zookeeper.checkExists().forPath(parentPath) == null) {
                zookeeper.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(parentPath);
            }
        } catch (Exception e) {
            String message = MessageFormat.format("Zookeeper error in the path : {0}", parentPath);
            LOGGER.error(message, e);
            throw new RpcException(message, e);
        }
    }

    public void unregister() {
        zookeeper.close();
    }

    public DynamicHostSet findAllService(){
        return null;
    }

}
