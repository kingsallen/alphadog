package com.moseeker.searchengine.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.util.StringUtils;
import com.moseeker.searchengine.util.SearchUtil;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zztaiwll on 17/12/8.
 */
@Service
public class TalentpoolSearchengine {

    @Autowired
    private SearchUtil searchUtil;

    public Map<String,Object>  talentSearch(Map<String,String> params){
        TransportClient client= searchUtil.getEsClient();
        return null;
    }
    /*
     组装查询条件
     */
    private QueryBuilder queryCommon(Map<String,String> params){

        String city_name=params.get("city_name");
        String company_name=params.get("company_name");
        String degree=params.get("degree");
        String lastCompany=params.get("in_last_job_company");
        String lastPosition=params.get("in_last_job_position");
        String intentionCity=params.get("intention_city_name");
        String intentionSalaryCode=params.get("intention_salary_code");
        String keyword=params.get("keyword");
        String pastPosition=params.get("past_position");
        String sex=params.get("sex");
        String submitTime=params.get("submit_time");
        String updateTime=params.get("update_time");
        String workYears=params.get("work_years");
        String progressStatus=params.get("progress_status");
        String pageNum=params.get("page_number");
        String pageSize=params.get("page_size");


        return null;
    }

    private QueryBuilder queryApplication(Map<String,String> params){
        String publisherIds=params.get("publisher_ids");
        String candidateSource=params.get("candidate_source");
        String recommend=params.get("only_recommend");
        String origins=params.get("origins");
        String isPublic=params.get("public");
        if(StringUtils.isNotNullOrEmpty(publisherIds)){

        }
        return null;
    }

    /*
     组装nest的查询语句
     */
    public QueryBuilder queryNest(Map<String,String> params){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        String tagIds=params.get("tag_ids");
        String favoriteHrs=params.get("favorite_hrs");
        if(StringUtils.isNotNullOrEmpty(tagIds)){
            this.queryByTagId(tagIds,query);
        }
        if(StringUtils.isNotNullOrEmpty(favoriteHrs)){
            this.queryHrTagId(favoriteHrs,query);
        }
        query=QueryBuilders.nestedQuery("user.talent_pool",query);
        return query;
    }

    /*
     创建按照hr查询的索引语句
     */
    private void queryByPublisher(String publisherIds,QueryBuilder queryBuilder){
        searchUtil.handleTerms(publisherIds,queryBuilder,"user.applications.publisher");
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
        searchUtil.handleMatch(source,queryBuilder,"user.applications.candidate_source");
    }
    /*
      构建是否内推的查询语句
     */
    private void queryByRecom(int source,QueryBuilder queryBuilder){
        searchUtil.hanleRange(0,queryBuilder,"user.applications.recommender_user_id");
    }
    /*
      构建简历来源的查询语句
     */
    private void queryByOrigin(String condition1,String condition2,QueryBuilder queryBuilder){

    }
    /*
      构建通过职位来查询的语句
     */
    private void queryByPosition(String positionIds,QueryBuilder queryBuilder ){
        searchUtil.handleTerms(positionIds,queryBuilder,"user.applications.position_id");
    }
    /*
      构建是否公开的查询语句,注意这个位置要做成nest的查询
     */
    private void queryByPublic(int isPublic,QueryBuilder queryBuilder){
        searchUtil.handleMatch(isPublic,queryBuilder,"user.talent_pool.is_public");
    }
    /*
      构建收藏人的查询语句
     */
    private void queryByCollectid(String hrIds,QueryBuilder queryBuilder){
        searchUtil.handleTerms(hrIds,queryBuilder,"user.talent_pool.hr_id");
    }
    /*
      构建按招标签的查询语句
     */
    private void queryByTagId(String tagIds,QueryBuilder queryBuilder){
        searchUtil.handleTerms(tagIds,queryBuilder,"user.talent_pool.tag.tag_id");
    }
    /*
      构建按照期望城市名称的查询语句
     */
    private void queryByIntentionCity(String cityNames,QueryBuilder queryBuilder){
        searchUtil.handleTerms(cityNames,queryBuilder,"user.profiles.intentions.city");
    }
    /*
      按照公司名称查询
     */
    private void queryByIntentionSalaryCode(String salaryCodes,QueryBuilder queryBuilder){
        searchUtil.handleTerms(salaryCodes,queryBuilder,"user.profiles.intentions.city");
    }
    /*
      按照学历查询
     */
    private void QueryByDegree(String degrees,QueryBuilder queryBuilder){
        searchUtil.handleTerms(degrees,queryBuilder,"user.profiles.basic.gender");
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
        searchUtil.handleTerm(home,queryBuilder,"user.profiles.basic.city_name");
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
    private void queryByAge(String ages,QueryBuilder queryBuilder){
        List<Map<String,Integer>> list=this.convertParams(ages);
        searchUtil.shoudRangeAgeOrDegreeList(list,queryBuilder,"user.age");
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
      按照性别查询
     */
    private void queryByGender(String gender,QueryBuilder queryBuilder){
        searchUtil.handleTerm(gender,queryBuilder,"user.profiles.basic.gender");
    }
    /*
      按照投递时间查询
     */
    private void queryBySubmitTime(String submitTime,QueryBuilder queryBuilder){
        Date date=new Date(submitTime);
        Long datetime=date.getTime();
        searchUtil.hanleRange(datetime,queryBuilder,"user.profiles.basic.gender");
    }
    /*
      按照工作年限查新
     */
    private void queryByWorkYear(String workYears,QueryBuilder queryBuilder){
        List<Map<String,Integer>> list=this.convertParams(workYears);
        searchUtil.shoudRangeAgeOrDegreeList(list,queryBuilder,"user.workyear");
    }
    /*
      按照招聘进度查询
     */
    private void queryByProgress(int progress,QueryBuilder queryBuilder){
        searchUtil.handleMatch(progress,queryBuilder,"user.applications.progress");
    }
    /*
     根据hr的标签查询
     */
    private void queryHrTagId(String hrIds,QueryBuilder queryBuilder){
        searchUtil.handleTerms(hrIds,queryBuilder,"user.talent_pool.hr_id");
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
