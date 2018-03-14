package com.moseeker.function.service.chaos.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.moseeker.baseorm.base.AbstractDictOccupationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.util.OccupationUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.CityEntity;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PositionSyncMailUtil {

    @Autowired
    OccupationUtil occupationUtil;

    @Autowired
    CityEntity cityEntity;

    @Autowired
    JobPositionCityDao jobPositionCityDao;

    public String getExperience(String experience) {
        if (StringUtils.isNullOrEmpty(experience)) {
            return "不限";
        }
        return experience;
    }

    public String getDegree(double degree) {
        switch ((int) degree) {
            case 0:
                return "不限";
            case 1:
                return "大专";
            case 2:
                return "本科";
            case 3:
                return "硕士";
            case 4:
                return "MBA";
            case 5:
                return "博士";
            case 6:
                return "中专";
            case 7:
                return "高中";
            case 8:
                return "博士后";
            case 9:
                return "初中";
            default:
                return "未知:" + degree;

        }
    }

    public String getOccupation(int channel, String occupationCode) throws BIZException {
        if (StringUtils.isNullOrEmpty(occupationCode)) {
            return "无";
        }
        List<String> occupationNames = new ArrayList<>();

        AbstractDictOccupationDao dao=occupationUtil.getOccupationDaoInstance(channel);

        JSONArray array=JSONArray.parseArray(JSON.toJSONString(dao.getFullOccupations(occupationCode)));

        for(int i=0;i<array.size();i++){
            occupationNames.add(array.getJSONObject(i).getString("name"));
        }

        if (occupationNames.size() == 0) {
            return "无";
        }
        StringBuilder occupationBuilder = new StringBuilder();
        for (String name : occupationNames) {
            occupationBuilder.append('【').append(name).append('】');
        }
        return occupationBuilder.toString();
    }

    public String getCitys(int positionId) {
        List<DictCityDO> dictCityDOS = jobPositionCityDao.getPositionCitys(positionId);
        if (dictCityDOS == null || dictCityDOS.size() == 0) {
            return "无";
        }

        List<List<DictCityDO>> fullCities = cityEntity.getFullCities(dictCityDOS);

        StringBuilder cityBuilder = new StringBuilder();
        StringBuilder innerBuilder = new StringBuilder();
        for (List<DictCityDO> cityDOS : fullCities) {
            cityBuilder.append("【");
            innerBuilder.delete(0, innerBuilder.length());
            for (DictCityDO cityDO : cityDOS) {
                innerBuilder.append(',').append(cityDO.getName());
            }
            if (innerBuilder.length() > 0) {
                innerBuilder.delete(0, 1);
            }
            cityBuilder.append(innerBuilder);
            cityBuilder.append("】");
        }
        return cityBuilder.toString();
    }

    public String getAddress(String address) {
        if (StringUtils.isNullOrEmpty(address)) {
            return "无";
        }

        return address;
    }
}
