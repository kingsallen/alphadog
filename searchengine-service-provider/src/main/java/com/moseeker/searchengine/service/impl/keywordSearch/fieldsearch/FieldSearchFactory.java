package com.moseeker.searchengine.service.impl.keywordSearch.fieldsearch;

import com.moseeker.common.util.StringUtils;
import com.moseeker.searchengine.domain.KeywordSearchParams;
import com.moseeker.searchengine.util.SearchUtil;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.Map;

public class FieldSearchFactory {
    private SearchUtil searchUtil;

    public FieldSearchFactory(SearchUtil searchUtil){
        this.searchUtil=searchUtil;
    }
    public QueryBuilder getSearchField(KeywordSearchParams keywordSearchParams){
        String profilePoolId = keywordSearchParams.getProfilePoolId();
        if(StringUtils.isNotNullOrEmpty(profilePoolId)){
            FieldSearchService service=new TalentpoolSearchServiceImpl(searchUtil);
            QueryBuilder query=service.querySearch(keywordSearchParams);
            return query;
        }else{
            FieldSearchService service=new ApplicationServiceImpl(searchUtil);
            QueryBuilder query=service.querySearch(keywordSearchParams);
            return query;
        }
    }
}
