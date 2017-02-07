package com.moseeker.rpccenter.config;

import com.moseeker.rpccenter.exception.IncompleteException;
import com.moseeker.rpccenter.exception.RpcException;

/**
 * 提供配置信息是否正确的校验接口。必要的参数应该要初始化配置类的时候就需要提供。
 * Created by jack on 06/02/2017.
 */
public interface ConfigCheck {

    /**
     * 提供配置信息是否配置正确的通用接口定义
     * @return true 配置信息正确; false 配置信息错误
     * @throws IncompleteException
     */
    boolean check() throws IncompleteException;
}
