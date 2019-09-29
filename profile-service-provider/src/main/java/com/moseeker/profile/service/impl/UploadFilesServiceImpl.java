package com.moseeker.profile.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.referraldb.ReferralUploadFilesDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralUploadFilesRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.commonservice.utils.ProfileDocCheckTool;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.UploadFilesService;
import com.moseeker.profile.service.impl.serviceutils.StreamUtils;
import com.moseeker.profile.service.impl.vo.FileNameData;
import com.moseeker.profile.service.impl.vo.UploadFilesResult;
import com.moseeker.profile.utils.OfficeUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@CounterIface
public class UploadFilesServiceImpl implements UploadFilesService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment env;

    @Autowired
    private ReferralUploadFilesDao referralUploadFilesDao;

    @Autowired
    private UserEmployeeDao employeeDao;

    @Resource(name = "cacheClient")
    private RedisClient client;

    /**
     * 文件保存
     * @param fileName 上传文件名称
     * @param fileData 文件二进制流
     * @return
     * @throws ProfileException
     */
    @Override
    public UploadFilesResult uploadFiles(String fileName, ByteBuffer fileData) throws ProfileException {
        logger.info("上传文件参数：fileName: {} ,fileData: {}",fileName,fileData);
        UploadFilesResult uploadFilesResult = new UploadFilesResult();

        if(!ProfileDocCheckTool.checkFileName(fileName)){
            throw ProfileException.REFERRAL_FILE_TYPE_NOT_SUPPORT;
        }
        byte[] dataArray = StreamUtils.ByteBufferToByteArray(fileData);
        String suffix = OfficeUtils.getSuffix(fileName);
        //保存文件到磁盘
        try {
            FileNameData fileNameData = StreamUtils.persistFile(dataArray, env.getProperty("profile.persist.url"), suffix);
            logger.info("保存文件到磁盘返回的文件名称{}",fileNameData);
            fileNameData.setSaveUrl(env.getProperty("profile.persist.url"));
            fileNameData.setOriginName(fileName);
            if(OfficeUtils.isWordFile(suffix)) {
                LocalDateTime anylisisStart = LocalDateTime.now();
                String pdfName = OfficeUtils.getPdfName(fileNameData.getFileName());
                int status = OfficeUtils.Word2Pdf(fileNameData.getFileAbsoluteName(),
                        fileNameData.getFileAbsoluteName().replace(fileNameData.getFileName(), pdfName));
                if(status == 1) {
                    fileNameData.setFileAbsoluteName(fileNameData.getFileAbsoluteName().replace(fileNameData.getFileName(), pdfName));
                    fileNameData.setFileName(pdfName);
                    fileNameData.setOriginName(fileNameData.getOriginName().replace(".docx", Constant.WORD_PDF)
                            .replace(".doc", Constant.WORD_PDF));
                }
                LocalDateTime anylisisEnd = LocalDateTime.now();
                Duration duration = Duration.between(anylisisStart,anylisisEnd);
                long millis = duration.toMillis();//相差毫秒数
                logger.info("UploadFilesServiceImpl uploadFiles 上传文件转化为pdf耗时：millis{}",millis);
            }
            logger.info("UploadFilesServiceImpl uploadFiles fileNameData:{}", JSONObject.toJSONString(fileNameData));

            Date date = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            //后台生成文件名称
            uploadFilesResult.setFileName(fileNameData.getFileName());
            uploadFilesResult.setCreateTime(sf.format(date));
            uploadFilesResult.setSaveUrl(fileNameData.getFileAbsoluteName());
            //原始文件名称
            uploadFilesResult.setName(fileNameData.getOriginName());
            logger.info("UploadFilesServiceImpl uploadFiles uploadFilesResult:{}", JSONObject.toJSONString(uploadFilesResult));
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        return uploadFilesResult;
    }



    @Override
    public String downLoadFiles(String fileId) {
        String url = new String();
        //String filename = new String();
        ReferralUploadFilesRecord referralUploadFilesRecord = referralUploadFilesDao.fetchByfileId(fileId);
        url = referralUploadFilesRecord.getUrl();
        //filename = referralUploadFilesRecord.getFilename();
        return url;
    }

    @Override
    public UploadFilesResult insertUpFiles(UploadFilesResult uploadFilesResult) {
        UploadFilesResult uploadFilesResult1 = new UploadFilesResult();
        ReferralUploadFilesRecord referralUploadFilesRecord =new ReferralUploadFilesRecord();
        referralUploadFilesRecord.setFileid(uploadFilesResult.getFileID());
        referralUploadFilesRecord.setUniname(uploadFilesResult.getFileName());
        referralUploadFilesRecord.setUnionid(uploadFilesResult.getUnionId());
        referralUploadFilesRecord.setType(1);
        referralUploadFilesRecord.setFilename(uploadFilesResult.getName());
        referralUploadFilesRecord.setUrl(uploadFilesResult.getSaveUrl());
        referralUploadFilesRecord.setStatus((byte) 0);
        uploadFilesResult.getCompanyId();
        logger.info("insertUpFiles uploadFilesResult{}",uploadFilesResult);
        ReferralUploadFilesRecord referralresult = referralUploadFilesDao.insertInto(referralUploadFilesRecord);
        logger.info("insertUpFiles referralUploadFilesDao  referralresult{}",referralresult);
        uploadFilesResult1.setId( referralresult.getId() );
        uploadFilesResult1.setFileName( referralresult.getFilename() );
        uploadFilesResult1.setSaveUrl( referralresult.getUrl() );
        return uploadFilesResult1;
    }

    @Override
    public UploadFilesResult resumeInfo(String fileId) {
        UploadFilesResult uploadFilesResult = new UploadFilesResult();
        ReferralUploadFilesRecord referralUploadFilesRecord = referralUploadFilesDao.fetchByfileId(fileId);
        uploadFilesResult.setSaveUrl(referralUploadFilesRecord.getUrl());
        uploadFilesResult.setFileName(referralUploadFilesRecord.getFilename());
        return uploadFilesResult;
    }


    @Override
    public List<UploadFilesResult> getUploadFiles(String unionid, Integer pageSize, Integer pageNo) throws BIZException {
        List<UploadFilesResult> uploadFilesResultList = new ArrayList<>();
        List<ReferralUploadFilesRecord> list = referralUploadFilesDao.fetchByunionid(unionid,pageSize,pageNo);
        if (list != null && list.size() != 0){
            SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            for(ReferralUploadFilesRecord referralUploadFilesRecord : list){
                UploadFilesResult uploadFilesResult = new UploadFilesResult();
                uploadFilesResult.setSaveUrl(referralUploadFilesRecord.getUrl());
                uploadFilesResult.setCreateTime(sf.format( referralUploadFilesRecord.getCreateTime() ));
                uploadFilesResult.setFileName(referralUploadFilesRecord.getFilename());
                uploadFilesResult.setFileID(String.valueOf(referralUploadFilesRecord.getId()));
                uploadFilesResult.setId(referralUploadFilesRecord.getId());
                uploadFilesResultList.add(uploadFilesResult);
            }
        }
        return uploadFilesResultList;
    }

    @Override
    public boolean getSpecifyProfileResult(int employeeId,String syncId) throws ProfileException {
        logger.info("UploadFilesServiceImpl getSpecifyProfileResult");
        logger.info("UploadFilesServiceImpl getSpecifyProfileResult employeeId:{}", employeeId);
        UserEmployeeDO employeeDO = employeeDao.getEmployeeById(employeeId);
        if (employeeDO == null) {
            throw ProfileException.PROFILE_EMPLOYEE_NOT_EXIST;
        }
        String key = String.valueOf(employeeDO.getSysuserId())+"&"+syncId;
        logger.info("getSpecifyProfileResult userId{}&syncId{}",employeeDO.getSysuserId(),syncId);
        return client.exists(AppId.APPID_ALPHADOG.getValue(),
                KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString(),key);
    }

    @Override
    public UploadFilesResult checkResult(int employeeId) throws ProfileException {
        logger.info("checkResult employeeId{}",employeeId);
        UploadFilesResult uploadFilesResult = new UploadFilesResult();
        String value = client.get(AppId.APPID_ALPHADOG.getValue(),
                KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString(),String.valueOf(employeeId));
        logger.info("checkResult redis对应的value{}",value);
        JSONObject jsonObject = JSON.parseObject(value);
        logger.info("UploadFilesServiceImpl checkResult jsonObject:{}", jsonObject.toJSONString());
        JSONObject user = jsonObject.getJSONObject("user");
        logger.info("UploadFilesServiceImpl checkResult user{}",user);
        if (user != null){
            logger.info("UploadFilesServiceImpl checkResult user:{}", user.toJSONString());
            uploadFilesResult.setName(StringUtils.isNotBlank(user.getString("name"))? user.getString("name"):user.getString("nickname"));
            logger.info("UploadFilesServiceImpl checkResult ,user.getmobile{}",user.get("mobile"));
            if (user.get("mobile") != null){
                String mobile = user.get("mobile").toString();
                uploadFilesResult.setMobile(mobile.trim().equals("0")?"":mobile);
            }

            if(user.get("email") != null){
                uploadFilesResult.setEmail(StringUtils.trimToEmpty(user.getString("email")));
            }
        }
        if (jsonObject.get("attachments") != null){
            JSONArray attachments = jsonObject.getJSONArray("attachments");
            if (attachments != null && attachments.size() > 0) {
                String url = ((JSONObject)attachments.get(0)).getString("path");
                File pdfFile = new File(OfficeUtils.getPdfName(url));
                ReferralUploadFilesRecord referralUploadFilesRecord = referralUploadFilesDao.fetchByUrl(pdfFile.exists()?pdfFile.getAbsolutePath():url);
                String fileName = referralUploadFilesRecord.getFilename();
                logger.info("UploadFilesServiceImpl  checkResult  fileName{}",fileName);
                //String fileName = ((JSONObject)attachments.get(0)).getString("name");
                uploadFilesResult.setFileName(fileName);
            }
        }
        logger.info("UploadFilesServiceImpl checkResult uploadFilesResult:{}", JSONObject.toJSONString(uploadFilesResult));
        return uploadFilesResult;
    }

    @Override
    public String setRedisKey(String userId, String sceneId) {
        String key = userId+"&"+sceneId;
        logger.info("UploadFilesServiceImpl userId{},sceneId{}",userId,sceneId);
        String result = client.set(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString(),
                key,"","userId&sceneId对应的redis值",8*60*60);
        logger.info("UploadFilesServiceImpl setRedisKey result{}",result);
        return result;
    }

}
