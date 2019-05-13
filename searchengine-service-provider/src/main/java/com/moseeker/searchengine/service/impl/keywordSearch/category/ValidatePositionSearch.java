package com.moseeker.searchengine.service.impl.keywordSearch.category;

import com.moseeker.common.constants.Constant;
import com.moseeker.searchengine.domain.KeywordSearchParams;
import com.moseeker.searchengine.service.impl.keywordSearch.fieldsearch.FieldSearchFactory;
import com.moseeker.searchengine.util.SearchUtil;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;


import java.util.ArrayList;
import java.util.List;

public  class ValidatePositionSearch implements ValidateSearch{

    private SearchUtil searchUtil;
    private FieldSearchFactory fieldSearchFactory;
    private Logger logger = Logger.getLogger(this.getClass());

    public ValidatePositionSearch() {
    }

    public ValidatePositionSearch(SearchUtil searchUtil) {
        this.searchUtil = searchUtil;
        this.fieldSearchFactory = new FieldSearchFactory(searchUtil);
    }
    @Override
    public boolean getValidateSearch(KeywordSearchParams keywordSearchParams) {
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);

        TransportClient client=keywordSearchParams.getClient();
        String keyword=keywordSearchParams.getKeyWord();
        this.convertPositionValidateSearch(keyword,query);
        searchUtil=new SearchUtil();
        fieldSearchFactory=new FieldSearchFactory(searchUtil);
        QueryBuilder fieldSearch=fieldSearchFactory.getSearchField(keywordSearchParams);
        ((BoolQueryBuilder) query).must(fieldSearch);

      //  searchUtil.handleTerm(companyId,query,"user.applications.company_id");
        SearchRequestBuilder builder = client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_TYPE).setQuery(query);
        builder.setSize(0);
        logger.info("==========查询是否属于职位============");
        logger.info(builder.toString());
        logger.info("====================================");
        SearchResponse response = builder.execute().actionGet();
        SearchHits hit=response.getHits();
        long totalNum=hit.getTotalHits();
        if(totalNum>0){
            return true;
        }
        return false;
    }
    private void convertPositionValidateSearch(String condition,QueryBuilder query){
        List<String> fieldNameList=new ArrayList<>();
        fieldNameList.add("user.profiles.other_workexps.job_name");
        fieldNameList.add("user.profiles.recent_job.job_name");
        searchUtil.shouldWildCard(fieldNameList,condition,query);

    }
}
