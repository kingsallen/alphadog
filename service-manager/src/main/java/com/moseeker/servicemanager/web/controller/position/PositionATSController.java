package com.moseeker.servicemanager.web.controller.position;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.PositionATSServices;
import com.moseeker.thrift.gen.position.struct.BatchHandlerJobPostion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PositionATSController {
    Logger logger = LoggerFactory.getLogger(PositionATSController.class);

    private PositionATSServices.Iface positonATSServices = ServiceManager.SERVICEMANAGER.getService(PositionATSServices.Iface.class);

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
