package test.com.moseeker.dict;

import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.DictConstanService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzh on 16/5/26.
 */
public class DictConstantServiceImplTest {

    public static void main(String[] args) {

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setConnectstr("127.0.0.1:2181");
        registryConfig.setNamespace("services");

        String iface = DictConstanService.Iface.class.getName();
        ClientConfig<DictConstanService.Iface> clientConfig = new ClientConfig<DictConstanService.Iface>();
        clientConfig.setService("com.moseeker.thrift.gen.dict.service.DictConstanService");
        clientConfig.setIface(iface);

        DictConstanService.Iface dictConstanService = null;

        try {
            dictConstanService = clientConfig.createProxy(registryConfig);

            List<Integer> parentCodeList = new ArrayList<Integer>();
            parentCodeList.add(3101);
            parentCodeList.add(3102);
            Response getDictConstantJsonByParentCode = dictConstanService.getDictConstantJsonByParentCode(parentCodeList);

            System.out.println(getDictConstantJsonByParentCode);

            Response getDictConstantJsonByParentCode1 = dictConstanService.getDictConstantJsonByParentCode(null);

            System.out.println(getDictConstantJsonByParentCode1);

            List<Integer> parentCodeList2 = new ArrayList<Integer>();
            parentCodeList2.add(3101);

            Response getDictConstantJsonByParentCode2 = dictConstanService.getDictConstantJsonByParentCode(parentCodeList2);

            System.out.println(getDictConstantJsonByParentCode2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

