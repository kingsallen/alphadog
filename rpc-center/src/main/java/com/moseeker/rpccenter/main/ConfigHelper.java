package com.moseeker.rpccenter.main;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.rpccenter.config.ServerData;
import com.moseeker.rpccenter.config.ThriftConfig;
import com.moseeker.rpccenter.config.ZKConfig;
import org.apache.commons.lang.StringUtils;

import com.moseeker.rpccenter.common.configure.PropertiesConfiguration;
import com.moseeker.rpccenter.exception.RpcException;

/**
 * Created by zzh on 16/3/29.
 */
class ConfigHelper {

    /* 配置信息 */
    private ThriftConfig thriftConfig;          //thrift服务配置信息
    private ZKConfig zkConfig;                  //zk配置
    private ServerData serverData;              //节点信息

    /** LOGGER */
    //private static final Logger LOGGER = LoggerFactory.getLogger(ConfigHelper.class);

    /**
     * 初始化配置对象
     * <p>
     *
     * @param configObject
     *            配置文件存储对象
     * @param configPrefix
     *            配置节点前缀
     * @param configuration
     *            {@link PropertiesConfiguration}
     * @throws RpcException
     */
    public static void initConfig(Object configObject, String configPrefix, PropertiesConfiguration configuration) throws RpcException {
        Method[] methods = configObject.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().length() > 3 && method.getName().startsWith("set") && method.getParameterTypes().length == 1) {
                String attribute = method.getName().substring(3);
                char ch = attribute.charAt(0);
                attribute = Character.toLowerCase(ch) + attribute.substring(1);
                String value = configuration.getProperty(configPrefix + attribute, "");

                try {
                    if (StringUtils.isNotEmpty(value)) {
                        Type type = method.getParameterTypes()[0];
                        if (type == boolean.class) {
                            method.invoke(configObject, Boolean.valueOf(value));
                        } else if (type == int.class) {
                            method.invoke(configObject, Integer.valueOf(value));
                        } else if (type == long.class) {
                            method.invoke(configObject, Long.valueOf(value));
                        } else {
                            method.invoke(configObject, value);
                        }
                    }
                } catch (Exception e) {
                    //LOGGER.error("Init config error", e);
                    throw new RpcException(RpcException.CONFIG_EXCEPTION, e);
                }
            }
        }
    }

    private void initConfig() {
        ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
        try {
            configUtils.loadResource("server.properties");

            int port = -1;
            int interval = -1;

            port = configUtils.get("port", Integer.class);
            ThriftConfig.Builder thriftBuilder = new ThriftConfig.Builder();
            thriftBuilder.
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
