package com.moseeker.dict;

import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.util.query.Query;
import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dict.service.DictCountryService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzh on 16/5/26.
 */
public class DictCountryServiceImplTest {

    public static void main(String[] args) {

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setConnectstr("127.0.0.1:2181");
        registryConfig.setNamespace("services");

        String iface = DictCountryService.Iface.class.getName();
        ClientConfig<DictCountryService.Iface> clientConfig = new ClientConfig<DictCountryService.Iface>();
        clientConfig.setService("/com.moseeker.thrift.gen.dict.service.DictCountryService");
        clientConfig.setIface(iface);

        DictCountryService.Iface dictCountryService = null;

        try {
            dictCountryService = clientConfig.createProxy(registryConfig);
            CommonQuery query =new CommonQuery();
            System.out.println(dictCountryService.getDictCountry(query));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

