package com.moseeker.position.service.fundationbs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.*;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.MD5Util;
import com.moseeker.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.db.hrdb.tables.records.HrCompanyAccountRecord;
import com.moseeker.db.jobdb.tables.records.*;
import com.moseeker.position.dao.*;
import com.moseeker.position.pojo.DictConstantPojo;
import com.moseeker.position.pojo.JobPositionPojo;
import com.moseeker.position.pojo.PositionForSynchronizationPojo;
import com.moseeker.position.pojo.RecommendedPositonPojo;
import com.moseeker.position.service.position.PositionChangeUtil;
import com.moseeker.position.utils.UpdataESThread;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.CompanyDao;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;
import com.moseeker.thrift.gen.dao.struct.ThirdPartyPositionData;
import com.moseeker.thrift.gen.position.struct.*;
import com.mysql.jdbc.StringUtils;
import org.apache.thrift.TException;
import org.jooq.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PositionService extends JOOQBaseServiceImpl<Position, JobPositionRecord> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PositionDao dao;

    @Autowired
    private JobPositionDao jobPositionDao;

    @Autowired
    private DictConstantDao dictConstantDao;

    @Autowired
    private JobCustomDao jobCustomDao;

    @Autowired
    private JobPositonExtDao jobPositonExtDao;

    @Autowired
    private JobOccupationDao jobOccupationDao;


    @Autowired
    private JobPositionCityDao jobPositionCityDao;


    com.moseeker.thrift.gen.dao.service.PositionDao.Iface positionDaoService = ServiceManager.SERVICEMANAGER
            .getService(com.moseeker.thrift.gen.dao.service.PositionDao.Iface.class);

    CompanyDao.Iface CompanyDao = ServiceManager.SERVICEMANAGER.getService(CompanyDao.Iface.class);


    com.moseeker.thrift.gen.searchengine.service.SearchengineServices.Iface searchengineServices = ServiceManager.SERVICEMANAGER
            .getService(com.moseeker.thrift.gen.searchengine.service.SearchengineServices.Iface.class);

    @Override
    protected void initDao() {
        super.dao = this.dao;
    }

    @Override
    protected Position DBToStruct(JobPositionRecord r) {
        return (Position) BeanUtils.DBToStruct(Position.class, r);
    }

    @Override
    protected JobPositionRecord structToDB(Position p) {
        return (JobPositionRecord) BeanUtils.structToDB(p, JobPositionRecord.class);
    }

    /**
     * 获取推荐职位
     * <p>
     * </p>
     *
     * @param pid
     * @return
     */
    @CounterIface
    public Response getRecommendedPositions(int pid) {

        List<RecommendedPositonPojo> recommendPositons = this.dao.getRecommendedPositions(pid);

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
        try {
            JobPositionRecord positionRecord = jobPositionDao.getPositionById(positionId);

            if (positionRecord == null) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_POSITION_NOTEXIST);
            }
            if (positionRecord.getAppCvConfigId() != null && positionRecord.getAppCvConfigId().intValue() > 0) {
                return ResponseUtils.success(true);
            } else {
                return ResponseUtils.success(false);
            }
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    /**
     * 根据职位Id获取当前职位信息
     *
     * @param positionId
     * @return
     * @throws TException
     */
    @CounterIface
    public Response getPositionById(int positionId) throws TException {

        try {
            // 必填项校验
            if (positionId == 0) {
                return ResponseUtils
                        .fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "position_id"));
            }

            // NullPoint check
            JobPositionRecord jobPositionRecord = jobPositionDao.getPositionById(positionId);
            if (jobPositionRecord == null) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }

            JobPositionPojo jobPositionPojo = jobPositionDao.getPosition(positionId);

            /** 子公司Id设置 **/
            if (jobPositionPojo.publisher != 0) {
                HrCompanyAccountRecord hrCompanyAccountRecord = jobPositionDao
                        .getHrCompanyAccountByPublisher(jobPositionPojo.publisher);
                // 子公司ID>0
                if (hrCompanyAccountRecord != null && hrCompanyAccountRecord.getCompanyId() > 0) {
                    jobPositionPojo.publisher_company_id = hrCompanyAccountRecord.getCompanyId();
                }
            }

            /** 常量转换 **/
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
                    JobCustomRecord jobCustomRecord = jobCustomDao
                            .getJobCustomRecord(jobPositionExtRecord.getJobCustomId());
                    if (jobCustomRecord != null && !"".equals(jobCustomRecord.getName())) {
                        jobPositionPojo.custom = jobCustomRecord.getName();
                    }
                }
                if (jobPositionExtRecord.getJobCustomId() > 0) {
                    JobOccupationRecord jobOccupationRecord =
                            jobOccupationDao.getJobOccupationRecord(jobPositionExtRecord.getJobOccupationId());
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
            List<DictCityRecord> provinces = dao.getProvincesByPositionID(positionId);
            if (provinces != null && provinces.size() > 0) {
                StringBuffer sb = new StringBuffer();
                provinces.forEach(province -> {
                    sb.append(province.getName() + ",");
                });
                sb.deleteCharAt(sb.length() - 1);
                jobPositionPojo.province = sb.toString();
            }

            return ResponseUtils.success(jobPositionPojo);
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    /**
     * 获取常量字典一条记录
     *
     * @param parentCode
     * @param code
     * @return
     * @throws Exception
     */
    private String getDictConstantJson(Integer parentCode, Integer code) throws Exception {
        DictConstantPojo dictConstantPojo = dictConstantDao.getDictConstantJson(parentCode, code);
        return dictConstantPojo != null ? dictConstantPojo.getName() : "";
    }

    private JobPositionExtRecord getJobPositionExtRecord(int positionId) {
        return jobPositonExtDao.getJobPositonExtRecordByPositionId(positionId);
    }

    /**
     * 校验同步职位必填信息
     *
     * @param position
     * @return
     */
    public boolean verifySynchronizePosition(PositionForSynchronizationPojo position) {
        return false;
    }

    /**
     * 转成第三方渠道职位
     *
     * @param forms
     * @param position
     * @return
     */
    public List<ThirdPartyPositionForSynchronization> changeToThirdPartyPosition(List<ThirdPartyPosition> forms,
                                                                                 Position position) {
        List<ThirdPartyPositionForSynchronization> positions = new ArrayList<>();
        if (forms != null && forms.size() > 0 && position != null && position.getId() > 0) {
            forms.forEach(form -> {
                ThirdPartyPositionForSynchronization p = PositionChangeUtil.changeToThirdPartyPosition(form, position);
                positions.add(p);
            });
        }
        return positions;
    }

    /**
     * 该职位是否可以刷新
     *
     * @param positionId 职位编号
     * @param channel    渠道编号
     * @return
     */
    public boolean ifAllowRefresh(int positionId, int channel) {
        boolean permission = false;
        try {
            logger.info("ifAllowRefresh");
            QueryUtil findPositionById = new QueryUtil();
            findPositionById.addEqualFilter("id", String.valueOf(positionId));
            logger.info("search position");
            Position position = positionDaoService.getPosition(findPositionById);
            logger.info("position:" + JSON.toJSONString(position));
            if (position.getId() > 0) {
                QueryUtil findThirdPartyAccount = new QueryUtil();
                findThirdPartyAccount.addEqualFilter("company_id", String.valueOf(position.getCompany_id()));
                findThirdPartyAccount.addEqualFilter("channel", String.valueOf(channel));

                logger.info("search company");
                ThirdPartAccountData account = CompanyDao.getThirdPartyAccount(findThirdPartyAccount);
                logger.info("company:" + JSON.toJSONString(account));

                QueryUtil findThirdPartyPosition = new QueryUtil();
                findThirdPartyPosition.addEqualFilter("position_id", String.valueOf(positionId));
                findThirdPartyPosition.addEqualFilter("channel", String.valueOf(channel));

                logger.info("search thirdparyposition");
                ThirdPartyPositionData p = positionDaoService.getThirdPartyPosition(positionId, channel);
                logger.info("thirdparyposition" + JSON.toJSONString(p));
                if (account != null && account.getBinding() == AccountSync.bound.getValue() && p.getId() > 0
                        && p.getIs_synchronization() == PositionSync.bound.getValue()) {
                    logger.info("data allow");
                    String str = RedisClientFactory.getCacheClient().get(AppId.APPID_ALPHADOG.getValue(),
                            KeyIdentifier.THIRD_PARTY_POSITION_REFRESH.toString(), String.valueOf(positionId), String.valueOf(channel));
                    if (StringUtils.isNullOrEmpty(str)) {
                        logger.info("cache allow");
                        permission = true;
                    }
                }
            }
        } catch (TException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            // do nothing
        }
        return permission;
    }

    /**
     * 创建刷新职位数据
     *
     * @param positionId 职位编号
     * @param channel    渠道编号
     * @return
     */
    public ThirdPartyPositionForSynchronizationWithAccount createRefreshPosition(int positionId, int channel) {

        ThirdPartyPositionForSynchronizationWithAccount account = new ThirdPartyPositionForSynchronizationWithAccount();
        try {
            ThirdPartyPosition form = new ThirdPartyPosition();
            QueryUtil findPosition = new QueryUtil();
            findPosition.addEqualFilter("id", String.valueOf(positionId));
            Position position = positionDaoService.getPosition(findPosition);

            ThirdPartyPositionData thirdPartyPosition = positionDaoService.getThirdPartyPosition(positionId, channel);

            QueryUtil findAccount = new QueryUtil();
            findAccount.addEqualFilter("company_id", String.valueOf(position.getCompany_id()));
            findAccount.addEqualFilter("channel", String.valueOf(channel));
            ThirdPartAccountData accountData = CompanyDao.getThirdPartyAccount(findAccount);
            account.setUser_name(accountData.getUsername());
            account.setMember_name(accountData.getMembername());
            account.setPassword(accountData.getPassword());
            account.setChannel(String.valueOf(channel));
            account.setPosition_id(String.valueOf(positionId));

            form.setChannel((byte) channel);
            if (position.getId() > 0 && thirdPartyPosition.getId() > 0) {
                ThirdPartyPositionForSynchronization p = PositionChangeUtil.changeToThirdPartyPosition(form, position);
                p.setJob_id(thirdPartyPosition.getThird_part_position_id());
                account.setPosition_info(p);
            }
        } catch (TException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            //do nothing
        }
        return account;
    }

    /**
     * 批量处理修改职位
     *
     * @param batchHandlerJobPostion
     * @return
     */
    public Response batchHandlerJobPostion(BatchHandlerJobPostion batchHandlerJobPostion) {
        logger.info("开始批量修改职位");
        try {
            // 提交的数据
            List<JobPostrionObj> jobPostrionHandlerDates = batchHandlerJobPostion.getData();
            // 如果为true, 数据不能删除. 否则,允许删除, data中的数据根据fields_nohash中以外的字段, 判断data中的记录和数据库中已有记录的关系, 进行添加, 修改,删除
            Boolean noDelete = batchHandlerJobPostion.nodelete;
            // 参数有误
            if (null == noDelete) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, ConstantErrorCodeMessage.POSITION_NODELETE_BLANK);
            }
            // 提交的数据为空
            if (com.moseeker.common.util.StringUtils.isEmptyObject(batchHandlerJobPostion.getData()) || batchHandlerJobPostion.getData().size() == 0) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, ConstantErrorCodeMessage.POSITION_DATA_BLANK);
            }
            Integer companyId;
            if (jobPostrionHandlerDates.get(0).getId() != 0) {
                QueryUtil queryUtil = new QueryUtil();
                queryUtil.addEqualFilter("id", String.valueOf(jobPostrionHandlerDates.get(0).getId()));
                JobPositionRecord jobPostionTemp = jobPositionDao.getResource(queryUtil);
                companyId = jobPostionTemp.getCompanyId().intValue();
            } else {
                // 将该公司下的所有职位查询出来
                companyId = jobPostrionHandlerDates.get(0).getCompany_id();
            }
            CommonQuery commonQuery = new CommonQuery();
            HashMap hashMap = new HashMap();
            hashMap.put("company_id", String.valueOf(companyId));
            // 默认会取10条数据
            commonQuery.setPer_page(10000);
            commonQuery.setEqualFilter(hashMap);
            // 数据库中该公司的职位列表
            List<JobPositionRecord> dbList = jobPositionDao.getResources(commonQuery);
            HashMap dbListMap = new HashMap();
            for (JobPositionRecord jobPositionRecord : dbList) {
                dbListMap.put(jobPositionRecord.getId(), jobPositionRecord);
            }
            // 删除操作,删除除了data以外的数据库中的数据
            if (!noDelete) {
                logger.info("---职位进行删除---");
                // 不需要删除的数据
                List<JobPositionRecord> noDeleJobPostionRecords = new ArrayList<>();
                // 提交的数据处理
                for (JobPositionRecord jobPositionRecord : dbList) {
                    for (JobPostrionObj jobPostrionHandlerDate : jobPostrionHandlerDates) {
                        // 当ID相同，数据不需要删除
                        if (jobPositionRecord.getId().intValue() == jobPostrionHandlerDate.getId()) {
                            noDeleJobPostionRecords.add(jobPositionRecord);
                            break;
                        }
                        // 当 source = 9 ，source_id ,company_id, jobnumber 相等时候，不需要删除
                        if (jobPositionRecord.getSource().equals(9) && jobPositionRecord.getSourceId().equals(jobPostrionHandlerDate.getSource_id())
                                && jobPositionRecord.getCompanyId().equals(jobPostrionHandlerDate.getCompany_id())
                                && jobPositionRecord.getJobnumber().equals(jobPostrionHandlerDate.getJobnumber())) {
                            noDeleJobPostionRecords.add(jobPositionRecord);
                            break;
                        }
                        jobPositionRecord.setStatus((byte) 1);
                    }
                }
                // 将总数据和不需要删除的数据取差集
                boolean remove = dbList.removeAll(noDeleJobPostionRecords);
                if (remove) {
                    if (dbList != null && dbList.size() > 0) {
                        jobPositionDao.putResources(dbList);
                        // 更新
                        UpdataESThread updataESThread = new UpdataESThread(searchengineServices, dbList);
                        Thread thread = new Thread(updataESThread);
                        thread.start();
                        return ResponseUtils.success(0);
                    } else {
                        return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_DATA_DELETE_FAIL);
                    }
                }
                // 新增数据或者更新数据
            } else {
                // 判断哪些数据不需要更新的
                String fieldsNooverwrite = batchHandlerJobPostion.getFields_nooverwrite();
                String[] fieldsNooverwriteStrings = null;
                if (!StringUtils.isNullOrEmpty(fieldsNooverwrite)) {
                    fieldsNooverwriteStrings = fieldsNooverwrite.split(",");
                }
                // 判断数据是否需要更新
                String fieldsNohash = batchHandlerJobPostion.getFields_nohash();
                String[] fieldsNohashs = null;
                if (!StringUtils.isNullOrEmpty(fieldsNohash)) {
                    fieldsNohashs = fieldsNohash.split(",");
                }
                // 需要更新JobPostion的数据
                List<JobPositionRecord> jobPositionUpdateRecordList = new ArrayList<>();
                // 需要新增JobPostion的数据
                List<JobPositionRecord> jobPositionAddRecordList = new ArrayList<>();
                //  需要更新的JobPositionExtRecord数据
                List<JobPositionExtRecord> JobPositionExtRecordUpdateRecords = new ArrayList<>();
                //  需要新增的JobPositionExtRecord数据
                List<JobPositionExtRecord> JobPositionExtRecordAddRecords = new ArrayList<>();
                // 处理数据
                for (JobPostrionObj jobPostrionHandlerDate : jobPostrionHandlerDates) {
                    // 按company_id + .source_id + .jobnumber + source=9取得数据
                    QueryUtil queryUtil = new QueryUtil();
                    queryUtil.addEqualFilter("company_id", String.valueOf(jobPostrionHandlerDate.getCompany_id()));
                    queryUtil.addEqualFilter("source", "9");
                    queryUtil.addEqualFilter("source_id", String.valueOf(jobPostrionHandlerDate.getSource_id()));
                    queryUtil.addEqualFilter("stauts", "0");
                    queryUtil.addEqualFilter("jobnumber", jobPostrionHandlerDate.getJobnumber());
                    JobPositionRecord jobPositionRecord = jobPositionDao.getResource(queryUtil);
                    // 更新或者新增数据
                    if (jobPostrionHandlerDate.getId() != 0 || !com.moseeker.common.util.StringUtils.isEmptyObject(jobPositionRecord)) {  // 数据更新
                        // 按company_id + .source_id + .jobnumber + source=9取得数据为空时，按Id进行更新
                        JobPositionRecord record = (JobPositionRecord) BeanUtils.structToDB(jobPostrionHandlerDate, JobPositionRecord.class);
                        // 判断该条数据是否需要更新
                        if (!com.moseeker.common.util.StringUtils.isEmptyObject(jobPositionRecord)) {
                            record.setId(jobPositionRecord.getId());
                        }
                        // 取出数据库中的数据进行对比操作
                        JobPositionRecord jobPositionRecordTemp = (JobPositionRecord) hashMap.get(record.getId());
                        if (jobPositionRecordTemp != null) {
                            QueryUtil query = new QueryUtil();
                            query.addEqualFilter("pid", String.valueOf(jobPositionRecordTemp.getId()));
                            JobPositionExtRecord jobPositionExtRecord = jobPositonExtDao.getResource(query);
                            if (!md5(fieldsNohashs, jobPositionRecordTemp, jobPositionExtRecord.getExtra()).equals(md5(fieldsNohashs, record, jobPostrionHandlerDate.getExtra()))) {
                                // 设置不需要更新的字段
                                if (fieldsNooverwriteStrings != null && fieldsNooverwriteStrings.length > 0) {
                                    for (Field field : record.fields()) {
                                        for (String fieldNo : fieldsNooverwriteStrings) {
                                            if (field.getName().equals(fieldNo)) {
                                                record.set(field, null);
                                            }
                                        }
                                    }
                                }
                                // 换算薪资范围
                                if (record.getSalaryBottom().intValue() == 0 && record.getSalaryTop().intValue() == 0) {
                                    record.setSalary("面议");
                                }
                                if (record.getSalaryBottom().intValue() > 0 && record.getSalaryTop().intValue() == 999) {
                                    record.setSalary(record.getSalaryBottom().intValue() + "K以上");
                                }
                                // 将需要更新的数据放入更新的列表
                                jobPositionUpdateRecordList.add(record);

                            }
                        }
                    } else { // 数据的新增
                        JobPositionRecord record = (JobPositionRecord) BeanUtils.structToDB(jobPostrionHandlerDate, JobPositionRecord.class);
                        // 换算薪资范围
                        if (record.getSalaryBottom().intValue() == 0 && record.getSalaryTop().intValue() == 0) {
                            record.setSalary("面议");
                        }
                        if (record.getSalaryBottom().intValue() > 0 && record.getSalaryTop().intValue() == 999) {
                            record.setSalary(record.getSalaryBottom().intValue() + "K以上");
                        }
                        jobPositionAddRecordList.add(record);
                        // 新增jobPostion_ext数据
                        JobPositionExtRecord jobPositionExtRecord = new JobPositionExtRecord();
                        jobPositionExtRecord.setExtra(jobPostrionHandlerDate.getExtra());
                        JobPositionExtRecordAddRecords.add(jobPositionExtRecord);
                    }
                }
                // 更新jobPostion数据
                jobPositionDao.putResources(jobPositionUpdateRecordList);
                // 新增jobPostion数据
                jobPositionDao.postResources(jobPositionAddRecordList);
                // 更新jobPostionExt数据
                jobPositonExtDao.putResources(JobPositionExtRecordUpdateRecords);
                // 新增jobPostionExt数据
                jobPositonExtDao.postResources(JobPositionExtRecordAddRecords);
            }
            // 取最新的数据用于更新ES Search Engine
            dbList = jobPositionDao.getResources(commonQuery);
            // 更新ES Search Engine
            UpdataESThread updataESThread = new UpdataESThread(searchengineServices, dbList);
            Thread thread = new Thread(updataESThread);
            thread.start();
            return ResponseUtils.success(0);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    /**
     * 删除职位
     *
     * @param id
     * @param companyId
     * @param jobnumber
     * @param sourceId
     * @return
     */
    public Response deleteJobposition(Integer id, Integer companyId, String jobnumber, Integer sourceId) {
        try {
            List<JobPositionRecord> jobPositionRecords = new ArrayList<>();
            JobPositionRecord jobPositionRecord = null;
            if (id != null) {
                QueryUtil queryUtil = new QueryUtil();
                queryUtil.addEqualFilter("id", String.valueOf(id));
                jobPositionRecord = jobPositionDao.getResource(queryUtil);

            } else if (companyId != null && jobnumber != null && sourceId != null) {
                QueryUtil queryUtil = new QueryUtil();
                queryUtil.addEqualFilter("company_id", String.valueOf(companyId));
                queryUtil.addEqualFilter("source", "9");
                queryUtil.addEqualFilter("source_id", String.valueOf(sourceId));
                queryUtil.addEqualFilter("stauts", "0");
                queryUtil.addEqualFilter("jobnumber", jobnumber);
                jobPositionRecord = jobPositionDao.getResource(queryUtil);
            }
            jobPositionRecord.setStatus((byte) 1);
            // 删除JobPostion
            jobPositionDao.postResource(jobPositionRecord);
            // 删除job_position_city
            JobPositionCityRecord jobPositionCityRecord = new JobPositionCityRecord();
            jobPositionCityRecord.setPid(id);



            // 更新ES Search Engine
            jobPositionRecords.add(jobPositionRecord);
            UpdataESThread updataESThread = new UpdataESThread(searchengineServices, jobPositionRecords);
            Thread thread = new Thread(updataESThread);
            thread.start();
            return ResponseUtils.success(0);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }


    /**
     * 对JobPositionRecord 进行除了nohash字段之外的值进行MD5进行计算
     *
     * @param nohashs
     * @param jobPositionRecord
     * @return
     */
    public String md5(String[] nohashs, JobPositionRecord jobPositionRecord, String extra) {
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
                if (!com.moseeker.common.util.StringUtils.isEmptyObject(str)) {
                    stringBuffer.append(jobPositionRecord.get(field.getName()));
                }
            }
            String str = (String) hashMap.get("extra");
            if (!com.moseeker.common.util.StringUtils.isEmptyObject(str)) {
                stringBuffer.append(extra);
            }
            md5 = MD5Util.md5(stringBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }


    public JobOccupationDao getJobOccupationDao() {
        return jobOccupationDao;
    }

    public void setJobOccupationDao(JobOccupationDao jobOccupationDao) {
        this.jobOccupationDao = jobOccupationDao;
    }

    public List<ThirdPartyPositionData> getThirdPartyPositions(CommonQuery query) {
        List<ThirdPartyPositionData> datas = new ArrayList<>();
        try {
//			if(query.getEqualFilter() != null) {
//				query.getEqualFilter().put("channel", "1");
//			} else {
//				Map<String, String> equalFilter = new HashMap<>();
//				equalFilter.put("channel", "1");
//				query.setEqualFilter(equalFilter);
//			}
            datas = positionDaoService.getPositionThirdPartyPositions(query);
        } catch (TException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            //do nothing
        }
        return datas;
    }


}