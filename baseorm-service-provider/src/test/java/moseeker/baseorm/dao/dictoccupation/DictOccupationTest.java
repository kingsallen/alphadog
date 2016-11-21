package moseeker.baseorm.dao.dictoccupation;

import java.util.HashMap;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.moseeker.baseorm.service.Impl.DictDaoServiceImpl;
import com.moseeker.baseorm.service.Impl.PositionServiceImpl;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.DictOccupationDao;

public class DictOccupationTest {
//	private DictDaoServiceImpl service;
//	private PositionServiceImpl position;
//	public void init() {
//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//		context.scan("com.moseeker.baseorm");
//		context.refresh();
//		service = context.getBean(DictDaoServiceImpl.class);
//		position= context.getBean(PositionServiceImpl.class);
//	}
//	@Test
//	public void testGetAllOccupation(){
//		init();
//		//Response result=service.queryAll51Occupation();
//		Response result=service.occupations51();
//	}
//	@Test
//	public void getOccupation() throws Exception{
//		DictOccupationDao.Iface dictOccupationDao = ServiceManager.SERVICEMANAGER
//				.getService(DictOccupationDao.Iface.class);
////		Map<String,Object> map=new HashMap<String,Object>();
////		map.put("single_layer", false);
////		map.put("channel", 1);
////		String param=JSONObject.toJSONString(map);
//		Response result=dictOccupationDao.getOccupations51();
//		System.out.println(result);
//	}
//	@Test
//	public void testJobOccupation(){
//		init();
//		CommonQuery query=new CommonQuery();
//		HashMap<String,String> map=new HashMap<String,String>();
//		map.put("company_id", "1");
//		query.setEqualFilter(map);
//		Response result=position.getJobOccupation(query);
//		System.out.println(result.getData());
//	}
}
