package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrResource;
import com.moseeker.baseorm.db.hrdb.tables.records.HrResourceRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrResourceDO;

/**
* @author xxx
* HrResourceDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrResourceDao extends StructDaoImpl<HrResourceDO, HrResourceRecord, HrResource> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrResource.HR_RESOURCE;
   }
}
