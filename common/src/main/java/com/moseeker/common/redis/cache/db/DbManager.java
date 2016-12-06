package com.moseeker.common.redis.cache.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.common.constants.Constant;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.exception.RedisException;
import com.moseeker.common.redis.RedisConfigRedisKey;
import com.moseeker.common.util.Notification;
import com.moseeker.db.configdb.Tables;

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
	public static RedisConfigRedisKey readFromDB(int appId, String keyIdentifier, byte configType) throws RedisException {
		RedisConfigRedisKey redisKey = null;
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			Record row = create.select().from(Tables.CONFIG_CACHECONFIG_REDISKEY)
					.where(Tables.CONFIG_CACHECONFIG_REDISKEY.PROJECT_APPID.equal(appId))
					.and(Tables.CONFIG_CACHECONFIG_REDISKEY.TYPE.equal(configType))
					.and(Tables.CONFIG_CACHECONFIG_REDISKEY.KEY_IDENTIFIER.equal(keyIdentifier)).fetchAny();
			if (row != null) {
				redisKey = new RedisConfigRedisKey();
				redisKey.setAppId(row.getValue(Tables.CONFIG_CACHECONFIG_REDISKEY.PROJECT_APPID));
				redisKey.setKeyIdentifier(row.getValue(Tables.CONFIG_CACHECONFIG_REDISKEY.KEY_IDENTIFIER));
				redisKey.setDesc(row.getValue(Tables.CONFIG_CACHECONFIG_REDISKEY.DESC));
				redisKey.setPattern(row.getValue(Tables.CONFIG_CACHECONFIG_REDISKEY.PATTERN));
				redisKey.setTtl(row.getValue(Tables.CONFIG_CACHECONFIG_REDISKEY.TTL));
			}

		} catch (Exception e) {
			LoggerFactory.getLogger(DbManager.class).error("error", e);
			throw new RedisException(e.getMessage(), Constant.REDIS_CONNECT_ERROR_APPID, DbManager.class.getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(appId, keyIdentifier, e.getMessage());
		} finally {
			try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				LoggerFactory.getLogger(DbManager.class).error(e.getMessage(), e);
			}
		}
		return redisKey;
	}

	/**
	 * 查询缓存标识符关键词。至多查找200条记录
	 * 
	 * @return List<CacheConfigRedisKey>
	 *         {@see com.moseeker.common.cache.lru.CacheConfigRedisKey }
	 */
	public static List<RedisConfigRedisKey> readAllConfigFromDB(byte configType) throws RedisException {
		List<RedisConfigRedisKey> redisKeys = new ArrayList<>();
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			create.select().from(Tables.CONFIG_CACHECONFIG_REDISKEY).where(Tables.CONFIG_CACHECONFIG_REDISKEY.TYPE.equal(configType))
					.fetch().forEach((row) -> {
						RedisConfigRedisKey redisKey = new RedisConfigRedisKey();
						redisKey.setAppId(row.getValue(Tables.CONFIG_CACHECONFIG_REDISKEY.PROJECT_APPID));
						redisKey.setKeyIdentifier(row.getValue(Tables.CONFIG_CACHECONFIG_REDISKEY.KEY_IDENTIFIER));
						redisKey.setDesc(row.getValue(Tables.CONFIG_CACHECONFIG_REDISKEY.DESC));
						redisKey.setPattern(row.getValue(Tables.CONFIG_CACHECONFIG_REDISKEY.PATTERN));
						redisKey.setTtl(row.getValue(Tables.CONFIG_CACHECONFIG_REDISKEY.TTL));
						redisKeys.add(redisKey);
					});
		} catch (Exception e) {
			LoggerFactory.getLogger(DbManager.class).error("error", e);
			throw new RedisException(e.getMessage(), Constant.REDIS_CONNECT_ERROR_APPID, DbManager.class.getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, e.getMessage());
		} finally {
			try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				LoggerFactory.getLogger(DbManager.class).error(e.getMessage(), e);
			}
		}
		return redisKeys;
	}
}
