package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.db.configdb.tables.ConfigSmsTemplate;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSmsTemplateRecord;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static org.jooq.impl.DSL.using;

/**
* @author xxx
* ConfigSysTemplateMessageLibraryDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigSmsTemplateDao extends com.moseeker.baseorm.db.configdb.tables.daos.ConfigSmsTemplateDao {

    @Autowired
    public ConfigSmsTemplateDao(Configuration configuration) {
        super(configuration);
    }


    public ConfigSmsTemplateRecord getConfigSmsTemplateByCodeAndChannel(String code){
        return using(configuration())
                .selectFrom(ConfigSmsTemplate.CONFIG_SMS_TEMPLATE)
                .where(ConfigSmsTemplate.CONFIG_SMS_TEMPLATE.CODE.eq(code))
                .limit(1)
                .fetchOneInto(ConfigSmsTemplateRecord.class);
    }

}
