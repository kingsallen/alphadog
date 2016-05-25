package com.moseeker.searchengine.service.impl;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders.*;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.lang.Exception;
import com.alibaba.fastjson.JSON;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices.Iface;



@Service
public class SearchengineServiceImpl implements Iface {
    @Override
    public Response query(String keywords, String filter) throws TException {

         SearchResponse response=null;
        try{
            
            Settings settings = Settings.settingsBuilder()
                                .put("cluster.name", "es-bj")
                                // .put("client.transport.sniff", true)
                                .build();

            TransportClient client = TransportClient
                                .builder()
                                .settings(settings)
                                .build()
                                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("123.57.155.239"), 9300));
            



            // String json = "{" +
            //           "\"content\":\"中国中央政府\"," +
            //           "\"postDate\":\"2013-01-30\"," +
            //           "\"message\":\"trying out Elasticsearch\"" +
            //           "}";

            // client.prepareIndex("index", "fulltext")
            //         .setSource(json)
            //         .execute()
            //         .actionGet();      
                if (keywords==null){
                  keywords = "android";
                }
                System.out.println(keywords);

          
                QueryBuilder keyquery = QueryBuilders.simpleQueryStringQuery(keywords);
                
                QueryBuilder query = QueryBuilders.boolQuery()
                		.must(keyquery);
                if (filter!=null){
                	QueryBuilder locationfilter = QueryBuilders.termQuery("province", filter);
                	((BoolQueryBuilder) query).must(locationfilter); 
                }
                

                response = client
                                        .prepareSearch("index")
                                        .setTypes("fulltext")
                                        .setQuery(query)
                                        .setFrom(0).setSize(60)
                                        .execute()
                                        .actionGet();
            // System.out.println(client.getClass().getName());
            
            // response = client
            //         .prepareSearch()
            //         .execute()
            //         .actionGet();

            //System.out.println(response.toString());
                
        }
        catch( Exception e){
            System.out.println(e.toString());
        }
        System.out.println(JSON.toJSONString(response.toString()));

        Map<String,String> res = new HashMap<String, String>();
        res.put("searchres",response.toString());

        // System.out.println(getEncoding(response.toString()));
        // TODO Auto-generated method stub
        // return ResponseUtils.success(response.toString());
         return ResponseUtils.success(res);
        
        //  ResponseUtils.fail
    }

    @Override
    public Response updateposition(String positionid) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

}
