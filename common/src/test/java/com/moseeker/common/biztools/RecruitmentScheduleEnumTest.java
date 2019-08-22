package com.moseeker.common.biztools;

import com.moseeker.common.weixin.QrcodeType;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.assertEquals;

/**
 * RecruitmentScheduleEnum Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Feb 17, 2017</pre>
 */
public class RecruitmentScheduleEnumTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getCurrentStatus()
     */
    //@Test
    public void testGetCurrentStatus() throws Exception {
    }

    /**
     * Method: setLastStep(int lastStep)
     */
    //@Test
    public void testSetLastStep() throws Exception {
        RecruitmentScheduleEnum recruitmentScheduleEnum = RecruitmentScheduleEnum.createFromID(4);

        recruitmentScheduleEnum.setLastStep(7);
        assertEquals(6, recruitmentScheduleEnum.getStatusForRecommendationInPersonalCenter());

        recruitmentScheduleEnum.setLastStep(13);
        assertEquals(6, recruitmentScheduleEnum.getStatusForRecommendationInPersonalCenter());

        recruitmentScheduleEnum.setLastStep(1);
        assertEquals(6, recruitmentScheduleEnum.getStatusForRecommendationInPersonalCenter());

        recruitmentScheduleEnum.setLastStep(6);
        assertEquals(7, recruitmentScheduleEnum.getStatusForRecommendationInPersonalCenter());

        recruitmentScheduleEnum.setLastStep(8);
        assertEquals(8, recruitmentScheduleEnum.getStatusForRecommendationInPersonalCenter());

        recruitmentScheduleEnum.setLastStep(9);
        assertEquals(8, recruitmentScheduleEnum.getStatusForRecommendationInPersonalCenter());

        recruitmentScheduleEnum.setLastStep(10);
        assertEquals(8, recruitmentScheduleEnum.getStatusForRecommendationInPersonalCenter());

        recruitmentScheduleEnum.setLastStep(2);
        assertEquals(9, recruitmentScheduleEnum.getStatusForRecommendationInPersonalCenter());

        recruitmentScheduleEnum.setLastStep(5);
        assertEquals(9, recruitmentScheduleEnum.getStatusForRecommendationInPersonalCenter());

        recruitmentScheduleEnum.setLastStep(12);
        assertEquals(9, recruitmentScheduleEnum.getStatusForRecommendationInPersonalCenter());
    }

    /**
     * Method: setLastStep(int lastStep)
     */
    //@Test
    public void testSetLastStep1() throws Exception {
        RecruitmentScheduleEnum recruitmentScheduleEnum = RecruitmentScheduleEnum.createFromID(1);

        recruitmentScheduleEnum.setLastStep(7);
        assertEquals(1, recruitmentScheduleEnum.getStatusForRecommendationInPersonalCenter());

        recruitmentScheduleEnum.setLastStep(13);
        assertEquals(1, recruitmentScheduleEnum.getStatusForRecommendationInPersonalCenter());


    }

    //@Test
    public void testGetAppStatusDescription() throws Exception {
        RecruitmentScheduleEnum recruitmentScheduleEnum = RecruitmentScheduleEnum.createFromID(12);
        System.out.println(recruitmentScheduleEnum.getAppStatusDescription((byte)0, (byte)0,10));

        RecruitmentScheduleEnum recruitmentScheduleEnum1 = RecruitmentScheduleEnum.createFromID(4);
        System.out.println(recruitmentScheduleEnum1.getAppStatusDescription((byte)0, (byte)0,12));

        RecruitmentScheduleEnum recruitmentScheduleEnum2 = RecruitmentScheduleEnum.createFromID(4);
        System.out.println(recruitmentScheduleEnum2.getAppStatusDescription((byte)0, (byte)0,12));

        RecruitmentScheduleEnum recruitmentScheduleEnum3 = RecruitmentScheduleEnum.createFromID(12);
        System.out.println(recruitmentScheduleEnum3.getAppStatusDescription((byte)0, (byte)0,12));

        RecruitmentScheduleEnum recruitmentScheduleEnum4 = RecruitmentScheduleEnum.createFromID(10);
        System.out.println(recruitmentScheduleEnum4.getAppStatusDescription((byte)0, (byte)0,12));

        RecruitmentScheduleEnum recruitmentScheduleEnum5 = RecruitmentScheduleEnum.createFromID(6);
        System.out.println(recruitmentScheduleEnum5.getAppStatusDescription((byte)0, (byte)0,10));

        RecruitmentScheduleEnum recruitmentScheduleEnum6 = RecruitmentScheduleEnum.createFromID(10);
        System.out.println(recruitmentScheduleEnum6.getAppStatusDescription((byte)0, (byte)0,6));

        RecruitmentScheduleEnum recruitmentScheduleEnum7 = RecruitmentScheduleEnum.createFromID(12);
        System.out.println(recruitmentScheduleEnum7.getAppStatusDescription((byte)0, (byte)0,10));

        RecruitmentScheduleEnum recruitmentScheduleEnum8 = RecruitmentScheduleEnum.createFromID(10);
        System.out.println(recruitmentScheduleEnum8.getAppStatusDescription((byte)0, (byte)0,12));

        RecruitmentScheduleEnum recruitmentScheduleEnum9 = RecruitmentScheduleEnum.createFromID(3);
        System.out.println(recruitmentScheduleEnum9.getAppStatusDescription((byte)0, (byte)0,12));

        RecruitmentScheduleEnum recruitmentScheduleEnum11 = RecruitmentScheduleEnum.createFromID(12);
        System.out.println(recruitmentScheduleEnum11.getAppStatusDescription((byte)0, (byte)0,3));

        RecruitmentScheduleEnum recruitmentScheduleEnum10 = RecruitmentScheduleEnum.createFromID(10);
        System.out.println(recruitmentScheduleEnum10.getAppStatusDescription((byte)0, (byte)0,12));

        RecruitmentScheduleEnum recruitmentScheduleEnum12 = RecruitmentScheduleEnum.createFromID(6);
        System.out.println(recruitmentScheduleEnum12.getAppStatusDescription((byte)0, (byte)0,10));
    }

    /**
     * Method: init(int value)
     */
    //@Test
    public void testInit() throws Exception {
        //TODO: Test goes here...
        /*
        try {
           Method method = RecruitmentScheduleEnum.getClass().getMethod("init", int.class);
           method.setAccessible(true);
           method.invoke(<Object>, <Parameters>);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }
        */
    }

    @Test
    public void testQrcodeType(){
        assertEquals(QrcodeType.QR_LIMIT_SCENE, QrcodeType.fromInt(1));
    }

} 
