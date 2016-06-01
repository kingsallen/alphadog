package com.moseeker.common.proxy;

public class IExecution implements Execution {

	@Override
	public void beforeMethod() {
		System.out.println("IExecution before method!");
	}

	@Override
	public void afterMethod() {
		System.out.println("IExecution after method!");
	}

}
