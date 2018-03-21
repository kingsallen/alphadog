package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionHrCompanyFeature;
import static com.moseeker.baseorm.db.jobdb.tables.JobPositionHrCompanyFeature.JOB_POSITION_HR_COMPANY_FEATURE;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPcAdvertisementRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionHrCompanyFeatureRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPcAdvertisementDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zztaiwll on 18/3/14.
 */
@Service
public class JobPositionHrCompanyFeatureDao extends JooqCrudImpl<JobPositionHrCompanyFeature,JobPositionHrCompanyFeatureRecord> {
    public JobPositionHrCompanyFeatureDao(){
        super(JOB_POSITION_HR_COMPANY_FEATURE,JobPositionHrCompanyFeature.class);
    }
    public JobPositionHrCompanyFeatureDao(TableImpl<JobPositionHrCompanyFeatureRecord> table, Class<JobPositionHrCompanyFeature> jobPositionHrCompanyFeatureClass) {
        super(table, jobPositionHrCompanyFeatureClass);
    }

    /*
     获取职位福利中间表,根据职位id
     */
    public List<JobPositionHrCompanyFeature> getPositionFeatureList(int pid){
        List<JobPositionHrCompanyFeature> list=create.selectFrom(JOB_POSITION_HR_COMPANY_FEATURE).where(JOB_POSITION_HR_COMPANY_FEATURE.PID.eq(pid)).fetchInto(JobPositionHrCompanyFeature.class);
        return list;
    }
    /*
    获取职位福利中间表,根据职位id的列表
    */
    public List<JobPositionHrCompanyFeature> getPositionFeatureBatch(List<Integer> pidList){
        List<JobPositionHrCompanyFeature> list=create.selectFrom(JOB_POSITION_HR_COMPANY_FEATURE).where(JOB_POSITION_HR_COMPANY_FEATURE.PID.in(pidList)).fetchInto(JobPositionHrCompanyFeature.class);
        return list;
    }
    /*
     根据职位id删除公司的福利特色
     */
    public int deletePositionFeature(int pid){
        int result=create.deleteFrom(JOB_POSITION_HR_COMPANY_FEATURE).where(JOB_POSITION_HR_COMPANY_FEATURE.PID.eq(pid)).execute();
        return result;
    }
    /*
     根据职位id列表删除公司的福利特色
     */
    public int deletePositionFeatureBatch(List<Integer> pids){
        int result=create.deleteFrom(JOB_POSITION_HR_COMPANY_FEATURE).where(JOB_POSITION_HR_COMPANY_FEATURE.PID.in(pids)).execute();
        return result;
    }
    public int addPositionFeature(int pid,int fid){
       int result=create.insertInto(JOB_POSITION_HR_COMPANY_FEATURE).columns(JOB_POSITION_HR_COMPANY_FEATURE.PID,JOB_POSITION_HR_COMPANY_FEATURE.FID).values(pid,fid).execute();
       return result;
    }
}
