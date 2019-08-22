package com.moseeker.position.service.position.base.sync;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.baseorm.dao.dictdb.DictCityMapDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyFeature;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.position.service.fundationbs.PositionQxService;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 第三方职位同步抽象类
 * @param <Form>    前台或者ats端同步请求的表单数据
 * @param <R>       推送给第三方的职位结构包含账号信息和职位信息
 * @param <Info>    R中的职位信息（其实可以省略掉）
 * @param <ExtP>    持久化到数据库中的第三方职位的额外表对象，
 *                  例如Jobsdb:HrThirdPartyPositionDO + ThirdpartyJobsDBPositionDO是一个完整的第三方职位
 *                  ThirdpartyJobsDBPositionDO就是额外表对象。
 *                  如果HrThirdPartyPositionDO中的字段可以满足需要持久化的数据，定义{@link EmptyExtThirdPartyPosition}即可
 *
 * @author pyb
 */
public abstract class AbstractPositionTransfer<Form, R, Info, ExtP> {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    protected FastDateFormat sdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    ChaosServices.Iface chaosService = ServiceManager.SERVICE_MANAGER.getService(ChaosServices.Iface.class);

    @Autowired
    private JobPositionCityDao jobPositionCityDao;

    @Autowired
    protected DictCityMapDao cityMapDao;

    @Autowired
    private HrCompanyAccountDao hrCompanyAccountDao;

    @Autowired
    private UserHrAccountDao userHrAccountDao;

    @Autowired
    private PositionQxService positionQxService;

    @Autowired
    private HRThirdPartyPositionDao thirdPartyPositionDao;

    /**
     * 将仟寻职位转成第卅方职位
     *
     * @param jsonForm
     * @param positionDB
     * @param account
     * @return
     */
    public TransferResult changeToThirdPartyPosition(JSONObject jsonForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) throws Exception {
        logger.info("change To ThirdPartyPosition start : jsonForm : {},  positionDB : {}, account : {}", jsonForm, positionDB, account);
        if (jsonForm == null || positionDB == null || account == null) {
            logger.error("change To ThirdPartyPosition param empty");
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, "change To ThirdPartyPosition param error");
        }
        TransferResult result = new TransferResult();

        Form positionForm = parseJsonForm(jsonForm);


        R positionWithAccount = changeToThirdPartyPosition(positionForm, positionDB, account);
        logger.info("change To positionWithAccount {}", JSON.toJSONString(positionWithAccount));

        HrThirdPartyPositionDO hrThirdPartyPositionDO = toThirdPartyPosition(positionForm, positionWithAccount);
        logger.info("change To hrThirdPartyPositionDO {}", JSON.toJSONString(hrThirdPartyPositionDO));

        ExtP extPosition = toExtThirdPartyPosition(positionForm, positionWithAccount);
        logger.info("change To extPosition {}", JSON.toJSONString(extPosition));

        result.setPositionWithAccount(positionWithAccount);
        result.setThirdPartyPositionDO(hrThirdPartyPositionDO);
        result.setExtPosition(extPosition);


        return result;
    }

    /**
     * 根据json字符串转换成表单
     *
     * @param json
     * @return
     * @throws BIZException
     */
    public Form parseJsonForm(JSONObject json) throws BIZException {
        logger.info("parse json form json : {}", json);
        try {
            Form form = JSONObject.parseObject(json.toJSONString(), getFormClass());
            logger.info("parse json form form : {}", JSON.toJSONString(form));
            return form;
        } catch (Exception e) {
            logger.info("parse json form error : {}", json);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, "parse json form error");
        }
    }

    public List<String> toChaosJson(R r) {
        return Arrays.asList(JSON.toJSONString(r));
    }

    /**
     * 额外判断是否使用当前转换策略
     * @param positionDB
     * @return
     */
    public boolean extTransferCheck (JobPositionDO positionDB){
        return true;
    }

    /**
     * 发送请求，并处理结果，默认是发送给爬虫端
     * @param result
     */
    public TwoParam<HrThirdPartyPositionDO,ExtP> sendSyncRequest(TransferResult<R,ExtP> result) throws TException {
        String syncData = JSON.toJSONString(result.getPositionWithAccount());
        // 提交到chaos处理
        logger.info("chaosService.synchronizePosition:{}", syncData);
        chaosService.synchronizePosition(toChaosJson(result.getPositionWithAccount()));

        // 回写数据到第三方职位表表
        TwoParam<HrThirdPartyPositionDO,ExtP> fullThirdPartyPosition = new TwoParam<>(result.getThirdPartyPositionDO(),result.getExtPosition());
        logger.info("write back to thirdpartyposition:{}", JSON.toJSONString(fullThirdPartyPosition));
        return thirdPartyPositionDao.upsertThirdPartyPosition(fullThirdPartyPosition);

    }

    /**
     * ========================抽象方法，让每个渠道去实现自己的逻辑========================
     */
    public abstract R changeToThirdPartyPosition(Form positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) throws Exception;

    //生成并且初始化需要同步的账号信息
    protected abstract R createAndInitAccountInfo(Form positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account);

    //生成并且初始化需要同步的职位信息
    protected abstract Info createAndInitPositionInfo(Form positionForm, JobPositionDO positionDB) throws Exception;

    //获取渠道类型
    public abstract ChannelType getChannel();

    //获取前台表单对应类型class
    public abstract Class<Form> getFormClass();

    //转换成额外的第三方职位
    public abstract HrThirdPartyPositionDO toThirdPartyPosition(Form form, R pwa);

    public abstract ExtP toExtThirdPartyPosition(Form form, R r);

    public abstract ExtP toExtThirdPartyPosition(Map<String, String> data);

    public abstract JSONObject toThirdPartyPositionForm(HrThirdPartyPositionDO thirdPartyPosition, ExtP extPosition);


    /**
     * ========================每个渠道共用的逻辑，当然也可以覆盖实现自己的逻辑========================
     */
    protected static int getSalaryBottom(int salaryBottom) {
        if (salaryBottom > 0) {
            return salaryBottom * 1000;
        }
        return 0;
    }

    protected static int getSalaryTop(int salary_top) {
        if (salary_top > 0) {
            return salary_top * 1000;
        }
        return 0;
    }

    protected static int getQuantity(int count, int countFromDB) {
        if (count > 0) {
            return count;
        } else {
            return countFromDB;
        }
    }

    /**
     * 设置职位福利特色
     *
     * @param positionDB 仟寻职位
     */
    protected List<String> getFeature(JobPositionDO positionDB) {
        List<HrCompanyFeature> features = positionQxService.getPositionFeature(positionDB.getId());
        if (StringUtils.isEmptyList(features)) {
            //爬虫需要即使数据库这个字段为空，也需要要一个空列表
            return new ArrayList<>();
        } else {
            return features.stream().map(f -> f.getFeature()).collect(Collectors.toList());
        }
    }

    /**
     * 合并职位责任和职位要求
     *
     * @param accounTabilities 职位责任
     * @param requirement      职位要求
     * @return
     */
    protected String getDescription(String accounTabilities, String requirement) {
        StringBuffer descript = new StringBuffer();
        if (StringUtils.isNotNullOrEmpty(accounTabilities)) {
            descript.append(accounTabilities);
        }
        if (StringUtils.isNotNullOrEmpty(requirement)) {
            if (!requirement.contains("职位要求")) {
                descript.append("\n职位要求：\n" + requirement);
            } else {
                descript.append(requirement);
            }
        }
        return descript.toString();
    }

    /**
     * 获取仟寻职位的发布城市对应的完整城市链
     *
     * @param positionDB
     * @return
     */
    public List<List<String>> getCities(JobPositionDO positionDB) {
        if (positionDB == null || positionDB.getId() == 0) {
            return Collections.emptyList();
        }

        //获取职位对应的moseeker城市code
        Query query = new Query.QueryBuilder().where("pid", positionDB.getId()).buildQuery();
        List<JobPositionCityDO> positionCitys = jobPositionCityDao.getDatas(query);

        Set<Integer> positionCityCodes = new HashSet<>();

        if (positionCitys == null || positionCitys.size() == 0) {
            //设置为全国
            positionCityCodes.add(111111);
        } else {
            for (JobPositionCityDO cityDO : positionCitys) {
                positionCityCodes.add(cityDO.getCode());
            }
        }

        List<List<String>> otherCityCodes = cityMapDao.getOtherCityByLastCodes(getChannel(), new ArrayList<>(positionCityCodes));
        logger.info("setCities:otherCityCodes:{}", otherCityCodes);
        return otherCityCodes;
    }

    //初始化公司
    public String getCompanyName(int publisher) {
        HrCompanyDO subCompany = hrCompanyAccountDao.getHrCompany(publisher);//获取发布者对应的公司，只返回一个
        if (subCompany != null) {
            logger.info("初始化公司名称:{}", subCompany);
            return subCompany.getAbbreviation();
        } else {
            return "";
        }
    }

    /**
     * 根据pattern格式补全
     *
     * @param list
     * @param pattern 例如："00000"表示用0补全到5位
     * @return
     */
    public List<List<String>> formateList(List<List<String>> list, String pattern) {
        if (list == null || list.isEmpty()) {
            return list;
        }
        List<List<String>> result = new ArrayList<>();
        for (List<String> o : list) {
            result.add(formateStr(o, pattern));
        }
        return result;
    }

    public List<String> formateStr(List<String> list, String pattern) {
        if (list == null || list.isEmpty()) {
            return list;
        }
        DecimalFormat df = new DecimalFormat(pattern);
        List<String> result = new ArrayList<>();
        for (String s : list) {
            try {
                result.add(df.format(Integer.valueOf(s)));
            } catch (NumberFormatException e) {
                continue;
            }
        }
        return result;
    }

    /**
     * 把moseeker职位的经验要求转换成int
     *
     * @param e
     * @return
     * @throws BIZException
     */
    public int experienceToInt(String e) throws BIZException {
        if (StringUtils.isNullOrEmpty(e)) {
            return 0;
        }
        //工作经验要求
        Integer experience = null;
        try {
            if (StringUtils.isNotNullOrEmpty(e)) {
                experience = Integer.valueOf(e.trim());
            }
        } catch (NumberFormatException exp) {
            logger.info("transfer experience To Int error ：" + e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, "transfer experience To Int error ：" + e);
        }

        return experience;
    }

    protected UserHrAccountDO getPublisherAccountInfo(JobPositionDO position) {
        if (position == null) {
            return null;
        }
        return userHrAccountDao.getValidAccount(position.getPublisher());
    }

    protected String getEmail(JobPositionDO positionDB) throws Exception {
        if (positionDB == null || positionDB.getId() == 0) {
            return "";
        }
        String email = getConfigString("chaos.email");
        return "cv_" + positionDB.getId() + email;
    }

    protected String getConfigString(String key) throws Exception {
        ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
        configUtils.loadResource("setting.properties");
        return configUtils.get(key, String.class);
    }

    public static class TransferResult<R, ExtP> {
        private R positionWithAccount;
        private ExtP extPosition;
        private HrThirdPartyPositionDO thirdPartyPositionDO;

        public ExtP getExtPosition() {
            return extPosition;
        }

        public void setExtPosition(ExtP extPosition) {
            this.extPosition = extPosition;
        }

        public HrThirdPartyPositionDO getThirdPartyPositionDO() {
            return thirdPartyPositionDO;
        }

        public void setThirdPartyPositionDO(HrThirdPartyPositionDO thirdPartyPositionDO) {
            this.thirdPartyPositionDO = thirdPartyPositionDO;
        }

        public R getPositionWithAccount() {
            return positionWithAccount;
        }

        public void setPositionWithAccount(R positionWithAccount) {
            this.positionWithAccount = positionWithAccount;
        }
    }
}