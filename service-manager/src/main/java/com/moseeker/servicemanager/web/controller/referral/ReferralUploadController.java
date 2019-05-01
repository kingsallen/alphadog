package com.moseeker.servicemanager.web.controller.referral;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.commonservice.utils.ProfileDocCheckTool;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.web.controller.MessageType;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.servicemanager.web.controller.referral.vo.UploadControllerVO;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.profile.service.ProfileServices;
import com.moseeker.thrift.gen.profile.struct.ReferralUploadFiles;
import com.moseeker.thrift.gen.referral.service.ReferralService;
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
@ResponseBody
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
    public UploadControllerVO uploadProfile(MultipartFile file, HttpServletRequest request){
        logger.info("ReferralUploadController weChatUploadProfile");
        logger.info("ReferralUploadController weChatUploadProfile file.length:{},  file.name:{}",file.getSize(), file.getName());
        Params<String, Object> params = null;
        UploadControllerVO result = new UploadControllerVO();
        try {
            //params = ParamUtils.parseequestParameter(request);
            String sceneId =  request.getParameter("sceneId");
            String unionId =  request.getParameter("unionId");
            String fileName = request.getParameter("fileName");
            logger.info("上传文件存储参数： sceneId:{}, unionId:{}, fileName:{}",sceneId, unionId, fileName);
            /*if (!ProfileDocCheckTool.checkFileName(fileName)) {
                result.setFileName(Result.fail(MessageType.PR

                OGRAM_FILE_NOT_SUPPORT).toJson());
                return result;
            }*/
            if (!ProfileDocCheckTool.checkFileLength(file.getSize())) {
                result.setFileName(Result.fail(MessageType.PROGRAM_FILE_OVER_SIZE).toJson());
                logger.info("uploadProfile checkFileLength  PROGRAM_FILE_OVER_SIZE");
                return result;
            }
            ByteBuffer byteBuffer = ByteBuffer.wrap(file.getBytes());
            logger.info("uploadProfile byteBuffer{}",byteBuffer);
            ReferralUploadFiles referralUploadFiles =  profileService.uploadFiles(sceneId,unionId,fileName,byteBuffer);
            logger.info("uploadProfile： referralUploadFiles:{}", JSONObject.toJSONString(referralUploadFiles));
            result.setName(referralUploadFiles.getFilename());
            result.setSaveUrl(referralUploadFiles.getUrl());
            result.setFileID(referralUploadFiles.getFileId());
            result.setCreateTime(referralUploadFiles.getCreate_time());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 分页查询上传文件列表
     */
    Logger logger = LoggerFactory.getLogger(ReferralUploadController.class);
    @RequestMapping(value = "/v1.2/show/list/resumeFiles",method = RequestMethod.GET)
    public String weChatUploadProfile(HttpServletRequest request){
        String unionid = new String();
        Integer pageSize = 10;
        Integer pageNo = 1;
        String result = new String();
        try {
            unionid = request.getParameter("unionid");
            pageSize = Integer.valueOf(request.getParameter("pageSize"));
            pageNo = Integer.valueOf(request.getParameter("pageNo"));
            /*Params<String, Object> params = parseRequestParam(request);
            unionid = (String) params.get("unionid");
            pageSize = (Integer) params.get("pageSize");
            pageNo = (Integer) params.get("pageNo");*/
            logger.info("分页查询上传文件列表参数：unionId"+unionid+"pageSize"+pageSize+"pageNo"+pageNo);
            List<ReferralUploadFiles> uploadFilesResultList = profileService.getUploadFiles(unionid, pageNo, pageSize);
            //传给app-mini
            result = JSON.toJSONString(uploadFilesResultList);
            logger.info("weChatUploadProfile result{}",result);
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
            //params = parseRequestParam(request);
            String fileId = request.getParameter("fileId");
            url = profileService.downLoadFiles(fileId);
            logger.info("downloadResume url{}",url);

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
        //Params<String, Object> params = parseRequestParam(request);
        String sceneId = request.getParameter("sceneId");
        String userId = request.getParameter("userId");
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
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));
        boolean flag = profileService.getSpecifyProfileResult(employeeId);
        return Result.success(flag).toJson();
    }
}
