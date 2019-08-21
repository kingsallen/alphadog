package com.moseeker.servicemanager.web.controller.referral;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.commonservice.utils.ProfileDocCheckTool;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.web.controller.MessageType;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.servicemanager.web.controller.referral.vo.ParseResult;
import com.moseeker.servicemanager.web.controller.referral.vo.UploadControllerVO;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.profile.service.ProfileServices;
import com.moseeker.thrift.gen.profile.struct.ProfileParseResult;
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
    public String uploadProfile(MultipartFile file, HttpServletRequest request){
        logger.info("ReferralUploadController weChatUploadProfile");
        logger.info("ReferralUploadController weChatUploadProfile file.length:{},  file.name:{}",file.getSize(), file.getName());
        UploadControllerVO result = new UploadControllerVO();
        try {
            String sceneId =  request.getParameter("sceneId");
            String unionId =  request.getParameter("unionId");
            String fileName = request.getParameter("fileName");

            logger.info("上传文件存储参数： sceneId:{}, unionId:{}, fileName:{}",sceneId, unionId, fileName);
            logger.info("ReferralUploadController uploadProfile size:{}", file.getSize());
            logger.info("ReferralUploadController uploadProfile bytes:{}", file.getBytes().length);
            if (!ProfileDocCheckTool.checkFileLength(file.getSize())) {
                logger.info("uploadProfile checkFileLength  PROGRAM_FILE_OVER_SIZE");
                return Result.fail(99999, "请上传小于5M的文件！").toJson();
            }

            if (!ProfileDocCheckTool.checkFileFormat(fileName,file.getBytes())) {
                return Result.fail(MessageType.PROGRAM_FILE_NOT_SUPPORT).toJson();
            }

            ByteBuffer byteBuffer = ByteBuffer.wrap(file.getBytes());
            logger.info("uploadProfile byteBuffer{}",byteBuffer);
            ReferralUploadFiles referralUploadFiles =  profileService.uploadFiles(sceneId,unionId,fileName,byteBuffer);
            logger.info("uploadProfile： referralUploadFiles:{}", JSONObject.toJSONString(referralUploadFiles));
            result.setName(referralUploadFiles.getFilename());
            result.setSaveUrl(referralUploadFiles.getUrl());
            result.setFileId(String.valueOf(referralUploadFiles.getId()));
            result.setCreateTime(referralUploadFiles.getCreate_time());
            logger.info("uploadProfile： result:{}", JSONObject.toJSONString(result));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.fail(99999, e.getMessage()).toJson();
        }
        return Result.success(result).toJson();
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
        logger.info("ReferralUploadController parseFileProfile");
        //Params<String, Object> params = parseRequestParam(request);
        String fileId = request.getParameter("fileId");
        String userId = request.getParameter("userId");
        String sceneId = request.getParameter("sceneId");
        logger.info("ReferralUploadController  parseFileProfile  fileId{},userId{},sceneId{}",fileId,userId,sceneId);
        ReferralUploadFiles uploadFilesResult = profileService.referralResumeInfo(fileId);
        logger.info("ReferralUploadController parseFileProfile:{}", JSONObject.toJSONString(uploadFilesResult));
        com.moseeker.thrift.gen.profile.struct.ProfileParseResult result =
                profileService.parseFileProfileByFilePath(uploadFilesResult.getUrl(), Integer.valueOf(userId), sceneId);
        return Result.success(result).toJson();
    }

    /**
     * 轮询是否已经指定推荐的简历
     * @return true 已经指定号推荐的简历；false位置定好推荐的简历
     * @throws Exception
     */
    @RequestMapping(value = "/v1.2/resuem/upload/complete",method = RequestMethod.GET)
    public String getSpecifyProfileResult(HttpServletRequest request) throws Exception {
        logger.info("ReferralUploadController getSpecifyProfileResult");
        Params<String, Object> params = parseRequestParam(request);
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));
        String syncId = request.getParameter("syncId");
        logger.info("ReferralUploadController getSpecifyProfileResult employeeId:{}, syncId:{}", employeeId, syncId);
        boolean flag = profileService.getSpecifyProfileResult(employeeId, syncId);
        logger.info("ReferralUploadController getSpecifyProfileResult flag:{}", flag);
        return Result.success(flag).toJson();
    }

    /**
     *  返回解析的简历内容
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "v1.2/referral/upload/candidate/info",method = RequestMethod.GET)
    public String getCandidateInfo(HttpServletRequest request) throws Exception {
        Params<String,Object> params = parseRequestParam(request);
        ParseResult parseResult = new ParseResult();
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));
        logger.info("getCandidateInfo  employeeId{}",employeeId);
        ProfileParseResult profileParseResult = profileService.checkResult(employeeId);
        logger.info("ReferralUploadController getCandidateInfo profileParseResult:{}", JSONObject.toJSONString(profileParseResult));
        parseResult.setFilename(profileParseResult.getFile());
        if (profileParseResult.getMobile() != null){
            parseResult.setMobile(profileParseResult.getMobile());
        }
        parseResult.setName(profileParseResult.getName());
        logger.info("ReferralUploadController getCandidateInfo parseResult:{}", JSONObject.toJSONString(parseResult));
        logger.info("ReferralUploadController  Result.success.toJson{}", Result.success(parseResult).toJson() );
        return Result.success(parseResult).toJson();
    }
}
