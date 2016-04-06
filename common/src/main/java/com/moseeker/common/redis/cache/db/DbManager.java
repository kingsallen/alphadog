package com.moseeker.common.redis.cache.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.common.dbconnection.DatabaseConnectionHelper;
import com.moseeker.common.redis.RedisConfigRedisKey;
import com.moseeker.common.redis.cache.db.configdb.Tables;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.Notification;

/**
 * 
 * 数据库管理
 * <p>
 * Company: MoSeeker
 * </P>
 * <p>
 * date: Mar 29, 2016
 * </p>
 * <p>
 * Email: wjf2255@gmail.com
 * </p>
 * 
 * @author wjf
 * @version Beta
 */
public class DbManager {
	
	Logger logger = LoggerFactory.getLogger(DbManager.class);

	private DbManager() {
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
	public static RedisConfigRedisKey readFromDB(int appId, String keyIdentifier, byte configType) {
		ConfigPropertiesUtil configUtil = ConfigPropertiesUtil.getInstance();
		RedisConfigRedisKey redisKey = new RedisConfigRedisKey();
		try (Connection conn = DriverManager.getConnection(configUtil.get("mycat.url", String.class),
				configUtil.get("mycat.userName", String.class), configUtil.get("mycat.password", String.class))) {
			DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
			Record row = create.select().from(Tables.CACHECONFIG_REDISKEY)
					.where(Tables.CACHECONFIG_REDISKEY.PROJECT_APPID.equal(appId))
					.and(Tables.CACHECONFIG_REDISKEY.TYPE.equal(configType))
					.and(Tables.CACHECONFIG_REDISKEY.KEY_IDENTIFIER.equal(keyIdentifier)).fetchAny();
			if (row != null) {
				redisKey.setAppId(row.getValue(Tables.CACHECONFIG_REDISKEY.PROJECT_APPID));
				redisKey.setKeyIdentifier(row.getValue(Tables.CACHECONFIG_REDISKEY.KEY_IDENTIFIER));
				redisKey.setDesc(row.getValue(Tables.CACHECONFIG_REDISKEY.DESC));
				redisKey.setPattern(row.getValue(Tables.CACHECONFIG_REDISKEY.PATTERN));
				redisKey.setTtl(row.getValue(Tables.CACHECONFIG_REDISKEY.TTL));
			}

		} catch (Exception e) {
			LoggerFactory.getLogger(DbManager.class).error("error", e);
			Notification.sendWarningWithHostName(appId, keyIdentifier, e.getMessage());
		} finally {
			//do nothing
		}
		return redisKey;
	}

	/**
	 * 查询缓存标识符关键词。至多查找200条记录
	 * 
	 * @return List<CacheConfigRedisKey>
	 *         {@see com.moseeker.common.cache.lru.CacheConfigRedisKey }
	 */
	public static List<RedisConfigRedisKey> readAllConfigFromDB(byte configType) {
		List<RedisConfigRedisKey> redisKeys = new ArrayList<>();
		DSLContext create;
		try {
			create = DatabaseConnectionHelper.getConnection().getJooqDSL();

			create.select().from(Tables.CACHECONFIG_REDISKEY).where(Tables.CACHECONFIG_REDISKEY.TYPE.equal(configType))
					.fetch().forEach((row) -> {
						RedisConfigRedisKey redisKey = new RedisConfigRedisKey();
						redisKey.setAppId(row.getValue(Tables.CACHECONFIG_REDISKEY.PROJECT_APPID));
						redisKey.setKeyIdentifier(row.getValue(Tables.CACHECONFIG_REDISKEY.KEY_IDENTIFIER));
						redisKey.setDesc(row.getValue(Tables.CACHECONFIG_REDISKEY.DESC));
						redisKey.setPattern(row.getValue(Tables.CACHECONFIG_REDISKEY.PATTERN));
						redisKey.setTtl(row.getValue(Tables.CACHECONFIG_REDISKEY.TTL));
						redisKeys.add(redisKey);
					});
		} catch (Exception e) {
			LoggerFactory.getLogger(DbManager.class).error("error", e);
			Notification.sendWarningWithHostName(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, e.getMessage());
		} finally {
			//do nothing
		}
		return redisKeys;
	}
}
