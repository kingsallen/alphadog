package com.moseeker.servicemanager.web.controller.referral;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.FormCheck;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.servicemanager.web.controller.referral.form.ReferralForm;
import com.moseeker.servicemanager.web.controller.referral.tools.ProfileDocCheckTool;
import com.moseeker.servicemanager.web.controller.referral.vo.ProfileDocParseResult;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.profile.service.ProfileServices;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.ByteBuffer;

/**
 * @Author: jack
 * @Date: 2018/9/4
 */
@Controller
@CounterIface
public class ReferralController {

    private ProfileServices.Iface profileService =  ServiceManager.SERVICEMANAGER.getService(ProfileServices.Iface.class);

    /**
     * 员工上传简历
     * @param file 简历文件
     * @param request 请求数据
     * @return 解析结果
     * @throws Exception 异常信息
     */
    @RequestMapping(value = "/v1/referral/file-parser", method = RequestMethod.POST)
    @ResponseBody
    public String parseFileProfile(@RequestParam(value = "file", required = false) MultipartFile file,
                                HttpServletRequest request) throws Exception {
        Params<String, Object> params = ParamUtils.parseequestParameter(request);
        int employeeId = params.getInt("employee", 0);
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("简历", file);
        validateUtil.addIntTypeValidate("员工", employeeId, 1, null);
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(result)) {

            if (!ProfileDocCheckTool.checkFileName(file.getName())) {
                return Result.fail("文件格式不支持！").toJson();
            }
            if (!ProfileDocCheckTool.checkFileLength(file.getSize())) {
                return Result.fail("文件过大！").toJson();
            }

            ByteBuffer byteBuffer = ByteBuffer.wrap(file.getBytes());

            com.moseeker.thrift.gen.profile.struct.ProfileParseResult result1 =
                    profileService.parseFileProfile(employeeId, file.getName(), byteBuffer);
            ProfileDocParseResult parseResult = new ProfileDocParseResult();
            BeanUtils.copyProperties(result1, parseResult);
            return Result.success(parseResult).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    /**
     * 员工推荐简历
     * @param id 员工编号
     * @param referralForm 推荐表单
     * @return 推荐结果
     * @throws Exception
     */
    @RequestMapping(value = "/v1/employee/{id}/referral", method = RequestMethod.POST)
    @ResponseBody
    public String referralProfile(@PathVariable int id, @RequestParam ReferralForm referralForm) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("手机", referralForm.getMobile());
        validateUtil.addRegExpressValidate("手机", referralForm.getMobile(), FormCheck.getMobileExp());
        validateUtil.addRequiredValidate("姓名", referralForm.getName());
        validateUtil.addRequiredOneValidate("推荐理由", referralForm.getReferralReasons());

        validateUtil.addIntTypeValidate("员工", id, 1, null);
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(result)) {

            int referralId = profileService.employeeReferralProfile(id, referralForm.getName(),
                    referralForm.getMobile(), referralForm.getReferralReasons());
            return Result.success(referralId).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }
}
