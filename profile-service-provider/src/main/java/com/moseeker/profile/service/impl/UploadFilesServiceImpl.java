package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.referraldb.ReferralUploadFilesDao;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralUploadFilesRecord;
import com.moseeker.commonservice.utils.ProfileDocCheckTool;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.UploadFilesService;
import com.moseeker.profile.service.impl.resumefileupload.iface.AbstractResumeFileParser;
import com.moseeker.profile.service.impl.serviceutils.StreamUtils;
import com.moseeker.profile.service.impl.vo.FileNameData;
import com.moseeker.profile.service.impl.vo.UploadFilesResult;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UploadFilesServiceImpl implements UploadFilesService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment env;

    @Autowired
    private ReferralUploadFilesDao referralUploadFilesDao;

    @Autowired
    AbstractResumeFileParser abstractResumeFileParser;



    /**
     * 文件保存
     * @param unionId 上传人ID
     * @param fileId 上传文件id
     * @param fileName 上传文件名称
     * @param fileData 文件二进制流
     * @return
     * @throws ProfileException
     */
    @Override
    public UploadFilesResult uploadFiles(int unionId, int fileId, String fileName, ByteBuffer fileData) throws ProfileException {
        logger.info("上传文件参数："+"unionId"+unionId+"fileId:"+fileId+"fileName"+fileName+"fileData"+fileData);
        UploadFilesResult uploadFilesResult = new UploadFilesResult();

        if(!ProfileDocCheckTool.checkFileName(fileName)){
            throw ProfileException.REFERRAL_FILE_TYPE_NOT_SUPPORT;
        }
        byte[] dataArray = StreamUtils.ByteBufferToByteArray(fileData);
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        //保存文件到磁盘
        try {
            FileNameData fileNameData = StreamUtils.persistFile(dataArray, env.getProperty("profile.persist.url"), suffix);
            logger.info("保存文件到磁盘返回的文件名称"+fileNameData.toString());
            fileNameData.setOriginName(fileName);
            Date date = new Date();
            //Timestamp timestamp = new Timestamp(date.getTime());
            SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            uploadFilesResult.setFileName(fileName);
            uploadFilesResult.setCreateTime(sf.format(date));
            uploadFilesResult.setSaveUrl(fileNameData.getSaveUrl());
            uploadFilesResult.setName(fileNameData.getOriginName());
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        return uploadFilesResult;
    }



    @Override
    public String downLoadFiles(String sceneId) {
        String url = new String();
        String filename = new String();
        ReferralUploadFilesRecord referralUploadFilesRecord = referralUploadFilesDao.fetchByfileId(sceneId);
        url = referralUploadFilesRecord.getUrl();
        filename = referralUploadFilesRecord.getFilename();
        return url;
    }

    @Override
    public Integer insertUpFiles(UploadFilesResult uploadFilesResult) {
        ReferralUploadFilesRecord referralUploadFilesRecord =new ReferralUploadFilesRecord();
        referralUploadFilesRecord.setFileid(uploadFilesResult.getFileID());
        referralUploadFilesRecord.setUniname(uploadFilesResult.getName());
        referralUploadFilesRecord.setUnionid(String.valueOf(uploadFilesResult.getUserId()));
        referralUploadFilesRecord.setFilename(uploadFilesResult.getFileName());
        referralUploadFilesRecord.setUrl(uploadFilesResult.getSaveUrl());
        referralUploadFilesRecord.setStatus(0);
        uploadFilesResult.getCompanyId();
        Integer integer = referralUploadFilesDao.insertInto(referralUploadFilesRecord);
        return integer;
    }

    @Override
    public UploadFilesResult resumeInfo(String sceneId) {
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
        if (list != null && list.size() != 0){
            SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            for(ReferralUploadFilesRecord referralUploadFilesRecord : list){
                UploadFilesResult uploadFilesResult = new UploadFilesResult();
                uploadFilesResult.setSaveUrl(referralUploadFilesRecord.getUrl());
                uploadFilesResult.setCreateTime(sf.format( referralUploadFilesRecord.getCreatetime() ));
                uploadFilesResult.setFileName(referralUploadFilesRecord.getFilename());
                uploadFilesResultList.add(uploadFilesResult);
            }
        }
        return uploadFilesResultList;
    }

    @Override
    public UploadFilesResult profile(String sceneId) {
        String url = downLoadFiles(sceneId);
        //读取文件并解析
        File file = new File(url);
        FileReader fileReader = null;
        BufferedReader bufferedReader = new BufferedReader(fileReader);


        return null;
    }

}
