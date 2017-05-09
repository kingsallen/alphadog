package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigAdminnotificationChannel;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationChannelRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigAdminnotificationChannelDO;
import org.jooq.DSLContext;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
* @author xxx
* ConfigAdminnotificationChannelDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigAdminnotificationChannelDao extends JooqCrudImpl<ConfigAdminnotificationChannelDO, ConfigAdminnotificationChannelRecord> {


    public ConfigAdminnotificationChannelDao() {
        super(ConfigAdminnotificationChannel.CONFIG_ADMINNOTIFICATION_CHANNEL, ConfigAdminnotificationChannelDO.class);
    }

    public ConfigAdminnotificationChannelDao(TableImpl<ConfigAdminnotificationChannelRecord> table, Class<ConfigAdminnotificationChannelDO> configAdminnotificationChannelDOClass) {
        super(table, configAdminnotificationChannelDOClass);
    }


    /**
     * 获取发送渠道列表
     * @param eventId
     * @return
     */
    public List<String> getChannels(Integer eventId) {
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("event_id", eventId);
        return getRecords(query.buildQuery()).stream().map(m -> m.getChannel()).collect(Collectors.toList());
    }
}
