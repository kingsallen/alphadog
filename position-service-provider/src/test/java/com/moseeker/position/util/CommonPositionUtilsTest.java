package com.moseeker.position.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moseeker.position.config.AppConfig;
import com.moseeker.position.utils.CommonPositionUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =AppConfig.class)
public class CommonPositionUtilsTest {
	@Autowired
	private CommonPositionUtils commonPositionUtils;
	//获取单个position对应的city
	@Test
	public void getJobPositionCityTest(){
		String cityName=commonPositionUtils.handlerCity(127364);
		System.out.println(cityName);
	}
	/*
	1009831, 1009833, 1009834, 1009837, 1009822, 1009821, 1009746, 1009503, 1009502, 1009501
	 */
	@Test
	public void handlePositionCityTest(){
		List<Integer> positionIds=new ArrayList<Integer>();
		positionIds.add(1009831);
        positionIds.add(1009833);
        positionIds.add(1009834);
        positionIds.add(1009837);
        positionIds.add(1009822);
        positionIds.add(1009821);
        positionIds.add(1009746);
        positionIds.add(1009503);
        positionIds.add(1009502);
        positionIds.add(1009501);
		Map<Integer,Set<String>> list=commonPositionUtils.handlePositionCity(positionIds);
		System.out.println(list);
	}

}
