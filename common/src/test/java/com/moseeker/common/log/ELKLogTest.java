package com.moseeker.common.log;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.Constant;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.HashMap;

/**
 * ELKLog Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Mar 8, 2017</pre>
 */
public class ELKLogTest {

    ELKLog log = ELKLog.ELK_LOG;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: log(LogVO logVO)
     */
    ////@Test
    public void testLog() throws Exception {
        HashMap<String, Object> statisticsForChannelmportVO = new HashMap<>();
        statisticsForChannelmportVO.put("profile_operation", (byte)0);
        statisticsForChannelmportVO.put("profile_id", 1);
        statisticsForChannelmportVO.put("user_id", 2);
        statisticsForChannelmportVO.put("import_channel", 3);
        statisticsForChannelmportVO.put("import_time", System.currentTimeMillis());

        LogVO logVO = new LogVO();
        logVO.setAppid(Constant.APPID_ALPHADOG);
        logVO.setEvent("WholeProfileService_test");
        logVO.setStatus_code(0);
        logVO.setReq_params(new HashMap<String, Object>(){{
            this.put("id", 1);
            this.put("name", 2);
        }});
        logVO.setCustoms(statisticsForChannelmportVO);
        ELKLog.ELK_LOG.log(logVO);
    }
}
