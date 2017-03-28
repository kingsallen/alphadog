package com.moseeker.servicemanager.web.controller.position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moseeker.common.providerutils.QueryUtil;
import org.jooq.tools.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.apps.positionbs.service.PositionBS;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.JobDBDao;
import com.moseeker.thrift.gen.dao.struct.ThirdPartyPositionData;
import com.moseeker.thrift.gen.position.service.PositionServices;
import com.moseeker.thrift.gen.position.struct.BatchHandlerJobPostion;
import com.moseeker.thrift.gen.position.struct.DelePostion;
import org.jooq.tools.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
public class PositionController {

    private Logger logger = LoggerFactory.getLogger(PositionController.class);

    private PositionServices.Iface positonServices = ServiceManager.SERVICEMANAGER.getService(PositionServices.Iface.class);
    private JobDBDao.Iface jobDBDao = ServiceManager.SERVICEMANAGER.getService(JobDBDao.Iface.class);
    private PositionBS.Iface positionBS = ServiceManager.SERVICEMANAGER.getService(PositionBS.Iface.class);
    
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
			QueryUtil query=new QueryUtil();
			HashMap<String,String> param=new HashMap<String,String>();
			param.put("company_id", company_id+"");
			query.setEqualFilter(param);
			query.setPageSize(Integer.MAX_VALUE);
			Response result=jobDBDao.getJobOccupations(query);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			logger.error(e.getMessage(), e);
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
    
	@RequestMapping(value = "/position/sync", method = RequestMethod.POST)
	@ResponseBody
	public String synchronizePosition(HttpServletRequest request, HttpServletResponse response) {
		try {
			ThirdPartyPositionForm form = PositionParamUtils.parseSyncParam(request);
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
			List<ThirdPartyPositionData> datas = positonServices.getThirdPartyPositions(qu);
			Response result = ResponseUtils.success(datas);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			logger.error(e.getMessage(), e);
			return ResponseLogNotification.fail(request, e.getMessage());
		} finally {
			//do nothing
		}
	}
    
    /**
     * 职位刷新
     * 
     * @param request
     * @param response
     * @return
     */
	@RequestMapping(value = "/position/refresh", method = RequestMethod.POST)
	@ResponseBody
	public String refreshPosition(HttpServletRequest request, HttpServletResponse response) {
		try {
			logger.info("/position/refresh");
			List<HashMap<Integer, Integer>> paramList = PositionParamUtils.parseRefreshParam(request);
			logger.info("/position/refresh paramList.size:"+paramList.size());
			List<Object> refreshResult = new ArrayList<>();
			if(paramList.size() > 0) {
				paramList.forEach(map -> {
					map.forEach((positionId, channel) -> {
						try {
							logger.info("positionId:"+positionId+"    channel:"+channel);
							Response refreshPositionResponse = positionBS.refreshPositionToThirdPartyPlatform(positionId, channel);
							logger.info("data:"+refreshPositionResponse.getData());
							refreshResult.add(JSON.parse(refreshPositionResponse.getData()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							HashMap<String, Object> param = new HashMap<>();
							param.put("position_id", String.valueOf(positionId));
							param.put("channel", String.valueOf(channel));
							param.put("sync_status", "0");
							param.put("sync_fail_reason", Constant.POSITION_REFRESH_FAILED);
							refreshResult.add(param);
						} finally {
							//do nothing
						}
					});
				});
			}
			Response res = ResponseUtils.success(refreshResult);
			return ResponseLogNotification.success(request, res);
		} catch (Exception e) {	
			logger.error(e.getMessage(), e);
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

    /**
     * 批量修改职位
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/jobposition/batchhandler", method = RequestMethod.POST)
    @ResponseBody
    public String batchHandlerJobPostion(HttpServletRequest request, HttpServletResponse response) {
        try {
            BatchHandlerJobPostion batchHandlerJobPostion = PositionParamUtils.parseBatchHandlerJobPostionParam(request);
            Response res = positonServices.batchHandlerJobPostion(batchHandlerJobPostion);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            //do nothing
        }
    }


    /**
     * 删除职位
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/delete/jobpostion", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteJobPostion(HttpServletRequest request, HttpServletResponse response) {
        try {
            DelePostion params = ParamUtils.initModelForm(request, DelePostion.class);
            Response res = positonServices.deleteJobposition(params);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

}
