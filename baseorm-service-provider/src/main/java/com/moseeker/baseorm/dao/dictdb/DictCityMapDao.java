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
            List<Integer> moseekerCityLevels = getMoseekerCityLevel(cityDO);
            if (moseekerCityLevels != null && moseekerCityLevels.size() > 0) {
                moseekerCodes.addAll(moseekerCityLevels);
                orginCodes.add(moseekerCityLevels);
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

                otherCodes.add(otherCity);
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


    /**
     * 获取完整的城市级别
     * 例:徐家汇 -> 上海，徐家汇
     *
     * @param cityDO
     * @return
     */
    public List<Integer> getMoseekerCityLevel(DictCityDO cityDO) {
        List<Integer> cityLevels = new ArrayList<>();
        if (cityDO == null) {
            cityLevels.add(111111);
            return cityLevels;
        }
        //如果给的城市的level为0，那么取它的上一级做为最后一级
        if (cityDO.getLevel() == 0) {
            DictCityDO upperLevel = getUpperLevel(cityDO.getCode());
            return getMoseekerCityLevel(upperLevel);
        } else if (cityDO.getLevel() == 1) {
            //如果级别为1，那么直接返回
            cityLevels.add(cityDO.getCode());
        } else {
            cityLevels.add(0, cityDO.getCode());
            DictCityDO upperLevel = null;
            while ((upperLevel = getUpperLevel(cityDO.getCode())) != null) {
                cityLevels.add(0, upperLevel.getCode());
                if (upperLevel.getLevel() == 1) {
                    break;
                }
            }
        }

        return cityLevels;
    }

    /**
     * 获取更高的级别
     * 例子:徐家汇->上海
     *
     * @return
     */
    public DictCityDO getUpperLevel(int code) {

        if (code <= 0) {
            return null;
        }

        int newLevelCode;

        int divide = 10;

        while ((newLevelCode = (code / divide) * divide) == code) {
            divide *= 10;
        }

        Query query = new Query.QueryBuilder().where("code", newLevelCode).buildQuery();

        DictCityDO upperLevel = dictCityDao.getData(query);

        //找不到父级或者父级的level=0那么继续向上找
        if (upperLevel == null || upperLevel.getLevel() == 0) {
            return getUpperLevel(newLevelCode);
        }

        return upperLevel;
    }
}
