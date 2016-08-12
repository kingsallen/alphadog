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
            String company_name, int page_from, int page_size,String child_company_name,String department,boolean order_by_priority) throws TException {
        
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
            
            if (keywords != null || !keywords.equals("") ) {
                String[] keyword_list = keywords.split(" ");
                QueryBuilder keyand = QueryBuilders.boolQuery();
                for (int i = 0; i < keyword_list.length; i++) {
                    String keyword = keyword_list[i];
                    QueryBuilder keyfilter = QueryBuilders.simpleQueryStringQuery(keyword);
                    ((BoolQueryBuilder) keyand).should(keyfilter);
                }
                ((BoolQueryBuilder) query).must(keyand);
            }
            


            if (cities != null|| !cities.equals("")) {
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
            
            if (industries != null || !industries.equals("")) {
                String[] industry_list = industries.split(",");
                QueryBuilder industryor = QueryBuilders.boolQuery();
                for (int i = 0; i < industry_list.length; i++) {
                    String industry = industry_list[i];
                    QueryBuilder industryfilter = QueryBuilders.matchQuery("industry", industry);
                    ((BoolQueryBuilder) query).should(industryfilter);
                }
                ((BoolQueryBuilder) query).must(industryor);
            }

            if (occupations != null || !occupations.equals("")) {
                String[] occupation_list = occupations.split(",");
                QueryBuilder occupationor = QueryBuilders.boolQuery();
                for (int i = 0; i < occupation_list.length; i++) {
                    String occupation = occupation_list[i];
                    QueryBuilder occupationfilter = QueryBuilders.matchQuery("occupation", occupation);
                    ((BoolQueryBuilder) query).should(occupationfilter);
                }
                ((BoolQueryBuilder) query).must(occupationor);
            }

            if (scale != null || !scale.equals("")) {
                QueryBuilder scalefilter = QueryBuilders.termQuery("scale", scale);
                ((BoolQueryBuilder) query).must(scalefilter);
            }

            if (employment_type != null || !employment_type.equals("")) {
                QueryBuilder employmentfilter = QueryBuilders.termQuery("employment_type_name", employment_type);
                ((BoolQueryBuilder) query).must(employmentfilter);
            }

            if (candidate_source != null || !candidate_source.equals("")) {
                QueryBuilder candidatefilter = QueryBuilders.termQuery("candidate_source_name", candidate_source);
                ((BoolQueryBuilder) query).must(candidatefilter);
            }
            
            if (department != null || !department.equals("")) {
                QueryBuilder departmentfilter = QueryBuilders.termQuery("department", department);
                ((BoolQueryBuilder) query).must(departmentfilter);
            }
            
            if (experience != null || !experience.equals("")) {
                QueryBuilder experiencefilter = QueryBuilders.termQuery("experience", experience);
                ((BoolQueryBuilder) query).must(experiencefilter);
            }

            if (degree != null || !degree.equals("")) {
                QueryBuilder degreefilter = QueryBuilders.termQuery("degree_name", degree);
                ((BoolQueryBuilder) query).must(degreefilter);
            }

            if (company_name != null || !company_name.equals("")) {
                QueryBuilder companyfilter = QueryBuilders.termQuery("company_name", company_name);
                ((BoolQueryBuilder) query).must(companyfilter);
            }

            if (salary != null || !salary.equals("")) {
                String[] salary_list = salary.split(",");
                String  salary_from = salary_list[0];
                String  salary_to = salary_list[1];
                QueryBuilder salary_bottom_filter = QueryBuilders.rangeQuery("salary_bottom" ).from(salary_from).to(salary_to);
                QueryBuilder salary_top_filter = QueryBuilders.rangeQuery("salary_top" ).from(salary_from).to(salary_to);
                ((BoolQueryBuilder) query).must(salary_bottom_filter);
                ((BoolQueryBuilder) query).must(salary_top_filter);
            }
            
            if (child_company_name != null || !child_company_name.equals("")) {
                QueryBuilder child_company_filter = QueryBuilders.termQuery("child_company_name", child_company_name);
                ((BoolQueryBuilder) query).must(child_company_filter);
            }
            
            
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
                String id = (String) hit.getSource().get("id");
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
