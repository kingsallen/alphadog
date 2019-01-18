import com.moseeker.position.config.AppConfig;
import com.moseeker.position.thrift.ThirdPartyAccountInfoServiceImpl;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfo;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@Configuration
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
//@PropertySource("classpath:common.properties")
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
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String json = "[\\\"\\\\u4f1a\\\\u5458\\\\u540d\\\\u3001\\\\u7528\\\\u6237\\\\u540d\\\\u6216\\\\u5bc6\\\\u7801\\\\u9519\\\\u8bef\\\\uff0c\\\\u8bf7\\\\u91cd\\\\u65b0\\\\u7ed1\\\\u5b9a\\\\u8d26\\\\u53f7\\\"]";
        json = StringEscapeUtils.unescapeJava(json);
        System.out.println(json);
    }
}
