package com.moseeker.position.service.fundationbs;

import com.moseeker.baseorm.dao.dictdb.DictLiepinOccupationDao;
import com.moseeker.entity.PcRevisionEntity;
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
    @Autowired
	private PcRevisionEntity pcRevisionEntity;
    @Autowired
	private DictLiepinOccupationDao dao;
    //测试总接口
    @Test
    public void recommendPcPosition() throws TException{
        int page=1;
        int pageSize=10;
        Response res=service.getRecommendPositionPC(page,pageSize);
        System.out.println(res);
    }
	  //获取公司下的职位数量
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
	  //获取千寻推荐的企业
	  @Test
	  public void getQXRecommendCompanyListTest() throws TException{
		  Response res=service.getQXRecommendCompanyList(1,10);
		  System.out.println(res);
	  }
	  //获取jd
	  @Test
	  public void getJDMaps() throws TException{
		  List<Integer> list=new ArrayList<Integer>();
		  list.add(1404);
		  list.add(1414);
		  List<Map<String,Object>> map=(List<Map<String, Object>>) pcRevisionEntity.HandleCmsResource(list,2);
		  for(Map<String,Object> map1:map){
			  System.out.println(JSON.toJSONString(map1));
		  }
	  }
	  //获取所有的推荐公司
	  @Test
	  public void getAllCompanyRecommendTest() throws Exception{
		  List<Map<String,Object>> list=service.getAllCompanyRecommend(1,10);
		  System.out.println(list);
	  }
	  //获取职位详情
	  @Test
	  public void getPositionDetailsTest() throws Exception {
		  Map<String,Object> result=service.getPositionDetails(124596);
		  System.out.println(result);
	  }
	  //获取推荐职位
	  @Test
	  public void getRecommendTest() throws TException {
		 List<Map<String,Object>> list=service.getRecommendPosition(124597,1,10);
		 System.out.println(list);
	  }
	  @Test
	  public void testGetAll(){
		List list=dao.getAll();
		System.out.println(list);
	  }
	  @Test
      public void testAdvertisement() throws TException {
          List<Map<String,Object>> list=service.getAdvertisement(1,10);
          System.out.println(list);
      }
      @Test
	  public void testModulePosition() throws TException {
		  Map<String,Object> map=service.getModuleRecommendPosition(1,10,2);
		  System.out.println(map);
	  }

}
