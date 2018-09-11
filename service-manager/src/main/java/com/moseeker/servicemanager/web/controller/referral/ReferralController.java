package com.moseeker.servicemanager.web.controller.referral;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.FormCheck;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.web.controller.MessageType;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.servicemanager.web.controller.referral.form.CandidateInfo;
import com.moseeker.servicemanager.web.controller.referral.form.ClainForm;
import com.moseeker.servicemanager.web.controller.referral.form.PCUploadProfileTypeForm;
import com.moseeker.servicemanager.web.controller.referral.form.ReferralForm;
import com.moseeker.servicemanager.web.controller.referral.tools.ProfileDocCheckTool;
import com.moseeker.servicemanager.web.controller.referral.vo.City;
import com.moseeker.servicemanager.web.controller.referral.vo.ProfileDocParseResult;
import com.moseeker.servicemanager.web.controller.referral.vo.ReferralPositionInfo;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.employee.service.EmployeeService;
import com.moseeker.thrift.gen.employee.struct.ReferralCard;
import com.moseeker.thrift.gen.employee.struct.ReferralPosition;
import com.moseeker.thrift.gen.profile.service.ProfileServices;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import com.moseeker.thrift.gen.useraccounts.struct.ClaimReferralCardForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jack
 * @Date: 2018/9/4
 */
@Controller
@CounterIface
public class ReferralController {

    private ProfileServices.Iface profileService =  ServiceManager.SERVICEMANAGER.getService(ProfileServices.Iface.class);
    private EmployeeService.Iface employeeService =  ServiceManager.SERVICEMANAGER.getService(EmployeeService.Iface.class);
    private UseraccountsServices.Iface userService =  ServiceManager.SERVICEMANAGER.getService(UseraccountsServices.Iface.class);

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

            if (!ProfileDocCheckTool.checkFileName(file.getOriginalFilename())) {
                return Result.fail("文件格式不支持！").toJson();
            }
            if (!ProfileDocCheckTool.checkFileLength(file.getSize())) {
                return Result.fail("文件过大！").toJson();
            }

            ByteBuffer byteBuffer = ByteBuffer.wrap(file.getBytes());

            com.moseeker.thrift.gen.profile.struct.ProfileParseResult result1 =
                    profileService.parseFileProfile(employeeId, file.getOriginalFilename(), byteBuffer);
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
    public String referralProfile(@PathVariable int id, @RequestBody ReferralForm referralForm) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("手机", referralForm.getMobile());
        validateUtil.addRegExpressValidate("手机", referralForm.getMobile(), FormCheck.getMobileExp());
        validateUtil.addRequiredValidate("姓名", referralForm.getName());
        validateUtil.addRequiredOneValidate("推荐理由", referralForm.getReferralReasons());
        validateUtil.addIntTypeValidate("员工", id, 1, null);
        validateUtil.addIntTypeValidate("appid", referralForm.getAppid(), 1, null);
        validateUtil.addIntTypeValidate("推荐类型", referralForm.getReferralType(), 1, 4);
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(result)) {

            int referralId = profileService.employeeReferralProfile(id, referralForm.getName(),
                    referralForm.getMobile(), referralForm.getReferralReasons(), referralForm.getPosition(),
                    (byte) referralForm.getReferralType());
            return Result.success(referralId).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    /**
     * 员工提交候选人关键信息
     * @param id 员工编号
     * @param form 表单新
     * @return 职位信息
     * @throws Exception
     */
    @RequestMapping(value = "/v1/employee/{id}/post-candidate-info", method = RequestMethod.POST)
    @ResponseBody
    public String postCandidateInfo(@PathVariable int id, @RequestBody CandidateInfo form) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredStringValidate("姓名", form.getName());
        validateUtil.addRequiredStringValidate("手机号码", form.getMobile());
        validateUtil.addRequiredStringValidate("邮箱", form.getEmail());
        validateUtil.addRequiredStringValidate("就职公司", form.getCompany());
        validateUtil.addRequiredStringValidate("就职职位", form.getPosition());
        validateUtil.addRequiredOneValidate("推荐理由", form.getReasons());
        validateUtil.addIntTypeValidate("appid", form.getAppid(), 1, null);
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {

            com.moseeker.thrift.gen.profile.struct.CandidateInfo candidateInfoStruct = new com.moseeker.thrift.gen.profile.struct.CandidateInfo();
            BeanUtils.copyProperties(candidateInfoStruct, form);
            int referralLogId = profileService.postCandidateInfo(id, candidateInfoStruct);
            return Result.success(referralLogId).toJson();
        } else {
            return Result.validateFailed(result).toJson();
        }
    }

    /**
     * 设置电脑端上传的推荐职位信息
     * @param id 员工
     * @param form 表单信息
     * @return 操作结果
     * @throws Exception 异常信息
     */
    @RequestMapping(value = "/v1/employee/{id}/referral-type", method = RequestMethod.POST)
    @ResponseBody
    public String setReferralType(@PathVariable int id, @RequestBody PCUploadProfileTypeForm form) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("职位", form.getPosition(), 1, null);
        validateUtil.addIntTypeValidate("appid", form.getAppid(), 1, null);
        validateUtil.addIntTypeValidate("员工", id, 1, null);
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(result)) {
            if (form.getType() == 0) {
                form.setType(2);
            }
            employeeService.setUploadType(id, form.getPosition(), (byte) form.getType());
            return Result.success().toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    /**
     * 获取电脑端上传配置的职位信息
     * @param id 员工编号
     * @param form 表单新
     * @return 职位信息
     * @throws Exception
     */
    @RequestMapping(value = "/v1/employee/{id}/referral-type", method = RequestMethod.GET)
    @ResponseBody
    public String getReferralType(@PathVariable int id, @RequestBody PCUploadProfileTypeForm form) throws Exception {
        if (form.getAppid() == 0) {
            return Result.fail(MessageType.APPID_NOT_EXIST).toJson();
        }

        ReferralPosition referralPosition = employeeService.getUploadType(id);

        return Result.success(convertReferralPosition(referralPosition)).toJson();
    }

    /**
     * 获取电脑端上传配置的职位信息
     * @param id 员工编号
     * @param request http请求参数
     * @return 职位信息
     * @throws Exception
     */
    @RequestMapping(value = "/v1/referral-records/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getReferralCard(@PathVariable int id, HttpServletRequest request) throws Exception {
        String appid = request.getParameter("appid");
        if (StringUtils.isBlank(appid)) {
            return Result.fail(MessageType.APPID_NOT_EXIST).toJson();
        }
        ReferralCard referralCard = employeeService.getReferralCard(id);
        com.moseeker.servicemanager.web.controller.referral.vo.ReferralCard card = new com.moseeker.servicemanager.web.controller.referral.vo.ReferralCard();
        BeanUtils.copyProperties(referralCard, card);
        return Result.success(card).toJson();
    }

    @RequestMapping(value = "/v1/referral/claim", method = RequestMethod.POST)
    @ResponseBody
    public String claimReferralCard(@RequestBody ClainForm clainForm) throws Exception {

        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("appid", clainForm.getAppid(), 1, null);
        validateUtil.addIntTypeValidate("推荐卡片", clainForm.getReferralRecordId(), 1, null);
        validateUtil.addIntTypeValidate("用户", clainForm.getUser(), 1, null);
        validateUtil.addRequiredStringValidate("用户姓名", clainForm.getName());
        String validateResult = validateUtil.validate();
        if (StringUtils.isNotBlank(validateResult)) {
            ClaimReferralCardForm form = new ClaimReferralCardForm();
            form.setName(clainForm.getName());
            form.setMobile(clainForm.getMobile());
            form.setReferralRecordId(clainForm.getReferralRecordId());
            form.setUserId(clainForm.getUser());
            form.setVerifyCode(clainForm.getVcode());
            userService.claimReferralCard(form);
            return Result.validateFailed(validateResult).toJson();
        } else {
            return Result.success(true).toJson();
        }
    }

    private ReferralPositionInfo convertReferralPosition(ReferralPosition referralPosition) {
        ReferralPositionInfo referralPositionInfo = new ReferralPositionInfo();
        referralPositionInfo.setCompanyAbbreviation(referralPosition.getCompanyAbbreviation());
        referralPositionInfo.setCompanyName(referralPosition.getCompanyName());
        referralPositionInfo.setExperience(referralPosition.getExperience());
        referralPositionInfo.setExperienceAbove(referralPosition.isExperienceAbove());
        referralPositionInfo.setId(referralPosition.getId());
        referralPositionInfo.setLogo(referralPosition.getLogo());
        referralPositionInfo.setSalaryBottom(referralPosition.getSalaryBottom());
        referralPositionInfo.setSalaryTop(referralPosition.getSalaryTop());
        referralPositionInfo.setTeam(referralPosition.getTeam());
        referralPositionInfo.setTitle(referralPosition.getTitle());
        if (referralPosition.getCities() != null && referralPosition.getCities().size() > 0) {
            List<City> cities = referralPosition.getCities().stream().map(cityStruct -> {
                City city = new City();
                city.setCode(cityStruct.getCode());
                city.setName(cityStruct.getName());
                city.setEname(cityStruct.getEname());
                return city;
            }).collect(Collectors.toList());
            referralPositionInfo.setCities(cities);
        }
        return referralPositionInfo;
    }
}
