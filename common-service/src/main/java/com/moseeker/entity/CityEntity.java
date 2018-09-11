package com.moseeker.entity;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.pojos.ParentCityRule;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 城市实体
 * Created by jack on 28/09/2017.
 */
@Component
public class CityEntity {

    @Autowired
    DictCityDao cityDao;

    /**
     * 查找城市链路
     * @param city 当前城市
     * @return
     */
    public List<DictCityDO> getFullCity(DictCityDO city) {
        List<DictCityDO> fullCities = new ArrayList<>();
        findCity(fullCities, city);
        fullCities.add(city);
        return fullCities;
    }

    /**
     * 查找城市链路
     * @param cityDOList 城市集合
     * @return 城市链路集合
     */
    public List<List<DictCityDO>> getFullCities(List<DictCityDO> cityDOList) {
        List<List<DictCityDO>> cities = new ArrayList<>();

        if (cityDOList != null && cityDOList.size() > 0) {
            cityDOList.forEach(cityDO -> cities.add(getFullCity(cityDO)));
        }

        return cities;
    }

    /**
     * 查找完成城市级别链路
     * @param fullCities
     * @param city
     */
    private void findCity(List<DictCityDO> fullCities, DictCityDO city) {
        if (city != null) {
            if (city.getLevel() > 1) {
                DictCityDO parentCity = findParentCity(city);
                if (parentCity != null) {
                    findCity(fullCities, parentCity);
                    fullCities.add(parentCity);
                }
            }
        }
    }

    /**
     * 查找上一级城市
     * @param cityDO 当前城市
     * @return 上一级城市 不存在时返回null
     */
    private DictCityDO findParentCity(DictCityDO cityDO) {
        if (cityDO.getLevel() > 1) {
            Query query = ParentCityRule.buildQuery(cityDO);
            if (query != null) {
                DictCityDO parentCity = cityDao.getData(query);
                if (parentCity == null && cityDO.getLevel() - 2 >= 1) {
                    parentCity = cityDao.getData(new ParentCityRule(cityDO.getCode(), cityDO.getLevel()-1).buildQuery());
                    return parentCity;
                } else {
                    return parentCity;
                }
            }
        }
        return null;
    }


}
