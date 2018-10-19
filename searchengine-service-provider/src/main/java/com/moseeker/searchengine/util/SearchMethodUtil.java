package com.moseeker.searchengine.util;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.FormCheck;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zztaiwll on 18/5/16.
 */
@Service
public class SearchMethodUtil {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SearchUtil searchUtil;
    @CounterIface
    public Map<String,Object> suggestPosition(Map<String,String> params){
        String keyWord=params.get("keyWord");
        String companyIds=params.get("company_id");
        String publisher=params.get("publisher");
        String publisherCompanyId=params.get("did");
        if(StringUtils.isBlank(keyWord)&&StringUtils.isBlank(companyIds)
                &&StringUtils.isBlank(publisher)&&StringUtils.isBlank(publisherCompanyId)){
            return null;
        }
        String page=params.get("page_from");
        String pageSize=params.get("page_size");
        if(StringUtils.isBlank(page)){
            page="1";
        }
        if(StringUtils.isBlank(pageSize)){
            pageSize="15";
        }

        TransportClient client=null;
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            client = searchUtil.getEsClient();
            SearchResponse hits=this.searchPrefix(keyWord,params,Integer.parseInt(page),Integer.parseInt(pageSize),client);
            long hitNum=hits.getHits().getTotalHits();
            if(hitNum==0){
                hits=this.searchQueryString(keyWord,params,Integer.parseInt(page),Integer.parseInt(pageSize),client);
                map=searchUtil.handleData(hits,"suggest");
            }else{
                map=searchUtil.handleData(hits,"suggest");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return map;
    }

    //通过Prefix方式搜索
    private SearchResponse searchPrefix(String keyWord,Map<String,String> params,int page,int pageSize,TransportClient client){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        List<String> list=new ArrayList<>();
        list.add("title");
        searchUtil.handleKeyWordForPrefix(keyWord, false, query, list);
        this.handlerCommonSuggest(params,query);
        logger.info("searchPrefix   query : {}", query);
        SearchRequestBuilder responseBuilder=client.prepareSearch("index").setTypes("fulltext")
                .setQuery(query)
                .setFrom((page-1)*pageSize)
                .setSize(pageSize)
                .setTrackScores(true);
        String returnParams=params.get("return_params");
        this.handlerReturnParams(returnParams,responseBuilder);
        this.handlerSort(params,responseBuilder);
        logger.info(responseBuilder.toString());
        SearchResponse res=responseBuilder.execute().actionGet();
        return res;
    }

    //通过QueryString搜索
    private SearchResponse searchQueryString(String keyWord,Map<String,String> params,int page,int pageSize,TransportClient client){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        List<String> list=new ArrayList<>();
        list.add("title");
        searchUtil.handleKeyWordforQueryString(keyWord, false, query, list);
        this.handlerCommonSuggest(params,query);
        SearchRequestBuilder responseBuilder=client.prepareSearch("index").setTypes("fulltext")
                .setQuery(query)
                .setFrom((page-1)*pageSize)
                .setSize(pageSize)
                .setTrackScores(true);
        String returnParams=params.get("return_params");
        this.handlerReturnParams(returnParams,responseBuilder);
        this.handlerSort(params,responseBuilder);
        logger.info(responseBuilder.toString());
        SearchResponse res=responseBuilder.execute().actionGet();
        return res;
    }

    /*
        处理排序
     */
    private void handlerSort(Map<String,String> params,SearchRequestBuilder responseBuilder){

        //默认按先置顶和更新时间排序
        responseBuilder.addSort("priority",SortOrder.ASC);
        responseBuilder.addSort("update_time",SortOrder.DESC);
        responseBuilder.addSort("id",SortOrder.DESC);

        String flag=params.get("flag");
        if("-1".equals(flag)){
            SortBuilder builder = new ScriptSortBuilder(this.buildScriptSort(), "number");
            builder.order(SortOrder.DESC);
            responseBuilder.addSort(builder);
            responseBuilder.addSort("id",SortOrder.DESC);
        }

    }
    /*
     根据script排序
     */
    private Script buildScriptSort(){
        StringBuffer sb=new StringBuffer();
        sb.append("double score=0 ;");
        sb.append("value=doc['status'].value;if(value==0){score=100}else if(value==2){score=50}else{score=10};return score");
        String scripts=sb.toString();
        Script script=new Script(scripts);
        return script;
    }
    /*
     提取公共部分，进行封装
     */

    public void handlerCommonSuggest(Map<String,String> params,QueryBuilder query ){
        String candidateSource=params.get("candidate_source");
        String candidateSourceName=params.get("candidate_source_name");
        String city=params.get("city");
        String cities=params.get("cities");
        String occupation=params.get("occupations");
        String teamName=params.get("team_name");
        String employmentType=params.get("employment_type");
        String employmentTypeName=params.get("employment_type_name");
        String degreeName=params.get("degree_name");
        String degree = params.get("degree");
        String custom=params.get("custom");
        String salaryTop=params.get("salary_top");
        String salaryBottom=params.get("salary_bottom");
        String companyIds=params.get("company_id");
        String publisher=params.get("publisher");
        String publisherCompanyId=params.get("did");
        String isReferral=params.get("is_referral");
        String department = params.get("department");
        String salary=params.get("salary");

        logger.info("handlerCommonSuggest params  {}", JSON.toJSONString(params));
        searchUtil.handleTerms(companyIds,query,"company_id");
        String flag=params.get("flag");
        if(StringUtils.isBlank(flag)){
            flag="0";
        }
        if(StringUtils.isNotBlank(publisherCompanyId)){
            searchUtil.handleTerms(publisherCompanyId,query,"publisher_company_id");
        }
        if(Integer.parseInt(flag)==0){
            searchUtil.handleMatch(0,query,"status");
        } else if(Integer.parseInt(flag ) ==1) {
            //flag=1 查询在招的并且是内推的职位
            searchUtil.handleMatch(0,query,"status");
            isReferral = "1";
        } else if(Integer.parseInt(flag)==-1){

        }else{
            this.handlerStatusQuery(query);
        }


        if(StringUtils.isNotBlank(publisher)){
            searchUtil.handleTerms(publisher,query,"publisher");
        }
        if(StringUtils.isNotBlank(candidateSource)){
            if(!FormCheck.isNumber(candidateSource)) {
                //如果candidateSource传的是汉字不是数字1或0
                searchUtil.handleMatchParse(candidateSource,query,"candidate_source_name");
            }else if(Integer.valueOf(candidateSource) >=0) {
                searchUtil.handleMatchParse(candidateSource,query,"candidate_source");
            }
        }

        if(StringUtils.isNotBlank(candidateSourceName)){
            searchUtil.handleMatchParse(candidateSourceName,query,"candidate_source_name");
        }

        if(StringUtils.isNotBlank(department)){
            searchUtil.handleTerm(department,query,"search_data.team_name");
        }

        if(StringUtils.isNotBlank(city)){
            List<String> citys = searchUtil.stringConvertList(city);
            List<String> fieldList=new ArrayList<>();
            fieldList.add("search_data.city_list");
            fieldList.add("search_data.ecity_list");
            searchUtil.shouldTermsQueryString(fieldList,citys,query);
        } else if(StringUtils.isNotBlank(cities) ) {
            searchUtil.handleTerms(cities,query,"city");
        }

        if(StringUtils.isNotBlank(occupation)){
            String filterOccupation = com.moseeker.common.util.StringUtils.filterStringForSearch(occupation);
            searchUtil.handleTerm(filterOccupation,query,"search_data.occupation");
        }
        if(StringUtils.isNotBlank(teamName)){
            searchUtil.handleTerm(teamName,query,"search_data.team_name");
        }
        if(StringUtils.isNotBlank(employmentType)){
            if(!FormCheck.isNumber(employmentType)) {
                    //如果employmentType传的是汉字不是数字1或0
                searchUtil.handleMatchParse(employmentType,query,"employment_type_name");
            }else if(Integer.valueOf(employmentType) >= 0) {
                searchUtil.handleMatchParse(employmentType,query,"employment_type");
            }
        }
        if(StringUtils.isNotBlank(employmentTypeName)){
            searchUtil.handleMatchParse(employmentTypeName,query,"employment_type_name");
        }
        if(StringUtils.isNotBlank(degreeName)){
            searchUtil.handleTerm(degreeName,query,"search_data.degree_name");
        }else if(StringUtils.isNotBlank(degree)){
            searchUtil.handleTerm(degree,query,"search_data.degree_name");
        }

        if(StringUtils.isNotBlank(custom)){
            searchUtil.handleTerm(custom,query,"search_data.custom");
        }
        if(StringUtils.isNotBlank(isReferral)){
            searchUtil.handleTerm(isReferral,query,"is_referral");
        }

        if(StringUtils.isNotBlank(salary)&&salary.contains(",")) {
            String[] salary_list = salary.split(",");
            String salary_from = salary_list[0];
            String salary_to = salary_list[1];
            QueryBuilder salary_bottom_filter = QueryBuilders.rangeQuery("salary_bottom").from(salary_from).to(salary_to);
            QueryBuilder salary_top_filter = QueryBuilders.rangeQuery("salary_top").from(salary_from).to(salary_to);
            QueryBuilder salaryor = QueryBuilders.boolQuery();

            ((BoolQueryBuilder) salaryor).should(salary_bottom_filter);
            ((BoolQueryBuilder) salaryor).should(salary_top_filter);
            ((BoolQueryBuilder) query).must(salaryor);
        }

    }

    /*
    制定返回参数
    */
    public void handlerReturnParams(String returnParams,SearchRequestBuilder builder){
        if(StringUtils.isNotBlank(returnParams)){
            builder.setFetchSource(returnParams.split(","),null);
        }
    }

    public void handlerStatusQuery(QueryBuilder query){
        List<String> statusList=new ArrayList<>();
        statusList.add("0");
        statusList.add("2");
        Map<String,List<String>> params=new HashMap<>();
        params.put("status",statusList);
        searchUtil.handleShouldMatchFilter(params,query);
    }


//    public void handleCityQuery(QueryBuilder query,String city){
//        List<String> cityNames = new ArrayList<String>();
//        String[] city_list = city.split(",");
//        for(String name :city_list){
//            cityNames.add(city);
//        }
//        Map<String,List<String>> params=new HashMap<>();
//        params.put("city",cityNames);
//
//        searchUtil.handleShouldMatchFilter(params,query);
//    }

}
