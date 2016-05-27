package com.moseeker.position.dao.impl;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.analytics.Analytics;
import com.moseeker.db.analytics.tables.StJobSimilarity;
import com.moseeker.db.hrdb.tables.HrCompany;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.db.jobdb.tables.JobPosition;
import com.moseeker.position.dao.PositionDao;
import com.sun.org.apache.regexp.internal.RE;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

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
    public List<JobPositionRecord> getRecommendedPositions(int pid) {
        // pid -> company_type
        List<JobPositionRecord> recommedPositoinsList = new ArrayList<>();
        try (Connection conn = DBConnHelper.DBConn.getConn()) {
            // pid -> company_id
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            Result<? extends Record> positoinResults = create.select().from(JobPosition.JOB_POSITION).where(JobPosition.JOB_POSITION.ID.equal(pid)).fetch();
            Record result = positoinResults.get(0);
            int company_id = ((UInteger) result.getValue("company_id")).intValue();
            System.out.println(company_id);
            // get company info
            Result<? extends Record> companyResults = create.select().from(HrCompany.HR_COMPANY).where(HrCompany.HR_COMPANY.ID.equal(UInteger.valueOf(company_id))).fetch();
            Record companyResult = companyResults.get(0);
            int company_type = ((UByte) companyResult.getValue("type")).intValue(); //公司区分(其它:2,免费用户:1,企业用户:0)
            // get recom results
            Result<? extends Record> recomResults;
            Condition condition = StJobSimilarity.ST_JOB_SIMILARITY.POS_ID.equal(pid);
            if (company_type == 0) {
                // select analytics by pid and did
                condition = condition.and(StJobSimilarity.ST_JOB_SIMILARITY.DEPARTMENT_ID.equal(company_id));
            }
            recomResults = create.select().from(StJobSimilarity.ST_JOB_SIMILARITY).where(condition).fetch();
            System.out.println(recomResults);
            List<Integer> pids = new ArrayList<>();
            for(Record recomResult: recomResults) {
                pids.add(((Integer)recomResult.getValue("recom_id")).intValue());
            }
            // pids -> result
            Result<Record> recommendedPositions = create.select().from(JobPosition.JOB_POSITION).where(JobPosition.JOB_POSITION.ID.in(pids)).fetch();
            for(Record r: recommendedPositions) {
                recommedPositoinsList.add((JobPositionRecord)r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return recommedPositoinsList;
    }
}
