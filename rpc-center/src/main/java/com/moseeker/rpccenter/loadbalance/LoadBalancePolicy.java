package com.moseeker.rpccenter.loadbalance;

import com.moseeker.rpccenter.listener.ZKPath;

public interface LoadBalancePolicy {

	public ZKPath getNode(ZKPath root, String name);
}
