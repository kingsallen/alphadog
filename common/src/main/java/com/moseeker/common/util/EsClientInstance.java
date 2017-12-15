package com.moseeker.common.util;

/**
 * Created by zztaiwll on 17/12/6.
 */
import com.moseeker.common.util.ConfigPropertiesUtil;
import org.apache.commons.lang.*;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import java.net.InetAddress;

/**
 * Created by zztaiwll on 17/12/5.
 */
public class EsClientInstance {

    private static TransportClient client=null;
    public  static  TransportClient getClient() {
        if(client!=null){
            return client;
        }else{
            synchronized(EsClientInstance.class) {
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
