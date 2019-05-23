package com.moseeker.searchengine.service.impl.keywordSearch.fieldsearch;

import com.moseeker.searchengine.domain.KeywordSearchParams;
import com.moseeker.searchengine.util.SearchUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.Map;

public class ApplicationServiceImpl implements FieldSearchService {
    private SearchUtil searchUtil;
    public ApplicationServiceImpl(SearchUtil searchUtil){
        this.searchUtil=searchUtil;
    }
    @Override
    public QueryBuilder querySearch(KeywordSearchParams keywordSearchParams) {
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        String companyId=keywordSearchParams.getCompanyId();
        String hrId=keywordSearchParams.getHrId();
        searchUtil.handleTerm(companyId,query,"user.applications.company_id");
        searchUtil.handleTerm(hrId,query,"user.applications.publisher");
        return query;
    }
}
