package com.moseeker.searchengine.service.impl.keywordSearch.searchkeyword;

import com.moseeker.searchengine.domain.KeywordSearchParams;
import com.moseeker.searchengine.util.SearchUtil;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class FullTextSearchBuilder implements KeywordSearch{

    private SearchUtil searchUtil;

    public FullTextSearchBuilder() {
    }

    public FullTextSearchBuilder(SearchUtil searchUtil) {
        this.searchUtil = searchUtil;
    }


    @Override
    public void queryNewKeyWords(KeywordSearchParams keywordSearchParams) {
        searchUtil=new SearchUtil();
        String keyWord=keywordSearchParams.getKeyWord();
        QueryBuilder queryBuilder=keywordSearchParams.getQueryBuilder();
        List<String> fieldList=this.getFieldList();
        List<Integer> boostList=this.getBoostList();
        searchUtil.keyWordforQueryStringPropery(keyWord,queryBuilder,fieldList,boostList);
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
