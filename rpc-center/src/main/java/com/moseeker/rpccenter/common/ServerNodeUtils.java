package com.moseeker.rpccenter.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzh on 16/3/30.
 */
public class ServerNodeUtils {

    /** 默认权重 */
    private static final int DEFAULT_WEIGHT = 1;

    /**
     * private constructor
     */
    private ServerNodeUtils() {
        super();
    }

    /**
     * 服务地址转换为ServerNode列表
     * <p>
     *
     * @param address
     * @return {@link List<ServerNode>}
     */
    public static List<ServerNode> transfer(String address) {
        String[] hostname = address.split(":");
        int weight = DEFAULT_WEIGHT;
        if (hostname.length == 3) {
            weight = Integer.valueOf(hostname[2]);
        }
        String ip = hostname[0];
        Integer port = Integer.valueOf(hostname[1]);
        List<ServerNode> result = new ArrayList<ServerNode>();
        for (int i = 0; i < weight; i++) {
            result.add(new ServerNode(ip, port));
        }
        return result;
    }
}
