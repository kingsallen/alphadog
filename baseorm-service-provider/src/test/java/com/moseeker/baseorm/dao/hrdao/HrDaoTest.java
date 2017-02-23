//package com.moseeker.baseorm.dao.hrdao;
//
//import com.moseeker.common.providerutils.QueryUtil;
//import com.moseeker.rpccenter.client.ServiceManager;
//import com.moseeker.thrift.gen.dao.service.HrDBDao;
//import com.moseeker.thrift.gen.dao.service.UserDBDao;
//import com.moseeker.thrift.gen.dao.struct.UserEmployeeDO;
//import com.moseeker.thrift.gen.dao.struct.UserEmployeePointsRecordDO;
//import org.apache.thrift.TException;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.List;
//
//
//
//public class HrDaoTest {
//
//	private UserDBDao.Iface userDao;
//
//	@Before
//	public void initialize() {
//		userDao = ServiceManager.SERVICEMANAGER.getService(UserDBDao.Iface.class);
//	}
//
//	@Test
//	public void testGet() {
//		QueryUtil qu = new QueryUtil();
//		qu.addEqualFilter("id", "1");
//
//		try {
//            com.moseeker.thrift.gen.dao.struct.UserEmployeeDO result = userDao.getEmployee(qu);
//			System.out.println(result);
//
//		} catch (TException e) {
//			e.printStackTrace();
//		}
//
//	}
//}




	/*
	@Test
	public void testGetHrHbConfig() {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", "1");
		
		try {
			HrHbConfigDO result = hrDao.getHbConfig(qu);
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
			List<HrHbItemsDO> result = hrDao.getHbItems(qu);
			assertEquals(result.size(), 2);
			assertEquals(result.stream().mapToInt(HrHbItemsDO::getId).sum(), 3);
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
			HrHbPositionBindingDO result = hrDao.getHbPositionBinding(qu);
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
			HrHbScratchCardDO result = hrDao.getHbScratchCard(qu);
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
			HrHbSendRecordDO result = hrDao.getHbSendRecord(qu);
			assertTrue(result != null);
		} catch (TException e) {
			e.printStackTrace();
		}
	}*/
//}
