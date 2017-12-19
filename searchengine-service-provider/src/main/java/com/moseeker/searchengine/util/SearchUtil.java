package com.moseeker.searchengine.util;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.moseeker.common.util.EsClientInstance;
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

    public TransportClient getEsClient() {
        return EsClientInstance.getClient();
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
    public void hanleRange(int conditions, QueryBuilder query, String conditionField) {
        QueryBuilder cityfilter = QueryBuilders.rangeQuery(conditionField).gt(conditions);
        ((BoolQueryBuilder) query).must(cityfilter);
        logger.info("组合的条件是==================" + query.toString() + "===========");
    }

    public void hanleRange(long conditions, QueryBuilder query, String conditionField) {
        QueryBuilder cityfilter = QueryBuilders.rangeQuery(conditionField).gt(conditions);
        ((BoolQueryBuilder) query).must(cityfilter);
        logger.info("组合的条件是==================" + query.toString() + "===========");
    }

    public void hanleRangeFilter(long conditions, QueryBuilder query, String conditionField) {
        QueryBuilder cityfilter = QueryBuilders.rangeQuery(conditionField).gt(conditions);
        ((BoolQueryBuilder) query).filter(cityfilter);
        logger.info("组合的条件是==================" + query.toString() + "===========");
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
    //组装query_string关键字带权重查询语句
    public void keyWordforQueryStringPropery(String keywords,QueryBuilder query,List<String> fieldList,List<Integer> properyList){
        if(StringUtils.isNotEmpty(keywords)){
            String words[]=keywords.split(",");
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
    /*
     处理范围数据的查询语句
     */
    public void shoudRangeAgeOrDegreeListFilter(List<Map<String,Integer>> list,QueryBuilder query,String conditionField){
        if(list!=null&&list.size()>0){
            QueryBuilder keyand = QueryBuilders.boolQuery();
            for(Map<String,Integer> map:list){
                int max=  map.get("max");
                int min=  map.get("min");
                QueryBuilder fullf = QueryBuilders.rangeQuery(conditionField).gt(min).lt(max);
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
            }else{
                if(condition.length()>8){
                    QueryBuilder query0=QueryBuilders.matchQuery("user.origin_data",condition);
                    ((BoolQueryBuilder) keyand).should(query0);
                }else{
                    QueryBuilder query0=QueryBuilders.matchQuery("user.applications.origin",condition);
                    ((BoolQueryBuilder) keyand).should(query0);
                }
            }
        }
        ((BoolQueryBuilder) keyand).minimumNumberShouldMatch(1);
        ((BoolQueryBuilder) builder).filter(keyand);
        System.out.println(builder.toString());
    }

}
