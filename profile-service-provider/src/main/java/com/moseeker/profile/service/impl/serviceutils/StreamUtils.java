package com.moseeker.profile.service.impl.serviceutils;

import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.vo.FileNameData;
import org.apache.http.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 二进制操作工具
 * @Author: jack
 * @Date: 2018/9/4
 */
public class StreamUtils {

    private static Logger logger = LoggerFactory.getLogger(StreamUtils.class);

    /**
     * 将ByteBuffer数据转成String
     * @param byteBuffer 字节数据
     * @return base64转码后的字符串
     */
    public static String ByteBufferToBase64String(ByteBuffer byteBuffer) {
        byte[] arr = new byte[byteBuffer.remaining()];
        byteBuffer.get(arr);
        byteBuffer.clear();
        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(arr), Consts.UTF_8);
    }

    /**
     * 将ByteBuffer数据转成byte数组
     * @param byteBuffer 字节数据
     * @return 字节数组
     */
    public static byte[] ByteBufferToByteArray(ByteBuffer byteBuffer) {
        byte[] arr = new byte[byteBuffer.remaining()];
        byteBuffer.get(arr);
        byteBuffer.clear();
        return arr;
    }

    /**
     * 简历文件保存
     * @param dataArray 简历二进制数据
     * @param dirAddress 文件根目录
     * @param suffix 后缀名称
     * @return 新的文件名称
     * @throws ProfileException 业务异常
     */
    public static FileNameData persistFile(byte[] dataArray, String dirAddress, String suffix) throws ProfileException {
        FileNameData fileNameData = new FileNameData();
        LocalDateTime now = LocalDateTime.now();
        String monthFileName = now.getYear()+""+now.getMonthValue();
        synchronized (StreamUtils.class) {
            File rootFile = new File(dirAddress);
            if (!rootFile.exists()) {
                rootFile.mkdir();
            }
            File monthFile = new File(dirAddress+File.separator+monthFileName);
            if (!monthFile.exists()) {
                monthFile.mkdir();
            }
        }

        String fileName = UUID.randomUUID().toString()+"."+suffix;
        fileNameData.setFileName(fileName);
        fileNameData.setFileAbsoluteName(dirAddress+File.separator+monthFileName+File.separator+fileName);

        File file = new File(dirAddress+File.separator+monthFileName+File.separator+fileName);
        try (FileOutputStream fop = new FileOutputStream(file)) {
            if (!file.exists()) {
                file.createNewFile();
            }
            fop.write(dataArray);
            fop.flush();
            fop.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ProfileException.PROFILE_FILE_SAVE_FAILED;
        }

        return fileNameData;
    }

    public static String byteArrayToBase64String(byte[] dataArray) {
        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(dataArray), Consts.UTF_8);
    }

    public static String convertASCToUTF8(String fileData) {
        return new String(fileData.getBytes(Consts.ASCII), Consts.UTF_8);
    }
}
