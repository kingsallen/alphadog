package com.moseeker.baseorm.dao.configdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.configdb.tables.ConfigPositionKenexa;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigPositionKenexaRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigPositionKenexaDO;

/**
* @author xxx
* ConfigPositionKenexaDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigPositionKenexaDao extends StructDaoImpl<ConfigPositionKenexaDO, ConfigPositionKenexaRecord, ConfigPositionKenexa> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ConfigPositionKenexa.CONFIG_POSITION_KENEXA;
   }
}
