package com.moseeker.common.zookeeper;

import org.apache.zookeeper.ZooKeeper;

import com.moseeker.common.zookeeper.DataMonitor.DataMonitorListener;

public class DataMonitorListenerTest implements DataMonitorListener {
	
	private ZooKeeper zk;

	public DataMonitorListenerTest(ZooKeeper zk) {
		this.zk = zk;
	}
	
	@Override
	public void exists(byte[] data) {
		if (data == null) {
			System.out.println("data data is null");
        } else {
            System.out.println(new String(data));
        }
	}

	@Override
	public void closing(int rc) {
		synchronized(zk) {
			zk.notify();
		}
	}

	public ZooKeeper getZk() {
		return zk;
	}

	public void setZk(ZooKeeper zk) {
		this.zk = zk;
	}

}
