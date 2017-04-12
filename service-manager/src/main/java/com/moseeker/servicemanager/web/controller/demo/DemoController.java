package com.moseeker.servicemanager.web.controller.demo;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.*;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.web.controller.base.BaseController;
import com.moseeker.thrift.gen.demo.service.DemoThriftService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 所有的Controller继承BaseController进行异常处理
 */
@Controller
public class DemoController extends BaseController {
    DemoThriftService.Iface demoThriftService = ServiceManager.SERVICEMANAGER.getService(DemoThriftService.Iface.class);

    /**
     * 所有@RequestMapping方法抛出Exception
     *
     * @throws Exception
     */
    @RequestMapping(value = "/demo/{id}", method = RequestMethod.GET,produces="application/json")
    @ResponseBody
    public String get(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long id) throws Exception {
        return demoThriftService.getData(QueryCreator.select().where(CommonCondition.equal("id", id)).pageSize(10).page(1).getCommonQuery());
    }

    @RequestMapping(value = "/demo", method = RequestMethod.POST,produces="application/json")
    @ResponseBody
    public String post(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,String> datas = ParamUtils.getMap(request);
        return demoThriftService.postData(datas);
    }

    @RequestMapping(value = "/demo", method = RequestMethod.PUT,produces="application/json")
    @ResponseBody
    public String put(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,String> datas = ParamUtils.getMap(request);
        if(datas.get("id") != null && StringUtils.isNumeric(datas.get("id"))) {
            return demoThriftService.putData(UpdateCreator.set(datas).where(CommonCondition.equal("id", datas.get("id"))).getCommonUpdate());
        }else{
            return ResponseUtils.failJson(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
        }
    }

    @RequestMapping(value = "/demo/{id}", method = RequestMethod.DELETE,produces="application/json")
    @ResponseBody
    public String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long id) throws Exception {
        return demoThriftService.deleteData(QueryCondition.equal("id", id));
    }

}