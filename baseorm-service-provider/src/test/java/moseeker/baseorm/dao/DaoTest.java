//package moseeker.baseorm.dao;
//
//import java.util.HashMap;
//
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//import com.moseeker.baseorm.dao.dictdb.DictCityDao;
//import com.moseeker.baseorm.dao.hrdb.HrAppCvConfDao;
//import com.moseeker.baseorm.dao.userdb.UserUserDao;
//import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
//import com.moseeker.baseorm.db.hrdb.tables.records.HrAppCvConfRecord;
//import com.moseeker.baseorm.util.BeanUtils;
//import com.moseeker.thrift.gen.common.struct.Query;
//import com.moseeker.thrift.gen.dao.struct.CURDException;
//import com.moseeker.thrift.gen.dao.struct.CandidatePositionDO;
//import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
//import com.moseeker.thrift.gen.dao.struct.hrdb.HrAppCvConfDO;
//import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
//
//
//public class DaoTest {
//	
//	AnnotationConfigApplicationContext annConfig = null;
//
//	@Before
//	public void init() {
//		annConfig = new AnnotationConfigApplicationContext();
//		annConfig.scan("com.moseeker.baseorm");
//		annConfig.refresh();
//	}
//	
//	//@Test
//	public void dictCityDaoTest() throws Exception {
//		DictCityDao dictCityDao = annConfig.getBean(DictCityDao.class);
//		Query query = new Query();
//		query.setEqualFilter(new HashMap<String, String>());
//		query.getEqualFilter().put("code", "110000");
//		DictCityRecord record = dictCityDao.getResource(query);
//		DictCityDO struct = BeanUtils.DBToStruct(DictCityDO.class, record);
//		System.out.println(record);
//		System.out.println(struct);
//	}
//	
//	//@Test
//	public void hrAppCvConfDaoTest() throws Exception {
//		HrAppCvConfDao appCvConfDao = annConfig.getBean(HrAppCvConfDao.class);
//		Query query = new Query();
//		query.setEqualFilter(new HashMap<String, String>());
//		query.getEqualFilter().put("code", "110000");
//		HrAppCvConfRecord record = appCvConfDao.getResource(query);
//		HrAppCvConfDO struct = BeanUtils.DBToStruct(HrAppCvConfDO.class, record);
//		System.out.println(record);
//		System.out.println(struct);
//	}
//	
//	
//	//@Test
//	public void userDaoTest() throws Exception {
//		UserUserDao userDao = annConfig.getBean(UserUserDao.class);
//		UserUserDO userDO = new UserUserDO();
//		userDO.setLastLoginIp("10.172.169.163");
//		userDO.setId(1122611);
//		userDao.updateResource(userDO);
//	}
//}
