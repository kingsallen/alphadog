package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrWxModule;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxModuleRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxModuleDO;

/**
* @author xxx
* HrWxModuleDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxModuleDao extends StructDaoImpl<HrWxModuleDO, HrWxModuleRecord, HrWxModule> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrWxModule.HR_WX_MODULE;
   }
}
