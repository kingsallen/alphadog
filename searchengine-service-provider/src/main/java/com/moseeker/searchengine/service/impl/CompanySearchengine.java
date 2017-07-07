package com.moseeker.searchengine.service.impl;

import java.net.InetAddress;
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
import org.elasticsearch.search.SearchHits;
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
	public SearchHits  query(String keywords,String citys,String industry,String scale,Integer page,Integer pageSize) throws TException{
		SearchHits hits=queryPrefix(keywords,citys,industry,scale,page,pageSize);
		long hitNum=hits.getTotalHits();
		if(hitNum==0){
			SearchHits hitsData=queryString(keywords,citys,industry,scale,page,pageSize);
			return hitsData;
		}else{
			return hits;
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
    public SearchHits queryString(String keywords,String citys,String industry,String scale,Integer page,Integer pageSize) throws TException {
         try{
        	 TransportClient client=this.getEsClient();
        	 if(client!=null){
        		 QueryBuilder query=this.buildQueryForString(keywords, citys, industry, scale);
                 SearchRequestBuilder responseBuilder=client.prepareSearch("companys").setTypes("company")
                         .setQuery(query)
                         .addSort("_score", SortOrder.DESC)
                         .setFrom(page)
                         .setSize(pageSize);
                 logger.info(responseBuilder.toString());
                 SearchResponse response = responseBuilder.execute().actionGet();
                 SearchHits hit=response.getHits();
                 return hit;
        	 }
             
         }catch(Exception e){
        	 logger.info(e.getMessage(),e);
         }
    	return null;
    }
    
   
    //通过prefix搜索
    public SearchHits queryPrefix(String keywords,String citys,String industry,String scale,Integer page,Integer pageSize) throws TException {
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
                        .setSize(pageSize);
                logger.info(responseBuilder.toString());
                SearchResponse response = responseBuilder.execute().actionGet();
                SearchHits hit=response.getHits();
                return hit;
       	 }
            
        }catch(Exception e){
       	 logger.info(e.getMessage(),e);
        }
   	return null;
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
}
