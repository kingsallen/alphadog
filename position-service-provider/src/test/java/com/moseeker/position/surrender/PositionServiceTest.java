package com.moseeker.position.surrender;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.moseeker.position.service.third.ThirdPositionService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.position.struct.City;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionInfoForm;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.moseeker.position.config.AppConfig;
import com.moseeker.position.service.fundationbs.PositionService;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.struct.RpExtInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =AppConfig.class)
@Transactional
public class PositionServiceTest {
	@Autowired
	private PositionService service;
	//@Test
	public void getRecommendedPositionsTest(){
		Response res=service.getRecommendedPositions(106002);
		System.out.println(res);
	}
	//@Test
	public void verifyCustomizeTest() throws Exception{
		Response res=service.verifyCustomize(106002);
		System.out.println(res);
	}
//	@Test
	public void getPositionByIdTest() throws Exception{
		Response res=service.getPositionById(80571);
		System.out.println(res);
	}
	//@Test
	public  void deleteJobpositionTest(){
		Response res=service.deleteJobposition(106002,0,"",0);
		System.out.println(res);
	}
	//@Test
	public void getTeamIdbyDepartmentNameTest(){
		Response res=service.getTeamIdbyDepartmentName(71176,"法务部");
		System.out.println(res);
	}
	//@Test
	public void ifAllowRefreshTest(){
//		boolean bool=service.ifAllowRefresh(124445, 82671);
//		System.out.println(bool+"==================");
	}

//    @Test
    public void test() throws Exception {
    	List<Integer> list=new ArrayList<Integer>();
    	list.add(124340);
    	list.add(124341);
    	list.add(124342);
        List<RpExtInfo> res= service.getPositionListRpExt(list);
        System.out.println(res);
    }

    @Autowired
    ThirdPositionService thirdPositionService;

//    @Test
    public void testThirdPartyPosition() throws BIZException {
        ThirdPartyPositionInfoForm infoForm = new ThirdPartyPositionInfoForm();
        ThirdPartyPositionResult result = thirdPositionService.getThirdPartyPositionInfo(infoForm);

        System.out.println(JSON.toJSONString(result));
    }

    /*@Test
    public void cityCode(){
    	List<City> cities=new ArrayList<>();
    	City city=new City();
    	city.setType("text");
    	city.setValue("九龙城区");
    	cities.add(city);
    	service.cityCode(cities,1909944);
	}*/

    @Test
	public void testCitys(){
    	List<City> list=new ArrayList<>();
    	City city=new City();
    	city.setType("text");
    	city.setValue("");
    	list.add(city);

		city=new City();
		city.setType("text");
		list.add(city);

		city=new City();
		city.setType("text");
		city.setValue("");
		list.add(city);

    	city=new City();
		city.setType("text");
		city.setValue("北京");
		list.add(city);

		city=new City();
		city.setType("text");
		city.setValue("北京");
		list.add(city);
//    	System.out.println(service.citys(list));
	}
}
