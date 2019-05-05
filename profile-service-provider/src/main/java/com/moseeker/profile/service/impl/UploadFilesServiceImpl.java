package com.moseeker.profile.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.referraldb.ReferralUploadFilesDao;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralUploadFilesRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.util.JsonToMap;
import com.moseeker.common.util.OfficeUtils;
import com.moseeker.commonservice.utils.ProfileDocCheckTool;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.UploadFilesService;
import com.moseeker.profile.service.impl.serviceutils.StreamUtils;
import com.moseeker.profile.service.impl.vo.FileNameData;
import com.moseeker.profile.service.impl.vo.UploadFilesResult;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@CounterIface
public class UploadFilesServiceImpl implements UploadFilesService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment env;

    @Autowired
    private ReferralUploadFilesDao referralUploadFilesDao;


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
        logger.info("UploadFilesServiceImpl uploadFiles");
        logger.info("UploadFilesServiceImpl uploadFiles fileName:{}", fileName);
        UploadFilesResult uploadFilesResult = new UploadFilesResult();

        if(!ProfileDocCheckTool.checkFileName(fileName)){
            logger.error("UploadFilesServiceImpl uploadFiles 不支持的文件类型");
            throw ProfileException.REFERRAL_FILE_TYPE_NOT_SUPPORT;
        }
        byte[] dataArray = StreamUtils.ByteBufferToByteArray(fileData);
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        //保存文件到磁盘
        try {
            FileNameData fileNameData = StreamUtils.persistFile(dataArray, env.getProperty("profile.persist.url"), suffix);
            logger.info("保存文件到磁盘返回的文件名称"+fileNameData.toString());
            fileNameData.setSaveUrl(env.getProperty("profile.persist.url"));
            fileNameData.setOriginName(fileName);
            if(Constant.WORD_DOC.equals(suffix) || Constant.WORD_DOCX.equals(suffix)) {
                String pdfName = fileNameData.getFileName().substring(0,fileNameData.getFileName().lastIndexOf("."))
                        + Constant.WORD_PDF;
                int status = OfficeUtils.Word2Pdf(fileNameData.getFileAbsoluteName(),
                        fileNameData.getFileAbsoluteName().replace(fileNameData.getFileName(), pdfName));
                if(status == 1) {
                    fileNameData.setFileAbsoluteName(fileNameData.getFileAbsoluteName().replace(fileNameData.getFileName(), pdfName));
                    fileNameData.setFileName(pdfName);
                    fileNameData.setOriginName(fileNameData.getOriginName().replace(".docx", Constant.WORD_PDF)
                            .replace(".doc", Constant.WORD_PDF));
                }
            }
            logger.info("UploadFilesServiceImpl uploadFiles fileNameData:{}", JSONObject.toJSONString(fileNameData));
            Date date = new Date();
            //Timestamp timestamp = new Timestamp(date.getTime());
            SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            //后台生成文件名称
            uploadFilesResult.setFileName(fileNameData.getFileName());
            uploadFilesResult.setCreateTime(sf.format(date));
            uploadFilesResult.setSaveUrl(fileNameData.getFileAbsoluteName());
            //原始文件名称
            uploadFilesResult.setName(fileName);
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
    public Integer insertUpFiles(UploadFilesResult uploadFilesResult) {
        ReferralUploadFilesRecord referralUploadFilesRecord =new ReferralUploadFilesRecord();
        referralUploadFilesRecord.setFileid(uploadFilesResult.getFileID());
        referralUploadFilesRecord.setUniname(uploadFilesResult.getFileName());
        referralUploadFilesRecord.setUnionid(uploadFilesResult.getUnionId());
        referralUploadFilesRecord.setType(1);
        referralUploadFilesRecord.setFilename(uploadFilesResult.getName());
        referralUploadFilesRecord.setUrl(uploadFilesResult.getSaveUrl());
        referralUploadFilesRecord.setStatus(0);
        uploadFilesResult.getCompanyId();
        logger.info("insertUpFiles uploadFilesResult{}",uploadFilesResult);
        Integer integer = referralUploadFilesDao.insertInto(referralUploadFilesRecord);
        logger.info("insertUpFiles referralUploadFilesDao  integer{}",integer);
        return integer;
    }

    @Override
    public UploadFilesResult resumeInfo(String sceneId) {
        logger.info("UploadFilesServiceImpl resumeInfo");
        logger.info("UploadFilesServiceImpl resumeInfo sceneId:{}", sceneId);
        UploadFilesResult uploadFilesResult = new UploadFilesResult();
        ReferralUploadFilesRecord referralUploadFilesRecord = referralUploadFilesDao.fetchByfileId(sceneId);
        uploadFilesResult.setSaveUrl(referralUploadFilesRecord.getUrl());
        uploadFilesResult.setFileName(uploadFilesResult.getFileName());
        return uploadFilesResult;
    }


    @Override
    public List<UploadFilesResult> getUploadFiles(String unionid, Integer pageSize, Integer pageNo) throws BIZException {
        List<UploadFilesResult> uploadFilesResultList = new ArrayList<>();
        List<ReferralUploadFilesRecord> list = referralUploadFilesDao.fetchByunionid(unionid,pageSize,pageNo);
        logger.info("UploadFilesServiceImpl getUploadFiles  list.size:{}",list == null?0:list.size());
        if (list != null && list.size() != 0){
            SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            for(ReferralUploadFilesRecord referralUploadFilesRecord : list){
                UploadFilesResult uploadFilesResult = new UploadFilesResult();
                uploadFilesResult.setSaveUrl(referralUploadFilesRecord.getUrl());
                uploadFilesResult.setCreateTime(sf.format( referralUploadFilesRecord.getCreatetime() ));
                uploadFilesResult.setFileName(referralUploadFilesRecord.getFilename());
                uploadFilesResult.setFileID(String.valueOf(referralUploadFilesRecord.getId()));
                uploadFilesResult.setId(referralUploadFilesRecord.getId());
                logger.info("UploadFilesServiceImpl getUploadFiles  referralUploadFilesRecord:{}",referralUploadFilesRecord);
                uploadFilesResultList.add(uploadFilesResult);
            }
        }
        logger.info("UploadFilesServiceImpl getUploadFiles  uploadFilesResultList:{}",JSONObject.toJSONString(uploadFilesResultList));
        return uploadFilesResultList;
    }

    /*@Override
    public UploadFilesResult profile(String sceneId) {




        return null;
    }*/

    @Override
    public boolean getSpecifyProfileResult(int employeeId) throws ProfileException {
        logger.info("UploadFilesServiceImpl getSpecifyProfileResult");
        logger.info("UploadFilesServiceImpl getSpecifyProfileResult employeeId:{}", employeeId);
        return client.exists(AppId.APPID_ALPHADOG.getValue(),
                KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString(), String.valueOf(employeeId));
    }

    @Override
    public UploadFilesResult checkResult(int employeeId) throws ProfileException {
        logger.info("checkResult employeeId{}",employeeId);
        UploadFilesResult uploadFilesResult = new UploadFilesResult();
        String value = client.get(AppId.APPID_ALPHADOG.getValue(),
                KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString(),String.valueOf(employeeId));
        logger.info("checkResult redis对应的value{}",value);
        JSONObject jsonObject = JSON.parseObject(value);
        JSONObject user = jsonObject.getJSONObject("user");
        uploadFilesResult.setName(StringUtils.isNotBlank(user.getString("name"))? user.getString("name"):user.getString("nickname"));
        uploadFilesResult.setMobile(user.get("mobile").toString());
        JSONArray attachments = jsonObject.getJSONArray("attachments");
        if (attachments != null && attachments.size() > 0) {
            String fileName = ((JSONObject)attachments.get(0)).getString("name");
            uploadFilesResult.setFileName(fileName);
        }
        return uploadFilesResult;
    }

}
