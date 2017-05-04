package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigPositionKenexaRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigPositionKenexaDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigPositionKenexaDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigPositionKenexaDao extends JooqCrudImpl<ConfigPositionKenexaDO, ConfigPositionKenexaRecord> {


    public ConfigPositionKenexaDao(TableImpl<ConfigPositionKenexaRecord> table, Class<ConfigPositionKenexaDO> configPositionKenexaDOClass) {
        super(table, configPositionKenexaDOClass);
    }
}
