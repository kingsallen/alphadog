package com.moseeker.rpccenter.registry;

import com.alibaba.fastjson.JSON;
import com.moseeker.rpccenter.common.Constants;
import com.moseeker.rpccenter.config.ServerData;
import com.moseeker.rpccenter.config.ZKConfig;
import com.moseeker.rpccenter.exception.RegisterException;
import com.moseeker.rpccenter.exception.RpcException;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.EnsurePath;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 提供服务注册与取消注册的功能
 * Created by jack on 06/02/2017.
 */
public class ServerRegistry {

    private CuratorFramework client;                                        //curator客户端
    private List<PathChildrenCache> listeners = new ArrayList<>();          //监听集合
    private ZKConfig config;                                                //zk配置信息
    private ServerData data;                                                //服务注册的信息
    private Logger logger = LoggerFactory.getLogger(ServerRegistry.class);

    /**
     * 初始化服务注册工具
     * @param config ZK配置信息
     * @param data  服务节点的data数据
     * @throws RegisterException
     */
    public ServerRegistry(ZKConfig config, ServerData data) throws RegisterException {
        if(config.check() && data.check()) {
            throw new RegisterException();
        }
        try {
            this.config = config;
            this.data = data;
            CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
            // FixedEnsembleProvider
            client = builder.connectString(this.config.getIp()+":"+this.config.getPort())
                    .sessionTimeoutMs(this.config.getSessionTimeOut())
                    .connectionTimeoutMs(this.config.getConnectionTimeOut())
                    .canBeReadOnly(false)
                    .retryPolicy(new ExponentialBackoffRetry(config.getBaseSleepTimeMS(), config.getMaxRetry()))
                    .namespace(this.config.getRoot())
                    .build();
            client.start();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw new RegisterException();
        } finally {
            if(client != null) {
                client.close();
            }
        }
    }

    /**
     * 注册服务。根据配置信息注册相关服务
     */
    public void register() {
        buildPaths();
        addListeners();
        logger.info("-----ServerRegistry register-----");
    }

    /**
     * 取消注册的服务，并关闭相关的curator资源
     */
    public void unRegister() {
        logger.info("-----ServerRegistry unRegister-----");
        clearListeners();
        if(client != null) {
            client.close();
            client = null;
        }
    }

    /**
     * 根据配置文件注册节点以及子节点，并注册服务的配置信息
     */
    private void buildPaths() {
        config.getServerNames().forEach(serverName -> {
            try {
                buildPath(serverName);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                e.printStackTrace();
            }
        });
    }

    /**
     * 注册给定的服务节点以及子节点，并注册服务的配置信息
     * @param serverName 服务节点的名称
     * @throws RegisterException 注册工具异常。在curator调用forPath方法时，会触发throw Exception。
     * 捕获这个异常时，抛出RegisterException
     */
    private void buildPath(String serverName) throws RegisterException {

        StringBuffer parentPath = new StringBuffer();
        parentPath.append(config.getZkSeparator()).append(serverName).append(config.getZkSeparator())
                .append(config.getServers());

        StringBuffer serverPath = new StringBuffer();
        parentPath.append(config.getZkSeparator()).append(serverName).append(config.getZkSeparator())
                .append(config.getServers()).append(config.getIp()).append(":").append(config.getPort());
        try {
            client.create().creatingParentContainersIfNeeded().forPath(parentPath.toString());
            //client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(sb.toString());
            client.create().withMode(CreateMode.EPHEMERAL).forPath(serverPath.toString(),
                    JSON.toJSONString(data).getBytes(Constants.UTF8));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RegisterException();
        }
    }

    /**
     * 添加监听
     */
    private void addListeners() {
        config.getServerNames().forEach(serverName -> {
            try {
                addListener(serverName);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                e.printStackTrace();
            }
        });
    }

    /**
     * 为每个服务节点（父节点）添加监控子节点变化的监听，并对各种情况做相应处理。现阶段主要是针对子节点丢失时，重新注册服务
     * @param serverName 服务名称
     */
    private void addListener(String serverName) {
        String path = new StringBuffer().append(config.getZkSeparator()).append(serverName).append(config.getZkSeparator()).append(config.getServers()).toString();
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, path, false);
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {

            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("CHILD_ADDED");
                        break;
                    case CHILD_UPDATED:
                        System.out.println("CHILD_UPDATED");
                        break;
                    case CHILD_REMOVED:
                        System.out.println("CHILD_REMOVED");
                        reRegister();
                        break;
                    case CONNECTION_SUSPENDED:
                        System.out.println("CONNECTION_SUSPENDED");
                        break;
                    case CONNECTION_RECONNECTED:
                        System.out.println("CONNECTION_RECONNECTED");
                        break;
                    case CONNECTION_LOST:
                        System.out.println("CONNECTION_LOST");
                        break;
                    case INITIALIZED:
                        break;
                    default:
                }
            }
        });
        try {
            pathChildrenCache.start();
            listeners.add(pathChildrenCache);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消注册并释放curator资源
     */
    private void reRegister() {
        clearAll();
        buildPaths();
    }

    /**
     * 取消监听并删除zk服务节点
     */
    private void clearAll() {
        clearListeners();
        config.getServerNames().forEach(serverName -> clear(serverName));
    }

    private void clear(String serverName) {
        StringBuffer parentPath = new StringBuffer();
        parentPath.append(config.getZkSeparator()+serverName+config.getServers());
        try {
            client.delete().guaranteed().deletingChildrenIfNeeded().forPath(parentPath.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }

    private void clearListeners() {
        if(listeners.size() > 0) {
            listeners.forEach(listener -> {
                try {
                    listener.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage(), e);
                }
            });
        }
    }
}
