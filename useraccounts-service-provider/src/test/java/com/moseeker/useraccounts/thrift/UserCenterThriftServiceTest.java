package com.moseeker.useraccounts.thrift;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.useraccounts.service.UserCenterService;
import com.moseeker.thrift.gen.useraccounts.struct.FavPositionForm;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * UserCenterThriftService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Feb 24, 2017</pre>
 */
public class UserCenterThriftServiceTest {

    UserCenterService.Iface userCenterService = ServiceManager.SERVICEMANAGER
            .getService(UserCenterService.Iface.class);

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getApplications(int userId)
     */
    @Test
    public void testGetApplications() throws Exception {
    }

    /**
     * Method: getApplicationDetail(int userId, int appId)
     */
    @Test
    public void testGetApplicationDetail() throws Exception {
    }

    /**
     * Method: getFavPositions(int userId)
     */
    @Test
    public void testGetFavPositions() throws Exception {
        List<FavPositionForm> formList = userCenterService.getFavPositions(2);
        if(formList != null && formList.size() > 0) {
            formList.forEach(form -> {
                System.out.println(form.getCity());
                System.out.println(form.getDepartment());
                System.out.println(form.getId());
                System.out.println(form.getSalary_bottom());
                System.out.println(form.getSalary_top());
                System.out.println(form.getStatus());
                System.out.println(form.getTime());
                System.out.println(form.getTitle());
                System.out.println(form.getUpdate_time());
            });
        }
    }

    /**
     * Method: getRecommendation(int userId, byte type, int pageNo, int pageSize)
     */
    @Test
    public void testGetRecommendation() throws Exception {
    }
}
