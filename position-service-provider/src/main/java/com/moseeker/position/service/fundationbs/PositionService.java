package com.moseeker.position.service.fundationbs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.moseeker.baseorm.dao.dictdb.*;
import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.dao.jobdb.*;
import com.moseeker.baseorm.db.dictdb.tables.records.DictAlipaycampusCityRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictAlipaycampusJobcategoryRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityPostcodeRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyAccountRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTeamRecord;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.records.*;
import com.moseeker.baseorm.pojo.JobPositionPojo;
import com.moseeker.baseorm.pojo.RecommendedPositonPojo;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.*;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.MD5Util;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.PositionEntity;
import com.moseeker.position.pojo.DictConstantPojo;
import com.moseeker.position.pojo.JobPositionFailMess;
import com.moseeker.position.pojo.JobPostionResponse;
import com.moseeker.position.pojo.PositionForSynchronizationPojo;
import com.moseeker.position.service.position.DegreeChangeUtil;
import com.moseeker.position.service.position.PositionChangeUtil;
import com.moseeker.position.service.position.PositionForAlipaycampusPojo;
import com.moseeker.position.service.position.WorkTypeChangeUtil;
import com.moseeker.position.service.position.qianxun.Degree;
import com.moseeker.position.service.position.qianxun.WorkType;
import com.moseeker.position.utils.CommonPositionUtils;
import com.moseeker.position.utils.SpecialCtiy;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.*;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobOccupationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.position.struct.*;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import org.apache.thrift.TException;
import org.jooq.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Math.round;
import static java.lang.Math.toIntExact;

@Service
@Transactional
public class PositionService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private DictConstantDao dictConstantDao;
    @Autowired
    private JobCustomDao jobCustomDao;
    @Autowired
    private JobPositionCityDao jobPositionCityDao;
    @Autowired
    private JobPositionExtDao jobPositionExtDao;
    @Autowired
    private JobOccupationDao jobOccupationDao;
    @Autowired
    private DictCityDao dictCityDao;
    @Autowired
    private HrTeamDao hrTeamDao;
    @Autowired
    private HrCompanyAccountDao hrCompanyAccountDao;
    @Autowired
    private HRThirdPartyAccountDao thirdPartyAccountDao;
    @Autowired
    private HRThirdPartyPositionDao thirdpartyPositionDao;
    @Autowired
    private JobPositionExtDao jobPositonExtDao;
    @Autowired
    private DictCityPostcodeDao dictCityPostcodeDao;
    @Autowired
    private DictAlipaycampusJobcategoryDao dictAlipaycampusJobcategoryDao;
    @Autowired
    private DictAlipaycampusCityDao dictAlipaycampusCityDao;
    @Autowired
    private HrCompanyDao hrCompanyDao;
    @Autowired
    private HrHbConfigDao hrHbConfigDao;
    @Autowired
    private HrHbPositionBindingDao hrHbPositionBindingDao;
    @Autowired
    private HrHbItemsDao hrHbItemsDao;
    @Autowired
    private PositionChangeUtil positionChangeUtil;

    @Autowired
    private CommonPositionUtils commonPositionUtils;

    @Autowired
    private PositionEntity positionEntity;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    private static List<DictAlipaycampusJobcategoryRecord> alipaycampusJobcategory;


    SearchengineServices.Iface searchengineServices = ServiceManager.SERVICEMANAGER.getService(SearchengineServices.Iface.class);

    private static List dictAlipaycampusJobcategorylist;

    /**
     * 获取推荐职位 <p> </p>
     *
     * @param pid 当前职位 id
     * @return 推荐职位列表
     */
    @CounterIface
    public Response getRecommendedPositions(int pid) {
        List<RecommendedPositonPojo> recommendPositons = jobPositionDao.getRecommendedPositions(pid);
        return ResponseUtils.successWithoutStringify(JSON.toJSONString(recommendPositons, new ValueFilter() {
            @Override
            public Object process(Object object, String name, Object value) {
                if (value == null) {
                    return "";
                }
                return value;
            }
        }));
    }

    @CounterIface
    public Response verifyCustomize(int positionId) throws TException {
        Query query = new Query.QueryBuilder().where("id", positionId).buildQuery();
        JobPositionRecord positionRecord = jobPositionDao.getRecord(query);
        if (positionRecord == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_POSITION_NOTEXIST);
        }
        if (positionRecord.getAppCvConfigId() != null && positionRecord.getAppCvConfigId() > 0) {
            return ResponseUtils.success(true);
        } else {
            return ResponseUtils.success(false);
        }
    }

    /**
     * 根据职位Id获取当前职位信息
     *
     * @param positionId 当前职位 id
     * @return 职位信息
     * @throws TException TException
     */
    @CounterIface
    public Response getPositionById(int positionId) {

        // 必填项校验
        if (positionId == 0) {
            return ResponseUtils
                    .fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "position_id"));
        }
        // NullPoint check
        JobPositionPojo jobPositionPojo = jobPositionDao.getPosition(positionId);
        if (jobPositionPojo == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        jobPositionPojo.team_name = "";
        jobPositionPojo.department = "";
        int team_id = jobPositionPojo.team_id;
        if (team_id != 0) {
            Query query1 = new Query.QueryBuilder().where("id", team_id).and("disable", 0).buildQuery();
            HrTeamRecord hrTeamRecord = hrTeamDao.getRecord(query1);
            if (hrTeamRecord != null) {
                jobPositionPojo.department = hrTeamRecord.getName();
                jobPositionPojo.team_name = hrTeamRecord.getName();
            }
        }


        /** 子公司Id设置 **/
        if (jobPositionPojo.publisher != 0) {
            Query query2 = new Query.QueryBuilder().where("account_id", jobPositionPojo.publisher).buildQuery();
            HrCompanyAccountRecord hrCompanyAccountRecord = hrCompanyAccountDao.getRecord(query2);
            // 子公司ID>0
            if (hrCompanyAccountRecord != null && hrCompanyAccountRecord.getCompanyId() > 0) {
                jobPositionPojo.publisher_company_id = hrCompanyAccountRecord.getCompanyId();
            }
        }

        // 常量转换
        // 性别
        if (jobPositionPojo.gender < 2) {
            jobPositionPojo.gender_name = getDictConstantJson(2102, jobPositionPojo.gender);
        }

        // 学历
        if (jobPositionPojo.degree > 0) {
            jobPositionPojo.degree_name = getDictConstantJson(2101, jobPositionPojo.degree);
        }

        // 工作性质
        jobPositionPojo.employment_type_name = getDictConstantJson(2103, jobPositionPojo.employment_type);

        // 招聘类型
        jobPositionPojo.candidate_source_name = getDictConstantJson(2104, jobPositionPojo.candidate_source);

        // 自定义字段 与 自定义职位职能
        JobPositionExtRecord jobPositionExtRecord = getJobPositionExtRecord(positionId);
        if (jobPositionExtRecord != null) {
            if (jobPositionExtRecord.getJobCustomId() > 0) {
                JobCustomRecord jobCustomRecord = jobCustomDao.getRecord(new Query.QueryBuilder().where("id", jobPositionExtRecord.getJobCustomId()).buildQuery());
                if (jobCustomRecord != null && !"".equals(jobCustomRecord.getName())) {
                    jobPositionPojo.custom = jobCustomRecord.getName();
                }
            }
            if (jobPositionExtRecord.getJobCustomId() > 0) {
                JobOccupationRecord jobOccupationRecord =
                        jobOccupationDao.getRecord(new Query.QueryBuilder().where("id", jobPositionExtRecord.getJobOccupationId()).buildQuery());
                if (jobOccupationRecord != null && com.moseeker.common.util.StringUtils.isNotNullOrEmpty(jobOccupationRecord.getName())) {
                    jobPositionPojo.occupation = jobOccupationRecord.getName();
                }
            }
        }
        // 修改更新时间
        jobPositionPojo.publish_date_view = DateUtils.dateToPattern(jobPositionPojo.publish_date,
                DateUtils.SHOT_TIME);
        jobPositionPojo.update_time_view = DateUtils.dateToPattern(jobPositionPojo.update_time,
                DateUtils.SHOT_TIME);

        // 省份
        List<DictCityRecord> provinces = this.getProvinces(positionId);
        if (provinces != null && provinces.size() > 0) {
            StringBuffer sb = new StringBuffer();
            provinces.forEach(province -> {
                sb.append(province.getName()).append(",");
            });
            sb.deleteCharAt(sb.length() - 1);
            jobPositionPojo.province = sb.toString();
        }
        String citynames = commonPositionUtils.handlerCity(positionId);
        logger.info("job_position_city的city信息是＝＝＝＝＝＝＝＝＝＝＝＝＝" + citynames);
        if (StringUtils.isNotNullOrEmpty(citynames)) {
            jobPositionPojo.city = citynames;
        }
        if ("全国".equals(jobPositionPojo.city)) {
            jobPositionPojo.city_flag = 1;
        }
        return ResponseUtils.success(jobPositionPojo);
    }


    /*
     * 获取城市
     */
    private List<DictCityRecord> getProvinces(int positionId) {
        Query query = new Query.QueryBuilder().where("pid", positionId).buildQuery();
        List<JobPositionCityRecord> jobPositionCityRecord = jobPositionCityDao.getRecords(query);
        if (jobPositionCityRecord != null && jobPositionCityRecord.size() > 0) {
            List<Integer> pids = new ArrayList<Integer>();
            jobPositionCityRecord.forEach(record -> {
                pids.add((int) ((record.getCode() / 10000) * 10000));
            });
            Query query2 = new Query.QueryBuilder().where(new Condition("code", pids, ValueOp.IN)).buildQuery();
            List<DictCityRecord> list = dictCityDao.getRecords(query2);
            return list;
        }
        return null;
    }

    /**
     * 获取常量字典一条记录
     */
    private String getDictConstantJson(Integer parentCode, Integer code) {
        Query query = new Query.QueryBuilder().where("parent_code", parentCode).and("code", code).buildQuery();
        DictConstantPojo dictConstantPojo = dictConstantDao.getData(query, DictConstantPojo.class);
        return dictConstantPojo != null ? dictConstantPojo.getName() : "";
    }

    private JobPositionExtRecord getJobPositionExtRecord(int positionId) {
        return jobPositionExtDao.getRecord(new Query.QueryBuilder().where("pid", positionId).buildQuery());
    }

    /**
     * 校验同步职位必填信息
     */
    public boolean verifySynchronizePosition(PositionForSynchronizationPojo position) {
        return false;
    }

    /**
     * 转成第三方渠道职位
     */
    public List<ThirdPartyPositionForSynchronization> changeToThirdPartyPosition(List<ThirdPartyPosition> forms,
                                                                                 JobPositionDO position) {
        List<ThirdPartyPositionForSynchronization> positions = new ArrayList<>();
        if (forms != null && forms.size() > 0 && position != null && position.getId() > 0) {
            forms.forEach(form -> {
                ThirdPartyPositionForSynchronization p = positionChangeUtil.changeToThirdPartyPosition(form, position);
                positions.add(p);
            });
        }
        return positions;
    }

    /**
     * 该职位是否可以刷新
     *
     * @param positionId 职位编号
     * @param account_id 第三方账号ID
     * @return bool
     */
    @CounterIface
    public boolean ifAllowRefresh(int positionId, int account_id) {
        logger.info("ifAllowRefresh");
        Query findPositionById = new Query.QueryBuilder().where("id", positionId).buildQuery();
        logger.info("search position");
        JobPositionDO position = jobPositionDao.getData(findPositionById);
        logger.info("position:" + JSON.toJSONString(position));

        if (position == null || position.getId() == 0) return false;

        Query queryUtil = new Query.QueryBuilder().where("id", account_id).buildQuery();

        HrThirdPartyAccountDO account = thirdPartyAccountDao.getData(queryUtil);

        if (account == null || account.binding == AccountSync.unbind.getValue()
                || account.binding == AccountSync.accountpasserror.getValue()
                || account.binding == AccountSync.error.getValue()
                || account.binding == AccountSync.bingdingerror.getValue()) {
            return false;
        }

        logger.info("ifAllowRefresh third party account:" + JSON.toJSONString(account));
        HrThirdPartyPositionDO thirdPartyPosition = thirdpartyPositionDao.getThirdPartyPosition(positionId, account_id);
        logger.info("thirdparyposition" + JSON.toJSONString(thirdPartyPosition));

        if (thirdPartyPosition == null || thirdPartyPosition.getIsSynchronization() != PositionSync.bound.getValue()) {
            return false;
        }


        logger.info("data allow");
        String str = redisClient.get(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_REFRESH.toString(), String.valueOf(positionId), String.valueOf(account_id));
        if (StringUtils.isNotNullOrEmpty(str)) {
            return false;
        }
        logger.info("cache allow");
        return true;
    }

    /**
     * 创建刷新职位数据
     *
     * @param positionId 职位编号
     * @param account_id 第三方账号ID
     */
    public ThirdPartyPositionForSynchronizationWithAccount createRefreshPosition(int positionId, int account_id) {
        ThirdPartyPositionForSynchronizationWithAccount syncAccount = new ThirdPartyPositionForSynchronizationWithAccount();
        Query findPosition = new Query.QueryBuilder().where("id", positionId).buildQuery();
        JobPositionDO position = jobPositionDao.getData(findPosition);

        if (position == null) {
            logger.info("createRefreshPosition position null :{}", positionId, account_id);
            return syncAccount;
        }

        HrThirdPartyPositionDO thirdPartyPosition = thirdpartyPositionDao.getThirdPartyPosition(positionId, account_id);

        if (thirdPartyPosition == null) {
            logger.info("createRefreshPosition thirdPartyPosition:{}:{}", positionId, account_id);
            return syncAccount;
        }

        Query findAccount = new Query.QueryBuilder().where("id", account_id).buildQuery();
        HrThirdPartyAccountDO thirdPartyAccount = thirdPartyAccountDao.getData(findAccount);

        if (thirdPartyAccount == null) {
            logger.info("createRefreshPosition thirdPartyAccount null:{}:{}", positionId, account_id);
            return syncAccount;
        }

        HrCompanyDO subCompany = hrCompanyAccountDao.getHrCompany(position.getPublisher());

        if (subCompany != null) {
            syncAccount.setCompany_name(subCompany.getAbbreviation());
        }

        syncAccount.setUser_name(thirdPartyAccount.getUsername());
        syncAccount.setMember_name(thirdPartyAccount.getMembername());
        syncAccount.setPassword(thirdPartyAccount.getPassword());
        syncAccount.setChannel(thirdPartyAccount.getChannel());
        syncAccount.setPosition_id(positionId);
        syncAccount.setAccount_id(account_id);

        ThirdPartyPosition form = new ThirdPartyPosition();
        form.setChannel((byte) thirdPartyAccount.getChannel());
        form.setAddress(thirdPartyPosition.getAddress());
        //count,occupation暂时没有放进去，目前不需要
        form.setDepartment(thirdPartyPosition.getDepartment());
        form.setFeedback_period(thirdPartyPosition.getFeedbackPeriod());
        form.setSalary_bottom(thirdPartyPosition.getSalaryBottom());
        form.setSalary_top(thirdPartyPosition.getSalaryTop());
        form.setSalary_discuss(thirdPartyPosition.getSalaryDiscuss() == 0 ? false : true);
        form.setSalary_month(thirdPartyPosition.getSalaryMonth());
        form.setUse_company_address(thirdPartyPosition.getUseCompanyAddress() == 0 ? false : true);
        form.setThird_party_account_id(thirdPartyAccount.getId());
        ThirdPartyPositionForSynchronization p = positionChangeUtil.changeToThirdPartyPosition(form, position);
        p.setJob_id(thirdPartyPosition.getThirdPartPositionId());
        syncAccount.setPosition_info(p);
        return syncAccount;
    }


    /**
     * 批量处理修改职位
     *
     * @param batchHandlerJobPosition
     * @return
     */
    @CounterIface
    public Response batchHandlerJobPostion(BatchHandlerJobPostion batchHandlerJobPosition) {
        logger.info("------开始批量修改职位--------");
        JobPostionResponse jobPostionResponse = new JobPostionResponse();
        // 返回新增或者更新失败的职位信息
        List<JobPositionFailMess> jobPositionFailMessPojos = new ArrayList<>();
        // 提交的数据
        List<JobPostrionObj> jobPositionHandlerDates = batchHandlerJobPosition.getData();
        // 如果为true, 数据不能删除. 否则,允许删除, data中的数据根据fields_nohash中以外的字段, 判断data中的记录和数据库中已有记录的关系, 进行添加, 修改,删除
        Boolean noDelete = batchHandlerJobPosition.nodelete;
        // 参数有误
        if (null == noDelete) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, ConstantErrorCodeMessage.POSITION_NODELETE_BLANK);
        }
        // 提交的数据为空
        if (com.moseeker.common.util.StringUtils.isEmptyList(batchHandlerJobPosition.getData())) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, ConstantErrorCodeMessage.POSITION_DATA_BLANK);
        }
        Integer companyId;
        if (jobPositionHandlerDates.get(0).getId() != 0) {
            Query query = new Query.QueryBuilder().where("id", jobPositionHandlerDates.get(0).getId()).buildQuery();
            JobPositionRecord jobPostionTemp = jobPositionDao.getRecord(query);
            if (jobPostionTemp != null) {
                companyId = jobPostionTemp.getCompanyId().intValue();
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_JOBPOSITION_COMPANY_ID_BLANK);
            }
        } else {
            // 将该公司下的所有职位查询出来
            companyId = jobPositionHandlerDates.get(0).getCompany_id();
        }
        // 将该公司下所有的部位取出来，用于判断更新或者新增数据时，部门设置是否正确
        Query queryUtilDepartment = new Query.QueryBuilder()
                .where("company_id", companyId)
                .and("disable", 0)
                .buildQuery();
        // 取的该公司下的所有部门信息
        List<HrTeamRecord> hrTeamRecordList = hrTeamDao.getRecords(queryUtilDepartment);
        HashMap hashMapHrTeam = new HashMap();
        // 当更新或者新增jobPosition数据时，如果公司部门信息为空，提示无法更新或者新增jobposition
        if (!com.moseeker.common.util.StringUtils.isEmptyList(hrTeamRecordList)) {
            for (HrTeamRecord hrTeamRecord : hrTeamRecordList) {
                hashMapHrTeam.put(replaceBlank(hrTeamRecord.getName()), hrTeamRecord);
            }
        }
        // 数据库中该公司的职位列表
        Query commonQuery = new Query.QueryBuilder()
                .where("company_id", companyId)
                .and("source", 9)
                .buildQuery();
        List<JobPositionRecord> dbList = jobPositionDao.getRecords(commonQuery);
        HashMap dbListMap = new HashMap();
        List<JobPositionRecord> dbOnlineList = new ArrayList<>();
        for (JobPositionRecord jobPositionRecord : dbList) {
            dbListMap.put(jobPositionRecord.getId(), jobPositionRecord);
            if (jobPositionRecord.getStatus() == 0) {
                dbOnlineList.add(jobPositionRecord);
            }
        }
        // 公司下职能信息
        HashMap jobOccupationMap = new LinkedHashMap();
        Query jobOccupationQuery = new Query.QueryBuilder()
                .where("company_id", companyId)
                .and("status", 1)
                .buildQuery();
        List<JobOccupationDO> jobOccupationList = jobOccupationDao.getDatas(jobOccupationQuery);
        for (JobOccupationDO jobOccupationDO : jobOccupationList) {
            jobOccupationMap.put(jobOccupationDO.getName().trim(), jobOccupationDO);
        }
        // 公司下职位自定义字段
        HashMap jobCustomMap = new LinkedHashMap();
        jobOccupationQuery = new Query.QueryBuilder()
                .where("company_id", companyId)
                .and("status", 1)
                .buildQuery();
        // 公司下职能信息
        List<JobCustomRecord> jobCustomRecordList = jobCustomDao.getRecords(jobOccupationQuery);
        for (JobCustomRecord jobCustomRecord : jobCustomRecordList) {
            jobCustomMap.put(jobCustomRecord.getName().trim(), jobCustomRecord);
        }
        // 需要删除的城市的数据ID列表
        List<Integer> deleteCitylist = new ArrayList<>();
        // 需要更新ES的jobpostionID
        List<Integer> jobPositionIds = new ArrayList<>();
        Integer deleteCounts = 0;
        Integer sourceId = jobPositionHandlerDates.get(0).getSource_id();
        // 删除操作,删除除了data以外的数据库中的数据
        if (!noDelete) {
            if (!com.moseeker.common.util.StringUtils.isEmptyList(dbOnlineList)) {
                // 不需要删除的数据
                List<JobPositionRecord> noDeleJobPostionRecords = new ArrayList<>();
                // 提交的数据处理
                for (JobPositionRecord jobPositionRecord : dbOnlineList) {
                    boolean existed = false;
                    for (JobPostrionObj jobPositionHandlerDate : jobPositionHandlerDates) {
                        // 当ID相同，数据不需要删除
                        if (jobPositionRecord.getId().intValue() == jobPositionHandlerDate.getId()) {
                            noDeleJobPostionRecords.add(jobPositionRecord);
                            existed = true;
                            break;
                        }
                        // 当 source = 9 ，source_id ,company_id, jobnumber 相等时候，不需要删除
                        if (jobPositionRecord.getSource() == 9 && jobPositionRecord.getSourceId().intValue() == jobPositionHandlerDate.getSource_id()
                                && jobPositionRecord.getCompanyId().intValue() == jobPositionHandlerDate.getCompany_id()
                                && jobPositionRecord.getJobnumber().equals(jobPositionHandlerDate.getJobnumber())) {
                            noDeleJobPostionRecords.add(jobPositionRecord);
                            existed = true;
                            break;
                        }
                    }
                    // 需要删除的数据
                    if (!existed) {
                        // 需要删除的职位必须sourceId 必须相同
                        if (jobPositionRecord.getSourceId() == sourceId) {
                            jobPositionRecord.setStatus((byte) 1);
                            jobPositionIds.add(jobPositionRecord.getId());
                        }
                    }
                }
                // 需要删除的列表不为空，否则全部删除
                if (noDeleJobPostionRecords.size() > 0) {
                    // 将总数据和不需要删除的数据取差集
                    dbOnlineList.removeAll(noDeleJobPostionRecords);
                }
                deleteCounts = dbOnlineList.size();
                // 更新jobposition数据，由于做逻辑删除，所以不删除jobpositionExt和jobpositionCity数据
                jobPositionDao.updateRecords(dbOnlineList);
            }
        }
        // 判断哪些数据不需要更新的
        String fieldsNooverwrite = batchHandlerJobPosition.getFields_nooverwrite();
        String[] fieldsNooverwriteStrings = null;
        if (!StringUtils.isNullOrEmpty(fieldsNooverwrite)) {
            fieldsNooverwriteStrings = fieldsNooverwrite.split(",");
        }
        // 判断数据是否需要更新
        String fieldsNohash = batchHandlerJobPosition.getFields_nohash();
        String[] fieldsNohashs = null;
        if (!StringUtils.isNullOrEmpty(fieldsNohash)) {
            fieldsNohashs = fieldsNohash.split(",");
        }
        // 需要更新JobPostion的数据
        List<JobPositionRecord> jobPositionUpdateRecordList = new ArrayList<>();
        // 需要新增JobPostion的数据
        List<JobPositionRecord> jobPositionAddRecordList = new ArrayList<>();
        //  需要更新的JobPositionExtRecord数据
        List<JobPositionExtRecord> jobPositionExtRecordUpdateRecords = new ArrayList<>();
        //  需要新增的JobPositionExtRecord数据
        List<JobPositionExtRecord> jobPositionExtRecordAddRecords = new ArrayList<>();
        // 需要更新的JobPositionCity数据
        List<JobPositionCityRecord> jobPositionCityRecordsUpdatelist = new ArrayList<>();
        // 需要新增的JobPositionCity数据
        List<JobPositionCityRecord> jobPositionCityRecordsAddlist = new ArrayList<>();
        // 处理数据
        for (JobPostrionObj jobPositionHandlerDate : jobPositionHandlerDates) {
            logger.info("提交的数据：" + jobPositionHandlerDate.toString());
            logger.info("提交的部门信息：" + jobPositionHandlerDate.getDepartment());
            JobPositionRecord record = BeanUtils.structToDB(jobPositionHandlerDate, JobPositionRecord.class);
            // 当职位要求为空时候，设置空串
            if (com.moseeker.common.util.StringUtils.isNullOrEmpty(record.getRequirement())) {
                record.setRequirement("");
            }
            int team_id = 0;
            if (!com.moseeker.common.util.StringUtils.isNullOrEmpty(record.getDepartment())) {
                logger.info(record.getDepartment().trim());
                String department = replaceBlank(record.getDepartment());
                HrTeamRecord hrTeamRecord = (HrTeamRecord) hashMapHrTeam.get(department);
                if (hrTeamRecord != null) {
                    logger.info("-----取到TeamId-------");
                    logger.info("----部门ID为---:" + hrTeamRecord.getId());
                    team_id = hrTeamRecord.getId();
                } else {
                    logger.info("-----未取到TeamId-------");
                    logger.info("--部门名称为--:" + record.getDepartment());
                    logger.info("--company_id--:" + record.getCompanyId());
                    logger.info("--JobPositionRecord数据--:" + record.toString());
                    logger.info("--提交的数据--:" + jobPositionHandlerDate.toString());
                    handlerFailMess(ConstantErrorCodeMessage.POSITION_DATA_DEPARTMENT_ERROR, jobPositionFailMessPojos, jobPositionHandlerDate);
                    continue;
                }
            } else {
                record.setDepartment("");
            }
            int jobOccupationId = 0;
            // 验证职能信息是否正确
            if (!com.moseeker.common.util.StringUtils.isNullOrEmpty(jobPositionHandlerDate.getOccupation())) {
                JobOccupationDO jobOccupationDO = (JobOccupationDO) jobOccupationMap.get(jobPositionHandlerDate.getOccupation().trim());
                if (jobOccupationDO != null) {
                    jobOccupationId = jobOccupationDO.getId();
                } else {
                    logger.info("-----职位职能不存在,新建一条职能,职能信息为:" + jobPositionHandlerDate.getOccupation());
                    // 职能错误的时候，自动添加一条职能新
                    JobOccupationDO jobOccupation = new JobOccupationDO();
                    jobOccupation.setCompanyId(companyId);
                    jobOccupation.setStatus((byte) 1);
                    jobOccupation.setName(jobPositionHandlerDate.getOccupation());
                    JobOccupationDO jobOccupationDOTemp = jobOccupationDao.addData(jobOccupation);
                    jobOccupationId = jobOccupationDOTemp.getId();
                    jobOccupationMap.put(jobOccupationDOTemp.getName().trim(), jobOccupationDOTemp);
                }
            }
            // 验证职位自定义字段
            int customId = 0;
            if (!com.moseeker.common.util.StringUtils.isNullOrEmpty(jobPositionHandlerDate.getCustom())) {
                JobCustomRecord jobCustomRecord = (JobCustomRecord) jobCustomMap.get(jobPositionHandlerDate.getCustom());
                if (jobCustomRecord != null) {
                    customId = jobCustomRecord.getId();
                } else {
                    logger.info("-----职位自定义字段错误,职位自定义为:" + jobPositionHandlerDate.getCustom());
                    JobPositionFailMess jobPositionFailMessPojo = new JobPositionFailMess();
                    jobPositionFailMessPojo.setCompanyId(jobPositionHandlerDate.getCompany_id());
                    jobPositionFailMessPojo.setJobNumber(jobPositionHandlerDate.getJobnumber());
                    jobPositionFailMessPojo.setSourceId(jobPositionHandlerDate.getSource_id());
                    jobPositionFailMessPojo.setJobPostionId(jobPositionHandlerDate.getId());
                    JSONObject jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.POSITION_DATA_CUSTOM_ERROR);
                    jobPositionFailMessPojo.setMessage(jsonObject.getString("message").replace("{MESSAGE}", jobPositionHandlerDate.getCustom()));
                    jobPositionFailMessPojo.setStatus(jsonObject.getInteger("status"));
                    jobPositionFailMessPojos.add(jobPositionFailMessPojo);
                    continue;
                }
            }
            // 换算薪资范围
            if (record.getSalaryBottom().intValue() == 0 && record.getSalaryTop().intValue() == 0) {
                record.setSalary("面议");
            }
            if (record.getSalaryBottom().intValue() > 0 && record.getSalaryTop().intValue() == 999) {
                record.setSalary(record.getSalaryBottom().intValue() + "K以上");
            }
            // 按company_id + .source_id + .jobnumber + source=9取得数据
            Query queryUtil = new Query.QueryBuilder()
                    .where(JobPosition.JOB_POSITION.COMPANY_ID.getName(), jobPositionHandlerDate.getCompany_id())
                    .and(JobPosition.JOB_POSITION.SOURCE.getName(), 9)
                    .and(JobPosition.JOB_POSITION.SOURCE_ID.getName(), jobPositionHandlerDate.getSource_id())
                    .and(JobPosition.JOB_POSITION.JOBNUMBER.getName(), jobPositionHandlerDate.getJobnumber())
                    .buildQuery();
            JobPositionRecord jobPositionRecord = jobPositionDao.getRecord(queryUtil);

            // 城市信息
            String city = citys(jobPositionHandlerDate.getCity());
            logger.info("城市信息：{}", city);
            // 城市信息太长时候，需要过滤数据
            if (city.length() > 100) {
                handlerFailMess(ConstantErrorCodeMessage.CITY_TOO_LONG, jobPositionFailMessPojos, jobPositionHandlerDate);
                continue;
            }
            // 更新或者新增数据
            if (jobPositionHandlerDate.getId() != 0 || !com.moseeker.common.util.StringUtils.isEmptyObject(jobPositionRecord)) {  // 数据更新
                // 按company_id + .source_id + .jobnumber + source=9取得数据为空时，按Id进行更新
                if (!com.moseeker.common.util.StringUtils.isEmptyObject(jobPositionRecord)) {
                    record.setId(jobPositionRecord.getId());
                    jobPositionIds.add(jobPositionRecord.getId());
                }
                // 取出数据库中的数据进行对比操作
                JobPositionRecord jobPositionRecordTemp = (JobPositionRecord) dbListMap.get(record.getId());
                if (jobPositionRecordTemp != null) {
                    Query query = new Query.QueryBuilder()
                            .where("pid", jobPositionRecordTemp.getId())
                            .buildQuery();
                    JobPositionExtRecord jobPositionExtRecord = jobPositonExtDao.getRecord(query);
                    if (fieldsNohashs == null ||
                            (!md5(fieldsNohashs, jobPositionRecordTemp, jobPositionExtRecord != null ? jobPositionExtRecord.getExtra() : "").equals(md5(fieldsNohashs, record, jobPositionHandlerDate.getExtra())))) {

                        record.setSourceId(jobPositionRecordTemp.getSourceId());
                        record.setCompanyId(companyId);
                        if (com.moseeker.common.util.StringUtils.isNullOrEmpty(record.getJobnumber())) {
                            record.setJobnumber(jobPositionRecordTemp.getJobnumber());
                        }
                        // 当城市无法转换时，入库为提交的数据
                        if (city != null) {
                            record.setCity(city);
                        }
                        record.setTeamId(team_id);
                        // 设置不需要更新的字段
                        if (fieldsNooverwriteStrings != null && fieldsNooverwriteStrings.length > 0) {
                            for (Field field : record.fields()) {
                                for (String fieldNo : fieldsNooverwriteStrings) {
                                    if (field.getName().equals(fieldNo)) {
                                        record.set(field, jobPositionRecordTemp.getValue(field.getName()));
                                    }
                                }
                            }
                        }
                        // 将需要更新JobPosition的数据放入更新的列表
                        jobPositionUpdateRecordList.add(record);
                        // 需要更新JobPositionCity数据
                        List<JobPositionCityRecord> jobPositionCityRecordList = cityCode(jobPositionHandlerDate.getCity(), record.getId());
                        if (jobPositionCityRecordList != null && jobPositionCityRecordList.size() > 0) {
                            // 更新时候需要把之前的jobPositionCity数据删除
                            deleteCitylist.add(record.getId());
                            jobPositionCityRecordsUpdatelist.addAll(jobPositionCityRecordList);
                        }
                        // 需要更新的JobPositionExra数据
                        if (jobPositionHandlerDate.getExtra() != null || jobOccupationId != 0 || customId != 0) {
                            if (jobPositionExtRecord == null) {
                                jobPositionExtRecord = new JobPositionExtRecord();
                                jobPositionExtRecord.setPid(jobPositionRecordTemp.getId());
                                jobPositionExtRecord.setExtra(jobPositionHandlerDate.getExtra() == null ? "" : jobPositionHandlerDate.getExtra());
                                if (jobOccupationId != 0) {
                                    jobPositionExtRecord.setJobOccupationId(jobOccupationId);
                                }
                                if (customId != 0) {
                                    jobPositionExtRecord.setJobCustomId(customId);
                                }
                                jobPositionExtRecordAddRecords.add(jobPositionExtRecord);
                            } else {
                                jobPositionExtRecord.setExtra(jobPositionHandlerDate.getExtra() == null ? "" : jobPositionHandlerDate.getExtra());
                                if (jobOccupationId != 0) {
                                    jobPositionExtRecord.setJobOccupationId(jobOccupationId);
                                }
                                if (customId != 0) {
                                    jobPositionExtRecord.setJobCustomId(customId);
                                }
                                jobPositionExtRecordUpdateRecords.add(jobPositionExtRecord);
                            }
                        }
                    }
                }
            } else { // 数据的新增
                record.setTeamId(team_id);
                // 当城市无法转换时，入库为提交的数据
                if (city != null) {
                    record.setCity(city);
                }
                logger.info("-- 新增jobPostion数据开始，新增的jobPostion数据为：" + record.toString() + "--");
                Integer pid = jobPositionDao.addRecord(record).getId();
                logger.info("-- 新增jobPostion数据结束,新增职位ID为：" + pid);
                if (pid != null) {
                    jobPositionIds.add(pid);
                    List<JobPositionCityRecord> jobPositionCityRecordList = cityCode(jobPositionHandlerDate.getCity(), record.getId());
                    if (jobPositionCityRecordList != null && jobPositionCityRecordList.size() > 0) {
                        // 新增城市code时，需要先删除jobpostionCity数据
                        jobPositionCityRecordsAddlist.addAll(jobPositionCityRecordList);
                    }
                }
                // 需要新增的JobPosition数据
                jobPositionAddRecordList.add(record);
                if (!com.moseeker.common.util.StringUtils.isNullOrEmpty(jobPositionHandlerDate.getExtra()) || jobOccupationId != 0 || customId != 0) {
                    // 新增jobPostion_ext数据
                    JobPositionExtRecord jobPositionExtRecord = new JobPositionExtRecord();
                    jobPositionExtRecord.setExtra(jobPositionHandlerDate.getExtra() == null ? "" : jobPositionHandlerDate.getExtra());
                    jobPositionExtRecord.setJobOccupationId(jobOccupationId);
                    jobPositionExtRecord.setJobCustomId(customId);
                    jobPositionExtRecord.setPid(pid);
                    jobPositionExtRecordAddRecords.add(jobPositionExtRecord);
                }
            }
        }
        logger.info("----------------------------------------------------------");
        logger.info("需要更新jobPostion数据的条数:" + jobPositionCityRecordsUpdatelist.size());
        logger.info("需要更新jobPostionExt数据的条数:" + jobPositionExtRecordUpdateRecords.size());
        logger.info("新增jobPostionExt数据的条数:" + jobPositionExtRecordAddRecords.size());
        logger.info("新增jobPositionCity数据的条数:" + jobPositionCityRecordsAddlist.size());
        logger.info("需要更新jobPositionCity数据条数:" + jobPositionCityRecordsUpdatelist.size());
        logger.info("---------------------------------------------------------");
        try {
            // 更新jobPostion数据
            if (jobPositionUpdateRecordList.size() > 0) {
                logger.info("-------------更新jobPostion数据开始------------------");
                jobPositionDao.updateRecords(jobPositionUpdateRecordList);
                logger.info("-------------更新jobPostion数据结束------------------");
            }
            // 更新jobPostionExt数据
            if (jobPositionExtRecordUpdateRecords.size() > 0) {
                logger.info("-------------更新jobPostionExt数据开始------------------");
                jobPositonExtDao.updateRecords(jobPositionExtRecordUpdateRecords);
                logger.info("-------------更新jobPostionExt数据结束------------------");
            }
            // 新增jobPostionExt数据
            if (jobPositionExtRecordAddRecords.size() > 0) {
                logger.info("-------------新增jobPostionExt数据开始------------------");
                jobPositonExtDao.addAllRecord(jobPositionExtRecordAddRecords);
                logger.info("-------------新增jobPostionExt数据结束------------------");
            }
            // 新增jobPositionCity数据
            if (jobPositionCityRecordsAddlist.size() > 0) {
                logger.info("-------------新增jobPositionCity数据开始------------------");
                jobPositionCityDao.addAllRecord(jobPositionCityRecordsAddlist.stream().distinct().collect(Collectors.toList()));
                logger.info("-------------新增jobPositionCity数据结束------------------");
            }
            // 更新jobPositionCity数据
            if (jobPositionCityRecordsUpdatelist.size() > 0) {
                if (deleteCitylist.size() > 0) {
                    logger.info("-------------需要删除jobPositionCity的数据：" + deleteCitylist.toString());
                    logger.info("-------------删除jobPositionCity的数据开始------------------");
                    jobPositionCityDao.delJobPostionCityByPids(deleteCitylist);
                    logger.info("-------------删除jobPositionCity的数据结束------------------");
                }
                logger.info("-------------新增jobPositionCity的数据开始------------------");
                jobPositionCityDao.addAllRecord(jobPositionCityRecordsUpdatelist.stream().distinct().collect(Collectors.toList()));
                logger.info("-------------新增jobPositionCity的数据结束------------------");
            }
        } catch (Exception e) {
            logger.info("更新和插入数据发生异常,异常信息为：" + e.getMessage());
            e.printStackTrace();
        }
        jobPostionResponse.setJobPositionFailMessPojolist(jobPositionFailMessPojos);
        jobPostionResponse.setDeleteCounts(deleteCounts);
        jobPostionResponse.setInsertCounts(jobPositionAddRecordList.size());
        jobPostionResponse.setUpdateCounts(jobPositionUpdateRecordList.size());
        jobPostionResponse.setTotalCounts(jobPositionHandlerDates.size());
        if (jobPositionIds.size() > 0) {
            logger.info("插入和新增的jobPositionIds为:" + jobPositionIds.toString());
            // 更新ES Search Engine
            PositionService.UpdateES updataESThread = new PositionService.UpdateES(jobPositionIds);
            Thread thread = new Thread(updataESThread);
            thread.start();
            return ResponseUtils.success(jobPostionResponse);
        }
        logger.info("-------批量修改职位结束---------");
        return ResponseUtils.fail(1, "failed", jobPostionResponse);
    }

    /**
     * 删除职位
     */
    public Response deleteJobposition(Integer id, Integer companyId, String jobnumber, Integer sourceId) {
        JobPositionRecord jobPositionRecord = null;
        if (id != null && id.intValue() != 0) {
            Query queryUtil = new Query.QueryBuilder().where("id", id).buildQuery();
            jobPositionRecord = jobPositionDao.getRecord(queryUtil);

        } else if (companyId != null && jobnumber != null && sourceId != null) {
            Query queryUtil = new Query.QueryBuilder()
                    .where("company_id", companyId)
                    .and("source", 9)
                    .and("source_id", sourceId)
                    .and("jobnumber", jobnumber)
                    .buildQuery();
            jobPositionRecord = jobPositionDao.getRecord(queryUtil);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_DATA_DELETE_PARAM);
        }
        // 删除jobPositionCity数据
        List<Integer> list = new ArrayList<>();
        if (jobPositionRecord != null && jobPositionRecord.getStatus().intValue() != 1) {
            jobPositionRecord.setStatus((byte) 1);
            // 删除JobPostion
            jobPositionDao.updateRecord(jobPositionRecord);
            // 更新ES Search Engine
            list.add(jobPositionRecord.getId());
            PositionService.UpdateES updataESThread = new PositionService.UpdateES(list);
            Thread thread = new Thread(updataESThread);
            thread.start();
            return ResponseUtils.success(0);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_DATA_DELETE_FAIL);
        }
    }


    /**
     * 对JobPositionRecord 进行除了nohash字段之外的值进行MD5进行计算
     */
    private String md5(String[] nohashs, JobPositionRecord jobPositionRecord, String extra) {
        String md5 = null;
        try {
            StringBuffer stringBuffer = new StringBuffer();
            HashMap hashMap = new HashMap();
            for (String nohash : nohashs) {
                hashMap.put(nohash, nohash);
            }
            // 判断JobPosion字段
            for (Field field : jobPositionRecord.fields()) {
                String str = (String) hashMap.get(field.getName());
                if (!com.moseeker.common.util.StringUtils.isNullOrEmpty(str)) {
                    stringBuffer.append(jobPositionRecord.get(field.getName()));
                }
            }
            String str = (String) hashMap.get("extra");
            if (!com.moseeker.common.util.StringUtils.isNullOrEmpty(str)) {
                stringBuffer.append(extra);
            }
            md5 = MD5Util.md5(stringBuffer.toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return md5;
    }


    /**
     * 错误信息处理
     *
     * @param message
     * @param jobPositionFailMessPojos
     * @param jobPostrionObj
     */
    public void handlerFailMess(String message, List<JobPositionFailMess> jobPositionFailMessPojos, JobPostrionObj jobPostrionObj) {
        JobPositionFailMess jobPositionFailMessPojo = new JobPositionFailMess();
        jobPositionFailMessPojo.setCompanyId(jobPostrionObj.getCompany_id());
        jobPositionFailMessPojo.setJobNumber(jobPostrionObj.getJobnumber());
        jobPositionFailMessPojo.setSourceId(jobPostrionObj.getSource_id());
        jobPositionFailMessPojo.setJobPostionId(jobPostrionObj.getId());
        jobPositionFailMessPojo.setDepartment(jobPostrionObj.getDepartment());
        JSONObject jsonObject = JSONObject.parseObject(message);
        jobPositionFailMessPojo.setMessage(jsonObject.getString("message"));
        jobPositionFailMessPojo.setStatus(jsonObject.getInteger("status"));
        jobPositionFailMessPojos.add(jobPositionFailMessPojo);
    }


    /**
     * 将地区或者邮编转换成行政编码
     * //todo 地区入库，jobdb.job_position.city 与 jobdb.job_position_city.code 入库不一致
     * @param citys
     * @param pid
     * @return
     */
    private List<JobPositionCityRecord> cityCode(List<City> citys, Integer pid) {
        List<JobPositionCityRecord> jobPositionCityRecordList = new ArrayList<>();
        try {
            // 将已经查询的到的cityCode放到map中，避免多次查询
            HashMap cityPostCodeMap = new LinkedHashMap();
            // 将从DictCity查询
            HashMap cityMap = new LinkedHashMap();
            if (citys != null && citys.size() > 0 && pid != null) {
                for (City city : citys) {
                    // 查询DictCityPostCode条件
                    Query.QueryBuilder cityCodeQuery = new Query.QueryBuilder();
                    // 查询DictCity条件
                    Query.QueryBuilder cityQuery = new Query.QueryBuilder();
                    JobPositionCityRecord jobPositionCityRecord = new JobPositionCityRecord();
                    jobPositionCityRecord.setPid(pid);
                    logger.info("城市类型：" + city.getType().toLowerCase());
                    logger.info("VAlUE：" + city.getValue());
                    // 城市名字，转换成cityCode，传入的是城市的时候查询dict_city
                    if (city.getType().toLowerCase().equals("text")) {
                        // 判断是不是特殊城市中的
                        String specicalCity = SpecialCtiy.specialCtiyMap.get(city.getValue().toLowerCase());
                        if (specicalCity != null) {
                            city.setValue(specicalCity);
                        }
                        // 判断下是否是中文还是英文
                        if (isChinese(city.getValue())) { // 是中文
                            cityQuery.where("name", city.getValue());
                        } else { // 英文
                            cityQuery.where("ename", city.getValue());
                        }
                        try {
                            DictCityDO dictCityDO = (DictCityDO) cityMap.get(city.getValue());
                            if (dictCityDO != null) {
                                jobPositionCityRecord.setCode(dictCityDO.getCode());
                            } else {
                                dictCityDO = dictCityDao.getData(cityQuery.buildQuery());
                                if (dictCityDO != null && dictCityDO.getCode() != 0) {
                                    jobPositionCityRecord.setCode(dictCityDO.getCode());
                                    cityMap.put(city.getValue(), dictCityDO);
                                }
                            }
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    } else if (city.getType().toLowerCase().equals("postcode")) { // 邮编，转成citycode
                        try {
                            DictCityPostcodeRecord cityPostcodeRecord = (DictCityPostcodeRecord) cityPostCodeMap.get(city.getValue());
                            if (cityPostcodeRecord != null) {
                                jobPositionCityRecord.setCode(Integer.valueOf(cityPostcodeRecord.getCode()));
                            } else {
                                cityCodeQuery.where("postcode", city.getValue());
                                cityPostcodeRecord = dictCityPostcodeDao.getRecord(cityCodeQuery.buildQuery());
                                if (cityPostcodeRecord != null && cityPostcodeRecord.getCode() != null) {
                                    jobPositionCityRecord.setCode(Integer.valueOf(cityPostcodeRecord.getCode()));
                                    cityPostCodeMap.put(city.getValue(), cityPostcodeRecord);
                                }
                            }
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    } else if (city.getType().toLowerCase().equals("citycode")) { // citycode 直接存储
                        jobPositionCityRecord.setCode(Integer.valueOf(city.getValue()));
                    } else if (city.getType().toLowerCase().equals("fuzzypostcode")) { // 模糊邮编，取邮编的前四位查询
                        String postCodeTemp = city.getValue().substring(0, 4);
                        DictCityPostcodeRecord cityPostcodeRecord = (DictCityPostcodeRecord) cityPostCodeMap.get(postCodeTemp);
                        if (cityPostcodeRecord != null) {
                            jobPositionCityRecord.setCode(Integer.valueOf(cityPostcodeRecord.getCode()));
                        } else {
                            cityPostcodeRecord = dictCityPostcodeDao.fuzzyGetCityPostCode(postCodeTemp);
                            if (cityPostcodeRecord != null && cityPostcodeRecord.getCode() != null) {
                                jobPositionCityRecord.setCode(Integer.valueOf(cityPostcodeRecord.getCode()));
                                cityPostCodeMap.put(city.getValue(), cityPostcodeRecord);
                            }
                        }
                    }
                    // 如果cityCode不入库
                    if (jobPositionCityRecord.getCode() != null) {
                        jobPositionCityRecordList.add(jobPositionCityRecord);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return jobPositionCityRecordList;
    }

    /**
     * 将city列表转换成城市以逗号分开
     */
    private String citys(List<City> list) {
        StringBuffer stringBuffer = new StringBuffer();
        if (list != null && list.size() > 0) {
            // 将已经查询的到的cityCode放到map中，避免多次查询
            HashMap cityPostCodeMap = new LinkedHashMap();
            int i = 0;
            for (City city : list) {
                Query.QueryBuilder cityCodeQuery = new Query.QueryBuilder();
                if (city.getType().toLowerCase().equals("text")) { // 城市名字，转换成cityCode
                    // 判断是不是特殊城市中的
                    String specicalCity = SpecialCtiy.specialCtiyMap.get(city.getValue().toLowerCase());
                    if (specicalCity != null) {
                        stringBuffer.append(specicalCity);
                    } else {
                        if (isChinese(city.getValue())) { // 是中文
                            stringBuffer.append(city.getValue());
                        } else { // 英文
                            cityCodeQuery.where("ename", city.getValue());
                            try {
                                DictCityDO dictCityDO = (DictCityDO) cityPostCodeMap.get(city.getValue());
                                if (dictCityDO != null) {
                                    stringBuffer.append(dictCityDO.getName());
                                } else {
                                    dictCityDO = dictCityDao.getData(cityCodeQuery.buildQuery()); //dictOccupationDao.dictCityDO(cityCodeQuery);
                                    if (dictCityDO != null && dictCityDO.getCode() != 0) {
                                        stringBuffer.append(dictCityDO.getName());
                                        cityPostCodeMap.put(city.getValue(), dictCityDO);
                                    } else {
                                        stringBuffer.append(city.getValue());
                                    }
                                }
                            } catch (Exception e) {
                                logger.error(e.getMessage(), e);
                            }
                        }
                    }
                } else if (city.getType().toLowerCase().equals("postcode")) { // 邮编，转成城市名
                    try {
                        DictCityPostcodeRecord cityPostcodeRecord = (DictCityPostcodeRecord) cityPostCodeMap.get(city.getValue());
                        if (cityPostcodeRecord != null) {
                            stringBuffer.append(cityPostcodeRecord.getCity());
                        } else {

                            cityCodeQuery.clear();
                            cityCodeQuery.where("postcode", city.getValue());
                            cityPostcodeRecord = dictCityPostcodeDao.getRecord(cityCodeQuery.buildQuery());
                            if (cityPostcodeRecord != null && cityPostcodeRecord.getCity() != null) {
                                if (com.moseeker.common.util.StringUtils.isNullOrEmpty(cityPostcodeRecord.getCity())) {
                                    stringBuffer.append(cityPostcodeRecord.getProvince());
                                } else {
                                    stringBuffer.append(cityPostcodeRecord.getCity());
                                }
                                cityPostCodeMap.put(city.getValue(), cityPostcodeRecord);
                            }
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                } else if (city.getType().toLowerCase().equals("citycode")) { // citycode 直接存储
                    try {
                        DictCityDO dictCityDO = (DictCityDO) cityPostCodeMap.get(city.getValue());
                        if (dictCityDO != null) {
                            stringBuffer.append(dictCityDO.getName());
                        } else {
                            cityCodeQuery.clear();
                            cityCodeQuery.where("code", city.getValue());
                            dictCityDO = dictCityDao.getData(cityCodeQuery.buildQuery());
                            if (dictCityDO != null && dictCityDO.getName() != null) {
                                stringBuffer.append(dictCityDO.getName());
                                cityPostCodeMap.put(city.getValue(), dictCityDO);
                            }
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                } else if (city.getType().toLowerCase().equals("fuzzypostcode")) {  // 模糊邮编查询，转为城市名字
                    try {
                        String postCodeTemp = city.getValue().substring(0, 4);
                        DictCityPostcodeRecord cityPostcodeRecord = (DictCityPostcodeRecord) cityPostCodeMap.get(postCodeTemp);
                        if (cityPostcodeRecord != null) {
                            stringBuffer.append(cityPostcodeRecord.getCity());
                        } else {
                            cityPostcodeRecord = dictCityPostcodeDao.fuzzyGetCityPostCode(postCodeTemp);
                            if (cityPostcodeRecord != null && cityPostcodeRecord.getCity() != null) {
                                if (!com.moseeker.common.util.StringUtils.isEmptyObject(cityPostcodeRecord.getCity())) {
                                    stringBuffer.append(cityPostcodeRecord.getCity());
                                } else {
                                    stringBuffer.append(cityPostcodeRecord.getProvince());
                                }
                                cityPostCodeMap.put(city.getValue(), cityPostcodeRecord);
                            }
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                i = i + 1;
                if (i != list.size() && com.moseeker.common.util.StringUtils.isNotNullOrEmpty(stringBuffer.toString()) && !stringBuffer.toString().endsWith(",")) {
                    stringBuffer.append("，");
                }
            }
            if (stringBuffer.toString().endsWith("，")) {
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            }
        }
        return stringBuffer.toString();
    }

    public List<HrThirdPartyPositionDO> getThirdPartyPositions(Query query) {
        return thirdpartyPositionDao.getDatas(query);

    }

    /**
     * 获取微信端职位列表
     *
     * @param query 查询条件
     * @return 微信端职位列表信息
     */
    public List<WechatPositionListData> getPositionList(WechatPositionListQuery query) {

        List<WechatPositionListData> dataList = new ArrayList<>();

        try {
            String childCompanyId = "";
            String companyId = "";

            logger.info("getPositionList did:{},company_id:{},query:{}", query.getDid(), query.getCompany_id(), BeanUtils.convertStructToJSON(query));

            if (query.isSetDid() && query.getDid() != 0) {
                // 如果有did, 赋值 childCompanyId
                childCompanyId = String.valueOf(query.getDid());
            } else {
                Query qu = new Query.QueryBuilder()
                        .where("parent_id", companyId)
                        .buildQuery();
                List<Hrcompany> companies = hrCompanyDao.getCompanies(qu);

                List<Integer> cIds = new ArrayList<>();
                if (companies.size() > 0) {
                    cIds = companies.stream().map(Hrcompany::getId).collect(Collectors.toList());
                }
                cIds.add(query.getCompany_id());
                companyId = org.apache.commons.lang.StringUtils.join(cIds.toArray(), ",");

                logger.info("companyId:" + companyId);
            }

            logger.info("<><><><><><><><><><><>");
            logger.info("companyId: " + companyId);
            logger.info("childCompanyId: " + childCompanyId);
            logger.info("<><><><><><><><><><><>");
            logger.info("query.getKeywords():" + query.getKeywords());
            logger.info("query.getCities():" + query.getCities());
            logger.info("query.getIndustries():" + query.getIndustries());
            logger.info("query.getOccupations():" + query.getOccupations());
            logger.info("query.getScale():" + query.getScale());
            logger.info("query.getEmployment_type(): " + query.getEmployment_type());
            logger.info("query.getCandidate_source():" + query.getCandidate_source());
            logger.info("query.getExperience():" + query.getExperience());
            logger.info("query.getDegree():" + query.getDegree());
            logger.info("query.getSalary():" + query.getSalary());
            logger.info("query.getPage_from(): " + query.getPage_from());
            logger.info("query.getPage_size(): " + query.getPage_size());
            logger.info("query.getDepartment(): " + query.getDepartment());
            logger.info("query.getCustom(): " + query.getCustom());
            logger.info("<><><><><><><><><><><>");
            String cities = query.getCities();
            if (StringUtils.isNotNullOrEmpty(cities) && commonPositionUtils.appendCity(cities) && !cities.contains("全国'")) {
                cities = cities + ",全国";
                query.setCities(cities);
            }
            //获取 pid list
            Response ret = searchengineServices.query(
                    query.getKeywords(),
                    query.getCities(),
                    query.getIndustries(),
                    query.getOccupations(),
                    query.getScale(),
                    query.getEmployment_type(),
                    query.getCandidate_source(),
                    query.getExperience(),
                    query.getDegree(),
                    query.getSalary(),
                    companyId,
                    query.getPage_from(),
                    query.getPage_size(),
                    childCompanyId,
                    query.getDepartment(),
                    true,
                    query.getCustom());

            if (ret.getStatus() == 0 && !StringUtils.isNullOrEmpty(ret.getData())) {
                // 通过 pid 列表查询 position 信息
                JSONObject jobj = JSON.parseObject(ret.getData());
                JSONArray jdIdJsonArray = jobj.getJSONArray("jd_id_list");
                List<Integer> jdIdList = jdIdJsonArray.stream().map(m -> Integer.valueOf(String.valueOf(m))).collect(Collectors.toList());
                logger.info("jdIdList: " + jdIdList);
                Condition con = new Condition("id", jdIdList.toArray(), ValueOp.IN);
                Query q = new Query.QueryBuilder().where(con).buildQuery();
                List<JobPositionRecord> jobRecords = positionEntity.getPositions(q);
                //List<JobPositionRecord> jobRecords = jobPositionDao.getRecords(q);
                //Map<Integer, Set<String>> cityMap = commonPositionUtils.handlePositionCity(jdIdList);
                for (int i = 0; i < jdIdList.size(); i++) {
                    int positionId = jdIdList.get(i);
                    for (JobPositionRecord jr : jobRecords) {
                        if (positionId == jr.getId()) {
                            logger.info("pid: " + String.valueOf(jr.getId()));
                            WechatPositionListData e = new WechatPositionListData();
                            e.setTitle(jr.getTitle());
                            e.setId(jr.getId());
                            // 数据库的 salary_top 和 salary_bottom 默认是 NULL 不是 0
                            // 所以这里需要对这两个字段做 null pointer 检查
                            if (jr.getSalaryTop() == null) {
                                e.setSalary_top(0);
                            } else {
                                e.setSalary_top(jr.getSalaryTop());
                            }
                            if (jr.getSalaryBottom() == null) {
                                e.setSalary_bottom(0);
                            } else {
                                e.setSalary_bottom(jr.getSalaryBottom());
                            }
                            e.setPublish_date(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(jr.getUpdateTime()));
                            e.setDepartment(jr.getDepartment());
                            e.setVisitnum(jr.getVisitnum());
                            e.setIn_hb(jr.getHbStatus() > 0);
                            e.setCount(jr.getCount());
                            e.setCity(jr.getCity());
                            e.setPriority(jr.getPriority());
                            e.setPublisher(jr.getPublisher()); // will be used for fetching sub company info
                            dataList.add(e);
                            break;
                        }
                    }
                }
                logger.info(dataList.toString());
                // 获取公司信息，拼装 company abbr, logo 等信息
                Map<Integer /* publisher id */, HrCompanyDO> publisherCompanyMap = new HashMap<>();
                //QueryUtil hrm = new QueryUtil();
                Query.QueryBuilder hrm = new Query.QueryBuilder();
                Set<Integer> publisherSet = dataList.stream().map(WechatPositionListData::getPublisher)
                        .collect(Collectors.toSet());
                // publisherList 应该不为空
                // 如果 publisherList 为空，那么返回空 ArrayList
                if (publisherSet == null || publisherSet.size() == 0) {
                    return new ArrayList<>();
                }
                // 根据 publisherSet 查询 hr_company_account_list
                Condition condition = new Condition("account_id", publisherSet.toArray(), ValueOp.IN);
                hrm.where(condition);
                List<HrCompanyAccountDO> companyAccountList = hrCompanyAccountDao.getDatas(hrm.buildQuery(), HrCompanyAccountDO.class);

                for (HrCompanyAccountDO hrCompanyAccount : companyAccountList) {
                    hrm = new Query.QueryBuilder();
                    hrm.where("id", hrCompanyAccount.getCompanyId());
                    HrCompanyDO companyInfo = hrCompanyDao.getData(hrm.buildQuery(), HrCompanyDO.class);
                    publisherCompanyMap.put(hrCompanyAccount.accountId, companyInfo);

                }

                //拼装 company 相关内容
                dataList = dataList.stream().map(s -> {
                    s.setCompany_abbr(publisherCompanyMap.get(s.getPublisher()) == null ? "" : publisherCompanyMap.get(s.getPublisher()).getAbbreviation());
                    s.setCompany_logo(publisherCompanyMap.get(s.getPublisher()) == null ? "" : publisherCompanyMap.get(s.getPublisher()).getLogo());
                    s.setCompany_name(publisherCompanyMap.get(s.getPublisher()) == null ? "" : publisherCompanyMap.get(s.getPublisher()).getName());
                    return s;
                }).collect(Collectors.toList());

            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<>();
        } finally {
            // do nothing
        }
        return dataList;
    }

    /**
     * 获得红包活动的分享信息
     *
     * @param hb_config_id 红包活动id
     * @return 红包活动分享信息
     */
    public WechatShareData getShareInfo(int hb_config_id) {
        WechatShareData result = new WechatShareData();

        Query qu = new Query.QueryBuilder()
                .where("id", hb_config_id).buildQuery();
        try {
            HrHbConfigDO hbConfig = hrHbConfigDao.getData(qu, HrHbConfigDO.class);
            result.setCover(hbConfig.getShareImg());
            result.setTitle(hbConfig.getShareTitle());
            result.setDescription(hbConfig.getShareDesc());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 返回指定 pid 的红包信息
     *
     * @param pids pids
     * @return pids 对应职位红包活动的额外信息
     */
    public List<RpExtInfo> getPositionListRpExt(List<Integer> pids) {
        List<RpExtInfo> result = new ArrayList<>();
        // 获取 company_id
        int company_id = 0;
        if (pids.size() > 0) {
            Query qus = new Query.QueryBuilder()
                    .where("id", pids.get(0)).buildQuery();
            Position p = jobPositionDao.getPositionByQuery(qus);

            company_id = p.getCompany_id();
        } else {
            return result;
        }
        Condition condition = new Condition("type", new Object[]{2, 3}, ValueOp.IN);
        // 获取正在运行的转发类红包活动集合
        Query qu = new Query.QueryBuilder()
                .where("status", "3")
                .and("company_id", company_id)
                .and(condition)
                .buildQuery();
        logger.info("qu:", qu.toString());
        List<HrHbConfigDO> hbConfigs = hrHbConfigDao.getDatas(qu, HrHbConfigDO.class);
        List<Integer> hbConfgIds = hbConfigs.stream().map(HrHbConfigDO::getId).collect(Collectors.toList());

        String allHbConfigIdsFilterString = Arrays.toString(hbConfgIds.toArray());

        logger.info("pids: " + pids.toString());
        logger.info("allHbConfigIdsFilterString: " + allHbConfigIdsFilterString);

        for (Integer pid : pids) {
            //对于每个 pid，先获取 position
            RpExtInfo rpExtInfo = new RpExtInfo();

            qu = new Query.QueryBuilder().where("id", pid).buildQuery();
            Position p = jobPositionDao.getPositionByQuery(qu);

            if (p.getHb_status() > 0) {
                // 该职位参与了两个红包活动
                // 获取 binding 记录
                Condition cons = new Condition("hb_config_id", hbConfgIds.toArray(), ValueOp.IN);
                qu = new Query.QueryBuilder().where("position_id", p.getId()).and(cons).buildQuery();
                List<HrHbPositionBindingDO> bindings = hrHbPositionBindingDao.getDatas(qu, HrHbPositionBindingDO.class);
                //获取binding ids
                logger.info(bindings.toString());
                //获取 binding ids
                List<Integer> bindingIds = bindings.stream().map(HrHbPositionBindingDO::getId).collect(Collectors.toList());
                //获取 binding 所对应的红包活动 id
                Set<Integer> hbConfigIdsSet = bindings.stream().map(HrHbPositionBindingDO::getHbConfigId).collect(Collectors.toSet());

                // 得到对应的红包活动 pojo （2个）
                List<HrHbConfigDO> configs = hbConfigs.stream().filter(s -> hbConfigIdsSet.contains(s.getId())).collect(Collectors.toList());

                logger.info(configs.toString());

                // 如果任意一个对象是非员工，设置成 false
                rpExtInfo.setEmployee_only(true);
                for (HrHbConfigDO config : configs) {
                    if (config.target > 0) {
                        rpExtInfo.setEmployee_only(false);
                        break;
                    }
                }

                String bindingIdsFilterString = Arrays.toString(bindingIds.toArray());
                // 根据 binding 获取 hb_items 记录
                Condition condition1 = new Condition("binding_id", bindingIds.toArray(), ValueOp.IN);
                qu = new Query.QueryBuilder().where(condition1).and("wxuser_id", 0).buildQuery();
                List<HrHbItemsDO> remainItems = hrHbItemsDao.getDatas(qu, HrHbItemsDO.class);
                Double remain = remainItems.stream().mapToDouble(HrHbItemsDO::getAmount).sum();
                Integer remainInt = toIntExact(round(remain));
                if (remainInt < 0) {
                    remainInt = 0;
                }

                rpExtInfo.setPid(p.getId());
                rpExtInfo.setRemain(remainInt);

                logger.info(rpExtInfo.toString());

                result.add(rpExtInfo);

            } else {
                // 如果该职位已经不属于任何红包活动，这不做任何操作
                logger.warn("pid: " + p.getId() + " 已经不属于任何红包活动");
            }
        }
        return result;
    }

    /**
     * @param company_id      公司ID
     * @param department_name 部门名称
     */
    public Response getTeamIdbyDepartmentName(Integer company_id, String department_name) {
        if (com.moseeker.common.util.StringUtils.isNullOrEmpty(department_name)) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.POSTION_COMPANY_DEPARTMENTI_PARAMETER_BLANK);
        }
        Query queryUtilDepartment = new Query.QueryBuilder()
                .where("company_id", company_id)
                .and("disable", 0)
                .and("name", department_name)
                .buildQuery();

        HrTeamRecord hrTeamRecord = hrTeamDao.getRecord(queryUtilDepartment);
        if (com.moseeker.common.util.StringUtils.isEmptyObject(hrTeamRecord)) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_DATA_DEPARTMENT_ERROR);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("team_id", hrTeamRecord.getId());
        return ResponseUtils.success(hashMap);
    }

    /**
     * 根据 hbConfigId 返回职位列表
     *
     * @param hbConfigId 红包活动id
     * @return 红包职位列表
     */
    public List<WechatRpPositionListData> getRpPositionList(int hbConfigId) {
        List<WechatRpPositionListData> result = new ArrayList<>();
        Query qu = new Query.QueryBuilder()
                .where("hb_config_id", hbConfigId)
                .buildQuery();
        List<HrHbPositionBindingDO> bindings = hrHbPositionBindingDao.getDatas(qu, HrHbPositionBindingDO.class);
        List<Integer> pids = bindings.stream().map(HrHbPositionBindingDO::getPositionId).collect(Collectors.toList());
        String pidFilter = "[" + org.apache.commons.lang.StringUtils.join(pids.toArray(), ",") + "]";
        Condition condition = new Condition("id", pids.toArray(), ValueOp.IN);
        Query q = new Query.QueryBuilder().where(condition).orderBy("priority").buildQuery();
        List<JobPositionRecord> jobRecords = jobPositionDao.getRecords(q);

        // filter 出已经发完红包的职位
        jobRecords = jobRecords.stream().filter(p -> p.getHbStatus() > 0).collect(Collectors.toList());

        // 拼装职位信息
        for (JobPositionRecord jr : jobRecords) {
            WechatRpPositionListData e = new WechatRpPositionListData();
            e.setTitle(jr.getTitle());
            e.setId(jr.getId());
            e.setSalary_top(jr.getSalaryTop());
            e.setSalary_bottom(jr.getSalaryBottom());
            e.setPublish_date(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(jr.getUpdateTime()));
            e.setDepartment(jr.getDepartment());
            e.setVisitnum(jr.getVisitnum());
            e.setIn_hb(true);
            e.setCount(jr.getCount());
            e.setCity(jr.getCity());
            result.add(e);
        }

        // 拼装公司信息
        qu = new Query.QueryBuilder().where("id", hbConfigId).buildQuery();
        Integer companyId = hrHbConfigDao.getData(qu, HrHbConfigDO.class).getCompanyId();
        qu = new Query.QueryBuilder().where("id", companyId).buildQuery();
        HrCompanyDO company = hrCompanyDao.getData(qu, HrCompanyDO.class);
        result.forEach(s -> {
            s.setCompany_abbr(company == null ? "" : company.getAbbreviation());
            s.setCompany_logo(company == null ? "" : company.getLogo());
            s.setCompany_name(company == null ? "" : company.getName());
        });

        // 拼装红包信息
        List<RpExtInfo> rpExtInfoList = getPositionListRpExt(pids);

        result.forEach(s -> {
            RpExtInfo rpInfo = rpExtInfoList.stream().filter(e -> e.getPid() == s.getId()).findFirst().orElse(
                    null);
            if (rpInfo != null) {
                s.setRemain(rpInfo.getRemain());
                s.setEmployee_only(rpInfo.isEmployee_only());
            }
        });
        return result;
    }

    private List<DictAlipaycampusJobcategoryRecord> getAllDictAlipaycampusJobcategory() {

        if (alipaycampusJobcategory == null) {

            synchronized (PositionService.class) {
                if (alipaycampusJobcategory == null) {
                    alipaycampusJobcategory = dictAlipaycampusJobcategoryDao.getRecords(null);
                }
            }
        }


        return alipaycampusJobcategory;
    }


    /**
     * @param positionId 职位id
     * @param channel    部门名称
     */
    @CounterIface
    public Response getPositionForThirdParty(int positionId, int channel) {
        Query query = new Query.QueryBuilder().where("id", positionId).buildQuery();
        JobPositionRecord positionRecord = jobPositionDao.getRecord(query);
        int company_id;
        if (positionRecord == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_POSITION_NOTEXIST);
        } else {
            company_id = positionRecord.getCompanyId();
            int publisher = positionRecord.getPublisher();
            // 获取子账号公司信息
            query = new Query.QueryBuilder().where("account_id", publisher).buildQuery();
            HrCompanyAccountRecord record = hrCompanyAccountDao.getRecord(query);
            if (record != null && record.getCompanyId() != null) {
                if (company_id != record.getCompanyId()) {
                    company_id = record.getCompanyId();
                }
            }
        }

        HrCompanyRecord hrCompanyRecord = hrCompanyDao.getCompanyRecordById(company_id);

        PositionForAlipaycampusPojo positionForAlipaycampusPojo = new PositionForAlipaycampusPojo();
        positionForAlipaycampusPojo.setSource_id(positionRecord.getId().toString());
        positionForAlipaycampusPojo.setJob_name(positionRecord.getTitle());
        positionForAlipaycampusPojo.setJob_desc(PositionChangeUtil.convertDescription(positionRecord.getAccountabilities(), positionRecord.getRequirement()));


        // 职业分类
        List<DictAlipaycampusJobcategoryRecord> allDictAlipaycampusJobcategory = this.getAllDictAlipaycampusJobcategory();
        for (int i = 0; i < allDictAlipaycampusJobcategory.size(); i++) {
            DictAlipaycampusJobcategoryRecord dictAlipaycampusJobcategoryRecord = allDictAlipaycampusJobcategory.get(i);
            String keyword = dictAlipaycampusJobcategoryRecord.getName();
            if (positionRecord.getTitle().indexOf(keyword) >= 0) {
                int level = dictAlipaycampusJobcategoryRecord.getLevel();
                if (level == 3) {
                    query = new Query.QueryBuilder().where("code", dictAlipaycampusJobcategoryRecord.getParentCode()).buildQuery();
                    dictAlipaycampusJobcategoryRecord = dictAlipaycampusJobcategoryDao.getRecord(query);
                    // level should equals 2 here.
                    level = dictAlipaycampusJobcategoryRecord.getLevel();
                }

                if (level == 2) {
                    positionForAlipaycampusPojo.setJob_tier_two_code(dictAlipaycampusJobcategoryRecord.getCode());
                    positionForAlipaycampusPojo.setJob_tier_two_name(dictAlipaycampusJobcategoryRecord.getName());
                    query = new Query.QueryBuilder().where("code", dictAlipaycampusJobcategoryRecord.getParentCode()).buildQuery();
                    dictAlipaycampusJobcategoryRecord = dictAlipaycampusJobcategoryDao.getRecord(query);
                    // level should equals 1 here.
                    level = dictAlipaycampusJobcategoryRecord.getLevel();

                }

                if (level == 1) {
                    positionForAlipaycampusPojo.setJob_tier_one_code(dictAlipaycampusJobcategoryRecord.getCode());
                    positionForAlipaycampusPojo.setJob_tier_one_name(dictAlipaycampusJobcategoryRecord.getName());

                }

                break;
            }

        }

        if (positionForAlipaycampusPojo.getJob_tier_one_code() == null) {
            positionForAlipaycampusPojo.setJob_tier_one_code("opj_e4tvvgcavs2j");
            positionForAlipaycampusPojo.setJob_tier_one_name("其他类型");
        }


        positionForAlipaycampusPojo.setJob_hire_number(positionRecord.getCount());
        positionForAlipaycampusPojo.setJob_perk(positionRecord.getBenefits());

        WorkType workType = WorkType.instanceFromInt(positionRecord.getEmploymentType());
        positionForAlipaycampusPojo.setJob_type(Integer.valueOf(WorkTypeChangeUtil.getAlipaycampusWorkType(workType).getValue()));


        Degree degree = Degree.instanceFromCode(String.valueOf(positionRecord.getDegree()));
        positionForAlipaycampusPojo.setJob_rq_education(Integer.valueOf(DegreeChangeUtil.getAlipaycampusDegree(degree).getValue()));


        //positionForAlipaycampusPojo.setJob_resume_lg();
        if (positionRecord.getSalaryBottom() != null) {
            positionForAlipaycampusPojo.setPayment_min(1000 * positionRecord.getSalaryBottom());
        } else {
            positionForAlipaycampusPojo.setPayment_min(0);
        }
        if (positionRecord.getSalaryTop() != null && positionRecord.getSalaryTop() != 999) {
            positionForAlipaycampusPojo.setPayment_max(1000 * positionRecord.getSalaryTop());
        }

        positionForAlipaycampusPojo.setPayment_unit(2);//month

        // 公司
        positionForAlipaycampusPojo.setCompany_source(String.valueOf(company_id));
        positionForAlipaycampusPojo.setCompany_name(hrCompanyRecord.getAbbreviation());
        positionForAlipaycampusPojo.setCompany_lawname(hrCompanyRecord.getAbbreviation());
        positionForAlipaycampusPojo.setCompany_logo("https://cdn.moseeker.com/" + hrCompanyRecord.getLogo());

        // 省市
        query = new Query.QueryBuilder().where("pid", positionRecord.getId()).buildQuery();
        JobPositionCityRecord jobPositionCityRecord = jobPositionCityDao.getRecord(query);

        if (jobPositionCityRecord == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_DATA_NOCITYCODE_ERROR);
        }
        Integer citycode = jobPositionCityRecord.getCode();

        if (citycode == null) {
            // todo
            return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_DATA_NOCITYCODE_ERROR);
        }

        // 全国的职位需要忽略吗？
        if (citycode == 111111) {
            // todo.
            return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_DATA_ALLCITY_ERROR);

        }


        query = new Query.QueryBuilder().where("id", citycode).buildQuery();

        DictAlipaycampusCityRecord dictAlipaycampusCityRecord = dictAlipaycampusCityDao.getRecord(query);

        if (dictAlipaycampusCityRecord != null && dictAlipaycampusCityRecord.getLevel() == 3) {
            citycode = dictAlipaycampusCityRecord.getPid();
            query = new Query.QueryBuilder().where("id", citycode).buildQuery();
            dictAlipaycampusCityRecord = dictAlipaycampusCityDao.getRecord(query);
        }

        if (dictAlipaycampusCityRecord != null && dictAlipaycampusCityRecord.getLevel() == 2) {
            positionForAlipaycampusPojo.setArea_city_code(dictAlipaycampusCityRecord.getId().toString());
            positionForAlipaycampusPojo.setArea_city_name(dictAlipaycampusCityRecord.getName());
            citycode = dictAlipaycampusCityRecord.getPid();
            query = new Query.QueryBuilder().where("id", citycode).buildQuery();
            dictAlipaycampusCityRecord = dictAlipaycampusCityDao.getRecord(query);
        }

        if (dictAlipaycampusCityRecord != null && dictAlipaycampusCityRecord.getLevel() == 1) {
            positionForAlipaycampusPojo.setArea_province_code(dictAlipaycampusCityRecord.getId());
            positionForAlipaycampusPojo.setArea_province_name(dictAlipaycampusCityRecord.getName());

        }

        // 直辖市特殊处理
        if (dictAlipaycampusCityRecord == null || positionForAlipaycampusPojo.getArea_city_code() == null
                || positionForAlipaycampusPojo.getArea_province_code() == 0) {
            Integer specialcitycode = jobPositionCityRecord.getCode();
            specialcitycode = specialcitycode / 10000 * 10000; // 取 code的前面2位数 + 4个0， 获取省份code

            switch (specialcitycode) {
                case 310000:
                    positionForAlipaycampusPojo.setArea_province_code(310100);
                    positionForAlipaycampusPojo.setArea_province_name("上海市");
                    positionForAlipaycampusPojo.setArea_city_code("310100");
                    positionForAlipaycampusPojo.setArea_city_name("上海市");
                    break;
                case 110000:
                    positionForAlipaycampusPojo.setArea_province_code(110100);
                    positionForAlipaycampusPojo.setArea_province_name("北京市");
                    positionForAlipaycampusPojo.setArea_city_code("110100");
                    positionForAlipaycampusPojo.setArea_city_name("北京市");
                    break;
                case 120000:
                    positionForAlipaycampusPojo.setArea_province_code(120100);
                    positionForAlipaycampusPojo.setArea_province_name("天津市");
                    positionForAlipaycampusPojo.setArea_city_code("120100");
                    positionForAlipaycampusPojo.setArea_city_name("天津市");
                    break;
                case 500000:
                    positionForAlipaycampusPojo.setArea_province_code(500100);
                    positionForAlipaycampusPojo.setArea_province_name("重庆市");
                    positionForAlipaycampusPojo.setArea_city_code("500100");
                    positionForAlipaycampusPojo.setArea_city_name("重庆市");
                    break;
                default:
                    logger.info("city code unknown,code:" + specialcitycode + ",position_id:" + positionId);
            }

        }


        // 时间 ,3个月过期，1天前刷新。
        positionForAlipaycampusPojo.setGmt_expired(String.valueOf(positionRecord.getUpdateTime().getTime() + 7776000000L));
        positionForAlipaycampusPojo.setGmt_refresh(String.valueOf(System.currentTimeMillis() - 864000000L));

        return ResponseUtils.successWithoutStringify(positionForAlipaycampusPojo.toString());
    }

    /**
     * @param channel    5，支付宝
     * @param type       0：创建、更新， 1 刷新， 2 下架
     * @param start_time "2017-04-05 11:34:43"
     * @param end_time
     */
    @CounterIface
    public List<Integer> getPositionListForThirdParty(int channel, int type, String start_time, String end_time) {
        Query query;
        switch (type) {
            case 0: //创建or更新
                query = new Query.QueryBuilder().select("id").where("status", 0)
                        .and(new Condition("update_time", start_time, ValueOp.GE))
                        .and(new Condition("update_time", end_time, ValueOp.LT)).buildQuery();
                break;
            case 1: //刷新
                query = new Query.QueryBuilder().select("id").where("status", 0)
                        .and(new Condition("update_time", start_time, ValueOp.GE))
                        .and(new Condition("update_time", end_time, ValueOp.LT)).buildQuery();
                break;
            case 2:
                query = new Query.QueryBuilder().select("id").where(new Condition("status", 0, ValueOp.NEQ))
                        .and(new Condition("update_time", start_time, ValueOp.GE))
                        .and(new Condition("update_time", end_time, ValueOp.LT)).buildQuery();
                break;
            default:
                return null;
        }


        List<JobPositionDO> jobPositionList = jobPositionDao.getPositions(query);
        List<Integer> positionlist = null;
        if (jobPositionList != null) {
            positionlist = new ArrayList<>(jobPositionList.size());
            for (JobPositionDO position : jobPositionList) {
                positionlist.add(position.getId());

            }
        }

        return positionlist;
    }


    private String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 根据查询条件查找职位信息
     * 职位数据如果存在job_position_city 数据，则使用职位数据如果存在job_position_city对应城市，否则直接取city
     * @param query
     * @return
     */
    public List<JobPositionRecord> getPositionRecords(Query query) {
        return positionEntity.getPositions(query);
    }

    /**
     * 内部线程类 用于更改ES索引
     */
    private class UpdateES extends Thread {
        private List<Integer> list;

        public UpdateES(List<Integer> list) {
            this.list = list;
        }

        public void run() {
            String position = "";
            try {
                logger.info("---Start ES Search Engine---");
                Thread.sleep(400);
                for (Integer jobPositionId : list) {
                    Response result = getPositionById(jobPositionId);
                    if (StringUtils.isEmptyObject(result.data)) {
                        continue;
                    }
                    position = result.data;
                    Map position_map = JSON.parseObject(position, Map.class);
                    if (StringUtils.isEmptyObject(position_map.get("company_id"))) {
                        continue;
                    }
                    String company_id = BeanUtils.converToString(position_map.get("company_id"));
                    Query query = new Query.QueryBuilder().where("id", company_id).buildQuery();

                    List<Hrcompany> company_maps = hrCompanyDao.getDatas(query, Hrcompany.class);
                    if (company_maps != null && company_maps.size() > 0) {

                        Hrcompany company_map = company_maps.get(0);
                        String company_name = company_map.getName();
                        String scale = company_map.getScale();
                        position_map.put("company_name", company_name);
                        String degree_name = BeanUtils.converToString(position_map.get("degree_name"));
                        Integer degree_above = BeanUtils.converToInteger(position_map.get("degree_above"));
                        if (degree_above == 1) {
                            degree_name = degree_name + "及以上";
                        }
                        position_map.put("degree_name", degree_name);
                        position_map.put("scale", scale);
                        logger.info("position_map:" + position_map.toString());
                    }
                    position = JSON.toJSONString(position_map);
                    logger.info("position:" + position);
                    searchengineServices.updateposition(position, jobPositionId);
                }
                logger.info("--- ES Search Engine end---");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }


    /**
     * 输入的字符是否是汉字
     *
     * @return boolean
     */
    private boolean isChinese(String str) {
        boolean flag = false;
        for (int i = 0; i < str.length(); i++) {
            int v = str.charAt(i);
            if ((v >= 19968 && v <= 171941)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
