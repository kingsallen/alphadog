package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigCacheconfigRediskey;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigCacheconfigRediskeyRecord;
import com.moseeker.baseorm.redis.RedisConfigRedisKey;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.RedisException;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigCacheconfigRediskeyDO;
import java.util.ArrayList;
import java.util.List;
import org.jooq.Record;
import org.jooq.impl.TableImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigCacheconfigRediskeyDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigCacheconfigRediskeyDao extends JooqCrudImpl<ConfigCacheconfigRediskeyDO, ConfigCacheconfigRediskeyRecord> {

    private static final Logger log = LoggerFactory.getLogger(ConfigCacheconfigRediskeyDao.class);

    public ConfigCacheconfigRediskeyDao() {
        super(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY, ConfigCacheconfigRediskeyDO.class);
    }

    public ConfigCacheconfigRediskeyDao(TableImpl<ConfigCacheconfigRediskeyRecord> table, Class<ConfigCacheconfigRediskeyDO> configCacheconfigRediskeyDOClass) {
        super(table, configCacheconfigRediskeyDOClass);
    }

    /**
     * 根据项目编号和标识符查询缓存编号信息
     *
     * @param appId
     *            项目编号
     * @param keyIdentifier
     *            标识符
     * @return CacheConfigRedisKey
     *         {@see com.moseeker.common.cache.lru.CacheConfigRedisKey}
     */
    public RedisConfigRedisKey readFromDB(int appId, String keyIdentifier, byte configType) throws RedisException {
        RedisConfigRedisKey redisKey = null;
        try {
            Record row = create.select().from(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY)
                    .where(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.PROJECT_APPID.equal(appId))
                    .and(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.TYPE.equal(configType))
                    .and(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.KEY_IDENTIFIER.equal(keyIdentifier)).fetchAny();
            if (row != null) {
                redisKey = new RedisConfigRedisKey();
                redisKey.setAppId(row.getValue(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.PROJECT_APPID));
                redisKey.setKeyIdentifier(row.getValue(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.KEY_IDENTIFIER));
                redisKey.setDesc(row.getValue(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.DESC));
                redisKey.setPattern(row.getValue(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.PATTERN));
                redisKey.setTtl(row.getValue(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.TTL));
            }

        } catch (Exception e) {
            log.error("error", e);
            throw new RedisException(e.getMessage(), Constant.REDIS_CONNECT_ERROR_APPID, ConfigCacheconfigRediskeyDao.class.getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(appId, keyIdentifier, e.getMessage());
        }
        return redisKey;
    }

    /**
     * 查询缓存标识符关键词。至多查找200条记录
     *
     * @return List<CacheConfigRedisKey>
     *         {@see com.moseeker.common.cache.lru.CacheConfigRedisKey }
     */
    public List<RedisConfigRedisKey> readAllConfigFromDB(byte configType) throws RedisException {
        List<RedisConfigRedisKey> redisKeys = new ArrayList<>();
        try {
            create.select().from(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY).where(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.TYPE.equal(configType))
                    .fetch().forEach((row) -> {
                RedisConfigRedisKey redisKey = new RedisConfigRedisKey();
                redisKey.setAppId(row.getValue(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.PROJECT_APPID));
                redisKey.setKeyIdentifier(row.getValue(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.KEY_IDENTIFIER));
                redisKey.setDesc(row.getValue(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.DESC));
                redisKey.setPattern(row.getValue(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.PATTERN));
                redisKey.setTtl(row.getValue(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.TTL));
                redisKeys.add(redisKey);
            });
        } catch (Exception e) {
            log.error("error", e);
            throw new RedisException(e.getMessage(), Constant.REDIS_CONNECT_ERROR_APPID, ConfigCacheconfigRediskeyDao.class.getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, e.getMessage());
        }
        return redisKeys;
    }
}
