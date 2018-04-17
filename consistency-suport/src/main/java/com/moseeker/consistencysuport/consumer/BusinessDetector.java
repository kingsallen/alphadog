package com.moseeker.consistencysuport.consumer;

import java.util.List;

/**
 *
 * 业务探头，查找服务注册的所有业务
 *
 * Created by jack on 2018/4/16.
 */
public interface BusinessDetector {

    /**
     * 查找服务中注册的所有业务
     * @return 业务列表
     */
    List<Business> findBusiness();
}
