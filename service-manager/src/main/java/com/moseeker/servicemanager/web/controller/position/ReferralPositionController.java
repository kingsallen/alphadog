package com.moseeker.servicemanager.web.controller.position;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.position.service.ReferralPositionServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Date: 2018/9/4
 * @Author: JackYang
 */
@Controller
@CounterIface
public class ReferralPositionController {

    private Logger logger = LoggerFactory.getLogger(PositionController.class);

    private ReferralPositionServices.Iface referralPositionService = ServiceManager.SERVICEMANAGER.getService(ReferralPositionServices.Iface.class);



    @RequestMapping(value = "/v1/referral/position/", method = RequestMethod.DELETE)
    @ResponseBody
    public String delReferralPosition(HttpServletRequest request, HttpServletResponse response) {
        try {

            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            List<Integer> pids = (List<Integer>)params.get("position_ids");

            if (CollectionUtils.isEmpty(pids)) {
                return ResponseLogNotification.fail(request, "position_ids不能为空");
            }

            referralPositionService.delReferralPositions(pids);

            return com.moseeker.servicemanager.web.controller.Result.success(true).toJson();
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    @RequestMapping(value = "/v1/referral/position/", method = RequestMethod.PUT)
    @ResponseBody
    public String putReferralPosition(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            List<Integer> pids = (List<Integer>)params.get("position_ids");
            if (CollectionUtils.isEmpty(pids)) {
                return ResponseLogNotification.fail(request, "position_ids不能为空");
            }

            referralPositionService.putReferralPositions(pids);

            return com.moseeker.servicemanager.web.controller.Result.success(true).toJson();
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
