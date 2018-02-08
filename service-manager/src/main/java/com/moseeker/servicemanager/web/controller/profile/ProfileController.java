package com.moseeker.servicemanager.web.controller.profile;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.profile.form.OutPutResumeForm;
import com.moseeker.servicemanager.web.controller.profile.form.OutPutResumeUtil;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.servicemanager.web.controller.util.ProfileParamUtil;
import com.moseeker.thrift.gen.application.service.JobApplicationServices;
import com.moseeker.thrift.gen.application.struct.ApplicationResponse;
import com.moseeker.thrift.gen.apps.profilebs.service.ProfileBS;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.ProfileOtherThriftService;
import com.moseeker.thrift.gen.profile.service.ProfileServices;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices;
import com.moseeker.thrift.gen.profile.struct.ProfileApplicationForm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@CounterIface
public class ProfileController {

    Logger logger = LoggerFactory.getLogger(ProfileController.class);

    WholeProfileServices.Iface profileService = ServiceManager.SERVICEMANAGER
            .getService(WholeProfileServices.Iface.class);
    ProfileServices.Iface service = ServiceManager.SERVICEMANAGER
            .getService(ProfileServices.Iface.class);
    OutPutResumeUtil outPutResumeService = new OutPutResumeUtil();

    ProfileBS.Iface profileBSService = ServiceManager.SERVICEMANAGER.getService(ProfileBS.Iface.class);

    ProfileOtherThriftService.Iface profileOtherService = ServiceManager.SERVICEMANAGER.getService(ProfileOtherThriftService.Iface.class);

    JobApplicationServices.Iface jobApplicationServices = ServiceManager.SERVICEMANAGER.getService(JobApplicationServices.Iface.class);

    @RequestMapping(value = "/profile/pdf", method = RequestMethod.GET)
    @ResponseBody
    public String outPutResume(HttpServletRequest request, HttpServletResponse response) {
        // PrintWriter writer = null;
        try {
            // GET方法 通用参数解析并赋值
            OutPutResumeForm form = ParamUtils.initModelForm(request, OutPutResumeForm.class);
            Response result = outPutResumeService.outPutResume(form);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ResponseBody
    public String get(HttpServletRequest request, HttpServletResponse response) {
        // PrintWriter writer = null;
        try {
            // GET方法 通用参数解析并赋值
            ImportCVForm form = ParamUtils.initModelForm(request, ImportCVForm.class);
            Response result = profileService.getResource(form.getUser_id(), form.getId(), form.getUuid());

            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    @ResponseBody
    public String post(HttpServletRequest request, HttpServletResponse response) {
        // PrintWriter writer = null;
        try {

            ImportCVForm form = ParamUtils.initModelForm(request, ImportCVForm.class);
            Response result = profileService.postResource(form.getProfile(), form.getUser_id());
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    /*
     * 批量profile接口
     */
    @RequestMapping(value = "/profiles", method = RequestMethod.POST)
    @ResponseBody
    public String getBatchProfiles(HttpServletRequest request, HttpServletResponse response) {
        logger.info("----------getBatchProfiles-----------");
        // PrintWriter writer = null;
        try {
            // GET方法 通用参数解析并赋值
            Map<String, Object> param = ParamUtils.parseRequestParam(request);

            List<String> userIds = ProfileParamUtil.getProfilesUserIds(param);
            List<String> uuids = ProfileParamUtil.getProfilesUUIDs(param);
            List<String> profileIds = ProfileParamUtil.getProfilesIds(param);
            logger.info("-----------------------------------");
            if ((userIds != null && userIds.size() > 0) || (uuids != null && uuids.size() > 0)
                    || (profileIds != null && profileIds.size() > 0)) {
                int count = 0;
                if (userIds != null) {
                    count = userIds.size();
                    logger.info("userIds:" + JSON.toJSONString(userIds));
                }
                if (uuids != null) {
                    count = Math.max(count, uuids.size());
                    logger.info("uuids:" + JSON.toJSONString(uuids));
                }
                if (profileIds != null) {
                    count = Math.max(count, profileIds.size());
                    logger.info("profileIds:" + JSON.toJSONString(profileIds));
                }
                if (count > 5000) {
                    return ResponseLogNotification.fail(request, "profile数量过大，拒绝查询！");
                }
                List<Object> profileData = new ArrayList<>();
                logger.info("count:" + count);


                Response result = null;
                for (int i = 0; i < count; i++) {
                    int userId = 0;
                    int profileId = 0;
                    String uuid = null;
                    if (userIds != null && userIds.size() - 1 >= i) {
                        userId = BeanUtils.converToInteger(userIds.get(i));
                    }
                    if (profileIds != null && profileIds.size() - 1 >= i) {
                        try {
                            profileId = BeanUtils.converToInteger(profileIds.get(i));
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                    if (uuids != null && uuids.size() - 1 >= i) {
                        uuid = BeanUtils.converToString(uuids.get(i));
                    }
                    try {
                        result = profileService.getResource(userId, profileId, uuid);
                        logger.info("data:" + JSON.parse(result.getData()));
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                    logger.info("current:" + i);
                    if (result != null && result.getStatus() == 0) {
                        profileData.add(JSON.parse(result.getData()));
                    }
                }
                Response res = ResponseUtils.success(profileData);
                return ResponseLogNotification.success(request, res);
            }
            return ResponseLogNotification.fail(request, "参数错误");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    @RequestMapping(value = "/profile/retrieve", method = RequestMethod.POST)
    @ResponseBody
    public String retrieveProfile(HttpServletRequest request, HttpServletResponse response) {
        // PrintWriter writer = null;
        try {

            Params<String, Object> form = ParamUtils.parseRequestParam(request);
            Response result = profileBSService.retrieveProfile(
                    form.getInt("position_id"),
                    form.getInt("channel"),
                    JSON.toJSONString(form.get("profile")));

            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    @RequestMapping(value = "/profile/process", method = RequestMethod.POST)
    @ResponseBody
    public String profileProcess(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> form = ParamUtils.parseRequestParam(request);
            Integer companyId = form.getInt("company_id");
            Integer progress_status = form.getInt("progress_status");
            List<Integer> appIds = (ArrayList<Integer>) form.get("aids");
            Integer accountId = form.getInt("account_id");
            logger.info("profileProcess companyId:{}, progress_status:{}  appIds:{}, accountId:{}", companyId, progress_status, appIds, accountId);
            if (progress_status == null || appIds == null || appIds.isEmpty()) {
                logger.info("profileProcess param illegal");
                return ResponseLogNotification.success(request, ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY));
            }
            Response result = profileBSService.profileProcess(companyId, progress_status, appIds, accountId);
            logger.info("profileProcess result:{}", request);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    /**
     * 更新申请，不需要设置company_id
     */
    @RequestMapping(value = "/profile/process", method = RequestMethod.PUT)
    @ResponseBody
    public String putJobApplication(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> form = ParamUtils.parseRequestParam(request);
            Integer applicationId = form.getInt("id");
            Integer progress_status = form.getInt("status");
            List<Integer> appIds = new ArrayList<>();
            appIds.add(applicationId);
            ApplicationResponse applicationResponse = jobApplicationServices.getAccountIdAndCompanyId(applicationId);
            if (applicationResponse != null) {
                Response result = profileBSService.profileProcess(applicationResponse.getCompany_id(), progress_status, appIds, applicationResponse.getAccount_id());
                return ResponseLogNotification.success(request, result);
            } else {
                return ResponseLogNotification.fail(request, "职位不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    @RequestMapping(value = "/profile/processats", method = RequestMethod.POST)
    @ResponseBody
    public String profileProcessAts(HttpServletRequest request, HttpServletResponse response) {
        try {

            Params<String, Object> form = ParamUtils.parseRequestParam(request);
            Integer progress_status = form.getInt("progress_status");
            String appIds = form.getString("aids");
            if (progress_status == null || StringUtils.isNullOrEmpty(appIds)) {
                return ResponseLogNotification.success(request, ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY));
            }
            Response result = profileBSService.profileProcessAts(progress_status, appIds);

            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    /**
     * 批量获取简历
     */
    @RequestMapping(value = "/profiles/application", method = RequestMethod.POST)
    @ResponseBody
    public String profilesByApplication(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> form = ParamUtils.parseRequestParam(request);
            ProfileApplicationForm profileApplicationForm = ParamUtils.initModelForm(form, ProfileApplicationForm.class);
            if (profileApplicationForm == null) {
                return ResponseLogNotification.fail(request, "参数不能为空");
            }
            if (!profileApplicationForm.isSetCompany_id()) {
                return ResponseLogNotification.fail(request, "company_id不能为空");
            } else if (!profileApplicationForm.isSetSource_id()) {
                return ResponseLogNotification.fail(request, "sourceId不能为空");
            }
            Map<String, String> conditions = new HashMap<>();
            for (String key : form.keySet()) {
                conditions.put(key, form.getString(key));
            }

            profileApplicationForm.setConditions(conditions);

            logger.info("/profiles/application params:{}", JSON.toJSONString(profileApplicationForm));
            Response result = service.getProfileByApplication(profileApplicationForm);

            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    /**
     * 回收简历 用于替换retrieveProfile
     */
    @RequestMapping(value = "/retrieval/profile", method = RequestMethod.POST)
    @ResponseBody
    public String retrieveProfileNew(HttpServletRequest request, HttpServletResponse response) {
        try {

            String parameter = ParamUtils.parseJsonParam(request);
            boolean result = profileService.retrieveProfile(parameter);
            return ResponseLogNotification.success(request, new Response(0, String.valueOf(result)));
        } catch (BIZException e) {
            Response result = new Response();
            result.setStatus(e.getCode());
            result.setMessage(e.getMessage());
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    /**
     * 回收简历 用于替换retrieveProfile
     */
    @RequestMapping(value = "/profile/parser", method = RequestMethod.POST)
    @ResponseBody
    public String profileParser(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseequestParameter(request);
            Integer uid = params.getInt("uid");
            if (file != null) {
                String data = new String(Base64.encodeBase64(file.getBytes()), Consts.UTF_8);
                Response res = service.resumeProfile(uid, file.getOriginalFilename(), data);
                return ResponseLogNotification.success(request, res);
            } else {
                return null;
            }
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 给用户添加或者更新profile
     * @param request
     * @return
     */
    @RequestMapping(value = "v2/profile", method = RequestMethod.POST)
    @ResponseBody
    public String upsertProfile(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int userId = 0;
            if (params.get("userId") != null) {
                if (params.get("userId") instanceof String) {
                    userId = Integer.parseInt((String) params.get("userId"));
                } else if (params.get("userId") instanceof Integer) {
                    userId = (Integer) params.get("userId");
                }
            }
            String profile = null;
            if (params.get("profile") != null) {
                profile = (String) params.get("profile");
            }
            int profileId = service.upsertProfile(userId, profile);
            return ResponseLogNotification.successJson(request, profileId);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/profile/check/other", method = RequestMethod.POST)
    @ResponseBody
    public String checkOther(HttpServletRequest request) {
        try {
            Params<String, Object> form = ParamUtils.parseRequestParam(request);
            int userId = form.getInt("userId", 0);
            int positionId = form.getInt("positionId", 0);
            return ResponseLogNotification.success(request, profileOtherService.checkProfileOther(userId, positionId));
        } catch (BIZException e) {
            Response result = new Response();
            result.setStatus(e.getCode());
            result.setMessage(e.getMessage());
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    @RequestMapping(value = "/profile/custom/other", method = RequestMethod.POST)
    @ResponseBody
    public String getOther(HttpServletRequest request) {
        try {
            Params<String, Object> form = ParamUtils.parseRequestParam(request);
            String params = JSONArray.toJSONString(form.get("params"));
            JSONArray paramsJson = JSONArray.parseArray(params);
            if (!params.isEmpty() && (paramsJson.getJSONObject(0).containsKey("positionId") && paramsJson.getJSONObject(0).containsKey("profileId"))) {
                return ResponseLogNotification.success(request, profileOtherService.getProfileOther(params));
            } else {
                return ResponseLogNotification.fail(request, ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST));
            }
        } catch (BIZException e) {
            Response result = new Response();
            result.setStatus(e.getCode());
            result.setMessage(e.getMessage());
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    @RequestMapping(value = "/profile/other/fields/check", method = RequestMethod.GET)
    @ResponseBody
    public String otherFieldsCheck(HttpServletRequest request) {
        try {
            Params<String, Object> form = ParamUtils.parseRequestParam(request);
            int profileId = form.getInt("profileId", 0);
            String fields = form.getString("fields");
            if (profileId != 0 && StringUtils.isNotNullOrEmpty(fields)) {
                return ResponseLogNotification.success(request, profileOtherService.otherFieldsCheck(profileId, fields));
            } else {
                return ResponseLogNotification.fail(request, ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST));
            }
        } catch (BIZException e) {
            Response result = new Response();
            result.setStatus(e.getCode());
            result.setMessage(e.getMessage());
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 人才库简历上传解析，也可以用在其他位置
     */
    @RequestMapping(value = "/profile/upload/parser", method = RequestMethod.POST)
    @ResponseBody
    public String talentUploadParser(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> params = ParamUtils.parseequestParameter(request);
            String  companyId=(String)params.get("company_id");
            String data = new String(Base64.encodeBase64(file.getBytes()), Consts.UTF_8);
            Response res = service.resumeTalentProfile( file.getOriginalFilename(), data,Integer.parseInt(companyId));
            return ResponseLogNotification.success(request, res);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/api/profile/upload/combine", method = RequestMethod.POST)
    @ResponseBody
    public String profileCombine(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> params = ParamUtils.parseRequestParam(request);
            Map<String,Object> profile= (Map<String, Object>) params.get("profile");
            int companyId=(int)params.get("company_id");
            Response res = profileService.combinationProfile(JSON.toJSONString(profile),companyId);
            return ResponseLogNotification.success(request, res);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/api/profile/talent/preserve", method = RequestMethod.POST)
    @ResponseBody
    public String saveProfile(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> params = ParamUtils.parseRequestParam(request);
            Map<String,Object> profile= (Map<String, Object>) params.get("profile");
            int hrId=(int)params.get("hr_id");
            int userId=(int)params.get("user_id");
            int companyId=(int)params.get("company_id");
            String fileName=(String)params.get("file_name");
            Response res = profileService.preserveProfile(JSON.toJSONString(profile),hrId,companyId,fileName,userId);
            return ResponseLogNotification.success(request, res);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/api/profile/upload/validate", method = RequestMethod.GET)
    @ResponseBody
    public String ValidateUploadHr(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> params = ParamUtils.parseRequestParam(request);
            String hrId=(String)params.get("hr_id");
            String userId=(String)params.get("user_id");
            String companyId=(String)params.get("company_id");
            if("0".equals(hrId)||StringUtils.isNullOrEmpty(hrId)){
                return ResponseLogNotification.fail(request, "hr_id不能为0或者为空");
            }
            if("0".equals(userId)||StringUtils.isNullOrEmpty(userId)){
                return ResponseLogNotification.fail(request, "userId不能为0或者为空");
            }
            if("0".equals(companyId)||StringUtils.isNullOrEmpty(companyId)){
                return ResponseLogNotification.fail(request, "companyId不能为0或者为空");
            }
            Response res = profileService.validateHrAndUploaduser(Integer.parseInt(hrId),Integer.parseInt(companyId),Integer.parseInt(userId));
            return ResponseLogNotification.success(request, res);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    @RequestMapping(value = "/profile/upload", method = RequestMethod.GET)
    @ResponseBody
    public String getUploadprofile(HttpServletRequest request, HttpServletResponse response) {
        // PrintWriter writer = null;
        try {
            // GET方法 通用参数解析并赋值
            Map<String, Object> params = ParamUtils.parseRequestParam(request);
            Response result = profileService.getUploadProfile(Integer.parseInt(String.valueOf(params.get("user_id"))));

            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

}
