package test.com.moseeker.position.service;

import com.alibaba.fastjson.JSON;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.PositionServices;
import com.moseeker.thrift.gen.position.struct.Position;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PositionService {
    private PositionServices.Iface positionService;

    @Before
    public void initialize() {
        positionService = ServiceManager.SERVICEMANAGER.getService(PositionServices.Iface.class);
    }

    @Test
    public void testGetPositionById() {
        try {

            Response p = positionService.getPositionById(1);
            assertTrue(p.getStatus() == 0);
            assertTrue(JSON.parseObject(p.getData(), Position.class).getId() == 1);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
