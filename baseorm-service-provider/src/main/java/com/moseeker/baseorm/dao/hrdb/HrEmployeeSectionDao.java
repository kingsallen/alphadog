package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrEmployeeSection;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeeSectionRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeSectionDO;

/**
* @author xxx
* HrEmployeeSectionDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrEmployeeSectionDao extends StructDaoImpl<HrEmployeeSectionDO, HrEmployeeSectionRecord, HrEmployeeSection> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrEmployeeSection.HR_EMPLOYEE_SECTION;
   }
}
