package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionCity;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionCityRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.position.struct.Position;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobPositionDao extends JooqCrudImpl<JobPositionDO, JobPositionRecord> {

    public JobPositionDao() {
        super(JobPosition.JOB_POSITION, JobPositionDO.class);
    }

    public JobPositionDao(TableImpl<JobPositionRecord> table, Class<JobPositionDO> jobPositionDOClass) {
        super(table, jobPositionDOClass);
    }

    public List<JobPositionDO> getPositions(Query query) {
        return this.getDatas(query);
    }

    public Position getPositionWithCityCode(Query query) {

        logger.info("JobPositionDao getPositionWithCityCode");

        Position position = new Position();
        JobPositionRecord record = this.getRecord(query);
        if (record != null) {
            position = record.into(position);
            Map<Integer, String> citiesParam = new HashMap<Integer, String>();
            List<Integer> cityCodes = new ArrayList<>();
            Result<JobPositionCityRecord> cities = create.selectFrom(JobPositionCity.JOB_POSITION_CITY)
                    .where(JobPositionCity.JOB_POSITION_CITY.PID.equal(record.getId())).fetch();
            if (cities != null && cities.size() > 0) {
                cities.forEach(city -> {
                    logger.info("code:{}", city.getCode());
                    if (city.getCode() != null) {
                        citiesParam.put(city.getCode(), null);
                        cityCodes.add(city.getCode());
                    }
                });
                logger.info("cityCodes:{}", cityCodes);
                Result<DictCityRecord> dictDicties = create.selectFrom(DictCity.DICT_CITY).where(DictCity.DICT_CITY.CODE.in(cityCodes)).fetch();
                if (dictDicties != null && dictDicties.size() > 0) {
                    dictDicties.forEach(dictCity -> {
                        citiesParam.entrySet().forEach(entry -> {
                            if (entry.getKey().intValue() == dictCity.getCode().intValue()) {
                                logger.info("cityName:{}", dictCity.getName());
                                entry.setValue(dictCity.getName());
                            }
                        });
                    });
                }
            }

            position.setCompany_id(record.getCompanyId().intValue());
            position.setCities(citiesParam);
            citiesParam.forEach((cityCode, cityName) -> {
                logger.info("cityCode:{}, cityName:{}", cityCode, cityName);
            });
        }
        return position;
    }

    public List<Integer> listPositionIdByUserId(int userId) {
        List<Integer> list = new ArrayList<>();
        UserEmployeeRecord employeeRecord = create.selectFrom(UserEmployee.USER_EMPLOYEE)
                .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.equal(userId)
                        .and(UserEmployee.USER_EMPLOYEE.DISABLE.equal((byte)0))
                        .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.equal((byte)0)))
                .fetchOne();
        if (employeeRecord != null) {
            Result<Record1<Integer>> result = create.select(JobPosition.JOB_POSITION.ID)
                    .from(JobPosition.JOB_POSITION)
                    .where(JobPosition.JOB_POSITION.COMPANY_ID.equal(employeeRecord.getCompanyId()))
                    .fetch();
            if(result != null && result.size() > 0) {
                result.forEach(record ->  {
                    list.add(record.value1());
                });
            }
        }
        return list;
    }
}
