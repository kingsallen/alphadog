package com.moseeker.searchengine.service.impl;

import com.moseeker.searchengine.util.SearchUtil;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zztaiwll on 17/12/8.
 */
@Service
public class TalentpoolSearchengine {

    @Autowired
    private SearchUtil searchUtil;

    public Map<String,Object>  talentSearch(Map<String,String> params){
        TransportClient client= searchUtil.getEsClient();
        return null;
    }

    private SearchResponse query(Map<String,String> params){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        String publisherIds=params.get("publisher_ids");

        return null;
    }

    private void queryByPublisher(String publisherIds,QueryBuilder queryBuilder){
        searchUtil.handleTerms(publisherIds,queryBuilder,"user.applications.publisher");
    }

}
