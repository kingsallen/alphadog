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
import com.moseeker.baseorm.pojo.JobPositionPojo;
import com.moseeker.baseorm.pojo.RecommendedPositonPojo;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.db.analytics.tables.StJobSimilarity;
import com.moseeker.db.hrdb.tables.HrCompany;
import com.moseeker.db.hrdb.tables.HrCompanyAccount;
import com.moseeker.db.hrdb.tables.records.HrCompanyAccountRecord;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.userdb.tables.UserHrAccount;
import com.moseeker.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.position.struct.Position;
import org.apache.thrift.TException;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
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
    /*
     * 获取推荐职位
     */
    public List<RecommendedPositonPojo> getRecommendedPositions(int pid) {
		// pid -> company_type
		List<RecommendedPositonPojo> recommedPositoinsList = new ArrayList<>();
		// pid -> company_id
		/*
		 * Result<? extends Record> positionResults =
		 * create.select().from(JobPosition.JOB_POSITION).where(JobPosition.
		 * JOB_POSITION.ID.equal(pid)).fetch(); Record positionResult =
		 * positionResults.get(0); int company_id = ((UInteger)
		 * positionResult.getValue("company_id")).intValue(); // get company
		 * info Result<? extends Record> companyResults =
		 * create.select().from(HrCompany.HR_COMPANY).where(HrCompany.
		 * HR_COMPANY.ID.equal((int)(company_id))).fetch();
		 * Record companyResult = companyResults.get(0); int company_type =
		 * ((UByte) companyResult.getValue("type")).intValue();
		 * //公司区分(其它:2,免费用户:1,企业用户:0)
		 */
		Result<? extends Record> positionAndCompanyRecords = create.select().from(JobPosition.JOB_POSITION)
				.join(HrCompany.HR_COMPANY).on(JobPosition.JOB_POSITION.COMPANY_ID.equal(HrCompany.HR_COMPANY.ID))
				.where(JobPosition.JOB_POSITION.ID.equal(pid)).fetch();
		if (positionAndCompanyRecords.size() == 0) {
			return recommedPositoinsList;
		}
		Record positionAndCompanyRecord = positionAndCompanyRecords.get(0);
		int company_id = positionAndCompanyRecord.getValue(JobPosition.JOB_POSITION.COMPANY_ID);
		int company_type = Integer.parseInt(positionAndCompanyRecord.getValue("type").toString()); // 公司区分(其它:2,免费用户:1,企业用户:0)

		// get recom
		Result<? extends Record> recomResults;
		Condition condition = StJobSimilarity.ST_JOB_SIMILARITY.POS_ID.equal(pid);
		if (company_type == 0) {
			UserHrAccountRecord account = create.selectFrom(UserHrAccount.USER_HR_ACCOUNT).where(UserHrAccount.USER_HR_ACCOUNT.ID.equal(positionAndCompanyRecord.getValue(JobPosition.JOB_POSITION.PUBLISHER))).fetchOne();
			//如果是子账号，则查询子账号下的推荐职位；如果是主帐号，则查询公司下的所有职位
			if(account != null && account.getAccountType() != null && account.getAccountType().intValue() == 1) {
				/* 检查是否是子公司的职位 */
				int publisher = (Integer) positionAndCompanyRecord.getValue("publisher");

				HrCompanyAccountRecord record = create.selectFrom(HrCompanyAccount.HR_COMPANY_ACCOUNT)
						.where(HrCompanyAccount.HR_COMPANY_ACCOUNT.ACCOUNT_ID.equal(publisher)).fetchOne();
				if (record != null && record.getCompanyId() != null) {
					if (company_id != record.getCompanyId()) {
						company_id = record.getCompanyId();
					}
				}
				condition = condition.and(StJobSimilarity.ST_JOB_SIMILARITY.DEPARTMENT_ID.equal(company_id)); 
			} else {
				Result<Record1<Integer>> result = create.select(HrCompany.HR_COMPANY.ID).from(HrCompany.HR_COMPANY).where(HrCompany.HR_COMPANY.PARENT_ID.equal((int)(company_id))).fetch();
				List<Integer> departmentIds = new ArrayList<>();
				if(result != null && result.size() > 0) {
					result.forEach(record -> {
						record.get(HrCompany.HR_COMPANY.ID);
						departmentIds.add(record.get(HrCompany.HR_COMPANY.ID).intValue());
					});
				}
				departmentIds.add(company_id);
				condition = condition.and(StJobSimilarity.ST_JOB_SIMILARITY.DEPARTMENT_ID.in(departmentIds)); 
			}
		}
		recomResults = create.select().from(StJobSimilarity.ST_JOB_SIMILARITY).where(condition).fetch();
		List<Integer> pids = new ArrayList<>();
		for (Record recomResult : recomResults) {
			pids.add(((Integer) recomResult.getValue("recom_id")).intValue());
		}

		/*
		 * public int pid; public String job_title; public int company_id;
		 * public String company_name; public String company_logo;
		 */
		// pids -> result
		recommedPositoinsList = create
				.select(JobPosition.JOB_POSITION.ID.as("pid"), JobPosition.JOB_POSITION.TITLE.as("job_title"),
						JobPosition.JOB_POSITION.COMPANY_ID.as("company_id"),
						JobPosition.JOB_POSITION.SALARY_TOP.as("salary_top"),
						JobPosition.JOB_POSITION.SALARY_BOTTOM.as("salary_bottom"),
						JobPosition.JOB_POSITION.CITY.as("job_city"),
						JobPosition.JOB_POSITION.PUBLISHER.as("publisher"),
						HrCompany.HR_COMPANY.ABBREVIATION.as("company_name"),
						HrCompany.HR_COMPANY.LOGO.as("company_logo"))
				.from(JobPosition.JOB_POSITION).join(HrCompany.HR_COMPANY)
				.on(HrCompany.HR_COMPANY.ID.equal(JobPosition.JOB_POSITION.COMPANY_ID))
				.where(JobPosition.JOB_POSITION.ID.in(pids)).fetch().into(RecommendedPositonPojo.class);
		/* 子公司职位需要返回子公司的公司简称和公司logo */
		recommedPositoinsList.forEach(position -> {
			/* 检查是否是子公司的职位 */
			int publisher = position.getPublisher();
			HrCompanyAccountRecord hrcompanyAccountrecord = create.selectFrom(HrCompanyAccount.HR_COMPANY_ACCOUNT)
					.where(HrCompanyAccount.HR_COMPANY_ACCOUNT.ACCOUNT_ID.equal(publisher)).fetchOne();
			if (hrcompanyAccountrecord != null && hrcompanyAccountrecord.getCompanyId() != null) {
				if (position.getCompany_id() != hrcompanyAccountrecord.getCompanyId()) {
					HrCompanyRecord subcompany = create.selectFrom(HrCompany.HR_COMPANY)
							.where(HrCompany.HR_COMPANY.ID.equal((int)(hrcompanyAccountrecord.getCompanyId()))).fetchOne();
					if (subcompany != null){
						position.setCompany_logo(subcompany.getLogo());
						position.setCompany_name(subcompany.getAbbreviation());							
					}
				}
			}
		});
			
		return recommedPositoinsList;
	}
    /*
     * 根据id获取position
     */
    public JobPositionPojo getPosition(int positionId) {
        List<JobPositionPojo> jobPositionPojoList = null;
        if (positionId > 0) {
            Condition condition = JobPosition.JOB_POSITION.ID.equal(positionId);
            jobPositionPojoList = create.select().from(JobPosition.JOB_POSITION)
                    .where(condition).fetchInto(JobPositionPojo.class);
        }
        return jobPositionPojoList != null ? jobPositionPojoList.get(0) : null;
    }
    /*
     * 获取position
     */
    public Position getPositionByQuery(Query query) throws TException {
		Position position = new Position();
		try {
			JobPositionRecord record = this.getRecord(query);
			if(record != null) {
				record.into(position);
				position.setHb_status(record.getHbStatus());
				position.setCompany_id(record.getCompanyId().intValue());
				position.setAccountabilities(record.getAccountabilities());
				return position;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		return position;
	}



    public JobPositionRecord getPositionById(int positionId) {
        JobPositionRecord record = null;
        Connection conn = null;
        try {
            if(positionId > 0) {
                Result<JobPositionRecord> result = create.selectFrom(JobPosition.JOB_POSITION)
                        .where(JobPosition.JOB_POSITION.ID.equal(positionId))
                        .limit(1).fetch();
                if(result != null && result.size() > 0) {
                    record = result.get(0);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            } finally {
                //do nothing
            }
        }
        return record;
    }

	public int updatePosition(Position position) throws TException {
		int count = 0;
		if(position.getId() > 0) {
			JobPositionRecord record = (JobPositionRecord)BeanUtils.structToDB(position, JobPositionRecord.class);
			try {
				count = this.updateRecord(record);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
		return count;
	}
}
