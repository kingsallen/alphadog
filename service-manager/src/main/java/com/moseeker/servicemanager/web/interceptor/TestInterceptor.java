package com.moseeker.servicemanager.web.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

public class TestInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		response.setCharacterEncoding("utf8");
		String test = request.getParameter("test");
		if(test != null && test.trim().equals("true")) {
			PrintWriter writer = null;
			try {
				writer = response.getWriter();
				// User user = new User();
				// user.setId(0);
				// user.setUsername("没有数据");
				String jsonString = JSON.toJSONString("user");
				writer.write(jsonString);
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					writer.close();
				}
			}
			return false;
		}
		return true;
	}
}
