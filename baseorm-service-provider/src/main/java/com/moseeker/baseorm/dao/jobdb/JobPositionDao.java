package com.moseeker.baseorm.dao.jobdb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.JobPositionDO;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionCity;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionCityRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.position.struct.Position;

@Service
public class JobPositionDao extends StructDaoImpl<JobPositionDO, JobPositionRecord, JobPosition> {

    @Override
    protected void initJOOQEntity() {
        this.tableLike = JobPosition.JOB_POSITION;
    }

    public List<JobPositionDO> getPositions(CommonQuery query) {
        List<JobPositionDO> positions = new ArrayList<>();

        try {
            List<JobPositionRecord> records = getResources(query);
            if (records != null && records.size() > 0) {
                positions = records.stream().filter(record -> record != null)
                        .map(record -> BeanUtils.DBToStruct(JobPositionDO.class, record))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //do nothing
        }

        return positions;
    }

    public Position getPositionWithCityCode(CommonQuery query) {

        logger.info("JobPositionDao getPositionWithCityCode");

        Position position = new Position();
        try (Connection conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);) {

            JobPositionRecord record = this.getResource(query);
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

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            // do nothing
        }
        return position;
    }

    public List<Integer> listPositionIdByUserId(int userId) {
        List<Integer> list = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            UserEmployeeRecord employeeRecord = create.selectFrom(UserEmployee.USER_EMPLOYEE)
                    .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.equal(userId)
                            .and(UserEmployee.USER_EMPLOYEE.DISABLE.equal((byte)0))
                            .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.equal((byte)0)))
                    .fetchOne();
            if (employeeRecord != null) {
                Result<Record1<Integer>> result = create.select(JobPosition.JOB_POSITION.ID)
                        .from(JobPosition.JOB_POSITION)
                        .where(JobPosition.JOB_POSITION.COMPANY_ID.equal(UInteger.valueOf(employeeRecord.getCompanyId())))
                        .fetch();
                if(result != null && result.size() > 0) {
                    result.forEach(record ->  {
                        list.add(record.value1());
                    });
                }
            }


        } catch (Exception e) {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                logger.error(e1.getMessage(), e);
            }
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e1) {
                logger.error(e1.getMessage(), e1);
            }
        }

        return list;
    }
}
