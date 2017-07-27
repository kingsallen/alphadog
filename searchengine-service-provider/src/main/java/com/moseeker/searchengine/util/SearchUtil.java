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
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.MetricsAggregationBuilder;
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
    	if (StringUtils.isNotEmpty(conditions)) {
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
    	if(StringUtils.isNotEmpty(keywords)){
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
    	if(StringUtils.isNotEmpty(keywords)){
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
    public AbstractAggregationBuilder handle(String fieldName,String name){
    	StringBuffer sb=new StringBuffer();
    	sb.append("scale=");
    	sb.append(fieldName);
    	sb.append(";if(scale  in _agg['transactions'] ){}");
    	sb.append("else{_agg['transactions'].add(scale)};");
    	String mapScript=sb.toString();
    	StringBuffer sb1=new StringBuffer();
    	sb1.append("jsay=[];");
    	sb1.append("for(a in _aggs){");
    	sb1.append("for(ss in a){");
    	sb1.append("if(ss in jsay){}");
    	sb1.append("else{jsay.add(ss);}}};");
    	sb1.append("return jsay");
    	String reduceScript=sb1.toString();
    	StringBuffer sb2=new StringBuffer();
    	sb2.append("jsay=[];");
    	sb2.append("for(ss in _agg['transactions']){jsay.add(ss)};");
    	sb2.append("return jsay");
    	String combinScript=sb2.toString();
    	MetricsAggregationBuilder build=AggregationBuilders.scriptedMetric(name)
                .initScript(new Script("_agg['transactions'] = []"))
                .mapScript(new Script(mapScript))
                .reduceScript(new Script(reduceScript))
                .combineScript(new Script(combinScript));
        return build;
    }
    
    public AbstractAggregationBuilder handleArray(String fieldName,String name){
    	StringBuffer sb=new StringBuffer();
    	sb.append("city="+fieldName);
    	sb.append(";for(ss in city){");
    	sb.append("if(ss  in _agg['transactions'] || !ss ){}");
    	sb.append("else{_agg['transactions'].add(ss)};}");
    	String mapScript=sb.toString();
    	StringBuffer sb1=new StringBuffer();
    	sb1.append("jsay=[];");
    	sb1.append("for(a in _aggs){");
    	sb1.append("for(ss in a){");
    	sb1.append("if(ss in jsay){}");
    	sb1.append("else{jsay.add(ss);}}};");
    	sb1.append("return jsay");
    	String reduceScript=sb1.toString();
    	StringBuffer sb2=new StringBuffer();
    	sb2.append("jsay=[];");
    	sb2.append("for(ss in _agg['transactions']){");
    	sb2.append("for(a in ss){jsay.add(ss)}};");
    	sb2.append("return jsay");
    	String combinScript=sb2.toString();
    	MetricsAggregationBuilder build=AggregationBuilders.scriptedMetric(name)
                .initScript(new Script("_agg['transactions'] = []"))
                .mapScript(new Script(mapScript))
                .reduceScript(new Script(reduceScript))
                .combineScript(new Script(combinScript));
        return build;
    }
    
}
