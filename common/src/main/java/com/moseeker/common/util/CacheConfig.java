package com.moseeker.common.util;

import java.sql.Connection;
import java.sql.DriverManager;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.moseeker.common.cache.db.configdb.Tables;



public class CacheConfig {
	private String pattern;
	private String json_extraparams=null;
	private int ttl=0;

	protected CacheConfig(int appid, String key_identifier) throws Exception {
		try (Connection conn = DriverManager.getConnection(Constant.CACHE_URL, Constant.CACHE_USERNAME,
				Constant.CACHE_PASSWORD)) {
			DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
			Record row = create.select()
					.from(Tables.CACHECONFIG_REDISKEY)
					.where(Tables.CACHECONFIG_REDISKEY.PROJECT_APPID.equal(appid))
					.and(Tables.CACHECONFIG_REDISKEY.KEY_IDENTIFIER
							.equal(key_identifier)).fetchAny();
			if ( row != null ){
				this.pattern = row.getValue(Tables.CACHECONFIG_REDISKEY.PATTERN);
				this.json_extraparams = row.getValue(Tables.CACHECONFIG_REDISKEY.JSON_EXTRAPARAMS);
				this.ttl = row.getValue(Tables.CACHECONFIG_REDISKEY.TTL);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("can't get cache config");
		}
	}

	public String getPattern() {
		return pattern;
	}

	public String getJson_extraparams() {
		return json_extraparams;
	}

	public int getTtl() {
		return ttl;
	}
	
	public static void main(String[] args) {
		
		try {
			CacheConfig cfg = new CacheConfig(0, "DEFAULT");
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
