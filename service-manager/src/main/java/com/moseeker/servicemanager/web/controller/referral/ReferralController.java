package com.moseeker.servicemanager.web.controller.referral;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.FormCheck;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.commonservice.utils.ProfileDocCheckTool;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.MessageType;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.servicemanager.web.controller.referral.form.CandidateInfo;
import com.moseeker.servicemanager.web.controller.referral.form.ClaimForm;
import com.moseeker.servicemanager.web.controller.referral.form.PCUploadProfileTypeForm;
import com.moseeker.servicemanager.web.controller.referral.form.ReferralForm;
import com.moseeker.servicemanager.web.controller.referral.vo.*;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.employee.service.EmployeeService;
import com.moseeker.thrift.gen.employee.struct.BonusVOPageVO;
import com.moseeker.thrift.gen.employee.struct.EmployeeInfo;
import com.moseeker.thrift.gen.employee.struct.ReferralPosition;
import com.moseeker.thrift.gen.profile.service.ProfileServices;
import com.moseeker.thrift.gen.referral.service.ReferralService;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import com.moseeker.thrift.gen.useraccounts.struct.ClaimReferralCardForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private ReferralService.Iface referralService =  ServiceManager.SERVICEMANAGER.getService(ReferralService.Iface.class);
    private UserHrAccountService.Iface userHrAccountService = ServiceManager.SERVICEMANAGER.getService(UserHrAccountService.Iface.class);
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
        validateUtil.addRequiredStringValidate("简历名称", params.getString("file_name"));
        validateUtil.addIntTypeValidate("员工", employeeId, 1, null);
        validateUtil.addRequiredValidate("appid", params.getInt("appid"));
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(result)) {

            if (!ProfileDocCheckTool.checkFileName(params.getString("file_name"))) {
                return Result.fail(MessageType.PROGRAM_FILE_NOT_SUPPORT).toJson();
            }
            if (!ProfileDocCheckTool.checkFileLength(file.getSize())) {
                return Result.fail(MessageType.PROGRAM_FILE_OVER_SIZE).toJson();
            }

            ByteBuffer byteBuffer = ByteBuffer.wrap(file.getBytes());

            com.moseeker.thrift.gen.profile.struct.ProfileParseResult result1 =
                    profileService.parseFileProfile(employeeId, params.getString("file_name"), byteBuffer);
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
        validateUtil.addIntTypeValidate("appid", referralForm.getAppid(), 0, null);
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
     * 员工删除上传的推荐简历
     * @param id 员工编号
     * @return 推荐结果
     * @throws Exception 业务异常
     */
    @RequestMapping(value = "/v1/employee/{id}/referral", method = RequestMethod.DELETE)
    @ResponseBody
    public String referralProfile(@PathVariable int id, HttpServletRequest request) throws Exception {

        if (StringUtils.isBlank(request.getParameter("appid"))) {
            throw CommonException.PROGRAM_APPID_LOST;
        }
        profileService.employeeDeleteReferralProfile(id);
        return Result.success(true).toJson();
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
        validateUtil.addIntTypeValidate("职位信息", form.getPosition(), 1, null);
        validateUtil.addRequiredStringValidate("就职职位", form.getJob());
        validateUtil.addRequiredOneValidate("推荐理由", form.getReferralReasons());
        validateUtil.addIntTypeValidate("appid", form.getAppid(), 0, null);
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {

            com.moseeker.thrift.gen.profile.struct.CandidateInfo candidateInfoStruct = new com.moseeker.thrift.gen.profile.struct.CandidateInfo();
            BeanUtils.copyProperties(form, candidateInfoStruct);
            candidateInfoStruct.setPositionId(form.getPosition());
            candidateInfoStruct.setReasons(form.getReferralReasons());
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
        validateUtil.addIntTypeValidate("appid", form.getAppid(), 0, null);
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
     * @param request 表单新
     * @return 职位信息
     * @throws Exception
     */
    @RequestMapping(value = "/v1/employee/{id}/referral-type", method = RequestMethod.GET)
    @ResponseBody
    public String getReferralType(@PathVariable int id, HttpServletRequest request) throws Exception {
        if (request.getParameter("appid") == null) {
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
        com.moseeker.thrift.gen.employee.struct.ReferralCard referralCard = employeeService.getReferralCard(id);
        com.moseeker.servicemanager.web.controller.referral.vo.ReferralCard card = new com.moseeker.servicemanager.web.controller.referral.vo.ReferralCard();
        BeanUtils.copyProperties(referralCard, card);
        return Result.success(card).toJson();
    }

    @RequestMapping(value = "/v1/referral/claim", method = RequestMethod.POST)
    @ResponseBody
    public String claimReferralCard(@RequestBody ClaimForm claimForm) throws Exception {

        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("appid", claimForm.getAppid(), 0, null);
        validateUtil.addIntTypeValidate("推荐卡片", claimForm.getReferralRecordId(), 1, null);
        validateUtil.addIntTypeValidate("用户", claimForm.getUser(), 1, null);
        validateUtil.addRequiredStringValidate("用户姓名", claimForm.getName());
        String validateResult = validateUtil.validate();
        if (StringUtils.isBlank(validateResult)) {
            ClaimReferralCardForm form = new ClaimReferralCardForm();
            form.setName(claimForm.getName());
            form.setMobile(claimForm.getMobile());
            form.setReferralRecordId(claimForm.getReferralRecordId());
            form.setUserId(claimForm.getUser());
            form.setVerifyCode(claimForm.getVcode());
            userService.claimReferralCard(form);
            return Result.success(true).toJson();
        } else {
            return Result.validateFailed(validateResult).toJson();
        }
    }

    @RequestMapping(value = "/v1/referral/users/{id}/employee-info", method = RequestMethod.GET)
    @ResponseBody
    public String getEmployeeInfo(@PathVariable Integer id, @RequestParam(value = "appid") Integer appid) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredValidate("用户编号", id);

        String validateResult = validateUtil.validate();
        if (StringUtils.isBlank(validateResult)) {

            EmployeeInfo employeeInfo = employeeService.getEmployeeInfo(id);
            EmployeeInfoVO employeeInfoVO = new EmployeeInfoVO();
            BeanUtils.copyProperties(employeeInfo, employeeInfoVO);
            return Result.success(employeeInfoVO).toJson();
        } else {
            return Result.validateFailed(validateResult).toJson();
        }
    }

    @RequestMapping(value = "v1/referral/users/{id}/redpackets", method = RequestMethod.GET)
    @ResponseBody
    public String getRedPackets(@PathVariable Integer id,
                                @RequestParam(value = "appid") Integer appid,
                                @RequestParam(value = "page_no", defaultValue = "1") Integer pageNo,
                                @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredValidate("用户编号", id);

        String validateResult = validateUtil.validate();
        if (StringUtils.isBlank(validateResult)) {
            com.moseeker.thrift.gen.referral.struct.RedPackets redPackets = referralService.getRedPackets(id, pageNo, pageSize);

            com.moseeker.servicemanager.web.controller.referral.vo.RedPackets result
                    = new com.moseeker.servicemanager.web.controller.referral.vo.RedPackets();
            BeanUtils.copyProperties(redPackets, result);
            if (redPackets.getRedpackets() != null && redPackets.getRedpackets().size() > 0) {
                result.setRedpackets(redPackets.getRedpackets().stream().map(redPacket -> {
                    RedPacket redPacketStruct = new RedPacket();
                    BeanUtils.copyProperties(redPacket, redPacketStruct);
                    return redPacketStruct;
                }).collect(Collectors.toList()));
            }
            return Result.success(result).toJson();
        } else {
            return Result.validateFailed(validateResult).toJson();
        }
    }

    @RequestMapping(value = "/v1/referral/users/{id}/bonus", method = RequestMethod.GET)
    @ResponseBody
    public String getBonus(@PathVariable Integer id,
                                @RequestParam(value = "appid") Integer appid,
                                @RequestParam(value = "page_no", defaultValue = "1") Integer pageNo,
                                @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredValidate("用户编号", id);

        String validateResult = validateUtil.validate();
        if (StringUtils.isBlank(validateResult)) {
            com.moseeker.thrift.gen.referral.struct.BonusList bonusList = referralService.getBonus(id, pageNo, pageSize);

            BonusList result = new BonusList();
            BeanUtils.copyProperties(bonusList, result);
            if (bonusList.getBonus() != null && bonusList.getBonus().size() > 0) {
                result.setBonus(bonusList.getBonus().stream().map(bonusStruct -> {
                    Bonus bonus = new Bonus();
                    BeanUtils.copyProperties(bonusStruct, bonus);
                    return bonus;
                }).collect(Collectors.toList()));
            }
            return Result.success(result).toJson();
        } else {
            return Result.validateFailed(validateResult).toJson();
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

    /**
     * 微信端员工认领内推奖金记录
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/v1/referral/wechat/employee/{id}/bonus/claim", method = RequestMethod.PUT)
    @ResponseBody
    public String claimReferralBonus(@PathVariable Integer id) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("id", id);
        String validateResult = validateUtil.validate();
        if (StringUtils.isBlank(validateResult)) {
            userService.claimReferralBonus(id);
            return Result.success(true).toJson();
        } else {
            return Result.validateFailed(validateResult).toJson();
        }
    }

    /**
     * HR端根据员工获取内推奖金明细
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/v1/referral/hr/employee/bonus/detail", method = RequestMethod.GET)
    @ResponseBody
    public String getEmployeeBonusDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Params<String, Object> params = ParamUtils.parseRequestParam(request);
        int employeeId = params.getInt("employeeId", 0);
        int companyId = params.getInt("companyId", 0);
        int pageNumber = params.getInt("pageNumber", 0);
        int pageSize = params.getInt("pageSize", 0);
        if (employeeId == 0) {
            return ResponseLogNotification.fail(request, "员工Id不能为空");
        } else {
            BonusVOPageVO result = userHrAccountService.getEmployeeBonus(employeeId, companyId, pageNumber, pageSize);
            return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(com.moseeker.baseorm.util.BeanUtils.convertStructToJSON(result)));
        }
    }
}
