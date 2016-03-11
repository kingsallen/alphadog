package com.moseeker.thrift.user.client;

import com.bfd.harpc.config.ClientConfig;
import com.bfd.harpc.config.RegistryConfig;
import com.moseeker.thrift.user.service.UserService.Iface;
import com.moseeker.thrift.user.struct.User;

/**
 * Created by zzh on 16/3/11.
 */
public class UserServiceImpl {

    /**
     *
     * @param id
     * @return
     */
    public User getUserById(int id){

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setConnectstr("127.0.0.1:4181");
        registryConfig.setAuth("moseeker:moseeker");
        registryConfig.setNamespace("moseeker_user");

        String iface = Iface.class.getName();
        ClientConfig<Iface> clientConfig = new ClientConfig<Iface>();
        clientConfig.setService("com.moseeker.user.service.user$UserService");
        clientConfig.setIface(iface);

        try {
            // 注意:代理内部已经使用连接池，所以这里只需要创建一个实例，多线程共享；特殊情况下，可以允许创建多个实例，
            // 但严禁每次调用前都创建一个实例。
            Iface userService = clientConfig.createProxy(registryConfig);

            User u = userService.getById(id);
            System.out.println(u.getEmail());
            return u;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args){
        UserServiceImpl usi = new UserServiceImpl();
        usi.getUserById(1);
    }

}
