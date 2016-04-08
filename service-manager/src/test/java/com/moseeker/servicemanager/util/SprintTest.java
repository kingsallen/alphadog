package com.moseeker.servicemanager.util;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.moseeker.servicemanager.common.Spring;
import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerQuery;

public class SprintTest {

	@Test
	public void initCommonQueryTest() {
		MockHttpServletRequest request = new MockHttpServletRequest("GET",
				"/main.app?appid=1&limit=10&offset=2&page=1&per_page=10&sortby=id&fields=id&fields=name&nocache=1");
		String[] appid = { "1" };
		String[] limit = { "10" };
		String[] offset = { "2" };
		String[] page = { "1" };
		String[] per_page = { "10" };
		String[] sortby = { "id" };
		String[] order = { "id, create_time" };
		String[] fields = { "id, name" };
		String[] nocache = { "1" };
		request.setParameter("appid", appid);
		request.setParameter("limit", limit);
		request.setParameter("offset", offset);
		request.setParameter("page", page);
		request.setParameter("per_page", per_page);
		request.setParameter("sortby", sortby);
		request.setParameter("order", order);
		request.setParameter("fields", fields);
		request.setParameter("nocache", nocache);
		CompanyfollowerQuery query;
		try {
			query = Spring.initCommonQuery(request, CompanyfollowerQuery.class);

			assertEquals(query.getAppid(), 1);
			assertEquals(query.getLimit(), 10);
			assertEquals(query.getOffset(), 2);
			assertEquals(query.getPage(), 1);
			assertEquals(query.getPer_page(), 10);
			assertEquals(query.getSortby(), "id");
			assertEquals(query.getOrder(), "id, create_time");
			System.out.println(query.getFields());
			// assertEquals(query.getFields(), "id, name");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
