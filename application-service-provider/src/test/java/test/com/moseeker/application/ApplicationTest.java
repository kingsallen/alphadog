//package test.com.moseeker.application;
//
//import org.apache.thrift.TException;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import com.moseeker.application.config.AppConfig;
//import com.moseeker.application.service.impl.JobApplicataionService;
//import com.moseeker.thrift.gen.application.struct.JobApplication;
//import com.moseeker.thrift.gen.application.struct.JobResumeOther;
//import com.moseeker.thrift.gen.common.struct.Response;
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes =AppConfig.class)
//@Transactional
//public class ApplicationTest {
//	@Autowired
//	private JobApplicataionService service;
//	//@Test
//	public void testSave() throws TException{
//		JobApplication app=new JobApplication();
//		app.setApp_tpl_id(1);
//		app.setApplier_id(222);
//		app.setApplier_name("ssss");
//		app.setApply_type(0);
//		app.setAts_status(9);
//		app.setCompany_id(1000);
//		app.setDisable(0);
//		app.setIs_viewed(0);
//		app.setReward(100);
//		app.setEmail_status(0);
//		app.setL_application_id(200);
//		app.setView_count(1000);
//		app.setWechat_id(234);
//		app.setPosition_id(111872);
//		app.setRecommender_user_id(4021);
//		Response res=service.postApplication(app);
//		System.out.println(res);
//	}
//	//@Test
//	public void testSaveNotApply() throws TException{
//		JobApplication app=new JobApplication();
//		app.setApp_tpl_id(1);
//		app.setApplier_id(222);
//		app.setApplier_name("ssss");
//		app.setApply_type(0);
//		app.setAts_status(9);
//		app.setCompany_id(1000);
//		app.setDisable(0);
//		app.setIs_viewed(0);
//		app.setReward(100);
//		app.setEmail_status(0);
//		app.setL_application_id(200);
//		app.setView_count(1000);
//		app.setWechat_id(234);
//		app.setPosition_id(111872);
//		app.setRecommender_user_id(4021);
//		Response res=service.postApplicationIfNotApply(app);
//		System.out.println(res);
//	}
//	//@Test
//	public void testUpdateApp() throws TException{
//		JobApplication app=new JobApplication();
//		app.setApp_tpl_id(2);
//		app.setApplier_id(333);
//		app.setApplier_name("niminsa");
//		app.setId(204499);
//		Response res=service.putApplication(app);
//		System.out.println(res);
//	}
//	//@Test
//	public void testDeleteApp() throws TException{
//		Response res=service.deleteApplication(204499);
//		System.out.println(res);
//	}
//	//@Test
//	public void testPostJobResumeOther() throws TException{
//		JobResumeOther resume=new JobResumeOther();
//		resume.setApp_id(204499);
//		resume.setOther("{'111':2222}");
//		Response res=service.postJobResumeOther(resume);
//		System.out.println(res);
//	}
//	//@Test
//	public void testGetApplicationByUserIdAndPositionId() throws TException{
//		Response res=service.getApplicationByUserIdAndPositionId(222, 98898, 1000);
//		System.out.println(res);
//	}
//
//}
