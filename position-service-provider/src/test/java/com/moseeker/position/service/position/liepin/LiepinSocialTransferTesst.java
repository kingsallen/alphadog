package com.moseeker.position.service.position.liepin;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.common.util.query.Query;
import com.moseeker.position.config.AppConfig;
import com.moseeker.position.pojo.LiePinPositionVO;
import com.moseeker.position.service.position.base.sync.AbstractPositionTransfer;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cjm
 * @date 2018-06-05 14:26
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class LiepinSocialTransferTesst {
    @Autowired
    LiepinSocialPositionTransfer liepinSocialPositionTransfer;

    @Autowired
    JobPositionDao jobPositionDao;

    @Autowired
    HRThirdPartyPositionDao dao;

    public LiePinPositionVO testChangeToThirdPartyPosition(ThirdPartyPosition positionForm, JobPositionDO moseekerJobPosition) throws Exception {
        LiePinPositionVO liePinPositionVO = liepinSocialPositionTransfer.changeToThirdPartyPosition(positionForm, moseekerJobPosition, null);
        System.out.println(liePinPositionVO);
        return liePinPositionVO;
    }


    public HrThirdPartyPositionDO testToThirdPartyPosition(ThirdPartyPosition positionForm, LiePinPositionVO liePinPositionVO) throws Exception {
        HrThirdPartyPositionDO hrThirdPartyPositionDO = liepinSocialPositionTransfer.toThirdPartyPosition(positionForm, liePinPositionVO);
        System.out.println(hrThirdPartyPositionDO);
        return hrThirdPartyPositionDO;
    }

    @Test
    public void testSendSyncRequest() throws Exception {
        Integer positionId = 19494081;
        ThirdPartyPosition positionForm = new ThirdPartyPosition();
        //软件/互联网开发/系统集成
        List<String> list = new ArrayList<>();
        list.add("030");
        list.add("030070");
        List<String> list1 = new ArrayList<>();
        list1.add("050");
        list1.add("050040");
        List<String> list2 = new ArrayList<>();
        list2.add("060");
        list2.add("060070");
        List<String> end = new ArrayList<>();
        end.add(JSON.toJSONString(list));
        end.add(JSON.toJSONString(list1));
        end.add(JSON.toJSONString(list2));
        System.out.println(end);
        positionForm.setOccupation(end);
        positionForm.setSalaryDiscuss(true);
        positionForm.setSalaryBottom(4000);
        positionForm.setSalaryTop(12000);
        positionForm.setSalaryMonth(12);
        List<String> featureList = new ArrayList<>();
        String features = "";
        featureList.add("五险一金");
        featureList.add("sadl;sdkas;kdk;addd;a");
        featureList.add("领导好");
        featureList.add("15薪");
        positionForm.setFeature(featureList);
        Query query = new Query.QueryBuilder().where(JobPosition.JOB_POSITION.ID.getName(), positionId).buildQuery();
        JobPositionDO moseekerJobPosition = jobPositionDao.getData(query);

        AbstractPositionTransfer.TransferResult<LiePinPositionVO, LiePinPositionVO> result = new AbstractPositionTransfer.TransferResult<LiePinPositionVO, LiePinPositionVO>();
        LiePinPositionVO liePinPositionVO = testChangeToThirdPartyPosition(positionForm, moseekerJobPosition);
        HrThirdPartyPositionDO hrThirdPartyPositionDO = testToThirdPartyPosition(positionForm, liePinPositionVO);
        result.setPositionWithAccount(liePinPositionVO);
        result.setThirdPartyPositionDO(hrThirdPartyPositionDO);
        liepinSocialPositionTransfer.sendSyncRequest(result);
    }


    @Test
    public void toThirdPartyPositionForm(){
        HrThirdPartyPositionDO hrThirdPartyPositionDO = new HrThirdPartyPositionDO();
        hrThirdPartyPositionDO.setFeature("福利特色,五险一金");
        hrThirdPartyPositionDO.setOccupation("123,1231");
        liepinSocialPositionTransfer.toThirdPartyPositionForm(hrThirdPartyPositionDO, null);
    }

}
