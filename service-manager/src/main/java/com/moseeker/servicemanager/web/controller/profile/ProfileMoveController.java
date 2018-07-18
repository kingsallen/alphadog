package com.moseeker.servicemanager.web.controller.profile;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.profile.form.ProfileMoveForm;
import com.moseeker.servicemanager.web.controller.util.Params;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    /**
     * 简历搬家用户登录
     * @author  cjm
     * @date  2018/7/18
     */
    @RequestMapping(value = "/profile/move/user/login", method = RequestMethod.POST)
    @ResponseBody
    public String userLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer hrId = params.getInt("hr_id");
            Integer companyId = params.getInt("company_id");
            Integer channel = params.getInt("channel");
            Integer crawlType = params.getInt("crawl_type");
            String startDate = params.getString("start_date");
            String endDate = params.getString("end_date");
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addIntTypeValidate("hrId", hrId, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredValidate("hrId", hrId, null, null);
            validateUtil.addIntTypeValidate("公司id", companyId, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredValidate("公司id", companyId, null, null);
            validateUtil.addIntTypeValidate("渠道", channel, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredValidate("渠道", channel, null, null);
            validateUtil.addIntTypeValidate("简历类型", crawlType, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredValidate("简历类型", crawlType, null, null);
            validateUtil.addStringLengthValidate("开始时间", startDate, null, null, 0, 50);
            validateUtil.addRequiredValidate("开始时间", crawlType, null, null);
            validateUtil.addStringLengthValidate("结束时间", endDate, null, null, 0, 50);
            validateUtil.addRequiredValidate("结束时间", crawlType, null, null);

            String message = validateUtil.validate();

            if (StringUtils.isBlank(message)) {
                ProfileMoveForm form = new ProfileMoveForm();
                BeanUtils.copyProperties(params, form);
                // 用户登录，增加简历搬家操作表
                return null;
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 获取简历搬家列表
     * @author  cjm
     * @date  2018/7/18
     */
    @RequestMapping(value = "/profile/move/getOperationList", method = RequestMethod.GET)
    @ResponseBody
    public String getMyHouseList(HttpServletRequest request, HttpServletResponse response){
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
                return null;
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 简历搬家的最终操作--简历合并、入库
     * @author  cjm
     * @date  2018/7/18
     */
    @RequestMapping(value = "/profile/move", method = RequestMethod.POST)
    @ResponseBody
    public String profileMove(HttpServletRequest request, HttpServletResponse response){
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String profile = params.getString("profile");
            Integer operationId = params.getInt("operation_id");
            Integer currentEmailNum = params.getInt("current_email_num");
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addIntTypeValidate("简历搬家操作主键id", operationId, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredValidate("简历搬家操作主键id", operationId, null, null);
            validateUtil.addIntTypeValidate("当前邮件条数", currentEmailNum, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredValidate("当前邮件条数", currentEmailNum, null, null);
            validateUtil.addRequiredValidate("简历json串", profile, null, null);

            String message = validateUtil.validate();

            if (StringUtils.isBlank(message)) {
                // 简历合并、入库
                return null;
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/profile/move/choose", method = RequestMethod.POST)
    @ResponseBody
    public String chooseCompany(HttpServletRequest request, HttpServletResponse response){
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String companyName = params.getString("company_name");
            Integer hrId = params.getInt("hr_id");
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addIntTypeValidate("hrId", hrId, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredValidate("hrId", hrId, null, null);
            validateUtil.addStringLengthValidate("简历搬家选择公司名称", companyName, null, null, 0, 100);
            validateUtil.addRequiredValidate("简历搬家选择公司名称", companyName, null, null);

            String message = validateUtil.validate();

            if (StringUtils.isBlank(message)) {
                // 选择公司名称
                return null;
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
