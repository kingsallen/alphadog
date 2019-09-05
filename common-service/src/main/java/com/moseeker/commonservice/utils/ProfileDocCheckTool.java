package com.moseeker.commonservice.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: jack
 * @Date: 2018/9/4
 */
public class ProfileDocCheckTool {

    private static final byte[] MAGIC_JPEG = {(byte) 0xFF,(byte) 0xD8,(byte) 0xFF,(byte) 0xE0} ;
    private static final byte[] MAGIC_PNG = {(byte) 0x89,(byte) 0x50,(byte) 0x4E,(byte) 0x47} ;
    private static final byte[] MAGIC_TXT = {(byte) 0x41,(byte) 0x47,(byte) 0x6B,(byte) 0x49} ;
    private static final byte[] MAGIC_PDF = {(byte) 0x25,(byte) 0x50,(byte) 0x44,(byte) 0x46} ;
    private static final byte[] MAGIC_JPG = {(byte) 0xFF,(byte) 0xD8,(byte) 0xFF,(byte) 0xE0} ;
    private static final byte[] MAGIC_XLS = {(byte) 0xD0,(byte) 0xCF,(byte) 0x11,(byte) 0xE0} ;
    private static final byte[] MAGIC_DOC = {(byte) 0xD0,(byte) 0xCF,(byte) 0x11,(byte) 0xE0} ;
    private static final byte[] MAGIC_DOCX = {(byte) 0x50,(byte) 0x4B,(byte) 0x3,(byte) 0x4} ;
    private static final byte[] MAGIC_XLSX = {(byte) 0x50,(byte) 0x4B,(byte) 0x3,(byte) 0x4} ;

    private static List<String> fileSupport = new ArrayList<String>(){{add(".DOC");add(".DOCX");add(".PDF");add(".JPG");add(".JPEG");
    add(".PNG");}};
    private static List<byte[]> MAGIC_NUMBER_SUPPORT = Arrays.asList(MAGIC_JPG,MAGIC_JPEG,MAGIC_PNG,MAGIC_TXT,
            MAGIC_DOC,MAGIC_DOCX,MAGIC_PDF,MAGIC_XLS,MAGIC_XLSX);

    private static long fileSize = 1024*1024*5;  //5M

    public static boolean checkFileLength(long length) {
        return checkFileLength(length, null);
    }

    public static boolean checkMagicNumber(byte[] bytes) {
        byte[] data4 = new byte[4];
        System.arraycopy(bytes,0,data4,0,4);
        for( byte[] magic : MAGIC_NUMBER_SUPPORT){
            if(Arrays.equals(magic,data4)){
                return true ;
            }
        }
        return false ;
    }

    public static boolean checkFileLength(long length, Long lengthUpper) {
        long lengthMax;
        if (lengthUpper == null || lengthUpper <= 0) {
            lengthMax = fileSize;
        } else {
            lengthMax = lengthUpper;
        }
        if (length >= lengthMax) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 校验文件是否是支持的文件格式
     * @param fileName 文件名称
     * @param fileSupport 支持的文件后缀
     * @return true 支持；false 不支持
     */
    public static boolean checkFileName(String fileName, List<String> fileSupport) {
        List<String> fileSupportParam;
        if (fileSupport == null || fileSupport.size() == 0) {
            fileSupportParam = ProfileDocCheckTool.fileSupport;
        } else {
            fileSupportParam = fileSupport;
        }
        for (String support : fileSupportParam) {
            if (fileName.toUpperCase().endsWith(support)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验文件是否是支持的文件格式
     * @param fileName 文件名称
     * @return true 支持；false 不支持
     */
    public static boolean checkFileName(String fileName) {
        return checkFileName(fileName, null) ;
    }


    /**
     * 校验文件是否是支持的文件格式
     * @param fileName 文件名称
     * @param fileContent 文件内容
     * @return true 支持；false 不支持
     */
    public static boolean checkFileFormat(String fileName,byte[] fileContent) {
        return checkFileName(fileName) && checkMagicNumber(fileContent);
    }
}
