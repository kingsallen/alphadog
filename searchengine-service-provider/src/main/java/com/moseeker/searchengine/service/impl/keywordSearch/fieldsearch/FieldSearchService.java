package com.moseeker.searchengine.service.impl.keywordSearch.fieldsearch;


import com.moseeker.searchengine.domain.KeywordSearchParams;
import org.elasticsearch.index.query.QueryBuilder;


import java.util.Map;

public interface FieldSearchService {
    public QueryBuilder querySearch(KeywordSearchParams keywordSearchParams);
}
