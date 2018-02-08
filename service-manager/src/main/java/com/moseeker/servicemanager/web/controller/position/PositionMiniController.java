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
    @RequestMapping(value = "/api/mini/position/list", method = RequestMethod.POST)
    @ResponseBody
    public String getPositionList(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> map = ParamUtils.parseRequestParam(request);

            String pageSize=String.valueOf(map.get("pageSize"));
            String page= String.valueOf(map.get("pageNum")) ;
            if(StringUtils.isNullOrEmpty(page)||"0".equals(page)){
                page="0";
            }
            if(StringUtils.isNullOrEmpty(pageSize)){
                pageSize="20";
            }
            int accountId=(int)map.get("accountId");
            String keyWords=(String)map.get("keyword");
            if(StringUtils.isNotNullOrEmpty(keyWords)){
                keyWords=StringUtils.filterStringForSearch(keyWords);
            }
            Response res = positonServices.getMiniPositionList(accountId,keyWords,Integer.parseInt(page),Integer.parseInt(pageSize));
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    /**
     * 小程序获取sug提示词
     */
    @RequestMapping(value = "/api/mini/position/suggest", method = RequestMethod.POST)
    @ResponseBody
    public String getPositionSuggest(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> map = ParamUtils.parseRequestParam(request);
            String page= String.valueOf(map.get("pageNum")) ;
            if(StringUtils.isNullOrEmpty(page)||"0".equals(page)){
                page="1";
            }
            String pageSize=String.valueOf(map.get("pageSize")) ;;
            if(StringUtils.isNullOrEmpty(pageSize)){
                pageSize="20";
            }
            int accountId=(int)map.get("accountId");
            String keyWords=(String)map.get("keyword");
            if(StringUtils.isNotNullOrEmpty(keyWords)){
                keyWords=StringUtils.filterStringForSearch(keyWords);
            }
            Response res = positonServices.getMiniPositionSuggest(accountId,keyWords,Integer.parseInt(page),Integer.parseInt(pageSize));
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
