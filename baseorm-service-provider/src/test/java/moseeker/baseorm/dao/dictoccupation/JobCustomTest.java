package moseeker.baseorm.dao.dictoccupation;

import java.util.HashMap;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.baseorm.service.Impl.PositionServiceImpl;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.PositionDao;

public class JobCustomTest {
//	private PositionServiceImpl position;
//	public void init() {
//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//		context.scan("com.moseeker.baseorm");
//		context.refresh();
//		position= context.getBean(PositionServiceImpl.class);
//	}
//	@Test
//	public void getCustomField() throws Exception{
//		PositionDao.Iface position = ServiceManager.SERVICEMANAGER
//				.getService(PositionDao.Iface.class);
//		CommonQuery query=new CommonQuery();
//		HashMap<String,String> map=new HashMap<String,String>();
//		map.put("company_id", "1");
//		query.setEqualFilter(map);
//		Response result=position.getJobCustoms(query);
//		System.out.println(result);
//	}
}
