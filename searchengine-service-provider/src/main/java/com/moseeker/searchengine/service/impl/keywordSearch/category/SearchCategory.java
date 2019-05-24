package com.moseeker.searchengine.service.impl.keywordSearch.category;

import com.moseeker.searchengine.domain.KeywordSearchParams;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;

public interface SearchCategory {
    public int getSearchCataGoery(KeywordSearchParams keywordSearchParams,TransportClient client);
}
