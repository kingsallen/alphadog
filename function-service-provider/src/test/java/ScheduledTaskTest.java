import com.alibaba.fastjson.JSON;
import com.moseeker.function.config.AppConfig;
import com.moseeker.function.service.ScheduledTask;
import com.moseeker.function.service.chaos.PositionForSyncResultPojo;
import com.moseeker.function.service.chaos.PositionSyncConsumer;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfo;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@PropertySource("classpath:common.properties")
public class ScheduledTaskTest {

    @Autowired
    ScheduledTask scheduledTask;

    @Autowired
    PositionSyncConsumer positionSyncConsumer;

    @Test
    public void test(){
        /*String data="{\"message\":\"[\\\"\\\\u60a8\\\\u76ee\\\\u524d\\\\u804c\\\\u4f4d\\\\u53d1\\\\u5e03\\\\u6570\\\\u4e0d\\\\u8db3\\\\uff0c\\\\u8bf7\\\\u91cd\\\\u65b0\\\\u8d2d\\\\u4e70\\\\u804c\\\\u4f4d\\\\u6570\\\\u3002\\\"]\",\"data\":{\"channel\":\"1\",\"accountId\":\"560\",\"positionId\":\"1017427\"},\"operation\":\"publish\",\"status\":1}";

        PositionForSyncResultPojo pojo= JSON.parseObject(data, PositionForSyncResultPojo.class);
        positionSyncConsumer.positionSyncComplete(pojo);*/

        ThirdPartyAccountInfoParam param=new ThirdPartyAccountInfoParam();
        param.setHrId(10000);
        param.setChannel(3);
//        ThirdPartyAccountInfo info=thirdPartyAccountInfoServices.getAllInfo(param);
    }

}
