package com.moseeker.rpccenter.registry;

import com.alibaba.fastjson.JSON;
import com.moseeker.rpccenter.common.Constants;
import com.moseeker.rpccenter.config.ServerData;
import com.moseeker.rpccenter.config.ZKConfig;
import com.moseeker.rpccenter.exception.IncompleteException;
import com.moseeker.rpccenter.exception.RegisterException;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.api.transaction.TransactionCheckBuilder;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
     * @throws RegisterException 连接zookeeper失败
     * @throws IncompleteException 配置信息丢失
     */
    public ServerRegistry(ZKConfig config, ServerData data) throws RegisterException, IncompleteException {
        if(!config.check() && !data.check()) {
            throw new IncompleteException();
        }
        try {
            this.config = config;
            this.data = data;
            initZK();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw new RegisterException();
        } finally {
            /*if(client != null) {
                client.close();
            }*/
        }
    }

    /**
     * 注册服务。根据配置信息注册相关服务
     */
    public void register() throws RegisterException {
        if(client == null) {
            initZK();
        }
        buildPaths();
        addListeners();
        logger.info("-----ServerRegistry register-----");
    }

    /**
     * 取消注册的服务，并关闭相关的curator资源
     */
    public void unRegister() {
        logger.info("-----ServerRegistry unRegister-----");
        clearAll();
        if(client != null) {
            client.close();
            client = null;
        }
    }

    private void initZK() throws RegisterException {
        try {
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
            e.printStackTrace();
            throw new RegisterException();
        }
    }

    /**
     * 根据配置文件注册节点以及子节点，并注册服务的配置信息
     */
    private void buildPaths() throws RegisterException {
        boolean flag = false;
        for (String serverName : config.getServerNames()) {
            try {
                buildPath(serverName);
                flag = true;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }
        if(!flag) {
            throw new RegisterException();
        }
    }

    /**
     * 注册给定的服务节点以及子节点，并注册服务的配置信息
     * @param serverName 服务节点的名称
     * @throws RegisterException 注册工具异常。在curator调用forPath方法时，会触发throw Exception。
     * 捕获这个异常时，抛出RegisterException
     */
    private void buildPath(String serverName) throws RegisterException {

        ServerData dataCopy = data.copy();
        dataCopy.setService(serverName);

        StringBuffer parentPath = new StringBuffer();
        parentPath.append(config.getZkSeparator()).append(serverName).append(config.getZkSeparator())
                .append(config.getServers());

        StringBuffer serverPath = new StringBuffer();
        serverPath.append(config.getZkSeparator()).append(serverName).append(config.getZkSeparator())
                .append(config.getServers()).append(config.getZkSeparator())
                .append(dataCopy.getIp()).append(":").append(dataCopy.getPort());
        logger.debug("NOC ServerRegistry buildPath serverPath:{}", serverPath);

        try {
            if(client.checkExists().forPath(parentPath.toString()) == null) {
                logger.debug("NOC ServerRegistry buildPath parent not exist! parentPath:{}", parentPath);
                try {
                    logger.debug("NOC ServerRegistry buildPath parent exist! create parentPath:{}", parentPath);
                    client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(parentPath.toString());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }

            if(client.checkExists().forPath(serverPath.toString()) == null) {
                logger.debug("NOC ServerRegistry serverPath not exist! serverPath:{}", serverPath);
                try {
                    logger.debug("NOC ServerRegistry create serverPath! serverPath:{}", serverPath);
                    client.create().withMode(CreateMode.EPHEMERAL).forPath(serverPath.toString(), JSON.toJSONString(dataCopy).getBytes(Constants.UTF8));
                } catch (Exception e) {
                    client.inTransaction()
                        .delete().forPath(serverPath.toString())
                        .and().create().forPath(serverPath.toString(), JSON.toJSONString(dataCopy).getBytes(Constants.UTF8))
                        .and().commit();
                }
            } else {
                logger.debug("NOC ServerRegistry servicePatch exist! servicePath:{}", serverPath);
                client.inTransaction()
                    .delete().forPath(serverPath.toString())
                    .and().create().forPath(serverPath.toString(), JSON.toJSONString(dataCopy).getBytes(Constants.UTF8))
                    .and().commit();
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RegisterException();
        }
    }

    /**
     * 修复节点
     * @param serverName 节点名称
     * @throws RegisterException zookeeper异常
     */
    private void reBuild(String serverName) throws RegisterException {

        logger.debug("NOC ServerRegistry reBuild！ serverName:{}", serverName);
        StringBuffer serverPath = new StringBuffer();
        serverPath.append(config.getZkSeparator()).append(serverName).append(config.getZkSeparator())
                .append(config.getServers()).append(config.getZkSeparator())
                .append(data.getIp()).append(":").append(data.getPort());

        try {
            if(client.checkExists().forPath(serverPath.toString()) == null ) {
                buildPath(serverName);
                //addListener(serverName);
            }
        } catch (Exception e) {
            throw new RegisterException();
        }
    }

    /**
     * 添加监听
     */
    private void addListeners() {
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.debug("add listener");
            config.getServerNames().forEach(serverName -> {
                try {
                    addListener(serverName);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    e.printStackTrace();
                }
            });
        }).start();
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
                        //reRegister();
                        try {
                            reBuild(serverName);
                        } catch (RegisterException e) {
                            logger.error(e.getMessage(), e);
                            e.printStackTrace();
                        }
                        break;
                    case CONNECTION_SUSPENDED:
                        System.out.println("CONNECTION_SUSPENDED");
                        break;
                    case CONNECTION_RECONNECTED:
                        System.out.println("CONNECTION_RECONNECTED");
                        break;
                    case CONNECTION_LOST:
                        System.out.println("CONNECTION_LOST");
                        logger.error("zookeeper监听：zookeeper连接丢失");
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
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 取消注册并释放curator资源
     */
    private void reRegister() {
        clearAll();
        register();
    }

    /**
     * 取消监听并删除zk服务节点
     */
    private void clearAll() {
        clearListeners();
        config.getServerNames().forEach(serverName -> clear(serverName));
    }

    /**
     * 删除具体服务节点以及子节点
     * @param serverName
     */
    private void clear(String serverName) {
        StringBuffer parentPath = new StringBuffer();
        logger.info("ServerRegistry clear parentPath:{}", serverName);
        parentPath.append(config.getZkSeparator()).append(serverName).append(config.getServers())
                .append(config.getZkSeparator()).append(data.getIp()).append(":").append(data.getPort());
        try {
            if (client.checkExists().forPath(parentPath.toString()) != null ) {
                client.delete().forPath(parentPath.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 清空节点监听
     */
    private void clearListeners() {
        if(listeners.size() > 0) {
            Iterator<PathChildrenCache> ci = listeners.iterator();
            while (ci.hasNext()) {
                PathChildrenCache childrenCache = ci.next();
                try {
                    childrenCache.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage(), e);
                }
                ci.remove();
            }
        }
    }
}
