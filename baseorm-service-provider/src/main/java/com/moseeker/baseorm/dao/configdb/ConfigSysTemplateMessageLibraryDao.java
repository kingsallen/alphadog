package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysTemplateMessageLibrary;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysTemplateMessageLibraryRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysTemplateMessageLibraryDO;
import java.util.List;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigSysTemplateMessageLibraryDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigSysTemplateMessageLibraryDao extends JooqCrudImpl<ConfigSysTemplateMessageLibraryDO, ConfigSysTemplateMessageLibraryRecord> {

    public ConfigSysTemplateMessageLibraryDao() {
        super(ConfigSysTemplateMessageLibrary.CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY, ConfigSysTemplateMessageLibraryDO.class);
    }

    public ConfigSysTemplateMessageLibraryDao(TableImpl<ConfigSysTemplateMessageLibraryRecord> table, Class<ConfigSysTemplateMessageLibraryDO> configSysTemplateMessageLibraryDOClass) {
        super(table, configSysTemplateMessageLibraryDOClass);
    }

    public List<ConfigSysTemplateMessageLibraryDO> getConfigSysTemplateMessageLibraryDOByidListAndDisable(List<Integer> idList, int disable){
        List<ConfigSysTemplateMessageLibraryDO> result= create.selectFrom(ConfigSysTemplateMessageLibrary.CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY)
                .where(ConfigSysTemplateMessageLibrary.CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.ID.in(idList))
                .and(ConfigSysTemplateMessageLibrary.CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.DISABLE.eq(disable))
                .fetchInto(ConfigSysTemplateMessageLibraryDO.class);
        return result;
    }

    public ConfigSysTemplateMessageLibraryRecord getByTemplateIdAndTitle(String templateIdShort, String title) {

        return create.selectFrom(ConfigSysTemplateMessageLibrary.CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY)
                .where(ConfigSysTemplateMessageLibrary.CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.TEMPLATE_ID_SHORT.eq(templateIdShort))
                .and(ConfigSysTemplateMessageLibrary.CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.TITLE.eq(title))
                .limit(1)
                .fetchOne();
    }
}
