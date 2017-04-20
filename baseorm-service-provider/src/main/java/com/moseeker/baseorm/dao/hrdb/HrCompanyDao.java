package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;

/**
* @author xxx
* HrCompanyDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrCompanyDao extends StructDaoImpl<HrCompanyDO, HrCompanyRecord, HrCompany> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrCompany.HR_COMPANY;
   }
}
