package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrCompanyConf;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyConfRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;

/**
* @author xxx
* HrCompanyConfDao 实现类 （groovy 生成）
* 2017-04-12
*/
@Repository
public class HrCompanyConfDao extends StructDaoImpl<HrCompanyConfDO, HrCompanyConfRecord, HrCompanyConf> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrCompanyConf.HR_COMPANY_CONF;
   }
}
