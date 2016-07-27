package com.moseeker.common.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

public class WJFWatcher {

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
    
    
}
