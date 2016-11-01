package com.zztaiwll.test;

import java.sql.DriverManager;
import java.util.Date;

import org.apache.thrift.TException;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.baseorm.db.hrdb.tables.ThirdPartAccount;
import com.moseeker.baseorm.service.ThirdpartAccountService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.mysql.jdbc.Connection;


public class Test {
	
	@org.junit.Test
	public void test() throws Exception{
		String userName = "www";
        String password = "moseeker.com";
        String url = "jdbc:mysql://192.168.31.66:3306/hrdb";
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection conn = (Connection) DriverManager.getConnection(url, userName, password)) {
        	DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
        	Result<Record> result = create.select().from(ThirdPartAccount.THIRD_PART_ACCOUNT).fetch();
        	for (Record r : result) {
        	    Integer id = r.getValue(ThirdPartAccount.THIRD_PART_ACCOUNT.ID);
        	    String firstName = r.getValue(ThirdPartAccount.THIRD_PART_ACCOUNT.NAME);
        	    int lastName = r.getValue(ThirdPartAccount.THIRD_PART_ACCOUNT.CHANNEL);

        	    System.out.println("ID: " + id + " first name: " + firstName + " last name: " + lastName);
        	}
        } catch(Exception e){
        	e.printStackTrace();
        	
        }
	}
	@org.junit.Test
	public void test1() throws Exception{
		CommonQuery query=new CommonQuery();
		query.setPer_page(10);
		AnnotationConfigApplicationContext context = initSpring();
		ThirdpartAccountService service=context.getBean(ThirdpartAccountService.class);
		System.out.println(service.getAllCount(query));
		
	}
	public static AnnotationConfigApplicationContext initSpring() {
		AnnotationConfigApplicationContext annConfig = new AnnotationConfigApplicationContext();
		annConfig.scan("com.moseeker.baseorm");
		annConfig.refresh();
		return annConfig;
	}
	@org.junit.Test
	/*
	 * 测试添加数据接口，确认无误
	 */
	public void testAddAccount() throws TException{
		com.moseeker.thrift.gen.thirdpart.struct.ThirdPartAccount account=new com.moseeker.thrift.gen.thirdpart.struct.ThirdPartAccount();
		account.setBinding(2);
		account.setChannel(2);
		account.setCompany_id(2);
		account.setMembername("22");
		account.setName("222");
		account.setPassword("2222");
		account.setRemain_num(2);
		account.setUsername("222");
		account.setSync_time(new Date()+"");
		AnnotationConfigApplicationContext context = initSpring();
		ThirdpartAccountService service=context.getBean(ThirdpartAccountService.class);
		System.out.println(service.addThirdPartAccount(account));
	
		
	}
}
