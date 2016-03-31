package com.moseeker.rpccenter.loadbalance;

import com.moseeker.rpccenter.loadbalance.common.DynamicHostSet;
import com.moseeker.rpccenter.common.ServerNode;

import java.util.Set;

/**
 * Created by zzh on 16/3/30.
 */
public class LoadBalance {

    /**
     * 随机分配一个服务地址
     *
     * @return SeverNode
     */
    public static ServerNode nextBackend(DynamicHostSet hostSet){
        Set<ServerNode> serverNodeSet = hostSet.getLives();
        Object[] hostArray = serverNodeSet.toArray();
        int len = hostArray.length-1;
        return (ServerNode)hostArray[(int)(Math.random()*(len-1+1))];
    }

}
