package com.moseeker.rpccenter.config;

import com.moseeker.rpccenter.exception.RpcException;
/**
 * 配置有效性检查
 * <p>
 *
 * @author : Mid Hua
 * @date : 2016-3-27
 */
public interface IConfigCheck {
    /**
     * 检查配置<br>
     * 配置非法时，抛出异常{@link RpcException}
     * <p>
     *
     * @throws RpcException
     */
    void check() throws RpcException;
}
