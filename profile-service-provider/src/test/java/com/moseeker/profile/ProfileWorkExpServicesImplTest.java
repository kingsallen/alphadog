package com.moseeker.profile;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.profile.service.WorkExpServices;
import com.moseeker.thrift.gen.profile.struct.WorkExp;

/**
 * Profile 工作经验客户端 测试类
 *
 * Created by zzh on 16/7/5.
 */
public class ProfileWorkExpServicesImplTest {

    public static void main(String[] args) {

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setConnectstr("127.0.0.1:2181");
        registryConfig.setNamespace("services");

        String iface = WorkExpServices.Iface.class.getName();
        ClientConfig<WorkExpServices.Iface> clientConfig = new ClientConfig<WorkExpServices.Iface>();
        clientConfig.setService("com.moseeker.thrift.gen.profile.service.WorkExpServices");
        clientConfig.setIface(iface);

        WorkExpServices.Iface workExpServices = null;

        try {
            workExpServices = clientConfig.createProxy(registryConfig);

//                System.out.println(workExpServices.postResource(getWorkExp()));

//                System.out.println(workExpServices.getResource(getCommonQuery()));
//                System.out.println(workExpServices.putResource(getWorkExp1()));

            System.out.println(workExpServices.delResource(getWorkExp1()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WorkExp getWorkExp(){
        WorkExp workExp = new WorkExp();
        workExp.setProfile_id(452805);
        workExp.setStart_date("1989-09-09");
        workExp.setEnd_date("1989-09-09");
        workExp.setEnd_until_now((short)1);
        workExp.setSalary_code(4);
        workExp.setIndustry_code(1101);
        workExp.setIndustry_name("test");
        workExp.setCompany_id(2878);
        workExp.setDepartment_name("departnet_name");
        workExp.setPosition_code(110000);
        workExp.setPosition_name("jiagoushi");
        workExp.setDescription("description");
        workExp.setType((short)1);
        workExp.setCity_code(110000);
        workExp.setCity_name("bj");
        workExp.setReport_to("report_to");
        workExp.setUnderlings(5);
        workExp.setReference("reference");
        workExp.setResign_reason("resign_reason");
        workExp.setAchievement("setAchievement");
        workExp.setJob("job");
        return workExp;
    }

    public static WorkExp getWorkExp1(){
        WorkExp workExp = new WorkExp();
        workExp.setId(669268);
        workExp.setProfile_id(452805);
        workExp.setStart_date("1989-09-19");
        workExp.setEnd_date("1989-09-19");
        workExp.setEnd_until_now((short)0);
        workExp.setSalary_code(4);
        workExp.setIndustry_code(1101);
        workExp.setIndustry_name("test1");
        workExp.setCompany_id(2878);
        workExp.setDepartment_name("departnet_name1");
        workExp.setPosition_code(110000);
        workExp.setPosition_name("jiagoushi1");
        workExp.setDescription("description1");
        workExp.setType((short)2);
        workExp.setCity_code(110000);
        workExp.setCity_name("bj1");
        workExp.setReport_to("report_to1");
        workExp.setUnderlings(6);
        workExp.setReference("reference1");
        workExp.setResign_reason("resign_reason1");
        workExp.setAchievement("setAchievement1");
        workExp.setJob("job1");
        return workExp;
    }


}
