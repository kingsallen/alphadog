package com.moseeker.common.zookeeper;

import org.apache.zookeeper.AsyncCallback.StringCallback;

public class StringCallbackTest implements StringCallback {

	@Override
	public void processResult(int rc, String path, Object ctx, String name) {
		System.out.println("rc:"+rc);
		System.out.println("path:"+path);
	}

}
