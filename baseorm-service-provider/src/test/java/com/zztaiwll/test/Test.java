package com.zztaiwll.test;

import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.thrift.TException;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccount;
import com.moseeker.baseorm.service.JobPositionService;
import com.moseeker.baseorm.service.ThirdpartAccountService;
import com.moseeker.baseorm.service.Impl.ThirdpartAccountServiceImpl;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartPosition;
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
        	Result<Record> result = create.select().from(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT).fetch();
        	for (Record r : result) {
        	    Integer id = r.getValue(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.ID);
        	    String firstName = r.getValue(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.USERNAME);
        	    //int lastName = r.getValue(HrThirdPartAccount.HR_THIRD_PART_ACCOUNT.CHANNEL);

        	    //System.out.println("ID: " + id + " first name: " + firstName + " last name: " + lastName);
        	}
        } catch(Exception e){
        	e.printStackTrace();
        	
        }
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
		ThirdpartAccountService service=context.getBean(ThirdpartAccountServiceImpl.class);
		System.out.println(service.add_ThirdPartAccount(account));
	
		
	}
	@org.junit.Test
	public void updateAccount(){
		com.moseeker.thrift.gen.thirdpart.struct.ThirdPartAccount account=new com.moseeker.thrift.gen.thirdpart.struct.ThirdPartAccount();
		account.setBinding(1);
		account.setChannel(2);
		account.setCompany_id(2);
		account.setName("11sss");
		AnnotationConfigApplicationContext context = initSpring();
		ThirdpartAccountService service=context.getBean(ThirdpartAccountServiceImpl.class);
		System.out.println(service.update_ThirdPartAccount(account));
	}
	@org.junit.Test
	public void addPosition(){
		List<ThirdPartPosition> list=new ArrayList<ThirdPartPosition>();
		ThirdPartPosition position=new ThirdPartPosition();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String time=format.format(new Date());
		position.setChannel(1);
		position.setIs_refresh(0);
		position.setIs_synchronization(2);
		position.setOccupation("haskhao");
		position.setPosition_id(2);
		position.setSync_time(time);
		list.add(position);
		ThirdPartPosition position1=new ThirdPartPosition();
		position1.setChannel(2);
		position1.setIs_refresh(0);
		position1.setIs_synchronization(2);
		position1.setOccupation("haskhaowxsax");
		position1.setPosition_id(2);
		position1.setSync_time(time);
		list.add(position1);
		ThirdPartPosition position2=new ThirdPartPosition();
		position2.setChannel(3);
		position2.setIs_refresh(0);
		position2.setIs_synchronization(2);
		position2.setOccupation("haskhao");
		position2.setPosition_id(2);
		position2.setSync_time(time);
		list.add(position2);
		AnnotationConfigApplicationContext context = initSpring();
		ThirdpartAccountService service=context.getBean(ThirdpartAccountServiceImpl.class);
		System.out.println(service.add_ThirdPartPositions(list));
		
	}
	@org.junit.Test
	public void updatePosition(){
		List<ThirdPartPosition> list=new ArrayList<ThirdPartPosition>();
		ThirdPartPosition position=new ThirdPartPosition();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String time=format.format(new Date());
		position.setChannel(1);
		position.setIs_synchronization(2);
		position.setOccupation("haskhao1111");
		position.setPosition_id(2);
		position.setSync_time(time);
		list.add(position);
		ThirdPartPosition position1=new ThirdPartPosition();
		position1.setChannel(2);
		position1.setIs_synchronization(2);
		position1.setPosition_id(2);
		position1.setSync_time(time);
		list.add(position1);
		ThirdPartPosition position2=new ThirdPartPosition();
		position2.setChannel(3);
		position2.setIs_synchronization(2);
		position2.setPosition_id(2);
		position2.setSync_time(time);
		list.add(position2);
		AnnotationConfigApplicationContext context = initSpring();
		ThirdpartAccountService service=context.getBean(ThirdpartAccountServiceImpl.class);
		System.out.println(service.update_ThirdPartPositions(list));
	}
	@org.junit.Test
	public void getAccountByCompanyId(){
		AnnotationConfigApplicationContext context = initSpring();
		ThirdpartAccountService service=context.getBean(ThirdpartAccountServiceImpl.class);
		System.out.println(service.getSingleAccountByCompanyId(2));
	}
	@org.junit.Test
	public void getPositionById(){
		AnnotationConfigApplicationContext context = initSpring();
		JobPositionService service=context.getBean(JobPositionService.class);
		for(int i=0;i<5;i++){
			System.out.println(service.getJobPositionById(2));
		}
		
		
		
	}

}
