package com.moseeker.position.service.fundationbs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.google.common.collect.Maps;
import com.moseeker.baseorm.dao.campaigndb.CampaignPersonaRecomDao;
import com.moseeker.baseorm.dao.campaigndb.CampaignRecomPositionlistDao;
import com.moseeker.baseorm.dao.dictdb.*;
import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.dao.jobdb.*;
import com.moseeker.baseorm.dao.redpacketdb.RedpacketActivityDao;
import com.moseeker.baseorm.dao.redpacketdb.RedpacketActivityPositionJOOQDao;
import com.moseeker.baseorm.dao.referraldb.ReferralPositionBonusDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignPersonaRecomRecord;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignRecomPositionlistRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictAlipaycampusCityRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictAlipaycampusJobcategoryRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityPostcodeRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyFeature;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyAccountRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTeamRecord;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.records.*;
import com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketActivity;
import com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketActivityPosition;
import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.baseorm.pojo.JobPositionPojo;
import com.moseeker.baseorm.pojo.RecommendedPositonPojo;
import com.moseeker.baseorm.pojo.SearchData;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.*;
import com.moseeker.common.constants.Position.PositionSource;
import com.moseeker.common.constants.Position.PositionStatus;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.MD5Util;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.entity.PositionEntity;
import com.moseeker.entity.pojos.JobPositionRecordWithCityName;
import com.moseeker.position.kafka.KafkaSender;
import com.moseeker.position.pojo.DictConstantPojo;
import com.moseeker.position.pojo.JobPositionFailMess;
import com.moseeker.position.pojo.JobPostionResponse;
import com.moseeker.position.pojo.PositionForSynchronizationPojo;
import com.moseeker.position.service.position.*;
import com.moseeker.position.service.position.liepin.LiePinReceiverHandler;
import com.moseeker.position.service.position.qianxun.Degree;
import com.moseeker.position.service.schedule.PositionIndexSender;
import com.moseeker.position.utils.*;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.config.ConfigCustomMetaVO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictConstantDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.*;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobOccupationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionExtDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.position.service.PositionServices;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    KafkaSender kafkaSender;
    @Autowired
    private PositionStateAsyncHelper positionStateAsyncHelper;

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
    private HrCompanyConfDao hrCompanyConfDao;
    @Autowired
    private RedpacketActivityDao activityDao;
    @Autowired
    private RedpacketActivityPositionJOOQDao activityPositionJOOQDao;
    @Autowired
    private HrHbItemsDao hrHbItemsDao;
    @Autowired
    private PositionChangeUtil positionChangeUtil;
    @Autowired
    private CommonPositionUtils commonPositionUtils;
    @Autowired
    private PositionEntity positionEntity;
    @Autowired
    private CampaignPersonaRecomDao campaignPersonaRecomDao;
    @Autowired
    private CampaignRecomPositionlistDao campaignRecomPositionlistDao;
    @Autowired
    private UserHrAccountDao userHrAccountDao;
    @Autowired
    private HrAppCvConfDao hrAppCvConfDao;
    @Autowired
    private JobPositionCcmailDao jobPositionCcmailDao;
    @Resource(name = "cacheClient")
    private RedisClient redisClient;
    @Autowired
    private JobPositionHrCompanyFeatureDao jobPositionHrCompanyFeatureDao;
    @Autowired
    private HrCompanyFeatureDao hrCompanyFeatureDao;
    @Autowired
    PositionATSService positionATSService;
    @Autowired
    LiePinReceiverHandler receiverHandler;

    @Autowired
    ReferralPositionBonusDao referralPositionBonusDao;

    @Autowired
    private RedpacketActivityPositionJOOQDao positionJOOQDao;

    private ThreadPool pool = ThreadPool.Instance;
    private static List<DictAlipaycampusJobcategoryRecord> alipaycampusJobcategory;
    SearchengineServices.Iface searchengineServices = ServiceManager.SERVICE_MANAGER.getService(SearchengineServices.Iface.class);
    PositionServices.Iface positionServices = ServiceManager.SERVICE_MANAGER.getService(PositionServices.Iface.class);

    private static List dictAlipaycampusJobcategorylist;

    private static String ALPHACLOUD_SEARCH_SYNC_MQ;
    private static String refreshCoordinatorsUrl;

    static {
        try {
            ConfigPropertiesUtil configPropertiesUtil = ConfigPropertiesUtil.getInstance();
            configPropertiesUtil.loadResource("setting.properties");
            ALPHACLOUD_SEARCH_SYNC_MQ = configPropertiesUtil.get("alphacloud_search_sync_mq", String.class);

            refreshCoordinatorsUrl = configPropertiesUtil.get("alphacloud.position.refreshcoordinates.url", String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取推荐职位 <p> </p>
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
        // NullPoint checkFormWrong
        JobPositionPojo jobPositionPojo = jobPositionDao.getPosition(positionId);
        if (jobPositionPojo == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        SearchData searchData = new SearchData();
        jobPositionPojo.team_name = "";
        jobPositionPojo.department = "";
        searchData.setTeam_name("");
        searchData.setDepartment("");
        int team_id = jobPositionPojo.team_id;
        if (team_id != 0) {
            Query query1 = new Query.QueryBuilder().where("id", team_id).and("disable", 0).buildQuery();
            HrTeamRecord hrTeamRecord = hrTeamDao.getRecord(query1);
            if (hrTeamRecord != null) {
                jobPositionPojo.department = hrTeamRecord.getName();
                jobPositionPojo.team_name = hrTeamRecord.getName();
                searchData.setTeam_name(hrTeamRecord.getName());
                searchData.setDepartment(hrTeamRecord.getName());
            }
        }
        searchData.setSearch_title(StringUtils.filterStringForSearch(jobPositionPojo.title));

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
            String degreeName = getDictConstantJson(2101, jobPositionPojo.degree);
            jobPositionPojo.degree_name = degreeName;
        }
        searchData.setDegree_name(jobPositionPojo.degree_name);
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
            if (jobPositionExtRecord.getJobOccupationId() > 0) {
                JobOccupationRecord jobOccupationRecord =
                        jobOccupationDao.getRecord(new Query.QueryBuilder().where("id", jobPositionExtRecord.getJobOccupationId()).buildQuery());
                if (jobOccupationRecord != null && com.moseeker.common.util.StringUtils.isNotNullOrEmpty(jobOccupationRecord.getName())) {
                    jobPositionPojo.occupation = jobOccupationRecord.getName();
                }
            }
        } else {
            jobPositionPojo.custom = "";
            jobPositionPojo.occupation = "";
        }
        searchData.setCustom(jobPositionPojo.custom);
        searchData.setOccupation(jobPositionPojo.occupation);

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
        List<DictCityDO> dictList = commonPositionUtils.handlerCity(positionId);

        String citynames = commonPositionUtils.getPositionCityName(dictList);
        String cityEnames = commonPositionUtils.getPositionCityEname(dictList);
        logger.info("job_position_city的city信息是＝＝＝＝＝＝＝＝＝＝＝＝＝" + citynames);
        if (StringUtils.isNotNullOrEmpty(citynames)) {
            jobPositionPojo.city = citynames;
            List<String> cityList = StringUtils.stringToList(citynames, ",");
            List<String> eCityList = StringUtils.stringToList(cityEnames, ",");
            searchData.setCity_list(cityList);
            searchData.setEcity_list(eCityList);
        } else {
            List<String> cityList = StringUtils.stringToList(jobPositionPojo.city, ",");
            searchData.setCity_list(cityList);
        }
        jobPositionPojo.city_ename = cityEnames;
        if ("全国".equals(jobPositionPojo.city)) {
            jobPositionPojo.city_flag = 1;
        }

        searchData.setTitle(jobPositionPojo.title);
        jobPositionPojo.search_data = searchData;
        if (jobPositionPojo.salary_bottom == 0 && jobPositionPojo.salary_top == 0) {
            jobPositionPojo.salary = "薪资面议";
        }
        List<Map<String, Object>> positionFeature = positionEntity.getPositionFeatureList(positionId);
        if (!StringUtils.isEmptyList(positionFeature)) {
            jobPositionPojo.position_feature = positionFeature;
        }
        jobPositionPojo.feature = this.getFeatureString(positionFeature);

        return ResponseUtils.success(jobPositionPojo);
    }

    /*
     获取字符串形式的福利特色
     */
    private String getFeatureString(List<Map<String, Object>> list) {
        if (StringUtils.isEmptyList(list)) {
            return "";
        }
        String features = "";
        for (Map<String, Object> map : list) {
            String feature = (String) map.get("feature");
            if (StringUtils.isNotNullOrEmpty(feature)) {
                features += feature + "#";
            }
        }
        if (StringUtils.isNotNullOrEmpty(features)) {
            features = features.substring(0, features.lastIndexOf("#"));
        }
        return features;
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

    private Integer getCompanyId(List<JobPostrionObj> jobPositionHandlerDates) throws BIZException {
        Integer companyId;
        if (jobPositionHandlerDates.get(0).getId() != 0) {
            Query query = new Query.QueryBuilder().where("id", jobPositionHandlerDates.get(0).getId()).buildQuery();
            JobPositionRecord jobPostionTemp = jobPositionDao.getRecord(query);
            if (jobPostionTemp != null) {
                companyId = jobPostionTemp.getCompanyId();
            } else {
                throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, ConstantErrorCodeMessage.POSITION_JOBPOSITION_COMPANY_ID_BLANK);
            }
        } else {
            // 将该公司下的所有职位查询出来
            companyId = jobPositionHandlerDates.get(0).getCompany_id();
        }
        return companyId;
    }

    private Map<String, HrTeamRecord> hrTeamGroupByName(int companyId) {
        Map<String, HrTeamRecord> hashMapHrTeam = new HashMap<>();

        Query queryUtilDepartment = new Query.QueryBuilder()
                .where("company_id", companyId)
                .and("disable", 0)
                .buildQuery();
        // 取的该公司下的所有部门信息
        List<HrTeamRecord> hrTeamRecordList = hrTeamDao.getRecords(queryUtilDepartment);
        // 当更新或者新增jobPosition数据时，如果公司部门信息为空，提示无法更新或者新增jobposition
        if (!com.moseeker.common.util.StringUtils.isEmptyList(hrTeamRecordList)) {
            for (HrTeamRecord hrTeamRecord : hrTeamRecordList) {
                hashMapHrTeam.put(replaceBlank(hrTeamRecord.getName()), hrTeamRecord);
            }
        }
        return hashMapHrTeam;
    }

    private Map<String, JobOccupationDO> jobOccupationGroupDyName(int companyId) {
        Map<String, JobOccupationDO> jobOccupationMap = new LinkedHashMap<>();
        Query commonQuery = new Query.QueryBuilder()
                .where("company_id", companyId)
                .and("status", 1)
                .buildQuery();
        List<JobOccupationDO> jobOccupationList = jobOccupationDao.getDatas(commonQuery);
        for (JobOccupationDO jobOccupationDO : jobOccupationList) {
            jobOccupationMap.put(jobOccupationDO.getName().trim(), jobOccupationDO);
        }
        return jobOccupationMap;
    }

    private Map<Integer, UserHrAccountDO> userHrAccountGroupByID(int companyId) {
        // 公司下职位自定义字段
        Map<Integer, UserHrAccountDO> userHrAccountMap = new LinkedHashMap<>();
        Query commonQuery = new Query.QueryBuilder()
                .where(UserHrAccount.USER_HR_ACCOUNT.COMPANY_ID.getName(), companyId)
                .and(UserHrAccount.USER_HR_ACCOUNT.DISABLE.getName(), 1)
                .buildQuery();
        // 公司下职能信息
        List<UserHrAccountDO> userHrAccountList = userHrAccountDao.getDatas(commonQuery);
        for (UserHrAccountDO userHrAccountDO : userHrAccountList) {
            userHrAccountMap.put(userHrAccountDO.getId(), userHrAccountDO);
        }
        return userHrAccountMap;
    }

    private Map<Integer, JobPositionRecord> dbListGroupById(int companyId) {
        // 数据库中该公司的职位列表
        List<JobPositionRecord> dbList = jobPositionDao.getDatasForBatchhandlerDelete(companyId, 9);
        HashMap<Integer, JobPositionRecord> dbListMap = new HashMap<>();
        for (JobPositionRecord jobPositionRecord : dbList) {
            dbListMap.put(jobPositionRecord.getId(), jobPositionRecord);
        }
        return dbListMap;
    }

    private Map<String, JobCustomRecord> jobCustomGroupByName(int companyId) {
        // 公司下职位自定义字段
        Map<String, JobCustomRecord> jobCustomMap = new LinkedHashMap<>();
        Query commonQuery = new Query.QueryBuilder()
                .where("company_id", companyId)
                .and("status", 1)
                .buildQuery();
        // 公司下职能信息
        List<JobCustomRecord> jobCustomRecordList = jobCustomDao.getRecords(commonQuery);
        for (JobCustomRecord jobCustomRecord : jobCustomRecordList) {
            jobCustomMap.put(jobCustomRecord.getName().trim(), jobCustomRecord);
        }
        return jobCustomMap;
    }

    private List<JobPositionRecord> getDBOnlineList(Map<Integer, JobPositionRecord> dbListMap) {
        List<JobPositionRecord> dbOnlineList = new ArrayList<>();
        dbListMap.forEach((id, jobPositionRecord) -> {
            if (jobPositionRecord.getStatus() == 0) {
                dbOnlineList.add(jobPositionRecord);
            }
        });
        return dbOnlineList;
    }

    private boolean containsFeature(Map<String, HrCompanyFeature> featureMap, JobPostrionObj jobPositionHandlerDate) {
        String feature = jobPositionHandlerDate.getFeature();
        if (StringUtils.isNullOrEmpty(feature)) {
            return true;
        }

        for (String featureName : feature.split("#")) {
            if (!featureMap.containsKey(featureName)) {
                return false;
            }
        }
        return true;
    }

    private JobPostrionObj buildPositionFeature(JobPostrionObj obj) {
        JobPostrionObj positionFeature = new JobPostrionObj();
        positionFeature.setJobnumber(obj.getJobnumber());
        positionFeature.setCompany_id(obj.getCompany_id());
        positionFeature.setSource_id(obj.getSource_id());
        positionFeature.setSource(obj.getSource());

        return positionFeature;
    }

    /**
     * 根据职位ID批量获取position_ext数据
     * @param ids
     * @return
     */
    public List<JobPositionExtDO> getPositionExtList(List<Integer> ids) {

        return jobPositionExtDao.getDatasByPids(ids);
    }

    private enum DBOperation {
        INSERT,
        UPDATE;
    }

    /**
     * 在ATS批量同步职位的时候对基本参数进行校验
     * @param jobPositionHandlerDate   职位参数
     * @param jobPositionFailMessPojos 错误信息，如果校验未通过，需要往这个list里添加错误信息
     * @return
     */
    private boolean basicCheckBatchPostionData(JobPostrionObj jobPositionHandlerDate, List<JobPositionFailMess> jobPositionFailMessPojos) {
        if (jobPositionHandlerDate.getCompany_id() <= 0
                || jobPositionHandlerDate.getSource_id() <= 0
                || StringUtils.isNullOrEmpty(jobPositionHandlerDate.getJobnumber())) {
            handlerFailMess(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST, jobPositionFailMessPojos, jobPositionHandlerDate);
            return false;
        }
        return true;
    }

    /**
     * 在ATS批量同步职位的时候对参数进行校验，需要知道职位是插入还是更新
     * @param jobPositionHandlerDate   职位参数
     * @param jobPositionFailMessPojos 错误信息，如果校验未通过，需要往这个list里添加错误信息
     * @param op                       职位是插入操作还是更新操作
     * @return true:校验通过. false:校验未通过
     */
    private boolean checkBatchPostionData(JobPostrionObj jobPositionHandlerDate, List<JobPositionFailMess> jobPositionFailMessPojos, DBOperation op) {
        boolean insert = (op == DBOperation.INSERT);    //是否插入操作
        boolean update = (op == DBOperation.UPDATE);    //是否更新操作

        // 更新操作的时候，如果没有set标题，无需判断标题是否为空
        // 插入操作的时候，无论是否set标题，都要判断标题是否为空
        // 下同
        if (((update && jobPositionHandlerDate.isSetTitle()) || insert)
                && StringUtils.isNullOrEmpty(jobPositionHandlerDate.getTitle())) {
            handlerFailMess(ConstantErrorCodeMessage.POSITION_TITLE_NOT_EMPTY, jobPositionFailMessPojos, jobPositionHandlerDate);
            return false;
        }

        if (((update && jobPositionHandlerDate.isSetAccountabilities()) || insert)
                && StringUtils.isNullOrEmpty(jobPositionHandlerDate.getAccountabilities())) {
            handlerFailMess(ConstantErrorCodeMessage.POSITION_ACCOUNTABILITIES_NOT_EMPTY, jobPositionFailMessPojos, jobPositionHandlerDate);
            return false;
        }

        if (((update && jobPositionHandlerDate.isSetRequirement()) || insert)
                && StringUtils.isNullOrEmpty(jobPositionHandlerDate.getRequirement())) {
            handlerFailMess(ConstantErrorCodeMessage.POSITION_REQUIREMENT_NOT_EMPTY, jobPositionFailMessPojos, jobPositionHandlerDate);
            return false;
        }

        if (((update && jobPositionHandlerDate.isSetCity()) || insert)
                && StringUtils.isEmptyList(jobPositionHandlerDate.getCity())) {
            handlerFailMess(ConstantErrorCodeMessage.POSITION_CITY_NOT_EMPTY, jobPositionFailMessPojos, jobPositionHandlerDate);
            return false;
        }

        return true;
    }

    /**
     * ATS职位数据预处理
     * @param record 职位参数
     */
    private void preHandlePostionData(JobPositionRecord record) {
        // 替换掉标题里的换行符
        String title = record.getTitle();
        if (title != null) {
            record.setTitle(title.replace("\r", "").replace("\n", "").trim());
        }

        // 当职位要求为空时候，设置空串
        if (com.moseeker.common.util.StringUtils.isNullOrEmpty(record.getRequirement())) {
            record.setRequirement("");
        }

        // 换算薪资范围
        if (record.getSalaryBottom() == 0 && record.getSalaryTop() == 0) {
            record.setSalary("面议");
        }
        if (record.getSalaryBottom() > 0 && record.getSalaryTop() == 999) {
            record.setSalary(record.getSalaryBottom() + "K以上");
        }
    }

    /**
     * 批量ATS职位适配器，包装batchHandlerJobPostion方法，
     * 原来准备在执行完batchHandlerJobPostion后加上更新职位福利特色
     * 现将福利特色处理放在batchHandlerJobPostion中
     * @param batchHandlerJobPosition
     * @return
     * @throws BIZException
     */

    public JobPostionResponse batchHandlerJobPostionAdapter(BatchHandlerJobPostion batchHandlerJobPosition) throws TException {
        CountDownLatch batchHandlerCountDown = new CountDownLatch(1);
        JobPostionResponse response = batchHandlerJobPostion(batchHandlerJobPosition, batchHandlerCountDown);
        batchHandlerCountDown.countDown();
        return response;
    }

    /**
     * 批量处理修改职位
     * @param batchHandlerJobPosition
     * @return
     */
    @CounterIface
    public JobPostionResponse batchHandlerJobPostion(BatchHandlerJobPostion batchHandlerJobPosition, CountDownLatch batchHandlerCountDown) throws TException {
        // 提交的数据为空
        if (batchHandlerJobPosition == null || com.moseeker.common.util.StringUtils.isEmptyList(batchHandlerJobPosition.getData())) {
            logger.info("PositionService batchHandlerJobPostion 数据不存在");
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, ConstantErrorCodeMessage.POSITION_DATA_BLANK);
        }
        // 提交的数据
        List<JobPostrionObj> jobPositionHandlerDates = batchHandlerJobPosition.getData();
        List<String> jobNumbers = jobPositionHandlerDates.stream().map(r -> r.getJobnumber()).collect(Collectors.toList());
        String dateStr = DateUtils.dateToPattern(new Date(), "yyyy-MM-dd HH:mm:ss");
        float pSize = 100;
        int jSize = (int) Math.ceil(jobNumbers.size() / pSize);
        for (int i = 0; i < jSize; i++) {
            if (i == jSize - 1) {
                logger.info("batchJobNumbers-{},size:{}, index: {},jobNumbers:{}", dateStr, jobNumbers.size(), i, jobNumbers.subList((int) (i * pSize), jobNumbers.size()));
            } else {
                logger.info("batchJobNumbers-{},size:{}, index: {},jobNumbers:{}", dateStr, jobNumbers.size(), i, jobNumbers.subList((int) (i * pSize), (int) ((i + 1) * pSize)));
            }
        }

        //过滤职位信息中的emoji表情
        PositionUtil.refineEmoji(jobPositionHandlerDates);

        Integer companyId = getCompanyId(jobPositionHandlerDates);
        // 将该公司下所有的部门取出来，用于判断更新或者新增数据时，部门设置是否正确
        Map<String, HrTeamRecord> hashMapHrTeam = hrTeamGroupByName(companyId);
        // 公司下职能信息
        Map<String, JobOccupationDO> jobOccupationMap = jobOccupationGroupDyName(companyId);
        // 公司下职位自定义字段
        Map<String, JobCustomRecord> jobCustomMap = jobCustomGroupByName(companyId);
        // 因为之前新增了不存在的福利特色，所以重新按照公司ID再查询一遍福利特色
        Map<String, HrCompanyFeature> featureMap = positionATSService.getCompanyFeatureGroupByName(companyId);

        List<Integer> batchLiepinPositionDownShelf = new ArrayList<>();

        // 需要更新ES的jobpostionID
        Set<Integer> jobPositionIds = new LinkedHashSet<>();
        //记录需要更新的jobpostionID
        Set<Integer> updatePostionIds = new LinkedHashSet<>();
        Integer deleteCounts = 0;
        Integer sourceId = jobPositionHandlerDates.get(0).getSource_id();
        // 删除操作,删除除了data以外的数据库中的数据
        // 如果为true, 数据不能删除. 否则,允许删除, data中的数据根据fields_nohash中以外的字段, 判断data中的记录和数据库中已有记录的关系, 进行添加, 修改,删除
        if (!batchHandlerJobPosition.nodelete) {
            // 数据库中该公司的职位列表
            Map<Integer, JobPositionRecord> dbListMap = dbListGroupById(companyId);
            List<JobPositionRecord> dbOnlineList = getDBOnlineList(dbListMap);

            if (!com.moseeker.common.util.StringUtils.isEmptyList(dbOnlineList)) {
                // 不需要删除的数据
                List<JobPositionRecord> noDeleJobPostionRecords = new ArrayList<>();
                // 提交的数据处理
                for (JobPositionRecord jobPositionRecord : dbOnlineList) {
                    //处理拼音问题
                    if (StringUtils.isNotNullOrEmpty(jobPositionRecord.getTitle())) {
                        String pinYin = PinyinUtil.getFirstLetter(jobPositionRecord.getTitle());
                        jobPositionRecord.setFirstPinyin(pinYin);
                    }
                    boolean existed = false;
                    for (JobPostrionObj jobPositionHandlerDate : jobPositionHandlerDates) {
                        // 当ID相同，数据不需要删除
                        if (jobPositionRecord.getId() == jobPositionHandlerDate.getId()) {
                            noDeleJobPostionRecords.add(jobPositionRecord);
                            existed = true;
                            break;
                        }
                        // 当 source = 9 ，source_id ,company_id, jobnumber 相等时候，不需要删除
                        if (jobPositionRecord.getSource() == 9 && jobPositionRecord.getSourceId() == jobPositionHandlerDate.getSource_id()
                                && jobPositionRecord.getCompanyId() == jobPositionHandlerDate.getCompany_id()
                                && jobPositionRecord.getJobnumber().equals(jobPositionHandlerDate.getJobnumber())) {
                            noDeleJobPostionRecords.add(jobPositionRecord);
                            existed = true;
                            break;
                        }
                    }
                    // 需要删除的数据
                    if (!existed) {
                        // 需要删除的职位必须sourceId 必须相同
                        if (jobPositionRecord.getSourceId().equals(sourceId)) {
                            jobPositionRecord.setStatus((byte) PositionStatus.BANNED.getValue());
                            jobPositionIds.add(jobPositionRecord.getId());
                            // todo 猎聘api新增
                            if (jobPositionRecord.getCandidateSource() == 0) {
                                batchLiepinPositionDownShelf.add(jobPositionRecord.getId());
                            }
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

                // 猎聘api对接下架职位 todo 这行代码是新增
                positionStateAsyncHelper.downShelf(batchHandlerCountDown, batchLiepinPositionDownShelf);
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
        // 更新前的jobPosition数据
        List<JobPositionRecord> jobPositionOldRecordList = new ArrayList<>();
        //  需要更新的JobPositionExtRecord数据
        List<JobPositionExtRecord> jobPositionExtRecordUpdateRecords = new ArrayList<>();
        //  需要新增的JobPositionExtRecord数据
        List<JobPositionExtRecord> jobPositionExtRecordAddRecords = new ArrayList<>();
        // 需要更新的JobPositionCity数据
        List<JobPositionCityRecord> jobPositionCityRecordsUpdatelist = new ArrayList<>();
        // 需要新增的JobPositionCity数据
        List<JobPositionCityRecord> jobPositionCityRecordsAddlist = new ArrayList<>();
        // 需要新增的JobPositionCcmail数据
        List<JobPositionCcmailRecord> jobPositionCcmailRecordsAddlist = new ArrayList<>();
        // 需要作废的第三方职位
        List<Integer> thirdPartyPositionDisablelist = new ArrayList<>();
        // 返回同步需要的的id以及对应的thirdParty_position
        List<ThirdPartyPositionForm> syncData = new ArrayList<>();
        // 返回新增或者更新失败的职位信息
        List<JobPositionFailMess> jobPositionFailMessPojos = new ArrayList<>();
        // 需要删除的城市的数据ID列表
        List<Integer> deleteCitylist = new ArrayList<>();
        // 需要删除的抄送邮箱数据职位ID列表
        List<Integer> ccmailPositionIdsToDelete = new ArrayList<>();
        // 需要更新的福利特色数据
        List<JobPostrionObj> needBindFeatureData = new ArrayList<>();
        // 上架数据
        List<Integer> needReSyncData = new ArrayList<>();
        // 公司下HR账号ID
        Map<Integer, UserHrAccountDO> userHrAccountMap = userHrAccountGroupByID(companyId);
        // 职位数据是更新还是插入操作
        DBOperation dbOperation;
        // 处理数据
        for (JobPostrionObj formData : jobPositionHandlerDates) {
            int company_id = formData.getCompany_id();
            String jobnumber = formData.getJobnumber();
            logger.info("batchHandlerJobPostion提交的数据：" + formData.toString());

            // 基础校验
            if (!basicCheckBatchPostionData(formData, jobPositionFailMessPojos)) {
                continue;
            }

            // 按company_id + .source_id + .jobnumber + source=9取得数据
            JobPositionRecord jobPositionRecord = jobPositionDao.getUniquePositionIgnoreDelete(
                    formData.getCompany_id(),
                    PositionSource.ATS.getCode(),
                    formData.getSource_id(),
                    formData.getJobnumber());
            // todo 猎聘新增
            // 更新或者新增数据
            if (formData.getId() != 0 || !com.moseeker.common.util.StringUtils.isEmptyObject(jobPositionRecord)) {
                dbOperation = DBOperation.UPDATE;
                if (jobPositionRecord.getStatus() == PositionStatus.BANNED.getValue()) {
                    needReSyncData.add(jobPositionRecord.getId());      // 记录上架职位
                } else if (jobPositionRecord.getStatus() == PositionStatus.ACTIVED.getValue()) {
                    jobPositionOldRecordList.add(jobPositionRecord);
                }
            } else {
                dbOperation = DBOperation.INSERT;
            }

            // 参数校验
            if (!checkBatchPostionData(formData, jobPositionFailMessPojos, dbOperation)) {
                continue;
            }

            JobPositionRecord formRcord = BeanUtils.structToDB(formData, JobPositionRecord.class);
            // 参数预处理
            preHandlePostionData(formRcord);

            // 判断publisher是否存在
            if (!userHrAccountMap.containsKey(formData.getPublisher())) {
                handlerFailMess(ConstantErrorCodeMessage.POSITION_PUBLISHER_NOT_EXIST, jobPositionFailMessPojos, formData);
                continue;
            }

            // 处理职位福利特色数据
            if (!containsFeature(featureMap, formData)) {
                handlerFailMess(ConstantErrorCodeMessage.FEATURE_MUST_EXISTS, jobPositionFailMessPojos, formData);
                continue;
            } else {
                needBindFeatureData.add(formData);
            }

            int team_id = 0;
            if (!com.moseeker.common.util.StringUtils.isNullOrEmpty(formRcord.getDepartment())) {
                String department = replaceBlank(formRcord.getDepartment());
                HrTeamRecord hrTeamRecord = (HrTeamRecord) hashMapHrTeam.get(department);
                if (hrTeamRecord != null) {
                    team_id = hrTeamRecord.getId();
                } else {
                    //部分公司在部门不存在时，直接插入新部门
                    if (batchHandlerJobPosition.isCreateDeparment) {
                        HrTeamRecord team = new HrTeamRecord();
                        team.setName(formRcord.getDepartment());
                        team.setCompanyId(formRcord.getCompanyId());

                        HrTeamRecord teamTemp = hrTeamDao.addRecord(team);
                        logger.info("----插入的部门ID为---:" + teamTemp.getId());

                        team_id = teamTemp.getId();

                        hashMapHrTeam.put(department, teamTemp);
                    } else {
                        logger.info("-----未取到TeamId-------部门名称为 ,{},company_id : {}", formRcord.getDepartment(), formRcord.getCompanyId());
                        handlerFailMess(ConstantErrorCodeMessage.POSITION_DATA_DEPARTMENT_ERROR, jobPositionFailMessPojos, formData);
                        continue;
                    }
                }
            } else {
                formRcord.setDepartment("");
            }
            int jobOccupationId = 0;
            // 验证职能信息是否正确
            if (!com.moseeker.common.util.StringUtils.isNullOrEmpty(formData.getOccupation())) {
                JobOccupationDO jobOccupationDO = jobOccupationMap.get(formData.getOccupation().trim());
                if (jobOccupationDO != null) {
                    jobOccupationId = jobOccupationDO.getId();
                } else {
                    // 职能错误的时候，自动添加一条职能新
                    JobOccupationDO jobOccupation = new JobOccupationDO();
                    jobOccupation.setCompanyId(companyId);
                    jobOccupation.setStatus((byte) 1);
                    jobOccupation.setName(formData.getOccupation());
                    JobOccupationDO jobOccupationDOTemp = jobOccupationDao.addData(jobOccupation);
                    jobOccupationId = jobOccupationDOTemp.getId();
                    jobOccupationMap.put(jobOccupationDOTemp.getName().trim(), jobOccupationDOTemp);
                }
            }
            // 验证职位自定义字段
            int customId = 0;
            if (!com.moseeker.common.util.StringUtils.isNullOrEmpty(formData.getCustom())) {
                JobCustomRecord jobCustomRecord = (JobCustomRecord) jobCustomMap.get(formData.getCustom());
                if (jobCustomRecord != null) {
                    customId = jobCustomRecord.getId();
                } else {
                    logger.info("-----职位自定义字段错误,职位自定义为:" + formData.getCustom());
                    handlerFailMess(ConstantErrorCodeMessage.POSITION_DATA_CUSTOM_ERROR.replace("{MESSAGE}", formData.getCustom()), jobPositionFailMessPojos, formData);
                    continue;
                }
            }

            // 城市信息
            String city = citys(formData.getCity());
            logger.info("城市信息：{}", city);
            // 城市信息太长时候，需要过滤数据
            if (city.length() > 100) {
                handlerFailMess(ConstantErrorCodeMessage.CITY_TOO_LONG, jobPositionFailMessPojos, formData);
                continue;
            }
            if(!RedisUtils.lock(redisClient, KeyIdentifier.THIRD_PARTY_POSITION_SYNCHRONIZATION_JOBNUMBER.toString(), company_id + "_" + jobnumber, 20 * 60)){
                logger.info("{}已存在正在更新的职位，直接添加同步数据", formData.getJobnumber());
                try {
                    String positionIdStr = redisClient.get(Constant.APPID_ALPHADOG, KeyIdentifier.THIRD_PARTY_POSITION_SYNC_POSITIONID.toString(), company_id + "_" + jobnumber);
                    logger.info("{} positionId {}", formData.getJobnumber(), positionIdStr);
                    int positionId = Integer.parseInt(positionIdStr);
                    if (positionId != 0) {
                        addSyncData(syncData, positionId, formData.getThirdParty_position());
                    }
                    continue;
                } catch (Exception e) {
                    logger.warn("未在redis找到positionId ,{}, {}", company_id, jobnumber);
                }
            }
            logger.info("{} 不存在数据", formData.getJobnumber());

            // 更新或者新增数据
            if (dbOperation == DBOperation.UPDATE) {
                // 数据更新
                // 按company_id + .source_id + .jobnumber + source=9取得数据为空时，按Id进行更新
                if (!com.moseeker.common.util.StringUtils.isEmptyObject(jobPositionRecord)) {
                    formRcord.setId(jobPositionRecord.getId());
                    // 把ID存入方法参数中，配合batchHandlerJobPostionAdapter方法
                    formData.setId(jobPositionRecord.getId());
                    // TODO: 2019/9/6 job更新刷新Es规则
                    if (isUpdatePosition(formData, jobPositionRecord)) {
                        jobPositionIds.add(jobPositionRecord.getId());
                        updatePostionIds.add(jobPositionRecord.getId());
                    }
                }
                redisClient.set(Constant.APPID_ALPHADOG, KeyIdentifier.THIRD_PARTY_POSITION_SYNC_POSITIONID.toString(), company_id + "_" + jobnumber, String.valueOf(formRcord.getId()));
                // 添加同步数据
                addSyncData(syncData, formRcord.getId(), formData.getThirdParty_position());

                // 取出数据库中的数据进行对比操作
                JobPositionRecord jobPositionRecordTemp = jobPositionRecord;
                if (jobPositionRecordTemp != null) {
                    Query query = new Query.QueryBuilder()
                            .where("pid", jobPositionRecordTemp.getId())
                            .buildQuery();
                    JobPositionExtRecord jobPositionExtRecord = jobPositonExtDao.getRecord(query);
                    if (fieldsNohashs == null ||
                            (!md5(fieldsNohashs, jobPositionRecordTemp, jobPositionExtRecord != null ? jobPositionExtRecord.getExtra() : "").equals(md5(fieldsNohashs, formRcord, formData.getExtra())))) {

                        formRcord.setSourceId(jobPositionRecordTemp.getSourceId());
                        formRcord.setCompanyId(companyId);
                        if (com.moseeker.common.util.StringUtils.isNullOrEmpty(formRcord.getJobnumber())) {
                            formRcord.setJobnumber(jobPositionRecordTemp.getJobnumber());
                        }
                        // 当城市无法转换时，入库为提交的数据
                        if (city != null) {
                            formRcord.setCity(city);
                        }
                        formRcord.setTeamId(team_id);
                        // 设置不需要更新的字段
                        if (fieldsNooverwriteStrings != null && fieldsNooverwriteStrings.length > 0) {
                            for (Field field : formRcord.fields()) {
                                for (String fieldNo : fieldsNooverwriteStrings) {
                                    if (field.getName().equals(fieldNo)) {
                                        formRcord.set(field, jobPositionRecordTemp.getValue(field.getName()));
                                    }
                                }
                            }
                        }

                        // 需要更新的抄送邮箱数据
                        if (formData.isSetProfile_cc_mail_enabled()) {
                            // 增加需要删除抄送邮箱
                            ccmailPositionIdsToDelete.add(formRcord.getId());

                            handleCcmail(formData, formRcord, jobPositionCcmailRecordsAddlist);
                        }

                        // 将需要更新JobPosition的数据放入更新的列表
                        jobPositionUpdateRecordList.add(formRcord);

                        //更新的职位只有在title变化时才发布新职位
                        //das端在更新职位时同样有这个判断，所以修改此处时考虑是否需要修改das中的PositionHandler.update方法
                        //考虑是否写个共通
                        if (formData.isSetTitle() && !jobPositionRecord.getTitle().equals(formRcord.getTitle())) {
                            //添加修改标题的职位对应的需要作废的第三方职位数据parent_id
                            thirdPartyPositionDisablelist.add(formRcord.getId());
                        }

                        // 需要更新JobPositionCity数据
                        List<JobPositionCityRecord> jobPositionCityRecordList = cityCode(formData.getCity(), formRcord.getId());
                        if (jobPositionCityRecordList != null && jobPositionCityRecordList.size() > 0) {
                            // 更新时候需要把之前的jobPositionCity数据删除
                            deleteCitylist.add(formRcord.getId());
                            jobPositionCityRecordsUpdatelist.addAll(jobPositionCityRecordList);
                        }
                        // 需要更新的JobPositionExra数据
                        if (formData.getExtra() != null
                                || jobOccupationId != 0
                                || customId != 0
                                || StringUtils.isNotNullOrEmpty(formData.getExt())) {
                            if (jobPositionExtRecord == null) {
                                jobPositionExtRecord = new JobPositionExtRecord();
                                jobPositionExtRecord.setPid(jobPositionRecordTemp.getId());
                                jobPositionExtRecord.setExtra(formData.getExtra() == null ? "" : formData.getExtra());
                                if (jobOccupationId != 0) {
                                    jobPositionExtRecord.setJobOccupationId(jobOccupationId);
                                }
                                if (customId != 0) {
                                    jobPositionExtRecord.setJobCustomId(customId);
                                }
                                if (StringUtils.isNotNullOrEmpty(formData.getExt())) {
                                    jobPositionExtRecord.setExt(formData.getExt());
                                }
                                jobPositionExtRecordAddRecords.add(jobPositionExtRecord);
                            } else {
                                jobPositionExtRecord.setExtra(formData.getExtra() == null ? "" : formData.getExtra());
                                if (jobOccupationId != 0) {
                                    jobPositionExtRecord.setJobOccupationId(jobOccupationId);
                                }
                                if (customId != 0) {
                                    jobPositionExtRecord.setJobCustomId(customId);
                                }
                                if (StringUtils.isNotNullOrEmpty(formData.getExt())) {
                                    jobPositionExtRecord.setExt(formData.getExt());
                                }
                                jobPositionExtRecordUpdateRecords.add(jobPositionExtRecord);
                            }
                        }
                    }
                }
            } else { // 数据的新增
                formRcord.setTeamId(team_id);
                // 当城市无法转换时，入库为提交的数据
                if (city != null) {
                    formRcord.setCity(city);
                }
                if (StringUtils.isNotNullOrEmpty(formRcord.getTitle())) {
                    String pinYin = PinyinUtil.getFirstLetter(formRcord.getTitle());
                    formRcord.setFirstPinyin(pinYin);
                }
                logger.info("-- 新增jobPostion数据开始，新增的jobPostion数据为：" + formRcord.toString() + "--");
                Integer pid = jobPositionDao.addRecord(formRcord).getId();
                if (pid != null) {
                    // 宜家刷协助人
                    if (StringUtils.isNotNullOrEmpty(formData.getDepartmentCode())) {
                        refreshCoordinators(pid, formData.getCompany_id(), formData.getDepartmentCode());
                    }
                    jobPositionIds.add(pid);
                    List<JobPositionCityRecord> jobPositionCityRecordList = cityCode(formData.getCity(), formRcord.getId());
                    if (jobPositionCityRecordList != null && jobPositionCityRecordList.size() > 0) {
                        // 新增城市code时，需要先删除jobpostionCity数据
                        jobPositionCityRecordsAddlist.addAll(jobPositionCityRecordList);
                    }
                }
                // 把ID存入方法参数中，配合batchHandlerJobPostionAdapter方法
                formData.setId(pid);
                // 需要新增的JobPosition数据
                jobPositionAddRecordList.add(formRcord);
                redisClient.set(Constant.APPID_ALPHADOG, KeyIdentifier.THIRD_PARTY_POSITION_SYNC_POSITIONID.toString(), company_id + "_" + jobnumber, String.valueOf(formRcord.getId()));
                // 需要同步的数据
                addSyncData(syncData, formRcord.getId(), formData.getThirdParty_position());
                // 需要更新的抄送邮箱数据
                if (formData.isSetProfile_cc_mail_enabled()) {
                    handleCcmail(formData, formRcord, jobPositionCcmailRecordsAddlist);
                }

                if (!com.moseeker.common.util.StringUtils.isNullOrEmpty(formData.getExtra())
                        || jobOccupationId != 0
                        || customId != 0
                        || StringUtils.isNotNullOrEmpty(formData.getExt())) {
                    // 新增jobPostion_ext数据
                    JobPositionExtRecord jobPositionExtRecord = new JobPositionExtRecord();
                    jobPositionExtRecord.setExtra(formData.getExtra() == null ? "" : formData.getExtra());
                    jobPositionExtRecord.setJobOccupationId(jobOccupationId);
                    jobPositionExtRecord.setJobCustomId(customId);
                    jobPositionExtRecord.setPid(pid);
                    jobPositionExtRecord.setExt(formData.getExt());
                    jobPositionExtRecordAddRecords.add(jobPositionExtRecord);

                }
            }
        }
        logger.info("需要更新jobPostion数据的条数:{},\n需要更新jobPostionExt数据的条数:{},\n新增jobPostionExt数据的条数:{},\n新增jobPositionCity数据的条数:{},\n需要更新jobPositionCity数据条数:{}",
                jobPositionCityRecordsUpdatelist.size(), jobPositionExtRecordUpdateRecords.size(),
                jobPositionExtRecordAddRecords.size(), jobPositionCityRecordsAddlist.size(), jobPositionCityRecordsUpdatelist.size());
        try {
            // 更新jobPostion数据
            if (jobPositionUpdateRecordList.size() > 0) {
                logger.info("-------------更新jobPostion数据开始------------------");
                for (JobPositionRecord jobPositionRecord : jobPositionUpdateRecordList) {
                    if (StringUtils.isNotNullOrEmpty(jobPositionRecord.getTitle())) {
                        String pinYin = PinyinUtil.getFirstLetter(jobPositionRecord.getTitle());
                        jobPositionRecord.setFirstPinyin(pinYin);
                    }
                }
                jobPositionDao.updateRecords(jobPositionUpdateRecordList);
            }
            // 更新jobPostionExt数据
            if (jobPositionExtRecordUpdateRecords.size() > 0) {
                jobPositonExtDao.updateRecords(jobPositionExtRecordUpdateRecords);
            }
            // 新增jobPostionExt数据
            if (jobPositionExtRecordAddRecords.size() > 0) {
                jobPositonExtDao.addAllRecord(jobPositionExtRecordAddRecords);
            }
            // 新增jobPositionCity数据
            if (jobPositionCityRecordsAddlist.size() > 0) {
                jobPositionCityDao.addAllRecord(jobPositionCityRecordsAddlist.stream().distinct().collect(Collectors.toList()));
            }
            // 删除jobPositionCcmail数据
            if (!ccmailPositionIdsToDelete.isEmpty()) {
                jobPositionCcmailDao.deleteByPositionIds(ccmailPositionIdsToDelete);
            }
            if (!jobPositionCcmailRecordsAddlist.isEmpty()) {
                jobPositionCcmailDao.addAllRecord(jobPositionCcmailRecordsAddlist);
            }
            // 更新jobPositionCity数据
            if (jobPositionCityRecordsUpdatelist.size() > 0) {
                if (deleteCitylist.size() > 0) {
                    logger.info("-------------需要删除jobPositionCity的数据长度：{},内容为：{}", deleteCitylist.size(), deleteCitylist.toString());
                    jobPositionCityDao.delJobPostionCityByPids(deleteCitylist);
                }
                jobPositionCityDao.addAllRecord(jobPositionCityRecordsUpdatelist.stream().distinct().collect(Collectors.toList()));
            }
            // 作废thirdPartyPosition数据
            if (thirdPartyPositionDisablelist.size() > 0) {
                logger.info("-------------作废thirdPartyPosition数据长度:{}，数据内容：{}------------",
                        thirdPartyPositionDisablelist.size(), JSON.toJSONString(thirdPartyPositionDisablelist));
                thirdpartyPositionDao.disable(thirdPartyPositionDisablelist);
            }
            if (needBindFeatureData.size() > 0) {
                BatchHandlerJobPostion featureData = new BatchHandlerJobPostion();
                featureData.setData(needBindFeatureData);
                positionATSService.updatePositionFeature(featureData);
            }
            // 批量请求猎聘编辑职位信息 todo 代码是猎聘api新增
            Map<Integer, JobPositionRecord> oldJobMap = new HashMap<>();
            for (JobPositionRecord jobPositionRecord : jobPositionOldRecordList) {
                if (jobPositionRecord != null) {
                    oldJobMap.put(jobPositionRecord.getId(), jobPositionRecord);
                }
            }

            if (updatePostionIds.size() > 0) {
                logger.info("saveAndSync更新的updatePostionIds长度为：{}, 数据为：{}", updatePostionIds.size(), updatePostionIds);
            }
            positionStateAsyncHelper.resync(batchHandlerCountDown, needReSyncData);
            positionStateAsyncHelper.edit(batchHandlerCountDown, jobPositionUpdateRecordList, oldJobMap);
        } catch (Exception e) {
            logger.error("更新和插入数据发生异常,异常信息为：" + e.getMessage());
            e.printStackTrace();
        }
        JobPostionResponse jobPostionResponse = new JobPostionResponse();
        jobPostionResponse.setJobPositionFailMessPojolist(jobPositionFailMessPojos);
        jobPostionResponse.setDeleteCounts(deleteCounts);
        jobPostionResponse.setInsertCounts(jobPositionAddRecordList.size());
        jobPostionResponse.setUpdateCounts(jobPositionUpdateRecordList.size());
        jobPostionResponse.setTotalCounts(jobPositionHandlerDates.size());
        jobPostionResponse.setSyncData(syncData);
        if (jobPositionIds.size() > 0) {
            List<Integer> jobPositionIdsList = new ArrayList<>();
            jobPositionIdsList.addAll(jobPositionIds);
            logger.info("saveAndSync插入和新增的jobPositionIds size 为:{}, 数据为:{}", jobPositionIds.size(), jobPositionIds.toString());
            // 更新ES Search Engine
            PositionService.UpdateES updataESThread = new PositionService.UpdateES(jobPositionIdsList);
            Thread thread = new Thread(updataESThread);
            thread.start();
            //此处使用硬编码，感觉十分不好
            String exchange = "new_position_es_index_update_exchange";
            String routingKey = "newpositionesindexupdate.#";
            sender.sendMqRequest(jobPositionIdsList, routingKey, exchange);
            return jobPostionResponse;
        }
        if (!StringUtils.isEmptyList(jobPositionAddRecordList)
                || !StringUtils.isEmptyList(jobPositionUpdateRecordList)) {
            sendSearchSyncMq(companyId, jobPositionAddRecordList, jobPositionUpdateRecordList);
        }
        return jobPostionResponse;
    }

    /**
     * 判断传入的职位信息是否有更新
     * @param formData          传入的数据
     * @param jobPositionRecord 数据库原有数据
     * @return bool
     * @Author lee
     * @Date 2019/9/6 14:07
     */
    private boolean isUpdatePosition(JobPostrionObj formData, JobPositionRecord jobPositionRecord) {
        //只要如下有任一属性发生变化，则认为该职位有更新
        return notEquals(formData.getTitle(), jobPositionRecord.getTitle())
                || notEquals(formData.getDepartment(), jobPositionRecord.getDepartment())
                || notEquals(formData.getAccountabilities(), jobPositionRecord.getAccountabilities())
                || notEquals(formData.getExperience(), jobPositionRecord.getExperience())
                || notEquals(formData.getSalary(), jobPositionRecord.getSalary())
                || notEquals(formData.getLanguage(), jobPositionRecord.getLanguage())
                || notEquals(formData.getBusiness_group(), jobPositionRecord.getBusinessGroup())
                || !Objects.equals(formData.getEmployment_type(), jobPositionRecord.getEmploymentType())
                || notEquals(formData.getHr_email(), jobPositionRecord.getHrEmail())
                || !Objects.equals(formData.getDegree(), jobPositionRecord.getDegree())
                || notEquals(formData.getFeature(), jobPositionRecord.getFeature())
                || !Objects.equals(formData.getEmail_notice(), jobPositionRecord.getEmailNotice())
                || !Objects.equals(formData.getCandidate_source(), jobPositionRecord.getCandidateSource())
                || notEquals(formData.getOccupation(), jobPositionRecord.getOccupation())
                || notEquals(formData.getIndustry(), jobPositionRecord.getIndustry())
                || !Objects.equals(formData.getEmail_resume_conf(), jobPositionRecord.getEmailResumeConf())
                || notEquals(formData.getDistrict(), jobPositionRecord.getDistrict())
                || !Objects.equals(formData.getCount(), jobPositionRecord.getCount())
                || !Objects.equals(formData.getSalary_top(), jobPositionRecord.getSalaryTop())
                || !Objects.equals(formData.getSalary_bottom(), jobPositionRecord.getSalaryBottom())
                || !Objects.equals(formData.getExperience_above(), jobPositionRecord.getExperienceAbove())
                || !Objects.equals(formData.getDegree_above(), jobPositionRecord.getDegreeAbove())
                || !Objects.equals(formData.getManagement_experience(), jobPositionRecord.getManagementExperience())
                || !Objects.equals(formData.getGender(), jobPositionRecord.getGender())
                || !Objects.equals(formData.getPublisher(), jobPositionRecord.getPublisher())
                || !Objects.equals(formData.getApp_cv_config_id(), jobPositionRecord.getAppCvConfigId())
                || !Objects.equals(formData.getAge(), jobPositionRecord.getAge())
                || notEquals(formData.getMajor_required(), jobPositionRecord.getMajorRequired())
                || notEquals(formData.getWork_address(), jobPositionRecord.getWorkAddress())
                || notEquals(formData.getKeyword(), jobPositionRecord.getKeyword())
                || notEquals(formData.getReporting_to(), jobPositionRecord.getReportingTo())
                || !Objects.equals(formData.getIs_hiring(), jobPositionRecord.getIsHiring())
                || !Objects.equals(formData.getUnderlings(), jobPositionRecord.getUnderlings())
                || !Objects.equals(formData.getLanguage_required(), jobPositionRecord.getLanguageRequired())
                || !Objects.equals(formData.getPosition_code(), jobPositionRecord.getPositionCode())
                || !Objects.equals(formData.getTeam_id(), jobPositionRecord.getTeamId());
    }

    //不相同的规则为传入值不能为null，并且和原始值不相同
    private boolean notEquals(String value1, String value2) {
        return StringUtils.isNotNullOrEmpty(value1) && !value1.equals(value2);
    }

    @Autowired
    private PositionIndexSender sender;

    private void sendSearchSyncMq(Integer companyId,
                                  List<JobPositionRecord> jobPositionAddRecordList,
                                  List<JobPositionRecord> jobPositionUpdateRecordList) {
        pool.startTast(() -> {
            JSONObject form = new JSONObject();
            form.put("companyId", companyId);

            JSONArray data = new JSONArray();
            if (!StringUtils.isEmptyList(jobPositionAddRecordList)) {
                jobPositionAddRecordList.stream().forEach(p -> {
                    JSONObject temp = new JSONObject();
                    temp.put("pid", p.getId());
                    temp.put("title", p.getTitle());
                    data.add(temp);
                });
            }

            if (!StringUtils.isEmptyList(jobPositionUpdateRecordList)) {
                jobPositionUpdateRecordList.stream().forEach(p -> {
                    JSONObject temp = new JSONObject();
                    temp.put("pid", p.getId());
                    temp.put("title", p.getTitle());
                    data.add(temp);
                });
            }
            try {
                HttpClientUtil.sentHttpPostRequest(ALPHACLOUD_SEARCH_SYNC_MQ, null, form);
            } catch (Exception e) {
                logger.error("send alphacloud search sync mq error:{},form:{}", e.getMessage(), form);
            }
            return null;
        });
    }

    /**
     * 处理抄送邮件记录
     * @param jobPositionHandlerDate          ATS传过来的数据
     * @param record                          要入库的数据
     * @param jobPositionCcmailRecordsAddlist 要入库的抄送邮件数据
     */
    private void handleCcmail(JobPostrionObj jobPositionHandlerDate, JobPositionRecord record, List<JobPositionCcmailRecord> jobPositionCcmailRecordsAddlist) {
        if (jobPositionHandlerDate.profile_cc_mail_enabled) {
            record.setProfileCcMailEnabled((byte) 1);
            if (!StringUtils.isEmptyList(jobPositionHandlerDate.getCcmail())) {
                List<JobPositionCcmailRecord> tempCcmailRecords = generateCcmailData(jobPositionHandlerDate.getCcmail(), record);
                jobPositionCcmailRecordsAddlist.addAll(tempCcmailRecords);
            }
        } else {
            record.setProfileCcMailEnabled((byte) 0);
        }
    }

    /**
     * 生成抄送邮箱数据
     * @param ccmails
     * @param record
     * @return
     */
    private List<JobPositionCcmailRecord> generateCcmailData(List<String> ccmails, JobPositionRecord record) {
        return ccmails.stream().filter(mail -> {
            ValidateUtil vu = new ValidateUtil();
            vu.addRegExpressValidate("抄送邮箱", mail, "^([\\w-])+(\\.[\\w-]+)*@([\\w-])+((\\.[\\w-]+)+)$", mail + "不符合规则", null);
            String result = vu.validate();
            if (StringUtils.isNullOrEmpty(result)) {
                return true;
            } else {
                logger.info("ATS邮箱格式不正确 {}", mail);
                return false;
            }
        }).map(mail -> {
            JobPositionCcmailRecord ccmailRecord = new JobPositionCcmailRecord();
            ccmailRecord.setHrId(record.getPublisher());
            ccmailRecord.setPositionId(record.getId());
            ccmailRecord.setToEmail(mail);
            return ccmailRecord;
        }).collect(Collectors.toList());
    }

    /**
     * 生成同步数据，并加入到syncData列表中
     * @param syncData
     * @param positionId
     * @param data
     */
    private void addSyncData(List<ThirdPartyPositionForm> syncData, int positionId, String data) {
        if (syncData == null || StringUtils.isNullOrEmpty(data)) {
            return;
        }
        // 需要同步的数据
        ThirdPartyPositionForm form = new ThirdPartyPositionForm();
        form.setPositionId(positionId);

        TypeReference<List<String>> typeRef
                = new TypeReference<List<String>>() {
        };
        List<String> channels = JSON.parseObject(data, typeRef);
        form.setChannels(channels);
        syncData.add(form);
    }

    /**
     * 删除职位
     */
    public Response deleteJobposition(Integer id, Integer companyId, String jobnumber, Integer sourceId) {
        CountDownLatch batchDeleteHandlerCountDown = new CountDownLatch(1);
        Response response = deleteJobPositionAdaptor(id, companyId, jobnumber, sourceId, batchDeleteHandlerCountDown);
        batchDeleteHandlerCountDown.countDown();
        return response;
    }

    private Response deleteJobPositionAdaptor(Integer id, Integer companyId, String jobnumber, Integer sourceId, CountDownLatch batchDeleteHandlerCountDown) {
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
                    .and("status", PositionStatus.ACTIVED.getValue())
                    .buildQuery();
            jobPositionRecord = jobPositionDao.getRecord(queryUtil);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_DATA_DELETE_PARAM);
        }
        // 删除jobPositionCity数据
        List<Integer> list = new ArrayList<>();
        if (jobPositionRecord != null && jobPositionRecord.getStatus().intValue() != 1) {
            jobPositionRecord.setStatus((byte) PositionStatus.DELETED.getValue());
            // 删除JobPostion
            jobPositionDao.updateRecord(jobPositionRecord);
            // 更新ES Search Engine
            list.add(jobPositionRecord.getId());
            PositionService.UpdateES updataESThread = new PositionService.UpdateES(list);
            Thread thread = new Thread(updataESThread);
            thread.start();

            // todo 删除时同时向猎聘下架职位
            // 猎聘api对接下架职位 todo 这行代码是新增
            List<Integer> jobPositionIds = new ArrayList<>();
            jobPositionIds.add(jobPositionRecord.getId());
            logger.info("===============jobPositionIds:{}==================", jobPositionIds);
            pool.startTast(() -> {
                if (batchDeleteHandlerCountDown.await(60, TimeUnit.SECONDS)) {
                    return receiverHandler.batchHandlerLiepinDownShelfOperation(jobPositionIds);
                } else {
                    throw new RuntimeException("rabbitmq线程等待超时");
                }
            });

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

    // 特殊城市拼音转CityCode
    public final static Map specialCityMap = new LinkedHashMap();

    /**
     * 错误信息处理
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
        jobPositionFailMessPojo.setFeature(jobPostrionObj.getFeature());
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
        logger.info("cityCode : {}, pid:{}", citys, pid);
        List<JobPositionCityRecord> jobPositionCityRecordList = new ArrayList<>();
        try {
            // 将已经查询的到的cityCode放到map中，避免多次查询
            HashMap cityPostCodeMap = new LinkedHashMap();

            // 将从DictCity查询
            HashMap cityMap = new LinkedHashMap();
            if (citys != null && citys.size() > 0 && pid != null) {
                for (City city : citys) {
                    //去空格
                    if (org.apache.commons.lang.StringUtils.isNotBlank(city.getValue())) {
                        city.setValue(city.getValue().trim());
                    }
                    if (org.apache.commons.lang.StringUtils.isNotBlank(city.getType())) {
                        city.setType(city.getType().trim());
                    }
                    // 查询DictCityPostCode条件
                    Query.QueryBuilder cityCodeQuery = new Query.QueryBuilder();
                    // 查询DictCity条件
                    Query.QueryBuilder cityQuery = new Query.QueryBuilder();
                    JobPositionCityRecord jobPositionCityRecord = new JobPositionCityRecord();
                    jobPositionCityRecord.setPid(pid);
                    logger.info("城市类型：:{}, pid:{}", city.getType().toLowerCase(), pid);
                    logger.info("VAlUE：{}, pid:{}", city.getValue(), pid);
                    // 城市名字，转换成cityCode，传入的是城市的时候查询dict_city
                    if (city.getType().toLowerCase().equals("text")) {
                        // 判断是不是特殊城市中的
                        String specicalCity = SpecialCtiy.specialCtiyMap.get(city.getValue().toLowerCase());
                        if (specicalCity != null) {
                            city.setValue(specicalCity);
                        }
                        if (org.apache.commons.lang.StringUtils.isBlank(city.getValue())) {
                            city.setValue("全国");
                        }
                        // 判断下是否是中文还是英文
                        if (isChinese(city.getValue())) { // 是中文
                            cityQuery.where("name", city.getValue());
                        } else { // 英文
                            SpecialProvince province = SpecialProvince.instanceOfMappingName(city.getValue().toLowerCase());
                            if (province != null) {
                                city.setValue(province.getName());
                            }
                            cityQuery.where("ename", city.getValue());
                        }
                        try {
                            logger.info("cityCode value : {}, pid:{}", city.getValue(), pid);
                            DictCityDO dictCityDO = (DictCityDO) cityMap.get(city.getValue());
                            logger.info("cityCode dictCityDO : {}, pid:{}", dictCityDO, pid);
                            if (dictCityDO != null) {
                                jobPositionCityRecord.setCode(dictCityDO.getCode());
                            } else {
                                dictCityDO = dictCityDao.getData(cityQuery.buildQuery());
                                logger.info("cityCode dictCityDO : {}, pid:{}", dictCityDO, pid);
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
                    if (StringUtils.isNullOrEmpty(city.getValue())) {
                        city.setValue("全国");
                    }
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

    public List<String> getThirdPartyPositions(Query query) throws BIZException {
        List<TwoParam<HrThirdPartyPositionDO, Object>> list = thirdpartyPositionDao.getDatas(query);

        List<String> result = new ArrayList<>();

        for (TwoParam<HrThirdPartyPositionDO, Object> p : list) {

            ChannelType channelType = ChannelType.instaceFromInteger(p.getR1().getChannel());

            JSONObject form = positionChangeUtil.transferSimpleFactory(channelType).toThirdPartyPositionForm(p.getR1(), p.getR2());

            result.add(form.toJSONString());
        }

        return result;
    }

    /**
     * 获取微信端职位列表
     * @param query 查询条件
     * @return 微信端职位列表信息
     */
    @CounterIface
    public List<WechatPositionListData> getPositionList(WechatPositionListQuery query) {

        List<WechatPositionListData> dataList = new ArrayList<>();

        try {
            String childCompanyId = "";
            String companyId = "";

            logger.info("getPositionList did:{},company_id:{},query:{}", query.getDid(), query.getCompany_id(), BeanUtils.convertStructToJSON(query));

            //记录搜索历史
            if (query.getUser_id() > 0 && StringUtils.isNotNullOrEmpty(query.getKeywords())) {
                pool.startTast(() -> {
                    updateRedisUserSearchPositionHistory(query.getUser_id(), query.getKeywords());
                    return 0;
                });
            }
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
            Response ret = searchengineServices.queryPositionIndex(
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
                    query.getCustom(),
                    query.getHb_config_id()
                    , null
            );
            logger.info("============================================");
            logger.info(JSON.toJSONString(ret));
            logger.info("============================================");
            if (ret.getStatus() == 0 && !StringUtils.isNullOrEmpty(ret.getData())) {
                JSONObject jobj = JSON.parseObject(ret.getData());
                JSONArray jdIdJsonArray = jobj.getJSONArray("jd_id_list");
                long totalNum = jobj.getLong("total");
                List<Integer> jdIdList = jdIdJsonArray.stream().map(m -> Integer.valueOf(String.valueOf(m))).collect(Collectors.toList());
                dataList = this.getWxPosition(jdIdList, (int) totalNum);
//                if (!StringUtils.isEmptyList(dataList)) {
//                    for (WechatPositionListData data : dataList) {
//                        data.setTotalNum((int) totalNum);
//                    }
//                }
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

    private void updateRedisUserSearchPositionHistory(int userId, String keywords) {
        logger.info("updateRedisUserSearchPositionHistory keywords:{}", keywords);
        String info = redisClient.get(Constant.APPID_ALPHADOG, KeyIdentifier.USER_POSITION_SEARCH.toString(), String.valueOf(userId));
        List<String> history = null;
        if (StringUtils.isNotNullOrEmpty(info)) {
            history = (List) JSONObject.parse(info);
        } else {
            history = new ArrayList<>();
        }
        history.remove(keywords);
        history.add(0, keywords);
        if (history.size() > 8) {
            history = history.subList(0, 8);
        }
        String result = JSONObject.toJSONString(history);
        logger.info("updateRedisUserSearchPositionHistory result:{}", result);
        redisClient.set(Constant.APPID_ALPHADOG, KeyIdentifier.USER_POSITION_SEARCH.toString(), String.valueOf(userId), result);
    }

    /*
       微信端获取个人画像推送职位
     */
    @CounterIface
    public List<WechatPositionListData> getPersonaRecomPosition(int userId, int companyId, int type, int pageNum, int pageSize) throws Exception {
        List<CampaignPersonaRecomRecord> list = this.getPersonaRecomPositionList(userId, companyId, type, pageNum, pageSize);
        List<Integer> pids = this.getRecomPositionIdList(list);
        if (StringUtils.isEmptyList(pids)) {
            return null;
        }
        int count = this.getPersonaRecomPositionListNum(userId, companyId, type);
        logger.info("==================================" + count);
        List<WechatPositionListData> result = this.getRecomWxPosition(pids, count);
        //这段本来可以不加，可是涉及到分页，所以肯定要在这边加上修改是否推送的功能
        if (!StringUtils.isEmptyList(result) && pageNum > 1) {
            this.updateIsSendStatus(list);
        }
        return result;
    }

    @CounterIface
    public List<WechatPositionListData> getEmployeeRecomPositionList(int recomPushId, int companyId, int type, int pageNum, int pageSize) {
        List<Integer> pids = this.handlerEmployeeRecom(recomPushId, companyId, type, pageNum, pageSize);
        if (StringUtils.isEmptyList(pids)) {
            return null;
        }

        int count = pids.size();
        logger.info("==================================" + count);
        List<WechatPositionListData> result = this.getRecomWxPosition(pids, count);

        return result;
    }

    /*
     处理推送数据，获取position.id的list
     */
    private List<Integer> handlerEmployeeRecom(int recomPushId, int companyId, int type, int pageNum, int pageSize) {
        CampaignRecomPositionlistRecord campaignRecomPosition = this.getCampaignRecomPositionlistByIdAndCompanyType(recomPushId, companyId, type, pageNum, pageSize);
        if (campaignRecomPosition == null) {
            return null;
        }
        List<Integer> list = this.convertStringToList(campaignRecomPosition.getPositionIds());
        return list;
    }

    /*
      获取推送的数据记录
     */
    private CampaignRecomPositionlistRecord getCampaignRecomPositionlistByIdAndCompanyType(int recomPushId, int companyId, int type, int pageNum, int pageSize) {
        Query query = new Query.QueryBuilder().where("id", recomPushId).and("company_id", companyId)
                .and("type", (byte) type)
                .setPageNum(pageNum)
                .setPageSize(pageSize)
                .buildQuery();
        CampaignRecomPositionlistRecord data = campaignRecomPositionlistDao.getRecord(query);
        return data;
    }

    /*
   获取推送的数据记录
  */
    private int getCampaignRecomPositionlistByIdAndCompanyTypeCount(int recomPushId, int companyId, int type) {
        Query query = new Query.QueryBuilder().where("id", recomPushId).and("company_id", companyId)
                .and("type", (byte) type)
                .buildQuery();
        int count = campaignRecomPositionlistDao.getCount(query);
        return count;
    }

    /*
     将String转化为list
     */
    private List<Integer> convertStringToList(String positionIds) {
        if (StringUtils.isNullOrEmpty(positionIds)) {
            return null;
        }
        List<Integer> pidList = new ArrayList<>();
        String[] pidArray = positionIds.split(",");
        for (String pid : pidArray) {
            pidList.add(Integer.parseInt(pid));
        }
        return pidList;
    }

    /*
      通过user_id ,公司id，类型 获取 CampaignPersonaRecomPojo 的list集合
     */

    private List<CampaignPersonaRecomRecord> getPersonaRecomPositionList(int userId, int companyId, int type, int pageNum, int pageSize) {
        Query query = new Query.QueryBuilder().where("user_id", userId).and("company_id", companyId).and("type", (byte) type).orderBy("id", Order.ASC).setPageNum(pageNum).setPageSize(pageSize).buildQuery();
        List<CampaignPersonaRecomRecord> list = campaignPersonaRecomDao.getRecords(query);
        return list;
    }

    /*
     通过user_id 获取 CampaignPersonaRecomPojo 的数量
    */
    private int getPersonaRecomPositionListNum(int userId, int companyId, int type) {
        Query query = new Query.QueryBuilder().where("user_id", userId).and("company_id", companyId).and("type", (byte) type).buildQuery();
        int result = campaignPersonaRecomDao.getCount(query);
        return result;
    }

    /*
     根据userId，companyId，type，List<Integer> pid获取
     */
    /*
      获取position.id的list
     */
    private List<Integer> getRecomPositionIdList(List<CampaignPersonaRecomRecord> list) {
        if (StringUtils.isEmptyList(list)) {
            return null;
        }
        List<Integer> result = new ArrayList<>();
        for (CampaignPersonaRecomRecord DO : list) {
            int positionId = DO.getPositionId();
            result.add(positionId);
        }
        return result;
    }

    /*
      更新为已读状态
     */
    public void updateIsSendStatus(List<CampaignPersonaRecomRecord> list) {
        SimpleDateFormat f = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String date = f.format(new Date());
        if (!StringUtils.isEmptyList(list)) {
            for (CampaignPersonaRecomRecord DO : list) {
                DO.setIsSend((byte) 1);
                DO.setSendTime(new Timestamp(System.currentTimeMillis()));
            }
            campaignPersonaRecomDao.updateRecords(list);
        }
    }

    private List<WechatPositionListData> getRecomWxPosition(List<Integer> jdIdList, int count) {
        logger.info("jdIdList: " + jdIdList);
        Condition con = new Condition("id", jdIdList.toArray(), ValueOp.IN);
        Query q = new Query.QueryBuilder().where(con).buildQuery();
        List<JobPositionRecordWithCityName> jobRecords = positionEntity.getPositions(q);
        List<Integer> activityPositions = positionJOOQDao.getHbPositionList(jdIdList);
        List<WechatPositionListData> dataList = this.handerPositionWx(jdIdList, jobRecords, count, activityPositions);
        return dataList;
    }

    /*
     将通过position.id获取微信端职位列表的接口拆出来，单独成立私有方法，从而保证可共用性
     */
    private List<WechatPositionListData> getWxPosition(List<Integer> jdIdList, int count) {
        // 通过 pid 列表查询 position 信息
        logger.info("jdIdList: " + jdIdList);
        Condition con = new Condition("id", jdIdList.toArray(), ValueOp.IN);
        Query q = new Query.QueryBuilder().where(con).and("status", 0).orderBy("priority", Order.ASC).orderBy("update_time", Order.DESC).orderBy("id", Order.DESC).buildQuery();
        List<JobPositionRecordWithCityName> jobRecords = positionEntity.getPositions(q);
        List<Integer> activityPositions = positionJOOQDao.getHbPositionList(jdIdList);
        List<WechatPositionListData> dataList = this.handerPositionWx(jdIdList, jobRecords, count, activityPositions);
        return dataList;
    }

    private List<WechatPositionListData> handerPositionWx(List<Integer> jdIdList, List<JobPositionRecordWithCityName> jobRecords, int count, List<Integer> activityPositions) {
        List<WechatPositionListData> dataList = new ArrayList<>();
        Set<Integer> teamIdList = new HashSet<Integer>();

        for (int i = 0; i < jdIdList.size(); i++) {
            int positionId = jdIdList.get(i);
            for (JobPositionRecordWithCityName jr : jobRecords) {
                if (positionId == jr.getId()) {
                    //logger.info("pid: " + String.valueOf(jr.getId()));
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
                    int visitorNum = 0;
                    if (jr.getVisitnum() != null) {
                        visitorNum = jr.getVisitnum();
                    }
                    e.setVisitnum(visitorNum);
                    if (activityPositions != null && activityPositions.size() > 0) {
                        if (activityPositions.contains(jr.getId())) {
                            e.setIn_hb(true);
                        } else {
                            e.setIn_hb(false);
                        }
                    } else {
                        e.setIn_hb(false);
                    }
                    e.setCount(jr.getCount());
                    e.setCity(jr.getCity());
                    e.setCity_ename(jr.getCityEname());
                    e.setPriority(jr.getPriority());
                    e.setPublisher(jr.getPublisher()); // will be used for fetching sub company info
                    e.setAccountabilities(jr.getAccountabilities());
                    e.setCandidate_source(jr.getCandidateSource());
                    e.setRequirement(jr.getRequirement());
                    e.setTotal_num(count);
                    e.setIs_referral(jr.getIsReferral());
                    e.setEmployment_type(jr.getEmploymentType());
                    e.setEmployment_type_name(jr.getEmploymentType() != null ? WorkType.instanceFromInt(jr.getEmploymentType()).getName() : "");
                    e.setUpdate_time(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(jr.getUpdateTime()));
                    e.setDegree(jr.getDegree());
                    e.setDegree_above(jr.getDegreeAbove());
                    e.setExperience(jr.getExperience());
                    e.setExperience_above(jr.getExperienceAbove());
                    e.setTeam_id(jr.getTeamId());
                    dataList.add(e);
                    teamIdList.add(jr.getTeamId());
                    break;
                }
            }
        }
        //logger.info(dataList.toString());
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

        // 获取发布人信息，拼装发布人姓名
        Map<Integer /* publisher id */, UserHrAccountDO> publisherUserHrAccountMap = new HashMap<>();

        //  根据publishSet 查询 user_hr_account
        Condition condition2 = new Condition("id", publisherSet.toArray(), ValueOp.IN);
        Query.QueryBuilder hrm2 = new Query.QueryBuilder();

        hrm2.where(condition2);
        List<UserHrAccountDO> userAccountList = userHrAccountDao.getDatas(hrm2.buildQuery(), UserHrAccountDO.class);
        logger.info(userAccountList.toString());
        for (UserHrAccountDO userHrAccountDO : userAccountList) {
            publisherUserHrAccountMap.put(userHrAccountDO.getId(), userHrAccountDO);
        }

        // 获取hrteam，填入职位的部门字段
        Map<Integer /* team id */, HrTeamDO> hrTeamMap = new HashMap<>();
        Condition condition3 = new Condition("id", teamIdList.toArray(), ValueOp.IN);
        Query.QueryBuilder hrm3 = new Query.QueryBuilder();
        hrm3.where(condition3);

        List<HrTeamDO> hrTeamDOS = hrTeamDao.getDatas(hrm3.buildQuery(), HrTeamDO.class);
        logger.info("handerPositionWx hrTeamDOS hrTeamDOS {}  teamIdList {}", JSON.toJSONString(hrTeamDOS), JSON.toJSONString(teamIdList));
        for (HrTeamDO hrTeamDO : hrTeamDOS) {
            hrTeamMap.put(hrTeamDO.getId(), hrTeamDO);
        }

        // 获取职位的内推奖金
        List<Integer> bonusPidList = dataList
                .stream()
                .filter(data -> data.getIs_referral() == 1)
                .map(WechatPositionListData::getId)
                .collect(Collectors.toList());
        if (bonusPidList == null) {
            bonusPidList = new ArrayList<>(0);
        }
        Map<Integer, ReferralPositionBonusVO> refBonusMap = referralPositionBonusDao.fetchByPid(bonusPidList);

        //拼装 company 和 publisher 相关内容
        dataList = dataList.stream().map(s -> {
            s.setCompany_abbr(publisherCompanyMap.get(s.getPublisher()) == null ? "" : publisherCompanyMap.get(s.getPublisher()).getAbbreviation());
            s.setCompany_logo(publisherCompanyMap.get(s.getPublisher()) == null ? "" : publisherCompanyMap.get(s.getPublisher()).getLogo());
            s.setCompany_name(publisherCompanyMap.get(s.getPublisher()) == null ? "" : publisherCompanyMap.get(s.getPublisher()).getName());
            //添加发布人姓名
            s.setPublisher_name(publisherUserHrAccountMap.get(s.getPublisher()) == null ? "" : publisherUserHrAccountMap.get(s.getPublisher()).getUsername());

            //添加部门信息 如果department为空，用hrteam name
            if (StringUtils.isNullOrEmpty(s.getDepartment())) {
                s.setDepartment(hrTeamMap.get(s.getTeam_id()) == null ? "" : hrTeamMap.get(s.getTeam_id()).getName());
            }

            //添加内推奖金信息
            if (refBonusMap != null) {
                s.setTotal_bonus(refBonusMap.get(s.getId()) == null ? null : (refBonusMap.get(s.getId()).getPosition_bonus()).getTotal_bonus());
            }
            return s;
        }).collect(Collectors.toList());

        return dataList;
    }

    /*
     * @Author zztaiwll
     * @Description  获取active_id的列表
     * @Date 下午5:38 19/1/14
     * @Param [list]
     * @return java.util.List<java.lang.Integer>
     **/
    private List<Integer> getRedpacketActivityIdList(List<RedpacketActivityPosition> list) {
        if (StringUtils.isEmptyList(list)) {
            return null;
        }
        List<Integer> idList = new ArrayList<>();
        for (RedpacketActivityPosition redpacketActivityPosition : list) {
            idList.add(redpacketActivityPosition.getActivityId());
        }
        return idList;
    }

    /**
     * 获得红包活动的分享信息
     * @param hb_config_id 红包活动id
     * @return 红包活动分享信息
     */
    public WechatShareData getShareInfo(int hb_config_id) {
        WechatShareData result = new WechatShareData();

        Query qu = new Query.QueryBuilder()
                .where("id", hb_config_id).buildQuery();
        try {
            RedpacketActivity redpacketActivity = activityDao.fetchOneById(hb_config_id);
            result.setCover(redpacketActivity.getShareImg());
            result.setTitle(redpacketActivity.getShareTitle());
            result.setDescription(redpacketActivity.getShareDesc());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @CounterIface
    public List<RpExtInfo> getNewPositionListRpExt(List<Integer> pids) {
        if (StringUtils.isEmptyList(pids)) {
            return new ArrayList<>();
        }
        List<RedpacketActivityPosition> list = activityPositionJOOQDao.fetchRuningPositionsByPositionIdList(pids);

        if (list != null && list.size() > 0) {
            List<Integer> positionIdList = new ArrayList<>();
            List<Integer> activityIdList = new ArrayList<>();
            list.forEach(redpacketActivityPositionDO -> {
                activityIdList.add(redpacketActivityPositionDO.getActivityId());
            });

            List<RedpacketActivity> activityDOS = activityDao.fetchByIdList(activityIdList);

            Map<Integer, RedpacketActivity> map = new HashMap<>();
            activityDOS.forEach(activityDO -> map.put(activityDO.getId(), activityDO));
            return list.stream().map(position -> {
                RpExtInfo infoVO = new RpExtInfo();
                infoVO.setPid(position.getPositionId());
                int remain = 0;
                for (RedpacketActivityPosition activityPosition : list) {
                    remain += activityPosition.getLeftAmount();
                }
                infoVO.setRemain(remain * 100L / 100);

                List<RedpacketActivity> activityList = activityDOS
                        .stream()
                        .filter(redpacketActivity -> redpacketActivity.getId().equals(position.getActivityId())
                                && redpacketActivity.getTarget() != 0)
                        .collect(Collectors.toList());
                if (activityList != null && activityList.size() > 0) {
                    infoVO.setEmployee_only(true);
                } else {
                    infoVO.setEmployee_only(false);
                }
                return infoVO;
            }).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
    /* *//*
     遍历处理数据，获取需要的RpExtInfo列表
     *//*
    private List<RpExtInfo> handlerPositionListRpExt(List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition> positionList,
                                                     List<RedpacketActivity> hrHbList,
                                                     List<RedpacketActivityPosition> bindingList) {
        List<RpExtInfo> result=new ArrayList<>();
        for(com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition position:positionList){
            RpExtInfo rpExtInfo=new RpExtInfo();
            int pid=position.getId();
            *//*
     * 找到职位下对应的HrHbPositionBinding.id的列表和HrHbConfig.id的列表
     *//*
            rpExtInfo.setPid(pid);
            Optional<RedpacketActivityPosition> positionOptional = bindingList
                    .stream()
                    .filter(redpacketActivityPosition -> redpacketActivityPosition.getPositionId().equals(pid))
                    .findAny();

            List<Integer> positionBindingIdList=new ArrayList<>();
            List<Integer> hbConfigIdList=new ArrayList<>();
            for(RedpacketActivityPosition activityPosition:bindingList){
                if(pid==hrHbPositionBinding.getPositionId()){
                    positionBindingIdList.add(hrHbPositionBinding.getId());
                    if(!hbConfigIdList.contains(hrHbPositionBinding.getHbConfigId())){
                        hbConfigIdList.add(hrHbPositionBinding.getHbConfigId());
                    }
                }
            }
            *//*
             根据获取的HrHbConfig.id，查找target 从而用来确定Employee_only的属性值
             *//*
            rpExtInfo.setEmployee_only(true);
            if(!StringUtils.isEmptyList(hbConfigIdList)){
                for(HrHbConfig hrHbConfig:hrHbList){
                    int id=hrHbConfig.getId();
                    if(hbConfigIdList.contains(id)){
                        if(hrHbConfig.getTarget()>0){
                            rpExtInfo.setEmployee_only(false);
                            break;
                        }
                    }
                }
            }
             *//*
             根据获取的HrHbConfig.id，查找HrHbItems列表 计算amount的值
             *//*
            if(!StringUtils.isEmptyList(positionBindingIdList)){
                double remain=0;
                for(RedpacketRedpacket redpacketRedpacket:hrHbItemsList){
                    int bid=hrHbItems.getBindingId();
                    if(positionBindingIdList.contains(bid)){
                        remain=remain+hrHbItems.getAmount().doubleValue();
                    }
                }
                rpExtInfo.setRemain(remain);
            }
            result.add(rpExtInfo);
        }
        return result;
    }*/

    /*
     根据公司获取正在运行的红包配置表
     */
    private List<RedpacketActivity> getHrHbList(int companyId, int status) {
        List<Integer> typeList = new ArrayList<>();
        typeList.add(2);
        typeList.add(3);
        typeList.add(4);
        List<RedpacketActivity> activities = activityDao.fetchActiveActivitiesByCompanyId(companyId, status, typeList);
        return activities;
    }

    /*
     获取HrHbConfig.id列表
     */
    private List<Integer> getHbConfgIdList(List<RedpacketActivity> list) {
        if (list != null && list.size() > 0) {
            return list
                    .stream()
                    .map(RedpacketActivity::getId)
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }
    /*
    获取HrHbPositionBinding.id列表
    *//*
    private List<Integer> getHrHbPositionBindingIdList(List<RedpacketActivityPosition> list){
        if (list != null && list.size() > 0) {
            return list
                    .stream()
                    .map(RedpacketActivityPosition::getId)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>(0);
        }
    }*/

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
     * @param hbConfigId 红包活动id
     * @return 红包职位列表
     */
    public List<WechatRpPositionListData> getRpPositionList(int hbConfigId, int pageNum, int pageSize) {
        if (pageSize > Constant.DATABASE_PAGE_SIZE) {
            new ArrayList<>(0);
        }
        List<WechatRpPositionListData> result = new ArrayList<>();
        Query qu = new Query.QueryBuilder()
                .where("hb_config_id", hbConfigId)
                .setPageNum(pageNum)
                .setPageSize(pageSize)
                .buildQuery();

        int size = pageSize;
        if (size <= 0) {
            size = 15;
        }
        int start = (pageNum - 1) * size;
        List<RedpacketActivityPosition> bindings = positionJOOQDao.listByActivityId(hbConfigId, true, start, size);
        //activityPositionJOOQDao.list

        List<Integer> pids = bindings.stream().map(RedpacketActivityPosition::getPositionId).collect(Collectors.toList());

        Condition condition = new Condition("id", pids.toArray(), ValueOp.IN);
        Query q = new Query.QueryBuilder().where(condition).and("status", 0).orderBy("priority")
                .orderBy("id", Order.DESC).buildQuery();
        List<JobPositionRecordWithCityName> jobRecords = positionEntity.getPositions(q);
        if (StringUtils.isEmptyList(jobRecords)) {
            return result;
        }
        // filter 出已经发完红包的职位
        //jobRecords = jobRecords.stream().filter(p -> p.getHbStatus() > 0).collect(Collectors.toList());
        int totalNum = this.getRpPositionCount(hbConfigId);
        // 拼装职位信息
        for (JobPositionRecordWithCityName jr : jobRecords) {
            WechatRpPositionListData e = new WechatRpPositionListData();
            e.setTitle(jr.getTitle());
            e.setId(jr.getId());
            if (jr.getSalaryTop() != null) {
                e.setSalary_top(jr.getSalaryTop());
            }
            if (jr.getSalaryBottom() != null) {
                e.setSalary_bottom(jr.getSalaryBottom());
            }
            e.setPublish_date(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(jr.getPublishDate()));
            e.setUpdate_time(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(jr.getUpdateTime()));
            e.setDepartment(jr.getDepartment());
            e.setVisitnum(jr.getVisitnum());
            e.setIn_hb(true);
            e.setCount(jr.getCount());
            e.setCity(jr.getCity());
            e.setCity_ename(jr.getCityEname());
            e.setCandidate_source(jr.getCandidateSource());
            e.setRequirement(jr.getRequirement());
            e.setTotal_num(totalNum);
            e.setIs_referral(jr.getIsReferral());
            result.add(e);
        }

        // 拼装公司信息
        RedpacketActivity redpacketActivity = activityDao.fetchOneById(hbConfigId);
        qu = new Query.QueryBuilder().where("id", redpacketActivity.getCompanyId()).buildQuery();
        HrCompanyDO company = hrCompanyDao.getData(qu, HrCompanyDO.class);
        result.forEach(s -> {
            s.setCompany_abbr(company == null ? "" : company.getAbbreviation());
            s.setCompany_logo(company == null ? "" : company.getLogo());
            s.setCompany_name(company == null ? "" : company.getName());
        });

        // 拼装红包信息
//        List<RpExtInfo> rpExtInfoList = getPositionListRpExt(pids);
        List<RpExtInfo> rpExtInfoList = getNewPositionListRpExt(pids);
        if (!StringUtils.isEmptyList(rpExtInfoList)) {
            result.forEach(s -> {
                RpExtInfo rpInfo = rpExtInfoList.stream().filter(e -> e.getPid() == s.getId()).findFirst().orElse(
                        null);
                if (rpInfo != null) {
                    s.setRemain(rpInfo.getRemain());
                    s.setEmployee_only(rpInfo.isEmployee_only());
                }
            });
        }
        return result;
    }

    /*
     获取所有的红包职位数量
     */
    private int getRpPositionCount(int activityId) {
        int count = activityPositionJOOQDao.countEnableByActivityId(activityId);
        return count;
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
        positionForAlipaycampusPojo.setJob_desc(PositionUtil.convertDescription(positionRecord.getAccountabilities(), positionRecord.getRequirement()));

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
                query = new Query.QueryBuilder().select("id").select("company_id").where("status", 0)
                        .and(new Condition("update_time", start_time, ValueOp.GE))
                        .and(new Condition("update_time", end_time, ValueOp.LT)).buildQuery();
                break;
            case 1: //刷新
                query = new Query.QueryBuilder().select("id").select("company_id").where("status", 0)
                        .and(new Condition("update_time", start_time, ValueOp.GE))
                        .and(new Condition("update_time", end_time, ValueOp.LT)).buildQuery();
                break;
            case 2:
                query = new Query.QueryBuilder().select("id").select("company_id").where(new Condition("status", 0, ValueOp.NEQ))
                        .and(new Condition("update_time", start_time, ValueOp.GE))
                        .and(new Condition("update_time", end_time, ValueOp.LT)).buildQuery();
                break;
            default:
                return null;
        }

        Query queryShowInQxCompany = new Query.QueryBuilder().select("company_id").where("show_in_qx", 1).buildQuery();

        List<HrCompanyConfDO> companyShowInQxList = hrCompanyConfDao.getDatas(queryShowInQxCompany);
        List<Integer> companylist = null;
        if (companyShowInQxList != null) {
            companylist = new ArrayList<>(companyShowInQxList.size());
            for (HrCompanyConfDO companyconf : companyShowInQxList) {
                companylist.add(companyconf.getCompanyId());
            }
        }

        List<JobPositionDO> jobPositionList = jobPositionDao.getPositions(query);

        List<Integer> positionlist = null;
        if (jobPositionList != null) {
            positionlist = new ArrayList<>(jobPositionList.size());
            for (JobPositionDO position : jobPositionList) {

                // 上架或者刷新，需要严格判断开启仟寻展示； 由于目前所有职位都在支付宝， 下架不需要判断是否开启。
                if (type == 0 || type == 1) {
                    if (companylist.contains(position.getCompanyId())) {
                        positionlist.add(position.getId());
                    }
                } else {
                    positionlist.add(position.getId());
                }
            }
        }

        return positionlist;
    }

    private static Pattern p = Pattern.compile("\\s*|\t|\r|\n");

    private String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
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
    public List<JobPositionRecordWithCityName> getPositionRecords(Query query) {
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
                //logger.info("---Start ES Search Engine---");
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
                        //logger.info("position_map:" + position_map.toString());
                    }
                    position = JSON.toJSONString(position_map);
                    //logger.info("position:" + position);
                    searchengineServices.updateposition(position, jobPositionId);
                }
                //logger.info("--- ES Search Engine end---");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public Response positionCvConf(int positionId) {
        int appCvConfId = positionEntity.getAppCvConfigIdByPosition(positionId);
        if (appCvConfId != 0) {
            Query.QueryBuilder query = new Query.QueryBuilder();
            query.where("id", appCvConfId);
            HrAppCvConfDO hrAppCvConfDO = hrAppCvConfDao.getData(query.buildQuery());
            if (hrAppCvConfDO != null && StringUtils.isNotNullOrEmpty(hrAppCvConfDO.getFieldValue())) {
                List<ConfigCustomMetaVO> configCustomMetaVOList = JSONArray.parseArray(hrAppCvConfDO.getFieldValue()).stream().flatMap(fm -> JSON.parseObject(String.valueOf(fm)).getJSONArray("fields").stream()).
                        map(m -> JSONObject.parseObject(String.valueOf(m), ConfigCustomMetaVO.class)).collect(Collectors.toList());
                configCustomMetaVOList.stream().filter(f -> f.getConstantParentCode() != 0).forEach(e -> {
                    query.clear();
                    query.where("parent_code", e.getConstantParentCode());
                    List<DictConstantDO> dictConstantDO = dictConstantDao.getDatas(query.buildQuery());
                    String dictconstantValue = JSON.toJSONString(dictConstantDO, new PropertyFilter() {
                                @Override
                                public boolean apply(Object object, String name, Object value) {
                                    if ("code".equals(name) || "name".equals(name)) {
                                        return true;
                                    }
                                    return false;
                                }
                            }
                    );
                    e.setConstantValue(dictconstantValue);
                });
                return ResponseUtils.success(configCustomMetaVOList);
            }
        }
        logger.error("自定义配置为空，positionId:{}, appCvConfId:{}", positionId, appCvConfId);
        return ResponseUtils.fail(1, "自定义配置为空");
    }

    /**
     * 小程序调用来进行职位上下架
     * ATS调用来进行职位上下架
     * 如果有修改，请同时修改上面两个项目
     * @param param
     * @return
     */
    public Response updatePosition(String param) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Response response = updatePositionAdaptor(param, countDownLatch);
        countDownLatch.countDown();
        return response;
    }

    private Response updatePositionAdaptor(String param, CountDownLatch countDownLatch) {
        logger.info("===================批量上下架开始===================");
        JSONObject obj = JSONObject.parseObject(param);
        int position_id = obj.getIntValue("id");
        int account_id = obj.getIntValue("accountId");
        String keyword = obj.getString("keyword");
        Query accountQuery = new Query.QueryBuilder().where(UserHrAccount.USER_HR_ACCOUNT.ID.getName(), account_id).buildQuery();
        UserHrAccountDO accountDO = userHrAccountDao.getData(accountQuery);
        JobPositionDO positionDO = null;
        if (accountDO != null && accountDO.getAccountType() != 0) {
            Query query = new Query.QueryBuilder().where(JobPosition.JOB_POSITION.ID.getName(), position_id)
                    .and(JobPosition.JOB_POSITION.PUBLISHER.getName(), account_id).buildQuery();
            positionDO = jobPositionDao.getData(query);
        }

        if ((accountDO != null && accountDO.getAccountType() == 0) || positionDO != null) {
            try {

                Map<String, Object> updateField = obj.getObject("updateField", Map.class);
                Date date = new Date();
                String dateStr = DateUtils.dateToShortTime(date);
                updateField.put("updateTime", new Timestamp(date.getTime()));
                JobPositionRecord record = BeanUtils.MapToRecord(updateField, JobPositionRecord.class);
                jobPositionDao.updateRecord(record);
                updateField.put("updateTime", dateStr);
                kafkaSender.sendPositionStatus(position_id, (Integer) updateField.get("status"), accountDO.getCompanyId());

                // 猎聘api新增
                if (updateField.get("status") != null) {
                    if (positionDO != null && (int) updateField.get("status") == 2 && positionDO.getStatus() == 0) {

                        pool.startTast(() -> {
                            if (countDownLatch.await(60, TimeUnit.SECONDS)) {
                                List<Integer> ids = new ArrayList<>();
                                ids.add(position_id);
                                return receiverHandler.batchHandlerLiepinDownShelfOperation(ids);
                            } else {
                                throw new RuntimeException("rabbitmq线程等待超时");
                            }
                        });
                    }
                }

                return ResponseUtils.success(updateField);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
            }
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_UPDATE_FAIL);
        }
    }

    /**
     * 输入的字符是否是汉字
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

    /**
     * 获取内职位列表
     * @param query 查询条件
     * @return 微信端职位列表信息
     */
    @CounterIface
    public List<WechatPositionListData> getReferralPositionList(Map<String, String> query) {

        logger.info("PositionService getReferralPositionList");
        logger.info("PositionService getReferralPositionList query:{}", JSONObject.toJSONString(query));
        List<WechatPositionListData> dataList = new ArrayList<>();
        try {
            WechatPositionListQuery searchParams = this.convertParams(query);
            logger.info("PositionService getReferralPositionList searchParams:{}", JSONObject.toJSONString(searchParams));
            Response res = this.getResponseEs(searchParams);
            if (res.getStatus() == 0 && !StringUtils.isNullOrEmpty(res.getData())) {
                JSONObject jobj = JSON.parseObject(res.getData());
                logger.info("PositionService getReferralPositionList totalNum:{}", jobj.getLong("total"));
                long totalNum = jobj.getLong("total");
                List<String> jdIdList = (List<String>) jobj.get("jd_id_list");
                List<Integer> idList = StringUtils.convertStringToIntegerList(jdIdList);
                dataList = this.getWxPosition(idList, (int) totalNum);
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

    private WechatPositionListQuery convertParams(Map<String, String> map) throws Exception {
        WechatPositionListQuery query = new WechatPositionListQuery();

        if (map.getOrDefault("company_id", null) != null) {
            query.setCompany_id(Integer.valueOf((String) map.get("company_id")));
        } else {
            throw new Exception("公司 id 未提供!");
        }

        int pageNum = Integer.valueOf(map.getOrDefault("page_from", "0"));
        int pageSize = Integer.valueOf(map.getOrDefault("page_size", "10"));
        query.setPage_from((pageNum - 1) * pageSize);
        query.setPage_size(pageSize);
        query.setUser_id(Integer.valueOf(map.getOrDefault("user_id", "0")));
        query.setKeywords(StringUtils.filterStringForSearch(map.getOrDefault("keyWord", "")));
        query.setCities((String) map.getOrDefault("cities", ""));
        query.setIndustries(StringUtils.filterStringForSearch(map.getOrDefault("industries", "")));
        query.setOccupations(map.getOrDefault("occupations", ""));
        query.setScale(map.getOrDefault("scale", ""));
        query.setCandidate_source(map.getOrDefault("candidate_source", ""));
        query.setEmployment_type(map.getOrDefault("employment_type", ""));
        query.setExperience(map.getOrDefault("experience", ""));
        query.setSalary(map.getOrDefault("salary", ""));
        query.setDegree(map.getOrDefault("degree", ""));
        query.setDepartment(map.getOrDefault("department", ""));
        query.setCustom(map.getOrDefault("custom", ""));
        query.setDid(Integer.valueOf(map.getOrDefault("did", "0")));
        query.setHb_config_id(map.getOrDefault("hb_config_id", ""));
        String param_setOrder_by_priority = map.getOrDefault("order_by_priority", "True");
        query.setOrder_by_priority(param_setOrder_by_priority.equals("True"));
        String isReference = map.getOrDefault("is_referral", "");
        if (StringUtils.isNotNullOrEmpty(isReference)) {
            query.setIs_referral(Integer.parseInt(isReference));
        } else {
            query.setIs_referral(-1);
        }
        return query;
    }

    private Response getResponseEs(WechatPositionListQuery query) throws TException {
        //获取 pid list
        Response ret = searchengineServices.queryPositionIndex(
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
                query.getCompany_id() + "",
                query.getPage_from(),
                query.getPage_size(),
                query.getDid() + "",
                query.getDepartment(),
                true,
                query.getCustom(),
                query.getHb_config_id()
                , query.getIs_referral() + ""
        );
        return ret;
    }

    /**
     * 刷入职位协助人
     * @param positionId
     * @param departmentCode
     */
    public void refreshCoordinators(Integer positionId, Integer companyId, String departmentCode) {
        try {
            Map<String, Object> params = Maps.newHashMap();
            params.put("jobPositionId", positionId);
            params.put("departmentCode", departmentCode);
            params.put("companyId", companyId);
            String result = HttpClientUtil.sentHttpPostRequest(refreshCoordinatorsUrl + "?appid=A11009&interfaceid=A11009001", null, JSON.toJSONString(params));
            logger.info("result: {}", result);
        } catch (Exception e) {
            logger.error("error: {}", e.getMessage());
        }
    }
}