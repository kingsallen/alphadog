package com.moseeker.useraccounts;

import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import com.moseeker.thrift.gen.useraccounts.struct.User;
import com.moseeker.thrift.gen.useraccounts.struct.UserFavoritePosition;

/**
 * 用户服务 客户端测试类
 * <p>
 *
 * Created by zzh on 16/5/25.
 */
public class UseraccountsServiceImplTest {

    public static void main(String[] args) {

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setConnectstr("127.0.0.1:2181");
        registryConfig.setNamespace("services");

        String iface = UseraccountsServices.Iface.class.getName();
        ClientConfig<UseraccountsServices.Iface> clientConfig = new ClientConfig<UseraccountsServices.Iface>();
        clientConfig.setService("com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices");
        clientConfig.setIface(iface);

        UseraccountsServices.Iface useraccountsServices = null;

        try {
            useraccountsServices = clientConfig.createProxy(registryConfig);

            // 添加我感兴趣
            Response getUserFavoritePosition = useraccountsServices.postUserFavoritePosition(getUserFavoritePosition());

            System.out.println(getUserFavoritePosition);

            // 是否我感兴趣
            Response response1 = useraccountsServices.getUserFavPositionCountByUserIdAndPositionId(1, 1);

            System.out.println(response1);

            Response response2 = useraccountsServices.getUserFavPositionCountByUserIdAndPositionId(1, 2);

            System.out.println(response2);

            // 用户注册
            String code = "1234";
            Response userResponse = useraccountsServices.postusermobilesignup(getUser(), code);
            System.out.println(userResponse);

            // 更新用户
            Response userResponse2 = useraccountsServices.updateUser(getUpdateUser(7));
            System.out.println(userResponse2);

            // 获取用户
            Response userResponse3 = useraccountsServices.getUserById(7);
            System.out.println(userResponse3);

            // 更新用户
            Response userResponse4 = useraccountsServices.updateUser(getUpdateUser1(7));
            System.out.println(userResponse4);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static UserFavoritePosition getUserFavoritePosition(){
        UserFavoritePosition userFavoritePosition = new UserFavoritePosition();
        userFavoritePosition.setSysuser_id(1);
        userFavoritePosition.setPosition_id(1);
        return userFavoritePosition;
    }

    public static User getUser(){
        User user = new User();
        user.setMobile(18610245973L);
        user.setUsername("18610245973");
        user.setPassword("123456");
        user.setSource((byte) 9);
        return user;
    }

    public static User getUpdateUser(int id){
        User user = new User();
        user.setId(id);
        user.setPassword("123456111111");
        user.setSource((byte) 8);
        user.setEmail("intasect@126.com");
        user.setName("zhaozhognhua");
        user.setCompany("company");
        user.setPosition("position");
        return user;
    }

    public static User getUpdateUser1(int id){
        User user = new User();
        user.setId(id);
        user.setPassword("123456789");
        return user;
    }

}
