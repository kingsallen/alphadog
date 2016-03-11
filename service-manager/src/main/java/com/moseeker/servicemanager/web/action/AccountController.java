package com.moseeker.servicemanager.web.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.moseeker.servicemanager.web.form.User;

@Controller
public class AccountController {
	
	Logger logger = LoggerFactory.getLogger(AccountController.class);

	@RequestMapping(value="/user/accounts/{id}", method=RequestMethod.GET)
    @ResponseBody
	public void getAccount(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			User user = new User();
			user.setId(id);
			user.setUsername("test");
			String jsonString = JSON.toJSONString(user);
			writer.write(jsonString);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			logger.info("/user/accounts/"+id);
			if (writer != null) {
				writer.close();
			}
		}
    }
	
	@RequestMapping(value="/user/accounts/{accountId}/profile/{profileId}", method=RequestMethod.GET)
    @ResponseBody
	public void getAccountProfile(@PathVariable Integer accountId, @PathVariable Integer profileId, 
			HttpServletRequest request, HttpServletResponse response) {
		request.getParameter("limit");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			User user = new User();
			user.setId(accountId);
			user.setUsername(profileId+"");
			String jsonString = JSON.toJSONString(user);
			writer.write(jsonString);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			logger.info("/user/accounts/"+accountId);
			if (writer != null) {
				writer.close();
			}
		}
    }
}
