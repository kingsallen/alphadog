package com.moseeker.servicemanager.web.controller.referral;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BonusTools;
import com.moseeker.common.util.FormCheck;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.commonservice.utils.ProfileDocCheckTool;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.exception.ProfileException;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.MessageType;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.servicemanager.web.controller.referral.form.*;
import com.moseeker.servicemanager.web.controller.referral.vo.*;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.employee.service.EmployeeService;
import com.moseeker.thrift.gen.employee.struct.BonusVOPageVO;
import com.moseeker.thrift.gen.employee.struct.EmployeeInfo;
import com.moseeker.thrift.gen.employee.struct.ReferralPosition;
import com.moseeker.thrift.gen.profile.service.ProfileServices;
import com.moseeker.thrift.gen.profile.struct.MobotReferralResult;
import com.moseeker.thrift.gen.referral.service.ReferralService;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import com.moseeker.thrift.gen.useraccounts.struct.ClaimReferralCardForm;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: jack
 * @Date: 2018/9/4
 */
@Controller
@CounterIface
public class ReferralController {

    private ProfileServices.Iface profileService =  ServiceManager.SERVICE_MANAGER.getService(ProfileServices.Iface.class);
    private EmployeeService.Iface employeeService =  ServiceManager.SERVICE_MANAGER.getService(EmployeeService.Iface.class);
    private UseraccountsServices.Iface userService =  ServiceManager.SERVICE_MANAGER.getService(UseraccountsServices.Iface.class);
    private ReferralService.Iface referralService =  ServiceManager.SERVICE_MANAGER.getService(ReferralService.Iface.class);
    private UserHrAccountService.Iface userHrAccountService = ServiceManager.SERVICE_MANAGER.getService(UserHrAccountService.Iface.class);
    private Logger logger = LoggerFactory.getLogger(ReferralController.class);

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
        Integer appid = params.getInt("appid");
        String fileName = params.getString("file_name");
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("简历", file);
        validateUtil.addRequiredStringValidate("简历名称", fileName);
        validateUtil.addIntTypeValidate("员工", employeeId, 1, null);
        validateUtil.addRequiredValidate("appid", appid);
        String result = validateUtil.validate();

        if (org.apache.commons.lang.StringUtils.isBlank(result)) {

            if (!ProfileDocCheckTool.checkFileFormat(params.getString("file_name"),file.getBytes())) {
                return Result.fail(MessageType.PROGRAM_FILE_NOT_SUPPORT).toJson();
            }
            if (!ProfileDocCheckTool.checkFileLength(file.getSize())) {
                return Result.fail(MessageType.PROGRAM_FILE_OVER_SIZE).toJson();
            }

            // 调用alphacloud接口
            String url = ProfileEntity.CLOUD_PARSING_HOST + "v4/referral/file-parser";
            Map<String, Object> parameter = new LinkedHashMap<>();
            //parameter.put("file", FILE);
            //parameter.put("file", new FileInputStream(FILE));
            parameter.put("file", file);
            parameter.put("filename", fileName);
            parameter.put("employeeId", employeeId);
            parameter.put(Constant.CONST_APPID,Constant.APPID_PARSING);
            parameter.put(Constant.CONST_INTERFACEID,Constant.INTERFACEID_PARSING);

            try{
                Object jsonObject = HttpClient.postMultipartForm(url, parameter, fileName, Object.class);
                if ( jsonObject == null){
                    throw ProfileException.PROFILE_PARSING_ERROR;
                }
                return com.moseeker.servicemanager.web.controller.Result.success(jsonObject).toJson();
            }catch (CommonException e){
                logger.error("调用简历解析服务错误",e);
                return com.moseeker.servicemanager.web.controller.Result.fail(e.getMessage(),e.getCode()).toJson();
            }
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
        logger.info("ReferralController referralProfile");
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("手机号", referralForm.getMobile());
        validateUtil.addRegExpressValidate("手机号", referralForm.getMobile(), FormCheck.getMobileExp());
        validateUtil.addRequiredValidate("姓名", referralForm.getName());
        validateUtil.addRequiredValidate("推荐关系", referralForm.getRelationship());
        validateUtil.addIntTypeValidate("员工", id, 1, null);
        validateUtil.addIntTypeValidate("appid", referralForm.getAppid(), 0, null);
        validateUtil.addIntTypeValidate("推荐类型", referralForm.getReferralType(), 1, 4);
        String result = validateUtil.validate();
        if(com.moseeker.common.util.StringUtils.isEmptyList(referralForm.getReferralReasons()) && com.moseeker.common.util.StringUtils.isNullOrEmpty(referralForm.getRecomReasonText())){
            result =result+ "推荐理由标签和文本必填任一一个；";
        }
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
     * 员工推荐简历(多职位)
     * @param referralForm 推荐表单
     * @return 推荐结果
     * @throws Exception
     */
    @RequestMapping(value = "/v1/employee/referrals", method = RequestMethod.POST)
    @ResponseBody
    public String referralProfiles(@RequestBody ReferralsForm referralForm) throws Exception {

        logger.info("ReferralController referralProfile");
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("手机号", referralForm.getMobile());
        validateUtil.addRegExpressValidate("手机号", referralForm.getMobile(), FormCheck.getMobileExp());
        validateUtil.addRequiredValidate("姓名", referralForm.getRealname());
        validateUtil.addRequiredValidate("推荐关系", referralForm.getRelation());
        validateUtil.addIntTypeValidate("员工", referralForm.getEmployeeId(), 1, null);
        validateUtil.addIntTypeValidate("appid", referralForm.getAppid(), 0, null);
        validateUtil.addIntTypeValidate("推荐类型", referralForm.getReferralType(), 1, 4);
        String result = validateUtil.validate();
        if(com.moseeker.common.util.StringUtils.isEmptyList(referralForm.getRecomTags()) &&
                com.moseeker.common.util.StringUtils.isNullOrEmpty(referralForm.getRecomText())){
            result =result+ "推荐理由标签和文本必填任一一个；";
        }
        if (org.apache.commons.lang.StringUtils.isBlank(result)) {

            List<MobotReferralResult> results = profileService.employeeReferralProfiles(referralForm.getEmployeeId(),
                    referralForm.getRealname(),referralForm.getMobile(), referralForm.getRecomTags(),
                        referralForm.getPids(),(byte)referralForm.getRelation(),
                            referralForm.getRecomText(),(byte) referralForm.getReferralType());
            return Result.success(results).toJson();
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
        logger.info("ReferralController postCandidateInfo");
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredStringValidate("姓名", form.getName());
        validateUtil.addRequiredStringValidate("手机号码", form.getMobile());
        validateUtil.addIntTypeValidate("职位信息", form.getPosition(), 1, null);
        validateUtil.addIntTypeValidate("appid", form.getAppid(), 0, null);
        String result = validateUtil.validate();
        if(com.moseeker.common.util.StringUtils.isEmptyList(form.getReferralReasons()) && com.moseeker.common.util.StringUtils.isNullOrEmpty(form.getRecomReasonText())){
            result =result+ "推荐理由标签和文本必填任一一个；";
        }
        if (StringUtils.isBlank(result)) {
            logger.info("postCandidateInfo gender:{}",form.getGender());
            com.moseeker.thrift.gen.profile.struct.CandidateInfo candidateInfoStruct = new com.moseeker.thrift.gen.profile.struct.CandidateInfo();
            BeanUtils.copyProperties(form, candidateInfoStruct);
            candidateInfoStruct.setPosition(form.getPosition());
            candidateInfoStruct.setReasons(form.getReferralReasons());
            logger.info("postCandidateInfo candidateInfoStruct gender:{}",candidateInfoStruct.getGender());
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

    /*
    * @param rkey:推荐id字符串
    * @return 认领信息
    * @throws Exception
    * */
    @RequestMapping(value = "/v1.3/referral/confirm",method = RequestMethod.GET)
    @ResponseBody
    public String getReferralsCard(@RequestParam String rkey,HttpServletRequest request) throws Exception{
        String appid = request.getParameter("appid");
        if (StringUtils.isBlank(appid)) {
            return Result.fail(MessageType.APPID_NOT_EXIST).toJson();
        }
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredStringValidate("rkey", rkey);
        String validateResult = validateUtil.validate();
        if (StringUtils.isBlank(validateResult)) {
            List<Integer> idList = null;
            try{
                String[] ids = rkey.split("@");
                idList =  Arrays.stream(ids).filter(e->com.moseeker.common.util.StringUtils.isNotNullOrEmpty(e))
                        .map(e->Integer.valueOf(e.trim()))
                        .collect(Collectors.toList());
            }catch(NumberFormatException e){
                logger.error(e.getMessage(),e);
                return Result.excetionToResult(e).toJson();
            }
            com.moseeker.thrift.gen.employee.struct.ReferralsCard referralsCard = employeeService.getReferralsCard(idList);
            ReferralsCardVO vo = new ReferralsCardVO();
            BeanUtils.copyProperties(referralsCard,vo);
            return Result.success(vo).toJsonStr();
        }
        return Result.validateFailed(validateResult).toJson();
    }

    /*
    * 多职位认领接口
    * */
    @RequestMapping(value = "/v1.3/referral/confirm", method = RequestMethod.POST)
    @ResponseBody
    public String claimReferralsCard(@RequestBody ClaimsForm claimForm) throws Exception {

        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("appid", claimForm.getAppid(), 0, null);
        validateUtil.addIntTypeValidate("用户", claimForm.getUserId(), 1, null);
        validateUtil.addRequiredStringValidate("用户姓名", claimForm.getName());
        validateUtil.addRequiredStringValidate("rkey", claimForm.getRkey());
        validateUtil.addRequiredStringValidate("用户手机", claimForm.getRkey());
        String validateResult = validateUtil.validate();

        if (StringUtils.isBlank(validateResult)) {
            List<Integer> idList = null;
            try{
                String[] ids = claimForm.getRkey().split("@");
                idList =  Arrays.stream(ids).filter(e->com.moseeker.common.util.StringUtils.isNotNullOrEmpty(e))
                        .map(e->Integer.valueOf(e.trim()))
                        .collect(Collectors.toList());
            }catch(NumberFormatException e){
                logger.error(e.getMessage(),e);
                return Result.excetionToResult(e).toJson();
            }
            String claimResults = userService.batchClaimReferralCard(
                    claimForm.getUserId(), claimForm.getName(), claimForm.getMobile(), Constant.NO_VSCODE_CHECK, idList);

            return Result.success(claimResults).toJsonStr();
        } else {
            return Result.validateFailed(validateResult).toJson();
        }
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
            logger.info("ReferralProfileTab tab :{}",JSON.toJSONString(result));
            logger.info("ReferralProfileTab tab :{}",JSON.toJSONString(tabList));
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
                logger.info("idReasons:{}", JSON.toJSONString(idReasons));
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
            return com.moseeker.servicemanager.web.controller.Result.validateFailed(result).toJson();
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
            logger.info("claimResults:{}", claimResults);
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
        validateUtil.addRequiredValidate("公司编号", form.getCompanyId());
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(result)) {
            referralService.addUserSeekRecommend(form.getCompanyId(), form.getUserId(), form.getPostUserId(),
                    form.getPositionId(), form.getOrigin());
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
        validateUtil.addRequiredValidate("员工user编号", form.getPostUserId());
        validateUtil.addRequiredValidate("内推编号", form.getReferralId());
        validateUtil.addRequiredValidate("公司编号", form.getCompanyId());
        validateUtil.addRequiredValidate("推荐人与被推荐人关系", form.getRelationship());
        validateUtil.addStringLengthValidate("推荐理由文本", form.getRecomReasonText(), "推荐理由文本长度过长",
                "推荐理由文本长度过长",null, 201);
        validateUtil.addSensitiveValidate("推荐理由文本", form.getRecomReasonText(), null, null);
        if (form.getReferralReasons() != null) {
            String reasons = form.getReferralReasons().stream().collect(Collectors.joining(","));
            validateUtil.addStringLengthValidate("推荐理由", reasons, null, 512);
        }
        String result = validateUtil.validate();
        if(com.moseeker.common.util.StringUtils.isEmptyList(form.getReferralReasons()) && com.moseeker.common.util.StringUtils.isNullOrEmpty(form.getRecomReasonText())){
            result =result+ "推荐理由标签和文本必填任一一个；";
        }
        if (org.apache.commons.lang.StringUtils.isBlank(result)) {
            referralService.employeeReferralReason(form.getCompanyId(), form.getPostUserId(),form.getPositionId(), form.getReferralId(), form.getReferralReasons(),
                    form.getRelationship(), form.getRecomReasonText());
            return Result.success().toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }


    @RequestMapping(value = "/v1/employee/application/evaluate", method = RequestMethod.POST)
    @ResponseBody
    public String employeeReferralApplicationEvaluate(@RequestBody ReferralEvaluateForm form) throws Exception {
        logger.info("ReferralController employeeReferralApplicationEvaluate");
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("appid", form
                .getAppid());
        validateUtil.addRequiredValidate("职位编号", form.getPositionId());
        validateUtil.addRequiredValidate("员工user编号", form.getPostUserId());
        validateUtil.addRequiredValidate("候选人编号", form.getPresenteeUserId());
        validateUtil.addRequiredValidate("公司编号", form.getCompanyId());
        validateUtil.addRequiredValidate("推荐人与被推荐人关系", form.getRelationship());
        validateUtil.addStringLengthValidate("推荐理由文本", form.getRecomReasonText(), "推荐理由文本长度过长",
                "推荐理由文本长度过长",null, 201);
        validateUtil.addSensitiveValidate("推荐理由文本", form.getRecomReasonText(), null, null);

        List<String> reasons1 = new ArrayList<>();
        if (form.getReferralReasons() != null && form.getReferralReasons().size() > 0) {
            reasons1.addAll(form.getReferralReasons());
        }
        if (StringUtils.isNotBlank(form.getRecomReasonText())) {
            reasons1.add(form.getRecomReasonText());
        }
        validateUtil.addRequiredOneValidate("推荐理由", reasons1);
        logger.info("ReferralController employeeReferralApplicationEvaluate reasons1:{}", JSONObject.toJSONString(reasons1));
        if (form.getReferralReasons() != null) {
            String reasons = form.getReferralReasons().stream().collect(Collectors.joining(","));
            validateUtil.addStringLengthValidate("推荐理由", reasons, null, 512);
        }
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(result)) {
            referralService.employeeReferralRecomEvaluation(form.getCompanyId(),form.getPostUserId(),form.getPositionId(), form.getPresenteeUserId(), form.getReferralReasons(),
                    form.getRelationship(), form.getRecomReasonText());
            return Result.success().toJson();
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
                                           @RequestParam(value = "company_id") int companyId,
                                           @RequestParam(value = "post_user_id") int postUserId) throws Exception {

        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredValidate("员工user编号", postUserId);
        validateUtil.addRequiredValidate("内推编号", referralId);
        validateUtil.addRequiredValidate("公司编号", companyId);
        String message = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(message)) {
            com.moseeker.thrift.gen.referral.struct.ContactPushInfo redult = referralService.fetchSeekRecommend(companyId, referralId, postUserId);
            com.moseeker.servicemanager.web.controller.referral.vo.ContactPushInfo info =
                    new com.moseeker.servicemanager.web.controller.referral.vo.ContactPushInfo();
            BeanUtils.copyProperties(redult, info);
            return Result.success(info).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(message).toJson();
        }
    }


}
