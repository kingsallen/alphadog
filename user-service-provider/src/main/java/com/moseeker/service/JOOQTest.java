package com.moseeker.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.Test;

import com.moseeker.jooqhelper.tables.Author;

public class JOOQTest {

	private static String userName = "wjf";
	private static String password = "wjf";
	private static String url = "jdbc:mysql://localhost:3306/";
	
	@Test
	public void selectTest() {
		
        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            Result<Record> result = create.select().from(Author.AUTHOR).fetch();

            for (Record r : result) {
                Integer id = r.getValue(Author.AUTHOR.ID);
                String firstName = r.getValue(Author.AUTHOR.FIRST_NAME);
                String lastName = r.getValue(Author.AUTHOR.LAST_NAME);

                System.out.println("ID: " + id + " first name: " + firstName + " last name: " + lastName);
            }
        } 

        // For the sake of this tutorial, let's keep exception handling simple
        catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test
	public void java8Test() {
		try (Connection c = DriverManager.getConnection(url, userName, password)) {
		    String sql = "select cd, description from jooq.language ";

		    DSL.using(c, SQLDialect.MYSQL)
		       .fetch(sql)

		       // We can use lambda expressions to map jOOQ Records
		       /*.map(rs -> new Schema(
		           rs.getValue("SCHEMA_NAME", String.class),
		           rs.getValue("IS_DEFAULT", boolean.class)
		       ))*/

		       // ... and then profit from the new Collection methods
		       .forEach(System.out::println);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
