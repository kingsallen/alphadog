package com.moseeker.search;

import java.util.Map;

import org.elasticsearch.search.SearchHits;
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
	@Test
	public void queryStringTest() throws Exception{
		Map<String,Object> res=companySearchengine.query("上海那里", null, null, null, 1, 10);
		System.out.println(res);
	}

}
