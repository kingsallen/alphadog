package com.moseeker.common.zookeeper.curator;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.EnsurePath;

public class Cache {

	public static PathChildrenCache pathChildrenCache(CuratorFramework client, String path, Boolean cacheData) throws Exception {
        final PathChildrenCache cached = new PathChildrenCache(client, path, cacheData);
        cached.getListenable().addListener(new PathChildrenCacheListener() { 
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                PathChildrenCacheEvent.Type eventType = event.getType();
                switch (eventType) {
                    case CONNECTION_RECONNECTED:
                        cached.rebuild();
                        break;
                    case CONNECTION_SUSPENDED:
                    case CONNECTION_LOST:
                        System.out.println("Connection error,waiting...");
                        break;
                    default:
                        System.out.println("PathChildrenCache changed : {path:" + event.getData().getPath() + " data:" +
                                new String(event.getData().getData()) + "}");
                }
            }
        });
        return cached;
    }


    public static NodeCache nodeCache(CuratorFramework client, String path) {
        final NodeCache cache = new NodeCache(client, path);
        cache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("NodeCache changed, data is: " + new String(cache.getCurrentData().getData()));
            }
        });

        return cache;
    }


    public static void main(String[] args) throws Exception {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);

        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1", retryPolicy);
        client.start();

        EnsurePath ensurePath = client.newNamespaceAwareEnsurePath("/create/test");
        ensurePath.ensure(client.getZookeeperClient());

        /**
         * pathChildrenCache
         */
        PathChildrenCache cache = pathChildrenCache(client, "/create", true);
        cache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        List<ChildData> datas = cache.getCurrentData();

        for (ChildData data : datas) {
            System.out.println("pathcache:{" + data.getPath() + ":" + new String(data.getData())+"}");
        }


        /**
         *    NodeCache
         */
        NodeCache nodeCache = nodeCache(client, "/create/test");
        nodeCache.start(true);

        client.setData().forPath("/create/test", "1111".getBytes());

        System.out.println(new String(nodeCache.getCurrentData().getData()));

        Thread.sleep(10000);
        cache.close();
        client.close();
    }
}
