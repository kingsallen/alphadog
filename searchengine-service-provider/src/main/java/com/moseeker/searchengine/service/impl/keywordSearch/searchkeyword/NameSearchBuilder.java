package com.moseeker.searchengine.service.impl.keywordSearch.searchkeyword;

import com.moseeker.common.util.StringUtils;
import com.moseeker.searchengine.domain.KeywordSearchParams;
import com.moseeker.searchengine.util.SearchUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;


public class NameSearchBuilder implements KeywordSearch {
    @Override
    public QueryBuilder queryNewKeyWords(String keyword) {
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        String []array=keyword.split(",");
        for(String key:array){
            QueryBuilder keyand = QueryBuilders.wildcardQuery("user.profiles.basic.user_name_new","*"+key+"*");
            ((BoolQueryBuilder) query).should(keyand);
            QueryBuilder keyand1 = QueryBuilders.matchPhraseQuery("user.profiles.basic.name",key);
            ((MatchQueryBuilder) keyand1).boost(100);
            ((BoolQueryBuilder) query).should(keyand1);
        }
        ((BoolQueryBuilder) query).minimumNumberShouldMatch(1);
        return query;
    }
}
