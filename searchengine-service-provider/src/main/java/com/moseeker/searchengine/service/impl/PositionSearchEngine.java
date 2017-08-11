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
import org.elasticsearch.script.Script;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
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
	//按条件查询，如果prefix的方式无法差的数据，那么转换为query_string的方式查询
	public Map<String,Object> search(String keyWord,String industry,String salaryCode,int page,int pageSize,String cityCode,String startTime,String endTime){
		Map<String,Object> map=new HashMap<String,Object>();
		TransportClient client=null;
		try{
			client=searchUtil.getEsClient();
			SearchResponse hits =this.quertPrefix(keyWord, industry, salaryCode, page, pageSize, cityCode,startTime,endTime,client);
			if(hits!=null){
				long hitNum=hits.getHits().getTotalHits();
				if(hitNum==0&&StringUtils.isNotEmpty(keyWord)){
					SearchResponse hitsData=this.quertString(keyWord, industry, salaryCode, page, pageSize, cityCode,startTime,endTime,client);
					map=searchUtil.handleData(hitsData,"positions");
					logger.info(map.toString());
					return map;
				}else{
					map=searchUtil.handleData(hits,"positions");
					logger.info(map.toString());
					return map;
				}
			}
		}finally{
			if(client!=null){
				client.close();
				client=null;
			}
		}

		return new HashMap<String,Object>();
	}
	//按照query_string的方式查询数据
	public SearchResponse quertString(String keyWord,String industry,String salaryCode,int page,int pageSize,String cityCode,String startTime,String endTime,TransportClient client){
		if(client!=null){
			QueryBuilder sentence=this.handleStringSearchSentence(keyWord, industry, salaryCode, page, pageSize, cityCode,startTime,endTime);
			SearchRequestBuilder responseBuilder=client.prepareSearch("positions").setTypes("position")
					.setQuery(sentence)
					.addAggregation(searchUtil.handle("_source.company.industry_data","industry"))
					.addAggregation(searchUtil.handleArray("_source.position.city_data","city"))
					.addAggregation(searchUtil.handle("_source.position.salary_data","salary"))
					.setFrom((page-1)*pageSize)
					.setSize(pageSize);
			logger.info(responseBuilder.toString());
			SearchResponse response = responseBuilder.execute().actionGet();
			return response;
		}
		return null;
	}
	//按照prefix的方式查询数据
	public SearchResponse quertPrefix(String keyWord,String industry,String salaryCode,int page,int pageSize,String cityCode,String startTime,String endTime,TransportClient client){
		if(client!=null){
			QueryBuilder sentence=this.handlePrefixSearchSentence(keyWord, industry, salaryCode, page, pageSize, cityCode,startTime,endTime);
			SearchRequestBuilder responseBuilder=client.prepareSearch("positions").setTypes("position")
					.setQuery(sentence)
					.addAggregation(searchUtil.handle("_source.company.industry_data","industry"))
					.addAggregation(searchUtil.handleArray("_source.position.city_data","city"))
					.addAggregation(searchUtil.handle("_source.position.salary_data","salary"))
					.setFrom((page-1)*pageSize)
					.setSize(pageSize);
			if(StringUtils.isNotEmpty(keyWord)){
				Script script=this.buildScriptSort(keyWord);
				ScriptSortBuilder builder=new ScriptSortBuilder(script,"number");
				builder.order( SortOrder.DESC);
				responseBuilder.addSort(builder);
				responseBuilder.setTrackScores(true);
			}
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
		searchUtil.handleKeyWordforQueryString(keyWord, false, query, list);
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
		searchUtil.handleTerms(industry, query, "company.industry_data.industry_code");
		searchUtil.handleTerms(cityCode, query, "position.city_data.code");
		handleDateGT(startTime, query);
		handleDateLT(endTime, query);
		handleSalary(salaryCode, query);
	}
	//处理时间范围查询，从xxx来时
	private void handleDateGT(String startTime, QueryBuilder query){
		if(StringUtils.isNotEmpty(startTime)){
			RangeQueryBuilder fullf = QueryBuilders.rangeQuery("position.publish_date").from(startTime);
			((BoolQueryBuilder) query).must(fullf);
		}
	}
	// 小于xxxx时间
	private void handleDateLT(String endTime, QueryBuilder query){
		if(StringUtils.isNotEmpty(endTime)){
			RangeQueryBuilder fullf = QueryBuilders.rangeQuery("position.publish_date").to(endTime);
			((BoolQueryBuilder) query).must(fullf);
		}
	}
	//处理排序
	private  Script handleSort(String order,String keywords,QueryBuilder query){
		StringBuffer sb=new StringBuffer();
		sb.append("double score = _score;");
		sb.append("name=_source.position.title;");
		sb.append("if(name.startsWith('"+keywords+"')");
		sb.append("{score=score*100}");
		sb.append(";return score;");
		String scripts=sb.toString();
		Script script=new Script(scripts);
		return script;
	}
	//处理工资范围
	public void handleSalary(String salaryCode,QueryBuilder query){
		if(StringUtils.isNotEmpty(salaryCode)){
			List<Map> list=convertToListMap(salaryCode);

			StringBuffer sb=new StringBuffer();
			sb.append("top=doc['position.salary_top'].value;bottom=doc['position.salary_bottom'].value;flag=0;");
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					int salaryTop=(int) list.get(i).get("salaryTop");
					int salaryBottom=(int) list.get(i).get("salaryBottom");
					sb.append("if(bottom<"+salaryBottom+"&&top>"+salaryTop+"){flag=1};");
					sb.append("if(bottom<="+salaryBottom+"&&top>"+salaryBottom+"){flag=1};");
					sb.append("if(bottom<"+salaryTop+"&&top>="+salaryTop+"){flag=1};");
					sb.append("if(bottom>="+salaryBottom+"&&top<="+salaryTop+"){flag=1};");
				}
				sb.append("if(flag==1){return true;}");
			}
			String builder=sb.toString();
			Script script=new Script(builder);
			QueryBuilder keyand = QueryBuilders.scriptQuery(script);
			((BoolQueryBuilder) query).filter(keyand);
		}
	}
	//组装sort的script
	private Script buildScriptSort(String keywords){
		StringBuffer sb=new StringBuffer();
		sb.append("double score = _score;title=_source.position.title;");
		sb.append("if(title&&title.startsWith('"+keywords+"'))");
		sb.append("{score=score*100};return score;");
		String scripts=sb.toString();
		Script script=new Script(scripts);
		return script;
	}
	private List<Map> convertToListMap(String data){
		if(StringUtils.isNotEmpty(data)){
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
