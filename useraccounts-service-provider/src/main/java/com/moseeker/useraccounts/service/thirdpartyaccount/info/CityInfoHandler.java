package com.moseeker.useraccounts.service.thirdpartyaccount.info;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCityDao;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.pojos.ThirdPartyInfoData;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCityDO;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.AbstractInfoHandler;
import org.jooq.UpdatableRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CityInfoHandler extends AbstractInfoHandler<ThirdpartyAccountCityDO>{
    Logger logger= LoggerFactory.getLogger(CityInfoHandler.class);

    @Autowired
    ThirdpartyAccountCityDao accountCityDao;

    @Autowired
    DictCityDao cityDao;

    @Override
    public List<ThirdpartyAccountCityDO> buildNewData(ThirdPartyInfoData data) {
        if (StringUtils.isEmptyList(data.getCities())) {
            return Collections.emptyList();
        }

        List<DictCityDO> cityDOList = cityDao.getFullCity();
        String currentTime = getCurrentTime();

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

        return thirdpartyAccountCityDOList;
    }

    @Override
    public boolean equals(ThirdpartyAccountCityDO data1, ThirdpartyAccountCityDO data2) {
        return data1.getJobtype() == data2.getJobtype()
                && data1.getCode() == data2.getCode();
    }

    @Override
    protected ThirdpartyAccountCityDao getDao() {
        return accountCityDao;
    }

    private static final HashMap<String,Integer> specialCity=new HashMap<>();

    static {
        specialCity.put("基层岗位",111111);
    }
}
