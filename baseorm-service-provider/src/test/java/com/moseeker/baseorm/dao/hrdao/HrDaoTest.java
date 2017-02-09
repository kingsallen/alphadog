package com.moseeker.baseorm.dao.hrdao;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.dao.service.HrDBDao;
import com.moseeker.thrift.gen.dao.struct.HrEmployeeCertConfPojo;
import com.moseeker.thrift.gen.dao.struct.HrEmployeeCustomFieldsPojo;
import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class HrDaoTest {
	
//	private HrDBDao.Iface hrDao;
//
//	@Before
//	public void initialize() {
//		hrDao = ServiceManager.SERVICEMANAGER.getService(HrDBDao.Iface.class);
//	}
//
//	@Test
//	public void testGet() {
//		QueryUtil qu = new QueryUtil();
//		qu.addEqualFilter("company_id", "1");
//
//		try {
//			List<HrEmployeeCustomFieldsPojo> result = hrDao.getEmployeeCustomFields(qu);
//			result.forEach(e -> System.out.println(e));
//		} catch (TException e) {
//			e.printStackTrace();
//		}
//	}

	/*
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
	}*/
}
