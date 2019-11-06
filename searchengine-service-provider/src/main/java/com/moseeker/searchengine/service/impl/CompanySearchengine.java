package com.moseeker.searchengine.service.impl;

import java.net.InetAddress;
import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrTeamDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.util.EsClientInstance;
import com.moseeker.searchengine.domain.fallback.FallBack;
import com.moseeker.searchengine.domain.vo.CompanyDetail;
import com.moseeker.searchengine.domain.vo.OtherVO;
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
import org.jooq.Record2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.ConfigPropertiesUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import static com.moseeker.common.constants.Constant.DATABASE_PAGE_SIZE;
import static com.moseeker.common.constants.Constant.PAGE_SIZE;
import static com.moseeker.common.constants.Constant.RETRY_UPPER_LIMIT;

@Service
@CounterIface
public class CompanySearchengine {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SearchUtil searchUtil;

	@Resource(name = "cacheClient")
	private RedisClient client;

	@Autowired
	private HrCompanyDao companyDao;

	@Autowired
	private HrTeamDao teamDao;

	@Autowired
	private JobPositionDao positionDao;

	private SerializeConfig config = new SerializeConfig();

	@PostConstruct
	public void init() {
		config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
	}

	//搜索信息
	@CounterIface
	public Map<String,Object>  query(String keywords,String citys,String industry,String scale,Integer page,Integer pageSize) throws TException{
		TransportClient client=null;
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			client=searchUtil.getEsClient();
			SearchResponse hits=queryPrefix(keywords,citys,industry,scale,page,pageSize,client);
			long hitNum=hits.getHits().getTotalHits();
			if(hitNum==0&&StringUtils.isNotEmpty(keywords)){
				SearchResponse hitsData=queryString(keywords,citys,industry,scale,page,pageSize,client);
				map=searchUtil.handleData(hitsData,"companies");
			}else{
				map=searchUtil.handleData(hits,"companies");
			}
		}catch(Exception e){
			logger.info(e.getMessage(),e);
			return fetchFallback(page, pageSize);
		}
		return map;

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
						.addAggregation(this.handleAggScale())
						.setTrackScores(true);
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
						.setSize(pageSize);
//						.addAggregation(this.handleAggIndustry())
//						.addAggregation(this.handleAggPositionCity())
//						.addAggregation(this.handleAggScale());

				if(StringUtils.isNotEmpty(keywords)){
					Script script=this.buildScriptSort(keywords);
					ScriptSortBuilder builder=new ScriptSortBuilder(script,"number");
					builder.order( SortOrder.DESC);
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
		searchUtil.handleTerms(industry, query, "company.industry_data.code");
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
		sb.append("company=_source.company;if(company){industry=company.industry_data;if(industry){");
		sb.append("if(industry  in _agg['transactions'] || !industry){}");
		sb.append("else{_agg['transactions'].add(industry)}}};");
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
		sb.append("city=_source.position_city;if(city){");
		sb.append("for(ss in city){");
		sb.append("if(ss  in _agg['transactions'] || !ss ){}");
		sb.append("else{_agg['transactions'].add(ss)};}}");
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
		sb.append("company=_source.company;if(company){scale=company.scale;if(scale){");
		sb.append("if(scale  in _agg['transactions'] ){}");
		sb.append("else{_agg['transactions'].add(scale)}}};");
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

	/**
	 * 从数据库冲查找公司列表
	 * @param pageNo 页码
	 * @param pageSize 每页数量
	 * @return 公司数据
	 */
	private Map<String,Object> fetchFallback(int pageNo,int pageSize) {

		if (pageNo <=0) {
			pageNo = 1;
		}
		if (pageSize <= 0 || pageSize > DATABASE_PAGE_SIZE) {
			pageSize = PAGE_SIZE;
		}

		String pattern = FallBack.generatePCCompanyListPattern(pageNo, pageSize);
		String json = FallBack.fetchFromCache(client, pattern);
		if (json != null) {
			return JSONObject.parseObject(json);
		}

		String uuid = UUID.randomUUID().toString();
		try {
			boolean getLock = client.tryGetLock(Constant.APPID_ALPHACLOUD, KeyIdentifier.REDIS_LOG.toString(), pattern, uuid);
			if (getLock) {
				JSONObject jsonObject = fetchFromDB(pageNo, pageSize);

				client.set(Constant.APPID_ALPHACLOUD, KeyIdentifier.POSITION_LIST_FALLBACK.toString(), pattern, jsonObject.toJSONString());

				return jsonObject;
			} else {
				//等待获取redis内容
				try {
					return waitForCache(0, pattern);
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
					Thread.interrupted();
					return new HashMap<>(0);
				}
			}
		} finally {
			client.releaseLock(Constant.APPID_ALPHACLOUD, KeyIdentifier.REDIS_LOG.toString(), pattern, uuid);
		}
	}

	/**
	 * 从数据库中查找职位列表数据
	 * @param pageNo 页码
	 * @param pageSize 每页显示的数量
	 * @return 备份方案的职位列表数据
	 */
	private JSONObject fetchFromDB(int pageNo, int pageSize) {

		JSONObject jsonObject = new JSONObject();

		int total = companyDao.count();
		jsonObject.put("totalNum", total);
		List<CompanyDetail> companyDetails = new ArrayList<>();
		jsonObject.put("companies", companyDetails);
		List<HrCompany> companyList = companyDao.list(pageNo, pageSize);
		if (companyList != null && companyList.size() > 0) {

			List<Integer> companyIdList = companyList
					.stream()
					.map(HrCompany::getId)
					.collect(Collectors.toList());

			List<Record2<Integer, Integer>> teamCount = teamDao.countByCompanyIdList(companyIdList);
			Map<Integer, Integer> teamCountRel = new HashMap<>(companyIdList.size());
			if (teamCount != null && teamCount.size() > 0) {
				for (Record2<Integer, Integer> record2 : teamCount) {
					teamCountRel.put(record2.value1(), record2.value2());
				}
			}

			Map<Integer, Integer> positionCountRel = new HashMap<>(companyIdList.size());
			List<Record2<Integer, Integer>> positionCount = positionDao.countByCompanyIdList(companyIdList);
			if (positionCount != null && positionCount.size() > 0) {
				for (Record2<Integer, Integer> record2 : positionCount) {
					positionCountRel.put(record2.value1(), record2.value2());
				}
			}

			for (HrCompany company : companyList) {
				CompanyDetail companyDetail = new CompanyDetail();
				companyDetail.setCompany(company);
				OtherVO otherVO = new OtherVO();
				if (teamCountRel.get(company.getId()) != null) {
					otherVO.setTeamNum(teamCountRel.get(company.getId()));
				}
				if (positionCountRel.get(company.getId()) != null) {
					otherVO.setPositionNum(positionCountRel.get(company.getId()));
				}
				otherVO.setWeight(10000);
				companyDetail.setOther(otherVO);
				companyDetails.add(companyDetail);
			}
		} else {
			return jsonObject;
		}
		String str = JSONObject.toJSONString(jsonObject, config);
		return (JSONObject) JSONObject.parse(str);
	}

	/**
	 * 如果触发锁，那么循环等待redis的数据
	 * @param times 等待次数
	 * @param pattern 查询条件
	 * @return 备份方案的职位列表数据
	 * @throws InterruptedException 线程中断异常
	 */
	private JSONObject waitForCache(int times, String pattern) throws InterruptedException {
		while (times <= RETRY_UPPER_LIMIT) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
				Thread.currentThread().interrupt();
				return new JSONObject();
			}
			String json = FallBack.fetchFromCache(client, pattern);
			if (json != null) {
				return JSONObject.parseObject(json);
			} else {
				return waitForCache(times+1, pattern);
			}
		}
		return new JSONObject();
	}
}
