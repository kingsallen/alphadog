package com.moseeker.baseorm.dao.hrdao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.dao.service.HrDBDao;
import com.moseeker.thrift.gen.dao.struct.HrHbConfigPojo;
import com.moseeker.thrift.gen.dao.struct.HrHbItemsPojo;
import com.moseeker.thrift.gen.dao.struct.HrHbPositionBindingPojo;
import com.moseeker.thrift.gen.dao.struct.HrHbScratchCardPojo;
import com.moseeker.thrift.gen.dao.struct.HrHbSendRecordPojo;

public class HrDaoTest {
	
	private HrDBDao.Iface hrDao;
	
	@Before public void initialize() {
		hrDao = ServiceManager.SERVICEMANAGER.getService(HrDBDao.Iface.class);
	}
	
	@Test
	public void testGetHrHbConfig() {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", "1");
		
		try {
			HrHbConfigPojo result = hrDao.getHbConfig(qu);
			assertEquals(result.getId(), 1);
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetHrHbItemsList() {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", "[1,2]");

		try {
			List<HrHbItemsPojo> result = hrDao.getHbItems(qu);
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
			HrHbPositionBindingPojo result = hrDao.getHbPositionBinding(qu);
			assertEquals(result.getId(), 1);
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetHrHbScratchCard() {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", "1");
		
		try {
			HrHbScratchCardPojo result = hrDao.getHbScratchCard(qu);
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
			HrHbSendRecordPojo result = hrDao.getHbSendRecord(qu);
			assertTrue(result != null);
		} catch (TException e) {
			e.printStackTrace();
		}
	}
}
