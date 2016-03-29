package com.moseeker.servicemanager.web.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface RestfulController{

	public void get(HttpServletRequest request, HttpServletResponse response);
	public void post(HttpServletRequest request, HttpServletResponse response);
	public void put(HttpServletRequest request, HttpServletResponse response);
	public void delete(HttpServletRequest request, HttpServletResponse response);

}
