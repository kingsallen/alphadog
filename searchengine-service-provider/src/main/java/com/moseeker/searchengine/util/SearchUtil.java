package com.moseeker.searchengine.util;

import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.EsClientInstance;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.*;
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

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchUtil {
	Logger logger = LoggerFactory.getLogger(this.getClass());
    //启动es客户端

    public TransportClient getEsClient() {
        return EsClientInstance.getClient();
    }

    public TransportClient getEsClient1() {
        TransportClient client=null;
        try {
            ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
            propertiesReader.loadResource("es.properties");
            String cluster_name = propertiesReader.get("es.cluster.name", String.class);
            String es_connection = propertiesReader.get("es.connection", String.class);
            Integer es_port = propertiesReader.get("es.port", Integer.class);
            Settings settings = Settings.settingsBuilder().put("cluster.name", cluster_name)
                    .put("client.transport.sniff", true)
                    .build();
            List<String> esList = stringConvertList(es_connection);
            for (int i = 0; i < esList.size(); i++) {
                if (i == 0) {
                    client = TransportClient.builder().settings(settings).build()
                            .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esList.get(i)), es_port));
                }
                client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esList.get(i)), es_port));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return client;
    }
    /*
     * 拼接city
     */
    public void handleTerms(String conditions,QueryBuilder query,String conditionField){
        if (StringUtils.isNotEmpty(conditions)) {
            List<Object> codes = new ArrayList<Object>();
            String[] conditions_list = conditions.split(",");
            for(String code:conditions_list){
                codes.add(code);
            }
            QueryBuilder cityfilter = QueryBuilders.termsQuery(conditionField, codes);
            ((BoolQueryBuilder) query).must(cityfilter);
        }
    }
    /*
     * 拼接city
     */
    public void handleTermsFilter(String conditions,QueryBuilder query,String conditionField){
        if (StringUtils.isNotEmpty(conditions)) {
            List<Object> codes = new ArrayList<Object>();
            String[] conditions_list = conditions.split(",");
            for(String code:conditions_list){
                codes.add(code);
            }
            QueryBuilder cityfilter = QueryBuilders.termsQuery(conditionField, codes);
            ((BoolQueryBuilder) query).filter(cityfilter);
        }
    }

    /*
     * 拼接city
     */
    public void handleTerm(String condition,QueryBuilder query,String conditionField){
        if (StringUtils.isNotEmpty(condition)) {
            QueryBuilder cityfilter = QueryBuilders.termsQuery(conditionField, condition);
            ((BoolQueryBuilder) query).must(cityfilter);
        }
    }
    public void handleTerm(List<String> condition,QueryBuilder query,String conditionField){
        if (condition!=null&&condition.size()>0) {
            QueryBuilder cityfilter = QueryBuilders.termsQuery(conditionField, condition);
            ((BoolQueryBuilder) query).must(cityfilter);
        }
    }
    public void handlerShouldTerm(String condition,QueryBuilder query,String conditionField){
        if (StringUtils.isNotEmpty(condition)) {
            QueryBuilder cityfilter = QueryBuilders.termQuery(conditionField, condition);
            ((BoolQueryBuilder) query).should(cityfilter);
        }
    }

    public void handleTagMatch(List<String> condition,QueryBuilder query,String conditionField){
        if (condition!=null&&condition.size()>0) {
            for(String items:condition){
                QueryBuilder cityfilter = QueryBuilders.matchQuery(conditionField, items);
                ((BoolQueryBuilder) query).must(cityfilter);
            }

        }
    }
    public void handleTermShould(String condition,QueryBuilder query,String conditionField){
        if (StringUtils.isNotEmpty(condition)) {
            QueryBuilder cityfilter = QueryBuilders.termsQuery(conditionField, condition);
            ((BoolQueryBuilder) query).should(cityfilter);
        }
    }
    public void handlerNotTerms(List<Integer> list,QueryBuilder query,String conditionField){
        if(list!=null&&list.size()>0){
            QueryBuilder cityfilter = QueryBuilders.termsQuery(conditionField,list);
            ((BoolQueryBuilder) query).mustNot(cityfilter);
        }
    }
    public void handleMatchParse(String condition,QueryBuilder query,String conditionField){
        if (StringUtils.isNotEmpty(condition)) {
            QueryBuilder cityfilter = QueryBuilders.matchPhraseQuery(conditionField, condition);
            ((BoolQueryBuilder) query).must(cityfilter);
        }
    }
    public void handleMatchParseShould(String condition,QueryBuilder query,String conditionField){
        if (StringUtils.isNotEmpty(condition)) {
            QueryBuilder cityfilter = QueryBuilders.matchPhraseQuery(conditionField, condition);
            ((BoolQueryBuilder) query).should(cityfilter);
        }
    }
    /*
     使用 filter的方式处理查询语句
     */
    public void handleTermFilter(String condition,QueryBuilder query,String conditionField){
        if (StringUtils.isNotEmpty(condition)) {
            QueryBuilder cityfilter = QueryBuilders.termsQuery(conditionField, condition);
            ((BoolQueryBuilder) query).filter(cityfilter);
        }
    }
    /*
        处理match的查询
     */
    public void handleMatch(int conditions,QueryBuilder query,String conditionField ){
        QueryBuilder cityfilter = QueryBuilders.matchQuery(conditionField, conditions);
        ((BoolQueryBuilder) query).must(cityfilter);
    }
    /*
        处理match的查询
     */
    public void handleMatch(String conditions,QueryBuilder query,String conditionField ){
        QueryBuilder cityfilter = QueryBuilders.matchQuery(conditionField, conditions);
        ((BoolQueryBuilder) query).must(cityfilter);
    }
    /*
     处理match的filter查询
     */
    public void handleMatchFilter(int conditions,QueryBuilder query,String conditionField ){
        QueryBuilder cityfilter = QueryBuilders.matchQuery(conditionField, conditions);
        ((BoolQueryBuilder) query).filter(cityfilter);
    }
    /*
     处理match的should查询
     */
    public void handleShouldMatchFilter(int conditions,QueryBuilder query,List<String> conditionFieldList ){
        QueryBuilder keyand = QueryBuilders.boolQuery();
        for(String field:conditionFieldList){
            QueryBuilder fullf = QueryBuilders.matchQuery(field, conditions);
            ((BoolQueryBuilder) keyand).should(fullf);
        }
        ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
        ((BoolQueryBuilder) query).filter(keyand);
    }
    /*
     处理一个字段多个值的查询
     */
    public void handleShouldMatchFilter(Map<String,List<String>> params, QueryBuilder query ){
        QueryBuilder keyand = QueryBuilders.boolQuery();
        for(String key:params.keySet()){
            List<String> list=params.get(key);
            for(String param:list){
                QueryBuilder fullf = QueryBuilders.matchQuery(key, param);
                ((BoolQueryBuilder) keyand).should(fullf);
            }

        }
        ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
        ((BoolQueryBuilder) query).filter(keyand);
    }
    public void hanleRange(int conditions, QueryBuilder query, String conditionField) {
        QueryBuilder cityfilter = QueryBuilders.rangeQuery(conditionField).gt(conditions);
        ((BoolQueryBuilder) query).must(cityfilter);
    }
    public void handlerRangeLess(int conditions, QueryBuilder query, String conditionField) {
        QueryBuilder cityfilter = QueryBuilders.rangeQuery(conditionField).lte(conditions);
        ((BoolQueryBuilder) query).must(cityfilter);
    }
    public void handlerRangeMore(int conditions, QueryBuilder query, String conditionField) {
        QueryBuilder cityfilter = QueryBuilders.rangeQuery(conditionField).gte(conditions);
        ((BoolQueryBuilder) query).must(cityfilter);
    }

    public void hanleRange(long conditions, QueryBuilder query, String conditionField) {
        QueryBuilder cityfilter = QueryBuilders.rangeQuery(conditionField).gt(conditions);
        ((BoolQueryBuilder) query).must(cityfilter);
    }

    public void hanleRangeFilter(String conditions, QueryBuilder query, String conditionField) {
        QueryBuilder cityfilter = QueryBuilders.rangeQuery(conditionField).gt(conditions);
        ((BoolQueryBuilder) query).filter(cityfilter);
    }
    public void hanleRangeLTFilter(String conditions, QueryBuilder query, String conditionField) {
        QueryBuilder cityfilter = QueryBuilders.rangeQuery(conditionField).lt(conditions);
        ((BoolQueryBuilder) query).filter(cityfilter);
    }

    public void hanleRangeFilter(long conditions, QueryBuilder query, String conditionField) {
        QueryBuilder cityfilter = QueryBuilders.rangeQuery(conditionField).gt(conditions);
        ((BoolQueryBuilder) query).filter(cityfilter);
    }

    public void hanleGtRange(String conditions, QueryBuilder query, String conditionField) {
        QueryBuilder cityfilter = QueryBuilders.rangeQuery(conditionField).gt(conditions);
        ((BoolQueryBuilder) query).must(cityfilter);
    }
    public void hanleLtRange(String conditions, QueryBuilder query, String conditionField) {
        QueryBuilder cityfilter = QueryBuilders.rangeQuery(conditionField).lt(conditions);
        ((BoolQueryBuilder) query).must(cityfilter);
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
    	if(aggs!=null){
            Map<String, Object> aggsMap=handleAggs(aggs);
            data.put("aggs", aggsMap);
        }
    	SearchHits hit=response.getHits();
    	long totalNum=hit.getTotalHits();
    	data.put("totalNum", totalNum);
    	SearchHit[] searchData=hit.getHits();
    	if(totalNum>0){
    		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
    		if(searchData!=null&&searchData.length>0){
    		for(SearchHit ss:searchData){
    			Map<String,Object> obj=ss.getSource();
    			list.add(obj);
    		}
            }
    		data.put(dataName, list);
    	}
    	return data;
    }
    //处理统计数据
    public Map<String,Object> handleAggData(SearchResponse response){
        Map<String,Object> data=new HashMap<String,Object>();
        Aggregations aggs=response.getAggregations();
        Map<String, Object> aggsMap=handleAggs(aggs);
        data.put("aggs", aggsMap);
        return data;
    }
    //组装prefix关键字查询语句
    public void handleKeyWordForPrefix(String keywords,boolean hasKey,QueryBuilder query,List<String> list){
    	if(StringUtils.isNotEmpty(keywords)&&!"".equals(keywords.trim())){
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
    	if(StringUtils.isNotEmpty(keywords)&&!"".equals(keywords.trim())){
    		hasKey=true;
            keywords=keywords.trim();
            String words[]=keywords.split(",");
    		QueryBuilder keyand = QueryBuilders.boolQuery();
    		StringBuffer sb=new StringBuffer();
    		for(int i=0;i<words.length;i++){
    		    if(StringUtils.isBlank(words[i])){
    		        continue;
                }
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
    //组装query_string关键字查询语句,重载方法
    public void handleKeyWordforQueryString(String keywords,boolean hasKey,QueryBuilder query,Map<String,Float> fieldBootMap){
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
            if(fieldBootMap!=null&&!fieldBootMap.isEmpty()){
                for(String key:fieldBootMap.keySet()){
                    fullf.field(key,fieldBootMap.get(key));
                }
            }

            ((BoolQueryBuilder) keyand).must(fullf);
            ((BoolQueryBuilder) query).must(keyand);
        }
    }
    //组装query_string关键字带权重查询语句
    public void keyWordforQueryStringPropery(String keywords,QueryBuilder query,List<String> fieldList,List<Integer> properyList){
        if(StringUtils.isNotEmpty(keywords)&&!"".equals(keywords)){
            keywords=keywords.trim();
            String words[]=keywords.split(",");
            StringBuffer sb=new StringBuffer();
            for(int i=0;i<words.length;i++){
                if(StringUtils.isBlank(words[i])){
                    continue;
                }
                if(i==words.length-1){
                    sb.append(words[i]);
                }else{
                    sb.append(words[i]+" or ");
                }
            }
//            if(words.length>1){
//                sb.deleteCharAt(sb.lastIndexOf("r"));
//                sb.deleteCharAt(sb.lastIndexOf("o"));
//            }
            String condition=sb.toString();
            QueryStringQueryBuilder fullf = QueryBuilders.queryStringQuery(condition);
            if(fieldList!=null&&fieldList.size()>0){
                for(int i=0;i<fieldList.size();i++){
                    fullf.field(fieldList.get(i),properyList.get(i));
                }
            }

            ((BoolQueryBuilder) query).must(fullf);
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
    	sb1.append("if(ss in jsay||!ss){}");
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

    public AbstractAggregationBuilder handleIndustry(String name){
        StringBuffer sb=new StringBuffer();
        sb.append("scale_new=_source.company; ");
        sb.append("if(scale_new){scale=scale_new['industry_data'];");
        sb.append("if(scale  in _agg['transactions'] ){}");
        sb.append("else{_agg['transactions'].add(scale)};};");
        String mapScript=sb.toString();
        StringBuffer sb1=new StringBuffer();
        sb1.append("jsay=[];");
        sb1.append("for(a in _aggs){");
        sb1.append("for(ss in a){");
        sb1.append("if(ss in jsay||!ss){}");
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

    public AbstractAggregationBuilder handleArray(String fieldName, String name) {
        StringBuffer sb = new StringBuffer();
        sb.append("city=" + fieldName);
        sb.append(";for(ss in city){");
        sb.append("if(ss  in _agg['transactions'] || !ss ){}");
        sb.append("else{_agg['transactions'].add(ss)};}");
        String mapScript = sb.toString();
        StringBuffer sb1 = new StringBuffer();
        sb1.append("jsay=[];");
        sb1.append("for(a in _aggs){");
        sb1.append("for(ss in a){");
        sb1.append("if(ss in jsay||!ss){}");
        sb1.append("else{jsay.add(ss);}}};");
        sb1.append("return jsay");
        String reduceScript = sb1.toString();
        StringBuffer sb2 = new StringBuffer();
        sb2.append("jsay=[];");
        sb2.append("for(ss in _agg['transactions']){");
        sb2.append("for(a in ss){jsay.add(ss)}};");
        sb2.append("return jsay");
        String combinScript = sb2.toString();
        MetricsAggregationBuilder build = AggregationBuilders.scriptedMetric(name)
                .initScript(new Script("_agg['transactions'] = []"))
                .mapScript(new Script(mapScript))
                .reduceScript(new Script(reduceScript))
                .combineScript(new Script(combinScript));
        return build;
    }

    public void shouldQuery(Map<String,Object> map,QueryBuilder query){
    	if(map!=null&&!map.isEmpty()){
			QueryBuilder keyand = QueryBuilders.boolQuery();
			for(String key:map.keySet()){
				QueryBuilder fullf = QueryBuilders.termsQuery(key,map.get(key));
				((BoolQueryBuilder) keyand).should(fullf);
			}
			((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
			((BoolQueryBuilder) query).must(keyand);
		}

	}

    /**
     * term查询，查询的值包含单个值
     *
     * @param map
     * @param query
     */
    public void matchPhrasePrefixQuery(Map<String, Object> map, QueryBuilder query) {
        if (map != null && !map.isEmpty()) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            for (String key : map.keySet()) {
                QueryBuilder fullf = QueryBuilders.matchPhrasePrefixQuery(key, map.get(key));
                ((BoolQueryBuilder) keyand).should(fullf);
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).must(keyand);
        }

    }

    /*
     * wildcard
     * @param map
     * @param query
     */
    public void wildcardQuery(Map<String, Object> map, QueryBuilder query) {
        if (map != null && !map.isEmpty()) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            for (String key : map.keySet()) {
                QueryBuilder fullf = QueryBuilders.wildcardQuery(key, "*"+map.get(key)+"*");
                ((BoolQueryBuilder) keyand).should(fullf);
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).must(keyand);
        }

    }
    /**
     * term查询，查询的值包含单个值
     *
     * @param map
     * @param query
     */
    public void shouldTermQuery(Map<String, Object> map, QueryBuilder query) {
        if (map != null && !map.isEmpty()) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            for (String key : map.keySet()) {
                QueryBuilder fullf = QueryBuilders.termsQuery(key, map.get(key));
                ((BoolQueryBuilder) keyand).should(fullf);
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).must(keyand);
        }
    }
    public void shouldTermsQuery(List<String> fieldsList,List<Integer>dataIdList, QueryBuilder query) {
        if (fieldsList!=null&&fieldsList.size()>0&&dataIdList!=null&&dataIdList.size()>0) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            for (String fields : fieldsList) {
                QueryBuilder fullf = QueryBuilders.termsQuery(fields, dataIdList);
                ((BoolQueryBuilder) keyand).should(fullf);
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).must(keyand);
        }
    }
    public void shouldTermsQuery(List<String>dataIdList, QueryBuilder query,String conditionField) {
        if (dataIdList!=null&&dataIdList.size()>0) {
            QueryBuilder fullf = QueryBuilders.termsQuery(conditionField, dataIdList);
            ((BoolQueryBuilder) query).should(fullf);
        }
    }

    public void exitsisQuery(List<String> list, QueryBuilder query) {
       if(list!=null&&list.size()>0){
          for(String filed:list){
              QueryBuilder keyand = QueryBuilders.existsQuery(filed);
              ((BoolQueryBuilder) query).must(keyand);
          }
       }
    }

    //terms查询，查询的值包含多个值
    public void shouldTermsQuery(Map<String, Object> map, QueryBuilder query) {
        if (map != null && !map.isEmpty()) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            for (String key : map.keySet()) {
                List<String> list = this.stringConvertList(map.get(key) + "");
                if (list == null || list.size() == 0) {
                    continue;
                }
                QueryBuilder fullf = QueryBuilders.termsQuery(key, list);
                ((BoolQueryBuilder) keyand).should(fullf);
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).must(keyand);
        }
    }
    /*
     should的terms的语句重载
     */
    public QueryBuilder shouldTermsQuery(List<String> fieldsList,List<String>dataIdList) {
        if (fieldsList!=null&&fieldsList.size()>0&&dataIdList!=null&&dataIdList.size()>0) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            for (String fields : fieldsList) {
                for(String condition: dataIdList){
                    QueryBuilder fullf = QueryBuilders.termsQuery(fields, condition);
                    ((BoolQueryBuilder) keyand).should(fullf);
                }

            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            return keyand;
        }
        return null;
    }
    public void shouldMatchQuery(Map<String, Object> map, QueryBuilder query){
        if (map != null && !map.isEmpty()) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            for (String key : map.keySet()) {
                String list=(String)map.get(key);
                QueryBuilder fullf = QueryBuilders.matchQuery(key, list);
                ((BoolQueryBuilder) keyand).should(fullf);
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).must(keyand);
        }
    }
    public void shouldExistsQuery(List<String> list, QueryBuilder query){
        if (list!=null&&list.size()>0) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            for (String key : list) {
                QueryBuilder fullf = QueryBuilders.existsQuery(key);
                ((BoolQueryBuilder) keyand).should(fullf);
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).must(keyand);
        }
    }
    public void shouldMatchParseQuery(Map<String, Object> map, QueryBuilder query){
        if (map != null && !map.isEmpty()) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            for (String key : map.keySet()) {
                String list=(String)map.get(key);
                QueryBuilder fullf = QueryBuilders.matchPhraseQuery(key, list);
                ((BoolQueryBuilder) keyand).should(fullf);
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).must(keyand);
        }
    }
    public void shouldMatchParseQuery(Map<String, Integer> map,String condition, QueryBuilder query){
        if (map != null && !map.isEmpty()&&condition!=null&&condition.length()>0) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            for (String key : map.keySet()) {
                Integer boost=map.get(key);
                String[] keywords=condition.split(",");
                for(String keyword:keywords){
                    QueryBuilder fullf = QueryBuilders.matchPhraseQuery(key, keyword).boost(boost);
                    ((BoolQueryBuilder) keyand).should(fullf);
                }

            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).must(keyand);
        }
    }
    /*
     should的terms的语句重载
     */
    public QueryBuilder shouldMatchQuery(List<String> fieldsList,List<String>dataIdList) {
        if (fieldsList!=null&&fieldsList.size()>0&&dataIdList!=null&&dataIdList.size()>0) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            for (String fields : fieldsList) {
                for(String condition: dataIdList){
                    QueryBuilder fullf = QueryBuilders.matchQuery(fields, condition);
                    ((BoolQueryBuilder) keyand).should(fullf);
                }

            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            return keyand;
        }
        return null;
    }
    public QueryBuilder shouldMatchParseQuery(List<String> fieldsList,List<String>dataIdList) {
        if (fieldsList!=null&&fieldsList.size()>0&&dataIdList!=null&&dataIdList.size()>0) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            for (String fields : fieldsList) {
                for(String condition: dataIdList){
                    QueryBuilder fullf = QueryBuilders.matchPhraseQuery(fields, condition);
                    ((BoolQueryBuilder) keyand).should(fullf);
                }

            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            return keyand;
        }
        return null;
    }
    public void shouldMatchQuery(List<String> fieldList,String condition ,QueryBuilder query){
        if (fieldList!=null&&fieldList.size()>0) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            String array[]=condition.split(",");
            for(String items:array){
                for(String field:fieldList){
                    QueryBuilder fullf = QueryBuilders.matchQuery(field, items);
                    ((BoolQueryBuilder) keyand).should(fullf);
                }
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).must(keyand);
        }
    }
    public void shouldMatchParseQuery(List<String> fieldList,String condition ,QueryBuilder query){
        if (fieldList!=null&&fieldList.size()>0) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            String array[]=condition.split(",");
            for(String items:array){
                for(String field:fieldList){
                    QueryBuilder fullf = QueryBuilders.matchPhraseQuery(field, items);
                    ((BoolQueryBuilder) keyand).should(fullf);
                }
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).must(keyand);
        }
    }
    public void shouldMatchParseQueryShould(List<String> fieldList,String condition ,QueryBuilder query){
        if (fieldList!=null&&fieldList.size()>0) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            String array[]=condition.split(",");
            for(String items:array){
                for(String field:fieldList){
                    QueryBuilder fullf = QueryBuilders.matchPhraseQuery(field, items);
                    ((BoolQueryBuilder) keyand).should(fullf);
                }
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).should(keyand);
        }
    }
    public void shouldMatchParseQueryShould(String field,String condition ,QueryBuilder query){
        if (StringUtils.isNotBlank(condition)) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            String array[]=condition.split(",");
            for(String items:array){
                QueryBuilder fullf = QueryBuilders.matchPhraseQuery(field, items);
                ((BoolQueryBuilder) keyand).should(fullf);
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).should(keyand);
        }
    }

    public void shouldMatchParseQuery(String field,String condition ,QueryBuilder query){
        if (StringUtils.isNotBlank(condition)) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            String array[]=condition.split(",");
            for(String items:array){
                QueryBuilder fullf = QueryBuilders.matchPhraseQuery(field, items);
                ((BoolQueryBuilder) keyand).should(fullf);
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).must(keyand);
        }
    }
    public QueryBuilder shouldMatchParseQuery(List<String> fieldList,String condition ){
        if (fieldList!=null&&fieldList.size()>0) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            String array[]=condition.split(",");
            for(String items:array){
                for(String field:fieldList){
                    QueryBuilder fullf = QueryBuilders.matchPhraseQuery(field, items);
                    ((BoolQueryBuilder) keyand).should(fullf);
                }
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            return keyand;
        }
        return null;
    }

    public QueryBuilder shouldTermsQuery(List<String> fieldList,String condition ){
        if (fieldList!=null&&fieldList.size()>0) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            String array[]=condition.split(",");
            for(String items:array){
                for(String field:fieldList){
                    QueryBuilder fullf = QueryBuilders.termQuery(field, items);
                    ((BoolQueryBuilder) keyand).should(fullf);
                }
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            return keyand;
        }
        return null;
    }

    //将xx,xx,xx格式的字符串转化为list
    public List<String> stringConvertList(String keyWords) {
        if (StringUtils.isNotEmpty(keyWords)) {
            String[] array = keyWords.split(",");
            List<String> list = new ArrayList<String>();
            for (String ss : array) {
                list.add(ss);
            }
            return list;
        }
        return null;
    }
    //将xx,xx,xx格式的字符串转化为list
    public List<Integer> stringConvertIntList(String keyWords) {
        if (StringUtils.isNotEmpty(keyWords)) {
            String[] array = keyWords.split(",");
            List<Integer> list = new ArrayList<Integer>();
            for (String ss : array) {
                list.add(Integer.parseInt(ss));
            }
            return list;
        }
        return null;
    }
    //将list格式的字符串转化为xx,xx,xx
    public String listConvertString(List<Integer> positionIdList) {
        if (positionIdList!=null&&positionIdList.size()>0) {
            String positionIds="";
            for(Integer id:positionIdList){
                positionIds+=id+",";
            }
            if(StringUtils.isNotBlank(positionIds)){
                positionIds=positionIds.substring(0,positionIds.lastIndexOf(","));
            }
            return positionIds;
        }
        return null;
    }
    /*
     处理工作年龄数据的查询语句
     */
    public void shoudWorkYearsListFilter(List<Map<String,Integer>> list,QueryBuilder query,String conditionField){
        if(list!=null&&list.size()>0){
            QueryBuilder keyand = QueryBuilders.boolQuery();
            for(Map<String,Integer> map:list){
                int max=  map.get("max");
                int min=  map.get("min");
                if(max==min&&max==0){
                    QueryBuilder fullf = QueryBuilders.matchQuery("user.is_fresh_graduates",1);
                    ((BoolQueryBuilder) keyand).should(fullf);
                }else{
                    QueryBuilder fullf = QueryBuilders.rangeQuery(conditionField).gt(min).lte(max);
                    ((BoolQueryBuilder) keyand).should(fullf);
                }

            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).filter(keyand);
        }
    }
    /*
    按照年龄查询
     */
    public void shoudAgeFilter(List<Map<String,Integer>> list,QueryBuilder query,String conditionField){
        if(list!=null&&list.size()>0){
            QueryBuilder keyand = QueryBuilders.boolQuery();
            for(Map<String,Integer> map:list){
                int max=  map.get("max");
                int min=  map.get("min");
                QueryBuilder fullf = QueryBuilders.rangeQuery(conditionField).gt(min).lte(max);
                ((BoolQueryBuilder) keyand).should(fullf);
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).filter(keyand);
        }
    }
    /*
    专门处理来源的问题
     */
    public void handleOrigins(String conditions,String companyId,QueryBuilder builder){
        List<String> list=this.stringConvertList(conditions);
        QueryBuilder keyand = QueryBuilders.boolQuery();
        for(String condition:list){
            QueryBuilder keyand1 = QueryBuilders.boolQuery();
            if("1".equals(condition)){
                QueryBuilder query0=QueryBuilders.matchQuery("user.upload",1);
                ((BoolQueryBuilder) keyand).should(query0);
            }else if("-99".equals(condition)||"99".equals(condition)){
                List<Integer>  conditionList=new ArrayList<>();
                conditionList.add(1);
                conditionList.add(2);
                conditionList.add(4);
                conditionList.add(128);
                conditionList.add(256);
                conditionList.add(512);
                conditionList.add(1024);
                QueryBuilder query0=QueryBuilders.termsQuery("user.applications.origin",conditionList);
                ((BoolQueryBuilder) keyand).should(query0);
            }else{
                if(condition.length()>8){
                    QueryBuilder query0=QueryBuilders.termQuery("user.origin_data",condition);
                    ((BoolQueryBuilder) keyand).should(query0);
                }else{
                    QueryBuilder query0=QueryBuilders.matchQuery("user.applications.origin",Long.parseLong(condition));
                    ((BoolQueryBuilder) keyand).should(query0);
                }
            }
        }
        ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
        ((BoolQueryBuilder) builder).filter(keyand);
        System.out.println(builder.toString());
    }
    /*
     处理按照标签查询
     */
    public void handlerTagIds(String tagIds,String hrId,QueryBuilder builder){
        List<String> tagIdList=this.stringConvertList(tagIds);
        if(tagIdList.size()>1 &&tagIdList.contains("alltalent")){
            tagIdList.remove("alltalent");
        }
        if(tagIdList != null && tagIdList.size() >0 && !tagIdList.contains("alltalent")){

            if(tagIdList.size()==1){
                if(tagIdList.contains("allpublic")){
                    handleMatch(1,builder,"user.talent_pool.is_public");
                }else if(tagIdList.contains("talent")){
                    handleMatch(Integer.parseInt(hrId),builder,"user.talent_pool.hr_id");
                    handleMatch(1,builder,"user.talent_pool.is_talent");
                }else{
                        handleMatch(Integer.parseInt(tagIdList.get(0)),builder,"user.talent_pool.tags.id");
                }

            }else{
                QueryBuilder keyand = QueryBuilders.boolQuery();
                if(tagIdList.contains("allpublic")){
                    QueryBuilder query0=QueryBuilders.matchQuery("user.talent_pool.is_public",1);
                    ((BoolQueryBuilder) keyand).should(query0);
                    tagIdList.remove("allpublic");
                }
                if(tagIdList.size()>0&&tagIdList.contains("talent")){
                    QueryBuilder keyand1 = QueryBuilders.boolQuery();
                    QueryBuilder query1=QueryBuilders.matchQuery("user.talent_pool.hr_id",Integer.parseInt(hrId));
                    ((BoolQueryBuilder) keyand1).must(query1);
                    QueryBuilder query2=QueryBuilders.matchQuery("user.talent_pool.is_talent",1);
                    ((BoolQueryBuilder) keyand1).must(query2);
                    ((BoolQueryBuilder) keyand).should(keyand1);
                    tagIdList.remove("talent");
                }
                if(tagIdList.size()>0){
                    QueryBuilder query2=QueryBuilders.termsQuery("user.talent_pool.tags.id",tagIdList);
                    ((BoolQueryBuilder) keyand).must(query2);

                }
                ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
                ((BoolQueryBuilder) builder).must(keyand);
            }
        }
    }
    /*
     对子账号查询公开的或子账号收藏的人才
     */
    public void childAccountTalentpool(String hrId,QueryBuilder builder ){
        //查询这个公司公开的
        QueryBuilder keyand = QueryBuilders.boolQuery();
        QueryBuilder query0=QueryBuilders.matchQuery("user.talent_pool.is_public",1);
        ((BoolQueryBuilder) keyand).should(query0);
        //查人这个账号收藏的
        QueryBuilder keyand1 = QueryBuilders.boolQuery();
        QueryBuilder query1=QueryBuilders.matchQuery("user.talent_pool.hr_id",Integer.parseInt(hrId));
        ((BoolQueryBuilder) keyand1).must(query1);
        QueryBuilder query2=QueryBuilders.matchQuery("user.talent_pool.is_talent",1);
        ((BoolQueryBuilder) keyand1).must(query2);
        ((BoolQueryBuilder) keyand).should(keyand1);
        ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
        ((BoolQueryBuilder) builder).must(keyand);
    }

    /*
     处理按照标签查询
     */
    public void handlerCompanyTag(String CompanyTag,QueryBuilder builder){
        List<String> tagIdList=this.stringConvertList(CompanyTag);

        if(tagIdList != null && tagIdList.size() >0){
            for(String item:tagIdList){
                QueryBuilder query2=QueryBuilders.matchQuery("user.company_tag.id",item);
                ((BoolQueryBuilder) builder).must(query2);
            }

        }
    }

    public void handlerHrAutoTag(String hrAutoTag,QueryBuilder builder){
        List<String> tagIdList=this.stringConvertList(hrAutoTag);
        if(tagIdList != null && tagIdList.size() >0){
            for(String item:tagIdList){
                QueryBuilder query2=QueryBuilders.matchQuery("user.hr_auto_tag.id",item);
                ((BoolQueryBuilder) builder).must(query2);
            }

        }
    }

    public void matchPhrasePrefixQuery(List<String> fieldList,String condition ,QueryBuilder query){
        if (fieldList!=null&&fieldList.size()>0) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            String array[]=condition.split(",");
            for(String items:array){
                for(String field:fieldList){
                    QueryBuilder fullf = QueryBuilders.matchPhrasePrefixQuery(field, items);
                    ((BoolQueryBuilder) keyand).should(fullf);
                }
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).must(keyand);
        }
    }

    public void matchPhrasePrefixQueryV2(List<String> fieldList,String condition ,QueryBuilder query){
        if (fieldList!=null&&fieldList.size()>0) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            String array[]=condition.split(",");
            for(String items:array){
                for(String field:fieldList){
                    QueryBuilder fullf = QueryBuilders.wildcardQuery(field, "*"+items+"*");
                    ((BoolQueryBuilder) keyand).should(fullf);
                }
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).must(keyand);
        }
    }

    public void matchPhrasePrefixQueryV3(List<String> fieldList,String condition ,QueryBuilder query){
        if (fieldList!=null&&fieldList.size()>0) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            for(String field:fieldList){
                QueryBuilder fullf = QueryBuilders.wildcardQuery(field, "*"+condition+"*");
                ((BoolQueryBuilder) keyand).should(fullf);
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).must(keyand);
        }
    }

    public void shouldTermsQueryString(List<String> fieldsList,List<String>dataIdList, QueryBuilder query) {
        if (fieldsList!=null&&fieldsList.size()>0&&dataIdList!=null&&dataIdList.size()>0) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            for (String fields : fieldsList) {
                QueryBuilder fullf = QueryBuilders.termsQuery(fields, dataIdList);
                ((BoolQueryBuilder) keyand).should(fullf);
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).must(keyand);
        }
    }




    public void queryMatchPrefixSingle(String fieldName,String condition,QueryBuilder query){
        MatchQueryBuilder search = QueryBuilders.matchPhrasePrefixQuery(fieldName, condition);
        search.maxExpansions(0);
        ((BoolQueryBuilder) query).must(search);
    }

    public void convertSearchNameScript(String condition,QueryBuilder query){
        StringBuffer sb=new StringBuffer();
        sb.append("user=_source.user; if(user){profiles=user.profiles;if(profiles){basic=profiles.basic;if(basic){name=basic.name;if(name&&name=='"+condition+"'){return true;}}};return false;}");
        ScriptQueryBuilder script=new ScriptQueryBuilder(new Script(sb.toString()));
        ((BoolQueryBuilder) query).filter(script);
    }

    public void shouldWildCard(List<String> fieldNameList,String condition,QueryBuilder query){
        if (fieldNameList!=null&&fieldNameList.size()>0) {
            QueryBuilder keyand = QueryBuilders.boolQuery();
            for (String fields : fieldNameList) {
                QueryBuilder fullf = QueryBuilders.wildcardQuery(fields, "*"+condition+"*");
                ((BoolQueryBuilder) keyand).should(fullf);
            }
            ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
            ((BoolQueryBuilder) query).must(keyand);
        }
    }

    public void searchNewPositionDataGroup(String condition,QueryBuilder query){
        QueryBuilder keyand = QueryBuilders.boolQuery();

        MatchQueryBuilder otherWorkexpJobMatch=QueryBuilders.matchQuery("user.profiles.other_workexps.job_name_data",condition);
        otherWorkexpJobMatch.boost(1);
        ((BoolQueryBuilder) keyand).should(otherWorkexpJobMatch);

        MatchQueryBuilder otherWorkexpJobMatchparse=QueryBuilders.matchPhraseQuery("user.profiles.other_workexps.job_name_data",condition);
        otherWorkexpJobMatchparse.boost(10);
        ((BoolQueryBuilder) keyand).should(otherWorkexpJobMatchparse);

        MatchQueryBuilder recentJobMatch=QueryBuilders.matchQuery("user.profiles.recent_job.job_name_data",condition);
        recentJobMatch.boost(2);
        ((BoolQueryBuilder) keyand).should(recentJobMatch);

        MatchQueryBuilder recentJobMatchParse=QueryBuilders.matchPhraseQuery("user.profiles.recent_job.job_name_data",condition);
        recentJobMatchParse.boost(20);
        ((BoolQueryBuilder) keyand).should(recentJobMatchParse);

        ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
        ((BoolQueryBuilder) query).must(keyand);
    }

    public void handlerProfilePoolId(String profilePoolId, QueryBuilder queryBuilder) {
        if(!StringUtils.isNumeric(profilePoolId) ||  "0".equals(profilePoolId)) {    // todo 这个兼容一下，之后最好去掉
            return;
        }
        List<String> profilePoolIdList=this.stringConvertList(profilePoolId);
        if(profilePoolIdList != null && profilePoolIdList.size() >0){
            QueryBuilder query2=QueryBuilders.matchQuery("user.talent_pool.profile_pool_id",profilePoolIdList);
            ((BoolQueryBuilder) queryBuilder).must(query2);
        }
    }

    public void handlerCompanyManualTag(String companyManualTag, QueryBuilder queryBuilder) {
        List<String> tagIdList=this.stringConvertList(companyManualTag);
        if(tagIdList != null && tagIdList.size() >0){
            for(String item:tagIdList){
                QueryBuilder query2=QueryBuilders.matchQuery("user.company_manual_tag_user.tag_id",item);
                ((BoolQueryBuilder) queryBuilder).must(query2);
            }

        }
    }
}
