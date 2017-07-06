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
import org.elasticsearch.search.SearchHits;
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
	//通过queryString
    public SearchHits queryString(String keywords,String citys,String industry,String scale,Integer page,Integer pageSize) throws TException {
         try{
        	 TransportClient client=this.getEsClient();
        	 if(client!=null){
        		 QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
                 QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
                 boolean hasKey = false;
                 this.handleKeyWordforQueryString(keywords,hasKey,query);
                 this.handleCitys( citys,query);
                 this.handleIndustry(industry, query);
                 this.handleScale(scale, query);
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
       		 QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
                QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
                boolean hasKey = false;
                this.handleKeyWordForPrefix(keywords,hasKey,query);
                this.handleCitys( citys,query);
                this.handleIndustry(industry, query);
                this.handleScale(scale, query);
                SearchResponse response = client.prepareSearch("companys").setTypes("company")
                            .setQuery(query)
                            .addSort("_score", SortOrder.DESC)
                            .setFrom(page)
                            .setSize(pageSize).execute().actionGet();
                SearchHits hit=response.getHits();
                return hit;
       	 }
            
        }catch(Exception e){
       	 logger.info(e.getMessage(),e);
        }
   	return null;
   }
    private void handleKeyWordForPrefix(String keywords,boolean hasKey,QueryBuilder query){
    	QueryBuilder keyand = QueryBuilders.boolQuery();
		QueryBuilder fullf = QueryBuilders.prefixQuery("company.name", keywords);
		((BoolQueryBuilder) keyand).should(fullf);
		QueryBuilder fullf1 = QueryBuilders.prefixQuery("company.abbreviation", keywords);
		((BoolQueryBuilder) keyand).should(fullf1);
        ((BoolQueryBuilder) query).must(keyand);
    }
    
    //组装关键字
    private void handleKeyWordforQueryString(String keywords,boolean hasKey,QueryBuilder query){
    	if(!StringUtils.isEmpty(keywords)){
    		hasKey=true;
//            String[] keyword_list = keywords.split(",");
//            QueryBuilder keyand = QueryBuilders.boolQuery();
//            for (int i = 0; i < keyword_list.length; i++) {
//                String keyword = keyword_list[i];
//                QueryBuilder fullf = QueryBuilders.queryStringQuery(keyword)
//                        .field("company.name")
//                        .field("company.abbreviation");
//                ((BoolQueryBuilder) keyand).should(fullf);
//            }
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
