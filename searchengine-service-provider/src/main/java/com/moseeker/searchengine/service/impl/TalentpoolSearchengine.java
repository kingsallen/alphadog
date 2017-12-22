package com.moseeker.searchengine.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.searchengine.util.SearchUtil;
import org.apache.log4j.Logger;
import org.apache.lucene.queryparser.xml.builders.FilteredQueryBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
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

import java.io.IOException;
import java.util.*;

/**
 * Created by zztaiwll on 17/12/8.
 */
@Service
public class TalentpoolSearchengine {
    private  Logger logger=Logger.getLogger(this.getClass());
    @Autowired
    private SearchUtil searchUtil;
    @Autowired
    private UserHrAccountDao userHrAccountDao;

    @CounterIface
    public Map<String,Object>  talentSearch(Map<String,String> params){
        String publisherIds=params.get("publisher");
        List<Integer> publisherIdList=convertStringToList(publisherIds);
        String hrId=params.get("hr_account_id");
        String keyword=params.get("keyword");
        String cityName=params.get("city_name");
        String companyName=params.get("company_name");
        String pastPosition=params.get("past_position");
        String intentionCity=params.get("intention_city_name");
        String returnParams=params.get("return_params");
        TransportClient client= searchUtil.getEsClient();
        QueryBuilder query=this.query(params);
        SearchRequestBuilder builder=client.prepareSearch("users").setTypes("users").setQuery(query);
        Map<String,Object> aggInfo=new HashMap<>();
        String searchType=params.get("all_publisher");
        if(StringUtils.isNotNullOrEmpty(searchType)){
            if("1".equals(searchType)){
                aggInfo=this.getUserAnalysisIndex(params,client);
            }
        }
        if(aggInfo==null||aggInfo.isEmpty()){
            if(!this.isExecAgg(returnParams)) {
                builder.addAggregation(this.handleAllApplicationCountAgg(params))
                        .addAggregation(this.handleAllcountAgg(params))
                        .addAggregation(this.handleEntryCountAgg(params))
                        .addAggregation(this.handleFirstTrialOkCountAgg(params))
                        .addAggregation(this.handleInterviewOkCountAgg(params))
                        .addAggregation(this.handleIsViewedCountAgg(params))
                        .addAggregation(this.handleNotViewedCountAgg(params));
            }
        }

        if(StringUtils.isNotNullOrEmpty(keyword)||StringUtils.isNotNullOrEmpty(keyword)||StringUtils.isNotNullOrEmpty(cityName)||
           StringUtils.isNotNullOrEmpty(companyName)||StringUtils.isNotNullOrEmpty(pastPosition) ||StringUtils.isNotNullOrEmpty(intentionCity)
           )
        {
            builder.addSort(this.handlerScoreOrderScript(publisherIds));
            if (publisherIdList.size() > 1) {
                builder.addSort("user.hr_all_" + hrId + "_last_submit_time", SortOrder.DESC);
            }else{
                if(this.isMianHr(Integer.parseInt(hrId))){
                    builder.addSort("user.hr_" + publisherIdList.get(0) + "_last_submit_time", SortOrder.DESC);
                }else{
                    builder.addSort("user.hr_" + hrId + "_last_submit_time", SortOrder.DESC);
                }
            }
        }else{
            //如果查询多个。注解按照hr_all_+主账号_last_submit_time
            if (publisherIdList.size() > 1) {
                builder.addSort("user.field_order.hr_all_"+hrId+"_order", SortOrder.DESC);
            }else{
                if(this.isMianHr(Integer.parseInt(hrId))){
                    builder.addSort("user.hr_" + publisherIdList.get(0) + "_last_submit_time", SortOrder.DESC);
                }else{
                    builder.addSort("user.hr_" + hrId + "_last_submit_time", SortOrder.DESC);
                }
            }
        }

        String pageNum=params.get("page_number");
        String pageSize=params.get("page_size");
        if(StringUtils.isNullOrEmpty(pageNum)){
            pageNum="0";
        }
        if(StringUtils.isNullOrEmpty(pageSize)){
            pageSize="15";
        }

        builder.setFrom(Integer.parseInt(pageNum)*Integer.parseInt(pageSize));
        builder.setSize(Integer.parseInt(pageSize));
        builder.setTrackScores(true);
        if(StringUtils.isNotNullOrEmpty(returnParams)){
            builder.setFetchSource(returnParams.split(","),null);
        }

        logger.info(builder.toString());
        SearchResponse response = builder.execute().actionGet();
        Map<String,Object>result=searchUtil.handleData(response,"users");
        if(aggInfo!=null&&!aggInfo.isEmpty()){
            result.put("agg",aggInfo);
        }
        return result;
    }
    /*
     组装查询语句
     */
    public QueryBuilder query(Map<String,String> params){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
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
    /*
     组装基本部分的查询条件
     */
    private void queryCommons(Map<String,String> params,QueryBuilder query){
        String keyword=params.get("keyword");
        String cityName=params.get("city_name");
        String companyName=params.get("company_name");
        String pastPosition=params.get("past_position");
        String intentionCity=params.get("intention_city_name");
        if(
            StringUtils.isNotNullOrEmpty(keyword)||
            StringUtils.isNotNullOrEmpty(keyword)||
            StringUtils.isNotNullOrEmpty(cityName)||
            StringUtils.isNotNullOrEmpty(companyName)||
            StringUtils.isNotNullOrEmpty(pastPosition)||
            StringUtils.isNotNullOrEmpty(intentionCity)
         ){
            if(StringUtils.isNotNullOrEmpty(intentionCity)){
                this.queryByIntentionCity(intentionCity,query);
            }
            if(StringUtils.isNotNullOrEmpty(keyword)){
                this.queryByKeyWord(keyword,query);
            }
            if(StringUtils.isNotNullOrEmpty(cityName)){
                this.queryByHome(cityName,query);
            }
            if(StringUtils.isNotNullOrEmpty(companyName)){
                String lastCompany=params.get("in_last_job_search_company");
                if(StringUtils.isNotNullOrEmpty(lastCompany)&&"1".equals(lastCompany)){
                    this.queryByLastCompany(companyName,query);
                }else {
                    this.queryByCompany(companyName, query);
                }
            }
            if(StringUtils.isNotNullOrEmpty(pastPosition)){
                String lastPosition=params.get("in_last_job_search_position");
                if(StringUtils.isNotNullOrEmpty(lastPosition)&&"1".equals(lastPosition)){
                    this.queryByLastPositions(lastPosition,query);
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
        String isFreshGraduate=params.get("is_fresh_graduates");
        if(
            StringUtils.isNotNullOrEmpty(degree)||StringUtils.isNotNullOrEmpty(intentionSalaryCode)||StringUtils.isNotNullOrEmpty(sex)||
            StringUtils.isNotNullOrEmpty(workYears)||StringUtils.isNotNullOrEmpty(updateTime)|| StringUtils.isNotNullOrEmpty(isFreshGraduate)||
            ((StringUtils.isNotNullOrEmpty(minAge)||StringUtils.isNotNullOrEmpty(maxAge))&&(!"0".equals(minAge)||!"0".equals(maxAge)))
           )
        {
            if(StringUtils.isNotNullOrEmpty(degree)){
                this.QueryByDegree(degree,query);
            }
            if(StringUtils.isNotNullOrEmpty(intentionSalaryCode)){
                this.queryBySlalryCode(intentionSalaryCode,query);
            }
            if(StringUtils.isNotNullOrEmpty(sex)){
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
                this.queryByAge(ages,query);
            }
            if(StringUtils.isNotNullOrEmpty(isFreshGraduate)){
                this.queryByIsFreshGraduate(Integer.parseInt(isFreshGraduate),query);
            }
        }

        return query;
    }

    private void queryApplications(Map<String,String> params,QueryBuilder query){

        String publisherIds=params.get("publisher");
        String candidateSource=params.get("candidate_source");
        String recommend=params.get("is_recommend");
        String origins=params.get("origins");
        String submitTime=params.get("submit_time");
        String progressStatus=params.get("progress_status");
        String positionIds=params.get("position_id");
        if(
            StringUtils.isNotNullOrEmpty(publisherIds)||StringUtils.isNotNullOrEmpty(candidateSource)||StringUtils.isNotNullOrEmpty(recommend)||
            StringUtils.isNotNullOrEmpty(origins)||StringUtils.isNotNullOrEmpty(submitTime)||
            StringUtils.isNotNullOrEmpty(progressStatus)||StringUtils.isNotNullOrEmpty(positionIds)
         )
        {
            if(StringUtils.isNotNullOrEmpty(publisherIds)){
                    String companyId=params.get("company_id");
                    this.queryByComapnyId(companyId,query);
            }
            if(StringUtils.isNotNullOrEmpty(candidateSource)){
                this.queryByCandidateSource(Integer.parseInt(candidateSource),query);
            }
            if(StringUtils.isNotNullOrEmpty(recommend)){
                this.queryByRecom(query);
            }
            if(StringUtils.isNotNullOrEmpty(submitTime)){
                this.queryBySubmitTime(submitTime,query);
            }
            if(StringUtils.isNotNullOrEmpty(progressStatus)){
                this.queryByProgress(Integer.parseInt(progressStatus),query);
            }
            if(StringUtils.isNotNullOrEmpty(origins)){
                String companyId=params.get("company_id");
                this.queryByOrigin(origins,companyId,query);
            }
            if(StringUtils.isNotNullOrEmpty(positionIds)){
                this.queryByPositionId(positionIds,query);
            }
        }

    }

    /*
     组装nest的查询语句
     */
    public QueryBuilder queryNest(Map<String,String> params){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        String tagIds=params.get("tag_ids");
        String favoriteHrs=params.get("favorite_hrs");
        String isPublic=params.get("is_public");
        if(
                StringUtils.isNullOrEmpty(tagIds)&&
                StringUtils.isNullOrEmpty(favoriteHrs)&&
                StringUtils.isNullOrEmpty(isPublic)
        )
        {
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
        if( StringUtils.isNullOrEmpty(publisherIds)
            &&StringUtils.isNullOrEmpty(progressStatus)
            &&StringUtils.isNullOrEmpty(candidateSource)
            &&StringUtils.isNullOrEmpty(recommend)
            &&StringUtils.isNullOrEmpty(origins)
            &&StringUtils.isNullOrEmpty(submitTime)
            &&StringUtils.isNullOrEmpty(positionId)
          )
        {
            return null;

        }
        StringBuffer sb=new StringBuffer();
        sb.append("origin=0;upload=_source.user.upload;profiles=_source.user.profiles;if(profiles){profile=profiles.profile;if(profile){origin=profile.origin}};for ( val in _source.user.applications) {");
        if(StringUtils.isNotNullOrEmpty(publisherIds)){
            List<Integer> publisherIdList=this.convertStringToList(publisherIds);
            if(!StringUtils.isEmptyList(publisherIdList)){
                sb.append("if(val.publisher in "+publisherIdList.toString()+"&&");
            }
        }
        if(StringUtils.isNotNullOrEmpty(candidateSource)){
            sb.append("val.candidate_source=="+candidateSource+"&&");
        }
        if(StringUtils.isNotNullOrEmpty(recommend)){
            sb.append("val.recommender_user_id>0 &&");
        }
        if(StringUtils.isNotNullOrEmpty(origins)){
            List<String> list=searchUtil.stringConvertList(origins);
            sb.append("(");
            for(String origin:list){
                if("1".equals(origin)){
                    sb.append("upload==1 ||");
                }else{
                    if(origin.length()>8){
                        sb.append(" origin=="+origin+"||");
                    }else{
                        sb.append(" val.origin=="+origin+"||");
                    }
                }
            }
            sb.deleteCharAt(sb.lastIndexOf("|"));
            sb.deleteCharAt(sb.lastIndexOf("|"));
            sb.append(")&&");

        }

        if(StringUtils.isNotNullOrEmpty(submitTime)){
            Date date=new Date();
            long datetime=date.getTime();
            long preTime=Long.parseLong(submitTime)*3600*24;
            long time=datetime-preTime;
            sb.append(" val.submit_time>"+time+"&&");
        }
        if(StringUtils.isNotNullOrEmpty(progressStatus)){
            sb.append(" val.progress_status=="+progressStatus+"&&");
        }
        if(StringUtils.isNotNullOrEmpty(positionId)){
            List<Integer> positionIdList=this.convertStringToList(positionId);
            sb.append(" val.position_id in "+positionIdList.toString()+"&&");
        }
        sb=sb.deleteCharAt(sb.lastIndexOf("&"));
        sb=sb.deleteCharAt(sb.lastIndexOf("&"));
        sb.append("){return true}}");
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
        Map<String,Object> result=searchUtil.handleData(response,"agg");
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
     根据简历的更新时间查询
     */
    private void queryByProfileUpDateTime(String updateTime,QueryBuilder queryBuilder){
        Date date=new Date();
        long datetime=date.getTime();
        long preTime=Long.parseLong(updateTime)*3600*24;
        long time=datetime-preTime;
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
     构建和公司相关的人才库
     */
    private void queryByNestCompanyId(int companyId,QueryBuilder queryBuilder){
        searchUtil.handleMatch(companyId,queryBuilder,"user.talent_pool.company_id");
    }
    /*
      构建按照期望城市名称的查询语句
     */
    private void queryByIntentionCity(String cityNames,QueryBuilder queryBuilder){
        searchUtil.handleTerms(cityNames,queryBuilder,"user.profiles.intentions.cities.city_name");
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
    /*
      按照最后工作的公司查询
     */
    private void queryByLastCompany(String companys,QueryBuilder queryBuilder){
        searchUtil.handleTerms(companys,queryBuilder,"user.profiles.recent_job.company_name");
    }
    /*
      按照最后工作的职位名称查询
     */
    private void queryByLastPositions(String positions,QueryBuilder queryBuilder){
        searchUtil.handleTerms(positions,queryBuilder,"user.profiles.recent_job.job");
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
        searchUtil.shoudRangeAgeOrDegreeListFilter(ages,queryBuilder,"user.age");
    }
    /*
     将字符串转换成SON
     */
    private List<Map<String,Integer>> convertParams(String params){
        List<Map<String,Integer>> list= (List<Map<String, Integer>>) JSON.toJSON(params);
        return list;
    }
    /*
      按照曾任职务查询
     */
    private void queryByWorkJob(String works,QueryBuilder queryBuilder){
        Map<String,Object> queryMap=new HashMap<>();
        queryMap.put("user.profiles.recent_job.job",works);
        queryMap.put("user.profiles.workexps.job",works);
        searchUtil.shouldTermsQuery(queryMap,queryBuilder);
    }
    /*
     构建通过曾经工作的公司查询
     */
    private void queryByCompany(String companys,QueryBuilder queryBuilder){
        Map<String,Object> queryMap=new HashMap<>();
        queryMap.put("user.profiles.recent_job.company_name",companys);
        queryMap.put("user.profiles.workexps.company_name",companys);
        searchUtil.shouldTermsQuery(queryMap,queryBuilder);
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
        Date date=new Date();
        long datetime=date.getTime();
        long preTime=Long.parseLong(submitTime)*3600*24;
        long time=datetime-preTime;
        searchUtil.hanleRangeFilter(time,queryBuilder,"user.applications.submit_time");
    }
    /*
      按照工作年限查新
     */
    private void queryByWorkYear(String workYears,QueryBuilder queryBuilder){
        List<Map<String,Integer>> list=this.convertParams(workYears);
        searchUtil.shoudRangeAgeOrDegreeListFilter(list,queryBuilder,"user.work_year");
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
     根据是否是刚毕业查询
     */
    private void queryByIsFreshGraduate(int isFreshGraduate,QueryBuilder queryBuilder){
        searchUtil.handleMatchFilter(isFreshGraduate,queryBuilder,"user.is_fresh_graduates");
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
    /*
       简历被查看的统计
     */
    private AbstractAggregationBuilder handleIsViewedCountAgg(Map<String,String> params){
        MetricsAggregationBuilder build= AggregationBuilders.scriptedMetric("is_viewed_count")
                .initScript(new Script(getAggInitScript()))
                .mapScript(new Script(this.getAggMapScript(params,"4",0)))
                .reduceScript(new Script(this.getAggReduceScript()))
                .combineScript(new Script(this.getAggCombineScript()));
        return build;
    }
    /*
        初试通过的统计
     */
    private AbstractAggregationBuilder handleFirstTrialOkCountAgg(Map<String,String> params){
        MetricsAggregationBuilder build= AggregationBuilders.scriptedMetric("first_trial_ok_count")
                .initScript(new Script(getAggInitScript()))
                .mapScript(new Script(this.getAggMapScript(params,"7",0)))
                .reduceScript(new Script(this.getAggReduceScript()))
                .combineScript(new Script(this.getAggCombineScript()));
        return build;
    }
    /*
        入职的统计
     */
    private AbstractAggregationBuilder handleEntryCountAgg(Map<String,String> params){
        MetricsAggregationBuilder build= AggregationBuilders.scriptedMetric("entry_count")
                .initScript(new Script(getAggInitScript()))
                .mapScript(new Script(this.getAggMapScript(params,"12",0)))
                .reduceScript(new Script(this.getAggReduceScript()))
                .combineScript(new Script(this.getAggCombineScript()));
        return build;
    }
    /*
        面试通过的统计
     */
    private AbstractAggregationBuilder handleInterviewOkCountAgg(Map<String,String> params){
        MetricsAggregationBuilder build= AggregationBuilders.scriptedMetric("interview_ok_count")
                .initScript(new Script(getAggInitScript()))
                .mapScript(new Script(this.getAggMapScript(params,"10",0)))
                .reduceScript(new Script(this.getAggReduceScript()))
                .combineScript(new Script(this.getAggCombineScript()));
        return build;
    }
    /*
        所有申请的统计
     */
    private AbstractAggregationBuilder handleAllApplicationCountAgg(Map<String,String> params){
        MetricsAggregationBuilder build= AggregationBuilders.scriptedMetric("all_application_count")
                .initScript(new Script(getAggInitScript()))
                .mapScript(new Script(this.getAggMapScript(params,null,1)))
                .reduceScript(new Script(this.getAggReduceScript()))
                .combineScript(new Script(this.getAggCombineScript()));
        return build;
    }
    /*
      未查看简历数量的统计
     */
    private AbstractAggregationBuilder handleNotViewedCountAgg(Map<String,String> params){
        MetricsAggregationBuilder build= AggregationBuilders.scriptedMetric("not_viewed_count")
                .initScript(new Script(getAggInitScript()))
                .mapScript(new Script(this.getAggMapScript(params,"3",0)))
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
        List<Integer> publisherIdList=this.convertStringToList(publishIds);
        StringBuffer sb=new StringBuffer();
        sb.append("int i = 0; for ( val in _source.user.applications)");
        sb.append("{if((val.publisher in "+publisherIdList.toString()+") &&");
        if(StringUtils.isNotNullOrEmpty(progressStatus)){
            sb.append("val.progress_status=="+progressStatus+"&&");
        }
        if(StringUtils.isNotNullOrEmpty(submitTime)){
            Date date=new Date();
            long datetime=date.getTime();
            long preTime=Long.parseLong(submitTime)*3600*24;
            long time=datetime-preTime;
            sb.append("val.submit_time>"+time+"&&");
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
    private Boolean isExecAgg(String returnParams){
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
}


