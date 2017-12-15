package com.moseeker.searchengine.util;

import com.moseeker.common.util.ConfigPropertiesUtil;
import org.apache.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import java.net.InetAddress;

/**
 * Created by zztaiwll on 17/12/5.
 */
public class EsClientInstace {
    private static Logger logger=Logger.getLogger(EsClientInstace.class);
    private static TransportClient client=null;
    public  static  TransportClient getClient(){
        if(client!=null){
            return client;
        }else{
            synchronized(EsClientInstace.class) {
                if(client!=null){
                    return client;
                }else {
                    while(true){
                       client=createClient();
                        logger.info("链接es客户端=========");
                       if(client!=null){
                           break;
                       }
                        try {
                            logger.info("链接es客户端失败，等待1.5秒重新连接=========");
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return client;
    }
    /*
     创建客户端
     */
    private static TransportClient createClient(){
        try {
            ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
            propertiesReader.loadResource("es.properties");
            String cluster_name = propertiesReader.get("es.cluster.name", String.class);
            String es_connection = propertiesReader.get("es.connection", String.class);
            Integer es_port = propertiesReader.get("es.port", Integer.class);
            Settings settings = Settings.settingsBuilder().put("cluster.name", cluster_name)
                    .put("client.transport.sniff", true)
                    .build();
            String es_alternate = propertiesReader.get("es.alternate", String.class);
            if(org.apache.commons.lang.StringUtils.isNotBlank(es_alternate)){
                client = TransportClient.builder().settings(settings).build()
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(es_connection), es_port))
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(es_alternate), es_port));
            }else{
                client = TransportClient.builder().settings(settings).build()
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(es_connection), es_port));
            }

        } catch (Exception e) {
            e.printStackTrace();
            client.close();
            client = null;
        }
        return client;
    }
    public synchronized static void closeEsClient(){
        if(client!=null){
            client.close();
            client=null;
        }
    }

}
