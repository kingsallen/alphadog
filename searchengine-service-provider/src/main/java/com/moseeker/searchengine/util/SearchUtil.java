package com.moseeker.searchengine.util;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.moseeker.common.util.ConfigPropertiesUtil;

@Service
public class SearchUtil {
	Logger logger = LoggerFactory.getLogger(this.getClass());
    //启动es客户端
    public TransportClient getEsClient(){
    	ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
        try {
            propertiesReader.loadResource("es.properties");
        } catch (Exception e1) {
           logger.info(e1.getMessage(),e1);
        }
        String cluster_name = propertiesReader.get("es.cluster.name", String.class);
        String es_connection = propertiesReader.get("es.connection", String.class);
        Integer es_port = propertiesReader.get("es.port", Integer.class);
        TransportClient client = null;
        try{
       	 Settings settings = Settings.settingsBuilder().put("cluster.name", cluster_name)
                    .build();
         client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(es_connection), es_port));
        }catch(Exception e){
        	logger.info(e.getMessage(),e);
        	client=null;
        }
        return client;
    }
    /*
     * 拼接city
     */
    public void handleTerms(String conditions,QueryBuilder query,String conditionField){
    	if (!StringUtils.isEmpty(conditions)) {
    		List<Integer> codes=new ArrayList<Integer>();
            String[] conditions_list = conditions.split(",");
            for(String code:conditions_list){
            	codes.add(Integer.parseInt(code));
            }
            QueryBuilder cityfilter = QueryBuilders.termsQuery(conditionField, codes);
            ((BoolQueryBuilder) query).must(cityfilter);
            logger.info("组合的条件是=================="+query.toString()+"===========");
        }
    }
    
    //处理聚合的结果
    public Map<String,Object> handleAggs(Aggregations aggs){
    	List<Aggregation> list=aggs.asList();
    	Map<String,Object> map=new HashMap<String,Object>();
    	for(Aggregation agg:list){
    		String name=agg.getName();
    		Object data=agg.getProperty("value");
    		map.put(name, data);
    	}
    	return map;
    }
    
  //处理es的返回数据
    public Map<String,Object> handleData(SearchResponse response,String dataName){
    	Map<String,Object> data=new HashMap<String,Object>();
    	Aggregations aggs=response.getAggregations();
    	Map<String, Object> aggsMap=handleAggs(aggs);
    	data.put("aggs", aggsMap);
    	SearchHits hit=response.getHits();
    	long totalNum=hit.getTotalHits();
    	data.put("totalNum", totalNum);
    	SearchHit[] searchData=hit.getHits();
    	if(totalNum>0){
    		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
    		for(SearchHit ss:searchData){
    			Map<String,Object> obj=ss.getSource();
    			list.add(obj);
    		}
    		data.put(dataName, list);
    	}
    	return data;
    }
    
    //组装prefix关键字查询语句
    public void handleKeyWordForPrefix(String keywords,boolean hasKey,QueryBuilder query,List<String> list){
    	if(!StringUtils.isNotEmpty(keywords)){
    		QueryBuilder keyand = QueryBuilders.boolQuery();
    		for(String field:list){
    			QueryBuilder fullf = QueryBuilders.matchPhrasePrefixQuery(field, keywords);
        		((BoolQueryBuilder) keyand).should(fullf);
    		}
    		((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).must(keyand);
    	}
    } 
    
	 //组装query_string关键字查询语句
    public void handleKeyWordforQueryString(String keywords,boolean hasKey,QueryBuilder query,List<String> list){
    	if(!StringUtils.isEmpty(keywords)){
    		hasKey=true;
    		String words[]=keywords.split(",");
    		QueryBuilder keyand = QueryBuilders.boolQuery();
    		StringBuffer sb=new StringBuffer();
    		for(int i=0;i<words.length;i++){
    			if(i==words.length-1){
    				sb.append(words[i]);
    			}else{
    				sb.append(words[i]+" or ");
    			}
    		}
    		String condition=sb.toString();
    		QueryStringQueryBuilder fullf = QueryBuilders.queryStringQuery(condition);
    		for(String field:list){
    			fullf.field(field);
    		}
    		((BoolQueryBuilder) keyand).must(fullf);
            ((BoolQueryBuilder) query).must(keyand);
        }
   }
    
}
