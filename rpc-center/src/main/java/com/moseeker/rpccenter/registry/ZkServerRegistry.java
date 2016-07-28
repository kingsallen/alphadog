package com.moseeker.rpccenter.registry;

import java.text.MessageFormat;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.rpccenter.common.Constants;
import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.rpccenter.loadbalance.common.DynamicHostSet;

/**
 * Created by zzh on 16/3/28.
 */
public class ZkServerRegistry implements IRegistry {

    /**
     * LOGGER
     */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * {@link CuratorFramework}
     */
    private final CuratorFramework zookeeper;

    /**
     * 服务zookeeper目录
     */
    private final String zkPath;

    /**
     * 服务地址
     */
    private final String address;

    /**
     * 当前服务名称
     */
    private String currentProviderNode = "";

    /**
     * 授权
     */
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

        build(config);

        try{
            // 添加监听器
            addListener(config, currentProviderNode);
        }catch (Exception e){
            String message = MessageFormat.format("ZkServerRegistry addListener error : {0}", currentProviderNode);
            throw new RpcException(message, e);
        }

//        while (true){
//            simulateSessionTimeout();
//        }
    }

    /**
     * 模拟zookeeper session过期
     *
     */
    private void simulateSessionTimeout() {
        try {
            Thread.sleep(10 * 1000);
            ZooKeeper zk = new ZooKeeper("127.0.0.1:2181",
                    zookeeper.getZookeeperClient().getZooKeeper().getSessionTimeout(),
                    event -> System.out.println("wowowowo" + event),
                    zookeeper.getZookeeperClient().getZooKeeper().getSessionId(),
                    zookeeper.getZookeeperClient().getZooKeeper().getSessionPasswd()
            );
            zk.close();
        } catch (Exception e) {
            // do nothing
            e.printStackTrace();
        }
    }

    /**
     * 添加监听器，防止网络异常或者zookeeper挂掉的情况
     * <p>
     *
     * @param config 配置信息
     */
    private void addListener(final String config, final String path) throws Exception {
        zookeeper.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
                if (connectionState == ConnectionState.LOST) {// session过期的情况
                    while (true) {
                        try {
                            if (curatorFramework.getZookeeperClient().blockUntilConnectedOrTimedOut()) {
                                if (build(config)) {
                                    LOGGER.info("ZkServerRegistry ConnectionState.LOST rebuild provider successful!" + config);
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
        zookeeper.getCuratorListenable().addListener(new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework client, CuratorEvent event) {
                WatchedEvent watchedEvent = event.getWatchedEvent();
                if (watchedEvent != null && watchedEvent.getType() == Watcher.Event.EventType.NodeDeleted) {
                	System.out.println("-----------------Watcher.Event.EventType.NodeDeleted and service rebuild-------------------");
                    while (true) {
                        try {
                            if (zookeeper.getZookeeperClient().blockUntilConnectedOrTimedOut()) {
                                if (build(config)) {
                                    LOGGER.info("ZkServerRegistry Watcher.Event.EventType.NodeDeleted rebuild provider successful!" + config);
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
        String data = new String(zookeeper.getData().watched().forPath(path), "utf-8");
        System.out.println(data);
    }

    /**
     * 构建节点
     * <p>
     *
     * @param config 配置信息
     * @return 是否创建
     * @throw RpcException
     */
    private boolean build(String config) throws RpcException {
        // 创建父节点
        createParentsNode();

        // 创建子节点
        StringBuilder pathBuilder = new StringBuilder(Constants.ZK_SEPARATOR_DEFAULT + zkPath);
        pathBuilder.append(Constants.ZK_SEPARATOR_DEFAULT).append(Constants.ZK_NAMESPACE_SERVERS).append(Constants.ZK_SEPARATOR_DEFAULT).append(address);
        try {
            if (zookeeper.checkExists().forPath(pathBuilder.toString()) == null) {
                this.currentProviderNode = pathBuilder.toString();
                zookeeper.create().withMode(CreateMode.EPHEMERAL).forPath(pathBuilder.toString(), config.getBytes(Constants.UTF8));
            }
        } catch (Exception e) {
            String message = MessageFormat.format("Create node error in the path : {0}", pathBuilder.toString());
            LOGGER.error(message, e);
            throw new RpcException(message, e);
        }
        return true;
    }

    /**
     * 创建父节点
     * <p>
     *
     * @throws RpcException
     */
    private void createParentsNode() throws RpcException {
        String parentPath = "/"+zkPath + Constants.ZK_SEPARATOR_DEFAULT + Constants.ZK_NAMESPACE_SERVERS;
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

    public DynamicHostSet findAllService() {
        return null;
    }

}
