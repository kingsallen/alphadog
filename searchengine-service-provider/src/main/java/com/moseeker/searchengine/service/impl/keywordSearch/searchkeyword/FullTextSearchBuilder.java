package com.moseeker.searchengine.service.impl.keywordSearch.searchkeyword;

import com.moseeker.searchengine.domain.KeywordSearchParams;
import com.moseeker.searchengine.util.SearchUtil;
import org.apache.log4j.Logger;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.ArrayList;
import java.util.List;

public class FullTextSearchBuilder implements KeywordSearch{
    private Logger logger = Logger.getLogger(this.getClass());
    private SearchUtil searchUtil;
    @Override
    public QueryBuilder queryNewKeyWords(String keyword) {
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        SearchUtil searchUtil=new SearchUtil();

        List<String> fieldList=this.getFieldList();
        List<Integer> boostList=this.getBoostList();
        searchUtil.keyWordforQueryStringPropery(keyword,query,fieldList,boostList);
        logger.info("==========第三种搜索方案============");
        logger.info(query.toString());
        logger.info("====================================");
        return query;
    }

    /*
       组装全文检索查询的条件
    */
    private List<String> getFieldList(){
        List<String> fieldList=new ArrayList<>();
        fieldList.add("user.profiles.basic.name");
        fieldList.add("user.profiles.recent_job.company_name");
        fieldList.add("user.profiles.recent_job.department_name");
        fieldList.add("user.profiles.recent_job.job");
        fieldList.add("user.profiles.recent_job.description");
        fieldList.add("user.profiles.other_workexps.company_name");
        fieldList.add("user.profiles.other_workexps.department_name");
        fieldList.add("user.profiles.other_workexps.job");
        fieldList.add("user.profiles.other_workexps.description");
        fieldList.add("user.profiles.educations.description");
        fieldList.add("user.profiles.projectexps.name");
        fieldList.add("user.profiles.projectexps.description");
        fieldList.add("user.profiles.skills");
        fieldList.add("user.profiles.credentials.name");
        return fieldList;
    }

    /*
     组装全文检索查询的权重
    */
    private List<Integer> getBoostList(){
        List<Integer> boostList=new ArrayList<>();
        boostList.add(20);
        boostList.add(10);
        boostList.add(10);
        boostList.add(10);
        boostList.add(10);
        boostList.add(8);
        boostList.add(8);
        boostList.add(8);
        boostList.add(5);
        boostList.add(5);
        boostList.add(3);
        boostList.add(3);
        boostList.add(1);
        boostList.add(1);
        return boostList;
    }
}
