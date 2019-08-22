package com.moseeker.servicemanager.web.controller.position;

import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.PositionServices;
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
    private PositionServices.Iface positonServices = ServiceManager.SERVICE_MANAGER.getService(PositionServices.Iface.class);
    /**
     * 小程序职位列表
     */
    @RequestMapping(value = "/api/mini/position/list", method = RequestMethod.POST)
    @ResponseBody
    public String getPositionList(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> map = ParamUtils.parseRequestParam(request);
            int page= 0;
            int pageSize=20;
            if(map.get("pageNum")!=null){
                page=(int)map.get("pageNum");
            }
            if(map.get("pageSize")!=null){
                pageSize=(int)map.get("pageSize");
            }
            int accountId=(int)map.get("accountId");
            String keyWords=(String)map.get("keyword");
            if(StringUtils.isNotNullOrEmpty(keyWords)){
                keyWords=StringUtils.filterStringForSearch(keyWords);
            }
            Response res = positonServices.getMiniPositionList(accountId,keyWords,page,pageSize);
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
            int page= 1;
            int pageSize=20;
            if(map.get("pageNum")!=null){
                page=(int)map.get("pageNum");
            }
            if(map.get("pageSize")!=null){
                pageSize=(int)map.get("pageSize");
            }
            int accountId=(int)map.get("accountId");
            String keyWords=(String)map.get("keyword");
            if(StringUtils.isNotNullOrEmpty(keyWords)){
                keyWords=StringUtils.filterStringForSearch(keyWords);
            }
            Response res = positonServices.getMiniPositionSuggest(accountId,keyWords,page,pageSize);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    /**
     * 小程序中上下架职位的数量
     */
    @RequestMapping(value = "/api/mini/position/num/status", method = RequestMethod.POST)
    @ResponseBody
    public String getPositionStatusNum(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> map = ParamUtils.parseRequestParam(request);
            int page= 0;
            int pageSize=20;
            if(map.get("pageNum")!=null){
                page=(int)map.get("pageNum");
            }
            if(map.get("pageSize")!=null){
                pageSize=(int)map.get("pageSize");
            }
            int accountId=(int)map.get("accountId");
            String keyWords=(String)map.get("keyword");
            if(StringUtils.isNotNullOrEmpty(keyWords)){
                keyWords=StringUtils.filterStringForSearch(keyWords);
            }
            Response res = positonServices.getMiniPositionNumStatus(accountId,keyWords,page,pageSize);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 小程序中职位详情
     */
    @RequestMapping(value = "/api/mini/position/detail", method = RequestMethod.GET)
    @ResponseBody
    public String getPositionDetail(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer positionId = params.getInt("positionId");
            Response res = positonServices.getMiniPositionDetail(positionId);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 小程序中职位详情页分享标题
     */
    @RequestMapping(value = "/api/mini/position/share", method = RequestMethod.GET)
    @ResponseBody
    public String getPositionShare(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer positionId = params.getInt("positionId");
            Response res = positonServices.getMiniPositionShare(positionId);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
