package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrEmployeePosition;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeePositionRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeePositionDO;

/**
* @author xxx
* HrEmployeePositionDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrEmployeePositionDao extends StructDaoImpl<HrEmployeePositionDO, HrEmployeePositionRecord, HrEmployeePosition> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrEmployeePosition.HR_EMPLOYEE_POSITION;
   }
}
