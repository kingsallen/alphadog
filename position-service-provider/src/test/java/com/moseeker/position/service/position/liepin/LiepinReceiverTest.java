package com.moseeker.position.service.position.liepin;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.jobdb.JobPositionLiepinMappingDao;
import com.moseeker.position.config.AppConfig;
import com.moseeker.position.service.appbs.PositionBS;
import com.moseeker.position.service.position.liepin.LiePinReceiverHandler;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionLiepinMappingDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author cjm
 * @date 2018-06-06 17:23
 **/
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class LiepinReceiverTest {


    @Autowired
    LiePinReceiverHandler receiverHandler;

    @Autowired
    PositionBS positionBS;

    @Autowired
    JobPositionLiepinMappingDao mappingDao;

//    @Test
    public void testEdit() throws UnsupportedEncodingException {
        JSONObject liePinJsonObject = new JSONObject();
        liePinJsonObject.put("id", "19493560");
        String requestStr = JSONObject.toJSONString(liePinJsonObject);
        Message requestMsg = new Message(requestStr.getBytes("UTF-8"), null);
        receiverHandler.handlerPositionLiepinEditOperation(requestMsg, null);
    }

//    @Test
    public void testDel() {

    }

//    @Test
    public void testDownShelf() throws UnsupportedEncodingException {
        JSONObject liePinJsonObject = new JSONObject();
        liePinJsonObject.put("id", "19493560");
        String requestStr = JSONObject.toJSONString(liePinJsonObject);
        Message requestMsg = new Message(requestStr.getBytes("UTF-8"), null);
        receiverHandler.handlerPositionLiepinDownShelfOperation(requestMsg, null);
    }

//    @Test
    public void testRePublish() throws UnsupportedEncodingException {
        JSONObject liePinJsonObject = new JSONObject();
        liePinJsonObject.put("id", "19493518");
        String requestStr = JSONObject.toJSONString(liePinJsonObject);
        Message requestMsg = new Message(requestStr.getBytes("UTF-8"), null);
        receiverHandler.handlerPositionLiepinReSyncOperation(requestMsg, null);
    }

//    @Test
    public void testGetPosition() throws Exception {
        Integer positionId = 19493560;
        Integer id = 986122455;
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
}
