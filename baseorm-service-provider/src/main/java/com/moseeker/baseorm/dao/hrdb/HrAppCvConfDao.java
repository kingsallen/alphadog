package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrAppCvConf;
import com.moseeker.baseorm.db.hrdb.tables.records.HrAppCvConfRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrAppCvConfDO;

/**
* @author xxx
* HrAppCvConfDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrAppCvConfDao extends StructDaoImpl<HrAppCvConfDO, HrAppCvConfRecord, HrAppCvConf> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrAppCvConf.HR_APP_CV_CONF;
   }
   
}
