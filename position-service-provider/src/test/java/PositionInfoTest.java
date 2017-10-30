import com.moseeker.position.config.AppConfig;
import com.moseeker.position.thrift.ThirdPartyAccountInfoServiceImpl;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfo;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import org.jooq.util.derby.sys.Sys;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@PropertySource("classpath:common.properties")
public class PositionInfoTest {

    @Autowired
    ThirdPartyAccountInfoServiceImpl thirdPartyAccountInfoServices;

    @Test
    public void test(){
        try{
            ThirdPartyAccountInfoParam param=new ThirdPartyAccountInfoParam();
            param.setHrId(10000);
            param.setChannel(3);
            ThirdPartyAccountInfo info=thirdPartyAccountInfoServices.getAllInfo(param);
        }catch (BIZException e){
            System.out.println(e.getCode()+":"+e.getMessage());
        }
    }
}
