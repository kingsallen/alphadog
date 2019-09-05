package com.moseeker.searchengine.service.impl;

import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrTeamDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrTeam;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.searchengine.domain.fallback.FallBack;
import com.moseeker.searchengine.domain.vo.PositionDetail;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.moseeker.searchengine.util.SearchUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import static com.moseeker.common.constants.Constant.DATABASE_PAGE_SIZE;
import static com.moseeker.common.constants.Constant.PAGE_SIZE;
import static com.moseeker.common.constants.Constant.RETRY_UPPER_LIMIT;

@Service
public class PositionSearchEngine {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "cacheClient")
    private RedisClient client;

    @Autowired
    private SearchUtil searchUtil;

    @Autowired
    private HrTeamDao teamDao;

    @Autowired
    private HrCompanyDao companyDao;

    @Autowired
    private JobPositionDao positionDao;

    private SerializeConfig config = new SerializeConfig();

    @PostConstruct
    public void init() {
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    //按条件查询，如果prefix的方式无法差的数据，那么转换为query_string的方式查询
    @CounterIface
    public Map<String,Object> search(String keyWord,String industry,String salaryCode,int page,int pageSize,String cityCode,String startTime
            ,String endTime,int companyId,int teamId,int motherCompanyId,int order,int moduleId,String candidateSource){
        Map<String,Object> map=new HashMap<String,Object>();
        TransportClient client=null;
        try{
          client=searchUtil.getEsClient();
            if(!StringUtils.isEmpty(cityCode)&&!cityCode.equals("233333")&&!cityCode.equals("111111")){
                cityCode=cityCode+",111111";
            }
            SearchResponse hits =this.quertPrefix(keyWord, industry, salaryCode, page, pageSize, cityCode
                    ,startTime,endTime, companyId,teamId,motherCompanyId,order,moduleId,candidateSource,client);
            if(hits!=null){
                long hitNum=hits.getHits().getTotalHits();
                if(hitNum==0&&StringUtils.isNotEmpty(keyWord)){
                    SearchResponse hitsData=this.quertString(keyWord, industry, salaryCode, page, pageSize, cityCode,startTime,endTime
                            , companyId,teamId,motherCompanyId,order,moduleId,candidateSource,client);
                    map=searchUtil.handleData(hitsData,"positions");
                    return map;
                }else{
                    map=searchUtil.handleData(hits,"positions");
                    return map;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return fetchFallback(page, pageSize, companyId);
        }
        return new HashMap<String,Object>();
    }
    //按照query_string的方式查询数据
    public SearchResponse quertString(String keyWord,String industry,String salaryCode,int page,int pageSize,String cityCode,String startTime,String endTime
            ,int companyId,int teamId,int motherCompanyId,int order,int moduleId,String candidateSource,TransportClient client){
        if(client!=null){
            QueryBuilder sentence=this.handleStringSearchSentence(keyWord, industry, salaryCode, page, pageSize,
                    cityCode,startTime,endTime,companyId,teamId,motherCompanyId,moduleId,candidateSource);
            SearchRequestBuilder responseBuilder=client.prepareSearch("positions").setTypes("position")
                    .setQuery(sentence)
                    .addAggregation(searchUtil.handleIndustry("industry"))
                    .addAggregation(searchUtil.handleArray("_source.position.city_data","city"))
                    .addAggregation(searchUtil.handle("_source.position.salary_data","salary"))
                    .setFrom((page-1)*pageSize)
                    .setSize(pageSize)
                    .setTrackScores(true);
            if(order==1){
                responseBuilder.addSort("position.update_time",SortOrder.DESC);
                if(StringUtils.isNotEmpty(cityCode)&&!cityCode.contains("233333")&&!cityCode.equals("111111")){
                    SortBuilder builder=new ScriptSortBuilder(this.buildScriptCitySort(cityCode,1),"number");
                    builder.order(SortOrder.DESC);
                    responseBuilder.addSort(builder);
                }
            }else{
                if(StringUtils.isNotEmpty(cityCode)&&!cityCode.equals("233333")&&!cityCode.equals("111111")){
                    SortBuilder builder=new ScriptSortBuilder(this.buildScriptCitySort(cityCode,2),"number");
                    builder.order(SortOrder.DESC);
                    responseBuilder.addSort(builder);
                }
            }
            logger.info(responseBuilder.toString());
            SearchResponse response = responseBuilder.execute().actionGet();
            return response;
        }
        return null;
    }
    //按照prefix的方式查询数据
    public SearchResponse quertPrefix(String keyWord,String industry,String salaryCode,int page,int pageSize
            ,String cityCode,String startTime,String endTime,int companyId,int teamId,int motherCompanyId
            ,int order,int moduleId,String candidateSource,TransportClient client){
        if(client!=null){
            QueryBuilder sentence=this.handlePrefixSearchSentence(keyWord, industry, salaryCode, page, pageSize, cityCode,startTime,
                                                        endTime,companyId,teamId,motherCompanyId,moduleId,candidateSource);
            SearchRequestBuilder responseBuilder=client.prepareSearch("positions").setTypes("position")
                    .setQuery(sentence)
                    .addAggregation(searchUtil.handleIndustry("industry"))
                    .addAggregation(searchUtil.handleArray("_source.position.city_data","city"))
                    .addAggregation(searchUtil.handle("_source.position.salary_data","salary"))
                    .setFrom((page-1)*pageSize)
                    .setSize(pageSize)
                    .setTrackScores(true);
            if(StringUtils.isNotEmpty(keyWord)){
                Script script=this.buildScriptSort(keyWord);
                ScriptSortBuilder builder=new ScriptSortBuilder(script,"number");
                builder.order( SortOrder.DESC);
                responseBuilder.addSort(builder);

            }
            if(order==1){
                responseBuilder.addSort("position.update_time",SortOrder.DESC);
                if(StringUtils.isNotEmpty(cityCode)&&!cityCode.contains("233333")&&!cityCode.equals("111111")){
                    SortBuilder builder=new ScriptSortBuilder(this.buildScriptCitySort(cityCode,1),"number");
                    builder.order(SortOrder.DESC);
                    responseBuilder.addSort(builder);
                }


            }else{
                if(StringUtils.isNotEmpty(cityCode)&&!cityCode.equals("233333")&&!cityCode.equals("111111")){
                    SortBuilder builder=new ScriptSortBuilder(this.buildScriptCitySort(cityCode,2),"number");
                    builder.order(SortOrder.DESC);
                    responseBuilder.addSort(builder);
                }
            }
            logger.info(responseBuilder.toString());
            SearchResponse response = responseBuilder.execute().actionGet();
            return response;
        }
        return null;
    }
    //根据query_string关键字组织查询
    private QueryBuilder handleStringSearchSentence(String keyWord,String industry,String salaryCode,int page,int pageSize
            ,String cityCode,String startTime,String endTime,int companyId,int teamId,int motherCompanyId,int moduleId,String candidateSource){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        List<String> list=new ArrayList<String>();
        list.add("position.title");
        searchUtil.handleKeyWordforQueryString(keyWord, false, query, list);
        CommonQuerySentence(industry, salaryCode,cityCode, startTime, endTime,companyId,teamId,motherCompanyId,moduleId,candidateSource, query);
        return query;
    }
    //通过match_prhase_prefix查询
    private QueryBuilder handlePrefixSearchSentence(String keyWord,String industry,String salaryCode,int page,int pageSize,String cityCode,String startTime,
                                                    String endTime,int companyId,int teamId,int motherCompanyId,int moduleId,String candidateSource){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        List<String> list=new ArrayList<String>();
        list.add("position.title");
        searchUtil.handleKeyWordForPrefix(keyWord, false, query, list);
        CommonQuerySentence(industry, salaryCode,cityCode, startTime, endTime, companyId,teamId,motherCompanyId,moduleId,candidateSource,query);
        return query;
    }
    //公共查询的条件部分
    private void CommonQuerySentence(String industry,String salaryCode,String cityCode,String startTime,String endTime,
                                     int companyId,int teamId,int motherCompanyId,int moduleId,String candidateSource,QueryBuilder query){
        searchUtil.handleTerms(industry, query, "company.industry_code");
        searchUtil.handleTerms(cityCode, query, "position.city_data.code");
        if(companyId>0){
            searchUtil.handleMatch(companyId,query,"company.id");
        }
        if(teamId>0){
            searchUtil.handleMatch(teamId,query,"team.id");
        }
        if(motherCompanyId>0){
            searchUtil.handleMatch(motherCompanyId,query,"position.company_id");
        }
        if(moduleId>0){
            searchUtil.handleMatch(moduleId,query,"module_id");
        }
        handleDateGT(startTime, query);
        handleDateLT(endTime, query);
        handleSalary(salaryCode, query);
        if(StringUtils.isNotBlank(candidateSource)&&!"-1".equals(candidateSource)){
            searchUtil.handleTerms(candidateSource,query,"position.candidate_source");
        }
    }
    //处理时间范围查询，从xxx来时
    private void handleDateGT(String startTime, QueryBuilder query){
        if(StringUtils.isNotEmpty(startTime)){
            RangeQueryBuilder fullf = QueryBuilders.rangeQuery("position.publish_date").from(startTime);
            ((BoolQueryBuilder) query).must(fullf);
        }
    }
    // 小于xxxx时间
    private void handleDateLT(String endTime, QueryBuilder query){
        if(StringUtils.isNotEmpty(endTime)){
            RangeQueryBuilder fullf = QueryBuilders.rangeQuery("position.publish_date").to(endTime);
            ((BoolQueryBuilder) query).must(fullf);
        }
    }
    //处理排序
    private  Script handleSort(String order,String keywords,QueryBuilder query){
        StringBuffer sb=new StringBuffer();
        sb.append("double score = _score;");
        sb.append("name=_source.position.title;");
        sb.append("if(name.startsWith('"+keywords+"')");
        sb.append("{score=score*100}");
        sb.append(";return score;");
        String scripts=sb.toString();
        Script script=new Script(scripts);
        return script;
    }
    //处理工资范围
    public void handleSalary(String salaryCode,QueryBuilder query){
        if(StringUtils.isNotEmpty(salaryCode)){
            List<Map> list=convertToListMap(salaryCode);

            StringBuffer sb=new StringBuffer();
            sb.append("top=doc['position.salary_top'].value;bottom=doc['position.salary_bottom'].value;flag=0;");
            if(list!=null&&list.size()>0){
                for(int i=0;i<list.size();i++){
                    int salaryTop=(int) list.get(i).get("salaryTop");
                    int salaryBottom=(int) list.get(i).get("salaryBottom");
                    sb.append("if(bottom<"+salaryBottom+"&&top>"+salaryTop+"){flag=1};");
                    sb.append("if(bottom<="+salaryBottom+"&&top>"+salaryBottom+"){flag=1};");
                    sb.append("if(bottom<"+salaryTop+"&&top>="+salaryTop+"){flag=1};");
                    sb.append("if(bottom>="+salaryBottom+"&&top<="+salaryTop+"){flag=1};");
                }
                sb.append("if(flag==1){return true;}");
            }
            String builder=sb.toString();
            Script script=new Script(builder);
            QueryBuilder keyand = QueryBuilders.scriptQuery(script);
            ((BoolQueryBuilder) query).filter(keyand);
        }
    }
    //组装sort的script
    private Script buildScriptSort(String keywords){
        StringBuffer sb=new StringBuffer();
        sb.append("double score = _score;title=_source.position.title;");
        sb.append("if(title&&title.startsWith('"+keywords+"'))");
        sb.append("{score=score*100};return score;");
        String scripts=sb.toString();
        Script script=new Script(scripts);
        return script;
    }
    private List<Map> convertToListMap(String data){
        if(StringUtils.isNotEmpty(data)){
            return JSON.parseArray(data, Map.class);
        }
        return null;
    }

    private Map convertToMap(String data){
        if(!StringUtils.isNotEmpty(data)){
            return (Map) JSON.parse(data);
        }
        return null;
    }
    /*
          按照被命中的城市是否是全国。来重新处理顺序问题，只有全国的，或者是全国命中的沉底
         */
    private Script buildScriptCitySort(String fieldValue, int flag ){
        StringBuffer sb=new StringBuffer();
        sb.append("double score=0 ;");
        if(flag==1) {
            sb.append("value=doc['position.update_time'].value;if(value){score=value};");
        }else{
            sb.append("value=_score;if(value){score=value};");
        }
        sb.append("city_flag=doc['position.city_flag'].value;if(city_flag==1){score=score/100;return score;};");
        sb.append("city_data=_source.position.city_data;flag=0; if(city_data){ for(city in city_data){code=city['code'];");
        String[] array=fieldValue.split(",");
        for(String param:array){
            int code=Integer.parseInt(param);
            if(code!=111111){
                sb.append("if(code=="+code+"){flag=1;};");
            }
        }
        sb.append("};};");
        sb.append("if(flag!=1){score=score/100;};return score;");
        String scripts=sb.toString();
        Script script=new Script(scripts);
        return script;
    }

    /**
     * 该方法不支持聚合好！！！即必须要有公司编号才能生效
     * @param pageNo 页码
     * @param pageSize 每页数量
     * @param companyId 公司编号
     * @return 备份方案的职位列表数据
     */
    private Map<String,Object> fetchFallback(int pageNo,int pageSize, int companyId) {

        if (pageNo <=0) {
            pageNo = 1;
        }
        if (pageSize <= 0 || pageSize > DATABASE_PAGE_SIZE) {
            pageSize = PAGE_SIZE;
        }

        String pattern = FallBack.generatePCPositionListPattern(pageNo, pageSize, companyId);
        String json = FallBack.fetchFromCache(client, pattern);
        if (json != null) {
            return JSONObject.parseObject(json);
        }

        String uuid = UUID.randomUUID().toString();
        try {
            boolean getLock = client.tryGetLock(Constant.APPID_ALPHACLOUD, KeyIdentifier.REDIS_LOG.toString(), pattern, uuid);
            if (getLock) {
                JSONObject jsonObject = fetchFromDB(pageNo, pageSize, companyId);

                client.set(Constant.APPID_ALPHACLOUD, KeyIdentifier.POSITION_LIST_FALLBACK.toString(), pattern, jsonObject.toJSONString());

                return jsonObject;
            } else {
                //等待获取redis内容
                try {
                    return waitForCache(0, pattern);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                    Thread.interrupted();
                    return new HashMap<>(0);
                }
            }
        } finally {
            client.releaseLock(Constant.APPID_ALPHACLOUD, KeyIdentifier.REDIS_LOG.toString(), pattern, uuid);
        }
    }

    /**
     * 从数据库中查找职位列表数据
     * @param pageNo 页码
     * @param pageSize 每页显示的数量
     * @param companyId 公司编号
     * @return 备份方案的职位列表数据
     */
    private JSONObject fetchFromDB(int pageNo, int pageSize, int companyId) {

        JSONObject jsonObject = new JSONObject();

        int total = positionDao.count(companyId);
        jsonObject.put("totalNum", total);
        List<PositionDetail> positionDetails = new ArrayList<>();
        jsonObject.put("positions", positionDetails);
        List<JobPosition> positionList = positionDao.fetch(companyId, pageNo, pageSize);
        if (positionList != null && positionList.size() > 0) {

            List<Integer> positionIdList = new ArrayList<>(positionList.size());

            List<Integer> companyIdList = new ArrayList<>(positionList.size());

            List<Integer> teamIdList = new ArrayList<>(positionIdList.size());
            for (JobPosition position : positionList) {
                if (position.getCompanyId() != null && position.getCompanyId() > 0) {
                    companyIdList.add(position.getCompanyId());
                }
                if (position.getTeamId() != null && position.getTeamId() > 0) {
                    teamIdList.add(position.getTeamId());
                }
                positionIdList.add(position.getId());

            }
            List<HrTeam> teams = teamDao.listByIdList(teamIdList);

            List<HrCompany> companyList = companyDao.list(companyIdList);

            for (JobPosition position : positionList) {
                PositionDetail positionDetail = new PositionDetail();
                positionDetail.setPosition(position);
                Optional<HrTeam> hrTeamOptional = teams
                        .stream()
                        .filter(team -> team.getId().equals(position.getTeamId()))
                        .findAny();
                if (hrTeamOptional.isPresent()) {
                    positionDetail.setTeam(hrTeamOptional.get());
                }

                Optional<HrCompany> companyOptional = companyList
                        .stream()
                        .filter(company -> company.getId().equals(position.getCompanyId()))
                        .findAny();
                if (companyOptional.isPresent()) {
                    positionDetail.setCompany(companyOptional.get());
                }
                positionDetails.add(positionDetail);
            }

        } else {
            return jsonObject;
        }
        String str = JSONObject.toJSONString(jsonObject, config);
        return (JSONObject) JSONObject.parse(str);
    }

    /**
     * 如果触发锁，那么循环等待redis的数据
     * @param times 等待次数
     * @param pattern 查询条件
     * @return 备份方案的职位列表数据
     * @throws InterruptedException 线程中断异常
     */
    private JSONObject waitForCache(int times, String pattern) throws InterruptedException {
        while (times <= RETRY_UPPER_LIMIT) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
                return new JSONObject();
            }
            String json = FallBack.fetchFromCache(client, pattern);
            if (json != null) {
                return JSONObject.parseObject(json);
            } else {
                return waitForCache(times+1, pattern);
            }
        }
        return new JSONObject();
    }
}
