package com.moseeker.baseorm.dao.hrdao;

import com.moseeker.baseorm.service.HrDaoService;
import com.moseeker.baseorm.service.Impl.HrDaoServiceImpl;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.thrift.gen.dao.struct.HrHbConfigPojo;
import com.moseeker.thrift.gen.dao.struct.HrHbItemsPojo;
import com.moseeker.thrift.gen.dao.struct.HrHbPositionBindingPojo;
import com.moseeker.thrift.gen.dao.struct.HrHbScratchCardPojo;
import com.moseeker.thrift.gen.dao.struct.HrHbSendRecordPojo;
import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HrDaoTest {
	
	private HrDaoService service;
	
	@Before public void initialize() {
		service = new HrDaoServiceImpl();
	}

	@Test
	public void testGetHrHbConfig() {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", "1");
		
		try {
			HrHbConfigPojo result = service.getHbConfig(qu);
			System.out.println(result);
			assertEquals(result.getId(), 1);
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetHrHbConfigList() {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", "[1,2]");

		try {
			List<HrHbConfigPojo> result = service.getHbConfigs(qu);
			assertEquals(result.size(), 2);
			assertEquals(result.stream().mapToInt(HrHbConfigPojo::getId).sum(), 3);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetHrHbItemsList() {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", "[1,2]");

		try {
			List<HrHbItemsPojo> result = service.getHbItems(qu);
			assertEquals(result.size(), 2);
			assertEquals(result.stream().mapToInt(HrHbItemsPojo::getId).sum(), 3);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetHrHbPositionBindingPojo() {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", "1");
		
		try {
			HrHbPositionBindingPojo result = service.getHbPositionBinding(qu);
			assertEquals(result.getId(), 1);
		} catch (TException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetHrHbPositionBindingPojoList() {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", "[1,2]");

		try {
			List<HrHbPositionBindingPojo> result = service.getHbPositionBindings(qu);
			assertEquals(result.size(), 2);
			assertEquals(result.stream().mapToInt(HrHbPositionBindingPojo::getId).sum(), 3);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetHrHbScratchCard() {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", "1");
		
		try {
			HrHbScratchCardPojo result = service.getHbScratchCard(qu);
			assertEquals(result.getId(), 1);
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetHrSendRecord() {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", "1");
		
		try {
			HrHbSendRecordPojo result = service.getHbSendRecord(qu);
			assertTrue(result != null);
		} catch (TException e) {
			e.printStackTrace();
		}
	}
}
