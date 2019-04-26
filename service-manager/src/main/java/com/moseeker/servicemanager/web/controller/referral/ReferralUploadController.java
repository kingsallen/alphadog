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
import java.io.*;
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
            String filename = (String) params.get("filename");
            String sceneId = (String) params.get("sceneId");
            if (!ProfileDocCheckTool.checkFileName(params.getString("file_name"))) {
                return Result.fail(MessageType.PROGRAM_FILE_NOT_SUPPORT).toJson();
            }
            if (!ProfileDocCheckTool.checkFileLength(file.getSize())) {
                return Result.fail(MessageType.PROGRAM_FILE_OVER_SIZE).toJson();
            }
            ByteBuffer byteBuffer = ByteBuffer.wrap(file.getBytes());

            referralService.uploadFiles(sceneId, filename, byteBuffer);

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
            List<ReferralUploadFiles> uploadFilesResultList = referralService.getUploadFiles(unionid, pageNo, pageSize);
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
            url = referralService.downLoadFiles(sceneId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     *查询简历上传结果(解析对应简历)
     * @return
     */
    @RequestMapping(value = "/v1.2/referral/upload/resume/info",method = RequestMethod.GET)
    public String parseFileProfile(String userId,String sceneId) throws Exception {
        //找到保存url
        ReferralUploadFiles uploadFilesResult = referralService.referralResumeInfo(sceneId);
        String url = uploadFilesResult.getUrl();
        profileService.parseFileProfileByFilePath(uploadFilesResult.getUrl(), Integer.valueOf(userId));
        File file = new File(url);
        InputStream fis = null;
        try {
            fis = new BufferedInputStream(new FileInputStream(url));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            //解析文件

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
