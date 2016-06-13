package com.moseeker.common.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZNodeTest {

	public static void main(String args[]) {
		//delete znode
		/*try {
			DataMonitorListenerTest listener = new DataMonitorListenerTest(null);
			ZKWatcher watch = new ZKWatcher(null, "/zk_test", null, listener);
			ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 3000, watch);
			VoidCallbackTest vc = new VoidCallbackTest();
			zk.delete("/zk_test", 0, vc, null);
			zk.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//add znode
		/*try {
			DataMonitorListenerTest listener = new DataMonitorListenerTest(null);
			ZKWatcher watch = new ZKWatcher(null, "/zk_test", null, listener);
			ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 3000, watch);
			StringCallbackTest scb = new StringCallbackTest();
			String data = "my_data";
			zk.create("/zk_test", data.getBytes(), Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT, scb, null);
			zk.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		try {
			DataMonitorListenerTest listener = new DataMonitorListenerTest(null);
			ZKWatcher watch = new ZKWatcher(null, "/services/com.moseeker.thrift.gen.profile.service.AttachmentServices/servers/192.168.31.49:19100", null, listener);
			ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 3000, watch);
			watch.setZk(zk);
			listener.setZk(zk);
			zk.exists("/services/com.moseeker.thrift.gen.profile.service.AttachmentServices/servers/192.168.31.49:19100", true, watch, null);
			String b = new String(zk.getData("/services/com.moseeker.thrift.gen.profile.service.AttachmentServices/servers/192.168.31.49:19100", false, null));
			System.out.println(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
