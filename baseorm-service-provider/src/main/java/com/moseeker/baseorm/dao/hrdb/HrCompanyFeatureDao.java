package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyFeature;
import static com.moseeker.baseorm.db.hrdb.tables.HrCompanyFeature.HR_COMPANY_FEATURE;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyFeatureRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zztaiwll on 18/3/14.
 */
@Service
public class HrCompanyFeatureDao extends JooqCrudImpl<HrCompanyFeature, HrCompanyFeatureRecord> {
    public HrCompanyFeatureDao() {
        super(HR_COMPANY_FEATURE, HrCompanyFeature.class);
    }
    public HrCompanyFeatureDao(TableImpl<HrCompanyFeatureRecord> table, Class<HrCompanyFeature> hrCompanyFeatureClass) {
        super(table, hrCompanyFeatureClass);
    }
    /*
     根据公司id获取公司福利特色列表
     */
    public List<HrCompanyFeature> getFeatureListByCompanyId(int companyId){
        List<HrCompanyFeature> list=create.selectFrom(HR_COMPANY_FEATURE).where(HR_COMPANY_FEATURE.COMPANY_ID.eq(companyId)).and(HR_COMPANY_FEATURE.DISABLE.eq(1))
                .and(HR_COMPANY_FEATURE.FEATURE.ne("")).fetchInto(HrCompanyFeature.class);
        return list;
    }
    /*
     根据公司id列表获取公司福利特色列表
     */
    public List<HrCompanyFeature> getFeatureListByCompanyIdList(List<Integer> companyIdList){
        List<HrCompanyFeature> list=create.selectFrom(HR_COMPANY_FEATURE).where(HR_COMPANY_FEATURE.COMPANY_ID.in(companyIdList)).and(HR_COMPANY_FEATURE.DISABLE.eq(1))
                .and(HR_COMPANY_FEATURE.FEATURE.ne("")).fetchInto(HrCompanyFeature.class);
        return list;
    }
    /*
     根据福利特色列表获取福利特色
     */
    public List<HrCompanyFeature> getFeatureListByIdList(List<Integer> idList){
        List<HrCompanyFeature> list=create.selectFrom(HR_COMPANY_FEATURE).where(HR_COMPANY_FEATURE.ID.in(idList)).and(HR_COMPANY_FEATURE.DISABLE.eq(1)).fetchInto(HrCompanyFeature.class);
        return list;
    }
    /*
     根据id获取福利特色
     */
    public HrCompanyFeature getFeatureListById(int id){
        HrCompanyFeature result=create.selectFrom(HR_COMPANY_FEATURE).where(HR_COMPANY_FEATURE.ID.eq(id)).fetchOneInto(HrCompanyFeature.class);
        return result;
    }

}
