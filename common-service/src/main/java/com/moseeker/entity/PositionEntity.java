package com.moseeker.entity;

import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionCityRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 职位业务
 * Created by jack on 06/09/2017.
 */
@Service
public class PositionEntity {

    @Autowired
    private JobPositionDao positionDao;

    @Autowired
    private JobPositionCityDao positionCityDao;

    @Autowired
    private DictCityDao cityDao;

    /**
     * 查找职位信息
     * 城市信息，如果存在job_position_city 则取code对应的name；不存在则取jobdb.job_position.city
     * @param ID 根据编号查找职位
     * @return 职位信息
     * @throws CommonException 异常信息
     */
    public JobPositionRecord getPositionByID(int ID) throws CommonException {
        JobPositionRecord positionRecord = positionDao.getPositionById(ID);
        if (positionRecord != null) {
            List<DictCityDO> dictCityDOList = positionCityDao.getPositionCitys(positionRecord.getId());
            if (dictCityDOList != null && dictCityDOList.size() > 0) {
                StringBuffer stringBuffer = new StringBuffer();
                dictCityDOList.forEach(city -> stringBuffer.append(city.getName()).append(","));
                if (stringBuffer.length() > 0) {
                    stringBuffer.deleteCharAt(stringBuffer.length()-1);
                    positionRecord.setCity(stringBuffer.toString());
                }
            }
        }
        return positionRecord;
    }

    /**
     * 根据查询条件查找职位信息
     * 职位数据如果存在job_position_city 数据，则使用职位数据如果存在job_position_city对应城市，否则直接取city
     * @param query 查询工具
     * @return 职位集合
     */
    public List<JobPositionRecord> getPositions(Query query) {

        List<JobPositionRecord> positionRecordList = positionDao.getRecords(query);
        if (positionRecordList != null) {
            List<Integer> pidList = positionRecordList.stream()
                    .map(JobPositionRecord::getId).collect(Collectors.toList());

            query = new Query.QueryBuilder().where(new com.moseeker.common.util.query.Condition("pid", pidList, ValueOp.IN)).buildQuery();
            List<JobPositionCityRecord> jobPositionCityRecordList = positionCityDao.getRecords(query);

            if (jobPositionCityRecordList == null || jobPositionCityRecordList.size() == 0) {
                return positionRecordList;
            }

            Set<Integer> cityIds = new HashSet<>();

            for (JobPositionCityRecord positionCityRecord : jobPositionCityRecordList) {
                cityIds.add(positionCityRecord.getCode());
            }
            query = new Query.QueryBuilder().where(new com.moseeker.common.util.query.Condition("code", cityIds, ValueOp.IN)).buildQuery();
            List<DictCityRecord> dictCityRecordList = cityDao.getRecords(query);


            if (dictCityRecordList == null || dictCityRecordList.size() == 0) {
                return positionRecordList;
            }

            /** 职位数据如果存在job_position_city 数据，则使用职位数据如果存在job_position_city对应城市，否则直接取city */
            for (JobPositionRecord positionRecord: positionRecordList) {

                /** 职位城市关系记录 */
                List<JobPositionCityRecord> positionCityRecordList = jobPositionCityRecordList.stream()
                        .filter(jobPositionCity ->
                                jobPositionCity.getPid().intValue() == positionRecord.getId().intValue())
                        .collect(Collectors.toList());

                if (positionCityRecordList != null && positionCityRecordList.size() > 0) {
                    StringBuffer cityNameBuffer = new StringBuffer();
                    for (JobPositionCityRecord positionCityRecord : positionCityRecordList) {
                        Optional<DictCityRecord> optionalDictCity = dictCityRecordList.stream()
                                .filter(dictCityRecord ->
                                        dictCityRecord.getCode().intValue() == positionCityRecord.getCode().intValue())
                                .findAny();
                        if (optionalDictCity.isPresent()) {
                            cityNameBuffer.append(optionalDictCity.get().getName()).append(",");
                        }
                    }
                    if (cityNameBuffer.length() > 0) {
                        cityNameBuffer.deleteCharAt(cityNameBuffer.length()-1);
                        positionRecord.setCity(cityNameBuffer.toString());
                    }
                }
            }
        }
        return positionRecordList;
    }

    public List<Integer> getAppCvConfigIdByCompany(int companyId, int hrAccountId) {
        List<Integer> appConfigCvIds = new ArrayList<>();
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.select("app_cv_config_id");
        queryBuilder.where("company_id", companyId).and(new Condition("app_cv_config_id", 0, ValueOp.NEQ));
        if (hrAccountId != 0) {
            queryBuilder.and("publisher", hrAccountId);
        }
        queryBuilder.and(new Condition("status", 1, ValueOp.NEQ));
        List<JobPositionDO> list = positionDao.getDatas(queryBuilder.buildQuery());
        if (list != null && !list.isEmpty()) {
            appConfigCvIds = list.stream().map(m -> m.getAppCvConfigId()).collect(Collectors.toList());
        }
        return appConfigCvIds;
    }

    public int getAppCvConfigIdByPosition(int positionId) {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where("id", positionId);
        JobPositionDO jobPositionDO = positionDao.getData(queryBuilder.buildQuery());
        return jobPositionDO == null ? 0 : jobPositionDO.getAppCvConfigId();
    }

    public Map<Integer, Integer> getAppCvConfigIdByPositions(List<Integer> positionIds) {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(new Condition("id", positionIds, ValueOp.IN));
        List<JobPositionDO> jobPositionList = positionDao.getDatas(queryBuilder.buildQuery());
        return (jobPositionList == null || jobPositionList.isEmpty()) ? new HashMap<>() :
                jobPositionList.parallelStream().collect(Collectors.toMap(k -> k.getId(), v -> v.getAppCvConfigId()));
    }

}
