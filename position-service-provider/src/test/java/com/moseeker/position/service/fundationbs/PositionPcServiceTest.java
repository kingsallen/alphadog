package com.moseeker.position.service.fundationbs;

import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignPcRecommendPositionDO;

import org.apache.thrift.TException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;

import com.alibaba.fastjson.JSON;
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
    public void recommendPcPosition() throws TException{
        int page=1;
        int pageSize=10;
        Response res=service.getRecommendPositionPC(page,pageSize);
        System.out.println(res);
    }
  @Test
  public void getPositionNumTest(){
	  List<Integer> list=new ArrayList<Integer>();
	  list.add(82671);
	  list.add(82673);
	  list.add(82691);
	  list.add(82752);
	  list.add(82753);
	  list.add(82755);
	  list.add(82756);
	  list.add(82767);
	  list.add(82787);
	  list.add(82794);
	  list.add(82795);
	  list.add(82796);
	  list.add(82797);
	  list.add(82798);
	  list.add(82800);
	  list.add(82805);
	  list.add(82806);
	  list.add(82807);
	  list.add(82808);
	  list.add(82827);
	  list.add(82851);
	  list.add(82852);
	  list.add(82862);
	  list.add(82864);
	  list.add(82948);
	  list.add(82976);
	  list.add(82980);
	  list.add(82980);
	  int num=service.getPositionNum(list);
	  System.out.println("职位的数量是＝＝＝＝＝＝＝"+num);
  }
  
  @Test
  public void getQXRecommendCompanyListTest() throws TException{
	  Response res=service.getQXRecommendCompanyList(1,10);
	  System.out.println(res);
  }
  @Test
  public void getJDMaps() throws TException{
	  List<Integer> list=new ArrayList<Integer>();
	  list.add(1404);
	  list.add(1414);
////	  List<Map<String,Object>> map=(List<Map<String, Object>>) service.HandleCmsResource(list,2);
//	  for(Map<String,Object> map1:map){
//		  System.out.println(JSON.toJSONString(map1));
//	  }
  }
  
  @Test
  public void getAllCompanyRecommendTest() throws Exception{
	  List<Map<String,Object>> list=service.getAllCompanyRecommend(1,10);
	  System.out.println(list);
  }
}
