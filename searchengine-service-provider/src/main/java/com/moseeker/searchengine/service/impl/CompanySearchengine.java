package com.moseeker.searchengine.service.impl;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moseeker.searchengine.util.SearchUtil;
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
import org.elasticsearch.search.aggregations.metrics.MetricsAggregationBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.ConfigPropertiesUtil;

@Service
@CounterIface
public class CompanySearchengine {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SearchUtil searchUtil;
	//搜索信息
	public Map<String,Object>  query(String keywords,String citys,String industry,String scale,Integer page,Integer pageSize) throws TException{
		TransportClient client=searchUtil.getEsClient();
		SearchResponse hits=queryPrefix(keywords,citys,industry,scale,page,pageSize,client);
		long hitNum=hits.getHits().getTotalHits();
		if(hitNum==0&&StringUtils.isNotEmpty(keywords)){
			SearchResponse hitsData=queryString(keywords,citys,industry,scale,page,pageSize,client);
			Map<String,Object> map=searchUtil.handleData(hitsData,"companies");
			logger.info(map.toString());
			return map;
		}else{
			Map<String,Object> map=searchUtil.handleData(hits,"companies");
			logger.info(map.toString());
			return map;
		}
	}

	//通过queryString查询es
    public SearchResponse queryString(String keywords,String citys,String industry,String scale,Integer page,Integer pageSize,TransportClient client) throws TException {
         try{
        	 if(client!=null){
        		 QueryBuilder query=this.buildQueryForString(keywords, citys, industry, scale);
                 SearchRequestBuilder responseBuilder=client.prepareSearch("companys").setTypes("company")
                         .setQuery(query)
                         .setFrom((page-1)*pageSize)
                         .setSize(pageSize)
                         .addAggregation(this.handleAggIndustry())
                         .addAggregation(this.handleAggPositionCity())
                         .addAggregation(this.handleAggScale());
                 if(!org.springframework.util.StringUtils.isEmpty(keywords)){
                	 responseBuilder.addSort("_score", SortOrder.DESC);
                 }
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
    public SearchResponse queryPrefix(String keywords,String citys,String industry,String scale,Integer page,Integer pageSize,TransportClient client) throws TException {
        try{
       	 if(client!=null){
       		 	QueryBuilder query = QueryBuilders.boolQuery();
       		    this.buildQueryForPrefix(keywords, citys, industry, scale,query);
                SearchRequestBuilder responseBuilder=client.prepareSearch("companys").setTypes("company")
                        .setQuery(query)
                        .setTrackScores(true)
                        .setFrom((page-1)*pageSize)
                        .setSize(pageSize)
                        .addAggregation(this.handleAggIndustry())
                        .addAggregation(this.handleAggPositionCity())
                        .addAggregation(this.handleAggScale());

                if(StringUtils.isNotEmpty(keywords)){
                	 Script script=this.buildScriptSort(keywords);
					 ScriptSortBuilder builder=new ScriptSortBuilder(script,"number");
                     builder.order( SortOrder.DESC);
                     builder.sortMode("max");
                     responseBuilder.addSort(builder);
                }
			    logger.info(responseBuilder.toString());
                SearchResponse response = responseBuilder.execute().actionGet();
                return response;
       	 }
        }catch(Exception e){
       	 logger.info(e.getMessage(),e);
        }
   	return null;
   }
    //处理聚合的结果
    private Map<String,Object> handleAggs(Aggregations aggs){
    	List<Aggregation> list=aggs.asList();
    	Map<String,Object> map=new HashMap<String,Object>();
    	for(Aggregation agg:list){
    		String name=agg.getName();
    		Object data=agg.getProperty("value");
    		map.put(name, data);
    	}
    	return map;
    }
    //构建prefix查询的语句
    private void buildQueryForPrefix(String keywords,String citys,String industry,String scale,QueryBuilder query ){
    	QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
    	((BoolQueryBuilder) query).must(defaultquery);
    	 boolean hasKey = false;
		List<String> list=new ArrayList<String>();
		list.add("company.name");
		list.add("company.abbreviation");
		searchUtil.handleKeyWordForPrefix(keywords,hasKey,query,list);
		this.CommonQuerySentence(industry,citys,scale,query);
    }
	//构建查询语句query_string
	public QueryBuilder buildQueryForString(String keywords,String citys,String industry,String scale){
		QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
		QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
		boolean hasKey = false;
		List<String> list=new ArrayList<String>();
		list.add("company.name");
		list.add("company.abbreviation");
		searchUtil.handleKeyWordforQueryString(keywords,hasKey,query,list);
		CommonQuerySentence(industry,citys,scale,query);
		return query;
	}

	//公共查询的条件部分
	private void CommonQuerySentence(String industry,String cityCode,String scale,QueryBuilder query){
		searchUtil.handleTerms(industry, query, "company.industry.code");
		searchUtil.handleTerms(cityCode, query, "position_city.code");
		searchUtil.handleTerms(scale, query, "company.scale");
	}
    //组装prefix关键字查询语句
    private void handleKeyWordForPrefix(String keywords,boolean hasKey,QueryBuilder query){
    	if(!com.moseeker.common.util.StringUtils.isNullOrEmpty(keywords)){
    		QueryBuilder keyand = QueryBuilders.boolQuery();
    		QueryBuilder fullf = QueryBuilders.matchPhrasePrefixQuery("company.name", keywords);
    		((BoolQueryBuilder) keyand).should(fullf);
    		QueryBuilder fullf1 = QueryBuilders.matchPhrasePrefixQuery("company.abbreviation", keywords);
    		((BoolQueryBuilder) keyand).should(fullf1);
    		((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).must(keyand);
    	}
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
    //做行业的统计
    private AbstractAggregationBuilder handleAggIndustry(){
    	StringBuffer sb=new StringBuffer();
    	sb.append("industry=_source.company.industry;");
    	sb.append("if(industry  in _agg['transactions'] || !industry){}");
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
    //做city的统计
    private AbstractAggregationBuilder handleAggPositionCity(){
    	StringBuffer sb=new StringBuffer();
    	sb.append("city=_source.position_city;");
    	sb.append("for(ss in city){");
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
    	MetricsAggregationBuilder build=AggregationBuilders.scriptedMetric("city")
                .initScript(new Script("_agg['transactions'] = []"))
                .mapScript(new Script(mapScript))
                .reduceScript(new Script(reduceScript))
                .combineScript(new Script(combinScript));
        return build;
    }
    //做scale的统计
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
