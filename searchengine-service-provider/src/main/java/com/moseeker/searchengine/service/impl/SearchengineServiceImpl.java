package com.moseeker.searchengine.service.impl;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.apache.commons.lang.StringUtils ;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Exception;
import com.alibaba.fastjson.JSON;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices.Iface;

@Service
public class SearchengineServiceImpl implements Iface {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Response query(String keywords, String cities, String industries, String occupations, String scale,
            String employment_type, String candidate_source, String experience, String degree, String salary,
            String company_name, int page_from, int page_size,String child_company_name,String department,boolean order_by_priority,String custom) throws TException {
        
        List  listOfid = new ArrayList();
        
        if (page_from == 0) {
            page_from = 0;
        }
        if (page_size == 0) {
            page_size = 20;
        }
        SearchResponse response = null;
        ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
        try {
            propertiesReader.loadResource("es.properties");
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String cluster_name = propertiesReader.get("es.cluster.name", String.class);
        String es_connection = propertiesReader.get("es.connection", String.class);
        Integer es_port = propertiesReader.get("es.port", Integer.class);
        
        try {

            Settings settings = Settings.settingsBuilder().put("cluster.name", cluster_name)
                    .build();

            TransportClient client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(es_connection), es_port));


            QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
            QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
            
            
            if ( !StringUtils.isEmpty(keywords)) {
                String[] keyword_list = keywords.split(" ");
                QueryBuilder keyand = QueryBuilders.boolQuery();
                for (int i = 0; i < keyword_list.length; i++) {
                    String keyword = keyword_list[i];
                    QueryBuilder keyfilter = QueryBuilders.simpleQueryStringQuery(keyword);
                    ((BoolQueryBuilder) keyand).should(keyfilter);
                }
                ((BoolQueryBuilder) query).must(keyand);
            }
            


            if (!StringUtils.isEmpty(cities)) {
                String[] city_list = cities.split(",");
                QueryBuilder cityor = QueryBuilders.boolQuery();
                for (int i = 0; i < city_list.length; i++) {
                    String city = city_list[i];
                    System.out.println(city);
                    QueryBuilder cityfilter = QueryBuilders.termQuery("city", city);
                    QueryBuilder cityboosting =QueryBuilders. boostingQuery()
                            .positive(cityfilter)
                            .negative(QueryBuilders.termQuery("title",city)) .negativeBoost(0.5f);
                            
                    ((BoolQueryBuilder) cityor).should(cityboosting);
                }
                ((BoolQueryBuilder) query).must(cityor);
            }
            
            if (!StringUtils.isEmpty(industries)) {
                String[] industry_list = industries.split(",");
                QueryBuilder industryor = QueryBuilders.boolQuery();
                for (int i = 0; i < industry_list.length; i++) {
                    String industry = industry_list[i];
                    QueryBuilder industryfilter = QueryBuilders.matchQuery("industry", industry);
                    ((BoolQueryBuilder) industryor).should(industryfilter);
                }
                ((BoolQueryBuilder) query).must(industryor);
            }

            if ( !StringUtils.isEmpty(occupations)) {
                String[] occupation_list = occupations.split(",");
                QueryBuilder occupationor = QueryBuilders.boolQuery();
                for (int i = 0; i < occupation_list.length; i++) {
                    String occupation = occupation_list[i];
                    QueryBuilder occupationfilter = QueryBuilders.matchQuery("occupation", occupation);
                    ((BoolQueryBuilder) occupationor).should(occupationfilter);
                }
                ((BoolQueryBuilder) query).must(occupationor);
            }

            if( !StringUtils.isEmpty(scale)){
                QueryBuilder scalefilter = QueryBuilders.matchQuery("scale", scale);
                ((BoolQueryBuilder) query).must(scalefilter);
            }
            
            if( !StringUtils.isEmpty(employment_type)){
                QueryBuilder employmentfilter = QueryBuilders.matchQuery("employment_type_name", employment_type);
                ((BoolQueryBuilder) query).must(employmentfilter);
            }

            if ( !StringUtils.isEmpty(candidate_source)) {
                QueryBuilder candidatefilter = QueryBuilders.matchQuery("candidate_source_name", candidate_source);
                ((BoolQueryBuilder) query).must(candidatefilter);
            }
            
            if ( !StringUtils.isEmpty(department)) {
                QueryBuilder departmentfilter = QueryBuilders.matchQuery("department", department);
                ((BoolQueryBuilder) query).must(departmentfilter);
            }
            
            if( !StringUtils.isEmpty(experience)){
                QueryBuilder experiencefilter = QueryBuilders.matchQuery("experience", experience);
                ((BoolQueryBuilder) query).must(experiencefilter);
            }
            
            
            if (!StringUtils.isEmpty(degree)) {
                String[] degree_list = degree.split(",");
                QueryBuilder degreeor = QueryBuilders.boolQuery();
                for (int i = 0; i < degree_list.length; i++) {
                    String degree_name = degree_list[i];
                    QueryBuilder degreefilter = QueryBuilders.termQuery("degree_name", degree_name);
                    ((BoolQueryBuilder) degreeor).should(degreefilter);
                }
                ((BoolQueryBuilder) query).must(degreeor);
            }
            
            
            if( !StringUtils.isEmpty(company_name)){
                QueryBuilder companyfilter = QueryBuilders.matchQuery("company_id", company_name);
                ((BoolQueryBuilder) query).must(companyfilter);
            }
            
            if ( !StringUtils.isEmpty(salary)){
                String[] salary_list = salary.split(",");
                String  salary_from = salary_list[0];
                String  salary_to = salary_list[1];
                QueryBuilder salary_bottom_filter = QueryBuilders.rangeQuery("salary_bottom" ).from(salary_from).to(salary_to);
                QueryBuilder salary_top_filter = QueryBuilders.rangeQuery("salary_top" ).from(salary_from).to(salary_to);
                QueryBuilder salaryor = QueryBuilders.boolQuery();
                
                ((BoolQueryBuilder) salaryor).should(salary_bottom_filter);
                ((BoolQueryBuilder) salaryor).should(salary_top_filter);
                ((BoolQueryBuilder) query).must(salaryor);
            }
            
            if( !StringUtils.isEmpty(child_company_name)){
                QueryBuilder child_company_filter = QueryBuilders.matchQuery("child_company_id", child_company_name);
                ((BoolQueryBuilder) query).must(child_company_filter);
            }
            
            if ( !StringUtils.isEmpty(custom)) {
                QueryBuilder custom_filter = QueryBuilders.matchQuery("custom", custom);
                ((BoolQueryBuilder) query).must(custom_filter);
            }
            
            QueryBuilder status_filter = QueryBuilders.termQuery("status", "0");
            ((BoolQueryBuilder) query).must(status_filter);
            
            if (order_by_priority){
                response = client.prepareSearch("index").setTypes("fulltext")
                        .setQuery(query)
                        .addSort("priority" , SortOrder.ASC)
                        .setFrom(page_from)
                        .setSize(page_size).execute().actionGet();
            }
            else{
                response = client.prepareSearch("index").setTypes("fulltext").setQuery(query).setFrom(page_from)
                        .setSize(page_size).execute().actionGet();
            }
            
            for (SearchHit hit : response.getHits()) {
                //Handle the hit...
                String id = BeanUtils.converToString( hit.getSource().get("id"));
                listOfid.add(id);
            }

        } catch (Exception e) {
            logger.error("error in search",e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }

        Map<String, List> res = new HashMap<String, List>();
        res.put("jd_id_list",listOfid);
        
        return ResponseUtils.success(res);

    }
    
    @Override
    public Response updateposition(String position,int  id) throws TException {
        ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
        try {
            propertiesReader.loadResource("es.properties");
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String cluster_name = propertiesReader.get("es.cluster.name", String.class);
        System.out.println(cluster_name);
        String es_connection = propertiesReader.get("es.connection", String.class);
        Integer es_port = propertiesReader.get("es.port", Integer.class);
        Settings settings = Settings.settingsBuilder().put("cluster.name", cluster_name)
                .build();
        String idx = ""+id;
        TransportClient client = null;
        try {
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(es_connection), es_port));
            IndexResponse response = client.prepareIndex("index", "fulltext",idx)
                    .setSource(position)
                    .execute()
                    .actionGet();
        } catch (UnknownHostException e) {
            logger.error("error in update",e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
        
        return ResponseUtils.success("");
    }

}
