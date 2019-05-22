package com.moseeker.searchengine.service.impl.keywordSearch.searchkeyword;

import com.moseeker.searchengine.domain.KeywordSearchParams;
import com.moseeker.searchengine.util.SearchUtil;
import org.apache.log4j.Logger;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;


import java.util.ArrayList;
import java.util.List;

public class PositionSearchBuilder implements KeywordSearch {
    private Logger logger = Logger.getLogger(this.getClass());
    @Override
    public QueryBuilder queryNewKeyWords(String keyword) {
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        SearchUtil searchUtil=new SearchUtil();
        List<String> fieldNameList=new ArrayList<>();
        fieldNameList.add("user.profiles.other_workexps.job_name");
        fieldNameList.add("user.profiles.recent_job.job_name");
        searchUtil.shouldWildCard(fieldNameList,keyword,query);
        searchUtil.searchNewPositionDataGroup(keyword,query);
        return query;
    }
}
