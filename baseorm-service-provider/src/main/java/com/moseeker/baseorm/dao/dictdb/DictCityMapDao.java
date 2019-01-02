package com.moseeker.baseorm.dao.dictdb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCityMap;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityMapRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityMapDO;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class DictCityMapDao extends JooqCrudImpl<DictCityMapDO, DictCityMapRecord> {

    @Autowired
    DictCityDao dictCityDao;

    public DictCityMapDao() {
        super(DictCityMap.DICT_CITY_MAP, DictCityMapDO.class);
    }

    public DictCityMapDao(TableImpl<DictCityMapRecord> table, Class<DictCityMapDO> dictCityMapDOClass) {
        super(table, dictCityMapDOClass);
    }

    public List<DictCityMapDO> getOtherCityByCodes(ChannelType channelType, List<Integer> cityCodes) {
        Query channelCityQuery = new Query.QueryBuilder()
                .where(new Condition(DictCityMap.DICT_CITY_MAP.CODE.getName(), cityCodes, ValueOp.IN))
                .and(DictCityMap.DICT_CITY_MAP.CHANNEL.getName(), channelType.getValue())
                .buildQuery();
        List<DictCityMapDO> dictCityMaps = getDatas(channelCityQuery);

        if(dictCityMaps==null){
            return new ArrayList<>();
        }

        return dictCityMaps;
    }

    public List<List<String>> getOtherCityByLastCodes(ChannelType channelType, List<Integer> cityCodes) {

        if (cityCodes == null || cityCodes.size() == 0) return new ArrayList<>();

        //找到所有city

        Query query = new Query.QueryBuilder().where(new Condition("code", cityCodes, ValueOp.IN)).buildQuery();

        List<DictCityDO> dictCitys = dictCityDao.getDatas(query);

        //仟寻的城市层次
        List<List<Integer>> orginCodes = new ArrayList<>();

        for (DictCityDO cityDO : dictCitys) {
            List<DictCityDO> moseekerCityLevels = dictCityDao.getMoseekerLevels(cityDO);
            if (moseekerCityLevels != null && moseekerCityLevels.size() > 0) {
                orginCodes.add(moseekerCityLevels.stream().map(city -> city.getCode()).collect(Collectors.toList()));
            }
        }

        return getOtherCityByFullCodes(channelType, orginCodes);

    }

    public List<List<String>> getOtherCityByFullCodes(ChannelType channelType, List<List<Integer>> cityCodes) {
        Set<Integer> moseekerCodes = new HashSet<>();
        for (List<Integer> codes : cityCodes) {
            for (Integer code : codes) {
                moseekerCodes.add(code);
            }
        }

        List<List<String>> otherCodes = new ArrayList<>();

        if (moseekerCodes.size() > 0) {
            Query channelCityQuery = new Query.QueryBuilder()
                    .where(new Condition(DictCityMap.DICT_CITY_MAP.CODE.getName(), moseekerCodes, ValueOp.IN))
                    .and(DictCityMap.DICT_CITY_MAP.CHANNEL.getName(), channelType.getValue())
                    .buildQuery();
            List<DictCityMapDO> dictCityMapDOS = getDatas(channelCityQuery);

            //将仟寻城市级别对应到第三方渠道级别
            for (List<Integer> moseekerCityLevels : cityCodes) {
                List<String> otherCity = new ArrayList<>();
                for (Integer moseekerCode : moseekerCityLevels) {
                    String otherCode = getOtherCode(moseekerCode, dictCityMapDOS);
                    if (otherCode != null) {
                        if(channelType==ChannelType.LIEPIN || channelType==ChannelType.JOB58) {
                            otherCity.add(otherCode);
                        }else{
                            TypeReference<List<String>> typeRef
                                    = new TypeReference<List<String>>() {};
                            otherCity= JSON.parseObject(otherCode,typeRef);
                        }

                    }
                }
                if (otherCity.size() > 0) {
                    otherCodes.add(otherCity);
                }
            }
        }

        return otherCodes;
    }

    private String getOtherCode(int moseekerCode, List<DictCityMapDO> dictCityMaps) {
        for (DictCityMapDO dictCityMapDO : dictCityMaps) {
            if (dictCityMapDO.getCode() == moseekerCode) {
                return dictCityMapDO.getCodeOther();
            }
        }

        return null;
    }

    public List<DictCityMapDO> getOtherCityByChannel(short channel) {
        return null;
    }
}
