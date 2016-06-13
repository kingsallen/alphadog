package com.moseeker.common.zookeeper;

import org.apache.zookeeper.AsyncCallback.VoidCallback;

public class VoidCallbackTest implements VoidCallback {

	@Override
	public void processResult(int rc, String path, Object ctx) {
		System.out.println("rc:"+rc);
		System.out.println("path:"+path);
	}

}
