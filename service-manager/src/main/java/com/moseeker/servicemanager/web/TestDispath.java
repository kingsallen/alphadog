package com.moseeker.servicemanager.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;

public class TestDispath extends DispatcherServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5154954011872142230L;

	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String[]> params = request.getParameterMap();
		super.doService(request, response);
	}

	@Override
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String[]> params = request.getParameterMap();
		// TODO Auto-generated method stub
		super.doDispatch(request, response);
	}

}
