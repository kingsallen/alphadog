import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import java.util.Arrays;
import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by lucky8987 on 17/7/31.
 */
public class EsTest {

    SearchengineServices.Iface service = null;

    @Before
    public void init() {
       service = ServiceManager.SERVICEMANAGER.getService(SearchengineServices.Iface.class);
    }

    @Test
    public void serachTest() throws TException {
        Response response = service.queryAwardRanking(Arrays.asList(), "2017-04",0, 0);
        System.out.println(response);
    }

    @Test
    public void searchInWxTest() throws TException {
        Response response = service.queryAwardRankingInWx(Arrays.asList(), "2017-04", 0);
        System.out.println(response);
    }
}
