package com.moseeker.common.zookeeper;

import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.KeeperException.Code;

import java.util.Arrays;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import com.moseeker.common.zookeeper.DataMonitor.DataMonitorListener;

public class ZKWatcher implements Watcher, StatCallback{
	
	private boolean dead = false;
	
	private String znode;
	
	private Watcher chainedWatcher;
	
	private DataMonitorListener listener;
	
	private ZooKeeper zk;
	
	private byte prevData[];
	
	public ZKWatcher(ZooKeeper zk, String znode, Watcher chainedWatcher, DataMonitorListenerTest listener) {
		this.zk = zk;
		this.znode = znode;
		this.chainedWatcher = chainedWatcher;
		this.listener = listener;
	}

	@Override
	public void process(WatchedEvent event) {
		String path = event.getPath();
        if (event.getType() == Event.EventType.None) {
        	System.out.println("Event.EventType.None");
            // We are are being told that the state of the
            // connection has changed
            switch (event.getState()) {
            case SyncConnected:
            	System.out.println("SyncConnected");
                // In this particular example we don't need to do anything
                // here - watches are automatically re-registered with 
                // server and any watches triggered while the client was 
                // disconnected will be delivered (in order of course)
                break;
            case Expired:
                // It's all over
            	System.out.println("Expired");
                dead = true;
                listener.closing(KeeperException.Code.SessionExpired);
                break;
            }
        } else {
        	System.out.println("Event.EventType:"+event.getType());
            if (path != null && path.equals(znode)) {
                // Something has changed on the node, let's find out
                zk.exists(znode, true, this, null);
            }
        }
        if (chainedWatcher != null) {
            chainedWatcher.process(event);
        }
	}

	@Override
	public void processResult(int rc, String path, Object ctx, Stat stat) {
		boolean exists;
        switch (rc) {
        case Code.Ok:
        	System.out.println("Code.OK");
            exists = true;
            break;
        case Code.NoNode:
        	System.out.println("Code.NoNode");
            exists = false;
            break;
        case Code.SessionExpired:
        case Code.NoAuth:
            dead = true;
            listener.closing(rc);
            return;
        default:
            // Retry errors
            zk.exists(znode, true, this, null);
            return;
        }

        byte b[] = null;
        if (exists) {
            try {
                b = zk.getData(znode, false, null);
                System.out.println(new String(b));
            } catch (KeeperException e) {
                // We don't need to worry about recovering now. The watch
                // callbacks will kick off any exception handling
                e.printStackTrace();
            } catch (InterruptedException e) {
                return;
            }
        }
        if ((b == null && b != prevData)
                || (b != null && !Arrays.equals(prevData, b))) {
            listener.exists(b);
            prevData = b;
        }
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public ZooKeeper getZk() {
		return zk;
	}

	public void setZk(ZooKeeper zk) {
		this.zk = zk;
	}
}
