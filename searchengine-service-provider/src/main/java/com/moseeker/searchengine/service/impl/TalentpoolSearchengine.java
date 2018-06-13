package com.moseeker.searchengine.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.searchengine.domain.PastPOJO;
import com.moseeker.searchengine.domain.SearchPast;
import com.moseeker.searchengine.util.SearchMethodUtil;
import com.moseeker.searchengine.util.SearchUtil;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.ScriptQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.MetricsAggregationBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zztaiwll on 17/12/8.
 */
@Service
public class TalentpoolSearchengine {
    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private SearchUtil searchUtil;
    @Autowired
    private UserHrAccountDao userHrAccountDao;
    @Autowired
    private SearchMethodUtil searchMethodUtil;

    @CounterIface
    public Map<String, Object> talentSearch(Map<String, String> params) {
        Map<String, Object> result=new HashMap<>();
        TransportClient client=null;
        try {
            client = searchUtil.getEsClient();
//            client = searchUtil.getEsClient1();
            QueryBuilder query = this.query(params);
            SearchRequestBuilder builder = client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_TYPE).setQuery(query);
            this.handlerSortOrder(params, builder);
            this.handlerPage(params, builder);
            this.handlerReturn(params, builder);
            builder.setTrackScores(true);
            logger.info(builder.toString());
            SearchResponse response = builder.execute().actionGet();
            result = searchUtil.handleData(response, "users");
            return result;
        } catch (Exception e) {
            logger.info(e.getMessage()+"=================");
            if (e.getMessage().contains("all shards")) {
                return result;
            }
        }
        return result;
    }
    @CounterIface
    public List<Integer> getTalentUserList(Map<String, String> params){
        List<Integer> userIdList=new ArrayList<>();
        TransportClient client=null;
        try {
            client = searchUtil.getEsClient();
            QueryBuilder query = this.query(params);
            SearchRequestBuilder builder = client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_TYPE).setQuery(query);
            this.handlerPage(params, builder);
            String[] returnParams={"user.profiles.profile.user_id"};
            builder.setFetchSource(returnParams,null);
            logger.info(builder.toString());
            SearchResponse response = builder.execute().actionGet();
            Map<String,Object> result = searchUtil.handleData(response, "userIdList");
            if(result!=null&&!result.isEmpty()){
                long totalNum=(long)result.get("totalNum");
                if(totalNum>0){
                    this.handlerResult(result,userIdList);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage()+"=================",e);

        }
        return userIdList;
    }


    /*
     根据user_id获取es中的数据

     */
    @CounterIface
    public Map<String, Object> getEsDataByUserIds(List<Integer> userIds){
        Map<String, Object> result=new HashMap<>();
        TransportClient client=null;
        try {
            client = searchUtil.getEsClient();
            QueryBuilder query = this.queryByUserId(userIds);
            SearchRequestBuilder builder = client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_TYPE).setQuery(query);
            logger.info(builder.toString());
            SearchResponse response = builder.execute().actionGet();
            result = searchUtil.handleData(response, "users");
            return result;
        } catch (Exception e) {
            logger.info(e.getMessage()+"=================");
            if (e.getMessage().contains("all shards")) {
                return result;
            }
        }
        return result;
    }
    /*
     查询企业标签的人才数量
     */
    public int talentSearchNum(Map<String, String> params) {
        Map<String, Object> result=new HashMap<>();
        TransportClient client=null;
        try {
            client = searchUtil.getEsClient();
            QueryBuilder query = this.query(params);
            logger.info("=========================================");
            logger.info(query.toString());
            logger.info("=========================================");
            SearchRequestBuilder builder = client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_TYPE).setQuery(query);
            builder.setSize(0);
            logger.info(builder.toString());
            SearchResponse response = builder.execute().actionGet();
            result = searchUtil.handleData(response, "users");
            logger.info("=========================================");
            logger.info(JSON.toJSONString(result));
            logger.info("=========================================");
            int total=(int)((long)result.get("totalNum"));
            logger.info(JSON.toJSONString(result));
            return total;
        } catch (Exception e) {
            logger.info(e.getMessage(),e);
            if (e.getMessage().contains("all shards")) {
                return 0;
            }
        }
        return 0;
    }
    @CounterIface
    public Map<String,Object> getAggInfo(Map<String, String> params){
        Map<String, Object> result=new HashMap<>();
        TransportClient client =null;
        try {
            String progressStatus = params.get("progress_status");
            if(StringUtils.isNotNullOrEmpty(progressStatus)){
                params.put("progress_status",null);
                //修改progress.没办法按照建立进度查询时只有applicationcount需要变化
                params.put("progress",progressStatus);
            }
            client=searchUtil.getEsClient();
            QueryBuilder query = this.query(params);
            SearchRequestBuilder builder = client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_INDEX).setQuery(query);
            builder.addAggregation(this.handleAllApplicationCountAgg(params))
                    .addAggregation(this.handleAllcountAgg(params))
                    .addAggregation(this.handleAggInfo(params,"entry_count",12,0))
                    .addAggregation(this.handleAggInfo(params,"entry_count_app",12,1))
                    .addAggregation(this.handleAggInfo(params,"interview_ok_count",10,0))
                    .addAggregation(this.handleAggInfo(params,"interview_ok_count_app",10,1))
                    .addAggregation(this.handleAggInfo(params,"first_trial_ok_count",7,0))
                    .addAggregation(this.handleAggInfo(params,"first_trial_ok_count_app",7,1))
                    .addAggregation(this.handleAggInfo(params,"is_viewed_count",4,0))
                    .addAggregation(this.handleAggInfo(params,"is_viewed_count_app",4,1))
                    .addAggregation(this.handleAggInfo(params,"is_not_suitable",13,0))
                    .addAggregation(this.handleAggInfo(params,"is_not_suitable_app",13,1))
                    .addAggregation(this.handleAggInfo(params,"not_viewed_count",3,1))
                    .addAggregation(this.handleAggInfo(params,"not_viewed_count_app",3,1));
            builder.setSize(0);
            logger.info(builder.toString());
            SearchResponse response = builder.execute().actionGet();
            result = searchUtil.handleAggData(response);
            return result;
        } catch (Exception e) {
            logger.info(e.getMessage()+"=================");
            if (e.getMessage().contains("all shards")) {
                return result;
            }
        }
        return result;
    }
    /*
     根据企业标签的规则获取符合该规则的人才id
     */
    @CounterIface
    public List<Integer> getUserListByCompanyTag(Map<String,String> params){
        List<Integer> list=new ArrayList<>();
        try{
            SearchResponse response=this.handlerSearch(params);
            Map<String,Object> result = searchUtil.handleData(response,"userIdList");
            logger.info("============================================");
            logger.info(JSON.toJSONString(result));
            logger.info("============================================");
            if(result!=null&&!result.isEmpty()){
                long totalNum=(long)result.get("totalNum");
                if(totalNum>0){
                    this.handlerResult(result,list);
                }
            }
        }catch(Exception e){
            logger.info(e.getMessage()+"=================");
        }
        logger.info("==========================");
        logger.info(JSON.toJSONString(list));
        logger.info("==========================");
        return list;
    }

    /*
     获取tag_id的标签的条数
     */
    @CounterIface
    public int getUserListByCompanyTagCount(Map<String,String> params){
        List<Integer> list=new ArrayList<>();
        try{
            SearchResponse response=this.handlerSearch(params);
            Map<String,Object> result = searchUtil.handleData(response,"userIdList");
            logger.info("============================================");
            logger.info(JSON.toJSONString(result));
            logger.info("============================================");
            if(result!=null&&!result.isEmpty()){
                long totalNum=(long)result.get("totalNum");
                return (int)totalNum;
            }
        }catch(Exception e){
            logger.info(e.getMessage()+"=================");
        }
        logger.info("==========================");
        logger.info(JSON.toJSONString(list));
        logger.info("==========================");
        return 0;
    }

    private SearchResponse handlerSearch(Map<String,String> params){
        TransportClient client=searchUtil.getEsClient();
        QueryBuilder query = this.getQueryByTag(params);
        SearchRequestBuilder builder = client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_TYPE).setQuery(query);
        String[] returnParams={"user.profiles.profile.user_id"};
        builder.setFetchSource(returnParams,null);
        if(StringUtils.isNotNullOrEmpty(params.get("size"))){
            builder.setSize(0);
        }else{
            this.handlerPage(params,builder);
        }
        logger.info("============================================");
        logger.info(builder.toString());
        logger.info("============================================");
        SearchResponse response = builder.execute().actionGet();
        return response;
    }
    /*
     根据筛选规则获取符合该规则的人才id
     */
    @CounterIface
    public  Map<String, Object> getUserListByFilterIds(List<Map<String, String>> filterList, int page_number, int page_size){
        Map<String, Object> result=new HashMap<>();
        try{
            TransportClient client=searchUtil.getEsClient();
            QueryBuilder query = this.convertBuild(filterList);
            SearchRequestBuilder builder = client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_TYPE).setQuery(query);
            String[] returnParams={"user.profiles.profile.user_id","user.profiles.basic.name","user.profiles.basic.email"};
            builder.setFetchSource(returnParams,null);
            builder.setSize(page_size);
            builder.setFrom((page_number-1)*page_size);
            logger.info("============================================");
            logger.info(builder.toString());
            logger.info("============================================");
            SearchResponse response = builder.execute().actionGet();
            result = searchUtil.handleData(response,"users");
        }catch(Exception e){
            logger.info(e.getMessage(),e);
        }

        return result;
    }
    /*
    根据条件搜索曾任职务
     */
    @CounterIface
    public PastPOJO searchPastPosition(SearchPast searchPast){
        try{
            TransportClient client=searchUtil.getEsClient();
            String pageNum=searchPast.getPageNumber();
            String pageSize=searchPast.getPageSize();
            QueryBuilder query = this.handlerProfilePastPosition(searchPast);
            SearchRequestBuilder builder = client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_TYPE).setQuery(query);
            String[] returnParams={"user.profiles.recent_job.job","user.profiles.other_workexps.job"};
            builder.setFetchSource(returnParams,null);
            builder.setSize(this.handlePageSize(pageSize));
            builder.setFrom(this.handlePageFrom(pageNum,pageSize));
            logger.info("============================================");
            logger.info(builder.toString());
            logger.info("============================================");
            SearchResponse response = builder.execute().actionGet();
            Map<String,Object> map=searchUtil.handleData(response,"users");
            PastPOJO result=this.handlerResultPast(map,this.handlePageNum(pageNum),this.handlePageSize(pageSize),0);
            return result;
        }catch(Exception e){
            logger.info(e.getMessage(),e);
        }
        return null;
    }
     /*
       根据条件搜索曾任公司
     */
     @CounterIface
     public PastPOJO searchPastCompany(SearchPast searchPast){
         try{
             TransportClient client=searchUtil.getEsClient();
             String pageNum=searchPast.getPageNumber();
             String pageSize=searchPast.getPageSize();
             QueryBuilder query = this.handlerProfilePastCompany(searchPast);
             SearchRequestBuilder builder = client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_TYPE).setQuery(query);
             String[] returnParams={"user.profiles.recent_job.company_name","user.profiles.other_workexps.company_name"};
             builder.setFetchSource(returnParams,null);
             builder.setSize(this.handlePageSize(pageSize));
             builder.setFrom(this.handlePageFrom(pageNum,pageSize));
             logger.info("============================================");
             logger.info(builder.toString());
             logger.info("============================================");
             SearchResponse response = builder.execute().actionGet();
             Map<String,Object> map=searchUtil.handleData(response,"users");
             PastPOJO result=this.handlerResultPast(map,this.handlePageNum(pageNum),this.handlePageSize(pageSize),1);
             return result;
         }catch(Exception e){
             logger.info(e.getMessage(),e);
         }
         return null;
     }
    /*
     处理页数
     */
    private int handlePageNum(String pageNum){
        if(StringUtils.isNotNullOrEmpty(pageNum)){
            return Integer.parseInt(pageNum);
        }
        return 1;
    }
    /*
     处理每页的数量
   */
    private int handlePageSize(String pageSize){
        if(StringUtils.isNotNullOrEmpty(pageSize)){
            return Integer.parseInt(pageSize);
        }
        return 15;
    }
    /*
    处理页数开始位置
    */
    private int handlePageFrom(String pageNum,String pageSize){
        if(StringUtils.isNotNullOrEmpty(pageNum)){
            if(StringUtils.isNotNullOrEmpty(pageSize)){
                return (Integer.parseInt(pageNum)-1)*Integer.parseInt(pageSize);
            }else{
                return (Integer.parseInt(pageNum)-1)*15;
            }
        }
        return 0;
    }
    /*
     处理曾任职位的查询
     */
    private QueryBuilder handlerProfilePastPosition(SearchPast searchPast){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        String keyWord=searchPast.getKeyword();
        List<String> list=new ArrayList<>();
        list.add("user.profiles.recent_job.job");
        list.add("user.profiles.other_workexps.job");
        if(StringUtils.isNotNullOrEmpty(keyWord)){
            searchUtil.shouldMatchQuery(list,keyWord,query);
        }
        this.searchPastCommon(searchPast,query);
        this.existsPast(list,query);
        return query;
    }
    /*
     处理曾任公司的查询
     */
    private QueryBuilder handlerProfilePastCompany(SearchPast searchPast){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        String keyWord=searchPast.getKeyword();
        List<String> list=new ArrayList<>();
        list.add("user.profiles.recent_job.company_name");
        list.add("user.profiles.other_workexps.company_name");
        if(StringUtils.isNotNullOrEmpty(keyWord)){
            searchUtil.shouldMatchQuery(list,keyWord,query);
        }
        this.searchPastCommon(searchPast,query);
        this.existsPast(list,query);
        return query;
    }
    /*
     查询曾任职务或者曾任公司的公共部分
     */
    private void searchPastCommon(SearchPast searchPast,QueryBuilder query){
        String publisher=searchPast.getPublisher();
        String companyId=searchPast.getCompanyId();
        if(StringUtils.isNotNullOrEmpty(publisher)){
            searchUtil.handleTerms(publisher,query,"user.applications.publisher");
        }
        if(StringUtils.isNotNullOrEmpty(companyId)){
            searchUtil.handleTerms(companyId,query,"user.applications.company_id");
        }
    }
    /*
     查询字段是否存在
     */
    private void existsPast(List<String> list,QueryBuilder query){
        if(!StringUtils.isEmptyList(list)){
            searchUtil.shouldExistsQuery(list,query);
        }
    }
    /*
     处理结果
     */
    private PastPOJO handlerResultPast(Map<String,Object> result,int pageNum,int pageSize,int flag){
        PastPOJO pastpojo=new PastPOJO();
        long totalNum=(long)result.get("totalNum");
        if(totalNum>0){
            List<String> list=this.handlerPastListString(result,flag);
            pastpojo.setList(list);
            pastpojo.setTotalRow((int)totalNum);
            int totalPages=(int)Math.ceil((double)totalNum/pageSize);
            pastpojo.setTotalPage(totalPages);
            pastpojo.setPageNumber(pageNum);
            pastpojo.setPageSize(pageSize);
        }
        return pastpojo;
    }

    private List<String> handlerPastListString(Map<String,Object> result,int flag) {
        List<Map<String,Object>> usersList=(List<Map<String,Object>>)result.get("users");
        List<String> list=new ArrayList<>();
        if(!StringUtils.isEmptyList(usersList)) {
            for (Map<String, Object> users : usersList) {
                Map<String, Object> user = (Map<String, Object>) users.get("user");
                if (!StringUtils.isEmptyMap(user)) {
                    Map<String, Object> profiles = (Map<String, Object>) user.get("profiles");
                    if (!StringUtils.isEmptyMap(profiles)) {
                        Map<String, Object> recentJob = (Map<String, Object>) profiles.get("recent_job");
                        logger.info("==================recentJob============================");
                        logger.info(JSON.toJSONString(recentJob));
                        logger.info("=======================================================");
                        List<Map<String, Object>> workExpsList = (List<Map<String, Object>>) profiles.get("other_workexps");
                        logger.info("==================workExpsList============================");
                        logger.info(JSON.toJSONString(workExpsList));
                        logger.info("=======================================================");
                        if (flag == 0) {//处理pastPosition
                            this.convertJobList(recentJob,workExpsList,list);
                        } else {
                            this.convertCompanyList(recentJob,workExpsList,list);
                        }
                    }
                }
            }
        }
        return list;
    }
    /*
     处理曾任职位
     */
    private void convertJobList(Map<String,Object> recentJob,List<Map<String,Object>>workExpsList,List<String> list){
        if (!StringUtils.isEmptyMap(recentJob)) {
            String job = (String) recentJob.get("job");
            logger.info("=====================job==================================");
            logger.info(job);
            logger.info("=======================================================");
            if (!list.contains(job)) {
                list.add(job);
            }
        }
        if (!StringUtils.isEmptyList(workExpsList)) {
            for (Map<String, Object> workExp : workExpsList) {
                String workJob = (String) workExp.get("job");
                logger.info("=====================workJob==================================");
                logger.info(workJob);
                logger.info("=======================================================");
                if (!list.contains(workJob)) {
                    list.add(workJob);
                }
            }
        }
    }
    /*
    处理曾任公司
    */
    private void convertCompanyList(Map<String,Object> recentJob,List<Map<String,Object>>workExpsList,List<String> list){
        if (!StringUtils.isEmptyMap(recentJob)) {
            String job = (String) recentJob.get("company_name");
            if (!list.contains(job)) {
                list.add(job);
            }
        }
        if (!StringUtils.isEmptyList(workExpsList)) {
            for (Map<String, Object> workExp : workExpsList) {
                String workJob = (String) workExp.get("company_name");
                if (!list.contains(workJob)) {
                    list.add(workJob);
                }
            }
        }
    }
    /*
     处理es返回的结果值获取人才列表id
     */
    private void handlerResult(Map<String,Object> result,List<Integer> userIdList){
        List<Map<String,Object>> dataList=(List<Map<String,Object>>)result.get("userIdList");
        for(Map<String,Object> map:dataList){
            logger.info("============================================userIdList");
            if(map!=null&&!map.isEmpty()){
                Map<String,Object> userMap=(Map<String,Object>)map.get("user");
                logger.info("============================================user");
                if(userMap!=null&&!userMap.isEmpty()){
                    Map<String,Object> profiles=(Map<String,Object>)userMap.get("profiles");
                    logger.info("============================================profiles");
                    logger.info(JSON.toJSONString(profiles));
                    if(profiles!=null&&!profiles.isEmpty()){
                        Map<String,Object> profile=(Map<String,Object>)profiles.get("profile");
                        logger.info("============================================profile");
                        logger.info(JSON.toJSONString(profile));
                        if(profile!=null&&!profile.isEmpty()){
                            int userId=Integer.parseInt(String.valueOf(profile.get("user_id")));
                            logger.info("============================================user_id"+userId);
                            userIdList.add(userId);
                        }
                    }
                }
            }
        }
    }

    private QueryBuilder convertBuild(List<Map<String,String>> mapList){
        QueryBuilder query = QueryBuilders.boolQuery();
        for(Map<String,String> map:mapList){
            QueryBuilder queryBuilder=this.getQueryByTag(map);
            ((BoolQueryBuilder) query).should(queryBuilder);
        }
        ((BoolQueryBuilder) query).minimumNumberShouldMatch(1);
        return query;
    }
    /*
      组装查询标签内容的部分，用于查处es中哪些标签是企业标签
     */
    private QueryBuilder getQueryByTag(Map<String,String> params){
        int companyId=Integer.parseInt(params.get("company_id"));
        String origins=params.get("origins");
        String workYears=params.get("work_years");
        String submitTime=params.get("submit_time");
        String cityName=params.get("city_name");
        String degree=params.get("degree");
        String pastPosition=params.get("past_position");
        String minAge=params.get("min_age");
        String maxAge=params.get("max_age");
        String intentionCityName=params.get("intention_city_name");
        String intentionSalaryCode=params.get("intention_salary_code");
        String sex=params.get("sex");
        String isRecommend=params.get("is_recommend");
        String companyName=params.get("company_name");
        String exists=params.get("exists");
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        if(StringUtils.isNotNullOrEmpty(exists)){
            this.exitsisQuery(exists,query);
        }
        if(StringUtils.isNotNullOrEmpty(workYears)){
            this.queryByWorkYear(workYears,query);
        }
        if(StringUtils.isNotNullOrEmpty(submitTime)){
            this.queryBySubmitTime(submitTime, query);
        }
        if(StringUtils.isNotNullOrEmpty(cityName)){
            if(!cityName.contains("全国")){
                cityName=cityName+",全国";
            }
            this.queryByHome(cityName,query);
        }
        if(StringUtils.isNotNullOrEmpty(degree)){
            this.QueryByDegree(degree,query);
        }
        if(StringUtils.isNotNullOrEmpty(pastPosition)){
            String lastPosition=params.get("in_last_job_search_position");
            if(StringUtils.isNotNullOrEmpty(lastPosition)&&"1".equals(lastPosition)){
                this.queryTermsByLastPositions(pastPosition,query);
            }else{
                this.queryTermsByWorkJob(pastPosition,query);
            }
        }
        if(((StringUtils.isNotNullOrEmpty(minAge)||StringUtils.isNotNullOrEmpty(maxAge))&&(!"0".equals(minAge)||!"0".equals(maxAge)))){
            List<Map<String,Integer>> ages=new ArrayList<>();
            Map<String,Integer> age=new HashMap<>();
            if(StringUtils.isNotNullOrEmpty(minAge)){
                age.put("min",Integer.parseInt(minAge));
            }
            if(StringUtils.isNotNullOrEmpty(maxAge)){
                age.put("max",Integer.parseInt(maxAge));
            }
            ages.add(age);
            this.queryByAge(ages,query);
        }
        if(StringUtils.isNotNullOrEmpty(intentionCityName)){
            if(!intentionCityName.contains("全国")){
                intentionCityName=intentionCityName+",全国";
            }
            this.queryByIntentionCityTag(intentionCityName,query);
        }
        if(StringUtils.isNotNullOrEmpty(intentionSalaryCode)){
            this.queryBySlalryCode(intentionSalaryCode,query);
        }
        if(StringUtils.isNotNullOrEmpty(sex)&&Integer.parseInt(sex)!=0){
            this.queryByGender(sex,query);
        }
        if(Integer.parseInt(isRecommend)>0){
            this.queryByRecom(query);
        }
        if(StringUtils.isNotNullOrEmpty(companyName)){
            String lastCompany=params.get("in_last_job_search_company");
            if(StringUtils.isNotNullOrEmpty(lastCompany)&&"1".equals(lastCompany)){
                this.queryTermByLastCompanyTag(companyName,query);
            }else {
                this.queryTermByCompanyTag(companyName, query);
            }
        }
        if(StringUtils.isNotNullOrEmpty(origins)||StringUtils.isNotNullOrEmpty(submitTime)||Integer.parseInt(isRecommend)>0){
            //这里是处理groovy语法的位置
            StringBuffer sb=new StringBuffer();
            sb.append("user=_source.user;if(user){applications=user.applications;;origins=user.origin_data;if(applications){for(val in applications){if(");
            sb.append("val.company_id=="+companyId+"&&");
            if(StringUtils.isNotNullOrEmpty(submitTime)){
                String longTime=this.getLongTime(submitTime);
                sb.append(" val.submit_time>'"+longTime+"'&&");
            }
            if(Integer.parseInt(isRecommend)>0){
                sb.append("val.recommender_user_id>0 &&");
            }
            if(StringUtils.isNotNullOrEmpty(origins)){
                List<String> list=searchUtil.stringConvertList(origins);
                sb.append("(");
                for(String origin:list){
                    if("-99".equals(origin)||"99".equals(origin)){
                        sb.append(" (val.origin==1 ||val.origin==2 ||val.origin==4 ||val.origin==128 || val.origin==256 ||val.origin==512 ||val.origin==1024) ||");
                    }else{
                        if(origin.length()>8){
                            sb.append("('"+origin+"' in origins)||");
                        }else{
                            sb.append(" (val.origin=="+origin+")||");
                        }
                    }
                }
                sb.deleteCharAt(sb.lastIndexOf("|"));
                sb.deleteCharAt(sb.lastIndexOf("|"));
                sb.append(")");
            }else{
                sb.deleteCharAt(sb.lastIndexOf("&"));
                sb.deleteCharAt(sb.lastIndexOf("&"));
            }
            sb.append("){return true}}}");
            if(StringUtils.isNotNullOrEmpty(origins)){
                List<String> list=searchUtil.stringConvertList(origins);
                int flag=0;
                for(String origin:list){
                    if(!"-99".equals(origin)&&!"99".equals(origin)){
                        if(flag==0){
                            sb.append("else{if(");
                            flag=1;
                        }
                        sb.append("('"+origin+"' in origins)||");
                    }
                }
                if(flag==1){
                    sb.deleteCharAt(sb.lastIndexOf("|"));
                    sb.deleteCharAt(sb.lastIndexOf("|"));
                    sb.append("){return true;}}");
                    sb.append("}");
                }else{
                    sb.append("}");
                }

            }else{
                sb.append("}");
            }
            ScriptQueryBuilder script=new ScriptQueryBuilder(new Script(sb.toString()));
            ((BoolQueryBuilder) query).filter(script);
        }
        QueryBuilder nestQuery=this.queryTalentNest(params);
        ((BoolQueryBuilder)query).filter(nestQuery);
        return query;
    }
    public QueryBuilder queryTalentNest(Map<String,String> params){
        int companyId=Integer.parseInt(params.get("company_id"));
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        this.queryByNestCompanyId(companyId,query);
        String hrId=params.get("hr_id");
        if(StringUtils.isNotNullOrEmpty(hrId)){
            String account_type=params.get("account_type");
            if(StringUtils.isNotNullOrEmpty(account_type)&&Integer.parseInt(account_type)!=0){
                searchUtil.childAccountTalentpool(hrId,query);
            }
        }
        query=QueryBuilders.nestedQuery("user.talent_pool",query);
        return query;
    }

    /*
     组装查询语句
     */
    public QueryBuilder query(Map<String,String> params){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        this.handlerPositionId(params);
        this.queryCommons(params,query);
        this.queryProfiles(params,query);
        this.queryApplications(params,query);
        QueryBuilder queryAppScript=this.queryScript(params);
        if(queryAppScript!=null){
            ((BoolQueryBuilder) query).filter(queryAppScript);
        }
        QueryBuilder queryNest=this.queryNest(params);
        if(queryNest!=null){
            ((BoolQueryBuilder) query).filter(queryNest);
        }
        return query;
    }
    private void handlerSortOrder(Map<String,String> params,SearchRequestBuilder builder){
        String publisherIds=params.get("publisher");
        List<Integer> publisherIdList=convertStringToList(publisherIds);
        String hrId=params.get("hr_account_id");
        String companyId=params.get("company_id");
        if(!this.isUseFieldorder(params)){
            builder.addSort(this.handlerScoreOrderScript(publisherIds));
            if(this.isOrderTalent(params)){
                this.orderByTalent(publisherIdList,hrId,companyId,builder);
            }else {
                if (publisherIdList.size() > 1) {
                    builder.addSort("user.hr_all_" + hrId + "_last_submit_time", SortOrder.DESC);
                } else {
                    if (this.isMianHr(Integer.parseInt(hrId))) {
                        builder.addSort("user.hr_" + publisherIdList.get(0) + "_last_submit_time", SortOrder.DESC);
                    } else {
                        builder.addSort("user.hr_" + hrId + "_last_submit_time", SortOrder.DESC);
                    }
                }
            }
        }else{
            if(this.isOrderTalent(params)){
                this.orderByTalent(publisherIdList,hrId,companyId,builder);
            }else{
                this.orderByApp(publisherIdList,hrId,companyId,builder);
            }
            //如果查询多个。注解按照hr_all_+主账号_order


        }
    }
    /*
     根据处理好的收藏时间排序
     */
    private void orderByTalent(List<Integer> publisherIdList,String hrId,String companyId,SearchRequestBuilder builder){
        if (publisherIdList.size() > 1) {
            builder.addSort("user.field_talent_order.hr_all_"+hrId+"_order", SortOrder.DESC);
        }else{
            if(this.isMianHr(Integer.parseInt(hrId))){
                logger.info("==============================");
                builder.addSort("user.field_talent_order.hr_all_" + hrId + "_order", SortOrder.DESC);
            }else{
                UserHrAccountRecord record=this.getMainAccount(Integer.parseInt(companyId));
                logger.info("++++++++++++++++++++++++++++++++");
                builder.addSort("user.field_talent_order.hr_all_" + record.getId() + "_order", SortOrder.DESC);
//                builder.addSort("user.field_talent_order.hr_" + hrId + "_order", SortOrder.DESC);


            }
        }
    }
    /*
     根据处理好的申请时间排序
     */
    private void orderByApp(List<Integer> publisherIdList,String hrId,String companyId,SearchRequestBuilder builder){

        if (publisherIdList.size() > 1) {
            builder.addSort("user.field_order.hr_all_"+hrId+"_order", SortOrder.DESC);
        }else{
            if(this.isMianHr(Integer.parseInt(hrId))){
                logger.info("==============================");
                builder.addSort("user.field_order.hr_all_" + hrId + "_order", SortOrder.DESC);
            }else{
//                UserHrAccountRecord record=this.getMainAccount(Integer.parseInt(companyId));
                logger.info("++++++++++++++++++++++++++++++++");
//                builder.addSort("user.field_order.hr_all_" + record.getId() + "_order", SortOrder.DESC);
                builder.addSort("user.field_order.hr_" + hrId + "_order", SortOrder.DESC);
            }
        }
    }
    /*
     按照收藏时间排序
     */
    private boolean isOrderTalent(Map<String,String>params){
        String tagIds=params.get("tag_ids");
        String favoriteHrs=params.get("favorite_hrs");
        String isPublic=params.get("is_public");
        if(StringUtils.isNullOrEmpty(tagIds)&&StringUtils.isNullOrEmpty(favoriteHrs)&&StringUtils.isNullOrEmpty(isPublic)){
            return false;
        }
        return true;
    }


    /*
     处理分页
     */
    private  void handlerPage(Map<String,String> params,SearchRequestBuilder builder){
        String pageNum=params.get("page_number");
        String pageSize=params.get("page_size");
        if(StringUtils.isNullOrEmpty(pageNum)){
            pageNum="1";
        }
        if(StringUtils.isNullOrEmpty(pageSize)){
            pageSize="15";
        }
        builder.setFrom((Integer.parseInt(pageNum)-1)*Integer.parseInt(pageSize));
        builder.setSize(Integer.parseInt(pageSize));
    }
    /*
     处理返回值
     */
    private void handlerReturn(Map<String,String> params,SearchRequestBuilder builder){
        String returnParams=params.get("return_params");
        if(StringUtils.isNotNullOrEmpty(returnParams)){
            builder.setFetchSource(returnParams.split(","),null);
        }
    }
    /*
     组装基本部分的查询条件
     */
    private void queryCommons(Map<String,String> params,QueryBuilder query){
        String keyword=params.get("keyword");
        String cityName=params.get("city_name");
        String companyName=params.get("company_name");
        String pastPosition=params.get("past_position");
        String intentionCity=params.get("intention_city_name");
        String companyTag=params.get("company_tag");
        String pastPositionKeyWord=params.get("past_position_key_word");
        String pastCompanyKeyWord=params.get("past_company_key_word");
        if(this.validateCommon(keyword,cityName,companyName,pastPosition,intentionCity,companyTag,pastPositionKeyWord,pastCompanyKeyWord) ){
            if(StringUtils.isNotNullOrEmpty(intentionCity)){
                if(!intentionCity.contains("全国")){
                    intentionCity=intentionCity+",全国";
                }
                this.queryByIntentionCity(intentionCity,query);
            }
            if(StringUtils.isNotNullOrEmpty(keyword)){
                this.queryByKeyWord(keyword,query);
            }
            this.homeQuery(cityName,query);
            String lastCompany=params.get("in_last_job_search_company");
            this.pastCompanyQuery(lastCompany,companyName,pastCompanyKeyWord,query);
            String lastPosition=params.get("in_last_job_search_position");
            this.pastPositionQuery(lastPosition,pastPosition,pastPositionKeyWord,query);
            if(StringUtils.isNotNullOrEmpty(companyTag)){
                this.queryByCompanyTag(companyTag,query);
            }
        }

    }
    /*
     判断是否继续执行查询操作
     */
    private boolean validateCommon(String keyword,String cityName,String companyName,String pastPosition,String intentionCity,String companyTag,
                                   String pastPositionKeyWord,String pastCompanyKeyWord){
        return StringUtils.isNotNullOrEmpty(keyword)||StringUtils.isNotNullOrEmpty(cityName)||
                StringUtils.isNotNullOrEmpty(companyName)||StringUtils.isNotNullOrEmpty(pastPosition)||
                StringUtils.isNotNullOrEmpty(intentionCity)||StringUtils.isNotNullOrEmpty(companyTag)||
                StringUtils.isNotNullOrEmpty(pastPositionKeyWord)||StringUtils.isNotNullOrEmpty(pastCompanyKeyWord);
    }
    /*
     处理现居住地
     */
    private void homeQuery(String cityName,QueryBuilder query){
        if(StringUtils.isNotNullOrEmpty(cityName)){
            if(!cityName.contains("全国")){
                cityName=cityName+",全国";
            }
            this.queryByHome(cityName,query);
        }
    }
    /*
     查询曾任职位
     */
    private void pastCompanyQuery(String lastCompany,String companyName,String pastCompanyKeyWord,QueryBuilder query){
        if(StringUtils.isNotNullOrEmpty(pastCompanyKeyWord)){
            if(StringUtils.isNotNullOrEmpty(lastCompany)&&"1".equals(lastCompany)){
                this.queryByLastCompany(pastCompanyKeyWord,query);
            }else {
                this.queryByCompany(pastCompanyKeyWord, query);
            }
        }else{
            if(StringUtils.isNotNullOrEmpty(companyName)){

                if(StringUtils.isNotNullOrEmpty(lastCompany)&&"1".equals(lastCompany)){
                    this.queryByLastCompany(companyName,query);
                }else {
                    this.queryByCompany(companyName, query);
                }
            }
        }
    }
    /*
    处理曾任职位
     */
    private void pastPositionQuery(String lastPosition,String pastPosition,String pastPositionKeyWord,QueryBuilder query ){
        if(StringUtils.isNotNullOrEmpty(pastPositionKeyWord)){
            if(StringUtils.isNotNullOrEmpty(lastPosition)&&"1".equals(lastPosition)){
                this.queryByLastPositions(pastPositionKeyWord,query);
            }else{
                this.queryByWorkJob(pastPositionKeyWord,query);
            }
        }else{
            if(StringUtils.isNotNullOrEmpty(pastPosition)){
                if(StringUtils.isNotNullOrEmpty(lastPosition)&&"1".equals(lastPosition)){
                    this.queryByLastPositions(pastPosition,query);
                }else{
                    this.queryByWorkJob(pastPosition,query);
                }
            }
        }
    }
    /*
     组装简历部分查询条件
     */
    private QueryBuilder queryProfiles(Map<String,String> params,QueryBuilder query){
        String degree=params.get("degree");
        String intentionSalaryCode=params.get("intention_salary_code");
        String sex=params.get("sex");
        String workYears=params.get("work_years");
        String minAge=params.get("min_age");
        String maxAge=params.get("max_age");
        String updateTime=params.get("update_time");
        String extsis=params.get("extsis");
        if(
                StringUtils.isNotNullOrEmpty(degree)||StringUtils.isNotNullOrEmpty(intentionSalaryCode)||StringUtils.isNotNullOrEmpty(sex)||
                        StringUtils.isNotNullOrEmpty(workYears)||StringUtils.isNotNullOrEmpty(updateTime)||
                        ((StringUtils.isNotNullOrEmpty(minAge)||StringUtils.isNotNullOrEmpty(maxAge))&&(!"0".equals(minAge)||!"0".equals(maxAge)))
                )
        {
            if(StringUtils.isNotNullOrEmpty(degree)){
                this.QueryByDegree(degree,query);
            }
            if(StringUtils.isNotNullOrEmpty(intentionSalaryCode)){
                this.queryBySlalryCode(intentionSalaryCode,query);
            }
            if(StringUtils.isNotNullOrEmpty(sex)&&!"0".equals(sex)){
                this.queryByGender(sex,query);
            }
            if(StringUtils.isNotNullOrEmpty(workYears)){
                this.queryByWorkYear(workYears,query);
            }
            if(StringUtils.isNotNullOrEmpty(updateTime)){
                this.queryByProfileUpDateTime(updateTime,query);
            }
            if(((StringUtils.isNotNullOrEmpty(minAge)||StringUtils.isNotNullOrEmpty(maxAge))&&(!"0".equals(minAge)||!"0".equals(maxAge)))){
                List<Map<String,Integer>> ages=new ArrayList<>();
                Map<String,Integer> age=new HashMap<>();
                if(StringUtils.isNotNullOrEmpty(minAge)){
                    age.put("min",Integer.parseInt(minAge));
                }
                if(StringUtils.isNotNullOrEmpty(maxAge)){
                    age.put("max",Integer.parseInt(maxAge));
                }
                ages.add(age);
                this.queryByAge(ages,query);
            }

        }
        if(StringUtils.isNotNullOrEmpty(extsis)){
            this.exitsisQuery(extsis,query);
        }

        return query;
    }

    private void queryApplications(Map<String,String> params,QueryBuilder query){
        String publisherIds = params.get("publisher");
        String candidateSource = params.get("candidate_source");
        String recommend = params.get("is_recommend");
        String origins = params.get("origins");
        String submitTime = params.get("submit_time");
        String progressStatus = params.get("progress_status");
        String positionIds = params.get("position_id");
        String companyId = params.get("company_id");
        String positionWord=params.get("position_key_word");
        String positionStatus=params.get("position_status");
        if (this.validateApplication(publisherIds,candidateSource,recommend,origins,submitTime,progressStatus,positionIds,positionWord)) {
            String tagIds=params.get("tag_ids");
            String company_tag=params.get("company_tag");
            String favoriteHrs=params.get("favorite_hrs");
            String isPublic=params.get("is_public");
            if(StringUtils.isNullOrEmpty(tagIds)&&StringUtils.isNullOrEmpty(company_tag)&&StringUtils.isNullOrEmpty(favoriteHrs)&&StringUtils.isNullOrEmpty(isPublic)) {
                if (StringUtils.isNotNullOrEmpty(publisherIds)) {
                    this.queryByPublisher(publisherIds, query);
                }
            }
            if (StringUtils.isNotNullOrEmpty(candidateSource)) {
                this.queryByCandidateSource(Integer.parseInt(candidateSource), query);
            }
            if (StringUtils.isNotNullOrEmpty(recommend)) {
                this.queryByRecom(query);
            }
            if (StringUtils.isNotNullOrEmpty(submitTime)) {
                this.queryBySubmitTime(submitTime, query);
            }
            if (StringUtils.isNotNullOrEmpty(progressStatus)) {
                this.queryByProgress(Integer.parseInt(progressStatus), query);
            }
            if (StringUtils.isNotNullOrEmpty(origins)) {
                this.queryByOrigin(origins, companyId, query);
            }
            if(StringUtils.isNotNullOrEmpty(positionIds)){
                this.queryByPositionId(positionIds, query);
            }
            if(StringUtils.isNotNullOrEmpty(positionStatus)&&!"-1".equals(positionStatus)){
                this.queryByPositionIdStatus(positionStatus,query);
            }
        }

    }
    /*
     处理职位id,这里改变了参数
     */
    private void handlerPositionId(Map<String,String> params){
        String positionWord=params.get("position_key_word");
        if(StringUtils.isNotNullOrEmpty(positionWord)){
            String positionIds=this.PositionIdQuery(params,positionWord);
            if(StringUtils.isNotNullOrEmpty(positionIds)){
                params.put("position_id",positionIds);
            }
        }
    }
    /*
     处理职位
     */
    private String PositionIdQuery(Map<String,String> params,String positionWord){
        if(StringUtils.isNotNullOrEmpty(positionWord)){
            Map<String,String> suggetParams=this.convertParams(params);
            Map<String,Object> result=searchMethodUtil.suggestPosition(suggetParams);
            List<Integer> positionIdList=this.getSuggestPositionId(result);
            String positionIds=searchUtil.listConvertString(positionIdList);
            return positionIds;
        }
        return null;
    }
    /*
     获取positionId的列表
     */
    private List<Integer> getSuggestPositionId(Map<String,Object> result){
        List<Integer> positionIdList=new ArrayList<>();
        if(!StringUtils.isEmptyMap(result)){
            List<Map<String,Object>> positionList= (List<Map<String, Object>>) result.get("suggest");
            if(!StringUtils.isEmptyList(positionList)){
                for(Map<String,Object> position:positionList){
                    int positionId= (int) position.get("id");
                    positionIdList.add(positionId);
                }
            }
        }
        return positionIdList;
    }
    /*
     组装参数
     */
    private Map<String,String> convertParams(Map<String,String> params){
        Map<String,String> suggetParams=new HashMap<>();
        suggetParams.put("keyWord",params.get("position_key_word"));
        suggetParams.put("company_id",params.get("company_id"));
        suggetParams.put("publisher",params.get("publisher"));
        suggetParams.put("page_from","1");
        suggetParams.put("page_size","1000000");
        suggetParams.put("return_params","title,id");
        String status=params.get("position_status");
        if(StringUtils.isNotNullOrEmpty(status)&&!"-1".equals(status)){
            suggetParams.put("flag","0");
        }else{
            suggetParams.put("flag","1");
        }
        return suggetParams;
    }
    /*
     校验是否向下执行
     */
    private boolean validateApplication(String publisherIds,String candidateSource,String recommend,String origins,String submitTime,
                                        String progressStatus,String positionIds,String positionWord){
        return StringUtils.isNotNullOrEmpty(publisherIds) || StringUtils.isNotNullOrEmpty(candidateSource) || StringUtils.isNotNullOrEmpty(recommend) ||
                StringUtils.isNotNullOrEmpty(origins) || StringUtils.isNotNullOrEmpty(submitTime) ||
                StringUtils.isNotNullOrEmpty(progressStatus) || StringUtils.isNotNullOrEmpty(positionIds);
    }

    /*
     组装nest的查询语句
     */
    public QueryBuilder queryNest(Map<String,String> params){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        String tagIds=params.get("tag_ids");
        //此处尤其要记住。当企业标签不为空时，根据是否是主账号将tagids置位alltalent和talent
        String company_tag=params.get("company_tag");
        if(StringUtils.isNotNullOrEmpty(company_tag)){
            String allPublisher=params.get("all_publisher");
            if(StringUtils.isNotNullOrEmpty(allPublisher)&&"1".equals(allPublisher)){
                tagIds="alltalent";
            }else{
                tagIds="talent";
            }
        }
        String favoriteHrs=params.get("favorite_hrs");
        String isPublic=params.get("is_public");
        if(StringUtils.isNullOrEmpty(tagIds)&&StringUtils.isNullOrEmpty(favoriteHrs)&&StringUtils.isNullOrEmpty(isPublic)){
            return null;
        }
        String companyId=params.get("company_id");
        this.queryByNestCompanyId(Integer.parseInt(companyId),query);
        if(StringUtils.isNotNullOrEmpty(tagIds)){
            String hrId=params.get("hr_account_id");
            this.queryByTagId(tagIds,hrId,query);
        }

        if(StringUtils.isNotNullOrEmpty(favoriteHrs)){
            this.queryTagHrId(favoriteHrs,query);
        }
        if(StringUtils.isNotNullOrEmpty(isPublic)){
            this.queryByPublic(Integer.parseInt(isPublic),query);
        }
        if(StringUtils.isNotNullOrEmpty(favoriteHrs)||StringUtils.isNotNullOrEmpty(isPublic)){
            this.queryByIstalent(query);
        }
        query=QueryBuilders.nestedQuery("user.talent_pool",query);
        return query;
    }

    /*
      使用script的方式组装对application的查询
     */

    public ScriptQueryBuilder queryScript(Map<String,String> params){
        String publisherIds=params.get("publisher");
        String candidateSource=params.get("candidate_source");
        String recommend=params.get("is_recommend");
        String origins=params.get("origins");
        String submitTime=params.get("submit_time");
        String progressStatus=params.get("progress_status");
        String positionId=params.get("position_id");
        String tagIds=params.get("tag_ids");
        String companyTag=params.get("company_tag");
        String favoriteHrs=params.get("favorite_hrs");
        String isPublic=params.get("is_public");
        String companyId=params.get("company_id");
        String positionStatus=params.get("position_status");
        if( StringUtils.isNullOrEmpty(progressStatus)&&StringUtils.isNullOrEmpty(candidateSource)&&StringUtils.isNullOrEmpty(recommend)
                &&StringUtils.isNullOrEmpty(origins)&&StringUtils.isNullOrEmpty(submitTime)&&StringUtils.isNullOrEmpty(positionId)
                &&StringUtils.isNullOrEmpty(positionStatus)&&!"-1".equals(positionStatus)){
            return null;
        }
        StringBuffer sb=new StringBuffer();
        sb.append("user=_source.user;if(user){applications=user.applications;;origins=user.origin_data;if(applications){for(val in applications){if(");

        if(StringUtils.isNullOrEmpty(tagIds)&&StringUtils.isNullOrEmpty(companyTag)&&StringUtils.isNullOrEmpty(favoriteHrs)&&StringUtils.isNullOrEmpty(isPublic)){
            if(StringUtils.isNotNullOrEmpty(publisherIds)){
                List<Integer> publisherIdList=this.convertStringToList(publisherIds);
                if(!StringUtils.isEmptyList(publisherIdList)){
                    sb.append("val.publisher in "+publisherIdList.toString()+"&&");
                }
            }
        }
        if(StringUtils.isNotNullOrEmpty(positionStatus)&&!"-1".equals(positionStatus)){
            sb.append("val.status=="+positionStatus+"&&");
        }
        if(StringUtils.isNotNullOrEmpty(companyId)&& StringUtils.isNullOrEmpty(tagIds)){
            sb.append("val.company_id=="+companyId+"&&");
        }
        if(StringUtils.isNotNullOrEmpty(candidateSource)){
            sb.append("val.candidate_source=="+candidateSource+"&&");
        }
        if(StringUtils.isNotNullOrEmpty(recommend)){
            sb.append("val.recommender_user_id>0 &&");
        }
        if(StringUtils.isNotNullOrEmpty(submitTime)){
            String longTime=this.getLongTime(submitTime);
            sb.append(" val.submit_time>'"+longTime+"'&&");
        }
        if(StringUtils.isNotNullOrEmpty(progressStatus)&&Integer.parseInt(progressStatus)>-1){
            sb.append(" val.progress_status=="+progressStatus+"&&");
        }
        if(StringUtils.isNotNullOrEmpty(positionId)){
            List<Integer> positionIdList=this.convertStringToList(positionId);
            sb.append(" val.position_id in "+positionIdList.toString()+"&&");
        }
        if(StringUtils.isNotNullOrEmpty(origins)){
            List<String> list=searchUtil.stringConvertList(origins);
            sb.append("(");
            for(String origin:list){
                if("-99".equals(origin)||"99".equals(origin)){
                    sb.append(" (val.origin==1 ||val.origin==2 ||val.origin==4 ||val.origin==128 || val.origin==256 ||val.origin==512 ||val.origin==1024) ||");
                }else{
                    if(origin.length()>8){
                        sb.append("('"+origin+"' in origins)||");
                    }else{
                        sb.append(" (val.origin=="+origin+")||");
                    }
                }
            }
            sb.deleteCharAt(sb.lastIndexOf("|"));
            sb.deleteCharAt(sb.lastIndexOf("|"));
            sb.append(")");
        }else{
            sb.deleteCharAt(sb.lastIndexOf("&"));
            sb.deleteCharAt(sb.lastIndexOf("&"));
        }
        sb.append("){return true}}}");

        if(StringUtils.isNotNullOrEmpty(origins)){
            List<String> list=searchUtil.stringConvertList(origins);
            int flag=0;
            for(String origin:list){
                if(!"-99".equals(origin)&&!"99".equals(origin)){
                    if(flag==0){
                        sb.append("else{if(");
                        flag=1;
                    }
                    sb.append("('"+origin+"' in origins)||");
                }
            }
            if(flag==1){
                sb.deleteCharAt(sb.lastIndexOf("|"));
                sb.deleteCharAt(sb.lastIndexOf("|"));
                sb.append("){return true;}}");
                sb.append("}");
            }else{
                sb.append("}");
            }

        }else{
            sb.append("}");
        }

        ScriptQueryBuilder script=new ScriptQueryBuilder(new Script(sb.toString()));
        return script;
    }
    /*
      查询该hr是否在索引当中
     */
    private Map<String,Object> getUserAnalysisIndex(Map<String,String> params,TransportClient client){
        SearchRequestBuilder responseBuilder=client.prepareSearch("users_analysis").setTypes("analysis")
                .setQuery(this.queryAggIndex(params));
        SearchResponse response = responseBuilder.execute().actionGet();
        long hitNum=response.getHits().getTotalHits();
        if(hitNum==0){
            return null;
        }
        Map<String,Object> result=searchUtil.handleData(response,"aggs");
        return result;
    }
    /*
     获取主账号查询所有的统计
     */
    private QueryBuilder queryAggIndex(Map<String,String> params){
        String hrId=params.get("hr_account_id");
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        searchUtil.handleMatch(Integer.parseInt(hrId),query,"account_id");
        return query;
    }


    private List<Integer> convertStringToList(String params){
        if(StringUtils.isNullOrEmpty(params)){
            return null;
        }
        List<Integer> list=new ArrayList<>();
        String[] arr=params.split(",");
        for(String item:arr){
            list.add(Integer.parseInt(item));
        }
        return list;
    }
    /*
     根据user_id查询es数据
     */
    private QueryBuilder queryByUserId(List<Integer> userIdList){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        List<String> fieldList=new ArrayList<>();
        fieldList.add("user.profiles.profile.user_id");
        fieldList.add("user.applications.applier_id");
        searchUtil.shouldTermsQuery(fieldList,userIdList,query);
        return query;
    }
    /*
     根据简历的更新时间查询
     */
    private void queryByProfileUpDateTime(String updateTime,QueryBuilder queryBuilder){

        String time=this.getLongTime(updateTime);
        this.searchUtil.hanleRangeFilter(time,queryBuilder,"user.profiles.profile.update_time");
    }

    /*
     创建按照hr查询的索引语句
     */
    private void queryByPublisher(String publisherIds,QueryBuilder queryBuilder){
        searchUtil.handleTermsFilter(publisherIds,queryBuilder,"user.applications.publisher");
    }
    /*
     构建按照关键词查询的索引语句
     */
    private void queryByKeyWord(String keyWord,QueryBuilder queryBuilder){
        List<String> fieldList=this.getFieldList();
        List<Integer> boostList=this.getBoostList();
        searchUtil.keyWordforQueryStringPropery(keyWord,queryBuilder,fieldList,boostList);
    }
    /*
       构建招聘类型的查询语句
     */
    private void queryByCandidateSource(int source,QueryBuilder queryBuilder){
        searchUtil.handleMatchFilter(source,queryBuilder,"user.applications.candidate_source");
    }

    private void queryByPositionStatus(int status,QueryBuilder queryBuilder){
        searchUtil.handleMatchFilter(status,queryBuilder,"user.applications.status");
    }
    /*
      构建是否内推的查询语句
     */
    private void queryByRecom(QueryBuilder queryBuilder){
        searchUtil.hanleRangeFilter(0,queryBuilder,"user.applications.recommender_user_id");
    }
    /*
      构建简历来源的查询语句
     */
    private void queryByOrigin(String condition,String companyId,QueryBuilder queryBuilder){
        searchUtil.handleOrigins(condition,companyId,queryBuilder);
    }
    /*
      构建通过职位来查询的语句
     */
    private void queryByPositionId(String positionIds,QueryBuilder queryBuilder ){
        searchUtil.handleTerms(positionIds,queryBuilder,"user.applications.position_id");
    }

    private void queryByPositionIdStatus(String status,QueryBuilder queryBuilder ){
        searchUtil.handleTerms(status,queryBuilder,"user.applications.status");
    }


    /*
      构建是否公开的查询语句,注意这个位置要做成nest的查询
     */
    private void queryByPublic(int isPublic,QueryBuilder queryBuilder){
        searchUtil.handleMatch(isPublic,queryBuilder,"user.talent_pool.is_public");
    }
    /*
      构建按招标签的查询语句
     */
    private void queryByTagId(String tagIds,String hrId,QueryBuilder queryBuilder){
        searchUtil.handlerTagIds(tagIds,hrId,queryBuilder);
    }
    /*
      构建按招标签的查询语句
     */
    private void queryByCompanyTag(String companyTag,QueryBuilder queryBuilder){
        searchUtil.handlerCompanyTag(companyTag,queryBuilder);
    }
    /*
     构建和公司相关的人才库
     */
    private void queryByNestCompanyId(int companyId,QueryBuilder queryBuilder){
        searchUtil.handleMatch(companyId,queryBuilder,"user.talent_pool.company_id");
    }
    /*
      构建按照期望城市名称的查询语句
     */
    private void queryByIntentionCity(String cityNames,QueryBuilder queryBuilder){
        searchUtil.handleMatch(cityNames,queryBuilder,"user.profiles.intentions.cities.city_name");
    }
    private void queryByIntentionCityTag(String cityNames,QueryBuilder queryBuilder){
        List<String> list=new ArrayList<>();
        list.add("user.profiles.intentions.cities.city_name");
        searchUtil.shouldMatchParseQuery(list,cityNames,queryBuilder);
//        searchUtil.handleMatch(cityNames,queryBuilder,"user.profiles.intentions.cities.city_name");
    }
    /*
      按照公司名称查询
     */
    private void queryByComapnyId(String companyId,QueryBuilder queryBuilder){
        searchUtil.handleTermsFilter(companyId,queryBuilder,"user.applications.company_id");
    }
    /*
      按照学历查询
     */
    private void QueryByDegree(String degrees,QueryBuilder queryBuilder){
        searchUtil.handleTermsFilter(degrees,queryBuilder,"user.profiles.basic.highest_degree");
    }

    private void queryParseByLastCompany(String companys,QueryBuilder queryBuilder){
        searchUtil.handleMatchParse(companys,queryBuilder,"user.profiles.recent_job.company_name");
    }
    /*
      按照最后工作的职位名称查询
     */
    private void queryByLastPositions(String positions,QueryBuilder queryBuilder){
        searchUtil.handleMatch(positions,queryBuilder,"user.profiles.recent_job.job");
    }
    private void queryParseByLastPositions(String positions,QueryBuilder queryBuilder){
        List<String> list=new ArrayList<>();
        list.add("user.profiles.recent_job.job_name");
        searchUtil.shouldMatchParseQuery(list,positions,queryBuilder);
//        searchUtil.handleMatchParse(positions,queryBuilder,"user.profiles.recent_job.job_name");
    }
    private void queryTermsByLastPositions(String positions,QueryBuilder queryBuilder){
        Map<String,Object> map=new HashMap<>();
        map.put("user.profiles.recent_job.job_name",searchUtil.stringConvertList(positions));
        searchUtil.shouldTermQuery(map,queryBuilder);
//        searchUtil.handleMatchParse(positions,queryBuilder,"user.profiles.recent_job.job_name");
    }
    /*
      按照现居住地查询
     */

    private void queryByHome(String home,QueryBuilder queryBuilder){
        searchUtil.handleTerms(home,queryBuilder,"user.profiles.basic.city_name");
    }
    /*
      按照期望薪资查询
     */
    private void queryBySlalryCode(String salaryCode,QueryBuilder queryBuilder){
        searchUtil.handleTerms(salaryCode,queryBuilder,"user.profiles.intentions.salary_code");
    }
    /*
      按照年龄查询
     */
    private void queryByAge(List<Map<String,Integer>> ages,QueryBuilder queryBuilder){
        searchUtil.shoudAgeFilter(ages,queryBuilder,"user.age");
    }

    /*
      按照曾任职务查询
     */
    private void queryByWorkJob(String works,QueryBuilder queryBuilder){
        List<String> list=new ArrayList<>();
        list.add("user.profiles.recent_job.job");
        list.add("user.profiles.other_workexps.job");
        searchUtil.shouldMatchQuery(list,works,queryBuilder);
    }
    private void queryParseByWorkJob(String works,QueryBuilder queryBuilder){
        List<String> list=new ArrayList<>();
        list.add("user.profiles.recent_job.job_name");
        list.add("user.profiles.other_workexps.job_name");
        searchUtil.shouldMatchParseQuery(list,works,queryBuilder);
    }
    private void queryTermsByWorkJob(String works,QueryBuilder queryBuilder){
        Map<String,Object> map=new HashMap<>();
        map.put("user.profiles.recent_job.job_name",searchUtil.stringConvertList(works));
        map.put("user.profiles.other_workexps.job_name",searchUtil.stringConvertList(works));
        searchUtil.shouldTermQuery(map,queryBuilder);
    }
    /*
      按照最后工作的公司查询
     */
    private void queryByLastCompany(String companys,QueryBuilder queryBuilder){
        List<String> list=new ArrayList<>();
        list.add("user.profiles.recent_job.company_name");
        searchUtil.shouldMatchQuery(list,companys,queryBuilder);
    }
    private void queryParseByLastCompanyTag(String companys,QueryBuilder queryBuilder){
        List<String> list=new ArrayList<>();
        list.add("user.profiles.recent_job.company_new_name");
        searchUtil.shouldMatchParseQuery(list,companys,queryBuilder);
    }
    private void queryTermByLastCompanyTag(String companys,QueryBuilder queryBuilder){
        Map<String,Object> map=new HashMap<>();
        map.put("user.profiles.recent_job.company_new_name",searchUtil.stringConvertList(companys));
        searchUtil.shouldTermQuery(map,queryBuilder);
    }

    private void exitsisQuery(String exitsis,QueryBuilder queryBuilder){
        List<String> list=searchUtil.stringConvertList(exitsis);
        searchUtil.exitsisQuery(list,queryBuilder);
    }
    /*
     构建通过曾经工作的公司查询
     */
    private void queryByCompany(String companys,QueryBuilder queryBuilder){
        List<String> list=new ArrayList<>();
        list.add("user.profiles.recent_job.company_name");
        list.add("user.profiles.other_workexps.company_name");
        searchUtil.shouldMatchQuery(list,companys,queryBuilder);
    }
    private void queryParseByCompanyTag(String companys,QueryBuilder queryBuilder){
        List<String> list=new ArrayList<>();
        list.add("user.profiles.recent_job.company_new_name");
        list.add("user.profiles.other_workexps.company_new_name");
        searchUtil.shouldMatchParseQuery(list,companys,queryBuilder);
    }
    private void queryTermByCompanyTag(String companys,QueryBuilder queryBuilder){
        Map<String,Object> map=new HashMap<>();
        map.put("user.profiles.recent_job.company_new_name",searchUtil.stringConvertList(companys));
        map.put("user.profiles.other_workexps.company_new_name",searchUtil.stringConvertList(companys));
        searchUtil.shouldTermQuery(map,queryBuilder);
    }
    /*
      按照性别查询
     */
    private void queryByGender(String gender,QueryBuilder queryBuilder){
        searchUtil.handleTerm(gender,queryBuilder,"user.profiles.basic.gender");
    }
    /*
      按照投递时间查询
     */
    private void queryBySubmitTime(String submitTime,QueryBuilder queryBuilder){
        String dataTime=this.getLongTime(submitTime);
        searchUtil.hanleRangeFilter(dataTime,queryBuilder,"user.applications.submit_time");
    }

    private String getLongTime(String submitTime){
        SimpleDateFormat ff=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date=new Date();
        long time=Long.parseLong(submitTime);
        if(time==1){
            time=3L;
        }else if(time==2){
            time=7L;
        }else if(time==3){
            time=30L;
        }
        long datetime=date.getTime();
        long preTime=time*3600*24*1000;
        long longTime=datetime-preTime;
        Date nowTime=new Date(longTime);
        String nowDate=ff.format(nowTime);
        nowDate=nowDate.replace(" ","T");
        return nowDate;
    }
    /*
      按照工作年限查新
     */
    private void queryByWorkYear(String workYears,QueryBuilder queryBuilder){
        List<Map<String,Integer>> list=this.handlerWorkYears(workYears);//this.convertParams(workYears);
        if(!StringUtils.isEmptyList(list)){
            searchUtil.shoudWorkYearsListFilter(list,queryBuilder,"user.work_year");
        }

    }

    private List<Map<String,Integer>> handlerWorkYears(String workYears){
        List<String> list=searchUtil.stringConvertList(workYears);
        List<Map<String,Integer>> result=new ArrayList<>();
        for(String key:list){
            int year=Integer.parseInt(key);
            Map<String,Integer> map=new HashMap<>();
            if(year==1){
                map.put("min",0);
                map.put("max",0);
            }else if(year==2){
                map.put("min",0);
                map.put("max",1);
            }else if(year==3){
                map.put("min",1);
                map.put("max",3);
            }else if(year==4){
                map.put("min",3);
                map.put("max",5);
            }else if(year==5){
                map.put("min",5);
                map.put("max",10);
            }else{
                map.put("min",10);
                map.put("max",100);
            }
            result.add(map);
        }
        return result;
    }
    /*
      按照招聘进度查询
     */
    private void queryByProgress(int progress,QueryBuilder queryBuilder){
        searchUtil.handleMatchFilter(progress,queryBuilder,"user.applications.progress_status");
    }
    /*
     根据hr的标签查询
     */
    private void queryTagHrId(String hrIds,QueryBuilder queryBuilder){
        searchUtil.handleTerms(hrIds,queryBuilder,"user.talent_pool.hr_id");
    }

    /*
     按照是否是收藏的人才搜索
     */
    private void queryByIstalent(QueryBuilder queryBuilder){
        searchUtil.handleMatch(1,queryBuilder,"user.talent_pool.is_talent");
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
    /*
     组装排序语句按照得分
     */
    private SortBuilder handlerScoreOrderScript(String publisherIds){
        List<Integer> publisherIdList=this.convertStringToList(publisherIds);
        StringBuffer sb=new StringBuffer();
        sb.append("double score = _score;int values=1;");
        sb.append("for (val in _source.user.applications){");
        sb.append("if((val.publisher in "+publisherIdList.toString()+") && val.not_suitable==0){values=0;break;}};");
        sb.append("if(values==1){score=score/10};return score;");
        Script script=new Script(sb.toString());
        SortBuilder builder=new ScriptSortBuilder(script,"number");
        builder.order(SortOrder.DESC);
        return builder;
    }
    /*
     组装没有publisher的排序
     */
    private SortBuilder handlerSort(int hrId,int type){
        StringBuffer sb=new StringBuffer();
        sb.append("double score = 0;fieldOrder=_source.user.field_order;if(fieldOrder){");
        if(type==1){
            sb.append("time=fieldOrder.hr_all"+hrId+"_order;if(time){score=time}};return score;");
        }else{
            sb.append("time=fieldOrder.hr_"+hrId+"_order;if(time){score=time}};return score;");
        }
        Script script=new Script(sb.toString());
        SortBuilder builder=new ScriptSortBuilder(script,"number");
        builder.order(SortOrder.DESC);
        return builder;
    }
    /*
     组装获取所有数量统计语句
     */

    private AbstractAggregationBuilder handleAllcountAgg(Map<String,String> params){
        MetricsAggregationBuilder build= AggregationBuilders.scriptedMetric("all_count")
                .initScript(new Script(getAggInitScript()))
                .mapScript(new Script(this.getAggMapScript(params,null,0)))
                .reduceScript(new Script(this.getAggReduceScript()))
                .combineScript(new Script(this.getAggCombineScript()));
        return build;
    }

    private AbstractAggregationBuilder handleAggInfo(Map<String,String> params,String name,int progressStatus,int type){
        MetricsAggregationBuilder build= AggregationBuilders.scriptedMetric(name)
                .initScript(new Script(getAggInitScript()))
                .mapScript(new Script(this.getAggMapScript(params,progressStatus+"",type)))
                .reduceScript(new Script(this.getAggReduceScript()))
                .combineScript(new Script(this.getAggCombineScript()));
        return build;
    }
    /*
      所有申请的统计
     */
    private AbstractAggregationBuilder handleAllApplicationCountAgg(Map<String,String> params){
        String progressStatus = params.get("progress");
        MetricsAggregationBuilder build= AggregationBuilders.scriptedMetric("all_application_count")
                .initScript(new Script(getAggInitScript()))
                .mapScript(new Script(this.getAggMapScript(params,progressStatus,1)))
                .reduceScript(new Script(this.getAggReduceScript()))
                .combineScript(new Script(this.getAggCombineScript()));
        return build;
    }

    /*
     根据不同的条件组装聚合语句
     */
    private String getAggMapScript(Map<String,String> params,String progressStatus,int type){
        String publishIds=params.get("publisher");
        String submitTime=params.get("submit_time");
        String positionIds=params.get("position_id");
        String candidateSource=params.get("candidate_source");
        String recommend=params.get("only_recommend");
        String positionStatus=params.get("position_status");
        List<Integer> publisherIdList=this.convertStringToList(publishIds);
        StringBuffer sb=new StringBuffer();
        sb.append("int i = 0; for ( val in _source.user.applications)");
        sb.append("{if((val.publisher in "+publisherIdList.toString()+") &&");
        if(StringUtils.isNotNullOrEmpty(progressStatus)){
            sb.append("val.progress_status=="+progressStatus+"&&");
        }
        if(StringUtils.isNotNullOrEmpty(submitTime)){
            String time=this.getLongTime(submitTime);
            sb.append("val.submit_time>'"+time+"'&&");
        }
        if(StringUtils.isNotNullOrEmpty(positionIds)){
            List<Integer> positionIdList=this.convertStringToList(positionIds);
            sb.append("val.position_id in"+positionIdList.toString()+"&&");
        }
        if(StringUtils.isNotNullOrEmpty(candidateSource)){
            sb.append("val.candidate_source =="+candidateSource+"&&");
        }
        if(StringUtils.isNotNullOrEmpty(recommend)){
            sb.append("val.recommender_user_id >0 &&");
        }
        if(StringUtils.isNotNullOrEmpty(positionStatus)&&!"-1".equals(positionStatus)){
            sb.append("val.status =="+positionStatus+" &&");
        }
        sb=sb.deleteCharAt(sb.lastIndexOf("&"));
        sb=sb.deleteCharAt(sb.lastIndexOf("&"));
        sb.append("){i=i+1;");
        if(type==0){
            sb.append("break;");
        }
        sb.append("}};_agg['transactions'].add(i)");
        return sb.toString();
    }
    /*
     获取talentpool的reduce_script
     */
    private String getAggReduceScript(){
        StringBuffer sb=new StringBuffer();
        sb.append("profit = 0; for (a in _aggs) { profit += a }; return profit");
        return sb.toString();
    }

    /*
     获取talentpool的init_script
     */
    private String getAggInitScript(){
        StringBuffer sb=new StringBuffer();
        sb.append("_agg['transactions'] = []");
        return sb.toString();
    }

    /*
     获取talentpool的combine_script
     */
    private String getAggCombineScript(){
        StringBuffer sb=new StringBuffer();
        sb.append("profit = 0; for (t in _agg.transactions) { profit += t }; return profit");
        return sb.toString();
    }
    /*
     判断当前操作人是否是主账号
     */
    private Boolean isMianHr(int hrId){
        Query query=new Query.QueryBuilder().where("id",hrId).and(new Condition("account_type",1, ValueOp.NEQ))
                .and("activation",1).and("disable",1).buildQuery();
        int count=userHrAccountDao.getCount(query);
        if(count>0){
            return true;
        }
        return false;
    }
    /*
     判断是否进行统计
     */
    private boolean isExecAgg(String returnParams){
        if(StringUtils.isNullOrEmpty(returnParams)){
            return false;
        }
        List<String> returnParamsList=searchUtil.stringConvertList(returnParams);
        if(returnParamsList.size()==2){
            if(returnParamsList.contains("user.applications.id")&&returnParamsList.contains("user.applications.applier_id")){
                return true;
            }
        }
        return false;
    }
    /*
     判断是否可以走单独的统计索引
     */
    private boolean valididateSearchAggIndex(Map<String,String> params){
        boolean flag=true;
        for(String key:params.keySet()){
            if(!"company_id".equals(key)&&!"publisher".equals(key)
                    &&!"hr_account_id".equals(key)&&!"all_publisher".equals(key)&&!"hr_id".equals(key)){
                return false;
            }
        }
        if(flag){
            if(StringUtils.isNullOrEmpty(params.get("all_publisher"))||!"1".equals(params.get("all_publisher"))){
                return false;
            }
        }
        return true;
    }
    /*
       处理排序的方式
     */
    private boolean isUseFieldorder(Map<String,String> params){
        String keyword=params.get("keyword");
        String cityName=params.get("city_name");
        String companyName=params.get("company_name");
        String pastPosition=params.get("past_position");
        String intentionCity=params.get("intention_city_name");
        if(StringUtils.isNotNullOrEmpty(keyword)||StringUtils.isNotNullOrEmpty(keyword)||StringUtils.isNotNullOrEmpty(cityName)||
                StringUtils.isNotNullOrEmpty(companyName)||StringUtils.isNotNullOrEmpty(pastPosition) ||StringUtils.isNotNullOrEmpty(intentionCity)){
            return false;
        }
        return true;
    }

    /*
     获取主账号
     */
    private UserHrAccountRecord getMainAccount(int companyId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and(new Condition("account_type",1,ValueOp.NEQ))
                .and("activation",1).and("disable",1).buildQuery();
        UserHrAccountRecord record=userHrAccountDao.getRecord(query);
        return record;
    }



}


