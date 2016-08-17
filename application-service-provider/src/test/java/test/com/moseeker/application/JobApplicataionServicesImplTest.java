package test.com.moseeker.application;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.application.service.JobApplicationServices;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.JobResumeOther;

/**
 * 申请客户端 测试类
 *
 * Created by zzh on 16/5/24.
 */
public class JobApplicataionServicesImplTest {

    public static void main(String[] args) {

        JobApplicationServices.Iface applicationService = null;

        try {
            applicationService = ServiceManager.SERVICEMANAGER.getService(JobApplicationServices.Iface.class);

            // 清除一个公司一个人申请次数限制的redis key 给sysplat用
//            applicationService.deleteRedisKeyApplicationCheckCount(1, 1);

//            // 添加申请
//            Response getJobApplication = applicationService.postApplication(getJobApplication());
//
//            System.out.println(getJobApplication);
//
//            System.out.println(applicationService.validateUserApplicationCheckCountAtCompany(1, 1));

//            // 添加申请副本
//            Response getJobResumeOther = applicationService.postJobResumeOther(getJobResumeOther());
//
//            System.out.println(getJobResumeOther);
//
//            // 是否申请过该职位
//            Response getApplicationByUserIdAndPositionId = applicationService.getApplicationByUserIdAndPositionId(2447, 123, 1);
//
//            System.out.println(getApplicationByUserIdAndPositionId);
//
//            Response getApplicationByUserIdAndPositionId1 = applicationService.getApplicationByUserIdAndPositionId(214, 123, 2);
//
//            System.out.println(getApplicationByUserIdAndPositionId1);
//            System.out.println(applicationService.putApplication(putJobApplication()));
//            System.out.println(applicationService.deleteApplication(44));
            System.out.println(applicationService.postApplication(getJobApplication()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static JobApplication getJobApplication(){
        JobApplication application = new JobApplication();
        application.setApplier_id(4);
        application.setCompany_id(2879);
        application.setPosition_id(2);
        return application;
    }

    public static JobApplication putJobApplication(){
        JobApplication application = new JobApplication();
        application.setId(2);
        application.setApplier_id(1);
        application.setCompany_id(1);
        application.setPosition_id(61144);
        application.setView_count(100);
        return application;
    }

    public static JobResumeOther getJobResumeOther(){
        JobResumeOther jobResumeOther = new JobResumeOther();
        jobResumeOther.setApp_id(1);
        jobResumeOther.setOther("{'other':{'basic':{'name':'2'}}}");
        return jobResumeOther;
    }
}

