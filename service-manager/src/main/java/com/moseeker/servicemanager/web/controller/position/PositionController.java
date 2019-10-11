package com.moseeker.servicemanager.web.controller.position;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.jobdb.JobOccupationDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.apps.positionbs.service.PositionBS;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.CampaignHeadImageVO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPcReportedDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionExtDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionLiepinMappingDO;
import com.moseeker.thrift.gen.position.service.PositionServices;
import com.moseeker.thrift.gen.position.struct.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.moseeker.servicemanager.common.ParamUtils.parseRequestParam;
import static java.util.Arrays.asList;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
@CounterIface
public class PositionController {

    private Logger logger = LoggerFactory.getLogger(PositionController.class);

    private PositionServices.Iface positonServices = ServiceManager.SERVICE_MANAGER.getService(PositionServices.Iface.class);
    private PositionBS.Iface positionBS = ServiceManager.SERVICE_MANAGER.getService(PositionBS.Iface.class);

    @Autowired
    private JobOccupationDao occuPationdao;


    @RequestMapping(value = "/positions", method = RequestMethod.GET)
    @ResponseBody
    public String get(HttpServletRequest request, HttpServletResponse response) {
        try {
            // GET方法 通用参数解析并赋值
            CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);
            logger.info(query.toString());
            Response result = positonServices.getResources(query);

            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
        	logger.info(e.getMessage(),e);
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
        	logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 获取职位列表
     *
     * @param request  request
     * @param response response
     * @return 职位列表数据
     */
    @RequestMapping(value = "/position/list", method = RequestMethod.GET)
    @ResponseBody
    public String getPositionList(HttpServletRequest request, HttpServletResponse response) {
        try {
            WechatPositionListQuery query = new WechatPositionListQuery();

            Map<String, Object> map = parseRequestParam(request);
            logger.info("map: " + map.toString());

            if (map.getOrDefault("company_id", null) != null) {
                query.setCompany_id(Integer.valueOf((String) map.get("company_id")));
            } else {
                throw new Exception("公司 id 未提供!");
            }

            query.setPage_from(Integer.valueOf((String) map.getOrDefault("page_from", "0")));
            query.setPage_size(Integer.valueOf((String) map.getOrDefault("page_size", "10")));

            query.setUser_id(Integer.valueOf((String) map.getOrDefault("user_id", "0")));
            query.setKeywords(StringUtils.filterStringForSearch((String) map.getOrDefault("keywords", "")));
            query.setCities((String) map.getOrDefault("cities", ""));
            query.setIndustries(StringUtils.filterStringForSearch((String) map.getOrDefault("industries", "")));
            query.setOccupations((String) map.getOrDefault("occupations", ""));
            query.setScale((String) map.getOrDefault("scale", ""));
            query.setCandidate_source((String) map.getOrDefault("candidate_source", ""));
            query.setEmployment_type((String) map.getOrDefault("employment_type", ""));
            query.setExperience((String) map.getOrDefault("experience", ""));
            query.setSalary((String) map.getOrDefault("salary", ""));
            query.setDegree((String) map.getOrDefault("degree", ""));
            query.setDepartment((String) map.getOrDefault("department", ""));
            query.setCustom((String) map.getOrDefault("custom", ""));
            query.setDid(Integer.valueOf((String) map.getOrDefault("did", "0")));
            query.setHb_config_id((String) map.getOrDefault("hb_config_id", ""));
            String param_setOrder_by_priority = (String) map.getOrDefault("order_by_priority", "True");
            query.setOrder_by_priority(param_setOrder_by_priority.equals("True"));
            String isReference=(String) map.getOrDefault("is_referral", "");
            if(StringUtils.isNotNullOrEmpty(isReference)){
                query.setIs_referral(Integer.parseInt(isReference));
            }else{
                query.setIs_referral(0);
            }

            List<WechatPositionListData> positionList = positonServices.getPositionList(query);
            Response res = ResponseUtils.success(positionList);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 获取职位列表
     *
     * @param request  request
     * @param response response
     * @return 职位列表数据
     */
    @RequestMapping(value = "/position_ext/list", method = RequestMethod.POST)
    @ResponseBody
    public String getPositionExtList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            Map<String, Object> map = parseRequestParam(request);

            List<Integer> ids = (List<Integer>) map.get("ids");

            List<JobPositionExtDO> positionExtList = positonServices.getPositionExtList(ids);
            Response res = ResponseUtils.success(positionExtList);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
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
            if (StringUtils.isNullOrEmpty(message)) {
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
            Map<String, Object> map = parseRequestParam(request);
            Response result = positonServices.CustomField(JSONObject.toJSONString(map));
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
            Map<String, Object> map = parseRequestParam(request);
            String company_id = (String) map.get("company_id");
            Query.QueryBuilder query = new Query.QueryBuilder();
            query.where("company_id", company_id);
            query.setPageSize(Integer.MAX_VALUE);
            Response result = ResponseUtils.success(occuPationdao.getDatas(query.buildQuery(), com.moseeker.thrift.gen.position.struct.dao.JobOccupationCustom.class));
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/position/refreshThirdPartyParam", method = RequestMethod.GET)
    @ResponseBody
    public String refreshThirdPartyParam(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> map = parseRequestParam(request);
            if(!"moseeker.com".equals(map.get("refreshKey"))){
                throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"wrong request param!");
            }
            int channel=0;
            if(map.containsKey("channel")){
                channel= map.getInt("channel");
            }
            logger.info("-----------refresh Third Party Param start------------");
            Response result = positionBS.refreshThirdPartyParam(channel);
            logger.info("result:" + JSON.toJSONString(result));
            logger.info("-----------refresh Third Party Param end------------");
            return ResponseLogNotification.success(request, result);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
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
            logger.info("params:" + JSON.toJSONString(form));
            Response result = positionBS.synchronizePositionToThirdPartyPlatform(form);
            logger.info("result:" + JSON.toJSONString(result));
            logger.info("-----------synchronizePosition end------------");
            return ResponseLogNotification.success(request, result);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/position/syncVerifyInfo", method = RequestMethod.POST)
    @ResponseBody
    public String syncVerifyInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> params = parseRequestParam(request);
            String jsonParam=JSON.toJSONString(params);
            logger.info("-----------syncVerifyInfo------------");
            logger.info("syncVerifyInfo params:" + jsonParam);
            Response result=positionBS.syncVerifyInfo(jsonParam);
            logger.info("syncVerifyInfo result:" + JSON.toJSONString(result));
            logger.info("-----------syncVerifyInfo end------------");
            return ResponseLogNotification.success(request, result);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/position/getSyncVerifyParam", method = RequestMethod.GET)
    @ResponseBody
    public String getVerifyParam(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> params = parseRequestParam(request);
            String jsonParam=JSON.toJSONString(params);
            logger.info("-----------getVerifyInfo------------");
            logger.info("getVerifyInfo params:" + jsonParam);
            Response result=positionBS.getVerifyParam(jsonParam);
            logger.info("getVerifyInfo result:" + JSON.toJSONString(result));
            logger.info("-----------getVerifyInfo end------------");
            return ResponseLogNotification.success(request, result);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/jobposition/saveAndSync", method = RequestMethod.POST)
    @ResponseBody
    public String saveAndSyncPosition(HttpServletRequest request, HttpServletResponse response) {
        try {
            BatchHandlerJobPostion batchHandlerJobPostion = PositionParamUtils.parseSyncBatchHandlerJobPostionParam(request);
            Response res = positonServices.saveAndSync(batchHandlerJobPostion);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            //do nothing
        }
    }

    /**
     * 职位刷新
     */
    @RequestMapping(value = "/position/refresh", method = RequestMethod.POST)
    @ResponseBody
    public String refreshPosition(HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info("/position/refresh");
            Params<String, Object> params = parseRequestParam(request);
            List< Integer> paramQXList = PositionParamUtils.parseRefreshParamQX(params);
            logger.info("/position/refresh paramQXList{}:" ,paramQXList);

            List<Object> refreshResult = new ArrayList<>();
            if(!StringUtils.isEmptyList(paramQXList)){
                positionBS.refreshPositionQXPlatform(paramQXList);
            }
            Response res = ResponseUtils.success(refreshResult);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/thirdparty/position", method = RequestMethod.GET)
    @ResponseBody
    public String thirdpartyposition(HttpServletRequest request, HttpServletResponse response) {
        try {
            CommonQuery qu = ParamUtils.initCommonQuery(request, CommonQuery.class);
            List<String> datas = positonServices.getThirdPartyPositions(qu);

            if (datas == null) datas = new ArrayList<>();

            List<Map<String,Object>> mapResult=new ArrayList<>();
            for (String json : datas) {
                Map<String,Object> positionDO=JSON.parseObject(json);
                positionDO.put("position_id",positionDO.get("positionId"));
                positionDO.put("accountId",positionDO.get("thirdPartyAccountId"));
                mapResult.add(positionDO);
            }

            Response result = ResponseUtils.success(mapResult);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            //do nothing
        }
    }

    /**
     * 根据 hb_config_id 获取分享信息
     *
     * @param request  request
     * @param response response
     * @return 分享信息
     */
    @RequestMapping(value = "/position/list/hb_share_info", method = RequestMethod.GET)
    @ResponseBody
    public String getHbShareInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> params = parseRequestParam(request);
            Integer hbConfigId = Integer.valueOf((String) params.get("hb_config_id"));
            WechatShareData shareData = positonServices.getShareInfo(hbConfigId);

            Response res = ResponseUtils.success(shareData);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 根据 hb_config_id 获取职位列表
     *
     * @param request  request
     * @param response response
     * @return 红包职位列表
     */
    @RequestMapping(value = "/position/rplist", method = RequestMethod.GET)
    @ResponseBody
    public String getRpPositionList(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> params = parseRequestParam(request);
            Integer hbConfigId = Integer.valueOf((String) params.get("hb_config_id"));
            if (hbConfigId == null) {
                throw new Exception("红包活动 id 不正确!");
            }
            String pageNum=(String)params.get("page_from");
            String pageSize=(String)params.get("page_size");
            if(StringUtils.isNullOrEmpty(pageNum)){
                pageNum="1";
            }
            if(StringUtils.isNullOrEmpty(pageSize)){
                pageSize="15";
            }
            List<WechatRpPositionListData> rpPositionList = positonServices.getRpPositionList(hbConfigId, Integer.parseInt(pageNum),Integer.parseInt(pageSize));

            Response res = ResponseUtils.success(rpPositionList);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 根据 pids (List<Integer>) 获取职位的红包附加信息
     * 根据 pids (List<Integer>)
     *
     * @param request  request
     * @param response response
     * @return 红包职位列表
     */
    @RequestMapping(value = "/position/rpext", method = RequestMethod.GET)
    @ResponseBody
    @SuppressWarnings("unchecked")
    public String getPositionListRpExt(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> params = parseRequestParam(request);
            List<String> pidStringList = asList(((String) params.get("pids")).split(","));
            List<Integer> pids = pidStringList.stream().map(Integer::valueOf).collect(Collectors.toList());

            List<RpExtInfo> rpExtInfoList = positonServices.getPositionListRpExt(pids);

            Response res = ResponseUtils.success(rpExtInfoList);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /**
     * 批量修改职位
     */
    @RequestMapping(value = "/jobposition/batchhandler", method = RequestMethod.POST)
    @ResponseBody
    public String batchHandlerJobPostion(HttpServletRequest request, HttpServletResponse response) {
        try {
            BatchHandlerJobPostion batchHandlerJobPostion = PositionParamUtils.parseBatchHandlerJobPostionParam(request);
            Response res = positonServices.batchHandlerJobPostion(batchHandlerJobPostion);
            logger.info("batchhandler result:{}",JSON.toJSONString(res));
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.info("batchhandler error result:{}",e);
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            //do nothing
        }
    }


    /**
     * 删除职位
     */
    @RequestMapping(value = "/jobposition", method = RequestMethod.DELETE)
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


    /**
     * 通过companyId和部门名获取TeamId
     */
    @RequestMapping(value = "/jobposition/getteamidbydepartmentname", method = RequestMethod.GET)
    @ResponseBody
    public String getTeamIdByDepartmentName(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> map = parseRequestParam(request);
            Integer companyId = null;
            if ((String) map.get("company_id") != null) {
                companyId = Integer.valueOf((String) map.get("company_id"));
            } else {
                return ResponseLogNotification.fail(request, "请设置 company_id!");
            }
            String departmentName = (String) map.get("department_name");
            Response res = positonServices.getTeamIdByDepartmentName(companyId, departmentName);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 职位查询头图查询
     */
    @RequestMapping(value = "/head/image", method = RequestMethod.GET)
    @ResponseBody
    public CampaignHeadImageVO headImage(HttpServletRequest request, HttpServletResponse response) {
        try {
            return positonServices.headImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询职位的详细信息
     *
     * @return positionDetailsVO
     */
    @RequestMapping(value = "/positions/positiondetails", method = RequestMethod.GET)
    @ResponseBody
    public PositionDetailsVO positionDetails(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = parseRequestParam(request);
            Integer positionId = params.getInt("position_id");
            return positonServices.positionDetails(positionId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 查询公司热招职位的详细信息
     */
    @RequestMapping(value = "/positions/companyhotpositiondetailslist", method = RequestMethod.GET)
    @ResponseBody
    public PositionDetailsListVO companyHotPositionDetailsList(HttpServletRequest request) {
        try {
            Params<String, Object> params = parseRequestParam(request);
            Integer companyId = params.getInt("company_id");
            Integer page = params.getInt("page");
            Integer per_age = params.getInt("per_age");
            PositionDetailsListVO positionDetailsListVO = positonServices.companyHotPositionDetailsList(companyId, page, per_age);
            if (positionDetailsListVO.getData() != null && positionDetailsListVO.getData().size() > 0) {
                logger.info("companyHotPositionDetailsList 1.salaryTop:{}, 1.salaryBottom:{}", positionDetailsListVO.getData().get(0).getSalaryTop(), positionDetailsListVO.getData().get(0).getSalaryBottom());
            }
            return positionDetailsListVO;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }


    /**
     * 职位相关职位接口
     */
    @RequestMapping(value = "/positions/similaritypositiondetailslist", method = RequestMethod.GET)
    @ResponseBody
    public PositionDetailsListVO similarityPositionDetailsList(HttpServletRequest request, HttpServletResponse response) {

        try {
            Params<String, Object> params = parseRequestParam(request);
            Integer pid = params.getInt("position_id");
            Integer page = params.getInt("page");
            Integer per_age = params.getInt("per_age");
            return positonServices.similarityPositionDetailsList(pid, page, per_age);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /*
     *获取pc端推荐职位列表
     */
    @RequestMapping(value = "/positions/apolegamic", method = RequestMethod.GET)
    @ResponseBody
    public String getPcRecommendPosition(HttpServletRequest request, HttpServletResponse response){
    	try{
	    	Params<String, Object> params = parseRequestParam(request);
	        Integer page = params.getInt("page");
	        Integer pageSize = params.getInt("pageSize");
	        if(page==null){
	        	page=0;
	        }
	        if(pageSize==null){
	        	pageSize=15;
	        }
	    	Response result=positonServices.getPcRecommand(page,pageSize);
	    	return ResponseLogNotification.success(request, result);
    	}catch(Exception e){
    		 logger.error(e.getMessage());
    		 return ResponseLogNotification.fail(request, e.getMessage());
    	}
    }

    /**
     * 职位同步到第三方接口
     */
    @RequestMapping(value = "/position/thirdpartyposition", method = RequestMethod.GET)
    @ResponseBody
    public String getPositionForThirdParty(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = parseRequestParam(request);
            Integer pid = params.getInt("positionId");
            Integer channel = params.getInt("channel");
            Response res =  positonServices.getPositionForThirdParty(pid, channel);
            return ResponseLogNotification.success(request, res);

        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request,e.getMessage());
        }
    }



    /**
     * 职位列表id同步到第三方接口
     */
    @RequestMapping(value = "/positions/thirdpartypositions", method = RequestMethod.GET)
    @ResponseBody
    public String getPositionListForThirdParty(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = parseRequestParam(request);
            Integer channel = params.getInt("channel");
            Integer type = params.getInt("type");
            String start_time = params.getString("start_time");
            String end_time = params.getString("end_time");
            List<Integer> positions =  positonServices.getPositionListForThirdParty(channel,type,start_time,end_time);
            Response res = ResponseUtils.success(positions);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request,e.getMessage());
        }
    }




    /**
     * 第三方职位列表详情
     */
    @RequestMapping(value = "/thirdparty/position/info", method = RequestMethod.GET)
    @ResponseBody
    public String getThirdPartyPositionInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            ThirdPartyPositionInfoForm infoForm = ParamUtils.initModelForm(request, ThirdPartyPositionInfoForm.class);
            ThirdPartyPositionResult result = positonServices.getThirdPartyPositionInfo(infoForm);
            return ResponseLogNotification.successJson(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseLogNotification.failJson(request, e);
        }
    }

    /**
     * 第三方职位列表详情
     */
    @RequestMapping(value = "/thirdparty/position", method = RequestMethod.PUT)
    @ResponseBody
    public String updateThirdPartyPosition(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = parseRequestParam(request);
            HrThirdPartyAccountDO thirdPartyAccount = ParamUtils.initModelForm(params, HrThirdPartyAccountDO.class);
            HrThirdPartyPositionDO thirdPartyPosition = ParamUtils.initModelForm(params, HrThirdPartyPositionDO.class);
            Map<String,String> extThirdPartyPosition = PositionParamUtils.toExtThirdPartyPosition(params);

            if (thirdPartyAccount == null || thirdPartyPosition == null) {
                throw new CommonException(2201, "参数错误");
            }

            thirdPartyAccount.setId(0);
            thirdPartyAccount.unsetId();
            positonServices.updateThirdPartyPositionWithAccount(thirdPartyPosition, thirdPartyAccount,extThirdPartyPosition);
            return ResponseLogNotification.successJson(request, 1);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseLogNotification.failJson(request, e);
        }
    }

    /**
     * 第三方职位列表详情
     */
    @RequestMapping(value = "/api/mini/update/position", method = RequestMethod.PUT)
    @ResponseBody
    public String updatePosition(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = parseRequestParam(request);
            Response result = positonServices.updatePosition(JSONObject.toJSONString(params));
            if(result.getStatus()==0) {
                return ResponseLogNotification.successJson(request, result.getData());
            }else{
                return ResponseLogNotification.failJson(request, result.getStatus(), result.getMessage(), result.getData());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseLogNotification.failJson(request, e);
        }
    }

    /*
    *获取pc端职位的详情
    */
    @RequestMapping(value = "/position/pc/details", method = RequestMethod.GET)
    @ResponseBody
    public String getPcPositionDetail(HttpServletRequest request, HttpServletResponse response){
        try{
            Params<String, Object> params = parseRequestParam(request);
            int positionId = params.getInt("positionId");
            Response result=positonServices.getPcPositionDetail(positionId);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
      *职位举报
      */
    @RequestMapping(value = "/position/pc/report", method = RequestMethod.POST)
    @ResponseBody
    public String addPcReport(HttpServletRequest request, HttpServletResponse response){
        try{
            Params<String, Object> params = parseRequestParam(request);
            String type= (String) params.get("type");
            String descrptiton= (String) params.get("description");
            String userId= (String) params.get("userId");
            String positionId= (String) params.get("positionId");
            if(StringUtils.isNullOrEmpty(type)||StringUtils.isNullOrEmpty(userId)||StringUtils.isNullOrEmpty(positionId)){
                return ResponseLogNotification.fail(request, "参数不全");
            }
            JobPcReportedDO DO=new JobPcReportedDO();
            DO.setDescription(descrptiton);
            DO.setPositionId(Integer.parseInt(positionId));
            DO.setUserId(Integer.parseInt(userId));
            DO.setType(Integer.parseInt(type));
            Response result=positonServices.addPcReport(DO);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
     *pc端广告位的获取
     */
    @RequestMapping(value = "/position/pc/advertisement", method = RequestMethod.GET)
    @ResponseBody
    public String getPcdvertisement(HttpServletRequest request, HttpServletResponse response){
        try{
            Params<String, Object> params = parseRequestParam(request);
            Integer page = params.getInt("page");
            Integer pageSize = params.getInt("pageSize");
            if(page==null){
                page=1;
            }
            if(pageSize==null){
                pageSize=15;
            }
            Response result=positonServices.getPcAdvertisement(page,pageSize);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
     *pc端广告位的获取
     */
    @RequestMapping(value = "/position/pc/advertisementposition", method = RequestMethod.GET)
    @ResponseBody
    public String getPcecommendposition(HttpServletRequest request, HttpServletResponse response){
        try{
            Params<String, Object> params = parseRequestParam(request);
            Integer page = params.getInt("page");
            Integer pageSize = params.getInt("pageSize");
            if(page==null){
                page=1;
            }
            if(pageSize==null){
                pageSize=15;
            }
            String moduleId=params.getString("id");
            if(StringUtils.isNullOrEmpty(moduleId)){
                return ResponseLogNotification.fail(request, "模块id不能为空");
            }
            Response result=positonServices.getPositionRecommendByModuleId(page,pageSize,Integer.parseInt(moduleId));
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
    *获取alipay同步的职位
    */
    @RequestMapping(value = "/positions/thirdpartysyncedpositions", method = RequestMethod.GET)
    @ResponseBody
    public String getAliPayPosition(HttpServletRequest request, HttpServletResponse response){
        try{
            Params<String, Object> params = parseRequestParam(request);
            String publisher = (String) params.get("publisher");
            String companyId= (String) params.get("companyId");
            int candidateSource=params.getInt("candidatesource");
            String channel= (String) params.get("channel");
            String page= (String) params.get("page");
            String pageSize= (String) params.get("pageSize");
            if(StringUtils.isNullOrEmpty(publisher)&&StringUtils.isNullOrEmpty(companyId)){
                return ResponseLogNotification.fail(request, "companyId和publisher至少有一个不能为空");
            }
            if(StringUtils.isNullOrEmpty(publisher)){
               publisher="0";
            }
            if(StringUtils.isNullOrEmpty(companyId)){
                companyId="0";
            }
            if(StringUtils.isNullOrEmpty(channel)){
                channel="5";
            }
            if(StringUtils.isNullOrEmpty(page)){
                page="1";
            }
            if(StringUtils.isNullOrEmpty(pageSize)){
                pageSize=Integer.MAX_VALUE+"";
            }
            Response result=positonServices.getThirdpartySyncedPositions(Integer.parseInt(channel)
                    ,Integer.parseInt(publisher),Integer.parseInt(companyId),candidateSource
                    ,Integer.parseInt(page)
                    ,Integer.parseInt(pageSize)
            );
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
    *获取alipay同步的职位
    */
    @RequestMapping(value = "/position/alipayresult", method = RequestMethod.POST)
    @ResponseBody
    public String putAlipayResult(HttpServletRequest request, HttpServletResponse response){
        try{
            Params<String, Object> params = parseRequestParam(request);
            Integer channel=params.getInt("channel");
            int positionId=params.getInt("positionId");
            int alipayJobId=params.getInt("alipayJobid");
            if(channel==null){
                channel=5;
            }
            Response result=positonServices.putAlipayResult(channel
                    ,positionId,alipayJobId
               );
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /*
      *获取alipay同步的职位
     */
    @RequestMapping(value = "/position/personarecom", method = RequestMethod.GET)
    @ResponseBody
    public String personaRecomPosition(HttpServletRequest request, HttpServletResponse response){
        try{
            Params<String, Object> params = parseRequestParam(request);
            String pageNum=params.getString("pageNum");
            String pageSize=params.getString("pageSize");
            String userId=params.getString("userId");
            String companyId=params.getString("companyId");
            String type=params.getString("type");
            if(StringUtils.isNullOrEmpty(userId)||"0".equals(userId)||StringUtils.isNullOrEmpty(companyId)||"0".equals(companyId)){
                return ResponseLogNotification.fail(request, "userId或者companyId不能为空或0");
            }
            if(pageNum==null){
                pageNum="0";
            }
            if(pageSize==null){
                pageSize="20";
            }
            if(StringUtils.isNullOrEmpty(type)){
                type="0";
            }
            Response result=positonServices.getPersonaRecomPositionList(Integer.parseInt(userId),Integer.parseInt(companyId),Integer.parseInt(type),
                    Integer.parseInt(pageNum),Integer.parseInt(pageSize));
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
     * 获取职位自定义字段信息
     */
    @RequestMapping(value = "/position/cv/conf", method = RequestMethod.GET)
    @ResponseBody
    public String positionCvConf(HttpServletRequest request, HttpServletResponse response){
        try{
            Params<String, Object> params = parseRequestParam(request);
            int positionId = params.getInt("positionId", 0);
            if(positionId == 0){
                return ResponseLogNotification.fail(request, "positionId不能为空");
            }
            Response result = positonServices.positionCvConf(positionId);
           return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
         *获取alipay同步的职位
        */
    @RequestMapping(value = "/position/employeerecom", method = RequestMethod.GET)
    @ResponseBody
    public String employeeRecomPosition(HttpServletRequest request, HttpServletResponse response){
        try{
            Params<String, Object> params = parseRequestParam(request);
            String recomPushId=params.getString("recomPushId");
            String companyId=params.getString("companyId");
            String type=params.getString("type");
            String pageNum=(String)params.get("page_from");
            String pageSize=(String)params.get("page_size");
            if(StringUtils.isNullOrEmpty(pageNum)){
                pageNum="1";
            }
            if(StringUtils.isNullOrEmpty(pageSize)){
                pageSize="15";
            }
            if(StringUtils.isNullOrEmpty(recomPushId)){
                return ResponseLogNotification.fail(request, "推荐id不能为空");
            }
            if(StringUtils.isNullOrEmpty(companyId)){
                return ResponseLogNotification.fail(request, "公司不能为空");
            }
            if(StringUtils.isNullOrEmpty(type)){
                type="1";
            }

            Response result=positonServices.getEmployeeRecomPositionByIds(
                    Integer.parseInt(recomPushId),Integer.parseInt(companyId),Integer.parseInt(type)
                    ,Integer.parseInt(pageNum),Integer.parseInt(pageSize)
            );
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/api/position/feature/{pid}", method = RequestMethod.GET)
    @ResponseBody
    public String getPositionFeatureByPid(@PathVariable("pid") int pid, HttpServletRequest request, HttpServletResponse response){
        try{
            Response result=positonServices.getFeatureByPId(pid);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    @RequestMapping(value = "/api/position/feature/list", method = RequestMethod.POST)
    @ResponseBody
    public String updateFeatureList( HttpServletRequest request, HttpServletResponse response){
        try{
            Map<String, Object> params = parseRequestParam(request);
            List<Integer> fidList=(List<Integer>)params.get("fids");
            int pid=(int)params.get("position_id");
            Response result=positonServices.updatePositionFeatures(pid,fidList);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/api/position/feature", method = RequestMethod.POST)
    @ResponseBody
    public String updateFeature( HttpServletRequest request, HttpServletResponse response){
        try{
            Map<String, Object> params = parseRequestParam(request);
            int fid=(int)params.get("fid");
            int pid=(int)params.get("position_id");
            Response result=positonServices.updatePositionFeature(pid,fid);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    @RequestMapping(value = "/api/position/feature/batch", method = RequestMethod.POST)
    @ResponseBody
    public String updateFeatureBatch( HttpServletRequest request, HttpServletResponse response){
        try{
            Map<String, Object> params = parseRequestParam(request);
            List<Map<String,Object>> list= (List<Map<String, Object>>) params.get("data");
            if(StringUtils.isEmptyList(list)){
                return ResponseLogNotification.fail(request, "所传参数不能为空");
            }
            List<JobPositionHrCompanyFeatureDO> dataList=new ArrayList<>();
            for(Map<String,Object> map:list){
                int pid=(int)map.get("pid");
                List<Integer> fidList=(List<Integer>)map.get("fids");
                if(!StringUtils.isEmptyList(fidList)){
                    for(Integer fid:fidList){
                        JobPositionHrCompanyFeatureDO DO=new JobPositionHrCompanyFeatureDO();
                        DO.setFid(fid);
                        DO.setPid(pid);
                        dataList.add(DO);
                    }
                }
            }
            if(StringUtils.isEmptyList(dataList)){
                return ResponseLogNotification.fail(request, "所传参数不能为空");
            }
            Response result=positonServices.updatePositionFeatureBatch(dataList);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/api/position/feature/batch", method = RequestMethod.GET)
    @ResponseBody
    public String getFeatureBatch( HttpServletRequest request, HttpServletResponse response){
        try{
            Map<String, Object> params = parseRequestParam(request);
            List<Integer> pidList=ParamUtils.convertIntList(String.valueOf(params.get("pids")));
            if(StringUtils.isEmptyList(pidList)){
                return ResponseLogNotification.fail(request, "职位id不能为空");
            }
            Response result=positonServices.getPositionFeatureBetch(pidList);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/api/position", method = RequestMethod.POST)
    @ResponseBody
    public String addPosition( HttpServletRequest request, HttpServletResponse response){
        try{
            Position position = ParamUtils.initModelForm(request, Position.class);
            if(position==null){
                return ResponseLogNotification.fail(request, "职位数据不能为空");
            }
            Response result=null;
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/position/exception", method = RequestMethod.POST)
    @ResponseBody
    public String positionException( HttpServletRequest request, HttpServletResponse response){
        try{
            ParamUtils.initModelForm(request,HrThirdPartyPositionDO.class);
            throw new NullPointerException();
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    @RequestMapping(value = "/position/liepin/getPositionId", method = RequestMethod.POST)
    @ResponseBody
    public String getLiepinPositionIds( HttpServletRequest request, HttpServletResponse response){
        try{
            Map<String, Object> params = parseRequestParam(request);
            String liepinUserId = String.valueOf(params.get("liepin_user_id"));
            if(StringUtils.isNullOrEmpty(liepinUserId)){
                return ResponseLogNotification.fail(request, "猎聘用户id不能为空");
            }
            Integer userId = Integer.parseInt(liepinUserId);
            List<JobPositionLiepinMappingDO> liepinPositionIds = positonServices.getLiepinPositionIds(userId);

            return ResponseLogNotification.successJson(request, JSONObject.toJSON(liepinPositionIds));
        }catch(Exception e){
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

}
