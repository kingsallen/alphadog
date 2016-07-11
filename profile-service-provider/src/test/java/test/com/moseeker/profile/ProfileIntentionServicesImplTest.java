package test.com.moseeker.profile;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.profile.service.IntentionServices;
import com.moseeker.thrift.gen.profile.struct.Intention;

/**
 * Profile 求职意向 客户端 测试类
 *
 * Created by zzh on 16/7/5.
 */
public class ProfileIntentionServicesImplTest {

    public static void main(String[] args) {

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setConnectstr("127.0.0.1:2181");
        registryConfig.setNamespace("services");

        String iface = IntentionServices.Iface.class.getName();
        ClientConfig<IntentionServices.Iface> clientConfig = new ClientConfig<IntentionServices.Iface>();
        clientConfig.setService("com.moseeker.thrift.gen.profile.service.IntentionServices");
        clientConfig.setIface(iface);

        IntentionServices.Iface intentionServices = null;

        try {
            intentionServices = clientConfig.createProxy(registryConfig);

//            System.out.println(intentionServices.postResource(getIntention()));
//            System.out.println(intentionServices.putResource(getIntention1()));
//            System.out.println(intentionServices.getResource(getCommonQuery()));
            System.out.println(intentionServices.delResource(getIntention1()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Intention getIntention(){
        Intention intention = new Intention();
        intention.setProfile_id(452805);
        intention.setWorkstate(1);
        intention.setWorktype((short)1);
        intention.setSalary_code(1);
        intention.setTag("tag");
        intention.setConsider_venture_company_opportunities((short)1);
        return intention;
    }

    public static Intention getIntention1(){
        Intention intention = new Intention();
        intention.setId(444776);
        intention.setProfile_id(452805);
        intention.setWorkstate(2);
        intention.setWorktype((short)2);
        intention.setSalary_code(2);
        intention.setTag("tag2");
        intention.setConsider_venture_company_opportunities((short)2);
        return intention;
    }

    public static CommonQuery getCommonQuery(){
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("profile_id", "452805");
        return queryUtil;
    }

}
