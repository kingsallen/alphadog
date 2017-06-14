//package com.moseeker.useraccounts.service;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertSame;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.when;
//
//import java.util.Arrays;
//
//import org.apache.thrift.TException;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import com.moseeker.thrift.gen.dao.service.SearcheConditionDao;
//import com.moseeker.thrift.gen.dao.service.TalentpoolDao;
//import com.moseeker.thrift.gen.dao.struct.Talentpool;
//import com.moseeker.thrift.gen.useraccounts.struct.SearchCondition;
//import com.moseeker.useraccounts.service.impl.UserHrAccountService;
//
//@RunWith(MockitoJUnitRunner.class)
//public class UserHrAccountServiceTest {
//	
//	@InjectMocks
//	public UserHrAccountService service;
//	
//	@Mock
//	public SearcheConditionDao.Iface searchConditionDao;
//	
//	@Mock
//	public TalentpoolDao.Iface talentpoolDao;
//	
//	//@Test
//	public void getSearchConditionTest() {
//		try {
//			// case 1: 请求成功
//			when(searchConditionDao.getResources(any())).thenReturn(Arrays.asList(new SearchCondition()));
//			assertSame(service.getSearchCondition(50, 0).getStatus(), 0);
//			// case 2: 出现异常
//			when(searchConditionDao.getResources(any())).thenThrow(TException.class);
//			assertEquals(service.getSearchCondition(50, 0).getStatus(), 99999);
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	//@Test
//	public void postSearchConditionTest() {
//		try {
//			SearchCondition searchCondition = new SearchCondition();
//			searchCondition.setHr_account_id(50);
//			searchCondition.setType(1);
//			when(searchConditionDao.postResource(any())).thenThrow(TException.class).thenReturn(0).thenReturn(1);
//			when(searchConditionDao.getResourceCount(any())).thenReturn(10, 1, 1, 1, 0, 1, 0, 1, 0);
//			// case 1: hr筛选项 >= 10 条
//			assertEquals(service.postSearchCondition(searchCondition).getStatus(), 42004);
//			// case 2: hr筛选项名字重复
//			assertEquals(service.postSearchCondition(searchCondition).getStatus(), 42004);
//			// case 2: hr筛选项 < 10 条 if postResource 出现异常
//			assertEquals(service.postSearchCondition(searchCondition).getStatus(), 99999);
//			// case 3: hr筛选项 < 10 条 if 保存失败
//			assertEquals(service.postSearchCondition(searchCondition).getStatus(), 42004);
//			// case 3: hr筛选项 < 10 条 if 保存成功
//			assertEquals(service.postSearchCondition(searchCondition).getStatus(), 0);
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	//@Test
//	public void delSearchConditionTest() {
//		try {
//			when(searchConditionDao.delResource(anyInt(), anyInt())).thenThrow(TException.class).thenReturn(0).thenReturn(1);
//			// case 1: 出现异常
//			assertEquals(service.delSearchCondition(50, 0).getStatus(), 99999);
//			// case 2: 删除失败
//			assertEquals(service.delSearchCondition(50, 0).getStatus(), 42004);
//			// case 3: 删除成功
//			assertEquals(service.delSearchCondition(50, 0).getStatus(), 0);
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	//@Test
//	public void joinTalentpoolTest() {
//		try {
//			when(talentpoolDao.getResource(any())).thenThrow(TException.class).thenReturn(null, null, new Talentpool(0, 50, 22, "", "", 1), new Talentpool(1, 50, 22, "", "", 1));
//			when(talentpoolDao.postResource(any())).thenThrow(TException.class).thenReturn(0).thenReturn(1);
//			when(talentpoolDao.putResource(any())).thenThrow(TException.class).thenReturn(0).thenReturn(1);
//			// case 1: 出现异常
//			assertEquals(service.joinTalentpool(50, Arrays.asList(0)).getStatus(), 99999);
//			// case 2: 候选人不存在人才库，将其加入人才库 if 数据插入异常
//			assertEquals(service.joinTalentpool(50, Arrays.asList(0)).getStatus(), 99999);
//			// case 3: 候选人不存在人才库，将其加入人才库 if 数据插入失败
//			assertEquals(service.joinTalentpool(50, Arrays.asList(0)).getStatus(), 42004);
//			// case 4: 候选人不存在人才库，将其加入人才库 if 数据插入成功
//			assertEquals(service.joinTalentpool(50, Arrays.asList(0)).getStatus(), 0);
//			// case 4: 候选人存在，将其状态修改为正常 if 数据修改异常
//			assertEquals(service.joinTalentpool(50, Arrays.asList(0)).getStatus(), 99999);
//			// case 5: 候选人存在，将其状态修改为正常 if 数据修改失败
//			assertEquals(service.joinTalentpool(50, Arrays.asList(0)).getStatus(), 42004);
//			// case 6: 候选人存在，将其状态修改为正常 if 数据修改成功
//			assertEquals(service.joinTalentpool(50, Arrays.asList(0,1)).getStatus(), 0);
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	//@Test
//	public void shiftOutTalentpoolTest() {
//		try {
//			when(talentpoolDao.getResource(any())).thenThrow(TException.class).thenReturn(null, new Talentpool(0, 50, 22, "", "", 0), new Talentpool(1, 50, 22, "", "", 0));
//			when(talentpoolDao.putResource(any())).thenThrow(TException.class).thenReturn(0).thenReturn(1);
//			// case 1: 出现异常
//			assertEquals(service.shiftOutTalentpool(50, Arrays.asList(0)).getStatus(), 99999);
//			// case 2: 候选人不存在人才库 talentpool == null，不做处理
//			assertEquals(service.shiftOutTalentpool(50, Arrays.asList(0)).getStatus(), 42004);
//			// case 3: 候选人不存在人才库 talentpool.getId() == 0，不做处理
//			assertEquals(service.shiftOutTalentpool(50, Arrays.asList(0)).getStatus(), 42004);
//			// case 4: 候选人存在，将其状态修改为删除 if 数据修改异常
//			assertEquals(service.shiftOutTalentpool(50, Arrays.asList(0)).getStatus(), 99999);
//			// case 5: 候选人存在，将其状态修改为正常 if 数据修改失败
//			assertEquals(service.shiftOutTalentpool(50, Arrays.asList(0)).getStatus(), 42004);
//			// case 6: 候选人存在，将其状态修改为正常 if 数据修改成功
//			assertEquals(service.shiftOutTalentpool(50, Arrays.asList(0,1)).getStatus(), 0);
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
// 	
//}
