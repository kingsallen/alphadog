package com.moseeker.thrift.service.impl;

import static com.moseeker.db.userdb.tables.Companyfollowers.COMPANYFOLLOWERS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;
import org.junit.Test;

import com.moseeker.thrift.gen.companyfollowers.Companyfollower;

public class JOOQTest {

	private static String userName = "wjf";
	private static String password = "wjf";
	private static String url = "jdbc:mysql://localhost:3306/";
	
	@Test
	public void selectTest() {
		
		Connection conn;
		try {
			conn = DriverManager.getConnection(url, userName, password);
			DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
			create.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*String sql = create.select(field("BOOK.TITLE"), field("AUTHOR.FIRST_NAME"), field("AUTHOR.LAST_NAME"))
                .from(table("BOOK"))
                .join(table("AUTHOR"))
                .on(field("BOOK.AUTHOR_ID").equal(field("AUTHOR.ID")))
                .where(field("BOOK.PUBLISHED_IN").equal(1948))
                .getSQL();*/
		
	}
}
