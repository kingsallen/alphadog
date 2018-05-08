package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import static com.moseeker.baseorm.db.hrdb.tables.HrCompanyCs.HR_COMPANY_CS;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyCs;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyCsRecord;
import java.util.List;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 18/3/14.
 */
@Service
public class HrCompanyCsDao extends JooqCrudImpl<HrCompanyCs, HrCompanyCsRecord> {
    public HrCompanyCsDao() {
        super(HR_COMPANY_CS, HrCompanyCs.class);
    }
    public HrCompanyCsDao(TableImpl<HrCompanyCsRecord> table, Class<HrCompanyCs> hrCompanyCsClass) {
        super(table, hrCompanyCsClass);
    }
    public HrCompanyCs getHrCompanyCsByCompanyId(int company_id){
        List<HrCompanyCs> companyCsList =  create.selectFrom(HR_COMPANY_CS)
                .where(HR_COMPANY_CS.COMPANY_ID.eq(company_id))
                .fetchInto(HrCompanyCs.class);
        if(companyCsList != null && companyCsList.size()>0){
            return companyCsList.get(0);
        }else{
            return  null;
        }
    }

}
