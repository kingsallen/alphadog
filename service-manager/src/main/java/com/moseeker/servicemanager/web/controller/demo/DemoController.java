package com.moseeker.servicemanager.web.controller.demo;

import com.moseeker.servicemanager.common.ResponseLogNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by jack on 2018/4/18.
 */
@Controller
public class DemoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DemoService demoService;

    /**
     * 用户申请
     * <p>
     *
     */
    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    @ResponseBody
    public String get(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取application实体对象
            String result = demoService.test(UUID.randomUUID().toString(), 1);
            logger.info("JobApplicationController result:{}", result);
            return ResponseLogNotification.successJson(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
