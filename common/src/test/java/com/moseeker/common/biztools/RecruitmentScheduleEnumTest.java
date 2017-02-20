package com.moseeker.common.biztools;

import org.junit.Assert;
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
    @Test
    public void testGetCurrentStatus() throws Exception {
    }

    /**
     * Method: setLastStep(int lastStep)
     */
    @Test
    public void testSetLastStep() throws Exception {
        RecruitmentScheduleEnum recruitmentScheduleEnum = RecruitmentScheduleEnum.createFromID(4);

        recruitmentScheduleEnum.setLastStep(7);
        assertEquals(6, recruitmentScheduleEnum.getDisplayStatus());

        recruitmentScheduleEnum.setLastStep(13);
        assertEquals(6, recruitmentScheduleEnum.getDisplayStatus());

        recruitmentScheduleEnum.setLastStep(1);
        assertEquals(6, recruitmentScheduleEnum.getDisplayStatus());

        recruitmentScheduleEnum.setLastStep(6);
        assertEquals(7, recruitmentScheduleEnum.getDisplayStatus());

        recruitmentScheduleEnum.setLastStep(8);
        assertEquals(8, recruitmentScheduleEnum.getDisplayStatus());

        recruitmentScheduleEnum.setLastStep(9);
        assertEquals(8, recruitmentScheduleEnum.getDisplayStatus());

        recruitmentScheduleEnum.setLastStep(10);
        assertEquals(8, recruitmentScheduleEnum.getDisplayStatus());

        recruitmentScheduleEnum.setLastStep(2);
        assertEquals(9, recruitmentScheduleEnum.getDisplayStatus());

        recruitmentScheduleEnum.setLastStep(5);
        assertEquals(9, recruitmentScheduleEnum.getDisplayStatus());

        recruitmentScheduleEnum.setLastStep(12);
        assertEquals(9, recruitmentScheduleEnum.getDisplayStatus());
    }

    /**
     * Method: setLastStep(int lastStep)
     */
    @Test
    public void testSetLastStep1() throws Exception {
        RecruitmentScheduleEnum recruitmentScheduleEnum = RecruitmentScheduleEnum.createFromID(1);

        recruitmentScheduleEnum.setLastStep(7);
        assertEquals(1, recruitmentScheduleEnum.getDisplayStatus());

        recruitmentScheduleEnum.setLastStep(13);
        assertEquals(1, recruitmentScheduleEnum.getDisplayStatus());


    }


    /**
     * Method: init(int value)
     */
    @Test
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

} 
