package com.moseeker.position.service.fundationbs;

import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignPcRecommendPositionDO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;
import com.moseeker.position.config.AppConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by zztaiwll on 17/6/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class PositionPcServiceTest {
    @Autowired
    private PositionPcService service;
    //测试总接口
    @Test
    public void recommendPcPosition(){
        int page=1;
        int pageSize=10;
        Response res=service.getRecommendPositionPC(page,pageSize);
        System.out.println(res);
    }
    //测试获取推荐表当中的数据
    @Test
    public void getPcRemmendPositionIdListTest(){
        int page=1;
        int pageSize=10;
        List<CampaignPcRecommendPositionDO> list=service.getPcRemmendPositionIdList(page,pageSize);
        System.out.println(list);
    }
    @Test
    public void getTeamNumTest(){
    	List<Integer> list=new ArrayList<Integer>();
    	list.add(39978);
    	list.add(71176);
    	List<Map> result=service.getTeamNum(list);
    	System.out.println(result);
    	
    }

}
