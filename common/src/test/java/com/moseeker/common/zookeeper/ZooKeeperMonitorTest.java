package com.moseeker.common.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.ZooKeeper;

public class ZooKeeperMonitorTest {

	private ZooKeeper zk;
	
	private String node;
	
	public static void main(String args[]) {
		//zk = new ZooKeeper();
		String host = "127.0.0.1:2181";
		try {
			DataMonitorListenerTest listener = new DataMonitorListenerTest(null);
			ZKWatcher watch = new ZKWatcher(null, "/services/com.moseeker.thrift.gen.profile.service.ProfileServices/servers/192.168.31.52:19090", null, listener);
			ZooKeeper zk = new ZooKeeper(host, 3000, watch);
			watch.setZk(zk);
			listener.setZk(zk);
			zk.exists("/services/com.moseeker.thrift.gen.profile.service.ProfileServices/servers/192.168.31.52:19090", true, watch, null);
			new Thread(() -> {
				try {
		            synchronized (zk) {
		            	System.out.println(watch.isDead());
		            	while(!watch.isDead()) {
		            		zk.wait();
		            	}
		            }
		        } catch (InterruptedException e) {
		        }
			}).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
