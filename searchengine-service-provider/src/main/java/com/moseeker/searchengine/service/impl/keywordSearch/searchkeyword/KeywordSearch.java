package com.moseeker.searchengine.service.impl.keywordSearch.searchkeyword;


import com.moseeker.searchengine.domain.KeywordSearchParams;
import org.elasticsearch.index.query.QueryBuilder;

public interface KeywordSearch {
    public QueryBuilder queryNewKeyWords(String keyword);
}
