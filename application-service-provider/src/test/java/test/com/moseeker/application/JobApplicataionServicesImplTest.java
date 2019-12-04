package test.com.moseeker.application;

import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;
import com.moseeker.thrift.gen.application.service.JobApplicationServices;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.JobResumeOther;

/**
 * 申请客户端 测试类
 * <p>
 * Created by zzh on 16/5/24.
 */
public class JobApplicataionServicesImplTest {

    public static void main(String[] args) {

        try {
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setConnectstr("127.0.0.1:2181");
            registryConfig.setNamespace("services");

            String iface = JobApplicationServices.Iface.class.getName();
            ClientConfig<JobApplicationServices.Iface> clientConfig = new ClientConfig<JobApplicationServices.Iface>();
            clientConfig.setService("/com.moseeker.thrift.gen.application.service.JobApplicationServices");
            clientConfig.setIface(iface);

            JobApplicationServices.Iface applicationService = null;

            try {
                applicationService = clientConfig.createProxy(registryConfig);
                System.out.println(applicationService);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JobApplication getJobApplication() {
        JobApplication application = new JobApplication();
        application.setApplier_id(4);
        application.setCompany_id(2879);
        application.setPosition_id(2);
        return application;
    }

    public static JobApplication putJobApplication() {
        JobApplication application = new JobApplication();
        application.setId(2);
        application.setApplier_id(1);
        application.setCompany_id(1);
        application.setPosition_id(61144);
        application.setView_count(100);
        return application;
    }

    public static JobResumeOther getJobResumeOther() {
        JobResumeOther jobResumeOther = new JobResumeOther();
        jobResumeOther.setApp_id(1);
        jobResumeOther.setOther("{'other':{'basic':{'name':'2'}}}");
        return jobResumeOther;
    }
}
