package com.moseeker.common.cache;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.moseeker.common.cache.db.configdb.Tables;
import com.moseeker.common.cache.lru.CacheConfigRedisKey;
import com.moseeker.common.util.Constant;

/**
 * 
 * 数据库管理 
 * <p>Company: MoSeeker</P>  
 * <p>date: Mar 29, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version Beta
 */
public class DbManager {
	
	private DbManager() {}
	
	/**
	 * 根据项目编号和标识符查询缓存编号信息
	 * @param appId 项目编号
	 * @param keyIdentifier 标识符
	 * @return CacheConfigRedisKey {@see com.moseeker.common.cache.lru.CacheConfigRedisKey}
	 */
	public static CacheConfigRedisKey readFromDB(int appId, String keyIdentifier) {
		CacheConfigRedisKey redisKey = new CacheConfigRedisKey();
		try (Connection conn = DriverManager.getConnection(Constant.CACHE_URL, Constant.CACHE_USERNAME,
				Constant.CACHE_PASSWORD)) {
			DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
			Record row = create.select()
					.from(Tables.CACHECONFIG_REDISKEY)
					.where(Tables.CACHECONFIG_REDISKEY.PROJECT_APPID.equal(appId))
					.and(Tables.CACHECONFIG_REDISKEY.KEY_IDENTIFIER
							.equal(keyIdentifier)).fetchAny();
			if ( row != null ){
				redisKey.setAppId(row.getValue(Tables.CACHECONFIG_REDISKEY.PROJECT_APPID));
				redisKey.setKeyIdentifier(row.getValue(Tables.CACHECONFIG_REDISKEY.KEY_IDENTIFIER));
				redisKey.setDesc(row.getValue(Tables.CACHECONFIG_REDISKEY.DESC));
				redisKey.setPattern(row.getValue(Tables.CACHECONFIG_REDISKEY.PATTERN));
				redisKey.setTtl(row.getValue(Tables.CACHECONFIG_REDISKEY.TTL));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return redisKey;
	}
	
	/**
	 * 查询缓存标识符关键词。至多查找200条记录
	 * @return List<CacheConfigRedisKey> {@see com.moseeker.common.cache.lru.CacheConfigRedisKey }
	 */
	public static List<CacheConfigRedisKey> readAllConfigFromDB() {
		List<CacheConfigRedisKey> redisKeys = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(Constant.CACHE_URL, Constant.CACHE_USERNAME,
				Constant.CACHE_PASSWORD)) {
			DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
			create.select()
					.from(Tables.CACHECONFIG_REDISKEY).limit(0, 200).fetch()
					.forEach((row)->{
						CacheConfigRedisKey redisKey = new CacheConfigRedisKey();
						redisKey.setAppId(row.getValue(Tables.CACHECONFIG_REDISKEY.PROJECT_APPID));
						redisKey.setKeyIdentifier(row.getValue(Tables.CACHECONFIG_REDISKEY.KEY_IDENTIFIER));
						redisKey.setDesc(row.getValue(Tables.CACHECONFIG_REDISKEY.DESC));
						redisKey.setPattern(row.getValue(Tables.CACHECONFIG_REDISKEY.PATTERN));
						redisKey.setTtl(row.getValue(Tables.CACHECONFIG_REDISKEY.TTL));
						redisKeys.add(redisKey);
					});;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return redisKeys;
	}
}

