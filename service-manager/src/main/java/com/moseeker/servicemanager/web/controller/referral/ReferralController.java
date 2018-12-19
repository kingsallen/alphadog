package com.moseeker.servicemanager.web.controller.referral;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BonusTools;
import com.moseeker.common.util.FormCheck;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.commonservice.utils.ProfileDocCheckTool;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.MessageType;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.servicemanager.web.controller.referral.form.*;
import com.moseeker.servicemanager.web.controller.referral.vo.*;
import com.moseeker.servicemanager.web.controller.referral.vo.Bonus;
import com.moseeker.servicemanager.web.controller.referral.vo.BonusList;
import com.moseeker.servicemanager.web.controller.referral.vo.ContactPushInfo;
import com.moseeker.servicemanager.web.controller.referral.vo.RedPacket;
import com.moseeker.servicemanager.web.controller.referral.vo.ReferralProfileTab;
import com.moseeker.servicemanager.web.controller.referral.vo.ReferralReasonInfo;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.employee.service.EmployeeService;
import com.moseeker.thrift.gen.employee.struct.BonusVOPageVO;
import com.moseeker.thrift.gen.employee.struct.EmployeeInfo;
import com.moseeker.thrift.gen.employee.struct.ReferralPosition;
import com.moseeker.thrift.gen.profile.service.ProfileServices;
import com.moseeker.thrift.gen.referral.service.ReferralService;
import com.moseeker.thrift.gen.referral.struct.*;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import com.moseeker.thrift.gen.useraccounts.struct.ClaimReferralCardForm;
import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
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
    private Logger logger = LoggerFactory.getLogger(ReferralController.class);
    DecimalFormat bonusFormat = new DecimalFormat("###################");

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
        validateUtil.addRequiredValidate("手机号", referralForm.getMobile());
        validateUtil.addRegExpressValidate("手机号", referralForm.getMobile(), FormCheck.getMobileExp());
        validateUtil.addRequiredValidate("姓名", referralForm.getName());
        validateUtil.addRequiredValidate("推荐关系", referralForm.getRelationship());
        validateUtil.addRequiredOneValidate("推荐理由", referralForm.getReferralReasons());
        validateUtil.addIntTypeValidate("员工", id, 1, null);
        validateUtil.addIntTypeValidate("appid", referralForm.getAppid(), 0, null);
        validateUtil.addIntTypeValidate("推荐类型", referralForm.getReferralType(), 1, 4);
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(result)) {

            int referralId = profileService.employeeReferralProfile(id, referralForm.getName(),
                    referralForm.getMobile(), referralForm.getReferralReasons(), referralForm.getPosition(),
                    (byte)referralForm.getRelationship(), referralForm.getRecomReasonText(),(byte) referralForm.getReferralType());
            return Result.success(referralId).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    /**
     * 获取该候选人员工内推理由
     * @param appid
     * @param userId
     * @param companyId
     * @param hrId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/v1/referral/evaluation", method = RequestMethod.GET)
    @ResponseBody
    public String referralProfile(@RequestParam(value = "appid") Integer appid,
                                  @RequestParam(value = "user_id") Integer userId,
                                  @RequestParam(value = "company_id") Integer companyId,
                                  @RequestParam(value = "hr_id") Integer hrId) throws Exception {
        if (StringUtils.isBlank(String.valueOf(appid))) {
            throw CommonException.PROGRAM_APPID_LOST;
        }
        List<com.moseeker.thrift.gen.referral.struct.ReferralReasonInfo> result = referralService.getReferralReason(userId, companyId, hrId);
        List<com.moseeker.servicemanager.web.controller.referral.vo.ReferralReasonInfo> resultList = new ArrayList<>();
        if (result != null && result.size() > 0) {
            resultList = result.stream().map(tab -> {
                com.moseeker.servicemanager.web.controller.referral.vo.ReferralReasonInfo info =
                        new com.moseeker.servicemanager.web.controller.referral.vo.ReferralReasonInfo();
                BeanUtils.copyProperties(tab, info);
                return info;
            }).collect(Collectors.toList());
        }
        return Result.success(resultList).toJson();

    }

    /**
     * 获取该候选人员工内推理由
     * @param companyId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/v1/referral/conf/information", method = RequestMethod.GET)
    @ResponseBody
    public String referralProfile(@RequestParam(value = "company_id") Integer companyId,HttpServletRequest request) throws Exception {
        if (StringUtils.isBlank(request.getParameter("appid"))) {
            throw CommonException.PROGRAM_APPID_LOST;
        }
        int result = referralService.fetchKeyInformationStatus(companyId);
        return Result.success(result).toJson();

    }

    /**
     * 获取该候选人员工内推理由
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/v1/referral/conf/information", method = RequestMethod.POST)
    @ResponseBody
    public String referralKeyInformation(@RequestBody KeyInformation key, HttpServletRequest request) throws Exception {
        if (StringUtils.isBlank(String.valueOf(key.getAppid()))) {
            throw CommonException.PROGRAM_APPID_LOST;
        }
        referralService.handerKeyInformationStatus(key.getCompanyId(), key.getKeyInformation());
        return Result.success("").toJson();

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
        validateUtil.addIntTypeValidate("职位信息", form.getPosition(), 1, null);
        validateUtil.addRequiredOneValidate("推荐理由", form.getReferralReasons());
        validateUtil.addIntTypeValidate("appid", form.getAppid(), 0, null);
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {

            com.moseeker.thrift.gen.profile.struct.CandidateInfo candidateInfoStruct = new com.moseeker.thrift.gen.profile.struct.CandidateInfo();
            BeanUtils.copyProperties(form, candidateInfoStruct);
            candidateInfoStruct.setPosition(form.getPosition());
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
                                @RequestParam(value = "company_id") Integer companyId,
                                @RequestParam(value = "page_no", defaultValue = "1") Integer pageNo,
                                @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize)
            throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredValidate("用户编号", id);
        validateUtil.addRequiredValidate("公司编号", companyId);

        String validateResult = validateUtil.validate();
        if (StringUtils.isBlank(validateResult)) {
            com.moseeker.thrift.gen.referral.struct.RedPackets redPackets = referralService.getRedPackets(id, companyId,
                    pageNo, pageSize);

            com.moseeker.servicemanager.web.controller.referral.vo.RedPackets result
                    = new com.moseeker.servicemanager.web.controller.referral.vo.RedPackets();
            BeanUtils.copyProperties(redPackets, result);
            if (redPackets.getRedpackets() != null && redPackets.getRedpackets().size() > 0) {
                result.setRedpackets(redPackets.getRedpackets().stream().map(redPacket -> {
                    RedPacket redPacketStruct = new RedPacket();
                    BeanUtils.copyProperties(redPacket, redPacketStruct);
                    redPacketStruct.setValue(BonusTools.convertToBonus(redPacket.getValue()));
                    return redPacketStruct;
                }).collect(Collectors.toList()));
            }
            result.setTotalBonus(BonusTools.convertToBonus(redPackets.getTotalBonus()));
            result.setTotalRedpackets(BonusTools.convertToBonus(redPackets.getTotalRedpackets()));
            return Result.success(result).toJson();
        } else {
            return Result.validateFailed(validateResult).toJson();
        }
    }

    @RequestMapping(value = "/v1/referral/users/{id}/bonus", method = RequestMethod.GET)
    @ResponseBody
    public String getBonus(@PathVariable Integer id,
                                @RequestParam(value = "appid") Integer appid,
                                @RequestParam(value = "company_id") Integer companyId,
                                @RequestParam(value = "page_no", defaultValue = "1") Integer pageNo,
                                @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize)
            throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredValidate("用户编号", id);
        validateUtil.addRequiredValidate("公司编号", companyId);

        String validateResult = validateUtil.validate();
        if (StringUtils.isBlank(validateResult)) {
            com.moseeker.thrift.gen.referral.struct.BonusList bonusList = referralService.getBonus(id, companyId, pageNo, pageSize);

            BonusList result = new BonusList();
            BeanUtils.copyProperties(bonusList, result);

            if (bonusList.getBonus() != null && bonusList.getBonus().size() > 0) {
                result.setBonus(bonusList.getBonus().stream().map(bonusStruct -> {
                    Bonus bonus = new Bonus();
                    BeanUtils.copyProperties(bonusStruct, bonus);

                    bonus.setValue(BonusTools.convertToBonus(bonusStruct.getValue()));
                    return bonus;
                }).collect(Collectors.toList()));
            }
            result.setTotalBonus(BonusTools.convertToBonus(bonusList.getTotalBonus()));
            result.setTotalRedpackets(BonusTools.convertToBonus(bonusList.getTotalRedpackets()));
            return Result.success(result).toJson();
        } else {
            return Result.validateFailed(validateResult).toJson();
        }
    }



    @RequestMapping(value = "/v1/referral/profile/{id}/tab", method = RequestMethod.GET)
    @ResponseBody
    public String getProfileTab(@PathVariable Integer id,
                           @RequestParam(value = "appid") Integer appid,
                           @RequestParam(value = "company_id") Integer companyId,
                           @RequestParam(value = "hr_id") Integer hrId)
            throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredValidate("用户编号", id);
        validateUtil.addRequiredValidate("公司编号", companyId);
        validateUtil.addRequiredValidate("HR编号", hrId);
        String validateResult = validateUtil.validate();
        if (StringUtils.isBlank(validateResult)) {
            List<com.moseeker.thrift.gen.referral.struct.ReferralProfileTab> tabList = referralService
                    .getReferralProfileList(id, companyId, hrId);

            List<ReferralProfileTab> result = new ArrayList<>();
            if (tabList != null && tabList.size() > 0) {
                result = tabList.stream().map(tab -> {
                    ReferralProfileTab profileTab = new ReferralProfileTab();
                    BeanUtils.copyProperties(tab, profileTab);
                    return profileTab;
                }).collect(Collectors.toList());
            }
            return Result.success(tabList).toJson();
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
    }/**/

    /**
     * HR端根据员工获取内推奖金明细
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/v1/referral/hr/employee/{id}/bonus/detail", method = RequestMethod.GET)
    @ResponseBody
    public String getEmployeeBonusDetail(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer id) throws Exception {
        Params<String, Object> params = ParamUtils.parseRequestParam(request);
        int employeeId = id;
        int companyId = params.getInt("company_id", 0);
        int pageNumber = params.getInt("page_number", 0);
        int pageSize = params.getInt("page_size", 0);
        if (employeeId == 0) {
            return ResponseLogNotification.fail(request, "员工Id不能为空");
        } else {
            BonusVOPageVO result = userHrAccountService.getEmployeeBonus(employeeId, companyId, pageNumber, pageSize);
            return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(com.moseeker.baseorm.util.BeanUtils.convertStructToJSON(result)));
        }
    }

    /**
     * 员工推荐简历，mobot上传简历使用，走内推的员工推荐逻辑
     * @param id 员工编号
     * @param referralForm 推荐表单
     * @return 推荐结果
     * @throws Exception
     */
    @RequestMapping(value = "/v1/employee/{id}/referral/confirm", method = RequestMethod.POST)
    @ResponseBody
    public String saveMobotReferralProfile(@PathVariable int id, @RequestBody ReferralPositionForm referralForm) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("员工", id, 1, null);
        validateUtil.addIntTypeValidate("appid", referralForm.getAppid(), 0, null);
        validateUtil.addRequiredOneValidate("推荐职位ids", referralForm.getIds());
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(result)) {
            Map<String, String> idReasons = profileService.saveMobotReferralProfile(id, referralForm.getIds());
            if(idReasons.get("state") == null){
                return Result.success(JSONArray.parseArray(idReasons.get("list"))).toJson();
            }else {
                return new Result(-1, "apply_limit", idReasons).toJson();
            }

        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }


    /**
     * 员工推荐简历，mobot上传简历使用，将推荐信息放到redis中，无插库操作
     * @param id 员工编号
     * @param referralForm 推荐表单
     * @return 推荐结果
     * @throws Exception
     */
    @RequestMapping(value = "/v1/employee/{id}/referral/cache", method = RequestMethod.POST)
    @ResponseBody
    public String saveMobotReferralProfileCache(@PathVariable int id, @RequestBody ReferralForm referralForm) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("手机", referralForm.getMobile());
        validateUtil.addRegExpressValidate("手机", referralForm.getMobile(), FormCheck.getMobileExp());
        validateUtil.addRequiredValidate("姓名", referralForm.getName());
        validateUtil.addRequiredValidate("文件名称", referralForm.getFileName());
        validateUtil.addRequiredOneValidate("推荐理由", referralForm.getReferralReasons());
        validateUtil.addIntTypeValidate("员工", id, 1, null);
        validateUtil.addIntTypeValidate("appid", referralForm.getAppid(), 0, null);
        validateUtil.addIntTypeValidate("推荐类型", referralForm.getReferralType(), 1, 4);
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(result)) {

            int userId = profileService.saveMobotReferralProfileCache(id, referralForm.getName(), referralForm.getMobile(),
                    referralForm.getReferralReasons(), (byte) referralForm.getReferralType(), referralForm.getFileName(),
                    referralForm.getRelationship(), referralForm.getRecomReasonText());
            return Result.success(userId).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    @RequestMapping(value = "/v1/referral/claim/batch", method = RequestMethod.POST)
    @ResponseBody
    public String batchClaimReferralCard(@RequestBody BatchClaimForm claimForm) throws Exception {

        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("appid", claimForm.getAppid(), 0, null);
        validateUtil.addIntTypeValidate("用户", claimForm.getUser(), 1, null);
        validateUtil.addRequiredOneValidate("推荐卡片", claimForm.getReferralRecordIds());
        validateUtil.addRequiredStringValidate("用户姓名", claimForm.getName());
        logger.info("user:{},recordIds:{},name:{}", claimForm.getUser(), claimForm.getReferralRecordIds(), claimForm.getName());
        String validateResult = validateUtil.validate();
        if (StringUtils.isBlank(validateResult)) {
            String claimResults = userService.batchClaimReferralCard(claimForm.getUser(), claimForm.getName(), claimForm.getMobile(), claimForm.getVcode(), claimForm.getReferralRecordIds());
            return Result.success(JSONArray.parseArray(claimResults)).toJson();
        } else {
            return Result.validateFailed(validateResult).toJson();
        }
    }

    /**
     * 点击告诉ta时回填推荐信息，从缓存中取
     * @param id 员工编号
     * @return 推荐结果
     * @throws Exception
     */
    @RequestMapping(value = "/v1/employee/{id}/referral/cache", method = RequestMethod.GET)
    @ResponseBody
    public String getMobotReferralCache(@PathVariable int id) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("员工", id, 1, null);
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(result)) {
            String jsonResult = profileService.getMobotReferralCache(id);
            jsonResult = (jsonResult == null ? "":jsonResult);
            ReferralInfoCache referralInfoCache = JSONObject.parseObject(jsonResult, ReferralInfoCache.class);
            return Result.success(referralInfoCache).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    /**
     * 10分钟消息模板-人脉筛选，获取卡片数据
     * @return 推荐结果
     * @throws Exception
     */
    @RequestMapping(value = "/v1/referral/radar/cards", method = RequestMethod.GET)
    @ResponseBody
    public String getRadarCards(HttpServletRequest request) throws Exception {
        Params<String, Object> params = ParamUtils.parseRequestParam(request);
        ReferralCardForm referralCard = ParamUtils.initModelForm(params, ReferralCardForm.class);
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("员工userId", referralCard.getUser_id(), 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("appid", referralCard.getAppid(), 0, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("员工userId", referralCard.getUser_id());
        validateUtil.addRequiredValidate("appid", referralCard.getAppid());
        validateUtil.addRequiredValidate("timestamp", referralCard.getTimestamp());
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            if(referralCard.getPage_number() == null || referralCard.getPage_number() <= 0){
                referralCard.setPage_number(1);
            }
            if(referralCard.getPage_size() == null || referralCard.getPage_size() <= 0){
                referralCard.setPage_size(10);
            }
            ReferralCardInfo cardInfo = new ReferralCardInfo();
            BeanUtils.copyProperties(referralCard, cardInfo);
            String jsonResult = referralService.getRadarCards(cardInfo);
            jsonResult = (jsonResult == null ? "":jsonResult);
            JSONArray response = JSONArray.parseArray(jsonResult);
            return Result.success(response).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    /**
     * 10分钟消息模板-邀请投递
     * @param inviteForm 邀请浏览职位的员工投递
     * @return 推荐结果
     * @throws Exception
     */
    @RequestMapping(value = "/v1/referral/radar/invite", method = RequestMethod.POST)
    @ResponseBody
    public String inviteApplication(@RequestBody ReferralInviteForm inviteForm) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateInviteInfo(validateUtil, inviteForm);
        validateUtil.addIntTypeValidate("公司id", inviteForm.getCompanyId(), 1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("公司id", inviteForm.getCompanyId());
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            ReferralInviteInfo inviteInfo = new ReferralInviteInfo();
            BeanUtils.copyProperties(inviteForm, inviteInfo);
            String jsonResult = referralService.inviteApplication(inviteInfo);
            jsonResult = (jsonResult == null ? "" : jsonResult);
            JSONObject response = JSONObject.parseObject(jsonResult);
            return Result.success(response).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }
    /**
     *  联系内推-预览个人档案确认提交
     * @param
     * @return 推荐结果
     * @throws Exception
     */
    @RequestMapping(value = "/v1/referral/contact/push", method = RequestMethod.POST)
    @ResponseBody
    public String referralContactPush(@RequestBody ReferralContactForm form) throws Exception {

        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("appid", form.getAppid());
        validateUtil.addRequiredValidate("用户编号", form.getUserId());
        validateUtil.addRequiredValidate("职位编号", form.getPositionId());
        validateUtil.addRequiredValidate("员工user编号", form.getPostUserId());
        validateUtil.addRequiredValidate("来源", form.getOrigin());
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(result)) {
            referralService.addUserSeekRecommend(form.getUserId(), form.getPostUserId(), form.getPositionId(), form.getOrigin());
            return Result.success().toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    private void validateInviteInfo(ValidateUtil validateUtil, ReferralInviteForm inviteForm){
        validateUtil.addIntTypeValidate("员工userId", inviteForm.getUserId(), 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("被邀请人id", inviteForm.getEndUserId(), 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("appid", inviteForm.getAppid(), 0, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("职位id", inviteForm.getPid(), 1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("员工userId", inviteForm.getUserId());
        validateUtil.addRequiredValidate("appid", inviteForm.getAppid());
        validateUtil.addRequiredValidate("职位id", inviteForm.getPid());
        validateUtil.addRequiredValidate("timestamp", inviteForm.getTimestamp());
        validateUtil.addRequiredValidate("当前处理的endUserId", inviteForm.getEndUserId());
    }

    /**
     * 10分钟消息模板-我不熟悉
     * @param inviteForm 跳过当前不熟悉的浏览人
     * @return 推荐结果
     * @throws Exception
     */
    @RequestMapping(value = "/v1/referral/radar/ignore", method = RequestMethod.POST)
    @ResponseBody
    public String ignoreCurrentViewer(@RequestBody ReferralInviteForm inviteForm) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateInviteInfo(validateUtil, inviteForm);
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            ReferralInviteInfo inviteInfo = new ReferralInviteInfo();
            BeanUtils.copyProperties(inviteForm, inviteInfo);
            referralService.ignoreCurrentViewer(inviteInfo);
            return Result.success().toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    /**
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/v1/employee/referral/evaluate", method = RequestMethod.POST)
    @ResponseBody
    public String employeeReferralEvaluate(@RequestBody ReferralEvaluateForm form) throws Exception {

        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("appid", form
                .getAppid());
        validateUtil.addRequiredValidate("职位编号", form.getPositionId());
        validateUtil.addRequiredValidate("员工user编号", form.getPostUseId());
        validateUtil.addRequiredValidate("内推编号", form.getReferralId());
        validateUtil.addRequiredValidate("推荐人与被推荐人关系", form.getRelationship());
        validateUtil.addStringLengthValidate("推荐理由文本", form.getRecomReasonText(), "推荐理由文本长度过长",
                "推荐理由文本长度过长",null, 201);
        validateUtil.addSensitiveValidate("推荐理由文本", form.getRecomReasonText(), null, null);
        validateUtil.addRequiredOneValidate("推荐理由", form.getReferralReasons());
        if (form.getReferralReasons() != null) {
            String reasons = form.getReferralReasons().stream().collect(Collectors.joining(","));
            validateUtil.addStringLengthValidate("推荐理由", reasons, null, 512);
        }
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(result)) {
            referralService.employeeReferralReason(form.getPostUseId(),form.getPostUseId(), form.getReferralId(), form.getReferralReasons(),
                    form.getRelationship(), form.getRecomReasonText());
            return Result.success().toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    /**
     * 点击人脉连连看按钮/点击分享的人脉连连看页面
     * @param radarForm 连接人脉雷达的参数
     * @return 推荐结果
     * @throws Exception
     */
    @RequestMapping(value = "/v1/referral/radar/connect", method = RequestMethod.POST)
    @ResponseBody
    public String connectRadar(@RequestBody ConnectRadarForm radarForm) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("转发人", radarForm.getRecomUserId(), 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("被邀请连线人id", radarForm.getNextUserId(), 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("appid", radarForm.getAppid(), 0, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("人脉连连看id", radarForm.getChainId(), 1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("转发人", radarForm.getRecomUserId());
        validateUtil.addRequiredValidate("被邀请连线人id", radarForm.getNextUserId());
        validateUtil.addRequiredValidate("appid", radarForm.getAppid());
        validateUtil.addRequiredValidate("人脉连连看id", radarForm.getChainId());
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            ConnectRadarInfo radarInfo = new ConnectRadarInfo();
            BeanUtils.copyProperties(radarForm, radarInfo);
            String jsonResult = referralService.connectRadar(radarInfo);
            jsonResult = (jsonResult == null ? "":jsonResult);
            JSONObject response = JSONObject.parseObject(jsonResult);
            return Result.success(response).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

     /**
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/v1/employee/seek/recommend", method = RequestMethod.GET)
    @ResponseBody
    public String employeeReferralEvaluate(@RequestParam(value = "appid") int appid,
                                           @RequestParam(value = "referral_id") int referralId,
                                           @RequestParam(value = "post_user_id") int postUserId) throws Exception {

        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredValidate("员工user编号", postUserId);
        validateUtil.addRequiredValidate("内推编号", referralId);
        String message = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(message)) {
            com.moseeker.thrift.gen.referral.struct.ContactPushInfo redult = referralService.fetchSeekRecommend(referralId, postUserId);
            com.moseeker.servicemanager.web.controller.referral.vo.ContactPushInfo info =
                    new com.moseeker.servicemanager.web.controller.referral.vo.ContactPushInfo();
            BeanUtils.copyProperties(redult, info);
            return Result.success(info).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(message).toJson();
        }
    }

    /**
     * 候选人打开职位连接判断推荐人是否是员工
     * @param checkForm 检验转发链路的起点是否是员工
     * @return 推荐结果
     * @throws Exception
     */
    @RequestMapping(value = "/v1/referral/employee/check", method = RequestMethod.POST)
    @ResponseBody
    public String checkEmployee(@RequestBody CheckEmployeeForm checkForm) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("转发人", checkForm.getRecomUserId(), 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("转发链路parentChainId", checkForm.getParentChainId(), 0, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("appid", checkForm.getAppid(), 0, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("职位id", checkForm.getPid(), 1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("转发人", checkForm.getRecomUserId());
        validateUtil.addRequiredValidate("转发链路parentChainId", checkForm.getParentChainId());
        validateUtil.addRequiredValidate("appid", checkForm.getAppid());
        validateUtil.addRequiredValidate("职位id", checkForm.getPid());
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            CheckEmployeeInfo checkInfo = new CheckEmployeeInfo();
            BeanUtils.copyProperties(checkForm, checkInfo);
            String jsonResult = referralService.checkEmployee(checkInfo);
            jsonResult = (jsonResult == null ? "":jsonResult);
            JSONObject response = JSONObject.parseObject(jsonResult);
            return Result.success(response).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }
}
