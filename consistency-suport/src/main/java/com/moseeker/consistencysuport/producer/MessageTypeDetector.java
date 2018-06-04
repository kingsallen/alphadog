package com.moseeker.consistencysuport.producer;

import java.util.List;

/**
 *
 * 业务探头，查找服务注册的所有业务
 *
 * Created by jack on 2018/4/16.
 */
public interface MessageTypeDetector {

    /**
     * 查找服务中注册的所有消息类型
     * @return 业务列表
     */
    List<MessageTypePojo> findMessageTypes();
}
