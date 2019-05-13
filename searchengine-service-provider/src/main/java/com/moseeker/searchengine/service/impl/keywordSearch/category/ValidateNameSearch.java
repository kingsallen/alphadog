package com.moseeker.searchengine.service.impl.keywordSearch.category;

import com.moseeker.common.constants.Constant;
import com.moseeker.searchengine.domain.KeywordSearchParams;
import com.moseeker.searchengine.service.impl.keywordSearch.fieldsearch.FieldSearchFactory;
import com.moseeker.searchengine.service.impl.keywordSearch.searchkeyword.NameSearchBuilder;
import com.moseeker.searchengine.util.SearchUtil;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;



public class ValidateNameSearch implements ValidateSearch{

    private SearchUtil searchUtil;
    private NameSearchBuilder nameSearchBuilder;
    private FieldSearchFactory fieldSearchFactory;
    private Logger logger = Logger.getLogger(this.getClass());

    public ValidateNameSearch() {
    }
    public ValidateNameSearch(SearchUtil searchUtil, NameSearchBuilder nameSearchBuilder) {
        this.searchUtil = searchUtil;
        this.nameSearchBuilder = nameSearchBuilder;
        fieldSearchFactory=new FieldSearchFactory(searchUtil);
    }

    @Override
    public boolean getValidateSearch(KeywordSearchParams keywordSearchParams) {
        searchUtil=new SearchUtil();
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        keywordSearchParams.setQueryBuilder(query);
        TransportClient client=keywordSearchParams.getClient();
        nameSearchBuilder=new NameSearchBuilder();
        nameSearchBuilder.queryNewKeyWords(keywordSearchParams);
//        searchUtil.handleTerm(companyId,query,"user.applications.company_id");

        searchUtil=new SearchUtil();
        fieldSearchFactory=new FieldSearchFactory(searchUtil);
        QueryBuilder fieldSearch=fieldSearchFactory.getSearchField(keywordSearchParams);
        ((BoolQueryBuilder) query).must(fieldSearch);

        SearchRequestBuilder builder = client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_TYPE).setQuery(query);
        builder.setSize(0);
        logger.info("==========查询是否属于人名============");
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
}
