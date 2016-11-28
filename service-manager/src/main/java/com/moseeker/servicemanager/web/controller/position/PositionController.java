package com.moseeker.servicemanager.web.controller.position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jooq.tools.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.apps.positionbs.service.PositionBS;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThridPartyPosition;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThridPartyPositionForm;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.PositionDao;
import com.moseeker.thrift.gen.position.service.PositionServices;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
public class PositionController {

    Logger logger = LoggerFactory.getLogger(PositionController.class);

    PositionServices.Iface positonServices = ServiceManager.SERVICEMANAGER.getService(PositionServices.Iface.class);
    PositionDao.Iface positionDao =ServiceManager.SERVICEMANAGER.getService(PositionDao.Iface.class);
    PositionBS.Iface positionBS =ServiceManager.SERVICEMANAGER.getService(PositionBS.Iface.class);
    
    com.moseeker.thrift.gen.dao.service.PositionDao.Iface positionDao1 = ServiceManager.SERVICEMANAGER.getService(com.moseeker.thrift.gen.dao.service.PositionDao.Iface.class);
    
    @RequestMapping(value = "/positions", method = RequestMethod.GET)
    @ResponseBody
    public String get(HttpServletRequest request, HttpServletResponse response) {
        try {
            // GET方法 通用参数解析并赋值
            CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);
            Response result = positonServices.getResources(query);

            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

	@RequestMapping(value = "/position/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String getPosition(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {
		try {
			Response result = positonServices.getPositionById(id);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

    @RequestMapping(value = "/positions/verifyCustomize", method = RequestMethod.GET)
	@ResponseBody
	public String verifyRequires(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		try {
			
			//ImportCVForm form = ParamUtils.initModelForm(request, ImportCVForm.class);
			String positionId = request.getParameter("position_id");
			ValidateUtil vu = new ValidateUtil();
			vu.addRequiredValidate("职位编号", positionId);
			vu.addIntTypeValidate("职位编号", positionId, null, null, 1, Integer.MAX_VALUE);
			String message = vu.validate();
			if(StringUtils.isNullOrEmpty(message)) {
				Response result = positonServices.verifyCustomize(Integer.valueOf(positionId));
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, message);
			}
		} catch (Exception e) {	
			logger.error(e.getMessage(), e);
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
    
    @RequestMapping(value = "/thirdparty/customfield", method = RequestMethod.GET)
	@ResponseBody
	public String customField(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		try {
			Map<String,Object> map=ParamUtils.parseRequestParam(request);
			Response result=positonServices.CustomField(JSONObject.toJSONString(map));
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			logger.error(e.getMessage(), e);
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
    
    @RequestMapping(value = "/thirdparty/joboccupation", method = RequestMethod.GET)
	@ResponseBody
	public String occupation(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String,Object> map=ParamUtils.parseRequestParam(request);
			String company_id= (String) map.get("company_id");
			CommonQuery query=new CommonQuery();
			HashMap<String,String> param=new HashMap<String,String>();
			param.put("company_id", company_id+"");
			query.setEqualFilter(param);
			query.setPer_page(Integer.MAX_VALUE);
			Response result=positionDao.getJobOccupations(query);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			logger.error(e.getMessage(), e);
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/position/sync", method = RequestMethod.POST)
	@ResponseBody
	public String synchronizePosition(HttpServletRequest request, HttpServletResponse response) {
		try {
			ThridPartyPositionForm form = new ThridPartyPositionForm();
			HashMap<String, Object> data = ParamUtils.parseRequestParam(request);
			form.setAppid((Integer)data.get("appid"));
			form.setPosition_id((Integer)data.get("position_id"));
			List<ThridPartyPosition> cs = new ArrayList<>();
			List<HashMap<String, Object>> channels = (List<HashMap<String, Object>>)data.get("channels");
			if(channels != null) {
				channels.forEach(channel -> {
					try {
						ThridPartyPosition c = ParamUtils.initModelForm(channel, ThridPartyPosition.class);
						cs.add(c);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error(e.getMessage(), e);
					} finally {
						//do nothing
					}
				});
			}
			form.setChannels(cs);
			logger.info("-----------synchronizePosition------------");
			logger.info("params:"+JSON.toJSONString(form));
			Response result = positionBS.synchronizePositionToThirdPartyPlatform(form);
			logger.info("result:"+JSON.toJSONString(result));
			logger.info("-----------synchronizePosition end------------");
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
    
    @RequestMapping(value = "/thirdparty/position", method = RequestMethod.GET)
	@ResponseBody
	public String thirdpartyposition(HttpServletRequest request, HttpServletResponse response) {
		try {
			CommonQuery qu = ParamUtils.initCommonQuery(request, CommonQuery.class);
			Response result = positionDao1.getPositionThirdPartyPositions(qu);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			logger.error(e.getMessage(), e);
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
    
    /**
     * {
    "appid":1,
    "positions":[
        "position_id":1,
        "channels":[1,2,3,4]
    ]
}
     * @param request
     * @param response
     * @return
     */
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/position/refresh", method = RequestMethod.POST)
	@ResponseBody
	public String refreshPosition(HttpServletRequest request, HttpServletResponse response) {
		try {
			Params<String, Object> params = ParamUtils.parseRequestParam(request);
			List<Map<String, Object>> positions = (List<Map<String, Object>>)params.get("positions");
			HashMap<Integer, Integer> param = new HashMap<>();
			if(positions != null && positions.size() > 0) {
				positions.forEach(position -> {
					int positionId = (Integer)position.get("position_id");
					List<Integer> channels = (List<Integer>)position.get("channels");
					if(channels != null && channels.size() > 0) {
						channels.forEach(channel -> {
							param.put(positionId, channel);
						});
					}
				});
			}
			List<Object> refreshResult = new ArrayList<>();
			if(param.size() > 0) {
				param.forEach((positionId, channel) -> {
					try {
						Response refreshPositionResponse = positionBS.refreshPositionToThirdPartyPlatform(positionId, channel);
						refreshResult.add(JSON.parse(refreshPositionResponse.getData()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error(e.getMessage(), e);
					}
				});
			}
			Response res = ResponseUtils.success(refreshResult);
			return ResponseLogNotification.success(request, res);
		} catch (Exception e) {	
			logger.error(e.getMessage(), e);
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
}
