package com.moseeker.searchengine.util;

import com.moseeker.common.util.ConfigPropertiesUtil;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.jooq.util.derby.sys.Sys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by zztaiwll on 17/12/5.
 */
public class EsClientInstace {

    private static TransportClient client=null;
    public  static  TransportClient getClient(){
        if(client!=null){
            return client;
        }else{
            synchronized(EsClientInstace.class) {
                if(client!=null){
                    return client;
                }else {
                    try {
                        ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
                        propertiesReader.loadResource("es.properties");
                        String cluster_name = propertiesReader.get("es.cluster.name", String.class);
                        String es_connection = propertiesReader.get("es.connection", String.class);
                        Integer es_port = propertiesReader.get("es.port", Integer.class);
                        Settings settings = Settings.settingsBuilder().put("cluster.name", cluster_name)
                                .build();
                        client = TransportClient.builder().settings(settings).build()
                                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(es_connection), es_port));
                    } catch (Exception e) {
                        e.printStackTrace();
                        client.close();
                        client = null;
                    }
                }
            }
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
