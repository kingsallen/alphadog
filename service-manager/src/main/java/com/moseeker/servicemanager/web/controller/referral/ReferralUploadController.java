package com.moseeker.servicemanager.web.controller.referral;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.commonservice.utils.ProfileDocCheckTool;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.web.controller.MessageType;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.profile.service.ProfileServices;
import com.moseeker.thrift.gen.referral.service.ReferralService;
import com.moseeker.thrift.gen.referral.struct.ReferralUploadFiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.ByteBuffer;
import java.util.List;

import static com.moseeker.servicemanager.common.ParamUtils.parseRequestParam;

@Controller
@CounterIface
public class ReferralUploadController {

    private ReferralService.Iface referralService = ServiceManager.SERVICEMANAGER.getService(ReferralService.Iface.class);
    private ProfileServices.Iface profileService = ServiceManager.SERVICEMANAGER.getService(ProfileServices.Iface.class);

    /**
     *上传文件存储
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/v1.2/referral/resume",method = RequestMethod.POST)
    @ResponseBody
    public String weChatUploadProfile(MultipartFile file, HttpServletRequest request){
        Params<String, Object> params = null;
        String result = new String();
        try {
            params = ParamUtils.parseequestParameter(request);
            String sceneId = (String) params.get("sceneId");
            String unionId = (String) params.get("unionId");
            String fileName = (String) params.get("fileName");
            if (!ProfileDocCheckTool.checkFileName(params.getString("file_name"))) {
                return Result.fail(MessageType.PROGRAM_FILE_NOT_SUPPORT).toJson();
            }
            if (!ProfileDocCheckTool.checkFileLength(file.getSize())) {
                return Result.fail(MessageType.PROGRAM_FILE_OVER_SIZE).toJson();
            }
            ByteBuffer byteBuffer = ByteBuffer.wrap(file.getBytes());
            profileService.uploadFiles(sceneId,unionId,fileName,byteBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 分页查询上传文件列表
     */
    Logger logger = LoggerFactory.getLogger(ReferralUploadController.class);
    @RequestMapping(value = "/v1.2/show/list/resumeFiles",method = RequestMethod.GET)
    @ResponseBody
    public String weChatUploadProfile(HttpServletRequest request){
        String unionid = new String();
        Integer pageSize = 10;
        Integer pageNo = 1;
        String result = new String();
        try {
            Params<String, Object> params = parseRequestParam(request);
            unionid = (String) params.get("unionid");
            pageSize = (Integer) params.get("pageSize");
            pageNo = (Integer) params.get("pageNo");
            logger.info("分页查询上传文件列表参数：unionId"+unionid+"pageSize"+pageSize+"pageNo"+pageNo);
            List<ReferralUploadFiles> uploadFilesResultList = profileService.getUploadFiles(unionid, pageNo, pageSize);
            //传给app-mini
            result = JSON.toJSONString(uploadFilesResultList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 下载文件
     * @param request
     * @return 上传文件保存地址
     */
    @RequestMapping(value = "/v1.2/download",method = RequestMethod.GET)
    public String downloadResume(HttpServletRequest request){
        Params<String, Object> params = null;
        String url = new String();
        try {
            params = parseRequestParam(request);
            String sceneId = (String) params.get("sceneId");
            url = profileService.downLoadFiles(sceneId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 指定文件做推荐
     * @return 指定结果
     * @throws Exception
     */
    @RequestMapping(value = "/v1.2/referral/upload/resume/info",method = RequestMethod.GET)
    public String parseFileProfile(HttpServletRequest request) throws Exception {
        Params<String, Object> params = parseRequestParam(request);
        String sceneId = (String) params.get("sceneId");
        Integer userId = (Integer) params.get("userId");
        ReferralUploadFiles uploadFilesResult = profileService.referralResumeInfo(sceneId);
        com.moseeker.thrift.gen.profile.struct.ProfileParseResult result =
                profileService.parseFileProfileByFilePath(uploadFilesResult.getUrl(), Integer.valueOf(userId));
        return Result.success(result).toJson();
    }

    /**
     * 轮询是否已经指定推荐的简历
     * @return true 已经指定号推荐的简历；false位置定好推荐的简历
     * @throws Exception
     */
    @RequestMapping(value = "/v1.2/resuem/upload/complete",method = RequestMethod.GET)
    public String getSpecifyProfileResult(HttpServletRequest request) throws Exception {
        Params<String, Object> params = parseRequestParam(request);
        int employeeId = params.getInt("employeeId");
        boolean flag = profileService.getSpecifyProfileResult(employeeId);
        return Result.success(flag).toJson();
    }
}
