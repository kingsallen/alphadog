package com.moseeker.baseorm.dao.jobdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.jobdb.tables.JobPositionShareTplConf;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionShareTplConfRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionShareTplConfDO;

/**
* @author xxx
* JobPositionShareTplConfDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class JobPositionShareTplConfDao extends StructDaoImpl<JobPositionShareTplConfDO, JobPositionShareTplConfRecord, JobPositionShareTplConf> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = JobPositionShareTplConf.JOB_POSITION_SHARE_TPL_CONF;
   }
}
