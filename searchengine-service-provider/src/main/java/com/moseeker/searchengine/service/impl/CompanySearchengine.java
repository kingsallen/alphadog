package com.moseeker.searchengine.service.impl;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.InternalAggregation;
import org.elasticsearch.search.aggregations.metrics.MetricsAggregationBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.ConfigPropertiesUtil;

@Service
@CounterIface
public class CompanySearchengine {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	public Map<String,Object>  query(String keywords,String citys,String industry,String scale,Integer page,Integer pageSize) throws TException{
		SearchResponse hits=queryPrefix(keywords,citys,industry,scale,page,pageSize);
		long hitNum=hits.getHits().getTotalHits();
		if(hitNum==0){
			SearchResponse hitsData=queryString(keywords,citys,industry,scale,page,pageSize);
			Map<String,Object> map=this.handleData(hitsData);
			return map;
		}else{
			Map<String,Object> map=this.handleData(hits);
			return map;
		}
		
		
	}
	//构建
	private QueryBuilder buildQueryForString(String keywords,String citys,String industry,String scale){
		QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        boolean hasKey = false;
        this.handleKeyWordforQueryString(keywords,hasKey,query);
        this.handleCitys( citys,query);
        this.handleIndustry(industry, query);
        this.handleScale(scale, query);
        return query;
	}
	//通过queryString
    public SearchResponse queryString(String keywords,String citys,String industry,String scale,Integer page,Integer pageSize) throws TException {
         try{
        	 TransportClient client=this.getEsClient();
        	 if(client!=null){
        		 QueryBuilder query=this.buildQueryForString(keywords, citys, industry, scale);
                 SearchRequestBuilder responseBuilder=client.prepareSearch("companys").setTypes("company")
                         .setQuery(query)
                         .addSort("_score", SortOrder.DESC)
                         .setFrom(page)
                         .setSize(pageSize)
                         .addAggregation(this.handleAggIndustry())
                         .addAggregation(this.handleAggPositionCity())
                         .addAggregation(this.handleAggScale());
                 logger.info(responseBuilder.toString());
                 SearchResponse response = responseBuilder.execute().actionGet();
                 return response;
        	 }
             
         }catch(Exception e){
        	 logger.info(e.getMessage(),e);
         }
    	return null;
    }
    
   
    //通过prefix搜索
    public SearchResponse queryPrefix(String keywords,String citys,String industry,String scale,Integer page,Integer pageSize) throws TException {
        try{
       	 TransportClient client=this.getEsClient();
       	 if(client!=null){
       		 	QueryBuilder query = QueryBuilders.boolQuery();
       		    this.buildQueryForPrefix(keywords, citys, industry, scale,query);
       		    Script script=this.buildScriptSort(keywords);
                SortBuilder builder=new ScriptSortBuilder(script,"number");
                builder.order( SortOrder.DESC);
                SearchRequestBuilder responseBuilder=client.prepareSearch("companys").setTypes("company")
                        .setQuery(query)
                        .addSort(builder)
                        .setTrackScores(true)
                        .setFrom(page)
                        .setSize(pageSize)
                        .addAggregation(this.handleAggIndustry())
                        .addAggregation(this.handleAggPositionCity())
                        .addAggregation(this.handleAggScale());
                logger.info(responseBuilder.toString());
                SearchResponse response = responseBuilder.execute().actionGet();
                
                return response;
       	 }
            
        }catch(Exception e){
       	 logger.info(e.getMessage(),e);
        }
   	return null;
   }
    
    private Map<String,Object> handleData(SearchResponse response){
    	Map<String,Object> data=new HashMap<String,Object>();
    	Aggregations aggs=response.getAggregations();
    	for(Aggregation aggregation :aggs){
    		System.out.println(aggregation.getName()+"==============");
    		System.out.println(aggregation.getMetaData()+"==============");
    		System.out.println(aggregation+"==============");
    	}
    	Map<String, Object> aggsMap=handleAggs(aggs);
    	data.put("aggs", aggsMap);
    	SearchHits hit=response.getHits();
    	long totalNum=hit.getTotalHits();
    	data.put("totalNum", totalNum);
    	SearchHit[] searchData=hit.getHits();
    	if(searchData!=null&&searchData.length>0){
    		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
    		for(SearchHit ss:searchData){
    			Map<String,Object> obj=ss.getSource();
    			list.add(obj);
    		}
    		data.put("companies", list);
    	}
    	return data;
    }
    
    private Map<String,Object> handleAggs(Aggregations aggs){
    	List<Aggregation> list=aggs.asList();
    	Map<String,Object> map=new HashMap<String,Object>();
    	for(Aggregation agg:list){
    		String name=agg.getName();
    		Object data=agg.getMetaData();
    		map.put(name, data);
    	}
    	return map;
    }
    //构建prefix查询的语句
    private void buildQueryForPrefix(String keywords,String citys,String industry,String scale,QueryBuilder query ){
    	QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
    	((BoolQueryBuilder) query).must(defaultquery);
    	 boolean hasKey = false;
         this.handleKeyWordForPrefix(keywords,hasKey,query);
         this.handleCitys( citys,query);
         this.handleIndustry(industry, query);
         this.handleScale(scale, query);
    }
    //组装prefix关键字查询语句
    private void handleKeyWordForPrefix(String keywords,boolean hasKey,QueryBuilder query){
    	QueryBuilder keyand = QueryBuilders.boolQuery();
		QueryBuilder fullf = QueryBuilders.matchPhrasePrefixQuery("company.name", keywords);
		((BoolQueryBuilder) keyand).should(fullf);
		QueryBuilder fullf1 = QueryBuilders.matchPhrasePrefixQuery("company.abbreviation", keywords);
		((BoolQueryBuilder) keyand).should(fullf1);
		((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
        ((BoolQueryBuilder) query).must(keyand);
    }
    //组装sort的script
    private Script buildScriptSort(String keywords){
   	 StringBuffer sb=new StringBuffer();
   	 sb.append("double score = _score;abbreviation=_source.company.abbreviation;");
        sb.append("name=_source.company.name ;");
        sb.append("if(abbreviation.startsWith('"+keywords+"')&&name.startsWith('"+keywords+"'))");
        sb.append("{score=score*100}");
        sb.append("else if(abbreviation.startsWith('"+keywords+"')||name.startsWith('"+keywords+"'))");
        sb.append("{score=score*50};return score;");
        String scripts=sb.toString();
        Script script=new Script(scripts);
        return script;
   }
    
    //组装query_string关键字查询语句
    private void handleKeyWordforQueryString(String keywords,boolean hasKey,QueryBuilder query){
    	if(!StringUtils.isEmpty(keywords)){
    		hasKey=true;
    		QueryBuilder keyand = QueryBuilders.boolQuery();
    		QueryBuilder fullf = QueryBuilders.queryStringQuery(keywords)
                  .field("company.name")
                  .field("company.abbreviation");
    		((BoolQueryBuilder) keyand).must(fullf);
            ((BoolQueryBuilder) query).must(keyand);
        }
   }
    /*
     * 拼接city
     */
    private void handleCitys(String citys,QueryBuilder query){
    	if (!StringUtils.isEmpty(citys)) {
            String[] city_list = citys.split(",");
            QueryBuilder cityor = QueryBuilders.boolQuery();
            for (int i = 0; i < city_list.length; i++) {
                String city = city_list[i];
                QueryBuilder cityfilter = QueryBuilders.matchPhraseQuery("position_city", city);
                ((BoolQueryBuilder) cityor).should(cityfilter);
            }
            ((BoolQueryBuilder) query).must(cityor);
        }
    }
    /*
     * 处理行业
     */
    private void handleIndustry(String industries,QueryBuilder query){
    	if (!StringUtils.isEmpty(industries)) {
            String[] industry_list = industries.split(",");
            QueryBuilder industryor = QueryBuilders.boolQuery();
            for (int i = 0; i < industry_list.length; i++) {
                String industry = industry_list[i];
                QueryBuilder industryfilter = QueryBuilders.matchPhraseQuery("industry", industry);
                ((BoolQueryBuilder) industryor).should(industryfilter);
            }
            ((BoolQueryBuilder) query).must(industryor);
        }
    }
    /*
     * 公司规模的处理
     */
    private void handleScale(String scales,QueryBuilder query){
    	if(!StringUtils.isEmpty(scales)){
    		 String[] scaleList = scales.split(",");
             QueryBuilder scaleor = QueryBuilders.boolQuery();
             for(int i = 0; i < scaleList.length; i++){
            	 int scale=Integer.parseInt(scaleList[i]);
            	 QueryBuilder scaleFilter = QueryBuilders.matchQuery("scale", scale);
            	 ((BoolQueryBuilder) scaleor).should(scaleFilter);
             }
             ((BoolQueryBuilder) query).must(scaleor);
    	}
    }
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
    
    private AbstractAggregationBuilder handleAggIndustry(){
    	StringBuffer sb=new StringBuffer();
    	sb.append("industry=_source.company.industry;");
    	sb.append("if(industry  in _agg['transactions'] ){}");
    	sb.append("else{_agg['transactions'].add(industry)};");
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
    	MetricsAggregationBuilder build=AggregationBuilders.scriptedMetric("industry")
                .initScript(new Script("_agg['transactions'] = []"))
                .mapScript(new Script(mapScript))
                .reduceScript(new Script(reduceScript))
                .combineScript(new Script(combinScript));
        return build;
    }
    private AbstractAggregationBuilder handleAggPositionCity(){
    	StringBuffer sb=new StringBuffer();
    	sb.append("city=_source.position_city;");
    	sb.append("for(ss in city){");
    	sb.append("if(ss  in _agg['transactions'] ){}");
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
    	MetricsAggregationBuilder build=AggregationBuilders.scriptedMetric("city")
                .initScript(new Script("_agg['transactions'] = []"))
                .mapScript(new Script(mapScript))
                .reduceScript(new Script(reduceScript))
                .combineScript(new Script(combinScript));
        return build;
    }
    private AbstractAggregationBuilder handleAggScale(){
    	StringBuffer sb=new StringBuffer();
    	sb.append("scale=_source.company.scale;");
    	sb.append("if(scale  in _agg['transactions'] ){}");
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
    	MetricsAggregationBuilder build=AggregationBuilders.scriptedMetric("scale")
                .initScript(new Script("_agg['transactions'] = []"))
                .mapScript(new Script(mapScript))
                .reduceScript(new Script(reduceScript))
                .combineScript(new Script(combinScript));
        return build;
    }
}
