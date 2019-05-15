package com.moseeker.searchengine.service.impl.keywordSearch.searchkeyword;

import com.moseeker.searchengine.domain.KeywordSearchParams;
import com.moseeker.searchengine.util.SearchUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;


public class NameSearchBuilder implements KeywordSearch {

    private SearchUtil searchUtil;
    public NameSearchBuilder() {
    }

    public NameSearchBuilder(SearchUtil searchUtil) {
        this.searchUtil = searchUtil;
    }

    @Override
    public QueryBuilder queryNewKeyWords(KeywordSearchParams keywordSearchParams) {
        searchUtil=new SearchUtil();
        String keyword=keywordSearchParams.getKeyWord();
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        String fieldName="user.profiles.basic.name";
        searchUtil.queryMatchPrefixSingle(fieldName,keyword,query);
        searchUtil.convertSearchNameScript(keyword,query);
        return query;
    }
}
