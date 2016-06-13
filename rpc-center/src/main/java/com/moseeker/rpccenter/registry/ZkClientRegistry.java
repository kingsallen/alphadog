package com.moseeker.rpccenter.registry;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.rpccenter.loadbalance.common.DynamicHostSet;
import com.moseeker.rpccenter.common.ServerNode;
import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.rpccenter.common.Constants;
import com.moseeker.rpccenter.common.ServerNodeUtils;

/**
 * Created by zzh on 16/3/30.
 */
public class ZkClientRegistry implements IRegistry {

    /**
     * LOGGER
     */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * zookeeper配置路径
     */
    private final String configPath;

    /**
     * {@link CuratorFramework}
     */
    private final CuratorFramework zookeeper;

    /**
     * {@link DynamicHostSet}
     */
    private final DynamicHostSet hostSet = new DynamicHostSet();

    /**
     * {@link ServerNode}
     */
    private final ServerNode clientNode;

    /**
     * 锁对象
     */
    private final Object lock = new Object();

    /**
     * @param configPath
     * @param zookeeper
     * @param clientNode
     */
    public ZkClientRegistry(String configPath, CuratorFramework zookeeper, ServerNode clientNode) {
        super();
        this.configPath = configPath;
        this.zookeeper = zookeeper;
        this.clientNode = clientNode;
    }

    @Override
    public void register(String config) throws RpcException {
        // 如果zk尚未启动,则启动
        if (zookeeper.getState() == CuratorFrameworkState.LATENT) {
            zookeeper.start();
        }

        // 构建zk节点
        buildPathClients(config);
        build();
    }

    /**
     * 创建clients节点
     * <p>
     *
     * @param config 配置信息
     * @return 是否创建节点
     * @throws RpcException
     */
    private boolean buildPathClients(String config) throws RpcException {
        String address = clientNode.genAddress();
        StringBuilder pathBuilder = new StringBuilder(configPath);
        pathBuilder.append(Constants.ZK_SEPARATOR_DEFAULT).append(Constants.ZK_NAMESPACE_CLIENTS).append(Constants.ZK_SEPARATOR_DEFAULT).append(address);
        // 创建节点
        try {
            // 注意：zk重启的过程中，节点可能会存在
            if (zookeeper.checkExists().forPath(pathBuilder.toString()) == null) {
                //addListener(getServersPath());
                zookeeper.create();
                return true;
            }
        } catch (Exception e) {
            String message = MessageFormat.format("Create node error in the path : {0}", pathBuilder.toString());
            LOGGER.error(message, e);
            throw new RpcException(message, e);
        }
        return false;
    }

    private void addListener(String path) throws Exception {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(zookeeper, path, false);
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                build();
            }
        });
        pathChildrenCache.start();
    }

    /**
     * 构建服务信息 <br>
     * <br>
     * 注意：构建时直接操作zookeeper，不使用PathChildrenCache,原因参考：{@link PathChildrenCache}
     * <p>
     *
     * @throws RpcException
     */
    private void build() throws RpcException {
        List<String> childrenList = null;
        String path = getServersPath();
        try {

            childrenList = zookeeper.getChildren().forPath(path);
        } catch (Exception e) {
            String message = MessageFormat.format("Get children node error in the path : {0}", path);
            LOGGER.error(message, e);
            throw new RpcException(message, e);
        }

        if (childrenList == null) {
            LOGGER.error("Not find a service in zookeeper!");
            throw new RpcException("Not find a service in zookeeper!");
        }

        List<ServerNode> current = new ArrayList<ServerNode>();
        for (String children : childrenList) {
            current.addAll(ServerNodeUtils.transfer(children));
        }
        freshContainer(current);
    }

    private String getServersPath() {
        return configPath + Constants.ZK_SEPARATOR_DEFAULT + Constants.ZK_NAMESPACE_SERVERS;
    }


    /**
     * 刷新容器
     * <p>
     *
     * @param current
     */
    private void freshContainer(List<ServerNode> current) {
        synchronized (lock) {
            hostSet.replaceWithList(current);
        }
    }

    @Override
    public DynamicHostSet findAllService() {
        return hostSet;
    }

    @Override
    public void unregister() {
        try {
            zookeeper.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
