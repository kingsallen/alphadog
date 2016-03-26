package com.moseeker.common.interfaces;


public abstract class MoSeeker {
	
	public void get(String request, String response) {
		before(request);
		doGet(request, response);
		after(response);
	}
	
	private void before(String request) {
		System.out.println(request);
	}
	
	private void after(String response) {
		System.out.println(response);
	}
	
	public abstract void doGet(String request, String response);
}
