package test.com.moseeker.rpccenter.loadbalance;

import com.moseeker.rpccenter.common.ServerNode;
import com.moseeker.rpccenter.loadbalance.LoadBalance;
import com.moseeker.rpccenter.loadbalance.common.DynamicHostSet;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzh on 16/4/8.
 */
public class LoadBalanceTest {

    @Test
    public void nextBackend(){
        ServerNode node1 = new ServerNode("192.168.4.165", 18091);
        ServerNode node2 = new ServerNode("192.168.4.165", 18092);
        ServerNode node3 = new ServerNode("192.168.4.165", 18093);
        List<ServerNode> current = new ArrayList<ServerNode>();
        current.add(node1);
        current.add(node2);
        current.add(node3);
        DynamicHostSet hostSet = new DynamicHostSet();
        hostSet.replaceWithList(current);
        for (int i=0; i<10; i++){
            System.out.println(LoadBalance.nextBackend(hostSet).toString());
        }
    }
}
