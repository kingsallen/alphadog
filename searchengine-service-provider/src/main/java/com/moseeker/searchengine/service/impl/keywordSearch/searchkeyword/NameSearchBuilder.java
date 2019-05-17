package com.moseeker.searchengine.service.impl.keywordSearch.searchkeyword;

import com.moseeker.searchengine.domain.KeywordSearchParams;
import com.moseeker.searchengine.util.SearchUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;


public class NameSearchBuilder implements KeywordSearch {
    @Override
    public QueryBuilder queryNewKeyWords(String keyword) {
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        SearchUtil searchUtil=new SearchUtil();
        String fieldName="user.profiles.basic.name";
        searchUtil.queryMatchPrefixSingle(fieldName,keyword,query);
        searchUtil.convertSearchNameScript(keyword,query);
        return query;
    }
}
