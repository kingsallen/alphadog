package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysCvTpl;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysCvTplRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysCvTplDO;
import java.util.ArrayList;
import java.util.List;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigSysCvTplDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigSysCvTplDao extends JooqCrudImpl<ConfigSysCvTplDO, ConfigSysCvTplRecord> {

    public ConfigSysCvTplDao() {
        super(ConfigSysCvTpl.CONFIG_SYS_CV_TPL, ConfigSysCvTplDO.class);
    }

    public ConfigSysCvTplDao(TableImpl<ConfigSysCvTplRecord> table, Class<ConfigSysCvTplDO> configSysCvTplDOClass) {
        super(table, configSysCvTplDOClass);
    }

    public List<ConfigSysCvTplDO> findAll(){
        List<ConfigSysCvTplDO> tplDOS = create.selectFrom(ConfigSysCvTpl.CONFIG_SYS_CV_TPL)
                .where(ConfigSysCvTpl.CONFIG_SYS_CV_TPL.DISABLE.eq(0)).orderBy(ConfigSysCvTpl.CONFIG_SYS_CV_TPL.PRIORITY)
                .fetchInto(ConfigSysCvTplDO.class);
        if(StringUtils.isEmptyList(tplDOS)){
            return new ArrayList<>();
        }else{
            return tplDOS;
        }
    }
}
