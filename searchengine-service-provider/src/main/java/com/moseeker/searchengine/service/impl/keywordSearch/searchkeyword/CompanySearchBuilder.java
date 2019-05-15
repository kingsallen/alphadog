package com.moseeker.searchengine.service.impl.keywordSearch.searchkeyword;

import com.moseeker.searchengine.domain.KeywordSearchParams;
import com.moseeker.searchengine.util.SearchUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;


import java.util.ArrayList;
import java.util.List;

public class CompanySearchBuilder implements KeywordSearch{

    private SearchUtil searchUtil;

    public CompanySearchBuilder() {
    }

    public CompanySearchBuilder(SearchUtil searchUtil) {
        this.searchUtil = searchUtil;
    }


    @Override
    public QueryBuilder queryNewKeyWords(KeywordSearchParams keywordSearchParams) {
        searchUtil=new SearchUtil();
        String keyword=keywordSearchParams.getKeyWord();
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        List<String> fieldNameList=new ArrayList<>();
        fieldNameList.add("user.profiles.other_workexps.company_name_data");
        fieldNameList.add("user.profiles.recent_job.company_name_data");
        searchUtil.shouldMatchParseQuery(fieldNameList,keyword,query);
        return query;
    }
}
