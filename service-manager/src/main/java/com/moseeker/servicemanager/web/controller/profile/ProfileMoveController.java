package com.moseeker.servicemanager.web.controller.profile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.talentpool.service.ProfileMoveThriftService;
import com.moseeker.thrift.gen.talentpool.struct.ProfileMoveForm;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 简历搬家controller
 *
 * @author cjm
 * @date 2018-07-17 18:27
 **/
@Controller
@CounterIface
public class ProfileMoveController {

    private static Logger logger = LoggerFactory.getLogger(ProfileMoveController.class);

    private ProfileMoveThriftService.Iface profileMoveService = ServiceManager.SERVICE_MANAGER.getService(ProfileMoveThriftService.Iface.class);

    /**
     * 简历搬家用户登录
     * @author  cjm
     * @date  2018/7/18
     */
    @RequestMapping(value = "/profile/move/user/login", method = RequestMethod.POST)
    @ResponseBody
    public String userLogin(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Object obj = params.get("profile_move_vo");
            ProfileMoveForm form = JSONObject.parseObject(JSON.toJSONString(obj), ProfileMoveForm.class);
            Integer hrId = form.getHr_id();
            Integer channel = form.getChannel();
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addIntTypeValidate("hrId", hrId, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredValidate("hrId", hrId, null, null);
            validateUtil.addIntTypeValidate("渠道", channel, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredValidate("渠道", channel, null, null);
            String message = validateUtil.validate();

            if (StringUtils.isBlank(message)) {
                // 用户登录，增加简历搬家操作表
                return ResponseLogNotification.success(request, profileMoveService.moveHouseLogin(form));
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    /**
     * 获取简历搬家列表
     * @author  cjm
     * @date  2018/7/18
     */
    @RequestMapping(value = "/profile/move/getOperationList", method = RequestMethod.GET)
    @ResponseBody
    public String getMoveOperationList(HttpServletRequest request){
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer pageNum = params.getInt("page_number");
            Integer pageSize = params.getInt("page_size");
            Integer hrId = params.getInt("hr_id");
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addIntTypeValidate("hrId", hrId, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredValidate("hrId", hrId, null, null);
            validateUtil.addIntTypeValidate("当前页码", pageNum, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredValidate("当前页码", pageNum, null, null);
            validateUtil.addIntTypeValidate("当前页显示最大数量", pageSize, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredValidate("当前页显示最大数量", pageSize, null, null);

            String message = validateUtil.validate();

            if (StringUtils.isBlank(message)) {
                // 分页查询
                return ResponseLogNotification.success(request, profileMoveService.getMoveOperationList(hrId, pageNum, pageSize));
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    /**
     * 获取简历搬家列表
     * @author  cjm
     * @date  2018/7/18
     */
    @RequestMapping(value = "/profile/move/getOperationState", method = RequestMethod.GET)
    @ResponseBody
    public String getMoveOperationState(HttpServletRequest request){
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer hrId = params.getInt("hr_id");
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addIntTypeValidate("hrId", hrId, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredValidate("hrId", hrId, null, null);
            String message = validateUtil.validate();
            if (StringUtils.isBlank(message)) {
                // 分页查询
                return ResponseLogNotification.success(request, profileMoveService.getMoveOperationState(hrId));
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    /**
     * 简历搬家的最终操作--简历合并、入库
     * @author  cjm
     * @date  2018/7/18
     */
    @RequestMapping(value = "/profile/mvhouse", method = RequestMethod.POST)
    @ResponseBody
    public String profileMove(HttpServletRequest request){
        try {
            Map<String, Object> params = ParamUtils.parseRequestParam(request);

            Map<String,Object> profile= (Map<String, Object>) params.get("profile");

            logger.info("service-manager-----params:{}", params);
            logger.info("service-manager-----profile:{}", profile);
            Integer operationId = Integer.parseInt(String.valueOf(params.get("operation_id")));
            Integer currentEmailNum = Integer.parseInt(String.valueOf(params.get("current_email_num")));
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addIntTypeValidate("简历搬家操作主键id", operationId, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredValidate("简历搬家操作主键id", operationId, null, null);
            validateUtil.addIntTypeValidate("当前邮件条数", currentEmailNum, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredValidate("当前邮件条数", currentEmailNum, null, null);
            validateUtil.addRequiredValidate("简历json串", profile, null, null);

            String message = validateUtil.validate();
            logger.info("profile:{}", profile);
            if (StringUtils.isBlank(message)) {
                // 简历合并、入库
                return ResponseLogNotification.success(request, profileMoveService.moveHouse(JSON.toJSONString(profile), operationId, currentEmailNum));
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

}
