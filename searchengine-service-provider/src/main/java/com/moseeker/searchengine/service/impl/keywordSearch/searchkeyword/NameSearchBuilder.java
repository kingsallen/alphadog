package com.moseeker.searchengine.service.impl.keywordSearch.searchkeyword;

import com.moseeker.searchengine.domain.KeywordSearchParams;
import com.moseeker.searchengine.util.SearchUtil;
import org.elasticsearch.index.query.QueryBuilder;



public class NameSearchBuilder implements KeywordSearch {

    private SearchUtil searchUtil;
    public NameSearchBuilder() {
    }

    public NameSearchBuilder(SearchUtil searchUtil) {
        this.searchUtil = searchUtil;
    }

    @Override
    public void queryNewKeyWords(KeywordSearchParams keywordSearchParams) {
        searchUtil=new SearchUtil();
        String keyword=keywordSearchParams.getKeyWord();
        QueryBuilder query=keywordSearchParams.getQueryBuilder();
        String fieldName="user.profiles.basic.name";
        searchUtil.queryMatchPrefixSingle(fieldName,keyword,query);
        searchUtil.convertSearchNameScript(keyword,query);
    }
}
