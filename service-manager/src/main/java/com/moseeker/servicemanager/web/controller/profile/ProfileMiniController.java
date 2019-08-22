package com.moseeker.servicemanager.web.controller.profile;

import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.ProfileOtherThriftService;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by zztaiwll on 18/2/1.
 */
@Controller
public class ProfileMiniController {

    Logger logger = LoggerFactory.getLogger(ProfileController.class);
    WholeProfileServices.Iface profileService = ServiceManager.SERVICE_MANAGER
            .getService(WholeProfileServices.Iface.class);
    ProfileOtherThriftService.Iface profileOtherService = ServiceManager.SERVICE_MANAGER
            .getService(ProfileOtherThriftService.Iface.class);
    @RequestMapping(value = "/api/mini/profile/list", method = RequestMethod.GET)
    @ResponseBody
    public String get(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> map = ParamUtils.parseRequestParam(request);
            if(map==null||map.isEmpty()){
                return  ResponseLogNotification.fail(request,"参数不能为空");
            }
            if(map.get("accountId")==null|| StringUtils.isNullOrEmpty(String.valueOf(map.get("accountId")))){
                return  ResponseLogNotification.fail(request,"accountId不能为空");
            }
            Map<String,String> requestParams=new HashMap<>();
            for(String key:map.keySet()){
                String value=String.valueOf(map.get(key));
                if(StringUtils.isNotNullOrEmpty(value)){
                    value=StringUtils.filterStringForSearch(value);
                }
                requestParams.put(key,value);
            }
            Response res=profileService.getProfileMiniList(requestParams);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/api/mini/profile/info", method = RequestMethod.GET)
    @ResponseBody
    public String getProfileInfo(HttpServletRequest request, HttpServletResponse response) {
        // PrintWriter writer = null;
        try {
            // GET方法 通用参数解析并赋值
            Params<String, Object> form = ParamUtils.parseRequestParam(request);
            int accountId = form.getInt("accountId", 0);
            int userId = form.getInt("userId",0);
            int positionId = form.getInt("positionId", 0);
            Response result = profileService.getProfileInfo(userId, accountId, positionId);

            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    @RequestMapping(value = "/api/mini/profile/other", method = RequestMethod.GET)
    @ResponseBody
    public String getProfileOther(HttpServletRequest request, HttpServletResponse response) {
        // PrintWriter writer = null;
        try {
            // GET方法 通用参数解析并赋值
            Params<String, Object> form = ParamUtils.parseRequestParam(request);
            int accountId = form.getInt("accountId", 0);
            int userId = form.getInt("userId");
            int positionId = form.getInt("positionId", 0);
            Response result = profileOtherService.getProfileOtherByPosition(userId, accountId, positionId);

            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    /**
     * 小程序获取sug提示词
     */
    @RequestMapping(value = "/api/mini/profile/suggest", method = RequestMethod.POST)
    @ResponseBody
    public String getProfileSuggest(HttpServletRequest request, HttpServletResponse response) {
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
            Response res = profileService.getMiniProfileSuggest(accountId,keyWords,page,pageSize);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
