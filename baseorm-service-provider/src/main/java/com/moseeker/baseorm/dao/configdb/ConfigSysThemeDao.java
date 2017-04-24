package com.moseeker.baseorm.dao.configdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.configdb.tables.ConfigSysTheme;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysThemeRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysThemeDO;

/**
* @author xxx
* ConfigSysThemeDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigSysThemeDao extends StructDaoImpl<ConfigSysThemeDO, ConfigSysThemeRecord, ConfigSysTheme> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ConfigSysTheme.CONFIG_SYS_THEME;
   }
}
