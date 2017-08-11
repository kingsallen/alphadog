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
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.ConverTools;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
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
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    public Response query(String keywords, String cities, String industries, String occupations, String scale,
                          String employment_type, String candidate_source, String experience, String degree, String salary,
                          String company_name, int page_from, int page_size, String child_company_name, String department, boolean order_by_priority, String custom) throws TException {

        List listOfid = new ArrayList();

        if (page_from == 0) {
            page_from = 0;
        }
        if (page_size == 0) {
            page_size = 20;
        }
        SearchResponse response = null;
        ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
        try {
            propertiesReader.loadResource("es.properties");
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String cluster_name = propertiesReader.get("es.cluster.name", String.class);
        String es_connection = propertiesReader.get("es.connection", String.class);
        Integer es_port = propertiesReader.get("es.port", Integer.class);

        TransportClient client = null;
        try {

            Settings settings = Settings.settingsBuilder().put("cluster.name", cluster_name)
                    .build();

            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(es_connection), es_port));

            QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
            QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);

            boolean haskey = false;

            if (!StringUtils.isEmpty(keywords)) {
                haskey = true;
                String[] keyword_list = keywords.split(" ");
                QueryBuilder keyand = QueryBuilders.boolQuery();
                for (int i = 0; i < keyword_list.length; i++) {
                    String keyword = keyword_list[i];
                    BoolQueryBuilder keyor = QueryBuilders.boolQuery();
                    QueryBuilder fullf = QueryBuilders.queryStringQuery(keyword)
//                            .field("_all", 1.0f)
                            .field("title", 20.0f)
                            .field("city", 10.0f)
//                            .field("company_name", 5.0f)
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
                    QueryBuilder cityfilter = QueryBuilders.matchPhraseQuery("city", city);
                    QueryBuilder cityboosting = QueryBuilders.boostingQuery()
                            .positive(cityfilter)
                            .negative(QueryBuilders.matchPhraseQuery("title", city)).negativeBoost(0.5f);

                    ((BoolQueryBuilder) cityor).should(cityboosting);
                }
                ((BoolQueryBuilder) query).must(cityor);
            }

            if (!StringUtils.isEmpty(industries)) {
                haskey = true;
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
                    QueryBuilder occupationfilter = QueryBuilders.matchPhraseQuery("occupation", occupation);
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
                QueryBuilder departmentfilter = QueryBuilders.matchPhraseQuery("team_name", department);
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
                    QueryBuilder degreefilter = QueryBuilders.matchPhraseQuery("degree_name", degree_name);
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
                QueryBuilder custom_filter = QueryBuilders.matchPhraseQuery("custom", custom);
                ((BoolQueryBuilder) query).must(custom_filter);
            }

            QueryBuilder status_filter = QueryBuilders.matchPhraseQuery("status", "0");
            ((BoolQueryBuilder) query).must(status_filter);

            if (order_by_priority) {

                if (haskey) {

                    response = client.prepareSearch("index").setTypes("fulltext")
                            .setQuery(query)
                            .addSort("priority", SortOrder.ASC)
                            .addSort("_score", SortOrder.DESC)
                            .setFrom(page_from)
                            .setSize(page_size).execute().actionGet();
                } else {
                    response = client.prepareSearch("index").setTypes("fulltext")
                            .setQuery(query)
                            .addSort("priority", SortOrder.ASC)
                            .addSort("update_time", SortOrder.DESC)
                            .setFrom(page_from)
                            .setSize(page_size).execute().actionGet();
                }

            } else {
                response = client.prepareSearch("index").setTypes("fulltext")
                        .setQuery(query)
                        .addSort("_score", SortOrder.DESC)
                        .setFrom(page_from)
                        .setSize(page_size).execute().actionGet();
            }

            for (SearchHit hit : response.getHits()) {
                //Handle the hit...
                String id = ConverTools.converToString(hit.getSource().get("id"));
                listOfid.add(id);
            }

        } catch (Exception e) {
            logger.error("error in search", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } finally {
            client.close();
        }

        Map<String, List> res = new HashMap<String, List>();
        res.put("jd_id_list", listOfid);

        return ResponseUtils.success(res);

    }


    public Response updateposition(String position, int id) throws TException {
        ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
        try {
            propertiesReader.loadResource("es.properties");
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String cluster_name = propertiesReader.get("es.cluster.name", String.class);
        logger.info(cluster_name);
        String es_connection = propertiesReader.get("es.connection", String.class);
        Integer es_port = propertiesReader.get("es.port", Integer.class);
        Settings settings = Settings.settingsBuilder().put("cluster.name", cluster_name)
                .build();
        String idx = "" + id;
        TransportClient client = null;
        try {
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(es_connection), es_port));
            IndexResponse response = client.prepareIndex("index", "fulltext", idx)
                    .setSource(position)
                    .execute()
                    .actionGet();
        } catch (UnknownHostException e) {
            logger.error("error in update", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } catch (Error error) {
            logger.error(error.getMessage());
        } finally {
            client.close();
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
        ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
        try {
            propertiesReader.loadResource("es.properties");
        } catch (Exception e1) {
            logger.error(e1.getMessage());
        }
        String cluster_name = propertiesReader.get("es.cluster.name", String.class);
        logger.info(cluster_name);
        String es_connection = propertiesReader.get("es.connection", String.class);
        Integer es_port = propertiesReader.get("es.port", Integer.class);
        Settings settings = Settings.settingsBuilder().put("cluster.name", cluster_name)
                .build();
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
                client = TransportClient.builder().settings(settings).build()
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(es_connection), es_port));
                bulkRequest = client.prepareBulk();
                // 更新数据
                for (UserEmployeeDO userEmployeeDO : userEmployeeDOList) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", userEmployeeDO.getId());
                    jsonObject.put("company_id", userEmployeeDO.getCompanyId());
                    jsonObject.put("binding_time", userEmployeeDO.getBindingTime());
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

                    jsonObject.put("update_time", DateUtils.shortTimeToDate(userEmployeeDO.getUpdateTime()));
                    jsonObject.put("create_time", DateUtils.shortTimeToDate(userEmployeeDO.getCreateTime()));

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
            } catch (UnknownHostException e) {
                logger.error("error in update", e);
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
            } catch (Error error) {
                logger.error(error.getMessage());
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                client.close();
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
        ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
        try {
            propertiesReader.loadResource("es.properties");
        } catch (Exception e1) {
            logger.error(e1.getMessage());
        }
        String cluster_name = propertiesReader.get("es.cluster.name", String.class);
        logger.info(cluster_name);
        String es_connection = propertiesReader.get("es.connection", String.class);
        Integer es_port = propertiesReader.get("es.port", Integer.class);
        Settings settings = Settings.settingsBuilder().put("cluster.name", cluster_name)
                .build();
        TransportClient client = null;
        BulkRequestBuilder bulkRequest = null;
        BulkResponse bulkResponse = null;
        try {
            // 连接ES
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(es_connection), es_port));
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
            e.printStackTrace();
        } finally {
            client.close();
        }

        return ResponseUtils.success("");
    }

    public void getAwards(JSONObject jsonObject, List<EmployeePointsRecordPojo> list) {
        if (list != null && list.size() > 0) {
            for (EmployeePointsRecordPojo employeePointsRecordPojo : list) {
                JSONObject a = new JSONObject();
                a.put("last_update_time", employeePointsRecordPojo.getLast_update_time());
                a.put("award", employeePointsRecordPojo.getAward());
                jsonObject.put(employeePointsRecordPojo.getTimespan(), a);
            }
        }
    }

    private SortBuilder buildSortScript(String timspanc, String field, SortOrder sortOrder) {
        StringBuffer sb = new StringBuffer();
        sb.append("double score=0; awards=_source.awards;times=awards['" + timspanc + "'];if(times){award=doc['awards." + timspanc + "." + field + "'].value;if(award){score=award}}; return score");
        String scripts = sb.toString();
        Script script = new Script(scripts);
        ScriptSortBuilder builder = new ScriptSortBuilder(script, "number").order(sortOrder);
        return builder;
    }

    private SearchRequestBuilder getSearchRequestBuilder(TransportClient searchClient, List<Integer> companyIds, Integer employeeId, String activation, int pageSize, int pageNum, String timespan) {
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

        SearchRequestBuilder searchRequestBuilder = searchClient.prepareSearch("awards").setTypes("award").setQuery(query)
                .addSort(buildSortScript(timespan, "award", SortOrder.DESC))
                .addSort(buildSortScript(timespan, "last_update_time", SortOrder.ASC))
                .setFetchSource(new String[]{"id", "awards." + timespan + ".award", "awards." + timespan + ".last_update_time"}, null);
        if (pageNum > 0 && pageSize > 0) {
            searchRequestBuilder.setSize(pageSize).setFrom((pageNum - 1) * pageSize);
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

        if (StringUtils.isNotEmpty(keyword)) {
            Map map = new HashMap();
            map.put("email", keyword);
            map.put("mobile", keyword);
            map.put("nickname", keyword);
            map.put("custom_field", keyword);
            searchUtil.shouldQuery(map, query);
        }



        SearchRequestBuilder searchRequestBuilder = searchClient.prepareSearch("awards").setTypes("award").setQuery(query)
                .addSort(buildSortScript(timespan, "award", SortOrder.DESC))
                .addSort("activation", SortOrder.ASC)
                .addSort(buildSortScript(timespan, "last_update_time", SortOrder.ASC))
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

    public Response queryAwardRanking(List<Integer> companyIds, String timespan, int pageSize, int pageNum, String keyword, int filter) {
        Map<String, Object> object = new HashMap<>();
        try (TransportClient searchClient = searchUtil.getEsClient()) {
            StringBuffer activation = new StringBuffer();
            if (filter == 0) {
                activation.append("");
            } else if (filter == 1) {
                activation.append("0");
            } else if (filter == 2) {
                activation.append("1");
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
            object.put("data", data);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
        return ResponseUtils.success(object);
    }

    public Response queryAwardRankingInWx(List<Integer> companyIds, String timespan, Integer employeeId) {
        // 保证插入有序，使用linkedhashMap˚
        Map<Integer, JSONObject> data = new LinkedHashMap<>();
        try (TransportClient searchClient = searchUtil.getEsClient()) {
            // 查找所有员工的积分排行
            SearchResponse response = getSearchRequestBuilder(searchClient, companyIds, null, "0", 20, 1, timespan).execute().actionGet();
            int index = 1;
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
            List<JSONObject> resultList = new ArrayList<>(23);
            if ((!data.isEmpty() && data.containsKey(employeeId)) || (employeeId == null || employeeId == 0)) {
                resultList = allRankingList.subList(0, allRankingList.size() >= 20 ? 20 : allRankingList.size());
            } else {
                // 默认查询前20条
                resultList = allRankingList.subList(0, allRankingList.size() >= 20 ? 20 : allRankingList.size());
                SearchResponse hitEmployee = getSearchRequestBuilder(searchClient, companyIds, employeeId, "0", 0, 0, timespan).execute().actionGet();
                if (hitEmployee != null && hitEmployee.getHits().getHits().length > 0) {
                    JSONObject employeeJson = JSONObject.parseObject(hitEmployee.getHits().getHits()[0].getSourceAsString()).getJSONObject("awards");
                    if (employeeJson.containsKey(timespan) && employeeJson.getJSONObject(timespan).getIntValue("award") != 0 && employeeJson.getJSONObject(timespan).getString("last_update_time") != null) {
                        // 获取用户名次
                        int employeeAward = employeeJson.getJSONObject(timespan).getIntValue("award");
                        long lastUpdateTime = LocalDateTime.parse(employeeJson.getJSONObject(timespan).getString("last_update_time")).toInstant(ZoneOffset.UTC).toEpochMilli();
                        SearchRequestBuilder builder = getSearchRequestBuilder(searchClient, companyIds, null, "0", 0, 0, timespan).setSize(0)
                                .addAggregation(handleAggScale("awards." + timespan, employeeAward, lastUpdateTime));
                        logger.info(builder.toString());
                        SearchResponse hitEmployeeRanking = builder.execute().actionGet();
                        if (hitEmployeeRanking != null) {
                            int ranking = (int) hitEmployeeRanking.getAggregations().asMap().get("ranking").getProperty("value");
                            SearchHits hits = getSearchRequestBuilder(searchClient, companyIds, null, "0", 0, 0, timespan).setFrom(ranking).setSize(3).execute().actionGet().getHits();
                            for (SearchHit searchHit : hits.getHits()) {
                                JSONObject jsonObject = JSON.parseObject(searchHit.getSourceAsString());
                                JSONObject obj = JSON.parseObject("{}");
                                obj.put("employee_id", jsonObject.getIntValue("id"));
                                obj.put("ranking", ++ranking);
                                obj.put("last_update_time", jsonObject.getJSONObject("awards").getJSONObject(timespan).getString("last_update_time"));
                                obj.put("award", jsonObject.getJSONObject("awards").getJSONObject(timespan).getIntValue("award"));
                                resultList.add(obj);
                            }
                        }
                    }
                }
            }
            data = resultList.stream().collect(Collectors.toMap(k -> TypeUtils.castToInt(k.remove("employee_id")), v -> v));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
        return ResponseUtils.success(data);
    }

}
