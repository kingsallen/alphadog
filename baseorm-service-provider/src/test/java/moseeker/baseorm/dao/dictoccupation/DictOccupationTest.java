package moseeker.baseorm.dao.dictoccupation;

import java.util.HashMap;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.DictOccupationDao;
/*
 * @auth:zzt
 * function:测试第三方职位职能
 * time:2016-11-21
 */
public class DictOccupationTest {
	/*private DictDaoServiceImpl service;
	private PositionServiceImpl position;
	public void init() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.moseeker.baseorm");
		context.refresh();
		service = context.getBean(DictDaoServiceImpl.class);
		position= context.getBean(PositionServiceImpl.class);
	}
	//测试获取所有，直接调用接口
	//@Test
	public void test(){
		init();
		Query query=new Query();
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("level", "1");
		map.put("status", "1");
		query.setEqualFilter(map);
		Response result=service.occupationsZPin();
		System.out.println(result);
	}*/
//  //获取occupation，通过Iface
//	//@Test
//	public void getOccupation() throws Exception{
//		DictOccupationDao.Iface dictOccupationDao = ServiceManager.SERVICEMANAGER
//				.getService(DictOccupationDao.Iface.class);
//		Response result=dictOccupationDao.getOccupations51();
//		System.out.println(result);
//	}
//	//测试获取joboccupation
	/*//@Test
	public void testJobOccupation(){
		init();
		Query query=new Query();
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("company_id", "2");
		map.put("status", "1");
		query.setEqualFilter(map);
		Response result=position.getJobOccupation(query);
		System.out.println(result.getData());
	}*/
}
