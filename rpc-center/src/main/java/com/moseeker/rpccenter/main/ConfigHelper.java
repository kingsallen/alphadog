package com.moseeker.rpccenter.main;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.rpccenter.common.Constants;
import com.moseeker.rpccenter.config.ServerData;
import com.moseeker.rpccenter.config.ThriftConfig;
import com.moseeker.rpccenter.config.ZKConfig;
import com.moseeker.rpccenter.exception.IncompleteException;
import org.apache.commons.lang.StringUtils;

import com.moseeker.rpccenter.common.configure.PropertiesConfiguration;
import com.moseeker.rpccenter.exception.RpcException;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

    /**
     * 初始化参数配置
     * @param configName 配置文件名称
     * @param impls thrift服务实现类
     * @throws ClassNotFoundException 配置文件定义的类不存在
     * @throws IncompleteException 无法正常加载server.properties配置文件
     * @throws RpcException 服务类错误
     * @throws BeansException 类找不着
     */
    public void initConfig(AnnotationConfigApplicationContext acac, String configName, List<Object> impls)
            throws ClassNotFoundException, IncompleteException, RpcException {
        ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();

        try {
            configUtils.loadResource(configName);
        } catch (Exception e) {
            throw new IncompleteException();
        }

        int interval = configUtils.get("zookeeper.interval", Integer.class, 0);
        String root = configUtils.get("zookeeper.root", String.class, Constants.ZK_NAMESPACE_ROOT);
        String servers = configUtils.get("zookeeper.servers", String.class, Constants.ZK_NAMESPACE_SERVERS);
        String zkSeparator = configUtils.get("zookeeper.zkSeparator", String.class, Constants.ZK_SEPARATOR_DEFAULT);
        int sessionTimeOut = configUtils.get("zookeeper.sessionTimeOut", Integer.class, 30000);
        int connectionTimeOut = configUtils.get("zookeeper.connectionTimeOut", Integer.class, 15000);
        int baseSleepTimeMS = configUtils.get("zookeeper.baseSleepTimeMS", Integer.class, 1000);
        int maxRetry = configUtils.get("zookeeper.maxRetry", Integer.class, 3);
        String protocol = configUtils.get("zookeeper.protocol", String.class, "thrift");
        int weight = configUtils.get("zookeeper.weight", Integer.class, 10);
        String zkIP = configUtils.get("zookeeper.ZKIP", String.class, "");
        int zkPort = configUtils.get("zookeeper.ZKport", Integer.class, 0);
        int multi = configUtils.get("zookeeper.multi", Integer.class, 0);
        String owner = configUtils.get("zookeeper.owner", String.class, "");
        String language = configUtils.get("zookeeper.language", String.class, "");

        String server_type = configUtils.get("thrift.server_type", String.class, "");
        int initialCapacity = configUtils.get("thrift.initialCapacity", Integer.class, 1024);
        int maxLength = configUtils.get("thrift.maxLength", Integer.class, 1024*1024*1024);
        int selector = configUtils.get("thrift.selector", Integer.class, 4);
        int worker = configUtils.get("thrift.worker", Integer.class, 4);
        int retry = configUtils.get("thrift.retry", Integer.class, 3);

        String IP = configUtils.get("thrift.ip", String.class, "");
        int port = configUtils.get("thrift.port", Integer.class, 0);

        Map<String, Object> services = createServices(configUtils, impls, acac);

        ThriftConfig.Builder thriftBuilder = new ThriftConfig.Builder(IP, port);
        thriftConfig = thriftBuilder.setInitialCapicity(initialCapacity).setMaxLength(maxLength)
                .setSelector(selector).setWorker(worker).setRetry(retry).addAllServer(services).createConfig();


        List<String> serviceNames = services.keySet().stream().collect(Collectors.toList());
        ZKConfig.Builder zkBuilder = new ZKConfig.Builder(zkIP, zkPort);
        zkConfig = zkBuilder.setInterval(interval).setRoot(root).setZkSeparator(zkSeparator).setSessionTimeOut(sessionTimeOut)
                .setConnectionTimeOut(connectionTimeOut).setBaseSleepTimeMS(baseSleepTimeMS).setMaxRetry(maxRetry)
                .setServers(servers).addServerNames(serviceNames).createConfig();

        serverData = new ServerData();
        serverData.setWorker(worker);
        serverData.setSelector(selector);
        serverData.setInterval(interval);
        serverData.setIp(thriftConfig.getIP());
        serverData.setPort(thriftConfig.getPort());
        serverData.setMulti(multi);
        serverData.setProtocol(protocol);
        serverData.setWeight(weight);
        serverData.setOwner(owner);
        serverData.setServer_type(server_type);
        serverData.setLanguage(language);
    }

    /**
     * 解析服务名称和服务实现类
     * @param configUtils 配置文件解析工具
     * @param impl 服务实现类
     * @return 服务名称和服务实现类对应关系
     * @throws ClassNotFoundException 配置文件定义的类不存在
     * @throws RpcException 服务类错误
     * @throws BeansException 类找不着
     */
    private Map<String, Object> createServices(ConfigPropertiesUtil configUtils, List<Object> impl,
                                               AnnotationConfigApplicationContext acac)
            throws ClassNotFoundException, RpcException {

        Map<String, Object> services = createServicesByConf(configUtils, acac);
        if(services == null || services.size() == 0) {
            services = createServicesByClass(impl);
        }
        return services;
    }

    /**
     * 通过服务实现类生成 服务名称和服务实现类对应关系
     * @param impl 服务实现类
     * @return 服务名称和服务实现类对应关系
     * @throws RpcException 服务类错误
     */
    private Map<String, Object> createServicesByClass(List<Object> impl) throws RpcException {
        Map<String, Object> servers = new HashMap<>();

        for(Object clazz : impl) {
            Class<?>[] interfaces = clazz.getClass().getInterfaces();
            if (interfaces.length == 0) {
                throw new RpcException("Service class should implements Iface!");
            }
            for (Class clazz1 : interfaces) {
                String cname = clazz1.getSimpleName();
                if (!cname.equals("Iface")) {
                    continue;
                }
                String pname = clazz1.getEnclosingClass().getSimpleName();
                servers.put(pname.toLowerCase(), clazz);
                break;
            }

        }

        return servers;
    }

    /**
     * 通过配置信息生成 服务名称和服务实现类对应关系
     * @param configUtils
     * @param acac spring
     * @return 服务名称和服务实现类对应关系
     * @throws ClassNotFoundException 配置文件定义的类不存在
     * @throws BeansException 类找不着
     */
    private Map<String, Object> createServicesByConf(ConfigPropertiesUtil configUtils,
                                                    AnnotationConfigApplicationContext acac)
            throws ClassNotFoundException, BeansException {
        Map<String, Object> servers = new HashMap<>();
        if(acac != null) {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            Set<Object> keys = configUtils.returnKeys();
            if(keys != null && keys.size() > 0) {
                keys = keys.stream().filter(key -> ((String)key).startsWith("zookeeper.servername.")).collect(Collectors.toSet());
                for(Object obj : keys) {
                    String className = configUtils.get((String)obj, String.class);
                    Object object = acac.getBean(classLoader.loadClass(className));
                    servers.put(((String)obj).substring(((String)obj).lastIndexOf(".")+1), object);
                }
            }
        }
        return servers;
    }

    public ThriftConfig getThriftConfig() {
        return thriftConfig;
    }

    public ZKConfig getZkConfig() {
        return zkConfig;
    }

    public ServerData getServerData() {
        return serverData;
    }
}
