package com.moseeker.searchengine.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeePointsDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.pojo.EmployeePointsRecordPojo;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.ConverTools;
import com.moseeker.common.util.EsClientInstance;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.searchengine.SearchEngineException;
import com.moseeker.searchengine.service.impl.tools.EmployeeBizTool;
import com.moseeker.searchengine.util.SearchMethodUtil;
import com.moseeker.searchengine.util.SearchUtil;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.ScriptQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.MetricsAggregationBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@CounterIface
public class SearchengineService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SearchUtil searchUtil;

    @Autowired
    private UserEmployeeDao userEmployeeDao;

    @Autowired
    private HrCompanyDao hrCompanyDao;

    @Autowired
    private UserUserDao userUserDao;

    @Autowired
    private UserEmployeePointsDao userEmployeePointsDao;

    @Autowired
    private SearchMethodUtil searchMethodUtil;


    @CounterIface
    public Response query(String keywords, String cities, String industries, String occupations, String scale,
                          String employment_type, String candidate_source, String experience, String degree, String salary,
                          String company_name, int page_from, int page_size, String child_company_name, String department,
                          boolean order_by_priority, String custom) throws TException {
        List listOfid = new ArrayList();
        try{
            SearchResponse response=this.getSearchIndex(keywords, cities,industries,occupations, scale,
                     employment_type, candidate_source, experience, degree,  salary, company_name, page_from, page_size,
                     child_company_name, department,order_by_priority,  custom);
            for (SearchHit hit : response.getHits()) {
                String id = ConverTools.converToString(hit.getSource().get("id"));
                listOfid.add(id);
            }
        } catch (Exception e) {
            logger.error("error in search", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
        Map<String, List> res = new HashMap<String, List>();
        res.put("jd_id_list", listOfid);
        return ResponseUtils.success(res);
    }

    @CounterIface
    public Response queryPositionIndex(String keywords, String cities, String industries, String occupations, String scale,
                                       String employment_type, String candidate_source, String experience, String degree, String salary,
                                       String company_name, int page_from, int page_size, String child_company_name, String department,
                                       boolean order_by_priority, String custom){

        Map<String,Object> result=new HashMap<>();
        try{
            SearchResponse response=this.getSearchIndex(keywords, cities,industries,occupations, scale,
                    employment_type, candidate_source, experience, degree,  salary, company_name, page_from, page_size,
                    child_company_name, department,order_by_priority,  custom);
            List listOfid = new ArrayList();
            SearchHits hits=response.getHits();
            long totalNum=hits.getTotalHits();
            if(totalNum>0) {
                for (SearchHit hit :hits) {
                    String id = ConverTools.converToString(hit.getSource().get("id"));
                    listOfid.add(id);
                }
            }
            result.put("jd_id_list",listOfid);
            result.put("total",totalNum);
        } catch (Exception e) {
            logger.error("error in search", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
        if(result.isEmpty()){
            return ResponseUtils.success("");
        }
        return ResponseUtils.success(result);
    }
    /*
     小程序用的查詢職位接口
     */
    @CounterIface
    public Map<String,Object> searchPositionMini(Map<String,String> params){

        TransportClient client= EsClientInstance.getClient();
        String pageFrom=params.get("page");
        String pageSize=params.get("pageSize");
        String childCompanyId=params.get("childCompanyId");
        String motherCompanyId=params.get("motherCompanyId");
        String keywords=params.get("keyword");
        String status=params.get("status");
        String publisher=params.get("publisher");
        if (StringUtils.isBlank(pageFrom)||"0".equals(pageFrom)) {
            pageFrom = "0";
        }
        if (StringUtils.isBlank(pageSize)||"0".equals(pageSize)) {
            pageSize = "20";
        }
        QueryBuilder query=this.getPositionQueryBuilder(keywords,null,null,null, null,
                null, null, null, null,  null, motherCompanyId,
                childCompanyId, null, null);

        if(StringUtils.isNotBlank(status)){
            searchUtil.handleMatch(Integer.parseInt(status),query,"status");
        }else{
            searchMethodUtil.handlerStatusQuery(query);
        }
        if(StringUtils.isNotBlank(publisher)){
            searchUtil.handleMatch(Integer.parseInt(publisher),query,"publisher");
        }
        SearchRequestBuilder responseBuilder = client.prepareSearch("index").setTypes("fulltext")
                .setQuery(query);
        boolean haskey=false;
        if(StringUtils.isNotBlank(keywords)){
            haskey=true;
        }
        responseBuilder.addSort("status", SortOrder.ASC);
        this.positionIndexOrder(responseBuilder,true,haskey,null);
//        this.handlerOrderByPriorityCityOrTimeOrStatus(responseBuilder,null);
        if(StringUtils.isNotBlank(status)){
            responseBuilder.setSize(0);
        }else{
            responseBuilder.setFrom(Integer.parseInt(pageFrom)).setSize(Integer.parseInt(pageSize));
        }
        responseBuilder.setTrackScores(true);
        logger.info(responseBuilder.toString());
        SearchResponse response = responseBuilder.execute().actionGet();
        Map<String,Object> result=searchUtil.handleData(response,"positionList");
        return result;
    }


    /*
     将查询elasticsearch index的逻辑独立出来
     */
    private SearchResponse getSearchIndex(String keywords, String cities, String industries, String occupations, String scale,
                                          String employment_type, String candidate_source, String experience, String degree, String salary,
                                          String company_name, int page_from, int page_size, String child_company_name, String department,
                                          boolean order_by_priority, String custom){
        TransportClient client= EsClientInstance.getClient();
        if (page_from == 0) {
            page_from = 0;
        }
        if (page_size == 0) {
            page_size = 20;
        }
        boolean haskey = false;

        if (!StringUtils.isEmpty(keywords)&&!"".equals(keywords.trim())) {
            haskey = true;
        }

        if (!StringUtils.isEmpty(industries)) {
            haskey = true;
        }
        QueryBuilder query = this.getPositionQueryBuilder(keywords, cities, industries, occupations, scale,
                employment_type, candidate_source, experience, degree, salary, company_name,
                child_company_name, department, custom);
        QueryBuilder status_filter = QueryBuilders.matchPhraseQuery("status", "0");
        ((BoolQueryBuilder) query).must(status_filter);
        SearchRequestBuilder responseBuilder = client.prepareSearch("index").setTypes("fulltext")
                .setQuery(query);
        this.positionIndexOrder(responseBuilder, order_by_priority, haskey, cities);
        responseBuilder.setFrom(page_from).setSize(page_size);
        responseBuilder.setTrackScores(true);
        logger.info(responseBuilder.toString());
        SearchResponse response = responseBuilder.execute().actionGet();
        return response;

    }
    /*
     封装一下对职位列表的查询语句
     */
    private QueryBuilder getPositionQueryBuilder(String keywords,String cities, String industries, String occupations, String scale,
                                                 String employment_type, String candidate_source, String experience, String degree, String salary,
                                                 String company_name, String child_company_name, String department,
                                                 String custom){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        if (!StringUtils.isEmpty(keywords)) {
            String[] keyword_list = keywords.split(" ");
            QueryBuilder keyand = QueryBuilders.boolQuery();
            for (int i = 0; i < keyword_list.length; i++) {
                String keyword = keyword_list[i];
                if(StringUtils.isBlank(keyword)){
                    continue;
                }
                BoolQueryBuilder keyor = QueryBuilders.boolQuery();
                QueryBuilder fullf = QueryBuilders.queryStringQuery(keyword)
                        .field("title", 20.0f)
                        .field("city", 10.0f)
                        .field("city_ename",10.0f)
                        .field("team_name", 5.0f)
                        .field("custom", 4.0f)
                        .field("occupation", 3.0f);
                ((BoolQueryBuilder) keyand).must(fullf);
            }
            ((BoolQueryBuilder) query).must(keyand);
        }
        if (!StringUtils.isEmpty(cities)) {
            String[] city_list = cities.split(",");
            QueryBuilder cityor = QueryBuilders.boolQuery();
            for (int i = 0; i < city_list.length; i++) {
                String city = city_list[i];
                System.out.println(city);
                QueryBuilder cityfilter =this.handlerCommonCity(city);
                QueryBuilder cityboosting = QueryBuilders.boostingQuery()
                        .positive(cityfilter)
                        .negative(QueryBuilders.matchPhraseQuery("title", city)).negativeBoost(0.5f);

                ((BoolQueryBuilder) cityor).should(cityboosting);
            }
            ((BoolQueryBuilder) query).must(cityor);
        }

        if (!StringUtils.isEmpty(industries)) {
            String[] industry_list = industries.split(",");
            QueryBuilder industryor = QueryBuilders.boolQuery();
            for (int i = 0; i < industry_list.length; i++) {
                String industry = industry_list[i];
                QueryBuilder industryfilter = QueryBuilders.matchPhraseQuery("industry", industry);
                ((BoolQueryBuilder) industryor).should(industryfilter);
            }
            ((BoolQueryBuilder) query).must(industryor);
        }
        if (!StringUtils.isEmpty(occupations)) {
            String[] occupation_list = occupations.split(",");
            QueryBuilder occupationor = QueryBuilders.boolQuery();
            for (int i = 0; i < occupation_list.length; i++) {
                String occupation = occupation_list[i];
                QueryBuilder occupationfilter = QueryBuilders.termQuery("search_data.occupation", occupation);
                ((BoolQueryBuilder) occupationor).should(occupationfilter);
            }
            ((BoolQueryBuilder) query).must(occupationor);
        }

        if (!StringUtils.isEmpty(scale)) {

            QueryBuilder scalefilter = QueryBuilders.matchPhraseQuery("scale", scale);
            ((BoolQueryBuilder) query).must(scalefilter);
        }

        if (!StringUtils.isEmpty(employment_type)) {
            QueryBuilder employmentfilter = QueryBuilders.matchPhraseQuery("employment_type_name", employment_type);
            ((BoolQueryBuilder) query).must(employmentfilter);
        }

        if (!StringUtils.isEmpty(candidate_source)) {

            QueryBuilder candidatefilter = QueryBuilders.matchPhraseQuery("candidate_source_name", candidate_source);
            ((BoolQueryBuilder) query).must(candidatefilter);
        }

        if (!StringUtils.isEmpty(department)) {
            QueryBuilder departmentfilter = QueryBuilders.termQuery("search_data.team_name", department);
            ((BoolQueryBuilder) query).must(departmentfilter);
        }

        if (!StringUtils.isEmpty(experience)) {
            QueryBuilder experiencefilter = QueryBuilders.matchPhraseQuery("experience", experience);
            ((BoolQueryBuilder) query).must(experiencefilter);
        }


        if (!StringUtils.isEmpty(degree)) {
            String[] degree_list = degree.split(",");
            QueryBuilder degreeor = QueryBuilders.boolQuery();
            for (int i = 0; i < degree_list.length; i++) {
                String degree_name = degree_list[i];
                QueryBuilder degreefilter = QueryBuilders.termQuery("search_data.degree_name", degree_name);
                ((BoolQueryBuilder) degreeor).should(degreefilter);
            }
            ((BoolQueryBuilder) query).must(degreeor);
        }


        if (!StringUtils.isEmpty(company_name)) {
            String[] company_list = company_name.split(",");
            QueryBuilder companyor = QueryBuilders.boolQuery();
            for (int i = 0; i < company_list.length; i++) {
                String company_id = company_list[i];
                QueryBuilder companyfilter = QueryBuilders.matchPhraseQuery("company_id", company_id);
                ((BoolQueryBuilder) companyor).should(companyfilter);
            }
            ((BoolQueryBuilder) query).must(companyor);
        }


        if (!StringUtils.isEmpty(salary)) {
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

        if (!StringUtils.isEmpty(child_company_name)) {
            QueryBuilder child_company_filter = QueryBuilders.matchPhraseQuery("publisher_company_id", child_company_name);
            ((BoolQueryBuilder) query).must(child_company_filter);
        }

        if (!StringUtils.isEmpty(custom)) {
            QueryBuilder custom_filter = QueryBuilders.termQuery("search_data.custom", custom);
            ((BoolQueryBuilder) query).must(custom_filter);
        }
        return query;
    }
    /*
         处理城市数据
         */
    private QueryBuilder handlerCommonCity(String citys){
        if(StringUtils.isNotBlank(citys)){
            List<String> fieldList=new ArrayList<>();
            fieldList.add("city");
            fieldList.add("city_ename");
            QueryBuilder keyand=searchUtil.shouldMatchQuery(fieldList,searchUtil.stringConvertList(citys));
            return keyand;
        }
        return null;
    }
    /*
     封装一下对排序的语句
     */
    private void positionIndexOrder(SearchRequestBuilder responseBuilder,boolean order_by_priority,boolean haskey,String cities){
        if (order_by_priority) {
            if (haskey) {
                this.handlerOrderByPriorityCityOrScore(responseBuilder,cities);
            } else {
                this.handlerOrderByPriorityCityOrTime(responseBuilder,cities);
            }
        } else {
            this.handlerOrderByCityOrScore(responseBuilder,cities);
        }
    }

    /*
     继续对排序进行细分1,按照priority排序，再按照城市或者得分排序
     */
   private void handlerOrderByPriorityCityOrScore(SearchRequestBuilder responseBuilder,String cities){
       responseBuilder.addSort("priority", SortOrder.ASC);
       if (!StringUtils.isEmpty(cities) && !"全国".equals(cities)) {
           SortBuilder builder = new ScriptSortBuilder(this.buildScriptSort(cities, 0), "number");
           builder.order(SortOrder.DESC);
           responseBuilder.addSort(builder);
       } else {
           responseBuilder.addSort("_score", SortOrder.DESC);
       }
       responseBuilder.addSort("id",SortOrder.DESC);
   }

   /*
    继续对排序进行细分2,按照城市或者排序
     */
    private void handlerOrderByPriorityCityOrTime(SearchRequestBuilder responseBuilder,String cities){
        responseBuilder.addSort("priority", SortOrder.ASC);
        if (!StringUtils.isEmpty(cities) && !"全国".equals(cities)) {
            SortBuilder builder = new ScriptSortBuilder(this.buildScriptSort(cities, 1), "number");
            builder.order(SortOrder.DESC);
            responseBuilder.addSort(builder);
        } else {
            responseBuilder.addSort("update_time", SortOrder.DESC);
        }
        responseBuilder.addSort("id",SortOrder.DESC);
    }
    /*
        继续对排序进行细分3,按照城市或者得分排序
         */
    private void handlerOrderByCityOrScore(SearchRequestBuilder responseBuilder,String cities){
        if (!StringUtils.isEmpty(cities) && !"全国".equals(cities)) {
            SortBuilder builder = new ScriptSortBuilder(this.buildScriptSort(cities, 0), "number");
            builder.order(SortOrder.DESC);
            responseBuilder.addSort(builder);
        } else {
            responseBuilder.addSort("_score", SortOrder.DESC);
        }
        responseBuilder.addSort("id",SortOrder.DESC);
    }
    /*
   继续对排序进行细分4,按照城市或者排序
    */
    private void handlerOrderByPriorityCityOrTimeOrStatus(SearchRequestBuilder responseBuilder,String cities){
        responseBuilder.addSort("status", SortOrder.ASC);
        responseBuilder.addSort("priority", SortOrder.ASC);
        responseBuilder.addSort("_score", SortOrder.DESC);
        if (!StringUtils.isEmpty(cities) && !"全国".equals(cities)) {
            SortBuilder builder = new ScriptSortBuilder(this.buildScriptSort(cities, 1), "number");
            builder.order(SortOrder.DESC);
            responseBuilder.addSort(builder);
        } else {
            responseBuilder.addSort("update_time", SortOrder.DESC);
        }
        responseBuilder.addSort("id",SortOrder.DESC);
    }
    /*
      按照被命中的城市是否是全国。来重新处理顺序问题，只有全国的，或者是全国命中的沉底
     */
    private Script buildScriptSort(String fieldValue, int flag ){
        StringBuffer sb=new StringBuffer();
        sb.append("double score=0 ;");
        if(flag==1) {
            sb.append("value=doc['update_time'].value;if(value){score=value};");
        }else{
            sb.append("value=_score;if(value){score=value};");
        }
        sb.append("city=_source['city'];flag=doc['city_flag'].value;if(flag==1){score=score/100;}else{ if(city&&");
        String []values=fieldValue.split(",");
        for(int i=0;i<values.length;i++){
            if("全国".equals(values[i])){
                continue;
            }

            sb.append("!city.contains('"+values[i]+"')&&");
        }
        sb.deleteCharAt(sb.lastIndexOf("&"));
        sb.deleteCharAt(sb.lastIndexOf("&"));
        sb.append("){score=score/100;}};return score");
        String scripts=sb.toString();
        Script script=new Script(scripts);
        return script;
    }


    @CounterIface
    public Response updateposition(String position, int id) throws TException {

        String idx = "" + id;
        TransportClient client = null;
        try {
            client=searchUtil.getEsClient();
            IndexResponse response = client.prepareIndex("index", "fulltext", idx).setSource(position).execute().actionGet();
        } catch (Exception e) {
            logger.error("error in update", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } catch (Error error) {
            logger.error(error.getMessage());
        }

        return ResponseUtils.success("");
    }


    /**
     * 更新员工积分
     *
     * @param employeeIds
     * @return
     * @throws TException
     */
    public Response updateEmployeeAwards(List<Integer> employeeIds) throws TException {

        TransportClient client = null;
        BulkRequestBuilder bulkRequest = null;
        if (employeeIds != null && employeeIds.size() > 0) {
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(new Condition(UserEmployee.USER_EMPLOYEE.ID.getName(), employeeIds, ValueOp.IN));
            // 查询员工信息
            List<UserEmployeeDO> userEmployeeDOList = userEmployeeDao.getDatas(queryBuilder.buildQuery());

            // 查询员工公司信息
            List<Integer> companyId = new ArrayList<>();
            // 员工基本信息
            List<Integer> userId = new ArrayList<>();
            userEmployeeDOList.forEach(userEmployeeDO -> {
                companyId.add(userEmployeeDO.getCompanyId());
                userId.add(userEmployeeDO.getSysuserId());
            });
            queryBuilder.clear();
            queryBuilder.where(new Condition(HrCompany.HR_COMPANY.ID.getName(), companyId, ValueOp.IN));
            List<HrCompanyDO> hrCompanyDOS = hrCompanyDao.getDatas(queryBuilder.buildQuery());
            Map companyMap = new HashMap<Integer, HrCompanyDO>();
            companyMap.putAll(hrCompanyDOS.stream().collect(Collectors.toMap(HrCompanyDO::getId, Function.identity())));

            Map userUerMap = new HashMap<Integer, UserUserDO>();
            queryBuilder.clear();
            queryBuilder.where(new Condition(UserUser.USER_USER.ID.getName(), userId, ValueOp.IN));
            List<UserUserDO> userUserDOS = userUserDao.getDatas(queryBuilder.buildQuery());
            userUerMap.putAll(userUserDOS.stream().collect(Collectors.toMap(UserUserDO::getId, Function.identity())));
            try {
                // 连接ES
                client=searchUtil.getEsClient();
                bulkRequest = client.prepareBulk();
                // 更新数据
                for (UserEmployeeDO userEmployeeDO : userEmployeeDOList) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", userEmployeeDO.getId());
                    jsonObject.put("company_id", userEmployeeDO.getCompanyId());
                    jsonObject.put("binding_time", userEmployeeDO.getBindingTime() != null ? LocalDateTime.parse(userEmployeeDO.getBindingTime(), java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : userEmployeeDO.getBindingTime());
                    jsonObject.put("custom_field", userEmployeeDO.getCustomField());
                    jsonObject.put("custom_field_values", userEmployeeDO.getCustomFieldValues());
                    jsonObject.put("sex", String.valueOf(new Double(userEmployeeDO.getSex()).intValue()));
                    jsonObject.put("mobile", String.valueOf(userEmployeeDO.getMobile()));
                    jsonObject.put("email_isvalid", String.valueOf(userEmployeeDO.getEmailIsvalid()));
                    jsonObject.put("idcard", userEmployeeDO.getIdcard());
                    jsonObject.put("download_token", userEmployeeDO.getDownloadToken());
                    jsonObject.put("groupname", userEmployeeDO.getGroupname());
                    jsonObject.put("sysuser_id", userEmployeeDO.getSysuserId());
                    jsonObject.put("education", userEmployeeDO.getEducation());
                    jsonObject.put("auth_level", userEmployeeDO.getAuthLevel());
                    jsonObject.put("companybody", userEmployeeDO.getCompanybody());
                    jsonObject.put("role_id", userEmployeeDO.getRoleId());
                    jsonObject.put("source", userEmployeeDO.getSource());
                    jsonObject.put("hr_wxuser_id", userEmployeeDO.getWxuserId());
                    jsonObject.put("managername", userEmployeeDO.getManagername());
                    jsonObject.put("status", userEmployeeDO.getStatus());
                    jsonObject.put("is_rp_sent", userEmployeeDO.getIsRpSent());
                    jsonObject.put("activation", userEmployeeDO.getActivation());
                    jsonObject.put("retiredate", userEmployeeDO.getRetiredate());
                    jsonObject.put("login_count", userEmployeeDO.getLoginCount());
                    jsonObject.put("section_id", userEmployeeDO.getSectionId());
                    jsonObject.put("birthday", userEmployeeDO.getBirthday());
                    jsonObject.put("is_admin", userEmployeeDO.getIsAdmin());
                    jsonObject.put("address", userEmployeeDO.getAddress());
                    jsonObject.put("register_ip", userEmployeeDO.getRegisterIp());
                    jsonObject.put("auth_method", userEmployeeDO.getAuthMethod());
                    jsonObject.put("employdate", userEmployeeDO.getEmploydate());
                    jsonObject.put("last_login_ip", userEmployeeDO.getLastLoginIp());
                    jsonObject.put("position", userEmployeeDO.getPosition());
                    jsonObject.put("position_id", userEmployeeDO.getPositionId());
                    jsonObject.put("bonus",userEmployeeDO.getBonus());
                    // 取年积分
                    List<EmployeePointsRecordPojo> listYear = userEmployeePointsDao.getAwardByYear(userEmployeeDO.getId());
                    // 取季度积分
                    List<EmployeePointsRecordPojo> listQuarter = userEmployeePointsDao.getAwardByQuarter(userEmployeeDO.getId());
                    // 取月积分
                    List<EmployeePointsRecordPojo> listMonth = userEmployeePointsDao.getAwardByMonth(userEmployeeDO.getId());
                    JSONObject awards = new JSONObject();
                    getAwards(awards, listYear);
                    getAwards(awards, listQuarter);
                    getAwards(awards, listMonth);
                    jsonObject.put("awards", awards);
                    // 积分信息
                    if (companyMap.containsKey(userEmployeeDO.getCompanyId())) {
                        HrCompanyDO hrCompanyDO = (HrCompanyDO) companyMap.get(userEmployeeDO.getCompanyId());
                        jsonObject.put("company_name", hrCompanyDO.getName());
                    }

                    if (userUerMap.containsKey(userEmployeeDO.getSysuserId())) {
                        UserUserDO userUserDO = (UserUserDO) userUerMap.get(userEmployeeDO.getSysuserId());
                        jsonObject.put("nickname", userUserDO.getUsername());
                    }

                    jsonObject.put("ename", userEmployeeDO.getEname());
                    jsonObject.put("cfname", userEmployeeDO.getCfname());
                    jsonObject.put("efname", userEmployeeDO.getEfname());
                    jsonObject.put("award", userEmployeeDO.getAward());
                    jsonObject.put("cname", userEmployeeDO.getCname());


                    jsonObject.put("update_time", LocalDateTime.parse(userEmployeeDO.getUpdateTime(), java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    jsonObject.put("create_time", LocalDateTime.parse(userEmployeeDO.getCreateTime(), java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                    logger.info(JSONObject.toJSONString(jsonObject));
                    bulkRequest.add(
                            client.prepareIndex("awards", "award", userEmployeeDO.getId() + "")
                                    .setSource(jsonObject)
                    );
                }
                BulkResponse bulkResponse = bulkRequest.execute().actionGet();
                logger.info(bulkResponse.buildFailureMessage());
                logger.info(bulkResponse.toString());
                if (bulkResponse.buildFailureMessage() != null) {
                    return ResponseUtils.fail(9999, bulkResponse.buildFailureMessage());
                }
            } catch (Exception e) {
                logger.error("error in update", e);
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
            } catch (Error error) {
                logger.error(error.getMessage());

            }
        }
        return ResponseUtils.success("");
    }

    /**
     * 删除员工积分索引
     *
     * @param employeeIds
     * @return
     * @throws TException
     */
    public Response deleteEmployeeDO(List<Integer> employeeIds) throws TException {
        TransportClient client = null;
        BulkRequestBuilder bulkRequest = null;
        BulkResponse bulkResponse = null;
        try {
            // 连接ES
            client=searchUtil.getEsClient();
            bulkRequest = client.prepareBulk();
            if (employeeIds != null && employeeIds.size() > 0) {
                for (Integer id : employeeIds) {
                    bulkRequest.add(client.prepareDelete("awards", "award", id + ""));
                }
            }
            bulkResponse = bulkRequest.execute().actionGet();
            if (bulkResponse.buildFailureMessage() != null) {
                return ResponseUtils.fail(9999, bulkResponse.buildFailureMessage());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

        }
        return ResponseUtils.success("");
    }

    public void getAwards(JSONObject jsonObject, List<EmployeePointsRecordPojo> list) {
        if (list != null && list.size() > 0) {
            for (EmployeePointsRecordPojo employeePointsRecordPojo : list) {
                JSONObject a = new JSONObject();
                a.put("last_update_time", employeePointsRecordPojo.getLast_update_time());
                a.put("award", employeePointsRecordPojo.getAward());
                a.put("timespan", employeePointsRecordPojo.getTimespan());
                jsonObject.put(employeePointsRecordPojo.getTimespan(), a);
            }
        }
    }


    /**
     * todo searchengine-service依赖 common-service，并依赖 SearchengineEntity的buildSrtScript代码
     * @param timspanc
     * @param field
     * @param sortOrder
     * @return
     */
    private SortBuilder buildSortScript(String timspanc, String field, SortOrder sortOrder) {
        StringBuffer sb = new StringBuffer();
        sb.append("double score=0; awards=_source.awards;times=awards['" + timspanc + "'];if(times){award=doc['awards." + timspanc + "." + field + "'].value;if(award){score=award}}; return score");
        String scripts = sb.toString();
        Script script = new Script(scripts);
        ScriptSortBuilder builder = new ScriptSortBuilder(script, "number").order(sortOrder);
        return builder;
    }

    /**
     * 查找制定用户积分
     * @param searchClient
     * @param companyIds 公司列表
     * @param employeeId 员工编号
     * @param activation 是否激活
     * @param pageSize 每页数量
     * @param from 榜单位置  从0开始
     * @param timespan 时间跨度--月、季、年
     * @return
     */
    private SearchRequestBuilder getSearchRequestBuilder(TransportClient searchClient, List<Integer> companyIds,
                                                         Integer employeeId, String activation, int pageSize,
                                                         int from, String timespan) {
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        searchUtil.handleTerms(Arrays.toString(companyIds.toArray()).replaceAll("\\[|\\]| ", ""), query, "company_id");
        searchUtil.handleTerms(timespan, query, "awards." + timespan + ".timespan");
        if (activation != null) {
            searchUtil.handleTerms(activation, query, "activation");
        }
        if (employeeId != null) {
            searchUtil.handleTerms(String.valueOf(employeeId), query, "id");
        }
        searchUtil.hanleRange(0, query, "awards." + timespan + ".award");
        SearchRequestBuilder searchRequestBuilder = searchClient.prepareSearch("awards").setTypes("award").setQuery(query)
                .addSort(buildSortScript(timespan, "award", SortOrder.DESC))
                .addSort(buildSortScript(timespan, "last_update_time", SortOrder.ASC))
                .setFetchSource(new String[]{"id", "awards." + timespan + ".award", "awards." + timespan + ".last_update_time"}, null);
        if (from > 0 && pageSize > 0) {
            searchRequestBuilder.setSize(pageSize).setFrom(from);
        }
        logger.info(searchRequestBuilder.toString());
        return searchRequestBuilder;
    }


    private SearchRequestBuilder getSearchRequestBuilder(TransportClient searchClient, List<Integer> companyIds, Integer employeeId, String activation, int pageSize, int pageNum, String timespan, String keyword) {
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        searchUtil.handleTerms(Arrays.toString(companyIds.toArray()).replaceAll("\\[|\\]| ", ""), query, "company_id");
        if (activation != null) {
            searchUtil.handleTerms(activation, query, "activation");
        }
        if (employeeId != null) {
            searchUtil.handleTerms(String.valueOf(employeeId), query, "id");
        }

        EmployeeBizTool.addKeywords(defaultquery, keyword, searchUtil);
        SearchRequestBuilder searchRequestBuilder = searchClient.prepareSearch("awards").setTypes("award").setQuery(query)
                .addSort("activation", SortOrder.ASC)
                .addSort(buildSortScript(timespan, "award", SortOrder.DESC))
                .addSort(buildSortScript(timespan, "last_update_time", SortOrder.ASC))
                .addSort("binding_time", SortOrder.DESC)
                .setFetchSource(new String[]{"id", "awards." + timespan + ".award", "awards." + timespan + ".last_update_time"}, null);
        if (pageNum > 0 && pageSize > 0) {
            searchRequestBuilder.setSize(pageSize).setFrom((pageNum - 1) * pageSize);
        }
        logger.info(searchRequestBuilder.toString());
        return searchRequestBuilder;
    }

    private AbstractAggregationBuilder handleAggScale(String nodeName, int award, long lastUpdateTime) {
        StringBuffer sb = new StringBuffer();
        sb.append("int i = 0;");
        sb.append(String.format("awardVal = doc['%s.award'].value; lastUpTimeVal = doc['%s.last_update_time'].value;", nodeName, nodeName));
        sb.append(String.format("if(awardVal > %d) {i = i+1}", award));
        sb.append(String.format("else if(awardVal == %d && lastUpTimeVal < %d) {i = i+1};", award, lastUpdateTime));
        sb.append("_agg['transactions'].add(i)");
        String mapScript = sb.toString();
        StringBuffer sb1 = new StringBuffer();
        sb1.append("profit = 0; for (t in _agg.transactions) { profit += t }; return profit");
        String combinScript = sb1.toString();
        StringBuffer sb2 = new StringBuffer();
        sb2.append("profit = 0; for (a in _aggs) { profit += a }; return profit");
        String reduceScript = sb2.toString();
        MetricsAggregationBuilder build = AggregationBuilders.scriptedMetric("ranking")
                .initScript(new Script("_agg['transactions'] = []"))
                .mapScript(new Script(mapScript))
                .reduceScript(new Script(reduceScript))
                .combineScript(new Script(combinScript));
        return build;
    }
    @CounterIface
    public Response queryAwardRanking(List<Integer> companyIds, String timespan, int pageSize, int pageNum, String keyword, int filter) {
        Map<String, Object> object = new HashMap<>();
        TransportClient searchClient =null;
        try {
            searchClient=searchUtil.getEsClient();
            StringBuffer activation = new StringBuffer();
            if (filter == 0) {
                activation.append("");
            } else if (filter == 1) {
                activation.append("0");
            } else if (filter == 2) {
                activation.append("1,2,3,4");
            }
            SearchRequestBuilder searchRequestBuilder = getSearchRequestBuilder(searchClient, companyIds, null, activation.toString(), pageSize, pageNum, timespan, keyword);
            SearchResponse response = searchRequestBuilder.execute().actionGet();
            List<Map<String, Object>> data = new ArrayList<>();
            object.put("total", response.getHits().getTotalHits());
            for (SearchHit searchHit : response.getHits().getHits()) {
                Map<String, Object> objectMap = new HashMap<>();
                JSONObject jsonObject = JSON.parseObject(searchHit.getSourceAsString());
                objectMap.put("employeeId", jsonObject.remove("id"));
                if (jsonObject.containsKey("awards") && jsonObject.getJSONObject("awards").containsKey(timespan)) {
                    objectMap.put("award", jsonObject.getJSONObject("awards").getJSONObject(timespan).getIntValue("award"));
                } else {
                    objectMap.put("award", 0);
                }
                data.add(objectMap);
            }
            logger.info("==================================");
            logger.info("total ======="+response.getHits().getTotalHits());
            logger.info("==================================");
            object.put("data", data);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
        return ResponseUtils.success(object);
    }

    public Response fetchEmployees(List<Integer> companyIds, String keywords, int filter, String order, String asc,
                                   String emailValidate, int pageSize, int pageNumber) throws SearchEngineException {
        TransportClient searchClient;
        try {
            searchClient = searchUtil.getEsClient();

            Map<String, Object> result = new HashMap<>();
            QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
            QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
            EmployeeBizTool.addCompanyIds(query, companyIds, searchUtil);
            EmployeeBizTool.addFilter(query, filter, searchUtil);
            EmployeeBizTool.addKeywords(query, keywords, searchUtil);
            EmployeeBizTool.addEmailValidate(query, emailValidate, searchUtil);
            SearchRequestBuilder searchRequestBuilder = searchClient.prepareSearch("awards").setTypes("award").setQuery(query);
            EmployeeBizTool.addOrder(searchRequestBuilder, order, asc);
            EmployeeBizTool.addPagination(searchRequestBuilder, pageNumber, pageSize);
            SearchResponse response = searchRequestBuilder.execute().actionGet();
            List<Map<String, Object>> data = new ArrayList<>();
            result.put("total", response.getHits().getTotalHits());
            for (SearchHit searchHit : response.getHits().getHits()) {
                JSONObject jsonObject = JSON.parseObject(searchHit.getSourceAsString());
                data.add(jsonObject);
            }
            logger.info("==================================");
            logger.info("total ======="+response.getHits().getTotalHits());
            logger.info("==================================");
            result.put("data", data);
            return ResponseUtils.success(result);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response queryAwardRankingInWx(List<Integer> companyIds, String timespan, Integer employeeId) {

        return queryLeaderBoard(companyIds, timespan, employeeId, 0, 20);
    }

    public Response listLeaderBoard(List<Integer> companyIds, String timespan, int employeeId, int pageNum,
                                    int pageSize) {
        return queryLeaderBoard(companyIds, timespan, employeeId, pageNum, pageSize);
    }

    public int countLeaderBoard(List<Integer> companyIds, String timeSpan) {
        TransportClient searchClient =null;
        try {
            searchClient =searchUtil.getEsClient();

            QueryBuilder defaultQuery = QueryBuilders.matchAllQuery();
            QueryBuilder query = QueryBuilders.boolQuery().must(defaultQuery);

            QueryBuilder awardQuery = QueryBuilders.rangeQuery("awards." + timeSpan + ".award")
                    .gt(0);
            ((BoolQueryBuilder) query).must(awardQuery);

            QueryBuilder companyIdListQueryBuild = QueryBuilders.termsQuery("company_id", companyIds);
            ((BoolQueryBuilder) query).must(companyIdListQueryBuild);

            QueryBuilder activeEmployeeCondition = QueryBuilders.termQuery("activation", "0");
            ((BoolQueryBuilder) query).must(activeEmployeeCondition);

            try {
                SearchResponse sortResponse = searchClient.prepareSearch("awards").setTypes("award")
                        .setQuery(query)
                        .setSize(0).execute().get();
                int count = (int)sortResponse.getHits().getTotalHits();
                logger.info("countLeaderBoard count");
                return count;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return 0;
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return 0;
        }
    }

    /**
     * 查找榜单信息
     * @param companyIds 公司编号集合
     * @param timespan 时间区间
     * @param employeeId 员工编号
     * @param from 榜单指定的位置 从0开始
     * @param pageSize 一次查找的信息数量
     * @return 榜单数据
     */
    private Response queryLeaderBoard(List<Integer> companyIds, String timespan, Integer employeeId, int from,
                                     int pageSize) {
        // 保证插入有序，使用linkedhashMap˚
        Map<Integer, JSONObject> data = new LinkedHashMap<>();
        TransportClient searchClient =null;
        try {
            searchClient =searchUtil.getEsClient();
            // 查找所有员工的积分排行
            SearchResponse response = getSearchRequestBuilder(searchClient, companyIds, null, "0",
                    pageSize, from, timespan).execute().actionGet();
            int index = from+1;
            for (SearchHit searchHit : response.getHits().getHits()) {
                JSONObject jsonObject = JSON.parseObject(searchHit.getSourceAsString());
                if (jsonObject.containsKey("awards") && jsonObject.getJSONObject("awards").containsKey(timespan) && jsonObject.getJSONObject("awards").getJSONObject(timespan).getIntValue("award") > 0) {
                    JSONObject obj = JSON.parseObject("{}");
                    obj.put("employee_id", jsonObject.getIntValue("id"));
                    obj.put("ranking", index++);
                    obj.put("last_update_time", jsonObject.getJSONObject("awards").getJSONObject(timespan).getString("last_update_time"));
                    obj.put("award", jsonObject.getJSONObject("awards").getJSONObject(timespan).getIntValue("award"));
                    data.put(jsonObject.getIntValue("id"), obj);
                }
            }
            // 当前用户在 >= 20 名，显示返回前20条，小于22条返回前20+用户前一名+用户排名+用户后一名，未上榜返回前20条
            List<JSONObject> allRankingList = new ArrayList<>(data.values());
            data = allRankingList
                    .stream()
                    .collect(Collectors.toMap(k -> TypeUtils.castToInt(k.remove("employee_id")),
                            v -> v, (oldKey, newKey) -> newKey));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
        return ResponseUtils.success(data);
    }

    @CounterIface
    public Map<String,Object> getPositionSuggest(Map<String,String> params){
        return searchMethodUtil.suggestPosition(params);
    }

    public Map<String,Object> getProfileSuggest(Map<String,String> params){
        String keyWord=params.get("keyWord");
        String companyId=params.get("company_id");
        String account_type=params.get("account_type");
        String hr_account_id=params.get("hr_account_id");
        if(StringUtils.isBlank(keyWord)&&StringUtils.isBlank(companyId)&&StringUtils.isBlank(hr_account_id)){
            return null;
        }
        String page=params.get("page_from");
        String pageSize=params.get("page_size");

        String flag=params.get("flag");
        String returnParams=params.get("return_params");
        if(StringUtils.isBlank(flag)){
            flag="0";
        }
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
            SearchResponse hits=this.searchProfilePrefix(keyWord,Integer.parseInt(companyId),Integer.parseInt(hr_account_id),Integer.parseInt(account_type),Integer.parseInt(flag),returnParams,Integer.parseInt(page),Integer.parseInt(pageSize),client);
            long hitNum=hits.getHits().getTotalHits();
            if(hitNum==0){
                hits=this.searchProfileQueryString(keyWord,Integer.parseInt(companyId),Integer.parseInt(hr_account_id),Integer.parseInt(account_type),Integer.parseInt(flag),returnParams,Integer.parseInt(page),Integer.parseInt(pageSize),client);
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
    private SearchResponse searchProfilePrefix(String keyWord,int companyId,int hr_account_id, int account_type, int flag, String returnParams,int page,int pageSize,TransportClient client){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        List<String> list=new ArrayList<>();
        list.add("user.profiles.basic.name");
        searchUtil.handleKeyWordForPrefix(keyWord, false, query, list);
        QueryBuilder queryAppScript=this.queryScript(companyId, account_type, hr_account_id);
        if(queryAppScript!=null){
            ((BoolQueryBuilder) query).filter(queryAppScript);
        }
        SearchRequestBuilder responseBuilder=client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_TYPE)
                .setQuery(query)
                .setFrom((page-1)*pageSize)
                .setSize(pageSize)
                .setTrackScores(true);
        searchMethodUtil.handlerReturnParams(returnParams,responseBuilder);
        logger.info(responseBuilder.toString());
        SearchResponse res=responseBuilder.execute().actionGet();
        return res;
    }

    //通过QueryString搜索
    private SearchResponse searchProfileQueryString(String keyWord,int companyId,int hr_account_id, int account_type, int flag, String returnParams,int page,int pageSize,TransportClient client){
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        List<String> list=new ArrayList<>();
        list.add("user.profiles.basic.name");
        searchUtil.handleKeyWordforQueryString(keyWord, false, query, list);
        QueryBuilder queryAppScript=this.queryScript(companyId, account_type, hr_account_id);
        if(queryAppScript!=null){
            ((BoolQueryBuilder) query).filter(queryAppScript);
        }
        SearchRequestBuilder responseBuilder=client.prepareSearch(Constant.ES_INDEX).setTypes(Constant.ES_TYPE)
                .setQuery(query)
                .setFrom((page-1)*pageSize)
                .setSize(pageSize)
                .setTrackScores(true);

        searchMethodUtil.handlerReturnParams(returnParams,responseBuilder);
        logger.info(responseBuilder.toString());
        SearchResponse res=responseBuilder.execute().actionGet();
        return res;
    }




     /*
      使用script的方式组装对application的查询
     */

    public ScriptQueryBuilder queryScript(int company_id, int account_type, int hraccount_id){

        StringBuffer sb=new StringBuffer();
        sb.append("user=_source.user;if(user){applications=user.applications;;origins=user.origin_data;if(applications){for(val in applications){if(");
        if(account_type == 0){
            sb.append("val.company_id == "+company_id);
        }else{
            sb.append("val.publisher == "+hraccount_id);
        }
        sb.append("){return true}}}");
        sb.append("}");

        ScriptQueryBuilder script=new ScriptQueryBuilder(new Script(sb.toString()));
        return script;
    }
}
