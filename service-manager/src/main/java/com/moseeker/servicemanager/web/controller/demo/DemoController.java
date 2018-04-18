package com.moseeker.servicemanager.web.controller.demo;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.consistencysuport.ProducerEntry;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.demo1.service.Demo1ThriftService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Demo1ThriftService.Iface demoService = ServiceManager.SERVICEMANAGER
            .getService(Demo1ThriftService.Iface.class);

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
            String result = test(UUID.randomUUID().toString(), 1);
            logger.info("JobApplicationController result:{}", result);
            return ResponseLogNotification.successJson(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    @ProducerEntry(name = "test", index = 0)
    private String test(String messageId, int id) throws TException {
        return demoService.comsumerTest(messageId, id);
    }
}
