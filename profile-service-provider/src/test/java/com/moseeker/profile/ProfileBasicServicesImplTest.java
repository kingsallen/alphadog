package com.moseeker.profile;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.profile.service.BasicServices;
import com.moseeker.thrift.gen.profile.struct.Basic;

/**
 * Profile basic信息客户端 测试类
 * <p>
 * Created by zzh on 16/7/5.
 */
public class ProfileBasicServicesImplTest {

    public static void main(String[] args) {

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setConnectstr("127.0.0.1:2181");
        registryConfig.setNamespace("services");

        String iface = BasicServices.Iface.class.getName();
        ClientConfig<BasicServices.Iface> clientConfig = new ClientConfig<BasicServices.Iface>();
        clientConfig.setService("com.moseeker.thrift.gen.profile.service.BasicServices");
        clientConfig.setIface(iface);

        BasicServices.Iface profileBasicService = null;

        try {
            profileBasicService = clientConfig.createProxy(registryConfig);

//            System.out.println(profileBasicService.postResource(getProfileBasic()));

//            System.out.println(profileBasicService.getResource(getCommonQuery()));

//            System.out.println(profileBasicService.putResource(getProfileBasic1()));

            profileBasicService.delResource(getProfileBasic());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Basic getProfileBasic() {
        Basic basic = new Basic();
        basic.setProfile_id(1);
        basic.setName("name");
        basic.setGender((short) 2);
        basic.setNationality_code(2);
        basic.setNationality_name("阿富汗伊斯兰国");
        basic.setCity_code(110000);
        basic.setCity_name("shanghia");
        basic.setBirth("1989-09-09");
        basic.setWeixin("weixin");
        basic.setQq("1234");
        basic.setMotto("motto");
        basic.setSelf_introduction("self introduction");
        return basic;
    }


    public static Basic getProfileBasic1() {
        Basic basic = new Basic();
        basic.setProfile_id(1);
        basic.setName("name1");
        basic.setGender((short) 1);
        basic.setNationality_code(2);
        basic.setNationality_name("阿富汗伊斯兰国");
        basic.setCity_code(110000);
        basic.setCity_name("shanghia");
        basic.setBirth("1989-09-09");
        basic.setWeixin("weixin");
        basic.setQq("1234");
        basic.setMotto("motto");
        basic.setSelf_introduction("self introduction");
        return basic;
    }

}
