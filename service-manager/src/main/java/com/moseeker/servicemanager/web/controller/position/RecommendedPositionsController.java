package com.moseeker.servicemanager.web.controller.position;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moseeker.common.annotation.iface.CounterIface;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.PositionServices;

import java.util.Map;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
@CounterIface
public class RecommendedPositionsController {

    Logger logger = org.slf4j.LoggerFactory.getLogger(RecommendedPositionsController.class);

    PositionServices.Iface positonServices = ServiceManager.SERVICE_MANAGER.getService(PositionServices.Iface.class);

    @RequestMapping(value = "/positions/recommended", method = RequestMethod.GET)
    @ResponseBody
    public String get(HttpServletRequest request, HttpServletResponse response) {
        try {
            // GET方法 通用参数解析并赋值
            Map<String,Object> params = ParamUtils.parseRequestParam(request);
            String pid = String.valueOf(params.get("pid"));
            int id = Integer.parseInt(pid);
            Response result = positonServices.getRecommendedPositions(id);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    @RequestMapping(value = "/position/pc/recommended", method = RequestMethod.GET)
    @ResponseBody
    public String getPcPositionRecommend(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String,Object> params = ParamUtils.parseRequestParam(request);
            String positionId=(String)params.get("positionId");
            String page= (String) params.get("page");
            String pageSize= (String) params.get("pageSize");
            if(page==null){
                page="1";
            }
            if(pageSize==null){
                pageSize="10";
            }
            Response result = positonServices.getPcRecommendPosition(Integer.parseInt(positionId),Integer.parseInt(page),Integer.parseInt(pageSize));
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

}
