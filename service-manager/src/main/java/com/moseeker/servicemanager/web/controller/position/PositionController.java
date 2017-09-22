package com.moseeker.servicemanager.web.controller.position;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.jobdb.JobOccupationDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.position.bean.ThirdPartyPositionVO;
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
import com.moseeker.thrift.gen.position.service.PositionServices;
import com.moseeker.thrift.gen.position.struct.*;
import org.jooq.tools.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
@CounterIface
public class PositionController {

    private Logger logger = LoggerFactory.getLogger(PositionController.class);

    private PositionServices.Iface positonServices = ServiceManager.SERVICEMANAGER.getService(PositionServices.Iface.class);
    private PositionBS.Iface positionBS = ServiceManager.SERVICEMANAGER.getService(PositionBS.Iface.class);

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

            Map<String, Object> map = ParamUtils.parseRequestParam(request);
            logger.info("map: " + map.toString());

            if (map.getOrDefault("company_id", null) != null) {
                query.setCompany_id(Integer.valueOf((String) map.get("company_id")));
            } else {
                throw new Exception("公司 id 未提供!");
            }

            query.setPage_from(Integer.valueOf((String) map.getOrDefault("page_from", "0")));
            query.setPage_size(Integer.valueOf((String) map.getOrDefault("page_size", "10")));

            query.setKeywords((String) map.getOrDefault("keywords", ""));
            query.setCities((String) map.getOrDefault("cities", ""));
            query.setIndustries((String) map.getOrDefault("industries", ""));
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

            String param_setOrder_by_priority = (String) map.getOrDefault("order_by_priority", "True");
            query.setOrder_by_priority(param_setOrder_by_priority.equals("True"));

            List<WechatPositionListData> positionList = positonServices.getPositionList(query);
            Response res = ResponseUtils.success(positionList);
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
            Map<String, Object> map = ParamUtils.parseRequestParam(request);
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
            Map<String, Object> map = ParamUtils.parseRequestParam(request);
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

    /**
     * 职位刷新
     */
    @RequestMapping(value = "/position/refresh", method = RequestMethod.POST)
    @ResponseBody
    public String refreshPosition(HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info("/position/refresh");
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            List<HashMap<Integer, Integer>> paramList = PositionParamUtils.parseRefreshParam(params);
            List< Integer> paramQXList = PositionParamUtils.parseRefreshParamQX(params);
            logger.info("/position/refresh paramList.size:" + paramList.size());
            List<Object> refreshResult = new ArrayList<>();
            if (paramList.size() > 0) {
                paramList.forEach(map -> {
                    map.forEach((positionId, channel) -> {
                        try {
                            //同步到智联的第三方职位不刷新
                            if (ChannelType.ZHILIAN.getValue() == channel) {
                                logger.info("synchronize position:{}:zhilian skip",positionId);
                                List<Integer> positionIds =new ArrayList<Integer>();
                                positionIds.add(positionId);
                                positionBS.refreshPositionQXPlatform(positionIds);
                            }else {
                                logger.info("positionId:" + positionId + "    channel:" + channel);
                                Response refreshPositionResponse = positionBS.refreshPositionToThirdPartyPlatform(positionId, channel);
                                logger.info("data:" + refreshPositionResponse.getData());
                                refreshResult.add(JSON.parse(refreshPositionResponse.getData()));
                            }
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
                if(!StringUtils.isEmptyList(paramQXList)){
                	 positionBS.refreshPositionQXPlatform(paramQXList);
                }

            }else{
            	  if(!StringUtils.isEmptyList(paramQXList)){
                 	 positionBS.refreshPositionQXPlatform(paramQXList);
                 }
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
            List<HrThirdPartyPositionDO> datas = positonServices.getThirdPartyPositions(qu);

            if (datas == null) datas = new ArrayList<>();

            List<ThirdPartyPositionVO> vos = new ArrayList<>();

            for (HrThirdPartyPositionDO positionDO : datas) {
                vos.add(new ThirdPartyPositionVO().copyDO(positionDO));
            }

            Response result = ResponseUtils.success(vos);
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
            Map<String, Object> params = ParamUtils.parseRequestParam(request);
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
            Map<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer hbConfigId = Integer.valueOf((String) params.get("hb_config_id"));
            if (hbConfigId == null) {
                throw new Exception("红包活动 id 不正确!");
            }
            List<WechatRpPositionListData> rpPositionList = positonServices.getRpPositionList(hbConfigId);

            Response res = ResponseUtils.success(rpPositionList);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 根据 pids (List<Integer>) 获取职位的红包附加信息
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
            Map<String, Object> params = ParamUtils.parseRequestParam(request);
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
            Map<String, Object> map = ParamUtils.parseRequestParam(request);
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
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
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
    public PositionDetailsListVO companyHotPositionDetailsList(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer companyId = params.getInt("company_id");
            Integer page = params.getInt("page");
            Integer per_age = params.getInt("per_age");
            return positonServices.companyHotPositionDetailsList(companyId, page, per_age);
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
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer pid = params.getInt("position_id");
            Integer page = params.getInt("page");
            Integer per_age = params.getInt("per_age");
            return positonServices.similarityPositionDetailsList(pid, page, per_age);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 职位同步到第三方接口
     */
    @RequestMapping(value = "/position/thirdpartyposition", method = RequestMethod.GET)
    @ResponseBody
    public String getPositionForThirdParty(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
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
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
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
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            HrThirdPartyAccountDO thirdPartyAccount = ParamUtils.initModelForm(params, HrThirdPartyAccountDO.class);
            HrThirdPartyPositionDO thirdPartyPosition = ParamUtils.initModelForm(params, HrThirdPartyPositionDO.class);

            if (thirdPartyAccount == null || thirdPartyPosition == null) {
                throw new CommonException(2201, "参数错误");
            }

            thirdPartyAccount.setId(0);
            thirdPartyAccount.unsetId();
            positonServices.updateThirdPartyPositionWithAccount(thirdPartyPosition, thirdPartyAccount);
            return ResponseLogNotification.successJson(request, 1);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseLogNotification.failJson(request, e);
        }
    }
    /*
        *获取pc端推荐职位列表
        */
    @RequestMapping(value = "/positions/apolegamic", method = RequestMethod.GET)
    @ResponseBody
    public String getPcRecommendPosition(HttpServletRequest request, HttpServletResponse response){
        try{
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer page = params.getInt("page");
            Integer pageSize = params.getInt("pageSize");
            if(page==null){
                page=1;
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

    /*
    *获取pc端职位的详情
    */
    @RequestMapping(value = "/position/pc/details", method = RequestMethod.GET)
    @ResponseBody
    public String getPcPositionDetail(HttpServletRequest request, HttpServletResponse response){
        try{
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
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
    @RequestMapping(value = "/position/pc/report", method = RequestMethod.GET)
    @ResponseBody
    public String addPcReport(HttpServletRequest request, HttpServletResponse response){
        try{
            JobPcReportedDO DO=ParamUtils.initCommonQuery(request, JobPcReportedDO.class);
            Response result=positonServices.addPcReport(DO);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
