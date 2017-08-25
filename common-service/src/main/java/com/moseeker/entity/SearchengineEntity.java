package com.moseeker.entity;

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
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
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
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    /**
     * 获取ES连接
     *
     * @return
     */
    public TransportClient getTransportClient() {
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
        try {
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(es_connection), es_port));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return client;
    }

    /**
     * 全量更新员工积分
     *
     * @param employeeIds
     * @return
     * @throws TException
     */
    public Response updateEmployeeAwards(List<Integer> employeeIds) throws CommonException {
        logger.info("----开始全量更新员工积分-------");
        // 连接ES
        TransportClient client = getTransportClient();
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
                        } else if (userUserDO.getName() == null && userUserDO.getNickname() != null) {
                            jsonObject.put("nickname", userUserDO.getNickname());
                        }
                    }
                    if (jsonObject.get("nickname") == null) {
                        queryBuilder.clear();
                        queryBuilder.where(UserWxUser.USER_WX_USER.SYSUSER_ID.getName(), userEmployeeDO.getSysuserId());
                        UserWxUserDO userWxUserDO = userWxUserDao.getData(queryBuilder.buildQuery());
                        if (userWxUserDO != null) {
                            jsonObject.put("nickname", userWxUserDO.getNickname());
                        }
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
                logger.info("------全量更新员工积分结束-------");
                logger.info("bulkResponse.buildFailureMessage():{}", bulkResponse.buildFailureMessage());
                logger.info("bulkResponse.toString():" + bulkResponse.toString());
                if (bulkResponse.buildFailureMessage() != null) {
                    return ResponseUtils.fail(9999, bulkResponse.buildFailureMessage());
                }
            } catch (Exception error) {
                logger.error(error.getMessage());
            } finally {
                client.close();
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
        TransportClient client = getTransportClient();
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
            // 积分信息
            JSONObject awards = new JSONObject();
            GetResponse response = client.prepareGet("awards", "award", userEmployeeId + "").execute().actionGet();
            // ES中的积分数据
            Map<String, Object> mapTemp = response.getSource();
            if (mapTemp != null) {
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
                    for (Map.Entry<String, Object> entry : awardsMap.entrySet()) {
                        JSONObject object = new JSONObject();
                        Map awardMap = (Map) entry.getValue();
                        // 判断是否追加积分信息
                        if (entry.getKey().equals(year) || entry.getKey().equals(season) || entry.getKey().equals(month)) {
                            Integer award = (Integer) awardMap.get("award");
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
            jsonObject.put("awards", awards);
            logger.info(JSONObject.toJSONString(jsonObject));
            // 更新ES
            client.prepareUpdate("awards", "award", userEmployeeId + "")
                    .setDoc(jsonObject).get();
            return ResponseUtils.success("");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            client.close();
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
        TransportClient client = getTransportClient();
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
                return ResponseUtils.fail(9999, bulkResponse.buildFailureMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
        logger.info("----删除员工积分索引信息结束-------");
        return ResponseUtils.success("");
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
                logger.info("last_update_time:{}", employeePointsRecordPojo.getLast_update_time());
                logger.info("award:{}", employeePointsRecordPojo.getAward());
                logger.info("timespan:{}", employeePointsRecordPojo.getTimespan());
                JSONObject a = new JSONObject();
                a.put("last_update_time", employeePointsRecordPojo.getLast_update_time());
                a.put("award", employeePointsRecordPojo.getAward());
                a.put("timespan", employeePointsRecordPojo.getTimespan());
                jsonObject.put(employeePointsRecordPojo.getTimespan(), a);
            }
        }
    }
}
