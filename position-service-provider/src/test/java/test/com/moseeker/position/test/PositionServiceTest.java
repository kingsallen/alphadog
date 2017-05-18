package test.com.moseeker.position.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.moseeker.position.service.fundationbs.PositionService;
import com.moseeker.thrift.gen.common.struct.Response;

public class PositionServiceTest {
	private AnnotationConfigApplicationContext acac;
	public static AnnotationConfigApplicationContext initSpring() {
        AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
        acac.scan("com.moseeker.position");
        acac.scan("com.moseeker.common.aop.iface");
        acac.scan("com.moseeker.baseorm");
        acac.refresh();
        return acac;
    }
	@Test
	public void getRecommendedPositionsTest(){
		acac=initSpring();
		PositionService service=acac.getBean(PositionService.class);
		Response res=service.getRecommendedPositions(106002);
		System.out.println(res);
	}
	@Test
	public void verifyCustomizeTest() throws Exception{
		acac=initSpring();
		PositionService service=acac.getBean(PositionService.class);
		Response res=service.verifyCustomize(106002);
		System.out.println(res);
	}
	@Test
	public void getPositionByIdTest() throws Exception{
		acac=initSpring();
		PositionService service=acac.getBean(PositionService.class);
		Response res=service.getPositionById(106002);
		System.out.println(res);
	}
	@Test
	public  void deleteJobpositionTest(){
		acac=initSpring();
		PositionService service=acac.getBean(PositionService.class);
		Response res=service.deleteJobposition(106002,0,"",0);
		System.out.println(res);
	}
}
