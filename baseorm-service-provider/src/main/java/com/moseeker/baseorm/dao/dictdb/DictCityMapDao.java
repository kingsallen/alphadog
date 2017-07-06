package com.moseeker.baseorm.dao.dictdb;

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

    public List<List<String>> getOtherCityFunllLevel(ChannelType channelType, Collection<Integer> cityCodes) {

        if (cityCodes == null || cityCodes.size() == 0) return new ArrayList<>();

        //找到所有city

        Query query = new Query.QueryBuilder().where(new Condition("code", cityCodes, ValueOp.IN)).buildQuery();

        List<DictCityDO> dictCitys = dictCityDao.getDatas(query);

        //仟寻的城市层次
        List<List<Integer>> orginCodes = new ArrayList<>();

        //对应的其它渠道的层次
        List<List<String>> otherCodes = new ArrayList<>();

        Set<Integer> moseekerCodes = new HashSet<>();
        for (DictCityDO cityDO : dictCitys) {
            List<DictCityDO> moseekerCityLevels = dictCityDao.getMoseekerCityLevel(cityDO);
            if (moseekerCityLevels != null && moseekerCityLevels.size() > 0) {
                for (DictCityDO dictCityDO : moseekerCityLevels) {
                    moseekerCodes.add(dictCityDO.getCode());
                }
                orginCodes.add(moseekerCityLevels.stream().map(city -> city.getCode()).collect(Collectors.toList()));
            }
        }

        if (moseekerCodes.size() > 0) {
            Query channelCityQuery = new Query.QueryBuilder()
                    .where(new Condition("CODE", moseekerCodes, ValueOp.IN))
                    .and("channel", channelType.getValue())
                    .buildQuery();
            List<DictCityMapDO> dictCityMapDOS = getDatas(channelCityQuery);

            //将仟寻城市级别对应到第三方渠道级别
            for (List<Integer> moseekerCityLevels : orginCodes) {
                List<String> otherCity = new ArrayList<>();
                for (Integer moseekerCode : moseekerCityLevels) {
                    String otherCode = getOtherCode(moseekerCode, dictCityMapDOS);
                    if (otherCode != null) {
                        otherCity.add(otherCode);
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
}
