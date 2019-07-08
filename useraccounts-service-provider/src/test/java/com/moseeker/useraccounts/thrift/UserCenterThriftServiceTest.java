package com.moseeker.useraccounts.thrift;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.useraccounts.service.UserCenterService;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import org.junit.After;
import org.junit.Before;

import java.util.List;

/**
 * UserCenterThriftService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Feb 24, 2017</pre>
 */
public class UserCenterThriftServiceTest {

    UserCenterService.Iface userCenterService = ServiceManager.SERVICE_MANAGER
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
    ////@Test
    public void testGetApplications() throws Exception {
        List<ApplicationRecordsForm> formList = userCenterService.getApplications(4);
        if(formList != null && formList.size() > 0) {
            formList.forEach(form -> {
                System.out.println(form.getCompany_name());
                System.out.println(form.getId());
                System.out.println(form.getStatus_name());
                System.out.println(form.getTime());
                System.out.println(form.getPosition_title());
            });
        }
    }

    /**
     * Method: getApplicationDetail(int userId, int appId)
     */
    ////@Test
    public void testGetApplicationDetail() throws Exception {
        ApplicationDetailVO vo = userCenterService.getApplicationDetail(3870, 204504);
        if(vo != null) {
            System.out.println("name : "+vo.getCompany_name());
            System.out.println("pid : "+vo.getPid());
            System.out.println("title : "+vo.getPosition_title());
            System.out.println("status_timeline : "+vo.getStatus_timeline());
            System.out.println("step : "+vo.getStep());
            System.out.println("step_status : "+vo.getStep_status());
            if(vo.getStatus_timeline() != null && vo.getStatus_timeline().size() > 0) {
                vo.getStatus_timeline().forEach(time_line-> {
                    System.out.println(time_line.getStep_status());
                    System.out.println(time_line.getDate());
                    System.out.println(time_line.getEvent());
                    System.out.println(time_line.getHide());
                });
            }
        }
    }

    /**
     * Method: getFavPositions(int userId)
     */
    ////@Test
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
    //@Test
    public void testGetRecommendation() throws Exception {
        RecommendationVO recommendationVO = userCenterService.getRecommendation(4021, (byte) 3, 1, 10);
        if(recommendationVO != null) {
            System.out.println("is recommended : "+recommendationVO.isHasRecommends());
            System.out.println("score:");
            if(recommendationVO.getScore() != null) {
                System.out.println(" applier_account : "+recommendationVO.getScore().getApplied_count());
                System.out.println(" interested_count : "+recommendationVO.getScore().getInterested_count());
                System.out.println(" link_viewed_count : "+recommendationVO.getScore().getLink_viewed_count());
            }

            List<RecommendationRecordVO> recommendationRecordVOList = recommendationVO.getRecommends();
            if(recommendationRecordVOList != null && recommendationRecordVOList.size() > 0) {
                recommendationRecordVOList.forEach(recommendationRecordVO -> {
                    System.out.println(" appler_name : " + recommendationRecordVO.getApplier_name());
                    System.out.println(" appler_rel : " + recommendationRecordVO.getApplier_rel());
                    System.out.println(" click_time : " + recommendationRecordVO.getClick_time());
                    System.out.println(" headimgurl : " + recommendationRecordVO.getHeadimgurl());
                    System.out.println(" position : " + recommendationRecordVO.getPosition());
                    System.out.println(" is_interested : " + recommendationRecordVO.getIs_interested());
                    System.out.println(" recom_status : " + recommendationRecordVO.getRecom_status());
                    System.out.println(" status : "+recommendationRecordVO.getStatus());
                    System.out.println(" view_number : " + recommendationRecordVO.getView_number());
                });
            }
        }
    }
}
