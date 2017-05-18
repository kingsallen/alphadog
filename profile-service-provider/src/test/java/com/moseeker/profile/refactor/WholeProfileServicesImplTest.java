package com.moseeker.profile.refactor;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices;
import org.apache.thrift.TException;
import org.junit.Test;

/**
 * Profile 整体接口 客户端 测试类
 * <p>
 * Created by zzh on 16/7/5.
 */
public class WholeProfileServicesImplTest {
    WholeProfileServices.Iface service = ServiceManager.SERVICEMANAGER.getService(WholeProfileServices.Iface.class);


    @Test
    public void testgetResource() throws TException {
        Response response =  service.getResource(0, 0, "");
    }


    @Test
    public void testpostResource() throws TException {
        Response response = service.postResource(null, 0);
    }


    @Test
    public void testimportCV() throws TException {
        Response response =  service.importCV("", 0);
    }


    @Test
    public void testverifyRequires() throws TException {
        Response response =  service.verifyRequires(0, 0);
    }


    @Test
    public void testcreateProfile() throws TException {
        Response response =  service.createProfile("");
    }


    @Test
    public void testimproveProfile() throws TException {
        Response response =  service.improveProfile("");
    }


    @Test
    public void testmoveProfile()
            throws TException {
        Response response =  service.moveProfile(0, 0);
    }
}
