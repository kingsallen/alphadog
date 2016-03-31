package test.com.moseeker.rpccenter.client;

import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;
import test.com.moseeker.rpccenter.gen.EchoService.Iface;

/**
 * Created by zzh on 16/3/30.
 */
public class ClientDemo {

    public static void main(String[] args) {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setConnectstr("127.0.0.1:4181");
        registryConfig.setNamespace("services");

        String iface = Iface.class.getName();
        ClientConfig<Iface> clientConfig = new ClientConfig<Iface>();
        clientConfig.setService("com.moseeker.thrift.service$EchoService");
        clientConfig.setIface(iface);

        try {
            // 注意:代理内部已经使用连接池，所以这里只需要创建一个实例，多线程共享；特殊情况下，可以允许创建多个实例，
            // 但严禁每次调用前都创建一个实例。
            Iface echoService = clientConfig.createProxy(registryConfig);

            for (int i = 0; i < 2; i++) {
                System.out.println(echoService.echo("   yep!"));
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
