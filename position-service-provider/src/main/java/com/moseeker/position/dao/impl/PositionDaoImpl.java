package com.moseeker.position.dao.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.analytics.tables.StJobSimilarity;
import com.moseeker.db.hrdb.tables.HrCompany;
import com.moseeker.db.hrdb.tables.HrCompanyAccount;
import com.moseeker.db.hrdb.tables.records.HrCompanyAccountRecord;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.jobdb.tables.JobPosition;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.position.dao.PositionDao;
import com.moseeker.position.pojo.RecommendedPositonPojo;

/**
 * Created by chendi on 5/25/16.
 */
@Repository
public class PositionDaoImpl extends BaseDaoImpl<JobPositionRecord, JobPosition> implements PositionDao {

    @Override
    protected void initJOOQEntity() {
        this.tableLike = JobPosition.JOB_POSITION;
    }

    @Override
    public List<RecommendedPositonPojo> getRecommendedPositions(int pid) {
        // pid -> company_type
        List<RecommendedPositonPojo> recommedPositoinsList = new ArrayList<>();
        try (Connection conn = DBConnHelper.DBConn.getConn()) {
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            // pid -> company_id
            /*
            Result<? extends Record> positionResults = create.select().from(JobPosition.JOB_POSITION).where(JobPosition.JOB_POSITION.ID.equal(pid)).fetch();
            Record positionResult = positionResults.get(0);
            int company_id = ((UInteger) positionResult.getValue("company_id")).intValue();
            // get company info
            Result<? extends Record> companyResults = create.select().from(HrCompany.HR_COMPANY).where(HrCompany.HR_COMPANY.ID.equal(UInteger.valueOf(company_id))).fetch();
            Record companyResult = companyResults.get(0);
            int company_type = ((UByte) companyResult.getValue("type")).intValue(); //公司区分(其它:2,免费用户:1,企业用户:0)
            */
            Result<? extends Record> positionAndCompanyRecords
                    = create.select()
                    .from(JobPosition.JOB_POSITION)
                    .join(HrCompany.HR_COMPANY).on(JobPosition.JOB_POSITION.COMPANY_ID.equal(HrCompany.HR_COMPANY.ID))
                    .where(JobPosition.JOB_POSITION.ID.equal(pid)).fetch();
            if (positionAndCompanyRecords.size() == 0) {
                return recommedPositoinsList;
            }
            Record positionAndCompanyRecord = positionAndCompanyRecords.get(0);
            int company_id = ((UInteger) positionAndCompanyRecord.getValue("company_id")).intValue();
            int company_type = ((UByte) positionAndCompanyRecord.getValue("type")).intValue(); //公司区分(其它:2,免费用户:1,企业用户:0)
            boolean child = false;
            
            // get recom
            Result<? extends Record> recomResults;
            Condition condition = StJobSimilarity.ST_JOB_SIMILARITY.POS_ID.equal(pid);
            if (company_type == 0) {
            	/* 检查是否是子公司的职位 */
            	int publisher = (Integer)positionAndCompanyRecord.getValue("publisher");
                
                HrCompanyAccountRecord record = create.selectFrom(HrCompanyAccount.HR_COMPANY_ACCOUNT)
                		.where(HrCompanyAccount.HR_COMPANY_ACCOUNT.ACCOUNT_ID.equal(publisher))
                		.fetchOne();
                if(record != null && record.getCompanyId() != null) {
                	if(company_id != record.getCompanyId()) {
                		company_id = record.getCompanyId();
                    	child = true;
                	}
                }
                
                condition = condition.and(StJobSimilarity.ST_JOB_SIMILARITY.DEPARTMENT_ID.equal(company_id)); // select analytics by pid and did
            }
            recomResults = create.select().from(StJobSimilarity.ST_JOB_SIMILARITY).where(condition).fetch();
            List<Integer> pids = new ArrayList<>();
            for (Record recomResult : recomResults) {
                pids.add(((Integer) recomResult.getValue("recom_id")).intValue());
            }
            
            /*
            public int pid;
            public String job_title;
            public int company_id;
            public String company_name;
            public String company_logo;
            */
            // pids -> result
            recommedPositoinsList =
                    create.select(
                            JobPosition.JOB_POSITION.ID.as("pid"),
                            JobPosition.JOB_POSITION.TITLE.as("job_title"),
                            JobPosition.JOB_POSITION.COMPANY_ID.as("company_id"),
                            HrCompany.HR_COMPANY.ABBREVIATION.as("company_name"),
                            HrCompany.HR_COMPANY.LOGO.as("company_logo")
                            )
                            .from(JobPosition.JOB_POSITION)
                            .join(HrCompany.HR_COMPANY).on(HrCompany.HR_COMPANY.ID.equal(JobPosition.JOB_POSITION.COMPANY_ID))
                            .where(JobPosition.JOB_POSITION.ID.in(pids))
                            .fetch().into(RecommendedPositonPojo.class);
            /* 子公司职位需要返回子公司的公司简称和公司logo */
            if(child) {
            	HrCompanyRecord company = create.selectFrom(HrCompany.HR_COMPANY)
            			.where(HrCompany.HR_COMPANY.ID.equal(UInteger.valueOf(company_id)))
            			.fetchOne();
            	if(company != null) {
            		recommedPositoinsList.forEach(position -> {
            			position.setCompany_logo(company.getLogo());
            			position.setCompany_name(company.getAbbreviation());
            		});
            	}
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return recommedPositoinsList;
    }
}
