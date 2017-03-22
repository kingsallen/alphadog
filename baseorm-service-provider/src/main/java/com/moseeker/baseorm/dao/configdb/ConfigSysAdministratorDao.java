package com.moseeker.baseorm.dao.configdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.configdb.tables.ConfigSysAdministrator;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysAdministratorRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysAdministratorDO;

/**
* @author xxx
* ConfigSysAdministratorDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigSysAdministratorDao extends StructDaoImpl<ConfigSysAdministratorDO, ConfigSysAdministratorRecord, ConfigSysAdministrator> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ConfigSysAdministrator.CONFIG_SYS_ADMINISTRATOR;
   }
}
