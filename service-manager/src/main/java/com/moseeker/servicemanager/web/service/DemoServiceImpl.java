package com.moseeker.servicemanager.web.service;

import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerQuery;
import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerServices.Iface;
import org.apache.thrift.TException;

/**
 * Created by zzh on 16/3/30.
 */
public class DemoServiceImpl extends BaseService<Iface> {

    public static final String DEMO_SERVICE_NAME = "/demo/get";

    Iface service = getService(DEMO_SERVICE_NAME, Iface.class.getName());

    public void getDemoService() throws TException {
        CompanyfollowerQuery n  = null;
        service.getCompanyfollowers(n);
    }

}
