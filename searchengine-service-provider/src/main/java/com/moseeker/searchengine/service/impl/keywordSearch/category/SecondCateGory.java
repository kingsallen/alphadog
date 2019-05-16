package com.moseeker.searchengine.service.impl.keywordSearch.category;

import com.moseeker.common.constants.Constant;
import com.moseeker.searchengine.domain.KeywordSearchParams;
import com.moseeker.searchengine.service.impl.keywordSearch.searchkeyword.*;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class SecondCateGory {
    private Logger logger = Logger.getLogger(this.getClass());
    public QueryBuilder getQueryKeyWord(String keyword, QueryBuilder query){

        KeywordSearch citySearch= new CitySearchBuilder();
        KeywordSearch companySearch=new CompanySearchBuilder();
        KeywordSearch nameSearch=new NameSearchBuilder();
        KeywordSearch positionSearch=new PositionSearchBuilder();
        ((BoolQueryBuilder) query).should(citySearch.queryNewKeyWords(keyword));
        ((BoolQueryBuilder) query).should(companySearch.queryNewKeyWords(keyword));
        ((BoolQueryBuilder) query).should(nameSearch.queryNewKeyWords(keyword));
        ((BoolQueryBuilder) query).should(positionSearch.queryNewKeyWords(keyword));
        ((BoolQueryBuilder) query).minimumNumberShouldMatch(1);

        logger.info("==========第二种搜索方案============");
        logger.info(query.toString());
        logger.info("====================================");
        return query;
    }
}
