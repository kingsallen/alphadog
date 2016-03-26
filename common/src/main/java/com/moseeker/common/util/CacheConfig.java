package com.moseeker.common.util;


import java.sql.Connection;
import java.sql.DriverManager;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import static com.moseeker.db.configdb.tables.CacheconfigRediskey.CACHECONFIG_REDISKEY;

public class CacheConfig {
	private String pattern;
	private String json_extraparams=null;
	private int ttl=0;

	private static String userName = "www";
	private static String password = "moseeker.com";
	private static String url = "jdbc:mysql://192.168.31.66:3306/";

	protected CacheConfig(int appid, String key_identifier) throws Exception {
		try (Connection conn = DriverManager.getConnection(url, userName,
				password)) {
			DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
			Record row = create.select()
					.from(CACHECONFIG_REDISKEY)
					.where(CACHECONFIG_REDISKEY.PROJECT_APPID.equal(appid))
					.and(CACHECONFIG_REDISKEY.KEY_IDENTIFIER
							.equal(key_identifier)).fetchAny();
			if ( row != null ){
				this.pattern = row.getValue(CACHECONFIG_REDISKEY.PATTERN);
				this.json_extraparams = row.getValue(CACHECONFIG_REDISKEY.JSON_EXTRAPARAMS);
				this.ttl = row.getValue(CACHECONFIG_REDISKEY.TTL);
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
