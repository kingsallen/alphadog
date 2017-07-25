package com.moseeker.searchengine.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.moseeker.searchengine.util.SearchUtil;

@Service
public class PositionSearchEngine {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SearchUtil searchUtil;
	
	public Map<String,Object> search(String keyWord,String industry,String salaryCode,int page,int pageSize,String cityCode,String startTime,String endTime){
		TransportClient client= searchUtil.getEsClient();
		SearchResponse hits =this.quertPrefix(keyWord, industry, salaryCode, page, pageSize, cityCode,startTime,endTime,client);
		if(hits!=null){
			long hitNum=hits.getHits().getTotalHits();
			if(hitNum==0&&StringUtils.isNotEmpty(keyWord)){
				SearchResponse hitsData=this.quertString(keyWord, industry, salaryCode, page, pageSize, cityCode,startTime,endTime,client);
				Map<String,Object> map=searchUtil.handleData(hitsData,"positions");
				logger.info(map.toString());
				return map;
			}else{
				Map<String,Object> map=searchUtil.handleData(hits,"positions");
				logger.info(map.toString());
				return map;
			}
		}
		return new HashMap<String,Object>();
	}
	public SearchResponse quertString(String keyWord,String industry,String salaryCode,int page,int pageSize,String cityCode,String startTime,String endTime,TransportClient client){
		if(client!=null){
			QueryBuilder sentence=this.handleStringSearchSentence(keyWord, industry, salaryCode, page, pageSize, cityCode,startTime,endTime);
			SearchRequestBuilder responseBuilder=client.prepareSearch("positions").setTypes("position")
	                .setQuery(sentence)
	                .addAggregation(searchUtil.handle("_source.company.industry_data","industry"))
	                .addAggregation(searchUtil.handle("_source.position.city_data","city"))
	                .addAggregation(searchUtil.handle("_source.position.salary_data","salary"))
	                .setFrom((page-1)*pageSize)
	                .setSize(pageSize);
	        logger.info(responseBuilder.toString());
	        SearchResponse response = responseBuilder.execute().actionGet();
	        return response;
		}
		return null;
	}
	//查询
	public SearchResponse quertPrefix(String keyWord,String industry,String salaryCode,int page,int pageSize,String cityCode,String startTime,String endTime,TransportClient client){
		if(client!=null){
			QueryBuilder sentence=this.handlePrefixSearchSentence(keyWord, industry, salaryCode, page, pageSize, cityCode,startTime,endTime);
			SearchRequestBuilder responseBuilder=client.prepareSearch("positions").setTypes("position")
	                .setQuery(sentence)
	                .addAggregation(searchUtil.handle("_source.company.industry_data","industry"))
	                .addAggregation(searchUtil.handle("_source.position.city_data","city"))
	                .addAggregation(searchUtil.handle("_source.position.salary_data","salary"))
	                .setFrom((page-1)*pageSize)
	                .setSize(pageSize);
	        logger.info(responseBuilder.toString());
	        SearchResponse response = responseBuilder.execute().actionGet();
	        return response;
		}
		return null;
	}
	//根据query_string关键字组织查询
	private QueryBuilder handleStringSearchSentence(String keyWord,String industry,String salaryCode,int page,int pageSize,String cityCode,String startTime,String endTime){
		QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        List<String> list=new ArrayList<String>();
        list.add("position.title");
        searchUtil.handleKeyWordForPrefix(keyWord, false, query, list);
        CommonQuerySentence(industry, salaryCode,cityCode, startTime, endTime, query);
		return query;
	}
	//通过match_prhase_prefix查询
	private QueryBuilder handlePrefixSearchSentence(String keyWord,String industry,String salaryCode,int page,int pageSize,String cityCode,String startTime,String endTime){
		QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        List<String> list=new ArrayList<String>();
        list.add("position.title");
        searchUtil.handleKeyWordForPrefix(keyWord, false, query, list);
        CommonQuerySentence(industry, salaryCode,cityCode, startTime, endTime, query);
		return query;
	}
	//公共查询的条件部分
	private void CommonQuerySentence(String industry,String salaryCode,String cityCode,String startTime,String endTime,QueryBuilder query){
	    searchUtil.handleTerms(industry, query, "company.industry.industry_code");
        searchUtil.handleTerms(cityCode, query, "position.city_code.code");
        handleDateGT(startTime, query);
        handleDateLT(startTime, query);
        handleSalary(salaryCode, query);
	}
	//处理时间范围查询，从xxx来时
	private void handleDateGT(String startTime, QueryBuilder query){
		if(!StringUtils.isNotEmpty(startTime)){
    		QueryBuilder keyand = QueryBuilders.boolQuery();
    		RangeQueryBuilder fullf = QueryBuilders.rangeQuery("position.publish_date").format(startTime);
    		((BoolQueryBuilder) keyand).must(fullf);
            ((BoolQueryBuilder) query).must(keyand);
    	}
	}
	// 小于xxxx时间
	private void handleDateLT(String endTime, QueryBuilder query){
		if(!StringUtils.isNotEmpty(endTime)){
    		QueryBuilder keyand = QueryBuilders.boolQuery();
    		RangeQueryBuilder fullf = QueryBuilders.rangeQuery("position.publish_date").to(endTime);
    		((BoolQueryBuilder) keyand).must(fullf);
            ((BoolQueryBuilder) query).must(keyand);
    	}
	}
	
	private void handleSalary(String salaryCode,QueryBuilder query){
		if(!StringUtils.isNotEmpty(salaryCode)){
			List<Map> list=convertToListMap(salaryCode);
			QueryBuilder keyand = QueryBuilders.boolQuery();
			if(list!=null&&list.size()>0){
				for(Map map:list){
					QueryBuilder keyand1 = QueryBuilders.boolQuery();
					int salaryTop=(int) map.get("salaryTop");
					QueryBuilder top = QueryBuilders.matchQuery("position.salary_top", salaryTop);
					((BoolQueryBuilder) keyand1).must(top);
					int salaryBottom=(int) map.get("salaryBottom");
					QueryBuilder bottom = QueryBuilders.matchQuery("position.salary_bottom", salaryBottom);
					((BoolQueryBuilder) keyand1).must(bottom);
					((BoolQueryBuilder) keyand).should(keyand1);
				}
				((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
				((BoolQueryBuilder) query).must(keyand);
			}
		}
	}
	
	private List<Map> convertToListMap(String data){
		if(!StringUtils.isNotEmpty(data)){
			return JSON.parseArray(data, Map.class);
		}
		return null;
	}
	
	private Map convertToMap(String data){
		if(!StringUtils.isNotEmpty(data)){
			return (Map) JSON.parse(data);
		}
		return null;
	}

}
