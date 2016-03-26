package com.moseeker.common.interfaces;

import org.junit.Test;

public class MoSeekerChild extends MoSeeker {

	public MoSeekerChild() { }
	
	@Override
	public void doGet(String request, String response) {
		int i =0;
		System.out.println("business");
	}
	
	@Test
	public void test() {
		MoSeekerChild child = new MoSeekerChild();
		child.doGet("request", "response");
	}
}
