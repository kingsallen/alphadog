package com.moseeker.servicemanager.web.controller.position;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.PositionATSServices;
import com.moseeker.thrift.gen.position.struct.BatchHandlerJobPostion;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyCompanyChannelConfDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class PositionATSController {
    Logger logger = LoggerFactory.getLogger(PositionATSController.class);

    private PositionATSServices.Iface positonATSServices = ServiceManager.SERVICE_MANAGER.getService(PositionATSServices.Iface.class);

    /**
     * 获取可同步渠道
     */
    @RequestMapping(value = "/thirdparty/channel", method = RequestMethod.GET)
    @ResponseBody
    public String getSyncChannel(HttpServletRequest request, HttpServletResponse response) {
        try {
            Response res = positonATSServices.getSyncChannel();
            return ResponseLogNotification.success(request, res);
        } catch (BIZException e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.failJson(request,e);
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 设置公司同步渠道
     */
    @RequestMapping(value = "/thirdparty/company/channel", method = RequestMethod.PUT)
    @ResponseBody
    public String updateCompanyChannelConf(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int company_id = params.getInt("company_id");
            TypeReference<List<Integer>> typeRef = new TypeReference<List<Integer>>(){};
            List<Integer> channel = JSON.parseObject(params.getString("channel"),typeRef);
            List<ThirdpartyCompanyChannelConfDO> result = positonATSServices.updateCompanyChannelConf(company_id,channel);
            return ResponseLogNotification.successJson(request,result);
        } catch (BIZException e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.failJson(request,e);
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 获取可同步渠道
     */
    @RequestMapping(value = "/thirdparty/company/channel", method = RequestMethod.GET)
    @ResponseBody
    public String getCompanyChannelConf(HttpServletRequest request, HttpServletResponse response) {
        try {
            int company_id = Integer.valueOf(request.getParameter("company_id"));
            List<Integer> res = positonATSServices.getCompanyChannelConfByCompanyId(company_id);
            return ResponseLogNotification.successJson(request, res);
        } catch (BIZException e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.failJson(request,e);
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 谷露新增职位
     */
    @RequestMapping(value = "/glluePosition", method = RequestMethod.POST)
    @ResponseBody
    public String insertGlluePosition(HttpServletRequest request, HttpServletResponse response) {
        try {
            BatchHandlerJobPostion batchHandlerJobPostion = PositionParamUtils.parseGlluePostionParam(request);
            Response res = positonATSServices.insertGlluePosition(batchHandlerJobPostion);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 谷露修改职位
     */
    @RequestMapping(value = "/glluePosition", method = RequestMethod.PUT)
    @ResponseBody
    public String updateGlluePosition(HttpServletRequest request, HttpServletResponse response) {
        try {
            BatchHandlerJobPostion batchHandlerJobPostion = PositionParamUtils.parseGlluePostionParam(request);
            Response res = positonATSServices.updateGlluePosition(batchHandlerJobPostion);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 谷露下架的职位重新发布
     */
    @RequestMapping(value = "/glluePosition/republish", method = RequestMethod.POST)
    @ResponseBody
    public String republishGlluePosition(HttpServletRequest request, HttpServletResponse response) {
        try {
            BatchHandlerJobPostion batchHandlerJobPostion = PositionParamUtils.parseGlluePostionParam(request);
            Response res = positonATSServices.republishPosition(batchHandlerJobPostion);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 谷露下架的职位重新发布
     */
    @RequestMapping(value = "/glluePosition/revokePosition", method = RequestMethod.POST)
    @ResponseBody
    public String revokeGlluePosition(HttpServletRequest request, HttpServletResponse response) {
        try {
            BatchHandlerJobPostion batchHandlerJobPostion = PositionParamUtils.parseGlluePostionParam(request);
            Response res = positonATSServices.revokeGlluePosition(batchHandlerJobPostion);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 修改职位福利特色
     */
    @RequestMapping(value = "/ats/position/feature", method = RequestMethod.POST)
    @ResponseBody
    public String atsUpdatePositionFeature(HttpServletRequest request, HttpServletResponse response) {
        try {
            BatchHandlerJobPostion batchHandlerJobPostion = PositionParamUtils.parseBatchHandlerJobPostionParam(request);
            Response res = positonATSServices.atsUpdatePositionFeature(batchHandlerJobPostion);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

}
