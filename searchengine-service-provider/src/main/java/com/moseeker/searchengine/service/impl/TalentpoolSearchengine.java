package com.moseeker.searchengine.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolProfilePoolJooqDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfilePool;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.searchengine.domain.KeywordSearchParams;
import com.moseeker.searchengine.domain.PastPOJO;
import com.moseeker.searchengine.domain.SearchPast;
import com.moseeker.searchengine.service.impl.keywordSearch.category.SecondCateGory;
import com.moseeker.searchengine.service.impl.keywordSearch.searchkeyword.FullTextSearchBuilder;
import com.moseeker.searchengine.service.impl.keywordSearch.searchkeyword.KeywordSearchFactory;
import com.moseeker.searchengine.util.SearTypeEnum;
import com.moseeker.searchengine.util.SearchMethodUtil;
import com.moseeker.searchengine.util.SearchUtil;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.MetricsAggregationBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private DictCityDao dictCityDao;
    @Resource(name="cacheClient")
    private RedisClient redis;
    @Autowired
    private TalentpoolProfilePoolJooqDao talentpoolProfilePoolJooqDao;

    @CounterIface
    public Map<String, Object> talentSearch(Map<String, String> params){
        /*
         当signal=0时的查询逻辑
            1，查找关键词城市，若命中只返回城市的查询，停止后边的操作，组装所有的查询语句
            2，查找工作经历中公司的关键字，若命中，停止后边的操作，返回公司的查询语句
            3，查找曾任职位的关键字，若命中，停止后边的操作，返回曾任职位的查询语句
            4，查找姓名的关键字，若命中，停止后边的操作，返回姓名的查询语句
            5，若不行执行全文查找
         当signal=1时的查询逻辑
            1，查询城市，工作经历中公司，曾任职位，姓名中任意一个
         当signal=2时的查询逻辑
            1，全文查找
         */
        params.put("signal","1");
        Map<String, Object> result1=talentSearchNew(params);
        if(result1==null||Integer.parseInt(String.valueOf(result1.get("totalNum")))==0){
            params.put("signal","2");
            Map<String, Object> result2=talentSearchNew(params);
//            if(result2==null||(int)result2.get("total")==0){
//                params.put("signal","2");
//                Map<String, Object> result3=talentSearchNew(params);
//                return result3;
//            }
            return result2;
        }
        return result1;
    }

    public Map<String, Object> talentSearchNew(Map<String, String> params) {
        logger.info("===================+++++++++++++++++++++++++++++++++++");
        logger.info(JSON.toJSONString(params));
        logger.info("===================+++++++++++++++++++++++++++++++++++");
        Map<String, Object> result=new HashMap<>();
        TransportClient client=null;
        try {
            client = searchUtil.getEsClient();
            QueryBuilder query = this.query(params,client);
            SearchRequestBuilder builder = client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_TYPE).setQuery(query);
            this.handlerSortOrder(params, builder);
            this.handlerPage(params, builder);
            this.handlerReturn(params, builder);
            builder.setTrackScores(true);
            logger.info(builder.toString());
            SearchResponse response = builder.execute().actionGet();
            result = searchUtil.handleData(response, "users");
            this.handlerResultData(result,params);
            return result;
        } catch (Exception e) {
            logger.info(e.getMessage()+"=================",e);
            if (e.getMessage().contains("all shards")) {
                return result;
            }
        }
        return result;
    }

    private void handlerResultData( Map<String, Object> result,Map<String, String> params){
        if(!StringUtils.isEmptyMap(result)){
            String isOut=params.get("is_out");
            int totalNum=(int)((long)result.get("totalNum"));
            if(totalNum>0){
                List<Map<String,Object>> list= (List<Map<String, Object>>) result.get("users");
                if(!StringUtils.isEmptyList(list)){
                    String companyId=params.get("company_id");
                    List<TalentpoolProfilePool> talentpoolProfilePools = talentpoolProfilePoolJooqDao.fetchByCompanyId(new Integer[]{Integer.valueOf(companyId)});
                    Map<Integer,String> profilePoolMap=this.processTreeProfilePool(talentpoolProfilePools);
                    for(Map<String,Object> map:list){
                        Map<String,Object> user= (Map<String, Object>) map.get("user");
                        if(!StringUtils.isEmptyMap(user)){
                            //处理简历池
                            List<Map<String,Object>> talentPoolList=(List<Map<String,Object>>)user.get("talent_pool");
                            if(!CollectionUtils.isEmpty(talentPoolList)){
                                for (Map<String, Object> talentpool : talentPoolList) {
                                    if(companyId.equals(talentpool.get("company_id").toString())){
                                        Object profilePoolId = talentpool.get("profile_pool_id");
                                        if(profilePoolId!=null){
                                            Integer poolId=Integer.parseInt(profilePoolId.toString());
                                            if(poolId>0){
                                                user.put("profile_pool_id",poolId);
                                                user.put("profile_pool_name",profilePoolMap.get(poolId));
                                            }
                                        }
                                    }
                                }
                            }
                            List<Map<String,Object>> commentList= (List<Map<String, Object>>) user.get("talentpool_comment");
                            if(!StringUtils.isEmptyList(commentList)){
                                if(StringUtils.isNotNullOrEmpty(companyId)){
                                    for(Map<String,Object> comment:commentList){
                                        int id= (int) comment.get("company_id");
                                        if(id==Integer.parseInt(companyId)){
                                            int count=(int)((int)comment.get("count"));
                                            user.put("remark_count",count);
                                            break;
                                        }
                                    }
                                }
                            }
                            /*
                             过滤掉不是查询职位 的申请
                             */
                            if(StringUtils.isNotNullOrEmpty(isOut)&&"1".equals(isOut)){
                                String positionWord=params.get("position_key_word");
                                String positionIds=params.get("position_id");
                                if(StringUtils.isNotNullOrEmpty(positionWord)||StringUtils.isNotNullOrEmpty(positionIds)){
                                    List<Integer> positionIdList=searchUtil.stringConvertIntList(positionIds);
                                    if(!StringUtils.isEmptyList(positionIdList)){
                                        List<Map<String,Object>> applications=(List<Map<String, Object>>) user.get("applications");
                                        if(!StringUtils.isEmptyList(applications)){
                                            List<Map<String,Object>> applicationNewList=new ArrayList<>();
                                            for(Map<String,Object> application:applications){
                                                if(application.get("position_id")!=null){
                                                    int positionId= (int) application.get("position_id");
                                                    if(positionIdList.contains(positionId)){
                                                        applicationNewList.add(application);
                                                    }
                                                }
                                            }
                                            if(!StringUtils.isEmptyList(applicationNewList)){
                                                user.put("applications",applicationNewList);
                                            }
                                        }

                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 将简历池处理成树状结构
     * @param talentpoolProfilePools
     * @return
     */
    private Map<Integer, String> processTreeProfilePool(List<TalentpoolProfilePool> talentpoolProfilePools) {
        Map<Integer,String> result=new HashMap<>();
        if(CollectionUtils.isEmpty(talentpoolProfilePools)){
            return result;
        }
        //得到一级简历池
        List<TalentpoolProfilePool> oneLevel=talentpoolProfilePools.stream().filter(t->t.getParentId()==0).collect(Collectors.toList());
        oneLevel.stream().forEach(t->result.put(t.getId(),t.getProfilePoolName()));
        List<Integer> oneLevelIdList=oneLevel.stream().map(TalentpoolProfilePool::getId).collect(Collectors.toList());
        //得到二级简历池
        List<TalentpoolProfilePool> twoLevel=talentpoolProfilePools.stream().filter(t->oneLevelIdList.contains(t.getParentId())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(twoLevel)){
            return result;
        }
        for (TalentpoolProfilePool talentpoolProfilePool : twoLevel) {
            for (TalentpoolProfilePool profilePool : oneLevel) {
                if(profilePool.getId().equals(talentpoolProfilePool.getParentId())){
                    result.put(talentpoolProfilePool.getId(),profilePool.getProfilePoolName()+"-"+talentpoolProfilePool.getProfilePoolName());
                    continue;
                }
            }
        }
        //得到三级简历池
        talentpoolProfilePools.removeAll(oneLevel);
        talentpoolProfilePools.removeAll(twoLevel);
        if(CollectionUtils.isEmpty(talentpoolProfilePools)){
            return result;
        }
        for (TalentpoolProfilePool talentpoolProfilePool : talentpoolProfilePools) {
            for (TalentpoolProfilePool profilePool : twoLevel) {
                if(profilePool.getId().equals(talentpoolProfilePool.getParentId())){
                    result.put(talentpoolProfilePool.getId(),result.get(profilePool.getId())+"-"+talentpoolProfilePool.getProfilePoolName());
                    continue;
                }
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
            QueryBuilder query = this.query(params,client);
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
            builder.setSize(userIds.size());
            logger.info(builder.toString());
            SearchResponse response = builder.execute().actionGet();
            result = searchUtil.handleData(response, "users");
            return result;
        } catch (Exception e) {
            logger.info(e.getMessage()+"=================",e);
            if (e.getMessage().contains("all shards")) {
                return result;
            }
        }
        return result;
    }
    /*
     查询企业标签的人才数量
     */
    @CounterIface
    public int talentSearchNum(Map<String, String> params) {
        Map<String, Object> result=new HashMap<>();
        TransportClient client=null;
        try {
            client = searchUtil.getEsClient();
            QueryBuilder query = this.query(params,client);
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
            QueryBuilder query = this.query(params,client);
            SearchRequestBuilder builder = client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_TYPE).setQuery(query);
            builder.addAggregation(this.handleAllApplicationCountAgg(params))        //当前状态下的申请数量数量
//                   .addAggregation(this.handleAllcountAgg(params))
                    .addAggregation(this.handleAggInfo(params,"all_count_app","",1))//所有申请的数量
                    .addAggregation(this.handleAggInfo(params,"all_count","",0))//所有申请的人数
                    .addAggregation(this.handleAggInfo(params,"entry_count",12+"",0))//入职的人数
                    .addAggregation(this.handleAggInfo(params,"entry_count_app",12+"",1))//入职的申请书
                    .addAggregation(this.handleAggInfo(params,"interview_ok_count",10+"",0))//面试通过的人数
                    .addAggregation(this.handleAggInfo(params,"interview_ok_count_app",10+"",1))//面试通过的申请数
                    .addAggregation(this.handleAggInfo(params,"first_trial_ok_count",7+"",0))//初审通过的人数
                    .addAggregation(this.handleAggInfo(params,"first_trial_ok_count_app",7+"",1))//初审通过的申请数
                    .addAggregation(this.handleAggInfo(params,"is_viewed_count",4+"",0))
                    .addAggregation(this.handleAggInfo(params,"is_viewed_count_app",4+"",1))
                    .addAggregation(this.handleAggInfo(params,"is_not_suitable",13+"",0))
                    .addAggregation(this.handleAggInfo(params,"is_not_suitable_app",13+"",1))
                    .addAggregation(this.handleAggInfo(params,"not_viewed_count",3+"",0))
                    .addAggregation(this.handleAggInfo(params,"not_viewed_count_app",3+"",1));
            builder.setSize(0);
            logger.info(builder.toString());
            SearchResponse response = builder.execute().actionGet();
            result = searchUtil.handleAggData(response);
            return result;
        } catch (Exception e) {
            logger.info(e.getMessage()+"=================",e);
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
            if(result!=null&&!result.isEmpty()){
                long totalNum=(long)result.get("totalNum");
                if(totalNum>0){
                    this.handlerResult(result,list);
                }
            }
        }catch(Exception e){
            logger.info(e.getMessage()+"=================",e);
        }
        return list;
    }

    /*
     获取tag_id的标签的条数
     */
    @CounterIface
    public int getUserListByCompanyTagCount(Map<String,String> params){
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
            logger.info(e.getMessage()+"=================",e);
        }
        return 0;
    }

    private SearchResponse handlerSearch(Map<String,String> params) throws TException {
        TransportClient client=searchUtil.getEsClient();
        QueryBuilder query = this.getQueryByTag(params);
        SearchRequestBuilder builder = client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_TYPE).setQuery(query);
        String[] returnParams={"user.profiles.profile.user_id"};
        builder.setFetchSource(returnParams,null);
        builder.addSort("user.profiles.profile.user_id",SortOrder.DESC);
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
        String isTalent=searchPast.getIsTalent();
        if(StringUtils.isNotNullOrEmpty(publisher)){
            searchUtil.handleTerms(publisher,query,"user.applications.publisher");
        }
        if(StringUtils.isNotNullOrEmpty(companyId)){
            searchUtil.handleTerms(companyId,query,"user.applications.company_id");
        }
        if(StringUtils.isNotNullOrEmpty(isTalent)){
            String tagIds="talent,allpublic";
            QueryBuilder queryNest=this.handlerPastNest(tagIds,companyId,publisher);
            if(queryNest!=null){
                ((BoolQueryBuilder) query).filter(queryNest);
            }
        }
    }

    private QueryBuilder handlerPastNest(String tagIds,String companyId,String hrId){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        this.queryByNestCompanyId(Integer.parseInt(companyId),query);
        if(StringUtils.isNotNullOrEmpty(tagIds)){
            this.queryByTagId(tagIds,hrId,query);
        }
        query=QueryBuilders.nestedQuery("user.talent_pool",query);
        return query;
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
            if(map!=null&&!map.isEmpty()){
                Map<String,Object> userMap=(Map<String,Object>)map.get("user");
                if(userMap!=null&&!userMap.isEmpty()){
                    Map<String,Object> profiles=(Map<String,Object>)userMap.get("profiles");
                    logger.info(JSON.toJSONString(profiles));
                    if(profiles!=null&&!profiles.isEmpty()){
                        Map<String,Object> profile=(Map<String,Object>)profiles.get("profile");
                        logger.info(JSON.toJSONString(profile));
                        if(profile!=null&&!profile.isEmpty()){
                            int userId=Integer.parseInt(String.valueOf(profile.get("user_id")));
                            userIdList.add(userId);
                        }
                    }
                }
            }
        }
    }

    private QueryBuilder convertBuild(List<Map<String,String>> mapList) throws TException {
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
    private QueryBuilder getQueryByTag(Map<String,String> params) throws TException {
        this.handlerProvinceCity(params);
        String companyId=params.get("company_id");
        String hrId=params.get("hr_id");
        String origins=params.get("origins");
        String workYears=params.get("work_years");
        String submitTime=params.get("submit_time");
        String cityCode=params.get("city_code");
        String degree=params.get("degree");
        String pastPosition=params.get("past_position");
        String minAge=params.get("min_age");
        String maxAge=params.get("max_age");
        String intentionCityCode=params.get("intention_city_code");
        String intentionSalaryCode=params.get("intention_salary_code");
        String sex=params.get("sex");
        String isRecommend=params.get("is_recommend");
        String companyName=params.get("company_name");
        String exists=params.get("exists");
        String keywords = params.get("keywords");
        String containAnykey = params.get("contain_any_key");
        String userId = params.get("user_id");
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
        if(StringUtils.isNotNullOrEmpty(userId)){
            searchUtil.handleTerm(userId, query, "user.profiles.profile.user_id");
        }
        if(StringUtils.isNotNullOrEmpty(cityCode)){
            if(!cityCode.contains("111111")){
                cityCode=cityCode+",111111";
            }
            this.queryByHome(cityCode,query);
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
        if(StringUtils.isNotNullOrEmpty(intentionCityCode)){
            if(!intentionCityCode.contains("111111")){
                intentionCityCode=intentionCityCode+",111111";
            }
            this.queryByIntentionCityTag(intentionCityCode,query);
        }
        if(StringUtils.isNotNullOrEmpty(intentionSalaryCode)){
            this.queryBySlalryCode(intentionSalaryCode,query);
        }
        if(StringUtils.isNotNullOrEmpty(sex)&&Integer.parseInt(sex)!=0){
            this.queryByGender(sex,query);
        }
        if(StringUtils.isNotNullOrEmpty(isRecommend)&&Integer.parseInt(isRecommend)>0){
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
        logger.info("keywords:========"+ keywords);
        if(StringUtils.isNotNullOrEmpty(keywords)){
            String[] keyword_list = keywords.split(";");
            QueryBuilder keyand = QueryBuilders.boolQuery();
            for (int i = 0; i < keyword_list.length; i++) {
                String keyword = keyword_list[i];
                if(org.apache.commons.lang.StringUtils.isBlank(keyword)){
                    continue;
                }
                MultiMatchQueryBuilder fullf = QueryBuilders.multiMatchQuery(keyword);
                fullf.type(MultiMatchQueryBuilder.Type.PHRASE);
                fullf.slop(0);
                List<String> colums = StringUtils.stringToList(Constant.PROFILE_SEARCH_KEYWORD_COLUMS,";");
                if(!StringUtils.isEmptyList(colums)){
                    for(String colum :colums){
                        if(StringUtils.isNotNullOrEmpty(colum)) {
                            fullf.field("user.profiles." + colum);
                        }
                    }
                }

                if(StringUtils.isNotNullOrEmpty(containAnykey) && Integer.parseInt(containAnykey) == 1){
                    ((BoolQueryBuilder) keyand).should(fullf);
                }else{
                    ((BoolQueryBuilder) keyand).must(fullf);
                }
            }
            ((BoolQueryBuilder) query).must(keyand);
        }
        if(StringUtils.isNotNullOrEmpty(origins)||StringUtils.isNotNullOrEmpty(submitTime)||(StringUtils.isNotNullOrEmpty(isRecommend)&&Integer.parseInt(isRecommend)>0)){
            //这里是处理groovy语法的位置
            StringBuffer sb=new StringBuffer();
            sb.append("user=_source.user;if(user){applications=user.applications;;origins=user.origin_data;if(applications){for(val in applications){if(");
            if(StringUtils.isNotNullOrEmpty(companyId)){
                sb.append("val.company_id=="+companyId+"&&");
            }
//            if(StringUtils.isNotNullOrEmpty(hrId)){
//                sb.append("val.publisher=="+hrId+"&&");
//            }
            if(StringUtils.isNotNullOrEmpty(submitTime)){
                String longTime=this.getLongTime(submitTime);
                sb.append(" val.submit_time>'"+longTime+"'&&");
            }
            if(StringUtils.isNotNullOrEmpty(isRecommend) && Integer.parseInt(isRecommend)>0){
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
        String companyId=params.get("company_id");
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        if(StringUtils.isNotNullOrEmpty(companyId)){
            this.queryByNestCompanyId(Integer.parseInt(companyId),query);
        }
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
    public QueryBuilder query(Map<String,String> params,TransportClient client) throws TException {
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        this.handlerPositionId(params);
        this.handlerProvinceCity(params);
        this.queryCommons(params,query,client);
        this.queryProfiles(params,query);
        this.queryApplications(params,query);
        QueryBuilder queryAppScript=this.queryScript(params);
        if(queryAppScript!=null){
            ((BoolQueryBuilder) query).filter(queryAppScript);
        }
        QueryBuilder gdprScript=this.convertGdprScript(params);
        if(gdprScript!=null){
            ((BoolQueryBuilder) query).filter(gdprScript);
        }
        QueryBuilder queryNest=this.queryNest(params);
        if(queryNest!=null){
            ((BoolQueryBuilder) query).filter(queryNest);
        }
        this.queryTalentComment(params,query);
        return query;
    }

    private void handlerProvinceCity(Map<String,String> params) throws TException {
        String cityCode=params.get("city_code");
        String intentionCityCode=params.get("intention_city_code");
        if(StringUtils.isNotNullOrEmpty(cityCode)){
            String cityNewCode=dictCityDao.handlerProvinceCity(cityCode);
            if(StringUtils.isNotNullOrEmpty(cityNewCode)){
                params.put("city_code",cityNewCode);
            }
        }
        logger.info("================原有城市数据为==========================");
        logger.info(cityCode);
        logger.info("==============处理后的城市数据为=========================");
        logger.info(params.get("city_code"));
        logger.info("=======================================================");
        if(StringUtils.isNotNullOrEmpty(intentionCityCode)){
            String intentionNewCityCode=dictCityDao.handlerProvinceCity(intentionCityCode);
            if(StringUtils.isNotNullOrEmpty(intentionNewCityCode)){
                params.put("intention_city_code",intentionNewCityCode);
            }
        }
        logger.info("================原有期望城市数据为==========================");
        logger.info(intentionCityCode);
        logger.info("==============处理后的期望城市数据为=======");
        logger.info(params.get("intention_city_code"));
        logger.info("======================================");
    }



    /*
     处理备注的情况
     */
    private void queryTalentComment(Map<String,String> params, QueryBuilder query){
        if(!StringUtils.isEmptyMap(params)){
            String remark=params.get("remark");
            String companyId=params.get("company_id");
            //处理在这家公司下有备注的情况
            if(StringUtils.isNotNullOrEmpty(remark)&&"1".equals(remark)){
                searchUtil.handleTerm(companyId,query,"user.talentpool_comment.company_id");
            }else if(StringUtils.isNotNullOrEmpty(remark)&&"0".equals(remark)){
                this.handlerNoComment(query,companyId);
            }
        }
    }
    /*
     处理在这家公司下无备注的条件查询
     */
    private void handlerNoComment(QueryBuilder query,String companyId){
        QueryBuilder filter1 = QueryBuilders.matchQuery("user.talentpool_comment.company_id",companyId);
        ((BoolQueryBuilder) query).mustNot(filter1);
    }
    private void handlerSortOrder(Map<String,String> params,SearchRequestBuilder builder){
        String publisherIds=params.get("publisher");
        List<Integer> publisherIdList=convertStringToList(publisherIds);
        String hrId=params.get("hr_account_id");
        String companyId=params.get("company_id");
        if(!this.isUseFieldorder(params)){
            builder.addSort("_score", SortOrder.DESC);
            if(this.isOrderTalent(params)){
                this.orderByTalent(publisherIdList,hrId,companyId,builder);
            }else {
                if (publisherIdList.size() > 1) {
                    String sortName="user.hr_all_" + hrId + "_last_submit_time";
                    if(this.getIsExistField(sortName)) {
                        builder.addSort(sortName, SortOrder.DESC);
                    }
                } else {
                    if (this.isMianHr(Integer.parseInt(hrId))) {
                        String sortName="user.hr_" + publisherIdList.get(0) + "_last_submit_time";
                        if(this.getIsExistField(sortName)) {
                            builder.addSort(sortName, SortOrder.DESC);
                        }
                    } else {
                        String sortName="user.hr_" + hrId + "_last_submit_time";
                        if(this.getIsExistField(sortName)) {
                            builder.addSort(sortName, SortOrder.DESC);
                        }
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
            String sortName="user.field_talent_order.hr_all_"+hrId+"_order";
            if(this.getIsExistField(sortName)){
                builder.addSort(sortName, SortOrder.DESC);
            }

        }else{
            if(this.isMianHr(Integer.parseInt(hrId))){
                logger.info("==============================");
                String sortName="user.field_talent_order.hr_all_"+hrId+"_order";
                if(this.getIsExistField(sortName)){
                    builder.addSort(sortName, SortOrder.DESC);
                }

            }else{
                UserHrAccountRecord record=this.getMainAccount(Integer.parseInt(companyId));
                logger.info("++++++++++++++++++++++++++++++++");
                String sortName="user.field_talent_order.hr_all_"+record.getId() +"_order";
                if(this.getIsExistField(sortName)){
                    builder.addSort(sortName, SortOrder.DESC);
                }
//                builder.addSort("user.field_talent_order.hr_" + hrId + "_order", SortOrder.DESC);
            }
        }
    }
    /*
     根据处理好的申请时间排序
     */
    private void orderByApp(List<Integer> publisherIdList,String hrId,String companyId,SearchRequestBuilder builder){

        if (publisherIdList.size() > 1) {
            String sortName="user.field_order.hr_all_"+hrId+"_order";
            if(this.getIsExistField(sortName)){
                builder.addSort(sortName, SortOrder.DESC);
            }
        }else{
            if(this.isMianHr(Integer.parseInt(hrId))){
                logger.info("==============================");
                String sortName="user.field_order.hr_all_"+hrId+"_order";
                if(this.getIsExistField(sortName)){
                    builder.addSort(sortName, SortOrder.DESC);
                }
            }else{
//                UserHrAccountRecord record=this.getMainAccount(Integer.parseInt(companyId));
                logger.info("++++++++++++++++++++++++++++++++");
//                builder.addSort("user.field_order.hr_all_" + record.getId() + "_order", SortOrder.DESC);
                String sortName="user.field_order.hr_"+hrId+"_order";
                if(this.getIsExistField(sortName)){
                    builder.addSort(sortName, SortOrder.DESC);
                }
            }
        }
    }
    /*
     按照收藏时间排序
     */
    private boolean isOrderTalent(Map<String,String>params){
        String profilePoolId=params.get("profile_pool_id");
        String favoriteHrs=params.get("favorite_hrs");
        String isPublic=params.get("is_public");
        if(StringUtils.isNullOrEmpty(profilePoolId)&&StringUtils.isNullOrEmpty(favoriteHrs)&&StringUtils.isNullOrEmpty(isPublic)){
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
    private void queryCommons(Map<String,String> params,QueryBuilder query,TransportClient client){
        String keyword=params.get("keyword");
        String cityCode=params.get("city_code");
        String companyName=params.get("company_name");
        String pastPosition=params.get("past_position");
        String intentionCityCode=params.get("intention_city_code");
        String companyTag=params.get("company_tag");
        String hrAutoTag=params.get("hr_auto_tag");
        String companyManualTag=params.get("company_manual_tag");
        String pastPositionKeyWord=params.get("past_position_key_word");
        String pastCompanyKeyWord=params.get("past_company_key_word");
        String hrId=params.get("hr_id");
        String profilePoolId = params.get("profile_pool_id");
        String tagIds=params.get("tag_ids");
        String favoriteHrs=params.get("favorite_hrs");
        String isPublic=params.get("is_public");
        String signal=params.get("signal");
        if(this.validateCommon(keyword,cityCode,companyName,pastPosition,intentionCityCode,companyTag,pastPositionKeyWord,pastCompanyKeyWord,hrAutoTag,companyManualTag) ){
            if(StringUtils.isNotNullOrEmpty(intentionCityCode)){
                if(!intentionCityCode.contains("111111")){
                    intentionCityCode=intentionCityCode+",111111";
                }
                this.queryByIntentionCity(intentionCityCode,query);
            }
            if(StringUtils.isNotNullOrEmpty(keyword)){
                keyword=keyword.toLowerCase();
                String cid=params.get("company_id");
                KeywordSearchParams keywordSearchParams=new KeywordSearchParams(keyword,cid,hrId,profilePoolId,tagIds,favoriteHrs,isPublic);
                if(StringUtils.isNotNullOrEmpty(signal)&&Integer.parseInt(signal)==0){
                    //执行方案一产生的语句结果 实质是返回一个QueryBuilder
                    KeywordSearchFactory keywordSearchFactory = new KeywordSearchFactory();
                    ((BoolQueryBuilder) query).must(keywordSearchFactory.search(keywordSearchParams,client));
                }else if(StringUtils.isNotNullOrEmpty(signal)&&Integer.parseInt(signal)==1){
                    //执行方案二产生的语句的QueryBuilder
                    SecondCateGory secondCateGory=new SecondCateGory();
                    secondCateGory.getQueryKeyWord(keyword,query);
                }else{
                    //执行FullTextSearchBuilder产生的语句
                    FullTextSearchBuilder fullTextSearchBuilder=new FullTextSearchBuilder();
                    ((BoolQueryBuilder) query).must(fullTextSearchBuilder.queryNewKeyWords(keyword));
                }

            }

            this.homeQuery(cityCode,query);
            String lastCompany=params.get("in_last_job_search_company");
            this.pastCompanyQuery(lastCompany,companyName,pastCompanyKeyWord,query);
            String lastPosition=params.get("in_last_job_search_position");
            this.pastPositionQuery(lastPosition,pastPosition,pastPositionKeyWord,query);
            if(StringUtils.isNotNullOrEmpty(companyTag)){
                this.queryByCompanyTag(companyTag,query);
            }
            if(StringUtils.isNotNullOrEmpty(hrAutoTag)){
                this.queryByHrAutoTag(hrAutoTag,query);
            }
            if(StringUtils.isNotNullOrEmpty(companyManualTag)) {
                this.queryByCompanyManualTag(companyManualTag,query);
            }
        }

    }



    /*
     判断是否继续执行查询操作
     */
    private boolean validateCommon(String keyword,String cityName,String companyName,String pastPosition,String intentionCity,String companyTag,
                                   String pastPositionKeyWord,String pastCompanyKeyWord,String hrAutoTag,String companyManualTag){
        return StringUtils.isNotNullOrEmpty(keyword)||StringUtils.isNotNullOrEmpty(cityName)||
                StringUtils.isNotNullOrEmpty(companyName)||StringUtils.isNotNullOrEmpty(pastPosition)||
                StringUtils.isNotNullOrEmpty(intentionCity)||StringUtils.isNotNullOrEmpty(companyTag)||
                StringUtils.isNotNullOrEmpty(pastPositionKeyWord)||
                StringUtils.isNotNullOrEmpty(pastCompanyKeyWord)||
                StringUtils.isNotNullOrEmpty(hrAutoTag)||
                StringUtils.isNotNullOrEmpty(companyManualTag);
    }
    /*
     处理现居住地
     */
    private void homeQuery(String cityName,QueryBuilder query){
        if(StringUtils.isNotNullOrEmpty(cityName)){
            if(!cityName.contains("111111")){
                cityName=cityName+",111111";
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
        //ats查询时使用的时间
        String atsTime=params.get("ats_profile_update_time");
        String hasAttachment=params.get("has_attachment");
        if(
                StringUtils.isNotNullOrEmpty(degree)||StringUtils.isNotNullOrEmpty(intentionSalaryCode)||StringUtils.isNotNullOrEmpty(sex)||
                        StringUtils.isNotNullOrEmpty(workYears)||StringUtils.isNotNullOrEmpty(updateTime)||
                        ((StringUtils.isNotNullOrEmpty(minAge)||StringUtils.isNotNullOrEmpty(maxAge))&&(!"0".equals(minAge)||!"0".equals(maxAge))||StringUtils.isNotNullOrEmpty(atsTime))
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
        if(StringUtils.isNotNullOrEmpty(atsTime)){
            this.queryByAtsProfileUpDateTime(atsTime,query);
        }
        if(StringUtils.isNotNullOrEmpty(hasAttachment)){
            this.QueryByAttachment(hasAttachment,query);
        }
        return query;
    }

    private void queryApplications(Map<String,String> params,QueryBuilder query){
        String publisherIds = params.get("publisher");
        String candidateSource = params.get("candidate_source");
        String recommend = params.get("is_recommend");
        String origins = params.get("origins");
        String submitTime = params.get("submit_time");
        String startSubmitTime=params.get("start_submit_time");
        String endSubmitTime=params.get("end_submit_time");
        String progressStatus = params.get("progress_status");
        String positionIds = params.get("position_id");
        String companyId = params.get("company_id");
        String positionWord=params.get("position_key_word");
        String positionStatus=params.get("position_status");
        String profilePoolId=params.get("profile_pool_id");
        String departmentIds=params.get("department_ids");
        String processId=params.get("process_id");
        String phaseId=params.get("phase_id");
        if (this.validateApplication(publisherIds,candidateSource,recommend,origins,submitTime,progressStatus,positionIds,positionWord,startSubmitTime,endSubmitTime)) {
            String company_tag=params.get("company_tag");
            String favoriteHrs=params.get("favorite_hrs");
            String isPublic=params.get("is_public");
            if(StringUtils.isNullOrEmpty(profilePoolId)
                    &&StringUtils.isNullOrEmpty(company_tag)
                    &&StringUtils.isNullOrEmpty(favoriteHrs)
                    &&StringUtils.isNullOrEmpty(isPublic)) {
                if (StringUtils.isNotNullOrEmpty(publisherIds)) {
                    this.queryByPublisher(publisherIds, query);
                }
            }

            if (StringUtils.isNotNullOrEmpty(departmentIds)) {
                this.queryByDepartment(departmentIds, query);
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
            if(StringUtils.isNotNullOrEmpty(startSubmitTime)){
                startSubmitTime=startSubmitTime.replace(" ","T");
                if(startSubmitTime.length()<19){
                    startSubmitTime=startSubmitTime+"T00:00:00";
                }
                searchUtil.hanleGtRange(startSubmitTime,query,"user.applications.submit_time");
            }
            if(StringUtils.isNotNullOrEmpty(endSubmitTime)){
                endSubmitTime=endSubmitTime.replace(" ","T");
                if(endSubmitTime.length()<19){
                    endSubmitTime=endSubmitTime+"T23:59:59";
                }
                searchUtil.hanleLtRange(endSubmitTime,query,"user.applications.submit_time");
            }
            if(StringUtils.isNotNullOrEmpty(processId)){
                searchUtil.handleTerm(processId,query,"user.applications.new_ats_process.process_id");
            }
            if(StringUtils.isNotNullOrEmpty(phaseId)){
                searchUtil.handleTerm(phaseId,query,"user.applications.new_ats_process.phase_id");
            }
        }

    }
    /*
     处理职位id,这里改变了参数
     */
    private void handlerPositionId(Map<String,String> params){
        String positionWord=params.get("position_key_word");
        String positionIdList=params.get("position_id");
        String is_referral = params.get("is_referral");
        if(StringUtils.isNotNullOrEmpty(positionWord)&&StringUtils.isNullOrEmpty(positionIdList)){
            String positionIds=this.PositionIdQuery(params,positionWord);
            if(StringUtils.isNotNullOrEmpty(positionIds)){
                params.put("position_id",positionIds);
            }
        }else if(StringUtils.isNotNullOrEmpty(is_referral)) {
            String positionIds=this.PositionIdQueryReferral(params,is_referral);
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
     处理职位(选中内推时)
     */
    private String PositionIdQueryReferral(Map<String,String> params, String isReferral){
        if(StringUtils.isNotNullOrEmpty(isReferral)){
            Map<String,String> suggetParams=this.convertParams(params);
            Map<String,Object> result=searchMethodUtil.suggestPosition(suggetParams);
            List<Integer> positionIdList=this.getSuggestPositionId(result);
            List<Integer> filterList = new ArrayList<>();
            if(positionIdList.size()>1024){
                filterList = positionIdList.subList(0,1024);
            } else {
                filterList = positionIdList;
            }
            String positionIds=searchUtil.listConvertString(filterList);
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
        suggetParams.put("page_size",params.get("page_size"));
        suggetParams.put("return_params","title,id");
        suggetParams.put("is_referral",params.get("is_referral"));
        String status=params.get("position_status");
        if(StringUtils.isNotNullOrEmpty(status)){
            suggetParams.put("flag",status);
        }
        return suggetParams;
    }
    /*
     校验是否向下执行
     */
    private boolean validateApplication(String publisherIds,String candidateSource,String recommend,String origins,String submitTime,
                                        String progressStatus,String positionIds,String positionWord,String startSubmitTime,String endSubmitTime){
        return StringUtils.isNotNullOrEmpty(publisherIds) || StringUtils.isNotNullOrEmpty(candidateSource) || StringUtils.isNotNullOrEmpty(recommend) ||
                StringUtils.isNotNullOrEmpty(origins) || StringUtils.isNotNullOrEmpty(submitTime) ||
                StringUtils.isNotNullOrEmpty(progressStatus) || StringUtils.isNotNullOrEmpty(positionIds)||StringUtils.isNotNullOrEmpty(startSubmitTime)||StringUtils.isNotNullOrEmpty(endSubmitTime);
    }

    /*
     组装nest的查询语句
     */
    public QueryBuilder queryNest(Map<String,String> params){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        String tagIds=params.get("tag_ids");
        String hrId=params.get("hr_id");
        String profilePoolId = params.get("profile_pool_id");
        if(StringUtils.isNotNullOrEmpty(profilePoolId)) {
            this.queryByProfilePoolId(profilePoolId,hrId,query);
            if(StringUtils.isNotNullOrEmpty(tagIds)){
                this.queryTagIds(searchUtil.stringConvertList(tagIds),query);
            }

        }
        String favoriteHrs=params.get("favorite_hrs");
        String isPublic=params.get("is_public");
        if (StringUtils.isNullOrEmpty(tagIds)
                && StringUtils.isNullOrEmpty(favoriteHrs)
                && StringUtils.isNullOrEmpty(isPublic)
                && StringUtils.isNullOrEmpty(profilePoolId)) {
            return null;
        }
        String companyId=params.get("company_id");
        this.queryByNestCompanyId(Integer.parseInt(companyId),query);

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
        String profilePoolId=params.get("profile_pool_id");
        String companyTag=params.get("company_tag");
        String favoriteHrs=params.get("favorite_hrs");
        String isPublic=params.get("is_public");
        String companyId=params.get("company_id");
        String positionStatus=params.get("position_status");
        String startSubmitTime=params.get("start_submit_time");
        String endSubmitTime=params.get("end_submit_time");
        String departmentIds=params.get("department_ids");
        if( StringUtils.isNullOrEmpty(progressStatus)&&StringUtils.isNullOrEmpty(candidateSource)&&StringUtils.isNullOrEmpty(recommend)
                &&StringUtils.isNullOrEmpty(origins)&&StringUtils.isNullOrEmpty(submitTime)&&StringUtils.isNullOrEmpty(positionId)
                &&(StringUtils.isNullOrEmpty(positionStatus)||"-1".equals(positionStatus))&&StringUtils.isNullOrEmpty(startSubmitTime)
                &&StringUtils.isNullOrEmpty(endSubmitTime)&&StringUtils.isNullOrEmpty(departmentIds)){
            return null;
        }
        StringBuffer sb=new StringBuffer();
        sb.append("user=_source.user;if(user){applications=user.applications;;origins=user.origin_data;if(applications){for(val in applications){if(");

        if(StringUtils.isNullOrEmpty(profilePoolId)&&StringUtils.isNullOrEmpty(companyTag)&&StringUtils.isNullOrEmpty(favoriteHrs)&&StringUtils.isNullOrEmpty(isPublic)){
            if(StringUtils.isNotNullOrEmpty(publisherIds)){
                List<Integer> publisherIdList=this.convertStringToList(publisherIds);
                if(!StringUtils.isEmptyList(publisherIdList)){
                    sb.append("val.publisher in "+publisherIdList.toString()+"&&");
                }
            }
        }

        if(StringUtils.isNotNullOrEmpty(departmentIds)){
            List<Integer> departmentIdList=this.convertStringToList(departmentIds);
            if(!StringUtils.isEmptyList(departmentIdList)){
                sb.append("val.team_id in "+departmentIdList.toString()+"&&");
            }
        }

        if(StringUtils.isNotNullOrEmpty(positionStatus)&&!"-1".equals(positionStatus)){
            sb.append("val.status=="+positionStatus+"&&");
        }
        if(StringUtils.isNotNullOrEmpty(companyId)&& StringUtils.isNullOrEmpty(profilePoolId)){
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
        if(StringUtils.isNotNullOrEmpty(startSubmitTime)){
            startSubmitTime=startSubmitTime.replace(" ","T");
            if(startSubmitTime.length()<19){
                startSubmitTime=startSubmitTime+"T00:00:00";
            }
            sb.append(" val.submit_time>'"+startSubmitTime+"'&&");
        }
        if(StringUtils.isNotNullOrEmpty(endSubmitTime)){
            endSubmitTime=endSubmitTime.replace(" ","T");
            if(endSubmitTime.length()<19){
                endSubmitTime=endSubmitTime+"T23:59:59";
            }
            sb.append(" val.submit_time<'"+endSubmitTime+"'&&");
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
     处理Gdpr
     */
    private ScriptQueryBuilder convertGdprScript(Map<String,String> params){
        String isGdpr=params.get("is_gdpr");
        String companyId=params.get("company_id");
        if(StringUtils.isNotNullOrEmpty(isGdpr)&&"1".equals(isGdpr)){
            StringBuffer sb=new StringBuffer();
            sb.append("user=_source.user;if(user){applications=user.applications;if(applications){");
            sb.append("for(val in applications){if(val.status!=1&&val.company_id=="+companyId+"){return true}}}}");
            ScriptQueryBuilder script=new ScriptQueryBuilder(new Script(sb.toString()));
            return script;
        }

        return null;
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

    private void queryByAtsProfileUpDateTime(String updateTime,QueryBuilder queryBuilder){
        String time=updateTime.replace(" ","T");
        this.searchUtil.hanleRangeFilter(time,queryBuilder,"user.profiles.profile.update_time");
    }

    /*
     创建按照hr查询的索引语句
     */
    private void queryByPublisher(String publisherIds,QueryBuilder queryBuilder){
        searchUtil.handleTermsFilter(publisherIds,queryBuilder,"user.applications.publisher");
    }
    /*
     创建按照departmentId查询的索引语句
     */
    private void queryByDepartment(String departmentIds,QueryBuilder queryBuilder){
        searchUtil.handleTerms(departmentIds,queryBuilder,"user.applications.team_id");
    }
    /*
     构建按照关键词查询的索引语句
     */
    private void queryByKeyWord(String keyWord,QueryBuilder queryBuilder){
        List<String> fieldList=this.getFieldList();
        List<Integer> boostList=this.getBoostList();
        searchUtil.keyWordforQueryStringPropery(keyWord,queryBuilder,fieldList,boostList);
    }


    private void queryNewKeyWords(String keyWord,String companyId,QueryBuilder queryBuilder,TransportClient client){
        int flag=this.getSearchCataGoery(keyWord,companyId,client);
        if(flag== SearTypeEnum.SEARCH_CITY.getValue()){
            this.convertCitySearch(keyWord,queryBuilder);
        }else if(flag== SearTypeEnum.SEARCH_POSITION.getValue()){
            this.convertPositionSearch(keyWord,queryBuilder);
        }else if(flag== SearTypeEnum.SEARCH_COMAPNY.getValue()){
            this.convertvalidateCompanySearch(keyWord,queryBuilder);
        }else if(flag== SearTypeEnum.SEARCH_NAME.getValue()){
            this.convertValidateNameSearch("user.profiles.basic.name",keyWord,queryBuilder);
        }else{
            this.queryByKeyWord(keyWord,queryBuilder);
        }

    }
    /*
     * @Author zztaiwll
     * @Description  组装查询职位的语句
     * @Date 下午3:02 19/1/18
     * @Param [keyWord, queryBuilder]
     * @return void
     **/
    private void convertPositionSearch(String keyWord,QueryBuilder queryBuilder){
        this.convertPositionValidateSearch(keyWord,queryBuilder);
        searchUtil.searchNewPositionDataGroup(keyWord,queryBuilder);
    }


    /*
     * @Author zztaiwll
     * @Description  判断类别
     * @Date 下午2:14 19/1/18
     * @Param [keyWord, companyId, client]
     * @return int
     **/
    private int getSearchCataGoery(String keyWord,String companyId,TransportClient client){

        boolean isCity=this.ValidateCitySearch(keyWord,companyId,client);
        if(isCity){
            return SearTypeEnum.SEARCH_CITY.getValue();
        }
        boolean isPosition=this.validatePositionSearch(keyWord,companyId,client);
        if(isPosition){
            return SearTypeEnum.SEARCH_POSITION.getValue();
        }
        boolean isCompany=this.validateCompanySearch(keyWord,companyId,client);
        if(isCompany){
            return SearTypeEnum.SEARCH_COMAPNY.getValue();
        }
        boolean isName=this.validateNameSearch("user.profiles.basic.name",keyWord,companyId,client);
        if(isName){
            return SearTypeEnum.SEARCH_NAME.getValue();
        }
        return -1;
    }
    /*
     * @Author zztaiwll
     * @Description  校验是否是公司
     * @Date 下午2:30 19/1/18
     * @Param [condition, companyId, client]
     * @return boolean
     **/
    private boolean validateCompanySearch(String condition,String companyId,TransportClient client){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        this.convertvalidateCompanySearch(condition,query);
        searchUtil.handleTerm(companyId,query,"user.applications.company_id");
        SearchRequestBuilder builder = client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_TYPE).setQuery(query);
        builder.setSize(0);
        logger.info("==========查询是否属于公司============");
        logger.info(builder.toString());
        logger.info("====================================");
        SearchResponse response = builder.execute().actionGet();
        SearchHits hit=response.getHits();
        long totalNum=hit.getTotalHits();
        if(totalNum>0){
            return true;
        }
        return false;
    }
    /*
     * @Author zztaiwll
     * @Description  校验是否是公司的语句
     * @Date 下午2:29 19/1/18
     * @Param [condition, query]
     * @return void
     **/
    private void  convertvalidateCompanySearch(String condition,QueryBuilder query){
        List<String> fieldNameList=new ArrayList<>();
        fieldNameList.add("user.profiles.other_workexps.company_name_data");
        fieldNameList.add("user.profiles.recent_job.company_name_data");
        searchUtil.shouldMatchParseQuery(fieldNameList,condition,query);
    }

    /*
     * @Author zztaiwll
     * @Description  校验是否是职位
     * @Date 下午2:27 19/1/18
     * @Param [condition, companyId, client]
     * @return boolean
     **/
    private boolean validatePositionSearch(String condition,String companyId,TransportClient client){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        this.convertPositionValidateSearch(condition,query);
        searchUtil.handleTerm(companyId,query,"user.applications.company_id");
        SearchRequestBuilder builder = client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_TYPE).setQuery(query);
        builder.setSize(0);
        logger.info("==========查询是否属于职位============");
        logger.info(builder.toString());
        logger.info("====================================");
        SearchResponse response = builder.execute().actionGet();
        SearchHits hit=response.getHits();
        long totalNum=hit.getTotalHits();
        if(totalNum>0){
            return true;
        }
        return false;
    }

    /*
     * @Author zztaiwll
     * @Description  校验是否是职位的语句拼装
     * @Date 下午2:27 19/1/18
     * @Param [condition, companyId, client]
     * @return boolean
     **/
    private void convertPositionValidateSearch(String condition,QueryBuilder query){
        List<String> fieldNameList=new ArrayList<>();
        fieldNameList.add("user.profiles.other_workexps.job_name");
        fieldNameList.add("user.profiles.recent_job.job_name");
        searchUtil.shouldWildCard(fieldNameList,condition,query);

    }
    /*
     * @Author zztaiwll
     * @Description  组装查询姓名的语句可以复用
     * @Date 上午11:58 19/1/18
     * @Param [fieldName, condition, query]
     * @return void
     **/
    private void convertValidateNameSearch(String fieldName,String condition, QueryBuilder query){
        searchUtil.queryMatchPrefixSingle(fieldName,condition,query);
        searchUtil.convertSearchNameScript(condition,query);
    }
    /*
     * @Author zztaiwll
     * @Description  查询是否是名字
     * @Date 上午11:55 19/1/18
     * @Param [fieldName, condition, client]
     * @return boolean
     **/
    private boolean validateNameSearch(String fieldName,String condition,String companyId,TransportClient client){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        this.convertValidateNameSearch(fieldName,condition,query);
        searchUtil.handleTerm(companyId,query,"user.applications.company_id");
        SearchRequestBuilder builder = client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_TYPE).setQuery(query);
        builder.setSize(0);
        logger.info("==========查询是否属于人名============");
        logger.info(builder.toString());
        logger.info("====================================");
        SearchResponse response = builder.execute().actionGet();
        SearchHits hit=response.getHits();
        long totalNum=hit.getTotalHits();
        if(totalNum>0){
            return true;
        }
        return false;
    }
    /*
     * @Author zztaiwll
     * @Description  校验城市
     * @Date 下午12:01 19/1/18
     * @Param [fieldName, condition, client]
     * @return boolean
     **/
    private boolean ValidateCitySearch(String condition,String companyId,TransportClient client){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        this.convertCitySearch(condition,query);
        searchUtil.handleTerm(companyId,query,"user.applications.company_id");
        SearchRequestBuilder builder = client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_TYPE).setQuery(query);
        builder.setSize(0);
        logger.info("==========查询是否属于城市============");
        logger.info(builder.toString());
        logger.info("====================================");
        SearchResponse response = builder.execute().actionGet();
        SearchHits hit=response.getHits();
        long totalNum=hit.getTotalHits();
        if(totalNum>0){
            return true;
        }
        return false;
    }
    /*
     * @Author zztaiwll
     * @Description  组装查询城市的语句
     * @Date 下午3:08 19/1/18
     * @Param [condition, query]
     * @return void
     **/
    private void convertCitySearch(String condition,QueryBuilder query){
        List<String> cityField=new ArrayList<>();
        cityField.add("user.profiles.basic.city_name");
        cityField.add("user.profiles.intentions.cities.city_name");
        searchUtil.shouldMatchParseQuery(cityField,condition,query);
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

    private void queryByProfilePoolId(String profilePoolId,String hrId, QueryBuilder queryBuilder){
        if(StringUtils.isNotNullOrEmpty(profilePoolId)){
            List<String> profilePoolIdList=searchUtil.stringConvertList(profilePoolId);
            if(profilePoolIdList.contains("allpublic")){
                //查询自己的和公开的
               this.queryPublic(hrId,queryBuilder);
            }else if(profilePoolIdList.contains("talent")){
                //仅仅只查询自己的
                searchUtil.handleMatch(1,queryBuilder,"user.talent_pool.is_talent");
                searchUtil.handleTerms(hrId,queryBuilder,"user.talent_pool.hr_id");
            }else if(profilePoolIdList.contains("public")){
                searchUtil.handleMatch(1,queryBuilder,"user.talent_pool.is_public");
            }else if(profilePoolIdList.contains("hrpublic")){
                searchUtil.handleMatch(1,queryBuilder,"user.talent_pool.is_public");
                searchUtil.handleTerms(hrId,queryBuilder,"user.talent_pool.hr_id");
            }else{
                if(!profilePoolIdList.contains("alltalent")){
                    //查询简历池关键是权限，查询的是公开或者自己私有下的相关标签
                    this.queryPublic(hrId,queryBuilder);
                    searchUtil.handleTerm(profilePoolId,queryBuilder,"user.talent_pool.profile_pool_id");
                }
            }
        }

    }


    //处理手动标签
    private void queryTagIds(List<String> tagIdList,QueryBuilder queryBuilder){
        searchUtil.handleTagMatch(tagIdList,queryBuilder,"user.talent_pool.tags.id");
    }

    private void queryPublic(String hrId,QueryBuilder queryBuilder){
        QueryBuilder keyand = QueryBuilders.boolQuery();
        searchUtil.handlerShouldTerm("1",keyand,"user.talent_pool.is_public");
        searchUtil.shouldTermsQuery(searchUtil.stringConvertList(hrId),keyand,"user.talent_pool.hr_id");
        ((BoolQueryBuilder) queryBuilder).must(keyand);
    }

    /*
      构建按招标签的查询语句
     */
    private void queryByCompanyTag(String companyTag,QueryBuilder queryBuilder){
        searchUtil.handlerCompanyTag(companyTag,queryBuilder);
    }

    private void queryByHrAutoTag(String hrAutoTag,QueryBuilder queryBuilder){
        searchUtil.handlerHrAutoTag(hrAutoTag,queryBuilder);
    }
    private void queryByCompanyManualTag(String companyManualTag, QueryBuilder queryBuilder) {
        searchUtil.handlerCompanyManualTag(companyManualTag,queryBuilder);
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
        searchUtil.handleTerms(cityNames,queryBuilder,"user.profiles.intentions.cities.city_code");
    }
    private void queryByIntentionCityTag(String cityNames,QueryBuilder queryBuilder){
        List<String> list=new ArrayList<>();
        list.add("user.profiles.intentions.cities.city_code");
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

    /*
      按照有无附件简历查询
     */
    private void QueryByAttachment(String hasAttachment,QueryBuilder queryBuilder){
        if("1".equals(hasAttachment)){
            QueryBuilder attachmentQuery = QueryBuilders.existsQuery("user.profiles.attachments");
            ((BoolQueryBuilder) queryBuilder).must(attachmentQuery);
        }else{
            QueryBuilder attachmentQuery = QueryBuilders.existsQuery("user.profiles.attachments");
            ((BoolQueryBuilder) queryBuilder).mustNot(attachmentQuery);
        }
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
        searchUtil.handleTerms(home,queryBuilder,"user.profiles.basic.city_code");
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
        SimpleDateFormat ff=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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

    private AbstractAggregationBuilder handleAggInfo(Map<String,String> params,String name,String progressStatus,int type){
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
        String recommend=params.get("is_recommend");
        String positionStatus=params.get("position_status");
        String startSubmitTime=params.get("start_submit_time");
        String endSubmitTime=params.get("end_submit_time");
        String departmentIds=params.get("department_ids");
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
        if(StringUtils.isNotNullOrEmpty(startSubmitTime)){
            startSubmitTime=startSubmitTime.replace(" ","T");
            if(startSubmitTime.length()<19){
                startSubmitTime=startSubmitTime+"T00:00:00";
            }
            sb.append("val.submit_time>'"+startSubmitTime+"'&&");
        }
        if(StringUtils.isNotNullOrEmpty(endSubmitTime)){
            endSubmitTime=endSubmitTime.replace(" ","T");
            if(endSubmitTime.length()<19){
                endSubmitTime=endSubmitTime+"T23:59:59";
            }
            sb.append("val.submit_time<'"+endSubmitTime+"'&&");
        }
        if(StringUtils.isNotNullOrEmpty(departmentIds)){
            List<Integer> departmentIdList=this.convertStringToList(departmentIds);
            if(!StringUtils.isEmptyList(departmentIdList)){
                sb.append("val.team_id in "+departmentIdList.toString()+"&&");
            }
        }
        if(StringUtils.isNotNullOrEmpty(positionIds)){
            List<Integer> positionIdList=this.convertStringToList(positionIds);
            sb.append("val.position_id in"+positionIdList.toString()+"&&");
        }
        if(StringUtils.isNotNullOrEmpty(candidateSource)){
            sb.append("val.candidate_source =="+candidateSource+"&&");
        }
        if(StringUtils.isNotNullOrEmpty(recommend)&&Integer.parseInt(recommend)>0){
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
        String companyName=params.get("company_name");
        String pastPosition=params.get("past_position");
        if(StringUtils.isNotNullOrEmpty(keyword)||StringUtils.isNotNullOrEmpty(keyword)||
                StringUtils.isNotNullOrEmpty(companyName)||StringUtils.isNotNullOrEmpty(pastPosition)){
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

    /*
     判断某一字段是否存在的查询
     */
    private boolean getIsExistField(String name){
        TransportClient client = searchUtil.getEsClient();
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        QueryBuilder cityfilter = QueryBuilders.existsQuery(name);
        ((BoolQueryBuilder) query).must(cityfilter);
        SearchRequestBuilder builder = client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_TYPE).setQuery(query);
        builder.setSize(0);
        logger.info("=========查询排序字段是否存在==================");
        logger.info(builder.toString());
        logger.info("============================================");
        SearchResponse response = builder.execute().actionGet();
        Map<String,Object> result = searchUtil.handleData(response, "isExists");
        if(StringUtils.isEmptyMap(result)){
            return false;
        }else{
            long count=(long)result.get("totalNum");
            if(count>0){
                return true;
            }else{
                return false;
            }
        }
    }
}


