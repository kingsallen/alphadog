package test.com.moseeker.application;

import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;
import com.moseeker.thrift.gen.application.service.JobApplicationServices;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.JobResumeOther;
import com.moseeker.thrift.gen.common.struct.Response;

/**
 * 申请客户端 测试类
 *
 * Created by zzh on 16/5/24.
 */
public class JobApplicataionServicesImplTest {

    public static void main(String[] args) {

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setConnectstr("127.0.0.1:2181");
        registryConfig.setNamespace("services");

        String iface = JobApplicationServices.Iface.class.getName();
        ClientConfig<JobApplicationServices.Iface> clientConfig = new ClientConfig<JobApplicationServices.Iface>();
        clientConfig.setService("com.moseeker.thrift.gen.application.service.JobApplicationServices");
        clientConfig.setIface(iface);

        JobApplicationServices.Iface applicationService = null;

        try {
            applicationService = clientConfig.createProxy(registryConfig);

            // 添加申请
            Response getJobApplication = applicationService.postApplication(getJobApplication());

            System.out.println(getJobApplication);

            // 添加申请副本
            Response getJobResumeOther = applicationService.postJobResumeOther(getJobResumeOther());

            System.out.println(getJobResumeOther);

            // 是否申请过该职位
            Response getApplicationByUserIdAndPositionId = applicationService.getApplicationByUserIdAndPositionId(2447, 123, 1);

            System.out.println(getApplicationByUserIdAndPositionId);

            Response getApplicationByUserIdAndPositionId1 = applicationService.getApplicationByUserIdAndPositionId(214, 123, 2);

            System.out.println(getApplicationByUserIdAndPositionId1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JobApplication getJobApplication(){
        JobApplication application = new JobApplication();
        application.setWechat_id(1);
        application.setApp_tpl_id(1);
        application.setApplier_id(1);
        application.setCompany_id(1);
        application.setPosition_id(1);
        return application;
    }

    public static JobResumeOther getJobResumeOther(){
        JobResumeOther jobResumeOther = new JobResumeOther();
        jobResumeOther.setApp_id(1);
        jobResumeOther.setOther("{'other':{'basic':{'name':'2'}}}");
        return jobResumeOther;
    }
}

