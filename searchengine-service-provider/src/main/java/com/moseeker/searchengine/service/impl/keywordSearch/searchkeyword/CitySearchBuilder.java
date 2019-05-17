package com.moseeker.searchengine.service.impl.keywordSearch.searchkeyword;

import com.moseeker.searchengine.domain.KeywordSearchParams;
import com.moseeker.searchengine.util.SearchUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;


import java.util.ArrayList;
import java.util.List;

public class CitySearchBuilder implements KeywordSearch {

    @Override
    public QueryBuilder queryNewKeyWords(String keyword) {
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        SearchUtil searchUtil =new SearchUtil();
        List<String> cityField=new ArrayList<>();
        cityField.add("user.profiles.basic.city_name");
        cityField.add("user.profiles.intentions.cities.city_name");
        searchUtil.shouldMatchParseQuery(cityField,keyword,query);
        return query;
    }
}
