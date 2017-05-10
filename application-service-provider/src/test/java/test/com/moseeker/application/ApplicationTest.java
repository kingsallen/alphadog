package test.com.moseeker.application;

import org.apache.thrift.TException;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.application.service.impl.JobApplicataionService;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.JobResumeOther;
import com.moseeker.thrift.gen.common.struct.Response;

public class ApplicationTest {
//   private static AnnotationConfigApplicationContext initSpring() {
//        AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
//        acac.scan("com.moseeker.application");
//        acac.scan("com.moseeker.common.aop.iface");
//        acac.scan("com.moseeker.baseorm");
//        acac.refresh();
//        return acac;
//    }
//	@Test
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
//		app.setPosition_id(98898);
//		app.setRecommender_user_id(4021);
//		AnnotationConfigApplicationContext acac=initSpring();
//		JobApplicataionService service=acac.getBean(JobApplicataionService.class);
//		Response res=service.postApplication(app);
//		System.out.println(res);
//	}
//	@Test
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
//		app.setPosition_id(98898);
//		app.setRecommender_user_id(4021);
//		AnnotationConfigApplicationContext acac=initSpring();
//		JobApplicataionService service=acac.getBean(JobApplicataionService.class);
//		Response res=service.postApplicationIfNotApply(app);
//		System.out.println(res);
//	}
//	@Test
//	public void testUpdateApp() throws TException{
//		JobApplication app=new JobApplication();
//		app.setApp_tpl_id(2);
//		app.setApplier_id(333);
//		app.setApplier_name("niminsa");
//		app.setId(225291);
//		AnnotationConfigApplicationContext acac=initSpring();
//		JobApplicataionService service=acac.getBean(JobApplicataionService.class);
//		Response res=service.putApplication(app);
//		System.out.println(res);
//	}
//	@Test
//	public void testDeleteApp() throws TException{
//		AnnotationConfigApplicationContext acac=initSpring();
//		JobApplicataionService service=acac.getBean(JobApplicataionService.class);
//		Response res=service.deleteApplication(225291);
//		System.out.println(res);
//	}
//	@Test
//	public void testPostJobResumeOther() throws TException{
//		AnnotationConfigApplicationContext acac=initSpring();
//		JobResumeOther resume=new JobResumeOther();
//		resume.setApp_id(225290);
//		resume.setOther("{'111':2222}");
//		JobApplicataionService service=acac.getBean(JobApplicataionService.class);
//		Response res=service.postJobResumeOther(resume);
//		System.out.println(res);
//	}
//	@Test
//	public void testGetApplicationByUserIdAndPositionId() throws TException{
//		AnnotationConfigApplicationContext acac=initSpring();
//		JobApplicataionService service=acac.getBean(JobApplicataionService.class);
//		Response res=service.getApplicationByUserIdAndPositionId(222, 98898, 1000);
//		System.out.println(res);
//	}
}
