package test.com.moseeker.position.service;

import com.moseeker.position.service.fundationbs.PositionService;
import com.moseeker.thrift.gen.common.struct.Response;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PositionServiceTest {

    public static AnnotationConfigApplicationContext initSpring() {
        AnnotationConfigApplicationContext annConfig = new AnnotationConfigApplicationContext();
        annConfig.scan("com.moseeker.position");
        annConfig.refresh();
        return annConfig;
    }




    @Test
    public void headImageTest() {
        AnnotationConfigApplicationContext acac = initSpring();
        try {
            PositionService positionService = acac.getBean(PositionService.class);
            Response response = positionService.headImage();
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
