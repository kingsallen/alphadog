package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrChildCompany;
import com.moseeker.baseorm.db.hrdb.tables.records.HrChildCompanyRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrChildCompanyDO;

/**
* @author xxx
* HrChildCompanyDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrChildCompanyDao extends StructDaoImpl<HrChildCompanyDO, HrChildCompanyRecord, HrChildCompany> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrChildCompany.HR_CHILD_COMPANY;
   }
}
