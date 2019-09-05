package com.moseeker.profile.thrift;

import com.moseeker.profile.config.AppConfig;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.profile.service.ProjectExpServices;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;

/**
 * Created by YYF
 *
 * Date: 2017/7/26
 *
 * Project_name :alphadog
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ProfileProjectExpServicesImplTest {


    @Autowired
    private ProfileProjectExpServicesImpl projectExpServices;

    ProjectExpServices.Iface projectExpService = ServiceManager.SERVICE_MANAGER
            .getService(ProjectExpServices.Iface.class);

    @Test
    public void getResource() throws Exception {
        CommonQuery query = new CommonQuery();
        HashMap map = new HashMap();
        map.put("rol", "ddd");
        query.setEqualFilter(map);
        projectExpServices.getResource(query);
    }


    @Test
    public void getResourceThrift() throws Exception {
        CommonQuery query = new CommonQuery();
        HashMap map = new HashMap();
        map.put("rol", "ddd");
        query.setEqualFilter(map);
        projectExpService.getResource(query);
    }

}