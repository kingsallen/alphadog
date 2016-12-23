package moseeker.baseorm.dao.ProfileProcess;

import java.util.ArrayList;
import java.util.List;

import org.jooq.types.UInteger;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.baseorm.service.Impl.DictDaoServiceImpl;
import com.moseeker.baseorm.service.Impl.JobApplicationServiceImpl;
import com.moseeker.baseorm.service.Impl.PositionServiceImpl;

public class ProfileProcessTest {
	private JobApplicationServiceImpl jobApplication;
	public void init() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.moseeker.baseorm");
		context.refresh();
		jobApplication = context.getBean(JobApplicationServiceImpl.class);
	}
	@Test
	public void TestAuth(){
		init();
		List<Integer> appIds=new ArrayList<Integer>();
		appIds.add(64);
		appIds.add(65);
		appIds.add(66);
		appIds.add(67);
		int companyId=8;
		int progressStatus=13;
		System.out.println(jobApplication.processvalidation(appIds, companyId, progressStatus));
	}
}
