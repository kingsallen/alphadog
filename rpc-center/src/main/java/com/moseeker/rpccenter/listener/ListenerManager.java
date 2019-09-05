package com.moseeker.rpccenter.listener;

public class ListenerManager {

	public static void main(String[] args) {
		ZKPath path = NodeManager.NODE_MANAGER.getRoot();
		if(path != null) {
			new Thread(() -> {
				for(int i=0;i<500; i++) {
					System.out.println(NodeManager.NODE_MANAGER.convertToString());
					i++;
					try {
						Thread.sleep(10000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			while (true) {
				synchronized (ListenerManager.class) {
					while (true) {
						try {
							ListenerManager.class.wait();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		// printPath(path);
	}
}
