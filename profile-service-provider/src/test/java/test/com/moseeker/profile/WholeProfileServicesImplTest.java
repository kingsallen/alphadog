package test.com.moseeker.profile;

import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices;

/**
 * Profile 整体接口 客户端 测试类
 *
 * Created by zzh on 16/7/5.
 */
public class WholeProfileServicesImplTest {

    public static void main(String[] args) {

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setConnectstr("127.0.0.1:2181");
        registryConfig.setNamespace("services");

        String iface = WholeProfileServices.Iface.class.getName();
        ClientConfig<WholeProfileServices.Iface> clientConfig = new ClientConfig<WholeProfileServices.Iface>();
        clientConfig.setService("com.moseeker.thrift.gen.profile.service.WholeProfileServices");
        clientConfig.setIface(iface);

        WholeProfileServices.Iface wholeProfileServices = null;

        try {
            wholeProfileServices = clientConfig.createProxy(registryConfig);

            System.out.println(wholeProfileServices.getResource(2425, 452805, "2408ae64-bcd4-4a68-9045-831c524577a4"));

            wholeProfileServices.importCV(getProfileStr(), 2425);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProfileStr(){
        return "";
    }
}
