package com.moseeker.servicemanager.web.controller.profile;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
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

import com.taobao.api.internal.util.json.JSONErrorListener;
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
     * 回收简历 用于替换retrieveProfile
     */
    @RequestMapping(value = "/profile/email/parser", method = RequestMethod.POST)
    @ResponseBody
    public String emailProfileParser(@RequestParam(value = "file", required = false) MultipartFile file,
                                     @RequestParam int position_id, @RequestParam int channel,
                                     @RequestParam int appid,
                                     HttpServletRequest request) throws Exception {
        logger.info("position_id:{}, channel:{}, appid:{}", position_id, channel, appid);
        if (file != null) {
            String data = new String(Base64.encodeBase64(file.getBytes()), Consts.UTF_8);
            Response res = service.parseProfileAttachment(file.getOriginalFilename(), data);
            if (res.getStatus() == 0) {
                JSONObject params = new JSONObject();
                JSONObject profile = JSON.parseObject(res.getData());
                params.put("profile", profile);
                params.put("positionId", position_id);
                params.put("channel", channel);
                params.put("appid", appid);

                boolean result = profileService.retrieveProfile(params.toJSONString());
                return ResponseLogNotification.successJson(request, result);
            }
            return ResponseLogNotification.success(request, res);
        } else {
            return ResponseLogNotification.fail(request, "文件是必填项");
        }
    }

    private static final String data = "{\"basic\":{\"birth\":\"1978-03-04\",\"cityName\":\"上海\",\"gender\":\"2\",\"name\":\"赵静\"},\"credentials\":[],\"educations\":[{\"collegeCode\":0,\"collegeName\":\"美国南加州大学\",\"degree\":6,\"endDate\":\"\",\"endUntilNow\":0,\"isFull\":0,\"isStudyAbroad\":0,\"isUnified\":0,\"majorCode\":0,\"majorName\":\"会计学\",\"startDate\":\"\"},{\"collegeCode\":0,\"collegeName\":\"英国兰开斯特大学\",\"degree\":6,\"endDate\":\"2003-09-01\",\"endUntilNow\":0,\"isFull\":0,\"isStudyAbroad\":0,\"isUnified\":0,\"majorCode\":0,\"majorName\":\"理学\",\"startDate\":\"2002-10-01\"},{\"collegeCode\":0,\"collegeName\":\"南京邮电大学\",\"degree\":5,\"endDate\":\"2000-07-01\",\"endUntilNow\":0,\"isFull\":0,\"isStudyAbroad\":0,\"isUnified\":0,\"majorCode\":0,\"majorName\":\"计算机通信工程\",\"startDate\":\"1996-09-01\"}],\"intentions\":[{\"workstate\":0,\"worktype\":0}],\"languages\":[{\"level\":0,\"name\":\"英语\"},{\"level\":0,\"name\":\"普通话\"}],\"projectexps\":[],\"skills\":[{\"level\":0,\"month\":0,\"name\":\"游戏\"},{\"level\":0,\"month\":0,\"name\":\"市场调研\"},{\"level\":0,\"month\":0,\"name\":\"法律\"},{\"level\":0,\"month\":0,\"name\":\"黑盒测试\"},{\"level\":0,\"month\":0,\"name\":\"powerpoint\"},{\"level\":0,\"month\":0,\"name\":\"客户服务\"},{\"level\":0,\"month\":0,\"name\":\"自动化测试\"},{\"level\":0,\"month\":0,\"name\":\"软件工程\"}],\"user\":{\"email\":\"miraonstar@qq.com\",\"mobile\":\"13817825207\",\"name\":\"赵静\"},\"workexps\":[{\"company\":{\"companyProperty\":0,\"companyScale\":\"1\"},\"description\":\"在ncrc(www.ncrc.org)执行的各种职务。\\n现场技术支持,电子举办的网络活动,电子表格和powerpoint制作作出了贡献。\\n协助国家住房自有可持续发展基金,考虑替代的摄入量系统和流程。\\n无界学习,上海,中国\\n质量保证经理,质量保证部4月 - 2007年7月\\n监督质量保证部,10个移动电话应用和产品,共5个20 +的功能,10m的客户群的初创公司设计,开发和测试。\\n重新设计,实施和优化所有的业务流程,提高效率,精简产品开发和发布程序。\\n招募和训练了5个团队成员,以最大限度地发挥功能的2个月内。\\n微软公司上海,中国\\n项目经理,msn新闻组9月 - 2006年12月\\n监督msn门户网站的30名成员组成的全球支持团队,以确保24小时客户服务和解决问题。\\n招聘,培训和管理团队成员的开发和维护msn的4 +全球网站。\\n最大化客户满意度,缩短响应时间。\\n易趣,上海,中国\",\"endUntilNow\":1,\"job\":\"顾问\",\"startDate\":\"2008-10-01\",\"type\":0},{\"company\":{\"companyName\":\"海安吉安星信息服务有限公司\",\"companyProperty\":0,\"companyScale\":\"1\"},\"description\":\"上海第九城市有限公司,中国\",\"endDate\":\"2012-02-01\",\"endUntilNow\":0,\"job\":\"系统分析员\",\"startDate\":\"2010-09-01\",\"type\":0},{\"company\":{\"companyName\":\"第九城市制作中心\",\"companyProperty\":0,\"companyScale\":\"1\"},\"description\":\"第九城市制作中心及业务发展工作队。\\n支持董事会通过全球网络游戏市场调研,本地/国际法律和法规咨询,产品评估,成本/收入预测全球服务器平台项目的商业计划书。\\n白盒和黑盒测试的内部游戏工作室的设计过程和方法。\\n全国社区再投资联盟华盛顿,dc,usa\",\"endDate\":\"2010-01-01\",\"endUntilNow\":0,\"job\":\"技术经理\",\"startDate\":\"2009-09-01\",\"type\":0},{\"company\":{\"companyName\":\"上海贝尔阿尔卡特上海\",\"companyProperty\":0,\"companyScale\":\"1\"},\"description\":\"开发和测试,易趣全球的32个网站,以扩大其国际市场和增长的收入。\\n合作和沟通40 + ebay的信任与安全和实现具体的时间表和质量保证水平的客户支持团队的成员。\\n率先推出,14个全球分类广告网站客齐集项目,设计并实现自动化测试框架,人员和训练有素的永久发展,维护和优化的所有功能。\",\"endDate\":\"2006-09-01\",\"endUntilNow\":0,\"job\":\"质量保证工程师\",\"startDate\":\"2005-10-01\",\"type\":0},{\"company\":{\"companyName\":\"移动通信集团\",\"companyProperty\":0,\"companyScale\":\"1\"},\"description\":\"开发和测试软件阿尔卡特的mxbsc项目,下一代基站控制器系统。\\n分析,更新,整理,并提出整个项目的250 +国际和当地的团队成员,以方便定义的规格,交流和发展过程中的所有文档。\\n合作与跨职能团队的任务,以激励团队士气和业绩。\\n青岛朗讯青岛,中国\",\"endDate\":\"2005-10-01\",\"endUntilNow\":0,\"job\":\"软件工程师\",\"startDate\":\"2003-11-01\",\"type\":0},{\"company\":{\"companyProperty\":0,\"companyScale\":\"1\"},\"description\":\"合作与全球自动化测试朗讯的5ess2000项目,第5增强型电路开关。\\n5个月的现场在贝尔实验室,朗讯科技,在伊利诺伊州,美国的研究,开发和测试软件合作。\\n其他资料\",\"endDate\":\"2001-08-01\",\"endUntilNow\":0,\"job\":\"软件工程师\",\"startDate\":\"2000-07-01\",\"type\":0}]}";

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
    public String talentUploadParser(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,
                                     HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId=params.getInt("company_id");
            String data = new String(Base64.encodeBase64(file.getBytes()), Consts.UTF_8);;
            Response res = service.resumeTalentProfile( file.getOriginalFilename(), data,companyId);
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
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String profile=params.getString("profile");
            int companyId=params.getInt("company_id");
            Response res = profileService.combinationProfile(profile,companyId);
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
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String profile=params.getString("profile");
            int hrId=params.getInt("hr_id");
            int userId=params.getInt("user_id");
            int companyId=params.getInt("company_id");
            String fileName=params.getString("file_name");
            Response res = profileService.preserveProfile(profile,hrId,companyId,fileName,userId);
            return ResponseLogNotification.success(request, res);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


}
