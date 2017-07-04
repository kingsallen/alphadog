package com.moseeker.servicemanager.web.controller.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.thrift.gen.application.service.JobApplicationServices;
import com.moseeker.thrift.gen.application.struct.ApplicationResponse;
import com.moseeker.thrift.gen.profile.service.ProfileServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.profile.form.OutPutResumeForm;
import com.moseeker.servicemanager.web.controller.profile.form.OutPutResumeUtil;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.servicemanager.web.controller.util.ProfileParamUtil;
import com.moseeker.thrift.gen.apps.profilebs.service.ProfileBS;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices;

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
                    result = profileService.getResource(userId, profileId, uuid);
                    logger.info("current:" + i);
                    logger.info("data:" + JSON.parse(result.getData()));
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

            int companyId = form.getInt("company_id", -1);
            int sourceId = form.getInt("source_id", -1);
            int atsStatus = form.getInt("ats_status", 1);
            boolean recommender = form.getBoolean("recommender", false);
            boolean dlUrlRequired = form.getBoolean("dl_url_required", false);
            Map<String, List<String>> filter = (Map<String, List<String>>) form.get("filter");
            if (companyId == -1) {
                return ResponseLogNotification.fail(request, "company_id不能为空");
            } else if (sourceId == -1) {
                return ResponseLogNotification.fail(request, "sourceId不能为空");
            }
            logger.info("profilesByApplication:companyId:{},sourceId:{},atsStatus:{},recommender:{},dlUrlRequired:{}", companyId, sourceId, atsStatus, recommender, dlUrlRequired);
            Response result = service.getProfileByApplication(companyId, sourceId, atsStatus, recommender, dlUrlRequired, filter);

            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }
}
