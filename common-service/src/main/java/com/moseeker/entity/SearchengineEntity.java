package com.moseeker.entity;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeePointsDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecord;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.db.userdb.tables.UserWxUser;
import com.moseeker.baseorm.pojo.EmployeePointsRecordPojo;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.EsClientInstance;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.pojo.searchengine.EmployeeAwards;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeePointsRecordDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import org.apache.thrift.TException;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

/**
 * Created by YYF
 *
 * Date: 2017/8/11
 *
 * Project_name :alphadog
 */
@Service
@CounterIface
public class SearchengineEntity {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserEmployeeDao userEmployeeDao;

    @Autowired
    private HrCompanyDao hrCompanyDao;

    @Autowired
    private UserUserDao userUserDao;

    @Autowired
    private UserEmployeePointsDao userEmployeePointsDao;

    @Autowired
    private UserWxUserDao userWxUserDao;

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取ES连接
     *
     * @return
     */
    private TransportClient getTransportClient() {
        return EsClientInstance.getClient();
    }

    /**
     * 更新员工积分
     *
     * @param userEmployeeDOList
     * @return
     * @throws TException
     */
    public Response updateEmployeeDOAwards(List<UserEmployeeDO> userEmployeeDOList) throws CommonException {
        logger.info("----开始更新员工积分信息-------");
        TransportClient client = null;
        BulkRequestBuilder bulkRequest = null;
        if (userEmployeeDOList != null && userEmployeeDOList.size() > 0) {
            // 查询员工公司信息
            List<Integer> companyId = new ArrayList<>();
            // 员工基本信息
            List<Integer> userId = new ArrayList<>();
            userEmployeeDOList.forEach(userEmployeeDO -> {
                companyId.add(userEmployeeDO.getCompanyId());
                userId.add(userEmployeeDO.getSysuserId());
            });
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
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
                client =this.getTransportClient();
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
                    jsonObject.put("activation", new Double(userEmployeeDO.getActivation()).intValue());
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
                    jsonObject.put("position_id", userEmployeeDO.getPositionId());
                    jsonObject.put("position", userEmployeeDO.getPosition());
                    jsonObject.put("bonus", userEmployeeDO.getBonus());

                    if (org.apache.commons.lang3.StringUtils.isNotBlank(userEmployeeDO.getUnbindTime())) {
                        jsonObject.put("unbind_time", userEmployeeDO.getUnbindTime());
                        jsonObject.put("unbind_time_long", LocalDateTime.parse(userEmployeeDO.getUnbindTime(), dtf).toInstant(ZoneOffset.of("+8")).toEpochMilli());
                    }
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(userEmployeeDO.getImportTime())) {
                        jsonObject.put("import_time", userEmployeeDO.getImportTime());
                        jsonObject.put("import_time_long", LocalDateTime.parse(userEmployeeDO.getImportTime(), dtf).toInstant(ZoneOffset.of("+8")).toEpochMilli());
                    }

                    JSONObject searchData = new JSONObject();
                    searchData.put("email", "");
                    if(StringUtils.isNotNullOrEmpty(userEmployeeDO.getEmail())) {
                        searchData.put("email", userEmployeeDO.getEmail().toLowerCase());
                    }
                    searchData.put("mobile",String.valueOf(userEmployeeDO.getMobile()));
                    searchData.put("custom_field", "");
                    if(StringUtils.isNotNullOrEmpty(userEmployeeDO.getCustomField())) {
                        searchData.put("custom_field", userEmployeeDO.getCustomField().toLowerCase());
                    }
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
                    // userdb.useruser.name > userdb.useruser.nickname > userdb.userwxuser.nickname
                    if (userUerMap.containsKey(userEmployeeDO.getSysuserId())) {
                        UserUserDO userUserDO = (UserUserDO) userUerMap.get(userEmployeeDO.getSysuserId());
                        if (userUserDO.getName() != null) {
                            jsonObject.put("nickname", userUserDO.getName());
                            searchData.put("nickname", userUserDO.getName().toLowerCase());
                        } else if (userUserDO.getName() == null && userUserDO.getNickname() != null) {
                            jsonObject.put("nickname", userUserDO.getNickname());
                            searchData.put("nickname", userUserDO.getNickname().toLowerCase());
                        }
                    }
                    if (jsonObject.get("nickname") == null) {
                        queryBuilder.clear();
                        queryBuilder.where(UserWxUser.USER_WX_USER.SYSUSER_ID.getName(), userEmployeeDO.getSysuserId());
                        UserWxUserDO userWxUserDO = userWxUserDao.getData(queryBuilder.buildQuery());
                        if (userWxUserDO != null) {
                            jsonObject.put("nickname", userWxUserDO.getNickname());
                            searchData.put("nickname", userWxUserDO.getNickname().toLowerCase());
                        }
                    }
                    jsonObject.put("ename", userEmployeeDO.getEname());
                    jsonObject.put("cfname", userEmployeeDO.getCfname());
                    jsonObject.put("efname", userEmployeeDO.getEfname());
                    jsonObject.put("award", userEmployeeDO.getAward());
                    jsonObject.put("email", userEmployeeDO.getEmail());
                    jsonObject.put("cname", userEmployeeDO.getCname());
                    searchData.put("cname", "");
                    if(StringUtils.isNotNullOrEmpty(userEmployeeDO.getCname())) {
                        searchData.put("cname", userEmployeeDO.getCname().toLowerCase());
                    }
                    jsonObject.put("disable", userEmployeeDO.getDisable());
                    jsonObject.put("search_data", searchData);

                    jsonObject.put("update_time", LocalDateTime.parse(userEmployeeDO.getUpdateTime(), java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    jsonObject.put("create_time", LocalDateTime.parse(userEmployeeDO.getCreateTime(), java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                    logger.info(JSONObject.toJSONString(jsonObject));
                    bulkRequest.add(
                            client.prepareIndex("awards", "award", userEmployeeDO.getId() + "")
                                    .setSource(jsonObject)
                    );
                }
                BulkResponse bulkResponse = bulkRequest.execute().actionGet();
                logger.info("------更新员工积分信息结束-------");
                logger.info("bulkResponse.buildFailureMessage():{}", bulkResponse.buildFailureMessage());
                logger.info("bulkResponse.toString():" + bulkResponse.toString());
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
     * 全量更新员工积分
     *
     * @param employeeIds
     * @return
     * @throws TException
     */
    public Response updateEmployeeAwards(List<Integer> employeeIds) throws CommonException {
        logger.info("SearchengineEntity updateEmployeeAwards employeeIds:{}", JSONObject.toJSONString(employeeIds));
        // 连接ES
        TransportClient client = this.getTransportClient();
        if (client == null) {
            return ResponseUtils.fail(9999, "ES 连接失败！");
        }
        BulkRequestBuilder bulkRequest = null;
        if (employeeIds != null && employeeIds.size() > 0) {
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(new Condition(UserEmployee.USER_EMPLOYEE.ID.getName(), employeeIds, ValueOp.IN));
            // 查询员工信息
            List<UserEmployeeDO> userEmployeeDOList = userEmployeeDao.getDatas(queryBuilder.buildQuery());

            if (userEmployeeDOList == null || userEmployeeDOList.isEmpty()) {
                logger.error("未查到员工数据, employeeIds:{}", employeeIds);
                return ResponseUtils.success("");
            }

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
                bulkRequest = client.prepareBulk();
                // 更新数据
                for (UserEmployeeDO userEmployeeDO : userEmployeeDOList) {
                    logger.info("SearchengineEntity updateEmployeeAwards userEmployeeDO:{}", userEmployeeDO);
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
                    jsonObject.put("groupname", userEmployeeDO.getGroupname());
                    jsonObject.put("download_token", userEmployeeDO.getDownloadToken());
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
                    jsonObject.put("activation", new Double(userEmployeeDO.getActivation()).intValue());
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
                    jsonObject.put("position", userEmployeeDO.getPosition());
                    jsonObject.put("bonus", userEmployeeDO.getBonus());
                    logger.info("SearchengineEntity updateEmployeeAwards unbind_time:{}", userEmployeeDO.getUnbindTime());
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(userEmployeeDO.getUnbindTime())) {
                        jsonObject.put("unbind_time", userEmployeeDO.getUnbindTime());
                        logger.info("SearchengineEntity updateEmployeeAwards unbind_time:{}", jsonObject.get("unbind_time"));
                        LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
                        jsonObject.put("unbind_time_long", LocalDateTime.parse(userEmployeeDO.getUnbindTime(), dtf).toInstant(ZoneOffset.of("+8")).toEpochMilli());
                        logger.info("SearchengineEntity updateEmployeeAwards unbind_time_long:{}", jsonObject.get("unbind_time_long"));
                    }
                    logger.info("SearchengineEntity updateEmployeeAwards import_time:{}", userEmployeeDO.getImportTime());
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(userEmployeeDO.getImportTime())) {
                        jsonObject.put("import_time", userEmployeeDO.getImportTime());
                        logger.info("SearchengineEntity updateEmployeeAwards import_time:{}", jsonObject.get("import_time"));
                        jsonObject.put("import_time_long", LocalDateTime.parse(userEmployeeDO.getImportTime(), dtf).toInstant(ZoneOffset.of("+8")).toEpochMilli());
                        logger.info("SearchengineEntity updateEmployeeAwards import_time_lonog:{}", jsonObject.get("import_time_long"));
                    }
                    JSONObject searchData = new JSONObject();
                    searchData.put("email", "");
                    if(StringUtils.isNotNullOrEmpty(userEmployeeDO.getEmail())) {
                        searchData.put("email", userEmployeeDO.getEmail().toLowerCase());
                    }
                    searchData.put("mobile",String.valueOf(userEmployeeDO.getMobile()));
                    searchData.put("custom_field", "");
                    if(StringUtils.isNotNullOrEmpty(userEmployeeDO.getCustomField())) {
                        searchData.put("custom_field", userEmployeeDO.getCustomField().toLowerCase());
                    }


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
                    // userdb.useruser.name > userdb.useruser.nickname > userdb.userwxuser.nickname
                    if (userUerMap.containsKey(userEmployeeDO.getSysuserId())) {
                        UserUserDO userUserDO = (UserUserDO) userUerMap.get(userEmployeeDO.getSysuserId());
                        if (userUserDO.getName() != null) {
                            jsonObject.put("nickname", userUserDO.getName());
                            searchData.put("nickname", userUserDO.getName().toLowerCase());
                        } else if (userUserDO.getName() == null && userUserDO.getNickname() != null) {
                            jsonObject.put("nickname", userUserDO.getNickname());
                            searchData.put("nickname", userUserDO.getNickname().toLowerCase());
                        }
                    }
                    if (jsonObject.get("nickname") == null) {
                        queryBuilder.clear();
                        queryBuilder.where(UserWxUser.USER_WX_USER.SYSUSER_ID.getName(), userEmployeeDO.getSysuserId());
                        UserWxUserDO userWxUserDO = userWxUserDao.getData(queryBuilder.buildQuery());
                        if (userWxUserDO != null && userWxUserDO.getNickname() != null) {
                            jsonObject.put("nickname", userWxUserDO.getNickname());
                            searchData.put("nickname", userWxUserDO.getNickname().toLowerCase());
                        }
                    }
                    jsonObject.put("ename", userEmployeeDO.getEname());
                    jsonObject.put("cfname", userEmployeeDO.getCfname());
                    jsonObject.put("efname", userEmployeeDO.getEfname());
                    jsonObject.put("award", userEmployeeDO.getAward());
                    jsonObject.put("email", userEmployeeDO.getEmail());
                    jsonObject.put("cname", userEmployeeDO.getCname());
                    searchData.put("cname", "");
                    if(StringUtils.isNotNullOrEmpty(userEmployeeDO.getCname())) {
                        searchData.put("cname", userEmployeeDO.getCname().toLowerCase());
                    }
                    jsonObject.put("disable", userEmployeeDO.getDisable());
                    jsonObject.put("search_data", searchData);

                    jsonObject.put("update_time", LocalDateTime.parse(userEmployeeDO.getUpdateTime(), java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    jsonObject.put("create_time", LocalDateTime.parse(userEmployeeDO.getCreateTime(), java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                    logger.info(JSONObject.toJSONString(jsonObject));
                    logger.info("SearchengineEntity updateEmployeeAwards jsonObject:{}", jsonObject);
                    bulkRequest.add(
                            client.prepareIndex("awards", "award", userEmployeeDO.getId() + "")
                                    .setSource(jsonObject)
                    );
                }
                BulkResponse bulkResponse = bulkRequest.execute().actionGet();
                logger.info("------全量更新员工积分结束-------");
                logger.info("bulkResponse.buildFailureMessage():{}", bulkResponse.buildFailureMessage());
                logger.info("bulkResponse.toString():" + bulkResponse.toString());
                if (bulkResponse.buildFailureMessage() != null) {
                    return ResponseUtils.fail(9999, bulkResponse.buildFailureMessage());
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);

            }
        }
        return ResponseUtils.success("");
    }


    /**
     * 增量更新员工积分信息
     *
     * @param userEmployeeId   员工ID
     * @param employeeRecordId 员工加积分记录表ID
     * @return
     */
    public Response updateEmployeeAwards(Integer userEmployeeId, Integer employeeRecordId) {
        logger.info("----开始增量更新员工积分信息-------");
        // 连接ES
        TransportClient client =this.getTransportClient();
        if (client == null) {
            return ResponseUtils.fail(9999, "ES连接失败！");
        }
        try {
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.ID.getName(), employeeRecordId);
            UserEmployeePointsRecordDO userEmployeePointsRecordDO = userEmployeePointsDao.getData(queryBuilder.buildQuery());
            if (userEmployeePointsRecordDO.getEmployeeId() != userEmployeeId) {
                return ResponseUtils.fail(9999, "积分信息有误！");
            }
            if (userEmployeePointsRecordDO == null && userEmployeePointsRecordDO.getAward() != 0) {
                return ResponseUtils.fail(9999, "更新的数据为空！");
            }
            JSONObject jsonObject = new JSONObject();
            int employeeAward = 0;
            // 积分信息
            JSONObject awards = new JSONObject();
            GetResponse response = client.prepareGet("awards", "award", userEmployeeId + "").execute().actionGet();
            // ES中的积分数据
            Map<String, Object> mapTemp = response.getSource();
            logger.info("SearchengineEntity updateEmployeeAwards mapTemp:{}", mapTemp);
            if (mapTemp != null) {
                if (mapTemp.get("award") != null) {
                    logger.info("SearchengineEntity updateEmployeeAwards mapTemp.award:{}", mapTemp.get("award"));
                    employeeAward = (Integer)mapTemp.get("award");
                }
                // 积分信息
                Map<String, Object> awardsMap = (Map) mapTemp.get("awards");
                String lastUpdateTime = userEmployeePointsRecordDO.getUpdateTime();
                int point = userEmployeePointsRecordDO.getAward();
                Date tempDate = DateUtils.nomalDateToDate(lastUpdateTime);
                // 年
                String year = DateUtils.getYear(tempDate) + "";
                // 季度
                String season = year + DateUtils.getSeason(tempDate);
                // 月
                String month = year + "-" + DateUtils.getMonth(tempDate);
                Map<String, Integer> hashMap = new LinkedHashMap<>();
                hashMap.put(year, point);
                hashMap.put(season, point);
                hashMap.put(month, point);
                // 更新ES信息
                if (awardsMap != null && awardsMap.size() > 0) {
                    boolean updateAward = false;
                    for (Map.Entry<String, Object> entry : awardsMap.entrySet()) {
                        JSONObject object = new JSONObject();
                        Map awardMap = (Map) entry.getValue();
                        // 判断是否追加积分信息
                        if (entry.getKey().equals(year) || entry.getKey().equals(season) || entry.getKey().equals(month)) {
                            int award = Double.valueOf(awardMap.get("award").toString()).intValue();
                            award = award + point;
                            object.put("last_update_time", LocalDateTime.parse(userEmployeePointsRecordDO.getUpdateTime(), java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                            object.put("award", award);
                            awards.put(entry.getKey(), object);
                            hashMap.remove(entry.getKey());
                        }

                    }
                }
                // 新追加的积分信息
                if (hashMap != null && hashMap.size() > 0) {
                    for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
                        JSONObject object = new JSONObject();
                        object.put("last_update_time", LocalDateTime.parse(userEmployeePointsRecordDO.getUpdateTime(), java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        object.put("award", point);
                        object.put("timespan", entry.getKey());
                        awards.put(entry.getKey(), object);
                    }
                }

            }
            logger.info("SearchengineEntity updateEmployeeAwards award:{}", employeeAward);
            jsonObject.put("award", employeeAward + userEmployeePointsRecordDO.getAward());
            logger.info("SearchengineEntity updateEmployeeAwards after update award:{}", employeeAward + userEmployeePointsRecordDO.getAward());
            jsonObject.put("awards", awards);
            logger.info("SearchengineEntity",JSONObject.toJSONString(jsonObject));
            // 更新ES
            client.prepareUpdate("awards", "award", userEmployeeId + "")
                    .setDoc(jsonObject).get();
            return ResponseUtils.success("");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("------增量更新员工积分信息结束-------");
        return ResponseUtils.success("");
    }


    /**
     * 删除员工积分索引
     *
     * @param employeeIds
     * @return
     * @throws TException
     */
    public Response deleteEmployeeDO(List<Integer> employeeIds) throws CommonException {
        logger.info("----删除员工积分索引信息开始，员工ID:{}-------", employeeIds.toString());
        // 连接ES
        TransportClient client =this.getTransportClient();
        if (client == null) {
            return ResponseUtils.fail(9999, "ES 连接失败！");
        }
        BulkRequestBuilder bulkRequest = null;
        BulkResponse bulkResponse = null;
        try {
            bulkRequest = client.prepareBulk();
            if (employeeIds != null && employeeIds.size() > 0) {
                for (Integer id : employeeIds) {
                    bulkRequest.add(client.prepareDelete("awards", "award", id + ""));
                }
            }
            bulkResponse = bulkRequest.execute().actionGet();
            if (bulkResponse.buildFailureMessage() != null) {
                return this.handlerSingleDelEmployeeEs(employeeIds,client);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return this.handlerSingleDelEmployeeEs(employeeIds,client);
        }
        logger.info("----删除员工积分索引信息结束-------");
        return ResponseUtils.success("");
    }

    /**
     * 删除员工积分索引
     *
     *
     * @param id
     * @param userId
     * @param applierName
     *@param updateTime @return
     * @throws TException
     */
    public Response removeApplication(Integer id, Integer applicationId, Integer userId, String applierName, Timestamp updateTime) throws CommonException {
        logger.info("----删除招聘，员工ID:{}-------", userId);
        logger.info("removeApplication id:{}, applicationId:{}, userId:{}, applierName:{}, updateTime:{}-------", id, applicationId, userId, applierName, updateTime.getTime());
        // 连接ES
        TransportClient client =this.getTransportClient();
        if (client == null) {
            return ResponseUtils.fail(9999, "ES 连接失败！");
        }
        try {
            if (id != null && id > 0) {
                GetResponse response = client.prepareGet("users", "users", id + "").execute().actionGet();
                // ES中的积分数据
                Map<String, Object> mapTemp = response.getSource();
                logger.info("SearchengineEntity removeApplication mapTemp:{}", JSONObject.toJSONString(mapTemp));
                if (mapTemp != null) {
                    mapTemp.put("id", userId);
                    Map<String, Object> userMap = (Map<String, Object>) mapTemp.get("user");
                    if (userMap != null && userMap.get("applications") != null) {
                        List<Map<String, Object>> applications = (List<Map<String, Object>>) userMap.get("applications");
                        logger.info("removeApplication applications:{}", JSONObject.toJSONString(applications));
                        if (applications != null && applications.size() > 0) {
                            Optional<Map<String, Object>> applicationOptional = applications.stream().filter(stringObjectMap -> (stringObjectMap.get("id")).equals(applicationId)).findAny();
                            logger.info("removeApplication applicationOptional:{}", applicationOptional.get());
                            if (applicationOptional.isPresent()) {
                                logger.info("removeApplication exists ");
                                List<Map<String, Object>> apps = applications.stream().filter(stringObjectMap -> !stringObjectMap.get("id").equals(applicationId)).collect(Collectors.toList());
                                logger.info("removeApplication apps :{}", apps);
                                if (apps == null || apps.size() == 0) {
                                    logger.info("removeApplication 删除索引 users id:{}", id);
                                    client.prepareDelete("users", "users", id + "").execute().actionGet();
                                } else {
                                    logger.info("removeApplication 更新索引 apps:{}", apps);
                                    userMap.put("applications", apps);
                                    mapTemp.put("user", userMap);
                                    client.prepareUpdate("users", "users", id + "")
                                            .setDoc(mapTemp).execute().actionGet();
                                }
                                // 更新ES
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return ResponseUtils.success("");
    }

    /*
     单独删除雇员es的数据
     */
    private Response handlerSingleDelEmployeeEs(List<Integer> employeeIds,TransportClient client){
        try{
            if(employeeIds != null && employeeIds.size()>0){
                for(Integer employeeId :employeeIds){
                    client.prepareDelete("awards", "award", employeeId + "").execute().actionGet();
                }
            }
            return ResponseUtils.success("");
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            logger.error("执行失败的雇员的id列表为==", JSON.toJSONString(employeeIds));
            return ResponseUtils.fail(9999,"执行失败");
        }
    }

    /**
     * 拼接积分信息 （月，季，年）
     *
     * @param jsonObject
     * @param list
     */
    public void getAwards(JSONObject jsonObject, List<EmployeePointsRecordPojo> list) {
        logger.info(list.size() + "");
        if (list != null && list.size() > 0) {
            for (EmployeePointsRecordPojo employeePointsRecordPojo : list) {
                JSONObject a = new JSONObject();
                a.put("last_update_time", employeePointsRecordPojo.getLast_update_time().format(DateTimeFormatter.ISO_DATE_TIME));
                a.put("award", employeePointsRecordPojo.getAward());
                a.put("timespan", employeePointsRecordPojo.getTimespan());
                jsonObject.put(employeePointsRecordPojo.getTimespan(), a);
            }
        }
    }

    public Map<Integer,Integer> getCurrentMonthList(List<Integer> employeeIdList, List<Integer> companyIdList) {

        TransportClient client =this.getTransportClient();
        if (client == null) {
            return new HashMap<>();
        } else {

            QueryBuilder employeeIdListQueryBuild = QueryBuilders.termsQuery("id", employeeIdList);

            SearchRequestBuilder searchRequestBuilder = client.prepareSearch("awards").setTypes("award")
                    .setQuery(employeeIdListQueryBuild).setFrom(0).setSize(employeeIdList.size());
            logger.info("getCurrentMonthList searchRequestBuilder:{}", searchRequestBuilder.toString());
            SearchResponse response = searchRequestBuilder.execute().actionGet();

            LocalDateTime localDateTime = LocalDateTime.now();
            String timeSpan = localDateTime.getYear() + "-" +
                    (localDateTime.getMonthValue() < 10 ?
                            ("0"+localDateTime.getMonthValue()) : localDateTime.getMonthValue());

            logger.info("getCurrentMonthList response:{}", response);
            List<EmployeeAwards> employeeAwardsList = new ArrayList<>();
            for (SearchHit searchHit : response.getHits().getHits()) {
                JSONObject jsonObject = JSON.parseObject(searchHit.getSourceAsString());

                EmployeeAwards employeeAwards = new EmployeeAwards();
                employeeAwards.setId(jsonObject.getInteger("id"));
                logger.info("id:{},  awards:{}, timespan:{}", jsonObject.get("id"), jsonObject.get("awards"), timeSpan);
                if (jsonObject.getJSONObject("awards") != null && jsonObject.getJSONObject("awards").getJSONObject(timeSpan) != null) {
                    JSONObject timeSpanAward = jsonObject.getJSONObject("awards").getJSONObject(timeSpan);
                    employeeAwards.setAward(timeSpanAward.getInteger("award"));
                    employeeAwards.setTimeSpan(timeSpan);
                    employeeAwards.setLastUpdateTime(
                            LocalDateTime.parse(jsonObject.getJSONObject("awards").getJSONObject(timeSpan)
                                    .getString("last_update_time"))
                                    .atZone(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli());
                    logger.info("getCurrentMonthList - employeeAwards:{}", employeeAwards);
                    employeeAwardsList.add(employeeAwards);
                }
            }


            if (employeeAwardsList != null && employeeAwardsList.size() > 0) {

                QueryBuilder companyIdListQueryBuild = QueryBuilders.termsQuery("company_id", companyIdList);

                for (int i =0; i< employeeAwardsList.size(); i++) {
                    employeeAwardsList.get(i).setSort(getSort(client, employeeAwardsList.get(i).getId(),
                            employeeAwardsList.get(i).getAward(), employeeAwardsList.get(i).getLastUpdateTime(), employeeAwardsList.get(i).getTimeSpan(),
                            companyIdListQueryBuild));
                }
            }
            return employeeAwardsList.stream().collect(Collectors.toMap(EmployeeAwards::getId, EmployeeAwards::getSort));
        }
    }

    /**
     * 获取员工排名
     * @param id 员工编号
     * @param award 积分
     * @param timeSpan 时间跨度
     * @param companyIdList 公司编号（集团公司是集团下的所有员工排名）
     * @return 排名
     * @throws CommonException 业务异常
     */
    public int getSort(int id, int award, long lastUpdateTime, String timeSpan, List<Integer> companyIdList) throws CommonException {
        TransportClient client =this.getTransportClient();
        if (client == null) {
            logger.error("无法获取ES客户端！！！！");
            throw CommonException.PROGRAM_EXCEPTION;
        }
        logger.info("getSort id:{}, award:{}, lastUpdateTime:{}, timeSpan:{}, companyIdList:{}", id, award, lastUpdateTime, timeSpan, companyIdList);
        QueryBuilder companyIdListQueryBuild = QueryBuilders.termsQuery("company_id", companyIdList);
        return getSort(client, id, award, lastUpdateTime, timeSpan, companyIdListQueryBuild);
    }

    /**
     * 增加排序脚本。针对月/季/年榜积分的特殊处理
     * @param timspanc 时间
     * @param field 字段
     * @param sortOrder 排序规则 升序还是降序
     * @return ES插叙预计
     */
    public SortBuilder buildSortScript(String timspanc, String field, SortOrder sortOrder) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("double score=0; awards=_source.awards;times=awards['" + timspanc +
                "'];if(times){award=doc['awards." + timspanc + "." + field +
                "'].value;if(award){score=award}}; return score");
        return new ScriptSortBuilder(new Script(stringBuffer.toString()), "number").order(sortOrder);
    }

    /**
     * 查询指定员工的索引信息
     * @param id
     * @return
     */
    public JSONObject getEmployeeInfo(int id) {
        TransportClient client =this.getTransportClient();
        if (client == null) {
            return new JSONObject();
        } else {
            QueryBuilder employeeIdListQueryBuild = QueryBuilders.termsQuery("id", String.valueOf(id));

            SearchRequestBuilder searchRequestBuilder = client.prepareSearch("awards").setTypes("award")
                    .setQuery(employeeIdListQueryBuild);
            SearchResponse response = searchRequestBuilder.execute().actionGet();
            logger.info("getEmployeeInfo id:{},  response:{}", id, response);
            if (response.getHits() != null && response.getHits().totalHits() > 0) {
                SearchHit searchHit = response.getHits().getAt(0);
                JSONObject jsonObject = JSON.parseObject(searchHit.getSourceAsString());
                return jsonObject;
            } else {
                return new JSONObject();
            }
        }
    }

    private int getSort(TransportClient client, int employeeId, int award, long lastUpdateTime,  String timeSpan,
                        QueryBuilder companyIdListQueryBuild) {
        logger.info("getSort award:{}", award);
        if (award > 0) {
            LocalDateTime lastUpdateDateTime = Instant.ofEpochMilli(lastUpdateTime).atZone(ZoneId.systemDefault()).toLocalDateTime();
            QueryBuilder defaultQueryGTAward = QueryBuilders.matchAllQuery();
            QueryBuilder queryGTAward = QueryBuilders.boolQuery().must(defaultQueryGTAward);

            QueryBuilder awardQuery = QueryBuilders.rangeQuery("awards." + timeSpan + ".award")
                    .gt(award);
            ((BoolQueryBuilder) queryGTAward).must(awardQuery);

            ((BoolQueryBuilder) queryGTAward).must(companyIdListQueryBuild);

            QueryBuilder exceptCurrentEmployeeQuery = QueryBuilders
                    .termQuery("id", employeeId);
            ((BoolQueryBuilder) queryGTAward).mustNot(exceptCurrentEmployeeQuery);

            QueryBuilder activeEmployeeCondition = QueryBuilders.termQuery("activation", "0");

            ((BoolQueryBuilder) queryGTAward).must(activeEmployeeCondition);
            logger.info("getSort activeEmployeeCondition:{}", queryGTAward);

            logger.info("ex sql :{}", client.prepareSearch("awards").setTypes("award")
                    .setQuery(queryGTAward)
                    .addSort(buildSortScript(timeSpan, "award", SortOrder.DESC))
                    .addSort(buildSortScript(timeSpan, "last_update_time", SortOrder.ASC))
                    .setFetchSource(new String[]{"id", "awards." + timeSpan + ".award", "awards." + timeSpan + ".last_update_time"}, null)
                    .setSize(0).toString());

            QueryBuilder defaultQuery = QueryBuilders.matchAllQuery();
            QueryBuilder query = QueryBuilders.boolQuery().must(defaultQuery);

            QueryBuilder award1Query = QueryBuilders.termQuery("awards." + timeSpan + ".award", award);
            ((BoolQueryBuilder) query).must(award1Query);

            QueryBuilder lastUpdateTimeQuery = QueryBuilders.rangeQuery("awards." + timeSpan + ".last_update_time")
                    .lte(lastUpdateDateTime.toString());
            ((BoolQueryBuilder) query).must(lastUpdateTimeQuery);

            ((BoolQueryBuilder) query).must(companyIdListQueryBuild);
            ((BoolQueryBuilder) query).mustNot(exceptCurrentEmployeeQuery);
            ((BoolQueryBuilder) query).must(activeEmployeeCondition);

            logger.info("getSort activeEmployeeCondition:{}", query);

            try {
                SearchResponse sortResponse = client.prepareSearch("awards").setTypes("award")
                        .setQuery(queryGTAward)
                        .setSize(0).execute().get();

                SearchResponse sortResponse1 = client.prepareSearch("awards").setTypes("award")
                        .setQuery(query)
                        .setSize(0).execute().get();
                int sort = (int)sortResponse.getHits().getTotalHits();
                int sort2 = (int)sortResponse1.getHits().getTotalHits();

                logger.info("getSort sort:{}, sort2:{}", sort, sort2);

                return sort + sort2 + 1;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return 0;
    }

    public JSONObject lastEmployeeInfo(int id, String timeSpan, List<Integer> companyIdList) {

        TransportClient client =this.getTransportClient();
        if (client == null) {
            logger.error("无法获取ES客户端！！！！");
            throw CommonException.PROGRAM_EXCEPTION;
        }

        QueryBuilder defaultQuery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultQuery);

        QueryBuilder awardQuery = QueryBuilders.rangeQuery("awards." + timeSpan + ".award")
                .gt(0);
        ((BoolQueryBuilder) query).must(awardQuery);

        QueryBuilder companyIdListQueryBuild = QueryBuilders.termsQuery("company_id", companyIdList);
        ((BoolQueryBuilder) query).must(companyIdListQueryBuild);

        QueryBuilder exceptCurrentEmployeeQuery = QueryBuilders
                .termQuery("id", id);
        ((BoolQueryBuilder) query).mustNot(exceptCurrentEmployeeQuery);

        QueryBuilder activeEmployeeCondition = QueryBuilders.termQuery("activation", "0");
        ((BoolQueryBuilder) query).must(activeEmployeeCondition);

        try {
            SearchResponse response = client.prepareSearch("awards").setTypes("award")
                    .setQuery(query)
                    .addSort(buildSortScript(timeSpan, "award", SortOrder.ASC))
                    .addSort(buildSortScript(timeSpan, "last_update_time", SortOrder.DESC))
                    .setFetchSource(new String[]{"id", "awards." + timeSpan + ".award", "awards." + timeSpan + ".last_update_time"}, null)
                    .setSize(1).execute().get();
            if (response.getHits() != null && response.getHits().totalHits() > 0) {
                SearchHit searchHit = response.getHits().getAt(0);
                JSONObject jsonObject = JSON.parseObject(searchHit.getSourceAsString());
                return jsonObject;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new JSONObject();
    }

    @Transactional
    public Response updateReferralPostionStatus(Integer positionId,Integer isReferral){
        logger.info("updateReferralPostionStatus {} {}",positionId,isReferral);
        String idx = "" + positionId;
        TransportClient client = getTransportClient();
        if (client == null) {
            return ResponseUtils.fail(9999, "ES连接失败！");
        }
        UpdateResponse response = client.prepareUpdate(Constant.ES_POSITION_INDEX, Constant.ES_POSITION_TYPE, idx)
                .setScript(new Script("ctx._source.is_referral = " + isReferral))
                .get();
        if(response.getGetResult() == null) {
            return  ResponseUtils.fail(9999,"ES操作失败");
        } else {
            return ResponseUtils.success(response);
        }    }

    @Transactional
    public Response updateBulkReferralPostionStatus(List<Integer> positionIds,Integer isReferral) throws Exception{
        if(CollectionUtils.isEmpty(positionIds)) {
            return ResponseUtils.success(null);
        }
        DateTime nowDate = new DateTime();

        logger.info("updateBulkReferralPostionStatus {} 条 计时开始时间 {}" ,positionIds.size(), nowDate.toString("yyyy-MM-dd HH:mm:ss"));
        TransportClient client = getTransportClient();
        if (client == null) {
            return ResponseUtils.fail(9999, "ES连接失败！");
        }
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for(Integer pid: positionIds) {
            String idx = "" + pid;
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("is_referral", isReferral)
                    .field("update_time",nowDate.getMillis()).field("update_time_view",nowDate.toString("yyyy-MM-dd HH:mm:ss"))
                    .endObject();

            bulkRequest.add(client.prepareUpdate(Constant.ES_POSITION_INDEX, Constant.ES_POSITION_TYPE, idx).setDoc(builder));

        }
        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        //logger.info("updateBulkReferralPostionStatus bulkResponse {}",JSON.toJSONString(bulkResponse));

        if(bulkResponse.hasFailures()) {
            DateTime endDate = new DateTime();
            logger.info("updateBulkReferralPostionStatus {} 条  计时结束时间 {}  耗时 {} 毫秒" ,positionIds.size(),endDate.toString("yyyy-MM-dd HH:mm:ss"),endDate.getMillisOfSecond()-nowDate.getMillisOfSecond() );
            return  ResponseUtils.fail(9999,bulkResponse.buildFailureMessage());
        } else {
            DateTime endDate = new DateTime();
            logger.info("updateBulkReferralPostionStatus {} 条  计时结束时间 {}  耗时 {} 毫秒" ,positionIds.size(),endDate.toString("yyyy-MM-dd HH:mm:ss"),endDate.getMillisOfSecond()-nowDate.getMillisOfSecond() );

            return ResponseUtils.success(bulkResponse);
        }
    }


    /**
     * 更新员工总奖金
     * @param employeeIds
     * @param bonus
     * @return
     * @throws Exception
     */
    @Transactional
    public Response updateEmployeeBonus(List<Integer> employeeIds,Integer bonus) throws Exception{
        LocalDateTime nowDate = LocalDateTime.now();

        TransportClient client = getTransportClient();
        if (client == null) {
            return ResponseUtils.fail(9999, "ES连接失败！");
        }
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for(Integer employeeId: employeeIds) {
            String idx = "" + employeeId;
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("bonus", bonus)
                    .field("update_time",nowDate)
                    .endObject();
            bulkRequest.add(client.prepareUpdate("awards", "award", idx).setDoc(builder));

        }
        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        if(bulkResponse.hasFailures()) {
            return  ResponseUtils.fail(9999,bulkResponse.buildFailureMessage());
        } else {
            return ResponseUtils.success(bulkResponse);
        }
    }


}
