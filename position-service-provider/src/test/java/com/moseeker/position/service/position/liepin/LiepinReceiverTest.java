package com.moseeker.position.service.position.liepin;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.jobdb.JobPositionLiepinMappingDao;
import com.moseeker.common.util.DateUtils;
import com.moseeker.position.config.AppConfig;
import com.moseeker.position.service.appbs.PositionBS;
import com.moseeker.position.service.position.liepin.LiePinReceiverHandler;
import com.moseeker.position.utils.HttpClientUtil;
import com.moseeker.position.utils.Md5Utils;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionLiepinMappingDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjm
 * @date 2018-06-06 17:23
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class LiepinReceiverTest {


    @Autowired
    LiePinReceiverHandler receiverHandler;

    @Autowired
    PositionBS positionBS;

    @Autowired
    JobPositionLiepinMappingDao mappingDao;

    @Test
    public void testEdit() throws UnsupportedEncodingException {
        JSONObject liePinJsonObject = new JSONObject();
        liePinJsonObject.put("id", "(19493736)");
        String requestStr = JSONObject.toJSONString(liePinJsonObject);
        Message requestMsg = new Message(requestStr.getBytes("UTF-8"), null);
        receiverHandler.handlerPositionLiepinEditOperation(requestMsg, null);
    }

//    @Test
    public void testDel() {

    }

    @Test
    public void testDownShelf() throws UnsupportedEncodingException {
        JSONObject liePinJsonObject = new JSONObject();
        liePinJsonObject.put("id", "(19493736)");
        String requestStr = JSONObject.toJSONString(liePinJsonObject);
        Message requestMsg = new Message(requestStr.getBytes("UTF-8"), null);
        receiverHandler.handlerPositionLiepinDownShelfOperation(requestMsg, null);
    }
    private static final String LP_USER_STOP_JOB = "https://apidev1.liepin.com/e/job/endEJob.json";
    @Test
    public void testSingleDownShelf() throws UnsupportedEncodingException {
        JSONObject liePinJsonObject = new JSONObject();
        String liePinToken = "a8676f15dd8ce0687373eee1d373d5f91288522acbf267ff3486777f15b952b0b4497b0ada0bb2e86fea2dfbc5c787b5381b754b2b2e55f5e7ce4c509fc2f548";
        liePinJsonObject.put("ejob_extRefids", 986122472);
        String httpResultJson = sendRequest2LiePin(liePinJsonObject, liePinToken, LP_USER_STOP_JOB);
    }

    @Test
    public void testRePublish() throws UnsupportedEncodingException {
        JSONObject liePinJsonObject = new JSONObject();
        liePinJsonObject.put("id", "(19493736)");
        String requestStr = JSONObject.toJSONString(liePinJsonObject);
        Message requestMsg = new Message(requestStr.getBytes("UTF-8"), null);
        receiverHandler.handlerPositionLiepinReSyncOperation(requestMsg, null);
    }

    @Test
    public void testGetPosition() throws Exception {
        Integer positionId = 19493736;
        Integer id = 986122475;
        String info = receiverHandler.getLpPositionInfo(positionId, id);
    }

//    @Test
    public void getLiepinPositionIds() {

        Integer userId = 1616589;
        List<JobPositionLiepinMappingDO> list= positionBS.getLiepinPositionIds(userId);

    }

//    @Test
    public void test1(){
        List<JobPositionLiepinMappingDO> list = mappingDao.getMappingDataByUserId(123);
    }



    public String sendRequest2LiePin(JSONObject liePinJsonObject, String liePinToken, String url) {
        String t = DateUtils.dateToPattern(new Date(), "yyyyMMdd");
        liePinJsonObject.put("t", t);
        String sign = Md5Utils.getMD5SortKey(Md5Utils.mapToList(liePinJsonObject), liePinJsonObject);
        liePinJsonObject.put("sign", sign);

        //设置请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("channel", "qianxun");
        headers.put("token", liePinToken);
        String httpResultJson = null;
        try {
            httpResultJson = HttpClientUtil.sentHttpPostRequest(url, headers, liePinJsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpResultJson;
    }
}
