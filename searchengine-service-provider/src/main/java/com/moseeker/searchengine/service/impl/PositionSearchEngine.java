package com.moseeker.searchengine.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moseeker.searchengine.util.SearchUtil;

@Service
public class PositionSearchEngine {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SearchUtil searchUtil;
	
	public Map<String,Object> search(String keyWord,String industry,int salaryCode,int page,int pageSize,String cityCode){
		TransportClient client= searchUtil.getEsClient();
		SearchResponse hits =this.quertPrefix(keyWord, industry, salaryCode, page, pageSize, cityCode, client);
		if(hits!=null){
			long hitNum=hits.getHits().getTotalHits();
			if(hitNum==0&&StringUtils.isNotEmpty(keyWord)){
				SearchResponse hitsData=this.quertString(keyWord, industry, salaryCode, page, pageSize, cityCode, client);
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
	public SearchResponse quertString(String keyWord,String industry,int salaryCode,int page,int pageSize,String cityCode,TransportClient client){
		if(client!=null){
			QueryBuilder sentence=this.handleStringSearchSentence(keyWord, industry, salaryCode, page, pageSize, cityCode);
			SearchRequestBuilder responseBuilder=client.prepareSearch("positions").setTypes("position")
	                .setQuery(sentence)
	                .setFrom((page-1)*pageSize)
	                .setSize(pageSize);
	        logger.info(responseBuilder.toString());
	        SearchResponse response = responseBuilder.execute().actionGet();
	        return response;
		}
		return null;
	}
	//查询
	public SearchResponse quertPrefix(String keyWord,String industry,int salaryCode,int page,int pageSize,String cityCode,TransportClient client){
		if(client!=null){
			QueryBuilder sentence=this.handlePrefixSearchSentence(keyWord, industry, salaryCode, page, pageSize, cityCode);
			SearchRequestBuilder responseBuilder=client.prepareSearch("positions").setTypes("position")
	                .setQuery(sentence)
	                .setFrom((page-1)*pageSize)
	                .setSize(pageSize);
	        logger.info(responseBuilder.toString());
	        SearchResponse response = responseBuilder.execute().actionGet();
	        return response;
		}
		return null;
	}
	private QueryBuilder handleStringSearchSentence(String keyWord,String industry,int salaryCode,int page,int pageSize,String cityCode){
		QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        searchUtil.handleTerms(industry, query, "company.industry.industry_code");
        searchUtil.handleTerms(cityCode, query, "position.city_code.code");
        List<String> list=new ArrayList<String>();
        list.add("position.title");
        searchUtil.handleKeyWordForPrefix(keyWord, false, query, list);
		return query;
	}
	private QueryBuilder handlePrefixSearchSentence(String keyWord,String industry,int salaryCode,int page,int pageSize,String cityCode){
		QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        searchUtil.handleTerms(industry, query, "company.industry.industry_code");
        searchUtil.handleTerms(cityCode, query, "position.city_code.code");
        List<String> list=new ArrayList<String>();
        list.add("position.title");
        searchUtil.handleKeyWordForPrefix(keyWord, false, query, list);
		return query;
	}

}
