package com.moseeker.servicemanager.web.controller.position;

import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.PositionServices;
import com.moseeker.thrift.gen.position.struct.DelePostion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by zztaiwll on 18/1/24.
 */
@Controller
public class PositionMiniController {
    private Logger logger = LoggerFactory.getLogger(PositionController.class);
    private PositionServices.Iface positonServices = ServiceManager.SERVICEMANAGER.getService(PositionServices.Iface.class);
    /**
     * 小程序职位列表
     */
    @RequestMapping(value = "/api/mini/position/list", method = RequestMethod.GET)
    @ResponseBody
    public String getPositionList(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> map = ParamUtils.parseRequestParam(request);
            String page= (String) map.get("pageNum");
            String pageSize=(String)map.get("pageSize");
            String accountId=(String)map.get("accountId");
            String keyWords=(String)map.get("keyword");
            Response res = positonServices.getMiniPositionList(Integer.parseInt(accountId),keyWords,Integer.parseInt(page),Integer.parseInt(pageSize));
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    /**
     * 小程序职位列表
     */
    @RequestMapping(value = "/api/mini/position/suggest", method = RequestMethod.GET)
    @ResponseBody
    public String getPositionSuggest(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> map = ParamUtils.parseRequestParam(request);
            String page= (String) map.get("pageNum");
            String pageSize=(String)map.get("pageSize");
            String accountId=(String)map.get("accountId");
            if(StringUtils.isNullOrEmpty(accountId)||"0".equals(accountId)){
                return ResponseLogNotification.fail(request,"accountId不能为空");
            }
            String keyWords=(String)map.get("keyword");
            Response res = positonServices.getMiniPositionSuggest(Integer.parseInt(accountId),keyWords,Integer.parseInt(page),Integer.parseInt(pageSize));
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
