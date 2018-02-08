package com.moseeker.entity;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.dictdb.DictCityMapDao;
import com.moseeker.baseorm.dao.thirdpartydb.*;
import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.DictCityMap;
import com.moseeker.baseorm.db.thirdpartydb.tables.*;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.pojos.Data;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityMapDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.*;
import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 第三方账号实体
 * Created by jack on 27/09/2017.
 */
@Component
public class ThridPartyAcountEntity {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ThirdpartyAccountCompanyDao accountCompanyDao;

    @Autowired
    ThirdpartyAccountDepartmentDao departmentDao;

    @Autowired
    ThirdpartyAccountCityDao accountCityDao;

    @Autowired
    ThirdpartyAccountCompanyAddressDao accountCompanyAddressDao;

    @Autowired
    ThirdPartyAccountJob1001SubsiteDao subsiteDao;

    @Autowired
    DictCityMapDao cityMapDao;

    @Autowired
    DictCityDao cityDao;

    /**
     * 添加第三方渠道账号的扩展信息
     * @param data
     * @param accountDO
     * @throws CommonException
     */
    @Transactional
    public void saveAccountExt(Data data, HrThirdPartyAccountDO accountDO) throws CommonException {


        logger.info("saveAccountExt data:{}", JSON.toJSONString(data));
        Query query = new Query.QueryBuilder().where(DictCityMap.DICT_CITY_MAP.CHANNEL.getName(), accountDO.getChannel()).buildQuery();
        List<DictCityMapDO> cityMapDOList = cityMapDao.getDatas(query);
        List<Integer> codeList = cityMapDOList.stream().map(cityMapDO -> cityMapDO.getCode()).collect(Collectors.toList());
        Condition condition = new Condition(DictCity.DICT_CITY.CODE.getName(), codeList, ValueOp.IN);
        Query findCityList = new Query.QueryBuilder().where(condition).buildQuery();
        List<DictCityDO> cityDOList = cityDao.getFullCity();

        //初始化创建时间和更新时间
        String currentTime=FastDateFormat.getDateInstance(FastDateFormat.LONG, Locale.CHINA).format(new Date());

        if (data.getCities() != null && data.getCities().size() > 0) {
            Condition deleteCondition = new Condition(ThirdpartyAccountCity.THIRDPARTY_ACCOUNT_CITY.ACCOUNT_ID.getName(), data.getAccountId());
            accountCityDao.delete(deleteCondition);

            List<ThirdpartyAccountCityDO> thirdpartyAccountCityDOList = data.getCities()
                    .stream()
                    .map(city -> {
                        ThirdpartyAccountCityDO thirdpartyAccountCityDO = new ThirdpartyAccountCityDO();
                        thirdpartyAccountCityDO.setAccountId(data.getAccountId());
                        String area;
                        if (StringUtils.lastContain(city.getArea(), "市")) {
                            area = city.getArea().substring(0, city.getArea().length()-1);
                        } else {
                            area = city.getArea();
                        }
                        Optional<DictCityDO> cityDOOptional = cityDOList
                                .stream()
                                .filter(cityDO -> cityDO.getName().equals(area))
                                .findAny();
                        if (cityDOOptional.isPresent()) {
                            thirdpartyAccountCityDO.setCode(cityDOOptional.get().getCode());
                        } else if(specialCity.containsKey(area)) {
                            thirdpartyAccountCityDO.setCode(specialCity.get(area));
                        } else{
                            thirdpartyAccountCityDO.setCode(0);
                        }
                        thirdpartyAccountCityDO.setRemainNum(city.getAmount());
                        thirdpartyAccountCityDO.setJobtype((byte) city.getJobTypeInt());
                        thirdpartyAccountCityDO.setAccountId(data.getAccountId());
                        thirdpartyAccountCityDO.setCreateTime(currentTime);
                        thirdpartyAccountCityDO.setUpdateTime(currentTime);
                        logger.info("saveAccountExt area:{}, amount:{}, jobType:{}, accountId:{}", area, city.getAmount(), city.getJobType(), data.getAccountId());
                        return thirdpartyAccountCityDO;

            }).collect(Collectors.toList());
            logger.info("saveAccountExt collectors.size:{}", thirdpartyAccountCityDOList.size());
            logger.info("thirdpartyAccountCityDOList : {}",thirdpartyAccountCityDOList);
            if (thirdpartyAccountCityDOList != null && thirdpartyAccountCityDOList.size() > 0) {
                accountCityDao.addAllData(thirdpartyAccountCityDOList);
            }
        }

        if (data.getAddresses() != null && data.getAddresses().size() > 0) {
            Condition deleteCondition = new Condition(ThirdpartyAccountCompanyAddress.THIRDPARTY_ACCOUNT_COMPANY_ADDRESS.ACCOUNT_ID.getName(), data.getAccountId());
            accountCompanyAddressDao.delete(deleteCondition);

            List<ThirdpartyAccountCompanyAddressDO> addressDOList = data.getAddresses()
                    .stream()
                    .map(address -> {
                        ThirdpartyAccountCompanyAddressDO addressDO = new ThirdpartyAccountCompanyAddressDO();
                        addressDO.setAccountId(data.getAccountId());
                        addressDO.setAddress(address.getAddress());
                        addressDO.setCity(address.getCity());
                        addressDO.setCreateTime(currentTime);
                        addressDO.setUpdateTime(currentTime);
                        return addressDO;
                    }).collect(Collectors.toList());
            logger.info("addressDOList : {}",addressDOList);
            if (addressDOList != null && addressDOList.size() > 0) {
                accountCompanyAddressDao.addAllData(addressDOList);
            }
        }

        if (data.getCompanies() != null && data.getCompanies().size() > 0) {
            Condition deleteCondition = new Condition(ThirdpartyAccountCompany.THIRDPARTY_ACCOUNT_COMPANY.ACCOUNT_ID.getName(), data.getAccountId());
            accountCompanyDao.delete(deleteCondition);

            List<ThirdpartyAccountCompanyDO> companyDOList = data.getCompanies()
                    .stream()
                    .map(company -> {
                        ThirdpartyAccountCompanyDO companyDO = new ThirdpartyAccountCompanyDO();
                        companyDO.setAccountId(data.getAccountId());
                        companyDO.setCompanyName(company);
                        companyDO.setCreateTime(currentTime);
                        companyDO.setUpdateTime(currentTime);
                        return companyDO;
                    }).collect(Collectors.toList());
            logger.info("companyDOList: {}",companyDOList);
            if (companyDOList != null && companyDOList.size() > 0) {
                accountCompanyDao.addAllData(companyDOList);
            }
        }

        if (data.getDepartments() != null && data.getDepartments().size() > 0) {
            Condition deleteCondition = new Condition(ThirdpartyAccountDepartment.THIRDPARTY_ACCOUNT_DEPARTMENT.ACCOUNT_ID.getName(), data.getAccountId());
            departmentDao.delete(deleteCondition);

            List<ThirdpartyAccountDepartmentDO> departmentDOList = data.getDepartments()
                    .stream()
                    .map(department -> {
                        ThirdpartyAccountDepartmentDO departmentDO = new ThirdpartyAccountDepartmentDO();
                        departmentDO.setAccountId(data.getAccountId());
                        departmentDO.setDepartmentName(department);
                        departmentDO.setCreateTime(currentTime);
                        departmentDO.setUpdateTime(currentTime);
                        return departmentDO;
                    }).collect(Collectors.toList());
            logger.info("departmentDOList : {}",departmentDOList);
            if (departmentDOList != null && departmentDOList.size() > 0) {
                departmentDao.addAllData(departmentDOList);
            }
        }

        if(data.getSubsites() != null && !data.getSubsites().isEmpty()){
            Condition deleteCondition = new Condition(ThirdpartyAccountJob1001Subsite.THIRDPARTY_ACCOUNT_JOB1001_SUBSITE.ACCOUNT_ID.getName(), data.getAccountId());
            subsiteDao.delete(deleteCondition);

            List<ThirdpartyAccountJob1001SubsiteDO> subsiteDOList= data.getSubsites()
                    .stream()
                    .map(subsite -> {
                        ThirdpartyAccountJob1001SubsiteDO subsiteDO=new ThirdpartyAccountJob1001SubsiteDO();
                        subsiteDO.setAccountId(data.getAccountId());
                        subsiteDO.setSite(subsite);
                        subsiteDO.setText(subsite);
                        subsiteDO.setCreateTime(currentTime);
                        subsiteDO.setUpdateTime(currentTime);
                        return subsiteDO;
                    }).collect(Collectors.toList());
            logger.info("subsiteDOList : {}",subsiteDOList);
            if(subsiteDOList != null && !subsiteDOList.isEmpty()){
                subsiteDao.addAllData(subsiteDOList);
            }
        }
    }

    private static final HashMap<String,Integer> specialCity=new HashMap<>();

    static {
        specialCity.put("基层岗位",111111);
    }
}
