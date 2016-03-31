package com.moseeker.servicemanager.web.controller;

import java.io.PrintWriter;

import com.moseeker.servicemanager.web.service.DemoServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zzh on 16/3/31.
 */
@Controller
public class DemoController {

    DemoServiceImpl demoService = new DemoServiceImpl();

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    @ResponseBody
    public void get(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = null;
        try {
            String msg = request.getParameter("msg");
            String res = demoService.echo(msg);
            writer = response.getWriter();
            writer.write(res);
        }catch (Exception e){
            writer.write("error");
        }finally {
            writer.flush();
        }
    }
}
