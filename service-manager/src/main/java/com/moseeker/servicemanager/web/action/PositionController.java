package com.moseeker.servicemanager.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.moseeker.servicemanager.web.form.User;

@Controller
public class PositionController {

	@RequestMapping(value={"/responseBody"},method=RequestMethod.GET)
	@ResponseBody
	public String getResponseBody() {
		return "test";
    }
	
	@RequestMapping(value={"/viewname"},method=RequestMethod.GET)
	public String viewname() {
		return "test";
    }
	
	@RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
	public Map<String, Object> error(HttpServletRequest request) {
		request.setAttribute("javax.servlet.error.status_code", 404);
		request.setAttribute("javax.servlet.error.message", "没有找到页面");
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", request.getAttribute("javax.servlet.error.status_code"));
        map.put("reason", request.getAttribute("javax.servlet.error.message"));
        return map;
    }
	
	@RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
	public void json(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			User user = new User();
			user.setId(1);
			user.setUsername(null);
			String jsonString = JSON.toJSONString(user);
			writer.write(jsonString);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				writer.close();
		}
    }
	
	@RequestMapping(value="/owners/{ownerId}", method=RequestMethod.GET)
    @ResponseBody
	public void restFul(@PathVariable Integer ownerId, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			User user = new User();
			user.setId(ownerId);
			user.setUsername("test");
			String jsonString = JSON.toJSONString(user);
			writer.write(jsonString);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				writer.close();
		}
    }
}
