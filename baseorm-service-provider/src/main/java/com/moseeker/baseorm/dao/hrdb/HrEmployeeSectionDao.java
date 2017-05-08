package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrEmployeeSection;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeeSectionRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeSectionDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrEmployeeSectionDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrEmployeeSectionDao extends JooqCrudImpl<HrEmployeeSectionDO, HrEmployeeSectionRecord> {

    public HrEmployeeSectionDao() {
        super(HrEmployeeSection.HR_EMPLOYEE_SECTION, HrEmployeeSectionDO.class);
    }

    public HrEmployeeSectionDao(TableImpl<HrEmployeeSectionRecord> table, Class<HrEmployeeSectionDO> hrEmployeeSectionDOClass) {
        super(table, hrEmployeeSectionDOClass);
    }
}
