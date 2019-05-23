package com.moseeker.searchengine.service.impl.keywordSearch.category;

import com.moseeker.searchengine.domain.KeywordSearchParams;
import org.elasticsearch.client.transport.TransportClient;

public interface ValidateSearch{
    public boolean getValidateSearch(KeywordSearchParams keywordSearchParams, TransportClient client);
}
