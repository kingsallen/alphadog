package com.moseeker.search;

import java.util.Map;

import com.moseeker.searchengine.util.SearchUtil;
import org.elasticsearch.action.search.SearchResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moseeker.searchengine.config.AppConfig;
import com.moseeker.searchengine.service.impl.CompanySearchengine;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =AppConfig.class)
public class CompanyIndexTest {
	@Autowired
	private CompanySearchengine companySearchengine;
	@Autowired
	private SearchUtil searchUtil;
	@Test
	public void queryTest() throws Exception{
		Map<String,Object> res=companySearchengine.query("上海那里", null, null, null, 1, 10);
		System.out.println(res);
	}
	public void queryStringTest() throws Exception{
		SearchResponse res=companySearchengine.queryString("上海那里", "", "", "", 1, 10,searchUtil.getEsClient());
		System.out.println(res);
	}
}