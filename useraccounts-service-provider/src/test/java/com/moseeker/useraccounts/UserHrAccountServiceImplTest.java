package com.moseeker.useraccounts;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import com.moseeker.thrift.gen.useraccounts.struct.UserHrAccount;
import com.moseeker.useraccounts.thrift.UserHrAccountServiceImpl;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Test;

/**
 * HR账号服务
 * <p>
 * Created by zzh on 16/6/1.
 */
public class UserHrAccountServiceImplTest {

    public static void main(String[] args) {

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setConnectstr("127.0.0.1:2181");
        registryConfig.setNamespace("services");

        String iface = UserHrAccountService.Iface.class.getName();
        ClientConfig<UserHrAccountService.Iface> clientConfig = new ClientConfig<UserHrAccountService.Iface>();
        clientConfig.setService("com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService");
        clientConfig.setIface(iface);

        UserHrAccountService.Iface userHrAccountService = null;

        try {
            userHrAccountService = clientConfig.createProxy(registryConfig);

            // 添加我感兴趣
            String code = "1234";
//            Response postResource = userHrAccountService.postResource(getUserHrAccount(), code);
            Response postResource = userHrAccountService.userHrAccount(1, 1, 1, 10);

            System.out.println(postResource);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 添加HR账号
     */
    private static UserHrAccount getUserHrAccount() {
        UserHrAccount userHrAccount = new UserHrAccount();
        userHrAccount.setMobile("18610245972");
        userHrAccount.setSource(2);
        return userHrAccount;
    }


    ////@Test
    public void userHrAccount() throws TException {
        UserHrAccountService.Iface user = ServiceManager.SERVICEMANAGER.getService(UserHrAccountService.Iface.class);
        Response response = user.userHrAccount(0, 1, 1, 10);
        System.out.println(response.getData());
    }

}
