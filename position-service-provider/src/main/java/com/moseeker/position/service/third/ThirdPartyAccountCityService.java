package com.moseeker.position.service.third;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCityDao;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCityDO;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoCity;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThirdPartyAccountCityService {
    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    ThirdpartyAccountCityDao cityDao;
    @Autowired
    DictCityDao dictCityDao;

    /**
     * 把ThirdpartyAccountCityDO转换成传给前台的类型ThirdPartyAccountInfoCity
     * @param accountId 第三方账号ID
     * @return ThirdPartyAccountInfoCity列表
     * @throws TException
     */
    public List<ThirdPartyAccountInfoCity> getCityByAccountId(int accountId) throws TException{
        List<ThirdpartyAccountCityDO> cityList=cityDao.getCityByAccountId(accountId);

        //取出第三方账号对应城市的所有code
        List<Integer> codes=new ArrayList<>();
        cityList.forEach(c->codes.add(c.getCode()));

        logger.info(accountId+"对应的城市code"+ JSON.toJSONString(codes));

        //根据codes获取城市信息(名称)
        List<DictCityRecord> dictCityList=dictCityDao.getCitiesByCodes(codes);

        List<ThirdPartyAccountInfoCity> infoCity=new ArrayList<>();
        cityList.forEach(c->{
            ThirdPartyAccountInfoCity city=new ThirdPartyAccountInfoCity();
            city.setId(c.getId());
            city.setCode(c.getCode());
            city.setJobType(c.getJobtype());
            city.setRemainNum(c.getRemainNum());
            //遍历城市信息，获取对应code的城市名称
            String citName=dictCityList.stream().filter(dc->dc.getCode().equals(c.getCode())).findFirst().get().getName();
            city.setName(citName);
            infoCity.add(city);
        });
        logger.info(accountId+"对应的城市{}",infoCity);

        return infoCity;
    }

}
