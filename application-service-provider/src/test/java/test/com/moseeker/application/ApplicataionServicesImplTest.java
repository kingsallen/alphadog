package test.com.moseeker.application;

import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;
import com.moseeker.thrift.gen.application.service.ApplicationServices;
import com.moseeker.thrift.gen.application.struct.Application;
import com.moseeker.thrift.gen.common.struct.Response;

/**
 * Created by zzh on 16/5/24.
 */
public class ApplicataionServicesImplTest {

    public static void main(String[] args) {

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setConnectstr("127.0.0.1:2181");
        registryConfig.setNamespace("services");

        String iface = ApplicationServices.Iface.class.getName();
        ClientConfig<ApplicationServices.Iface> clientConfig = new ClientConfig<ApplicationServices.Iface>();
        clientConfig.setService("com.moseeker.thrift.gen.application.service.ApplicationServices");
        clientConfig.setIface(iface);

        ApplicationServices.Iface applicationService = null;

        try {
            applicationService = clientConfig.createProxy(registryConfig);

            Response response = applicationService.postResource(getApplication());

            System.out.println(response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Application getApplication(){
        Application application = new Application();
        application.setWechat_id(1);
        application.setApp_tpl_id(1);
        application.setApplier_id(1);
        application.setCompany_id(1);
        application.setPosition_id(1);
        return application;
    }
}

