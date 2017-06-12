//package com.moseeker.position.surrender;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.moseeker.position.config.AppConfig;
//import com.moseeker.position.service.fundationbs.PositionQxService;
//import com.moseeker.thrift.gen.dao.struct.CampaignHeadImageVO;
//import com.moseeker.thrift.gen.position.struct.PositionDetailsListVO;
//import com.moseeker.thrift.gen.position.struct.PositionDetailsVO;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes =AppConfig.class)
//@Transactional
//public class PositionQXTest {
//	@Autowired
//	private PositionQxService service;
//	//@Test
//	public void headImageTest(){
//		CampaignHeadImageVO vo=service.headImage();
//		System.out.println(vo);
//	}
//	//@Test
//	public void positionDetailsTest(){
//		PositionDetailsVO vo=service.positionDetails(111872);
//		System.out.println(vo);
//	}
//	//@Test
//	public void companyHotPositionDetailsListTest(){
//		PositionDetailsListVO vo=service.companyHotPositionDetailsList(39978, 1, 10);
//		System.out.println(vo);
//	}
//	//@Test
//	public void similarityPositionDetailsListTest(){
//		PositionDetailsListVO vo=service.similarityPositionDetailsList(39978, 1, 10);
//		System.out.println(vo);
//	}
//}
