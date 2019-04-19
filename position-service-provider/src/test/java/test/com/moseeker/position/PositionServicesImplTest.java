package test.com.moseeker.position;

import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;
import com.moseeker.thrift.gen.position.service.PositionServices;

/**
 * 职位服务 调试客户端
 *
 * Created by zzh on 16/7/12.
 */
public class PositionServicesImplTest {

    public static void main(String[] args) {

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setConnectstr("127.0.0.1:2181");
        registryConfig.setNamespace("services");

        String iface = PositionServices.Iface.class.getName();
        ClientConfig<PositionServices.Iface> clientConfig = new ClientConfig<PositionServices.Iface>();
        clientConfig.setService("/com.moseeker.thrift.gen.position.service.PositionServices");
        clientConfig.setIface(iface);

        PositionServices.Iface positionServices = null;

        try {
            positionServices = clientConfig.createProxy(registryConfig);

            System.out.println(positionServices.getPositionById(62875));
//            System.out.println(positionServices.getPositionById(1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

