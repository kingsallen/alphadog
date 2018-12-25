//package com.moseeker.position.service.position.liepin;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
//import com.moseeker.baseorm.dao.jobdb.JobPositionLiepinMappingDao;
//import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
//import com.moseeker.common.util.DateUtils;
//import com.moseeker.position.config.AppConfig;
//import com.moseeker.position.constants.position.liepin.LiepinPositionOperateConstant;
//import com.moseeker.position.service.appbs.PositionBS;
//import com.moseeker.position.utils.HttpClientUtil;
//import com.moseeker.position.utils.Md5Utils;
//import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
//import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionLiepinMappingDO;
//import org.apache.http.client.utils.HttpClientUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.amqp.core.Message;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.io.UnsupportedEncodingException;
//import java.lang.reflect.InvocationTargetException;
//import java.util.*;
//
///**
// * @author cjm
// * @date 2018-06-06 17:23
// **/
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
//public class LiepinReceiverTest {
//
//
//        @Autowired
//    LiePinReceiverHandler receiverHandler;
//
//        @Autowired
//    PositionBS positionBS;
//
//        @Autowired
//    JobPositionDao jobPositionDao;
//
//        @Autowired
//    JobPositionLiepinMappingDao mappingDao;
//
//    @Test
//    public void testEdit() throws UnsupportedEncodingException {
//        JSONObject liePinJsonObject = new JSONObject();
//        Integer id = 19493997;
//        JobPositionRecord jobPositionRecord = jobPositionDao.getPositionById(id);
//        JobPositionDO jobPositionDO = com.moseeker.baseorm.util.BeanUtils.DBToStruct(JobPositionDO.class,jobPositionRecord);
//        liePinJsonObject.put("id", id);
//        liePinJsonObject.put("oldPosition", JSONObject.toJSONString(jobPositionDO));
//        jobPositionRecord.setCount((short) 12);
//        jobPositionRecord.setCity("430100,320400");
//        jobPositionDO = com.moseeker.baseorm.util.BeanUtils.DBToStruct(JobPositionDO.class,jobPositionRecord);
//        liePinJsonObject.put("params", JSONObject.toJSONString(jobPositionDO));
//        String requestStr = JSONObject.toJSONString(liePinJsonObject);
//        Message requestMsg = new Message(requestStr.getBytes("UTF-8"), null);
//        receiverHandler.handlerPositionLiepinEditOperation(requestMsg, null);
//    }
//
//    //    @Test
//    public void testDel() {
//
//    }
//
////    @Test
////    public void testDownShelf() throws UnsupportedEncodingException {
////        JSONObject liePinJsonObject = new JSONObject();
////        JSONArray jsonArray = new JSONArray();
////        jsonArray.add(19493756);
////        liePinJsonObject.put("id", jsonArray);
////        String requestStr = JSONObject.toJSONString(liePinJsonObject);
////        Message requestMsg = new Message(requestStr.getBytes("UTF-8"), null);
////        receiverHandler.handlerPositionLiepinDownShelfOperation(requestMsg, null);
////    }
//
//    private static final String LP_USER_STOP_JOB = "https://apidev1.liepin.com/e/job/endEJob.json";
//
//    @Test
//    public void testSingleDownShelf() throws UnsupportedEncodingException {
//        JSONObject liePinJsonObject = new JSONObject();
//        String liePinToken = "a8676f15dd8ce0687373eee1d373d5f91288522acbf267ff3486777f15b952b0b4497b0ada0bb2e86fea2dfbc5c787b5381b754b2b2e55f5e7ce4c509fc2f548";
//        liePinJsonObject.put("ejob_extRefids", 986122472);
//        String httpResultJson = sendRequest2LiePin(liePinJsonObject, liePinToken, LP_USER_STOP_JOB);
//    }
//
//    @Test
//    public void testRePublish() throws UnsupportedEncodingException {
//        JSONObject liePinJsonObject = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.add(19493756);
//        liePinJsonObject.put("id", jsonArray);
//        String requestStr = JSONObject.toJSONString(liePinJsonObject);
//        Message requestMsg = new Message(requestStr.getBytes("UTF-8"), null);
//        receiverHandler.handlerPositionLiepinReSyncOperation(requestMsg, null);
//    }
//
//    @Test
//    public void testGetPosition() throws Exception {
//        JSONObject liePinJsonObject = new JSONObject();
//        liePinJsonObject.put("ejob_extRefids", 986300089);
//        liePinJsonObject.put("usere_id", 1916031);
//        String liePinToken = "a8676f15dd8ce0687373eee1d373d5f91288522acbf267ff3486777f15b952b0b4497b0ada0bb2e86fea2dfbc5c787b5381b754b2b2e55f5e7ce4c509fc2f548";
//        String result = sendRequest2LiePin(liePinJsonObject, liePinToken, LiepinPositionOperateConstant.liepinPositionGet);
//    }
//
//    //    @Test
//    public void getLiepinPositionIds() {
//
//        Integer userId = 1616589;
//        List<JobPositionLiepinMappingDO> list = positionBS.getLiepinPositionIds(userId);
//
//    }
//
//    //    @Test
//    public void test1() {
//        List<JobPositionLiepinMappingDO> list = mappingDao.getMappingDataByUserId(123);
//    }
//
//
//    public String sendRequest2LiePin(JSONObject liePinJsonObject, String liePinToken, String url) {
//        String t = DateUtils.dateToPattern(new Date(), "yyyyMMdd");
//        liePinJsonObject.put("t", t);
//        String sign = Md5Utils.getMD5SortKey(Md5Utils.mapToList(liePinJsonObject), liePinJsonObject);
//        liePinJsonObject.put("sign", sign);
//
//        //设置请求头
//        Map<String, String> headers = new HashMap<>();
//        headers.put("channel", "qianxun");
//        headers.put("token", liePinToken);
//        String httpResultJson = null;
//        try {
//            httpResultJson = HttpClientUtil.sentHttpPostRequest(url, headers, liePinJsonObject);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return httpResultJson;
//    }
//
//    @Test
//    public void testbatchHandlerLiepinDownShelfOperation() throws UnsupportedEncodingException {
//        List<Integer> list = new ArrayList<>();
//        list.add(19493745);
//        list.add(19493736);
//        receiverHandler.batchHandlerLiepinDownShelfOperation(list);
//    }
//
//    @Test
//    public void testbatchHandleLiepinEditOperation() throws UnsupportedEncodingException {
//        List<Integer> list = new ArrayList<>();
//        list.add(19493745);
//        list.add(19493736);
////        receiverHandler.batchHandleLiepinEditOperation(list);
//    }
//
//    private JobPositionDO getUpdateJobPositionFromMq(String msgBody) {
//        try {
//            JSONObject jsonObject = JSONObject.parseObject(msgBody);
//            String jobStr = jsonObject.getString("params");
//            JobPositionDO jobPositionDO = JSONObject.parseObject(jobStr, JobPositionDO.class);
//            return jobPositionDO;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private JobPositionDO getOldJobPositionFromMq(String msgBody) {
//        try {
//            JSONObject jsonObject = JSONObject.parseObject(msgBody);
//            JobPositionDO jobPositionDO = JSONObject.parseObject(jsonObject.getString("oldPosition"), JobPositionDO.class);
//            return jobPositionDO;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Test
//    public void testConvert() throws UnsupportedEncodingException, InvocationTargetException, IllegalAccessException {
//        JobPositionRecord record = new JobPositionRecord();
//        record.setId(123123);
//        record.setCity("121231");
//        record.setFeature("asdqjwoif");
//        record.setOccupation("123123");
//        record.setCount((short) 12);
//        JobPositionDO jobPositionDO = new JobPositionDO();
//        jobPositionDO = com.moseeker.baseorm.util.BeanUtils.DBToStruct(JobPositionDO.class, record);
//        JSONObject liePinJsonObject = new JSONObject();
//        liePinJsonObject.put("id", record.getId());
//        liePinJsonObject.put("oldPosition", JSON.toJSON(jobPositionDO));
//        record.setCount((short) 13);
//        liePinJsonObject.put("params", JSON.toJSON(jobPositionDO));
//        String requestStr = JSONObject.toJSONString(liePinJsonObject);
//        Message requestMsg = new Message(requestStr.getBytes("UTF-8"), null);
//        convert(requestMsg);
//    }
//
//
//    public void convert(Message message) throws UnsupportedEncodingException {
//        String msgBody = "{}";
//        Integer id = null;
//
//        msgBody = new String(message.getBody(), "UTF-8");
//
//
//        id = requireValidEditId(msgBody);
//
//        if (id == null) {
//            return;
//        }
//        JobPositionDO updateJobPosition = getUpdateJobPositionFromMq(msgBody);
//
//        // 获取das端已修改后的职位数据
//        JobPositionDO jobPositionDO = getOldJobPositionFromMq(msgBody);
//    }
//
//    private Integer requireValidEditId(String msgBody) throws UnsupportedEncodingException {
//
//        JSONObject jsonObject = JSONObject.parseObject(msgBody);
//
//        Integer positionId = jsonObject.getInteger("id");
//
//        if (positionId == null) {
//            return null;
//        }
//
//        return positionId;
//    }
//
//    @Test
//    public void testDownShelf() throws Exception {
//        // 构造请求数据
//        JSONObject liePinJsonObject = new JSONObject();
//
//        LiepinHttpClientUtil httpClientUtil = new LiepinHttpClientUtil();
//        liePinJsonObject.put("ejob_extRefids", 986300123);
//
//        String httpResultJson = httpClientUtil.sendRequest2LiePin(liePinJsonObject,
//                "19bb0c417f30d436fe6e2d3cf5aecba314e9430e3016ffa1623b146c50b957439da458eff7d621709af8c4f92929d3d9489ea842de395ffec115161fb69d2bdf",
//                "https://apidev1.liepin.com/e/job/rePublishEjob.json");
//    }
//}
