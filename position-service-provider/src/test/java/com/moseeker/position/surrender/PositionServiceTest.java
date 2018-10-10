package com.moseeker.position.surrender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.moseeker.position.service.third.ThirdPositionService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.position.struct.*;
import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.moseeker.position.config.AppConfig;
import com.moseeker.position.service.fundationbs.PositionService;
import com.moseeker.thrift.gen.common.struct.Response;

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

    @Test
    public void test() throws Exception {
    	List<Integer> list=new ArrayList<Integer>();
    	list.add(124340);
    	list.add(124341);
    	list.add(124342);
        List<RpExtInfo> res= service.getNewPositionListRpExt(list);
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

    /*@Test
    public void cityCode(){
    	List<City> cities=new ArrayList<>();
    	City city=new City();
    	city.setType("text");
    	city.setValue("九龙城区");
    	cities.add(city);
    	service.cityCode(cities,1909944);
	}*/

//	@Test
//	@Commit
//	public void batchHandlerJobPostion() throws BIZException {
//		BatchHandlerJobPostion batchHandlerJobPostion=new BatchHandlerJobPostion();
//
//		JobPostrionObj jobPostrionObj=JSON.toJavaObject(JSON.parseObject(position),JobPostrionObj.class);
//
//		batchHandlerJobPostion.setData(Arrays.asList(jobPostrionObj,jobPostrionObj));
//		batchHandlerJobPostion.setFields_nooverwrite("");
//		batchHandlerJobPostion.setNodelete(true);
//		batchHandlerJobPostion.setFields_nohash("");
//		batchHandlerJobPostion.setIsCreateDeparment(true);
//
//
//		service.batchHandlerJobPostionAdapter(batchHandlerJobPostion);
//	}
	@Test
	@Commit
	public void batchHandlerJobPostion() throws BIZException {
		BatchHandlerJobPostion batchHandlerJobPostion=new BatchHandlerJobPostion();

		JobPostrionObj jobPostrionObj=JSON.toJavaObject(JSON.parseObject(position),JobPostrionObj.class);

		batchHandlerJobPostion.setData(Arrays.asList(jobPostrionObj,jobPostrionObj));
		batchHandlerJobPostion.setFields_nooverwrite("");
		batchHandlerJobPostion.setNodelete(true);
		batchHandlerJobPostion.setFields_nohash("");
		batchHandlerJobPostion.setIsCreateDeparment(true);


		try {
			service.batchHandlerJobPostionAdapter(batchHandlerJobPostion);
		} catch (TException e) {
			e.printStackTrace();
		}
	}


	private String position="{\n" +
			"  'degree': 0,\n" +
			"  'keyword': '',\n" +
			"  'language': '',\n" +
			"  'reporting_to': '',\n" +
			"  'business_group': '',\n" +
			"  'district': '',\n" +
			"  'major_required': '',\n" +
			"  'employment_type': 0,\n" +
			"  'stop_date': 'None',\n" +
			"  'source_id': 10,\n" +
			"  'source': 9,\n" +
			"  'app_cv_config_id': 0,\n" +
			"  'department': '上海研发6',\n" +
			"  'custom': '银弹奖金',\n" +
			"  'experience_above': false,\n" +
			"  'hr_email': 'huanqing.chen@uisee.com',\n" +
			"  'experience': '',\n" +
			"  'candidate_source': 0,\n" +
			"  'age': 0,\n" +
			"  'work_address': '',\n" +
			"  'position_code': 0,\n" +
			"  'title': '智能驾驶软件工程师',\n" +
			"  'management_experience': 1,\n" +
			"  'underlings': 0,\n" +
			"  'email_notice': 1,\n" +
			"  'company_id': 39978,\n" +
			"  'is_hiring': false,\n" +
			"  'city': [\n" +
			"    {\n" +
			"      'type': 'text',\n" +
			"      'value': '上海'\n" +
			"    }\n" +
			"  ],\n" +
			"  'industry': '',\n" +
			"  'email_resume_conf': 0,\n" +
			"  'occupation': '正式岗位',\n" +
			"  'degree_above': false,\n" +
			"  'team_id': 0,\n" +
			"  'extra': null,\n" +
			"  'feature': '',\n" +
			"  'jobnumber': '07cd6a55-76a8-42ab-89da-33c083630d80',\n" +
			"  'salary_top': 0,\n" +
			"  'priority': '',\n" +
			"  'publisher': 91342,\n" +
			"  'count': 2,\n" +
			"  'language_required': 0,\n" +
			"  'salary_bottom': 0,\n" +
			"  'gender': 2,\n" +
			"  'province': ''\n" +
			"}";
}
