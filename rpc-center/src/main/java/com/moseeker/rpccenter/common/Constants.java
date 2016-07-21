package com.moseeker.rpccenter.common;

/**
 * Created by zzh on 16/3/28.
 */
public class Constants {

    /** utf-8 */
    public static final String UTF8 = "utf-8";
    
    public static final String ZOO_CONF_FILE = "classpath:zookeeper.properties";

    /** zookeeper根目录 */
    public static final String ZK_NAMESPACE_ROOT = "services";

    /** zookeeper目录分割符 */
    public static final String ZK_SEPARATOR_DEFAULT = "/";

    /** servers子目录 */
    public static final String ZK_NAMESPACE_SERVERS = "servers";

    /** clients子目录 */
    public static final String ZK_NAMESPACE_CLIENTS = "clients";

    /** configs子目录 */
    public static final String ZK_NAMESPACE_CONFIGS = "configs";

    /** statistics子目录 */
    public static final String ZK_NAMESPACE_STATISTICS = "statistics";

    /** zookeeper中使用时间戳作目录的格式 */
    public static final String ZK_TIME_NODE_FORMAT = "yyyyMMddHHmmss";

    /** zookeeper中总计节点名称 */
    public static final String ZK_NAMESPACE_TOTAL = "total";

    /** zookeeper中详细节点名称 */
    public static final String ZK_NAMESPACE_DETAIL = "detail";

    /**
     * private constructor
     */
    private Constants() {
        super();
    }

}
