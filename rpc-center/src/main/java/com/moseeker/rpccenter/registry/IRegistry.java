package com.moseeker.rpccenter.registry;

import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.rpccenter.loadbalance.common.DynamicHostSet;

/**
 * 注册中心
 * <p>
 *
 * @author : Mid Hua
 * @date : 2016-3-27
 */
public interface IRegistry {

    /**
     * 注册<br>
     * 包括：provider和client
     * <p>
     *
     * @param config
     *            配置信息
     * @throws RpcException
     */
    void register(String config) throws RpcException;

    /**
     * 服务注销
     * <p>
     */
    void unregister();

    DynamicHostSet findAllService();
}
