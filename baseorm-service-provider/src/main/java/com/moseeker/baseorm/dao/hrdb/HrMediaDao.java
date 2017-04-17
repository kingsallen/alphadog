package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrMedia;
import com.moseeker.baseorm.db.hrdb.tables.records.HrMediaRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrMediaDO;

/**
* @author xxx
* HrMediaDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrMediaDao extends StructDaoImpl<HrMediaDO, HrMediaRecord, HrMedia> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrMedia.HR_MEDIA;
   }
}
